
package unittest.domain;

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
import org.omg.dds.core.event.DataAvailableEvent;
import org.omg.dds.core.event.DataOnReadersEvent;
import org.omg.dds.core.event.LivelinessChangedEvent;
import org.omg.dds.core.event.LivelinessLostEvent;
import org.omg.dds.core.event.OfferedDeadlineMissedEvent;
import org.omg.dds.core.event.OfferedIncompatibleQosEvent;
import org.omg.dds.core.event.PublicationMatchedEvent;
import org.omg.dds.core.event.RequestedDeadlineMissedEvent;
import org.omg.dds.core.event.RequestedIncompatibleQosEvent;
import org.omg.dds.core.event.SampleRejectedEvent;
import org.omg.dds.core.event.SubscriptionMatchedEvent;
import org.omg.dds.core.policy.Deadline;
import org.omg.dds.core.policy.Durability;
import org.omg.dds.core.policy.History;
import org.omg.dds.core.policy.History.Kind;
import org.omg.dds.core.policy.Liveliness;
import org.omg.dds.core.policy.ResourceLimits;
import org.omg.dds.core.status.DataAvailableStatus;
import org.omg.dds.core.status.DataOnReadersStatus;
import org.omg.dds.core.status.LivelinessChangedStatus;
import org.omg.dds.core.status.LivelinessLostStatus;
import org.omg.dds.core.status.OfferedDeadlineMissedStatus;
import org.omg.dds.core.status.OfferedIncompatibleQosStatus;
import org.omg.dds.core.status.PublicationMatchedStatus;
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
import org.eclipse.cyclonedds.test.DomainParticipantListenerClass;

public class DomainParticipantListenerTest {

    private final static int                      DOMAIN_ID   = 123;
    private static ServiceEnvironment             env;
    private static DomainParticipantFactory       dpf;
    private static AbstractUtilities              util        = AbstractUtilities.getInstance(DomainParticipantListenerTest.class);
    private final static Properties               prop        = new Properties();
    private static DomainParticipant              participant = null;
    private static Publisher                      publisher   = null;
    private static Subscriber                     subscriber  = null;
    private static Topic<Msg>                     topic       = null;
    private static String                         topicName   = "DomainParticipantListenerTest";
    private static Msg                            message     = new Msg(0, "DomainParticipantListenerTest");
    private final static Semaphore                sem         = new Semaphore(0);
    private static DomainParticipantListenerClass dpl         = null;

    @BeforeClass
    public static void init() {
        try {
            String serviceEnvironment = System.getProperty("JAVA5PSM_SERVICE_ENV");
            assertTrue("Check for valid ServiceEnvironment string", !serviceEnvironment.equals(""));
            System.setProperty(ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY, serviceEnvironment);
            env = ServiceEnvironment.createInstance(DomainParticipantListenerTest.class.getClassLoader());
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
            dpl = new DomainParticipantListenerClass(sem);
            assertTrue("Check for valid DomainParticipantListener object failed", dpl instanceof DomainParticipantListenerClass);
        } catch (Exception e) {
            fail("Exception occured while initiating the DomainParticipantListenerTest class: " + util.printException(e));
        }

    }

    private void checkValidEntities() {
        assertTrue("Check for valid DomainParticipant object failed", participant instanceof DomainParticipant);
        assertTrue("Check for valid Publisher object failed", publisher instanceof Publisher);
        assertTrue("Check for valid Subscriber object failed", subscriber instanceof Subscriber);
        assertTrue("Check for valid Topic object failed", topic instanceof Topic);
        assertTrue("Check for valid DomainParticipantListener object failed", dpl instanceof DomainParticipantListenerClass);
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
    @Test
    public void testOnRequestedIncompatibleQos() {
        checkValidEntities();
        DataWriter<Msg> dw = null;
        DataReader<Msg> dr = null;
        boolean triggerOccured = false;
        String function = "testOnRequestedIncompatibleQos";
        try {
            /*
             * should trigger OnRequestedIncompatibleQos because of non matching
             * durability qos
             */
            DataReaderQos drq = subscriber.getDefaultDataReaderQos();
            Durability dur = drq.getDurability().withTransient();
            drq = drq.withPolicy(dur);
            participant.setListener(dpl, RequestedIncompatibleQosStatus.class);
            dr = subscriber.createDataReader(topic, drq);
            dw = publisher.createDataWriter(topic);

            try {
                triggerOccured = sem.tryAcquire(10, TimeUnit.SECONDS);
                /* test status event */
                if (triggerOccured) {
                    assertTrue("check for valid RequestedIncompatibleQosEvent failed ", util.objectCheck(function, dpl.riqStatus));
                    RequestedIncompatibleQosEvent<?> o = dpl.riqStatus.clone();
                    assertTrue("check for valid clone of RequestedIncompatibleQosEvent failed ", util.objectCheck(function, o));
                    ServiceEnvironment se = o.getEnvironment();
                    assertTrue("check for valid ServiceEnvironment failed ", util.objectCheck(function, se));
                    DataReader<?> dar = o.getSource();
                    assertTrue("check for valid DataReader failed ", util.objectCheck(function, dar));
                    RequestedIncompatibleQosStatus status = o.getStatus();
                    assertTrue("check for valid Status failed ", util.objectCheck(function, status));
                    /* test status */
                    if (status != null) {
                        assertTrue("Check for valid OnRequestedIncompatibleQos totalcount value failed", util.valueCompareCheck(function, status.getTotalCount(), 0, AbstractUtilities.Equality.GT));
                        assertTrue("Check for valid OnRequestedIncompatibleQos totalcount changed value failed",
                                util.valueCompareCheck(function, status.getTotalCountChange(), 0, AbstractUtilities.Equality.GT));
                        assertTrue("Check for valid LastPolicyClass value failed", util.objectCheck(function, status.getLastPolicyClass()));
                        assertTrue("Check for valid Policy Set value failed", util.objectCompareCheck(function, status.getPolicies().isEmpty(), false));
                        assertTrue("Check for valid status environment failed", util.objectCheck(function, status.getEnvironment()));
                    }
                }
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck(function, e, false));
            }
            assertTrue("OnRequestedIncompatibleQos listener status failed to trigger", triggerOccured);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck(function, e, false));
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
        assertTrue("Check for valid OnRequestedIncompatibleQos value", util.valueCompareCheck(function, dpl.requestedIncompatibleQosCounter, 0, AbstractUtilities.Equality.GT));
        dpl.reset();
    }


    @SuppressWarnings("unchecked")
    @Test
    public void testOnRequestedDeadlineMissed() {
        checkValidEntities();
        boolean triggerOccured = false;
        DataWriter<Msg> dw = null;
        DataReader<Msg> dr = null;
        String function = "testOnRequestedDeadlineMissed";
        try {

            participant.setListener(dpl, RequestedDeadlineMissedStatus.class);
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
                /* test status event */
                if (triggerOccured) {
                    assertTrue("check for valid qosevent failed ", util.objectCheck(function, dpl.rdmStatus));
                    RequestedDeadlineMissedEvent<?> o = dpl.rdmStatus.clone();
                    assertTrue("check for valid clone of qosevent failed ", util.objectCheck(function, o));
                    ServiceEnvironment se = o.getEnvironment();
                    assertTrue("check for valid ServiceEnvironment failed ", util.objectCheck(function, se));
                    DataReader<?> dar = o.getSource();
                    assertTrue("check for valid DataReader failed ", util.objectCheck(function, dar));
                    RequestedDeadlineMissedStatus status = o.getStatus();
                    assertTrue("check for valid Status failed ", util.objectCheck(function, status));
                    /* test status */
                    if (status != null) {
                        assertTrue("Check for valid totalcount value failed",
                                util.valueCompareCheck(function, status.getTotalCount(), 0, AbstractUtilities.Equality.GT));
                        assertTrue("Check for valid totalcount changed value failed",
                                util.valueCompareCheck(function, status.getTotalCountChange(), 0, AbstractUtilities.Equality.GT));
                        assertTrue("Check for valid InstanceHandle failed", util.objectCheck(function, status.getLastInstanceHandle()));
                        assertTrue("Check for valid status environment failed", util.objectCheck(function, status.getEnvironment()));
                    }
                }
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck(function, e, false));
            }
            assertTrue("RequestedDeadlineMissed listener status failed to trigger", triggerOccured);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck(function, e, false));
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
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck(function, e, false));
            }
        }
        assertTrue("Check for valid RequestedDeadlineMissed value", util.valueCompareCheck(function, dpl.requestedDeadlineMissedCounter, 0, AbstractUtilities.Equality.GT));
        dpl.reset();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testonLivelinessChanged() {
        checkValidEntities();
        DataReader<Msg> dr = null;
        DataWriter<Msg> dw = null;
        boolean triggerOccured = false;
        String function = "testonLivelinessChanged";
        try {
            /*
             * should trigger testonLivelinessChanged as sleep > lease duration
             */
            DataWriterQos dwq = publisher.getDefaultDataWriterQos();
            Liveliness ll = dwq.getLiveliness().withManualByTopic().withLeaseDuration(1, TimeUnit.SECONDS);
            dwq = dwq.withPolicy(ll);
            dw = publisher.createDataWriter(topic, dwq);
            DataReaderQos drq = subscriber.getDefaultDataReaderQos();
            ll = drq.getLiveliness().withManualByTopic().withLeaseDuration(1, TimeUnit.SECONDS);
            drq = drq.withPolicy(ll);
            dr = subscriber.createDataReader(topic, drq);
            participant.setListener(dpl, LivelinessChangedStatus.class);
            try {
                Thread.sleep(1200);
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck(function, e, false));
            }
            try {
                triggerOccured = sem.tryAcquire(10, TimeUnit.SECONDS);
                /* test status event */
                if (triggerOccured) {
                    assertTrue("check for valid qosevent failed ", util.objectCheck(function, dpl.llcStatus));
                    LivelinessChangedEvent<?> o = dpl.llcStatus.clone();
                    assertTrue("check for valid clone of qosevent failed ", util.objectCheck(function, o));
                    ServiceEnvironment se = o.getEnvironment();
                    assertTrue("check for valid ServiceEnvironment failed ", util.objectCheck(function, se));
                    DataReader<?> dar = o.getSource();
                    assertTrue("check for valid DataReader failed ", util.objectCheck(function, dar));
                    LivelinessChangedStatus status = o.getStatus();
                    assertTrue("check for valid Status failed ", util.objectCheck(function, status));
                    /* test status */
                    if (status != null) {
                        assertTrue("Check for valid getAliveCount value failed", util.valueCompareCheck(function, status.getAliveCount(), 0, AbstractUtilities.Equality.EQ));
                        assertTrue("Check for valid getAliveCountChange value failed", util.valueCompareCheck(function, status.getAliveCountChange(), 0, AbstractUtilities.Equality.GT));
                        assertTrue("Check for valid getLastPublicationHandle failed", util.objectCheck(function, status.getLastPublicationHandle()));
                        assertTrue("Check for valid getNotAliveCount value failed", util.valueCompareCheck(function, status.getNotAliveCount(), 0, AbstractUtilities.Equality.GT));
                        assertTrue("Check for valid getNotAliveCountChange value failed",
                                util.valueCompareCheck(function, status.getNotAliveCountChange(), 0, AbstractUtilities.Equality.GT));
                        assertTrue("Check for valid status environment failed", util.objectCheck(function, status.getEnvironment()));
                    }
                }
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck(function, e, false));
            }
            assertTrue("testonLivelinessChanged listener status failed to trigger", triggerOccured);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck(function, e, false));
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
        assertTrue("Check for valid testonLivelinessChanged value", util.valueCompareCheck(function, dpl.livelinessChangedCounter, 0, AbstractUtilities.Equality.GT));
        dpl.reset();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testOnSubscriptionMatched() {
        checkValidEntities();
        DataWriter<Msg> dw = null;
        DataReader<Msg> dr = null;
        boolean triggerOccured = false;
        String function = "testOnSubscriptionMatched";
        try {
            participant.setListener(dpl, SubscriptionMatchedStatus.class);
            dr = subscriber.createDataReader(topic);
            dw = publisher.createDataWriter(topic);
            try {
                triggerOccured = sem.tryAcquire(10, TimeUnit.SECONDS);
                /* test status event */
                if (triggerOccured) {
                    assertTrue("check for valid qosevent failed ", util.objectCheck(function, dpl.smStatus));
                    SubscriptionMatchedEvent<?> o = dpl.smStatus.clone();
                    assertTrue("check for valid clone of qosevent failed ", util.objectCheck(function, o));
                    ServiceEnvironment se = o.getEnvironment();
                    assertTrue("check for valid ServiceEnvironment failed ", util.objectCheck(function, se));
                    DataReader<?> dar = o.getSource();
                    assertTrue("check for valid DataReader failed ", util.objectCheck(function, dar));
                    SubscriptionMatchedStatus status = o.getStatus();
                    assertTrue("check for valid Status failed ", util.objectCheck(function, status));
                    /* test status */
                    if (status != null) {
                        assertTrue("Check for valid getCurrentCount value failed", util.valueCompareCheck(function, status.getCurrentCount(), 0, AbstractUtilities.Equality.GT));
                        assertTrue("Check for valid getCurrentCountChange value failed", util.valueCompareCheck(function, status.getCurrentCountChange(), 0, AbstractUtilities.Equality.GT));
                        assertTrue("Check for valid getLastPublicationHandle failed", util.objectCheck(function, status.getLastPublicationHandle()));
                        assertTrue("Check for valid getTotalCount value failed", util.valueCompareCheck(function, status.getTotalCount(), 0, AbstractUtilities.Equality.GT));
                        assertTrue("Check for valid getTotalCountChange value failed", util.valueCompareCheck(function, status.getTotalCountChange(), 0, AbstractUtilities.Equality.GT));
                        assertTrue("Check for valid status environment failed", util.objectCheck(function, status.getEnvironment()));
                    }
                }
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck(function, e, false));
            }
            assertTrue("OnSubscriptionMatched listener status failed to trigger", triggerOccured);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck(function, e, false));
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
        assertTrue("Check for valid OnSubscriptionMatched value", util.valueCompareCheck(function, dpl.subscriptionMatchCounter, 0, AbstractUtilities.Equality.GT));
        dpl.reset();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testOnDataAvailable() {
        checkValidEntities();
        DataWriter<Msg> dw = null;
        DataReader<Msg> dr = null;
        boolean triggerOccured = false;
        String function = "testOnDataAvailable";
        try {
            participant.setListener(dpl, DataAvailableStatus.class);
            dr = subscriber.createDataReader(topic);
            dw = publisher.createDataWriter(topic);
            dw.write(message);
            Thread.sleep(util.getWriteSleepTime());
            try {
                triggerOccured = sem.tryAcquire(10, TimeUnit.SECONDS);
                /* test status event */
                if (triggerOccured) {
                    assertTrue("check for valid qosevent failed ", util.objectCheck(function, dpl.daStatus));
                    DataAvailableEvent<?> o = dpl.daStatus.clone();
                    assertTrue("check for valid clone of qosevent failed ", util.objectCheck(function, o));
                    ServiceEnvironment se = o.getEnvironment();
                    assertTrue("check for valid ServiceEnvironment failed ", util.objectCheck(function, se));
                    DataReader<?> dar = o.getSource();
                    assertTrue("check for valid DataReader failed ", util.objectCheck(function, dar));
                    DataAvailableStatus status = o.getStatus();
                    assertTrue("check for valid Status failed ", util.objectCheck(function, status));
                    /* test status */
                    if (status != null) {
                        assertTrue("Check for valid status environment failed", util.objectCheck(function, status.getEnvironment()));
                    }
                }
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck(function, e, false));
            }
            assertTrue("OnDataAvailable listener status failed to trigger", triggerOccured);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck(function, e, false));
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
        assertTrue("Check for valid OnDataAvailable value", util.valueCompareCheck(function, dpl.dataAvailableCounter, 0, AbstractUtilities.Equality.GT));
        dpl.reset();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testOnDataOnReaders() {
        checkValidEntities();
        DataWriter<Msg> dw = null;
        DataReader<Msg> dr = null;
        boolean triggerOccured = false;
        String function = "testOnDataOnReaders";
        try {
            participant.setListener(dpl, DataOnReadersStatus.class);
            dr = subscriber.createDataReader(topic);
            dw = publisher.createDataWriter(topic);
            dw.write(message);
            Thread.sleep(util.getWriteSleepTime());
            try {
                triggerOccured = sem.tryAcquire(10, TimeUnit.SECONDS);
                /* test status event */
                if (triggerOccured) {
                    assertTrue("check for valid qosevent failed ", util.objectCheck(function, dpl.dorStatus));
                    DataOnReadersEvent o = dpl.dorStatus.clone();
                    assertTrue("check for valid clone of qosevent failed ", util.objectCheck(function, o));
                    ServiceEnvironment se = o.getEnvironment();
                    assertTrue("check for valid ServiceEnvironment failed ", util.objectCheck(function, se));
                    Subscriber src = o.getSource();
                    assertTrue("check for valid DataReader failed ", util.objectCheck(function, src));
                    DataOnReadersStatus status = o.getStatus();
                    assertTrue("check for valid Status failed ", util.objectCheck(function, status));
                    /* test status */
                    if (status != null) {
                        assertTrue("Check for valid status environment failed", util.objectCheck(function, status.getEnvironment()));
                    }
                }
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck(function, e, false));
            }
            assertTrue("OnDataOnReaders listener status failed to trigger", triggerOccured);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck(function, e, false));
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
        assertTrue("Check for valid OnDataOnReaders value", util.valueCompareCheck(function, dpl.dataOnReadersCounter, 0, AbstractUtilities.Equality.GT));
        dpl.reset();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testOnSampleRejected() {
        checkValidEntities();
        DataWriter<Msg> dw = null;
        DataReader<Msg> dr = null;
        boolean triggerOccured = false;
        String function = "testOnSampleRejected";
        try {
            int maxSamples = 4;
            int maxInstances = 2;
            int maxSamplesPerInstance = 2;

            DataReaderQos drq = subscriber.getDefaultDataReaderQos();
            History h = drq.getHistory().withKind(Kind.KEEP_LAST).withDepth(1);
            ResourceLimits rl = drq.getResourceLimits().withMaxSamples(maxSamples).withMaxInstances(maxInstances).withMaxSamplesPerInstance(maxSamplesPerInstance);
            drq = drq.withPolicy(h).withPolicy(rl);
            participant.setListener(dpl, SampleRejectedStatus.class);
            dr = subscriber.createDataReader(topic, drq);

            dw = publisher.createDataWriter(topic);
            for (int i = 0; i <= maxSamples; i++) {
                message = new Msg(i, function);
                dw.write(message);
            }
            Thread.sleep(util.getWriteSleepTime());
            try {
                triggerOccured = sem.tryAcquire(10, TimeUnit.SECONDS);
                /* test status event */
                if (triggerOccured) {
                    assertTrue("check for valid qosevent failed ", util.objectCheck(function, dpl.srStatus));
                    SampleRejectedEvent<?> o = dpl.srStatus.clone();
                    assertTrue("check for valid clone of qosevent failed ", util.objectCheck(function, o));
                    ServiceEnvironment se = o.getEnvironment();
                    assertTrue("check for valid ServiceEnvironment failed ", util.objectCheck(function, se));
                    DataReader<?> dar = o.getSource();
                    assertTrue("check for valid DataReader failed ", util.objectCheck(function, dar));
                    SampleRejectedStatus status = o.getStatus();
                    assertTrue("check for valid Status failed ", util.objectCheck(function, status));
                    /* test status */
                    if (status != null) {
                        assertTrue("Check for valid getLastReason value failed", util.objectCheck(function, status.getLastReason()));
                        assertTrue("Check for valid getLastInstanceHandle failed", util.objectCheck(function, status.getLastInstanceHandle()));
                        assertTrue("Check for valid getTotalCount value failed", util.valueCompareCheck(function, status.getTotalCount(), 0, AbstractUtilities.Equality.GT));
                        assertTrue("Check for valid getTotalCountChange value failed", util.valueCompareCheck(function, status.getTotalCountChange(), 0, AbstractUtilities.Equality.GT));
                        assertTrue("Check for valid status environment failed", util.objectCheck(function, status.getEnvironment()));
                    }
                }
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck(function, e, false));
            }
            assertTrue("OnSampleRejected listener status failed to trigger", triggerOccured);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck(function, e, false));
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
        assertTrue("Check for valid OnSampleRejected value", util.valueCompareCheck(function, dpl.sampleRejectCounter, 0, AbstractUtilities.Equality.GT));
        dpl.reset();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testOnOfferedIncompatibleQos() {
        checkValidEntities();
        DataReader<Msg> dr = null;
        DataWriter<Msg> dw = null;
        boolean triggerOccured = false;
        String function = "testOnOfferedIncompatibleQos";
        try {
            /*
             * should trigger OnOfferedIncompatibleQos because of non matching
             * durability qos
             */
            DataReaderQos drq = subscriber.getDefaultDataReaderQos();
            Durability dur = drq.getDurability().withTransient();
            drq = drq.withPolicy(dur);
            participant.setListener(dpl, OfferedIncompatibleQosStatus.class);
            dr = subscriber.createDataReader(topic, drq);
            dw = publisher.createDataWriter(topic);
            try {
                triggerOccured = sem.tryAcquire(10, TimeUnit.SECONDS);
                /* test status event */
                if (triggerOccured) {
                    assertTrue("check for valid QosEvent failed ", util.objectCheck(function, dpl.oiqStatus));
                    OfferedIncompatibleQosEvent<?> o = dpl.oiqStatus.clone();
                    assertTrue("check for valid clone of event failed ", util.objectCheck(function, o));
                    ServiceEnvironment se = o.getEnvironment();
                    assertTrue("check for valid ServiceEnvironment failed ", util.objectCheck(function, se));
                    DataWriter<?> src = o.getSource();
                    assertTrue("check for valid DataReader failed ", util.objectCheck(function, src));
                    OfferedIncompatibleQosStatus status = o.getStatus();
                    assertTrue("check for valid Status failed ", util.objectCheck(function, status));
                    /* test status */
                    if (status != null) {
                        assertTrue("Check for valid totalcount value failed", util.valueCompareCheck(function, status.getTotalCount(), 0, AbstractUtilities.Equality.GT));
                        assertTrue("Check for valid totalcount changed value failed", util.valueCompareCheck(function, status.getTotalCountChange(), 0, AbstractUtilities.Equality.GT));
                        assertTrue("Check for valid LastPolicyClass value failed", util.objectCheck(function, status.getLastPolicyClass()));
                        assertTrue("Check for valid Policy Set value failed", util.objectCompareCheck(function, status.getPolicies().isEmpty(), false));
                        assertTrue("Check for valid status environment failed", util.objectCheck(function, status.getEnvironment()));
                    }
                }
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck(function, e, false));
            }
            assertTrue("OnOfferedIncompatibleQos listener status failed to trigger", triggerOccured);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck(function, e, false));
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
        assertTrue("Check for valid OnOfferedIncompatibleQos value", util.valueCompareCheck(function, dpl.offeredIncompatibleQosCounter, 0, AbstractUtilities.Equality.GT));
        dpl.reset();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testOnOfferedDeadlineMissed() {
        checkValidEntities();
        boolean triggerOccured = false;
        DataWriter<Msg> dw = null;
        String function = "testOnOfferedDeadlineMissed";
        try {
            participant.setListener(dpl, OfferedDeadlineMissedStatus.class);
            DataWriterQos dwq = publisher.getDefaultDataWriterQos();
            Deadline du = dwq.getDeadline().withPeriod(1, TimeUnit.SECONDS);
            dwq = dwq.withPolicy(du);
            dw = publisher.createDataWriter(topic, dwq);
            dw.write(message);
            /*
             * should trigger OnOfferedDeadlineMissed because of sleep >
             * deadline time
             */
            try {
                triggerOccured = sem.tryAcquire(10, TimeUnit.SECONDS);
                /* test status event */
                if (triggerOccured) {
                    assertTrue("check for valid qosevent failed ", util.objectCheck(function, dpl.odmStatus));
                    OfferedDeadlineMissedEvent<?> o = dpl.odmStatus.clone();
                    assertTrue("check for valid clone of qosevent failed ", util.objectCheck(function, o));
                    ServiceEnvironment se = o.getEnvironment();
                    assertTrue("check for valid ServiceEnvironment failed ", util.objectCheck(function, se));
                    DataWriter<?> src = o.getSource();
                    assertTrue("check for valid DataReader failed ", util.objectCheck(function, src));
                    OfferedDeadlineMissedStatus status = o.getStatus();
                    assertTrue("check for valid Status failed ", util.objectCheck(function, status));
                    /* test status */
                    if (status != null) {
                        assertTrue("Check for valid totalcount value failed", util.valueCompareCheck(function, status.getTotalCount(), 0, AbstractUtilities.Equality.GT));
                        assertTrue("Check for valid totalcount changed value failed", util.valueCompareCheck(function, status.getTotalCountChange(), 0, AbstractUtilities.Equality.GT));
                        assertTrue("Check for valid InstanceHandle failed", util.objectCheck(function, status.getLastInstanceHandle()));
                        assertTrue("Check for valid status environment failed", util.objectCheck(function, status.getEnvironment()));
                    }
                }
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck(function, e, false));
            }
            assertTrue("OnOfferedDeadlineMissed listener status failed to trigger", triggerOccured);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck(function, e, false));
        } finally {
            if (sem.availablePermits() > 0) {
                sem.drainPermits();
            }
            try {
                dw.close();
            } catch (Exception e) {
                /* ignore */
            }
        }
        assertTrue("Check for valid OnOfferedDeadlineMissed value", util.valueCompareCheck(function, dpl.offeredDeadlineMissedCounter, 0, AbstractUtilities.Equality.GT));
        dpl.reset();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testOnLivelinessLost() {
        checkValidEntities();
        DataWriter<Msg> dw = null;
        boolean triggerOccured = false;
        String function = "testOnLivelinessLost";
        try {
            /*
             * should trigger OnLivelinessLost as sleep > lease duration
             */
            DataWriterQos dwq = publisher.getDefaultDataWriterQos();
            Liveliness ll = dwq.getLiveliness().withManualByTopic().withLeaseDuration(2, TimeUnit.SECONDS);
            dwq = dwq.withPolicy(ll);
            participant.setListener(dpl, LivelinessLostStatus.class);
            dw = publisher.createDataWriter(topic, dwq);
            try {
                triggerOccured = sem.tryAcquire(10, TimeUnit.SECONDS);
                /* test status event */
                if (triggerOccured) {
                    assertTrue("check for valid qosevent failed ", util.objectCheck(function, dpl.llStatus));
                    LivelinessLostEvent<?> o = dpl.llStatus.clone();
                    assertTrue("check for valid clone of qosevent failed ", util.objectCheck(function, o));
                    ServiceEnvironment se = o.getEnvironment();
                    assertTrue("check for valid ServiceEnvironment failed ", util.objectCheck(function, se));
                    DataWriter<?> src = o.getSource();
                    assertTrue("check for valid DataReader failed ", util.objectCheck(function, src));
                    LivelinessLostStatus status = o.getStatus();
                    assertTrue("check for valid Status failed ", util.objectCheck(function, status));
                    /* test status */
                    if (status != null) {
                        assertTrue("Check for valid getTotalCount value failed", util.valueCompareCheck(function, status.getTotalCount(), 0, AbstractUtilities.Equality.GT));
                        assertTrue("Check for valid getTotalCountChange value failed", util.valueCompareCheck(function, status.getTotalCountChange(), 0, AbstractUtilities.Equality.GT));
                        assertTrue("Check for valid status environment failed", util.objectCheck(function, status.getEnvironment()));
                    }
                }
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck(function, e, false));
            }
            assertTrue("OnLivelinessLost listener status failed to trigger", triggerOccured);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck(function, e, false));
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
        assertTrue("Check for valid OnLivelinessLost value", util.valueCompareCheck(function, dpl.livelinessLostCounter, 0, AbstractUtilities.Equality.GT));
        dpl.reset();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testOnPublicationMatched() {
        checkValidEntities();
        DataWriter<Msg> dw = null;
        DataReader<Msg> dr = null;
        boolean triggerOccured = false;
        String function = "testOnPublicationMatched";
        try {
            dw = publisher.createDataWriter(topic);
            participant.setListener(dpl, PublicationMatchedStatus.class);
            dr = subscriber.createDataReader(topic);
            try {
                triggerOccured = sem.tryAcquire(10, TimeUnit.SECONDS);
                /* test status event */
                if (triggerOccured) {
                    assertTrue("check for valid qosevent failed ", util.objectCheck(function, dpl.pmStatus));
                    PublicationMatchedEvent<?> o = dpl.pmStatus.clone();
                    assertTrue("check for valid clone of qosevent failed ", util.objectCheck(function, o));
                    ServiceEnvironment se = o.getEnvironment();
                    assertTrue("check for valid ServiceEnvironment failed ", util.objectCheck(function, se));
                    DataWriter<?> src = o.getSource();
                    assertTrue("check for valid DataReader failed ", util.objectCheck(function, src));
                    PublicationMatchedStatus status = o.getStatus();
                    assertTrue("check for valid Status failed ", util.objectCheck(function, status));
                    /* test status */
                    if (status != null) {
                        assertTrue("Check for valid getCurrentCount value failed", util.valueCompareCheck(function, status.getCurrentCount(), 0, AbstractUtilities.Equality.GT));
                        assertTrue("Check for valid getCurrentCountChange value failed", util.valueCompareCheck(function, status.getCurrentCountChange(), 0, AbstractUtilities.Equality.GT));
                        assertTrue("Check for valid getLastSubscriptionHandle failed", util.objectCheck(function, status.getLastSubscriptionHandle()));
                        assertTrue("Check for valid getTotalCount value failed", util.valueCompareCheck(function, status.getTotalCount(), 0, AbstractUtilities.Equality.GT));
                        assertTrue("Check for valid getTotalCountChange value failed", util.valueCompareCheck(function, status.getTotalCountChange(), 0, AbstractUtilities.Equality.GT));
                        assertTrue("Check for valid status environment failed", util.objectCheck(function, status.getEnvironment()));
                    }
                }
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck(function, e, false));
            }
            assertTrue("OnPublicationMatched listener status failed to trigger", triggerOccured);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck(function, e, false));
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
        assertTrue("Check for valid OnPublicationMatched value", util.valueCompareCheck(function, dpl.publicationMatchCounter, 0, AbstractUtilities.Equality.GT));
        dpl.reset();
    }
}
