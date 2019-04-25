package unittest.sub;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.dds.core.AlreadyClosedException;
import org.omg.dds.core.Duration;
import org.omg.dds.core.InstanceHandle;
import org.omg.dds.core.PreconditionNotMetException;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.StatusCondition;
import org.omg.dds.core.policy.Presentation.AccessScopeKind;
import org.omg.dds.core.status.DataAvailableStatus;
import org.omg.dds.core.status.Status;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.domain.DomainParticipantFactory;
import org.omg.dds.sub.DataReader;
import org.omg.dds.sub.DataReaderListener;
import org.omg.dds.sub.DataReaderQos;
import org.omg.dds.sub.Subscriber;
import org.omg.dds.sub.Subscriber.DataState;
import org.omg.dds.sub.SubscriberListener;
import org.omg.dds.sub.SubscriberQos;
import org.omg.dds.topic.Topic;

import Test.Msg;

import org.eclipse.cyclonedds.test.AbstractUtilities;
import org.eclipse.cyclonedds.test.DataReaderListenerClass;
import org.eclipse.cyclonedds.test.SubscriberListenerClass;

public class SubscriberTest {

    private final static int                DOMAIN_ID   = 123;
    private static ServiceEnvironment       env;
    private static DomainParticipantFactory dpf;
    private static AbstractUtilities        util        = AbstractUtilities.getInstance(SubscriberTest.class);
    private final static Properties         prop        = new Properties();
    private static DomainParticipant        participant = null;
    private static Subscriber               subscriber  = null;
    private static Subscriber               subscriberCoherent = null;
    private static Topic<Msg>               topic       = null;
    private static String                   topicName   = "SubscriberTest";

    @BeforeClass
    public static void init() {
        try {
            String serviceEnvironment = System.getProperty("JAVA5PSM_SERVICE_ENV");
            assertTrue("Check for valid ServiceEnvironment string", !serviceEnvironment.equals(""));
            System.setProperty(ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY, serviceEnvironment);
            env = ServiceEnvironment.createInstance(SubscriberTest.class.getClassLoader());
            assertTrue("Check for valid ServiceEnvironment object", env instanceof ServiceEnvironment);
            dpf = DomainParticipantFactory.getInstance(env);
            assertTrue("Check for valid DomainParticipantFactory object", dpf instanceof DomainParticipantFactory);
            prop.setProperty("domainid", new Integer(DOMAIN_ID).toString());
            assertTrue("Check is deamon is correctly started", util.beforeClass(prop));
            participant = dpf.createParticipant(DOMAIN_ID);
            assertTrue("Check for valid DomainParticipant object", participant instanceof DomainParticipant);
            assertTrue("Check for valid DomainParticipant with id " + DOMAIN_ID, participant.getDomainId() == DOMAIN_ID);
            subscriber = participant.createSubscriber();
            assertTrue("Check for valid Subscriber object", subscriber instanceof Subscriber);
            topic = participant.createTopic(topicName, Msg.class);
            SubscriberQos subQos = participant.getDefaultSubscriberQos();
            subQos = subQos.withPolicy(subQos.getPresentation().withAccessScope(AccessScopeKind.GROUP).withCoherentAccess(true));
            subscriberCoherent = participant.createSubscriber(subQos);
            assertTrue("Check for valid Subscriber object", subscriberCoherent instanceof Subscriber);
        } catch (Exception e) {
            fail("Exception occured while initiating the SubscriberTest class: " + util.printException(e));
        }

    }

    private void checkValidEntities() {
        assertTrue("Check for valid DomainParticipant object", participant instanceof DomainParticipant);
        assertTrue("Check for valid Subscriber object", subscriber instanceof Subscriber);
        assertTrue("Check for valid coherent Subscriber object", subscriberCoherent instanceof Subscriber);
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
     * Test method for {@link org.omg.dds.sub.Subscriber#createDataReader(org.omg.dds.topic.TopicDescription)}.
     */
    //@tTest
    public void testCreateDataReaderTopicDescriptionOfTYPE() {
        checkValidEntities();
        DataReader<Msg> dr = null;
        try {
            dr = subscriber.createDataReader(topic);
        } catch (Exception e) {
            fail("Exception occured while calling createDataReader: " + util.printException(e));
        }
        assertTrue("Check for valid DataWriter object", dr instanceof DataReader);
        if (dr != null) {
            dr.close();
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.sub.Subscriber#createDataReader(org.omg.dds.topic.TopicDescription)}
     * .
     */
    //@tTest
    public void testCreateDataReaderTopicDescriptionOfTYPENull() {
        checkValidEntities();
        DataReader<Msg> dr = null;
        try {
            dr = subscriber.createDataReader(null);
        } catch (Exception e) {
            assertTrue("Check for IllegalArgumentException but got exception:" + util.printException(e), e instanceof IllegalArgumentException);
        }
        assertTrue("Check for invalid DataWriter object", dr == null);

    }

    /**
     * Test method for
     * {@link org.omg.dds.sub.Subscriber#createDataReader(org.omg.dds.topic.TopicDescription, org.omg.dds.sub.DataReaderQos, org.omg.dds.sub.DataReaderListener, java.util.Collection)}
     * .
     */
    //@tTest
    public void testCreateDataReaderTopicDescriptionOfTYPEDataReaderQosDataReaderListenerOfTYPECollectionOfClassOfQextendsStatus() {
        checkValidEntities();
        DataReader<Msg> dr = null;
        DataReaderListener<Msg> listener = new DataReaderListenerClass();
        Collection<Class<? extends Status>> statuses = new HashSet<Class<? extends Status>>();
        statuses.add(DataAvailableStatus.class);
        DataReaderQos drq = subscriber.getDefaultDataReaderQos();
        assertTrue("Check for valid DataReaderQos object", drq instanceof DataReaderQos);
        try {
            dr = subscriber.createDataReader(topic, drq, listener, statuses);
        } catch (Exception e) {
            assertTrue("Exception occured while creating a DataReader with qos, listener and status col: " + util.printException(e),
                    util.exceptionCheck("testCreateDataReaderTopicDescriptionOfTYPEDataReaderQosDataReaderListenerOfTYPECollectionOfClassOfQextendsStatus", e, false));
        }
        assertTrue("Check for valid DataReader object", util.objectCheck("testCreateDataReaderTopicDescriptionOfTYPEDataReaderQosDataReaderListenerOfTYPECollectionOfClassOfQextendsStatus", dr));
        if (dr != null) {
            dr.close();
        }
    }

    /**
     * Test method for {@link org.omg.dds.sub.Subscriber#createDataReader(org.omg.dds.topic.TopicDescription, org.omg.dds.sub.DataReaderQos, org.omg.dds.sub.DataReaderListener, java.lang.Class<? extends org.omg.dds.core.status.Status>[])}.
     */
    @SuppressWarnings("unchecked")
    //@tTest
    public void testCreateDataReaderTopicDescriptionOfTYPEDataReaderQosDataReaderListenerOfTYPEClassOfQextendsStatusArray() {
        checkValidEntities();
        DataReader<Msg> dr = null;
        DataReaderListener<Msg> listener = null;
        Class<? extends Status> status = DataAvailableStatus.class;
        DataReaderQos drq = subscriber.getDefaultDataReaderQos();
        assertTrue("Check for valid DataReaderQos object", drq instanceof DataReaderQos);
        try {
            dr = subscriber.createDataReader(topic, drq, listener, status);
        } catch (Exception e) {
            assertTrue("Exception occured while creating a DataReader with qos, listener and status col: " + util.printException(e),
                    util.exceptionCheck("testCreateDataReaderTopicDescriptionOfTYPEDataReaderQosDataReaderListenerOfTYPEClassOfQextendsStatusArray", e, false));
        }
        assertTrue("Check for valid DataReader object", util.objectCheck("testCreateDataReaderTopicDescriptionOfTYPEDataReaderQosDataReaderListenerOfTYPEClassOfQextendsStatusArray", dr));
        if (dr != null) {
            dr.close();
        }
    }

    /**
     * Test method for {@link org.omg.dds.sub.Subscriber#createDataReader(org.omg.dds.topic.TopicDescription, org.omg.dds.sub.DataReaderQos)}.
     */
    //@tTest
    public void testCreateDataReaderTopicDescriptionOfTYPEDataReaderQos() {
        checkValidEntities();
        DataReader<Msg> dr = null;
        DataReaderQos drq = null;
        try {
            drq = subscriber.getDefaultDataReaderQos();
            assertTrue("Check for valid DataReaderQos object", drq instanceof DataReaderQos);
            dr = subscriber.createDataReader(topic, drq);
        } catch (Exception e) {
            fail("Exception occured while calling createDataReader(topic,qos): " + util.printException(e));
        }
        assertTrue("Check for valid DataReader object", dr instanceof DataReader);
        if (dr != null) {
            dr.close();
        }
    }

    /**
     * Test method for {@link org.omg.dds.sub.Subscriber#lookupDataReader(java.lang.String)}.
     */
    //@tTest
    public void testLookupDataReaderString() {
        checkValidEntities();
        DataReader<Msg> dr = null;
        DataReader<Msg> dr1 = null;
        try {
            dr = subscriber.createDataReader(topic);
        } catch (Exception e) {
            fail("Exception occured while calling createDataReader(topic): " + util.printException(e));
        }
        assertTrue("Check for valid DataReader object", dr instanceof DataReader);
        try {
            dr1 = subscriber.lookupDataReader(topicName);
        } catch (Exception e) {
            fail("Exception occured while calling lookupDataReader(string): " + util.printException(e));
        }
        assertTrue("Check for valid DataReader object", dr1 instanceof DataReader);
        if (dr != null) {
            dr.close();
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.sub.Subscriber#lookupDataReader(java.lang.String)}.
     */
    //@tTest
    public void testLookupDataReaderStringInvalid() {
        checkValidEntities();
        DataReader<Msg> dr = null;
        DataReader<Msg> dr1 = null;
        try {
            dr = subscriber.createDataReader(topic);
        } catch (Exception e) {
            fail("Exception occured while calling createDataReader(topic): " + util.printException(e));
        }
        assertTrue("Check for valid DataReader object", dr instanceof DataReader);
        try {
            dr1 = subscriber.lookupDataReader("foo");
        } catch (Exception e) {
            fail("Exception occured while calling lookupDataReader(string): " + util.printException(e));
        }
        assertTrue("Check for valid DataReader object", dr1 == null);
        if (dr != null) {
            dr.close();
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.sub.Subscriber#lookupDataReader(java.lang.String)}.
     */
    //@tTest
    public void testLookupDataReaderStringNull() {
        checkValidEntities();
        DataReader<Msg> dr = null;
        DataReader<Msg> dr1 = null;
        Topic<Msg> top = null;
        try {
            dr = subscriber.createDataReader(topic);
        } catch (Exception e) {
            fail("Exception occured while calling createDataReader(topic): " + util.printException(e));
        }
        assertTrue("Check for valid DataReader object", dr instanceof DataReader);
        try {
            dr1 = subscriber.lookupDataReader(top);
        } catch (Exception e) {
            assertTrue("Check for IllegalArgumentException but got exception:" + util.printException(e), e instanceof IllegalArgumentException);
        }
        assertTrue("Check for valid DataReader object", dr1 == null);
        String topicname = null;
        try {
            dr1 = subscriber.lookupDataReader(topicname);
        } catch (Exception e) {
            assertTrue("Check for IllegalArgumentException but got exception:" + util.printException(e), e instanceof IllegalArgumentException);
        }
        assertTrue("Check for valid DataReader object", dr1 == null);
        if (dr != null) {
            dr.close();
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.sub.Subscriber#lookupDataReader(org.omg.dds.topic.TopicDescription)}
     * .
     */
    //@tTest
    public void testLookupDataReaderTopicDescriptionOfTYPE() {
        checkValidEntities();
        DataReader<Msg> dr = null;
        DataReader<Msg> dr1 = null;
        try {
            dr = subscriber.createDataReader(topic);
        } catch (Exception e) {
            fail("Exception occured while calling createDataReader(topic): " + util.printException(e));
        }
        assertTrue("Check for valid DataReader object", dr instanceof DataReader);
        try {
            dr1 = subscriber.lookupDataReader(topic);
        } catch (Exception e) {
            fail("Exception occured while calling lookupDataReader(topic): " + util.printException(e));
        }
        assertTrue("Check for valid DataReader object", dr1 instanceof DataReader);
        if (dr != null) {
            dr.close();
        }
    }

    /**
     * Test method for {@link org.omg.dds.sub.Subscriber#closeContainedEntities()}.
     */
    //@tTest
    public void testCloseContainedEntities() {
        checkValidEntities();
        DataReader<Msg> dr = null;
        boolean exceptionOccured = false;
        try {
            dr = subscriber.createDataReader(topic);
        } catch (Exception e) {
            fail("Exception occured while calling createDataReader(topic): " + util.printException(e));
        }
        assertTrue("Check for valid DataReader object", dr instanceof DataReader);
        try {
            subscriber.closeContainedEntities();
        } catch (Exception e) {
            fail("Exception occured while calling closeContainedEntities: " + util.printException(e));
        }
        try {
            dr.close();
        } catch (Exception e) {
            assertTrue("Check for AlreadyClosedException but got exception:" + util.printException(e), e instanceof AlreadyClosedException);
            exceptionOccured = true;
        }
        assertTrue("Check if AlreadyClosedException occured", exceptionOccured);
    }

    /**
     * Test method for {@link org.omg.dds.sub.Subscriber#getDataReaders()}.
     */
    //@tTest
    public void testGetDataReaders() {
        checkValidEntities();
        DataReader<Msg> dr = null;
        Collection<DataReader<?>> result = null;
        try {
            dr = subscriber.createDataReader(topic);
        } catch (Exception e) {
            fail("Exception occured while calling createDataReader(topic): " + util.printException(e));
        }
        assertTrue("Check for valid DataReader object", dr instanceof DataReader);
        try {
            result = subscriber.getDataReaders();
        } catch (Exception e) {
            assertTrue("Exception occured while calling getDataReaders: " + util.printException(e), util.exceptionCheck("testGetDataReaders", e, false));
        }
        assertTrue("Check for valid DataReader object", util.objectCheck("testGetDataReaders", result));
    }

    /**
     * Test method for
     * {@link org.omg.dds.sub.Subscriber#getDataReaders(org.omg.dds.sub.Subscriber.DataState)}
     * .
     */
    //@tTest
    public void testGetDataReadersDataState() {
        checkValidEntities();
        DataReader<Msg> dr = null;
        Collection<DataReader<?>> result = null;
        DataState ds = subscriber.createDataState().withAnySampleState().withAnyViewState().withAnyInstanceState();
        try {
            dr = subscriber.createDataReader(topic);
        } catch (Exception e) {
            fail("Exception occured while calling createDataReader(topic): " + util.printException(e));
        }
        assertTrue("Check for valid DataReader object", dr instanceof DataReader);
        try {
            result = subscriber.getDataReaders(ds);
        } catch (Exception e) {
            assertTrue("Exception occured while calling getDataReaders: " + util.printException(e), util.exceptionCheck("testGetDataReaders", e, false));
        }
        assertTrue("Check for valid DataReader object", util.objectCheck("testGetDataReaders", result));
    }

    /**
     * Test method for {@link org.omg.dds.sub.Subscriber#getDataReaders(org.omg.dds.sub.Subscriber.DataState)}.
     */
    //@tTest
    public void testGetDataReadersDataStateNull() {
        checkValidEntities();
        DataReader<Msg> dr = null;
        Collection<DataReader<?>> result = null;
        DataState ds = null;
        try {
            dr = subscriber.createDataReader(topic);
        } catch (Exception e) {
            fail("Exception occured while calling createDataReader(topic): " + util.printException(e));
        }
        assertTrue("Check for valid DataReader object", dr instanceof DataReader);
        try {
            result = subscriber.getDataReaders(ds);
        } catch (Exception e) {
            assertTrue("Check for IllegalArgumentException but got exception:" + util.printException(e),
                    util.exceptionCheck("testGetDataReadersDataStateNull", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for invalid DataReader object", result == null);
    }

    /**
     * Test method for {@link org.omg.dds.sub.Subscriber#notifyDataReaders()}.
     */
    //@tTest
    public void testNotifyDataReaders() {
        checkValidEntities();
        DataReader<Msg> dr = null;
        try {
            dr = subscriber.createDataReader(topic);
        } catch (Exception e) {
            fail("Exception occured while calling createDataReader(topic): " + util.printException(e));
        }
        assertTrue("Check for valid DataReader object", dr instanceof DataReader);
        try {
            subscriber.notifyDataReaders();
        } catch (Exception e) {
            assertTrue("Exception occured while calling notifyDataReaders: " + util.printException(e), util.exceptionCheck("testNotifyDataReaders", e, false));
        }
    }

    /**
     * Test method for {@link org.omg.dds.sub.Subscriber#beginAccess()}.
     */
    //@tTest
    public void testBeginAccess() {
        checkValidEntities();
        try {
            subscriberCoherent.enable();
        } catch (Exception e) {
            assertTrue("Exception occured while calling enable()" + util.printException(e), util.exceptionCheck("testBeginAccess", e, false));
        }
        try {
            subscriberCoherent.beginAccess();
        } catch (Exception e) {
            assertTrue("Exception occured while calling beginAccess()" + util.printException(e), util.exceptionCheck("testBeginAccess", e, false));
        }
        try {
            subscriberCoherent.endAccess();
        } catch (Exception e) {
            assertTrue("Exception occured while calling endAccess()" + util.printException(e), util.exceptionCheck("testBeginAccess", e, false));
        }
    }

    /**
     * Test method for {@link org.omg.dds.sub.Subscriber#endAccess()}.
     */
    //@tTest
    public void testEndAccess() {
        checkValidEntities();
        boolean exceptionOccured = false;
        try {
            subscriberCoherent.enable();
        } catch (Exception e) {
            assertTrue("Exception occured while calling enable()" + util.printException(e), util.exceptionCheck("testEndAccess", e, false));
        }
        try {
            subscriberCoherent.endAccess();
        } catch (Exception e) {
            assertTrue("Check for PreconditionNotMetException but got exception:" + util.printException(e), util.exceptionCheck("testEndAccess", e, e instanceof PreconditionNotMetException));
            exceptionOccured = true;
        }
        assertTrue("Expected PreconditionNotMetException dit not occur", exceptionOccured);
    }

    /**
     * Test method for {@link org.omg.dds.sub.Subscriber#getDefaultDataReaderQos()}.
     */
    //@tTest
    public void testGetDefaultDataReaderQos() {
        checkValidEntities();
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        DataReaderQos drq = null;
        try {
            drq = subscriber.getDefaultDataReaderQos();
        } catch (Exception e) {
            fail("Exception occured while doing getDefaultDataReaderQos(): " + util.printException(e));
        }
        assertTrue("Check for valid DataReaderQos", drq != null);
    }

    /**
     * Test method for {@link org.omg.dds.sub.Subscriber#setDefaultDataReaderQos(org.omg.dds.sub.DataReaderQos)}.
     */
    //@tTest
    public void testSetDefaultDataReaderQos() {
        checkValidEntities();
        DataReaderQos drq = null;
        Duration deadline = Duration.newDuration(5, TimeUnit.SECONDS, env);
        try {
            drq = subscriber.getDefaultDataReaderQos();
        } catch (Exception e) {
            fail("Exception occured while calling getDefaultDataReaderQos: " + util.printException(e));
        }
        assertTrue("Check for valid DataReaderQos", drq != null);
        try {
            drq = drq.withPolicy(drq.getDeadline().withPeriod(deadline));
            subscriber.setDefaultDataReaderQos(drq);
        } catch (Exception ex) {
            fail("Failed to set the DataReaderQos");
        }
        try {
            drq = subscriber.getDefaultDataReaderQos();
            assertTrue("Check for valid DataReaderQos value", drq.getDeadline().getPeriod().getDuration(TimeUnit.SECONDS) == deadline.getDuration(TimeUnit.SECONDS));
            /* restore qos */
            drq = drq.withPolicy(drq.getDeadline().withPeriod(Duration.infiniteDuration(env)));
            subscriber.setDefaultDataReaderQos(drq);
            assertTrue("Check for valid DefaultDataReaderQos value", drq.getDeadline().getPeriod().isInfinite());
        } catch (Exception ex) {
            fail("Failed to restore the DefaultDataReaderQos");
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.sub.Subscriber#setDefaultDataReaderQos(org.omg.dds.sub.DataReaderQos)}
     * .
     */
    //@tTest
    public void testSetDefaultDataReaderQosNull() {
        checkValidEntities();
        boolean exceptionOccured = false;
        try {
            subscriber.setDefaultDataReaderQos(null);
        } catch (Exception e) {
            assertTrue("Check for IllegalArgumentException but got exception:" + util.printException(e), e instanceof IllegalArgumentException);
            exceptionOccured = true;
        }
        assertTrue("Expected IllegalArgumentException dit not occure", exceptionOccured);
    }

    /**
     * Test method for
     * {@link org.omg.dds.sub.Subscriber#copyFromTopicQos(org.omg.dds.sub.DataReaderQos, org.omg.dds.topic.TopicQos)}
     * .
     */
    //@tTest
    public void testCopyFromTopicQos() {
        checkValidEntities();
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        DataReaderQos drq = null;
        try {
            drq = subscriber.copyFromTopicQos(subscriber.getDefaultDataReaderQos(), topic.getQos());
        } catch (Exception e) {
            fail("Exception occured while calling copyFromTopicQos(drqos,topicqos): " + util.printException(e));
        }
        assertTrue("Check for valid DataReaderQos", drq != null);
    }

    /**
     * Test method for
     * {@link org.omg.dds.sub.Subscriber#copyFromTopicQos(org.omg.dds.sub.DataReaderQos, org.omg.dds.topic.TopicQos)}
     * .
     */
    //@tTest
    public void testCopyFromTopicQosDrqNull() {
        checkValidEntities();
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        DataReaderQos drq = null;
        try {
            drq = subscriber.copyFromTopicQos(null, topic.getQos());
        } catch (Exception e) {
            assertTrue("Expected IllegalArgumentException but got: " + util.printException(e), util.exceptionCheck("testCopyFromTopicQosDwqNull", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for invalid DataReaderQos object", drq == null);
    }

    /**
     * Test method for
     * {@link org.omg.dds.pub.Subscriber#copyFromTopicQos(org.omg.dds.pub.DataReaderQos, org.omg.dds.topic.TopicQos)}
     * .
     */
    //@tTest
    public void testCopyFromTopicQosTqNull() {
        checkValidEntities();
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        DataReaderQos drq = null;
        try {
            drq = subscriber.copyFromTopicQos(subscriber.getDefaultDataReaderQos(), null);
        } catch (Exception e) {
            assertTrue("Expected IllegalArgumentException but got: " + util.printException(e), util.exceptionCheck("testCopyFromTopicQosTqNull", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for invalid DataReaderQos object", drq == null);
    }

    /**
     * Test method for {@link org.omg.dds.sub.Subscriber#getStatusCondition()}.
     */
    //@tTest
    public void testGetStatusCondition() {
        checkValidEntities();
        StatusCondition<Subscriber> result = null;
        try {
            result = subscriber.getStatusCondition();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetStatusCondition", e, false));
        }
        assertTrue("Check for valid StatusCondition object", util.objectCheck("testGetStatusCondition", result));
    }

    /**
     * Test method for {@link org.omg.dds.sub.Subscriber#getParent()}.
     */
    //@tTest
    public void testGetParent() {
        checkValidEntities();
        DomainParticipant result = null;
        try {
            result = subscriber.getParent();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetParent", e, false));
        }
        assertTrue("Check for valid Domainparticipant object", util.objectCheck("testGetParent", result.equals(participant)));
    }

    /**
     * Test method for {@link org.omg.dds.sub.Subscriber#createDataState()}.
     */
    //@tTest
    public void testCreateDataState() {
        checkValidEntities();
        DataState ds = null;
        try {
            ds = subscriber.createDataState();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testCreateDataState", e, false));
        }
        assertTrue("Check for valid Domainparticipant object", util.objectCheck("testCreateDataState", ds));
        assertTrue("Check for empty InstanceState", util.objectCheck("testCreateDataState", ds.getInstanceStates().isEmpty()));
        assertTrue("Check for empty SampleState", util.objectCheck("testCreateDataState", ds.getSampleStates().isEmpty()));
        assertTrue("Check for empty ViewState", util.objectCheck("testCreateDataState", ds.getViewStates().isEmpty()));

    }


    /**
     * Test method for {@link org.omg.dds.core.Entity#getListener()}.
     */
    @SuppressWarnings("unchecked")
    //@tTest
    public void testSubscriberGetListener() {
        checkValidEntities();
        SubscriberListener sl = new SubscriberListenerClass();
        SubscriberListener sl1 = null;
        Collection<Class<? extends Status>> statuses = new HashSet<Class<? extends Status>>();
        Class<? extends Status> status = null;
        statuses.add(DataAvailableStatus.class);
        try {
            subscriber.setListener(sl, statuses);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetSubscriberListener", e, false));
        }
        try {
            sl1 = subscriber.getListener();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetSubscriberListener", e, false));
        }
        assertTrue("Check for valid SubscriberListener object", util.objectCheck("testGetSubscriberListener", sl1));
        /* reset listener to null */
        try {
            subscriber.setListener(null, status);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetSubscriberListener", e, false));
        }
        try {
            sl1 = subscriber.getListener();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetSubscriberListener", e, false));
        }
        assertTrue("Check for null SubscriberListener object", sl1 == null);
    }

    /**
     * Test method for {@link org.omg.dds.core.Entity#setListener(java.util.EventListener)}.
     */
    //@tTest
    public void testSubscriberSetListener() {
        checkValidEntities();
        SubscriberListener sl = new SubscriberListenerClass();
        try {
            subscriber.setListener(sl);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testSubscriberSetListener", e, false));
        }
    }

    /**
     * Test method for {@link org.omg.dds.core.Entity#setListener(java.util.EventListener, java.util.Collection)}.
     */
    //@tTest
    public void testSubscriberSetListenerCollectionOfClassOfQextendsStatus() {
        checkValidEntities();
        SubscriberListener sl = new SubscriberListenerClass();
        Collection<Class<? extends Status>> statuses = new HashSet<Class<? extends Status>>();
        statuses.add(DataAvailableStatus.class);
        try {
            subscriber.setListener(sl, statuses);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testSubscriberSetListenerCollectionOfClassOfQextendsStatus", e, false));
        }
    }

    /**
     * Test method for {@link org.omg.dds.core.Entity#setListener(java.util.EventListener, java.lang.Class<? extends org.omg.dds.core.status.Status>[])}.
     */
    @SuppressWarnings("unchecked")
    //@tTest
    public void testSubscriberSetListenerClassOfQextendsStatusArray() {
        checkValidEntities();
        SubscriberListener sl = new SubscriberListenerClass();
        Class<? extends Status> status = DataAvailableStatus.class;
        try {
            subscriber.setListener(sl, status);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testSubscriberSetListenerClassOfQextendsStatusArray", e, false));
        }
    }

    /**
     * Test method for {@link org.omg.dds.core.Entity#getQos()}.
     */
    //@tTest
    public void testGetQos() {
        checkValidEntities();
        SubscriberQos sq = null;
        try {
            sq = subscriber.getQos();
        } catch (Exception e) {
            fail("Exception occured while calling getQos(): " + util.printException(e));
        }
        assertTrue("Check for valid SubscriberQos", sq != null);
    }

    /**
     * Test method for {@link org.omg.dds.core.Entity#setQos(org.omg.dds.core.EntityQos)}.
     */
    //@tTest
    public void testSetQos() {
        checkValidEntities();
        SubscriberQos pq = null;
        try {
            pq = subscriber.getQos();
        } catch (Exception e) {
            fail("Exception occured while doing getDefaultSubscriberQos(): " + util.printException(e));
        }
        try {
            pq = pq.withPolicy(pq.getEntityFactory().withAutoEnableCreatedEntities(false));
            assertFalse("Check for valid SubscriberQos value", pq.getEntityFactory().isAutoEnableCreatedEntities());
            subscriber.setQos(pq);
        } catch (Exception ex) {
            fail("Failed to set the SubscriberQos");
        }
        try {
            pq = subscriber.getQos();
            assertFalse("Check for valid SubscriberQos value", pq.getEntityFactory().isAutoEnableCreatedEntities());
            /* restore qos */
            pq = pq.withPolicy(pq.getEntityFactory().withAutoEnableCreatedEntities(true));
            subscriber.setQos(pq);
        } catch (Exception e) {
            fail("Failed to restore the SubscriberQos");
        }
        assertTrue("Check for valid SubscriberQos value", subscriber.getQos().getEntityFactory().isAutoEnableCreatedEntities());

    }

    /**
     * Test method for
     * {@link org.omg.dds.core.Entity#setQos(org.omg.dds.core.EntityQos)}.
     */
    //@tTest
    public void testSetQosNull() {
        checkValidEntities();
        boolean exceptionOccured = false;
        try {
            subscriber.setQos(null);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testSetQosNull", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException occured failed", exceptionOccured);

    }

    /**
     * Test method for {@link org.omg.dds.core.Entity#enable()}.
     */
    //@tTest
    public void testEnable() {
        checkValidEntities();
        try {
            subscriber.enable();
        } catch (Exception e) {
            assertTrue("Exception occured while calling enable()" + util.printException(e), util.exceptionCheck("testEnable", e, false));
        }
    }

    /**
     * Test method for {@link org.omg.dds.core.Entity#getStatusChanges()}.
     */
    //@tTest
    public void testGetStatusChanges() {
        checkValidEntities();
        Set<Class<? extends Status>> statuses = null;
        try {
            statuses = subscriber.getStatusChanges();
        } catch (Exception e) {
            assertTrue("Exception occured while calling getStatusChanges()" + util.printException(e), util.exceptionCheck("testGetStatusChanges", e, false));
        }
        assertTrue("Check for valid status set", util.objectCheck("testGetStatusChanges", statuses));
    }

    /**
     * Test method for {@link org.omg.dds.core.Entity#getInstanceHandle()}.
     */
    //@tTest
    public void testGetInstanceHandle() {
        checkValidEntities();
        InstanceHandle ih = null;
        try {
            ih = subscriber.getInstanceHandle();
        } catch (Exception e) {
            fail("Exception occured while calling getInstanceHandle(): " + util.printException(e));
        }
        assertTrue("Check for valid getInstanceHandle", ih != null);
    }

    /**
     * Test method for {@link org.omg.dds.core.Entity#retain()}.
     */
    //@tTest
    public void testRetain() {
        checkValidEntities();
        try {
            subscriber.retain();
        } catch (Exception e) {
            assertTrue("Exception occured while calling retain()" + util.printException(e), util.exceptionCheck("testRetain", e, false));
        }
    }

    /**
     * Test method for {@link org.omg.dds.core.DDSObject#getEnvironment()}.
     */
    //@tTest
    public void testGetEnvironment() {
        checkValidEntities();
        ServiceEnvironment senv = null;
        try {
            senv = subscriber.getEnvironment();
        } catch (Exception e) {
            fail("Exception occured while calling getEnvironment(): " + util.printException(e));
        }
        assertTrue("Check for valid ServiceEnvironment", senv != null);
    }

}
