package unittest.sub;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.domain.DomainParticipantFactory;
import org.omg.dds.sub.Subscriber;
import org.omg.dds.sub.SubscriberQos;

import org.eclipse.cyclonedds.test.AbstractUtilities;

public class SubscriberQosTest {

    private final static int                DOMAIN_ID   = 123;
    private static ServiceEnvironment       env;
    private static DomainParticipantFactory dpf;
    private static AbstractUtilities        util        = AbstractUtilities.getInstance(SubscriberQosTest.class);
    private final static Properties         prop        = new Properties();
    private static DomainParticipant        participant = null;
    private static Subscriber               subscriber  = null;
    private static SubscriberQos            sq          = null;

    @BeforeClass
    public static void init() {
        try {
            String serviceEnvironment = System.getProperty("JAVA5PSM_SERVICE_ENV");
            assertTrue("Check for valid ServiceEnvironment string", !serviceEnvironment.equals(""));
            System.setProperty(ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY, serviceEnvironment);
            env = ServiceEnvironment.createInstance(SubscriberQosTest.class.getClassLoader());
            assertTrue("Check for valid ServiceEnvironment object", env instanceof ServiceEnvironment);
            dpf = DomainParticipantFactory.getInstance(env);
            assertTrue("Check for valid DomainParticipantFactory object", dpf instanceof DomainParticipantFactory);
            prop.setProperty("domainid", new Integer(DOMAIN_ID).toString());
            assertTrue("Check is deamon is correctly started", util.beforeClass(prop));
            participant = dpf.createParticipant(DOMAIN_ID);
            assertTrue("Check for valid DomainParticipant object", participant instanceof DomainParticipant);
            assertTrue("Check for valid DomainParticipant with id " + DOMAIN_ID, participant.getDomainId() == DOMAIN_ID);
            subscriber = participant.createSubscriber();
            assertTrue("Check for valid Subscriber object", subscriber instanceof Subscriber);
            sq = subscriber.getQos();
            assertTrue("Check for valid SubscriberQos object", sq instanceof SubscriberQos);
        } catch (Exception e) {
            fail("Exception occured while initiating the SubscriberQosTest class: " + util.printException(e));
        }

    }

    private void checkValidEntities() {
        assertTrue("Check for valid DomainParticipant object", participant instanceof DomainParticipant);
        assertTrue("Check for valid Subscriber object", subscriber instanceof Subscriber);
        assertTrue("Check for valid SubscriberQos object", sq instanceof SubscriberQos);
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
     * Test method for {@link org.omg.dds.sub.SubscriberQos#getPresentation()}.
     */
    //@tTest
    public void testGetPresentation() {
        checkValidEntities();
        Presentation pr = null;
        try {
            pr = sq.getPresentation();
        } catch (Exception ex) {
            fail("Failed to get the Presentation qos from the SubscriberQos");
        }
        assertTrue("Check for valid Presentation object", pr != null);
    }

    /**
     * Test method for {@link org.omg.dds.sub.SubscriberQos#getPartition()}.
     */
    //@tTest
    public void testGetPartition() {
        checkValidEntities();
        Partition p = null;
        try {
            p = sq.getPartition();
        } catch (Exception ex) {
            fail("Failed to get the Partition qos from the SubscriberQos");
        }
        assertTrue("Check for valid Partition object", p != null);
    }

    /**
     * Test method for {@link org.omg.dds.sub.SubscriberQos#getGroupData()}.
     */
    //@tTest
    public void testGetGroupData() {
        checkValidEntities();
        GroupData gd = null;
        try {
            gd = sq.getGroupData();
        } catch (Exception ex) {
            fail("Failed to get the GroupData qos from the SubscriberQos");
        }
        assertTrue("Check for valid GroupData object", gd != null);
    }

    /**
     * Test method for {@link org.omg.dds.sub.SubscriberQos#getEntityFactory()}.
     */
    //@tTest
    public void testGetEntityFactory() {
        checkValidEntities();
        EntityFactory ef = null;
        try {
            ef = sq.getEntityFactory();
        } catch (Exception ex) {
            fail("Failed to get the EntityFactory qos from the SubscriberQos");
        }
        assertTrue("Check for valid EntityFactory object", ef != null);
    }
}
