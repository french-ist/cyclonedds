/**
 *
 */
package unittest.sub;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Properties;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.domain.DomainParticipantFactory;
import org.omg.dds.pub.Publisher;
import org.omg.dds.sub.DataReader;
import org.omg.dds.sub.InstanceState;
import org.omg.dds.sub.ReadCondition;
import org.omg.dds.sub.SampleState;
import org.omg.dds.sub.Subscriber;
import org.omg.dds.sub.Subscriber.DataState;
import org.omg.dds.sub.ViewState;
import org.omg.dds.topic.Topic;

import Test.Msg;

import org.eclipse.cyclonedds.test.AbstractUtilities;


public class ReadConditionTest {

    private final static int                DOMAIN_ID   = 123;
    private static ServiceEnvironment       env;
    private static DomainParticipantFactory dpf;
    private static AbstractUtilities        util        = AbstractUtilities.getInstance(ReadConditionTest.class);
    private final static Properties         prop        = new Properties();
    private static DomainParticipant        participant = null;
    private static Publisher                publisher   = null;
    private static Subscriber               subscriber  = null;
    private static Topic<Msg>               topic       = null;
    private static DataReader<Msg>          dataReader  = null;
    private static String                   topicName   = "ReadConditionTest";
    private static ReadCondition<Msg>       condition   = null;

    @BeforeClass
    public static void init() {
        try {
            String serviceEnvironment = System.getProperty("JAVA5PSM_SERVICE_ENV");
            assertTrue("Check for valid ServiceEnvironment string", !serviceEnvironment.equals(""));
            System.setProperty(ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY, serviceEnvironment);
            env = ServiceEnvironment.createInstance(ReadConditionTest.class.getClassLoader());
            assertTrue("Check for valid ServiceEnvironment object failed", env instanceof ServiceEnvironment);
            dpf = DomainParticipantFactory.getInstance(env);
            assertTrue("Check for valid DomainParticipantFactory object failed", dpf instanceof DomainParticipantFactory);
            prop.setProperty("domainid", new Integer(DOMAIN_ID).toString());
            assertTrue("Check is deamon is correctly started", util.beforeClass(prop));
            participant = dpf.createParticipant(DOMAIN_ID);
            assertTrue("Check for valid DomainParticipant object failed", participant instanceof DomainParticipant);
            assertTrue("Check for valid DomainParticipant with id " + DOMAIN_ID, participant.getDomainId() == DOMAIN_ID);
            publisher = participant.createPublisher();
            assertTrue("Check for valid Publisher object failed", publisher instanceof Publisher);
            subscriber = participant.createSubscriber();
            assertTrue("Check for valid Subscriber object failed", subscriber instanceof Subscriber);
            topic = participant.createTopic(topicName, Msg.class);
            assertTrue("Check for valid Topic object failed", topic instanceof Topic);
            dataReader = subscriber.createDataReader(topic);
            assertTrue("Check for valid DataReader object failed", dataReader instanceof DataReader);
            DataState ds = null;
            ds = subscriber.createDataState();
            condition = dataReader.createReadCondition(ds.withAnyInstanceState().withAnySampleState().withAnyViewState());
        } catch (Exception e) {
            fail("Exception occured while initiating the ReadConditionTest class: " + util.printException(e));
        }

    }

    private void checkValidEntities() {
        assertTrue("Check for valid DomainParticipant object failed", participant instanceof DomainParticipant);
        assertTrue("Check for valid Publisher object failed", publisher instanceof Publisher);
        assertTrue("Check for valid Subscriber object failed", subscriber instanceof Subscriber);
        assertTrue("Check for valid Topic object failed", topic instanceof Topic);
        assertTrue("Check for valid DataReader object failed", dataReader instanceof DataReader);
        assertTrue("Check for valid ReadCondition object failed", condition instanceof ReadCondition);
    }

    @AfterClass
    public static void cleanup() {
        try {
            participant.closeContainedEntities();
        } catch (Exception e) {
            /* ignore */
        }
        try {
            participant.close();
        } catch (Exception e) {
            /* ignore */
        }
        assertTrue("Check is deamon is correctly stopped", util.afterClass(prop));
    }

    /**
     * Test method for {@link org.omg.dds.sub.ReadCondition#close()}.
     */
    @Test
    public void testClose() {
        checkValidEntities();
        DataState ds = null;
        ReadCondition<Msg> result = null;
        try {
            ds = subscriber.createDataState();
            result = dataReader.createReadCondition(ds.withAnyInstanceState().withAnySampleState().withAnyViewState());
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testClose", e, false));
        }
        assertTrue("Check for valid ReadCondition object failed", util.objectCheck("testClose", result));
        if (result != null) {
            try {
                result.close();
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testClose", e, false));
            }
        }
    }

    /**
     * Test method for {@link org.omg.dds.sub.ReadCondition#getSampleStates()}.
     */
    @Test
    public void testGetSampleStates() {
        checkValidEntities();
        Set<SampleState> result = null;
        try {
            result = condition.getSampleStates();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetSampleStates", e, false));
        }
        assertTrue("Check for valid Set<SampleState> object failed", util.objectCheck("testGetSampleStates", result));
    }

    /**
     * Test method for {@link org.omg.dds.sub.ReadCondition#getViewStates()}.
     */
    @Test
    public void testGetViewStates() {
        checkValidEntities();
        Set<ViewState> result = null;
        try {
            result = condition.getViewStates();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetViewStates", e, false));
        }
        assertTrue("Check for valid Set<ViewState> object failed", util.objectCheck("testGetViewStates", result));
    }

    /**
     * Test method for {@link org.omg.dds.sub.ReadCondition#getInstanceStates()}.
     */
    @Test
    public void testGetInstanceStates() {
        checkValidEntities();
        Set<InstanceState> result = null;
        try {
            result = condition.getInstanceStates();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetInstanceStates", e, false));
        }
        assertTrue("Check for valid Set<InstanceState> object failed", util.objectCheck("testGetInstanceStates", result));
    }

    /**
     * Test method for {@link org.omg.dds.sub.ReadCondition#getParent()}.
     */
    @Test
    public void testGetParent() {
        checkValidEntities();
        DataReader<Msg> result = null;
        try {
            result = condition.getParent();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetParent", e, false));
        }
        assertTrue("Check for valid DataReader object failed", util.objectCheck("testGetParent", result.equals(dataReader)));
    }

    /**
     * Test method for {@link org.omg.dds.core.Condition#getTriggerValue()}.
     */
    @Test
    public void testGetTriggerValue() {
        checkValidEntities();
        boolean result = true;
        try {
            result = condition.getTriggerValue();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetTriggerValue", e, false));
        }
        assertFalse("Check for invalid triggervalue object failed", result);
    }

    /**
     * Test method for {@link org.omg.dds.core.DDSObject#getEnvironment()}.
     */
    @Test
    public void testGetEnvironment() {
        checkValidEntities();
        ServiceEnvironment senv = null;
        try {
            senv = condition.getEnvironment();
        } catch (Exception e) {
            fail("Exception occured while calling getEnvironment(): " + util.printException(e));
        }
        assertTrue("Check for valid ServiceEnvironment", senv != null);
    }

}
