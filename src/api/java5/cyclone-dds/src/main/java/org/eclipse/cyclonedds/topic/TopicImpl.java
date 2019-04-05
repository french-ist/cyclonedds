/**
  Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 
  This program and the accompanying materials are made available under the
  terms of the Eclipse Public License v. 2.0 which is available at
  http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
  v. 1.0 which is available at
  http://www.eclipse.org/org/documents/edl-v10.php.
 
  SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.cyclonedds.topic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.eclipse.cyclonedds.core.CycloneServiceEnvironment;
import org.eclipse.cyclonedds.core.DomainEntityImpl;
import org.eclipse.cyclonedds.core.IllegalArgumentExceptionImpl;
import org.eclipse.cyclonedds.core.IllegalOperationExceptionImpl;
import org.eclipse.cyclonedds.core.Utilities;
import org.eclipse.cyclonedds.core.status.AllDataDisposedStatus;
import org.eclipse.cyclonedds.ddsc.dds.DdscLibrary.dds_topic_descriptor_t;
import org.eclipse.cyclonedds.domain.DomainParticipantImpl;
import org.eclipse.cyclonedds.sub.DataReader;
import org.eclipse.cyclonedds.type.AbstractTypeSupport;
import org.omg.CORBA.portable.IDLEntity;
import org.omg.dds.core.InstanceHandle;
import org.omg.dds.core.StatusCondition;
import org.omg.dds.core.status.InconsistentTopicStatus;
import org.omg.dds.core.status.Status;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.topic.Topic;
import org.omg.dds.topic.TopicDescription;
import org.omg.dds.topic.TopicListener;
import org.omg.dds.topic.TopicQos;
import org.omg.dds.type.TypeSupport;

public class TopicImpl<TYPE> extends DomainEntityImpl<TopicQos, TopicListener<TYPE>, TopicListenerImpl<TYPE>> 
	implements org.eclipse.cyclonedds.topic.AbstractTopic<TYPE> {
	
	private List<DataReader<TYPE>> associatedReaders;
	protected  String topicName;
	protected AbstractTypeSupport<TYPE> typeSupport;
	protected TopicQos qos;
	protected Collection<Class<? extends Status>> statuses;
	
	private final int jnaTopic;
	private Topic<TYPE> topic;
    
	public TopicImpl(CycloneServiceEnvironment environment,
            DomainParticipantImpl participant, 
            String topicName,
            AbstractTypeSupport<TYPE> typeSupport, 
            TopicQos qos,
            TopicListener<TYPE> listener,
            Collection<Class<? extends Status>> statuses) {
        super(environment);
        this.topicName = topicName;
        this.typeSupport = typeSupport;
        this.qos = qos;        
        this.listener = (TopicListenerImpl<TYPE>) listener;
        this.statuses = statuses;
        associatedReaders = new ArrayList<DataReader<TYPE>>();
        
        UserClassHelper userClassHelperInstance = JnaUserClassFactory.getJnaUserClassHelperInstance(environment, this);
        
        jnaTopic = org.eclipse.cyclonedds.ddsc.dds.DdscLibrary.dds_create_topic(participant.getJnaParticipant(),
        		userClassHelperInstance.getDdsTopicDescriptor(topicName), 
				topicName, 
				Utilities.convert(environment, qos), 
				Utilities.convert(environment, listener));
        
        Utilities.checkReturnCode(
        		jnaTopic,
                environment,
                "Registration of Type with name '"
                        + this.typeSupport.getTypeName() + "' failed.");
        
        /* TODO FRCYC
        if (qos == null) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Supplied DataReaderQos is null.");
        }
        if (typeSupport == null) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Supplied TypeSupport is null.");
        }
        this.typeSupport = typeSupport;
        
        int rc = this.typeSupport.getOldTypeSupport().register_type(
                parent.getOld(), this.typeSupport.getTypeName());
        
        TopicQos oldQos;


        try {
            oldQos = ((TopicQosImpl) qos).convert();
        } catch (ClassCastException e) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Cannot create Topic with non-OpenSplice qos");
        }
*/

        if (listener != null) {
            this.listener = new TopicListenerImpl<TYPE>(this.environment, this,
                    listener, true);
        } else {
            this.listener = null;
        }
        
        /* TODO FRCYC
        Topic old = this.parent.getOld().create_topic(topicName,
                this.typeSupport.getTypeName(), oldQos, this.listener,
                StatusConverter.convertMask(this.environment, statuses));

        if (old == null) {
            Utilities.throwLastErrorException(this.environment);
        }
        this.setOld(old);
*/
        if (this.listener != null) {
            this.listener.setInitialised();
        }
    }
	
    @SuppressWarnings("unchecked")
    public TopicImpl(CycloneServiceEnvironment environment,
            DomainParticipantImpl participant, String topicName, Topic<TYPE> topic) {
        
    	super(environment);
        this.topicName = topicName;
        this.listener = (TopicListenerImpl<TYPE>) listener;
        this.topic = topic;        
        associatedReaders = new ArrayList<DataReader<TYPE>>();
        
        UserClassHelper typeDescription = JnaUserClassFactory.getJnaUserClassHelperInstance(environment, this);
        
        jnaTopic = org.eclipse.cyclonedds.ddsc.dds.DdscLibrary.dds_create_topic(participant.getJnaParticipant(),
        		typeDescription.getDdsTopicDescriptor(topicName), 
				topicName, 
				Utilities.convert(environment, qos), 
				Utilities.convert(environment, listener));
        
        Utilities.checkReturnCode(
        		jnaTopic,
                environment,
                "Registration of Type with name '"
                        + this.typeSupport.getTypeName() + "' failed.");
    	
    	/*
    	super(environment);
        this.listener = null;

        if (topicName == null) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Supplied Topic name is null.");
        }
        if (topic == null) {
            throw new IllegalArgumentExceptionImpl(environment,
                    "Invalid <null> Topic provided.");
        }
        /* TODO FRCYC
        this.setOld(old);

         
        try {
            AbstractTypeSupport<?> temp;

            if ("DCPSParticipant".equals(topicName)) {
                temp = new TypeSupportImpl<ParticipantBuiltinTopicData>(
                        this.environment,
                        ParticipantBuiltinTopicData.class, null);
            } else if ("DCPSTopic".equals(topicName)) {
                temp = new TypeSupportImpl<TopicBuiltinTopicData>(
                        this.environment, TopicBuiltinTopicData.class, null);
            } else if ("CMSubscriber".equals(topicName)) {
                temp = new TypeSupportImpl<CMSubscriberBuiltinTopicData>(
                        this.environment,
                        CMSubscriberBuiltinTopicData.class, null);
            } else if ("CMPublisher".equals(topicName)) {
                temp = new TypeSupportImpl<CMPublisherBuiltinTopicData>(
                        this.environment,
                        CMPublisherBuiltinTopicData.class, null);
            } else if ("CMParticipant".equals(topicName)) {
                temp = new TypeSupportImpl<CMParticipantBuiltinTopicData>(
                        this.environment,
                        CMParticipantBuiltinTopicData.class, null);
            } else if ("DCPSSubscription".equals(topicName)) {
                temp = new TypeSupportImpl<SubscriptionBuiltinTopicData>(
                        this.environment,
                        SubscriptionBuiltinTopicData.class, null);
            } else if ("CMDataReader".equals(topicName)) {
                temp = new TypeSupportImpl<CMDataReaderBuiltinTopicData>(
                        this.environment,
                        CMDataReaderBuiltinTopicData.class, null);
            } else if ("DCPSPublication".equals(topicName)) {
                temp = new TypeSupportImpl<PublicationBuiltinTopicData>(
                        this.environment,
                        PublicationBuiltinTopicData.class, null);
            } else if ("CMDataWriter".equals(topicName)) {
                temp = new TypeSupportImpl<CMDataWriterBuiltinTopicData>(
                        this.environment,
                        CMDataWriterBuiltinTopicData.class, null);
            } else if ("DCPSType".equals(topicName)) {
                temp = new TypeSupportImpl<TypeBuiltinTopicData>(
                        this.environment,
                        TypeBuiltinTopicData.class, null);
            } else {
                temp = null;
            }
            this.typeSupport = (AbstractTypeSupport<TYPE>) temp;
        } catch (ClassCastException cce) {
            this.typeSupport = null;
        }
        */
    }
   
    @SuppressWarnings("unchecked")
    @Override
    public <OTHER> TopicDescription<OTHER> cast() {
        TopicDescription<OTHER> other;

        try {
            other = (TopicDescription<OTHER>) this;
        } catch (ClassCastException cce) {
            throw new IllegalOperationExceptionImpl(this.environment,
                    "Unable to perform requested cast.");
        }
        return other;
    }

	@Override
	public void disposeAllData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AllDataDisposedStatus getAllDataDisposedTopicStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InconsistentTopicStatus getInconsistentTopicStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusCondition<Topic<TYPE>> getStatusCondition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DomainParticipant getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TypeSupport<TYPE> getTypeSupport() {
		return this.typeSupport;
	}

	@Override
	public String getTypeName() {
		return typeSupport.getTypeName();
	}

	@Override
	public String getName() {
		return topicName;
	}

	@Override
	public void setListener(TopicListener<TYPE> listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setListener(TopicListener<TYPE> listener, Collection<Class<? extends Status>> statuses) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setListener(TopicListener<TYPE> listener, Class<? extends Status>... statuses) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TopicQos getQos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setQos(TopicQos qos) {
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
	protected void destroy() {
		// TODO Auto-generated method stub
		
	}

	public int getJnaTopic() {
		return jnaTopic;
	}

   

    
    
    
    
    
    
    
    

}



/* TODO FRCYC
@Override
public void setListener(TopicListener<TYPE> listener) {
    this.setListener(listener, StatusConverter.getAnyMask());
}

@Override
public void setListener(TopicListener<TYPE> listener,
        Collection<Class<? extends Status>> statuses) {
    this.setListener(listener,
            StatusConverter.convertMask(this.environment, statuses));
}

@Override
public void setListener(TopicListener<TYPE> listener,
        Class<? extends Status>... statuses) {
    this.setListener(listener,
            StatusConverter.convertMask(this.environment, statuses));
}

@Override
public TopicQos getQos() {
    TopicQosHolder holder = new TopicQosHolder();
    int rc = this.getOld().get_qos(holder);
    Utilities.checkReturnCode(rc, this.environment,
            "Topic.getQos() failed.");

    return TopicQosImpl.convert(this.environment, holder.value);
}

@Override
public void setQos(TopicQos qos) {
    TopicQosImpl q;

    if (qos == null) {
        throw new IllegalArgumentExceptionImpl(this.environment,
                "Supplied TopicQos is null.");
    }
    try {
        q = (TopicQosImpl) qos;
    } catch (ClassCastException e) {
        throw new IllegalArgumentExceptionImpl(this.environment,
                "Setting non-OpenSplice Qos not supported.");
    }
    int rc = this.getOld().set_qos(q.convert());
    Utilities.checkReturnCode(rc, this.environment,
            "Topic.setQos() failed.");

}

@Override
public InconsistentTopicStatus getInconsistentTopicStatus() {
    InconsistentTopicStatusHolder holder = new InconsistentTopicStatusHolder();
    int rc = this.getOld().get_inconsistent_topic_status(holder);
    Utilities.checkReturnCode(rc, this.environment,
            "Topic.getInconsistentTopicStatus()");

    return StatusConverter.convert(this.environment, holder.value);
}

@Override
public StatusCondition<Topic<TYPE>> getStatusCondition() {
    StatusCondition oldCondition = this.getOld().get_statuscondition();

    if (oldCondition == null) {
        Utilities.throwLastErrorException(this.environment);
    }
    return new StatusConditionImpl<Topic<TYPE>>(this.environment,
            oldCondition, this);
}

@Override
protected void destroy() {
    this.parent.destroyTopic(this);

}
*/


/*
@Override
public String getName() {
    return this.getOld().get_name();
}

@Override
public DomainParticipant getParent() {
    return this.parent;
}

 TODO FRCYC
@Override
public void disposeAllData() {
    int rc = this.getOld().dispose_all_data();
    Utilities.checkReturnCode(rc, this.environment,
            "Topic.disposeAllData() failed.");
}

@Override
public AllDataDisposedStatus getAllDataDisposedTopicStatus() {
    AllDataDisposedTopicStatusHolder holder = new AllDataDisposedTopicStatusHolder();
    int rc = this.getOld().get_all_data_disposed_topic_status(holder);
    Utilities.checkReturnCode(rc, this.environment,
            "Topic.getAllDataDisposedTopicStatus()");

    return StatusConverter.convert(this.environment, holder.value);
}
*/

