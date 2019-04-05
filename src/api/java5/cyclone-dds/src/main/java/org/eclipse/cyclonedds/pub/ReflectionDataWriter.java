/**
  Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 
  This program and the accompanying materials are made available under the
  terms of the Eclipse Public License v. 2.0 which is available at
  http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
  v. 1.0 which is available at
  http://www.eclipse.org/org/documents/edl-v10.php.
 
  SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.cyclonedds.pub;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.eclipse.cyclonedds.core.AbstractDDSObject;
import org.omg.dds.core.DDSObject;
import org.omg.dds.core.Duration;
import org.omg.dds.core.InstanceHandle;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.Time;
import org.omg.dds.core.status.LivelinessLostStatus;
import org.omg.dds.core.status.OfferedDeadlineMissedStatus;
import org.omg.dds.core.status.OfferedIncompatibleQosStatus;
import org.omg.dds.core.status.PublicationMatchedStatus;
import org.omg.dds.pub.DataWriterQos;
import org.omg.dds.topic.SubscriptionBuiltinTopicData;
import org.eclipse.cyclonedds.core.DDSExceptionImpl;
import org.eclipse.cyclonedds.core.IllegalArgumentExceptionImpl;
import org.eclipse.cyclonedds.core.CycloneServiceEnvironment;
import org.eclipse.cyclonedds.core.PreconditionNotMetExceptionImpl;
import org.eclipse.cyclonedds.core.TimeImpl;
import org.eclipse.cyclonedds.core.Utilities;
import org.eclipse.cyclonedds.core.status.StatusConverter;
import org.eclipse.cyclonedds.topic.SubscriptionBuiltinTopicDataImpl;

public class ReflectionDataWriter<TYPE> extends AbstractDDSObject implements
        DDSObject {
    private final CycloneServiceEnvironment environment;
    private final DataWriter old = null;
    private final Method registerInstance = null;
    private final Method registerInstanceTimestamp  = null;;
    private final Method unregisterInstance = null;
    private final Method unregisterInstanceTimestamp = null;
    private final Method write = null;
    private final Method writeTimestamp = null;
    private final Method dispose = null;
    private final Method disposeTimestamp = null;
    private final Method writeDispose = null;
    private final Method writeDisposeTimestamp = null;
    private final Method getKeyValue = null;
    private final Method lookupInstance = null;
    private final Class<?> sampleHolderClz = null;
    private final Field samplHolderValueField = null;

    public ReflectionDataWriter(CycloneServiceEnvironment environment,
            DataWriter writer, Class<TYPE> typeClz) {
        Class<?> typedWriterClz;
        String typedWriterClzName;

        //TODO FRCYC this.old = writer;
        this.environment = environment;

        typedWriterClzName = typeClz.getName() + "DataWriterImpl";

        /* TODO FRCYC
        try {
            typedWriterClz = Class.forName(typedWriterClzName);

            this.sampleHolderClz = Class.forName(typeClz.getName() + "Holder");
            this.samplHolderValueField = this.sampleHolderClz
                    .getDeclaredField("value");

            this.registerInstance = typedWriterClz.getMethod(
                    "register_instance", typeClz);
            this.registerInstanceTimestamp = typedWriterClz.getMethod(
                    "register_instance_w_timestamp", typeClz, Time_t.class);
            this.unregisterInstance = typedWriterClz.getMethod(
                    "unregister_instance", typeClz, long.class);
            this.unregisterInstanceTimestamp = typedWriterClz.getMethod(
                    "unregister_instance_w_timestamp", typeClz, long.class,
                    Time_t.class);
            this.write = typedWriterClz.getMethod("write", typeClz, long.class);
            this.writeTimestamp = typedWriterClz.getMethod("write_w_timestamp",
                    typeClz, long.class, Time_t.class);
            this.dispose = typedWriterClz.getMethod("dispose", typeClz,
                    long.class);
            this.disposeTimestamp = typedWriterClz.getMethod(
                    "dispose_w_timestamp", typeClz, long.class,
                    Time_t.class);
            this.writeDispose = typedWriterClz.getMethod("writedispose",
                    typeClz, long.class);
            this.writeDisposeTimestamp = typedWriterClz.getMethod(
                    "writedispose_w_timestamp", typeClz, long.class,
                    Time_t.class);
            this.getKeyValue = typedWriterClz.getMethod("get_key_value",
                    this.sampleHolderClz, long.class);
            this.lookupInstance = typedWriterClz.getMethod("lookup_instance",
                    typeClz);

        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentExceptionImpl(
                    this.environment,
                    "Cannot find Typed DataWriter '"
                            + typedWriterClzName
                            + "' that should be generated with OpenSplice idlpp");
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentExceptionImpl(
                    this.environment,
                    "Cannot find correct methods in '"
                            + typedWriterClzName
                            + "' that should be generated with OpenSplice idlpp ( "
                            + e.getMessage() + ").");
        } catch (NoSuchFieldException e) {
            throw new IllegalArgumentExceptionImpl(
                    this.environment,
                    "Cannot find 'value' field in "
                            + "the typed sampleHolderClass "
                            + "that should be generated with OpenSplice idlpp ( "
                            + e.getMessage() + ").");
        } catch (SecurityException e) {
            throw new IllegalArgumentExceptionImpl(
                    this.environment,
                    "Cannot find 'value' field in "
                            + "the typed sampleHolderClass "
                            + "that should be generated with OpenSplice idlpp ( "
                            + e.getMessage() + ").");
        }
        */
    }

    @Override
    public ServiceEnvironment getEnvironment() {
        return this.environment;
    }

    public InstanceHandle registerInstance(TYPE instanceData)
            throws TimeoutException {
        long handle;

        if (instanceData == null) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Illegal instanceData (null) provided.");
        }
        try {
            handle = (Long) this.registerInstance
                    .invoke(this.old, instanceData);
        } catch (IllegalAccessException e) {
            throw new DDSExceptionImpl(this.environment,
                    "DataWriter.registerInstance() failed (" + e.getMessage()
                            + ").");
        } catch (IllegalArgumentExceptionImpl e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw new DDSExceptionImpl(this.environment,
                    "DataWriter.registerInstance() failed (" + e.getMessage()
                            + ").");
        } catch (InvocationTargetException e) {
            throw new DDSExceptionImpl(this.environment,
                    "DataWriter.registerInstance() failed (" + e.getMessage()
                            + ").");
        }
        return Utilities.convert(this.environment, handle);
    }

    /* TODO FRCYC
    public InstanceHandle registerInstance(TYPE instanceData,
            Time sourceTimestamp) throws TimeoutException {
        long handle;

        if (instanceData == null) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Illegal instanceData (null) provided.");
        }
        
        try {
            handle = (Long) this.registerInstanceTimestamp.invoke(this.old,
                    instanceData,
                    //TODO FRCYC Utilities.convert(this.environment, sourceTimestamp));
        } catch (IllegalAccessException e) {
            throw new DDSExceptionImpl(this.environment,
                    "DataWriter.registerInstance() failed (" + e.getMessage()
                            + ").");
        } catch (IllegalArgumentExceptionImpl e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw new DDSExceptionImpl(this.environment,
                    "DataWriter.registerInstance() failed (" + e.getMessage()
                            + ").");
        } catch (InvocationTargetException e) {
            throw new DDSExceptionImpl(this.environment,
                    "DataWriter.registerInstance() failed (" + e.getMessage()
                            + ").");
        }
        return Utilities.convert(this.environment, handle);
    }
    

    public InstanceHandle registerInstance(TYPE instanceData,
            long sourceTimestamp, TimeUnit unit) throws TimeoutException {
        return this.registerInstance(instanceData, new TimeImpl(
                this.environment, sourceTimestamp, unit));
    }
    */

    public void unregisterInstance(InstanceHandle handle)
            throws TimeoutException {
        this.unregisterInstance(handle, null);
    }

    public void unregisterInstance(InstanceHandle handle, TYPE instanceData)
            throws TimeoutException {
        try {
            int rc = (Integer) this.unregisterInstance.invoke(this.old,
                    instanceData, Utilities.convert(this.environment, handle));
            Utilities.checkReturnCodeWithTimeout(rc, this.environment,
                    "Datawriter.unregisterInstance() failed.");
        } catch (IllegalAccessException e) {
            throw new DDSExceptionImpl(this.environment,
                    "DataWriter.unregisterInstance() failed (" + e.getMessage()
                            + ").");
        } catch (IllegalArgumentExceptionImpl e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw new DDSExceptionImpl(this.environment,
                    "DataWriter.unregisterInstance() failed (" + e.getMessage()
                            + ").");
        } catch (InvocationTargetException e) {
            throw new DDSExceptionImpl(this.environment,
                    "DataWriter.unregisterInstance() failed (" + e.getMessage()
                            + ").");
        }
    }

    /* TODO FRCYC
    public void unregisterInstance(InstanceHandle handle, TYPE instanceData,
            Time sourceTimestamp) throws TimeoutException {
        try {
            int rc = (Integer) this.unregisterInstanceTimestamp.invoke(
                    this.old, instanceData,
                    Utilities.convert(this.environment, handle),
                    Utilities.convert(this.environment, sourceTimestamp));
            Utilities.checkReturnCodeWithTimeout(rc, this.environment,
                    "Datawriter.unregisterInstance() failed.");
        } catch (IllegalAccessException e) {
            throw new DDSExceptionImpl(this.environment,
                    "DataWriter.unregisterInstance() failed (" + e.getMessage()
                            + ").");
        } catch (IllegalArgumentExceptionImpl e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw new DDSExceptionImpl(this.environment,
                    "DataWriter.unregisterInstance() failed (" + e.getMessage()
                            + ").");
        } catch (InvocationTargetException e) {
            throw new DDSExceptionImpl(this.environment,
                    "DataWriter.unregisterInstance() failed (" + e.getMessage()
                            + ").");
        }
    }
    
    
    public void unregisterInstance(InstanceHandle handle, TYPE instanceData,
            long sourceTimestamp, TimeUnit unit) throws TimeoutException {
        this.unregisterInstance(handle, instanceData, new TimeImpl(
                this.environment, sourceTimestamp, unit));

    }*/

    public void write(TYPE instanceData) throws TimeoutException {
        this.write(instanceData, this.environment.getSPI().nilHandle());
    }

    public void write(TYPE instanceData, Time sourceTimestamp)
            throws TimeoutException {
    	/* TODO FRCYC
        this.write(instanceData, this.environment.getSPI().nilHandle(),
                sourceTimestamp);
                */
    }

    public void write(TYPE instanceData, long sourceTimestamp, TimeUnit unit)
            throws TimeoutException {
        this.write(instanceData, new TimeImpl(this.environment,
                sourceTimestamp, unit));

    }

    public void write(TYPE instanceData, InstanceHandle handle)
            throws TimeoutException {
        try {
            int rc = (Integer) this.write.invoke(this.old, instanceData,
                    Utilities.convert(this.environment, handle));
            Utilities.checkReturnCodeWithTimeout(rc, this.environment,
                    "DataWriter.write() failed.");
        } catch (IllegalAccessException e) {
            throw new DDSExceptionImpl(this.environment,
                    "DataWriter.write() failed (" + e.getMessage() + ").");
        } catch (IllegalArgumentExceptionImpl e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw new DDSExceptionImpl(this.environment,
                    "DataWriter.write() failed (" + e.getMessage() + ").");
        } catch (InvocationTargetException e) {
            throw new DDSExceptionImpl(this.environment,
                    "DataWriter.write() failed (" + e.getMessage() + ").");
        }
    }

    /* TODO FRCYC
    public void write(TYPE instanceData, InstanceHandle handle,
            Time sourceTimestamp) throws TimeoutException {
        try {
            int rc = (Integer) this.writeTimestamp.invoke(this.old,
                    instanceData, Utilities.convert(this.environment, handle),
                    Utilities.convert(this.environment, sourceTimestamp));
            Utilities.checkReturnCodeWithTimeout(rc, this.environment,
                    "DataWriter.write() failed.");
        } catch (IllegalAccessException e) {
            throw new DDSExceptionImpl(this.environment,
                    "DataWriter.write() failed (" + e.getMessage() + ").");
        } catch (IllegalArgumentExceptionImpl e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw new DDSExceptionImpl(this.environment,
                    "DataWriter.write() failed (" + e.getMessage() + ").");
        } catch (InvocationTargetException e) {
            throw new DDSExceptionImpl(this.environment,
                    "DataWriter.write() failed (" + e.getMessage() + ").");
        }
    }
	*/

    public void write(TYPE instanceData, InstanceHandle handle,
            long sourceTimestamp, TimeUnit unit) throws TimeoutException {
        /* TODO FRCYC
    	this.write(instanceData, handle, new TimeImpl(this.environment,
                sourceTimestamp, unit));*/
    }

    public void dispose(InstanceHandle instanceHandle) throws TimeoutException {
        this.dispose(instanceHandle, null);
    }

    public void dispose(InstanceHandle instanceHandle, TYPE instanceData)
            throws TimeoutException {
        try {
            int rc = (Integer) this.dispose.invoke(this.old, instanceData,
                    Utilities.convert(this.environment, instanceHandle));
            Utilities.checkReturnCodeWithTimeout(rc, this.environment,
                    "DataWriter.dispose() failed.");
        } catch (IllegalAccessException e) {
            throw new DDSExceptionImpl(this.environment,
                    "DataWriter.dispose() failed (" + e.getMessage() + ").");
        } catch (IllegalArgumentExceptionImpl e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw new DDSExceptionImpl(this.environment,
                    "DataWriter.dispose() failed (" + e.getMessage() + ").");
        } catch (InvocationTargetException e) {
            throw new DDSExceptionImpl(this.environment,
                    "DataWriter.dispose() failed (" + e.getMessage() + ").");
        }
    }

    /* TODO FRCYC
    public void dispose(InstanceHandle instanceHandle, TYPE instanceData,
            Time sourceTimestamp) throws TimeoutException {
        try {
            int rc = (Integer) this.disposeTimestamp.invoke(this.old,
                    instanceData,
                    Utilities.convert(this.environment, instanceHandle),
                    Utilities.convert(this.environment, sourceTimestamp));
            Utilities.checkReturnCodeWithTimeout(rc, this.environment,
                    "DataWriter.dispose() failed.");
        } catch (IllegalAccessException e) {
            throw new DDSExceptionImpl(this.environment,
                    "DataWriter.dispose() failed (" + e.getMessage() + ").");
        } catch (IllegalArgumentExceptionImpl e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw new DDSExceptionImpl(this.environment,
                    "DataWriter.dispose() failed (" + e.getMessage() + ").");
        } catch (InvocationTargetException e) {
            throw new DDSExceptionImpl(this.environment,
                    "DataWriter.dispose() failed (" + e.getMessage() + ").");
        }

    }


    public void dispose(InstanceHandle instanceHandle, TYPE instanceData,
            long sourceTimestamp, TimeUnit unit) throws TimeoutException {
        this.dispose(instanceHandle, instanceData, new TimeImpl(
                this.environment, sourceTimestamp, unit));

    }
        */

    @SuppressWarnings("unchecked")
    public TYPE getKeyValue(TYPE keyHolder, InstanceHandle handle) {
        Object holder;
        TYPE result;

        if (keyHolder == null) {
            return this.getKeyValue(handle);
        }

        try {
            holder = this.sampleHolderClz.newInstance();
            this.samplHolderValueField.set(holder, keyHolder);
            int rc = (Integer) this.getKeyValue.invoke(this.old, holder,
                    Utilities.convert(this.environment, handle));
            Utilities.checkReturnCode(rc, this.environment,
                    "DataWriter.getKeyValue() failed.");
            result = (TYPE) this.samplHolderValueField.get(holder);
        } catch (IllegalAccessException e) {
            throw new DDSExceptionImpl(this.environment,
                    "DataWriter.getKeyValue() failed (" + e.getMessage() + ").");
        } catch (IllegalArgumentExceptionImpl e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw new DDSExceptionImpl(this.environment,
                    "DataWriter.getKeyValue() failed (" + e.getMessage() + ").");
        } catch (InvocationTargetException e) {
            throw new DDSExceptionImpl(this.environment,
                    "DataWriter.getKeyValue() failed (" + e.getMessage() + ").");
        } catch (InstantiationException e) {
            throw new DDSExceptionImpl(this.environment,
                    "DataWriter.getKeyValue() failed (" + e.getMessage() + ").");
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public TYPE getKeyValue(InstanceHandle handle) {
        Object holder;
        TYPE result;

        try {
            holder = this.sampleHolderClz.newInstance();
            int rc = (Integer) this.getKeyValue.invoke(this.old, holder,
                    Utilities.convert(this.environment, handle));
            Utilities.checkReturnCode(rc, this.environment,
                    "DataWriter.getKeyValue() failed.");
            result = (TYPE) this.samplHolderValueField.get(holder);
        } catch (IllegalAccessException e) {
            throw new DDSExceptionImpl(this.environment,
                    "DataWriter.getKeyValue() failed (" + e.getMessage() + ").");
        } catch (IllegalArgumentExceptionImpl e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw new DDSExceptionImpl(this.environment,
                    "DataWriter.getKeyValue() failed (" + e.getMessage() + ").");
        } catch (InvocationTargetException e) {
            throw new DDSExceptionImpl(this.environment,
                    "DataWriter.getKeyValue() failed (" + e.getMessage() + ").");
        } catch (InstantiationException e) {
            throw new DDSExceptionImpl(this.environment,
                    "DataWriter.getKeyValue() failed (" + e.getMessage() + ").");
        }
        return result;
    }

    public InstanceHandle lookupInstance(TYPE keyHolder) {
        InstanceHandle handle;

        if (keyHolder == null) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Illegal keyHolder (null) provided.");
        }
        try {
            long result = (Long) this.lookupInstance
                    .invoke(this.old, keyHolder);
            handle = Utilities.convert(this.environment, result);
        } catch (IllegalAccessException e) {
            throw new DDSExceptionImpl(this.environment,
                    "DataWriter.lookupInstance() failed (" + e.getMessage()
                            + ").");
        } catch (IllegalArgumentExceptionImpl e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw new DDSExceptionImpl(this.environment,
                    "DataWriter.lookupInstance() failed (" + e.getMessage()
                            + ").");
        } catch (InvocationTargetException e) {
            throw new DDSExceptionImpl(this.environment,
                    "DataWriter.lookupInstance() failed (" + e.getMessage()
                            + ").");
        }
        return handle;
    }

    public void writeDispose(TYPE instanceData) throws TimeoutException {
        this.writeDispose(instanceData, this.environment.getSPI().nilHandle());

    }

    public void writeDispose(TYPE instanceData, Time sourceTimestamp)
            throws TimeoutException {
        this.writeDispose(instanceData, this.environment.getSPI().nilHandle(),
                sourceTimestamp);
    }

    public void writeDispose(TYPE instanceData, long sourceTimestamp,
            TimeUnit unit) throws TimeoutException {
        this.writeDispose(instanceData, this.environment.getSPI().nilHandle(),
                new TimeImpl(this.environment, sourceTimestamp, unit));
    }

    public void writeDispose(TYPE instanceData, InstanceHandle handle)
            throws TimeoutException {
        try {
            int rc = (Integer) this.writeDispose.invoke(this.old, instanceData,
                    Utilities.convert(this.environment, handle));
            Utilities.checkReturnCodeWithTimeout(rc, this.environment,
                    "DataWriter.writeDispose() failed.");
        } catch (IllegalAccessException e) {
            throw new DDSExceptionImpl(this.environment,
                    "DataWriter.writeDispose() failed (" + e.getMessage()
                            + ").");
        } catch (IllegalArgumentExceptionImpl e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw new DDSExceptionImpl(this.environment,
                    "DataWriter.writeDispose() failed (" + e.getMessage()
                            + ").");
        } catch (InvocationTargetException e) {
            throw new DDSExceptionImpl(this.environment,
                    "DataWriter.writeDispose() failed (" + e.getMessage()
                            + ").");
        }

    }

    public void writeDispose(TYPE instanceData, InstanceHandle handle,
            Time sourceTimestamp) throws TimeoutException {
        try {
            int rc = (Integer) this.writeDisposeTimestamp.invoke(this.old,
                    instanceData, Utilities.convert(this.environment, handle),
                    Utilities.convert(this.environment, sourceTimestamp));
            Utilities.checkReturnCodeWithTimeout(rc, this.environment,
                    "DataWriter.writeDispose() failed.");
        } catch (IllegalAccessException e) {
            throw new DDSExceptionImpl(this.environment,
                    "DataWriter.writeDispose() failed (" + e.getMessage()
                            + ").");
        } catch (IllegalArgumentExceptionImpl e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw new DDSExceptionImpl(this.environment,
                    "DataWriter.writeDispose() failed (" + e.getMessage()
                            + ").");
        } catch (InvocationTargetException e) {
            throw new DDSExceptionImpl(this.environment,
                    "DataWriter.writeDispose() failed (" + e.getMessage()
                            + ").");
        }
    }

    public void writeDispose(TYPE instanceData, InstanceHandle handle,
            long sourceTimestamp, TimeUnit unit) throws TimeoutException {
        this.writeDispose(instanceData, handle, new TimeImpl(this.environment,
                sourceTimestamp, unit));

    }

    
    public void assertLiveliness() {
        /* TODO FRCYC
    	int rc = this.old.assert_liveliness();
        Utilities.checkReturnCode(rc, this.environment,
                "DataWriter.assertLiveliness() failed.");
                */
    }

    public Set<InstanceHandle> getMatchedSubscriptions() {
    	/* TODO FRCYC
        InstanceHandleSeqHolder holder = new InstanceHandleSeqHolder();
        Set<InstanceHandle> handles;

        int rc = this.old.get_matched_subscriptions(holder);
        Utilities.checkReturnCode(rc, this.environment,
                "DataWriter.getMatchedSubscriptions() failed.");

        handles = new HashSet<InstanceHandle>();

        for (long handle : holder.value) {
            handles.add(Utilities.convert(this.environment, handle));
        }
        return handles;
        */
    	return null;
    }

    public SubscriptionBuiltinTopicData getMatchedSubscriptionData(
            InstanceHandle subscriptionHandle) {
    	/* TODO FRCYC
        SubscriptionBuiltinTopicDataHolder holder = new SubscriptionBuiltinTopicDataHolder();
        int rc = this.old.get_matched_subscription_data(holder,
                Utilities.convert(this.environment, subscriptionHandle));
        Utilities.checkReturnCode(rc, this.environment,
                "DataWriter.getMatchedSubscriptionData() failed.");
        if (holder.value != null) {
            return new SubscriptionBuiltinTopicDataImpl(this.environment,
                    holder.value);
        }
        throw new PreconditionNotMetExceptionImpl(this.environment,
                    "No data for this instanceHandle.");
                    */
    	return null;
    }

    public DataWriterQos getQos() {
    	/* TODO FRCYC
        DataWriterQosHolder holder = new DataWriterQosHolder();
        int rc = this.old.get_qos(holder);
        Utilities.checkReturnCode(rc, this.environment,
                "DataWriter.getQos() failed.");

        return DataWriterQosImpl.convert(this.environment, holder.value);
        */
    	return null;
    }

    
    public void setQos(DataWriterQos qos) {
        DataWriterQosImpl q;

        if (qos == null) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Supplied DataWriterQos is null.");
        }
        try {
            q = (DataWriterQosImpl) qos;
        } catch (ClassCastException e) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Setting non-OpenSplice Qos not supported.");
        }
        /* TODO FRCYC
        int rc = this.old.set_qos(q.convert());
        Utilities.checkReturnCode(rc, this.environment,
                "DataWriter.setQos() failed.");
                */
    }

    public void waitForAcknowledgments(Duration maxWait)
            throws TimeoutException {
    	/* TODO FRCYC
        int rc = this.old.wait_for_acknowledgments(Utilities.convert(this.environment,
                maxWait));
        Utilities.checkReturnCodeWithTimeout(rc, this.environment,
                "DataWriter.waitForAcknowledgments() failed.");*/
    }

    public void waitForAcknowledgments(long maxWait, TimeUnit unit)
            throws TimeoutException {
        this.waitForAcknowledgments(this.environment.getSPI().newDuration(
                maxWait, unit));
    }

    public LivelinessLostStatus getLivelinessLostStatus() {
    	/* TODO FRCYC
        LivelinessLostStatusHolder holder = new LivelinessLostStatusHolder();

        int rc = this.old.get_liveliness_lost_status(holder);
        Utilities.checkReturnCode(rc, this.environment,
                "DataWriter.getLivelinessLostStatus() failed.");

        return StatusConverter.convert(this.environment, holder.value);
        */ 
    	return null;
    }

    public OfferedDeadlineMissedStatus getOfferedDeadlineMissedStatus() {
        /* TODO FRCYC
    	OfferedDeadlineMissedStatusHolder holder = new OfferedDeadlineMissedStatusHolder();

        int rc = this.old.get_offered_deadline_missed_status(holder);
        Utilities.checkReturnCode(rc, this.environment,
                "DataWriter.getOfferedDeadlineMissedStatus() failed.");

        return StatusConverter.convert(this.environment, holder.value);
        */
    	return null;
    }

    public OfferedIncompatibleQosStatus getOfferedIncompatibleQosStatus() {
    	/* TODO FRCYC
        OfferedIncompatibleQosStatusHolder holder = new OfferedIncompatibleQosStatusHolder();

        int rc = this.old.get_offered_incompatible_qos_status(holder);
        Utilities.checkReturnCode(rc, this.environment,
                "DataWriter.getOfferedIncompatibleQosStatus() failed.");

        return StatusConverter.convert(this.environment, holder.value);
        */
    	return null;
    }

    public PublicationMatchedStatus getPublicationMatchedStatus() {
    	/* TODO FRCYC
        PublicationMatchedStatusHolder holder = new PublicationMatchedStatusHolder();

        int rc = this.old.get_publication_matched_status(holder);
        Utilities.checkReturnCode(rc, this.environment,
                "DataWriter.getPublicationMatchedStatus() failed.");

        return StatusConverter.convert(this.environment, holder.value);
        */
    	return null;
    }
    
}
