package unittest.pub;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.dds.core.AlreadyClosedException;
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
import org.omg.dds.core.policy.OwnershipStrength;
import org.omg.dds.core.policy.Reliability;
import org.omg.dds.core.policy.ResourceLimits;
import org.omg.dds.core.policy.TransportPriority;
import org.omg.dds.core.policy.TypeConsistencyEnforcement;
import org.omg.dds.core.policy.UserData;
import org.omg.dds.core.policy.WriterDataLifecycle;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.domain.DomainParticipantFactory;
import org.omg.dds.pub.DataWriter;
import org.omg.dds.pub.DataWriterQos;
import org.omg.dds.pub.Publisher;
import org.omg.dds.topic.Topic;

import Test.Msg;

import org.eclipse.cyclonedds.test.AbstractUtilities;

public class DataWriterQosTest {

    private final static int                DOMAIN_ID   = 123;
    private static ServiceEnvironment       env;
    private static DomainParticipantFactory dpf;
    private static AbstractUtilities        util        = AbstractUtilities.getInstance(DataWriterQosTest.class);
    private final static Properties         prop        = new Properties();
    private static DomainParticipant        participant = null;
    private static Publisher                publisher   = null;
    private static Topic<Msg>               topic       = null;
    private static DataWriter<Msg>          dataWriter  = null;
    private static String                   topicName   = "DataWriterQosTest";
    private static DataWriterQos            dwq         = null;

    @BeforeClass
    public static void init() {
        try {
            String serviceEnvironment = System.getProperty("JAVA5PSM_SERVICE_ENV");
            assertTrue("Check for valid ServiceEnvironment string", !serviceEnvironment.equals(""));
            System.setProperty(ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY, serviceEnvironment);
            env = ServiceEnvironment.createInstance(DataWriterQosTest.class.getClassLoader());
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
            topic = participant.createTopic(topicName, Msg.class);
            assertTrue("Check for valid Topic object failed", topic instanceof Topic);
            dataWriter = publisher.createDataWriter(topic);
            assertTrue("Check for valid DataWriter object failed", dataWriter instanceof DataWriter);
            dwq = dataWriter.getQos();
            assertTrue("Check for valid DataWriterQos object failed", dwq instanceof DataWriterQos);
        } catch (Exception e) {
            fail("Exception occured while initiating the DataWriterQosTest class: " + util.printException(e));
        }

    }

    private void checkValidEntities() {
        assertTrue("Check for valid DomainParticipant object failed", participant instanceof DomainParticipant);
        assertTrue("Check for valid Publisher object failed", publisher instanceof Publisher);
        assertTrue("Check for valid Topic object failed", topic instanceof Topic);
        assertTrue("Check for valid DataWriter object failed", dataWriter instanceof DataWriter);
        assertTrue("Check for valid DataWriterQos object failed", dwq instanceof DataWriterQos);
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
     * Test method for {@link org.omg.dds.pub.DataWriterQos#getDurability()}.
     */
    @Test
    public void testGetDurability() {
        checkValidEntities();
        Durability result = null;
        try {
            result = dwq.getDurability();
        } catch (Exception ex) {
            fail("Failed to get the Durability qos from the DataWriterQos");
        }
        assertTrue("Check for valid Durability object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.pub.DataWriterQos#getDurabilityService()}.
     */
    @Test
    public void testGetDurabilityService() {
        checkValidEntities();
        DurabilityService result = null;
        try {
            result = dwq.getDurabilityService();
        } catch (Exception ex) {
            assertTrue("No exception expected but got: " + util.printException(ex), util.exceptionCheck("testDWQGetDurabilityService", ex, false));
        }
        assertTrue("Check for valid DurabilityService object failed", util.objectCheck("testDWQGetDurabilityService", result));
    }

    /**
     * Test method for {@link org.omg.dds.pub.DataWriterQos#getDeadline()}.
     */
    @Test
    public void testGetDeadline() {
        checkValidEntities();
        Deadline result = null;
        try {
            result = dwq.getDeadline();
        } catch (Exception ex) {
            fail("Failed to get the Deadline qos from the DataWriterQos");
        }
        assertTrue("Check for valid Deadline object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.pub.DataWriterQos#getLatencyBudget()}.
     */
    @Test
    public void testGetLatencyBudget() {
        checkValidEntities();
        LatencyBudget result = null;
        try {
            result = dwq.getLatencyBudget();
        } catch (Exception ex) {
            fail("Failed to get the LatencyBudget qos from the DataWriterQos");
        }
        assertTrue("Check for valid LatencyBudget object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.pub.DataWriterQos#getLiveliness()}.
     */
    @Test
    public void testGetLiveliness() {
        checkValidEntities();
        Liveliness result = null;
        try {
            result = dwq.getLiveliness();
        } catch (Exception ex) {
            fail("Failed to get the Liveliness qos from the DataWriterQos");
        }
        assertTrue("Check for valid Liveliness object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.pub.DataWriterQos#getReliability()}.
     */
    @Test
    public void testGetReliability() {
        checkValidEntities();
        Reliability result = null;
        try {
            result = dwq.getReliability();
        } catch (Exception ex) {
            fail("Failed to get the Reliability qos from the DataWriterQos");
        }
        assertTrue("Check for valid Reliability object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.pub.DataWriterQos#getDestinationOrder()}.
     */
    @Test
    public void testGetDestinationOrder() {
        checkValidEntities();
        DestinationOrder result = null;
        try {
            result = dwq.getDestinationOrder();
        } catch (Exception ex) {
            fail("Failed to get the DestinationOrder qos from the DataWriterQos");
        }
        assertTrue("Check for valid DestinationOrder object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.pub.DataWriterQos#getHistory()}.
     */
    @Test
    public void testGetHistory() {
        checkValidEntities();
        History result = null;
        try {
            result = dwq.getHistory();
        } catch (Exception ex) {
            fail("Failed to get the History qos from the DataWriterQos");
        }
        assertTrue("Check for valid History object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.pub.DataWriterQos#getResourceLimits()}.
     */
    @Test
    public void testGetResourceLimits() {
        checkValidEntities();
        ResourceLimits result = null;
        try {
            result = dwq.getResourceLimits();
        } catch (Exception ex) {
            fail("Failed to get the ResourceLimits qos from the DataWriterQos");
        }
        assertTrue("Check for valid ResourceLimits object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.pub.DataWriterQos#getTransportPriority()}.
     */
    @Test
    public void testGetTransportPriority() {
        checkValidEntities();
        TransportPriority result = null;
        try {
            result = dwq.getTransportPriority();
        } catch (Exception ex) {
            fail("Failed to get the TransportPriority qos from the DataWriterQos");
        }
        assertTrue("Check for valid TransportPriority object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.pub.DataWriterQos#getLifespan()}.
     */
    @Test
    public void testGetLifespan() {
        checkValidEntities();
        Lifespan result = null;
        try {
            result = dwq.getLifespan();
        } catch (Exception ex) {
            fail("Failed to get the Lifespan qos from the DataWriterQos");
        }
        assertTrue("Check for valid Lifespan object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.pub.DataWriterQos#getUserData()}.
     */
    @Test
    public void testGetUserData() {
        checkValidEntities();
        UserData result = null;
        try {
            result = dwq.getUserData();
        } catch (Exception ex) {
            fail("Failed to get the UserData qos from the DataWriterQos");
        }
        assertTrue("Check for valid UserData object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.pub.DataWriterQos#getOwnership()}.
     */
    @Test
    public void testGetOwnership() {
        checkValidEntities();
        Ownership result = null;
        try {
            result = dwq.getOwnership();
        } catch (Exception ex) {
            fail("Failed to get the Ownership qos from the DataWriterQos");
        }
        assertTrue("Check for valid Ownership object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.pub.DataWriterQos#getOwnershipStrength()}.
     */
    @Test
    public void testGetOwnershipStrength() {
        checkValidEntities();
        OwnershipStrength result = null;
        try {
            result = dwq.getOwnershipStrength();
            assertTrue("Check for valid OwnershipStrength object", result != null);
            result = result.withValue(10);
            assertTrue("Check for valid OwnershipStrength object", result != null);
            assertTrue("Check for valid value", result.getValue() == 10);
        } catch (Exception ex) {
            fail("Failed to get the OwnershipStrength qos from the DataWriterQos");
        }
        assertTrue("Check for valid OwnershipStrength object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.pub.DataWriterQos#getWriterDataLifecycle()}.
     */
    @Test
    public void testGetWriterDataLifecycle() {
        checkValidEntities();
        WriterDataLifecycle result = null;
        try {
            result = dwq.getWriterDataLifecycle();
            assertTrue("Check for valid WriterDataLifecycle object", result != null);
            result = result.withAutDisposeUnregisteredInstances(true);
            assertTrue("Check for valid WriterDataLifecycle object", result != null);
            assertTrue("Check for valid value", result.isAutDisposeUnregisteredInstances());

        } catch (Exception ex) {
            fail("Failed to get the WriterDataLifecycle qos from the DataWriterQos");
        }
        assertTrue("Check for valid WriterDataLifecycle object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.pub.DataWriterQos#getRepresentation()}.
     */
    @Test
    public void testGetRepresentation() {
        checkValidEntities();
        DataRepresentation result = null;
        try {
            result = dwq.getRepresentation();
        } catch (Exception ex) {
            fail("Failed to get the DataRepresentation qos from the DataWriterQos");
        }
        assertTrue("Check for valid DataRepresentation object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.pub.DataWriterQos#getTypeConsistency()}.
     */
    @Test
    public void testGetTypeConsistency() {
        checkValidEntities();
        TypeConsistencyEnforcement result = null;
        try {
            result = dwq.getTypeConsistency();
        } catch (Exception ex) {
            fail("Failed to get the TypeConsistencyEnforcement qos from the DataWriterQos");
        }
        assertTrue("Check for valid TypeConsistencyEnforcement object failed", result != null);
    }
}
