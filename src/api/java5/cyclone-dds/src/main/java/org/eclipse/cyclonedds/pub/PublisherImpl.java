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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.eclipse.cyclonedds.core.AlreadyClosedExceptionImpl;
import org.eclipse.cyclonedds.core.CycloneServiceEnvironment;
import org.eclipse.cyclonedds.core.DomainEntityImpl;
import org.eclipse.cyclonedds.core.InstanceHandleImpl;
import org.eclipse.cyclonedds.core.PreconditionNotMetExceptionImpl;
import org.eclipse.cyclonedds.core.UnsupportedOperationExceptionImpl;
import org.eclipse.cyclonedds.core.Utilities;
import org.eclipse.cyclonedds.ddsc.dds.DdscLibrary;
import org.eclipse.cyclonedds.domain.DomainParticipantImpl;
import org.eclipse.cyclonedds.topic.TopicImpl;
import org.eclipse.cyclonedds.type.AbstractTypeSupport;
import org.omg.dds.core.Duration;
import org.omg.dds.core.InstanceHandle;
import org.omg.dds.core.StatusCondition;
import org.omg.dds.core.policy.QosPolicy.ForPublisher;
import org.omg.dds.core.status.Status;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.domain.DomainParticipantQos;
import org.omg.dds.pub.DataWriter;
import org.omg.dds.pub.DataWriterListener;
import org.omg.dds.pub.DataWriterQos;
import org.omg.dds.pub.Publisher;
import org.omg.dds.pub.PublisherListener;
import org.omg.dds.pub.PublisherQos;
import org.omg.dds.topic.Topic;
import org.omg.dds.topic.TopicQos;

public class PublisherImpl extends DomainEntityImpl<PublisherQos, PublisherListener, PublisherListenerImpl> implements Publisher {

	private DomainParticipantImpl parent;
	private PublisherQos qos;
	private InstanceHandleImpl handle;
	private List<AbstractDataWriter<?>> writers;
	private DataWriterQos defaultDataWriterQos;
	private boolean closed = false;
	private boolean enabled = false;
	private final int jnaPublisher;

	public PublisherImpl(CycloneServiceEnvironment environment,
			DomainParticipantImpl parent, 
			PublisherQos qos,
			PublisherListener listener,
			Collection<Class<? extends Status>> statuses) {
		super(environment);
		this.parent = parent;
		this.qos = qos;
		handle = new InstanceHandleImpl(environment, parent.getJnaParticipant()); 
		writers = Collections.synchronizedList(new ArrayList<AbstractDataWriter<?>>());
		defaultDataWriterQos = new DataWriterQosImpl(environment);
				
		jnaPublisher = DdscLibrary.dds_create_publisher(
				parent.getJnaParticipant(), 
				Utilities.convert(qos),
				Utilities.convert(parent));
	}

	@Override
	public void setListener(PublisherListener listener) {
		throw new UnsupportedOperationExceptionImpl(environment, "NYI");
	}

	@Override
	public void setListener(PublisherListener listener, Collection<Class<? extends Status>> statuses) {
		throw new UnsupportedOperationExceptionImpl(environment, "NYI");
	}

	@Override
	public void setListener(PublisherListener listener, Class<? extends Status>... statuses) {
		throw new UnsupportedOperationExceptionImpl(environment, "NYI");		
	}

	@Override
	public PublisherQos getQos() {
		if( !closed  ) {
			return qos;
		}
		throw new AlreadyClosedExceptionImpl(environment, "public PublisherQos getQos()");
	}

	@Override
	public void setQos(PublisherQos qos) {
		if(closed) {
			throw new AlreadyClosedExceptionImpl(environment, "public PublisherQos getQos()");
		}
		
		for(ForPublisher policy: qos.values()) {
			this.qos.withPolicy(policy);
		}
		
		for(AbstractDataWriter<?> writer: writers) {
			if(writer instanceof DataWriterImpl) {
				DataWriterImpl<?> dwImpl = (DataWriterImpl<?>)writer;
				throw new AlreadyClosedExceptionImpl(environment, "TODO set qos for each DW with qos.values(), handle, and name impact in JNA");
			}
		}
		
	}

	@Override
	public void enable() {
		if(parent.isEnabled()) {
			enabled  = true;
			if(qos.getEntityFactory().isAutoEnableCreatedEntities()) {
				for (DataWriter<?> dw : writers) {
					dw.enable();
				}
			}
		} else {
			throw new PreconditionNotMetExceptionImpl(environment, "Parent domain participant not yer enabled");
		}
	}

	@Override
	public Set<Class<? extends Status>> getStatusChanges() {
		throw new UnsupportedOperationExceptionImpl(environment, "NYI");	
	}

	@Override
	public InstanceHandle getInstanceHandle() {
		if( !closed  ) {
			return handle;
		}
		throw new AlreadyClosedExceptionImpl(environment, "public InstanceHandle getInstanceHandle()");
	}

	@Override
	public <TYPE> DataWriter<TYPE> createDataWriter(Topic<TYPE> topic) {
		return createDataWriter(topic, getDefaultDataWriterQos());
	}

	@Override
	public <TYPE> DataWriter<TYPE> createDataWriter(Topic<TYPE> topic, DataWriterQos qos,
			DataWriterListener<TYPE> listener, Collection<Class<? extends Status>> statuses) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <TYPE> DataWriter<TYPE> createDataWriter(Topic<TYPE> topic, DataWriterQos qos,
			DataWriterListener<TYPE> listener, Class<? extends Status>... statuses) {
		
		if(closed) {
			throw new AlreadyClosedExceptionImpl(environment, "Publisher closed, can't create DataWriter");
		}
		
		if(qos == null) {
			qos = getDefaultDataWriterQos();
		}
		
		AbstractTypeSupport<TYPE> typeSupport = (AbstractTypeSupport<TYPE>) topic.getTypeSupport();
		AbstractDataWriter<TYPE> dataWriter = typeSupport.createDataWriter(this,(TopicImpl<TYPE>)topic, qos, listener, Arrays.asList(statuses));
		writers.add(dataWriter);
		if(this.qos.getEntityFactory().isAutoEnableCreatedEntities() && enabled) {
			dataWriter.enable();
		}
		return dataWriter;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <TYPE> DataWriter<TYPE> createDataWriter(Topic<TYPE> topic, DataWriterQos qos) {
		return createDataWriter(topic, qos, null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <TYPE> DataWriter<TYPE> lookupDataWriter(String topicName) {
		if(closed) {
			throw new AlreadyClosedExceptionImpl(environment, "Publisher closed, can't lookup DataWriter");
		}
		
		for (AbstractDataWriter<?> writer : writers) {
			if(writer.getTopic().getName().equals(topicName)) {
				return ( DataWriter<TYPE>) writer;
			}
		}
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <TYPE> DataWriter<TYPE> lookupDataWriter(Topic<TYPE> topic) {
		if(closed) {
			throw new AlreadyClosedExceptionImpl(environment, "Publisher closed, can't lookup DataWriter");
		}
		
		for (AbstractDataWriter<?> writer : writers) {
			if(writer.getTopic() == topic) {
				return ( DataWriter<TYPE>) writer;
			}
		}
		
		return null;
	}

	@Override
	public void closeContainedEntities() {
		if(closed) {
			throw new AlreadyClosedExceptionImpl(environment, "Publisher closed, can't close contained DataWriter");
		}
		for (AbstractDataWriter<?> dataWriter : writers) {
			dataWriter.close();
		}
	}

	@Override
	public void suspendPublications() {
		throw new UnsupportedOperationExceptionImpl(environment, "NYI");		
	}

	@Override
	public void resumePublications() {
		throw new UnsupportedOperationExceptionImpl(environment, "NYI");		
	}

	@Override
	public void beginCoherentChanges() {
		throw new UnsupportedOperationExceptionImpl(environment, "NYI");		
	}

	@Override
	public void endCoherentChanges() {
		throw new UnsupportedOperationExceptionImpl(environment, "NYI");		
	}

	@Override
	public void waitForAcknowledgments(Duration maxWait) throws TimeoutException {
		throw new UnsupportedOperationExceptionImpl(environment, "NYI");		
	}

	@Override
	public void waitForAcknowledgments(long maxWait, TimeUnit unit) throws TimeoutException {
		throw new UnsupportedOperationExceptionImpl(environment, "NYI");		
	}

	@Override
	public DataWriterQos getDefaultDataWriterQos() {
		if(closed) {
			throw new AlreadyClosedExceptionImpl(environment, "Publisher closed, can't get default DataWriter Qos");
		}
		return defaultDataWriterQos;
	}

	@Override
	public void setDefaultDataWriterQos(DataWriterQos qos) {
		if(closed) {
			throw new AlreadyClosedExceptionImpl(environment, "Publisher closed, can't set default DataWriter Qos");
		}
		defaultDataWriterQos = qos;
	}

	@Override
	public DataWriterQos copyFromTopicQos(DataWriterQos dwQos, TopicQos tQos) {
		if(closed) {
			throw new AlreadyClosedExceptionImpl(environment, "Publisher closed, can't set default DataWriter Qos");
		}
		
		return dwQos.withPolicy(tQos.getDeadline())
				.withPolicy(tQos.getDestinationOrder())
				.withPolicy(tQos.getDurability())
				.withPolicy(tQos.getDurabilityService())
				.withPolicy(tQos.getHistory())
				.withPolicy(tQos.getLatencyBudget())
				.withPolicy(tQos.getLifespan())
				.withPolicy(tQos.getLiveliness())
				.withPolicy(tQos.getOwnership())
				.withPolicy(tQos.getReliability())
				.withPolicy(tQos.getResourceLimits())
				.withPolicy(tQos.getTransportPriority());
	}

	@Override
	public StatusCondition<Publisher> getStatusCondition() {
		throw new UnsupportedOperationExceptionImpl(environment, "NYI");
	}

	@Override
	public DomainParticipant getParent() {
		return parent;
	}

	@Override
	protected void destroy() {
		// TODO Call to JNA for destruction
	}

	public int getJnaPublisher() {
		return jnaPublisher;
	}
}






/*
 * 
 
 @Override
	public <TYPE> DataWriter<TYPE> createDataWriter(Topic<TYPE> topic) {
		return this.createDataWriter(topic, this.getDefaultDataWriterQos(),
				null, new HashSet<Class<? extends Status>>());
	}

	@Override
	public <TYPE> DataWriter<TYPE> createDataWriter(Topic<TYPE> topic,
			DataWriterQos qos, DataWriterListener<TYPE> listener,
			Collection<Class<? extends Status>> statuses) {
		AbstractDataWriter<TYPE> writer;
		AbstractTypeSupport<TYPE> typeSupport;

		if (topic == null) {
			throw new IllegalArgumentExceptionImpl(this.environment,
					"Supplied Topic is null.");
		}
		synchronized(this.writers){
			try {
				typeSupport = (AbstractTypeSupport<TYPE>) topic.getTypeSupport();
				writer = typeSupport.createDataWriter(this,
						(TopicImpl<TYPE>) topic, qos, listener, statuses);
			} catch (ClassCastException e) {
				throw new IllegalArgumentExceptionImpl(this.environment,
						"Cannot create DataWriter with non-OpenSplice Topic");
			}
			//TODO FRCYC this.writers.put(writer.getOld(), writer);
		}
		return writer;
	}

	@Override
	public <TYPE> DataWriter<TYPE> createDataWriter(Topic<TYPE> topic,
			DataWriterQos qos, DataWriterListener<TYPE> listener,
			Class<? extends Status>... statuses) {
		return createDataWriter(topic, qos, listener, Arrays.asList(statuses));
	}

	@Override
	public <TYPE> DataWriter<TYPE> createDataWriter(Topic<TYPE> topic,
			DataWriterQos qos) {
		return this.createDataWriter(topic, qos, null,
				new HashSet<Class<? extends Status>>());
	}

	public <TYPE> DataWriter<TYPE> lookupDataWriter(DataWriter writer) {
		DataWriter<?> dw;

		synchronized (this.writers) {
			dw = this.writers.get(writer);
		}
		if (dw != null) {
			return dw.cast();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <TYPE> DataWriter<TYPE> lookupDataWriter(String topicName) {
		if (topicName == null) {
			throw new IllegalArgumentExceptionImpl(this.environment,
					"Supplied topicName is null.");
		}
		synchronized (this.writers) {
			for (DataWriter<?> writer : this.writers.values()) {
				if (topicName.equals(writer.getTopic().getName())) {
					try {
						return (DataWriter<TYPE>) writer;
					} catch (ClassCastException e) {
						throw new IllegalOperationExceptionImpl(
								this.environment,
								"Cannot cast DataWriter to desired type.");
					}
				}
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <TYPE> DataWriter<TYPE> lookupDataWriter(Topic<TYPE> topic) {
		if (topic == null) {
			throw new IllegalArgumentExceptionImpl(this.environment,
					"Supplied Topic is null.");
		}
		synchronized (this.writers) {
			for (DataWriter<?> writer : this.writers.values()) {
				if (topic.equals(writer.getTopic())) {
					try {
						return (DataWriter<TYPE>) writer;
					} catch (ClassCastException e) {
						throw new IllegalOperationExceptionImpl(
								this.environment,
								"Cannot cast DataWriter to desired type.");
					}
				}
			}
		}
		return null;
	}

	@Override
	public void closeContainedEntities() {
		synchronized(this.writers){
			HashMap<DataWriter, DataWriter<?>> copyWriter = new HashMap<DataWriter, DataWriter<?>>(this.writers);
			for (DataWriter<?> writer : copyWriter.values()) {
				try {
					writer.close();
				} catch (AlreadyClosedException a) {
					 Entity may be closed concurrently by application 
				}
			}
		}
	}

	

	@Override
	public DataWriterQos copyFromTopicQos(DataWriterQos dwQos, TopicQos tQos) {
		DataWriterQosImpl result;

		if (tQos == null) {
			throw new IllegalArgumentExceptionImpl(this.environment,
					"Supplied TopicQos is null.");
		}
		if (dwQos == null) {
			throw new IllegalArgumentExceptionImpl(this.environment,
					"Supplied DataWriterQos is null.");
		}
		try {
			result = (DataWriterQosImpl) dwQos;
		} catch (ClassCastException e) {
			throw new IllegalArgumentExceptionImpl(this.environment,
					"Non-OpenSplice DataWriterQos not supported.");
		}
		result.mergeTopicQos(tQos);

		return result;
	}
 
 
 */
 
 




/* TODO FRCYC
private void setListener(PublisherListener listener, int mask) {
    PublisherListenerImpl wrapperListener;
    int rc;

    if (listener != null) {
        wrapperListener = new PublisherListenerImpl(this.environment, this,
                listener);
    } else {
        wrapperListener = null;
    }
    rc = this.getOld().set_listener(wrapperListener, mask);
    Utilities.checkReturnCode(rc, this.environment,
            "Publisher.setListener() failed.");

    this.listener = wrapperListener;
}

@Override
public void setListener(PublisherListener listener) {
    this.setListener(listener, StatusConverter.getAnyMask());
}

@Override
public void setListener(PublisherListener listener,
        Collection<Class<? extends Status>> statuses) {
    this.setListener(listener,
            StatusConverter.convertMask(this.environment, statuses));
}

@Override
public void setListener(PublisherListener listener,
        Class<? extends Status>... statuses) {
    this.setListener(listener,
            StatusConverter.convertMask(this.environment, statuses));
}

@Override
public PublisherQos getQos() {
    PublisherQosHolder holder = new PublisherQosHolder();
    int rc = this.getOld().get_qos(holder);
    Utilities.checkReturnCode(rc, this.environment,
            "Publisher.getQos() failed.");

    return PublisherQosImpl.convert(this.environment, holder.value);
}

@Override
public void setQos(PublisherQos qos) {
    PublisherQosImpl q;

    if (qos == null) {
        throw new IllegalArgumentExceptionImpl(this.environment,
                "Supplied PublisherQos is null.");
    }
    try {
        q = (PublisherQosImpl) qos;
    } catch (ClassCastException e) {
        throw new IllegalArgumentExceptionImpl(this.environment,
                "Setting non-OpenSplice Qos not supported.");
    }
    int rc = this.getOld().set_qos(q.convert());
    Utilities.checkReturnCode(rc, this.environment,
            "Publisher.setQos() failed.");

}
 */


/* TODO FRCYC
@Override
public void suspendPublications() {
    int rc = this.getOld().suspend_publications();
    Utilities.checkReturnCode(rc, this.environment,
            "Publisher.suspendPublications() failed.");

}

@Override
public void resumePublications() {
    int rc = this.getOld().resume_publications();
    Utilities.checkReturnCode(rc, this.environment,
            "Publisher.resumePublications() failed.");
}

@Override
public void beginCoherentChanges() {
    int rc = this.getOld().begin_coherent_changes();
    Utilities.checkReturnCode(rc, this.environment,
            "Publisher.beginCoherentChanges() failed.");
}

@Override
public void endCoherentChanges() {
    int rc = this.getOld().end_coherent_changes();
    Utilities.checkReturnCode(rc, this.environment,
            "Publisher.endCoherentChanges() failed.");
}

@Override
public void waitForAcknowledgments(Duration maxWait)
        throws TimeoutException {
    int rc = this.getOld().wait_for_acknowledgments(
            Utilities.convert(this.environment,
            maxWait));
    Utilities.checkReturnCodeWithTimeout(rc, this.environment,
            "Publisher.waitForAcknowledgments() failed.");
}

@Override
public void waitForAcknowledgments(long maxWait, TimeUnit unit)
        throws TimeoutException {
    this.waitForAcknowledgments(this.environment.getSPI().newDuration(
            maxWait, unit));
}

@Override
public DataWriterQos getDefaultDataWriterQos() {
    DataWriterQosHolder holder = new DataWriterQosHolder();
    int rc = this.getOld().get_default_datawriter_qos(holder);
    Utilities.checkReturnCode(rc, this.environment,
            "Publisher.getDefaultDataWriterQos() failed.");

    return DataWriterQosImpl.convert(this.environment, holder.value);
}

@Override
public void setDefaultDataWriterQos(DataWriterQos qos) {
    if (qos == null) {
        throw new IllegalArgumentExceptionImpl(this.environment,
                "Supplied DataWriterQos is null.");
    }
    try {
        this.getOld().set_default_datawriter_qos(
                ((DataWriterQosImpl) qos)
                .convert());
    } catch (ClassCastException e) {
        throw new IllegalArgumentExceptionImpl(this.environment,
                "Non-OpenSplice DataWriterQos not supported.");
    }

}
 */

/* TODO FRCYC
@Override
public StatusCondition<Publisher> getStatusCondition() {
    StatusCondition oldCondition = this.getOld().get_statuscondition();

    if (oldCondition == null) {
        Utilities.throwLastErrorException(this.environment);
    }
    return new StatusConditionImpl<Publisher>(this.environment,
            oldCondition, this);
}

@Override
public org.omg.dds.domain.DomainParticipant getParent() {
    return this.parent;
}

@Override
protected void destroy() {
    this.closeContainedEntities();
    this.parent.destroyPublisher(this);
}

public void destroyDataWriter(
        EntityImpl<DataWriter, ?, ?, ?, ?> dataWriter) {
    DataWriter old = dataWriter.getOld();
    int rc = this.getOld().delete_datawriter(old);
    synchronized(this.writers){
        this.writers.remove(old);
    }
    Utilities.checkReturnCode(rc, this.environment,
            "DataWriter.close() failed.");
}
 */
