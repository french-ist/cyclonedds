/**
 *
 */
package unittest.sub;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.dds.core.AlreadyClosedException;
import org.omg.dds.core.Duration;
import org.omg.dds.core.InstanceHandle;
import org.omg.dds.core.PreconditionNotMetException;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.Time;
import org.omg.dds.core.policy.DataRepresentation;
import org.omg.dds.core.policy.Deadline;
import org.omg.dds.core.policy.DestinationOrder;
import org.omg.dds.core.policy.Durability;
import org.omg.dds.core.policy.History;
import org.omg.dds.core.policy.LatencyBudget;
import org.omg.dds.core.policy.Liveliness;
import org.omg.dds.core.policy.Ownership;
import org.omg.dds.core.policy.PolicyFactory;
import org.omg.dds.core.policy.QosPolicy.ForDataReader;
import org.omg.dds.core.policy.ReaderDataLifecycle;
import org.omg.dds.core.policy.Reliability;
import org.omg.dds.core.policy.ResourceLimits;
import org.omg.dds.core.policy.TimeBasedFilter;
import org.omg.dds.core.policy.TypeConsistencyEnforcement;
import org.omg.dds.core.policy.UserData;
import org.omg.dds.core.status.Status;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.domain.DomainParticipantFactory;
import org.omg.dds.pub.DataWriter;
import org.omg.dds.pub.Publisher;
import org.omg.dds.sub.DataReader;
import org.omg.dds.sub.DataReaderListener;
import org.omg.dds.sub.DataReaderQos;
import org.omg.dds.sub.Sample;
import org.omg.dds.sub.Subscriber;
import org.omg.dds.topic.Topic;
import org.opensplice.dds.core.OsplServiceEnvironment;
import org.opensplice.dds.sub.DataReaderImpl;
import org.opensplice.dds.sub.SubscriberImpl;
import org.opensplice.dds.topic.TopicDescriptionExt;

import Test.Msg;

import org.eclipse.cyclonedds.test.AbstractUtilities;

public class DataReaderopenspliceTest {

    private final static int                DOMAIN_ID   = 123;
    private static ServiceEnvironment       env;
    private static DomainParticipantFactory dpf;
    private static AbstractUtilities        util        = AbstractUtilities.getInstance(DataReaderopenspliceTest.class);
    private final static Properties         prop        = new Properties();
    private static DomainParticipant        participant = null;
    private static Publisher                publisher   = null;
    private static Subscriber               subscriber  = null;
    private static Topic<Msg>               topic       = null;
    private static DataReader<Msg>          dataReader  = null;
    private static String                   topicName   = "DataReaderopenspliceTest";

    @BeforeClass
    public static void init() {
        try {
            String serviceEnvironment = System.getProperty("JAVA5PSM_SERVICE_ENV");
            assertTrue("Check for valid ServiceEnvironment string", !serviceEnvironment.equals(""));
            System.setProperty(ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY, serviceEnvironment);
            env = ServiceEnvironment.createInstance(DataReaderopenspliceTest.class.getClassLoader());
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
            fail("Exception occured while initiating the DataReaderopenspliceTest class: " + util.printException(e));
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
     * Test method for
     * {@link org.omg.dds.sub.DataReader#getKeyValue(org.omg.dds.core.InstanceHandle)}
     * .
     */
    @Test
    public void testGetKeyValueInstanceHandle() {
        checkValidEntities();
        Publisher pub = null;
        DataWriter<Msg> dw = null;
        Iterator<Sample<Msg>> takeResult = null;
        Msg message = new Msg(15, "testGetKeyValueInstanceHandle");
        Msg result = null;
        try {
            pub = participant.createPublisher();
            dw = pub.createDataWriter(topic);
            dw.write(message);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetKeyValueInstanceHandle", e, false));
        }
        try {
            takeResult = dataReader.take();
            InstanceHandle ih = takeResult.next().getInstanceHandle();
            while (takeResult.hasNext() && ih.isNil()) {
                ih = takeResult.next().getInstanceHandle();
            }
            result = dataReader.getKeyValue(ih);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetKeyValueInstanceHandle", e, false));
        }
        assertTrue("Check if for valid Msg object failed", util.objectCheck("testGetKeyValueInstanceHandle", result));
        try {
            dw.close();
            pub.close();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetKeyValueInstanceHandle", e, false));
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.sub.DataReader#getKeyValue(org.omg.dds.core.InstanceHandle)}
     * .
     */
    @Test
    public void testGetKeyValueInstanceHandleInvalid() {
        checkValidEntities();
        Msg message = new Msg(16, "testGetKeyValueInstanceHandleInvalid");
        Msg result = null;
        try {
            result = dataReader.getKeyValue(dataReader.lookupInstance(message));
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testGetKeyValueInstanceHandleInvalid", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for invalid Msg object failed", result == null);
    }

    /**
     * Test method for
     * {@link org.omg.dds.pub.DataReader#getKeyValue(org.omg.dds.core.InstanceHandle)}
     * .
     */
    @Test
    public void testGetKeyValueInstanceHandleNull() {
        checkValidEntities();
        Msg result = null;
        try {
            result = dataReader.getKeyValue(null);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testGetKeyValueInstanceHandleNull", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for invalid Msg object failed", result == null);
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.sub.DataReaderImpl#DataReaderImpl} .
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testDataReaderImplQosNull() {
        checkValidEntities();
        DataReaderImpl<Msg> result = null;
        DataReaderListener<Msg> listener = null;
        Collection<Class<? extends Status>> status = null;
        DataReaderQos drq = null;
        try {
            result = new DataReaderImpl<Msg>(((OsplServiceEnvironment) env), (SubscriberImpl) subscriber, (TopicDescriptionExt<Msg>) topic, drq, listener, status);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testDataReaderImplQosNull", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for invalid Msg object failed", result == null);
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.sub.DataReaderImpl#DataReaderImpl} .
     */
    @Test
    public void testDataReaderImplTopicNull() {
        checkValidEntities();
        DataReaderImpl<Msg> result = null;
        DataReaderListener<Msg> listener = null;
        Collection<Class<? extends Status>> status = null;
        DataReaderQos drq = subscriber.getDefaultDataReaderQos();
        try {
            result = new DataReaderImpl<Msg>(((OsplServiceEnvironment) env), (SubscriberImpl) subscriber, null, drq, listener, status);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testDataReaderImplQosNull", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for invalid Msg object failed", result == null);
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.sub.DataReaderImpl#DataReaderImpl} .
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testDataReaderImplQosInvalid() {
        checkValidEntities();
        DataReaderImpl<Msg> result = null;
        DataReaderListener<Msg> listener = null;
        Collection<Class<? extends Status>> status = null;
        @SuppressWarnings("serial")
        DataReaderQos drq = new DataReaderQos() {

            @Override
            public ServiceEnvironment getEnvironment() {
                return null;
            }

            @Override
            public Collection<ForDataReader> values() {
                return null;
            }

            @Override
            public int size() {
                return 0;
            }

            @Override
            public ForDataReader remove(Object key) {
                return null;
            }

            @Override
            public void putAll(Map<? extends Class<? extends ForDataReader>, ? extends ForDataReader> m) {
            }

            @Override
            public ForDataReader put(Class<? extends ForDataReader> key, ForDataReader value) {
                return null;
            }

            @Override
            public Set<Class<? extends ForDataReader>> keySet() {
                return null;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public ForDataReader get(Object key) {
                return null;
            }

            @Override
            public Set<Entry<Class<? extends ForDataReader>, ForDataReader>> entrySet() {
                return null;
            }

            @Override
            public boolean containsValue(Object value) {
                return false;
            }

            @Override
            public boolean containsKey(Object key) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public PolicyFactory getPolicyFactory() {
                return null;
            }

            @Override
            public <POLICY extends ForDataReader> POLICY get(Class<POLICY> id) {
                return null;
            }

            @Override
            public DataReaderQos withPolicy(ForDataReader policy) {
                return null;
            }

            @Override
            public DataReaderQos withPolicies(ForDataReader... policy) {
                return null;
            }

            @Override
            public UserData getUserData() {
                return null;
            }

            @Override
            public TypeConsistencyEnforcement getTypeConsistency() {
                return null;
            }

            @Override
            public TimeBasedFilter getTimeBasedFilter() {
                return null;
            }

            @Override
            public ResourceLimits getResourceLimits() {
                return null;
            }

            @Override
            public DataRepresentation getRepresentation() {
                return null;
            }

            @Override
            public Reliability getReliability() {
                return null;
            }

            @Override
            public ReaderDataLifecycle getReaderDataLifecycle() {
                return null;
            }

            @Override
            public Ownership getOwnership() {
                return null;
            }

            @Override
            public Liveliness getLiveliness() {
                return null;
            }

            @Override
            public LatencyBudget getLatencyBudget() {
                return null;
            }

            @Override
            public History getHistory() {
                return null;
            }

            @Override
            public Durability getDurability() {
                return null;
            }

            @Override
            public DestinationOrder getDestinationOrder() {
                return null;
            }

            @Override
            public Deadline getDeadline() {
                return null;
            }
        };

        try {
            result = new DataReaderImpl<Msg>(((OsplServiceEnvironment) env), (SubscriberImpl) subscriber, (TopicDescriptionExt<Msg>) topic, drq, listener, status);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testDataReaderImplQosNull", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for invalid Msg object failed", result == null);
    }

    /**
     * Test method for
     * {@link org.omg.dds.core.Entity#setQos(org.omg.dds.core.EntityQos)}.
     */
    @Test
    public void testSetQosIllegal() {
        checkValidEntities();
        boolean exceptionOccured = false;
        @SuppressWarnings("serial")
        DataReaderQos drq = new DataReaderQos() {

            @Override
            public ServiceEnvironment getEnvironment() {

                return null;
            }

            @Override
            public Collection<ForDataReader> values() {

                return null;
            }

            @Override
            public int size() {

                return 0;
            }

            @Override
            public ForDataReader remove(Object key) {

                return null;
            }

            @Override
            public void putAll(Map<? extends Class<? extends ForDataReader>, ? extends ForDataReader> m) {


            }

            @Override
            public ForDataReader put(Class<? extends ForDataReader> key, ForDataReader value) {

                return null;
            }

            @Override
            public Set<Class<? extends ForDataReader>> keySet() {

                return null;
            }

            @Override
            public boolean isEmpty() {

                return false;
            }

            @Override
            public ForDataReader get(Object key) {

                return null;
            }

            @Override
            public Set<Entry<Class<? extends ForDataReader>, ForDataReader>> entrySet() {

                return null;
            }

            @Override
            public boolean containsValue(Object value) {

                return false;
            }

            @Override
            public boolean containsKey(Object key) {

                return false;
            }

            @Override
            public void clear() {


            }

            @Override
            public PolicyFactory getPolicyFactory() {

                return null;
            }

            @Override
            public <POLICY extends ForDataReader> POLICY get(Class<POLICY> id) {

                return null;
            }

            @Override
            public DataReaderQos withPolicy(ForDataReader policy) {

                return null;
            }

            @Override
            public DataReaderQos withPolicies(ForDataReader... policy) {

                return null;
            }

            @Override
            public UserData getUserData() {

                return null;
            }

            @Override
            public TypeConsistencyEnforcement getTypeConsistency() {

                return null;
            }

            @Override
            public TimeBasedFilter getTimeBasedFilter() {

                return null;
            }

            @Override
            public ResourceLimits getResourceLimits() {

                return null;
            }

            @Override
            public DataRepresentation getRepresentation() {

                return null;
            }

            @Override
            public Reliability getReliability() {

                return null;
            }

            @Override
            public ReaderDataLifecycle getReaderDataLifecycle() {

                return null;
            }

            @Override
            public Ownership getOwnership() {

                return null;
            }

            @Override
            public Liveliness getLiveliness() {

                return null;
            }

            @Override
            public LatencyBudget getLatencyBudget() {

                return null;
            }

            @Override
            public History getHistory() {

                return null;
            }

            @Override
            public Durability getDurability() {

                return null;
            }

            @Override
            public DestinationOrder getDestinationOrder() {

                return null;
            }

            @Override
            public Deadline getDeadline() {

                return null;
            }
        };
        try {
            dataReader.setQos(drq);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testSetQosNull", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException occured failed", exceptionOccured);

    }

    @Test
    public void testWaitForHistoricalDataConditionDuration() {
        checkValidEntities();
        Duration timeout = Duration.newDuration(2, TimeUnit.SECONDS, env);
        try {
            org.opensplice.dds.sub.DataReader<Msg> osplDataReader = (org.opensplice.dds.sub.DataReader<Msg>) dataReader;
            osplDataReader.waitForHistoricalData((String) null, null, timeout);
        } catch (Exception e) {
            assertTrue(
                    "No exception expected but got: "
                            + util.printException(e), util.exceptionCheck(
                            "testWaitForHistoricalDataConditionDuration", e,
                            e instanceof Exception));
        }
    }

    @Test
    public void testWaitForHistoricalDataConditionDuration2() {
        checkValidEntities();

        try {
            org.opensplice.dds.sub.DataReader<Msg> osplDataReader = (org.opensplice.dds.sub.DataReader<Msg>) dataReader;
            osplDataReader.waitForHistoricalData((String) null, null, 2,
                    TimeUnit.SECONDS);
        } catch (Exception e) {
            assertTrue(
                    "No exception expected but got: "
                            + util.printException(e), util.exceptionCheck(
                            "testWaitForHistoricalDataConditionDuration2", e,
                            e instanceof Exception));
        }
    }

    @Test
    public void testWaitForHistoricalDataConditionResourceLimits() {
        checkValidEntities();
        Duration timeout = Duration.newDuration(2, TimeUnit.SECONDS, env);
        try {
            org.opensplice.dds.sub.DataReader<Msg> osplDataReader = (org.opensplice.dds.sub.DataReader<Msg>) dataReader;
            osplDataReader.waitForHistoricalData(PolicyFactory
                    .getPolicyFactory(dataReader.getEnvironment())
                    .ResourceLimits(), timeout);
        } catch (Exception e) {
            assertTrue(
                    "No exception expected but got: "
                            + util.printException(e), util.exceptionCheck(
                            "testWaitForHistoricalDataConditionResourceLimits",
                            e,
                            e instanceof Exception));
        }
    }

    @Test
    public void testWaitForHistoricalDataConditionResourceLimits2() {
        checkValidEntities();

        try {
            org.opensplice.dds.sub.DataReader<Msg> osplDataReader = (org.opensplice.dds.sub.DataReader<Msg>) dataReader;
            osplDataReader.waitForHistoricalData(PolicyFactory
                    .getPolicyFactory(dataReader.getEnvironment())
                    .ResourceLimits(), 2, TimeUnit.SECONDS);
        } catch (Exception e) {
            assertTrue(
                    "No exception expected but got: "
                            + util.printException(e), util.exceptionCheck(
                            "testWaitForHistoricalDataConditionResourceLimits2",
                            e,
                            e instanceof TimeoutException));
        }
    }

    @Test
    public void testWaitForHistoricalDataConditionBadResourceLimits() {
        checkValidEntities();
        boolean exceptionOccured = false;
        Duration timeout = Duration.newDuration(2, TimeUnit.SECONDS, env);
        try {
            org.opensplice.dds.sub.DataReader<Msg> osplDataReader = (org.opensplice.dds.sub.DataReader<Msg>) subscriber.createDataReader(topic);
            assertTrue("Check for valid DataReader object failed", osplDataReader instanceof DataReader);
            osplDataReader.waitForHistoricalData(null, timeout);
        } catch (Exception e) {
            assertTrue(
                    "IllegalArgumentException expected but got: "
                            + util.printException(e),
                    util.exceptionCheck(
                            "testWaitForHistoricalDataConditionBadResourceLimits",
                            e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("Check IllegalArgumentException occured failed",
                exceptionOccured);
    }

    @Test
    public void testWaitForHistoricalDataConditionBadResourceLimits2() {
        checkValidEntities();
        boolean exceptionOccured = false;

        try {
            org.opensplice.dds.sub.DataReader<Msg> osplDataReader = (org.opensplice.dds.sub.DataReader<Msg>) subscriber.createDataReader(topic);
            assertTrue("Check for valid DataReader object failed", osplDataReader instanceof DataReader);
            osplDataReader.waitForHistoricalData(null, 2, TimeUnit.SECONDS);
        } catch (Exception e) {
            assertTrue(
                    "IllegalArgumentException expected but got: "
                            + util.printException(e),
                    util.exceptionCheck(
                            "testWaitForHistoricalDataConditionBadResourceLimits2",
                            e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("Check IllegalArgumentException occured failed",
                exceptionOccured);
    }

    @Test
    public void testWaitForHistoricalDataConditionTimeRange() {
        checkValidEntities();
        Duration timeout = Duration.newDuration(2, TimeUnit.SECONDS, env);
        try {
            org.opensplice.dds.sub.DataReader<Msg> osplDataReader = (org.opensplice.dds.sub.DataReader<Msg>) subscriber.createDataReader(topic);
            assertTrue("Check for valid DataReader object failed", osplDataReader instanceof DataReader);
            osplDataReader.waitForHistoricalData(participant.getCurrentTime(),
                    Time.invalidTime(env), timeout);
        } catch (Exception e) {
            assertTrue(
                    "No exception expected but got: "
                            + util.printException(e), util.exceptionCheck(
                            "testWaitForHistoricalDataConditionTimeRange", e,
                            e instanceof Exception));
        }
    }

    @Test
    public void testWaitForHistoricalDataConditionTimeRange2() {
        checkValidEntities();

        try {
            org.opensplice.dds.sub.DataReader<Msg> osplDataReader = (org.opensplice.dds.sub.DataReader<Msg>) subscriber.createDataReader(topic);
            assertTrue("Check for valid DataReader object failed", osplDataReader instanceof DataReader);
            osplDataReader.waitForHistoricalData(participant.getCurrentTime(),
                    Time.invalidTime(env), 2, TimeUnit.SECONDS);
        } catch (Exception e) {
            assertTrue(
                    "No exception expected but got: "
                            + util.printException(e), util.exceptionCheck(
                            "testWaitForHistoricalDataConditionTimeRange2", e,
                            e instanceof Exception));
        }
    }

    @Test
    public void testWaitForHistoricalDataConditionFilter() {
        checkValidEntities();
        Duration timeout = Duration.newDuration(2, TimeUnit.SECONDS, env);
        try {
            List<String> params = new ArrayList<String>(1);
            params.add("10");
            org.opensplice.dds.sub.DataReader<Msg> osplDataReader = (org.opensplice.dds.sub.DataReader<Msg>) subscriber.createDataReader(topic);
            assertTrue("Check for valid DataReader object failed", osplDataReader instanceof DataReader);
            osplDataReader
                    .waitForHistoricalData("userID > %0", params, timeout);
        } catch (Exception e) {
            assertTrue(
                    "No exception expected but got: "
                            + util.printException(e), util.exceptionCheck(
                            "testWaitForHistoricalDataConditionFilter", e,
                            e instanceof Exception));
        }
    }

    @Test
    public void testWaitForHistoricalDataConditionFilter2() {
        checkValidEntities();

        try {
            List<String> params = new ArrayList<String>(1);
            params.add("10");
            org.opensplice.dds.sub.DataReader<Msg> osplDataReader = (org.opensplice.dds.sub.DataReader<Msg>) subscriber.createDataReader(topic);
            assertTrue("Check for valid DataReader object failed", osplDataReader instanceof DataReader);
            osplDataReader.waitForHistoricalData("userID > %0", params, 2,
                    TimeUnit.SECONDS);
        } catch (Exception e) {
            assertTrue(
                    "No exception expected but got: "
                            + util.printException(e), util.exceptionCheck(
                            "testWaitForHistoricalDataConditionFilter2", e,
                            e instanceof Exception));
        }
    }

    @Test
    public void testWaitForHistoricalDataConditionFilterAndTimeRange() {
        checkValidEntities();
        Duration timeout = Duration.newDuration(2, TimeUnit.SECONDS, env);
        try {
            List<String> params = new ArrayList<String>(1);
            params.add("10");
            org.opensplice.dds.sub.DataReader<Msg> osplDataReader = (org.opensplice.dds.sub.DataReader<Msg>) subscriber.createDataReader(topic);
            assertTrue("Check for valid DataReader object failed", osplDataReader instanceof DataReader);
            osplDataReader.waitForHistoricalData("userID > %0", params,
                    Time.invalidTime(env), Time.invalidTime(env), timeout);
        } catch (Exception e) {
            assertTrue(
                    "No exception expected but got: "
                            + util.printException(e), util.exceptionCheck(
                            "testWaitForHistoricalDataConditionDuration", e,
                            e instanceof Exception));
        }
    }

    @Test
    public void testWaitForHistoricalDataConditionFilterAndTimeRange2() {
        checkValidEntities();

        try {
            List<String> params = new ArrayList<String>(1);
            params.add("10");
            org.opensplice.dds.sub.DataReader<Msg> osplDataReader = (org.opensplice.dds.sub.DataReader<Msg>) subscriber.createDataReader(topic);
            assertTrue("Check for valid DataReader object failed", osplDataReader instanceof DataReader);
            osplDataReader.waitForHistoricalData("userID > %0", params,
                    Time.invalidTime(env), Time.invalidTime(env), 2,
                    TimeUnit.SECONDS);
        } catch (Exception e) {
            assertTrue(
                    "No exception expected but got: "
                            + util.printException(e), util.exceptionCheck(
                            "testWaitForHistoricalDataConditionDuration", e,
                            e instanceof Exception));
        }
    }

    @Test
    public void testWaitForHistoricalDataConditionFilterTimeRangeResourceLimits() {
        checkValidEntities();
        Duration timeout = Duration.newDuration(2, TimeUnit.SECONDS, env);
        try {
            List<String> params = new ArrayList<String>(1);
            params.add("10");
            org.opensplice.dds.sub.DataReader<Msg> osplDataReader = (org.opensplice.dds.sub.DataReader<Msg>) subscriber.createDataReader(topic);
            assertTrue("Check for valid DataReader object failed", osplDataReader instanceof DataReader);
            osplDataReader.waitForHistoricalData("userID > %0", params, Time
                    .invalidTime(env), Time.invalidTime(env), PolicyFactory
                    .getPolicyFactory(env).ResourceLimits(), timeout);
        } catch (Exception e) {
            assertTrue(
                    "No exception expected but got: "
                            + util.printException(e), util.exceptionCheck(
                            "testWaitForHistoricalDataConditionFilterTimeRangeResourceLimits",
                            e,
                            e instanceof Exception));
        }
    }

    @Test
    public void testWaitForHistoricalDataConditionFilterTimeRangeResourceLimits2() {
        checkValidEntities();
        boolean exceptionOccured = false;
        try {
            List<String> params = new ArrayList<String>(1);
            params.add("10");
            org.opensplice.dds.sub.DataReader<Msg> osplDataReader = (org.opensplice.dds.sub.DataReader<Msg>) subscriber.createDataReader(topic);
            assertTrue("Check for valid DataReader object failed", osplDataReader instanceof DataReader);
            osplDataReader.waitForHistoricalData("userID > %0", params, Time
                    .invalidTime(env), Time.invalidTime(env), PolicyFactory
                    .getPolicyFactory(env).ResourceLimits(), 2,
                    TimeUnit.SECONDS);
        } catch (Exception e) {
            assertTrue(
                    "No exception expected but got: "
                            + util.printException(e), util.exceptionCheck(
                            "testWaitForHistoricalDataConditionFilterTimeRangeResourceLimits2",
                            e,
                            e instanceof Exception));
        }
    }
}
