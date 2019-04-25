/**
 *
 */
package unittest.sub;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.dds.core.AlreadyClosedException;
import org.omg.dds.core.Duration;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.domain.DomainParticipantFactory;
import org.omg.dds.sub.DataReader;
import org.omg.dds.sub.Subscriber;
import org.omg.dds.topic.Topic;
import org.opensplice.dds.core.OsplServiceEnvironment;
import org.opensplice.dds.core.policy.ReaderDataLifecycleImpl;
import org.opensplice.dds.core.policy.ReaderLifespan;
import org.opensplice.dds.core.policy.ReaderLifespanImpl;
import org.opensplice.dds.core.policy.Share;
import org.opensplice.dds.core.policy.ShareImpl;
import org.opensplice.dds.core.policy.SubscriptionKeys;
import org.opensplice.dds.core.policy.SubscriptionKeysImpl;
import org.opensplice.dds.core.policy.TimeBasedFilterImpl;
import org.opensplice.dds.core.policy.UserDataImpl;
import org.opensplice.dds.sub.DataReaderImpl;
import org.opensplice.dds.sub.DataReaderQosImpl;

import Test.Msg;

import org.eclipse.cyclonedds.test.AbstractUtilities;

public class DataReaderQosopenspliceTest {

    private final static int                DOMAIN_ID   = 123;
    private static ServiceEnvironment       env;
    private static DomainParticipantFactory dpf;
    private static AbstractUtilities        util        = AbstractUtilities.getInstance(DataReaderQosopenspliceTest.class);
    private final static Properties         prop        = new Properties();
    private static DomainParticipant        participant = null;
    private static Subscriber               subscriber  = null;
    private static Topic<Msg>               topic       = null;
    private static DataReaderImpl<Msg>      dataReader  = null;
    private static String                   topicName   = "DataReaderQosopenspliceTest";
    private static DataReaderQosImpl        drq         = null;

    @BeforeClass
    public static void init() {
        try {
            String serviceEnvironment = System.getProperty("JAVA5PSM_SERVICE_ENV");
            assertTrue("Check for valid ServiceEnvironment string", !serviceEnvironment.equals(""));
            System.setProperty(ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY, serviceEnvironment);
            env = ServiceEnvironment.createInstance(DataReaderQosopenspliceTest.class.getClassLoader());
            assertTrue("Check for valid ServiceEnvironment object failed", env instanceof ServiceEnvironment);
            dpf = DomainParticipantFactory.getInstance(env);
            assertTrue("Check for valid DomainParticipantFactory object failed", dpf instanceof DomainParticipantFactory);
            prop.setProperty("domainid", new Integer(DOMAIN_ID).toString());
            assertTrue("Check is deamon is correctly started", util.beforeClass(prop));
            participant = dpf.createParticipant(DOMAIN_ID);
            assertTrue("Check for valid DomainParticipant object failed", participant instanceof DomainParticipant);
            assertTrue("Check for valid DomainParticipant with id " + DOMAIN_ID, participant.getDomainId() == DOMAIN_ID);
            subscriber = participant.createSubscriber();
            assertTrue("Check for valid Publisher object failed", subscriber instanceof Subscriber);
            topic = participant.createTopic(topicName, Msg.class);
            assertTrue("Check for valid Topic object failed", topic instanceof Topic);
            dataReader = (DataReaderImpl<Msg>) subscriber.createDataReader(topic);
            assertTrue("Check for valid DataWriter object failed", dataReader instanceof DataReader);
            drq = (DataReaderQosImpl) dataReader.getQos();
            Share sq = new ShareImpl((OsplServiceEnvironment) env, "Test");
            SubscriptionKeys sk = new SubscriptionKeysImpl((OsplServiceEnvironment) env, new HashSet<String>());
            ReaderLifespan rl = new ReaderLifespanImpl((OsplServiceEnvironment) env, Duration.newDuration(5, TimeUnit.SECONDS, env));
            drq = (DataReaderQosImpl) drq.withPolicies(sq, sk, rl);
            assertTrue("Check for valid DataWriterQos object failed", drq instanceof DataReaderQosImpl);
        } catch (Exception e) {
            fail("Exception occured while initiating the DataReaderQosopenspliceTest class: " + util.printException(e));
        }

    }

    private void checkValidEntities() {
        assertTrue("Check for valid DomainParticipant object failed", participant instanceof DomainParticipant);
        assertTrue("Check for valid Subscriber object failed", subscriber instanceof Subscriber);
        assertTrue("Check for valid Topic object failed", topic instanceof Topic);
        assertTrue("Check for valid DataReader object failed", dataReader instanceof DataReaderImpl);
        assertTrue("Check for valid DataWriterQos object failed", drq instanceof DataReaderQosImpl);
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
     * {@link org.opensplice.sub.dds.DataReaderQosImpl#getReaderLifespan()}.
     */
    //@tTest
    public void testGetReaderLifespan() {
        checkValidEntities();
        ReaderLifespanImpl result = null;
        try {
            result = (ReaderLifespanImpl) drq.getReaderLifespan();
            assertTrue("Check for valid ReaderLifespan object failed", result != null);
            result = (ReaderLifespanImpl) result.withDuration(Duration.newDuration(5, TimeUnit.SECONDS, env));
            assertTrue("Check for valid  ReaderLifespan object", result != null);
            Duration d = result.getDuration();
            assertTrue("Check for valid Duration object", d.getDuration(TimeUnit.SECONDS) == 5);
            result = (ReaderLifespanImpl) result.withDuration(3, TimeUnit.SECONDS);
            assertTrue("Check for valid  ReaderLifespan object", result != null);
            d = result.getDuration();
            assertTrue("Check for valid Duration object", d.getDuration(TimeUnit.SECONDS) == 3);
            assertTrue("Check for valid ReaderLifespan PolicyClass object", result.getPolicyClass() != null);
            assertTrue("Check for valid ServiceEnvironment object failed", result.getEnvironment() != null);
        } catch (Exception ex) {
            fail("Failed to get the ReaderLifespan qos from the DataReaderQos");
        }
        assertTrue("Check for valid ReaderLifespan object failed", result != null);
    }

    /**
     * Test method for
     * {@link org.opensplice.sub.dds.DataReaderQosImpl#getShare()}.
     */
    //@tTest
    public void testGetShare() {
        checkValidEntities();
        ShareImpl result = null;
        try {
            result = (ShareImpl) drq.getShare();
            assertTrue("Check for valid Share object failed", result != null);
            result = (ShareImpl) result.withName("Foo");
            assertTrue("Check for valid  Share object", result != null);
            String name = result.getName();
            assertTrue("Check for valid name value", name.equals("Foo"));
            assertTrue("Check for valid ServiceEnvironment object failed", result.getEnvironment() != null);
            assertTrue("Check for valid Share PolicyClass object", result.getPolicyClass() != null);
        } catch (Exception ex) {
            fail("Failed to get the Share qos from the DataReaderQos" + util.printException(ex));
        }
        assertTrue("Check for valid Share object failed", result != null);
    }

    /**
     * Test method for
     * {@link org.opensplice.sub.dds.DataReaderQosImpl#getSubscriptionKeys()}.
     */
    //@tTest
    public void testGetSubscriptionKeys() {
        checkValidEntities();
        SubscriptionKeysImpl result = null;
        try {
            result = (SubscriptionKeysImpl) drq.getSubscriptionKeys();
            assertTrue("Check for valid SubscriptionKeys object failed", result != null);
            result = (SubscriptionKeysImpl) result.withKey("Foo");
            assertTrue("Check for valid SubscriptionKeys object", result != null);
            Set<String> name = result.getKey();
            assertTrue("Check for valid Key value", name.contains("Foo"));
            assertTrue("Check for valid Key Set size", name.size() == 1);

            result = (SubscriptionKeysImpl) result.withKey("One", "Two");
            assertTrue("Check for valid SubscriptionKeys object", result != null);
            name = result.getKey();
            assertTrue("Check for valid Key value", name.contains("One"));
            assertTrue("Check for valid Key value", name.contains("Two"));
            assertTrue("Check for valid Key Set size", name.size() == 2);

            HashSet<String> keys = new HashSet<String>();
            keys.add("Three");
            result = (SubscriptionKeysImpl) result.withKey(keys);
            assertTrue("Check for valid SubscriptionKeys object", result != null);
            name = result.getKey();
            assertTrue("Check for valid Key value", name.contains("Three"));
            assertTrue("Check for valid Key Set size", name.size() == 1);

            assertTrue("Check for valid ServiceEnvironment object failed", result.getEnvironment() != null);
            assertTrue("Check for valid SubscriptionKeys PolicyClass object", result.getPolicyClass() != null);
        } catch (Exception ex) {
            fail("Failed to get the SubscriptionKeys qos from the DataReaderQos");
        }
        assertTrue("Check for valid SubscriptionKeys object failed", result != null);
    }




    /**
     * Test method for
     * {@link org.opensplice.sub.dds.DataReaderQosImpl#getUserData()}.
     */
    //@tTest
    public void testGetUserData() {
        checkValidEntities();
        UserDataImpl result = null;
        try {
            result = (UserDataImpl) drq.getUserData();
            assertTrue("Check for valid UserData PolicyClass object", result.getPolicyClass() != null);
        } catch (Exception ex) {
            fail("Failed to get the UserData qos from the DataReaderQos");
        }
        assertTrue("Check for valid UserData object failed", result != null);
    }

    /**
     * Test method for
     * {@link org.opensplice.sub.dds.DataReaderQosImpl#getTimeBasedFilter()}.
     */
    //@tTest
    public void testGetTimeBasedFilter() {
        checkValidEntities();
        TimeBasedFilterImpl result = null;
        try {
            result = (TimeBasedFilterImpl) drq.getTimeBasedFilter();
            assertTrue("Check for valid TimeBasedFilter PolicyClass object", result.getPolicyClass() != null);
        } catch (Exception ex) {
            fail("Failed to get the TimeBasedFilter qos from the DataReaderQos");
        }
        assertTrue("Check for valid TimeBasedFilter object failed", result != null);
    }

    /**
     * Test method for
     * {@link org.opensplice.sub.dds.DataReaderQosImpl#getReaderDataLifecycle()}
     * .
     */
    //@tTest
    public void testGetReaderDataLifecycle() {
        checkValidEntities();
        ReaderDataLifecycleImpl result = null;
        try {
            result = (ReaderDataLifecycleImpl) drq.getReaderDataLifecycle();
            assertTrue("Check for valid ReaderDataLifecycle object failed", result != null);
            result = (ReaderDataLifecycleImpl) result.withAutoPurgeDisposeAll();
            assertTrue("Check for valid ReaderDataLifecycle object", result != null);
            assertTrue("Check for valid AutoPurgeDisposeAll value", result.getAutoPurgeDisposeAll());

            result = (ReaderDataLifecycleImpl) result.withInvalidSampleInvisibility(org.opensplice.dds.core.policy.ReaderDataLifecycle.Kind.valueOf("NONE"));
            assertTrue("Check for valid ReaderDataLifecycle object", result != null);
            assertTrue("Check for valid ReaderDataLifecycle kind", result.getInvalidSampleInvisibility() == org.opensplice.dds.core.policy.ReaderDataLifecycle.Kind.NONE);
            assertTrue("Check for valid ReaderDataLifecycle PolicyClass object", result.getPolicyClass() != null);
        } catch (Exception ex) {
            fail("Failed to get the ReaderDataLifecycle qos from the DataReaderQos");
        }
        assertTrue("Check for valid ReaderDataLifecycle object failed", result != null);
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.sub.DataReaderQosImpl#convert()} .
     */
    //@tTest
    public void testConvert() {
        checkValidEntities();
        DataReaderQosImpl result = null;
        try {
            result = DataReaderQosImpl.convert((OsplServiceEnvironment) env, null);
        } catch (Exception e) {
            assertTrue("Expected IllegalArgumentException but got: " + util.printException(e), util.exceptionCheck("testConvert", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for invalid Selector object", result == null);
    }
}
