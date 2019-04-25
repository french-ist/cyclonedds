/**
 *
 */
package unittest.domain;

import static org.junit.Assert.assertTrue;

import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.dds.core.AlreadyClosedException;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.domain.DomainParticipantFactory;
import org.omg.dds.pub.Publisher;

import org.eclipse.cyclonedds.test.AbstractUtilities;


public class DomainParticipantFactory4Test {

    private final static int                DOMAIN_ID = 123;
    private static ServiceEnvironment       env;
    private static DomainParticipantFactory dpf;
    private static AbstractUtilities        util      = AbstractUtilities.getInstance(DomainParticipantFactory4Test.class);
    private final static Properties         prop      = new Properties();

    @BeforeClass
    public static void init() {
        String serviceEnvironment = System.getProperty("JAVA5PSM_SERVICE_ENV");
        assertTrue("Check for valid ServiceEnvironment string", !serviceEnvironment.equals(""));
        System.setProperty(ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY, serviceEnvironment);
        env = ServiceEnvironment.createInstance(DomainParticipantFactory4Test.class.getClassLoader());
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
     * Test method for {@link org.omg.dds.domain.DomainParticipantFactory#createParticipant(int)}.
     */
    @Test
    public void testCreateParticipantInt() {
        DomainParticipant participant = dpf.createParticipant(DOMAIN_ID);
        assertTrue("Check for valid DomainParticipant object", participant instanceof DomainParticipant);
        assertTrue("Check for valid DomainParticipant with id " + DOMAIN_ID, participant.getDomainId()==DOMAIN_ID);
        participant.close();
        Publisher pub = null;
        try {
            pub = participant.createPublisher();
        } catch (Exception ex) {
            assertTrue("Check for AlreadyClosedException", ex instanceof AlreadyClosedException);
        }
        assertTrue("Check for invalid Publisher object", pub == null);
    }
}
