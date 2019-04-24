
package unittest.pub;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Properties;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.dds.core.Duration;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.policy.Deadline;
import org.omg.dds.core.policy.Durability;
import org.omg.dds.core.policy.Liveliness;
import org.omg.dds.core.policy.PolicyFactory;
import org.omg.dds.core.status.LivelinessLostStatus;
import org.omg.dds.core.status.OfferedDeadlineMissedStatus;
import org.omg.dds.core.status.OfferedIncompatibleQosStatus;
import org.omg.dds.core.status.PublicationMatchedStatus;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.domain.DomainParticipantFactory;
import org.omg.dds.pub.DataWriter;
import org.omg.dds.pub.DataWriterQos;
import org.omg.dds.pub.Publisher;
import org.omg.dds.sub.DataReader;
import org.omg.dds.sub.DataReaderQos;
import org.omg.dds.sub.Subscriber;
import org.omg.dds.topic.Topic;

import Test.Msg;

import org.eclipse.cyclonedds.test.AbstractUtilities;
import org.eclipse.cyclonedds.test.PublisherListenerClass;

public class PublisherListenerTest {

    private final static int                DOMAIN_ID   = 123;
    private static ServiceEnvironment       env;
    private static DomainParticipantFactory dpf;
    private static AbstractUtilities        util        = AbstractUtilities.getInstance(PublisherListenerTest.class);
    private final static Properties         prop        = new Properties();
    private static DomainParticipant        participant = null;
    private static Publisher                publisher   = null;
    private static Subscriber               subscriber  = null;
    private static Topic<Msg>               topic       = null;
    private static DataWriter<Msg>          dataWriter  = null;
    private static String                   topicName   = "PublisherListenerTest";
    private static Msg                      message     = new Msg(0, "PublisherListenerTest");
    private final static Semaphore          sem         = new Semaphore(0);
    private static PublisherListenerClass   pl          = null;

    @BeforeClass
    public static void init() {
        try {
            String serviceEnvironment = System.getProperty("JAVA5PSM_SERVICE_ENV");
            assertTrue("Check for valid ServiceEnvironment string", !serviceEnvironment.equals(""));
            System.setProperty(ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY, serviceEnvironment);
            env = ServiceEnvironment.createInstance(PublisherListenerTest.class.getClassLoader());
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
            pl = new PublisherListenerClass(sem);
            assertTrue("Check for valid DataWriterListener object failed", pl instanceof PublisherListenerClass);
            DataWriterQos dwq = publisher.getDefaultDataWriterQos();
            Liveliness ll = dwq.getLiveliness().withLeaseDuration(2, TimeUnit.SECONDS);
            dwq = dwq.withPolicy(ll);
            dataWriter = publisher.createDataWriter(topic, dwq);
        } catch (Exception e) {
            fail("Exception occured while initiating the PublisherListenerTest class: " + util.printException(e));
        }

    }

    private void checkValidEntities() {
        assertTrue("Check for valid DomainParticipant object failed", participant instanceof DomainParticipant);
        assertTrue("Check for valid Publisher object failed", publisher instanceof Publisher);
        assertTrue("Check for valid Subscriber object failed", subscriber instanceof Subscriber);
        assertTrue("Check for valid Topic object failed", topic instanceof Topic);
        assertTrue("Check for valid DataWriter object failed", dataWriter instanceof DataWriter);
        assertTrue("Check for valid PublisherListenerClass object failed", pl instanceof PublisherListenerClass);
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


    @SuppressWarnings("unchecked")
    @Test
    public void testOnOfferedIncompatibleQos() {
        checkValidEntities();
        DataReader<Msg> dr = null;
        boolean triggerOccured = false;
        try {
            /*
             * should trigger OnOfferedIncompatibleQos because of non matching
             * durability qos
             */
            publisher.setListener(pl, OfferedIncompatibleQosStatus.class);
            DataReaderQos drq = subscriber.getDefaultDataReaderQos();
            Durability dur = drq.getDurability().withTransient();
            drq = drq.withPolicy(dur);
            dr = subscriber.createDataReader(topic, drq);
            try {
                triggerOccured = sem.tryAcquire(10, TimeUnit.SECONDS);
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testOnOfferedIncompatibleQos", e, false));
            }
            assertTrue("OnOfferedIncompatibleQos listener status failed to trigger", triggerOccured);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testOnOfferedIncompatibleQos", e, false));
        } finally {
            try {
                dr.close();
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testOnOfferedIncompatibleQos", e, false));
            }
            if (sem.availablePermits() > 0) {
                sem.drainPermits();
            }
        }
        assertTrue("Check for valid OnOfferedIncompatibleQos value", util.valueCompareCheck("testOnOfferedIncompatibleQos", pl.offeredIncompatibleQosCounter, 0, AbstractUtilities.Equality.GT));
        pl.reset();
    }


    @SuppressWarnings("unchecked")
    @Test
    public void testOnOfferedDeadlineMissed() {
        checkValidEntities();
        boolean triggerOccured = false;
        try {
            publisher.setListener(pl, OfferedDeadlineMissedStatus.class);
            DataWriterQos dwq = dataWriter.getQos();
            Deadline du = dwq.getDeadline().withPeriod(1, TimeUnit.SECONDS);
            dwq = dwq.withPolicy(du);
            dataWriter.setQos(dwq);
            dataWriter.write(message);
            /*
             * should trigger OnOfferedDeadlineMissed because of sleep >
             * deadline time
             */
            try {
                triggerOccured = sem.tryAcquire(10, TimeUnit.SECONDS);
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testOnOfferedDeadlineMissed", e, false));
            }
            assertTrue("OnOfferedDeadlineMissed listener status failed to trigger", triggerOccured);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testOnOfferedDeadlineMissed", e, false));
        } finally {
            if (sem.availablePermits() > 0) {
                sem.drainPermits();
            }
            try {
                DataWriterQos dwq = dataWriter.getQos();
                PolicyFactory policyFactory = dpf.getEnvironment().getSPI().getPolicyFactory();
                dwq = dwq.withPolicy(policyFactory.Deadline().withPeriod(Duration.infiniteDuration(env)));
                dataWriter.setQos(dwq);
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testOnOfferedDeadlineMissed", e, false));
            }
        }
        assertTrue("Check for valid OnOfferedDeadlineMissed value", util.valueCompareCheck("testOnOfferedDeadlineMissed", pl.offeredDeadlineMissedCounter, 0, AbstractUtilities.Equality.GT));
        pl.reset();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testOnLivelinessLost() {
        checkValidEntities();
        DataWriter<Msg> dw = null;
        boolean triggerOccured = false;
        try {
            /*
             * should trigger OnLivelinessLost as sleep > lease duration
             */
            DataWriterQos dwq = publisher.getDefaultDataWriterQos();
            Liveliness ll = dwq.getLiveliness().withManualByTopic().withLeaseDuration(2, TimeUnit.SECONDS);
            dwq = dwq.withPolicy(ll);
            dw = publisher.createDataWriter(topic, dwq);
            publisher.setListener(pl, LivelinessLostStatus.class);
            try {
                triggerOccured = sem.tryAcquire(10, TimeUnit.SECONDS);
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testOnLivelinessLost", e, false));
            }
            assertTrue("OnLivelinessLost listener status failed to trigger", triggerOccured);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testOnLivelinessLost", e, false));
        } finally {
            try {
                dw.close();
            } catch (Exception e) {
                /* ignore */
            }
            if (sem.availablePermits() > 0) {
                sem.drainPermits();
            }
        }
        assertTrue("Check for valid OnLivelinessLost value", util.valueCompareCheck("testOnLivelinessLost", pl.livelinessLostCounter, 0, AbstractUtilities.Equality.GT));
        pl.reset();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testOnPublicationMatched() {
        checkValidEntities();
        DataWriter<Msg> dw = null;
        DataReader<Msg> dr = null;
        boolean triggerOccured = false;
        try {
            dw = publisher.createDataWriter(topic);
            publisher.setListener(pl, PublicationMatchedStatus.class);
            dr = subscriber.createDataReader(topic);
            try {
                triggerOccured = sem.tryAcquire(10, TimeUnit.SECONDS);
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testOnPublicationMatched", e, false));
            }
            assertTrue("OnPublicationMatched listener status failed to trigger", triggerOccured);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testOnPublicationMatched", e, false));
        } finally {
            try {
                dw.close();
                dr.close();
            } catch (Exception e) {
                /* ignore */
            }
            if (sem.availablePermits() > 0) {
                sem.drainPermits();
            }
        }
        assertTrue("Check for valid OnPublicationMatched value", util.valueCompareCheck("testOnPublicationMatched", pl.publicationMatchCounter, 0, AbstractUtilities.Equality.GT));
        pl.reset();
    }
}
