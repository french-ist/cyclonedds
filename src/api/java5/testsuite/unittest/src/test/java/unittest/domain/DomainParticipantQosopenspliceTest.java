/**
 *
 */
package unittest.domain;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.dds.core.AlreadyClosedException;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.policy.EntityFactory;
import org.omg.dds.core.policy.UserData;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.domain.DomainParticipantFactory;
import org.opensplice.dds.core.OsplServiceEnvironment;
import org.opensplice.dds.core.policy.Scheduling.ListenerScheduling;
import org.opensplice.dds.core.policy.Scheduling.SchedulingClass;
import org.opensplice.dds.core.policy.Scheduling.SchedulingKind;
import org.opensplice.dds.core.policy.Scheduling.WatchdogScheduling;
import org.opensplice.dds.core.policy.SchedulingImpl;
import org.opensplice.dds.core.policy.UserDataImpl;
import org.opensplice.dds.domain.DomainParticipantQosImpl;

import org.eclipse.cyclonedds.test.AbstractUtilities;


public class DomainParticipantQosopenspliceTest {

    private final static int                DOMAIN_ID   = 123;
    private static ServiceEnvironment       env;
    private static DomainParticipantFactory dpf;
    private static AbstractUtilities        util        = AbstractUtilities.getInstance(DomainParticipantQosopenspliceTest.class);
    private final static Properties         prop        = new Properties();
    private static DomainParticipant        participant = null;
    private static DomainParticipantQosImpl dpq         = null;

    @BeforeClass
    public static void init() {
        try {
            String serviceEnvironment = System.getProperty("JAVA5PSM_SERVICE_ENV");
            assertTrue("Check for valid ServiceEnvironment string", !serviceEnvironment.equals(""));
            System.setProperty(ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY, serviceEnvironment);
            env = ServiceEnvironment.createInstance(DomainParticipantQosopenspliceTest.class.getClassLoader());
            assertTrue("Check for valid ServiceEnvironment object", env instanceof ServiceEnvironment);
            dpf = DomainParticipantFactory.getInstance(env);
            assertTrue("Check for valid DomainParticipantFactory object", dpf instanceof DomainParticipantFactory);
            prop.setProperty("domainid", new Integer(DOMAIN_ID).toString());
            assertTrue("Check is deamon is correctly started", util.beforeClass(prop));
            participant = dpf.createParticipant(DOMAIN_ID);
            assertTrue("Check for valid DomainParticipant object", participant instanceof DomainParticipant);
            assertTrue("Check for valid DomainParticipant with id " + DOMAIN_ID, participant.getDomainId() == DOMAIN_ID);
            dpq = (DomainParticipantQosImpl) participant.getQos();
            assertTrue("Check for valid DomainParticipantQosImpl object", dpq instanceof DomainParticipantQosImpl);
        } catch (Exception e) {
            fail("Exception occured while initiating the DomainParticipantQosopenspliceTest class: " + util.printException(e));
        }

    }

    private void checkValidEntities() {
        assertTrue("Check for valid DomainParticipant object", participant instanceof DomainParticipant);
        assertTrue("Check for valid DomainParticipantQosImpl object", dpq instanceof DomainParticipantQosImpl);
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
     * Test method for {@link org.opensplice.dds.domain.DomainParticipantQosImpl#DomainParticipantQosImpl(org.opensplice.dds.core.OsplServiceEnvironment, org.omg.dds.core.policy.QosPolicy.ForDomainParticipant[])}.
     */
    //@tTest
    public void testDomainParticipantQosImplOsplServiceEnvironmentForDomainParticipantArray() {
        checkValidEntities();
        DomainParticipantQosImpl result = null;
        try {
            UserData ud = new UserDataImpl((OsplServiceEnvironment) env);
            result = new DomainParticipantQosImpl((OsplServiceEnvironment) env, ud);
        } catch (Exception e) {
            fail("Exception occured while running testDomainParticipantQosImplOsplServiceEnvironmentForDomainParticipantArray: " + util.printException(e));
        }
        assertTrue("Check for invalid DomainParticipantQosImpl object", result != null);
    }

    /**
     * Test method for {@link org.opensplice.dds.domain.DomainParticipantQosImpl#getUserData()}.
     */
    //@tTest
    public void testGetUserData() {
        checkValidEntities();
        UserDataImpl result = null;
        try {
            result = (UserDataImpl) dpq.getUserData();
            assertTrue("Check for valid UserData PolicyClass object", result.getPolicyClass() != null);
        } catch (Exception ex) {
            fail("Failed to get the UserData qos from the DomainParticipantQos");
        }
        assertTrue("Check for valid UserData object failed", result != null);
    }

    /**
     * Test method for {@link org.opensplice.dds.domain.DomainParticipantQosImpl#getEntityFactory()}.
     */
    //@tTest
    public void testGetEntityFactory() {
        checkValidEntities();
        EntityFactory ef = null;
        try {
            ef = dpq.getEntityFactory();
        } catch (Exception ex) {
            fail("Failed to get the EntityFactory qos from the DomainParticipantQos");
        }
        assertTrue("Check for valid EntityFactory object", ef != null);
    }

    /**
     * Test method for {@link org.opensplice.dds.domain.DomainParticipantQosImpl#getListenerScheduling()}.
     */
    //@tTest
    public void testGetListenerScheduling() {
        checkValidEntities();
        ListenerScheduling result = null;
        try {
            result = dpq.getListenerScheduling();
            assertTrue("Check for valid ListenerScheduling object", result != null);
            result = (ListenerScheduling) result.withKind(SchedulingKind.valueOf("ABSOLUTE"));
            assertTrue("Check for valid ListenerScheduling kind value", result.getKind() == SchedulingKind.ABSOLUTE);
            result = (ListenerScheduling) result.withSchedulingClass(SchedulingClass.valueOf("DEFAULT"));
            assertTrue("Check for valid ListenerScheduling class value", result.getSchedulingClass() == SchedulingClass.DEFAULT);
            result = (ListenerScheduling) result.withPriority(1);
            assertTrue("Check for valid ListenerScheduling priority value", result.getPriority() == 1);
            assertTrue("Check for valid SchedulingImpl PolicyClass object", ((SchedulingImpl) result).getPolicyClass() != null);
        } catch (Exception ex) {
            fail("Failed to get the ListenerScheduling qos from the DomainParticipantQos");
        }
        assertTrue("Check for valid ListenerScheduling object", result != null);
    }

    /**
     * Test method for {@link org.opensplice.dds.domain.DomainParticipantQosImpl#getWatchdogScheduling()}.
     */
    //@tTest
    public void testGetWatchdogScheduling() {
        checkValidEntities();
        WatchdogScheduling result = null;
        try {
            result = dpq.getWatchdogScheduling();
            assertTrue("Check for valid ListenerScheduling object", result != null);
            result = (WatchdogScheduling) result.withKind(SchedulingKind.valueOf("RELATIVE"));
            assertTrue("Check for valid ListenerScheduling kind value", result.getKind() == SchedulingKind.RELATIVE);
            result = (WatchdogScheduling) result.withSchedulingClass(SchedulingClass.valueOf("REALTIME"));
            assertTrue("Check for valid ListenerScheduling class value", result.getSchedulingClass() == SchedulingClass.REALTIME);
            result = (WatchdogScheduling) result.withPriority(2);
            assertTrue("Check for valid ListenerScheduling priority value", result.getPriority() == 2);
            assertTrue("Check for valid SchedulingImpl PolicyClass object", ((SchedulingImpl) result).getPolicyClass() != null);
        } catch (Exception ex) {
            fail("Failed to get the WatchdogScheduling qos from the DomainParticipantQos");
        }
        assertTrue("Check for valid WatchdogScheduling object", result != null);
    }


}
