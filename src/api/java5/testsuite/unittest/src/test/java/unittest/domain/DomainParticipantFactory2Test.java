/**
 *
 */
package unittest.domain;

import static org.junit.Assert.assertTrue;

import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.omg.dds.core.DDSException;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.domain.DomainParticipantFactory;

import org.eclipse.cyclonedds.test.AbstractUtilities;


public class DomainParticipantFactory2Test {

    private final static int                DOMAIN_ID = -1;
    private static ServiceEnvironment       env;
    private static DomainParticipantFactory dpf;
    private static AbstractUtilities        util      = AbstractUtilities.getInstance(DomainParticipantFactory2Test.class);
    private final static Properties         prop      = new Properties();

    @BeforeClass
    public static void init() {
        String serviceEnvironment = System.getProperty("JAVA5PSM_SERVICE_ENV");
        assertTrue("Check for valid ServiceEnvironment string", !serviceEnvironment.equals(""));
        System.setProperty(ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY, serviceEnvironment);
        env = ServiceEnvironment.createInstance(DomainParticipantFactory2Test.class.getClassLoader());
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
    @Ignore
    public void testCreateParticipantInt() {
        /* try negative domain */
        DomainParticipant participant = null;
        try {
            participant = dpf.createParticipant(DOMAIN_ID);
        } catch (Exception ex) {
            ex.printStackTrace();
            assertTrue("Check for Exception", ex instanceof DDSException);
        }
        assertTrue("Check for invalid DomainParticipant object", participant == null);

    }
}