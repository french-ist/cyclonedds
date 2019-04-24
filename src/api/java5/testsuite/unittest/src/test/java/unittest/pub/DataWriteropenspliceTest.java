
package unittest.pub;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.dds.core.AlreadyClosedException;
import org.omg.dds.core.InstanceHandle;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.Time;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.domain.DomainParticipantFactory;
import org.omg.dds.pub.DataWriter;
import org.omg.dds.pub.Publisher;
import org.omg.dds.topic.Topic;
import org.opensplice.dds.pub.DataWriterImpl;

import Test.Msg;

import org.eclipse.cyclonedds.test.AbstractUtilities;

public class DataWriteropenspliceTest {

    private final static int                DOMAIN_ID   = 123;
    private static ServiceEnvironment       env;
    private static DomainParticipantFactory dpf;
    private static AbstractUtilities        util        = AbstractUtilities.getInstance(DataWriteropenspliceTest.class);
    private final static Properties         prop        = new Properties();
    private static DomainParticipant        participant = null;
    private static Publisher                publisher   = null;
    private static Topic<Msg>               topic       = null;
    private static DataWriterImpl<Msg>      dataWriter  = null;
    private static String                   topicName   = "DataWriteropenspliceTest";

    @BeforeClass
    public static void init() {
        try {
            String serviceEnvironment = System.getProperty("JAVA5PSM_SERVICE_ENV");
            assertTrue("Check for valid ServiceEnvironment string", !serviceEnvironment.equals(""));
            System.setProperty(ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY, serviceEnvironment);
            env = ServiceEnvironment.createInstance(DataWriteropenspliceTest.class.getClassLoader());
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
            assertTrue("Check for valid Topic object", topic instanceof Topic);
            dataWriter = (DataWriterImpl<Msg>) publisher.createDataWriter(topic);
            assertTrue("Check for valid DataWriter object", dataWriter instanceof DataWriter);
        } catch (Exception e) {
            fail("Exception occured while initiating the DataWriteropenspliceTest class: " + util.printException(e));
        }

    }

    private void checkValidEntities() {
        assertTrue("Check for valid DomainParticipant object", participant instanceof DomainParticipant);
        assertTrue("Check for valid Publisher object", publisher instanceof Publisher);
        assertTrue("Check for valid Topic object", topic instanceof Topic);
        assertTrue("Check for valid DataWriter object", dataWriter instanceof DataWriter);
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
     * Test method for {@link org.opensplice.dds.core.EntityImpl#getOldParent()}
     * .
     */
    @Test
    public void testGetOldParent() {
        checkValidEntities();
        DDS.Publisher pub = null;
        try {
            pub = dataWriter.getOldParent();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetOldParent", e, false));
        }
        assertTrue("Check for valid Parent object failed", pub != null);
    }


    /**
     * Test method for
     * {@link org.opensplice.dds.core.EntityImpl#setProperty(java.lang.String, java.lang.String)}
     * .
     */
    @Test
    public void testSetProperty() {
        checkValidEntities();
        String result = null;
        try {
            dataWriter.setProperty("Foo", "Bar");
            dataWriter.getProperty("Foo");
            assertTrue("Check for valid String object failed", result != null);
            assertTrue("Check for valid property value failed", result.equals("Bar"));
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testSetProperty", e, false));
        }
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.core.EntityImpl#setProperty(java.lang.String, java.lang.String)}
     * .
     */
    @Test
    public void testSetPropertyNull() {
        checkValidEntities();
        boolean exceptionOccured = false;
        try {
            dataWriter.setProperty(null, "Bar");
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testSetPropertyNull", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException has occured failed", exceptionOccured);
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.core.EntityImpl#getProperty(java.lang.String)}
     * .
     */
    @Test
    public void testGetProperty() {
        checkValidEntities();
        String result = null;
        try {
            result = dataWriter.getProperty("Foo");
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetProperty", e, false));
        }
        assertTrue("Check for valid String object failed", result == null);
    }


    /**
     * Test method for
     * {@link org.opensplice.dds.pub.DataWriter#writeDispose(java.lang.Object)}.
     */
    @Test
    public void testWriteDisposeTYPE() {
        checkValidEntities();
        Msg message = new Msg(1, "testWriteDisposeTYPE");
        try {
            dataWriter.writeDispose(message);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testWriteDisposeTYPE", e, false));
        }
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.pub.DataWriter#writeDispose(java.lang.Object)}.
     */
    @Test
    public void testWriteDisposeTYPENull() {
        checkValidEntities();
        boolean exceptionOccured = false;
        try {
            dataWriter.writeDispose(null);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testWriteDisposeTYPENull", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException has occured failed", exceptionOccured);
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.pub.DataWriter#writeDispose(java.lang.Object, org.omg.dds.core.Time)}
     * .
     */
    @Test
    public void testWriteDisposeTYPETime() {
        checkValidEntities();
        Msg message = new Msg(1, "testWriteDisposeTYPETime");
        Time t = Time.newTime(5, TimeUnit.SECONDS, env);
        try {
            dataWriter.writeDispose(message, t);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testWriteDisposeTYPETime", e, false));
        }
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.pub.DataWriter#writeDispose(java.lang.Object, org.omg.dds.core.Time)}
     * .
     */
    @Test
    public void testWriteDisposeTYPETimeNull() {
        checkValidEntities();
        Msg message = new Msg(1, "testWriteDisposeTYPETimeNull");
        Time t = null;
        boolean exceptionOccured = false;
        try {
            dataWriter.writeDispose(message, t);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testWriteDisposeTYPETimeNull", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException has occured failed", exceptionOccured);
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.pub.DataWriter#writeDispose(java.lang.Object, long, java.util.concurrent.TimeUnit)}
     * .
     */
    @Test
    public void testWriteDisposeTYPELongTimeUnit() {
        checkValidEntities();
        Msg message = new Msg(1, "testWriteDisposeTYPELongTimeUnit");
        try {
            dataWriter.writeDispose(message, 5, TimeUnit.SECONDS);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testWriteDisposeTYPELongTimeUnit", e, false));
        }
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.pub.DataWriter#writeDispose(java.lang.Object, long, java.util.concurrent.TimeUnit)}
     * .
     */
    @Test
    public void testWriteDisposeTYPELongTimeUnitNull() {
        checkValidEntities();
        Msg message = new Msg(1, "testWriteDisposeTYPELongTimeUnitNull");
        boolean exceptionOccured = false;
        try {
            dataWriter.writeDispose(message, 5, null);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testWriteDisposeTYPETimeNull", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException has occured failed", exceptionOccured);
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.pub.DataWriter#writeDispose(java.lang.Object, org.omg.dds.core.InstanceHandle)}
     * .
     */
    @Test
    public void testWriteDisposeTYPEInstanceHandle() {
        checkValidEntities();
        Msg message = new Msg(1, "testWriteDisposeTYPEInstanceHandle");
        InstanceHandle ih = null;
        try {
            ih = dataWriter.registerInstance(message);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testWriteDisposeTYPEInstanceHandle", e, false));
        }
        try {
            dataWriter.writeDispose(message, ih);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testWriteDisposeTYPEInstanceHandle", e, false));
        }
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.pub.DataWriter#writeDispose(java.lang.Object, org.omg.dds.core.InstanceHandle, org.omg.dds.core.Time)}
     * .
     */
    @Test
    public void testWriteDisposeTYPEInstanceHandleTime() {
        checkValidEntities();
        Msg message = new Msg(1, "testWriteDisposeTYPEInstanceHandleTime");
        Time t = Time.newTime(5, TimeUnit.SECONDS, env);
        InstanceHandle ih = null;
        try {
            ih = dataWriter.registerInstance(message);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testWriteDisposeTYPEInstanceHandleTime", e, false));
        }
        try {
            dataWriter.writeDispose(message, ih, t);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testWriteDisposeTYPEInstanceHandleTime", e, false));
        }
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.pub.DataWriter#writeDispose(java.lang.Object, org.omg.dds.core.InstanceHandle, long, java.util.concurrent.TimeUnit)}
     * .
     */
    @Test
    public void testWriteDisposeTYPEInstanceHandleLongTimeUnit() {
        checkValidEntities();
        Msg message = new Msg(1, "testWriteDisposeTYPEInstanceHandleLongTimeUnit");
        InstanceHandle ih = null;
        try {
            ih = dataWriter.registerInstance(message);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testWriteDisposeTYPEInstanceHandleLongTimeUnit", e, false));
        }
        try {
            dataWriter.writeDispose(message, ih, 5, TimeUnit.SECONDS);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testWriteDisposeTYPEInstanceHandleLongTimeUnit", e, false));
        }
    }

}
