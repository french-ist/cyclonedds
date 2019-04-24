/**
 *
 */
package unittest.sub;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.dds.core.Duration;
import org.omg.dds.core.ImmutablePolicyException;
import org.omg.dds.core.InconsistentPolicyException;
import org.omg.dds.core.InstanceHandle;
import org.omg.dds.core.PreconditionNotMetException;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.StatusCondition;
import org.omg.dds.core.policy.Deadline;
import org.omg.dds.core.policy.Durability;
import org.omg.dds.core.policy.TimeBasedFilter;
import org.omg.dds.core.policy.Presentation.AccessScopeKind;
import org.omg.dds.core.policy.Reliability;
import org.omg.dds.core.policy.DestinationOrder;
import org.omg.dds.core.status.DataAvailableStatus;
import org.omg.dds.core.status.LivelinessChangedStatus;
import org.omg.dds.core.status.RequestedDeadlineMissedStatus;
import org.omg.dds.core.status.RequestedIncompatibleQosStatus;
import org.omg.dds.core.status.SampleLostStatus;
import org.omg.dds.core.status.SampleRejectedStatus;
import org.omg.dds.core.status.Status;
import org.omg.dds.core.status.SubscriptionMatchedStatus;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.domain.DomainParticipantFactory;
import org.omg.dds.pub.DataWriter;
import org.omg.dds.pub.Publisher;
import org.omg.dds.pub.PublisherQos;
import org.omg.dds.sub.DataReader;
import org.omg.dds.sub.DataReader.Selector;
import org.omg.dds.sub.DataReaderListener;
import org.omg.dds.sub.DataReaderQos;
import org.omg.dds.sub.QueryCondition;
import org.omg.dds.sub.ReadCondition;
import org.omg.dds.sub.Sample;
import org.omg.dds.sub.SampleState;
import org.omg.dds.sub.Subscriber;
import org.omg.dds.sub.SubscriberQos;
import org.omg.dds.sub.Subscriber.DataState;
import org.omg.dds.topic.PublicationBuiltinTopicData;
import org.omg.dds.topic.Topic;
import org.omg.dds.topic.TopicDescription;
import org.omg.dds.topic.TopicQos;
import org.omg.dds.core.policy.PolicyFactory;

import Test.Msg;

import org.eclipse.cyclonedds.test.AbstractUtilities;
import org.eclipse.cyclonedds.test.DataReaderListenerClass;

/**
 * @author Thijs
 *
 */
public class DataReaderTest {

    private final static int                DOMAIN_ID   = 123;
    private static ServiceEnvironment       env;
    private static DomainParticipantFactory dpf;
    private static AbstractUtilities        util        = AbstractUtilities.getInstance(DataReaderTest.class);
    private final static Properties         prop        = new Properties();
    private static DomainParticipant        participant = null;
    private static Publisher                publisher   = null;
    private static Subscriber               subscriber  = null;
    private static Topic<Msg>               topic       = null;
    private static DataReader<Msg>          dataReader  = null;
    private static String                   topicName   = "DataReaderTest";

    @BeforeClass
    public static void init() {
        try {
            String serviceEnvironment = System.getProperty("JAVA5PSM_SERVICE_ENV");
            assertTrue("Check for valid ServiceEnvironment string", !serviceEnvironment.equals(""));
            System.setProperty(ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY, serviceEnvironment);
            env = ServiceEnvironment.createInstance(DataReaderTest.class.getClassLoader());
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
            fail("Exception occured while initiating the DataReaderTest class: " + util.printException(e));
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
     * Test method for {@link org.omg.dds.sub.DataReader#cast()}.
     */
    @Test
    public void testCast() {
        checkValidEntities();
        DataReader<Object> result = null;
        try {
            result = dataReader.cast();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testCast", e, false));
        }
        assertTrue("Check for valid DataReader object failed", util.objectCheck("testCast", result));
    }

    /**
     * Test method for {@link org.omg.dds.sub.DataReader#cast()}.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Test
    public void testCastInvalid() {
        checkValidEntities();
        DataWriter<Msg> result = null;
        try {
            result = (DataWriter) dataReader.cast();
        } catch (Exception e) {
            assertTrue("ClassCastException expected but got: " + util.printException(e), util.exceptionCheck("testCastInvalid", e, e instanceof ClassCastException));
        }
        assertTrue("Check for invalid DataReader object failed", result == null);
    }

    /**
     * Test method for
     * {@link org.omg.dds.sub.DataReader#createReadCondition(org.omg.dds.sub.Subscriber.DataState)}
     * .
     */
    @Test
    public void testCreateReadCondition() {
        checkValidEntities();
        ReadCondition<Msg> result = null;
        DataState ds = null;
        try {
            ds = subscriber.createDataState();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testCreateReadCondition", e, false));
        }
        try {
            result = dataReader.createReadCondition(ds.withAnyInstanceState().withAnySampleState().withAnyViewState());
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testCreateReadCondition", e, false));
        }
        assertTrue("Check for valid ReadCondition object failed", util.objectCheck("testCreateReadCondition", result));
        if (result != null) {
            try {
                result.close();
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testCreateReadCondition", e, false));
            }
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.sub.DataReader#createReadCondition(org.omg.dds.sub.Subscriber.DataState)}
     * .
     */
    @Test
    public void testCreateReadConditionNull() {
        checkValidEntities();
        ReadCondition<Msg> result = null;
        DataState ds = null;
        try {
            result = dataReader.createReadCondition(ds);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testCreateReadConditionNull", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for invalid ReadCondition object failed", result == null);
    }

    /**
     * Test method for
     * {@link org.omg.dds.sub.DataReader#createReadCondition(org.omg.dds.sub.Subscriber.DataState)}
     * .
     */
    @Test
    public void testCreateReadConditionInvalid() {
        checkValidEntities();
        ReadCondition<Msg> result = null;
        DataState ds = null;
        try {
            ds = subscriber.createDataState();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testCreateReadConditionInvalid", e, false));
        }
        try {
            /* empty dataState */
            result = dataReader.createReadCondition(ds);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testCreateReadConditionInvalid", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for invalid ReadCondition object failed", result == null);
    }

    /**
     * Test method for
     * {@link org.omg.dds.sub.DataReader#createQueryCondition(java.lang.String, java.util.List)}
     * .
     */
    @Test
    public void testCreateQueryConditionStringListOfString() {
        checkValidEntities();
        QueryCondition<Msg> result = null;
        String queryExpression = "userID < %0";
        ArrayList<String> queryParameters = new ArrayList<String>();
        queryParameters.add("2");
        try {
            result = dataReader.createQueryCondition(queryExpression, queryParameters);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testCreateQueryConditionStringListOfString", e, false));
        }
        assertTrue("Check for valid QueryCondition object failed", util.objectCheck("testCreateQueryConditionStringListOfString", result));
        if (result != null) {
            try {
                result.close();
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testCreateQueryConditionStringListOfString", e, false));
            }
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.sub.DataReader#createQueryCondition(java.lang.String, java.util.List)}
     * .
     */
    @Test
    public void testCreateQueryConditionStringListOfString1() {
        checkValidEntities();
        QueryCondition<Msg> result = null;
        String queryExpression = "userID < 2";
        ArrayList<String> queryParameters = new ArrayList<String>();
        try {
            result = dataReader.createQueryCondition(queryExpression, queryParameters);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testCreateQueryConditionStringListOfString1", e, false));
        }
        assertTrue("Check for valid QueryCondition object failed", util.objectCheck("testCreateQueryConditionStringListOfString1", result));
        if (result != null) {
            try {
                result.close();
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testCreateQueryConditionStringListOfString1", e, false));
            }
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.sub.DataReader#createQueryCondition(java.lang.String, java.util.List)}
     * .
     */
    @Test
    public void testCreateQueryConditionStringNullListOfString() {
        checkValidEntities();
        QueryCondition<Msg> result = null;
        String queryExpression = null;
        ArrayList<String> queryParameters = new ArrayList<String>();
        queryParameters.add("2");
        try {
            result = dataReader.createQueryCondition(queryExpression, queryParameters);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e),
                    util.exceptionCheck("testCreateQueryConditionStringNullListOfString", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for invalid QueryCondition object failed", result == null);
    }

    /**
     * Test method for
     * {@link org.omg.dds.sub.DataReader#createQueryCondition(java.lang.String, java.util.List)}
     * .
     */
    @Test
    public void testCreateQueryConditionStringListOfStringNull() {
        checkValidEntities();
        QueryCondition<Msg> result = null;
        String queryExpression = "userID < %0";
        ArrayList<String> queryParameters = null;
        try {
            result = dataReader.createQueryCondition(queryExpression, queryParameters);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e),
                    util.exceptionCheck("testCreateQueryConditionStringListOfStringNull", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for invalid QueryCondition object failed", result == null);
    }

    /**
     * Test method for
     * {@link org.omg.dds.sub.DataReader#createQueryCondition(java.lang.String, java.lang.String[])}
     * .
     */
    @Test
    public void testCreateQueryConditionStringStringArray() {
        checkValidEntities();
        QueryCondition<Msg> result = null;
        String queryExpression = "userID > %0 AND userID < %1";
        try {
            result = dataReader.createQueryCondition(queryExpression, "0", "2");
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testCreateQueryConditionStringListOfString", e, false));
        }
        assertTrue("Check for valid QueryCondition object failed", util.objectCheck("testCreateQueryConditionStringListOfString", result));
        if (result != null) {
            try {
                result.close();
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testCreateQueryConditionStringStringArray", e, false));
            }
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.sub.DataReader#createQueryCondition(java.lang.String, java.lang.String[])}
     * .
     */
    @Test
    public void testCreateQueryConditionStringStringArrayNull() {
        checkValidEntities();
        QueryCondition<Msg> result = null;
        String queryExpression = "userID > %0 AND userID < %1";
        try {
            result = dataReader.createQueryCondition(queryExpression, "0", null);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e),
                    util.exceptionCheck("testCreateQueryConditionStringStringArrayNull", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for invalid QueryCondition object failed", result == null);
    }

    /**
     * Test method for
     * {@link org.omg.dds.sub.DataReader#createQueryCondition(java.lang.String, java.lang.String[])}
     * .
     */
    @Test
    public void testCreateQueryConditionStringStringArrayNull1() {
        checkValidEntities();
        QueryCondition<Msg> result = null;
        String queryExpression = "userID > %0 AND userID < %1";
        String queryParameters = null;
        try {
            result = dataReader.createQueryCondition(queryExpression, queryParameters);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e),
                    util.exceptionCheck("testCreateQueryConditionStringStringArrayNull1", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for invalid QueryCondition object failed", result == null);
    }

    /**
     * Test method for
     * {@link org.omg.dds.sub.DataReader#createQueryCondition(java.lang.String, java.lang.String[])}
     * .
     */
    @Test
    public void testCreateQueryConditionStringNullStringArray() {
        checkValidEntities();
        QueryCondition<Msg> result = null;
        String queryExpression = null;
        try {
            result = dataReader.createQueryCondition(queryExpression, "0", "0");
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e),
                    util.exceptionCheck("testCreateQueryConditionStringNullStringArray", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for invalid QueryCondition object failed", result == null);
    }

    /**
     * Test method for {@link
     org.omg.dds.sub.DataReader#createQueryCondition(org.omg.dds.sub.Subscriber.DataState,
     java.lang.String, java.util.List)}.
     */
    @Test
    public void testCreateQueryConditionDataStateStringListOfString() {
        checkValidEntities();
        QueryCondition<Msg> result = null;
        String queryExpression = "userID < %0";
        ArrayList<String> queryParameters = new ArrayList<String>();
        queryParameters.add("2");
        DataState ds = null;
        try {
            ds = subscriber.createDataState();
            ds = ds.withAnyInstanceState().withAnySampleState().withAnyViewState();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testCreateQueryConditionDataStateStringListOfString", e, false));
        }
        try {
            result = dataReader.createQueryCondition(ds,queryExpression, queryParameters);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testCreateQueryConditionDataStateStringListOfString", e, false));
        }
        assertTrue("Check for valid QueryCondition object failed", util.objectCheck("testCreateQueryConditionDataStateStringListOfString", result));
        if (result != null) {
            try {
                result.close();
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testCreateQueryConditionDataStateStringListOfString", e, false));
            }
        }
    }

    /**
     * Test method for {@link
     org.omg.dds.sub.DataReader#createQueryCondition(org.omg.dds.sub.Subscriber.DataState,
     java.lang.String, java.util.List)}.
     */
    @Test
    public void testCreateQueryConditionDataStateNullStringListOfString() {
        checkValidEntities();
        QueryCondition<Msg> result = null;
        String queryExpression = "userID < %0";
        ArrayList<String> queryParameters = new ArrayList<String>();
        queryParameters.add("2");
        DataState ds = null;
        try {
            result = dataReader.createQueryCondition(ds,queryExpression, queryParameters);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e),
                    util.exceptionCheck("testCreateQueryConditionDataStateNullStringListOfString", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for invalid QueryCondition object failed", result == null);
    }

    /**
     * Test method for
     * {@link org.omg.dds.sub.DataReader#createQueryCondition(org.omg.dds.sub.Subscriber.DataState, java.lang.String, java.lang.String[])}
     * .
     */
    @Test
    public void testCreateQueryConditionDataStateStringStringArray() {
        checkValidEntities();
        QueryCondition<Msg> result = null;
        String queryExpression = "userID > %0 AND userID < %1";
        DataState ds = null;
        try {
            ds = subscriber.createDataState();
            ds = ds.withAnyInstanceState().withAnySampleState().withAnyViewState();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testCreateQueryConditionDataStateStringStringArray", e, false));
        }
        try {
            result = dataReader.createQueryCondition(ds,queryExpression, "0", "2");
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testCreateQueryConditionDataStateStringStringArray", e, false));
        }
        assertTrue("Check for valid QueryCondition object failed", util.objectCheck("testCreateQueryConditionDataStateStringStringArray", result));
        if (result != null) {
            try {
                result.close();
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testCreateQueryConditionDataStateStringStringArray", e, false));
            }
        }
    }


    /**
     * Test method for
     * {@linkorg.omg.dds.sub.DataReader#closeContainedEntities()}.
     */
    @Test
    public void testCloseContainedEntities() {
        checkValidEntities();
        try {
            dataReader.closeContainedEntities();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testCloseContainedEntities", e, false));
        }
    }

    /**
     * Test method for {@link org.omg.dds.sub.DataReader#getTopicDescription()}.
     */
    @Test
    public void testGetTopicDescription() {
        checkValidEntities();
        TopicDescription<Msg> result = null;
        try {
            result = dataReader.getTopicDescription();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetTopicDescription", e, false));
        }
        assertTrue("Check for valid TopicDescription object failed", util.objectCheck("testGetTopicDescription", result));

    }

    /**
     * Test method for
     * {@link org.omg.dds.sub.DataReader#getSampleRejectedStatus()}.
     */
    @Test
    public void testGetSampleRejectedStatus() {
        checkValidEntities();
        SampleRejectedStatus result = null;
        try {
            result = dataReader.getSampleRejectedStatus();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetSampleRejectedStatus", e, false));
        }
        assertTrue("Check for valid SampleRejectedStatus object failed", util.objectCheck("testGetSampleRejectedStatus", result));
    }


    /**
     * Test method for
     * {@link org.omg.dds.sub.DataReader#getLivelinessChangedStatus()}.
     */
    @Test
    public void testGetLivelinessChangedStatus() {
        checkValidEntities();
        LivelinessChangedStatus result = null;
        try {
            result = dataReader.getLivelinessChangedStatus();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetLivelinessChangedStatus", e, false));
        }
        assertTrue("Check for valid LivelinessChangedStatus object failed", util.objectCheck("testGetLivelinessChangedStatus", result));
    }

    /**
     * Test method for
     * {@link org.omg.dds.sub.DataReader#getRequestedDeadlineMissedStatus()}.
     */
    @Test
    public void testGetRequestedDeadlineMissedStatus() {
        checkValidEntities();
        RequestedDeadlineMissedStatus result = null;
        try {
            result = dataReader.getRequestedDeadlineMissedStatus();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetRequestedDeadlineMissedStatus", e, false));
        }
        assertTrue("Check for valid RequestedDeadlineMissedStatus object failed", util.objectCheck("testGetRequestedDeadlineMissedStatus", result));
    }

    /**
     * Test method for
     * {@link org.omg.dds.sub.DataReader#getRequestedIncompatibleQosStatus()}.
     */
    @Test
    public void testGetRequestedIncompatibleQosStatus() {
        checkValidEntities();
        RequestedIncompatibleQosStatus result = null;
        try {
            result = dataReader.getRequestedIncompatibleQosStatus();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetRequestedIncompatibleQosStatus", e, false));
        }
        assertTrue("Check for valid RequestedIncompatibleQosStatus object failed", util.objectCheck("testGetRequestedIncompatibleQosStatus", result));
    }

    /**
     * Test method for
     * {@link org.omg.dds.sub.DataReader#getSubscriptionMatchedStatus()}.
     */
    @Test
    public void testGetSubscriptionMatchedStatus() {
        checkValidEntities();
        SubscriptionMatchedStatus result = null;
        try {
            result = dataReader.getSubscriptionMatchedStatus();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetSubscriptionMatchedStatus", e, false));
        }
        assertTrue("Check for valid SubscriptionMatchedStatus object failed", util.objectCheck("testGetSubscriptionMatchedStatus", result));
    }

    /**
     * Test method for {@link org.omg.dds.sub.DataReader#getSampleLostStatus()}.
     */
    @Test
    public void testGetSampleLostStatus() {
        checkValidEntities();
        SampleLostStatus result = null;
        try {
            result = dataReader.getSampleLostStatus();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetSampleLostStatus", e, false));
        }
        assertTrue("Check for valid SampleLostStatus object failed", util.objectCheck("testGetSampleLostStatus", result));
    }

    /**
     * Test method for
     * {@link org.omg.dds.sub.DataReader#waitForHistoricalData(org.omg.dds.core.Duration)}
     * .
     */
    @Test
    public void testWaitForHistoricalDataDuration() {
        checkValidEntities();
        Duration timeout = Duration.newDuration(2, TimeUnit.SECONDS, env);
        try {
            dataReader.waitForHistoricalData(timeout);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetSampleLostStatus", e, false));
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.sub.DataReader#waitForHistoricalData(org.omg.dds.core.Duration)}
     * .
     */
    @Test
    public void testWaitForHistoricalDataDurationNull() {
        checkValidEntities();
        Duration timeout = null;
        boolean exceptionOccured = false;
        try {
            dataReader.waitForHistoricalData(timeout);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e),
                    util.exceptionCheck("testWaitForHistoricalDataDurationNull", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("Check IllegalArgumentException occured failed", exceptionOccured);
    }

    /**
     * Test method for
     * {@link org.omg.dds.sub.DataReader#waitForHistoricalData(org.omg.dds.core.Duration)}
     * .
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testWaitForHistoricalDataDurationPersistent() {
        DomainParticipant par = null;
        Topic<Msg> top = null;
        Subscriber sub = null;
        DataReader<Msg> dr = null;
        Duration timeout = Duration.newDuration(30, TimeUnit.SECONDS, env);
        try {
            par = dpf.createParticipant(DOMAIN_ID);
            assertTrue("Check for valid DomainParticipant object failed", par instanceof DomainParticipant);
            sub = par.createSubscriber();
            assertTrue("Check for valid Subscriber object failed", sub instanceof Subscriber);
            TopicQos tq = par.getDefaultTopicQos();
            tq = tq.withPolicy(tq.getDurability().withPersistent());
            Class<? extends Status> status = null;
            top = par.createTopic("testWaitForHistoricalDataDurationPersistent", Msg.class, tq, null, status);
            assertTrue("Check for valid Topic object failed", top instanceof Topic);
            dr = sub.createDataReader(top,
                    sub.copyFromTopicQos(sub.getDefaultDataReaderQos(), tq));
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testWaitForHistoricalDataDurationPersistent", e, false));
        }
        try {
            dr.waitForHistoricalData(timeout);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testWaitForHistoricalDataDurationPersistent", e, false));
         }
        try {
            dr.waitForHistoricalData(30, TimeUnit.SECONDS);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testWaitForHistoricalDataDurationPersistent", e, false));
        }
        try {
            par.closeContainedEntities();
            par.close();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testWaitForHistoricalDataDurationPersistent", e, false));
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.sub.DataReader#waitForHistoricalData(long, java.util.concurrent.TimeUnit)}
     * .
     */
    @Test
    public void testWaitForHistoricalDataLongTimeUnit() {
        checkValidEntities();
        try {
            dataReader.waitForHistoricalData(2, TimeUnit.SECONDS);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetSampleLostStatus", e, false));
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.sub.DataReader#waitForHistoricalData(long, java.util.concurrent.TimeUnit)}
     * .
     */
    @Test
    public void testWaitForHistoricalDataLongTimeUnitNull() {
        checkValidEntities();
        boolean exceptionOccured = false;
        try {
            dataReader.waitForHistoricalData(2, null);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e),
                    util.exceptionCheck("testWaitForHistoricalDataLongTimeUnitNull", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("Check IllegalArgumentException occured failed", exceptionOccured);
    }

    /**
     * Test method for
     * {@link org.omg.dds.sub.DataReader#waitForHistoricalData(long, java.util.concurrent.TimeUnit)}
     * .
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testWaitForHistoricalDataLongTimeUnitNegative() {
        DomainParticipant par = null;
        Topic<Msg> top = null;
        Subscriber sub = null;
        DataReader<Msg> dr = null;
        boolean exceptionOccured = false;
        try {
            par = dpf.createParticipant(DOMAIN_ID);
            assertTrue("Check for valid DomainParticipant object failed", par instanceof DomainParticipant);
            sub = par.createSubscriber();
            assertTrue("Check for valid Subscriber object failed", sub instanceof Subscriber);
            TopicQos tq = par.getDefaultTopicQos();
            tq = tq.withPolicy(tq.getDurability().withPersistent());
            Class<? extends Status> status = null;
            top = par.createTopic("testWaitForHistoricalDataLongTimeUnitNegative", Msg.class, tq, null, status);
            assertTrue("Check for valid Topic object failed", top instanceof Topic);
            dr = sub.createDataReader(top,
                    sub.copyFromTopicQos(sub.getDefaultDataReaderQos(), tq));
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testWaitForHistoricalDataLongTimeUnitNegative", e, false));
        }
        try {
            dr.waitForHistoricalData(-1, TimeUnit.SECONDS);
        } catch (Exception e) {
            assertTrue("TimeoutException expected but got: " + util.printException(e), util.exceptionCheck("testWaitForHistoricalDataLongTimeUnitNegative", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        } finally {
            try {
                par.closeContainedEntities();
                par.close();
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testWaitForHistoricalDataLongTimeUnitNegative", e, false));
            }
        }
        assertTrue("Check IllegalArgumentException occured failed", exceptionOccured);
    }

    /**
     * Test method for
     * {@link org.omg.dds.sub.DataReader#waitForHistoricalData(long, java.util.concurrent.TimeUnit)}
     * .
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testWaitForHistoricalDataLongTimeUnitOverflow() {
        DomainParticipant par = null;
        Topic<Msg> top = null;
        Subscriber sub = null;
        DataReader<Msg> dr = null;
        boolean exceptionOccured = false;
        try {
            par = dpf.createParticipant(DOMAIN_ID);
            assertTrue("Check for valid DomainParticipant object failed", par instanceof DomainParticipant);
            sub = par.createSubscriber();
            assertTrue("Check for valid Subscriber object failed", sub instanceof Subscriber);
            TopicQos tq = par.getDefaultTopicQos();
            tq = tq.withPolicy(tq.getDurability().withPersistent());
            Class<? extends Status> status = null;
            top = par.createTopic("testWaitForHistoricalDataLongTimeUnitOverflow", Msg.class, tq, null, status);
            assertTrue("Check for valid Topic object failed", top instanceof Topic);
            dr = sub.createDataReader(top,
                    sub.copyFromTopicQos(sub.getDefaultDataReaderQos(), tq));
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testWaitForHistoricalDataLongTimeUnitOverflow", e, false));
        }
        try {
            dr.waitForHistoricalData(Long.MAX_VALUE + 1, TimeUnit.SECONDS); // should result in 0
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testWaitForHistoricalDataLongTimeUnitNegative", e, false));
            exceptionOccured = true;
        } finally {
            try {
                par.closeContainedEntities();
                par.close();
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testWaitForHistoricalDataLongTimeUnitNegative", e, false));
            }
        }
        assertTrue("Check TimeoutException occured failed", !exceptionOccured);
    }


    /**
     * Test method for
     * {@link org.omg.dds.sub.DataReader#getMatchedPublications()}.
     */
    @Test
    public void testGetMatchedPublications() {
        checkValidEntities();
        Set<InstanceHandle> subscriptions = null;
        try {
            subscriptions = dataReader.getMatchedPublications();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetMatchedPublications", e, false));
        }
        assertTrue("Check for valid subscriptions object failed", util.objectCheck("testGetMatchedPublications", subscriptions));
    }

    /**
     * Test method for
     * {@link org.omg.dds.sub.DataReader#getMatchedPublicationData(org.omg.dds.core.InstanceHandle)}
     * .
     */
    @Test
    public void testGetMatchedPublicationData() {
        checkValidEntities();
        Publisher pub = null;
        DataWriter<Msg> dw = null;
        Set<InstanceHandle> subscriptions = null;
        int nrOfSubscriptions = 0;
        try {
            pub = participant.createPublisher();
            dw = pub.createDataWriter(topic);
            Thread.sleep(1000);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetMatchedPublicationData", e, false));
        }
        try {
            subscriptions = dataReader.getMatchedPublications();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetMatchedPublicationData", e, false));
        }
        assertTrue("Check for valid subscriptions object failed", util.objectCheck("testGetMatchedPublicationData", subscriptions));
        if (subscriptions != null) {
            for (InstanceHandle ih : subscriptions) {
                try {
                    PublicationBuiltinTopicData pbtd = dataReader.getMatchedPublicationData(ih);

                    assertTrue("Check for valid ParticipantBuiltinTopicData object", util.objectCheck("testGetMatchedPublicationData", pbtd));
                    PublicationBuiltinTopicData clone = pbtd.clone();
                    assertTrue("Check for valid PublicationBuiltinTopicData object", util.objectCheck("testGetMatchedPublicationData", clone));
                    clone.copyFrom(pbtd);
                    assertTrue("Check for valid PublicationBuiltinTopicData object", util.objectCheck("testGetMatchedPublicationData", clone));
                    assertTrue("Check for valid PublicationBuiltinTopicData env", util.objectCheck("testGetMatchedPublicationData", pbtd.getEnvironment()));
                    assertTrue("Check for valid PublicationBuiltinTopicData key", util.objectCheck("testGetMatchedPublicationData", pbtd.getKey()));

                    assertTrue("Check for valid SubscriptionBuiltinTopicData object failed", util.objectCheck("testGetMatchedPublicationData", pbtd));
                    if (pbtd != null && pbtd.getTopicName().equals(topicName)) {
                        nrOfSubscriptions++;
                    }
                } catch (Exception e) {
                    assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetMatchedPublicationData", e, false));
                }
            }
        }
        try {
            dw.close();
            pub.close();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetMatchedPublicationData", e, false));
        }
        boolean res = nrOfSubscriptions > 0 ? true : false;
        assertTrue("Check for correct number of subscriptions in subscriptions set", util.objectCompareCheck("testGetMatchedPublicationData", res, true));
    }

    /**
     * Test method for {@link org.omg.dds.sub.DataReader#read()}.
     */
    @Test
    public void testRead() {
        checkValidEntities();
        Publisher pub = null;
        DataWriter<Msg> dw = null;
        Msg message = new Msg(1, "testRead");
        Iterator<Sample<Msg>> result = null;
        try {
            pub = participant.createPublisher();
            dw = pub.createDataWriter(topic);
            dw.write(message);
            Thread.sleep(util.getWriteSleepTime());
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testRead", e, false));
        }
        try {
            result = dataReader.read();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testRead", e, false));
        }
        if (result != null) {
            boolean messageFound = false;
            while (result.hasNext()) {
                Msg mes = result.next().getData();
                if (mes != null) {
                    if (mes.userID == 1 && mes.message.equals("testRead")) {
                        messageFound = true;
                    } else {
                        fail("Found other message with id:" + mes.userID + " message:" + mes.message);
                    }
                }
            }
            assertTrue("Check that the written message is found failed", messageFound);
        }
        try {
            dataReader.take();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testRead", e, false));
        }
        try {
            dw.close();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testRead", e, false));
        }

    }

    /**
     * Test method for
     * {@link org.omg.dds.sub.DataReader#read(org.omg.dds.sub.DataReader.Selector)}
     * .
     */
    @Test
    public void testReadSelectorOfTYPE() {
        checkValidEntities();
        Publisher pub = null;
        DataWriter<Msg> dw = null;
        Msg message = new Msg(2, "testReadSelectorOfTYPE");
        Iterator<Sample<Msg>> result = null;
        DataState ds = subscriber.createDataState();
        ds = ds.withAnyInstanceState().withAnySampleState().withAnyViewState();
        Selector<Msg> selector = dataReader.select().dataState(ds);

        try {
            pub = participant.createPublisher();
            dw = pub.createDataWriter(topic);
            dw.write(message);
            Thread.sleep(util.getWriteSleepTime());
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testReadSelectorOfTYPE", e, false));
        }
        try {
            result = dataReader.read(selector);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testReadSelectorOfTYPE", e, false));
        } finally {
            try {
                dataReader.take();
                dw.close();
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testReadSelectorOfTYPE", e, false));
            }
        }

        if (result != null) {
            boolean messageFound = false;
            while (result.hasNext()) {
                Msg mes = result.next().getData();
                if (mes != null) {
                    if (mes.userID == 2 && mes.message.equals("testReadSelectorOfTYPE")) {
                        messageFound = true;
                    } else {
                        fail("Found other message with id:" + mes.userID + " message:" + mes.message);
                    }
                }
            }
            assertTrue("Check that the written message is found failed", messageFound);
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.sub.DataReader#take(org.omg.dds.sub.DataReader.Selector)}
     * .
     */
    @Test
    public void testReadSelectorOfTYPE1() {
        checkValidEntities();
        Publisher pub = null;
        DataWriter<Msg> dw = null;
        Msg message = null;
        Iterator<Sample<Msg>> result = null;
        Selector<Msg> selector = null;
        boolean skip = false;
        try {
            DataState ds = subscriber.createDataState();
            ds = ds.withAnyInstanceState().withAnySampleState().withAnyViewState();
            selector = dataReader.select().dataState(ds);
            selector = selector.nextInstance(true);
            pub = participant.createPublisher();
            dw = pub.createDataWriter(topic);
            for (int i = 0; i < 5; i++) {
                message = new Msg(i, "testReadSelectorOfTYPE1");
                dw.write(message);
            }
            Thread.sleep(util.getWriteSleepTime());
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testReadSelectorOfTYPE1", e, false));
            skip = true;
        }
        try {
            if (!skip) {
                for (int i = 0; i < 5; i++) {
                    result = dataReader.read(selector);
                    if (result != null) {
                        boolean messageFound = false;
                        while (result.hasNext()) {
                            Msg mes = result.next().getData();
                            selector = selector.instance(dataReader.lookupInstance(mes));
                            if (mes != null) {
                                if (mes.userID == i && mes.message.equals("testReadSelectorOfTYPE1")) {
                                    messageFound = true;
                                } else {
                                    fail("Expected: id: " + i + " Found other message with id:" + mes.userID + " message:" + mes.message);
                                }
                            }
                        }
                        assertTrue("Check that the written message is found failed", messageFound);
                    }
                }
            }
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testReadSelectorOfTYPE1", e, false));
        } finally {
            try {
                dataReader.take();
                dw.close();
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testReadSelectorOfTYPE1", e, false));
            }
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.sub.DataReader#read(org.omg.dds.sub.DataReader.Selector)}
     * .
     */
    @Test
    public void testReadSelectorOfTYPE2() {
        checkValidEntities();
        Publisher pub = null;
        DataWriter<Msg> dw = null;
        Msg message = null;
        Iterator<Sample<Msg>> result = null;
        Selector<Msg> selector = null;
        boolean skip = false;
        try {
            selector = dataReader.select();
            selector = selector.nextInstance(true);
            pub = participant.createPublisher();
            dw = pub.createDataWriter(topic);
            for (int i = 0; i < 5; i++) {
                message = new Msg(i, "testReadSelectorOfTYPE2");
                dw.write(message);
            }
            Thread.sleep(util.getWriteSleepTime());
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testReadSelectorOfTYPE2", e, false));
            skip = true;
        }
        try {
            if (!skip) {
                for (int i = 0; i < 5; i++) {
                    result = dataReader.read(selector);
                    if (result != null) {
                        boolean messageFound = false;
                        while (result.hasNext()) {
                            Msg mes = result.next().getData();
                            selector = selector.instance(dataReader.lookupInstance(mes));
                            if (mes != null) {
                                if (mes.userID == i && mes.message.equals("testReadSelectorOfTYPE2")) {
                                    messageFound = true;
                                }
                            }
                        }
                        assertTrue("Check that the written message is found failed", messageFound);
                    }
                }
            }
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testReadSelectorOfTYPE2", e, false));
        } finally {
            try {
                dataReader.take();
                dw.close();
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testReadSelectorOfTYPE2", e, false));
            }
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.sub.DataReader#read(org.omg.dds.sub.DataReader.Selector)}
     * .
     */
    @Test
    public void testReadSelectorOfTYPE3() {
        checkValidEntities();
        Publisher pub = null;
        DataWriter<Msg> dw = null;
        Msg message = null;
        Iterator<Sample<Msg>> result = null;
        Selector<Msg> selector = null;
        InstanceHandle ih = null;
        try {
            selector = dataReader.select();
            pub = participant.createPublisher();
            dw = pub.createDataWriter(topic);
            message = new Msg(77, "testReadSelectorOfTYPE3");
            dw.write(message);
            Thread.sleep(util.getWriteSleepTime());
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testReadSelectorOfTYPE3", e, false));
        }

        try {
            result = dataReader.read();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testReadSelectorOfTYPE3", e, false));
        }
        if (result != null) {
            boolean messageFound = false;
            while (result.hasNext()) {
                Msg mes = result.next().getData();
                if (mes != null) {
                    if (mes.userID == 77 && mes.message.equals("testReadSelectorOfTYPE3")) {
                        messageFound = true;
                        ih = dataReader.lookupInstance(mes);
                    } else {
                        fail("Found other message with id:" + mes.userID + " message:" + mes.message);
                    }
                }
            }
            assertTrue("Check that the written message is found failed", messageFound);
        }

        try {
            selector.instance(ih);
            result = dataReader.read(selector);
            if (result != null) {
                boolean messageFound = false;
                while (result.hasNext()) {
                    Msg mes = result.next().getData();
                    if (mes != null) {
                        if (mes.userID == 77 && mes.message.equals("testReadSelectorOfTYPE3")) {
                            messageFound = true;
                        }
                    }
                }
                assertTrue("Check that the written message is found failed", messageFound);
            }

        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testReadSelectorOfTYPE3", e, false));
        } finally {
            try {
                dataReader.take();
                dw.close();
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testReadSelectorOfTYPE3", e, false));
            }
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.sub.DataReader#read(org.omg.dds.sub.DataReader.Selector)}
     * .
     */
    @Test
    public void testReadSelectorOfTYPE4() {
        checkValidEntities();
        Publisher pub = null;
        DataWriter<Msg> dw = null;
        Msg message = null;
        Iterator<Sample<Msg>> result = null;
        Selector<Msg> selector = null;
        try {
            selector = dataReader.select();
            pub = participant.createPublisher();
            dw = pub.createDataWriter(topic);
            message = new Msg(88, "testReadSelectorOfTYPE4");
            /*
             * get instanceHandle from datawriter so expect IllegalArgument on
             * the take as it is not a valid handle for the datareader
             */
            InstanceHandle ih = dw.registerInstance(message);
            selector = selector.instance(ih);
            dw.write(message);
            Thread.sleep(util.getWriteSleepTime());
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testReadSelectorOfTYPE4", e, false));
        }
        boolean exceptionOccured = false;
        try {
            result = dataReader.read(selector);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testReadSelectorOfTYPE4", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        } finally {
            try {
                dataReader.take();
                dw.close();
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testReadSelectorOfTYPE4", e, false));
            }
        }
        assertTrue("Check for invalid Iterator object failed", result == null);
        assertTrue("Check if IllegalArgumentException occured failed", exceptionOccured);
    }

    /**
     * Test method for
     * {@link org.omg.dds.sub.DataReader#read(org.omg.dds.sub.DataReader.Selector)}
     * .
     */
    @Test
    public void testReadSelectorOfTYPENull() {
        checkValidEntities();
        Iterator<Sample<Msg>> result = null;
        Selector<Msg> selector = null;
        try {
            result = dataReader.read(selector);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testReadSelectorOfTYPENull", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check IllegalArgumentException occured failed", result == null);

    }

    /**
     * Test method for {@link org.omg.dds.sub.DataReader#read(int)} .
     */
    @Test
    public void testReadIntNegative() {
        checkValidEntities();
        Iterator<Sample<Msg>> result = null;
        boolean exceptionOccured = false;
        try {
            result = dataReader.read(-2);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testReadIntNegative", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("Check IllegalArgumentException occured failed", exceptionOccured);
        assertTrue("Check for correct result failed", result == null);

    }

    /**
     * Test method for {@link org.omg.dds.sub.DataReader#read(int)} .
     */

    @Test
    public void testReadIntOverflow() {
        checkValidEntities();
        Iterator<Sample<Msg>> result = null;
        boolean exceptionOccured = false;
        try {
            result = dataReader.read(Integer.MAX_VALUE + 1);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testReadIntOverflow", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("Check IllegalArgumentException occured failed", exceptionOccured);
        assertTrue("Check for correct result failed", result == null);

    }

    /**
     * Test method for {@link org.omg.dds.sub.DataReader#read(int)}.
     */
    @Test
    public void testReadInt() {
        checkValidEntities();
        Publisher pub = null;
        DataWriter<Msg> dw = null;
        Msg message = new Msg(3, "testReadInt");
        Iterator<Sample<Msg>> result = null;
        try {
            pub = participant.createPublisher();
            dw = pub.createDataWriter(topic);
            dw.write(message);
            Thread.sleep(util.getWriteSleepTime());
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testReadInt", e, false));
        }
        try {
            result = dataReader.read(10);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testReadInt", e, false));
        } finally {
            try {
                dataReader.take();
                dw.close();
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testReadInt", e, false));
            }
        }
        if (result != null) {
            int counter = 0;
            boolean messageFound = false;
            while (result.hasNext()) {
                counter++;
                Msg mes = result.next().getData();
                if (mes != null) {
                    if (mes.userID == 3 && mes.message.equals("testReadInt")) {
                        messageFound = true;
                    } else {
                        fail("Found other message with id:" + mes.userID + " message:" + mes.message);
                    }
                }
            }
            assertTrue("Check that the written message is found failed", messageFound);
            assertTrue("Check that the received number of samples (" + counter + ")is less or equal that requested (10) failed", counter <= 10);
        }
    }

    /**
     * Test method for {@link org.omg.dds.sub.DataReader#read(java.util.List)}.
     */
    @Test
    public void testReadListOfSampleOfTYPE() {
        checkValidEntities();
        Publisher pub = null;
        DataWriter<Msg> dw = null;
        Msg message = new Msg(4, "testReadListOfSampleOfTYPE");
        List<Sample<Msg>> result = null;
        List<Sample<Msg>> samples = new ArrayList<Sample<Msg>>(10);

        try {
            pub = participant.createPublisher();
            dw = pub.createDataWriter(topic);
            dw.write(message);
            Thread.sleep(util.getWriteSleepTime());
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testReadListOfSampleOfTYPE", e, false));
        }
        try {
            result = dataReader.read(samples);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testReadListOfSampleOfTYPE", e, false));
        } finally {
            try {
                dataReader.take();
                dw.close();
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testReadListOfSampleOfTYPE", e, false));
            }
        }

        if (result != null) {
            boolean messageFound = false;
            for (Sample<Msg> sample : samples) {
                if (sample != null) {
                    Msg mes = sample.getData();
                    if (mes != null) {
                        if (mes.userID == 4 && mes.message.equals("testReadListOfSampleOfTYPE")) {
                            messageFound = true;
                        } else {
                            fail("Found other message with id:" + mes.userID + " message:" + mes.message);
                        }
                    }
                }
            }
            assertTrue("Check that the written message is found failed", messageFound);
            messageFound = false;
            for (Sample<Msg> sample : result) {
                if (sample != null) {
                    Msg mes = sample.getData();
                    if (mes != null) {
                        if (mes.userID == 4 && mes.message.equals("testReadListOfSampleOfTYPE")) {
                            messageFound = true;
                        } else {
                            fail("Found other message with id:" + mes.userID + " message:" + mes.message);
                        }
                    }
                }
            }
            assertTrue("Check that the written message is found failed", messageFound);
        }
    }

    /**
     * Test method for {@link org.omg.dds.sub.DataReader#read(java.util.List)}.
     */
    @Test
    public void testReadListOfSampleOfTYPENull() {
        checkValidEntities();
        Publisher pub = null;
        DataWriter<Msg> dw = null;
        Msg message = new Msg(6, "testReadListOfSampleOfTYPENull");
        List<Sample<Msg>> result = null;
        List<Sample<Msg>> samples = null;

        try {
            pub = participant.createPublisher();
            dw = pub.createDataWriter(topic);
            dw.write(message);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testReadListOfSampleOfTYPENull", e, false));
        }
        try {
            result = dataReader.read(samples);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testReadListOfSampleOfTYPENull", e, false));
        } finally {
            try {
                dataReader.take();
                dw.close();
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testReadListOfSampleOfTYPENull", e, false));
            }
        }

        if (result != null) {
            boolean messageFound = false;
            for (Sample<Msg> sample : result) {
                if (sample != null) {
                    Msg mes = sample.getData();
                    if (mes != null) {
                        if (mes.userID == 6 && mes.message.equals("testReadListOfSampleOfTYPENull")) {
                            messageFound = true;
                        } else {
                            fail("Found other message with id:" + mes.userID + " message:" + mes.message);
                        }
                    }
                }
            }
            assertTrue("Check that the written message is found failed", messageFound);
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.sub.DataReader#read(java.util.List, org.omg.dds.sub.DataReader.Selector)}
     * .
     */
    @Test
    public void testReadListOfSampleOfTYPESelectorOfTYPE() {
        checkValidEntities();
        Publisher pub = null;
        DataWriter<Msg> dw = null;
        Msg message = new Msg(5, "testReadListOfSampleOfTYPESelectorOfTYPE");
        List<Sample<Msg>> result = null;
        List<Sample<Msg>> samples = new ArrayList<Sample<Msg>>(10);
        DataState ds = subscriber.createDataState();
        ds = ds.withAnyInstanceState().withAnySampleState().withAnyViewState();
        Selector<Msg> selector = dataReader.select().dataState(ds);

        try {
            pub = participant.createPublisher();
            dw = pub.createDataWriter(topic);
            dw.write(message);
            Thread.sleep(util.getWriteSleepTime());
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testReadListOfSampleOfTYPESelectorOfTYPE", e, false));
        }
        try {
            result = dataReader.read(samples, selector);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testReadListOfSampleOfTYPESelectorOfTYPE", e, false));
        } finally {
            try {
                dataReader.take();
                dw.close();
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testReadListOfSampleOfTYPESelectorOfTYPE", e, false));
            }
        }

        if (result != null) {
            boolean messageFound = false;
            for (Sample<Msg> sample : samples) {
                if (sample != null) {
                    Msg mes = sample.getData();
                    if (mes != null) {
                        if (mes.userID == 5 && mes.message.equals("testReadListOfSampleOfTYPESelectorOfTYPE")) {
                            messageFound = true;
                        } else {
                            fail("Found other message with id:" + mes.userID + " message:" + mes.message);
                        }
                    }
                }
            }
            assertTrue("Check that the written message is found failed", messageFound);
            messageFound = false;
            for (Sample<Msg> sample : result) {
                if (sample != null) {
                    Msg mes = sample.getData();
                    if (mes != null) {
                        if (mes.userID == 5 && mes.message.equals("testReadListOfSampleOfTYPESelectorOfTYPE")) {
                            messageFound = true;
                        } else {
                            fail("Found other message with id:" + mes.userID + " message:" + mes.message);
                        }
                    }
                }
            }
            assertTrue("Check that the written message is found failed", messageFound);
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.sub.DataReader#read(java.util.List, org.omg.dds.sub.DataReader.Selector)}
     * .
     */
    @Test
    public void testReadListOfSampleOfTYPESelectorOfTYPE1() {
        checkValidEntities();
        Publisher pub = null;
        DataWriter<Msg> dw = null;
        Msg message = null;
        List<Sample<Msg>> result = null;
        List<Sample<Msg>> samples = new ArrayList<Sample<Msg>>(10);
        Selector<Msg> selector = null;
        boolean skip = false;
        try {
            DataState ds = subscriber.createDataState();
            ds = ds.withAnyInstanceState().withAnySampleState().withAnyViewState();
            selector = dataReader.select().dataState(ds);
            selector = selector.nextInstance(true);
            pub = participant.createPublisher();
            dw = pub.createDataWriter(topic);
            pub = participant.createPublisher();
            dw = pub.createDataWriter(topic);
            for (int i = 0; i < 5; i++) {
                message = new Msg(i, "testReadListOfSampleOfTYPESelectorOfTYPE1");
                dw.write(message);
            }
            Thread.sleep(util.getWriteSleepTime());
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testReadListOfSampleOfTYPESelectorOfTYPE1", e, false));
            skip = true;
        }
        try {
            if (!skip) {
                for (int i = 0; i < 5; i++) {
                    result = dataReader.read(samples, selector);

                    if (result != null) {
                        boolean messageFound = false;
                        for (Sample<Msg> sample : samples) {
                            if (sample != null) {
                                Msg mes = sample.getData();
                                if (mes != null) {
                                    selector = selector.instance(dataReader.lookupInstance(mes));
                                    if (mes.userID == i && mes.message.equals("testReadListOfSampleOfTYPESelectorOfTYPE1")) {
                                        messageFound = true;
                                    } else {
                                        fail("Found other message with id:" + mes.userID + " message:" + mes.message);
                                    }
                                }
                            }
                        }
                        assertTrue("Check that the written message is found failed", messageFound);
                        messageFound = false;
                        for (Sample<Msg> sample : result) {
                            if (sample != null) {
                                Msg mes = sample.getData();
                                if (mes != null) {
                                    if (mes.userID == i && mes.message.equals("testReadListOfSampleOfTYPESelectorOfTYPE1")) {
                                        messageFound = true;
                                    } else {
                                        fail("Found other message with id:" + mes.userID + " message:" + mes.message);
                                    }
                                }
                            }
                        }
                        assertTrue("Check that the written message is found failed", messageFound);
                    }
                }
            }
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testReadListOfSampleOfTYPESelectorOfTYPE1", e, false));
        } finally {
            try {
                dataReader.take();
                dw.close();
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testReadListOfSampleOfTYPESelectorOfTYPE1", e, false));
            }
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.sub.DataReader#read(java.util.List, org.omg.dds.sub.DataReader.Selector)}
     * .
     */
    @Test
    public void testReadListOfSampleOfTYPESelectorOfTYPE2() {
        checkValidEntities();
        Publisher pub = null;
        DataWriter<Msg> dw = null;
        Msg message = null;
        List<Sample<Msg>> result = null;
        List<Sample<Msg>> samples = new ArrayList<Sample<Msg>>(10);
        Selector<Msg> selector = null;
        boolean skip = false;
        try {
            selector = dataReader.select();
            selector = selector.nextInstance(true);
            pub = participant.createPublisher();
            dw = pub.createDataWriter(topic);
            pub = participant.createPublisher();
            dw = pub.createDataWriter(topic);
            for (int i = 0; i < 5; i++) {
                message = new Msg(i, "testReadListOfSampleOfTYPESelectorOfTYPE2");
                dw.write(message);
            }
            Thread.sleep(util.getWriteSleepTime());
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testReadListOfSampleOfTYPESelectorOfTYPE2", e, false));
            skip = true;
        }
        try {
            if (!skip) {
                for (int i = 0; i < 5; i++) {
                    result = dataReader.read(samples, selector);

                    if (result != null) {
                        boolean messageFound = false;
                        for (Sample<Msg> sample : samples) {
                            if (sample != null) {
                                Msg mes = sample.getData();
                                if (mes != null) {
                                    selector = selector.instance(dataReader.lookupInstance(mes));
                                    if (mes.userID == i && mes.message.equals("testReadListOfSampleOfTYPESelectorOfTYPE2")) {
                                        messageFound = true;
                                    } else {
                                        fail("Found other message with id:" + mes.userID + " message:" + mes.message);
                                    }
                                }
                            }
                        }
                        assertTrue("Check that the written message is found failed", messageFound);
                        messageFound = false;
                        for (Sample<Msg> sample : result) {
                            if (sample != null) {
                                Msg mes = sample.getData();
                                if (mes != null) {
                                    if (mes.userID == i && mes.message.equals("testReadListOfSampleOfTYPESelectorOfTYPE2")) {
                                        messageFound = true;
                                    } else {
                                        fail("Found other message with id:" + mes.userID + " message:" + mes.message);
                                    }
                                }
                            }
                        }
                        assertTrue("Check that the written message is found failed", messageFound);
                    }
                }
            }
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testReadListOfSampleOfTYPESelectorOfTYPE2", e, false));
        } finally {
            try {
                dataReader.take();
                dw.close();
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testReadListOfSampleOfTYPESelectorOfTYPE2", e, false));
            }
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.sub.DataReader#read(java.util.List, org.omg.dds.sub.DataReader.Selector)}
     * .
     */
    @Test
    public void testReadListOfSampleOfTYPESelectorOfTYPE3() {
        checkValidEntities();
        Publisher pub = null;
        DataWriter<Msg> dw = null;
        Msg message = null;
        List<Sample<Msg>> result = null;
        List<Sample<Msg>> samples = new ArrayList<Sample<Msg>>(10);
        Selector<Msg> selector = dataReader.select();
        pub = participant.createPublisher();
        dw = pub.createDataWriter(topic);

        try {
            pub = participant.createPublisher();
            dw = pub.createDataWriter(topic);
            message = new Msg(99, "testReadListOfSampleOfTYPESelectorOfTYPE3");
            dw.write(message);
            Thread.sleep(util.getWriteSleepTime());
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testReadListOfSampleOfTYPESelectorOfTYPE3", e, false));
        }
        Iterator<Sample<Msg>> result1 = null;
        InstanceHandle ih = null;
        try {
            result1 = dataReader.read();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testReadListOfSampleOfTYPESelectorOfTYPE3", e, false));
        }
        if (result1 != null) {
            boolean messageFound = false;
            while (result1.hasNext()) {
                Msg mes = result1.next().getData();
                if (mes != null) {
                    if (mes.userID == 99 && mes.message.equals("testReadListOfSampleOfTYPESelectorOfTYPE3")) {
                        messageFound = true;
                        ih = dataReader.lookupInstance(mes);
                    } else {
                        fail("Found other message with id:" + mes.userID + " message:" + mes.message);
                    }
                }
            }
            assertTrue("Check that the written message is found failed", messageFound);
        }

        try {
            selector.instance(ih);
            result = dataReader.read(samples, selector);

            if (result != null) {
                boolean messageFound = false;
                for (Sample<Msg> sample : samples) {
                    if (sample != null) {
                        Msg mes = sample.getData();
                        if (mes != null) {
                            if (mes.userID == 99 && mes.message.equals("testReadListOfSampleOfTYPESelectorOfTYPE3")) {
                                messageFound = true;
                            } else {
                                fail("Found other message with id:" + mes.userID + " message:" + mes.message);
                            }
                        }
                    }
                }
                assertTrue("Check that the written message is found failed", messageFound);
                messageFound = false;
                for (Sample<Msg> sample : result) {
                    if (sample != null) {
                        Msg mes = sample.getData();
                        if (mes != null) {
                            if (mes.userID == 99 && mes.message.equals("testReadListOfSampleOfTYPESelectorOfTYPE3")) {
                                messageFound = true;
                            } else {
                                fail("Found other message with id:" + mes.userID + " message:" + mes.message);
                            }
                        }
                    }
                }
                assertTrue("Check that the written message is found failed", messageFound);
            }

        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testReadListOfSampleOfTYPESelectorOfTYPE3", e, false));
        } finally {
            try {
                dataReader.take();
                dw.close();
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testReadListOfSampleOfTYPESelectorOfTYPE3", e, false));
            }
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.sub.DataReader#read(java.util.List, org.omg.dds.sub.DataReader.Selector)}
     * .
     */
    @Test
    public void testReadListOfSampleOfTYPESelectorOfTYPENull() {
        List<Sample<Msg>> result = null;
        List<Sample<Msg>> samples = new ArrayList<Sample<Msg>>(10);
        Selector<Msg> selector = null;
        try {
            result = dataReader.read(samples, selector);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e),
                    util.exceptionCheck("testReadListOfSampleOfTYPESelectorOfTYPENull", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check IllegalArgumentException occured failed", util.objectCheck("testReadListOfSampleOfTYPESelectorOfTYPENull", result));
    }


    /**
     * Test method for {@link org.omg.dds.sub.DataReader#take()}.
     */
    @Test
    public void testTake() {
        checkValidEntities();
        Publisher pub = null;
        DataWriter<Msg> dw = null;
        Msg message = new Msg(6, "testTake");
        Iterator<Sample<Msg>> result = null;
        try {
            pub = participant.createPublisher();
            dw = pub.createDataWriter(topic);
            dw.write(message);
            Thread.sleep(util.getWriteSleepTime());
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testTake", e, false));
        }
        try {
            result = dataReader.take();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testTake", e, false));
        } finally {
            try {
                dw.close();
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testTake", e, false));
            }
        }

        if (result != null) {
            boolean messageFound = false;
            while (result.hasNext()) {
                Msg mes = result.next().getData();
                if (mes != null) {
                    if (mes.userID == 6 && mes.message.equals("testTake")) {
                        messageFound = true;
                    } else {
                        fail("Found other message with id:" + mes.userID + " message:" + mes.message);
                    }
                }
            }
            assertTrue("Check that the written message is found failed", messageFound);
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.sub.DataReader#take(org.omg.dds.sub.DataReader.Selector)}
     * .
     */
    @Test
    public void testTakeSelectorOfTYPE() {
        checkValidEntities();
        Publisher pub = null;
        DataWriter<Msg> dw = null;
        Msg message = new Msg(7, "testTakeSelectorOfTYPE");
        Iterator<Sample<Msg>> result = null;
        DataState ds = subscriber.createDataState();
        ds = ds.withAnyInstanceState().withAnySampleState().withAnyViewState();
        Selector<Msg> selector = dataReader.select().dataState(ds);

        try {
            pub = participant.createPublisher();
            dw = pub.createDataWriter(topic);
            dw.write(message);
            Thread.sleep(util.getWriteSleepTime());
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testTakeSelectorOfTYPE", e, false));
        }
        try {
            result = dataReader.take(selector);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testTakeSelectorOfTYPE", e, false));
        } finally {
            try {
                dw.close();
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testTakeSelectorOfTYPE", e, false));
            }
        }

        if (result != null) {
            boolean messageFound = false;
            while (result.hasNext()) {
                Msg mes = result.next().getData();
                if (mes != null) {
                    if (mes.userID == 7 && mes.message.equals("testTakeSelectorOfTYPE")) {
                        messageFound = true;
                    } else {
                        fail("Found other message with id:" + mes.userID + " message:" + mes.message);
                    }
                }
            }
            assertTrue("Check that the written message is found failed", messageFound);
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.sub.DataReader#take(org.omg.dds.sub.DataReader.Selector)}
     * .
     */
    @Test
    public void testTakeSelectorOfTYPE1() {
        checkValidEntities();
        Publisher pub = null;
        DataWriter<Msg> dw = null;
        Msg message = null;
        Iterator<Sample<Msg>> result = null;
        Selector<Msg> selector = null;
        boolean skip = false;
        try {
            DataState ds = subscriber.createDataState();
            ds = ds.withAnyInstanceState().withAnySampleState().withAnyViewState();
            selector = dataReader.select().dataState(ds);
            selector = selector.nextInstance(true);
            pub = participant.createPublisher();
            dw = pub.createDataWriter(topic);
            for (int i = 0; i < 5; i++) {
                message = new Msg(i, "testTakeSelectorOfTYPE1");
                dw.write(message);
            }
            Thread.sleep(util.getWriteSleepTime());
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testTakeSelectorOfTYPE1", e, false));
            skip = true;
        }
        try {
            if (!skip) {
                for (int i = 0; i < 5; i++) {
                    result = dataReader.take(selector);
                    if (result != null) {
                        boolean messageFound = false;
                        while (result.hasNext()) {
                            Msg mes = result.next().getData();
                            if (mes != null) {
                                if (mes.userID == i && mes.message.equals("testTakeSelectorOfTYPE1")) {
                                    messageFound = true;
                                }
                            }
                        }
                        assertTrue("Check that the written message is found failed", messageFound);
                    }
                }
            }
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testTakeSelectorOfTYPE1", e, false));
        } finally {
            try {
                dw.close();
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testTakeSelectorOfTYPE1", e, false));
            }
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.sub.DataReader#take(org.omg.dds.sub.DataReader.Selector)}
     * .
     */
    @Test
    public void testTakeSelectorOfTYPE2() {
        checkValidEntities();
        Publisher pub = null;
        DataWriter<Msg> dw = null;
        Msg message = null;
        Iterator<Sample<Msg>> result = null;
        Selector<Msg> selector = null;
        boolean skip = false;
        try {
            selector = dataReader.select();
            selector = selector.nextInstance(true);
            pub = participant.createPublisher();
            dw = pub.createDataWriter(topic);
            for (int i = 0; i < 5; i++) {
                message = new Msg(i, "testTakeSelectorOfTYPE2");
                dw.write(message);
            }
            Thread.sleep(util.getWriteSleepTime());
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testTakeSelectorOfTYPE2", e, false));
            skip = true;
        }
        try {
            if (!skip) {
                for (int i = 0; i < 5; i++) {
                    result = dataReader.take(selector);
                    if (result != null) {
                        boolean messageFound = false;
                        while (result.hasNext()) {
                            Msg mes = result.next().getData();
                            if (mes != null) {
                                if (mes.userID == i && mes.message.equals("testTakeSelectorOfTYPE2")) {
                                    messageFound = true;
                                }
                            }
                        }
                        assertTrue("Check that the written message is found failed", messageFound);
                    }
                }
            }
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testTakeSelectorOfTYPE2", e, false));
        } finally {
            try {
                dw.close();
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testTakeSelectorOfTYPE2", e, false));
            }
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.sub.DataReader#take(org.omg.dds.sub.DataReader.Selector)}
     * .
     */
    @Test
    public void testTakeSelectorOfTYPE3() {
        checkValidEntities();
        Publisher pub = null;
        DataWriter<Msg> dw = null;
        Msg message = null;
        Iterator<Sample<Msg>> result = null;
        Selector<Msg> selector = null;
        InstanceHandle ih = null;
        try {
            selector = dataReader.select();
            pub = participant.createPublisher();
            dw = pub.createDataWriter(topic);
            message = new Msg(77, "testTakeSelectorOfTYPE3");
            dw.write(message);
            Thread.sleep(util.getWriteSleepTime());
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testTakeSelectorOfTYPE3", e, false));
        }

        try {
            result = dataReader.read();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testTakeSelectorOfTYPE3", e, false));
        }
        if (result != null) {
            boolean messageFound = false;
            while (result.hasNext()) {
                Msg mes = result.next().getData();
                if (mes != null) {
                    if (mes.userID == 77 && mes.message.equals("testTakeSelectorOfTYPE3")) {
                        messageFound = true;
                        ih = dataReader.lookupInstance(mes);
                    } else {
                        fail("Found other message with id:" + mes.userID + " message:" + mes.message);
                    }
                }
            }
            assertTrue("Check that the written message is found failed", messageFound);
        }

        try {
            selector.instance(ih);
            result = dataReader.take(selector);
            if (result != null) {
                boolean messageFound = false;
                while (result.hasNext()) {
                    Msg mes = result.next().getData();
                    if (mes != null) {
                        if (mes.userID == 77 && mes.message.equals("testTakeSelectorOfTYPE3")) {
                            messageFound = true;
                        }
                    }
                }
                assertTrue("Check that the written message is found failed", messageFound);
            }

        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testTakeSelectorOfTYPE3", e, false));
        } finally {
            try {
                dw.close();
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testTakeSelectorOfTYPE3", e, false));
            }
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.sub.DataReader#take(org.omg.dds.sub.DataReader.Selector)}
     * .
     */
    @Test
    public void testTakeSelectorOfTYPE4() {
        checkValidEntities();

        Publisher pub = null;
        DataWriter<Msg> dw = null;
        Msg message = null;
        Iterator<Sample<Msg>> result = null;
        Selector<Msg> selector = null;
        try {
            selector = dataReader.select();
            pub = participant.createPublisher();
            dw = pub.createDataWriter(topic);
            message = new Msg(88, "testTakeSelectorOfTYPE4");
            /*
             * get instanceHandle from datawriter so expect IllegalArgument on
             * the take as it is not a valid handle for the datareader
             */
            InstanceHandle ih = dw.registerInstance(message);
            selector = selector.instance(ih);
            dw.write(message);
            Thread.sleep(util.getWriteSleepTime());
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testTakeSelectorOfTYPE4", e, false));
        }
        boolean exceptionOccured = false;
        try {
            result = dataReader.take(selector);

        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testTakeSelectorOfTYPE4", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        } finally {
            try {
                dataReader.take();
                dw.close();
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testTakeSelectorOfTYPE4", e, false));
            }
        }
        assertTrue("Check for invalid Iterator object failed", result == null);
        assertTrue("Check if IllegalArgumentException occured failed", exceptionOccured);
    }

    /**
     * Test method for
     * {@link org.omg.dds.sub.DataReader#take(org.omg.dds.sub.DataReader.Selector)}
     * .
     */
    @Test
    public void testTakeSelectorOfTYPENull() {
        checkValidEntities();
        Iterator<Sample<Msg>> result = null;
        Selector<Msg> selector = null;

        try {
            result = dataReader.take(selector);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testTakeSelectorOfTYPENull", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for invalid Iterator object failed", result == null);
    }

    /**
     * Test method for {@link org.omg.dds.sub.DataReader#take(int)}.
     */
    @Test
    public void testTakeInt() {
        checkValidEntities();
        Publisher pub = null;
        DataWriter<Msg> dw = null;
        Msg message = new Msg(8, "testTakeInt");
        Iterator<Sample<Msg>> result = null;
        try {
            pub = participant.createPublisher();
            dw = pub.createDataWriter(topic);
            dw.write(message);
            Thread.sleep(util.getWriteSleepTime());
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testTakeInt", e, false));
        }
        try {
            result = dataReader.take(10);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testTakeInt", e, false));
        } finally {
            try {
                dw.close();
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testTakeInt", e, false));
            }
        }
        if (result != null) {
            int counter = 0;
            boolean messageFound = false;
            while (result.hasNext()) {
                counter++;
                Msg mes = result.next().getData();
                if (mes != null) {
                    if (mes.userID == 8 && mes.message.equals("testTakeInt")) {
                        messageFound = true;
                    } else {
                        fail("Found other message with id:" + mes.userID + " message:" + mes.message);
                    }
                }
            }
            assertTrue("Check that the written message is found failed", messageFound);
            assertTrue("Check that the received number of samples (" + counter + ")is less or equal that requested (10) failed", counter <= 10);
        }
    }

    /**
     * Test method for {@link org.omg.dds.sub.DataReader#take(java.util.List)}.
     */
    @Test
    public void testTakeListOfSampleOfTYPE() {
        checkValidEntities();
        Publisher pub = null;
        DataWriter<Msg> dw = null;
        Msg message = new Msg(9, "testTakeListOfSampleOfTYPE");
        List<Sample<Msg>> result = null;
        List<Sample<Msg>> samples = new ArrayList<Sample<Msg>>(10);

        try {
            pub = participant.createPublisher();
            dw = pub.createDataWriter(topic);
            dw.write(message);
            Thread.sleep(util.getWriteSleepTime());
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testTakeListOfSampleOfTYPE", e, false));
        }
        try {
            result = dataReader.take(samples);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testTakeListOfSampleOfTYPE", e, false));
        } finally {
            try {
                dw.close();
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testTakeListOfSampleOfTYPE", e, false));
            }
        }

        if (result != null) {
            boolean messageFound = false;
            for (Sample<Msg> sample : samples) {
                if (sample != null) {
                    Msg mes = sample.getData();
                    if (mes != null) {
                        if (mes.userID == 9 && mes.message.equals("testTakeListOfSampleOfTYPE")) {
                            messageFound = true;
                        } else {
                            fail("Found other message with id:" + mes.userID + " message:" + mes.message);
                        }
                    }
                }
            }
            assertTrue("Check that the written message is found failed", messageFound);
            messageFound = false;
            for (Sample<Msg> sample : result) {
                if (sample != null) {
                    Msg mes = sample.getData();
                    if (mes != null) {
                        if (mes.userID == 9 && mes.message.equals("testTakeListOfSampleOfTYPE")) {
                            messageFound = true;
                        } else {
                            fail("Found other message with id:" + mes.userID + " message:" + mes.message);
                        }
                    }
                }
            }
            assertTrue("Check that the written message is found failed", messageFound);
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.sub.DataReader#take(java.util.List, org.omg.dds.sub.DataReader.Selector)}
     * .
     */
    @Test
    public void testTakeListOfSampleOfTYPESelectorOfTYPE() {
        checkValidEntities();
        Publisher pub = null;
        DataWriter<Msg> dw = null;
        Msg message = new Msg(10, "testTakeListOfSampleOfTYPESelectorOfTYPE");
        List<Sample<Msg>> result = null;
        List<Sample<Msg>> samples = new ArrayList<Sample<Msg>>(10);
        DataState ds = subscriber.createDataState();
        ds = ds.withAnyInstanceState().withAnySampleState().withAnyViewState();
        Selector<Msg> selector = dataReader.select().dataState(ds);

        try {
            pub = participant.createPublisher();
            dw = pub.createDataWriter(topic);
            dw.write(message);
            Thread.sleep(util.getWriteSleepTime());
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testTakeListOfSampleOfTYPESelectorOfTYPE", e, false));
        }
        try {
            result = dataReader.take(samples, selector);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testTakeListOfSampleOfTYPESelectorOfTYPE", e, false));
        } finally {
            try {
                dw.close();
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testTakeListOfSampleOfTYPESelectorOfTYPE", e, false));
            }
        }

        if (result != null) {
            boolean messageFound = false;
            for (Sample<Msg> sample : samples) {
                if (sample != null) {
                    Msg mes = sample.getData();
                    if (mes != null) {
                        if (mes.userID == 10 && mes.message.equals("testTakeListOfSampleOfTYPESelectorOfTYPE")) {
                            messageFound = true;
                        } else {
                            fail("Found other message with id:" + mes.userID + " message:" + mes.message);
                        }
                    }
                }
            }
            assertTrue("Check that the written message is found failed", messageFound);
            messageFound = false;
            for (Sample<Msg> sample : result) {
                if (sample != null) {
                    Msg mes = sample.getData();
                    if (mes != null) {
                        if (mes.userID == 10 && mes.message.equals("testTakeListOfSampleOfTYPESelectorOfTYPE")) {
                            messageFound = true;
                        } else {
                            fail("Found other message with id:" + mes.userID + " message:" + mes.message);
                        }
                    }
                }
            }
            assertTrue("Check that the written message is found failed", messageFound);
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.sub.DataTakeer#take(java.util.List, org.omg.dds.sub.DataTakeer.Selector)}
     * .
     */
    @Test
    public void testTakeListOfSampleOfTYPESelectorOfTYPE1() {
        checkValidEntities();
        Publisher pub = null;
        DataWriter<Msg> dw = null;
        Msg message = null;
        List<Sample<Msg>> result = null;
        List<Sample<Msg>> samples = new ArrayList<Sample<Msg>>(10);
        Selector<Msg> selector = null;
        boolean skip = false;
        try {
            DataState ds = subscriber.createDataState();
            ds = ds.withAnyInstanceState().withAnySampleState().withAnyViewState();
            selector = dataReader.select().dataState(ds);
            selector = selector.nextInstance(true);
            pub = participant.createPublisher();
            dw = pub.createDataWriter(topic);
            pub = participant.createPublisher();
            dw = pub.createDataWriter(topic);
            for (int i = 0; i < 5; i++) {
                message = new Msg(i, "testTakeListOfSampleOfTYPESelectorOfTYPE1");
                dw.write(message);
            }
            Thread.sleep(util.getWriteSleepTime());
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testTakeListOfSampleOfTYPESelectorOfTYPE1", e, false));
            skip = true;
        }
        try {
            if (!skip) {
                for (int i = 0; i < 5; i++) {
                    result = dataReader.take(samples, selector);

                    if (result != null) {
                        boolean messageFound = false;
                        for (Sample<Msg> sample : samples) {
                            if (sample != null) {
                                Msg mes = sample.getData();
                                if (mes != null) {
                                    selector = selector.instance(dataReader.lookupInstance(mes));
                                    if (mes.userID == i && mes.message.equals("testTakeListOfSampleOfTYPESelectorOfTYPE1")) {
                                        messageFound = true;
                                    } else {
                                        fail("Found other message with id:" + mes.userID + " message:" + mes.message);
                                    }
                                }
                            }
                        }
                        assertTrue("Check that the written message is found failed", messageFound);
                        messageFound = false;
                        for (Sample<Msg> sample : result) {
                            if (sample != null) {
                                Msg mes = sample.getData();
                                if (mes != null) {
                                    if (mes.userID == i && mes.message.equals("testTakeListOfSampleOfTYPESelectorOfTYPE1")) {
                                        messageFound = true;
                                    } else {
                                        fail("Found other message with id:" + mes.userID + " message:" + mes.message);
                                    }
                                }
                            }
                        }
                        assertTrue("Check that the written message is found failed", messageFound);
                    }
                }
            }
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testTakeListOfSampleOfTYPESelectorOfTYPE1", e, false));
        } finally {
            try {
                dw.close();
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testTakeListOfSampleOfTYPESelectorOfTYPE1", e, false));
            }
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.sub.DataTakeer#take(java.util.List, org.omg.dds.sub.DataTakeer.Selector)}
     * .
     */
    @Test
    public void testTakeListOfSampleOfTYPESelectorOfTYPE2() {
        checkValidEntities();
        Publisher pub = null;
        DataWriter<Msg> dw = null;
        Msg message = null;
        List<Sample<Msg>> result = null;
        List<Sample<Msg>> samples = new ArrayList<Sample<Msg>>(10);
        Selector<Msg> selector = null;
        boolean skip = false;

        try {
            selector = dataReader.select();
            selector = selector.nextInstance(true);
            pub = participant.createPublisher();
            dw = pub.createDataWriter(topic);
            pub = participant.createPublisher();
            dw = pub.createDataWriter(topic);
            for (int i = 0; i < 5; i++) {
                message = new Msg(i, "testTakeListOfSampleOfTYPESelectorOfTYPE2");
                dw.write(message);
            }
            Thread.sleep(util.getWriteSleepTime());
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testTakeListOfSampleOfTYPESelectorOfTYPE2", e, false));
            skip = true;
        }
        try {
            if (!skip) {
                for (int i = 0; i < 5; i++) {
                    result = dataReader.take(samples, selector);

                    if (result != null) {
                        boolean messageFound = false;
                        for (Sample<Msg> sample : samples) {
                            if (sample != null) {
                                Msg mes = sample.getData();
                                if (mes != null) {
                                    selector = selector.instance(dataReader.lookupInstance(mes));
                                    if (mes.userID == i && mes.message.equals("testTakeListOfSampleOfTYPESelectorOfTYPE2")) {
                                        messageFound = true;
                                    } else {
                                        fail("Found other message with id:" + mes.userID + " message:" + mes.message);
                                    }
                                }
                            }
                        }
                        assertTrue("Check that the written message is found failed", messageFound);
                        messageFound = false;
                        for (Sample<Msg> sample : result) {
                            if (sample != null) {
                                Msg mes = sample.getData();
                                if (mes != null) {
                                    if (mes.userID == i && mes.message.equals("testTakeListOfSampleOfTYPESelectorOfTYPE2")) {
                                        messageFound = true;
                                    } else {
                                        fail("Found other message with id:" + mes.userID + " message:" + mes.message);
                                    }
                                }
                            }
                        }
                        assertTrue("Check that the written message is found failed", messageFound);
                    }
                }
            }
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testTakeListOfSampleOfTYPESelectorOfTYPE2", e, false));
        } finally {
            try {
                dw.close();
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testTakeListOfSampleOfTYPESelectorOfTYPE2", e, false));
            }
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.sub.DataTakeer#take(java.util.List, org.omg.dds.sub.DataTakeer.Selector)}
     * .
     */
    @Test
    public void testTakeListOfSampleOfTYPESelectorOfTYPE3() {
        checkValidEntities();
        Publisher pub = null;
        DataWriter<Msg> dw = null;
        Msg message = null;
        List<Sample<Msg>> result = null;
        List<Sample<Msg>> samples = new ArrayList<Sample<Msg>>(10);
        Selector<Msg> selector = dataReader.select();
        pub = participant.createPublisher();
        dw = pub.createDataWriter(topic);

        try {
            pub = participant.createPublisher();
            dw = pub.createDataWriter(topic);
            message = new Msg(99, "testTakeListOfSampleOfTYPESelectorOfTYPE3");
            dw.write(message);
            Thread.sleep(util.getWriteSleepTime());
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testTakeListOfSampleOfTYPESelectorOfTYPE3", e, false));
        }

        Iterator<Sample<Msg>> result1 = null;
        InstanceHandle ih = null;
        try {
            result1 = dataReader.read();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testTakeListOfSampleOfTYPESelectorOfTYPE3", e, false));
        }
        if (result1 != null) {
            boolean messageFound = false;
            while (result1.hasNext()) {
                Msg mes = result1.next().getData();
                if (mes != null) {
                    if (mes.userID == 99 && mes.message.equals("testTakeListOfSampleOfTYPESelectorOfTYPE3")) {
                        messageFound = true;
                        ih = dataReader.lookupInstance(mes);
                    } else {
                        fail("Found other message with id:" + mes.userID + " message:" + mes.message);
                    }
                }
            }
            assertTrue("Check that the written message is found failed", messageFound);
        }

        try {
            selector = selector.instance(ih);
            result = dataReader.take(samples, selector);
            if (result != null) {
                boolean messageFound = false;
                for (Sample<Msg> sample : samples) {
                    if (sample != null) {
                        Msg mes = sample.getData();
                        if (mes != null) {
                            if (mes.userID == 99 && mes.message.equals("testTakeListOfSampleOfTYPESelectorOfTYPE3")) {
                                messageFound = true;
                            } else {
                                fail("Found other message with id:" + mes.userID + " message:" + mes.message);
                            }
                        }
                    }
                }
                assertTrue("Check that the written message is found failed", messageFound);
                messageFound = false;
                for (Sample<Msg> sample : result) {
                    if (sample != null) {
                        Msg mes = sample.getData();
                        if (mes != null) {
                            if (mes.userID == 99 && mes.message.equals("testTakeListOfSampleOfTYPESelectorOfTYPE3")) {
                                messageFound = true;
                            } else {
                                fail("Found other message with id:" + mes.userID + " message:" + mes.message);
                            }
                        }
                    }
                }
                assertTrue("Check that the written message is found failed", messageFound);
            }

        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testTakeListOfSampleOfTYPESelectorOfTYPE3", e, false));
        } finally {
            try {
                dataReader.take();
                dw.close();
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testTakeListOfSampleOfTYPESelectorOfTYPE3", e, false));
            }
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.sub.DataTakeer#take(java.util.List, org.omg.dds.sub.DataTakeer.Selector)}
     * .
     */
    @Test
    public void testTakeListOfSampleOfTYPESelectorOfTYPENull() {
        checkValidEntities();
        List<Sample<Msg>> result = null;
        Selector<Msg> selector = null;
        List<Sample<Msg>> samples = new ArrayList<Sample<Msg>>(10);

        try {
            result = dataReader.take(samples, selector);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testTakeListOfSampleOfTYPESelectorOfTYPENull", e, false));
        }
        assertTrue("Check for valid Iterator object failed", result != null);
    }

    /**
     * Test method for
     * {@link org.omg.dds.sub.DataReader#readNextSample(org.omg.dds.sub.Sample)}
     * .
     */
    @Test
    public void testReadNextSample() {
        checkValidEntities();
        Publisher pub = null;
        DataWriter<Msg> dw = null;
        Msg message = new Msg(11, "testReadNextSample");
        Sample<Msg> sample = null;
        Iterator<Sample<Msg>> result = null;

        try {
            pub = participant.createPublisher();
            dw = pub.createDataWriter(topic);
            dw.write(message);
            Thread.sleep(util.getWriteSleepTime());
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testReadNextSample", e, false));
        }

        try {
            boolean messageFound = false;
            result = dataReader.read();
            sample = result.next();
            while(dataReader.readNextSample(sample)) {
                if (sample != null) {
                    Msg mes = sample.getData();
                    if (mes != null) {
                        if (mes.userID == 11 && mes.message.equals("testReadNextSample")) {
                            messageFound = true;
                        } else {
                            fail("Found other message with id:" + mes.userID + " message:" + mes.message);
                        }
                    }
                }
            }
            assertTrue("Check that the written message is found failed", messageFound);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testReadNextSample", e, false));
        } finally {
            try {
                dataReader.take();
                dw.close();
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testReadNextSample", e, false));
            }
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.sub.DataReader#takeNextSample(org.omg.dds.sub.Sample)}
     * .
     */
    @Test
    public void testTakeNextSample() {
        checkValidEntities();
        Publisher pub = null;
        DataWriter<Msg> dw = null;
        Msg message = new Msg(12, "testTakeNextSample");
        Sample<Msg> sample = null;
        Iterator<Sample<Msg>> result = null;

        try {
            pub = participant.createPublisher();
            dw = pub.createDataWriter(topic);
            dw.write(message);
            Thread.sleep(util.getWriteSleepTime());
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testTakeNextSample", e, false));
        }

        try {
            boolean messageFound = false;
            result = dataReader.read();
            sample = result.next();
            while (dataReader.takeNextSample(sample)) {
                if (sample != null) {
                    Msg mes = sample.getData();
                    if (mes != null) {
                        if (mes.userID == 12 && mes.message.equals("testTakeNextSample")) {
                            messageFound = true;
                        } else {
                            fail("Found other message with id:" + mes.userID + " message:" + mes.message);
                        }
                    }
                }
            }
            assertTrue("Check that the written message is found failed", messageFound);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testTakeNextSample", e, false));
        } finally {
            try {
                dataReader.take();
                dw.close();
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testTakeNextSample", e, false));
            }
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.sub.DataReader#getKeyValue(java.lang.Object, org.omg.dds.core.InstanceHandle)}
     * .
     */
    @Test
    public void testGetKeyValueTYPEInstanceHandle() {
        checkValidEntities();
        Publisher pub = null;
        DataWriter<Msg> dw = null;
        Iterator<Sample<Msg>> takeResult = null;
        Msg message = new Msg(13, "testGetKeyValueTYPEInstanceHandle");
        Msg result = null;
        Sample<Msg> sample = null;
        try {
            pub = participant.createPublisher();
            dw = pub.createDataWriter(topic);
            dw.write(message);
            Thread.sleep(util.getWriteSleepTime());
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetKeyValueTYPEInstanceHandle", e, false));
        }
        try {
            takeResult = dataReader.take();
            sample = takeResult.next();
            InstanceHandle ih = sample.getInstanceHandle();
            while (takeResult.hasNext()) {
                sample = takeResult.next();
                if (sample.getData() != null && sample.getData().userID == 13) {
                    ih = sample.getInstanceHandle();
                    break;
                }
            }
            result = dataReader.getKeyValue(message, ih);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetKeyValueTYPEInstanceHandle", e, false));
        }
        assertTrue("Check for valid Msg object failed", util.objectCheck("testGetKeyValueTYPEInstanceHandle", result));
        try {
            dw.close();
            pub.close();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetKeyValueTYPEInstanceHandle", e, false));
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.pub.DataReader#getKeyValue(java.lang.Object, org.omg.dds.core.InstanceHandle)}
     * .
     */
    @Test
    public void testGetKeyValueTYPEInstanceHandleNull() {
        checkValidEntities();
        Msg message = new Msg(14, "testGetKeyValueTYPEInstanceHandleNull");
        Msg result = null;
        try {
            result = dataReader.getKeyValue(null, dataReader.lookupInstance(message));
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testGetKeyValueInstanceHandleInvalid", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for invalid Msg object failed", result == null);
    }



    /**
     * Test method for
     * {@link org.omg.dds.pub.DataReader#lookupInstance(java.lang.Object)}.
     */
    @Test
    public void testLookupInstance() {
        checkValidEntities();
        Msg message = new Msg(17, "testLookupInstance");
        InstanceHandle result = null;
        try {
            result = dataReader.lookupInstance(message);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testLookupInstance", e, false));
        }
        assertTrue("Check if for valid InstanceHandle object failed", util.objectCheck("testLookupInstance", result));
    }

    /**
     * Test method for
     * {@link org.omg.dds.pub.DataReader#lookupInstance(java.lang.Object)}.
     */
    @Test
    public void testLookupInstanceNull() {
        checkValidEntities();
        Msg message = null;
        InstanceHandle result = null;
        try {
            result = dataReader.lookupInstance(message);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testLookupInstanceNull", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for invalid InstanceHandle object failed", result == null);
    }

    /**
     * Test method for {@link org.omg.dds.pub.DataReader#getStatusCondition()}.
     */
    @Test
    public void testGetStatusCondition() {
        checkValidEntities();
        StatusCondition<DataReader<Msg>> result = null;
        try {
            result = dataReader.getStatusCondition();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetStatusCondition", e, false));
        }
        assertTrue("Check for valid StatusCondition object failed", util.objectCheck("testGetStatusCondition", result));
    }

    /**
     * Test method for {@link org.omg.dds.pub.DataReader#getParent()}.
     */
    @Test
    public void testGetParent() {
        checkValidEntities();
        Subscriber result = null;
        try {
            result = dataReader.getParent();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetParent", e, false));
        }
        assertTrue("Check for valid Subscriber object failed", util.objectCheck("testGetParent", result.equals(subscriber)));
    }

    /**
     * Test method for {@link org.omg.dds.sub.DataReader#select()}.
     */
    @Test
    public void testSelect() {
        checkValidEntities();
        Selector<Msg> result = null;
        try {
            result = dataReader.select();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetParent", e, false));
        }
        assertTrue("Check for valid Subscriber object failed", util.objectCheck("testGetParent", result));
    }

    /**
     * Test method for {@link org.omg.dds.core.Entity#getListener()}.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetDataReaderListener() {
        checkValidEntities();
        DataReaderListener<Msg> drl = new DataReaderListenerClass();
        DataReaderListener<Msg> dwl1 = null;
        Collection<Class<? extends Status>> statuses = new HashSet<Class<? extends Status>>();
        Class<? extends Status> status = null;
        statuses.add(DataAvailableStatus.class);
        try {
            dataReader.setListener(drl, statuses);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetDataReaderListener", e, false));
        }
        try {
            dwl1 = dataReader.getListener();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetDataReaderListener", e, false));
        }
        assertTrue("Check for valid DataReaderListener object failed", util.objectCheck("testGetDataReaderListener", dwl1));
        /* reset listener to null */
        try {
            dataReader.setListener(null, status);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetDataReaderListener", e, false));
        }
        try {
            dwl1 = dataReader.getListener();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetDataReaderListener", e, false));
        }
        assertTrue("Check for null DataReaderListener object failed", dwl1 == null);
    }

    /**
     * Test method for
     * {@link org.omg.dds.core.Entity#setListener(java.util.EventListener)}.
     */
    @Test
    public void testDataReaderSetListener() {
        checkValidEntities();
        DataReaderListener<Msg> drl = new DataReaderListenerClass();
        try {
            dataReader.setListener(drl);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testDataReaderSetListener", e, false));
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.core.Entity#setListener(java.util.EventListener, java.util.Collection)}
     * .
     */
    @Test
    public void testDataReaderSetListenerCollectionOfClassOfQextendsStatus() {
        checkValidEntities();
        DataReaderListener<Msg> drl = new DataReaderListenerClass();
        Collection<Class<? extends Status>> statuses = new HashSet<Class<? extends Status>>();
        statuses.add(DataAvailableStatus.class);
        try {
            dataReader.setListener(drl, statuses);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testDataReaderSetListenerCollectionOfClassOfQextendsStatus", e, false));
        }
    }

    /**
     * Test method for {@link
     * org.omg.dds.core.Entity#setListener(java.util.EventListener,
     * java.lang.Class<? extends org.omg.dds.core.status.Status>[])}.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testDataReaderSetListenerClassOfQextendsStatusArray() {
        checkValidEntities();
        DataReaderListener<Msg> drl = new DataReaderListenerClass();
        Class<? extends Status> status = DataAvailableStatus.class;
        try {
            dataReader.setListener(drl, status);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testDataReaderSetListenerClassOfQextendsStatusArray", e, false));
        }
    }

    /**
     * Test method for {@link org.omg.dds.core.Entity#getQos()}.
     */
    @Test
    public void testGetQos() {
        checkValidEntities();
        DataReaderQos drq = null;
        try {
            drq = dataReader.getQos();
        } catch (Exception e) {
            fail("Exception occured while doing getQos(): " + util.printException(e));
        }
        assertTrue("Check for valid DataReaderQos", drq != null);
    }

    /**
     * Test method for
     * {@link org.omg.dds.core.Entity#setQos(org.omg.dds.core.EntityQos)}.
     */
    @Test
    public void testSetQos() {
        checkValidEntities();
        DataReaderQos drq = null;
        Duration deadline = Duration.newDuration(5, TimeUnit.SECONDS, env);
        try {
            drq = dataReader.getQos();
        } catch (Exception e) {
            fail("Exception occured while calling getQos: " + util.printException(e));
        }
        assertTrue("Check for valid DataReaderQos", drq != null);
        try {
            drq = drq.withPolicy(drq.getDeadline().withPeriod(deadline));
            dataReader.setQos(drq);
        } catch (Exception ex) {
            fail("Failed to set the DataReaderQos");
        }
        try {
            drq = dataReader.getQos();
            assertTrue("Check for valid DataReaderQos value", drq.getDeadline().getPeriod().getDuration(TimeUnit.SECONDS) == deadline.getDuration(TimeUnit.SECONDS));
            /* restore qos */
            drq = drq.withPolicy(drq.getDeadline().withPeriod(Duration.infiniteDuration(env)));
            dataReader.setQos(drq);
            assertTrue("Check for valid DataReaderQos value", drq.getDeadline().getPeriod().isInfinite());
        } catch (Exception ex) {
            fail("Failed to restore the DataReaderQos");
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.core.Entity#setQos(org.omg.dds.core.EntityQos)}.
     */
    @Test
    public void testSetQosNull() {
        checkValidEntities();
        boolean exceptionOccured = false;
        try {
            dataReader.setQos(null);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testSetQosNull", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException occured failed", exceptionOccured);

    }

    /**
     * Test method for
     * {@link org.omg.dds.core.Entity#setQos(org.omg.dds.core.EntityQos)}.
     */
    @Test
    public void testSetQosException() {
        checkValidEntities();
        DataReaderQos drq = null;
        DataReaderQos original = null;
        boolean exceptionOccured = false;
        try {
            original = dataReader.getQos();

        } catch (Exception e) {
            fail("Exception occured while calling getQos: " + util.printException(e));
        }
        assertTrue("Check for valid DataReaderQos", original != null);
        try {
            drq = original;
            Durability dur = drq.getDurability().withTransient();
            drq = drq.withPolicy(dur);
            dataReader.setQos(drq);
        } catch (Exception e) {
            assertTrue("ImmutablePolicyException expected but got: " + util.printException(e), util.exceptionCheck("testSetQosException", e, e instanceof ImmutablePolicyException));
            exceptionOccured = true;
        }
        assertTrue("Check if ImmutablePolicyException occured failed", exceptionOccured);
        exceptionOccured = false;
        try {
            drq = original;
            TimeBasedFilter tbf = drq.getTimeBasedFilter().withMinimumSeparation(5, TimeUnit.SECONDS);
            Deadline d = drq.getDeadline().withPeriod(3, TimeUnit.SECONDS);
            drq = drq.withPolicy(d).withPolicy(tbf);
            dataReader.setQos(drq);
        } catch (Exception e) {
            assertTrue("InconsistentPolicyException expected but got: " + util.printException(e), util.exceptionCheck("testSetQosException", e, e instanceof InconsistentPolicyException));
            exceptionOccured = true;
        }
        assertTrue("Check if InconsistentPolicyException occured failed", exceptionOccured);
    }

    /**
     * Test method for {@link org.omg.dds.core.Entity#enable()}.
     */
    @Test
    public void testEnable() {
        checkValidEntities();
        try {
            dataReader.enable();
        } catch (Exception e) {
            assertTrue("Exception occured while calling enable()" + util.printException(e), util.exceptionCheck("testEnable", e, false));
        }
    }

    /**
     * Test method for {@link org.omg.dds.core.Entity#getStatusChanges()}.
     */
    @Test
    public void testGetStatusChanges() {
        checkValidEntities();
        Set<Class<? extends Status>> statuses = null;
        try {
            statuses = dataReader.getStatusChanges();
        } catch (Exception e) {
            assertTrue("Exception occured while calling getStatusChanges()" + util.printException(e), util.exceptionCheck("testGetStatusChanges", e, false));
        }
        assertTrue("Check for valid status set", util.objectCheck("testGetStatusChanges", statuses));

    }

    /**
     * Test method for {@link org.omg.dds.core.Entity#getInstanceHandle()}.
     */
    @Test
    public void testGetInstanceHandle() {
        checkValidEntities();
        InstanceHandle ih = null;
        try {
            ih = dataReader.getInstanceHandle();
        } catch (Exception e) {
            fail("Exception occured while calling getInstanceHandle(): " + util.printException(e));
        }
        assertTrue("Check for valid getInstanceHandle", ih != null);
    }

    /**
     * Test method for {@link org.omg.dds.core.Entity#retain()}.
     */
    @Test
    public void testRetain() {
        checkValidEntities();
        try {
            dataReader.retain();
        } catch (Exception e) {
            assertTrue("Exception occured while calling retain()" + util.printException(e), util.exceptionCheck("testRetain", e, false));
        }
    }

    /**
     * Test method for {@link org.omg.dds.core.DDSObject#getEnvironment()}.
     */
    @Test
    public void testGetEnvironment() {
        checkValidEntities();
        ServiceEnvironment senv = null;
        try {
            senv = dataReader.getEnvironment();
        } catch (Exception e) {
            fail("Exception occured while calling getEnvironment(): " + util.printException(e));
        }
        assertTrue("Check for valid ServiceEnvironment", senv != null);
    }

    /**
     * Test if samples are read in order with ordered access enabled at topic
     * level.
     */
    @Test
    public void testPresentationTopicReadOrdered() {
        boolean proceed = true;
        TopicQos tq = null;
        Topic<Msg> t = null;
        PublisherQos pq = null;
        Publisher p = null;
        DataWriter<Msg> w = null;
        SubscriberQos sq = null;
        Subscriber s = null;
        DataReader<Msg> r = null;
        int i, n = 3;

        PolicyFactory policyFactory = env.getSPI().getPolicyFactory();

        try {
            pq = participant.getDefaultPublisherQos().withPolicies(
                policyFactory.Presentation().withAccessScope(AccessScopeKind.TOPIC).withOrderedAccess(true),
                policyFactory.EntityFactory().withAutoEnableCreatedEntities(true));
            sq = participant.getDefaultSubscriberQos().withPolicies(
                policyFactory.Presentation().withAccessScope(AccessScopeKind.TOPIC).withOrderedAccess(true),
                policyFactory.EntityFactory().withAutoEnableCreatedEntities(true));
            tq = participant.getDefaultTopicQos().withPolicies(
                policyFactory.DestinationOrder().withKind(DestinationOrder.Kind.BY_RECEPTION_TIMESTAMP),
                policyFactory.Reliability().withKind(Reliability.Kind.RELIABLE));

            p = participant.createPublisher(pq);
            assertTrue("Check for valid Publisher object", p instanceof Publisher);
            s = participant.createSubscriber(sq);
            assertTrue("Check for valid Subscriber object", s instanceof Subscriber);
            t = participant.createTopic("orderedAccessTopic", Msg.class);
            assertTrue("Check for valid Topic object", t instanceof Topic);

            w = p.createDataWriter(t, p.copyFromTopicQos(p.getDefaultDataWriterQos(), tq));
            r = s.createDataReader(t, s.copyFromTopicQos(s.getDefaultDataReaderQos(), tq));

            Msg m = new Msg();
            Iterator<Sample<Msg>> iter = null;

            for (i = n; i > 0; i--) {
                m.userID = i;
                w.write(m);
            }

            iter = r.take();
            for (i = n; iter.hasNext() && proceed; i--) {
                m = iter.next().getData();
                if (m != null) {
                    assertTrue("Sample received out of order; expected sample with key " + i + ", received sample with key " + m.userID, m.userID == i);
                    if (m.userID != n) {
                        proceed = false;
                    }
                } else {
                    proceed = false;
                }
            }
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testPresentationGroupReadOrdered", e, false));
        }
    }


    /**
     * Test if samples are read in order with ordered access enabled at topic
     * level.
     */
    @Test
    public void testPresentationTopicReadOrderedHead() {
        TopicQos tq = null;
        Topic<Msg> t = null;
        PublisherQos pq = null;
        Publisher p = null;
        DataWriter<Msg> w = null;
        SubscriberQos sq = null;
        Subscriber s = null;
        DataReader<Msg> r = null;
        int i, n = 3;

        PolicyFactory policyFactory = env.getSPI().getPolicyFactory();

        try {
            pq = participant.getDefaultPublisherQos().withPolicies(
                policyFactory.Presentation().withAccessScope(AccessScopeKind.TOPIC).withOrderedAccess(true),
                policyFactory.EntityFactory().withAutoEnableCreatedEntities(true));
            sq = participant.getDefaultSubscriberQos().withPolicies(
                policyFactory.Presentation().withAccessScope(AccessScopeKind.TOPIC).withOrderedAccess(true),
                policyFactory.EntityFactory().withAutoEnableCreatedEntities(true));
            tq = participant.getDefaultTopicQos().withPolicies(
                policyFactory.DestinationOrder().withKind(DestinationOrder.Kind.BY_RECEPTION_TIMESTAMP),
                policyFactory.Reliability().withKind(Reliability.Kind.RELIABLE));

            p = participant.createPublisher(pq);
            assertTrue("Check for valid Publisher object", p instanceof Publisher);
            s = participant.createSubscriber(sq);
            assertTrue("Check for valid Subscriber object", s instanceof Subscriber);
            t = participant.createTopic("orderedAccessTopic", Msg.class);
            assertTrue("Check for valid Topic object", t instanceof Topic);

            w = p.createDataWriter(t, p.copyFromTopicQos(p.getDefaultDataWriterQos(), tq));
            r = s.createDataReader(t, s.copyFromTopicQos(s.getDefaultDataReaderQos(), tq));

            Msg m = new Msg();
            Iterator<Sample<Msg>> iter = null;

            for (i = n; i > 0; i--) {
                m.userID = i;
                w.write(m);
            }

            iter = r.read(1);
            assertTrue("Expected to read a sample, but read returned null", iter != null);
            m = iter.next().getData();
            assertTrue("Expected to read sample with key 3, received sample with key " + m.userID + " instead", m.userID == 3);
            assertFalse("Read more samples than expected; requested to read a maximum of one sample", iter.hasNext());

            iter = r.read();
            assertTrue("Expected to read two samples, but read returned null", iter != null);
            m = iter.next().getData();
            assertTrue("Expected to read sample with key 2, received sample with key " + m.userID + " instead", m.userID == 2);
            m = iter.next().getData();
            assertTrue("Expected to read sample with key 1, received sample with key " + m.userID + " instead", m.userID == 1);
            assertFalse("Read more samples than expected; expected to read a maximum of two samples", iter.hasNext());

            iter = r.read();
            for (i = 0; iter.hasNext(); i++) {
                m = iter.next().getData();
            }

            assertTrue("Expected to read three samples, but received " + i + " instead", i == n);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testPresentationGroupReadOrdered", e, false));
        }
    }


    /**
     * Test if samples are read in order with ordered access enabled at group
     * level.
     */
    @Test
    public void testPresentationGroupReadOrdered() {
        TopicQos tq = null;
        List<Topic<Msg>> t = new ArrayList<Topic<Msg>>();
        PublisherQos pq = null;
        Publisher p = null;
        List<DataWriter<Msg>> w = new ArrayList<DataWriter<Msg>>();
        SubscriberQos sq = null;
        Subscriber s = null;
        Collection<DataReader<?>> rl = null;
        Iterator<DataReader<?>> ri = null;
        List<DataReader<Msg>> r = new ArrayList<DataReader<Msg>>();
        int i, j, n = 3;

        PolicyFactory policyFactory = env.getSPI().getPolicyFactory();

        try {
            pq = participant.getDefaultPublisherQos().withPolicies(
                policyFactory.Presentation().withAccessScope(AccessScopeKind.GROUP).withOrderedAccess(true),
                policyFactory.EntityFactory().withAutoEnableCreatedEntities(true));
            sq = participant.getDefaultSubscriberQos().withPolicies(
                policyFactory.Presentation().withAccessScope(AccessScopeKind.GROUP).withOrderedAccess(true),
                policyFactory.EntityFactory().withAutoEnableCreatedEntities(true));
            tq = participant.getDefaultTopicQos().withPolicies(
                policyFactory.DestinationOrder().withKind(DestinationOrder.Kind.BY_RECEPTION_TIMESTAMP),
                policyFactory.Reliability().withKind(Reliability.Kind.RELIABLE));

            p = participant.createPublisher(pq);
            assertTrue("Check for valid Publisher object", p instanceof Publisher);
            s = participant.createSubscriber(sq);
            assertTrue("Check for valid Subscriber object", s instanceof Subscriber);
            t.add(0, participant.createTopic("orderedAccessTopic", Msg.class));
            assertTrue("Check for valid Topic object", t.get(0) instanceof Topic);
            t.add(1, participant.createTopic("orderedAccessTopic2", Msg.class));
            assertTrue("Check for valid Topic object", t.get(1) instanceof Topic);

            w.add(0, p.createDataWriter(t.get(0), p.copyFromTopicQos(p.getDefaultDataWriterQos(), tq)));
            w.add(1, p.createDataWriter(t.get(1), p.copyFromTopicQos(p.getDefaultDataWriterQos(), tq)));
            r.add(0, s.createDataReader(t.get(0), s.copyFromTopicQos(s.getDefaultDataReaderQos(), tq)));
            r.add(1, s.createDataReader(t.get(1), s.copyFromTopicQos(s.getDefaultDataReaderQos(), tq)));

            Msg m = new Msg();
            Iterator<Sample<Msg>> iter = null;

            for (i = n; i > 0; i--) {
                m.userID = i;
                w.get(i % 2).write(m);
            }

            s.beginAccess();

            DataState ds = s.createDataState().with(SampleState.NOT_READ).withAnyViewState().withAnyInstanceState();
            rl = s.getDataReaders( ds );
            assertTrue("Check number of DataReaders ("+rl.size()+") matches the number of samples " +n , rl.size() == n);

            for (i = n, ri = rl.iterator(); ri.hasNext(); i--) {
                @SuppressWarnings("unchecked")
                DataReader<Msg> rdr = (DataReader<Msg>)ri.next();
                assertTrue("Check reader is expected reader", rdr.equals(r.get(i % 2)));
                iter = rdr.select().dataState(ds).read();
                for (j = 0; iter.hasNext(); j++) {
                    m = iter.next().getData();
                    if (m != null) {
                        assertTrue("Sample received out of order; expected sample with key " + i + ", received sample with key " + m.userID, m.userID == i);
                    }
                }
                assertTrue("Read returned more than one sample; number of samples returned was " + (j + 1), j == 1);
            }

            s.endAccess();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testPresentationGroupReadOrdered", e, false));
        }
    }

    /**
     * Test method for verifying same destination order is enforced by the
     * subscriber when ordered access is configured for group scope.
     */
    @Test
    public void testGroupOrderedAccessSetInconsistentDestinationOrder() {
        TopicQos tq = null;
        Topic<Msg> t = null;
        SubscriberQos sq = null;
        Subscriber s = null;
        DataReaderQos rq = null;
        DataReader<Msg> r1 = null;
        DataReader<Msg> r2 = null;

        PolicyFactory policyFactory = env.getSPI().getPolicyFactory();

        try {
            sq = participant.getDefaultSubscriberQos().withPolicies(
                policyFactory.Presentation().withAccessScope(AccessScopeKind.GROUP).withOrderedAccess(true),
                policyFactory.EntityFactory().withAutoEnableCreatedEntities(true));
            tq = participant.getDefaultTopicQos().withPolicies(
                policyFactory.DestinationOrder().withKind(DestinationOrder.Kind.BY_RECEPTION_TIMESTAMP),
                policyFactory.Reliability().withKind(Reliability.Kind.RELIABLE));

            s = participant.createSubscriber(sq);
            assertTrue("Check for valid Subscriber object", s instanceof Subscriber);
            t = participant.createTopic("orderedAccessTopic", Msg.class);
            assertTrue("Check for valid Topic object", t instanceof Topic);

            rq = s.copyFromTopicQos(s.getDefaultDataReaderQos(), tq);
            r1 = s.createDataReader(t, rq);
            assertTrue ("Check for valid DataReader object", r1 instanceof DataReader);
            rq = rq.withPolicies(
                policyFactory.DestinationOrder().withKind(DestinationOrder.Kind.BY_SOURCE_TIMESTAMP));
            r2 = s.createDataReader(t, rq);
        } catch (PreconditionNotMetException e) {
            assertTrue ("Verify creation of second DataReader object failed " + util.printException (e), r2 == null);
        } catch (Exception e) {
            fail("Exception occured while running test: " + util.printException(e));
        }
    }

    /**
     * Test method for verifying readers are allowed different destination
     * order policies when ordered access is configured for topic scope.
     */
    @Test
    public void testTopicOrderedAccessSetDestinationOrder() {
        TopicQos tq = null;
        Topic<Msg> t = null;
        SubscriberQos sq = null;
        Subscriber s = null;
        DataReaderQos rq = null;
        DataReader<Msg> r1 = null;
        DataReader<Msg> r2 = null;

        PolicyFactory policyFactory = env.getSPI().getPolicyFactory();

        try {
            sq = participant.getDefaultSubscriberQos().withPolicies(
                policyFactory.Presentation().withAccessScope(AccessScopeKind.TOPIC).withOrderedAccess(true),
                policyFactory.EntityFactory().withAutoEnableCreatedEntities(true));
            tq = participant.getDefaultTopicQos().withPolicies(
                policyFactory.DestinationOrder().withKind(DestinationOrder.Kind.BY_RECEPTION_TIMESTAMP),
                policyFactory.Reliability().withKind(Reliability.Kind.RELIABLE));

            s = participant.createSubscriber(sq);
            assertTrue("Check for valid Subscriber object", s instanceof Subscriber);
            t = participant.createTopic("orderedAccessTopic", Msg.class);
            assertTrue("Check for valid Topic object", t instanceof Topic);

            rq = s.copyFromTopicQos(s.getDefaultDataReaderQos(), tq);
            r1 = s.createDataReader(t, rq);
            assertTrue ("Check for valid DataReader object", r1 instanceof DataReader);
            rq = rq.withPolicies(
                policyFactory.DestinationOrder().withKind(DestinationOrder.Kind.BY_SOURCE_TIMESTAMP));
            r2 = s.createDataReader(t, rq);
            assertTrue ("Check for valid DataReader object", r2 instanceof DataReader);
        } catch (Exception e) {
            fail("Exception occured while running test: " + util.printException(e));
        }
    }
}
