package unittest.domain;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.dds.core.AlreadyClosedException;
import org.omg.dds.core.Duration;
import org.omg.dds.core.InstanceHandle;
import org.omg.dds.core.ModifiableTime;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.StatusCondition;
import org.omg.dds.core.Time;
import org.omg.dds.core.policy.Durability;
import org.omg.dds.core.policy.EntityFactory;
import org.omg.dds.core.policy.PolicyFactory;
import org.omg.dds.core.policy.QosPolicy.ForDomainParticipant;
import org.omg.dds.core.policy.UserData;
import org.omg.dds.core.status.DataAvailableStatus;
import org.omg.dds.core.status.Status;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.domain.DomainParticipantFactory;
import org.omg.dds.domain.DomainParticipantListener;
import org.omg.dds.domain.DomainParticipantQos;
import org.omg.dds.pub.Publisher;
import org.omg.dds.pub.PublisherListener;
import org.omg.dds.pub.PublisherQos;
import org.omg.dds.sub.Subscriber;
import org.omg.dds.sub.SubscriberListener;
import org.omg.dds.sub.SubscriberQos;
import org.omg.dds.topic.ContentFilteredTopic;
import org.omg.dds.topic.ParticipantBuiltinTopicData;
import org.omg.dds.topic.Topic;
import org.omg.dds.topic.TopicBuiltinTopicData;
import org.omg.dds.topic.TopicDescription;
import org.omg.dds.topic.TopicListener;
import org.omg.dds.topic.TopicQos;
import org.omg.dds.type.TypeSupport;

import Test.Msg;

import org.eclipse.cyclonedds.test.AbstractUtilities;
import org.eclipse.cyclonedds.test.DomainParticipantListenerClass;
import org.eclipse.cyclonedds.test.PublisherListenerClass;
import org.eclipse.cyclonedds.test.SubscriberListenerClass;
import org.eclipse.cyclonedds.test.TopicListenerClass;


public class DomainParticipantTest {

    private final static int                DOMAIN_ID = 123;
    private static ServiceEnvironment       env;
    private static DomainParticipantFactory dpf;
    private static DomainParticipant        participant;
    private static AbstractUtilities        util      = AbstractUtilities.getInstance(DomainParticipantTest.class);
    private final static Properties         prop      = new Properties();

    @BeforeClass
    public static void init() {
        try {
            String serviceEnvironment = System.getProperty("JAVA5PSM_SERVICE_ENV");
            assertTrue("Check for valid ServiceEnvironment string", !serviceEnvironment.equals(""));
            System.setProperty(ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY, serviceEnvironment);
            env = ServiceEnvironment.createInstance(DomainParticipantTest.class.getClassLoader());
            assertTrue("Check for valid ServiceEnvironment object", env instanceof ServiceEnvironment);
            dpf = DomainParticipantFactory.getInstance(env);
            assertTrue("Check for valid DomainParticipantFactory object", dpf instanceof DomainParticipantFactory);
            prop.setProperty("domainid", new Integer(DOMAIN_ID).toString());
            assertTrue("Check is deamon is correctly started", util.beforeClass(prop));
            try {
                DomainParticipantQos dpq = dpf.getDefaultParticipantQos();
                byte[] ud = new String("DomainParticipantTest").getBytes();
                dpq = dpq.withPolicies(dpq.getPolicyFactory().UserData().withValue(ud, 0, ud.length));
                dpf.setDefaultParticipantQos(dpq);

            } catch (Exception e) {
                fail("Exception occured while setting the domainParticipant name in the userdata field: " + util.printException(e));
            }
            participant = dpf.createParticipant(DOMAIN_ID);
            assertTrue("Check for valid DomainParticipant object", participant instanceof DomainParticipant);
            assertTrue("Check for valid DomainParticipant with id " + DOMAIN_ID, participant.getDomainId() == DOMAIN_ID);
        } catch (Exception e) {
            fail("Exception occured while initiating the DomainParticipantTest class: " + util.printException(e));
        }

    }

    @AfterClass
    public static void cleanup() {
        try {
            participant.closeContainedEntities();
        } catch (AlreadyClosedException e) {
            /* ignore */
        }
        try {
            participant.close();
        } catch (AlreadyClosedException e) {
            /* ignore */
        }
        assertTrue("Check is deamon is correctly stopped", util.afterClass(prop));
    }


    /**
     * Test method for {@link org.omg.dds.domain.DomainParticipant#createPublisher()}.
     */
    @Test
    public void testCreatePublisher() {
        Publisher pub = null;
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        try {
            pub = participant.createPublisher();
        } catch (Exception e) {
            fail("Exception occured while creating a publisher: " + util.printException(e));
        }
        assertTrue("Check for valid Publisher object", pub != null);
        pub.close();
    }

    /**
     * Test method for {@link org.omg.dds.domain.DomainParticipant#createPublisher(org.omg.dds.pub.PublisherQos)}.
     */
    @Test
    public void testCreatePublisherPublisherQos() {
        Publisher pub = null;
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        PublisherQos pq = participant.getDefaultPublisherQos();
        assertTrue("Check for valid PublisherQos object", pq instanceof PublisherQos);
        try {
            pub = participant.createPublisher(pq);
        } catch (Exception e) {
            fail("Exception occured while creating a publisher with qos: " + util.printException(e));
        }
        assertTrue("Check for valid Publisher object", pub != null);
        pub.close();
    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipant#createPublisher(org.omg.dds.pub.PublisherQos)}
     * .
     */
    @Test
    public void testCreatePublisherPublisherQosNull() {
        Publisher pub = null;
        PublisherQos pq = null;
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        try {
            pub = participant.createPublisher(pq);
        } catch (Exception e) {
            assertTrue("Check for IllegalArgumentException but got exception:" + util.printException(e), e instanceof IllegalArgumentException);
        }
        assertTrue("Check for invalid Publisher object", pub == null);
    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipant#createPublisher(org.omg.dds.pub.PublisherQos, org.omg.dds.pub.PublisherListener, java.util.Collection)}
     * .
     */
    @Test
    public void testCreatePublisherPublisherQosPublisherListenerCollectionOfClassOfQextendsStatus() {
        Publisher pub = null;
        PublisherListener listener = new PublisherListenerClass();
        Collection<Class<? extends Status>> statuses = new HashSet<Class<? extends Status>>();
        statuses.add(DataAvailableStatus.class);
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        PublisherQos pq = participant.getDefaultPublisherQos();
        assertTrue("Check for valid PublisherQos object", pq instanceof PublisherQos);
        try {
            pub = participant.createPublisher(pq, listener, statuses);
        } catch (Exception e) {
            assertTrue("Exception occured while creating a publisher with qos, listener and status col: " + util.printException(e),
                    util.exceptionCheck("testCreatePublisherPublisherQosPublisherListenerCollectionOfClassOfQextendsStatus", e, false));
        }
        assertTrue("Check for valid Publisher object", util.objectCheck("testCreatePublisherPublisherQosPublisherListenerCollectionOfClassOfQextendsStatus", pub));
        if (pub != null) {
            pub.close();
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipant#createPublisher(org.omg.dds.pub.PublisherQos, org.omg.dds.pub.PublisherListener, java.util.Collection)}
     * .
     */
    @Test
    public void testCreatePublisherPublisherQosPublisherListenerCollectionOfClassOfQextendsStatusNull() {
        Publisher pub = null;
        PublisherListener listener = null;
        Collection<Class<? extends Status>> statuses = null;
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        PublisherQos pq = participant.getDefaultPublisherQos();
        assertTrue("Check for valid PublisherQos object", pq instanceof PublisherQos);
        try {
            pub = participant.createPublisher(pq, listener, statuses);
        } catch (Exception e) {
            assertTrue("Exception occured while creating a publisher with qos, listener and status col: " + util.printException(e),
                    util.exceptionCheck("testCreatePublisherPublisherQosPublisherListenerCollectionOfClassOfQextendsStatusNull", e, false));
        }
        assertTrue("Check for valid Publisher object", util.objectCheck("testCreatePublisherPublisherQosPublisherListenerCollectionOfClassOfQextendsStatusNull", pub));
        if (pub != null) {
            pub.close();
        }
    }

    /**
     * Test method for {@link
     * org.omg.dds.domain.DomainParticipant#createPublisher(org.omg.dds.pub.
     * PublisherQos, org.omg.dds.pub.PublisherListener, java.lang.Class<?
     * extends org.omg.dds.core.status.Status>[])}.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testCreatePublisherPublisherQosPublisherListenerClassOfQextendsStatusArray() {
        Publisher pub = null;
        PublisherListener listener = null;
        Class<? extends Status> status = DataAvailableStatus.class;
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        PublisherQos pq = participant.getDefaultPublisherQos();
        assertTrue("Check for valid PublisherQos object", pq instanceof PublisherQos);
        try {
            pub = participant.createPublisher(pq, listener, status);
        } catch (Exception e) {
            assertTrue("Exception occured while creating a publisher with qos, listener and status: " + util.printException(e),
                    util.exceptionCheck("testCreatePublisherPublisherQosPublisherListenerClassOfQextendsStatusArray", e, false));
        }
        assertTrue("Check for valid Publisher object", util.objectCheck("testCreatePublisherPublisherQosPublisherListenerClassOfQextendsStatusArray", pub));
        if (pub != null) {
            pub.close();
        }
    }

    /**
     * Test method for {@link
     * org.omg.dds.domain.DomainParticipant#createPublisher(org.omg.dds.pub.
     * PublisherQos, org.omg.dds.pub.PublisherListener, java.lang.Class<?
     * extends org.omg.dds.core.status.Status>[])}.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testCreatePublisherPublisherQosPublisherListenerClassOfQextendsStatusArrayNull() {
        Publisher pub = null;
        PublisherListener listener = null;
        Class<? extends Status> status = null;
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        PublisherQos pq = participant.getDefaultPublisherQos();
        assertTrue("Check for valid PublisherQos object", pq instanceof PublisherQos);
        try {
            pub = participant.createPublisher(pq, listener, status);
        } catch (Exception e) {
            assertTrue("Exception occured while creating a publisher with qos, listener and status: " + util.printException(e),
                    util.exceptionCheck("testCreatePublisherPublisherQosPublisherListenerClassOfQextendsStatusArray", e, false));
        }
        assertTrue("Check for valid Publisher object", util.objectCheck("testCreatePublisherPublisherQosPublisherListenerClassOfQextendsStatusArray", pub));
        if (pub != null) {
            pub.close();
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipant#createSubscriber()}.
     */
    @Test
    public void testCreateSubscriber() {
        Subscriber sub = null;
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        try {
            sub = participant.createSubscriber();
        } catch (Exception e) {
            fail("Exception occured while creating a subscriber: " + util.printException(e));
        }
        assertTrue("Check for valid Subscriber object", sub != null);
        sub.close();
    }

    /**
     * Test method for {@link org.omg.dds.domain.DomainParticipant#createSubscriber(org.omg.dds.sub.SubscriberQos)}.
     */
    @Test
    public void testCreateSubscriberSubscriberQos() {
        Subscriber sub = null;
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        SubscriberQos sq = participant.getDefaultSubscriberQos();
        assertTrue("Check for validSubscriberQos object", sq instanceof SubscriberQos);
        try {
            sub = participant.createSubscriber(sq);
        } catch (Exception e) {
            fail("Exception occured while creating a Subscriber with qos: " + util.printException(e));
        }
        assertTrue("Check for valid Subscriber object", sub != null);
        sub.close();
    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipant#createSubscriber(org.omg.dds.sub.SubscriberQos)}
     * .
     */
    @Test
    public void testCreateSubscriberSubscriberQosNull() {
        Subscriber sub = null;
        SubscriberQos sq = null;
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        try {
            sub = participant.createSubscriber(sq);
        } catch (Exception e) {
            assertTrue("Check for IllegalArgumentException but got exception:" + util.printException(e), e instanceof IllegalArgumentException);
        }
        assertTrue("Check for invalid Subscriber object", sub == null);
    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipant#createSubscriber(org.omg.dds.sub.SubscriberQos, org.omg.dds.sub.SubscriberListener, java.util.Collection)}
     * .
     */
    @Test
    public void testCreateSubscriberSubscriberQosSubscriberListenerCollectionOfClassOfQextendsStatus() {
        Subscriber sub = null;
        SubscriberListener listener = new SubscriberListenerClass();
        Collection<Class<? extends Status>> statuses = new HashSet<Class<? extends Status>>();
        statuses.add(DataAvailableStatus.class);
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        SubscriberQos sq = participant.getDefaultSubscriberQos();
        assertTrue("Check for valid SubscriberQos object", sq instanceof SubscriberQos);
        try {
            sub = participant.createSubscriber(sq, listener, statuses);
        } catch (Exception e) {
            assertTrue("Exception occured while creating a Subscriber with qos, listener and status col: " + util.printException(e),
                    util.exceptionCheck("testCreateSubscriberSubscriberQosSubscriberListenerCollectionOfClassOfQextendsStatus", e, false));
        }
        assertTrue("Check for valid Subscriber object", util.objectCheck("testCreateSubscriberSubscriberQosSubscriberListenerCollectionOfClassOfQextendsStatus", sub));
        if (sub != null) {
            sub.close();
        }
    }

    /**
     * Test method for {@link org.omg.dds.domain.DomainParticipant#createSubscriber(org.omg.dds.sub.SubscriberQos, org.omg.dds.sub.SubscriberListener, java.lang.Class<? extends org.omg.dds.core.status.Status>[])}.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testCreateSubscriberSubscriberQosSubscriberListenerClassOfQextendsStatusArray() {
        Subscriber sub = null;
        SubscriberListener listener = null;
        Class<? extends Status> status = DataAvailableStatus.class;
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        SubscriberQos sq = participant.getDefaultSubscriberQos();
        assertTrue("Check for valid SubscriberQos object", sq instanceof SubscriberQos);
        try {
            sub = participant.createSubscriber(sq, listener, status);
        } catch (Exception e) {
            assertTrue("Exception occured while creating a Subscriber with qos, listener and status col: " + util.printException(e),
                    util.exceptionCheck("testCreateSubscriberSubscriberQosSubscriberListenerClassOfQextendsStatusArray", e, false));
        }
        assertTrue("Check for valid Subscriber object", util.objectCheck("testCreateSubscriberSubscriberQosSubscriberListenerClassOfQextendsStatusArray", sub));
        if (sub != null) {
            sub.close();
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipant#getBuiltinSubscriber()}.
     */
    @Test
    public void testGetBuiltinSubscriber() {
        Subscriber sub = null;
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        try {
            sub = participant.getBuiltinSubscriber();
        } catch (Exception e) {
            fail("Exception occured while doing getBuiltinSubscriber: " + e.getMessage());
        }
        assertTrue("Check for valid Subscriber object", sub != null);
        sub.close();
    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipant#createTopic(java.lang.String, java.lang.Class)}
     * .
     */
    @Test
    public void testCreateTopicStringClassOfTYPE() {
        Topic<Msg> t = null;
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        try {
            t = participant.createTopic("Topic1", Msg.class);
        } catch (Exception e) {
            fail("Exception occured while doing createTopic(string,type): " + util.printException(e));
        }
        assertTrue("Check for valid Topic object", t != null);
        assertTrue("Check for correct Topic name", t.getName().equals("Topic1"));
        t.close();

    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipant#createTopic(java.lang.String, java.lang.Class)}
     * .
     */
    /* fails for opensplice see OSPL-5543 */
    @Test
    public void testCreateTopicStringClassOfTYPENull() {
        Topic<Msg> t = null;
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        try {
            t = participant.createTopic(null, Msg.class);
        } catch (Exception e) {
            assertTrue("Check for IllegalArgumentException but got exception: " + util.printException(e), e instanceof IllegalArgumentException);
        }
        assertTrue("Check for valid Topic object", t == null);

    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipant#createTopic(java.lang.String, java.lang.Class, org.omg.dds.topic.TopicQos, org.omg.dds.topic.TopicListener, java.util.Collection)}
     * .
     */
    @Test
    public void testCreateTopicStringClassOfTYPETopicQosTopicListenerOfTYPECollectionOfClassOfQextendsStatus() {
        Topic<Msg> t = null;
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        TopicQos tq = participant.getDefaultTopicQos();
        assertTrue("Check for valid TopicQos object", tq instanceof TopicQos);
        TopicListener<Msg> listener = new TopicListenerClass();
        Collection<Class<? extends Status>> statuses = new HashSet<Class<? extends Status>>();
        statuses.add(DataAvailableStatus.class);

        try {
            t = participant.createTopic("Topic2", Msg.class, tq, listener, statuses);
        } catch (Exception e) {
            assertTrue("Exception occured while doing createTopic(string,type,qos,listener,statuses): " + util.printException(e),
                    util.exceptionCheck("testCreateTopicStringClassOfTYPETopicQosTopicListenerOfTYPECollectionOfClassOfQextendsStatus", e, false));
        }
        if (t != null) {
            assertTrue("Check for valid Topic object", t != null);
            assertTrue("Check for correct Topic name", t.getName().equals("Topic2"));
            t.close();
        }
    }

    /**
     * Test method for {@link
     * org.omg.dds.domain.DomainParticipant#createTopic(java.lang.String,
     * java.lang.Class, org.omg.dds.topic.TopicQos,
     * org.omg.dds.topic.TopicListener, java.lang.Class<? extends
     * org.omg.dds.core.status.Status>[])}.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testCreateTopicStringClassOfTYPETopicQosTopicListenerOfTYPEClassOfQextendsStatusArray() {

        Topic<Msg> t = null;
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        TopicQos tq = participant.getDefaultTopicQos();
        assertTrue("Check for valid TopicQos object", tq instanceof TopicQos);
        TopicListener<Msg> listener = null;
        Class<? extends Status> status = DataAvailableStatus.class;

        try {
            t = participant.createTopic("Topic3", Msg.class, tq, listener, status);
        } catch (Exception e) {
            fail("Exception occured while doing createTopic(string,type,qos,listener,status): " + util.printException(e));
        }
        assertTrue("Check for valid Topic object", t != null);
        assertTrue("Check for correct Topic name", t.getName().equals("Topic3"));
        t.close();
    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipant#createTopic(java.lang.String, org.omg.dds.type.TypeSupport)}
     * .
     */
    @Test
    public void testCreateTopicStringTypeSupportOfTYPE() {
        Topic<Msg> t = null;
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        TypeSupport<Msg> typeSupport = TypeSupport.newTypeSupport(Msg.class, "testCreateTopicStringTypeSupportOfTYPE", env);
        assertTrue("Check for TypeSupport object", typeSupport instanceof TypeSupport);
        try {
            t = participant.createTopic("Topic4", typeSupport);
        } catch (Exception e) {
            fail("Exception occured while doing createTopic(string,typesupport): " + util.printException(e));
        }
        assertTrue("Check for valid Topic object", t != null);
        assertTrue("Check for correct Topic name", t.getName().equals("Topic4"));
        t.close();
    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipant#createTopic(java.lang.String, org.omg.dds.type.TypeSupport)}
     * .
     */
    /* fails for opensplice see OSPL-5543 */
    @Test
    public void testCreateTopicStringTypeSupportOfTYPENull() {
        Topic<Msg> t = null;
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        TypeSupport<Msg> typeSupport = TypeSupport.newTypeSupport(Msg.class, "testCreateTopicStringTypeSupportOfTYPE", env);
        assertTrue("Check for TypeSupport object", typeSupport instanceof TypeSupport);
        try {
            t = participant.createTopic(null, typeSupport);
        } catch (Exception e) {
            assertTrue("Check for IllegalArgumentException but got exception: " + util.printException(e), e instanceof IllegalArgumentException);
        }
        assertTrue("Check for valid Topic object", t == null);
    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipant#createTopic(java.lang.String, org.omg.dds.type.TypeSupport, org.omg.dds.topic.TopicQos, org.omg.dds.topic.TopicListener, java.util.Collection)}
     * .
     */
    @Test
    public void testCreateTopicStringTypeSupportOfTYPETopicQosTopicListenerOfTYPECollectionOfClassOfQextendsStatus() {
        Topic<Msg> t = null;
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        TypeSupport<Msg> typeSupport = TypeSupport.newTypeSupport(Msg.class, "testCreateTopicStringTypeSupportOfTYPE", env);
        assertTrue("Check for TypeSupport object", typeSupport instanceof TypeSupport);
        TopicQos tq = participant.getDefaultTopicQos();
        assertTrue("Check for valid TopicQos object", tq instanceof TopicQos);
        TopicListener<Msg> listener = null;
        Collection<Class<? extends Status>> statuses = new HashSet<Class<? extends Status>>();
        statuses.add(DataAvailableStatus.class);

        try {
            t = participant.createTopic("Topic5", typeSupport, tq, listener, statuses);
        } catch (Exception e) {
            fail("Exception occured while doing createTopic(string,typesupport,qos,listener,statuses): " + util.printException(e));
        }
        assertTrue("Check for valid Topic object", t != null);
        assertTrue("Check for correct Topic name", t.getName().equals("Topic5"));
        t.close();
    }

    /**
     * Test method for {@link
     * org.omg.dds.domain.DomainParticipant#createTopic(java.lang.String,
     * org.omg.dds.type.TypeSupport, org.omg.dds.topic.TopicQos,
     * org.omg.dds.topic.TopicListener, java.lang.Class<? extends
     * org.omg.dds.core.status.Status>[])}.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testCreateTopicStringTypeSupportOfTYPETopicQosTopicListenerOfTYPEClassOfQextendsStatusArray() {
        Topic<Msg> t = null;
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        TypeSupport<Msg> typeSupport = TypeSupport.newTypeSupport(Msg.class, "testCreateTopicStringTypeSupportOfTYPE", env);
        assertTrue("Check for TypeSupport object", typeSupport instanceof TypeSupport);
        TopicQos tq = participant.getDefaultTopicQos();
        assertTrue("Check for valid TopicQos object", tq instanceof TopicQos);
        TopicListener<Msg> listener = null;
        Class<DataAvailableStatus> status = DataAvailableStatus.class;

        try {
            t = participant.createTopic("Topic6", typeSupport, tq, listener, status);
        } catch (Exception e) {
            fail("Exception occured while doing createTopic(string,typesupport,qos,listener,status): " + util.printException(e));
        }
        assertTrue("Check for valid Topic object", t != null);
        assertTrue("Check for correct Topic name", t.getName().equals("Topic6"));
        t.close();
    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipant#findTopic(java.lang.String, org.omg.dds.core.Duration)}
     * .
     */
    @Test
    public void testFindTopicStringDuration() {

        Topic<Msg> t1 = null;
        Topic<Msg> t2 = null;
        String topicName = "testFindTopicStringDuration";
        Duration timeout = Duration.newDuration(5, TimeUnit.SECONDS, env);
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        try {
            t1 = participant.createTopic(topicName, Msg.class);
        } catch (Exception e) {
            fail("Exception occured while doing createTopic(string,type): " + util.printException(e));
        }
        assertTrue("Check for valid Topic object", t1 != null);
        try {
            t2 = participant.findTopic(topicName, timeout);
        } catch (Exception e) {
            assertTrue("Exception occured while doing findTopic(string,duration): " + util.printException(e), util.exceptionCheck("testFindTopicStringDuration", e, false));
        }
        assertTrue("Check for valid Topic object", util.objectCheck("testFindTopicStringDuration", t2));
        t1.close();
        if (t2 != null) {
            t2.close();
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipant#findTopic(java.lang.String, org.omg.dds.core.Duration)}
     * .
     */
    @Test
    public void testFindTopicStringNullDuration() {

        Topic<Msg> t1 = null;
        String topicName = null;
        Duration timeout = Duration.newDuration(5, TimeUnit.SECONDS, env);
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        try {
            t1 = participant.findTopic(topicName, timeout);
        } catch (Exception e) {
            assertTrue("Exception occured while doing findTopic(string,duration): " + util.printException(e),
                    util.exceptionCheck("testFindTopicStringNullDuration", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for invalid Topic object", t1 == null);
    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipant#findTopic(java.lang.String, org.omg.dds.core.Duration)}
     * .
     */
    @Test
    public void testFindTopicStringDurationNull() {

        Topic<Msg> t1 = null;
        Topic<Msg> t2 = null;
        String topicName = "testFindTopicStringDurationNull";
        Duration timeout = null;
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        try {
            t1 = participant.createTopic(topicName, Msg.class);
        } catch (Exception e) {
            fail("Exception occured while doing createTopic(string,type): " + util.printException(e));
        }
        assertTrue("Check for valid Topic object", t1 != null);
        try {
            t2 = participant.findTopic(topicName, timeout);
        } catch (Exception e) {
            assertTrue("Exception occured while doing findTopic(string,duration): " + util.printException(e),
                    util.exceptionCheck("testFindTopicStringDurationNull", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for invalid Topic object", t2 == null);
        t1.close();
    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipant#findTopic(java.lang.String, long, java.util.concurrent.TimeUnit)}
     * .
     */
    @Test
    public void testFindTopicStringLongTimeUnit() {
        Topic<Msg> t1 = null;
        Topic<Msg> t2 = null;
        String topicName = "testFindTopicStringLongTimeUnit";
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        try {
            t1 = participant.createTopic(topicName, Msg.class);
        } catch (Exception e) {
            fail("Exception occured while doing createTopic(string,type): " + util.printException(e));
        }
        assertTrue("Check for valid Topic object", t1 != null);
        try {
            t2 = participant.findTopic(topicName, 5, TimeUnit.SECONDS);
        } catch (Exception e) {
            assertTrue("Exception occured while doing findTopic(string,duration): " + util.printException(e), util.exceptionCheck("testFindTopicStringLongTimeUnit", e, false));
        }
        assertTrue("Check for valid Topic object", util.objectCheck("testFindTopicStringLongTimeUnit", t2));
        t1.close();
        if (t2 != null) {
            t2.close();
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipant#findTopic(java.lang.String, long, java.util.concurrent.TimeUnit)}
     * .
     */
    @Test
    public void testFindTopicStringLongTimeUnitNegative() {
        Topic<Msg> t1 = null;
        String topicName = "testFindTopicStringLongTimeUnitNegative";
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        try {
            t1 = participant.findTopic(topicName, -1, TimeUnit.SECONDS);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException exception expected but got: " + util.printException(e),
                    util.exceptionCheck("testFindTopicStringLongTimeUnitNegative", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for invalid Topic object", t1 == null);

    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipant#findTopic(java.lang.String, long, java.util.concurrent.TimeUnit)}
     * .
     */
    @Test
    public void testFindTopicStringLongTimeUnitOverflow() {
        Topic<Msg> t1 = null;
        String topicName = "testFindTopicStringLongTimeUnitNegative";
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        try {
            t1 = participant.findTopic(topicName, Long.MAX_VALUE + 1, TimeUnit.SECONDS);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException exception expected but got: " + util.printException(e),
                    util.exceptionCheck("testFindTopicStringLongTimeUnitOverflow", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for invalid Topic object", t1 == null);

    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipant#findTopic(java.lang.String, long, java.util.concurrent.TimeUnit)}
     * .
     */
    @Test
    public void testFindTopicStringLongTimeUnitTimeout() {
        Topic<Msg> t1 = null;
        String topicName = "testFindTopicStringLongTimeUnitTimeout";
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        try {
            t1 = participant.findTopic(topicName, 2, TimeUnit.SECONDS);
        } catch (Exception e) {
            assertTrue("TimeoutException exception expected but got: " + util.printException(e), util.exceptionCheck("testFindTopicStringLongTimeUnitTimeout", e, e instanceof TimeoutException));
        }
        assertTrue("Check for invalid Topic object", t1 == null);

    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipant#lookupTopicDescription(java.lang.String)}
     * .
     */
    @Test
    public void testLookupTopicDescription() {
        Topic<Msg> t1 = null;
        TopicDescription<Msg> description = null;
        String topicName = "testLookupTopicDescription";
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        try {
            t1 = participant.createTopic(topicName, Msg.class);
        } catch (Exception e) {
            fail("Exception occured while doing createTopic(string,type): " + util.printException(e));
        }
        assertTrue("Check for valid Topic object", t1 != null);
        try {
            description = participant.lookupTopicDescription(topicName);
        } catch (Exception e) {
            fail("Exception occured while doing lookupTopicDescription(string): " + util.printException(e));
        }
        assertTrue("Check for valid Topic object", description != null);
        t1.close();
    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipant#lookupTopicDescription(java.lang.String)}
     * .
     */
    @Test
    public void testLookupTopicDescriptionNull() {
        Topic<Msg> t1 = null;
        TopicDescription<Msg> description = null;
        String topicName = "testLookupTopicDescriptionNull";
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        try {
            t1 = participant.createTopic(topicName, Msg.class);
        } catch (Exception e) {
            fail("Exception occured while doing createTopic(string,type): " + util.printException(e));
        }
        assertTrue("Check for valid Topic object", t1 != null);
        try {
            description = participant.lookupTopicDescription(null);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), e instanceof IllegalArgumentException);
        }
        assertTrue("Check for invalid Topic object", description == null);
        t1.close();
    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipant#createContentFilteredTopic(java.lang.String, org.omg.dds.topic.Topic, java.lang.String, java.util.List)}
     * .
     */
    @Test
    public void testCreateContentFilteredTopicStringTopicOfQextendsTYPEStringListOfString() {
        Topic<Msg> t1 = null;
        ContentFilteredTopic<Msg> t2 = null;
        String topicName = "testCreateContentFilteredTopicStringTopicOfQextendsTYPEStringListOfString";
        String filteredTopicName = "testCreateContentFilteredTopicStringTopicOfQextendsTYPEStringListOfStringFilterTopic";
        String filterExpression = "userID < 10";
        List<String> filterParams = new ArrayList<String>();
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        try {
            t1 = participant.createTopic(topicName, Msg.class);
        } catch (Exception e) {
            fail("Exception occured while doing createTopic(string,type): " + util.printException(e));
        }
        assertTrue("Check for valid Topic object", t1 != null);
        try {
            t2 = participant.createContentFilteredTopic(filteredTopicName, t1, filterExpression, filterParams);
        } catch (Exception e) {
            assertTrue("Exception occured while doing createContentFilteredTopic(string,topic,expression,params arr): " + util.printException(e),
                    util.exceptionCheck("testCreateContentFilteredTopicStringTopicOfQextendsTYPEStringListOfString", e, false));
        }
        assertTrue("Check for valid ContentFilteredTopic object", util.objectCheck("testCreateContentFilteredTopicStringTopicOfQextendsTYPEStringListOfString", t2));
        t1.close();
        if (t2 != null) {
            t2.close();
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipant#createContentFilteredTopic(java.lang.String, org.omg.dds.topic.Topic, java.lang.String, java.lang.String[])}
     * .
     */
    @Test
    public void testCreateContentFilteredTopicStringTopicOfQextendsTYPEStringStringArray() {
        Topic<Msg> t1 = null;
        ContentFilteredTopic<Msg> t2 = null;
        String topicName = "testCreateContentFilteredTopicStringTopicOfQextendsTYPEStringStringArray";
        String filteredTopicName = "testCreateContentFilteredTopicStringTopicOfQextendsTYPEStringStringArrayFilterTopic";
        String filterExpression = "userID < 10";
        String filterParams = "";
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        try {
            t1 = participant.createTopic(topicName, Msg.class);
        } catch (Exception e) {
            fail("Exception occured while doing createTopic(string,type): " + util.printException(e));
        }
        assertTrue("Check for valid Topic object", t1 != null);
        try {
            t2 = participant.createContentFilteredTopic(filteredTopicName, t1, filterExpression, filterParams);
        } catch (Exception e) {
            assertTrue("Exception occured while doing createContentFilteredTopic(string,topic,expression,params str): " + util.printException(e),
                    util.exceptionCheck("testCreateContentFilteredTopicStringTopicOfQextendsTYPEStringStringArray", e, false));
        }
        assertTrue("Check for valid ContentFilteredTopic object", util.objectCheck("testCreateContentFilteredTopicStringTopicOfQextendsTYPEStringStringArray", t2));
        t1.close();
        if (t2 != null) {
            t2.close();
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipant#createContentFilteredTopic(java.lang.String, org.omg.dds.topic.Topic, java.lang.String, java.util.List)}
     * .
     */
    /* Fails on opensplice see OSPL-5551 */
    @Test
    public void testCreateContentFilteredNameNull() {
        Topic<Msg> t1 = null;
        ContentFilteredTopic<Msg> t2 = null;
        String topicName = "testCreateContentFilteredNameNull";
        String filteredTopicName = null;
        String filterExpression = "userID < 10";
        List<String> filterParams = new ArrayList<String>();
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        try {
            t1 = participant.createTopic(topicName, Msg.class);
        } catch (Exception e) {
            fail("Exception occured while doing createTopic(string,type): " + util.printException(e));
        }
        assertTrue("Check for valid Topic object", t1 != null);
        try {
            t2 = participant.createContentFilteredTopic(filteredTopicName, t1, filterExpression, filterParams);
        } catch (Exception e) {
            assertTrue("Expected IllegalArgumentException but got: " + util.printException(e), util.exceptionCheck("testCreateContentFilteredNameNull", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for invalid ContentFilteredTopic object", t2 == null);
        t1.close();
    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipant#createContentFilteredTopic(java.lang.String, org.omg.dds.topic.Topic, java.lang.String, java.util.List)}
     * .
     */
    /* Fails on opensplice see OSPL-5551 */
    @Test
    public void testCreateContentFilteredExpressionNull() {
        Topic<Msg> t1 = null;
        ContentFilteredTopic<Msg> t2 = null;
        String topicName = "testCreateContentFilteredExpressionNull";
        String filteredTopicName = "testCreateContentFilteredExpressionNullFilterTopic";
        String filterExpression = null;
        List<String> filterParams = new ArrayList<String>();
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        try {
            t1 = participant.createTopic(topicName, Msg.class);
        } catch (Exception e) {
            fail("Exception occured while doing createTopic(string,type): " + util.printException(e));
        }
        assertTrue("Check for valid Topic object", t1 != null);
        try {
            t2 = participant.createContentFilteredTopic(filteredTopicName, t1, filterExpression, filterParams);
        } catch (Exception e) {
            assertTrue("Expected IllegalArgumentException but got: " + util.printException(e), util.exceptionCheck("testCreateContentFilteredExpressionNull", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for invalid ContentFilteredTopic object", t2 == null);
        t1.close();
    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipant#createContentFilteredTopic(java.lang.String, org.omg.dds.topic.Topic, java.lang.String, java.util.List)}
     * .
     */
    @Test
    public void testCreateContentFilteredParamsNull() {
        Topic<Msg> t1 = null;
        ContentFilteredTopic<Msg> t2 = null;
        String topicName = "testCreateContentFilteredParamsNull";
        String filteredTopicName = "testCreateContentFilteredParamsNullFilterTopic";
        String filterExpression = "userID < 10";
        List<String> filterParams = null;
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        try {
            t1 = participant.createTopic(topicName, Msg.class);
        } catch (Exception e) {
            fail("Exception occured while doing createTopic(string,type): " + util.printException(e));
        }
        assertTrue("Check for valid Topic object", t1 != null);
        try {
            t2 = participant.createContentFilteredTopic(filteredTopicName, t1, filterExpression, filterParams);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testCreateContentFilteredParamsNull", e, false));
        }
        assertTrue("Check for valid ContentFilteredTopic object", util.objectCheck("testCreateContentFilteredParamsNull", t2));
        t1.close();
        if (t2 != null) {
            t2.close();
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipant#createContentFilteredTopic(java.lang.String, org.omg.dds.topic.Topic, java.lang.String, java.util.List)}
     * .
     */
    /* Fails on opensplice see OSPL-5551 */
    @Test
    public void testCreateContentFilteredMissingParameter() {
        Topic<Msg> t1 = null;
        ContentFilteredTopic<Msg> t2 = null;
        String topicName = "testCreateContentFilteredMissingParameter";
        String filteredTopicName = "testCreateContentFilteredMissingParameterFilterTopic";
        String filterExpression = "userID < %0";
        List<String> filterParams = new ArrayList<String>();
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        try {
            t1 = participant.createTopic(topicName, Msg.class);
        } catch (Exception e) {
            fail("Exception occured while doing createTopic(string,type): " + util.printException(e));
        }
        assertTrue("Check for valid Topic object", t1 != null);
        try {
            t2 = participant.createContentFilteredTopic(filteredTopicName, t1, filterExpression, filterParams);
        } catch (Exception e) {
            assertTrue("Expected IllegalArgumentException but got: " + util.printException(e),
                    util.exceptionCheck("testCreateContentFilteredMissingParameter", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for invalid ContentFilteredTopic object", t2 == null);
        t1.close();
    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipant#closeContainedEntities()}.
     */
    @Test
    public void testCloseContainedEntities() {
        DomainParticipant par = null;
        Publisher pub = null;
        Subscriber sub = null;
        Topic<Msg> top = null;

        try {
            par = dpf.createParticipant(DOMAIN_ID);
            assertTrue("Check for valid Participant object", par != null);
            pub = par.createPublisher();
            sub = par.createSubscriber();
            top = par.createTopic("testCloseContainedEntities", Msg.class);
        } catch (Exception e) {
            fail("Exception occured while creating a participant, publisher, subscriber and topic: " + util.printException(e));
        }
        assertTrue("Check for valid Publisher object", pub != null);
        assertTrue("Check for valid Subscriber object", sub != null);
        assertTrue("Check for valid Topic object", top != null);
        boolean exceptionOccured = false;
        try {
            par.closeContainedEntities();
        } catch (Exception e) {
            fail("Exception occured while doing closeContainedEntities: " + util.printException(e));
        }
        try {
            pub.close();
        } catch (Exception e) {
            assertTrue("Check for AlreadyClosedException", e instanceof AlreadyClosedException);
            exceptionOccured = true;
        }
        assertTrue("Expected AlreadyClosedException not occured", exceptionOccured);
        exceptionOccured = false;
        try {
            sub.close();
        } catch (Exception e) {
            assertTrue("Check for AlreadyClosedException", e instanceof AlreadyClosedException);
            exceptionOccured = true;
        }
        assertTrue("Expected AlreadyClosedException not occured", exceptionOccured);
        exceptionOccured = false;
        try {
            top.close();
        } catch (Exception e) {
            assertTrue("Check for AlreadyClosedException", e instanceof AlreadyClosedException);
            exceptionOccured = true;
        }
        assertTrue("Expected AlreadyClosedException not occured", exceptionOccured);
        par.close();

    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipant#ignoreParticipant(org.omg.dds.core.InstanceHandle)}
     * .
     */
    @Test
    public void testIgnoreParticipant() {
        DomainParticipant par = null;
        InstanceHandle ih = null;
        try {
            par = dpf.createParticipant(DOMAIN_ID);
        } catch (Exception e) {
            fail("Exception occured while creating a participant: " + util.printException(e));
        }
        assertTrue("Check for valid Participant object", par != null);
        try {
            ih = participant.getInstanceHandle();
        } catch (Exception e) {
            fail("Exception occured calling getInstanceHandle: " + util.printException(e));
        }
        try {
            par.ignoreParticipant(ih);
        } catch (Exception e) {
            assertTrue("Expected no exception but got: " + util.printException(e), util.exceptionCheck("testIgnoreParticipant", e, false));
        }
        par.close();

    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipant#ignoreParticipant(org.omg.dds.core.InstanceHandle)}
     * .
     */
    @Test
    public void testIgnoreParticipantNull() {
        DomainParticipant par = null;
        try {
            par = dpf.createParticipant(DOMAIN_ID);
        } catch (Exception e) {
            fail("Exception occured while creating a participant: " + util.printException(e));
        }
        assertTrue("Check for valid Participant object", par != null);
        try {
            par.ignoreParticipant(null);
        } catch (Exception e) {
            assertTrue("Expected IllegalArgumentException but got: " + util.printException(e), util.exceptionCheck("testIgnoreParticipantNull", e, e instanceof IllegalArgumentException));
        }
        par.close();

    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipant#ignoreParticipant(org.omg.dds.core.InstanceHandle)}
     * .
     */
    @Test
    public void testIgnoreParticipantInvalidHandle() {
        DomainParticipant par = null;
        @SuppressWarnings("serial")
        InstanceHandle ih = new InstanceHandle() {

            @Override
            public ServiceEnvironment getEnvironment() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public int compareTo(InstanceHandle o) {
                // TODO Auto-generated method stub
                return 0;
            }

            @Override
            public boolean isNil() {
                // TODO Auto-generated method stub
                return false;
            }
        };
        try {
            par = dpf.createParticipant(DOMAIN_ID);
        } catch (Exception e) {
            fail("Exception occured while creating a participant: " + util.printException(e));
        }
        assertTrue("Check for valid Participant object", par != null);
        try {
            par.ignoreParticipant(ih);
        } catch (Exception e) {
            assertTrue("Expected IllegalArgumentException but got: " + util.printException(e), util.exceptionCheck("testIgnoreParticipantInvalidHandle", e, e instanceof IllegalArgumentException));
        }
        par.close();

    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipant#ignoreTopic(org.omg.dds.core.InstanceHandle)}
     * .
     */
    @Test
    public void testIgnoreTopic() {
        DomainParticipant par = null;
        Topic<Msg> top = null;
        InstanceHandle ih = null;
        try {
            par = dpf.createParticipant(DOMAIN_ID);
            assertTrue("Check for valid Participant object", par != null);
            top = participant.createTopic("testIgnoreTopic", Msg.class);
        } catch (Exception e) {
            fail("Exception occured while creating a participant and topic: " + util.printException(e));
        }
        assertTrue("Check for valid Topic object", top != null);
        try {
            ih = top.getInstanceHandle();
        } catch (Exception e) {
            fail("Exception occured calling getInstanceHandle: " + util.printException(e));
        }
        try {
            par.ignoreParticipant(ih);
        } catch (Exception e) {
            assertTrue("Expected no exception but got: " + util.printException(e), util.exceptionCheck("testIgnoreTopic", e, false));
        }
        top.close();
        par.close();
    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipant#ignoreTopic(org.omg.dds.core.InstanceHandle)}
     * .
     */
    @Test
    public void testIgnoreTopicNull() {
        DomainParticipant par = null;
        try {
            par = dpf.createParticipant(DOMAIN_ID);
        } catch (Exception e) {
            fail("Exception occured while creating a participant: " + util.printException(e));
        }
        assertTrue("Check for valid Participant object", par != null);
        try {
            par.ignoreTopic(null);
        } catch (Exception e) {
            assertTrue("Expected IllegalArgumentException but got: " + util.printException(e), util.exceptionCheck("testIgnoreTopicNull", e, e instanceof IllegalArgumentException));
        }
        par.close();

    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipant#ignorePublication(org.omg.dds.core.InstanceHandle)}
     * .
     */
    @Test
    public void testIgnorePublication() {
        DomainParticipant par = null;
        Publisher pub = null;
        InstanceHandle ih = null;
        try {
            par = dpf.createParticipant(DOMAIN_ID);
            assertTrue("Check for valid Participant object", par != null);
            pub = participant.createPublisher();
        } catch (Exception e) {
            fail("Exception occured while creating a participant and publisher: " + util.printException(e));
        }
        assertTrue("Check for valid Publisher object", pub != null);
        try {
            ih = pub.getInstanceHandle();
        } catch (Exception e) {
            fail("Exception occured calling getInstanceHandle: " + util.printException(e));
        }
        try {
            par.ignorePublication(ih);
        } catch (Exception e) {
            assertTrue("Expected no exception but got: " + util.printException(e), util.exceptionCheck("testIgnorePublication", e, false));
        }
        pub.close();
        par.close();
    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipant#ignorePublication(org.omg.dds.core.InstanceHandle)}
     * .
     */
    @Test
    public void testIgnorePublicationNull() {
        DomainParticipant par = null;
        try {
            par = dpf.createParticipant(DOMAIN_ID);
            assertTrue("Check for valid Participant object", par != null);
        } catch (Exception e) {
            fail("Exception occured while creating a participant: " + util.printException(e));
        }
        try {
            par.ignorePublication(null);
        } catch (Exception e) {
            assertTrue("Expected IllegalArgumentException but got: " + util.printException(e), util.exceptionCheck("testIgnorePublicationNull", e, e instanceof IllegalArgumentException));
        }
        par.close();
    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipant#ignoreSubscription(org.omg.dds.core.InstanceHandle)}
     * .
     */
    @Test
    public void testIgnoreSubscription() {
        DomainParticipant par = null;
        Subscriber sub = null;
        InstanceHandle ih = null;
        try {
            par = dpf.createParticipant(DOMAIN_ID);
            assertTrue("Check for valid Participant object", par != null);
            sub = participant.createSubscriber();
        } catch (Exception e) {
            fail("Exception occured while creating a participant and Subscriber: " + util.printException(e));
        }
        assertTrue("Check for valid Subscriber object", sub != null);
        try {
            ih = sub.getInstanceHandle();
        } catch (Exception e) {
            fail("Exception occured calling getInstanceHandle: " + util.printException(e));
        }
        try {
            par.ignoreSubscription(ih);
        } catch (Exception e) {
            assertTrue("Expected no exception but got: " + util.printException(e), util.exceptionCheck("testIgnoreSubscription", e, false));
        }
        sub.close();
        par.close();
    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipant#ignoreSubscription(org.omg.dds.core.InstanceHandle)}
     * .
     */
    @Test
    public void testIgnoreSubscriptionNull() {
        DomainParticipant par = null;
        try {
            par = dpf.createParticipant(DOMAIN_ID);
            assertTrue("Check for valid Participant object", par != null);
        } catch (Exception e) {
            fail("Exception occured while creating a participant: " + util.printException(e));
        }
        try {
            par.ignoreSubscription(null);
        } catch (Exception e) {
            assertTrue("Expected IllegalArgumentException but got: " + util.printException(e), util.exceptionCheck("testIgnoreSubscriptionNull", e, e instanceof IllegalArgumentException));
        }
        par.close();
    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipant#getDomainId()}.
     */
    @Test
    public void testGetDomainId() {
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        int id = 0;
        try {
            id = participant.getDomainId();
        } catch (Exception e) {
            fail("Exception occured while doing getDomainId(): " + util.printException(e));
        }
        assertTrue("Check for valid domain ID ", id == DOMAIN_ID);
    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipant#assertLiveliness()}.
     */
    @Test
    public void testAssertLiveliness() {
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        try {
            participant.assertLiveliness();
        } catch (Exception e) {
            assertTrue("Exception occured while doing assertLiveliness: " + util.printException(e), util.exceptionCheck("testAssertLiveliness", e, false));
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipant#getDefaultPublisherQos()}.
     */
    @Test
    public void testGetDefaultPublisherQos() {
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        PublisherQos pq = null;
        try {
            pq = participant.getDefaultPublisherQos();
        } catch (Exception e) {
            fail("Exception occured while doing getDefaultPublisherQos(): " + util.printException(e));
        }
        assertTrue("Check for valid PublisherQos", pq != null);
    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipant#setDefaultPublisherQos(org.omg.dds.pub.PublisherQos)}
     * .
     */
    @Test
    public void testSetDefaultPublisherQosNull() {
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        boolean exceptionOccured = false;
        try {
            participant.setDefaultPublisherQos(null);
        } catch (Exception e) {
            assertTrue("Check for IllegalArgumentException but got exception:" + util.printException(e), e instanceof IllegalArgumentException);
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException occured failed", exceptionOccured);
    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipant#setDefaultPublisherQos(org.omg.dds.pub.PublisherQos)}
     * .
     */
    @Test
    public void testSetDefaultPublisherQos() {
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        PublisherQos pq = null;
        try {
            pq = participant.getDefaultPublisherQos();
            pq = pq.withPolicy(pq.getEntityFactory().withAutoEnableCreatedEntities(false));
            assertFalse("Check for valid PublisherQos value", pq.getEntityFactory().isAutoEnableCreatedEntities());
        } catch (Exception e) {
            fail("Exception occured while doing getDefaultPublisherQos(): " + util.printException(e));
        }
        try {
            participant.setDefaultPublisherQos(pq);
            pq = participant.getDefaultPublisherQos();
            assertFalse("Check for valid PublisherQos value", pq.getEntityFactory().isAutoEnableCreatedEntities());
            /* restore qos */
            pq = pq.withPolicy(pq.getEntityFactory().withAutoEnableCreatedEntities(true));
            participant.setDefaultPublisherQos(pq);
            assertTrue("Check for valid PublisherQos value", participant.getDefaultPublisherQos().getEntityFactory().isAutoEnableCreatedEntities());
        } catch (Exception ex) {
            fail("Exception occured while setting and restoring the publisherqos: " + util.printException(ex));
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipant#getDefaultSubscriberQos()}.
     */
    @Test
    public void testGetDefaultSubscriberQos() {
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        SubscriberQos sq = null;
        try {
            sq = participant.getDefaultSubscriberQos();
        } catch (Exception e) {
            fail("Exception occured while doing getDefaultSubscriberQos(): " + util.printException(e));
        }
        assertTrue("Check for valid SubscriberQos", sq != null);
    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipant#setDefaultSubscriberQos(org.omg.dds.pub.SubscriberQos)}
     * .
     */
    @Test
    public void testSetDefaultSubscriberQosNull() {
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        boolean exceptionOccured = false;
        try {
            participant.setDefaultSubscriberQos(null);
        } catch (Exception e) {
            assertTrue("Check for IllegalArgumentException but got exception:" + util.printException(e), e instanceof IllegalArgumentException);
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException occured failed", exceptionOccured);
    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipant#setDefaultSubscriberQos(org.omg.dds.pub.SubscriberQos)}
     * .
     */
    @Test
    public void testSetDefaultSubscriberQos() {
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        SubscriberQos sq = null;
        try {
            sq = participant.getDefaultSubscriberQos();
            sq = sq.withPolicy(sq.getEntityFactory().withAutoEnableCreatedEntities(false));
            assertFalse("Check for valid SubscriberQos value", sq.getEntityFactory().isAutoEnableCreatedEntities());
        } catch (Exception e) {
            fail("Exception occured while doing getDefaultSubscriberQos(): " + util.printException(e));
        }
        try {
            participant.setDefaultSubscriberQos(sq);
            sq = participant.getDefaultSubscriberQos();
            assertFalse("Check for valid SubscriberQos value", sq.getEntityFactory().isAutoEnableCreatedEntities());
            /* restore qos */
            sq = sq.withPolicy(sq.getEntityFactory().withAutoEnableCreatedEntities(true));
            participant.setDefaultSubscriberQos(sq);
            assertTrue("Check for valid SubscriberQos value", participant.getDefaultSubscriberQos().getEntityFactory().isAutoEnableCreatedEntities());
        } catch (Exception ex) {
            fail("Exception occured while setting and restoring the subscriberqos: " + util.printException(ex));
        }

    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipant#getDefaultTopicQos()}.
     */
    @Test
    public void testGetDefaultTopicQos() {
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        TopicQos tq = null;
        try {
            tq = participant.getDefaultTopicQos();
        } catch (Exception e) {
            fail("Exception occured while doing getDefaultTopicQos(): " + util.printException(e));
        }
        assertTrue("Check for valid TopicQos", tq != null);
    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipant#setDefaultTopicQos(org.omg.dds.pub.TopicQos)}
     * .
     */
    @Test
    public void testSetDefaultTopicQosNull() {
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        boolean exceptionOccured = false;
        try {
            participant.setDefaultTopicQos(null);
        } catch (Exception e) {
            assertTrue("Check for IllegalArgumentException but got exception:" + util.printException(e), e instanceof IllegalArgumentException);
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException occured failed", exceptionOccured);
    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipant#setDefaultTopicQos(org.omg.dds.pub.TopicQos)}
     * .
     */
    @Test
    public void testSetDefaultTopicQos() {
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        TopicQos tq = null;
        try {
            tq = participant.getDefaultTopicQos();
            tq = tq.withPolicy(tq.getDurability().withPersistent());
            assertTrue("Check for valid TopicQos value", tq.getDurability().getKind() == Durability.Kind.PERSISTENT);
        } catch (Exception e) {
            fail("Exception occured while doing getDefaultTopicQos(): " + util.printException(e));
        }

        try {
            participant.setDefaultTopicQos(tq);
            tq = participant.getDefaultTopicQos();
            assertTrue("Check for valid TopicQos value", tq.getDurability().getKind() == Durability.Kind.PERSISTENT);
            /* restore qos */
            tq = tq.withPolicy(tq.getDurability().withVolatile());
            participant.setDefaultTopicQos(tq);
            assertTrue("Check for valid TopicQos value", tq.getDurability().getKind() == Durability.Kind.VOLATILE);
        } catch (Exception ex) {
            fail("Exception occured while setting and restoring the topicqos: " + util.printException(ex));
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipant#getDiscoveredParticipants()}.
     */
    @Test
    public void testGetDiscoveredParticipants() {
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        Set<InstanceHandle> participants = null;
        boolean participantFound = false;
        try {
            participants = participant.getDiscoveredParticipants();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetDiscoveredParticipants", e, false));
        }
        assertTrue("Check for valid participants object", util.objectCheck("testGetDiscoveredParticipants", participants));
        if (participants != null) {
            for (InstanceHandle ih : participants) {
                try {
                    ParticipantBuiltinTopicData pbtd = participant.getDiscoveredParticipantData(ih);
                    assertTrue("Check for valid ParticipantBuiltinTopicData object", util.objectCheck("testGetDiscoveredParticipants", pbtd));
                    ParticipantBuiltinTopicData clone = pbtd.clone();
                    assertTrue("Check for valid ParticipantBuiltinTopicData object", util.objectCheck("testGetDiscoveredParticipants", clone));
                    clone.copyFrom(pbtd);
                    assertTrue("Check for valid ParticipantBuiltinTopicData object", util.objectCheck("testGetDiscoveredParticipants", clone));
                    assertTrue("Check for valid ParticipantBuiltinTopicData env", util.objectCheck("testGetDiscoveredParticipants", pbtd.getEnvironment()));
                    assertTrue("Check for valid ParticipantBuiltinTopicData key", util.objectCheck("testGetDiscoveredParticipants", pbtd.getKey()));
                    if (new String(pbtd.getUserData().getValue()).equals("DomainParticipantTest")) {
                        participantFound = true;
                    }
                } catch (Exception e) {
                    assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetDiscoveredParticipants", e, false));
                }
            }
            assertTrue("Check for correct participant in participants set", participantFound);
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipant#getDiscoveredParticipantData(org.omg.dds.core.InstanceHandle)}
     * .
     */
    @Test
    public void testGetDiscoveredParticipantDataNull() {
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        ParticipantBuiltinTopicData pbtd = null;
        try {
            pbtd = participant.getDiscoveredParticipantData(null);
        } catch (Exception e) {
            assertTrue("Expected IllegalArgumentException but got: " + util.printException(e), util.exceptionCheck("testGetDiscoveredParticipantDataNull", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for invalid ParticipantBuiltinTopicData object", pbtd == null);
    }


    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipant#getDiscoveredTopics()}.
     */
    @Test
    public void testGetDiscoveredTopics() {
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        Topic<Msg> top = null;
        Set<InstanceHandle> topics = null;
        boolean topicFound = false;
        try {
            top = participant.createTopic("GetDiscoveredTopics", Msg.class);
        } catch (Exception e) {
            fail("Exception occured while creating a topic: " + util.printException(e));
        }
        assertTrue("Check for valid Topic object", top != null);
        try {
            topics = participant.getDiscoveredTopics();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetDiscoveredTopics", e, false));
        }
        assertTrue("Check for valid participants object", util.objectCheck("testGetDiscoveredTopics", topics));

        if (topics != null) {
            for (InstanceHandle ih : topics) {
                try {
                    TopicBuiltinTopicData tbtd = participant.getDiscoveredTopicData(ih);
                    assertTrue("Check for valid TopicBuiltinTopicData object", util.objectCheck("testGetDiscoveredTopics", tbtd));
                    if (new String(tbtd.getName()).equals("GetDiscoveredTopics")) {
                        topicFound = true;
                    }
                } catch (Exception e) {
                    assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetDiscoveredTopics", e, false));
                }
            }
            assertTrue("Check for correct topic in topics set", topicFound);
        }
        top.close();
    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipant#getDiscoveredTopicData(org.omg.dds.core.InstanceHandle)}
     * .
     */
    @Test
    public void testGetDiscoveredTopicDataNull() {
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        TopicBuiltinTopicData tbtd = null;
        try {
            tbtd = participant.getDiscoveredTopicData(null);
        } catch (Exception e) {
            assertTrue("Expected IllegalArgumentException but got: " + util.printException(e), util.exceptionCheck("testGetDiscoveredTopicDataNull", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for invalid TopicBuiltinTopicData object", tbtd == null);
    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipant#containsEntity(org.omg.dds.core.InstanceHandle)}
     * .
     */
    @Test
    public void testContainsEntity() {
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        Publisher pub = null;
        Subscriber sub = null;
        Topic<Msg> top = null;

        try {
            pub = participant.createPublisher();
            sub = participant.createSubscriber();
            top = participant.createTopic("testContainsEntity", Msg.class);
        } catch (Exception e) {
            fail("Exception occured while creating a publisher, subscriber and topic: " + util.printException(e));
        }
        assertTrue("Check for valid Publisher object", pub != null);
        assertTrue("Check for valid Subscriber object", sub != null);
        assertTrue("Check for valid Topic object", top != null);
        try {
            assertTrue("Check if publisher entity is found", participant.containsEntity(pub.getInstanceHandle()));
            assertTrue("Check if subscriber entity is found", participant.containsEntity(sub.getInstanceHandle()));
            assertTrue("Check if topic entity is found", participant.containsEntity(top.getInstanceHandle()));
        } catch (Exception e) {
            fail("Exception occured while calling containsEntity: " + util.printException(e));
        }
        pub.close();
        sub.close();
        top.close();
    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipant#getCurrentTime(org.omg.dds.core.ModifiableTime)}
     * .
     */
    @Test
    public void testGetCurrentTimeModifiableTime() {
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        ModifiableTime currentTime = null;
        ModifiableTime result = null;
        try {
            currentTime = participant.getCurrentTime(null);
            currentTime.add(2, TimeUnit.SECONDS);
            result = participant.getCurrentTime(currentTime);
        } catch (Exception e) {
            fail("Exception occured while calling getCurrentTime(): " + util.printException(e));
        }
        assertTrue("Check for valid ModifiableTime object", result != null);
        try {
            result = participant.getCurrentTime(result);
        } catch (Exception e) {
            fail("Exception occured while calling getCurrentTime(time): " + util.printException(e));
        }
        assertTrue("Check for valid ModifiableTime object", result != null);
    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipant#getCurrentTime(org.omg.dds.core.ModifiableTime)}
     * .
     */
    @Test
    public void testGetCurrentTimeModifiableTimeNull() {
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        ModifiableTime currentTime = null;
        ModifiableTime result = null;
        try {
            result = participant.getCurrentTime(currentTime);
        } catch (Exception e) {
            fail("Exception occured while calling getCurrentTime(time): " + util.printException(e));
        }
        assertTrue("Check for valid ModifiableTime object", result != null);
    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipant#getCurrentTime()}.
     */
    @Test
    public void testGetCurrentTime() {
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        Time result = null;
        try {
            result = participant.getCurrentTime();
        } catch (Exception e) {
            fail("Exception occured while calling getCurrentTime(): " + util.printException(e));
        }
        assertTrue("Check for valid Time object", result != null);
    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipant#getStatusCondition()}.
     */
    @Test
    public void testGetStatusCondition() {
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        StatusCondition<DomainParticipant> result = null;
        try {
            result = participant.getStatusCondition();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetStatusCondition", e, false));
        }
        assertTrue("Check for valid StatusCondition object", util.objectCheck("testGetStatusCondition", result));
    }

    /**
     * Test method for {@link org.omg.dds.domain.DomainParticipant#getQos()}.
     */

    @Test
    public void testGetQos() {
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        DomainParticipantQos dpq = null;
        try {
            dpq = participant.getQos();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testDomainParticipantGetQos", e, false));
        }
        assertTrue("Check for valid DomainParticipantQos object", util.objectCheck("testGetStatusCondition", dpq));
    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipant#setQos(org.omg.dds.domain.DomainParticipantQos)}
     * .
     */
    @Test
    public void testSetQosNull() {
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        DomainParticipantQos dpq = null;
        boolean exceptionOccured = false;
        try {
            participant.setQos(dpq);
        } catch (Exception e) {
            assertTrue("Check for IllegalArgumentException but got exception:" + util.printException(e),
                    util.exceptionCheck("testDomainParticipantSetQosNull", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException occured failed", exceptionOccured);
    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipant#setQos(org.omg.dds.domain.DomainParticipantQos)}
     * .
     */
    @Test
    public void testSetQosIllegal() {
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        @SuppressWarnings("serial")
        DomainParticipantQos dpq = new DomainParticipantQos() {

            @Override
            public ServiceEnvironment getEnvironment() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public Collection<ForDomainParticipant> values() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public int size() {
                // TODO Auto-generated method stub
                return 0;
            }

            @Override
            public ForDomainParticipant remove(Object arg0) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public void putAll(Map<? extends Class<? extends ForDomainParticipant>, ? extends ForDomainParticipant> arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public ForDomainParticipant put(Class<? extends ForDomainParticipant> arg0, ForDomainParticipant arg1) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public Set<Class<? extends ForDomainParticipant>> keySet() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public boolean isEmpty() {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public ForDomainParticipant get(Object arg0) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public Set<Entry<Class<? extends ForDomainParticipant>, ForDomainParticipant>> entrySet() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public boolean containsValue(Object arg0) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean containsKey(Object arg0) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public void clear() {
                // TODO Auto-generated method stub

            }

            @Override
            public PolicyFactory getPolicyFactory() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public <POLICY extends ForDomainParticipant> POLICY get(Class<POLICY> id) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public DomainParticipantQos withPolicy(ForDomainParticipant policy) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public DomainParticipantQos withPolicies(ForDomainParticipant... policy) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public UserData getUserData() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public EntityFactory getEntityFactory() {
                // TODO Auto-generated method stub
                return null;
            }
        };
        boolean exceptionOccured = false;
        try {
            participant.setQos(dpq);
        } catch (Exception e) {
            assertTrue("Check for IllegalArgumentException but got exception:" + util.printException(e),
                    util.exceptionCheck("testDomainParticipantSetQosIllegal", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException occured failed", exceptionOccured);
    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipant#setQos(org.omg.dds.domain.DomainParticipantQos)}
     * .
     */

    @Test
    public void testSetQos() {
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        DomainParticipantQos dpq = null;
        try {
            dpq = participant.getQos();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testDomainParticipantSetQos", e, false));
        }
        assertTrue("Check for valid DomainParticipantQos object", util.objectCheck("testDomainParticipantSetQos", dpq));
        if (dpq != null) {
            dpq = dpq.withPolicy(dpq.getEntityFactory().withAutoEnableCreatedEntities(false));
            assertFalse("Check for valid DomainParticipantQos value", dpq.getEntityFactory().isAutoEnableCreatedEntities());
        }
        try {
            participant.setQos(dpq);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testDomainParticipantSetQos", e, false));
        }
        try {
            dpq = participant.getQos();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testDomainParticipantSetQos", e, false));
        }
        assertTrue("Check for valid DomainParticipantQos object", util.objectCheck("testDomainParticipantSetQos", dpq));
        if (dpq != null) {
            assertFalse("Check for valid DomainParticipantQos value", dpq.getEntityFactory().isAutoEnableCreatedEntities());
            /* restore qos */
            try {
                dpq = dpq.withPolicy(dpq.getEntityFactory().withAutoEnableCreatedEntities(true));
                participant.setQos(dpq);
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testDomainParticipantSetQos", e, false));
            }
            assertTrue("Check for valid DomainParticipantQos value", dpq.getEntityFactory().isAutoEnableCreatedEntities());
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipant#setListener(org.omg.dds.domain.DomainParticipantListener,java.util.Collection)}
     * .
     */

    @Test
    public void testDomainParticipantSetListenerCollection() {
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        DomainParticipantListener dpl = new DomainParticipantListenerClass();
        Collection<Class<? extends Status>> statuses = new HashSet<Class<? extends Status>>();
        statuses.add(DataAvailableStatus.class);
        try {
            participant.setListener(dpl, statuses);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testDomainParticipantSetListenerCollection", e, false));
        }

    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipant#setListener(org.omg.dds.domain. DomainParticipantListener)}
     * .
     */

    @Test
    public void testDomainParticipantSetListener() {
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        DomainParticipantListener dpl = new DomainParticipantListenerClass();
        try {
            participant.setListener(dpl);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testDomainParticipantSetListener", e, false));
        }

    }

    /**
     * Test method for {@link
     * org.omg.dds.domain.DomainParticipant#setListener(org.omg.dds.domain.
     * DomainParticipantListener,org.omg.dds.core.status.Status>[])} .
     */

    @SuppressWarnings("unchecked")
    @Test
    public void testDomainParticipantSetListenerStatus() {
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        DomainParticipantListener dpl = new DomainParticipantListenerClass();
        Class<? extends Status> status = DataAvailableStatus.class;
        try {
            participant.setListener(dpl, status);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testDomainParticipantSetListenerStatus", e, false));
        }

    }

    @SuppressWarnings("unchecked")
    @Test
    public void testDomainParticipantGetListener() {
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        DomainParticipantListener dpl = new DomainParticipantListenerClass();
        DomainParticipantListener dpl1 = null;
        Collection<Class<? extends Status>> statuses = new HashSet<Class<? extends Status>>();
        Class<? extends Status> status = null;
        statuses.add(DataAvailableStatus.class);
        try {
            participant.setListener(dpl, statuses);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testDomainParticipantGetListener", e, false));
        }
        try {
            dpl1 = participant.getListener();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testDomainParticipantGetListener", e, false));
        }
        assertTrue("Check for valid DomainParticipantListener object", util.objectCheck("testDomainParticipantGetListener", dpl1));
        /* reset listener to null */
        try {
            participant.setListener(null, status);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testDomainParticipantGetListener", e, false));
        }
        try {
            dpl1 = participant.getListener();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testDomainParticipantGetListener", e, false));
        }
        assertTrue("Check for null DomainParticipantListener object", dpl1 == null);

    }

    /**
     * Test method for {@link org.omg.dds.core.Entity#enable()}.
     */
    @Test
    public void testEnable() {
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        try {
            participant.enable();
        } catch (Exception e) {
            assertTrue("Exception occured while calling enable()" + util.printException(e), util.exceptionCheck("testEnable", e, false));
        }
    }

    /**
     * Test method for {@link org.omg.dds.core.Entity#getStatusChanges()}.
     */
    @Test
    public void testGetStatusChanges() {
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        Set<Class<? extends Status>> statuses = null;
        try {
            statuses = participant.getStatusChanges();
        } catch (Exception e) {
            assertTrue("Exception occured while calling getStatusChanges()" + util.printException(e), util.exceptionCheck("testGetStatusChanges", e, false));
        }
        assertTrue("Check for valid status set", util.objectCheck("testGetStatusChanges", statuses));

    }

    /**
     * Test method for {@link org.omg.dds.core.Entity#getInstanceHandle()}.
     */
    @Test
    public void testGetInstanceHandle() {
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        InstanceHandle ih = null;
        try {
            ih = participant.getInstanceHandle();
        } catch (Exception e) {
            fail("Exception occured while calling getInstanceHandle(): " + util.printException(e));
        }
        assertTrue("Check for valid getInstanceHandle", ih != null);
    }

    /**
     * Test method for {@link org.omg.dds.core.Entity#retain()}.
     */
    @Test
    public void testRetain() {
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        try {
            participant.retain();
        } catch (Exception e) {
            assertTrue("Exception occured while calling retain()" + util.printException(e), util.exceptionCheck("testRetain", e, false));
        }
    }

    /**
     * Test method for {@link org.omg.dds.core.DDSObject#getEnvironment()}.
     */
    @Test
    public void testGetEnvironment() {
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        ServiceEnvironment senv = null;
        try {
            senv = participant.getEnvironment();
        } catch (Exception e) {
            fail("Exception occured while calling getEnvironment(): " + util.printException(e));
        }
        assertTrue("Check for valid ServiceEnvironment", senv != null);
    }
}
