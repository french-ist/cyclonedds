package unittest.sub;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.dds.core.AlreadyClosedException;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.domain.DomainParticipantFactory;
import org.omg.dds.sub.Subscriber;
import org.omg.dds.sub.SubscriberQos;
import org.opensplice.dds.core.OsplServiceEnvironment;
import org.opensplice.dds.core.policy.Share;
import org.opensplice.dds.core.policy.ShareImpl;
import org.opensplice.dds.sub.SubscriberQosImpl;

import org.eclipse.cyclonedds.test.AbstractUtilities;

public class SubscriberQosopenspliceTest {

    private final static int                DOMAIN_ID   = 123;
    private static ServiceEnvironment       env;
    private static DomainParticipantFactory dpf;
    private static AbstractUtilities        util        = AbstractUtilities.getInstance(SubscriberQosopenspliceTest.class);
    private final static Properties         prop        = new Properties();
    private static DomainParticipant        participant = null;
    private static Subscriber               subscriber  = null;
    private static SubscriberQosImpl        sq          = null;

    @BeforeClass
    public static void init() {
        try {
            String serviceEnvironment = System.getProperty("JAVA5PSM_SERVICE_ENV");
            assertTrue("Check for valid ServiceEnvironment string", !serviceEnvironment.equals(""));
            System.setProperty(ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY, serviceEnvironment);
            env = ServiceEnvironment.createInstance(SubscriberQosopenspliceTest.class.getClassLoader());
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
            sq = (SubscriberQosImpl) subscriber.getQos();
            assertTrue("Check for valid SubscriberQos object", sq instanceof SubscriberQos);
            Share s = new ShareImpl((OsplServiceEnvironment) env, "Test");
            sq = (SubscriberQosImpl) sq.withPolicies(s);
        } catch (Exception e) {
            fail("Exception occured while initiating the SubscriberQosopenspliceTest class: " + util.printException(e));
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
     * Test method for
     * {@link org.opensplice.dds.sub.SubscriberQosImp#getShare()}.
     */
    //@tTest
    public void testGetShare() {
        checkValidEntities();
        Share s = null;
        try {
            s = sq.getShare();
        } catch (Exception ex) {
            fail("Failed to get the Share qos from the SubscriberQos");
        }
        assertTrue("Check for valid Share object", s != null);
    }

    /**
     * Test method for {@link org.opensplice.dds.sub.SubscriberQosImp#convert()}
     * .
     */
    //@tTest
    public void testConvert() {
        checkValidEntities();
        SubscriberQosImpl result = null;
        try {
            result = SubscriberQosImpl.convert((OsplServiceEnvironment) env, null);
        } catch (Exception e) {
            assertTrue("Expected IllegalArgumentException but got: " + util.printException(e), util.exceptionCheck("testConvert", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for invalid Selector object", result == null);
    }


}
