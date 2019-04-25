/**
 *
 */
package unittest.sub;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.dds.core.AlreadyClosedException;
import org.omg.dds.core.Duration;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.policy.DataRepresentation;
import org.omg.dds.core.policy.Deadline;
import org.omg.dds.core.policy.DestinationOrder;
import org.omg.dds.core.policy.Durability;
import org.omg.dds.core.policy.History;
import org.omg.dds.core.policy.LatencyBudget;
import org.omg.dds.core.policy.Liveliness;
import org.omg.dds.core.policy.Ownership;
import org.omg.dds.core.policy.ReaderDataLifecycle;
import org.omg.dds.core.policy.Reliability;
import org.omg.dds.core.policy.ResourceLimits;
import org.omg.dds.core.policy.TimeBasedFilter;
import org.omg.dds.core.policy.TypeConsistencyEnforcement;
import org.omg.dds.core.policy.UserData;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.domain.DomainParticipantFactory;
import org.omg.dds.sub.DataReader;
import org.omg.dds.sub.DataReaderQos;
import org.omg.dds.sub.Subscriber;
import org.omg.dds.topic.Topic;

import Test.Msg;

import org.eclipse.cyclonedds.test.AbstractUtilities;

public class DataReaderQosTest {

    private final static int                DOMAIN_ID   = 123;
    private static ServiceEnvironment       env;
    private static DomainParticipantFactory dpf;
    private static AbstractUtilities        util        = AbstractUtilities.getInstance(DataReaderQosTest.class);
    private final static Properties         prop        = new Properties();
    private static DomainParticipant        participant = null;
    private static Subscriber               subscriber  = null;
    private static Topic<Msg>               topic       = null;
    private static DataReader<Msg>          dataReader  = null;
    private static String                   topicName   = "DataReaderQosTest";
    private static DataReaderQos            drq         = null;

    @BeforeClass
    public static void init() {
        try {
            String serviceEnvironment = System.getProperty("JAVA5PSM_SERVICE_ENV");
            assertTrue("Check for valid ServiceEnvironment string", !serviceEnvironment.equals(""));
            System.setProperty(ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY, serviceEnvironment);
            env = ServiceEnvironment.createInstance(DataReaderQosTest.class.getClassLoader());
            assertTrue("Check for valid ServiceEnvironment object failed", env instanceof ServiceEnvironment);
            dpf = DomainParticipantFactory.getInstance(env);
            assertTrue("Check for valid DomainParticipantFactory object failed", dpf instanceof DomainParticipantFactory);
            prop.setProperty("domainid", new Integer(DOMAIN_ID).toString());
            assertTrue("Check is deamon is correctly started", util.beforeClass(prop));
            participant = dpf.createParticipant(DOMAIN_ID);
            assertTrue("Check for valid DomainParticipant object failed", participant instanceof DomainParticipant);
            assertTrue("Check for valid DomainParticipant with id " + DOMAIN_ID, participant.getDomainId() == DOMAIN_ID);
            subscriber = participant.createSubscriber();
            assertTrue("Check for valid Publisher object failed", subscriber instanceof Subscriber);
            topic = participant.createTopic(topicName, Msg.class);
            assertTrue("Check for valid Topic object failed", topic instanceof Topic);
            dataReader = subscriber.createDataReader(topic);
            assertTrue("Check for valid DataWriter object failed", dataReader instanceof DataReader);
            drq = dataReader.getQos();
            assertTrue("Check for valid DataWriterQos object failed", drq instanceof DataReaderQos);
        } catch (Exception e) {
            fail("Exception occured while initiating the DataReaderQosTest class: " + util.printException(e));
        }

    }

    private void checkValidEntities() {
        assertTrue("Check for valid DomainParticipant object failed", participant instanceof DomainParticipant);
        assertTrue("Check for valid Subscriber object failed", subscriber instanceof Subscriber);
        assertTrue("Check for valid Topic object failed", topic instanceof Topic);
        assertTrue("Check for valid DataReader object failed", dataReader instanceof DataReader);
        assertTrue("Check for valid DataWriterQos object failed", drq instanceof DataReaderQos);
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
     * Test method for {@link org.omg.dds.sub.DataReaderQos#getDurability()}.
     */
    @Test
    public void testGetDurability() {
        checkValidEntities();
        Durability result = null;
        try {
            result = drq.getDurability();
        } catch (Exception ex) {
            fail("Failed to get the Durability qos from the DataReaderQos");
        }
        assertTrue("Check for valid Durability object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.sub.DataReaderQos#getReliability()}.
     */
    @Test
    public void testGetReliability() {
        checkValidEntities();
        Reliability result = null;
        try {
            result = drq.getReliability();
        } catch (Exception ex) {
            fail("Failed to get the Reliability qos from the DataReaderQos");
        }
        assertTrue("Check for valid Reliability object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.sub.DataReaderQos#getDeadline()}.
     */
    @Test
    public void testGetDeadline() {
        checkValidEntities();
        Deadline result = null;
        try {
            result = drq.getDeadline();
        } catch (Exception ex) {
            fail("Failed to get the Deadline qos from the DataReaderQos");
        }
        assertTrue("Check for valid Deadline object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.sub.DataReaderQos#getLatencyBudget()}.
     */
    @Test
    public void testGetLatencyBudget() {
        checkValidEntities();
        LatencyBudget result = null;
        try {
            result = drq.getLatencyBudget();
        } catch (Exception ex) {
            fail("Failed to get the LatencyBudget qos from the DataReaderQos");
        }
        assertTrue("Check for valid LatencyBudget object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.sub.DataReaderQos#getLiveliness()}.
     */
    @Test
    public void testGetLiveliness() {
        checkValidEntities();
        Liveliness result = null;
        try {
            result = drq.getLiveliness();
        } catch (Exception ex) {
            fail("Failed to get the Liveliness qos from the DataReaderQos");
        }
        assertTrue("Check for valid Liveliness object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.sub.DataReaderQos#getDestinationOrder()}.
     */
    @Test
    public void testGetDestinationOrder() {
        checkValidEntities();
        DestinationOrder result = null;
        try {
            result = drq.getDestinationOrder();
        } catch (Exception ex) {
            fail("Failed to get the DestinationOrder qos from the DataReaderQos");
        }
        assertTrue("Check for valid DestinationOrder object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.sub.DataReaderQos#getHistory()}.
     */
    @Test
    public void testGetHistory() {
        checkValidEntities();
        History result = null;
        try {
            result = drq.getHistory();
        } catch (Exception ex) {
            fail("Failed to get the History qos from the DataReaderQos");
        }
        assertTrue("Check for valid History object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.sub.DataReaderQos#getResourceLimits()}.
     */
    @Test
    public void testGetResourceLimits() {
        checkValidEntities();
        ResourceLimits result = null;
        try {
            result = drq.getResourceLimits();
        } catch (Exception ex) {
            fail("Failed to get the ResourceLimits qos from the DataReaderQos");
        }
        assertTrue("Check for valid ResourceLimits object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.sub.DataReaderQos#getUserData()}.
     */
    @Test
    public void testGetUserData() {
        checkValidEntities();
        UserData result = null;
        try {
            result = drq.getUserData();
        } catch (Exception ex) {
            fail("Failed to get the UserData qos from the DataReaderQos");
        }
        assertTrue("Check for valid UserData object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.sub.DataReaderQos#getOwnership()}.
     */
    @Test
    public void testGetOwnership() {
        checkValidEntities();
        Ownership result = null;
        try {
            result = drq.getOwnership();
        } catch (Exception ex) {
            fail("Failed to get the Ownership qos from the DataReaderQos");
        }
        assertTrue("Check for valid Ownership object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.sub.DataReaderQos#getTimeBasedFilter()}.
     */
    @Test
    public void testGetTimeBasedFilter() {
        checkValidEntities();
        TimeBasedFilter result = null;
        try {
            result = drq.getTimeBasedFilter();
            assertTrue("Check for valid TimeBasedFilter object failed", result != null);
            result = result.withMinimumSeparation(Duration.newDuration(5, TimeUnit.SECONDS, env));
            assertTrue("Check for valid TimeBasedFilter object", result != null);
            Duration d = result.getMinimumSeparation();
            assertTrue("Check for valid Duration object", d.getDuration(TimeUnit.SECONDS) == 5);
            result = result.withMinimumSeparation(3, TimeUnit.SECONDS);
            assertTrue("Check for valid TimeBasedFilter object", result != null);
            d = result.getMinimumSeparation();
            assertTrue("Check for valid Duration object", d.getDuration(TimeUnit.SECONDS) == 3);
        } catch (Exception ex) {
            fail("Failed to get the TimeBasedFilter qos from the DataReaderQos");
        }
        assertTrue("Check for valid TimeBasedFilter object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.sub.DataReaderQos#getReaderDataLifecycle()}.
     */
    @Test
    public void testGetReaderDataLifecycle() {
        checkValidEntities();
        ReaderDataLifecycle result = null;
        try {
            result = drq.getReaderDataLifecycle();
            assertTrue("Check for valid ReaderDataLifecycle object failed", result != null);
            result = result.withAutoPurgeDisposedSamplesDelay(Duration.newDuration(5, TimeUnit.SECONDS, env));
            assertTrue("Check for valid ReaderDataLifecycle object", result != null);
            Duration d = result.getAutoPurgeDisposedSamplesDelay();
            assertTrue("Check for valid Duration object", d.getDuration(TimeUnit.SECONDS) == 5);
            result = result.withAutoPurgeDisposedSamplesDelay(3, TimeUnit.SECONDS);
            assertTrue("Check for valid ReaderDataLifecycle object", result != null);
            d = result.getAutoPurgeDisposedSamplesDelay();
            assertTrue("Check for valid Duration object", d.getDuration(TimeUnit.SECONDS) == 3);

            result = result.withAutoPurgeNoWriterSamplesDelay(Duration.newDuration(5, TimeUnit.SECONDS, env));
            assertTrue("Check for valid ReaderDataLifecycle object", result != null);
            d = result.getAutoPurgeNoWriterSamplesDelay();
            assertTrue("Check for valid Duration object", d.getDuration(TimeUnit.SECONDS) == 5);
            result = result.withAutoPurgeNoWriterSamplesDelay(3, TimeUnit.SECONDS);
            assertTrue("Check for valid ReaderDataLifecycle object", result != null);
            d = result.getAutoPurgeNoWriterSamplesDelay();
            assertTrue("Check for valid Duration object", d.getDuration(TimeUnit.SECONDS) == 3);

        } catch (Exception ex) {
            fail("Failed to get the ReaderDataLifecycle qos from the DataReaderQos");
        }
        assertTrue("Check for valid ReaderDataLifecycle object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.sub.DataReaderQos#getRepresentation()}.
     */
    @Test
    public void testGetRepresentation() {
        checkValidEntities();
        DataRepresentation result = null;
        try {
            result = drq.getRepresentation();
        } catch (Exception ex) {
            fail("Failed to get the DataRepresentation qos from the DataReaderQos");
        }
        assertTrue("Check for valid DataRepresentation object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.sub.DataReaderQos#getTypeConsistency()}.
     */
    @Test
    public void testGetTypeConsistency() {
        checkValidEntities();
        TypeConsistencyEnforcement result = null;
        try {
            result = drq.getTypeConsistency();
        } catch (Exception ex) {
            fail("Failed to get the TypeConsistencyEnforcement qos from the DataReaderQos");
        }
        assertTrue("Check for valid TypeConsistencyEnforcement object failed", result != null);
    }
}
