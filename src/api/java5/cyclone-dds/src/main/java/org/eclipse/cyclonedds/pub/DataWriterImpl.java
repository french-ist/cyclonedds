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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.eclipse.cyclonedds.core.AlreadyClosedExceptionImpl;
import org.eclipse.cyclonedds.core.ServiceEnvironmentImpl;
import org.eclipse.cyclonedds.core.IllegalArgumentExceptionImpl;
import org.eclipse.cyclonedds.core.InstanceHandleImpl;
import org.eclipse.cyclonedds.core.TimeImpl;
import org.eclipse.cyclonedds.core.Utilities;
import org.eclipse.cyclonedds.dcps.keys.KeyHashEncoder;
import org.eclipse.cyclonedds.ddsc.dds.DdscLibrary;
import org.eclipse.cyclonedds.topic.UserClassHelper;
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
import com.sun.jna.Structure;
import com.sun.jna.Structure.ByReference;
import org.eclipse.cyclonedds.core.UserClass;


public class DataWriterImpl<TYPE> extends AbstractDataWriter<TYPE> {
    
	private final TopicImpl<TYPE> topic;
	private DataWriterQos qos;
	private Collection<Class<? extends Status>> statuses;
	private boolean enabled = false;
	private final int jnaDataWriter;

    @SuppressWarnings("unchecked")
	public DataWriterImpl(ServiceEnvironmentImpl environment,
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
	public DataWriterQos getQos() {
		return qos;
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
	public void setQos(DataWriterQos qos) {
		this.qos = qos;
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
	public void write(TYPE instanceData, InstanceHandle handle, Time sourceTimestamp) throws TimeoutException {
		if(jnaDataWriter > 0) {
			UserClass data = (UserClass) instanceData;
			ByReference ref = data.getStructureReference();
			((Structure) ref).write();
			DdscLibrary.dds_write_ts(jnaDataWriter, ((Structure) ref).getPointer(), sourceTimestamp.getTime(TimeUnit.NANOSECONDS));
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
}
