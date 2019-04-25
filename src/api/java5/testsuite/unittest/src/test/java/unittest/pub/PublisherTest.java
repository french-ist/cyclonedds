package unittest.pub;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.HashSet;
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
import org.omg.dds.core.StatusCondition;
import org.omg.dds.core.status.DataAvailableStatus;
import org.omg.dds.core.status.Status;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.domain.DomainParticipantFactory;
import org.omg.dds.pub.DataWriter;
import org.omg.dds.pub.DataWriterListener;
import org.omg.dds.pub.DataWriterQos;
import org.omg.dds.pub.Publisher;
import org.omg.dds.pub.PublisherListener;
import org.omg.dds.pub.PublisherQos;
import org.omg.dds.topic.Topic;

import Test.Msg;

import org.eclipse.cyclonedds.test.AbstractUtilities;
import org.eclipse.cyclonedds.test.DataWriterListenerClass;
import org.eclipse.cyclonedds.test.PublisherListenerClass;

public class PublisherTest {

    private final static int                DOMAIN_ID   = 123;
    private static ServiceEnvironment       env;
    private static DomainParticipantFactory dpf;
    private static AbstractUtilities        util        = AbstractUtilities.getInstance(PublisherTest.class);
    private final static Properties         prop        = new Properties();
    private static DomainParticipant        participant = null;
    private static Publisher                publisher   = null;
    private static Topic<Msg>               topic       = null;
    private static String                   topicName   = "PublisherTest";

    @BeforeClass
    public static void init() {
        try {
            String serviceEnvironment = System.getProperty("JAVA5PSM_SERVICE_ENV");
            assertTrue("Check for valid ServiceEnvironment string", !serviceEnvironment.equals(""));
            System.setProperty(ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY, serviceEnvironment);
            env = ServiceEnvironment.createInstance(PublisherTest.class.getClassLoader());
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
            fail("Exception occured while initiating the PublisherTest class: " + util.printException(e));
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
     * Test method for {@link org.omg.dds.pub.Publisher#createDataWriter(org.omg.dds.topic.Topic)}.
     */
    //@tTest
    public void testCreateDataWriterTopicOfTYPE() {
        checkValidEntities();
        DataWriter<Msg> dw = null;
        try {
            dw = publisher.createDataWriter(topic);
        } catch (Exception e) {
            fail("Exception occured while calling createDataWriter: " + util.printException(e));
        }
        assertTrue("Check for valid DataWriter object", dw instanceof DataWriter);
        if (dw != null) {
            dw.close();
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.pub.Publisher#createDataWriter(org.omg.dds.topic.Topic)}
     * .
     */
    //@tTest
    public void testCreateDataWriterTopicOfTYPENull() {
        checkValidEntities();
        DataWriter<Msg> dw = null;
        try {
            dw = publisher.createDataWriter(null);
        } catch (Exception e) {
            assertTrue("Check for IllegalArgumentException but got exception:" + util.printException(e), e instanceof IllegalArgumentException);
        }
        assertTrue("Check for invalid DataWriter object", dw == null);
    }

    /**
     * Test method for
     * {@link org.omg.dds.pub.Publisher#createDataWriter(org.omg.dds.topic.Topic, org.omg.dds.pub.DataWriterQos, org.omg.dds.pub.DataWriterListener, java.util.Collection)}
     * .
     */
    //@tTest
    public void testCreateDataWriterTopicOfTYPEDataWriterQosDataWriterListenerOfTYPECollectionOfClassOfQextendsStatus() {
        checkValidEntities();
        DataWriter<Msg> dw = null;
        DataWriterListener<Msg> listener = new DataWriterListenerClass();
        Collection<Class<? extends Status>> statuses = new HashSet<Class<? extends Status>>();
        statuses.add(DataAvailableStatus.class);
        DataWriterQos dwq = publisher.getDefaultDataWriterQos();
        assertTrue("Check for valid DataWriterQos object", dwq instanceof DataWriterQos);
        try {
            dw = publisher.createDataWriter(topic, dwq, listener, statuses);
        } catch (Exception e) {
            assertTrue("Exception occured while creating a DataWriter with qos, listener and status col: " + util.printException(e),
                    util.exceptionCheck("testCreateDataWriterTopicOfTYPEDataWriterQosDataWriterListenerOfTYPECollectionOfClassOfQextendsStatus", e, false));
        }
        assertTrue("Check for valid DataWriter object", util.objectCheck("testCreateDataWriterTopicOfTYPEDataWriterQosDataWriterListenerOfTYPECollectionOfClassOfQextendsStatus", dw));
        if (dw != null) {
            dw.close();
        }
    }

    /**
     * Test method for {@link
     * org.omg.dds.pub.Publisher#createDataWriter(org.omg.dds.topic.Topic,
     * org.omg.dds.pub.DataWriterQos, org.omg.dds.pub.DataWriterListener,
     * java.lang.Class<? extends org.omg.dds.core.status.Status>[])}.
     */
    @SuppressWarnings("unchecked")
    //@tTest
    public void testCreateDataWriterTopicOfTYPEDataWriterQosDataWriterListenerOfTYPEClassOfQextendsStatusArray() {
        checkValidEntities();
        DataWriter<Msg> dw = null;
        DataWriterListener<Msg> listener = null;
        Class<DataAvailableStatus> status = DataAvailableStatus.class;
        DataWriterQos dwq = publisher.getDefaultDataWriterQos();
        assertTrue("Check for valid DataWriterQos object", dwq instanceof DataWriterQos);
        try {
            dw = publisher.createDataWriter(topic, dwq, listener, status);
        } catch (Exception e) {
            assertTrue("Exception occured while creating a DataWriter with qos, listener and status: " + util.printException(e),
                    util.exceptionCheck("testCreateDataWriterTopicOfTYPEDataWriterQosDataWriterListenerOfTYPEClassOfQextendsStatusArray", e, false));
        }
        assertTrue("Check for valid DataWriter object", util.objectCheck("testCreateDataWriterTopicOfTYPEDataWriterQosDataWriterListenerOfTYPEClassOfQextendsStatusArray", dw));
        if (dw != null) {
            dw.close();
        }
    }

    /**
     * Test method for {@link
     * org.omg.dds.pub.Publisher#createDataWriter(org.omg.dds.topic.Topic,
     * org.omg.dds.pub.DataWriterQos, org.omg.dds.pub.DataWriterListener,
     * java.lang.Class<? extends org.omg.dds.core.status.Status>[])}.
     */
    @SuppressWarnings("unchecked")
    //@tTest
    public void testCreateDataWriterTopicOfTYPEDataWriterQosNullDataWriterListenerOfTYPEClassOfQextendsStatusArray() {
        checkValidEntities();
        DataWriter<Msg> dw = null;
        DataWriterListener<Msg> listener = null;
        Class<DataAvailableStatus> status = DataAvailableStatus.class;
        DataWriterQos dwq = publisher.getDefaultDataWriterQos();
        assertTrue("Check for valid DataWriterQos object", dwq instanceof DataWriterQos);
        try {
            dw = publisher.createDataWriter(topic, null, listener, status);
        } catch (Exception e) {
            assertTrue("Check for IllegalArgumentException but got exception:" + util.printException(e), e instanceof IllegalArgumentException);
        }
        assertTrue("Check for invalid DataWriter object", dw == null);
    }

    /**
     * Test method for {@link
     * org.omg.dds.pub.Publisher#createDataWriter(org.omg.dds.topic.Topic,
     * org.omg.dds.pub.DataWriterQos, org.omg.dds.pub.DataWriterListener,
     * java.lang.Class<? extends org.omg.dds.core.status.Status>[])}.
     */
    @SuppressWarnings("unchecked")
    //@tTest
    public void testCreateDataWriterTopicNullOfTYPEDataWriterQosDataWriterListenerOfTYPEClassOfQextendsStatusArray() {
        checkValidEntities();
        DataWriter<Msg> dw = null;
        DataWriterListener<Msg> listener = null;
        Class<DataAvailableStatus> status = DataAvailableStatus.class;
        DataWriterQos dwq = publisher.getDefaultDataWriterQos();
        assertTrue("Check for valid DataWriterQos object", dwq instanceof DataWriterQos);
        Topic<Msg> top = null;
        try {
            dw = publisher.createDataWriter(top, dwq, listener, status);
        } catch (Exception e) {
            assertTrue("Check for IllegalArgumentException but got exception:" + util.printException(e), e instanceof IllegalArgumentException);
        }
        assertTrue("Check for invalid DataWriter object", dw == null);
    }

    /**
     * Test method for
     * {@link org.omg.dds.pub.Publisher#createDataWriter(org.omg.dds.topic.Topic, org.omg.dds.pub.DataWriterQos)}
     * .
     */
    //@tTest
    public void testCreateDataWriterTopicOfTYPEDataWriterQos() {
        checkValidEntities();
        DataWriter<Msg> dw = null;
        DataWriterQos dwq = null;
        try {
            dwq = publisher.getDefaultDataWriterQos();
            assertTrue("Check for valid DataWriterQos object", dwq instanceof DataWriterQos);
            dw = publisher.createDataWriter(topic, dwq);
        } catch (Exception e) {
            fail("Exception occured while calling createDataWriter(topic,qos): " + util.printException(e));
        }
        assertTrue("Check for valid DataWriter object", dw instanceof DataWriter);
        if (dw != null) {
            dw.close();
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.pub.Publisher#lookupDataWriter(java.lang.String)}.
     */
    //@tTest
    public void testLookupDataWriterString() {
        checkValidEntities();
        DataWriter<Msg> dw = null;
        DataWriter<Msg> dw1 = null;
        try {
            dw = publisher.createDataWriter(topic);
        } catch (Exception e) {
            fail("Exception occured while calling createDataWriter(topic): " + util.printException(e));
        }
        assertTrue("Check for valid DataWriter object", dw instanceof DataWriter);
        try {
            dw1 = publisher.lookupDataWriter(topicName);
        } catch (Exception e) {
            fail("Exception occured while calling lookupDataWriter(string): " + util.printException(e));
        }
        assertTrue("Check for valid DataWriter object", dw1 instanceof DataWriter);
        if (dw != null) {
            dw.close();
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.pub.Publisher#lookupDataWriter(java.lang.String)}.
     */
    //@tTest
    public void testLookupDataWriterStringInvalid() {
        checkValidEntities();
        DataWriter<Msg> dw = null;
        DataWriter<Msg> dw1 = null;
        try {
            dw = publisher.createDataWriter(topic);
        } catch (Exception e) {
            fail("Exception occured while calling createDataWriter(topic): " + util.printException(e));
        }
        assertTrue("Check for valid DataWriter object", dw instanceof DataWriter);
        try {
            dw1 = publisher.lookupDataWriter("foo");
        } catch (Exception e) {
            fail("Exception occured while calling lookupDataWriter(string): " + util.printException(e));
        }
        assertTrue("Check for invalid DataWriter object", dw1 == null);
        if (dw != null) {
            dw.close();
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.pub.Publisher#lookupDataWriter(org.omg.dds.topic.Topic)}
     * .
     */
    //@tTest
    public void testLookupDataWriterStringNull() {
        checkValidEntities();
        DataWriter<Msg> dw = null;
        DataWriter<Msg> dw1 = null;
        Topic<Msg> top = null;
        try {
            dw = publisher.createDataWriter(topic);
        } catch (Exception e) {
            fail("Exception occured while calling createDataWriter(topic): " + util.printException(e));
        }
        assertTrue("Check for valid DataWriter object", dw instanceof DataWriter);
        try {
            dw1 = publisher.lookupDataWriter(top);
        } catch (Exception e) {
            assertTrue("Check for IllegalArgumentException but got exception:" + util.printException(e), e instanceof IllegalArgumentException);
        }
        assertTrue("Check for invalid DataWriter object", dw1 == null);
        if (dw != null) {
            dw.close();
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.pub.Publisher#lookupDataWriter(org.omg.dds.topic.Topic)}
     * .
     */
    //@tTest
    public void testLookupDataWriterTopicOfTYPE() {
        checkValidEntities();
        DataWriter<Msg> dw = null;
        DataWriter<Msg> dw1 = null;
        try {
            dw = publisher.createDataWriter(topic);
        } catch (Exception e) {
            fail("Exception occured while calling createDataWriter(topic): " + util.printException(e));
        }
        assertTrue("Check for valid DataWriter object", dw instanceof DataWriter);
        try {
            dw1 = publisher.lookupDataWriter(topic);
        } catch (Exception e) {
            fail("Exception occured while calling lookupDataWriter(string): " + util.printException(e));
        }
        assertTrue("Check for valid DataWriter object", dw1 instanceof DataWriter);
        if (dw != null) {
            dw.close();
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.pub.Publisher#closeContainedEntities()}.
     */
    //@tTest
    public void testCloseContainedEntities() {
        checkValidEntities();
        DataWriter<Msg> dw = null;
        boolean exceptionOccured = false;
        try {
            dw = publisher.createDataWriter(topic);
        } catch (Exception e) {
            fail("Exception occured while calling createDataWriter(topic): " + util.printException(e));
        }
        assertTrue("Check for valid DataWriter object", dw instanceof DataWriter);
        try {
            publisher.closeContainedEntities();
        } catch (Exception e) {
            fail("Exception occured while calling closeContainedEntities: " + util.printException(e));
        }
        try {
            dw.close();
        } catch (Exception e) {
            assertTrue("Check for AlreadyClosedException but got exception:" + util.printException(e), e instanceof AlreadyClosedException);
            exceptionOccured = true;
        }
        assertTrue("Check if AlreadyClosedException occured", exceptionOccured);
    }

    /**
     * Test method for {@link org.omg.dds.pub.Publisher#suspendPublications()}.
     */
    //@tTest
    public void testSuspendPublications() {
        checkValidEntities();
        try {
            publisher.suspendPublications();
        } catch (Exception e) {
            assertTrue("Exception occured while calling suspendPublications()" + util.printException(e), util.exceptionCheck("testSuspendPublications", e, false));
        }
        try {
            publisher.resumePublications();
        } catch (Exception e) {
            assertTrue("Exception occured while calling resumePublications()" + util.printException(e), util.exceptionCheck("testSuspendPublications", e, false));
        }
    }

    /**
     * Test method for {@link org.omg.dds.pub.Publisher#resumePublications()}.
     */
    //@tTest
    public void testResumePublications() {
        checkValidEntities();
        boolean exceptionOccured = false;
        try {
            publisher.resumePublications();
        } catch (Exception e) {
            assertTrue("Check for PreconditionNotMetException but got exception:" + util.printException(e), util.exceptionCheck("testResumePublications", e, e instanceof PreconditionNotMetException));
            exceptionOccured = true;
        }
        assertTrue("Expected PreconditionNotMetException dit not occure", exceptionOccured);
    }

    /**
     * Test method for {@link org.omg.dds.pub.Publisher#beginCoherentChanges()}.
     */
    //@tTest
    public void testBeginCoherentChanges() {
        checkValidEntities();
        Publisher pub = null;

        try {
            PublisherQos pq = publisher.getQos();
            pq = pq.withPolicy(pq.getPresentation().withCoherentAccess(true));
            pub = participant.createPublisher(pq);
        } catch (Exception e) {
            fail("Exception occured during test initialization: " + util.printException(e));
        }
        try {
            pub.beginCoherentChanges();
        } catch (Exception e) {
            assertTrue("Exception occured while calling beginCoherentChanges()" + util.printException(e), util.exceptionCheck("testBeginCoherentChanges", e, false));
        }
        try {
            pub.endCoherentChanges();
        } catch (Exception e) {
            assertTrue("Exception occured while calling endCoherentChanges()" + util.printException(e), util.exceptionCheck("testBeginCoherentChanges", e, false));
        }
        if (pub != null) {
            pub.close();
        }

    }

    /**
     * Test method for {@link org.omg.dds.pub.Publisher#endCoherentChanges()}.
     */
    //@tTest
    public void testEndCoherentChanges() {
        checkValidEntities();
        boolean exceptionOccured = false;
        Publisher pub = null;
        try {
            PublisherQos pq = publisher.getQos();
            pq = pq.withPolicy(pq.getPresentation().withCoherentAccess(true));
            pub = participant.createPublisher(pq);

        } catch (Exception e) {
            fail("Exception occured during test initialization: " + util.printException(e));
        }
        try {
            pub.endCoherentChanges();
        } catch (Exception e) {
            assertTrue("Check for PreconditionNotMetException but got exception:" + util.printException(e), util.exceptionCheck("testEndCoherentChanges", e, e instanceof PreconditionNotMetException));
            exceptionOccured = true;
        }
        if (pub != null) {
            pub.close();
        }
        assertTrue("Expected PreconditionNotMetException dit not occure", exceptionOccured);
    }

    /**
     * Test method for
     * {@link org.omg.dds.pub.Publisher#waitForAcknowledgments(org.omg.dds.core.Duration)}
     * .
     */
    //@tTest
    public void testWaitForAcknowledgmentsDuration() {
        checkValidEntities();
        Duration timeout = Duration.newDuration(1, TimeUnit.SECONDS, env);
        boolean exceptionOccured = false;
        try {
            publisher.waitForAcknowledgments(timeout);
        } catch (Exception e) {
            assertTrue("TimeoutException expected but got: " + util.printException(e), util.exceptionCheck("testWaitForAcknowledgmentsDuration", e, e instanceof TimeoutException));
            exceptionOccured = true;
        }
        assertTrue("Check if TimeoutException occured", exceptionOccured);
    }

    /**
     * Test method for
     * {@link org.omg.dds.pub.Publisher#waitForAcknowledgments(long, java.util.concurrent.TimeUnit)}
     * .
     */
    //@tTest
    public void testWaitForAcknowledgmentsLongTimeUnit() {
        checkValidEntities();
        boolean exceptionOccured = false;
        try {
            publisher.waitForAcknowledgments(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            assertTrue("TimeoutException expected but got: " + util.printException(e), util.exceptionCheck("testWaitForAcknowledgmentsLongTimeUnit", e, e instanceof TimeoutException));
            exceptionOccured = true;
        }
        assertTrue("Check if TimeoutException occured", exceptionOccured);
    }

    /**
     * Test method for
     * {@link org.omg.dds.pub.Publisher#waitForAcknowledgments(long, java.util.concurrent.TimeUnit)}
     * .
     */
    //@tTest
    public void testWaitForAcknowledgmentsLongTimeUnitOverflow() {
        checkValidEntities();
        boolean exceptionOccured = false;
        try {
            publisher.waitForAcknowledgments(Long.MAX_VALUE + 1, TimeUnit.SECONDS);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e),
                    util.exceptionCheck("testWaitForAcknowledgmentsLongTimeUnitOverflow", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException occured", exceptionOccured);
    }

    /**
     * Test method for
     * {@link org.omg.dds.pub.Publisher#waitForAcknowledgments(long, java.util.concurrent.TimeUnit)}
     * .
     */
    //@tTest
    public void testWaitForAcknowledgmentsLongTimeUnitNegative() {
        checkValidEntities();
        boolean exceptionOccured = false;
        try {
            publisher.waitForAcknowledgments(-1, TimeUnit.SECONDS);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e),
                    util.exceptionCheck("testWaitForAcknowledgmentsLongTimeUnitNegative", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException occured", exceptionOccured);
    }

    /**
     * Test method for
     * {@link org.omg.dds.pub.Publisher#getDefaultDataWriterQos()}.
     */
    //@tTest
    public void testGetDefaultDataWriterQos() {
        checkValidEntities();
        DataWriterQos dwq = null;
        try {
            dwq = publisher.getDefaultDataWriterQos();
        } catch (Exception e) {
            fail("Exception occured while doing getDefaultDataWriterQos(): " + util.printException(e));
        }
        assertTrue("Check for valid DataWriterQos", dwq != null);
    }

    /**
     * Test method for
     * {@link org.omg.dds.pub.Publisher#setDefaultDataWriterQos(org.omg.dds.pub.DataWriterQos)}
     * .
     */
    //@tTest
    public void testSetDefaultDataWriterQos() {
        checkValidEntities();
        DataWriterQos dwq = null;
        Duration deadline = Duration.newDuration(5, TimeUnit.SECONDS, env);
        try {
            dwq = publisher.getDefaultDataWriterQos();
        } catch (Exception e) {
            fail("Exception occured while calling getDefaultDataWriterQos: " + util.printException(e));
        }
        assertTrue("Check for valid DataWriterQos", dwq != null);
        try {
            dwq = dwq.withPolicy(dwq.getDeadline().withPeriod(deadline));
            publisher.setDefaultDataWriterQos(dwq);
        } catch (Exception ex) {
            fail("Failed to set the DataWriterQos");
        }
        try {
            dwq = publisher.getDefaultDataWriterQos();
            assertTrue("Check for valid DataWriterQos value", dwq.getDeadline().getPeriod().getDuration(TimeUnit.SECONDS) == deadline.getDuration(TimeUnit.SECONDS));
            /* restore qos */
            dwq = dwq.withPolicy(dwq.getDeadline().withPeriod(Duration.infiniteDuration(env)));
            publisher.setDefaultDataWriterQos(dwq);
            assertTrue("Check for valid DefaultDataWriterQos value", dwq.getDeadline().getPeriod().isInfinite());
        } catch (Exception ex) {
            fail("Failed to restore the DefaultDataWriterQos");
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.pub.Publisher#setDefaultDataWriterQos(org.omg.dds.pub.DataWriterQos)}
     * .
     */
    //@tTest
    public void testSetDefaultDataWriterQosNull() {
        checkValidEntities();
        boolean exceptionOccured = false;
        try {
            publisher.setDefaultDataWriterQos(null);
        } catch (Exception e) {
            assertTrue("Check for IllegalArgumentException but got exception:" + util.printException(e), e instanceof IllegalArgumentException);
            exceptionOccured = true;
        }
        assertTrue("Expected IllegalArgumentException dit not occure", exceptionOccured);

    }

    /**
     * Test method for
     * {@link org.omg.dds.pub.Publisher#copyFromTopicQos(org.omg.dds.pub.DataWriterQos, org.omg.dds.topic.TopicQos)}
     * .
     */
    //@tTest
    public void testCopyFromTopicQos() {
        checkValidEntities();
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        DataWriterQos dwq = null;
        try {
            dwq = publisher.copyFromTopicQos(publisher.getDefaultDataWriterQos(), topic.getQos());
        } catch (Exception e) {
            fail("Exception occured while calling copyFromTopicQos(dwqos,topicqos): " + util.printException(e));
        }
        assertTrue("Check for valid DataWriterQos", dwq != null);
    }

    /**
     * Test method for
     * {@link org.omg.dds.pub.Publisher#copyFromTopicQos(org.omg.dds.pub.DataWriterQos, org.omg.dds.topic.TopicQos)}
     * .
     */
    //@tTest
    public void testCopyFromTopicQosDwqNull() {
        checkValidEntities();
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        DataWriterQos dwq = null;
        try {
            dwq = publisher.copyFromTopicQos(null, topic.getQos());
        } catch (Exception e) {
            assertTrue("Expected IllegalArgumentException but got: " + util.printException(e), util.exceptionCheck("testCopyFromTopicQosDwqNull", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for invalid DataWriterQos object", dwq == null);
    }

    /**
     * Test method for
     * {@link org.omg.dds.pub.Publisher#copyFromTopicQos(org.omg.dds.pub.DataWriterQos, org.omg.dds.topic.TopicQos)}
     * .
     */
    //@tTest
    public void testCopyFromTopicQosTqNull() {
        checkValidEntities();
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        DataWriterQos dwq = null;
        try {
            dwq = publisher.copyFromTopicQos(publisher.getDefaultDataWriterQos(), null);
        } catch (Exception e) {
            assertTrue("Expected IllegalArgumentException but got: " + util.printException(e), util.exceptionCheck("testCopyFromTopicQosTqNull", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for invalid DataWriterQos object", dwq == null);
    }

    /**
     * Test method for {@link org.omg.dds.pub.Publisher#getStatusCondition()}.
     */
    //@tTest
    public void testGetStatusCondition() {
        checkValidEntities();
        StatusCondition<Publisher> result = null;
        try {
            result = publisher.getStatusCondition();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetStatusCondition", e, false));
        }
        assertTrue("Check for valid StatusCondition object", util.objectCheck("testGetStatusCondition", result));
    }

    /**
     * Test method for {@link org.omg.dds.pub.Publisher#getParent()}.
     */
    //@tTest
    public void testGetParent() {
        checkValidEntities();
        DomainParticipant result = null;
        try {
            result = publisher.getParent();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetParent", e, false));
        }
        assertTrue("Check for valid Domainparticipant object", util.objectCheck("testGetParent", result.equals(participant)));
    }

    /**
     * Test method for {@link org.omg.dds.core.Entity#getListener()}.
     */
    @SuppressWarnings("unchecked")
    //@tTest
    public void testGetPublisherListener() {
        checkValidEntities();
        PublisherListener pl = new PublisherListenerClass();
        PublisherListener pl1 = null;
        Collection<Class<? extends Status>> statuses = new HashSet<Class<? extends Status>>();
        Class<? extends Status> status = null;
        statuses.add(DataAvailableStatus.class);
        try {
            publisher.setListener(pl, statuses);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetPublisherListener", e, false));
        }
        try {
            pl1 = publisher.getListener();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetPublisherListener", e, false));
        }
        assertTrue("Check for valid PublisherListener object", util.objectCheck("testGetPublisherListener", pl1));
        /* reset listener to null */
        try {
            publisher.setListener(null, status);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetPublisherListener", e, false));
        }
        try {
            pl1 = publisher.getListener();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetPublisherListener", e, false));
        }
        assertTrue("Check for null PublisherListener object", pl1 == null);
    }

    /**
     * Test method for
     * {@link org.omg.dds.core.Entity#setListener(java.util.EventListener)}.
     */
    //@tTest
    public void testPublisherSetListener() {
        checkValidEntities();
        PublisherListener pl = new PublisherListenerClass();
        try {
            publisher.setListener(pl);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testPublisherSetListener", e, false));
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.core.Entity#setListener(java.util.EventListener, java.util.Collection)}
     * .
     */
    //@tTest
    public void testPublisherSetListenerCollectionOfClassOfQextendsStatus() {
        checkValidEntities();
        PublisherListener pl = new PublisherListenerClass();
        Collection<Class<? extends Status>> statuses = new HashSet<Class<? extends Status>>();
        statuses.add(DataAvailableStatus.class);
        try {
            publisher.setListener(pl, statuses);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testPublisherSetListenerCollectionOfClassOfQextendsStatus", e, false));
        }
    }

    /**
     * Test method for {@link
     * org.omg.dds.core.Entity#setListener(java.util.EventListener,
     * java.lang.Class<? extends org.omg.dds.core.status.Status>[])}.
     */
    @SuppressWarnings("unchecked")
    //@tTest
    public void testPublisherSetListenerClassOfQextendsStatusArray() {
        checkValidEntities();
        PublisherListener pl = new PublisherListenerClass();
        Class<? extends Status> status = DataAvailableStatus.class;
        try {
            publisher.setListener(pl, status);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testPublisherSetListenerClassOfQextendsStatusArray", e, false));
        }
    }

    /**
     * Test method for {@link org.omg.dds.core.Entity#getQos()}.
     */
    //@tTest
    public void testGetQos() {
        checkValidEntities();
        PublisherQos pq = null;
        try {
            pq = publisher.getQos();
        } catch (Exception e) {
            fail("Exception occured while calling getQos(): " + util.printException(e));
        }
        assertTrue("Check for valid PublisherQos", pq != null);
    }

    /**
     * Test method for
     * {@link org.omg.dds.core.Entity#setQos(org.omg.dds.core.EntityQos)}.
     */
    //@tTest
    public void testSetQos() {
        checkValidEntities();
        PublisherQos pq = null;
        try {
            pq = publisher.getQos();
        } catch (Exception e) {
            fail("Exception occured while doing getDefaultPublisherQos(): " + util.printException(e));
        }
        try {
            pq = pq.withPolicy(pq.getEntityFactory().withAutoEnableCreatedEntities(false));
            assertFalse("Check for valid PublisherQos value", pq.getEntityFactory().isAutoEnableCreatedEntities());
            publisher.setQos(pq);
        } catch (Exception ex) {
            fail("Failed to set the PublisherQos");
        }
        try {
            pq = publisher.getQos();
            assertFalse("Check for valid PublisherQos value", pq.getEntityFactory().isAutoEnableCreatedEntities());
            /* restore qos */
            pq = pq.withPolicy(pq.getEntityFactory().withAutoEnableCreatedEntities(true));
            publisher.setQos(pq);
        } catch (Exception e) {
            fail("Failed to restore the PublisherQos");
        }
        assertTrue("Check for valid PublisherQos value", publisher.getQos().getEntityFactory().isAutoEnableCreatedEntities());

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
            publisher.setQos(null);
        } catch (Exception e) {
            assertTrue("Check for IllegalArgumentException but got exception:" + util.printException(e), e instanceof IllegalArgumentException);
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException has occured failed", exceptionOccured);
    }

    /**
     * Test method for {@link org.omg.dds.core.Entity#enable()}.
     */
    //@tTest
    public void testEnable() {
        checkValidEntities();
        try {
            publisher.enable();
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
            statuses = publisher.getStatusChanges();
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
            ih = publisher.getInstanceHandle();
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
            publisher.retain();
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
            senv = publisher.getEnvironment();
        } catch (Exception e) {
            fail("Exception occured while calling getEnvironment(): " + util.printException(e));
        }
        assertTrue("Check for valid ServiceEnvironment", senv != null);
    }

}
