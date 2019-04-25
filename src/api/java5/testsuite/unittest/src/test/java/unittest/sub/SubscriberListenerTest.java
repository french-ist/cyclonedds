
package unittest.sub;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Properties;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.dds.core.AlreadyClosedException;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.policy.Deadline;
import org.omg.dds.core.policy.Durability;
import org.omg.dds.core.policy.History;
import org.omg.dds.core.policy.History.Kind;
import org.omg.dds.core.policy.Liveliness;
import org.omg.dds.core.policy.ResourceLimits;
import org.omg.dds.core.status.DataAvailableStatus;
import org.omg.dds.core.status.DataOnReadersStatus;
import org.omg.dds.core.status.LivelinessChangedStatus;
import org.omg.dds.core.status.RequestedDeadlineMissedStatus;
import org.omg.dds.core.status.RequestedIncompatibleQosStatus;
import org.omg.dds.core.status.SampleRejectedStatus;
import org.omg.dds.core.status.SubscriptionMatchedStatus;
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
import org.eclipse.cyclonedds.test.SubscriberListenerClass;

public class SubscriberListenerTest {

    private final static int                DOMAIN_ID   = 123;
    private static ServiceEnvironment       env;
    private static DomainParticipantFactory dpf;
    private static AbstractUtilities        util        = AbstractUtilities.getInstance(SubscriberListenerTest.class);
    private final static Properties         prop        = new Properties();
    private static DomainParticipant        participant = null;
    private static Publisher                publisher   = null;
    private static Subscriber               subscriber  = null;
    private static Topic<Msg>               topic       = null;
    private static String                   topicName   = "SubscriberListenerTest";
    private static Msg                      message     = new Msg(0, "SubscriberListenerTest");
    private final static Semaphore          sem         = new Semaphore(0);
    private static SubscriberListenerClass  sl          = null;

    @BeforeClass
    public static void init() {
        try {
            String serviceEnvironment = System.getProperty("JAVA5PSM_SERVICE_ENV");
            assertTrue("Check for valid ServiceEnvironment string", !serviceEnvironment.equals(""));
            System.setProperty(ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY, serviceEnvironment);
            env = ServiceEnvironment.createInstance(SubscriberListenerTest.class.getClassLoader());
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
            sl = new SubscriberListenerClass(sem);
            assertTrue("Check for valid SubscriberListener object failed", sl instanceof SubscriberListenerClass);
        } catch (Exception e) {
            fail("Exception occured while initiating the SubscriberListenerTest class: " + util.printException(e));
        }

    }

    private void checkValidEntities() {
        assertTrue("Check for valid DomainParticipant object failed", participant instanceof DomainParticipant);
        assertTrue("Check for valid Publisher object failed", publisher instanceof Publisher);
        assertTrue("Check for valid Subscriber object failed", subscriber instanceof Subscriber);
        assertTrue("Check for valid Topic object failed", topic instanceof Topic);
        assertTrue("Check for valid SubscriberListener object failed", sl instanceof SubscriberListenerClass);
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


    @SuppressWarnings("unchecked")
    //@tTest
    public void testOnRequestedIncompatibleQos() {
        checkValidEntities();
        DataWriter<Msg> dw = null;
        DataReader<Msg> dr = null;
        boolean triggerOccured = false;
        try {
            /*
             * should trigger OnRequestedIncompatibleQos because of non matching
             * durability qos
             */
            DataReaderQos drq = subscriber.getDefaultDataReaderQos();
            Durability dur = drq.getDurability().withTransient();
            drq = drq.withPolicy(dur);
            subscriber.setListener(sl, RequestedIncompatibleQosStatus.class);
            dr = subscriber.createDataReader(topic, drq);
            dw = publisher.createDataWriter(topic);

            try {
                triggerOccured = sem.tryAcquire(10, TimeUnit.SECONDS);
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testOnRequestedIncompatibleQos", e, false));
            }
            assertTrue("OnRequestedIncompatibleQos listener status failed to trigger", triggerOccured);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testOnRequestedIncompatibleQos", e, false));
        } finally {
            if (dw != null) {
                dw.close();
            }
            if (dr != null) {
                dr.close();
            }
            if (sem.availablePermits() > 0) {
                sem.drainPermits();
            }
        }
        assertTrue("Check for valid OnRequestedIncompatibleQos value", util.valueCompareCheck("testOnRequestedIncompatibleQos", sl.requestedIncompatibleQosCounter, 0, AbstractUtilities.Equality.GT));
        sl.reset();
    }


    @SuppressWarnings("unchecked")
    //@tTest
    public void testOnRequestedDeadlineMissed() {
        checkValidEntities();
        boolean triggerOccured = false;
        DataWriter<Msg> dw = null;
        DataReader<Msg> dr = null;
        try {

            subscriber.setListener(sl, RequestedDeadlineMissedStatus.class);
            DataReaderQos drq = subscriber.getDefaultDataReaderQos();
            Deadline du = drq.getDeadline().withPeriod(1, TimeUnit.SECONDS);
            drq = drq.withPolicy(du);
            dr = subscriber.createDataReader(topic, drq);

            DataWriterQos dwq = publisher.getDefaultDataWriterQos();
            du = dwq.getDeadline().withPeriod(1, TimeUnit.SECONDS);
            dwq = dwq.withPolicy(du);
            dw = publisher.createDataWriter(topic, dwq);
            dw.write(message);
            /*
             * should trigger OnOfferedDeadlineMissed because of sleep >
             * deadline time
             */
            try {
                triggerOccured = sem.tryAcquire(10, TimeUnit.SECONDS);
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testOnRequestedDeadlineMissed", e, false));
            }
            assertTrue("RequestedDeadlineMissed listener status failed to trigger", triggerOccured);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testOnRequestedDeadlineMissed", e, false));
        } finally {
            if (sem.availablePermits() > 0) {
                sem.drainPermits();
            }
            try {
                dr.take();
                if (dw != null) {
                    dw.close();
                }
                if (dr != null) {
                    dr.close();
                }
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testOnRequestedDeadlineMissed", e, false));
            }
        }
        assertTrue("Check for valid RequestedDeadlineMissed value", util.valueCompareCheck("testOnRequestedDeadlineMissed", sl.requestedDeadlineMissedCounter, 0, AbstractUtilities.Equality.GT));
        sl.reset();
    }

    @SuppressWarnings("unchecked")
    //@tTest
    public void testonLivelinessChanged() {
        checkValidEntities();
        DataReader<Msg> dr = null;
        DataWriter<Msg> dw = null;
        boolean triggerOccured = false;
        try {
            /*
             * should trigger testonLivelinessChanged as sleep > lease duration
             */

            DataWriterQos dwq = publisher.getDefaultDataWriterQos();
            Liveliness ll = dwq.getLiveliness().withManualByTopic().withLeaseDuration(2, TimeUnit.SECONDS);
            dwq = dwq.withPolicy(ll);
            dw = publisher.createDataWriter(topic, dwq);


            DataReaderQos drq = subscriber.getDefaultDataReaderQos();
            ll = drq.getLiveliness().withManualByTopic().withLeaseDuration(2, TimeUnit.SECONDS);
            drq = drq.withPolicy(ll);
            dr = subscriber.createDataReader(topic, drq);
            subscriber.setListener(sl, LivelinessChangedStatus.class);
            try {
                triggerOccured = sem.tryAcquire(10, TimeUnit.SECONDS);
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testonLivelinessChanged", e, false));
            }
            assertTrue("testonLivelinessChanged listener status failed to trigger", triggerOccured);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testonLivelinessChanged", e, false));
        } finally {
            try {
                if (dw != null) {
                    dw.close();
                }
                if (dr != null) {
                    dr.close();
                }
            } catch (Exception e) {
                /* ignore */
            }
            if (sem.availablePermits() > 0) {
                sem.drainPermits();
            }
        }
        assertTrue("Check for valid testonLivelinessChanged value", util.valueCompareCheck("testonLivelinessChanged", sl.livelinessChangedCounter, 0, AbstractUtilities.Equality.GT));
        sl.reset();
    }

    @SuppressWarnings("unchecked")
    //@tTest
    public void testOnSubscriptionMatched() {
        checkValidEntities();
        DataWriter<Msg> dw = null;
        DataReader<Msg> dr = null;
        boolean triggerOccured = false;
        try {
            subscriber.setListener(sl, SubscriptionMatchedStatus.class);
            dr = subscriber.createDataReader(topic);
            dw = publisher.createDataWriter(topic);
            try {
                triggerOccured = sem.tryAcquire(10, TimeUnit.SECONDS);
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testOnSubscriptionMatched", e, false));
            }
            assertTrue("OnSubscriptionMatched listener status failed to trigger", triggerOccured);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testOnSubscriptionMatched", e, false));
        } finally {
            try {
                if (dw != null) {
                    dw.close();
                }
                if (dr != null) {
                    dr.close();
                }
            } catch (Exception e) {
                /* ignore */
            }
            if (sem.availablePermits() > 0) {
                sem.drainPermits();
            }
        }
        assertTrue("Check for valid OnSubscriptionMatched value", util.valueCompareCheck("testOnSubscriptionMatched", sl.subscriptionMatchCounter, 0, AbstractUtilities.Equality.GT));
        sl.reset();
    }

    @SuppressWarnings("unchecked")
    //@tTest
    public void testOnDataAvailable() {
        checkValidEntities();
        DataWriter<Msg> dw = null;
        DataReader<Msg> dr = null;
        boolean triggerOccured = false;
        try {
            subscriber.setListener(sl, DataAvailableStatus.class);
            dr = subscriber.createDataReader(topic);
            dw = publisher.createDataWriter(topic);
            dw.write(message);
            Thread.sleep(util.getWriteSleepTime());
            try {
                triggerOccured = sem.tryAcquire(10, TimeUnit.SECONDS);
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testOnDataAvailable", e, false));
            }
            assertTrue("OnDataAvailable listener status failed to trigger", triggerOccured);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testOnDataAvailable", e, false));
        } finally {
            try {
                if (dw != null) {
                    dw.close();
                }
                if (dr != null) {
                    dr.close();
                }
            } catch (Exception e) {
                /* ignore */
            }
            if (sem.availablePermits() > 0) {
                sem.drainPermits();
            }
        }
        assertTrue("Check for valid OnDataAvailable value", util.valueCompareCheck("testOnDataAvailable", sl.dataAvailableCounter, 0, AbstractUtilities.Equality.GT));
        sl.reset();
    }

    @SuppressWarnings("unchecked")
    //@tTest
    public void testOnDataOnReaders() {
        checkValidEntities();
        DataWriter<Msg> dw = null;
        DataReader<Msg> dr = null;
        boolean triggerOccured = false;
        try {
            subscriber.setListener(sl, DataOnReadersStatus.class);
            dr = subscriber.createDataReader(topic);
            dw = publisher.createDataWriter(topic);
            dw.write(message);
            Thread.sleep(util.getWriteSleepTime());
            try {
                triggerOccured = sem.tryAcquire(10, TimeUnit.SECONDS);
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testOnDataOnReaders", e, false));
            }
            assertTrue("OnDataOnReaders listener status failed to trigger", triggerOccured);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testOnDataOnReaders", e, false));
        } finally {
            try {
                if (dw != null) {
                    dw.close();
                }
                if (dr != null) {
                    dr.close();
                }
            } catch (Exception e) {
                /* ignore */
            }
            if (sem.availablePermits() > 0) {
                sem.drainPermits();
            }
        }
        assertTrue("Check for valid OnDataOnReaders value", util.valueCompareCheck("testOnDataOnReaders", sl.dataOnReadersCounter, 0, AbstractUtilities.Equality.GT));
        sl.reset();
    }

    @SuppressWarnings("unchecked")
    //@tTest
    public void testOnSampleRejected() {
        checkValidEntities();
        DataWriter<Msg> dw = null;
        DataReader<Msg> dr = null;
        boolean triggerOccured = false;
        try {
            int maxSamples = 4;
            int maxInstances = 2;
            int maxSamplesPerInstance = 2;

            DataReaderQos drq = subscriber.getDefaultDataReaderQos();
            History h = drq.getHistory().withKind(Kind.KEEP_LAST).withDepth(1);
            ResourceLimits rl = drq.getResourceLimits().withMaxSamples(maxSamples).withMaxInstances(maxInstances).withMaxSamplesPerInstance(maxSamplesPerInstance);
            drq = drq.withPolicy(h).withPolicy(rl);
            subscriber.setListener(sl, SampleRejectedStatus.class);
            dr = subscriber.createDataReader(topic, drq);

            dw = publisher.createDataWriter(topic);
            for (int i = 0; i <= maxSamples; i++) {
                message = new Msg(i, "testOnSampleRejected");
                dw.write(message);
            }
            Thread.sleep(util.getWriteSleepTime());
            try {
                triggerOccured = sem.tryAcquire(10, TimeUnit.SECONDS);
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testOnSampleRejected", e, false));
            }
            assertTrue("OnSampleRejected listener status failed to trigger", triggerOccured);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testOnSampleRejected", e, false));
        } finally {
            try {
                dr.take();
                if (dw != null) {
                    dw.close();
                }
                if (dr != null) {
                    dr.close();
                }
            } catch (Exception e) {
                /* ignore */
            }
            if (sem.availablePermits() > 0) {
                sem.drainPermits();
            }
        }
        assertTrue("Check for valid OnSampleRejected value", util.valueCompareCheck("testOnSampleRejected", sl.sampleRejectCounter, 0, AbstractUtilities.Equality.GT));
        sl.reset();
    }
}
