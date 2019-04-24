/**
 *
 */
package unittest.sub;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.dds.core.InstanceHandle;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.domain.DomainParticipantFactory;
import org.omg.dds.pub.Publisher;
import org.omg.dds.sub.DataReader;
import org.omg.dds.sub.DataReader.Selector;
import org.omg.dds.sub.InstanceState;
import org.omg.dds.sub.Sample;
import org.omg.dds.sub.Sample.Iterator;
import org.omg.dds.sub.Subscriber;
import org.omg.dds.topic.Topic;

import Test.Msg;

import org.eclipse.cyclonedds.test.AbstractUtilities;

/**
 * @author Thijs
 *
 */
public class SelectorTest {

    private final static int                DOMAIN_ID   = 123;
    private static ServiceEnvironment       env;
    private static DomainParticipantFactory dpf;
    private static AbstractUtilities        util        = AbstractUtilities.getInstance(DataReaderListenerTest.class);
    private final static Properties         prop        = new Properties();
    private static DomainParticipant        participant = null;
    private static Publisher                publisher   = null;
    private static Subscriber               subscriber  = null;
    private static Topic<Msg>               topic       = null;
    private static String                   topicName   = "SelectorTest";
    private static DataReader<Msg>          dataReader  = null;
    private static Selector<Msg>            selector    = null;

    @BeforeClass
    public static void init() {
        try {
            String serviceEnvironment = System.getProperty("JAVA5PSM_SERVICE_ENV");
            assertTrue("Check for valid ServiceEnvironment string", !serviceEnvironment.equals(""));
            System.setProperty(ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY, serviceEnvironment);
            env = ServiceEnvironment.createInstance(SelectorTest.class.getClassLoader());
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
        } catch (Exception e) {
            fail("Exception occured while initiating the SelectorTest class: " + util.printException(e));
        }

    }

    private void checkValidEntities() {
        assertTrue("Check for valid DomainParticipant object failed", participant instanceof DomainParticipant);
        assertTrue("Check for valid Publisher object failed", publisher instanceof Publisher);
        assertTrue("Check for valid Subscriber object failed", subscriber instanceof Subscriber);
        assertTrue("Check for valid Topic object failed", topic instanceof Topic);
        assertTrue("Check for valid DataReader object failed", dataReader instanceof DataReader);
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
     * Test method for {@link org.opensplice.dds.sub.SelectorImpl#getEnvironment()}.
     */
    @Test
    public void testGetEnvironment() {
        checkValidEntities();
        ServiceEnvironment senv = null;
        selector = dataReader.select();
        try {
            senv = selector.getEnvironment();
        } catch (Exception e) {
            fail("Exception occured while calling getEnvironment(): " + util.printException(e));
        }
        assertTrue("Check for valid ServiceEnvironment", senv != null);
    }

    /**
     * Test method for {@link org.opensplice.dds.sub.SelectorImpl#instance(org.omg.dds.core.InstanceHandle)}.
     */
    @Test
    public void testInstance() {
        checkValidEntities();
        Selector<Msg> sel = null;
        selector = dataReader.select();
        try {
            sel = selector.instance(InstanceHandle.nilHandle(env));
        } catch (Exception e) {
            fail("Exception occured while calling instance(): " + util.printException(e));
        }
        assertTrue("Check for valid Selector Object", sel != null);
        assertTrue("Check for valid InstanceHandle Object", sel.getInstance() != null);
        assertTrue("Check for valid Selector Nil InstanceHandle", sel.getInstance().isNil());
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.sub.SelectorImpl#instance(org.omg.dds.core.InstanceHandle)}
     * .
     */
    @Test
    public void testInstanceNull() {
        checkValidEntities();
        Selector<Msg> sel = null;
        selector = dataReader.select();
        try {
            sel = selector.instance(null);
        } catch (Exception e) {
            assertTrue("Expected IllegalArgumentException but got: " + util.printException(e), util.exceptionCheck("testInstanceNull", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for invalid Selector object", sel == null);
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.sub.SelectorImpl#nextInstance(boolean)}.
     */
    @Test
    public void testNextInstance() {
        checkValidEntities();
        Selector<Msg> sel = null;
        selector = dataReader.select();
        try {
            sel = selector.nextInstance(true);
        } catch (Exception e) {
            assertTrue("No exception Expected but got: " + util.printException(e), util.exceptionCheck("testNextInstance", e, false));
        }
        if (sel != null) {
            assertTrue("Check for valid Selector Object", sel != null);
            assertTrue("Check for valid Selector retrieveNextInstance value", sel.retrieveNextInstance());
        }
    }

    /**
     * Test method for {@link org.opensplice.dds.sub.SelectorImpl#dataState(org.omg.dds.sub.Subscriber.DataState)}.
     */
    @Test
    public void testDataState() {
        checkValidEntities();
        Selector<Msg> sel = null;
        selector = dataReader.select();
        try {
            sel = selector.dataState(subscriber.createDataState().withAnyInstanceState().withAnySampleState().withAnyViewState());
        } catch (Exception e) {
            fail("Exception occured while calling dataState(): " + util.printException(e));
        }
        assertTrue("Check for valid Selector Object", sel != null);
        assertTrue("Check for valid Selector DataState Object", sel.getDataState() != null);
        assertTrue("Check for valid Condition", sel.getCondition() != null);
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.sub.SelectorImpl#dataState(org.omg.dds.sub.Subscriber.DataState)}
     * .
     */
    @Test
    public void testDataState1() {
        checkValidEntities();
        Selector<Msg> sel = null;
        selector = dataReader.select();
        try {
            String queryExpression = "userID < 2";
            ArrayList<String> queryParameters = new ArrayList<String>();
            sel = selector.Content(queryExpression, queryParameters);
            sel = sel.dataState(subscriber.createDataState().withAnyInstanceState().withAnySampleState().withAnyViewState());
            assertTrue("Check for valid Selector Object", sel != null);
            assertTrue("Check for valid Selector DataState Object", sel.getDataState() != null);
            assertTrue("Check for valid Condition", sel.getCondition() != null);
        } catch (Exception e) {
            assertTrue("No exception expected but got exception:" + util.printException(e), util.exceptionCheck("testDataState1", e, false));
        }
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.sub.SelectorImpl#dataState(org.omg.dds.sub.Subscriber.DataState)}
     * .
     */
    @Test
    public void testDataState2() {
        checkValidEntities();
        Selector<Msg> sel = null;
        selector = dataReader.select();
        try {
            sel = selector.dataState(subscriber.createDataState().withAnyInstanceState().withAnySampleState().withAnyViewState());
            sel = sel.dataState(subscriber.createDataState().with(InstanceState.ALIVE).withAnySampleState().withAnyViewState());
        } catch (Exception e) {
            fail("Exception occured while calling dataState(): " + util.printException(e));
        }
        assertTrue("Check for valid Selector Object", sel != null);
        assertTrue("Check for valid Selector DataState Object", sel.getDataState() != null);
        assertTrue("Check for valid Condition", sel.getCondition() != null);
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.sub.SelectorImpl#dataState(org.omg.dds.sub.Subscriber.DataState)}
     * .
     */
    @Test
    public void testDataStateNull() {
        checkValidEntities();
        Selector<Msg> sel = null;
        selector = dataReader.select();
        try {
            sel = selector.dataState(null);
        } catch (Exception e) {
            assertTrue("Expected IllegalArgumentException but got: " + util.printException(e), util.exceptionCheck("testDataStateNull", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for invalid Selector object", sel == null);
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.sub.SelectorImpl#dataState(org.omg.dds.sub.Subscriber.DataState)}
     * .
     */
    @Test
    public void testDataStateEmpty() {
        checkValidEntities();
        Selector<Msg> sel = null;
        selector = dataReader.select();
        try {
            sel = selector.dataState(subscriber.createDataState());
        } catch (Exception e) {
            assertTrue("Expected IllegalArgumentException but got: " + util.printException(e), util.exceptionCheck("testDataStateNull", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for invalid Selector object", sel == null);
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.sub.SelectorImpl#Content(java.lang.String, java.util.List)}
     * .
     */
    @Test
    public void testContentStringListOfString() {
        checkValidEntities();
        Selector<Msg> sel = null;
        String queryExpression = "userID < 2";
        ArrayList<String> queryParameters = new ArrayList<String>();
        selector = dataReader.select();
        try {
            sel = selector.Content(queryExpression, queryParameters);
            assertTrue("Check for valid Selector Object", sel != null);
            assertTrue("Check for valid Selector QueryExpression value", sel.getQueryExpression().equals(queryExpression));
            assertTrue("Check for valid Selector QueryParameters value", sel.getQueryParameters().equals(queryParameters));
        } catch (Exception e) {
            assertTrue("No exception Expected but got: " + util.printException(e), util.exceptionCheck("testContentStringListOfString", e, false));
        }
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.sub.SelectorImpl#Content(java.lang.String)} .
     */
    @Test
    public void testContentStringListOfString1() {
        checkValidEntities();
        Selector<Msg> sel = null;
        String queryExpression = "userID < 2";
        selector = dataReader.select();
        try {
            sel = selector.Content(queryExpression);
            assertTrue("Check for valid Selector Object", sel != null);
            assertTrue("Check for valid Selector QueryExpression value", sel.getQueryExpression().equals(queryExpression));
        } catch (Exception e) {
            assertTrue("No exception Expected but got: " + util.printException(e), util.exceptionCheck("testContentStringListOfString1", e, false));
        }
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.sub.SelectorImpl#Content(java.lang.String, java.util.List)}
     * .
     */
    @Test
    public void testContentStringListOfString2() {
        checkValidEntities();
        Selector<Msg> sel = null;
        String queryExpression = "userID < 2";
        ArrayList<String> queryParameters = new ArrayList<String>();
        try {
            selector = dataReader.select();
            sel = selector.dataState(subscriber.createDataState().withAnyInstanceState().withAnySampleState().withAnyViewState());
            sel = sel.Content(queryExpression, queryParameters);
            assertTrue("Check for valid Selector Object", sel != null);
            assertTrue("Check for valid Selector QueryExpression value", sel.getQueryExpression().equals(queryExpression));
        } catch (Exception e) {
            assertTrue("No exception Expected but got: " + util.printException(e), util.exceptionCheck("testContentStringListOfString2", e, false));
        }
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.sub.SelectorImpl#Content(java.lang.String, java.util.List)}
     * .
     */
    @Test
    public void testContentStringListOfStringNull() {
        checkValidEntities();
        Selector<Msg> sel = null;
        String queryExpression = "userID < 2";
        ArrayList<String> queryParameters = null;
        selector = dataReader.select();
        try {
            sel = selector.Content(queryExpression, queryParameters);
        } catch (Exception e) {
            assertTrue("Expected IllegalArgumentException but got: " + util.printException(e), util.exceptionCheck("testContentStringListOfStringNull", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for invalid Selector object", sel == null);
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.sub.SelectorImpl#Content(java.lang.String, java.util.List)}
     * .
     */
    @Test
    public void testContentStringNullListOfString() {
        checkValidEntities();
        Selector<Msg> sel = null;
        ArrayList<String> queryParameters = null;
        selector = dataReader.select();
        try {
            sel = selector.Content(null, queryParameters);
        } catch (Exception e) {
            assertTrue("Expected IllegalArgumentException but got: " + util.printException(e), util.exceptionCheck("testContentStringNullListOfString", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for invalid Selector object", sel == null);
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.sub.SelectorImpl#Content(java.lang.String, java.lang.String[])}
     * .
     */
    @Test
    public void testContentStringStringArray() {
        checkValidEntities();
        Selector<Msg> sel = null;
        String queryExpression = "userID < 2";
        String[] queryParameters = { "foo", "bar" };
        selector = dataReader.select();
        try {
            sel = selector.Content(queryExpression, queryParameters);
            assertTrue("Check for valid Selector Object", sel != null);
            assertTrue("Check for valid Selector QueryExpression value", sel.getQueryExpression().equals(queryExpression));
            assertTrue("Check for valid Selector QueryParameters value", sel.getQueryParameters().equals(Arrays.asList(queryParameters)));
        } catch (Exception e) {
            assertTrue("No exception Expected but got: " + util.printException(e), util.exceptionCheck("testContentStringStringArray", e, false));
        }
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.sub.SelectorImpl#Content(java.lang.String, java.lang.String[])}
     * .
     */
    @Test
    public void testContentStringStringArrayNull() {
        checkValidEntities();
        Selector<Msg> sel = null;
        String queryExpression = "userID < %0";
        String[] queryParameters = { null, "bar" };
        selector = dataReader.select();
        try {
            sel = selector.Content(queryExpression, queryParameters);
        } catch (Exception e) {
            assertTrue("Expected IllegalArgumentException but got: " + util.printException(e), util.exceptionCheck("testContentStringStringArrayNull", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for invalid Selector object", sel == null);
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.sub.SelectorImpl#Content(java.lang.String, java.lang.String[])}
     * .
     */
    @Test
    public void testContentStringStringArrayNull1() {
        checkValidEntities();
        Selector<Msg> sel = null;
        String queryExpression = "userID < %0";
        String queryParameters = null;
        selector = dataReader.select();
        try {
            sel = selector.Content(queryExpression, queryParameters);
        } catch (Exception e) {
            assertTrue("Expected IllegalArgumentException but got: " + util.printException(e), util.exceptionCheck("testContentStringStringArrayNull1", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for invalid Selector object", sel == null);
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.sub.SelectorImpl#maxSamples(int)}.
     */
    @Test
    public void testMaxSamples() {
        checkValidEntities();
        Selector<Msg> sel = null;
        int max = 5;
        selector = dataReader.select();
        try {
            sel = selector.maxSamples(max);
        } catch (Exception e) {
            fail("Exception occured while calling maxSamples(): " + util.printException(e));
        }
        assertTrue("Check for valid Selector Object", sel != null);
        assertTrue("Check for valid Selector MaxSamples value", sel.getMaxSamples() == max);
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.sub.SelectorImpl#maxSamples(int)}.
     */
    @Test
    public void testMaxSamplesNegative() {
        checkValidEntities();
        Selector<Msg> sel = null;
        int max = -4;
        selector = dataReader.select();
        try {
            sel = selector.maxSamples(max);
        } catch (Exception e) {
            assertTrue("Expected IllegalArgumentException but got: " + util.printException(e), util.exceptionCheck("testMaxSamplesNegative", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for invalid Selector object", sel == null);
    }


    /**
     * Test method for {@link org.opensplice.dds.sub.SelectorImpl#read()}.
     */
    @Test
    public void testRead() {
        checkValidEntities();
        Iterator<Msg> sel = null;
        selector = dataReader.select();
        try {
            sel = selector.read();
        } catch (Exception e) {
            fail("Exception occured while calling Read(): " + util.printException(e));
        }
        assertTrue("Check for valid Iterator Object", sel != null);
    }



    /**
     * Test method for
     * {@link org.opensplice.dds.sub.SelectorImpl#read(java.util.List)}.
     */
    @Test
    public void testReadListOfSampleOfTYPE() {
        checkValidEntities();
        List<Sample<Msg>> sel = null;
        List<Sample<Msg>> samples = new ArrayList<Sample<Msg>>(10);
        selector = dataReader.select();
        try {
            sel = selector.read(samples);
        } catch (Exception e) {
            fail("Exception occured while calling Read(): " + util.printException(e));
        }
        assertTrue("Check for valid  List<Sample<Msg>> Object", sel != null);
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.sub.SelectorImpl#read(java.util.List)}.
     */
    @Test
    public void testReadListOfSampleOfTYPENull() {
        checkValidEntities();
        List<Sample<Msg>> sel = null;
        selector = dataReader.select();
        try {
            sel = selector.read(null);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testReadListOfSampleOfTYPENull", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for valid Selector object", sel != null);
    }


    /**
     * Test method for {@link org.opensplice.dds.sub.SelectorImpl#take()}.
     */
    @Test
    public void testTake() {
        checkValidEntities();
        Iterator<Msg> sel = null;
        selector = dataReader.select();
        try {
            sel = selector.take();
        } catch (Exception e) {
            fail("Exception occured while calling take(): " + util.printException(e));
        }
        assertTrue("Check for valid Iterator Object", sel != null);
    }



    /**
     * Test method for
     * {@link org.opensplice.dds.sub.SelectorImpl#take(java.util.List)}.
     */
    @Test
    public void testTakeListOfSampleOfTYPE() {
        checkValidEntities();
        List<Sample<Msg>> sel = null;
        List<Sample<Msg>> samples = new ArrayList<Sample<Msg>>(10);
        selector = dataReader.select();
        try {
            sel = selector.take(samples);
        } catch (Exception e) {
            fail("Exception occured while calling take(): " + util.printException(e));
        }
        assertTrue("Check for valid  List<Sample<Msg>> Object", sel != null);
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.sub.SelectorImpl#take(java.util.List)}.
     */
    @Test
    public void testTakeQuery() {
        checkValidEntities();
        Iterator<Msg> sel = null;
        String queryExpression = "userID < 2";
        ArrayList<String> queryParameters = new ArrayList<String>();
        try {
            selector = dataReader.select();
            selector = selector.Content(queryExpression, queryParameters);
            sel = selector.take();
            assertTrue("Check for valid  List<Sample<Msg>> Object", sel != null);
        } catch (Exception e) {
            assertTrue("No exception expected but got exception:" + util.printException(e), util.exceptionCheck("testTakeQuery", e, false));
        }
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.sub.SelectorImpl#take(java.util.List)}.
     */
    @Test
    public void testTakeListOfSampleOfTYPENull() {
        checkValidEntities();
        List<Sample<Msg>> sel = null;
        selector = dataReader.select();
        try {
            sel = selector.take(null);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testTakeListOfSampleOfTYPENull", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for valid Selector object", sel != null);
    }

}
