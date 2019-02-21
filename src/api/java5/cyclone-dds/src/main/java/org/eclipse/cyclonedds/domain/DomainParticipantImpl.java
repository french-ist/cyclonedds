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
package org.eclipse.cyclonedds.domain;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.omg.dds.core.AlreadyClosedException;
import org.omg.dds.core.Duration;
import org.omg.dds.core.InstanceHandle;
import org.omg.dds.core.ModifiableTime;
import org.omg.dds.core.StatusCondition;
import org.omg.dds.core.Time;
import org.omg.dds.core.status.Status;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.domain.DomainParticipantListener;
import org.omg.dds.domain.DomainParticipantQos;
import org.omg.dds.pub.DataWriter;
import org.omg.dds.pub.Publisher;
import org.omg.dds.pub.PublisherListener;
import org.omg.dds.pub.PublisherQos;
import org.omg.dds.sub.DataReader;
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
import org.eclipse.cyclonedds.core.AlreadyClosedExceptionImpl;
import org.eclipse.cyclonedds.core.EntityImpl;
import org.eclipse.cyclonedds.core.IllegalArgumentExceptionImpl;
import org.eclipse.cyclonedds.core.InstanceHandleImpl;
import org.eclipse.cyclonedds.core.ModifiableTimeImpl;
import org.eclipse.cyclonedds.core.CycloneServiceEnvironment;
import org.eclipse.cyclonedds.core.PreconditionNotMetExceptionImpl;
import org.eclipse.cyclonedds.core.StatusConditionImpl;
import org.eclipse.cyclonedds.core.TimeImpl;
import org.eclipse.cyclonedds.core.UnsupportedOperationExceptionImpl;
import org.eclipse.cyclonedds.core.Utilities;
import org.eclipse.cyclonedds.core.status.StatusConverter;
//import org.eclipse.cyclonedds.dcps.MultiTopicImpl;
import org.eclipse.cyclonedds.pub.PublisherImpl;
import org.eclipse.cyclonedds.pub.PublisherQosImpl;
import org.eclipse.cyclonedds.sub.SubscriberImpl;
import org.eclipse.cyclonedds.sub.SubscriberQosImpl;
import org.eclipse.cyclonedds.topic.AbstractTopic;
import org.eclipse.cyclonedds.topic.ContentFilteredTopicImpl;
import org.eclipse.cyclonedds.topic.ParticipantBuiltinTopicDataImpl;
import org.eclipse.cyclonedds.topic.TopicBuiltinTopicDataImpl;
import org.eclipse.cyclonedds.topic.TopicDescriptionExt;
import org.eclipse.cyclonedds.topic.TopicImpl;
import org.eclipse.cyclonedds.topic.TopicQosImpl;
import org.eclipse.cyclonedds.type.AbstractTypeSupport;

/* TODO FRCYC
import DomainParticipantQosHolder;
import Time_tHolder;
*/
public class DomainParticipantImpl
        extends
        EntityImpl<DomainParticipant, DomainParticipantFactory, DomainParticipantQos, DomainParticipantListener, DomainParticipantListenerImpl>
        implements DomainParticipant, org.eclipse.cyclonedds.domain.DomainParticipant {
    private final DomainParticipantFactoryImpl factory;
    private final HashMap<TopicDescription, TopicDescriptionExt<?>> topics;
    private final HashMap<Publisher, PublisherImpl> publishers;
    private final HashMap<Subscriber, SubscriberImpl> subscribers;

    public DomainParticipantImpl(CycloneServiceEnvironment environment,
            DomainParticipantFactoryImpl factory, int domainId,
            DomainParticipantQos qos, DomainParticipantListener listener,
            Collection<Class<? extends Status>> statuses) {
        //TODO FRCYC super(environment, DomainParticipantFactory.get_instance());
    	super(environment, null);
        this.factory = factory;
        this.topics = new HashMap<TopicDescription, TopicDescriptionExt<?>>();
        this.publishers = new HashMap<Publisher, PublisherImpl>();
        this.subscribers = new HashMap<Subscriber, SubscriberImpl>();

        if (qos == null) {
            throw new IllegalArgumentExceptionImpl(environment,
                    "Supplied DomainParticipantQos is null.");
        }

        DomainParticipantQos oldQos;

        /*
        try {
            oldQos = ((DomainParticipantQosImpl) qos).convert();
        } catch (ClassCastException e) {
            throw new IllegalArgumentExceptionImpl(environment,
                    "Cannot create participant with non-OpenSplice qos");
        }
        if (listener != null) {
            this.listener = new DomainParticipantListenerImpl(this.environment,
                    this, listener, true);
        } else {
            this.listener = null;
        }
         TODO FRCYC
        DomainParticipant old = this.getOldParent().create_participant(
                domainId,
                oldQos,
                this.listener,
                StatusConverter.convertMask(this.environment, statuses));

        if (old == null) {
            Utilities.throwLastErrorException(this.environment);
        }
        this.setOld(old);

        if (this.listener != null) {
            this.listener.setInitialised();
        }
        */
    }

    @SuppressWarnings("unchecked")
    public <TYPE> Topic<TYPE> getTopic(Topic oldTopic) {
        synchronized (this.topics) {
            return (Topic<TYPE>) this.topics.get(oldTopic);
        }
    }
    
    /* TODO FRCYC
    private void setListener(
            org.omg.dds.domain.DomainParticipantListener listener, int mask) {
        DomainParticipantListenerImpl wrapperListener;
        int rc;

        if (listener != null) {
            wrapperListener = new DomainParticipantListenerImpl(
                    this.environment, this, listener);
        } else {
            wrapperListener = null;
        }

        rc = this.getOld().set_listener(wrapperListener, mask);

        Utilities.checkReturnCode(rc, this.environment,
                "DomainParticipant.setListener() failed.");

        this.listener = wrapperListener;
    }

    
    @Override
    public DomainParticipantQos getQos() {
        DomainParticipantQosHolder holder;
        int rc;

        holder = new DomainParticipantQosHolder();
        rc = this.getOld().get_qos(holder);
        Utilities.checkReturnCode(rc, this.environment,
                "DomainParticipant.getQos() failed.");

        return DomainParticipantQosImpl.convert(this.environment, holder.value);
    }
    

    @Override
    public void setQos(DomainParticipantQos qos) {
        DomainParticipantQos oldQos;
        int rc;

        if (qos == null) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Supplied DomainParticipantQos is null.");
        }
        try {
            oldQos = ((DomainParticipantQosImpl) qos).convert();
        } catch (ClassCastException e) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Setting non-OpenSplice Qos not supported.");
        }
        
        rc = this.getOld().set_qos(oldQos);
        Utilities.checkReturnCode(rc, this.environment,
                "DomainParticipant.setQos() failed.");
    }
    */

    @Override
    public Publisher createPublisher() {
        return this.createPublisher(this.getDefaultPublisherQos());
    }

    @Override
    public Publisher createPublisher(PublisherQos qos) {
        return this.createPublisher(qos, null,
                new HashSet<Class<? extends Status>>());
    }

    @Override
    public Publisher createPublisher(PublisherQos qos,
            PublisherListener listener,
            Collection<Class<? extends Status>> statuses) {
        PublisherImpl publisher;

        synchronized (this.publishers) {
            publisher = new PublisherImpl(this.environment, this, qos,
                    listener, statuses);
            this.publishers.put(publisher.getOld(), publisher);
        }
        return publisher;
    }

    @Override
    public Publisher createPublisher(PublisherQos qos,
            PublisherListener listener, Class<? extends Status>... statuses) {
        return createPublisher(qos, listener, Arrays.asList(statuses));
    }

    @Override
    public Subscriber createSubscriber() {
        return this.createSubscriber(this.getDefaultSubscriberQos());
    }

    @Override
    public Subscriber createSubscriber(SubscriberQos qos) {
        return this.createSubscriber(qos, null,
                new HashSet<Class<? extends Status>>());
    }

    @Override
    public Subscriber createSubscriber(SubscriberQos qos,
            SubscriberListener listener,
            Collection<Class<? extends Status>> statuses) {
        SubscriberImpl subscriber;

        synchronized (this.subscribers) {
            subscriber = new SubscriberImpl(this.environment, this, qos,
                    listener, statuses);
            this.subscribers.put(subscriber.getOld(), subscriber);
        }
        return subscriber;
    }

    @Override
    public Subscriber createSubscriber(SubscriberQos qos,
            SubscriberListener listener, Class<? extends Status>... statuses) {
        return createSubscriber(qos, listener, Arrays.asList(statuses));
    }

    @Override
    public Subscriber getBuiltinSubscriber() {
        SubscriberImpl result = null;

        synchronized (this.subscribers) {
            /* TODO FRCYC
        	Subscriber old = this.getOld().get_builtin_subscriber();

            if (old == null) {
                Utilities.throwLastErrorException(this.environment);
            }
            result = this.subscribers.get(old);

            if (result == null) {
                result = new SubscriberImpl(this.environment, this, old);
                this.subscribers.put(old, result);
            }*/
        }
        return result;
    }

    @Override
    public <TYPE> Topic<TYPE> createTopic(String topicName, Class<TYPE> type) {
        return this.createTopic(topicName, type, this.getDefaultTopicQos(),
                null, new HashSet<Class<? extends Status>>());
    }

    @Override
    public <TYPE> Topic<TYPE> createTopic(String topicName, Class<TYPE> type,
            TopicQos qos, TopicListener<TYPE> listener,
            Collection<Class<? extends Status>> statuses) {
        AbstractTopic<TYPE> topic;

        AbstractTypeSupport<TYPE> typeSupport = (AbstractTypeSupport<TYPE>) this.environment
                .getSPI().newTypeSupport(type, null);

        synchronized (this.topics) {
            topic = typeSupport.createTopic(this, topicName, qos, listener,
                    statuses);
            this.topics.put(topic.getOld(), topic);
        }
        return topic;
    }

    @Override
    public <TYPE> Topic<TYPE> createTopic(String topicName, Class<TYPE> type,
            TopicQos qos, TopicListener<TYPE> listener,
            Class<? extends Status>... statuses) {
        return createTopic(topicName, type, qos, listener,
                Arrays.asList(statuses));
    }

    @Override
    public <TYPE> Topic<TYPE> createTopic(String topicName,
            TypeSupport<TYPE> type) {
        return this.createTopic(topicName, type, this.getDefaultTopicQos(),
                null, new HashSet<Class<? extends Status>>());
    }

    @Override
    public <TYPE> Topic<TYPE> createTopic(String topicName,
            TypeSupport<TYPE> type, TopicQos qos, TopicListener<TYPE> listener,
            Collection<Class<? extends Status>> statuses) {
        AbstractTopic<TYPE> topic;

        synchronized (this.topics) {
            topic = ((AbstractTypeSupport<TYPE>) type).createTopic(this,
                    topicName,
                    qos, listener, statuses);
            this.topics.put(topic.getOld(), topic);
        }
        return topic;
    }

    @Override
    public <TYPE> Topic<TYPE> createTopic(String topicName,
            TypeSupport<TYPE> type, TopicQos qos, TopicListener<TYPE> listener,
            Class<? extends Status>... statuses) {
        return createTopic(topicName, type, qos, listener,
                Arrays.asList(statuses));
    }

    @Override
    public Topic<DynamicType> createTopic(String topicName, DynamicType type) {
        return this.createTopic(topicName, type, this.getDefaultTopicQos(),
                null, new HashSet<Class<? extends Status>>());
    }

    @Override
    public Topic<DynamicType> createTopic(String topicName, DynamicType type,
            TopicQos qos, TopicListener<DynamicType> listener,
            Collection<Class<? extends Status>> statuses) {
        throw new UnsupportedOperationExceptionImpl(this.environment,
                "Dynamic types have not been implemented yet.");
    }

    @Override
    public Topic<DynamicType> createTopic(String topicName, DynamicType type,
            TopicQos qos, TopicListener<DynamicType> listener,
            Class<? extends Status>... statuses) {
        throw new UnsupportedOperationExceptionImpl(this.environment,
                "Dynamic types have not been implemented yet.");
    }

    @Override
    public Topic<DynamicType> createTopic(String topicName, DynamicType type,
            TypeSupport<DynamicType> typeSupport) {
        return this.createTopic(topicName, type, typeSupport,
                this.getDefaultTopicQos(), null,
                new HashSet<Class<? extends Status>>());
    }

    @Override
    public Topic<DynamicType> createTopic(String topicName, DynamicType type,
            TypeSupport<DynamicType> typeSupport, TopicQos qos,
            TopicListener<DynamicType> listener,
            Collection<Class<? extends Status>> statuses) {
        throw new UnsupportedOperationExceptionImpl(this.environment,
                "Dynamic types have not been implemented yet.");
    }

    @Override
    public Topic<DynamicType> createTopic(String topicName, DynamicType type,
            TypeSupport<DynamicType> typeSupport, TopicQos qos,
            TopicListener<DynamicType> listener,
            Class<? extends Status>... statuses) {
        throw new UnsupportedOperationExceptionImpl(this.environment,
                "Dynamic types have not been implemented yet.");
    }

    @SuppressWarnings("unchecked")
    @Override
    public <TYPE> Topic<TYPE> findTopic(String topicName, Duration timeout)
            throws TimeoutException {
        TopicImpl<TYPE> result = null;
        /*TODO FRCYC
         * Topic old = this.getOld().find_topic(topicName,
                Utilities.convert(this.environment, timeout));

        if (old != null) {
            try {
                synchronized (this.topics) {
                    result = (TopicImpl<TYPE>) this.topics.get(old);

                    if (result == null) {
                        result = new TopicImpl<TYPE>(this.environment, this,
                                topicName, old);
                        this.topics.put(old, result);
                    }
                }
            } catch (ClassCastException e) {
                throw new IllegalArgumentExceptionImpl(this.environment,
                        "Type of Topic does not match provided Type.");
            }
        }
        */
        return result;
    }

    @Override
    public <TYPE> Topic<TYPE> findTopic(String topicName, long timeout,
            TimeUnit unit) throws TimeoutException {
        return this.findTopic(topicName,
                this.environment.getSPI().newDuration(timeout, unit));
    }

    @Override
    public <TYPE> TopicDescription<TYPE> lookupTopicDescription(String name) {
        TopicDescription<TYPE> td = null;

        if (name != null) {
            synchronized (this.topics) {
                for (TopicDescriptionExt<?> topic : this.topics.values()) {
                    if (topic.getName().equals(name)) {
                        td = topic.cast();
                    }
                }
                /*TODO FRCYC
                if (td == null) {
                    TopicDescription builtinTopic = this.getOld()
                            .lookup_topicdescription(name);

                    if (builtinTopic != null) {

                        try {
                            TopicImpl<TYPE> wrapper = new TopicImpl<TYPE>(
                                    this.environment, this, name,
                                    (Topic) builtinTopic);

                            this.topics.put(builtinTopic, wrapper);
                            td = wrapper;
                        } catch (ClassCastException cce) {
                            /* Ignore this 
                        }
                    }
                }*/
            }
        }
        return td;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <TYPE> ContentFilteredTopic<TYPE> createContentFilteredTopic(
            String name, Topic<? extends TYPE> relatedTopic,
            String filterExpression, List<String> expressionParameters) {
        ContentFilteredTopic<TYPE> result = null;
        try {
            ContentFilteredTopicImpl<TYPE> cfTopic = null;

            synchronized (this.topics) {
                cfTopic = new ContentFilteredTopicImpl<TYPE>(
                        this.environment, this, name,
                        (AbstractTopic<TYPE>) relatedTopic, filterExpression,
                        expressionParameters);
                this.topics.put(cfTopic.getOld(), cfTopic);
            }
            result = cfTopic;
        } catch (ClassCastException e) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Cannot create ContentFilteredTopic which relates to non-OpenSplice Topic.");
        }
        return result;
    }

    @Override
    public <TYPE> ContentFilteredTopic<TYPE> createContentFilteredTopic(
            String name, Topic<? extends TYPE> relatedTopic,
            String filterExpression, String... expressionParameters) {
        if (expressionParameters.length == 0) {
            return createContentFilteredTopic(name, relatedTopic,
                    filterExpression, (List<String>) null);
        }
        return createContentFilteredTopic(name, relatedTopic, filterExpression,
                Arrays.asList(expressionParameters));
    }

    @Override
    public <TYPE> MultiTopic<TYPE> createMultiTopic(String name,
            Class<TYPE> type, String subscriptionExpression,
            List<String> expressionParameters) {
        throw new UnsupportedOperationExceptionImpl(this.environment,
                "MultiTopics have not been implemented yet.");
    }

    @Override
    public <TYPE> MultiTopic<TYPE> createMultiTopic(String name,
            TypeSupport<TYPE> type, String subscriptionExpression,
            List<String> expressionParameters) {
        throw new UnsupportedOperationExceptionImpl(this.environment,
                "MultiTopics have not been implemented yet.");
    }

    @Override
    public <TYPE> MultiTopic<TYPE> createMultiTopic(String name,
            Class<TYPE> type, String subscriptionExpression,
            String... expressionParameters) {
        throw new UnsupportedOperationExceptionImpl(this.environment,
                "MultiTopics have not been implemented yet.");
    }

    @Override
    public <TYPE> MultiTopic<TYPE> createMultiTopic(String name,
            TypeSupport<TYPE> type, String subscriptionExpression,
            String... expressionParameters) {
        throw new UnsupportedOperationExceptionImpl(this.environment,
                "MultiTopics have not been implemented yet.");
    }

    @Override
    public void closeContainedEntities() {
        synchronized (this.publishers) {
            HashMap<Publisher, PublisherImpl> copyPub = new HashMap<Publisher, PublisherImpl>(this.publishers);
            for (PublisherImpl publisher : copyPub.values()) {
                try{
                    publisher.close();
                } catch (AlreadyClosedException a) {
                    /* Entity may be closed concurrently by application */
                }
            }
        }
        synchronized (this.subscribers) {
            HashMap<Subscriber, SubscriberImpl> copySub = new HashMap<Subscriber, SubscriberImpl>(this.subscribers);
            for (SubscriberImpl subscriber : copySub.values()) {
                try {
                    subscriber.close();
                } catch (AlreadyClosedException a) {
                    /* Entity may be closed concurrently by application */
                }
            }
        }

        /*
         * Topics cannot be deleted in case ContentFilteredTopic or MultiTopic
         * entities still refer to them, so close the latter two first.
         */
        synchronized (this.topics) {
            HashMap<TopicDescription, TopicDescriptionExt<?>> copyTop = new HashMap<TopicDescription, TopicDescriptionExt<?>>(this.topics);
            for (TopicDescriptionExt<?> topic : copyTop.values()) {
                try {
                    if (topic instanceof ContentFilteredTopicImpl) {
                        topic.close();
                    } /* TODO FRCYC else if (topic instanceof MultiTopicImpl) {
                        topic.close();
                    } */
                } catch (AlreadyClosedException a) {
                    /* Entity may be closed concurrently by application */
                }
            }
            copyTop = new HashMap<TopicDescription, TopicDescriptionExt<?>>(this.topics);
            for (TopicDescriptionExt<?> topic : copyTop.values()) {
                try {
                    topic.close();
                } catch (AlreadyClosedException a) {
                    /* Entity may be closed concurrently by application */
                }
            }
        }
    }

    @Override
    public void ignoreParticipant(InstanceHandle handle) {
        try {
        	/* TODO FRCYC
            int rc = this.getOld().ignore_participant(
                    Utilities.convert(
                    this.environment, handle));
            Utilities.checkReturnCode(rc, environment,
                    "DomainParticipant.ignoreParticipant() failed");
                    */
        } catch (ClassCastException cce) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Usage of non OpenSplice InstanceHandle not supported.");
        }
    }

    @Override
    public void ignoreTopic(InstanceHandle handle) {
        try {
        	/* TODO FRCYC
            int rc = this.getOld().ignore_topic(
                    Utilities.convert(this.environment,
                    handle));

            Utilities.checkReturnCode(rc, environment,
                    "DomainParticipant.ignoreTopic() failed");
                    */
        } catch (ClassCastException cce) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Usage of non OpenSplice InstanceHandle not supported.");
        }
    }

    @Override
    public void ignorePublication(InstanceHandle handle) {
        try { /* TODO FRCYC
            int rc = this.getOld().ignore_publication(
                    Utilities.convert(
                    this.environment, handle));

            Utilities.checkReturnCode(rc, environment,
                    "DomainParticipant.ignorePublication() failed");
                    */
        } catch (ClassCastException cce) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Usage of non OpenSplice InstanceHandle not supported.");
        }
    }

    @Override
    public void ignoreSubscription(InstanceHandle handle) {
        try {
        	/* TODO FRCYC
            int rc = this.getOld().ignore_subscription(
                    Utilities.convert(
                    this.environment, handle));

            Utilities.checkReturnCode(rc, environment,
                    "DomainParticipant.ignoreSubscription() failed");
                    */
        } catch (ClassCastException cce) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Usage of non OpenSplice InstanceHandle not supported.");
        }
    }

    /* TODO FRCYC
    
    @Override
    public int getDomainId() {
        return this.getOld().get_domain_id();
    }

    @Override
    public void assertLiveliness() {
        int rc = this.getOld().assert_liveliness();
        Utilities.checkReturnCode(rc, this.environment,
                "DomainParticipant.assertLiveliness() failed.");

    }

    @Override
    public PublisherQos getDefaultPublisherQos() {
        PublisherQosHolder holder = new PublisherQosHolder();
        int rc = this.getOld().get_default_publisher_qos(holder);
        Utilities.checkReturnCode(rc, this.environment,
                "DomainParticipant.getDefaultPublisherQos() failed.");
        return PublisherQosImpl.convert(this.environment, holder.value);
    }

    @Override
    public void setDefaultPublisherQos(PublisherQos qos) {
        if (qos == null) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Supplied PublisherQos is null.");
        }
        try {
            this.getOld().set_default_publisher_qos(
                    ((PublisherQosImpl) qos)
                    .convert());
        } catch (ClassCastException e) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Non-OpenSplice PublisherQos not supported.");
        }
    }

    @Override
    public SubscriberQos getDefaultSubscriberQos() {
        SubscriberQosHolder holder = new SubscriberQosHolder();
        int rc = this.getOld().get_default_subscriber_qos(holder);
        Utilities.checkReturnCode(rc, this.environment,
                "DomainParticipant.getDefaultSubscriberQos() failed.");
        return SubscriberQosImpl.convert(this.environment, holder.value);
    }

    @Override
    public void setDefaultSubscriberQos(SubscriberQos qos) {
        if (qos == null) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Supplied SubscriberQos is null.");
        }
        try {
            this.getOld().set_default_subscriber_qos(
                    ((SubscriberQosImpl) qos)
                    .convert());
        } catch (ClassCastException e) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Non-OpenSplice SubscriberQos not supported.");
        }
    }

    @Override
    public TopicQos getDefaultTopicQos() {
        TopicQosHolder holder = new TopicQosHolder();
        int rc = this.getOld().get_default_topic_qos(holder);
        Utilities.checkReturnCode(rc, this.environment,
                "DomainParticipant.getDefaultTopicQos() failed.");

        return TopicQosImpl.convert(this.environment, holder.value);
    }

    @Override
    public void setDefaultTopicQos(TopicQos qos) {
        if (qos == null) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Supplied TopicQos is null.");
        }
        try {
            this.getOld().set_default_topic_qos(((TopicQosImpl) qos).convert());
        } catch (ClassCastException e) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Non-OpenSplice TopicQos not supported.");
        }
    }

    @Override
    public Set<InstanceHandle> getDiscoveredParticipants() {
        InstanceHandleSeqHolder holder = new InstanceHandleSeqHolder();
        int rc = this.getOld().get_discovered_participants(holder);

        Utilities.checkReturnCode(rc, this.environment,
                "DomainParticipant.getDiscoveredParticipants() failed.");

        HashSet<InstanceHandle> handles = new HashSet<InstanceHandle>(
                holder.value.length);

        for (long handle : holder.value) {
            handles.add(new InstanceHandleImpl(this.environment, handle));
        }
        return handles;
    }

    @Override
    public ParticipantBuiltinTopicData getDiscoveredParticipantData(
            InstanceHandle participantHandle) {
        ParticipantBuiltinTopicDataHolder holder = new ParticipantBuiltinTopicDataHolder();
        int rc = this.getOld().get_discovered_participant_data(holder,
                Utilities.convert(this.environment, participantHandle));
        Utilities.checkReturnCode(rc, this.environment,
                "DomainParticipant.getDiscoveredParticipantData() failed.");
        if (holder.value != null) {
            return new ParticipantBuiltinTopicDataImpl(this.environment,
                    holder.value);
        }
        throw new PreconditionNotMetExceptionImpl(this.environment,
                    "No data for this instanceHandle.");

    }

    @Override
    public Set<InstanceHandle> getDiscoveredTopics() {
        InstanceHandleSeqHolder holder = new InstanceHandleSeqHolder();
        int rc = this.getOld().get_discovered_topics(holder);

        Utilities.checkReturnCode(rc, this.environment,
                "DomainParticipant.getDiscoveredTopics() failed.");

        HashSet<InstanceHandle> handles = new HashSet<InstanceHandle>(
                holder.value.length);

        for (long handle : holder.value) {
            handles.add(new InstanceHandleImpl(this.environment, handle));
        }
        return handles;
    }

    @Override
    public TopicBuiltinTopicData getDiscoveredTopicData(
            InstanceHandle topicHandle) {
        TopicBuiltinTopicDataHolder holder = new TopicBuiltinTopicDataHolder();
        int rc = this.getOld().get_discovered_topic_data(holder,
                Utilities.convert(this.environment, topicHandle));
        Utilities.checkReturnCode(rc, this.environment,
                "DomainParticipant.getDiscoveredTopicData() failed.");
        if (holder.value != null) {
            return new TopicBuiltinTopicDataImpl(this.environment, holder.value);
        }
        throw new PreconditionNotMetExceptionImpl(this.environment,
                    "No data for this instanceHandle.");
    }

    @Override
    public boolean containsEntity(InstanceHandle handle) {
        return this.getOld().contains_entity(
                Utilities.convert(this.environment,
                handle));
    }

    @Override
    public ModifiableTime getCurrentTime(ModifiableTime currentTime) {
        ModifiableTime result;
        Time_tHolder holder = new Time_tHolder();
        int rc = this.getOld().get_current_time(holder);
        Utilities.checkReturnCode(rc, this.environment,
                "DomainParticipant.getCurrentTime() failed.");

        if (currentTime == null) {
            result = new ModifiableTimeImpl(this.environment, holder.value.sec,
                    holder.value.nanosec);
        } else {
            currentTime.copyFrom(new TimeImpl(this.environment,
                    holder.value.sec, holder.value.nanosec));
            result = currentTime;
        }
        return result;
    }

    @Override
    public Time getCurrentTime() {
        Time_tHolder holder = new Time_tHolder();
        int rc = this.getOld().get_current_time(holder);
        Utilities.checkReturnCode(rc, this.environment,
                "DomainParticipant.getCurrentTime() failed.");

        return new TimeImpl(this.environment, holder.value.sec,
                holder.value.nanosec);
    }

    @Override
    public StatusCondition<DomainParticipant> getStatusCondition() {
        StatusCondition oldCondition = this.getOld().get_statuscondition();

        if (oldCondition == null) {
            Utilities.throwLastErrorException(this.environment);
        }
        return new StatusConditionImpl<DomainParticipant>(this.environment,
                oldCondition, this);
    }

    @Override
    protected void destroy() {
        this.closeContainedEntities();
        this.factory.destroyParticipant(this);
    }

    @Override
    public void setListener(
            org.omg.dds.domain.DomainParticipantListener listener) {
        this.setListener(listener, StatusConverter.getAnyMask());

    }

    @Override
    public void setListener(
            org.omg.dds.domain.DomainParticipantListener listener,
            Collection<Class<? extends Status>> statuses) {
        this.setListener(listener,
                StatusConverter.convertMask(this.environment, statuses));
    }

    @Override
    public void setListener(DomainParticipantListener listener,
            Class<? extends Status>... statuses) {
        this.setListener(listener,
                StatusConverter.convertMask(this.environment, statuses));
    }
    */

    public <TYPE> DataWriter<TYPE> lookupDataWriter(DataWriter old) {
        DataWriter<TYPE> writer;

        synchronized (this.publishers) {
            for (PublisherImpl p : this.publishers.values()) {
                writer = p.lookupDataWriter(old);

                if (writer != null) {
                    return writer;
                }
            }
        }
        return null;
    }

    public <TYPE> DataReader<TYPE> lookupDataReader(DataReader classic) {
        DataReader<TYPE> reader;
        boolean seenBuiltin = false;

        synchronized (this.subscribers) {
            for (SubscriberImpl s : this.subscribers.values()) {
                reader = s.lookupDataReader(classic);

                if (reader != null) {
                    return reader;
                }
                if (s.isBuiltin()) {
                    seenBuiltin = true;
                }
            }
            if (!seenBuiltin) {
                SubscriberImpl sub = (SubscriberImpl) this
                        .getBuiltinSubscriber();

                if (sub != null) {
                    return sub.lookupDataReader(classic);
                }
            }
        }
        return null;
    }

    public org.omg.dds.sub.Subscriber lookupSubscriber(Subscriber subs) {
        SubscriberImpl subscriber;
        SubscriberImpl builtinSub;

        synchronized (this.subscribers) {
            subscriber = this.subscribers.get(subs);

            if (subscriber == null) {
                /*
                 * If subscriber is unknown it'll have to be the built-in
                 * subscriber
                 */
                builtinSub = (SubscriberImpl) this.getBuiltinSubscriber();

                if (builtinSub != null) {
                    subscriber = builtinSub;
                }
            }
        }
        return subscriber;
    }

	@Override
	public void setListener(DomainParticipantListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setListener(DomainParticipantListener listener, Collection<Class<? extends Status>> statuses) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setListener(DomainParticipantListener listener, Class<? extends Status>... statuses) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public DomainParticipantQos getQos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setQos(DomainParticipantQos qos) {
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
	public int getDomainId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void assertLiveliness() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PublisherQos getDefaultPublisherQos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDefaultPublisherQos(PublisherQos qos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SubscriberQos getDefaultSubscriberQos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDefaultSubscriberQos(SubscriberQos qos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TopicQos getDefaultTopicQos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDefaultTopicQos(TopicQos qos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<InstanceHandle> getDiscoveredParticipants() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ParticipantBuiltinTopicData getDiscoveredParticipantData(InstanceHandle participantHandle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<InstanceHandle> getDiscoveredTopics() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TopicBuiltinTopicData getDiscoveredTopicData(InstanceHandle topicHandle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean containsEntity(InstanceHandle handle) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ModifiableTime getCurrentTime(ModifiableTime currentTime) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Time getCurrentTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusCondition<DomainParticipant> getStatusCondition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void destroy() {
		// TODO Auto-generated method stub
		
	}

    /*TODO FRCYC
    public void destroyPublisher(PublisherImpl child) {
        Publisher old = child.getOld();
        old.delete_contained_entities();
        int rc = this.getOld().delete_publisher(old);
        synchronized (this.publishers) {
            this.publishers.remove(old);
        }
        Utilities.checkReturnCode(rc, this.environment,
                "Publisher.close() failed.");
    }

    public void destroySubscriber(SubscriberImpl child) {
        Subscriber old = child.getOld();
        old.delete_contained_entities();
        int rc = this.getOld().delete_subscriber(old);
        synchronized (this.subscribers) {
            this.subscribers.remove(old);
        }
        Utilities.checkReturnCode(rc, this.environment,
                "Subscriber.close() failed.");
    }

    public <TYPE> void destroyTopic(TopicDescriptionExt<TYPE> child) {
        TopicDescription old = child.getOld();
        int rc = this.getOld().delete_topic((Topic) old);
        synchronized (this.topics) {
            this.topics.remove(old);
        }
        Utilities
                .checkReturnCode(rc, this.environment, "Topic.close() failed.");
    }

    public <TYPE> void destroyContentFilteredTopic(
            ContentFilteredTopicImpl<TYPE> child) {
        TopicDescription old = child.getOld();
        synchronized (this.topics) {
            TopicDescriptionExt<?> removed = this.topics.remove(old);
            if (removed == null) {
                throw new AlreadyClosedExceptionImpl(this.environment,
                        "ContentFilteredTopic already closed.");
            }
        }
        int rc = this.getOld().delete_contentfilteredtopic(
                (ContentFilteredTopic) old);
        Utilities.checkReturnCode(rc, this.environment,
                "ContentFilteredTopic.close() failed.");
        child.getRelatedTopic().close();

    }

    @Override
    public void deleteHistoricalData(String partitionExpression,String topicExpression) {
        int rc = this.getOld().delete_historical_data(partitionExpression, topicExpression);
        Utilities.checkReturnCode(rc, this.environment,"deleteHistoricalData operation failed.");
    }

    @Override
    public void createPersistentSnapshot(String partitionExpression,String topicExpression,String uri) {
        Domain domain = this.getOldParent().lookup_domain(this.getDomainId());
        if (domain != null) {
            int rc = domain.create_persistent_snapshot(partitionExpression, topicExpression, uri);
            Utilities.checkReturnCode(rc, this.environment,"createPersistenSnapshot operation failed.");
        } else {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Invalid domain used.");
        }
    }

    @Override
    public void setProperty(String key, String value) {
        int rc = this.getOld().set_property(new Property(key, value));
        Utilities.checkReturnCode(rc, this.environment,
                "Properties.setProperty() failed.");
    }

    @Override
    public String getProperty(String key) {
        PropertyHolder holder = new PropertyHolder();
        holder.value = new Property(key, null);
        int rc = this.getOld().get_property(holder);
        Utilities.checkReturnCode(rc, this.environment,
                "Properties.getProperty() failed.");
        return holder.value.value;
    }
    */
}
