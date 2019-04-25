/**
 *
 */
package unittest.type;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.dds.core.AlreadyClosedException;
import org.omg.dds.core.PreconditionNotMetException;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.domain.DomainParticipantFactory;
import org.omg.dds.pub.Publisher;
import org.omg.dds.topic.Topic;
import org.omg.dds.type.TypeSupport;

import Test.Msg;

import org.eclipse.cyclonedds.test.AbstractUtilities;

public class TypeSupportTest {

    private final static int                DOMAIN_ID   = 123;
    private static ServiceEnvironment       env;
    private static DomainParticipantFactory dpf;
    private static AbstractUtilities        util        = AbstractUtilities.getInstance(TypeSupportTest.class);
    private final static Properties         prop        = new Properties();
    private static DomainParticipant        participant = null;
    private static Publisher                publisher   = null;
    private static Topic<Msg>               topic       = null;
    private static String                   topicName   = "TypeSupportTest";

    @BeforeClass
    public static void init() {
        try {
            String serviceEnvironment = System.getProperty("JAVA5PSM_SERVICE_ENV");
            assertTrue("Check for valid ServiceEnvironment string", !serviceEnvironment.equals(""));
            System.setProperty(ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY, serviceEnvironment);
            env = ServiceEnvironment.createInstance(TypeSupportTest.class.getClassLoader());
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
            topic = participant.createTopic(topicName, Msg.class);
        } catch (Exception e) {
            fail("Exception occured while initiating the TypeSupportTest class: " + util.printException(e));
        }

    }

    private void checkValidEntities() {
        assertTrue("Check for valid DomainParticipant object", participant instanceof DomainParticipant);
        assertTrue("Check for valid Publisher object", publisher instanceof Publisher);
        assertTrue("Check for valid Topic object", topic instanceof Topic);
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
     * Test method for {@link org.omg.dds.type.TypeSupport#newTypeSupport(java.lang.Class, org.omg.dds.core.ServiceEnvironment)}.
     */
    //@tTest
    public void testNewTypeSupportClassOfTYPEServiceEnvironment() {
        checkValidEntities();
        TypeSupport<Msg> result = null;
        try {
            result = TypeSupport.newTypeSupport(Msg.class, env);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testNewTypeSupportClassOfTYPEServiceEnvironment", e, false));
        }
        assertTrue("Check for valid TypeSupport object", util.objectCheck("testNewTypeSupportClassOfTYPEServiceEnvironment", result));
    }

    /**
     * Test method for
     * {@link org.omg.dds.type.TypeSupport#newTypeSupport(java.lang.Class, org.omg.dds.core.ServiceEnvironment)}
     * .
     */
    //@tTest
    public void testNewTypeSupportClassOfTYPENullServiceEnvironment() {
        checkValidEntities();
        TypeSupport<Msg> result = null;
        boolean exceptionOccured = false;
        try {
            result = TypeSupport.newTypeSupport(null, env);
        } catch (Exception e) {
            assertTrue("Check for IllegalArgumentException but got exception:" + util.printException(e),
                    util.exceptionCheck("testNewTypeSupportClassOfTYPENullServiceEnvironment", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException occured failed", exceptionOccured);
        assertTrue("Check for invalid TypeSupport object", result == null);
    }

    /**
     * Test method for
     * {@link org.omg.dds.type.TypeSupport#newTypeSupport(java.lang.Class, org.omg.dds.core.ServiceEnvironment)}
     * .
     */
    //@tTest
    public void testNewTypeSupportClassOfTYPEServiceEnvironmentInvalid() {
        checkValidEntities();
        TypeSupport<DomainParticipant> result = null;
        boolean exceptionOccured = false;
        try {
            result = TypeSupport.newTypeSupport(DomainParticipant.class, env);
        } catch (Exception e) {
            assertTrue("Check for PreconditionNotMetException but got exception:" + util.printException(e),
                    util.exceptionCheck("testNewTypeSupportClassOfTYPEServiceEnvironmentInvalid", e, e instanceof PreconditionNotMetException));
            exceptionOccured = true;
        }
        assertTrue("Check if PreconditionNotMetException occured failed", exceptionOccured);
        assertTrue("Check for invalid TypeSupport object", result == null);
    }

    /**
     * Test method for
     * {@link org.omg.dds.type.TypeSupport#newTypeSupport(java.lang.Class, org.omg.dds.core.ServiceEnvironment)}
     * .
     */
    //@tTest
    public void testNewTypeSupportClassOfTYPEServiceEnvironmentNull() {
        checkValidEntities();
        TypeSupport<Msg> result = null;
        boolean exceptionOccured = false;
        try {
            result = TypeSupport.newTypeSupport(Msg.class, null);
        } catch (Exception e) {
            assertTrue("Check for IllegalArgumentException but got exception:" + util.printException(e),
                    util.exceptionCheck("testNewTypeSupportClassOfTYPEServiceEnvironmentNull", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException occured failed", exceptionOccured);
        assertTrue("Check for invalid TypeSupport object", result == null);
    }

    /**
     * Test method for
     * {@link org.omg.dds.type.TypeSupport#newTypeSupport(java.lang.Class, java.lang.String, org.omg.dds.core.ServiceEnvironment)}
     * .
     */
    //@tTest
    public void testNewTypeSupportClassOfTYPEStringServiceEnvironment() {
        checkValidEntities();
        TypeSupport<Msg> result = null;
        try {
            result = TypeSupport.newTypeSupport(Msg.class, "Message", env);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testNewTypeSupportClassOfTYPEStringServiceEnvironment", e, false));
        }
        assertTrue("Check for valid TypeSupport object", util.objectCheck("testNewTypeSupportClassOfTYPEStringServiceEnvironment", result));
    }

    /**
     * Test method for {@link org.omg.dds.type.TypeSupport#newData()}.
     */
    //@tTest
    public void testNewData() {
        checkValidEntities();
        TypeSupport<Msg> result = null;
        Msg msgobj = null;
        try {
            result = TypeSupport.newTypeSupport(Msg.class, "testNewData", env);
            assertTrue("Check for valid TypeSupport object", util.objectCheck("testNewData", result));
            msgobj = result.newData();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testNewData", e, false));
        }
        assertTrue("Check for valid Msg object", util.objectCheck("testNewData", msgobj));
    }

    /**
     * Test method for {@link org.omg.dds.type.TypeSupport#getType()}.
     */
    //@tTest
    public void testGetType() {
        checkValidEntities();
        TypeSupport<Msg> result = null;
        Class<Msg> type = null;
        try {
            result = TypeSupport.newTypeSupport(Msg.class, "testGetTypeTypeSupport", env);
            assertTrue("Check for valid TypeSupport object", util.objectCheck("testGetTypeTypeSupport", result));
            type = result.getType();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetTypeTypeSupport", e, false));
        }
        assertTrue("Check for valid Type object", type != null);
    }

    /**
     * Test method for {@link org.omg.dds.type.TypeSupport#getTypeName()}.
     */
    //@tTest
    public void testGetTypeName() {
        checkValidEntities();
        TypeSupport<Msg> result = null;
        String typeName = null;
        try {
            result = TypeSupport.newTypeSupport(Msg.class, "testGetTypeNameTypeSupport", env);
            assertTrue("Check for valid TypeSupport object", util.objectCheck("testGetTypeNameTypeSupport", result));
            typeName = result.getTypeName();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetTypeNameTypeSupport", e, false));
        }
        assertTrue("Check for valid typeName string", typeName.equals("testGetTypeNameTypeSupport"));
    }

}
