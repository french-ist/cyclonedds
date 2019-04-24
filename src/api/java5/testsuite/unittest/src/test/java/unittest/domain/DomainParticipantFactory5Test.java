/**
 *
 */
package unittest.domain;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.dds.core.AlreadyClosedException;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.status.DataAvailableStatus;
import org.omg.dds.core.status.Status;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.domain.DomainParticipantFactory;
import org.omg.dds.pub.Publisher;

import org.eclipse.cyclonedds.test.AbstractUtilities;


public class DomainParticipantFactory5Test {

    private final static int                DOMAIN_ID = 123;
    private static ServiceEnvironment       env;
    private static DomainParticipantFactory dpf;
    private static AbstractUtilities        util      = AbstractUtilities.getInstance(DomainParticipantFactory5Test.class);
    private final static Properties         prop      = new Properties();

    @BeforeClass
    public static void init() {
        String serviceEnvironment = System.getProperty("JAVA5PSM_SERVICE_ENV");
        assertTrue("Check for valid ServiceEnvironment string", !serviceEnvironment.equals(""));
        System.setProperty(ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY, serviceEnvironment);
        env = ServiceEnvironment.createInstance(DomainParticipantFactory5Test.class.getClassLoader());
        assertTrue("Check for valid ServiceEnvironment object", env instanceof ServiceEnvironment);
        dpf = DomainParticipantFactory.getInstance(env);
        assertTrue("Check for valid DomainParticipantFactory object", dpf instanceof DomainParticipantFactory);
        prop.setProperty("domainid", new Integer(DOMAIN_ID).toString());
        assertTrue("Check is deamon is correctly started", util.beforeClass(prop));

    }

    @AfterClass
    public static void cleanup() {
        assertTrue("Check is deamon is correctly stopped", util.afterClass(prop));

    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipantFactory#createParticipant(int, org.omg.dds.domain.DomainParticipantQos, org.omg.dds.domain.DomainParticipantListener, java.util.Collection)}
     * .
     */
    @Test
    public void testCreateParticipantWithNullQosArg() {
        org.omg.dds.domain.DomainParticipantQos qos = null;
        org.omg.dds.domain.DomainParticipantListener listener = null;
        Collection<Class<? extends Status>> statuses = null;
        DomainParticipant participant = null;
        try {
            participant = dpf.createParticipant(DOMAIN_ID, qos, listener, statuses);
        } catch (Exception ex) {
            assertTrue("Check for invalid DomainParticipant qos argument", util.exceptionCheck("testCreateParticipantWithNullQosArg", ex, ex instanceof IllegalArgumentException));
        }
        assertNull("Check for invalid DomainParticipant", participant);
    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipantFactory#createParticipant(int, org.omg.dds.domain.DomainParticipantQos, org.omg.dds.domain.DomainParticipantListener, java.util.Collection)}
     * .
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testCreateParticipantWithNullListenerArg() {
        org.omg.dds.domain.DomainParticipantQos qos = dpf.getDefaultParticipantQos();
        org.omg.dds.domain.DomainParticipantListener listener = null;
        DomainParticipant participant = null;
        try {
            participant = dpf.createParticipant(DOMAIN_ID, qos, listener, Status.class);
        } catch (Exception ex) {
            assertTrue("Check for invalid DomainParticipant status argument", util.exceptionCheck("testCreateParticipantWithNullListenerArg", ex, ex instanceof IllegalArgumentException));
        }
        assertNull("Check for invalid DomainParticipant", participant);
    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipantFactory#createParticipant(int, org.omg.dds.domain.DomainParticipantQos, org.omg.dds.domain.DomainParticipantListener, java.util.Collection)}
     * .
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testCreateParticipantWithArg() {
        org.omg.dds.domain.DomainParticipantQos qos = dpf.getDefaultParticipantQos();
        org.omg.dds.domain.DomainParticipantListener listener = null;
        DomainParticipant participant = null;
        try {
            participant = dpf.createParticipant(DOMAIN_ID, qos, listener, DataAvailableStatus.class);
        } catch (Exception ex) {
            assertTrue("Catch unsupported operation exception", util.exceptionCheck("testCreateParticipantWithArg", ex, ex instanceof UnsupportedOperationException));
        }
        assertTrue("Check for valid DomainParticipant object", util.objectCheck("testCreateParticipantWithArg", participant));
        if (participant != null) {
            assertTrue("Check for valid DomainParticipant with id " + DOMAIN_ID, participant.getDomainId() == DOMAIN_ID);
            participant.close();
            Publisher pub = null;
            try {
                pub = participant.createPublisher();
            } catch (Exception ex) {
                assertTrue("Check for AlreadyClosedException", ex instanceof AlreadyClosedException);
            }
            assertNull("Check for invalid Publisher object", pub);
        }

    }
}
