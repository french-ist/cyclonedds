package unittest.topic;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
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
import org.omg.dds.core.policy.Durability.Kind;
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
import org.omg.dds.topic.Topic;
import org.omg.dds.topic.TopicQos;

import Test.Msg;

import org.eclipse.cyclonedds.test.AbstractUtilities;

public class TopicQosTest {

    private final static int                DOMAIN_ID   = 123;
    private static ServiceEnvironment       env;
    private static DomainParticipantFactory dpf;
    private static AbstractUtilities        util        = AbstractUtilities.getInstance(TopicQosTest.class);
    private final static Properties         prop        = new Properties();
    private static DomainParticipant        participant = null;
    private static Topic<Msg>               topic       = null;
    private static TopicQos                 tq          = null;
    private static String                   topicName   = "TopicQosTest";

    @BeforeClass
    public static void init() {
        try {
            String serviceEnvironment = System.getProperty("JAVA5PSM_SERVICE_ENV");
            assertTrue("Check for valid ServiceEnvironment string", !serviceEnvironment.equals(""));
            System.setProperty(ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY, serviceEnvironment);
            env = ServiceEnvironment.createInstance(TopicQosTest.class.getClassLoader());
            assertTrue("Check for valid ServiceEnvironment object", env instanceof ServiceEnvironment);
            dpf = DomainParticipantFactory.getInstance(env);
            assertTrue("Check for valid DomainParticipantFactory object", dpf instanceof DomainParticipantFactory);
            prop.setProperty("domainid", new Integer(DOMAIN_ID).toString());
            assertTrue("Check is deamon is correctly started", util.beforeClass(prop));
            participant = dpf.createParticipant(DOMAIN_ID);
            assertTrue("Check for valid DomainParticipant object", participant instanceof DomainParticipant);
            assertTrue("Check for valid DomainParticipant with id " + DOMAIN_ID, participant.getDomainId() == DOMAIN_ID);
            topic = participant.createTopic(topicName, Msg.class);
            assertTrue("Check for valid Topic object", topic instanceof Topic);
            tq = topic.getQos();
            assertTrue("Check for valid TopicQos object", tq instanceof TopicQos);
        } catch (Exception e) {
            fail("Exception occured while initiating the TopicQosTest class: " + util.printException(e));
        }

    }

    private void checkValidEntities() {
        assertTrue("Check for valid DomainParticipant object", participant instanceof DomainParticipant);
        assertTrue("Check for valid Topic object", topic instanceof Topic);
        assertTrue("Check for valid TopicQos object", tq instanceof TopicQos);
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
     * Test method for {@link org.omg.dds.topic.TopicQos#getTopicData()}.
     */
    //@tTest
    public void testGetTopicData() {
        checkValidEntities();
        TopicData result = null;
        try {
            result = tq.getTopicData();
            byte[] b = new String("Foo").getBytes();
            result = result.withValue(b, 0, b.length);
            assertTrue("Check for valid TopicData object", result != null);
            assertTrue("Check for valid TopicData value", new String(result.getValue()).equals("Foo"));
        } catch (Exception ex) {
            fail("Failed to get the TopicData qos from the TopicQos");
        }
        assertTrue("Check for valid TopicData object", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.topic.TopicQos#getDurability()}.
     */
    //@tTest
    public void testGetDurability() {
        checkValidEntities();
        Durability result = null;
        try {
            result = tq.getDurability();
            assertTrue("Check for valid Durability object", result != null);
            result = result.withPersistent();
            assertTrue("Check for valid Durability object", result != null);
            assertTrue("Check for valid Durability.Kind", result.getKind() == Durability.Kind.PERSISTENT);
            result = result.withTransient();
            assertTrue("Check for valid Durability object", result != null);
            assertTrue("Check for valid Durability.Kind", result.getKind() == Durability.Kind.TRANSIENT);
            result = result.withTransientLocal();
            assertTrue("Check for valid Durability object", result != null);
            assertTrue("Check for valid Durability.Kind", result.getKind() == Durability.Kind.TRANSIENT_LOCAL);
            result = result.withVolatile();
            assertTrue("Check for valid Durability object", result != null);
            assertTrue("Check for valid Durability.Kind", result.getKind() == Durability.Kind.VOLATILE);
            result = result.withKind(Kind.PERSISTENT);
            assertTrue("Check for valid Durability object", result != null);
        } catch (Exception ex) {
            fail("Failed to get the Durability qos from the TopicQos");
        }
        assertTrue("Check for valid Durability Kind", result.getKind() == Durability.Kind.valueOf("PERSISTENT"));
    }

    /**
     * Test method for {@link org.omg.dds.topic.TopicQos#getDurabilityService()}.
     */
    //@tTest
    public void testGetDurabilityService() {
        checkValidEntities();
        DurabilityService result = null;
        try {
            result = tq.getDurabilityService();
            assertTrue("Check for valid DurabilityService object", result != null);
            result = result.withServiceCleanupDelay(Duration.newDuration(5, TimeUnit.SECONDS, env));
            assertTrue("Check for valid DurabilityService object", result != null);
            Duration d = result.getServiceCleanupDelay();
            assertTrue("Check for valid Duration object", d.getDuration(TimeUnit.SECONDS) == 5);
            result = result.withServiceCleanupDelay(6, TimeUnit.SECONDS);
            assertTrue("Check for valid DurabilityService object", result != null);
            d = result.getServiceCleanupDelay();
            assertTrue("Check for valid Duration object", d.getDuration(TimeUnit.SECONDS) == 6);
            result = result.withHistoryKind(History.Kind.KEEP_ALL);
            assertTrue("Check for valid DurabilityService object", result != null);
            assertTrue("Check for valid DurabilityService kind", result.getHistoryKind() == History.Kind.KEEP_ALL);
            result = result.withHistoryDepth(10);
            assertTrue("Check for valid DurabilityService object", result != null);
            assertTrue("Check for valid history depth", result.getHistoryDepth() == 10);
            result = result.withMaxInstances(12);
            assertTrue("Check for valid DurabilityService object", result != null);
            assertTrue("Check for valid max instances", result.getMaxInstances() == 12);
            result = result.withMaxSamples(3);
            assertTrue("Check for valid DurabilityService object", result != null);
            assertTrue("Check for valid max samples", result.getMaxSamples() == 3);
            result = result.withMaxSamplesPerInstance(4);
            assertTrue("Check for valid DurabilityService object", result != null);
            assertTrue("Check for valid max samples", result.getMaxSamplesPerInstance() == 4);
        } catch (Exception ex) {
            fail("Failed to get the DurabilityService qos from the TopicQos");
        }
        assertTrue("Check for valid DurabilityService object", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.topic.TopicQos#getDeadline()}.
     */
    //@tTest
    public void testGetDeadline() {
        checkValidEntities();
        Deadline result = null;
        try {
            result = tq.getDeadline();
            assertTrue("Check for valid Deadline object", result != null);
            result = result.withPeriod(Duration.newDuration(5, TimeUnit.SECONDS, env));
            assertTrue("Check for valid Deadline object", result != null);
            Duration d = result.getPeriod();
            assertTrue("Check for valid Duration object", d.getDuration(TimeUnit.SECONDS) == 5);
            result = result.withPeriod(3, TimeUnit.SECONDS);
            assertTrue("Check for valid Deadline object", result != null);
            d = result.getPeriod();
            assertTrue("Check for valid Duration object", d.getDuration(TimeUnit.SECONDS) == 3);
        } catch (Exception ex) {
            fail("Failed to get the Deadline qos from the TopicQos");
        }
        assertTrue("Check for valid Deadline object", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.topic.TopicQos#getLatencyBudget()}.
     */
    //@tTest
    public void testGetLatencyBudget() {
        checkValidEntities();
        LatencyBudget result = null;
        try {
            result = tq.getLatencyBudget();
            assertTrue("Check for valid LatencyBudget object", result != null);
            result = result.withDuration(Duration.newDuration(5, TimeUnit.SECONDS, env));
            assertTrue("Check for valid LatencyBudget object", result != null);
            Duration d = result.getDuration();
            assertTrue("Check for valid Duration object", d.getDuration(TimeUnit.SECONDS) == 5);
            result = result.withDuration(3, TimeUnit.SECONDS);
            assertTrue("Check for valid LatencyBudget object", result != null);
            d = result.getDuration();
            assertTrue("Check for valid Duration object", d.getDuration(TimeUnit.SECONDS) == 3);
        } catch (Exception ex) {
            fail("Failed to get the LatencyBudget qos from the TopicQos");
        }
        assertTrue("Check for valid LatencyBudget object", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.topic.TopicQos#getLiveliness()}.
     */
    //@tTest
    public void testGetLiveliness() {
        checkValidEntities();
        Liveliness result = null;
        try {
            result = tq.getLiveliness();
            assertTrue("Check for valid Liveliness object", result != null);
            result = result.withLeaseDuration(Duration.newDuration(5, TimeUnit.SECONDS, env));
            assertTrue("Check for valid Liveliness object", result != null);
            Duration d = result.getLeaseDuration();
            assertTrue("Check for valid Duration object", d.getDuration(TimeUnit.SECONDS) == 5);
            result = result.withLeaseDuration(3, TimeUnit.SECONDS);
            assertTrue("Check for valid Liveliness object", result != null);
            d = result.getLeaseDuration();
            assertTrue("Check for valid Duration object", d.getDuration(TimeUnit.SECONDS) == 3);
            result = result.withAutomatic();
            assertTrue("Check for valid Liveliness object", result != null);
            assertTrue("Check for valid Liveliness.Kind", result.getKind() == Liveliness.Kind.AUTOMATIC);
            result = result.withManualByParticipant();
            assertTrue("Check for valid Liveliness object", result != null);
            assertTrue("Check for valid Liveliness.Kind", result.getKind() == Liveliness.Kind.MANUAL_BY_PARTICIPANT);
            result = result.withManualByTopic();
            assertTrue("Check for valid Liveliness object", result != null);
            assertTrue("Check for valid Liveliness.Kind", result.getKind() == Liveliness.Kind.MANUAL_BY_TOPIC);
            result = result.withKind(Liveliness.Kind.AUTOMATIC);
            assertTrue("Check for valid Durability object", result != null);
        } catch (Exception ex) {
            fail("Failed to get the Liveliness qos from the TopicQos");
        }
        assertTrue("Check for valid Liveliness.Kind", result.getKind() == Liveliness.Kind.valueOf("AUTOMATIC"));
    }

    /**
     * Test method for {@link org.omg.dds.topic.TopicQos#getReliability()}.
     */
    //@tTest
    public void testGetReliability() {
        checkValidEntities();
        Reliability result = null;
        try {
            result = tq.getReliability();
            assertTrue("Check for valid Reliability object", result != null);
            result = result.withMaxBlockingTime(Duration.newDuration(5, TimeUnit.SECONDS, env));
            assertTrue("Check for valid Reliability object", result != null);
            Duration d = result.getMaxBlockingTime();
            assertTrue("Check for valid Duration object", d.getDuration(TimeUnit.SECONDS) == 5);
            result = result.withMaxBlockingTime(3, TimeUnit.SECONDS);
            assertTrue("Check for valid Reliability object", result != null);
            d = result.getMaxBlockingTime();
            assertTrue("Check for valid Duration object", d.getDuration(TimeUnit.SECONDS) == 3);
            result = result.withBestEffort();
            assertTrue("Check for valid Reliability object", result != null);
            assertTrue("Check for valid Reliability.Kind", result.getKind() == Reliability.Kind.BEST_EFFORT);
            result = result.withReliable();
            assertTrue("Check for valid Reliability object", result != null);
            assertTrue("Check for valid Reliability.Kind", result.getKind() == Reliability.Kind.RELIABLE);
            result = result.withKind(Reliability.Kind.BEST_EFFORT);
            assertTrue("Check for valid Reliability object", result != null);
        } catch (Exception ex) {
            fail("Failed to get the Reliability qos from the TopicQos");
        }
        assertTrue("Check for valid Reliability.Kind", result.getKind() == Reliability.Kind.valueOf("BEST_EFFORT"));
    }

    /**
     * Test method for {@link org.omg.dds.topic.TopicQos#getDestinationOrder()}.
     */
    //@tTest
    public void testGetDestinationOrder() {
        checkValidEntities();
        DestinationOrder result = null;
        try {
            result = tq.getDestinationOrder();
            assertTrue("Check for valid DestinationOrder object", result != null);
            result = result.withReceptionTimestamp();
            assertTrue("Check for valid DestinationOrder object", result != null);
            assertTrue("Check for valid DestinationOrder.Kind", result.getKind() == DestinationOrder.Kind.BY_RECEPTION_TIMESTAMP);
            result = result.withSourceTimestamp();
            assertTrue("Check for valid DestinationOrder object", result != null);
            assertTrue("Check for valid DestinationOrder.Kind", result.getKind() == DestinationOrder.Kind.BY_SOURCE_TIMESTAMP);
            result = result.withKind(DestinationOrder.Kind.BY_RECEPTION_TIMESTAMP);
            assertTrue("Check for valid DestinationOrder object", result != null);
        } catch (Exception ex) {
            fail("Failed to get the DestinationOrder qos from the TopicQos");
        }
        assertTrue("Check for valid DestinationOrder.Kind", result.getKind() == DestinationOrder.Kind.valueOf("BY_RECEPTION_TIMESTAMP"));
    }

    /**
     * Test method for {@link org.omg.dds.topic.TopicQos#getHistory()}.
     */
    //@tTest
    public void testGetHistory() {
        checkValidEntities();
        History result = null;
        try {
            result = tq.getHistory();
            assertTrue("Check for valid History object", result != null);
            result = result.withDepth(10);
            assertTrue("Check for valid History object", result != null);
            assertTrue("Check for valid depth", result.getDepth() == 10);
            result = result.withKeepLast(2);
            assertTrue("Check for valid History object", result != null);
            assertTrue("Check for valid History.Kind", result.getKind() == History.Kind.KEEP_LAST);
            assertTrue("Check for valid depth", result.getDepth() == 2);
            result = result.withKeepAll();
            assertTrue("Check for valid History object", result != null);
            assertTrue("Check for valid History.Kind", result.getKind() == History.Kind.KEEP_ALL);
            result = result.withKind(History.Kind.KEEP_LAST);
            assertTrue("Check for valid History object", result != null);
        } catch (Exception ex) {
            fail("Failed to get the History qos from the TopicQos");
        }
        assertTrue("Check for valid History.Kind", result.getKind() == History.Kind.valueOf("KEEP_LAST"));
    }

    /**
     * Test method for {@link org.omg.dds.topic.TopicQos#getResourceLimits()}.
     */
    //@tTest
    public void testGetResourceLimits() {
        checkValidEntities();
        ResourceLimits result = null;
        try {
            result = tq.getResourceLimits();
            assertTrue("Check for valid ResourceLimits object", result != null);
            result = result.withMaxInstances(12);
            assertTrue("Check for valid ResourceLimits object", result != null);
            assertTrue("Check for valid max instances", result.getMaxInstances() == 12);
            result = result.withMaxSamples(3);
            assertTrue("Check for valid ResourceLimits object", result != null);
            assertTrue("Check for valid max samples", result.getMaxSamples() == 3);
            result = result.withMaxSamplesPerInstance(4);
            assertTrue("Check for valid ResourceLimits object", result != null);
            assertTrue("Check for valid max samples", result.getMaxSamplesPerInstance() == 4);
        } catch (Exception ex) {
            fail("Failed to get the ResourceLimits qos from the TopicQos");
        }
        assertTrue("Check for valid ResourceLimits object", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.topic.TopicQos#getTransportPriority()}.
     */
    //@tTest
    public void testGetTransportPriority() {
        checkValidEntities();
        TransportPriority result = null;
        try {
            result = tq.getTransportPriority();
            assertTrue("Check for valid TransportPriority object", result != null);
            result = result.withValue(10);
            assertTrue("Check for valid TransportPriority object", result != null);
            assertTrue("Check for valid value", result.getValue() == 10);
        } catch (Exception ex) {
            fail("Failed to get the TransportPriority qos from the TopicQos");
        }
        assertTrue("Check for valid TransportPriority object", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.topic.TopicQos#getLifespan()}.
     */
    //@tTest
    public void testGetLifespan() {
        checkValidEntities();
        Lifespan result = null;
        try {
            result = tq.getLifespan();
            assertTrue("Check for valid Lifespan object", result != null);
            result = result.withDuration(Duration.newDuration(5, TimeUnit.SECONDS, env));
            assertTrue("Check for valid Lifespan object", result != null);
            Duration d = result.getDuration();
            assertTrue("Check for valid Duration object", d.getDuration(TimeUnit.SECONDS) == 5);
            result = result.withDuration(3, TimeUnit.SECONDS);
            assertTrue("Check for valid Lifespan object", result != null);
            d = result.getDuration();
            assertTrue("Check for valid Duration object", d.getDuration(TimeUnit.SECONDS) == 3);
        } catch (Exception ex) {
            fail("Failed to get the Lifespan qos from the TopicQos");
        }
        assertTrue("Check for valid Lifespan object", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.topic.TopicQos#getOwnership()}.
     */
    //@tTest
    public void testGetOwnership() {
        checkValidEntities();
        Ownership result = null;
        try {
            result = tq.getOwnership();
            assertTrue("Check for valid Ownership object", result != null);
            result = result.withExclusive();
            assertTrue("Check for valid Ownership object", result != null);
            assertTrue("Check for valid Ownership.Kind", result.getKind() == Ownership.Kind.EXCLUSIVE);
            result = result.withShared();
            assertTrue("Check for valid Ownership object", result != null);
            assertTrue("Check for valid Ownership.Kind", result.getKind() == Ownership.Kind.SHARED);
            result = result.withKind(Ownership.Kind.EXCLUSIVE);
            assertTrue("Check for valid Ownership object", result != null);
        } catch (Exception ex) {
            fail("Failed to get the Ownership qos from the TopicQos");
        }
        assertTrue("Check for valid Ownership.Kind", result.getKind() == Ownership.Kind.valueOf("EXCLUSIVE"));
    }

    /**
     * Test method for {@link org.omg.dds.topic.TopicQos#getRepresentation()}.
     */
    //@tTest
    public void testGetRepresentation() {
        checkValidEntities();
        DataRepresentation result = null;
        try {
            result = tq.getRepresentation();
            assertTrue("Check for valid DataRepresentation object", result != null);

            ArrayList<Short> l = new ArrayList<Short>();
            l.add(DataRepresentation.Id.XCDR_DATA_REPRESENTATION);
            l.add(DataRepresentation.Id.XML_DATA_REPRESENTATION);
            result = result.withValue(l);
            assertTrue("Check for valid DataRepresentation object", result != null);
            assertTrue("Check for valid value", result.getValue().get(0) == DataRepresentation.Id.XCDR_DATA_REPRESENTATION);
            assertTrue("Check for valid value", result.getValue().get(1) == DataRepresentation.Id.XML_DATA_REPRESENTATION);
            result = result.withValue((short) 3, (short) 4);
            assertTrue("Check for valid DataRepresentation object", result != null);
            assertTrue("Check for valid value", result.getValue().get(0) == 3);
            assertTrue("Check for valid value", result.getValue().get(1) == 4);
        } catch (Exception ex) {
            fail("Failed to get the DataRepresentation qos from the TopicQos");
        }
        assertTrue("Check for valid DataRepresentation object", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.topic.TopicQos#getTypeConsistency()}.
     */
    //@tTest
    public void testGetTypeConsistency() {
        checkValidEntities();
        TypeConsistencyEnforcement result = null;
        try {
            result = tq.getTypeConsistency();
            assertTrue("Check for valid TypeConsistencyEnforcement object", result != null);
            result = result.withAssignableTypeConsistency();
            assertTrue("Check for valid TypeConsistencyEnforcement object", result != null);
            assertTrue("Check for valid TypeConsistencyEnforcement.Kind", result.getKind() == TypeConsistencyEnforcement.Kind.ASSIGNABLE_TYPE_CONSISTENCY);
            result = result.withDeclaredTypeConsistency();
            assertTrue("Check for valid TypeConsistencyEnforcement object", result != null);
            assertTrue("Check for valid TypeConsistencyEnforcement.Kind", result.getKind() == TypeConsistencyEnforcement.Kind.DECLARED_TYPE_CONSISTENCY);
            result = result.withExactNameTypeConsistency();
            assertTrue("Check for valid TypeConsistencyEnforcement object", result != null);
            assertTrue("Check for valid TypeConsistencyEnforcement.Kind", result.getKind() == TypeConsistencyEnforcement.Kind.EXACT_NAME_TYPE_CONSISTENCY);
            result = result.withExactTypeTypeConsistency();
            assertTrue("Check for valid TypeConsistencyEnforcement object", result != null);
            assertTrue("Check for valid TypeConsistencyEnforcement.Kind", result.getKind() == TypeConsistencyEnforcement.Kind.EXACT_TYPE_TYPE_CONSISTENCY);
            result = result.withKind(TypeConsistencyEnforcement.Kind.ASSIGNABLE_TYPE_CONSISTENCY);
            assertTrue("Check for valid TypeConsistencyEnforcement object", result != null);
            assertTrue("Check for valid environment failed", result.getEnvironment() != null);
        } catch (Exception ex) {
            fail("Failed to get the TypeConsistencyEnforcement qos from the TopicQos");
        }
        assertTrue("Check for valid TypeConsistencyEnforcement.Kind", result.getKind() == TypeConsistencyEnforcement.Kind.valueOf("ASSIGNABLE_TYPE_CONSISTENCY"));
    }

}
