package unittest.pub;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.dds.core.AlreadyClosedException;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.policy.EntityFactory;
import org.omg.dds.core.policy.GroupData;
import org.omg.dds.core.policy.Partition;
import org.omg.dds.core.policy.Presentation;
import org.omg.dds.core.policy.Presentation.AccessScopeKind;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.domain.DomainParticipantFactory;
import org.omg.dds.pub.Publisher;
import org.omg.dds.pub.PublisherQos;

import org.eclipse.cyclonedds.test.AbstractUtilities;

public class PublisherQosTest {

    private final static int                DOMAIN_ID   = 123;
    private static ServiceEnvironment       env;
    private static DomainParticipantFactory dpf;
    private static AbstractUtilities        util        = AbstractUtilities.getInstance(PublisherQosTest.class);
    private final static Properties         prop        = new Properties();
    private static DomainParticipant        participant = null;
    private static Publisher                publisher   = null;
    private static PublisherQos             pq          = null;

    @BeforeClass
    public static void init() {
        try {
            String serviceEnvironment = System.getProperty("JAVA5PSM_SERVICE_ENV");
            assertTrue("Check for valid ServiceEnvironment string", !serviceEnvironment.equals(""));
            System.setProperty(ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY, serviceEnvironment);
            env = ServiceEnvironment.createInstance(PublisherQosTest.class.getClassLoader());
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
            pq = publisher.getQos();
            assertTrue("Check for valid PublisherQos object", pq instanceof PublisherQos);
        } catch (Exception e) {
            fail("Exception occured while initiating the PublisherQosTest class: " + util.printException(e));
        }

    }

    private void checkValidEntities() {
        assertTrue("Check for valid DomainParticipant object", participant instanceof DomainParticipant);
        assertTrue("Check for valid Publisher object", publisher instanceof Publisher);
        assertTrue("Check for valid PublisherQos object", pq instanceof PublisherQos);
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
     * Test method for {@link org.omg.dds.pub.PublisherQos#getPresentation()}.
     */
    //@tTest
    public void testGetPresentation() {
        checkValidEntities();
        Presentation result = null;
        try {
            result = pq.getPresentation();

            assertTrue("Check for valid Presentation object", result != null);
            result = result.withGroup();
            assertTrue("Check for valid Presentation object", result != null);
            assertTrue("Check for valid Presentation.Kind", result.getAccessScope() == AccessScopeKind.GROUP);
            result = result.withInstance();
            assertTrue("Check for valid Presentation object", result != null);
            assertTrue("Check for valid Presentation.Kind", result.getAccessScope() == AccessScopeKind.INSTANCE);
            result = result.withTopic();
            assertTrue("Check for valid Presentation object", result != null);
            assertTrue("Check for valid Presentation.Kind", result.getAccessScope() == AccessScopeKind.TOPIC);
            result = result.withOrderedAccess(true);
            assertTrue("Check for valid Presentation object", result != null);
            assertTrue("Check for valid Presentation.Kind", result.isOrderedAccess());
            result = result.withCoherentAccess(true);
            assertTrue("Check for valid Presentation object", result != null);
            assertTrue("Check for valid Presentation.Kind", result.isCoherentAccess());
            result = result.withAccessScope(AccessScopeKind.GROUP);
            assertTrue("Check for valid Presentation object", result != null);
        } catch (Exception ex) {
            fail("Failed to get the Presentation qos from the PublisherQos");
        }
        assertTrue("Check for valid Presentation.Kind", result.getAccessScope() == AccessScopeKind.GROUP);
    }

    /**
     * Test method for {@link org.omg.dds.pub.PublisherQos#getPartition()}.
     */
    //@tTest
    public void testGetPartition() {
        checkValidEntities();
        Partition result = null;
        try {
            result = pq.getPartition();
            assertTrue("Check for valid Partition object", result != null);
            ArrayList<String> name = new ArrayList<String>();
            name.add("Foo");
            name.add("Bar");
            result = result.withName(name);
            assertTrue("Check for valid Partition object", result != null);
            assertTrue("Check for valid value", result.getName().contains("Foo"));
            assertTrue("Check for valid value", result.getName().contains("Bar"));
            result = result.withName("Presentation", "Qos");
            assertTrue("Check for valid Partition object", result != null);
            assertTrue("Check for valid value", result.getName().contains("Presentation"));
            assertTrue("Check for valid value", result.getName().contains("Qos"));
            result = result.withName("Par");
            assertTrue("Check for valid Partition object", result != null);
            assertTrue("Check for valid value", result.getName().contains("Par"));
        } catch (Exception ex) {
            fail("Failed to get the Partition qos from the PublisherQos");
        }
        assertTrue("Check for valid Partition object", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.pub.PublisherQos#getGroupData()}.
     */
    //@tTest
    public void testGetGroupData() {
        checkValidEntities();
        GroupData result = null;
        try {
            result = pq.getGroupData();
            byte[] b = new String("Foo").getBytes();
            result = result.withValue(b, 0, b.length);
            assertTrue("Check for valid TopicData object", result != null);
            assertTrue("Check for valid TopicData value", new String(result.getValue()).equals("Foo"));
        } catch (Exception ex) {
            fail("Failed to get the GroupData qos from the PublisherQos");
        }
        assertTrue("Check for valid GroupData object", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.pub.PublisherQos#getEntityFactory()}.
     */
    //@tTest
    public void testGetEntityFactory() {
        checkValidEntities();
        EntityFactory result = null;
        try {
            result = pq.getEntityFactory();
            assertTrue("Check for valid EntityFactory object", result != null);
            result = result.withAutoEnableCreatedEntities(false);
            assertTrue("Check for valid EntityFactory object", result != null);
            assertFalse("Check for valid value", result.isAutoEnableCreatedEntities());

        } catch (Exception ex) {
            fail("Failed to get the EntityFactory qos from the PublisherQos");
        }
        assertTrue("Check for valid EntityFactory object", result != null);
    }
}
