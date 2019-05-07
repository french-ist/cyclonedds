/**
  Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 
  This program and the accompanying materials are made available under the
  terms of the Eclipse Public License v. 2.0 which is available at
  http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
  v. 1.0 which is available at
  http://www.eclipse.org/org/documents/edl-v10.php.
 
  SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.cyclonedds.sub;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.eclipse.cyclonedds.core.AbstractDDSObject;
import org.omg.dds.core.DDSObject;
import org.omg.dds.core.Duration;
import org.omg.dds.core.InstanceHandle;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.Time;
import org.omg.dds.core.policy.ResourceLimits;
import org.omg.dds.core.status.LivelinessChangedStatus;
import org.omg.dds.core.status.RequestedDeadlineMissedStatus;
import org.omg.dds.core.status.RequestedIncompatibleQosStatus;
import org.omg.dds.core.status.SampleLostStatus;
import org.omg.dds.core.status.SampleRejectedStatus;
import org.omg.dds.core.status.SubscriptionMatchedStatus;
import org.omg.dds.sub.DataReaderQos;
import org.omg.dds.sub.ReadCondition;
import org.omg.dds.sub.Sample;
import org.omg.dds.sub.Sample.Iterator;
import org.omg.dds.topic.PublicationBuiltinTopicData;
import org.eclipse.cyclonedds.core.DDSExceptionImpl;
import org.eclipse.cyclonedds.core.IllegalArgumentExceptionImpl;
import org.eclipse.cyclonedds.core.InstanceHandleImpl;
import org.eclipse.cyclonedds.core.ServiceEnvironmentImpl;
import org.eclipse.cyclonedds.core.PreconditionNotMetExceptionImpl;
import org.eclipse.cyclonedds.core.Utilities;
import org.eclipse.cyclonedds.core.policy.PolicyConverter;
import org.eclipse.cyclonedds.core.status.StatusConverter;
import org.eclipse.cyclonedds.topic.PublicationBuiltinTopicDataImpl;

//TODO FRCYC import SampleInfoHolder;
//TODO FRCYC import SampleInfoSeqHolder;

public class ReflectionDataReader<TYPE, OUT_TYPE> extends AbstractDDSObject
        implements
        DDSObject {
    private final ServiceEnvironmentImpl environment;
    private final AbstractDataReader<OUT_TYPE> reader;
    private final DataReader old = null;
    private final Class<?> sampleSeqHolderClz;
    private final Field sampleSeqHolderValueField;

    private final Class<?> sampleHolderClz;
    private final Field sampleHolderValueField;

    private final Method read;
    private final Method take;
    private final Method readCondition;
    private final Method takeCondition;
    private final Method readNextSample = null;
    private final Method takeNextSample = null;
    private final Method readInstance;
    private final Method takeInstance;
    private final Method readNextInstance;
    private final Method takeNextInstance;
    private final Method readNextInstanceCondition;
    private final Method takeNextInstanceCondition;
    private final Method returnLoan;
    private final Method getKeyValue;
    private final Method lookupInstance;

    public ReflectionDataReader(ServiceEnvironmentImpl environment,
            AbstractDataReader<OUT_TYPE> reader, Class<TYPE> ddsTypeClz) {
        this.environment = environment;
        this.reader = reader;
        //TODO FRCYC this.old = reader.getOld();

        Class<?> typedReaderClz;
        String typedReaderClzName = ddsTypeClz.getName() + "DataReaderImpl";

        try {
            typedReaderClz = Class.forName(typedReaderClzName);

            this.sampleHolderClz = Class.forName(ddsTypeClz.getName() + "Holder");
            this.sampleHolderValueField = this.sampleHolderClz
                    .getDeclaredField("value");

            this.sampleSeqHolderClz = Class.forName(ddsTypeClz.getName()
                    + "SeqHolder");
            this.sampleSeqHolderValueField = this.sampleSeqHolderClz
                    .getDeclaredField("value");

            this.read = typedReaderClz.getMethod("read",
                    this.sampleSeqHolderClz, SampleInfoSeqHolder.class,
                    int.class, int.class, int.class, int.class);
            this.take = typedReaderClz.getMethod("take",
                    this.sampleSeqHolderClz, SampleInfoSeqHolder.class,
                    int.class, int.class, int.class, int.class);

            this.readCondition = typedReaderClz.getMethod("read_w_condition",
                    this.sampleSeqHolderClz, SampleInfoSeqHolder.class,
                    int.class, ReadCondition.class);
            this.takeCondition = typedReaderClz.getMethod("take_w_condition",
                    this.sampleSeqHolderClz, SampleInfoSeqHolder.class,
                    int.class, ReadCondition.class);
            /* TODO FRCYC            
            this.readNextSample = typedReaderClz.getMethod("read_next_sample",
                    this.sampleHolderClz, SampleInfoHolder.class);
            this.takeNextSample = typedReaderClz.getMethod("take_next_sample",
                    this.sampleHolderClz, SampleInfoHolder.class);
			*/

            this.readInstance = typedReaderClz.getMethod("read_instance",
                    this.sampleSeqHolderClz, SampleInfoSeqHolder.class,
                    int.class, long.class, int.class, int.class, int.class);
            this.takeInstance = typedReaderClz.getMethod("take_instance",
                    this.sampleSeqHolderClz, SampleInfoSeqHolder.class,
                    int.class, long.class, int.class, int.class, int.class);

            this.readNextInstance = typedReaderClz.getMethod(
                    "read_next_instance", this.sampleSeqHolderClz,
                    SampleInfoSeqHolder.class, int.class, long.class,
                    int.class, int.class, int.class);
            this.takeNextInstance = typedReaderClz.getMethod(
                    "take_next_instance", this.sampleSeqHolderClz,
                    SampleInfoSeqHolder.class, int.class, long.class,
                    int.class, int.class, int.class);

            this.readNextInstanceCondition = typedReaderClz.getMethod(
                    "read_next_instance_w_condition", this.sampleSeqHolderClz,
                    SampleInfoSeqHolder.class, int.class, long.class,
                    ReadCondition.class);
            this.takeNextInstanceCondition = typedReaderClz.getMethod(
                    "take_next_instance_w_condition", this.sampleSeqHolderClz,
                    SampleInfoSeqHolder.class, int.class, long.class,
                    ReadCondition.class);

            this.returnLoan = typedReaderClz.getMethod("return_loan",
                    this.sampleSeqHolderClz, SampleInfoSeqHolder.class);

            this.getKeyValue = typedReaderClz.getMethod("get_key_value",
                    this.sampleHolderClz, long.class);
            this.lookupInstance = typedReaderClz.getMethod("lookup_instance",
                    ddsTypeClz);
        } catch (ClassNotFoundException e) {
            throw new PreconditionNotMetExceptionImpl(
                    environment,
                    "Cannot find Typed DataReader '"
                            + typedReaderClzName
                            + "' that should have been generated manually with the OpenSplice IDL pre-processor.("
                            + e.getMessage() + ").");
        } catch (NoSuchMethodException e) {
            throw new DDSExceptionImpl(environment,
                    "Cannot find correct methods in OpenSplice IDL pre-processor generated class: "
                            + typedReaderClzName + " (" + e.getMessage() + ").");
        } catch (NoSuchFieldException e) {
            throw new DDSExceptionImpl(
                    environment,
                    "Cannot find 'value' field in "
                            + "the typed sampleHolderClass "
                            + "that should have been generated by the OpenSplice IDL pre-processor ("
                            + e.getMessage() + ").");
        } catch (SecurityException e) {
            throw new PreconditionNotMetExceptionImpl(
                    environment,
                    "Insufficient rights to find methods/fields in code that has been generated by the OpenSplice IDL pre-processor ("
                            + e.getMessage() + ").");
        }
    }

    @Override
    public ServiceEnvironment getEnvironment() {
        return this.environment;
    }

    public DataReaderQos getQos() {
        /* TODO FRCYC
         DataReaderQosHolder holder = new DataReaderQosHolder();
        int rc = this.old.get_qos(holder);
        Utilities.checkReturnCode(rc, this.environment,
                "DataReader.getQos() failed.");

        return DataReaderQosImpl.convert(this.environment, holder.value);
        */
    	return null;
    }

    public void setQos(DataReaderQos qos) {
        DataReaderQosImpl q;

        if (qos == null) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Supplied DataReaderQos is null.");
        }
        try {
            q = (DataReaderQosImpl) qos;
        } catch (ClassCastException e) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Setting non-OpenSplice Qos not supported.");
        }
        /* TODO FRCYC
        int rc = this.old.set_qos(q.convert());
        Utilities.checkReturnCode(rc, this.environment,
                "DataReader.setQos() failed."); */
    }

    public SampleRejectedStatus getSampleRejectedStatus() {
    	/* TODO FRCYC
        SampleRejectedStatusHolder holder = new SampleRejectedStatusHolder();

        int rc = this.old.get_sample_rejected_status(holder);
        Utilities.checkReturnCode(rc, this.environment,
                "DataReader.getSampleRejectedStatus() failed.");

        return StatusConverter.convert(this.environment, holder.value);
        */
    	return null;
    }

    public LivelinessChangedStatus getLivelinessChangedStatus() {
    	/* TODO FRCYC
        LivelinessChangedStatusHolder holder = new LivelinessChangedStatusHolder();

        int rc = this.old.get_liveliness_changed_status(holder);
        Utilities.checkReturnCode(rc, this.environment,
                "DataReader.getLivelinessChangedStatus() failed.");

        return StatusConverter.convert(this.environment, holder.value);
    	*/
    	return null;
    }

    public RequestedDeadlineMissedStatus getRequestedDeadlineMissedStatus() {
    	/* TODO FRCYC
        RequestedDeadlineMissedStatusHolder holder = new RequestedDeadlineMissedStatusHolder();

        int rc = this.old.get_requested_deadline_missed_status(holder);
        Utilities.checkReturnCode(rc, this.environment,
                "DataReader.getRequestedDeadlineMissedStatus() failed.");

        return StatusConverter.convert(this.environment, holder.value);
        */
    	return null;
    }

    public RequestedIncompatibleQosStatus getRequestedIncompatibleQosStatus() {    	
    	/* TODO FRCYC
        RequestedIncompatibleQosStatusHolder holder = new RequestedIncompatibleQosStatusHolder();

        int rc = this.old.get_requested_incompatible_qos_status(holder);
        Utilities.checkReturnCode(rc, this.environment,
                "DataReader.getRequestedIncompatibleQosStatus() failed.");

        return StatusConverter.convert(this.environment, holder.value);
        */
    	return null;
    }

    public SubscriptionMatchedStatus getSubscriptionMatchedStatus() {
    	/* TODO FRCYC
        SubscriptionMatchedStatusHolder holder = new SubscriptionMatchedStatusHolder();

        int rc = this.old.get_subscription_matched_status(holder);
        Utilities.checkReturnCode(rc, this.environment,
                "DataReader.getSubscriptionMatchedStatus() failed.");

        return StatusConverter.convert(this.environment, holder.value);
        */
    	return null;
    }

    public SampleLostStatus getSampleLostStatus() {    	
    	/* TODO FRCYC
        SampleLostStatusHolder holder = new SampleLostStatusHolder();

        int rc = this.old.get_sample_lost_status(holder);
        Utilities.checkReturnCode(rc, this.environment,
                "DataReader.getSampleLostStatus() failed.");

        return StatusConverter.convert(this.environment, holder.value);
        */
    	return null;
    }

    public void waitForHistoricalData(Duration maxWait) throws TimeoutException {
    	/* TODO FRCYC
        int rc = this.old.wait_for_historical_data(Utilities.convert(this.environment,
                maxWait));
        Utilities.checkReturnCodeWithTimeout(rc, this.environment,
                "DataReader.waitForHistoricalData() failed.");
		*/
    }

    public void waitForHistoricalData(long maxWait, TimeUnit unit)
            throws TimeoutException {
        this.waitForHistoricalData(this.environment.getSPI().newDuration(
                maxWait, unit));
    }

    public void waitForHistoricalData(String filterExpression,
            List<String> filterParameters, Time minSourceTimestamp,
            Time maxSourceTimestamp, ResourceLimits resourceLimits,
            Duration maxWait) throws TimeoutException {
        if (resourceLimits == null) {
            throw new IllegalArgumentExceptionImpl(environment,
                    "Invalid resourceLimits (null) supplied.");
        }
        String[] params;

        if(filterParameters != null){
            params = filterParameters.toArray(new String[filterParameters
                    .size()]);
        } else {
            params = null;
        }
        /* TODO FRCYC
        int rc = this.old.wait_for_historical_data_w_condition(
                filterExpression, params,
                Utilities.convert(this.environment, minSourceTimestamp),
                Utilities.convert(this.environment, maxSourceTimestamp),
                
                PolicyConverter.convert(this.environment, resourceLimits),
                Utilities.convert(this.environment, maxWait));
        Utilities.checkReturnCodeWithTimeout(rc, this.environment,
                "DataReader.waitForHistoricalData() failed.");
                */
    }

    public void waitForHistoricalData(String filterExpression,
            List<String> filterParameters, Time minSourceTimestamp,
            Time maxSourceTimestamp, ResourceLimits resourceLimits,
            long maxWait, TimeUnit unit) throws TimeoutException {
        this.waitForHistoricalData(filterExpression, filterParameters,
                minSourceTimestamp, maxSourceTimestamp, resourceLimits,
                this.environment.getSPI().newDuration(maxWait, unit));
    }

    public Set<InstanceHandle> getMatchedPublications() {
    	/* TODO FRCYC
        InstanceHandleSeqHolder holder = new InstanceHandleSeqHolder();
        Set<InstanceHandle> handles;

        int rc = this.old.get_matched_publications(holder);
        Utilities.checkReturnCode(rc, this.environment,
                "DataReader.getMatchedPublications() failed.");

        handles = new HashSet<InstanceHandle>();

        for (long handle : holder.value) {
            handles.add(Utilities.convert(this.environment, handle));
        }
        return handles;
    	*/
    	return null;
    }
    

    public PublicationBuiltinTopicData getMatchedPublicationData(
            InstanceHandle publicationHandle) {
    	/* TODO FRCYC
        PublicationBuiltinTopicDataHolder holder = new PublicationBuiltinTopicDataHolder();
        int rc = this.old.get_matched_publication_data(holder,
                Utilities.convert(this.environment, publicationHandle));
        Utilities.checkReturnCode(rc, this.environment,
                "DataReader.getMatchedPublicationData() failed.");
        if (holder.value != null) {
            return new PublicationBuiltinTopicDataImpl(this.environment,
                    holder.value);
        }
        throw new PreconditionNotMetExceptionImpl(this.environment,
                    "No data for this instanceHandle.");
                    */
    	return null;
    }

    public TYPE getKeyValue(TYPE keyHolder, InstanceHandle handle) {
        Object sampleHolder;

        if (keyHolder == null) {
            throw new IllegalArgumentException(
                    "Invalid key holder (null) provided.");
        }

        try {
            sampleHolder = this.sampleHolderClz.newInstance();
            this.sampleHolderValueField.set(sampleHolder, keyHolder);
            int rc = (Integer) this.getKeyValue.invoke(this.old, sampleHolder,
                    Utilities.convert(this.environment, handle));
            Utilities.checkReturnCode(rc, this.environment,
                    "DataReader.getKeyValue() failed.");
        } catch (InstantiationException e) {
            throw new DDSExceptionImpl(environment, "Internal error ("
                    + e.getMessage() + ").");
        } catch (IllegalAccessException e) {
            throw new DDSExceptionImpl(environment, "Internal error ("
                    + e.getMessage() + ").");
        } catch (IllegalArgumentExceptionImpl e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw new DDSExceptionImpl(environment, "Internal error ("
                    + e.getMessage() + ").");
        } catch (InvocationTargetException e) {
            throw new DDSExceptionImpl(environment, "Internal error ("
                    + e.getMessage() + ").");
        } catch (ClassCastException e) {
            throw new DDSExceptionImpl(environment, "Internal error ("
                    + e.getMessage() + ").");
        }
        return keyHolder;
    }

    @SuppressWarnings("unchecked")
    public TYPE getKeyValue(InstanceHandle handle) {
        Object sampleHolder;


        try {
            sampleHolder = this.sampleHolderClz.newInstance();
            int rc = (Integer) this.getKeyValue.invoke(this.old, sampleHolder,
                    Utilities.convert(this.environment, handle));
            Utilities.checkReturnCode(rc, this.environment,
                    "DataReader.getKeyValue() failed.");

            return (TYPE) this.sampleHolderValueField.get(sampleHolder);
        } catch (InstantiationException e) {
            throw new DDSExceptionImpl(environment, "Internal error ("
                    + e.getMessage() + ").");
        } catch (IllegalAccessException e) {
            throw new DDSExceptionImpl(environment, "Internal error ("
                    + e.getMessage() + ").");
        } catch (IllegalArgumentExceptionImpl e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw new DDSExceptionImpl(environment, "Internal error ("
                    + e.getMessage() + ").");
        } catch (InvocationTargetException e) {
            throw new DDSExceptionImpl(environment, "Internal error ("
                    + e.getMessage() + ").");
        } catch (ClassCastException e) {
            throw new DDSExceptionImpl(environment, "Internal error ("
                    + e.getMessage() + ").");
        }
    }

    public InstanceHandle lookupInstance(TYPE keyHolder) {
        long oldHandle;

        if (keyHolder == null) {
            throw new IllegalArgumentException(
                    "Invalid key holder (null) provided.");
        }

        try {
            oldHandle = (Long) this.lookupInstance.invoke(this.old, keyHolder);
        } catch (IllegalAccessException e) {
            throw new DDSExceptionImpl(environment, "Internal error ("
                    + e.getMessage() + ").");
        } catch (IllegalArgumentExceptionImpl e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw new DDSExceptionImpl(environment, "Internal error ("
                    + e.getMessage() + ").");
        } catch (InvocationTargetException e) {
            throw new DDSExceptionImpl(environment, "Internal error ("
                    + e.getMessage() + ").");
        } catch (ClassCastException e) {
            throw new DDSExceptionImpl(environment, "Internal error ("
                    + e.getMessage() + ").");
        }
        return Utilities.convert(this.environment, oldHandle);
    }

    public void returnLoan(Object sampleSeqHolder,
            SampleInfoSeqHolder infoSeqHolder) {
        try {
            int rc = (Integer) this.returnLoan.invoke(this.old,
                    sampleSeqHolder, infoSeqHolder);
            Utilities.checkReturnCode(rc, this.environment,
                    "Return loan failed.");
        } catch (IllegalAccessException e) {
            throw new DDSExceptionImpl(environment, "Internal error ("
                    + e.getMessage() + ").");
        } catch (InvocationTargetException e) {
            throw new DDSExceptionImpl(environment, "Internal error ("
                    + e.getMessage() + ").");
        }
    }

    public Iterator<OUT_TYPE> read() {
    	/* TODO FRCYC
        return this.read(LENGTH_UNLIMITED.value);
        */
    	return null;
    }

    @SuppressWarnings("unchecked")
    public Iterator<OUT_TYPE> read(
            org.omg.dds.sub.DataReader.Selector<OUT_TYPE> query) {
        SampleInfoSeqHolder info = new SampleInfoSeqHolder();
        Object sampleSeqHolder;

        if (query == null) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Invalid Selector (null) provided.");
        }
        ReadCondition<OUT_TYPE> condition = null;

        if (query.getQueryExpression() != null) {
            condition = query.getCondition();
        }
        InstanceHandle instance = query.getInstance();

        try {
            DataStateImpl state = (DataStateImpl) query.getDataState();
            InstanceHandleImpl handle = (InstanceHandleImpl) instance;
            sampleSeqHolder = this.sampleSeqHolderClz.newInstance();
            int rc;

            if (condition != null) {
                ReadCondition oldCondition = ((ReadConditionImpl<OUT_TYPE>) condition)
                        .getOld();

                if (query.retrieveNextInstance()) {
                    // read_next_instance_w_condition
                    rc = (Integer) this.readNextInstanceCondition.invoke(
                            this.old, sampleSeqHolder, info,
                            query.getMaxSamples(), handle.getValue(),
                            oldCondition);
                } else {
                    // read_w_condition
                    rc = (Integer) this.readCondition.invoke(this.old,
                            sampleSeqHolder, info, query.getMaxSamples(),
                            oldCondition);
                }
            } else {
                // read_next_instance
                if (query.retrieveNextInstance()) {
                    rc = (Integer) this.readNextInstance.invoke(this.old,
                            sampleSeqHolder, info, query.getMaxSamples(),
                            handle.getValue(), state.getOldSampleState(),
                            state.getOldViewState(),
                            state.getOldInstanceState());
                } else {
                    // read
                    if (instance.isNil()) {
                        rc = (Integer) this.read.invoke(this.old,
                                sampleSeqHolder, info, query.getMaxSamples(),
                                state.getOldSampleState(),
                                state.getOldViewState(),
                                state.getOldInstanceState());
                    }
                    // read_instance
                    else {
                        rc = (Integer) this.readInstance.invoke(this.old,
                                sampleSeqHolder, info, query.getMaxSamples(),
                                handle.getValue(), state.getOldSampleState(),
                                state.getOldViewState(),
                                state.getOldInstanceState());
                    }
                }
            }
            Utilities.checkReturnCode(rc, this.environment,
                    "DataReader.read() failed.");
        } catch (InstantiationException e) {
            throw new DDSExceptionImpl(this.environment, "Internal error ("
                    + e.getMessage() + ").");
        } catch (IllegalAccessException e) {
            throw new DDSExceptionImpl(this.environment, "Internal error ("
                    + e.getMessage() + ").");
        } catch (IllegalArgumentExceptionImpl e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw new DDSExceptionImpl(this.environment, "Internal error ("
                    + e.getMessage() + ").");
        } catch (InvocationTargetException e) {
            throw new DDSExceptionImpl(this.environment, "Internal error ("
                    + e.getMessage() + ").");
        } catch (ClassCastException e) {
            throw new IllegalArgumentExceptionImpl(
                    this.environment,
                    "Reading with non-OpenSplice DataState, InstanceHandle or ReadCondition not supported");
        }
        /* TODO FRCYC
        return (Iterator<OUT_TYPE>) this.reader.createIterator(
                sampleSeqHolder, this.sampleSeqHolderValueField, info);
                */
        return null;
    }

    @SuppressWarnings("unchecked")
    public Iterator<OUT_TYPE> read(int maxSamples) {
        SampleInfoSeqHolder info = new SampleInfoSeqHolder();
        Object sampleSeqHolder;

        /*
        try {
            sampleSeqHolder = this.sampleSeqHolderClz.newInstance();
             TODO FRCYC
            int rc = (Integer) this.read.invoke(this.old, sampleSeqHolder,
                    info, maxSamples, ANY_SAMPLE_STATE.value,
                    ANY_VIEW_STATE.value, ANY_INSTANCE_STATE.value);
            Utilities.checkReturnCode(rc, this.environment,
                    "DataReader.read() failed.");
                    
        } catch (InstantiationException e) {
            throw new DDSExceptionImpl(environment, "Internal error ("
                    + e.getMessage() + ").");
        } catch (IllegalAccessException e) {
            throw new DDSExceptionImpl(environment, "Internal error ("
                    + e.getMessage() + ").");
        } catch (IllegalArgumentExceptionImpl e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw new DDSExceptionImpl(environment, "Internal error ("
                    + e.getMessage() + ").");
        } catch (InvocationTargetException e) {
            throw new DDSExceptionImpl(environment, "Internal error ("
                    + e.getMessage() + ").");
        }
        /* TODO FRCYC
        return (Iterator<OUT_TYPE>) this.reader.createIterator(
                sampleSeqHolder, this.sampleSeqHolderValueField, info);
                */
        return null;
    }

    public Iterator<OUT_TYPE> take() {
    	//TODO FRCYC        return this.take(LENGTH_UNLIMITED.value);
    	return null;
    }

    @SuppressWarnings("unchecked")
    public Iterator<OUT_TYPE> take(int maxSamples) {
        SampleInfoSeqHolder info = new SampleInfoSeqHolder();
        Object sampleSeqHolder;
        /* 
        try {
        	TODO FRCYC
            sampleSeqHolder = this.sampleSeqHolderClz.newInstance();
            int rc = (Integer) this.take.invoke(this.old, sampleSeqHolder,
                    info, maxSamples, ANY_SAMPLE_STATE.value,
                    ANY_VIEW_STATE.value, ANY_INSTANCE_STATE.value);
            Utilities.checkReturnCode(rc, this.environment,
                    "DataReader.take() failed.");
                   
        } catch (InstantiationException e) {
            throw new DDSExceptionImpl(environment, "Internal error ("
                    + e.getMessage() + ").");
        } catch (IllegalAccessException e) {
            throw new DDSExceptionImpl(environment, "Internal error ("
                    + e.getMessage() + ").");
        } catch (IllegalArgumentExceptionImpl e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw new DDSExceptionImpl(environment, "Internal error ("
                    + e.getMessage() + ").");
        } catch (InvocationTargetException e) {
            throw new DDSExceptionImpl(environment, "Internal error ("
                    + e.getMessage() + ").");
        }
        
        return (Iterator<OUT_TYPE>) this.reader.createIterator(
                sampleSeqHolder, this.sampleSeqHolderValueField, info);
                */
        return null;
    }

    public Field getSampleSeqHolderValueField() {
        return this.sampleSeqHolderValueField;
    }

    @SuppressWarnings("unchecked")
    public Iterator<OUT_TYPE> take(
            org.omg.dds.sub.DataReader.Selector<OUT_TYPE> query) {
        SampleInfoSeqHolder info = new SampleInfoSeqHolder();
        Object sampleSeqHolder;

        if (query == null) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Invalid Selector (null) provided.");
        }
        ReadCondition<OUT_TYPE> condition = null;

        if (query.getQueryExpression() != null) {
            condition = query.getCondition();
        }
        InstanceHandle instance = query.getInstance();

        try {
            DataStateImpl state = (DataStateImpl) query.getDataState();
            InstanceHandleImpl handle = (InstanceHandleImpl) instance;
            sampleSeqHolder = this.sampleSeqHolderClz.newInstance();
            int rc;

            if (condition != null) {
                ReadCondition oldCondition = ((ReadConditionImpl<OUT_TYPE>) condition)
                        .getOld();

                if (query.retrieveNextInstance()) {
                    // take_next_instance_w_condition
                    rc = (Integer) this.takeNextInstanceCondition.invoke(
                            this.old, sampleSeqHolder, info,
                            query.getMaxSamples(), handle.getValue(),
                            oldCondition);
                } else {
                    // take_w_condition
                    rc = (Integer) this.takeCondition.invoke(this.old,
                            sampleSeqHolder, info, query.getMaxSamples(),
                            oldCondition);
                }
            } else {
                // take_next_instance
                if (query.retrieveNextInstance()) {
                    rc = (Integer) this.takeNextInstance.invoke(this.old,
                            sampleSeqHolder, info, query.getMaxSamples(),
                            handle.getValue(), state.getOldSampleState(),
                            state.getOldViewState(),
                            state.getOldInstanceState());
                } else {
                    // take
                    if (instance.isNil()) {
                        rc = (Integer) this.take.invoke(this.old,
                                sampleSeqHolder, info, query.getMaxSamples(),
                                state.getOldSampleState(),
                                state.getOldViewState(),
                                state.getOldInstanceState());
                    }
                    // take_instance
                    else {
                        rc = (Integer) this.takeInstance.invoke(this.old,
                                sampleSeqHolder, info, query.getMaxSamples(),
                                handle.getValue(), state.getOldSampleState(),
                                state.getOldViewState(),
                                state.getOldInstanceState());
                    }
                }
            }
            Utilities.checkReturnCode(rc, this.environment,
                    "DataReader.take() failed.");
        } catch (InstantiationException e) {
            throw new DDSExceptionImpl(this.environment, "Internal error ("
                    + e.getMessage() + ").");
        } catch (IllegalAccessException e) {
            throw new DDSExceptionImpl(this.environment, "Internal error ("
                    + e.getMessage() + ").");
        } catch (IllegalArgumentExceptionImpl e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw new DDSExceptionImpl(this.environment, "Internal error ("
                    + e.getMessage() + ").");
        } catch (InvocationTargetException e) {
            throw new DDSExceptionImpl(this.environment, "Internal error ("
                    + e.getMessage() + ").");
        } catch (ClassCastException e) {
            throw new IllegalArgumentExceptionImpl(
                    this.environment,
                    "Taking with non-OpenSplice DataState, InstanceHandle or ReadCondition not supported");
        }
        /* TODO FRCYC
        return (Iterator<OUT_TYPE>) this.reader.createIterator(
                sampleSeqHolder, this.sampleSeqHolderValueField, info);*/
        return null;
    }

    @SuppressWarnings("unchecked")
    public boolean readNextSample(SampleImpl<TYPE> sample) {
        SampleInfoHolder info;
        Object sampleHolder;
        boolean result;

        if (sample == null) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Provided an invalid null sample.");
        }
        try {
            info = new SampleInfoHolder();
            sampleHolder = this.sampleHolderClz.newInstance();

            this.sampleHolderValueField.set(sampleHolder, sample.getData());
            int rc = (Integer) this.readNextSample.invoke(this.old,
                    sampleHolder, info);
            Utilities.checkReturnCode(rc, this.environment,
                    "DataReader.readNextSample() failed.");

            /* TODO FRCYC
            if (rc == RETCODE_OK.value) {
                sample.setContent(
                        (TYPE) this.sampleHolderValueField.get(sampleHolder),
                        info.value);
                result = true;
            } else {
                result = false;
            }
            */

        } catch (InstantiationException e) {
            throw new DDSExceptionImpl(environment, "Internal error ("
                    + e.getMessage() + ").");
        } catch (IllegalAccessException e) {
            throw new DDSExceptionImpl(environment, "Internal error ("
                    + e.getMessage() + ").");
        } catch (IllegalArgumentExceptionImpl e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw new DDSExceptionImpl(environment, "Internal error ("
                    + e.getMessage() + ").");
        } catch (InvocationTargetException e) {
            throw new DDSExceptionImpl(environment, "Internal error ("
                    + e.getMessage() + ").");
        } catch (ClassCastException e) {
            throw new DDSExceptionImpl(environment, "Internal error ("
                    + e.getMessage() + ").");
        }
        // TODO FRCYC return result;
        return false;
    }

    @SuppressWarnings("unchecked")
    public boolean takeNextSample(SampleImpl<TYPE> sample) {
        SampleInfoHolder info;
        Object sampleHolder;
        boolean result;

        if (sample == null) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Provided an invalid null sample.");
        }
        try {
            info = new SampleInfoHolder();
            sampleHolder = this.sampleHolderClz.newInstance();
            this.sampleHolderValueField.set(sampleHolder, sample.getData());
            int rc = (Integer) this.takeNextSample.invoke(this.old,
                    sampleHolder, info);
            Utilities.checkReturnCode(rc, this.environment,
                    "DataReader.readNextSample() failed.");
/* TODO FRCYC
            if (rc == RETCODE_OK.value) {
                sample.setContent(
                        (TYPE) this.sampleHolderValueField.get(sampleHolder),
                        info.value);
                result = true;
            } else {
                result = false;
            }
            */

        } catch (InstantiationException e) {
            throw new DDSExceptionImpl(environment, "Internal error ("
                    + e.getMessage() + ").");
        } catch (IllegalAccessException e) {
            throw new DDSExceptionImpl(environment, "Internal error ("
                    + e.getMessage() + ").");
        } catch (IllegalArgumentExceptionImpl e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw new DDSExceptionImpl(environment, "Internal error ("
                    + e.getMessage() + ").");
        } catch (InvocationTargetException e) {
            throw new DDSExceptionImpl(environment, "Internal error ("
                    + e.getMessage() + ").");
        } catch (ClassCastException e) {
            throw new DDSExceptionImpl(environment, "Internal error ("
                    + e.getMessage() + ").");
        }
     // TODO FRCYC return result;
        return false;
    }

    public List<Sample<OUT_TYPE>> take(List<Sample<OUT_TYPE>> samples) {
        PreAllocator<OUT_TYPE> pa = this.reader.getPreAllocator(samples,
                this.sampleSeqHolderClz, this.sampleSeqHolderValueField);
        /* TODO FRCYC
        try {
        	
            int rc = (Integer) this.take.invoke(this.old,
                    pa.getDataSeqHolder(), pa.getInfoSeqHolder(),
                    LENGTH_UNLIMITED.value, ANY_SAMPLE_STATE.value,
                    ANY_VIEW_STATE.value, ANY_INSTANCE_STATE.value);
            Utilities.checkReturnCode(rc, this.environment,
                    "DataReader.read() failed.");
                    
        } catch (IllegalAccessException e) {
            throw new DDSExceptionImpl(environment, "Internal error ("
                    + e.getMessage() + ").");
        } catch (IllegalArgumentExceptionImpl e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw new DDSExceptionImpl(environment, "Internal error ("
                    + e.getMessage() + ").");
        } catch (InvocationTargetException e) {
            throw new DDSExceptionImpl(environment, "Internal error ("
                    + e.getMessage() + ").");
        }
        pa.updateReferences();
*/
        return pa.getSampleList();
    }

    public List<Sample<OUT_TYPE>> take(List<Sample<OUT_TYPE>> samples,
            org.omg.dds.sub.DataReader.Selector<OUT_TYPE> selector) {
        if (selector == null) {
            return this.take(samples);
        }

        PreAllocator<OUT_TYPE> pa = this.reader.getPreAllocator(samples,
                this.sampleSeqHolderClz, this.sampleSeqHolderValueField);
        ReadCondition<OUT_TYPE> condition = null;

        if (selector.getQueryExpression() != null) {
            condition = selector.getCondition();
        }
        InstanceHandle instance = selector.getInstance();

        try {
            DataStateImpl state = (DataStateImpl) selector.getDataState();
            InstanceHandleImpl handle = (InstanceHandleImpl) instance;
            int rc;

            if (condition != null) {
                ReadCondition oldCondition = ((ReadConditionImpl<OUT_TYPE>) condition)
                        .getOld();

                if (selector.retrieveNextInstance()) {
                    // take_next_instance_w_condition
                    rc = (Integer) this.takeNextInstanceCondition.invoke(
                            this.old, pa.getDataSeqHolder(),
                            pa.getInfoSeqHolder(), selector.getMaxSamples(),
                            handle.getValue(), oldCondition);
                } else {
                    // take_w_condition
                    rc = (Integer) this.takeCondition.invoke(this.old,
                            pa.getDataSeqHolder(), pa.getInfoSeqHolder(),
                            selector.getMaxSamples(), oldCondition);
                }
            } else {
                // take_next_instance
                if (selector.retrieveNextInstance()) {
                    rc = (Integer) this.takeNextInstance.invoke(this.old,
                            pa.getDataSeqHolder(), pa.getInfoSeqHolder(),
                            selector.getMaxSamples(), handle.getValue(),
                            state.getOldSampleState(), state.getOldViewState(),
                            state.getOldInstanceState());
                } else {
                    // take
                    if (instance.isNil()) {
                        rc = (Integer) this.take.invoke(this.old,
                                pa.getDataSeqHolder(), pa.getInfoSeqHolder(),
                                selector.getMaxSamples(),
                                state.getOldSampleState(),
                                state.getOldViewState(),
                                state.getOldInstanceState());
                    }
                    // take_instance
                    else {
                        rc = (Integer) this.takeInstance.invoke(this.old,
                                pa.getDataSeqHolder(), pa.getInfoSeqHolder(),
                                selector.getMaxSamples(), handle.getValue(),
                                state.getOldSampleState(),
                                state.getOldViewState(),
                                state.getOldInstanceState());
                    }
                }
            }
            Utilities.checkReturnCode(rc, this.environment,
                    "DataReader.take() failed.");
        } catch (IllegalAccessException e) {
            throw new DDSExceptionImpl(this.environment, "Internal error ("
                    + e.getMessage() + ").");
        } catch (IllegalArgumentExceptionImpl e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw new DDSExceptionImpl(this.environment, "Internal error ("
                    + e.getMessage() + ").");
        } catch (InvocationTargetException e) {
            throw new DDSExceptionImpl(this.environment, "Internal error ("
                    + e.getMessage() + ").");
        } catch (ClassCastException e) {
            throw new IllegalArgumentExceptionImpl(
                    this.environment,
                    "Reading with non-OpenSplice DataState, InstanceHandle or ReadCondition not supported");
        }
        pa.updateReferences();

        return pa.getSampleList();
    }

    public List<Sample<OUT_TYPE>> read(List<Sample<OUT_TYPE>> samples,
            org.omg.dds.sub.DataReader.Selector<OUT_TYPE> selector) {

        if (selector == null) {
            return this.read(samples);
        }

        PreAllocator<OUT_TYPE> pa = this.reader.getPreAllocator(samples,
                this.sampleSeqHolderClz, this.sampleSeqHolderValueField);

        ReadCondition<OUT_TYPE> condition = null;

        if (selector.getQueryExpression() != null) {
            condition = selector.getCondition();
        }
        InstanceHandle instance = selector.getInstance();

        try {
            DataStateImpl state = (DataStateImpl) selector.getDataState();
            InstanceHandleImpl handle = (InstanceHandleImpl) instance;
            int rc;

            if (condition != null) {
                ReadCondition oldCondition = ((ReadConditionImpl<OUT_TYPE>) condition)
                        .getOld();

                if (selector.retrieveNextInstance()) {
                    // read_next_instance_w_condition
                    rc = (Integer) this.readNextInstanceCondition.invoke(
                            this.old, pa.getDataSeqHolder(),
                            pa.getInfoSeqHolder(), selector.getMaxSamples(),
                            handle.getValue(), oldCondition);
                } else {
                    // read_w_condition
                    rc = (Integer) this.readCondition.invoke(this.old,
                            pa.getDataSeqHolder(), pa.getInfoSeqHolder(),
                            selector.getMaxSamples(), oldCondition);
                }
            } else {
                // read_next_instance
                if (selector.retrieveNextInstance()) {
                    rc = (Integer) this.readNextInstance.invoke(this.old,
                            pa.getDataSeqHolder(), pa.getInfoSeqHolder(),
                            selector.getMaxSamples(), handle.getValue(),
                            state.getOldSampleState(), state.getOldViewState(),
                            state.getOldInstanceState());
                } else {
                    // read
                    if (instance.isNil()) {
                        rc = (Integer) this.read.invoke(this.old,
                                pa.getDataSeqHolder(), pa.getInfoSeqHolder(),
                                selector.getMaxSamples(),
                                state.getOldSampleState(),
                                state.getOldViewState(),
                                state.getOldInstanceState());
                    }
                    // read_instance
                    else {
                        rc = (Integer) this.readInstance.invoke(this.old,
                                pa.getDataSeqHolder(), pa.getInfoSeqHolder(),
                                selector.getMaxSamples(), handle.getValue(),
                                state.getOldSampleState(),
                                state.getOldViewState(),
                                state.getOldInstanceState());
                    }
                }
            }
            Utilities.checkReturnCode(rc, this.environment,
                    "DataReader.read() failed.");
        } catch (IllegalAccessException e) {
            throw new DDSExceptionImpl(this.environment, "Internal error ("
                    + e.getMessage() + ").");
        } catch (IllegalArgumentExceptionImpl e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw new DDSExceptionImpl(this.environment, "Internal error ("
                    + e.getMessage() + ").");
        } catch (InvocationTargetException e) {
            throw new DDSExceptionImpl(this.environment, "Internal error ("
                    + e.getMessage() + ").");
        } catch (ClassCastException e) {
            throw new IllegalArgumentExceptionImpl(
                    this.environment,
                    "Reading with non-OpenSplice DataState, InstanceHandle or ReadCondition not supported");
        }
        pa.updateReferences();

        return pa.getSampleList();
    }

    public List<Sample<OUT_TYPE>> read(List<Sample<OUT_TYPE>> samples) {
        PreAllocator<OUT_TYPE> pa = this.reader.getPreAllocator(samples,
                this.sampleSeqHolderClz, this.sampleSeqHolderValueField);

        /* TODO FRCYC
        try {
            int rc = (Integer) this.read.invoke(this.old,
                    pa.getDataSeqHolder(), pa.getInfoSeqHolder(),
                    LENGTH_UNLIMITED.value, ANY_SAMPLE_STATE.value,
                    ANY_VIEW_STATE.value, ANY_INSTANCE_STATE.value);
            Utilities.checkReturnCode(rc, this.environment,
                    "DataReader.read() failed.");
        } catch (IllegalAccessException e) {
            throw new DDSExceptionImpl(environment, "Internal error ("
                    + e.getMessage() + ").");
        } catch (IllegalArgumentExceptionImpl e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw new DDSExceptionImpl(environment, "Internal error ("
                    + e.getMessage() + ").");
        } catch (InvocationTargetException e) {
            throw new DDSExceptionImpl(environment, "Internal error ("
                    + e.getMessage() + ").");
        }
        pa.updateReferences();
         */
        return pa.getSampleList();
    }
}
