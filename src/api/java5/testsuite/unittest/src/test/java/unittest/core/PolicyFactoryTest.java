/**
 *
 */
package unittest.core;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.policy.Deadline;
import org.omg.dds.core.policy.DestinationOrder;
import org.omg.dds.core.policy.Durability;
import org.omg.dds.core.policy.DurabilityService;
import org.omg.dds.core.policy.EntityFactory;
import org.omg.dds.core.policy.GroupData;
import org.omg.dds.core.policy.History;
import org.omg.dds.core.policy.LatencyBudget;
import org.omg.dds.core.policy.Lifespan;
import org.omg.dds.core.policy.Liveliness;
import org.omg.dds.core.policy.Ownership;
import org.omg.dds.core.policy.OwnershipStrength;
import org.omg.dds.core.policy.Partition;
import org.omg.dds.core.policy.PolicyFactory;
import org.omg.dds.core.policy.Presentation;
import org.omg.dds.core.policy.ReaderDataLifecycle;
import org.omg.dds.core.policy.Reliability;
import org.omg.dds.core.policy.ResourceLimits;
import org.omg.dds.core.policy.TimeBasedFilter;
import org.omg.dds.core.policy.TopicData;
import org.omg.dds.core.policy.TransportPriority;
import org.omg.dds.core.policy.TypeConsistencyEnforcement;
import org.omg.dds.core.policy.UserData;
import org.omg.dds.core.policy.WriterDataLifecycle;
import org.omg.dds.domain.DomainParticipantFactory;

import org.eclipse.cyclonedds.test.AbstractUtilities;

public class PolicyFactoryTest {

    private static ServiceEnvironment       env;
    private static DomainParticipantFactory dpf;
    private static AbstractUtilities        util        = AbstractUtilities.getInstance(PolicyFactoryTest.class);
    private static PolicyFactory            pf          = null;

    @BeforeClass
    public static void init() {
        try {
            String serviceEnvironment = System.getProperty("JAVA5PSM_SERVICE_ENV");
            assertTrue("Check for valid ServiceEnvironment string", !serviceEnvironment.equals(""));
            System.setProperty(ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY, serviceEnvironment);
            env = ServiceEnvironment.createInstance(PolicyFactoryTest.class.getClassLoader());
            assertTrue("Check for valid ServiceEnvironment object", env instanceof ServiceEnvironment);
            dpf = DomainParticipantFactory.getInstance(env);
            assertTrue("Check for valid DomainParticipantFactory object", dpf instanceof DomainParticipantFactory);
            pf = dpf.getQos().getPolicyFactory();
        } catch (Exception e) {
            fail("Exception occured while initiating the PolicyFactoryTest class: " + util.printException(e));
        }

    }

    private void checkValidEntities() {
        assertTrue("Check for valid PolicyFactory object", pf instanceof PolicyFactory);
    }

    @AfterClass
    public static void cleanup() {

    }

    /**
     * Test method for {@link org.omg.dds.core.policy.PolicyFactory#getPolicyFactory(org.omg.dds.core.ServiceEnvironment)}.
     */
    @Test
    public void testGetPolicyFactory() {
        checkValidEntities();
        PolicyFactory result = null;
        try {
            result = PolicyFactory.getPolicyFactory(env);
        } catch (Exception ex) {
            fail("Failed to get the PolicyFactory");
        }
        assertTrue("Check for valid PolicyFactory object", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.core.policy.PolicyFactory#Durability()}.
     */
    @Test
    public void testDurability() {
        checkValidEntities();
        Durability result = null;
        try {
            result = pf.Durability();
        } catch (Exception ex) {
            fail("Failed to get the Durability");
        }
        assertTrue("Check for valid Durability object", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.core.policy.PolicyFactory#Deadline()}.
     */
    @Test
    public void testDeadline() {
        checkValidEntities();
        Deadline result = null;
        try {
            result = pf.Deadline();
        } catch (Exception ex) {
            fail("Failed to get the Deadline");
        }
        assertTrue("Check for valid Deadline object", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.core.policy.PolicyFactory#LatencyBudget()}.
     */
    @Test
    public void testLatencyBudget() {
        checkValidEntities();
        LatencyBudget result = null;
        try {
            result = pf.LatencyBudget();
        } catch (Exception ex) {
            fail("Failed to get the LatencyBudget");
        }
        assertTrue("Check for valid LatencyBudget object", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.core.policy.PolicyFactory#Liveliness()}.
     */
    @Test
    public void testLiveliness() {
        checkValidEntities();
        Liveliness result = null;
        try {
            result = pf.Liveliness();
        } catch (Exception ex) {
            fail("Failed to get the Liveliness");
        }
        assertTrue("Check for valid Liveliness object", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.core.policy.PolicyFactory#DestinationOrder()}.
     */
    @Test
    public void testDestinationOrder() {
        checkValidEntities();
        DestinationOrder result = null;
        try {
            result = pf.DestinationOrder();
        } catch (Exception ex) {
            fail("Failed to get the DestinationOrder");
        }
        assertTrue("Check for valid DestinationOrder object", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.core.policy.PolicyFactory#History()}.
     */
    @Test
    public void testHistory() {
        checkValidEntities();
        History result = null;
        try {
            result = pf.History();
        } catch (Exception ex) {
            fail("Failed to get the History");
        }
        assertTrue("Check for valid History object", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.core.policy.PolicyFactory#ResourceLimits()}.
     */
    @Test
    public void testResourceLimits() {
        checkValidEntities();
        ResourceLimits result = null;
        try {
            result = pf.ResourceLimits();
        } catch (Exception ex) {
            fail("Failed to get the ResourceLimits");
        }
        assertTrue("Check for valid ResourceLimits object", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.core.policy.PolicyFactory#UserData()}.
     */
    @Test
    public void testUserData() {
        checkValidEntities();
        UserData result = null;
        try {
            result = pf.UserData();
        } catch (Exception ex) {
            fail("Failed to get the UserData");
        }
        assertTrue("Check for valid UserData object", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.core.policy.PolicyFactory#Ownership()}.
     */
    @Test
    public void testOwnership() {
        checkValidEntities();
        Ownership result = null;
        try {
            result = pf.Ownership();
        } catch (Exception ex) {
            fail("Failed to get the Ownership");
        }
        assertTrue("Check for valid Ownership object", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.core.policy.PolicyFactory#TimeBasedFilter()}.
     */
    @Test
    public void testTimeBasedFilter() {
        checkValidEntities();
        TimeBasedFilter result = null;
        try {
            result = pf.TimeBasedFilter();
        } catch (Exception ex) {
            fail("Failed to get the TimeBasedFilter");
        }
        assertTrue("Check for valid TimeBasedFilter object", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.core.policy.PolicyFactory#ReaderDataLifecycle()}.
     */
    @Test
    public void testReaderDataLifecycle() {
        checkValidEntities();
        ReaderDataLifecycle result = null;
        try {
            result = pf.ReaderDataLifecycle();
        } catch (Exception ex) {
            fail("Failed to get the ReaderDataLifecycle");
        }
        assertTrue("Check for valid ReaderDataLifecycle object", result != null);
    }

    /**
     * Test method for
     * {@link org.omg.dds.core.policy.PolicyFactory#Presentation()}.
     */
    @Test
    public void testPresentation() {
        checkValidEntities();
        Presentation result = null;
        try {
            result = pf.Presentation();
        } catch (Exception ex) {
            fail("Failed to get the Presentation");
        }
        assertTrue("Check for valid Presentation object", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.core.policy.PolicyFactory#TopicData()}.
     */
    @Test
    public void testTopicData() {
        checkValidEntities();
        TopicData result = null;
        try {
            result = pf.TopicData();
        } catch (Exception ex) {
            fail("Failed to get the TopicData");
        }
        assertTrue("Check for valid TopicData object", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.core.policy.PolicyFactory#TypeConsistency()}.
     */
    @Test
    public void testTypeConsistency() {
        checkValidEntities();
        TypeConsistencyEnforcement result = null;
        try {
            result = pf.TypeConsistency();
        } catch (Exception ex) {
            fail("Failed to get the TypeConsistencyEnforcement");
        }
        assertTrue("Check for valid TypeConsistencyEnforcement object", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.core.policy.PolicyFactory#DurabilityService()}.
     */
    @Test
    public void testDurabilityService() {
        checkValidEntities();
        DurabilityService result = null;
        try {
            result = pf.DurabilityService();
        } catch (Exception ex) {
            fail("Failed to get the DurabilityService");
        }
        assertTrue("Check for valid DurabilityService object", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.core.policy.PolicyFactory#Reliability()}.
     */
    @Test
    public void testReliability() {
        checkValidEntities();
        Reliability result = null;
        try {
            result = pf.Reliability();
        } catch (Exception ex) {
            fail("Failed to get the Reliability");
        }
        assertTrue("Check for valid Reliability object", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.core.policy.PolicyFactory#TransportPriority()}.
     */
    @Test
    public void testTransportPriority() {
        checkValidEntities();
        TransportPriority result = null;
        try {
            result = pf.TransportPriority();
        } catch (Exception ex) {
            fail("Failed to get the TransportPriority");
        }
        assertTrue("Check for valid TransportPriority object", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.core.policy.PolicyFactory#Lifespan()}.
     */
    @Test
    public void testLifespan() {
        checkValidEntities();
        Lifespan result = null;
        try {
            result = pf.Lifespan();
        } catch (Exception ex) {
            fail("Failed to get the Lifespan");
        }
        assertTrue("Check for valid Lifespan object", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.core.policy.PolicyFactory#OwnershipStrength()}.
     */
    @Test
    public void testOwnershipStrength() {
        checkValidEntities();
        OwnershipStrength result = null;
        try {
            result = pf.OwnershipStrength();
        } catch (Exception ex) {
            fail("Failed to get the OwnershipStrength");
        }
        assertTrue("Check for valid OwnershipStrength object", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.core.policy.PolicyFactory#WriterDataLifecycle()}.
     */
    @Test
    public void testWriterDataLifecycle() {
        checkValidEntities();
        WriterDataLifecycle result = null;
        try {
            result = pf.WriterDataLifecycle();
        } catch (Exception ex) {
            fail("Failed to get the WriterDataLifecycle");
        }
        assertTrue("Check for valid WriterDataLifecycle object", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.core.policy.PolicyFactory#Partition()}.
     */
    @Test
    public void testPartition() {
        checkValidEntities();
        Partition result = null;
        try {
            result = pf.Partition();
        } catch (Exception ex) {
            fail("Failed to get the Partition");
        }
        assertTrue("Check for valid Partition object", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.core.policy.PolicyFactory#GroupData()}.
     */
    @Test
    public void testGroupData() {
        checkValidEntities();
        GroupData result = null;
        try {
            result = pf.GroupData();
        } catch (Exception ex) {
            fail("Failed to get the GroupData");
        }
        assertTrue("Check for valid GroupData object", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.core.policy.PolicyFactory#EntityFactory()}.
     */
    @Test
    public void testEntityFactory() {
        checkValidEntities();
        EntityFactory result = null;
        try {
            result = pf.EntityFactory();
        } catch (Exception ex) {
            fail("Failed to get the EntityFactory");
        }
        assertTrue("Check for valid EntityFactory object", result != null);
    }

    @Test
    public void testServiceEnvironment() {
        checkValidEntities();
        ServiceEnvironment result = null;
        try {
            result = pf.getEnvironment();
        } catch (Exception ex) {
            fail("Failed to get the ServiceEnvironment");
        }
        assertTrue("Check for valid ServiceEnvironment object", result != null);
    }

}
