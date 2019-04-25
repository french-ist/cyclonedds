/**
 *
 */
package unittest.topic;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.dds.core.AlreadyClosedException;
import org.omg.dds.core.InstanceHandle;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.policy.DataRepresentation;
import org.omg.dds.core.policy.Deadline;
import org.omg.dds.core.policy.DestinationOrder;
import org.omg.dds.core.policy.Durability;
import org.omg.dds.core.policy.DurabilityService;
import org.omg.dds.core.policy.History;
import org.omg.dds.core.policy.LatencyBudget;
import org.omg.dds.core.policy.Lifespan;
import org.omg.dds.core.policy.Liveliness;
import org.omg.dds.core.policy.Ownership;
import org.omg.dds.core.policy.Reliability;
import org.omg.dds.core.policy.ResourceLimits;
import org.omg.dds.core.policy.TopicData;
import org.omg.dds.core.policy.TransportPriority;
import org.omg.dds.core.policy.TypeConsistencyEnforcement;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.domain.DomainParticipantFactory;
import org.omg.dds.pub.DataWriter;
import org.omg.dds.pub.Publisher;
import org.omg.dds.sub.DataReader;
import org.omg.dds.sub.Subscriber;
import org.omg.dds.topic.BuiltinTopicKey;
import org.omg.dds.topic.Topic;
import org.omg.dds.topic.TopicBuiltinTopicData;
import org.omg.dds.type.typeobject.TypeObject;

import Test.Msg;

import org.eclipse.cyclonedds.test.AbstractUtilities;

public class TopicBuiltinTopicDataopenspliceTest {

    private final static int                DOMAIN_ID   = 123;
    private static ServiceEnvironment       env;
    private static DomainParticipantFactory dpf;
    private static AbstractUtilities        util        = AbstractUtilities.getInstance(TopicBuiltinTopicDataopenspliceTest.class);
    private final static Properties         prop        = new Properties();
    private static DomainParticipant        participant = null;
    private static Publisher                publisher   = null;
    private static Subscriber               subscriber  = null;
    private static Topic<Msg>               topic       = null;
    private static DataReader<Msg>          dataReader  = null;
    private static String                   topicName   = "TopicBuiltinTopicDataTest";
    private static TopicBuiltinTopicData    tbtd        = null;

    @BeforeClass
    public static void init() {
        try {
            String serviceEnvironment = System.getProperty("JAVA5PSM_SERVICE_ENV");
            assertTrue("Check for valid ServiceEnvironment string", !serviceEnvironment.equals(""));
            System.setProperty(ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY, serviceEnvironment);
            env = ServiceEnvironment.createInstance(TopicBuiltinTopicDataopenspliceTest.class.getClassLoader());
            assertTrue("Check for valid ServiceEnvironment object failed", env instanceof ServiceEnvironment);
            dpf = DomainParticipantFactory.getInstance(env);
            assertTrue("Check for valid DomainParticipantFactory object failed", dpf instanceof DomainParticipantFactory);
            prop.setProperty("domainid", new Integer(DOMAIN_ID).toString());
            assertTrue("Check is deamon is correctly started", util.beforeClass(prop));
            participant = dpf.createParticipant(DOMAIN_ID);
            assertTrue("Check for valid DomainParticipant object failed", participant instanceof DomainParticipant);
            assertTrue("Check for valid DomainParticipant with id " + DOMAIN_ID, participant.getDomainId() == DOMAIN_ID);
            publisher = participant.createPublisher();
            assertTrue("Check for valid Publisher object failed", publisher instanceof Publisher);
            subscriber = participant.createSubscriber();
            assertTrue("Check for valid Subscriber object failed", subscriber instanceof Subscriber);
            topic = participant.createTopic(topicName, Msg.class);
            assertTrue("Check for valid Topic object failed", topic instanceof Topic);
            dataReader = subscriber.createDataReader(topic);
            assertTrue("Check for valid DataReader object failed", dataReader instanceof DataReader);
            DataWriter<Msg> dw = publisher.createDataWriter(topic);
            assertTrue("Check for valid DataWriter object failed", dw instanceof DataWriter);
            long startTime = System.currentTimeMillis();
            /* wait at max 10 seconds for the topic */
            while (tbtd == null && ((System.currentTimeMillis() - startTime) < 10000)) {
                Set<InstanceHandle> subscriptions = participant.getDiscoveredTopics();
                for (InstanceHandle ih : subscriptions) {
                    TopicBuiltinTopicData data = participant.getDiscoveredTopicData(ih);
                    if (data != null && data.getName().equals(topicName)) {
                        tbtd = data;
                    }
                }
                Thread.sleep(100);
            }
            assertTrue("Check for valid TopicBuiltinTopicData object failed", tbtd != null);
        } catch (Exception e) {
            fail("Exception occured while initiating the TopicBuiltinTopicDataTest class: " + util.printException(e));
        }

    }

    private void checkValidEntities() {
        assertTrue("Check for valid DomainParticipant object failed", participant instanceof DomainParticipant);
        assertTrue("Check for valid Publisher object failed", publisher instanceof Publisher);
        assertTrue("Check for valid Subscriber object failed", subscriber instanceof Subscriber);
        assertTrue("Check for valid Topic object failed", topic instanceof Topic);
        assertTrue("Check for valid DataReader object failed", dataReader instanceof DataReader);
        assertTrue("Check for valid TopicBuiltinTopicData object failed", tbtd != null);
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
     * Test method for {@link org.omg.dds.topic.TopicBuiltinTopicData#getKey()}.
     */
    @Test
    public void testGetKey() {
        checkValidEntities();
        BuiltinTopicKey result = null;
        BuiltinTopicKey clone = null;
        try {
            result = tbtd.getKey();
            assertTrue("Check for valid BuiltinTopicKey object", result != null);
            clone = result.clone();
            assertTrue("Check for valid BuiltinTopicKey object", clone != null);
            clone.copyFrom(result);
            assertTrue("Check for valid BuiltinTopicKey object", clone != null);
            assertTrue("Check for valid value", result.getValue() != null);
            assertTrue("Check for valid env value", result.getEnvironment() != null);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetKey", e, false));
        }
        assertTrue("Check for valid BuiltinTopicKey object", util.objectCheck("testGetKey", result));
    }


    /**
     * Test method for {@link org.omg.dds.topic.TopicBuiltinTopicData#getName()}
     * .
     */
    @Test
    public void testGetName() {
        checkValidEntities();
        String result = null;
        try {
            result = tbtd.getName();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetName", e, false));
        }
        assertTrue("Check for valid Name value", util.objectCheck("testGetName", result));
        if (result != null) {
            assertTrue("Check for valid topicName", result.equals(topicName));
        }
    }

    /**
     * Test method for {@link org.omg.dds.topic.TopicBuiltinTopicData#getTypeName()}.
     */
    @Test
    public void testGetTypeName() {
        checkValidEntities();
        String result = null;
        try {
            result = tbtd.getTypeName();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetTopicName", e, false));
        }
        assertTrue("Check for valid Name value", util.objectCheck("testGetTopicName", result));
        if (result != null) {
            assertTrue("Check for valid topicName", result.equals(topic.getTypeName()));
        }
    }

    /**
     * Test method for {@link org.omg.dds.topic.TopicBuiltinTopicData#getEquivalentTypeName()}.
     */
    @Test
    public void testGetEquivalentTypeName() {
        checkValidEntities();
        List<String> result = null;
        try {
            result = tbtd.getEquivalentTypeName();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetEquivalentTypeName", e, false));
        }
        assertTrue("Check for valid Name value", util.objectCheck("testGetEquivalentTypeName", result));
    }

    /**
     * Test method for {@link org.omg.dds.topic.TopicBuiltinTopicData#getBaseTypeName()}.
     */
    @Test
    public void testGetBaseTypeName() {
        checkValidEntities();
        List<String> result = null;
        try {
            result = tbtd.getBaseTypeName();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetBaseTypeName", e, false));
        }
        assertTrue("Check for valid Name value", util.objectCheck("testGetBaseTypeName", result));
    }

    /**
     * Test method for {@link org.omg.dds.topic.TopicBuiltinTopicData#getType()}.
     */
    @Test
    public void testGetType() {
        checkValidEntities();
        TypeObject result = null;
        try {
            result = tbtd.getType();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetType", e, false));
        }
        assertTrue("Check for valid TypeObject object", util.objectCheck("testGetType", result));
    }

    /**
     * Test method for {@link org.omg.dds.topic.TopicBuiltinTopicData#getDurability()}.
     */
    @Test
    public void testGetDurability() {
        checkValidEntities();
        Durability result = null;
        try {
            result = tbtd.getDurability();
        } catch (Exception ex) {
            fail("Failed to get the Durability qos from the TopicBuiltinTopicData");
        }
        assertTrue("Check for valid Durability object failed", result != null);
    }

    /**
     * Test method for
     * {@link org.omg.dds.topic.TopicBuiltinTopicData#getDurabilityService()}.
     */
    @Test
    public void testGetDurabilityService() {
        checkValidEntities();
        DurabilityService result = null;
        try {
            result = tbtd.getDurabilityService();
        } catch (Exception e) {
            fail("Failed to get the DurabilityService qos from the TopicBuiltinTopicData");
        }
        assertTrue("Check for valid DurabilityService object failed", result != null);
    }

    /**
     * Test method for
     * {@link org.omg.dds.topic.TopicBuiltinTopicData#getDeadline()}.
     */
    @Test
    public void testGetDeadline() {
        checkValidEntities();
        Deadline result = null;
        try {
            result = tbtd.getDeadline();
        } catch (Exception ex) {
            fail("Failed to get the Deadline qos from the TopicBuiltinTopicData");
        }
        assertTrue("Check for valid Deadline object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.topic.TopicBuiltinTopicData#getLatencyBudget()}.
     */
    @Test
    public void testGetLatencyBudget() {
        checkValidEntities();
        LatencyBudget result = null;
        try {
            result = tbtd.getLatencyBudget();
        } catch (Exception ex) {
            fail("Failed to get the LatencyBudget qos from the TopicBuiltinTopicData");
        }
        assertTrue("Check for valid LatencyBudget object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.topic.TopicBuiltinTopicData#getLiveliness()}.
     */
    @Test
    public void testGetLiveliness() {
        checkValidEntities();
        Liveliness result = null;
        try {
            result = tbtd.getLiveliness();
        } catch (Exception ex) {
            fail("Failed to get the Liveliness qos from the TopicBuiltinTopicData");
        }
        assertTrue("Check for valid Liveliness object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.topic.TopicBuiltinTopicData#getReliability()}.
     */
    @Test
    public void testGetReliability() {
        checkValidEntities();
        Reliability result = null;
        try {
            result = tbtd.getReliability();
        } catch (Exception ex) {
            fail("Failed to get the Reliability qos from the TopicBuiltinTopicData");
        }
        assertTrue("Check for valid Reliability object failed", result != null);
    }

    /**
     * Test method for
     * {@link org.omg.dds.topic.TopicBuiltinTopicData#getTransportPriority()}.
     */
    @Test
    public void testGetTransportPriority() {
        checkValidEntities();
        TransportPriority result = null;
        try {
            result = tbtd.getTransportPriority();
        } catch (Exception ex) {
            fail("Failed to get the TransportPriority qos from the TopicBuiltinTopicData");
        }
        assertTrue("Check for valid TransportPriority object failed", result != null);
    }

    /**
     * Test method for
     * {@link org.omg.dds.topic.TopicBuiltinTopicData#getLifespan()}.
     */
    @Test
    public void testGetLifespan() {
        checkValidEntities();
        Lifespan result = null;
        try {
            result = tbtd.getLifespan();
        } catch (Exception ex) {
            fail("Failed to get the Lifespan qos from the TopicBuiltinTopicData");
        }
        assertTrue("Check for valid Lifespan object failed", result != null);
    }


    /**
     * Test method for
     * {@link org.omg.dds.topic.TopicBuiltinTopicData#getDestinationOrder()}.
     */
    @Test
    public void testGetDestinationOrder() {
        checkValidEntities();
        DestinationOrder result = null;
        try {
            result = tbtd.getDestinationOrder();
        } catch (Exception ex) {
            fail("Failed to get the DestinationOrder qos from the TopicBuiltinTopicData");
        }
        assertTrue("Check for valid DestinationOrder object failed", result != null);
    }

    /**
     * Test method for
     * {@link org.omg.dds.topic.TopicBuiltinTopicData#getHistory()}.
     */
    @Test
    public void testGetHistory() {
        checkValidEntities();
        History result = null;
        try {
            result = tbtd.getHistory();
        } catch (Exception ex) {
            fail("Failed to get the History qos from the TopicBuiltinTopicData");
        }
        assertTrue("Check for valid History object failed", result != null);
    }

    /**
     * Test method for
     * {@link org.omg.dds.topic.TopicBuiltinTopicData#getHistory()}.
     */
    @Test
    public void testGetResourceLimits() {
        checkValidEntities();
        ResourceLimits result = null;
        try {
            result = tbtd.getResourceLimits();
        } catch (Exception ex) {
            fail("Failed to get the ResourceLimits qos from the TopicBuiltinTopicData");
        }
        assertTrue("Check for valid ResourceLimits object failed", result != null);
    }

    /**
     * Test method for
     * {@link org.omg.dds.topic.TopicBuiltinTopicData#getOwnership()}.
     */
    @Test
    public void testGetOwnership() {
        checkValidEntities();
        Ownership result = null;
        try {
            result = tbtd.getOwnership();
        } catch (Exception ex) {
            fail("Failed to get the Ownership qos from the TopicBuiltinTopicData");
        }
        assertTrue("Check for valid Ownership object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.topic.TopicBuiltinTopicData#getTopicData()}.
     */
    @Test
    public void testGetTopicData() {
        checkValidEntities();
        TopicData result = null;
        try {
            result = tbtd.getTopicData();
        } catch (Exception ex) {
            fail("Failed to get the TopicData qos from the TopicBuiltinTopicData");
        }
        assertTrue("Check for valid TopicData object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.topic.TopicBuiltinTopicData#getRepresentation()}.
     */
    @Test
    public void testGetRepresentation() {
        checkValidEntities();
        DataRepresentation result = null;
        try {
            result = tbtd.getRepresentation();
        } catch (Exception ex) {
            fail("Failed to get the DataRepresentation qos from the TopicBuiltinTopicData");
        }
        assertTrue("Check for valid DataRepresentation object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.topic.TopicBuiltinTopicData#getTypeConsistency()}.
     */
    @Test
    public void testGetTypeConsistency() {
        checkValidEntities();
        TypeConsistencyEnforcement result = null;
        try {
            result = tbtd.getTypeConsistency();
        } catch (Exception ex) {
            fail("Failed to get the TypeConsistencyEnforcement qos from the TopicBuiltinTopicData");
        }
        assertTrue("Check for valid TypeConsistencyEnforcement object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.topic.TopicBuiltinTopicData#copyFrom(org.omg.dds.topic.TopicBuiltinTopicData)}.
     */
    @Test
    public void testCopyFrom() {
        checkValidEntities();
        TopicBuiltinTopicData result = null;
        TopicBuiltinTopicData clone = tbtd;
        try {
            result = tbtd.clone();
            result.copyFrom(clone);
        } catch (Exception ex) {
            fail("Failed to get the TopicBuiltinTopicData object");
        }
        assertTrue("Check for valid TopicBuiltinTopicData object failed", result != null);
    }

    /**
     * Test method for
     * {@link org.omg.dds.topic.TopicBuiltinTopicData#copyFrom(org.omg.dds.topic.TopicBuiltinTopicData)}
     * .
     */
    @Test
    public void testCopyFromNull() {
        checkValidEntities();
        TopicBuiltinTopicData result = tbtd;
        boolean exceptionOccured = false;
        try {
            result.copyFrom(null);
        } catch (Exception e) {
            assertTrue("Expected IllegalArgumentException but got: " + util.printException(e), util.exceptionCheck("testCopyFromNull", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException has occured failed", exceptionOccured);
    }

    /**
     * Test method for {@link org.omg.dds.topic.TopicBuiltinTopicData#clone()}.
     */
    @Test
    public void testClone() {
        checkValidEntities();
        TopicBuiltinTopicData result = null;
        try {
            result = tbtd.clone();
        } catch (Exception ex) {
            fail("Failed to get the TopicBuiltinTopicData object");
        }
        assertTrue("Check for valid TopicBuiltinTopicData object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.core.DDSObject#getEnvironment()}.
     */
    @Test
    public void testGetEnvironment() {
        checkValidEntities();
        ServiceEnvironment senv = null;
        try {
            senv = tbtd.getEnvironment();
        } catch (Exception e) {
            fail("Exception occured while calling getEnvironment(): " + util.printException(e));
        }
        assertTrue("Check for valid ServiceEnvironment", senv != null);
    }

}
