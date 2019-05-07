/**
  Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 
  This program and the accompanying materials are made available under the
  terms of the Eclipse Public License v. 2.0 which is available at
  http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
  v. 1.0 which is available at
  http://www.eclipse.org/org/documents/edl-v10.php.
 
  SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.cyclonedds.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.eclipse.cyclonedds.core.AlreadyClosedExceptionImpl;
import org.eclipse.cyclonedds.core.ServiceEnvironmentImpl;
import org.eclipse.cyclonedds.core.EntityImpl;
import org.eclipse.cyclonedds.core.InstanceHandleImpl;
import org.eclipse.cyclonedds.core.PreconditionNotMetExceptionImpl;
import org.eclipse.cyclonedds.core.UnsupportedOperationExceptionImpl;
import org.eclipse.cyclonedds.core.Utilities;
import org.eclipse.cyclonedds.dcps.keys.KeyList;
//import org.eclipse.cyclonedds.dcps.MultiTopicImpl;
import org.eclipse.cyclonedds.pub.PublisherImpl;
import org.eclipse.cyclonedds.pub.PublisherQosImpl;
import org.eclipse.cyclonedds.sub.SubscriberImpl;
import org.eclipse.cyclonedds.sub.SubscriberQosImpl;
import org.eclipse.cyclonedds.topic.TopicQosImpl;
import org.eclipse.cyclonedds.type.AbstractTypeSupport;
import org.omg.dds.core.Duration;
import org.omg.dds.core.InstanceHandle;
import org.omg.dds.core.ModifiableTime;
import org.omg.dds.core.StatusCondition;
import org.omg.dds.core.Time;
import org.omg.dds.core.status.Status;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.domain.DomainParticipantListener;
import org.omg.dds.domain.DomainParticipantQos;
import org.omg.dds.pub.Publisher;
import org.omg.dds.pub.PublisherListener;
import org.omg.dds.pub.PublisherQos;
import org.omg.dds.sub.Subscriber;
import org.omg.dds.sub.SubscriberListener;
import org.omg.dds.sub.SubscriberQos;
import org.omg.dds.topic.ContentFilteredTopic;
import org.omg.dds.topic.MultiTopic;
import org.omg.dds.topic.ParticipantBuiltinTopicData;
import org.omg.dds.topic.Topic;
import org.omg.dds.topic.TopicBuiltinTopicData;
import org.omg.dds.topic.TopicDescription;
import org.omg.dds.topic.TopicListener;
import org.omg.dds.topic.TopicQos;
import org.omg.dds.type.TypeSupport;
import org.omg.dds.type.dynamic.DynamicType;

public class DomainParticipantImpl extends EntityImpl<DomainParticipantQos, DomainParticipantListener, DomainParticipantListenerImpl> implements DomainParticipant, org.eclipse.cyclonedds.domain.DomainParticipant {
    private final DomainParticipantFactoryImpl domainParticipanFactory;
    private final List<Topic<?>>  topics;
    private final List<Publisher> publishers;
    private final List<Subscriber> subscribers;

    private final int jnaParticipant;
	private TopicQos defaultTopicQoS;
	private int domainId;
	private DomainParticipantQos qos;
	private PublisherQosImpl defaultPublisherQos;
	private SubscriberQosImpl defaultSubscriberQos;
	private boolean enabled;
	private InstanceHandle handle;
    
    public DomainParticipantImpl(ServiceEnvironmentImpl environment,
            DomainParticipantFactoryImpl factory, 
            int domainId,
            DomainParticipantQos qos, 
            DomainParticipantListener listener,
            Collection<Class<? extends Status>> statuses) {
        super(environment);
        
        jnaParticipant = org.eclipse.cyclonedds.ddsc.dds.DdscLibrary.dds_create_participant (domainId, 
        		Utilities.convert(qos), 
        		Utilities.convert(listener));
        
        Utilities.checkReturnCode(jnaParticipant, environment, "Can't create JNA participant"); //TODO check if valid
        
        this.domainId = domainId;
        this.qos = qos;        
        defaultPublisherQos = new PublisherQosImpl(environment);
        defaultSubscriberQos = new SubscriberQosImpl(environment);
        defaultTopicQoS = new TopicQosImpl(environment);
        topics = Collections.synchronizedList(new ArrayList<Topic<?>>());
        publishers = Collections.synchronizedList(new ArrayList<Publisher>());
        subscribers = Collections.synchronizedList(new ArrayList<Subscriber>());
        handle = new InstanceHandleImpl(environment, jnaParticipant);
        
        //TODO set all buidling with the factory
        domainParticipanFactory = factory;
    }

	@Override
	public void setListener(DomainParticipantListener listener) {
		throw new UnsupportedOperationExceptionImpl(environment, "setListener(DomainParticipantListener listener)");
	}

	@Override
	public void setListener(DomainParticipantListener listener, Collection<Class<? extends Status>> statuses) {
		throw new UnsupportedOperationExceptionImpl(environment, "setListener(DomainParticipantListener listener, Collection<Class<? extends Status>> statuses)");		
	}

	@SuppressWarnings("unchecked") 
	@Override
	public void setListener(DomainParticipantListener listener, Class<? extends Status>... statuses) {
		throw new UnsupportedOperationExceptionImpl(environment, "setListener(DomainParticipantListener listener, Class<? extends Status>... statuses)");		
	}

	@Override
	public DomainParticipantQos getQos() {
		return qos;
	}

	@Override
	public void setQos(DomainParticipantQos qos) {
		this.qos = qos;
		throw new UnsupportedOperationExceptionImpl(environment, "TODO call JNA: setQos(DomainParticipantQos qos)");
	}

	@Override
	public void enable() {
		enabled = true;		
	}

	@Override
	public Set<Class<? extends Status>> getStatusChanges() {
		throw new UnsupportedOperationExceptionImpl(environment, "DomainParticipantImpl: public Set<Class<? extends Status>> getStatusChanges()");
	}

	@Override
	public InstanceHandle getInstanceHandle() {
		if(jnaParticipant > 0) {
			return handle;
		}
		throw new AlreadyClosedExceptionImpl(environment, "public InstanceHandle getInstanceHandle()"); 
	}

	@Override
	public void deleteHistoricalData(String partitionExpression, String topicExpression) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createPersistentSnapshot(String partitionExpression, String topicExpression, String uri) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setProperty(String key, String value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getProperty(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Publisher createPublisher() {		
		return createPublisher(getDefaultPublisherQos());
	}

	@Override
	public Publisher createPublisher(PublisherQos qos) {
		if(jnaParticipant > 0) {
			PublisherImpl pub =  new PublisherImpl(environment,this, qos, null, null);
			publishers.add(pub);
			if(qos.getEntityFactory().isAutoEnableCreatedEntities() && isEnabled()) {
				pub.enable();
			}
			return pub;	
		}
		throw new AlreadyClosedExceptionImpl(environment, "public Publisher createPublisher(PublisherQos qos)");
	}

	@Override
	public Publisher createPublisher(PublisherQos qos, PublisherListener listener,
			Collection<Class<? extends Status>> statuses) {
		if(jnaParticipant > 0) {
			Publisher pub =  new PublisherImpl(environment,this, qos, listener, statuses);
			publishers.add(pub);
		}
		throw new AlreadyClosedExceptionImpl(environment, "ublisher createPublisher(PublisherQos qos, PublisherListener listener, Collection<Class<? extends Status>> statuses");
	}

	@Override
	public Publisher createPublisher(PublisherQos qos, PublisherListener listener,
			Class<? extends Status>... statuses) {
		if(jnaParticipant > 0) {
			Publisher pub =  new PublisherImpl(environment,this, qos, listener, new ArrayList<Class<? extends Status>>(Arrays.asList(statuses)) );
			publishers.add(pub);
		}
		throw new AlreadyClosedExceptionImpl(environment, "ublisher createPublisher(PublisherQos qos, PublisherListener listener, Collection<Class<? extends Status>> statuses");
	}

	@Override
	public Subscriber createSubscriber() {
		return createSubscriber(getDefaultSubscriberQos());
	}

	@Override
	public Subscriber createSubscriber(SubscriberQos qos) {
		if(jnaParticipant > 0) {
			SubscriberImpl sub = new SubscriberImpl(environment, this, qos, null, null);
			subscribers.add(sub);
			if(qos.getEntityFactory().isAutoEnableCreatedEntities() && isEnabled()) {
				sub.enable();
			}
			return sub;	
		}
		throw new AlreadyClosedExceptionImpl(environment, "public Subscriber createSubscriber(SubscriberQos qos)");
	}

	@Override
	public Subscriber createSubscriber(SubscriberQos qos, SubscriberListener listener,
			Collection<Class<? extends Status>> statuses) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked") 
	@Override
	public Subscriber createSubscriber(SubscriberQos qos, SubscriberListener listener,
			Class<? extends Status>... statuses) {
		if(jnaParticipant > 0) {
			SubscriberImpl sub = new SubscriberImpl(environment, this, qos, listener, new ArrayList<Class<? extends Status>>(Arrays.asList(statuses)) );
			subscribers.add(sub);
			if(qos.getEntityFactory().isAutoEnableCreatedEntities() && isEnabled()) {
				sub.enable();
			}
			return sub;	
		}
		throw new AlreadyClosedExceptionImpl(environment, "public Subscriber createSubscriber(SubscriberQos qos)");
	}

	@Override
	public Subscriber getBuiltinSubscriber() {
		throw new UnsupportedOperationExceptionImpl(environment, "public Subscriber getBuiltinSubscriber()");
	}

	@Override
	public <TYPE> Topic<TYPE> createTopic(String topicName, Class<TYPE> type) {
		return createTopic(topicName, type, defaultTopicQoS, null, (Collection<Class<? extends Status>>)null);
	}

	@Override
	public <TYPE> Topic<TYPE> createTopic(String topicName, Class<TYPE> type, TopicQos qos,
			TopicListener<TYPE> listener, Collection<Class<? extends Status>> statuses) {
		TypeSupport<TYPE> typeSupport = TypeSupport.newTypeSupport(type, environment);		
		return createTopic(topicName, typeSupport, qos, listener, statuses);
	}

	@Override
	public <TYPE> Topic<TYPE> createTopic(String topicName, Class<TYPE> type, TopicQos qos,
			TopicListener<TYPE> listener, Class<? extends Status>... statuses) {
		return createTopic(topicName, type, qos, listener, Arrays.asList(statuses));
	}

	@Override
	public <TYPE> Topic<TYPE> createTopic(String topicName, TypeSupport<TYPE> type) {
		return createTopic(topicName, type, defaultTopicQoS, null, (Collection<Class<? extends Status>>)null);
	}

	@Override
	public <TYPE> Topic<TYPE> createTopic(String topicName, TypeSupport<TYPE> type, TopicQos qos,
			TopicListener<TYPE> listener, Collection<Class<? extends Status>> statuses) {

		if(jnaParticipant > 0) {
			if(type instanceof AbstractTypeSupport) {
				AbstractTypeSupport<TYPE> typeSupport = (AbstractTypeSupport<TYPE>) type;
				KeyList keyList = (KeyList) type.getType().getAnnotation(KeyList.class);
				Topic<TYPE> topic = typeSupport.createTopic(this, topicName, qos, listener, statuses);
				topics.add(topic);
				return topic;
			}
			throw new PreconditionNotMetExceptionImpl(environment, "Cannot create Cyclone Topic with non-cyclone TypeSupport");
		}
		throw new PreconditionNotMetExceptionImpl(environment, "Participant is closed, can't create topic");
	}

	@SuppressWarnings("unchecked") 
	@Override
	public <TYPE> Topic<TYPE> createTopic(String topicName, TypeSupport<TYPE> type, TopicQos qos,
			TopicListener<TYPE> listener, Class<? extends Status>... statuses) {
		return createTopic(topicName, type, qos, listener, Arrays.asList(statuses));
	}

	@Override
	public Topic<DynamicType> createTopic(String topicName, DynamicType type) {
		throw new UnsupportedOperationExceptionImpl(environment, "public Topic<DynamicType> createTopic(String topicName, DynamicType type)");
	}

	@Override
	public Topic<DynamicType> createTopic(String topicName, DynamicType type, TopicQos qos,
			TopicListener<DynamicType> listener, Collection<Class<? extends Status>> statuses) {
		throw new UnsupportedOperationExceptionImpl(environment, "NYI");
	}

	@Override
	public Topic<DynamicType> createTopic(String topicName, DynamicType type, TopicQos qos,
			TopicListener<DynamicType> listener, Class<? extends Status>... statuses) {
		return createTopic(topicName, type, qos, listener, Arrays.asList(statuses));
	}

	@Override
	public Topic<DynamicType> createTopic(String topicName, DynamicType type, TypeSupport<DynamicType> typeSupport) {
		throw new UnsupportedOperationExceptionImpl(environment, "NYI");
	}

	@Override
	public Topic<DynamicType> createTopic(String topicName, DynamicType type, TypeSupport<DynamicType> typeSupport,
			TopicQos qos, TopicListener<DynamicType> listener, Collection<Class<? extends Status>> statuses) {
		throw new UnsupportedOperationExceptionImpl(environment, "NYI");
	}

	@SuppressWarnings("unchecked") 
	@Override
	public Topic<DynamicType> createTopic(String topicName, DynamicType type, TypeSupport<DynamicType> typeSupport,
			TopicQos qos, TopicListener<DynamicType> listener, Class<? extends Status>... statuses) {
		return createTopic(topicName, type, typeSupport, qos, listener, Arrays.asList(statuses));
	}

	@Override
	public <TYPE> Topic<TYPE> findTopic(String topicName, Duration timeout) throws TimeoutException {
		throw new UnsupportedOperationExceptionImpl(environment, "NYI");
	}

	@Override
	public <TYPE> Topic<TYPE> findTopic(String topicName, long timeout, TimeUnit unit) throws TimeoutException {
		throw new UnsupportedOperationExceptionImpl(environment, "NYI");
	}

	@SuppressWarnings("unchecked") 
	@Override
	public <TYPE> TopicDescription<TYPE> lookupTopicDescription(String name) {
		TopicDescription<TYPE> topicDescription = null;
		for(Topic<?> topic:topics) {
			if(topic.getName().equals(name)) {
				try {
					topicDescription = (TopicDescription<TYPE>)topic;
					return topicDescription;
				} catch (Exception e) {
					//TODO put a logger
					return null;
				}
			}
		}
		return null;
	}

	@Override
	public <TYPE> ContentFilteredTopic<TYPE> createContentFilteredTopic(String name, Topic<? extends TYPE> relatedTopic,
			String filterExpression, List<String> expressionParameters) {
		throw new UnsupportedOperationExceptionImpl(environment, "NYI");
	}

	@Override
	public <TYPE> ContentFilteredTopic<TYPE> createContentFilteredTopic(String name, Topic<? extends TYPE> relatedTopic,
			String filterExpression, String... expressionParameters) {
		throw new UnsupportedOperationExceptionImpl(environment, "NYI");
	}
	

	@Override
	public <TYPE> MultiTopic<TYPE> createMultiTopic(String name, Class<TYPE> type, String subscriptionExpression,
			List<String> expressionParameters) {
		throw new UnsupportedOperationExceptionImpl(environment, "NYI");
	}

	@Override
	public <TYPE> MultiTopic<TYPE> createMultiTopic(String name, Class<TYPE> type, String subscriptionExpression,
			String... expressionParameters) {
		throw new UnsupportedOperationExceptionImpl(environment, "NYI");
	}

	@Override
	public <TYPE> MultiTopic<TYPE> createMultiTopic(String name, TypeSupport<TYPE> type, String subscriptionExpression,
			List<String> expressionParameters) {
		throw new UnsupportedOperationExceptionImpl(environment, "NYI");
	}

	@Override
	public <TYPE> MultiTopic<TYPE> createMultiTopic(String name, TypeSupport<TYPE> type, String subscriptionExpression,
			String... expressionParameters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void closeContainedEntities() {
		if(jnaParticipant>0) {
			for (Publisher pub : publishers) {
				pub.close();
			}			
			
			for (Subscriber sub : subscribers) {
				sub.close();
			}
			
			for(Topic<?> topic: topics) {
				topic.close();
			}
			
		} else {
			throw new AlreadyClosedExceptionImpl(environment, "jna participant not valid");
		}
	}

	@Override
	public void ignoreParticipant(InstanceHandle handle) {
		throw new UnsupportedOperationExceptionImpl(environment, "NYI");		
	}

	@Override
	public void ignoreTopic(InstanceHandle handle) {
		throw new UnsupportedOperationExceptionImpl(environment, "NYI");
	}

	@Override
	public void ignorePublication(InstanceHandle handle) {
		throw new UnsupportedOperationExceptionImpl(environment, "NYI");
	}

	@Override
	public void ignoreSubscription(InstanceHandle handle) {
		throw new UnsupportedOperationExceptionImpl(environment, "NYI");
	}

	@Override
	public int getDomainId() {
		if(jnaParticipant>0) {
			return domainId;
		}
		throw new AlreadyClosedExceptionImpl(environment, "jna participant not valid");
	}

	@Override
	public void assertLiveliness() {
		throw new UnsupportedOperationExceptionImpl(environment, "NYI");		
	}

	@Override
	public PublisherQos getDefaultPublisherQos() {
		if(jnaParticipant > 0) {
			return defaultPublisherQos;
		}
		throw new AlreadyClosedExceptionImpl(environment, "public PublisherQos getDefaultPublisherQos()");
	}

	@Override
	public void setDefaultPublisherQos(PublisherQos qos) {
		if(jnaParticipant > 0) {
			defaultPublisherQos = (PublisherQosImpl) qos;
		} else { 
			throw new AlreadyClosedExceptionImpl(environment, "public void setDefaultPublisherQos(PublisherQos qos)");
		}	
	}

	@Override
	public SubscriberQos getDefaultSubscriberQos() {
		if(jnaParticipant > 0) {
			return defaultSubscriberQos;
		}
		throw new AlreadyClosedExceptionImpl(environment, "public SubscriberQos getDefaultSubscriberQos()");
	}

	@Override
	public void setDefaultSubscriberQos(SubscriberQos qos) {
		if(jnaParticipant > 0) {
			defaultSubscriberQos = (SubscriberQosImpl) qos;
		} else {			
			throw new AlreadyClosedExceptionImpl(environment, "public SubscriberQos getDefaultSubscriberQos()");
		}		
	}

	@Override
	public TopicQos getDefaultTopicQos() {
		if(jnaParticipant > 0) {
			return defaultTopicQoS;
		}
		throw new AlreadyClosedExceptionImpl(environment, "public TopicQos getDefaultTopicQos() ");
	}

	@Override
	public void setDefaultTopicQos(TopicQos qos) {
		if(jnaParticipant > 0) {
			defaultTopicQoS = (TopicQosImpl) qos;
		} else {			
			throw new AlreadyClosedExceptionImpl(environment, "public void setDefaultTopicQos(TopicQos qos)");
		}
		
	}

	@Override
	public Set<InstanceHandle> getDiscoveredParticipants() {
		throw new UnsupportedOperationExceptionImpl(environment, "NYI");
	}

	@Override
	public ParticipantBuiltinTopicData getDiscoveredParticipantData(InstanceHandle participantHandle) {
		throw new UnsupportedOperationExceptionImpl(environment, "NYI");
	}

	@Override
	public Set<InstanceHandle> getDiscoveredTopics() {
		throw new UnsupportedOperationExceptionImpl(environment, "NYI");
	}

	@Override
	public TopicBuiltinTopicData getDiscoveredTopicData(InstanceHandle topicHandle) {
		throw new UnsupportedOperationExceptionImpl(environment, "NYI");
	}

	@Override
	public boolean containsEntity(InstanceHandle handle) {
		throw new UnsupportedOperationExceptionImpl(environment, "NYI");
	}

	@Override
	public ModifiableTime getCurrentTime(ModifiableTime currentTime) {
		throw new UnsupportedOperationExceptionImpl(environment, "NYI");
	}

	@Override
	public Time getCurrentTime() {
		throw new UnsupportedOperationExceptionImpl(environment, "NYI");
	}

	@Override
	public StatusCondition<DomainParticipant> getStatusCondition() {
		throw new UnsupportedOperationExceptionImpl(environment, "NYI");
	}

	@Override
	protected void destroy() {
		// TODO Auto-generated method stub		
	}

	public int getJnaParticipant() {		
		return jnaParticipant;
	}

	public boolean isEnabled() {
		return enabled;
	}

}














