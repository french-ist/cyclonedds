/**
 *
 */
package unittest.domain;

import static org.junit.Assert.assertTrue;

import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.domain.DomainParticipantFactory;

import org.eclipse.cyclonedds.test.AbstractUtilities;


public class DomainParticipantFactory1Test {

    private final static int                DOMAIN_ID = 0;
    private static ServiceEnvironment       env;
    private static DomainParticipantFactory dpf;
    private static AbstractUtilities        util      = AbstractUtilities.getInstance(DomainParticipantFactory1Test.class);
    private final static Properties         prop      = new Properties();
    @BeforeClass
    public static void init() {
        String serviceEnvironment = System.getProperty("JAVA5PSM_SERVICE_ENV");
        assertTrue("Check for valid ServiceEnvironment string", !serviceEnvironment.equals(""));
        System.setProperty(ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY, serviceEnvironment);
        env = ServiceEnvironment.createInstance(DomainParticipantFactory1Test.class.getClassLoader());
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
     * {@link org.omg.dds.domain.DomainParticipantFactory#createParticipant()} .
     */
    //@tTest
    public void testCreateParticipant() {

        DomainParticipant participant = dpf.createParticipant();
        assertTrue("Check for valid DomainParticipant object", participant instanceof DomainParticipant);
        assertTrue("Check for valid DomainParticipant with id " + DOMAIN_ID, participant.getDomainId() == DOMAIN_ID);
        participant.close();
    }
}
