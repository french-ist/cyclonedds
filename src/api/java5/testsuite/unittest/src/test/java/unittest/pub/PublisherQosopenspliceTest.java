package unittest.pub;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashSet;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.dds.core.AlreadyClosedException;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.policy.Presentation;
import org.omg.dds.core.policy.Presentation.AccessScopeKind;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.domain.DomainParticipantFactory;
import org.omg.dds.pub.Publisher;
import org.omg.dds.pub.PublisherQos;
import org.opensplice.dds.core.OsplServiceEnvironment;
import org.opensplice.dds.core.policy.EntityFactoryImpl;
import org.opensplice.dds.core.policy.GroupDataImpl;
import org.opensplice.dds.core.policy.PartitionImpl;
import org.opensplice.dds.core.policy.PresentationImpl;

import org.eclipse.cyclonedds.test.AbstractUtilities;

public class PublisherQosopenspliceTest {

    private final static int                DOMAIN_ID   = 123;
    private static ServiceEnvironment       env;
    private static DomainParticipantFactory dpf;
    private static AbstractUtilities        util        = AbstractUtilities.getInstance(PublisherQosopenspliceTest.class);
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
            env = ServiceEnvironment.createInstance(PublisherQosopenspliceTest.class.getClassLoader());
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
            fail("Exception occured while initiating the PublisherQosopenspliceTest class: " + util.printException(e));
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
        PresentationImpl result = null;
        try {
            result = (PresentationImpl) pq.getPresentation();
            assertTrue("Check for valid Presentation object", result != null);
            assertTrue("Check for valid Presentation PolicyClass object", result.getPolicyClass() != null);
            assertTrue("Check for valid Presentation object", result.compareTo((PresentationImpl) result.requestedOfferedContract()) == 0);

            Presentation p = pq.getPresentation().withOrderedAccess(false).withCoherentAccess(true);
            result = (PresentationImpl) result.withOrderedAccess(true).withCoherentAccess(true);
            assertTrue("Check for valid Presentation object", result.compareTo(p) == 1);

            p = pq.getPresentation().withOrderedAccess(true).withCoherentAccess(true);
            result = (PresentationImpl) result.withOrderedAccess(false).withCoherentAccess(true);
            assertTrue("Check for valid Presentation object", result.compareTo(p) == -1);

            p = pq.getPresentation().withOrderedAccess(true).withCoherentAccess(true);
            result = (PresentationImpl) result.withOrderedAccess(true).withCoherentAccess(false);
            assertTrue("Check for valid Presentation object", result.compareTo(p) == -1);

            p = pq.getPresentation().withOrderedAccess(true).withCoherentAccess(false);
            result = (PresentationImpl) result.withOrderedAccess(true).withCoherentAccess(true);
            assertTrue("Check for valid Presentation object", result.compareTo(p) == 1);

            p = pq.getPresentation().withOrderedAccess(true).withCoherentAccess(false);
            result = (PresentationImpl) result.withOrderedAccess(false).withCoherentAccess(true);
            assertTrue("Check for valid Presentation object", result.compareTo(p) == 1);

            p = pq.getPresentation().withOrderedAccess(true).withCoherentAccess(true);
            result = (PresentationImpl) result.withOrderedAccess(false).withCoherentAccess(false);
            assertTrue("Check for valid Presentation object", result.compareTo(p) == -1);

            result = (PresentationImpl) result.withAccessScope(AccessScopeKind.valueOf("GROUP")).withCoherentAccess(true).withOrderedAccess(true);
            p = pq.getPresentation().withTopic().withOrderedAccess(true).withCoherentAccess(true);
            assertTrue("Check for valid Presentation object", result.compareTo(p) == 1);

            result = (PresentationImpl) result.withAccessScope(AccessScopeKind.valueOf("TOPIC")).withCoherentAccess(true).withOrderedAccess(true);
            p = pq.getPresentation().withInstance().withOrderedAccess(true).withCoherentAccess(true);
            assertTrue("Check for valid Presentation object", result.compareTo(p) == 1);

            result = (PresentationImpl) result.withAccessScope(AccessScopeKind.valueOf("TOPIC")).withCoherentAccess(true).withOrderedAccess(true);
            p = pq.getPresentation().withGroup().withOrderedAccess(true).withCoherentAccess(true);
            assertTrue("Check for valid Presentation object", result.compareTo(p) == -1);

            result = (PresentationImpl) result.withAccessScope(AccessScopeKind.valueOf("INSTANCE")).withCoherentAccess(true).withOrderedAccess(true);
            p = pq.getPresentation().withGroup().withOrderedAccess(true).withCoherentAccess(true);
            assertTrue("Check for valid Presentation object", result.compareTo(p) == -1);

        } catch (Exception ex) {
            fail("Failed to get the Presentation qos from the PublisherQos");
        }
        assertTrue("Check for valid Presentation object", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.pub.PublisherQos#getPartition()}.
     */
    //@tTest
    public void testGetPartition() {
        checkValidEntities();
        PartitionImpl result = null;
        try {
            result = (PartitionImpl) pq.getPartition();
            assertTrue("Check for valid Partition object", result != null);
            assertTrue("Check for valid Partition PolicyClass object", result.getPolicyClass() != null);
            result = new PartitionImpl((OsplServiceEnvironment) env, new HashSet<String>());
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
        GroupDataImpl result = null;
        try {
            result = (GroupDataImpl) pq.getGroupData();
            assertTrue("Check for valid GroupData object", result != null);
            assertTrue("Check for valid GroupData PolicyClass object", result.getPolicyClass() != null);
            result = new GroupDataImpl((OsplServiceEnvironment) env, null);
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
        EntityFactoryImpl result = null;
        try {
            result = (EntityFactoryImpl) pq.getEntityFactory();
            assertTrue("Check for valid EntityFactory PolicyClass object", result.getPolicyClass() != null);

        } catch (Exception ex) {
            fail("Failed to get the EntityFactory qos from the PublisherQos");
        }
        assertTrue("Check for valid EntityFactory object", result != null);
    }
}
