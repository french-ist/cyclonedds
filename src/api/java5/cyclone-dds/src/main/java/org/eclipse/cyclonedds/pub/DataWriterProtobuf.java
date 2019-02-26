/*
 *                         Vortex OpenSplice
 *
 *   This software and documentation are Copyright 2006 to TO_YEAR ADLINK
 *   Technology Limited, its affiliated companies and licensors. All rights
 *   reserved.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */
package org.eclipse.cyclonedds.pub;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.omg.dds.core.Duration;
import org.omg.dds.core.InstanceHandle;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.StatusCondition;
import org.omg.dds.core.Time;
import org.omg.dds.core.status.LivelinessLostStatus;
import org.omg.dds.core.status.OfferedDeadlineMissedStatus;
import org.omg.dds.core.status.OfferedIncompatibleQosStatus;
import org.omg.dds.core.status.PublicationMatchedStatus;
import org.omg.dds.core.status.Status;
import org.omg.dds.pub.DataWriter;
import org.omg.dds.pub.DataWriterListener;
import org.omg.dds.pub.DataWriterQos;
import org.omg.dds.topic.SubscriptionBuiltinTopicData;
import org.omg.dds.topic.Topic;
import org.eclipse.cyclonedds.core.IllegalArgumentExceptionImpl;
import org.eclipse.cyclonedds.core.IllegalOperationExceptionImpl;
import org.eclipse.cyclonedds.core.CycloneServiceEnvironment;
import org.eclipse.cyclonedds.core.StatusConditionImpl;
import org.eclipse.cyclonedds.core.Utilities;
import org.eclipse.cyclonedds.core.status.StatusConverter;
import org.eclipse.cyclonedds.pub.PublisherImpl;
import org.eclipse.cyclonedds.topic.TopicProtobuf;
import org.eclipse.cyclonedds.type.TypeSupportProtobuf;

public class DataWriterProtobuf<PROTOBUF_TYPE, DDS_TYPE> extends
        AbstractDataWriter<PROTOBUF_TYPE> {
    private final TopicProtobuf<PROTOBUF_TYPE> topic;
    private final ReflectionDataWriter<DDS_TYPE> reflectionWriter = null; //TODO FRCYC 
    protected final TypeSupportProtobuf<PROTOBUF_TYPE, DDS_TYPE> typeSupport;

    @SuppressWarnings("unchecked")
    public DataWriterProtobuf(CycloneServiceEnvironment environment,
            PublisherImpl parent, TopicProtobuf<PROTOBUF_TYPE> topic,
            DataWriterQos qos, DataWriterListener<PROTOBUF_TYPE> listener,
            Collection<Class<? extends Status>> statuses) {
        super(environment, parent);

        if (qos == null) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Supplied DataWriterQos is null.");
        }
        if (topic == null) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Supplied Topic is null.");
        }
        DataWriterQos oldQos;

        this.topic = topic;
        this.typeSupport = (TypeSupportProtobuf<PROTOBUF_TYPE, DDS_TYPE>) topic
                .getTypeSupport();

        try {
            //oldQos = ((DataWriterQosImpl) qos).convert();
        } catch (ClassCastException e) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Cannot create DataWriter with non-OpenSplice qos");
        }

        if (listener != null) {
            this.listener = new DataWriterListenerImpl<PROTOBUF_TYPE>(
                    this.environment, this, listener, true);
        } else {
            this.listener = null;
        }
        /* TODO FRCYC
        DataWriter old = this.parent.getOld().create_datawriter(
                topic.getOld(), oldQos, this.listener,
                StatusConverter.convertMask(this.environment, statuses));

        if (old == null) {
            Utilities.throwLastErrorException(this.environment);
        }
        this.setOld(old);
        this.reflectionWriter = new ReflectionDataWriter<DDS_TYPE>(
                this.environment, this.getOld(), this.typeSupport
                        .getTypeSupportStandard().getType());
        this.topic.retain();
         */
        if (this.listener != null) {
            this.listener.setInitialised();
        }
    }

    @Override
    public void dispose(InstanceHandle instanceHandle) throws TimeoutException {
        this.reflectionWriter.dispose(instanceHandle);
    }

    @Override
    public void assertLiveliness() {
        this.reflectionWriter.assertLiveliness();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <OTHER> org.omg.dds.pub.DataWriter<OTHER> cast() {
        DataWriter<OTHER> other;
        try {
            other = (DataWriter<OTHER>) this;
        } catch (ClassCastException cce) {
            throw new IllegalOperationExceptionImpl(this.environment,
                    "Unable to perform requested cast.");
        }
        return other;

    }

    @Override
    public void dispose(InstanceHandle instanceHandle,
            PROTOBUF_TYPE instanceData) throws TimeoutException {
        this.reflectionWriter.dispose(instanceHandle,
                this.typeSupport.protobufToDds(instanceData));
    }

    /* TODO FRCYC
    @Override
    public void dispose(InstanceHandle instanceHandle,
            PROTOBUF_TYPE instanceData, Time sourceTimestamp)
            throws TimeoutException {
        this.reflectionWriter.dispose(instanceHandle,
                this.typeSupport.protobufToDds(instanceData), sourceTimestamp);

    }
    

    @Override
    public void dispose(InstanceHandle instanceHandle,
            PROTOBUF_TYPE instanceData, long sourceTimeStamp, TimeUnit unit)
            throws TimeoutException {
        this.reflectionWriter.dispose(instanceHandle,
                this.typeSupport.protobufToDds(instanceData), sourceTimeStamp,
                unit);
    }
    */

    @Override
    public PROTOBUF_TYPE getKeyValue(InstanceHandle instanceHandle) {
        DDS_TYPE keyValue = this.reflectionWriter.getKeyValue(instanceHandle);

        if (keyValue != null) {
            return this.typeSupport.ddsKeyToProtobuf(keyValue);
        }
        return null;
    }

    @Override
    public PROTOBUF_TYPE getKeyValue(PROTOBUF_TYPE instanceData,
            InstanceHandle instanceHandle) {
        DDS_TYPE keyValue = this.reflectionWriter.getKeyValue(
                this.typeSupport.protobufToDds(instanceData), instanceHandle);

        if (keyValue != null) {
            return this.typeSupport.ddsKeyToProtobuf(keyValue);
        }
        return null;
    }

    /* TODO FRCYC
    @Override
    public LivelinessLostStatus getLivelinessLostStatus() {
        return this.reflectionWriter.getLivelinessLostStatus();
    }
     */
    @Override
    public SubscriptionBuiltinTopicData getMatchedSubscriptionData(
            InstanceHandle instanceHandle) {
        return this.reflectionWriter.getMatchedSubscriptionData(instanceHandle);
    }

    @Override
    public Set<InstanceHandle> getMatchedSubscriptions() {
        return this.reflectionWriter.getMatchedSubscriptions();
    }

    /* TODO FRCYC
    @Override
    public OfferedDeadlineMissedStatus getOfferedDeadlineMissedStatus() {
        return this.reflectionWriter.getOfferedDeadlineMissedStatus();
    }

    @Override
    public OfferedIncompatibleQosStatus getOfferedIncompatibleQosStatus() {
        return this.reflectionWriter.getOfferedIncompatibleQosStatus();
    }

    @Override
    public PublicationMatchedStatus getPublicationMatchedStatus() {
        return this.reflectionWriter.getPublicationMatchedStatus();
    }

    
    @Override
    public StatusCondition<org.omg.dds.pub.DataWriter<PROTOBUF_TYPE>> getStatusCondition() {
        StatusCondition oldCondition = this.getOld().get_statuscondition();

        if (oldCondition == null) {
            Utilities.throwLastErrorException(this.environment);
        }
        return new StatusConditionImpl<DataWriter<PROTOBUF_TYPE>>(
                this.environment, oldCondition, this);
    }
    */

    @Override
    public Topic<PROTOBUF_TYPE> getTopic() {
        return this.topic;
    }

    @Override
    public InstanceHandle lookupInstance(PROTOBUF_TYPE instanceData) {
        return this.reflectionWriter.lookupInstance(this.typeSupport
                .protobufToDds(instanceData));
    }

    @Override
    public InstanceHandle registerInstance(PROTOBUF_TYPE instanceData)
            throws TimeoutException {
        return this.reflectionWriter.registerInstance(this.typeSupport
                .protobufToDds(instanceData));
    }

    /* TODO FRCYC
    @Override
    public InstanceHandle registerInstance(PROTOBUF_TYPE instanceData,
            Time sourceTimestamp) throws TimeoutException {
        return this.reflectionWriter.registerInstance(
                this.typeSupport.protobufToDds(instanceData), sourceTimestamp);
    }

    @Override
    public InstanceHandle registerInstance(PROTOBUF_TYPE instanceData,
            long sourceTimestamp, TimeUnit unit) throws TimeoutException {
        return this.reflectionWriter.registerInstance(
                this.typeSupport.protobufToDds(instanceData), sourceTimestamp,
                unit);
    }
    */

    @Override
    public void unregisterInstance(InstanceHandle handle)
            throws TimeoutException {
        this.reflectionWriter.unregisterInstance(handle);

    }

    @Override
    public void unregisterInstance(InstanceHandle handle,
            PROTOBUF_TYPE instanceData) throws TimeoutException {
        this.reflectionWriter.unregisterInstance(handle,
                this.typeSupport.protobufToDds(instanceData));
    }

    /* TODO FRCYC
    @Override
    public void unregisterInstance(InstanceHandle handle,
            PROTOBUF_TYPE instanceData, Time sourceTimestamp)
            throws TimeoutException {
        this.reflectionWriter.unregisterInstance(handle,
                this.typeSupport.protobufToDds(instanceData), sourceTimestamp);

    }

    @Override
    public void unregisterInstance(InstanceHandle handle,
            PROTOBUF_TYPE instanceData, long sourceTimestamp, TimeUnit unit)
            throws TimeoutException {
        this.reflectionWriter.unregisterInstance(handle,
                this.typeSupport.protobufToDds(instanceData), sourceTimestamp,
                unit);

    }

    @Override
    public void waitForAcknowledgments(Duration maxWait)
            throws TimeoutException {
        this.reflectionWriter.waitForAcknowledgments(maxWait);
    }

    @Override
    public void waitForAcknowledgments(long maxWait, TimeUnit unit)
            throws TimeoutException {
        this.reflectionWriter.waitForAcknowledgments(maxWait, unit);
    }
    */

    @Override
    public void write(PROTOBUF_TYPE instanceData) throws TimeoutException {
        this.reflectionWriter.write(this.typeSupport
                .protobufToDds(instanceData));
    }

    @Override
    public void write(PROTOBUF_TYPE instanceData, Time sourceTimestamp)
            throws TimeoutException {
        this.reflectionWriter.write(
                this.typeSupport.protobufToDds(instanceData), sourceTimestamp);
    }

    @Override
    public void write(PROTOBUF_TYPE instanceData, InstanceHandle handle)
            throws TimeoutException {
        this.reflectionWriter.write(
                this.typeSupport.protobufToDds(instanceData), handle);

    }

    @Override
    public void write(PROTOBUF_TYPE instanceData, long sourceTimestamp,
            TimeUnit unit) throws TimeoutException {
        this.reflectionWriter.write(
                this.typeSupport.protobufToDds(instanceData), sourceTimestamp,
                unit);
    }

    /* TODO FRCYC
    @Override
    public void write(PROTOBUF_TYPE instanceData, InstanceHandle handle,
            Time sourceTimestamp) throws TimeoutException {
        this.reflectionWriter.write(
                this.typeSupport.protobufToDds(instanceData), handle,
                sourceTimestamp);
    }
    */

    @Override
    public void write(PROTOBUF_TYPE instanceData, InstanceHandle handle,
            long sourceTimestamp, TimeUnit unit) throws TimeoutException {
        this.reflectionWriter.write(
                this.typeSupport.protobufToDds(instanceData), handle,
                sourceTimestamp, unit);
    }

    @Override
    public DataWriterQos getQos() {
        return this.reflectionWriter.getQos();
    }

    /* TODO FRCYC    
    private void setListener(DataWriterListener<PROTOBUF_TYPE> listener,
            int mask) {
        DataWriterListenerImpl<PROTOBUF_TYPE> wrapperListener;
        int rc;

        if (listener != null) {
            wrapperListener = new DataWriterListenerImpl<PROTOBUF_TYPE>(
                    this.environment, this, listener);
        } else {
            wrapperListener = null;
        }
        rc = this.getOld().set_listener(wrapperListener, mask);
        Utilities.checkReturnCode(rc, this.environment,
                "DataWriter.setListener() failed.");

        this.listener = wrapperListener;
    }
    
    @Override
    public void setListener(DataWriterListener<PROTOBUF_TYPE> listener) {
        this.setListener(listener, StatusConverter.getAnyMask());
    }

    @Override
    public void setListener(DataWriterListener<PROTOBUF_TYPE> listener,
            Collection<Class<? extends Status>> statuses) {
        this.setListener(listener,
                StatusConverter.convertMask(this.environment, statuses));
    }

    @Override
    public void setListener(DataWriterListener<PROTOBUF_TYPE> listener,
            Class<? extends Status>... statuses) {
        this.setListener(listener,
                StatusConverter.convertMask(this.environment, statuses));
    }

    @Override
    public void setQos(DataWriterQos qos) {
        this.reflectionWriter.setQos(qos);
    }
    */

    @Override
    public ServiceEnvironment getEnvironment() {
        return this.environment;
    }

    @Override
    public void writeDispose(PROTOBUF_TYPE instanceData)
            throws TimeoutException {
        this.reflectionWriter.writeDispose(this.typeSupport
                .protobufToDds(instanceData));
    }

    @Override
    public void writeDispose(PROTOBUF_TYPE instanceData, Time sourceTimestamp)
            throws TimeoutException {
        this.reflectionWriter.writeDispose(
                this.typeSupport.protobufToDds(instanceData), sourceTimestamp);
    }

    @Override
    public void writeDispose(PROTOBUF_TYPE instanceData, InstanceHandle handle)
            throws TimeoutException {
        this.reflectionWriter.writeDispose(
                this.typeSupport.protobufToDds(instanceData), handle);
    }

    @Override
    public void writeDispose(PROTOBUF_TYPE instanceData, long sourceTimestamp,
            TimeUnit unit) throws TimeoutException {
        this.reflectionWriter.writeDispose(
                this.typeSupport.protobufToDds(instanceData), sourceTimestamp,
                unit);
    }

    @Override
    public void writeDispose(PROTOBUF_TYPE instanceData, InstanceHandle handle,
            Time sourceTimestamp) throws TimeoutException {
        this.reflectionWriter.writeDispose(
                this.typeSupport.protobufToDds(instanceData), handle,
                sourceTimestamp);

    }

    @Override
    public void writeDispose(PROTOBUF_TYPE instanceData,
            InstanceHandle instanceHandle, long sourceTimestamp, TimeUnit unit)
            throws TimeoutException {
        this.reflectionWriter.writeDispose(
                this.typeSupport.protobufToDds(instanceData), sourceTimestamp,
                unit);
    }

    @Override
    protected void destroy() {
        super.destroy();
        this.topic.close();
    }

	@Override
	public StatusCondition<DataWriter<PROTOBUF_TYPE>> getStatusCondition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setListener(DataWriterListener<PROTOBUF_TYPE> listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setListener(DataWriterListener<PROTOBUF_TYPE> listener, Collection<Class<? extends Status>> statuses) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setListener(DataWriterListener<PROTOBUF_TYPE> listener, Class<? extends Status>... statuses) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enable() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<Class<? extends Status>> getStatusChanges() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InstanceHandle getInstanceHandle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void waitForAcknowledgments(Duration maxWait) throws TimeoutException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void waitForAcknowledgments(long maxWait, TimeUnit unit) throws TimeoutException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public LivelinessLostStatus getLivelinessLostStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OfferedDeadlineMissedStatus getOfferedDeadlineMissedStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OfferedIncompatibleQosStatus getOfferedIncompatibleQosStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PublicationMatchedStatus getPublicationMatchedStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InstanceHandle registerInstance(PROTOBUF_TYPE instanceData, Time sourceTimestamp) throws TimeoutException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InstanceHandle registerInstance(PROTOBUF_TYPE instanceData, long sourceTimestamp, TimeUnit unit)
			throws TimeoutException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void unregisterInstance(InstanceHandle handle, PROTOBUF_TYPE instanceData, Time sourceTimestamp)
			throws TimeoutException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unregisterInstance(InstanceHandle handle, PROTOBUF_TYPE instanceData, long sourceTimestamp,
			TimeUnit unit) throws TimeoutException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void write(PROTOBUF_TYPE instanceData, InstanceHandle handle, Time sourceTimestamp) throws TimeoutException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose(InstanceHandle instanceHandle, PROTOBUF_TYPE instanceData, Time sourceTimestamp)
			throws TimeoutException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose(InstanceHandle instanceHandle, PROTOBUF_TYPE instanceData, long sourceTimestamp, TimeUnit unit)
			throws TimeoutException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setQos(DataWriterQos qos) {
		// TODO Auto-generated method stub
		
	}
}
