package unittest.topic;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.dds.core.AlreadyClosedException;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.policy.DestinationOrder;
import org.omg.dds.core.policy.Durability;
import org.omg.dds.core.policy.Liveliness;
import org.omg.dds.core.policy.Ownership;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.domain.DomainParticipantFactory;
import org.opensplice.dds.core.OsplServiceEnvironment;
import org.opensplice.dds.core.policy.DataRepresentationImpl;
import org.opensplice.dds.core.policy.DeadlineImpl;
import org.opensplice.dds.core.policy.DestinationOrderImpl;
import org.opensplice.dds.core.policy.DurabilityImpl;
import org.opensplice.dds.core.policy.DurabilityServiceImpl;
import org.opensplice.dds.core.policy.HistoryImpl;
import org.opensplice.dds.core.policy.LatencyBudgetImpl;
import org.opensplice.dds.core.policy.LifespanImpl;
import org.opensplice.dds.core.policy.LivelinessImpl;
import org.opensplice.dds.core.policy.OwnershipImpl;
import org.opensplice.dds.core.policy.ReliabilityImpl;
import org.opensplice.dds.core.policy.ResourceLimitsImpl;
import org.opensplice.dds.core.policy.TopicDataImpl;
import org.opensplice.dds.core.policy.TransportPriorityImpl;
import org.opensplice.dds.core.policy.TypeConsistencyEnforcementImpl;
import org.opensplice.dds.topic.TopicImpl;
import org.opensplice.dds.topic.TopicQosImpl;

import Test.Msg;

import org.eclipse.cyclonedds.test.AbstractUtilities;

public class TopicQosopenspliceTest {

    private final static int                DOMAIN_ID   = 123;
    private static ServiceEnvironment       env;
    private static DomainParticipantFactory dpf;
    private static AbstractUtilities        util        = AbstractUtilities.getInstance(TopicQosopenspliceTest.class);
    private final static Properties         prop        = new Properties();
    private static DomainParticipant        participant = null;
    private static TopicImpl<Msg>           topic       = null;
    private static TopicQosImpl             tq          = null;
    private static String                   topicName   = "TopicQosopenspliceTest";

    @BeforeClass
    public static void init() {
        try {
            String serviceEnvironment = System.getProperty("JAVA5PSM_SERVICE_ENV");
            assertTrue("Check for valid ServiceEnvironment string", !serviceEnvironment.equals(""));
            System.setProperty(ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY, serviceEnvironment);
            env = ServiceEnvironment.createInstance(TopicQosopenspliceTest.class.getClassLoader());
            assertTrue("Check for valid ServiceEnvironment object", env instanceof ServiceEnvironment);
            dpf = DomainParticipantFactory.getInstance(env);
            assertTrue("Check for valid DomainParticipantFactory object", dpf instanceof DomainParticipantFactory);
            prop.setProperty("domainid", new Integer(DOMAIN_ID).toString());
            assertTrue("Check is deamon is correctly started", util.beforeClass(prop));
            participant = dpf.createParticipant(DOMAIN_ID);
            assertTrue("Check for valid DomainParticipant object", participant instanceof DomainParticipant);
            assertTrue("Check for valid DomainParticipant with id " + DOMAIN_ID, participant.getDomainId() == DOMAIN_ID);
            topic = (TopicImpl<Msg>) participant.createTopic(topicName, Msg.class);
            assertTrue("Check for valid Topic object", topic instanceof TopicImpl);
            tq = (TopicQosImpl) topic.getQos();
            assertTrue("Check for valid TopicQos object", tq instanceof TopicQosImpl);
        } catch (Exception e) {
            fail("Exception occured while initiating the TopicQosopenspliceTest class: " + util.printException(e));
        }

    }

    private void checkValidEntities() {
        assertTrue("Check for valid DomainParticipant object", participant instanceof DomainParticipant);
        assertTrue("Check for valid Topic object", topic instanceof TopicImpl);
        assertTrue("Check for valid TopicQos object", tq instanceof TopicQosImpl);
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
        TopicDataImpl result = null;
        try {
            result = (TopicDataImpl) tq.getTopicData();
            assertTrue("Check for valid TopicData PolicyClass object", result.getPolicyClass() != null);
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
        DurabilityImpl result = null;
        try {
            result = (DurabilityImpl) tq.getDurability();
            assertTrue("Check for valid Durability PolicyClass object", result.getPolicyClass() != null);
            assertTrue("Check for valid Durability object", result.compareTo((DurabilityImpl) result.requestedOfferedContract()) == 0);

            result = (DurabilityImpl) result.withPersistent();
            Durability du = tq.getDurability().withVolatile();
            assertTrue("Check for valid Durability object", result.compareTo(du) == 1);

            result = (DurabilityImpl) result.withVolatile();
            du = tq.getDurability().withTransientLocal();
            assertTrue("Check for valid Durability object", result.compareTo(du) == -1);

            result = (DurabilityImpl) result.withTransient();
            du = tq.getDurability().withTransientLocal();
            assertTrue("Check for valid Durability object", result.compareTo(du) == 1);

            result = (DurabilityImpl) result.withTransientLocal();
            du = tq.getDurability().withTransientLocal();
            assertTrue("Check for valid Durability object", result.compareTo(du) == 0);

            result = (DurabilityImpl) result.withVolatile();
            du = tq.getDurability().withTransient();
            assertTrue("Check for valid Durability object", result.compareTo(du) == -1);

            result = (DurabilityImpl) result.withPersistent();
            du = tq.getDurability().withTransient();
            assertTrue("Check for valid Durability object", result.compareTo(du) == 1);

            result = (DurabilityImpl) result.withVolatile();
            du = tq.getDurability().withPersistent();
            assertTrue("Check for valid Durability object", result.compareTo(du) == -1);

        } catch (Exception ex) {
            fail("Failed to get the Durability qos from the TopicQos");
        }
        assertTrue("Check for valid Durability object", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.topic.TopicQos#getDurabilityService()}.
     */
    //@tTest
    public void testGetDurabilityService() {
        checkValidEntities();
        DurabilityServiceImpl result = null;
        try {
            result = (DurabilityServiceImpl) tq.getDurabilityService();
            assertTrue("Check for valid DurabilityService object", result.getPolicyClass() != null);
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
        DeadlineImpl result = null;
        try {
            result = (DeadlineImpl) tq.getDeadline();
            assertTrue("Check for valid Deadline PolicyClass object", result.getPolicyClass() != null);
            assertTrue("Check for valid Deadline object", result.compareTo((DeadlineImpl) result.requestedOfferedContract()) == 0);

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
        LatencyBudgetImpl result = null;
        try {
            result = (LatencyBudgetImpl) tq.getLatencyBudget();
            assertTrue("Check for valid LatencyBudget PolicyClass object", result.getPolicyClass() != null);
            assertTrue("Check for valid LatencyBudget object", result.compareTo((LatencyBudgetImpl) result.requestedOfferedContract()) == 0);
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
        LivelinessImpl result = null;
        try {
            result = (LivelinessImpl) tq.getLiveliness();
            assertTrue("Check for valid Liveliness PolicyClass object", result.getPolicyClass() != null);
            assertTrue("Check for valid Liveliness object", result.compareTo((LivelinessImpl) result.requestedOfferedContract()) == 0);

            /*
             * AUTOMATIC < MANUAL_BY_PARTICIPANT < MANUAL_BY_TOPIC and offered
             * leaseDuration <= requested leaseDuration
             */

            result = (LivelinessImpl) result.withAutomatic();
            Liveliness li = tq.getLiveliness().withManualByParticipant();
            assertTrue("Check for valid Liveliness object", result.compareTo(li) == -1);

            result = (LivelinessImpl) result.withAutomatic();
            li = tq.getLiveliness().withManualByTopic();
            assertTrue("Check for valid Liveliness object", result.compareTo(li) == -1);

            result = (LivelinessImpl) result.withManualByParticipant();
            li = tq.getLiveliness().withManualByTopic();
            assertTrue("Check for valid Liveliness object", result.compareTo(li) == -1);

            result = (LivelinessImpl) result.withManualByParticipant();
            li = tq.getLiveliness().withAutomatic();
            assertTrue("Check for valid Liveliness object", result.compareTo(li) == 1);

            result = (LivelinessImpl) result.withManualByTopic();
            li = tq.getLiveliness().withManualByParticipant();
            assertTrue("Check for valid Liveliness object", result.compareTo(li) == 1);

            result = (LivelinessImpl) result.withManualByTopic();
            li = tq.getLiveliness().withAutomatic();
            assertTrue("Check for valid Liveliness object", result.compareTo(li) == 1);

        } catch (Exception ex) {
            fail("Exception occured in testGetLiveliness: " + util.printException(ex));
        }
        assertTrue("Check for valid Liveliness object", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.topic.TopicQos#getReliability()}.
     */
    //@tTest
    public void testGetReliability() {
        checkValidEntities();
        ReliabilityImpl result = null;
        try {
            result = (ReliabilityImpl) tq.getReliability();
            assertTrue("Check for valid Reliability PolicyClass object", result.getPolicyClass() != null);
            result = (ReliabilityImpl) result.withSynchronous(true);
            assertTrue("Check for valid Reliability Synchronous value", result.isSynchronous());
            assertTrue("Check for valid Reliability compare", result.compareTo((ReliabilityImpl) result.requestedOfferedContract()) == 0);
            ReliabilityImpl rb = (ReliabilityImpl) tq.getReliability().withReliable();
            assertTrue("Check for valid Reliability compare", result.compareTo(rb) == -1);
            result = (ReliabilityImpl) result.withReliable();
            rb = (ReliabilityImpl) rb.withBestEffort();
            assertTrue("Check for valid Reliability compare", result.compareTo(rb) == 1);

            result = (ReliabilityImpl) result.withReliable().withSynchronous(true);
            rb = (ReliabilityImpl) rb.withReliable().withSynchronous(false);
            assertTrue("Check for valid Reliability compare", result.compareTo(rb) == 1);

            result = (ReliabilityImpl) result.withReliable().withSynchronous(false);
            rb = (ReliabilityImpl) rb.withReliable().withSynchronous(true);
            assertTrue("Check for valid Reliability compare", result.compareTo(rb) == -1);

            result = (ReliabilityImpl) result.withReliable().withSynchronous(false).withMaxBlockingTime(5, TimeUnit.SECONDS);
            rb = (ReliabilityImpl) rb.withReliable().withSynchronous(false);
            assertTrue("Check for valid Reliability compare", result.compareTo(rb) == 1);

        } catch (Exception ex) {
            fail("Failed to get the Reliability qos from the TopicQos");
        }
        assertTrue("Check for valid Reliability object", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.topic.TopicQos#getDestinationOrder()}.
     */
    //@tTest
    public void testGetDestinationOrder() {
        checkValidEntities();
        DestinationOrderImpl result = null;
        try {
            result = (DestinationOrderImpl) tq.getDestinationOrder();
            assertTrue("Check for valid DestinationOrder PolicyClass object", result.getPolicyClass() != null);
            assertTrue("Check for valid DestinationOrder object", result.compareTo((DestinationOrderImpl) result.requestedOfferedContract()) == 0);
            result = (DestinationOrderImpl) result.withReceptionTimestamp();
            DestinationOrder des = tq.getDestinationOrder().withSourceTimestamp();
            assertTrue("Check for valid DestinationOrder object", result.compareTo(des) == -1);
            result = (DestinationOrderImpl) result.withSourceTimestamp();
            des = des.withReceptionTimestamp();
            assertTrue("Check for valid DestinationOrder object", result.compareTo(des) == 1);
        } catch (Exception ex) {
            fail("Failed to get the DestinationOrder qos from the TopicQos");
        }
        assertTrue("Check for valid DestinationOrder object", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.topic.TopicQos#getHistory()}.
     */
    //@tTest
    public void testGetHistory() {
        checkValidEntities();
        HistoryImpl result = null;
        try {
            result = (HistoryImpl) tq.getHistory();
            assertTrue("Check for valid History PolicyClass object", result.getPolicyClass() != null);
            result = (HistoryImpl) result.withKeepAll();
            assertTrue("Check for valid History object", result != null);
            assertTrue("Check for valid History.Kind", result.getKind() == HistoryImpl.Kind.KEEP_ALL);
        } catch (Exception ex) {
            fail("Failed to get the History qos from the TopicQos");
        }
        assertTrue("Check for valid History object", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.topic.TopicQos#getResourceLimits()}.
     */
    //@tTest
    public void testGetResourceLimits() {
        checkValidEntities();
        ResourceLimitsImpl result = null;
        try {
            result = (ResourceLimitsImpl) tq.getResourceLimits();
            assertTrue("Check for valid ResourceLimits PolicyClass object", result.getPolicyClass() != null);
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
        TransportPriorityImpl result = null;
        try {
            result = (TransportPriorityImpl) tq.getTransportPriority();
            assertTrue("Check for valid TransportPriority PolicyClass object", result.getPolicyClass() != null);
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
        LifespanImpl result = null;
        try {
            result = (LifespanImpl) tq.getLifespan();
            assertTrue("Check for valid Lifespan PolicyClass object", result.getPolicyClass() != null);
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
        OwnershipImpl result = null;
        try {
            result = (OwnershipImpl) tq.getOwnership();
            assertTrue("Check for valid Ownership PolicyClass object", result.getPolicyClass() != null);
            assertTrue("Check for valid Ownership object", result.compareTo((OwnershipImpl) result.requestedOfferedContract()) == 0);
            result = (OwnershipImpl) result.withShared();
            Ownership os = tq.getOwnership().withExclusive();
            assertTrue("Check for valid Ownership object", result.compareTo(os) == -1);
            result = (OwnershipImpl) result.withExclusive();
            os = os.withShared();
            assertTrue("Check for valid Ownership object", result.compareTo(os) == 1);

        } catch (Exception ex) {
            fail("Failed to get the Ownership qos from the TopicQos");
        }
        assertTrue("Check for valid Ownership object", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.topic.TopicQos#getRepresentation()}.
     */
    //@tTest
    public void testGetRepresentation() {
        checkValidEntities();
        DataRepresentationImpl result = null;
        try {
            result = (DataRepresentationImpl) tq.getRepresentation();
            assertTrue("Check for valid DataRepresentation PolicyClass object", result.getPolicyClass() != null);
            assertTrue("Check for valid DataRepresentation object", result.compareTo((DataRepresentationImpl) result.requestedOfferedContract()) == 0);
            assertTrue("Check for valid DataRepresentation object", result.compareTo(null) == 1);
            DataRepresentationImpl drp = (DataRepresentationImpl) tq.getRepresentation().withValue((short) 2);
            assertTrue("Check for valid DataRepresentation object", result.compareTo(drp) > Integer.MIN_VALUE);
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
        TypeConsistencyEnforcementImpl result = null;
        try {
            result = (TypeConsistencyEnforcementImpl) tq.getTypeConsistency();
            assertTrue("Check for valid TypeConsistencyEnforcement object", result.compareTo((TypeConsistencyEnforcementImpl) result.requestedOfferedContract()) == 0);

            result = (TypeConsistencyEnforcementImpl) result.withExactTypeTypeConsistency();
            TypeConsistencyEnforcementImpl tce = (TypeConsistencyEnforcementImpl) tq.getTypeConsistency().withExactNameTypeConsistency();
            assertTrue("Check for valid TypeConsistencyEnforcement object", result.compareTo(tce) == 1);

            result = (TypeConsistencyEnforcementImpl) result.withExactNameTypeConsistency();
            tce = (TypeConsistencyEnforcementImpl) tq.getTypeConsistency().withExactTypeTypeConsistency();
            assertTrue("Check for valid TypeConsistencyEnforcement object", result.compareTo(tce) == -1);

            result = (TypeConsistencyEnforcementImpl) result.withExactNameTypeConsistency();
            tce = (TypeConsistencyEnforcementImpl) tq.getTypeConsistency().withDeclaredTypeConsistency();
            assertTrue("Check for valid TypeConsistencyEnforcement object", result.compareTo(tce) == 1);

            result = (TypeConsistencyEnforcementImpl) result.withDeclaredTypeConsistency();
            tce = (TypeConsistencyEnforcementImpl) tq.getTypeConsistency().withAssignableTypeConsistency();
            assertTrue("Check for valid TypeConsistencyEnforcement object", result.compareTo(tce) == 1);

            result = (TypeConsistencyEnforcementImpl) result.withDeclaredTypeConsistency();
            tce = (TypeConsistencyEnforcementImpl) tq.getTypeConsistency().withExactTypeTypeConsistency();
            assertTrue("Check for valid TypeConsistencyEnforcement object", result.compareTo(tce) == -1);

            result = (TypeConsistencyEnforcementImpl) result.withAssignableTypeConsistency();
            tce = (TypeConsistencyEnforcementImpl) tq.getTypeConsistency().withExactTypeTypeConsistency();
            assertTrue("Check for valid TypeConsistencyEnforcement object", result.compareTo(tce) == -1);

        } catch (Exception ex) {
            fail("Failed to get the TypeConsistencyEnforcement qos from the TopicQos");
        }
        assertTrue("Check for valid TypeConsistencyEnforcement object", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.topic.TopicQos#convert()}.
     */
    //@tTest
    public void testConvertNull() {
        checkValidEntities();
        boolean exceptionOccured = false;
        TopicQosImpl result = null;
        try {
            result = TopicQosImpl.convert((OsplServiceEnvironment) env, null);
        } catch (Exception e) {
            assertTrue("Check for IllegalArgumentException but got exception:" + util.printException(e), e instanceof IllegalArgumentException);
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException occured failed", exceptionOccured);
        assertTrue("Check for invalid TopicQosImpl object", result == null);
    }

}
