/**
 *
 */
package unittest.domain;

import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.HashSet;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.status.DataAvailableStatus;
import org.omg.dds.core.status.Status;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.domain.DomainParticipantFactory;
import org.omg.dds.domain.DomainParticipantListener;
import org.omg.dds.domain.DomainParticipantQos;

import org.eclipse.cyclonedds.test.AbstractUtilities;
import org.eclipse.cyclonedds.test.DomainParticipantListenerClass;


public class DomainParticipantFactory7Test {

    private final static int                DOMAIN_ID = 123;
    private static ServiceEnvironment       env;
    private static DomainParticipantFactory dpf;
    private static AbstractUtilities        util        = AbstractUtilities.getInstance(DomainParticipantFactory7Test.class);
    private final static Properties         prop        = new Properties();

    @BeforeClass
    public static void init() {
        String serviceEnvironment = System.getProperty("JAVA5PSM_SERVICE_ENV");
        assertTrue("Check for valid ServiceEnvironment string", !serviceEnvironment.equals(""));
        System.setProperty(ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY, serviceEnvironment);
        env = ServiceEnvironment.createInstance(DomainParticipantFactory7Test.class.getClassLoader());
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
    public void testCreateParticipantIntDomainParticipantQosDomainParticipantListenerCollectionOfClassOfQextendsStatus() {
        DomainParticipant participant = null;
        DomainParticipantListener listener = new DomainParticipantListenerClass();
        Collection<Class<? extends Status>> statuses = new HashSet<Class<? extends Status>>();
        statuses.add(DataAvailableStatus.class);
        DomainParticipantQos dpq = dpf.getDefaultParticipantQos();
        assertTrue("Check for valid SubscriberQos object", dpq instanceof DomainParticipantQos);
        try {
            participant = dpf.createParticipant(DOMAIN_ID, dpq, listener, statuses);
        } catch (Exception e) {
            assertTrue("Exception occured while creating a Participant with qos, listener and status col: " + e.toString(),
                    util.exceptionCheck("testCreateParticipantIntDomainParticipantQosDomainParticipantListenerCollectionOfClassOfQextendsStatus", e, false));
        }
        assertTrue("Check for valid Participant object", util.objectCheck("testCreateParticipantIntDomainParticipantQosDomainParticipantListenerCollectionOfClassOfQextendsStatus", participant));
        if (participant != null) {
            participant.close();
        }
    }

    /**
     * Test method for {@link
     * org.omg.dds.domain.DomainParticipantFactory#createParticipant(int,
     * org.omg.dds.domain.DomainParticipantQos,
     * org.omg.dds.domain.DomainParticipantListener, java.lang.Class<? extends
     * org.omg.dds.core.status.Status>[])}.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testCreateParticipantIntDomainParticipantQosDomainParticipantListenerClassOfQextendsStatusArray() {
        DomainParticipant participant = null;
        DomainParticipantListener listener = new DomainParticipantListenerClass();
        Class<? extends Status> status = DataAvailableStatus.class;
        DomainParticipantQos dpq = dpf.getDefaultParticipantQos();
        assertTrue("Check for valid SubscriberQos object", dpq instanceof DomainParticipantQos);
        try {
            participant = dpf.createParticipant(DOMAIN_ID, dpq, listener, status);
        } catch (Exception e) {
            assertTrue("Exception occured while creating a Participant with qos, listener and status: " + e.toString(),
                    util.exceptionCheck("testCreateParticipantIntDomainParticipantQosDomainParticipantListenerClassOfQextendsStatusArray", e, false));
        }
        assertTrue("Check for valid Participant object", util.objectCheck("testCreateParticipantIntDomainParticipantQosDomainParticipantListenerClassOfQextendsStatusArray", participant));
        if (participant != null) {
            participant.close();
        }
    }
}
