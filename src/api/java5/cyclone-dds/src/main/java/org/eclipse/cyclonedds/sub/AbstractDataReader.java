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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.omg.dds.core.AlreadyClosedException;
import org.omg.dds.core.Condition;
import org.omg.dds.core.Duration;
import org.omg.dds.core.InstanceHandle;
import org.omg.dds.core.StatusCondition;
import org.omg.dds.core.Time;
import org.omg.dds.core.policy.ResourceLimits;
import org.omg.dds.core.status.LivelinessChangedStatus;
import org.omg.dds.core.status.RequestedDeadlineMissedStatus;
import org.omg.dds.core.status.RequestedIncompatibleQosStatus;
import org.omg.dds.core.status.SampleLostStatus;
import org.omg.dds.core.status.SampleRejectedStatus;
import org.omg.dds.core.status.Status;
import org.omg.dds.core.status.SubscriptionMatchedStatus;
import org.omg.dds.sub.DataReader;
import org.omg.dds.sub.DataReaderListener;
import org.omg.dds.sub.DataReaderQos;
import org.omg.dds.sub.QueryCondition;
import org.omg.dds.sub.ReadCondition;
import org.omg.dds.sub.Sample;
import org.omg.dds.sub.Sample.Iterator;
import org.omg.dds.sub.Subscriber;
import org.omg.dds.sub.Subscriber.DataState;
import org.omg.dds.topic.PublicationBuiltinTopicData;
import org.omg.dds.topic.TopicDescription;

import org.eclipse.cyclonedds.core.DomainEntityImpl;
import org.eclipse.cyclonedds.core.IllegalArgumentExceptionImpl;
import org.eclipse.cyclonedds.core.IllegalOperationExceptionImpl;
import org.eclipse.cyclonedds.core.UserClass;
import org.eclipse.cyclonedds.core.AlreadyClosedExceptionImpl;
import org.eclipse.cyclonedds.core.CycloneServiceEnvironment;
import org.eclipse.cyclonedds.core.StatusConditionImpl;
import org.eclipse.cyclonedds.core.UnsupportedOperationExceptionImpl;
import org.eclipse.cyclonedds.core.Utilities;
import org.eclipse.cyclonedds.core.policy.ResourceLimitsImpl;
import org.eclipse.cyclonedds.core.status.StatusConverter;
import org.eclipse.cyclonedds.ddsc.dds.DdscLibrary;
import org.eclipse.cyclonedds.helper.NativeSize;
import org.eclipse.cyclonedds.topic.TopicDescriptionExt;

public abstract class AbstractDataReader<TYPE>
        extends
        DomainEntityImpl<DataReaderQos, DataReaderListener<TYPE>, DataReaderListenerImpl<TYPE>>
        implements org.eclipse.cyclonedds.sub.DataReader<TYPE> {

    protected final HashMap<Condition, ReadConditionImpl<TYPE>> conditions;
    protected final HashSet<AbstractIterator<TYPE>> iterators;
    protected final Selector<TYPE> selector;
	protected boolean closed = false;


    public AbstractDataReader(CycloneServiceEnvironment environment,
            SubscriberImpl parent) {
        super(environment);

        this.conditions = new HashMap<Condition, ReadConditionImpl<TYPE>>();
        this.iterators = new HashSet<AbstractIterator<TYPE>>();
        this.selector = new SelectorImpl<TYPE>(environment, this);
    }

    public void registerIterator(AbstractIterator<TYPE> iterator) {
        synchronized (this.iterators) {
            this.iterators.add(iterator);
        }
    }

    public void deregisterIterator(AbstractIterator<TYPE> iterator) {
        synchronized (this.iterators) {
            this.iterators.remove(iterator);
        }
    }

    protected abstract ReflectionDataReader<?, TYPE> getReflectionReader();

    @Override
    public ReadCondition<TYPE> createReadCondition(DataState states) {
        ReadConditionImpl<TYPE> condition;

        synchronized (this.conditions) {
            try {
                condition = new ReadConditionImpl<TYPE>(this.environment, this,
                        (DataStateImpl) states);
            } catch (ClassCastException e) {
                throw new IllegalArgumentExceptionImpl(this.environment,
                        "Non-OpenSplice DataState not supported.");
            }
            this.conditions.put(condition.getOld(), condition);
        }
        return condition;
    }

    @Override
    public QueryCondition<TYPE> createQueryCondition(String queryExpression,
            List<String> queryParameters) {
        return this.createQueryCondition(
                DataStateImpl.getAnyStateDataState(this.environment),
                queryExpression, queryParameters);
    }

    @Override
    public QueryCondition<TYPE> createQueryCondition(DataState states,
            String queryExpression, List<String> queryParameters) {
        QueryConditionImpl<TYPE> query;

        try {
            synchronized (this.conditions) {
                query = new QueryConditionImpl<TYPE>(this.environment, this,
                        (DataStateImpl) states, queryExpression,
                        queryParameters);

                this.conditions.put(query.getOld(), query);
            }
        } catch (ClassCastException e) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Non-OpenSplice DataState not supported.");
        }

        return query;
    }

    @Override
    public QueryCondition<TYPE> createQueryCondition(String queryExpression,
            String... queryParameters) {
        return createQueryCondition(queryExpression,
                Arrays.asList(queryParameters));
    }

    @Override
    public QueryCondition<TYPE> createQueryCondition(DataState states,
            String queryExpression, String... queryParameters) {
        QueryCondition<TYPE> result;

        if (queryParameters == null) {
            result = createQueryCondition(states, queryExpression,
                    new ArrayList<String>());
        } else {
            result = createQueryCondition(states, queryExpression,
                    Arrays.asList(queryParameters));
        }
        return result;
    }

    public void destroyReadCondition(ReadConditionImpl<TYPE> condition) {
        synchronized (this.conditions) {
            ReadCondition old = condition.getOld();
            /* TODO FRCYC
            int rc = this.getOld().delete_readcondition(old);
            this.conditions.remove(old);
            Utilities.checkReturnCode(rc, this.environment,
                    "Condition already closed.");
                    */
        }

    }

    @SuppressWarnings("unchecked")
    @Override
    public void closeContainedEntities() {
        synchronized (this.conditions) {
            HashMap<Condition, ReadConditionImpl<TYPE>> copyConditions = new HashMap<Condition, ReadConditionImpl<TYPE>>(this.conditions);
            for (ReadConditionImpl<TYPE> condition : copyConditions.values()) {
                /*
                 * Intentionally ignoring potential errors during deletion as
                 * application may concurrently close conditions.
                 */
                // TODO FRCYC this.getOld().delete_readcondition(condition.getOld());
            }
        }
        HashSet<AbstractIterator<TYPE>> clones;

        synchronized (this.iterators) {
            clones = (HashSet<AbstractIterator<TYPE>>) this.iterators.clone();
        }
        for (AbstractIterator<TYPE> iterator : clones) {
            try {
                iterator.close();
            } catch (AlreadyClosedException a) {
                /* Entity may be closed concurrently by application */
            }
        }
    }

    @Override
    protected void destroy() {
        this.closeContainedEntities();
        //TODO FRCYC this.parent.destroyDataReader(this);
    }

    @Override
    public SubscriberImpl getParent() {
        return null; //TODO FRCYC this.parent;
    }

    private void setListener(DataReaderListener<TYPE> listener, int mask) {
        DataReaderListenerImpl<TYPE> wrapperListener;
        int rc;

        if (listener != null) {
            wrapperListener = new DataReaderListenerImpl<TYPE>(
                    this.environment, this, listener);
        } else {
            wrapperListener = null;
        }
        /* TODO FRCYC
        rc = this.getOld().set_listener(wrapperListener, mask);
        Utilities.checkReturnCode(rc, this.environment,
                "DataReader.setListener() failed.");

        this.listener = wrapperListener;
        */
    }

    @Override
    public void setListener(DataReaderListener<TYPE> listener) {
        //TODO FRCYC this.setListener(listener, StatusConverter.getAnyMask());
    }

    @Override
    public void setListener(DataReaderListener<TYPE> listener,
            Collection<Class<? extends Status>> statuses) {
        /* TODO FRCYC
    	this.setListener(listener,
                StatusConverter.convertMask(this.environment, statuses));
                */
    }

    @Override
    public void setListener(DataReaderListener<TYPE> listener,
            Class<? extends Status>... statuses) {
        /* TODO FRCYC
    	this.setListener(listener,
                StatusConverter.convertMask(this.environment, statuses)); */
    }


    /*
    @Override
    public void setProperty(String key, String value) {
         TODO FRCYC
    	int rc = this.getOld().set_property(new Property(key, value));
        Utilities.checkReturnCode(rc, this.environment,
                "DataReader.setProperty() failed.");
                
    }

    @Override
    public String getProperty(String key) {
    	 TODO FRCYC
        PropertyHolder holder = new PropertyHolder();
        int rc = this.getOld().get_property(holder);
        Utilities.checkReturnCode(rc, this.environment,
                "DataReader.getProperty() failed.");
        return holder.value.value;
    	return null;
    }
    */

    @SuppressWarnings("unchecked")
    @Override
    public <OTHER> DataReader<OTHER> cast() {
        DataReader<OTHER> other;
        try {
            other = (DataReader<OTHER>) this;
        } catch (ClassCastException cce) {
            throw new IllegalOperationExceptionImpl(this.environment,
                    "Unable to perform requested cast.");
        }
        return other;
    }

    @Override
    public DataReaderQos getQos() {
        return this.getReflectionReader().getQos();
    }

    @Override
    public void setQos(DataReaderQos qos) {
        this.getReflectionReader().setQos(qos);
    }

    @Override
    public SampleRejectedStatus getSampleRejectedStatus() {
        return this.getReflectionReader().getSampleRejectedStatus();
    }

    @Override
    public LivelinessChangedStatus getLivelinessChangedStatus() {
        return this.getReflectionReader().getLivelinessChangedStatus();
    }

    @Override
    public RequestedDeadlineMissedStatus getRequestedDeadlineMissedStatus() {
        return this.getReflectionReader().getRequestedDeadlineMissedStatus();
    }

    @Override
    public RequestedIncompatibleQosStatus getRequestedIncompatibleQosStatus() {
        return this.getReflectionReader().getRequestedIncompatibleQosStatus();
    }

    @Override
    public SubscriptionMatchedStatus getSubscriptionMatchedStatus() {
        return this.getReflectionReader().getSubscriptionMatchedStatus();
    }

    @Override
    public SampleLostStatus getSampleLostStatus() {
        return this.getReflectionReader().getSampleLostStatus();
    }

    @Override
    public void waitForHistoricalData(Duration maxWait) throws TimeoutException {
        this.getReflectionReader().waitForHistoricalData(maxWait);
    }

    @Override
    public void waitForHistoricalData(long maxWait, TimeUnit unit)
            throws TimeoutException {
        this.getReflectionReader().waitForHistoricalData(maxWait, unit);
    }

    @Override
    public void waitForHistoricalData(String filterExpression,
            List<String> filterParameters, Time minSourceTimestamp,
            Time maxSourceTimestamp, ResourceLimits resourceLimits,
            Duration maxWait) throws TimeoutException {
        this.getReflectionReader().waitForHistoricalData(filterExpression,
                filterParameters, minSourceTimestamp, maxSourceTimestamp,
                resourceLimits, maxWait);
    }

    @Override
    public void waitForHistoricalData(String filterExpression,
            List<String> filterParameters, Time minSourceTimestamp,
            Time maxSourceTimestamp, Duration maxWait) throws TimeoutException {
        this.getReflectionReader().waitForHistoricalData(filterExpression,
                filterParameters, minSourceTimestamp, maxSourceTimestamp,
                new ResourceLimitsImpl(this.environment), maxWait);
    }

    @Override
    public void waitForHistoricalData(String filterExpression,
            List<String> filterParameters, ResourceLimits resourceLimits,
            Duration maxWait) throws TimeoutException {
        this.getReflectionReader().waitForHistoricalData(filterExpression,
                filterParameters, Time.invalidTime(this.environment),
                Time.invalidTime(this.environment), resourceLimits, maxWait);
    }

    @Override
    public void waitForHistoricalData(String filterExpression,
            List<String> filterParameters, Duration maxWait)
            throws TimeoutException {
        this.getReflectionReader().waitForHistoricalData(filterExpression,
                filterParameters, Time.invalidTime(this.environment),
                Time.invalidTime(this.environment),
                new ResourceLimitsImpl(this.environment), maxWait);
    }

    @Override
    public void waitForHistoricalData(Time minSourceTimestamp,
            Time maxSourceTimestamp, ResourceLimits resourceLimits,
            Duration maxWait) throws TimeoutException {
        this.getReflectionReader()
                .waitForHistoricalData(null, null, minSourceTimestamp,
                        maxSourceTimestamp, resourceLimits, maxWait);

    }

    @Override
    public void waitForHistoricalData(Time minSourceTimestamp,
            Time maxSourceTimestamp, Duration maxWait) throws TimeoutException {
        this.getReflectionReader().waitForHistoricalData(null, null,
                minSourceTimestamp, maxSourceTimestamp,
                new ResourceLimitsImpl(this.environment), maxWait);

    }

    @Override
    public void waitForHistoricalData(ResourceLimits resourceLimits,
            Duration maxWait) throws TimeoutException {
        this.getReflectionReader().waitForHistoricalData(null, null,
                Time.invalidTime(this.environment),
                Time.invalidTime(this.environment), resourceLimits, maxWait);

    }

    @Override
    public void waitForHistoricalData(String filterExpression,
            List<String> filterParameters, Time minSourceTimestamp,
            Time maxSourceTimestamp, ResourceLimits resourceLimits,
            long maxWait, TimeUnit unit) throws TimeoutException {
        this.getReflectionReader().waitForHistoricalData(filterExpression,
                filterParameters, minSourceTimestamp, maxSourceTimestamp,
                resourceLimits, maxWait, unit);
    }

    @Override
    public void waitForHistoricalData(String filterExpression,
            List<String> filterParameters, Time minSourceTimestamp,
            Time maxSourceTimestamp, long maxWait, TimeUnit unit)
            throws TimeoutException {
        this.getReflectionReader().waitForHistoricalData(filterExpression,
                filterParameters, minSourceTimestamp, maxSourceTimestamp,
                new ResourceLimitsImpl(this.environment), maxWait, unit);
    }

    @Override
    public void waitForHistoricalData(String filterExpression,
            List<String> filterParameters, ResourceLimits resourceLimits,
            long maxWait, TimeUnit unit) throws TimeoutException {
        this.getReflectionReader().waitForHistoricalData(filterExpression,
                filterParameters, Time.invalidTime(this.environment),
                Time.invalidTime(this.environment), resourceLimits, maxWait,
                unit);
    }

    @Override
    public void waitForHistoricalData(String filterExpression,
            List<String> filterParameters, long maxWait, TimeUnit unit)
            throws TimeoutException {
        this.getReflectionReader().waitForHistoricalData(filterExpression,
                filterParameters, Time.invalidTime(this.environment),
                Time.invalidTime(this.environment),
                new ResourceLimitsImpl(this.environment), maxWait, unit);
    }

    @Override
    public void waitForHistoricalData(Time minSourceTimestamp,
            Time maxSourceTimestamp, ResourceLimits resourceLimits,
            long maxWait, TimeUnit unit) throws TimeoutException {
        this.getReflectionReader().waitForHistoricalData(null, null,
                minSourceTimestamp, maxSourceTimestamp, resourceLimits,
                maxWait, unit);
    }

    @Override
    public void waitForHistoricalData(Time minSourceTimestamp,
            Time maxSourceTimestamp, long maxWait, TimeUnit unit)
            throws TimeoutException {
        this.getReflectionReader().waitForHistoricalData(null, null,
                minSourceTimestamp, maxSourceTimestamp,
                new ResourceLimitsImpl(this.environment), maxWait, unit);
    }

    @Override
    public void waitForHistoricalData(ResourceLimits resourceLimits,
            long maxWait, TimeUnit unit) throws TimeoutException {
        this.getReflectionReader().waitForHistoricalData(null, null,
                Time.invalidTime(this.environment),
                Time.invalidTime(this.environment), resourceLimits, maxWait,
                unit);
    }

    @Override
    public Set<InstanceHandle> getMatchedPublications() {
        return this.getReflectionReader().getMatchedPublications();
    }

    @Override
    public PublicationBuiltinTopicData getMatchedPublicationData(
            InstanceHandle publicationHandle) {
        return this.getReflectionReader()
                .getMatchedPublicationData(publicationHandle);
    }

    @Override
    public org.omg.dds.sub.DataReader.Selector<TYPE> select() {
        if(closed) {
        	throw new AlreadyClosedExceptionImpl(environment, "DataReader is closed; can't select");
        }
        return this.selector;
    }

    /* TODO FRCYC
    public void returnLoan(Object sampleSeqHolder,
            SampleInfoSeqHolder infoSeqHolder) {
        this.getReflectionReader().returnLoan(sampleSeqHolder, infoSeqHolder);
    }
    */

    @Override
    public Iterator<TYPE> read() {
        return this.getReflectionReader().read();
    }

    @Override
    public Iterator<TYPE> read(org.omg.dds.sub.DataReader.Selector<TYPE> query) {
        return this.getReflectionReader().read(query);
    }

    @Override
    public Iterator<TYPE> read(int maxSamples) {
        return this.getReflectionReader().read(maxSamples);
    }

    @Override
    public List<Sample<TYPE>> read(List<Sample<TYPE>> samples) {
    	return read(samples, select());
    }

    @Override
    public List<Sample<TYPE>> read(List<Sample<TYPE>> samples,
            org.omg.dds.sub.DataReader.Selector<TYPE> selector) {
    	return read(samples, selector, true);
    }
    
    public List<Sample<TYPE>> read(List<Sample<TYPE>> samples,
    		DataReader.Selector<TYPE> selector, boolean changeState)
    {
    	throw new UnsupportedOperationExceptionImpl(environment, "NYI");
    }

    @Override
    public Iterator<TYPE> take() {
        return this.getReflectionReader().take();
    }

    @Override
    public Iterator<TYPE> take(int maxSamples) {
        return this.getReflectionReader().take(maxSamples);
    }

    @Override
    public Iterator<TYPE> take(org.omg.dds.sub.DataReader.Selector<TYPE> query) {
        return this.getReflectionReader().take(query);
    }

    @Override
    public List<Sample<TYPE>> take(List<Sample<TYPE>> samples) {
        return this.getReflectionReader().take(samples);
    }

    @Override
    public List<Sample<TYPE>> take(List<Sample<TYPE>> samples,
            org.omg.dds.sub.DataReader.Selector<TYPE> selector) {
        return this.getReflectionReader().take(samples, selector);
    }

    @Override
    public StatusCondition<DataReader<TYPE>> getStatusCondition() {
        /* TODO FRCYC
         StatusCondition oldCondition = this.getOld().get_statuscondition();
         

        if (oldCondition == null) {
            Utilities.throwLastErrorException(this.environment);
        }
        return new StatusConditionImpl<DataReader<TYPE>>(this.environment,
                oldCondition, this);
                */
    	return null;
    }

    public abstract PreAllocator<TYPE> getPreAllocator(
            List<Sample<TYPE>> samples, Class<?> sampleSeqHolderClz,
            Field sampleSeqHolderValueField);
    /* TODO FRCYC
    public abstract Sample.Iterator<?> createIterator(
            Object sampleSeqHolder,
            Field sampleSeqHolderValueField, SampleInfoSeqHolder info);
            */
}
