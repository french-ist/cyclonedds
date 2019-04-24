/**
 *
 */
package unittest.topic;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.dds.core.AlreadyClosedException;
import org.omg.dds.core.Duration;
import org.omg.dds.core.InstanceHandle;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.StatusCondition;
import org.omg.dds.core.policy.PolicyFactory;
import org.omg.dds.core.status.DataAvailableStatus;
import org.omg.dds.core.status.InconsistentTopicStatus;
import org.omg.dds.core.status.Status;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.domain.DomainParticipantFactory;
import org.omg.dds.pub.Publisher;
import org.omg.dds.topic.Topic;
import org.omg.dds.topic.TopicDescription;
import org.omg.dds.topic.TopicListener;
import org.omg.dds.topic.TopicQos;
import org.omg.dds.type.TypeSupport;

import Test.Msg;

import org.eclipse.cyclonedds.test.AbstractUtilities;
import org.eclipse.cyclonedds.test.TopicListenerClass;

public class TopicTest {

    private final static int                DOMAIN_ID   = 123;
    private static ServiceEnvironment       env;
    private static DomainParticipantFactory dpf;
    private static AbstractUtilities        util        = AbstractUtilities.getInstance(TopicTest.class);
    private final static Properties         prop        = new Properties();
    private static DomainParticipant        participant = null;
    private static Publisher                publisher   = null;
    private static Topic<Msg>               topic       = null;
    private static String                   topicName   = "TopicTest";

    @BeforeClass
    public static void init() {
        try {
            String serviceEnvironment = System.getProperty("JAVA5PSM_SERVICE_ENV");
            assertTrue("Check for valid ServiceEnvironment string", !serviceEnvironment.equals(""));
            System.setProperty(ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY, serviceEnvironment);
            env = ServiceEnvironment.createInstance(TopicTest.class.getClassLoader());
            assertTrue("Check for valid ServiceEnvironment object", env instanceof ServiceEnvironment);
            dpf = DomainParticipantFactory.getInstance(env);
            assertTrue("Check for valid DomainParticipantFactory object", dpf instanceof DomainParticipantFactory);
            prop.setProperty("domainid", new Integer(DOMAIN_ID).toString());
            assertTrue("Check is deamon is correctly started", util.beforeClass(prop));
            participant = dpf.createParticipant(DOMAIN_ID);
            assertTrue("Check for valid DomainParticipant object", participant instanceof DomainParticipant);
            assertTrue("Check for valid DomainParticipant with id " + DOMAIN_ID, participant.getDomainId() == DOMAIN_ID);
            publisher = participant.createPublisher();
            assertTrue("Check for valid Publisher object", publisher instanceof Publisher);
            topic = participant.createTopic(topicName, Msg.class);
        } catch (Exception e) {
            fail("Exception occured while initiating the TopicTest class: " + util.printException(e));
        }

    }

    private void checkValidEntities() {
        assertTrue("Check for valid DomainParticipant object", participant instanceof DomainParticipant);
        assertTrue("Check for valid Publisher object", publisher instanceof Publisher);
        assertTrue("Check for valid Topic object", topic instanceof Topic);
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
     * Test method for {@link org.omg.dds.topic.Topic#getParent()}.
     */
    @Test
    public void testGetParent() {
        checkValidEntities();
        DomainParticipant result = null;
        try {
            result = topic.getParent();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetParent", e, false));
        }
        assertTrue("Check for valid Domainparticipant object", util.objectCheck("testGetParent", result.equals(participant)));
    }

    /**
     * Test method for {@link org.omg.dds.topic.Topic#getInconsistentTopicStatus()}.
     */
    @Test
    public void testGetInconsistentTopicStatus() {
        checkValidEntities();
        InconsistentTopicStatus its = null;
        try {
            its = topic.getInconsistentTopicStatus();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetInconsistentTopicStatus", e, false));
        }
        assertTrue("Check for valid Domainparticipant object", util.objectCheck("testGetInconsistentTopicStatus", its));
    }

    /**
     * Test method for {@link org.omg.dds.topic.Topic#getStatusCondition()}.
     */
    @Test
    public void testGetStatusCondition() {
        checkValidEntities();
        StatusCondition<Topic<Msg>> result = null;
        try {
            result = topic.getStatusCondition();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetStatusCondition", e, false));
        }
        assertTrue("Check for valid StatusCondition object", util.objectCheck("testGetStatusCondition", result));
    }


    /**
     * Test method for {@link org.omg.dds.topic.TopicDescription#getTypeSupport()}.
     */
    @Test
    public void testGetTypeSupport() {
        checkValidEntities();
        TypeSupport<Msg> result = null;
        try {
            result = topic.getTypeSupport();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetTypeSupport", e, false));
        }
        assertTrue("Check for valid TypeSupport object", util.objectCheck("testGetTypeSupport", result));
    }

    /**
     * Test method for {@link org.omg.dds.topic.TopicDescription#cast()}.
     */
    @Test
    public void testCast() {
        checkValidEntities();
        TopicDescription<Object> result = null;
        try {
            result = topic.cast();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testCast", e, false));
        }
        assertTrue("Check for valid Topic object", util.objectCheck("testCast", result));
    }

    /**
     * Test method for {@link org.omg.dds.topic.TopicDescription#getTypeName()}.
     */
    @Test
    public void testGetTypeName() {
        checkValidEntities();
        String result = null;
        try {
            result = topic.getTypeName();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetTypeName", e, false));
        }
        assertTrue("Check for valid TypeName", util.objectCheck("testGetTypeName", result));
    }

    /**
     * Test method for {@link org.omg.dds.topic.TopicDescription#getName()}.
     */
    @Test
    public void testGetName() {
        checkValidEntities();
        String result = null;
        try {
            result = topic.getName();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetName", e, false));
        }
        assertTrue("Check for valid Name", util.objectCheck("testGetName", result));
        if (result != null) {
            assertTrue("Check for valid Name value", result.equals(topicName));
        }
    }

    /**
     * Test method for {@link org.omg.dds.core.DDSObject#getEnvironment()}.
     */
    @Test
    public void testGetEnvironment() {
        checkValidEntities();
        ServiceEnvironment senv = null;
        try {
            senv = topic.getEnvironment();
        } catch (Exception e) {
            fail("Exception occured while calling getEnvironment(): " + util.printException(e));
        }
        assertTrue("Check for valid ServiceEnvironment", senv != null);
    }


    /**
     * Test method for {@link org.omg.dds.core.Entity#getListener()}.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetTopicListener() {
        checkValidEntities();
        TopicListener<Msg> pl = new TopicListenerClass();
        TopicListener<Msg> pl1 = null;
        Collection<Class<? extends Status>> statuses = new HashSet<Class<? extends Status>>();
        Class<? extends Status> status = null;
        statuses.add(DataAvailableStatus.class);
        try {
            topic.setListener(pl, statuses);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetTopicListener", e, false));
        }
        try {
            pl1 = topic.getListener();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetTopicListener", e, false));
        }
        assertTrue("Check for valid TopicListener object", util.objectCheck("testGetTopicListener", pl1));
        /* reset listener to null */
        try {
            topic.setListener(null, status);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetTopicListener", e, false));
        }
        try {
            pl1 = topic.getListener();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetTopicListener", e, false));
        }
        assertTrue("Check for null TopicListener object", pl1 == null);
    }

    /**
     * Test method for
     * {@link org.omg.dds.core.Entity#setListener(java.util.EventListener)}.
     */
    @Test
    public void testTopicSetListener() {
        checkValidEntities();
        TopicListener<Msg> pl = new TopicListenerClass();
        try {
            topic.setListener(pl);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testTopicSetListener", e, false));
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.core.Entity#setListener(java.util.EventListener, java.util.Collection)}
     * .
     */
    @Test
    public void testTopicSetListenerCollectionOfClassOfQextendsStatus() {
        checkValidEntities();
        TopicListener<Msg> pl = new TopicListenerClass();
        Collection<Class<? extends Status>> statuses = new HashSet<Class<? extends Status>>();
        statuses.add(DataAvailableStatus.class);
        try {
            topic.setListener(pl, statuses);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testTopicSetListenerCollectionOfClassOfQextendsStatus", e, false));
        }
    }

    /**
     * Test method for {@link
     * org.omg.dds.core.Entity#setListener(java.util.EventListener,
     * java.lang.Class<? extends org.omg.dds.core.status.Status>[])}.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testTopicSetListenerClassOfQextendsStatusArray() {
        checkValidEntities();
        TopicListener<Msg> pl = new TopicListenerClass();
        Class<? extends Status> status = DataAvailableStatus.class;
        try {
            topic.setListener(pl, status);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testTopicSetListenerClassOfQextendsStatusArray", e, false));
        }
    }

    /**
     * Test method for {@link org.omg.dds.core.Entity#getQos()}.
     */
    @Test
    public void testGetQos() {
        checkValidEntities();
        TopicQos tq = null;
        try {
            tq = topic.getQos();
        } catch (Exception e) {
            fail("Exception occured while doing getQos: " + util.printException(e));
        }
        assertTrue("Check for valid TopicQos", tq != null);
    }

    /**
     * Test method for {@link org.omg.dds.core.Entity#setQos(org.omg.dds.core.EntityQos)}.
     */
    @Test
    public void testSetQosNull() {
        checkValidEntities();
        boolean exceptionOccured = false;
        try {
            topic.setQos(null);
        } catch (Exception e) {
            assertTrue("Check for IllegalArgumentException but got exception:" + util.printException(e), util.exceptionCheck("testTopicSetQosIllegal", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException occured failed", exceptionOccured);
    }

    /**
     * Test method for
     * {@link org.omg.dds.core.Entity#setQos(org.omg.dds.core.EntityQos)}.
     */
    @Test
    public void testSetQos() {
        checkValidEntities();
        TopicQos tq = null;
        PolicyFactory policyFactory = null;
        try {
            tq = topic.getQos();
            policyFactory = dpf.getEnvironment().getSPI().getPolicyFactory();
            tq = tq.withPolicies(policyFactory.Lifespan().withDuration(5, TimeUnit.SECONDS));
            assertTrue("Check for valid TopicQos value", tq.getLifespan().getDuration().getDuration(TimeUnit.SECONDS) == 5);
        } catch (Exception e) {
            fail("Exception occured while doing getQos(): " + util.printException(e));
        }
        try {
            topic.setQos(tq);
        } catch (Exception ex) {
            assertTrue("No exception expected but got: " + util.printException(ex), util.exceptionCheck("testTopicSetQos", ex, false));
        }
        try {
            tq = topic.getQos();
            assertTrue("Check for valid TopicQos value", tq.getLifespan().getDuration().getDuration(TimeUnit.SECONDS) == 5);
            /* restore qos */
            tq = tq.withPolicy(policyFactory.Lifespan().withDuration(Duration.infiniteDuration(env)));
            topic.setQos(tq);
        } catch (Exception ex) {
            assertTrue("No exception expected but got: " + util.printException(ex), util.exceptionCheck("testTopicSetQos", ex, false));
        }
        assertTrue("Check for valid TopicQos value", tq.getLifespan().getDuration().isInfinite());
    }

    /**
     * Test method for {@link org.omg.dds.core.Entity#enable()}.
     */
    @Test
    public void testEnable() {
        checkValidEntities();
        try {
            topic.enable();
        } catch (Exception e) {
            assertTrue("Exception occured while calling enable()" + util.printException(e), util.exceptionCheck("testEnable", e, false));
        }
    }

    /**
     * Test method for {@link org.omg.dds.core.Entity#getStatusChanges()}.
     */
    @Test
    public void testGetStatusChanges() {
        checkValidEntities();
        Set<Class<? extends Status>> statuses = null;
        try {
            statuses = topic.getStatusChanges();
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
        checkValidEntities();
        InstanceHandle ih = null;
        try {
            ih = topic.getInstanceHandle();
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
        checkValidEntities();
        try {
            topic.retain();
        } catch (Exception e) {
            assertTrue("Exception occured while calling retain()" + util.printException(e), util.exceptionCheck("testRetain", e, false));
        }
    }

}
