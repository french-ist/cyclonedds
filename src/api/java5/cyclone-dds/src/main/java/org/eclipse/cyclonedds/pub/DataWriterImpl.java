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

import org.eclipse.cyclonedds.core.AlreadyClosedExceptionImpl;
import org.eclipse.cyclonedds.core.CycloneServiceEnvironment;
import org.eclipse.cyclonedds.core.IllegalArgumentExceptionImpl;
import org.eclipse.cyclonedds.core.InstanceHandleImpl;
import org.eclipse.cyclonedds.core.TimeImpl;
import org.eclipse.cyclonedds.core.Utilities;
import org.eclipse.cyclonedds.dcps.keys.KeyHashEncoder;
import org.eclipse.cyclonedds.ddsc.dds.DdscLibrary;
import org.eclipse.cyclonedds.topic.TopicImpl;
import org.omg.dds.core.Duration;
import org.omg.dds.core.InstanceHandle;
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

import com.sun.jna.Pointer;

public class DataWriterImpl<TYPE> extends AbstractDataWriter<TYPE> {
    
	private final TopicImpl<TYPE> topic;
	private DataWriterQos qos;
	private Collection<Class<? extends Status>> statuses;
	private boolean enabled = false;
	private final int jnaDataWriter;

    @SuppressWarnings("unchecked")
	public DataWriterImpl(CycloneServiceEnvironment environment,
            PublisherImpl parent, TopicImpl<TYPE> topic, DataWriterQos qos,
            DataWriterListener<TYPE> listener,
            Collection<Class<? extends Status>> statuses) {
        super(environment, parent);
        this.topic = topic;
        this.qos = qos;
        this.listener = (DataWriterListenerImpl<TYPE>) listener;
        this.statuses = statuses;
        
        if (qos == null) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Supplied DataWriterQos is null.");
        }
        
        if (topic == null) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Supplied Topic is null.");
        }
        
        jnaDataWriter = DdscLibrary.dds_create_writer(
        		parent.getJnaPublisher(), 
        		topic.getJnaTopic(), 
        		Utilities.convert(qos), 
        		Utilities.convert(listener));

        /*
        DataWriterQos oldQos;

        try {
            //oldQos = ((DataWriterQosImpl) qos).convert();
        } catch (ClassCastException e) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Cannot create DataWriter with non-OpenSplice qos");
        }

        if (listener != null) {
            this.listener = new DataWriterListenerImpl<TYPE>(this.environment,
                    this, listener, true);
        } else {
            this.listener = null;
        }
        FRCYC To be replaced by JNA/JNI access
        DataWriter old = this.parent.getOld().create_datawriter(
                topic.getOld(), oldQos, this.listener,
                StatusConverter.convertMask(this.environment, statuses));

        if (old == null) {
            Utilities.throwLastErrorException(this.environment);
        }
        this.setOld(old);
        
        this.reflectionWriter = new ReflectionDataWriter<TYPE>(this.environment, this.getOld(), this.topic.getTypeSupport()
                        .getType());
        this.topic.retain();

        if (this.listener != null) {
            this.listener.setInitialised();
        }
        */
    }

	@Override
	public void writeDispose(TYPE instanceData) throws TimeoutException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeDispose(TYPE instanceData, Time sourceTimestamp) throws TimeoutException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeDispose(TYPE instanceData, long sourceTimestamp, TimeUnit unit) throws TimeoutException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeDispose(TYPE instanceData, InstanceHandle handle) throws TimeoutException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeDispose(TYPE instanceData, InstanceHandle handle, Time sourceTimestamp) throws TimeoutException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeDispose(TYPE instanceData, InstanceHandle handle, long sourceTimestamp, TimeUnit unit)
			throws TimeoutException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <OTHER> DataWriter<OTHER> cast() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Topic<TYPE> getTopic() {
		return topic;
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
	public void assertLiveliness() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<InstanceHandle> getMatchedSubscriptions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SubscriptionBuiltinTopicData getMatchedSubscriptionData(InstanceHandle subscriptionHandle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InstanceHandle registerInstance(TYPE instanceData) throws TimeoutException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InstanceHandle registerInstance(TYPE instanceData, Time sourceTimestamp) throws TimeoutException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InstanceHandle registerInstance(TYPE instanceData, long sourceTimestamp, TimeUnit unit)
			throws TimeoutException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void unregisterInstance(InstanceHandle handle) throws TimeoutException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unregisterInstance(InstanceHandle handle, TYPE instanceData) throws TimeoutException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unregisterInstance(InstanceHandle handle, TYPE instanceData, Time sourceTimestamp)
			throws TimeoutException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unregisterInstance(InstanceHandle handle, TYPE instanceData, long sourceTimestamp, TimeUnit unit)
			throws TimeoutException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void write(TYPE instanceData) throws TimeoutException {
		write(instanceData, new TimeImpl(environment, System.nanoTime(), TimeUnit.NANOSECONDS));
	}

	protected KeyHashEncoder<TYPE> keyHashEncoder;
	
	@Override
	public void write(TYPE instanceData, Time sourceTimestamp) throws TimeoutException {
		if(jnaDataWriter > 0) {
			InstanceHandle handle = null;
			if(keyHashEncoder != null) {				
				handle = keyHashEncoder.encode(instanceData);
			} else {
				handle = new InstanceHandleImpl(environment, jnaDataWriter);
			}
			write(instanceData, handle, sourceTimestamp);
		} else {
			throw new AlreadyClosedExceptionImpl(environment, "DataWriter is closed; can't write");
		}
	}

	@Override
	public void write(TYPE instanceData, long sourceTimestamp, TimeUnit unit) throws TimeoutException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void write(TYPE instanceData, InstanceHandle handle) throws TimeoutException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void write(TYPE instanceData, InstanceHandle handle, Time sourceTimestamp) throws TimeoutException {
		if(jnaDataWriter > 0) {			
			Pointer pointer = null;
			DdscLibrary.dds_write_ts(jnaDataWriter, pointer, sourceTimestamp.getTime(TimeUnit.NANOSECONDS));
			
		} else {
			throw new AlreadyClosedExceptionImpl(environment, "DataWriter is closed; can't write");
		}
		
	}

	@Override
	public void write(TYPE instanceData, InstanceHandle handle, long sourceTimestamp, TimeUnit unit)
			throws TimeoutException {
		// TODO Auto-generated method stub
	}

	@Override
	public void dispose(InstanceHandle instanceHandle) throws TimeoutException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose(InstanceHandle instanceHandle, TYPE instanceData) throws TimeoutException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose(InstanceHandle instanceHandle, TYPE instanceData, Time sourceTimestamp)
			throws TimeoutException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose(InstanceHandle instanceHandle, TYPE instanceData, long sourceTimestamp, TimeUnit unit)
			throws TimeoutException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TYPE getKeyValue(TYPE keyHolder, InstanceHandle handle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TYPE getKeyValue(InstanceHandle handle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InstanceHandle lookupInstance(TYPE keyHolder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusCondition<DataWriter<TYPE>> getStatusCondition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setListener(DataWriterListener<TYPE> listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setListener(DataWriterListener<TYPE> listener, Collection<Class<? extends Status>> statuses) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setListener(DataWriterListener<TYPE> listener, Class<? extends Status>... statuses) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public DataWriterQos getQos() {
		return qos;
	}

	@Override
	public void setQos(DataWriterQos qos) {
		this.qos = qos;
	}

	@Override
	public void enable() {
		this.enabled  = true;
	}

	@Override
	public Set<Class<? extends Status>> getStatusChanges() {
		return (Set<Class<? extends Status>>) statuses;
	}

	@Override
	public InstanceHandle getInstanceHandle() {
		// TODO Auto-generated method stub
		return null;
	}

    
}
