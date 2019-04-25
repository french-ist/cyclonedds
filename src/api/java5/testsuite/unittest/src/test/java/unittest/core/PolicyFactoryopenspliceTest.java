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
import org.omg.dds.core.policy.DataRepresentation;
import org.omg.dds.core.policy.PolicyFactory;
import org.omg.dds.domain.DomainParticipantFactory;
import org.opensplice.dds.core.policy.PolicyFactoryImpl;
import org.opensplice.dds.core.policy.ReaderLifespan;
import org.opensplice.dds.core.policy.Scheduling.ListenerScheduling;
import org.opensplice.dds.core.policy.Scheduling.WatchdogScheduling;
import org.opensplice.dds.core.policy.Share;
import org.opensplice.dds.core.policy.SubscriptionKeys;
import org.opensplice.dds.core.policy.ViewKeys;

import org.eclipse.cyclonedds.test.AbstractUtilities;

public class PolicyFactoryopenspliceTest {

    private static ServiceEnvironment       env;
    private static DomainParticipantFactory dpf;
    private static AbstractUtilities        util        = AbstractUtilities.getInstance(PolicyFactoryopenspliceTest.class);
    private static PolicyFactoryImpl        pf   = null;

    @BeforeClass
    public static void init() {
        try {
            String serviceEnvironment = System.getProperty("JAVA5PSM_SERVICE_ENV");
            assertTrue("Check for valid ServiceEnvironment string", !serviceEnvironment.equals(""));
            System.setProperty(ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY, serviceEnvironment);
            env = ServiceEnvironment.createInstance(PolicyFactoryopenspliceTest.class.getClassLoader());
            assertTrue("Check for valid ServiceEnvironment object", env instanceof ServiceEnvironment);
            dpf = DomainParticipantFactory.getInstance(env);
            assertTrue("Check for valid DomainParticipantFactory object", dpf instanceof DomainParticipantFactory);
            pf = (PolicyFactoryImpl) dpf.getQos().getPolicyFactory();
        } catch (Exception e) {
            fail("Exception occured while initiating the PolicyFactoryopenspliceTest class: " + util.printException(e));
        }

    }

    private void checkValidEntities() {
        assertTrue("Check for valid PolicyFactory object", pf instanceof PolicyFactory);
    }

    @AfterClass
    public static void cleanup() {

    }

    /**
     * Test method for
     * {@link org.omg.dds.core.policy.PolicyFactory#DataRepresentation()}.
     */
    //@tTest
    public void testDataRepresentation() {
        checkValidEntities();
        DataRepresentation result = null;
        try {
            result = pf.DataRepresentation();
        } catch (Exception ex) {
            fail("Failed to get the DataRepresentation");
        }
        assertTrue("Check for valid DataRepresentation object", result != null);
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.core.policy.PolicyFactory#Share()}.
     */
    //@tTest
    public void testShare() {
        checkValidEntities();
        Share result = null;
        try {
            result = pf.Share();
        } catch (Exception ex) {
            fail("Failed to get the Share");
        }
        assertTrue("Check for valid Share object", result != null);
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.core.policy.PolicyFactory#SubscriptionKeys()}.
     */
    //@tTest
    public void testSubscriptionKeys() {
        checkValidEntities();
        SubscriptionKeys result = null;
        try {
            result = pf.SubscriptionKeys();
        } catch (Exception ex) {
            fail("Failed to get the SubscriptionKeys");
        }
        assertTrue("Check for valid SubscriptionKeys object", result != null);
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.core.policy.PolicyFactory#ViewKeys()}.
     */
    //@tTest
    public void testViewKeys() {
        checkValidEntities();
        ViewKeys result = null;
        try {
            result = pf.ViewKeys();
        } catch (Exception ex) {
            fail("Failed to get the ViewKeys");
        }
        assertTrue("Check for valid ViewKeys object", result != null);
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.core.policy.PolicyFactory#ReaderLifespan()}.
     */
    //@tTest
    public void testReaderLifespan() {
        checkValidEntities();
        ReaderLifespan result = null;
        try {
            result = pf.ReaderLifespan();
        } catch (Exception ex) {
            fail("Failed to get the ReaderLifespan");
        }
        assertTrue("Check for valid ReaderLifespan object", result != null);
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.core.policy.PolicyFactory#WatchdogScheduling()}
     * .
     */
    //@tTest
    public void testWatchdogScheduling() {
        checkValidEntities();
        WatchdogScheduling result = null;
        try {
            result = pf.WatchDogScheduling();
        } catch (Exception ex) {
            fail("Failed to get the WatchdogScheduling");
        }
        assertTrue("Check for valid WatchdogScheduling object", result != null);
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.core.policy.PolicyFactory#ListenerScheduling()}
     * .
     */
    //@tTest
    public void testListenerScheduling() {
        checkValidEntities();
        ListenerScheduling result = null;
        try {
            result = pf.ListenerScheduling();
        } catch (Exception ex) {
            fail("Failed to get the ListenerScheduling");
        }
        assertTrue("Check for valid ListenerScheduling object", result != null);
    }

}
