
package unittest.core;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.HashSet;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.dds.core.AlreadyClosedException;
import org.omg.dds.core.Condition;
import org.omg.dds.core.Duration;
import org.omg.dds.core.GuardCondition;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.StatusCondition;
import org.omg.dds.core.WaitSet;
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
import org.omg.dds.core.status.Status;
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

public class WaitsetopenspliceTest {

    private final static int                DOMAIN_ID   = 123;
    private static ServiceEnvironment       env;
    private static DomainParticipantFactory dpf;
    private static AbstractUtilities        util        = AbstractUtilities.getInstance(WaitsetopenspliceTest.class);
    private final static Properties         prop        = new Properties();
    private static DomainParticipant        participant = null;
    private static Publisher                publisher   = null;
    private static Subscriber               subscriber  = null;
    private static Topic<Msg>               topic       = null;
    private static String                   topicName   = "WaitsetTest";
    private static Msg                      message     = new Msg(0, "WaitsetTest");
    private static WaitSet                  waitset     = null;

    @BeforeClass
    public static void init() {
        try {
            String serviceEnvironment = System.getProperty("JAVA5PSM_SERVICE_ENV");
            assertTrue("Check for valid ServiceEnvironment string", !serviceEnvironment.equals(""));
            System.setProperty(ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY, serviceEnvironment);
            env = ServiceEnvironment.createInstance(WaitsetopenspliceTest.class.getClassLoader());
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
            waitset = env.getSPI().newWaitSet();
            assertTrue("Check for valid Waitset object failed", waitset instanceof WaitSet);

        } catch (Exception e) {
            fail("Exception occured while initiating the WaitsetTest class: " + util.printException(e));
        }

    }

    private void checkValidEntities() {
        assertTrue("Check for valid DomainParticipant object failed", participant instanceof DomainParticipant);
        assertTrue("Check for valid Publisher object failed", publisher instanceof Publisher);
        assertTrue("Check for valid Subscriber object failed", subscriber instanceof Subscriber);
        assertTrue("Check for valid Topic object failed", topic instanceof Topic);
        assertTrue("Check for valid Waitset object failed", waitset instanceof WaitSet);
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
     * Test method for {@link org.omg.dds.core.WaitSet#waitForConditions()}.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testWaitForConditions() {
        WaitSet w = null;
        DataWriter<Msg> dw = null;
        DataReader<Msg> dr = null;
        StatusCondition<DataReader<Msg>> statusCond = null;
        try {
            dr = subscriber.createDataReader(topic);

            w = env.getSPI().newWaitSet();
            statusCond = dr.getStatusCondition();
            statusCond.setEnabledStatuses(DataAvailableStatus.class);
            w.attachCondition(statusCond);

            dw = publisher.createDataWriter(topic);
            dw.write(message);
            Thread.sleep(util.getWriteSleepTime());

        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testWaitForConditions", e, false));
        }
        try {
            w.waitForConditions();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testWaitForConditions", e, false));
        } finally {
            try {
                w.detachCondition(statusCond);
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
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.core.WaitSet#waitForConditions(java.util.Collection, org.omg.dds.core.Duration)}
     * .
     */
    @Test
    public void testWaitForConditionsCollectionOfConditionDurationNull() {
        WaitSet w = null;
        boolean exceptionOccured = false;
        Duration duration = Duration.newDuration(5, TimeUnit.SECONDS, env);
        HashSet<Condition> triggeredConditions = null;
        try {
            w = env.getSPI().newWaitSet();
            w.waitForConditions(triggeredConditions, duration);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e),
                    util.exceptionCheck("testWaitForConditionsCollectionOfConditionDurationNull", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("Check for IllegalArgumentException occured failed", exceptionOccured);
    }

    /**
     * Test method for
     * {@link org.omg.dds.core.WaitSet#waitForConditions(java.util.Collection, org.omg.dds.core.Duration)}
     * .
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testWaitForConditionsCollectionOfConditionDuration() {
        WaitSet w = null;
        DataWriter<Msg> dw = null;
        DataReader<Msg> dr = null;
        StatusCondition<DataReader<Msg>> statusCond = null;
        HashSet<Condition> triggeredConditions = new HashSet<Condition>();
        Duration dur = Duration.newDuration(5, TimeUnit.SECONDS, env);
        try {
            dr = subscriber.createDataReader(topic);

            w = env.getSPI().newWaitSet();
            statusCond = dr.getStatusCondition();
            statusCond.setEnabledStatuses(DataAvailableStatus.class);
            w.attachCondition(statusCond);

            dw = publisher.createDataWriter(topic);
            dw.write(message);

        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testWaitForConditionsCollectionOfConditionDuration", e, false));
        }
        try {
            w.waitForConditions(triggeredConditions, dur);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testWaitForConditionsCollectionOfConditionDuration", e, false));
        } finally {
            try {
                w.detachCondition(statusCond);
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
        }
        assertTrue("Check for valid StatusCondition failed ", util.objectCompareCheck("testWaitForConditionsCollectionOfConditionDuration", triggeredConditions.isEmpty(), false));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testOnRequestedIncompatibleQosWaitset() {
        checkValidEntities();
        DataWriter<Msg> dw = null;
        DataReader<Msg> dr = null;
        StatusCondition<DataReader<Msg>> statusCond = null;
        try {
            /*
             * should trigger OnRequestedIncompatibleQos because of non matching
             * durability qos
             */
            DataReaderQos drq = subscriber.getDefaultDataReaderQos();
            Durability dur = drq.getDurability().withTransient();
            drq = drq.withPolicy(dur);
            dr = subscriber.createDataReader(topic, drq);
            statusCond = dr.getStatusCondition();
            statusCond.setEnabledStatuses(RequestedIncompatibleQosStatus.class);
            waitset.attachCondition(statusCond);

            dw = publisher.createDataWriter(topic);

            try {
                HashSet<Condition> triggeredConditions = new HashSet<Condition>();
                waitset.waitForConditions(triggeredConditions, 5, TimeUnit.SECONDS);
                for (Condition cond : triggeredConditions) {
                    assertTrue("No matching conditions: ", util.objectCompareCheck("testOnRequestedIncompatibleQosWaitset", cond, statusCond));
                }
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testOnRequestedIncompatibleQosWaitset", e, false));
            }
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testOnRequestedIncompatibleQosWaitset", e, false));
        } finally {
            waitset.detachCondition(statusCond);
            if (dw != null) {
                dw.close();
            }
            if (dr != null) {
                dr.close();
            }

        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testOnRequestedDeadlineMissedWaitset() {
        checkValidEntities();
        DataWriter<Msg> dw = null;
        DataReader<Msg> dr = null;
        StatusCondition<DataReader<Msg>> statusCond = null;
        try {

            DataReaderQos drq = subscriber.getDefaultDataReaderQos();
            Deadline du = drq.getDeadline().withPeriod(1, TimeUnit.SECONDS);
            drq = drq.withPolicy(du);
            dr = subscriber.createDataReader(topic, drq);

            statusCond = dr.getStatusCondition();
            statusCond.setEnabledStatuses(RequestedDeadlineMissedStatus.class);
            waitset.attachCondition(statusCond);

            DataWriterQos dwq = publisher.getDefaultDataWriterQos();
            du = dwq.getDeadline().withPeriod(1, TimeUnit.SECONDS);
            dwq = dwq.withPolicy(du);
            dw = publisher.createDataWriter(topic, dwq);
            dw.write(message);
            /*
             * should trigger OnOfferedDeadlineMissed because of timeout >
             * deadline time
             */
            try {
                HashSet<Condition> triggeredConditions = new HashSet<Condition>();
                waitset.waitForConditions(triggeredConditions, 5, TimeUnit.SECONDS);
                for (Condition cond : triggeredConditions) {
                    assertTrue("No matching conditions: ", util.objectCompareCheck("testOnRequestedDeadlineMissedWaitset", cond, statusCond));
                }
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testOnRequestedDeadlineMissedWaitset", e, false));
            }
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testOnRequestedDeadlineMissedWaitset", e, false));
        } finally {
            try {
                waitset.detachCondition(statusCond);
                dr.take();
                if (dw != null) {
                    dw.close();
                }
                if (dr != null) {
                    dr.close();
                }
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testOnRequestedDeadlineMissedWaitset", e, false));
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testonLivelinessChangedWaitset() {
        checkValidEntities();
        DataReader<Msg> dr = null;
        DataWriter<Msg> dw = null;
        StatusCondition<DataReader<Msg>> statusCond = null;
        try {
            /*
             * should trigger testonLivelinessChangedWaitset as sleep > lease
             * duration
             */

            DataWriterQos dwq = publisher.getDefaultDataWriterQos();
            Liveliness ll = dwq.getLiveliness().withManualByTopic().withLeaseDuration(2, TimeUnit.SECONDS);
            dwq = dwq.withPolicy(ll);
            dw = publisher.createDataWriter(topic, dwq);

            DataReaderQos drq = subscriber.getDefaultDataReaderQos();
            ll = drq.getLiveliness().withManualByTopic().withLeaseDuration(2, TimeUnit.SECONDS);
            drq = drq.withPolicy(ll);
            dr = subscriber.createDataReader(topic, drq);

            statusCond = dr.getStatusCondition();
            statusCond.setEnabledStatuses(LivelinessChangedStatus.class);
            waitset.attachCondition(statusCond);

            try {
                HashSet<Condition> triggeredConditions = new HashSet<Condition>();
                waitset.waitForConditions(triggeredConditions, 5, TimeUnit.SECONDS);
                for (Condition cond : triggeredConditions) {
                    assertTrue("No matching conditions: ", util.objectCompareCheck("testonLivelinessChangedWaitset", cond, statusCond));
                }
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testonLivelinessChangedWaitset", e, false));
            }
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testonLivelinessChangedWaitset", e, false));
        } finally {
            try {
                waitset.detachCondition(statusCond);
                if (dw != null) {
                    dw.close();
                }
                if (dr != null) {
                    dr.close();
                }

            } catch (Exception e) {
                /* ignore */
            }

        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testOnSubscriptionMatchedWaitset() {
        checkValidEntities();
        DataWriter<Msg> dw = null;
        DataReader<Msg> dr = null;
        StatusCondition<DataReader<Msg>> statusCond = null;
        try {
            dr = subscriber.createDataReader(topic);

            statusCond = dr.getStatusCondition();
            statusCond.setEnabledStatuses(SubscriptionMatchedStatus.class);
            waitset.attachCondition(statusCond);

            dw = publisher.createDataWriter(topic);
            try {
                HashSet<Condition> triggeredConditions = new HashSet<Condition>();
                waitset.waitForConditions(triggeredConditions, 5, TimeUnit.SECONDS);
                for (Condition cond : triggeredConditions) {
                    assertTrue("No matching conditions: ", util.objectCompareCheck("testOnSubscriptionMatchedWaitset", cond, statusCond));
                }
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testOnSubscriptionMatchedWaitset", e, false));
            }
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testOnSubscriptionMatchedWaitset", e, false));
        } finally {
            try {
                waitset.detachCondition(statusCond);
                if (dw != null) {
                    dw.close();
                }
                if (dr != null) {
                    dr.close();
                }

            } catch (Exception e) {
                /* ignore */
            }

        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testOnDataAvailableWaitset() {
        checkValidEntities();
        DataWriter<Msg> dw = null;
        DataReader<Msg> dr = null;
        StatusCondition<DataReader<Msg>> statusCond = null;
        try {
            dr = subscriber.createDataReader(topic);

            statusCond = dr.getStatusCondition();
            statusCond.setEnabledStatuses(DataAvailableStatus.class);
            waitset.attachCondition(statusCond);

            dw = publisher.createDataWriter(topic);
            dw.write(message);
            try {
                HashSet<Condition> triggeredConditions = new HashSet<Condition>();
                waitset.waitForConditions(triggeredConditions, 5, TimeUnit.SECONDS);
                for (Condition cond : triggeredConditions) {
                    assertTrue("No matching conditions: ", util.objectCompareCheck("testOnDataAvailableWaitset", cond, statusCond));
                }
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testOnDataAvailableWaitset", e, false));
            }
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testOnDataAvailableWaitset", e, false));
        } finally {
            try {
                waitset.detachCondition(statusCond);
                if (dw != null) {
                    dw.close();
                }
                if (dr != null) {
                    dr.close();
                }

            } catch (Exception e) {
                /* ignore */
            }

        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testOnDataOnReadersWaitset() {
        checkValidEntities();
        DataWriter<Msg> dw = null;
        DataReader<Msg> dr = null;
        StatusCondition<DataReader<Msg>> statusCond = null;
        try {
            dr = subscriber.createDataReader(topic);

            statusCond = dr.getStatusCondition();
            statusCond.setEnabledStatuses(DataOnReadersStatus.class);
            waitset.attachCondition(statusCond);

            dw = publisher.createDataWriter(topic);
            dw.write(message);
            try {
                HashSet<Condition> triggeredConditions = new HashSet<Condition>();
                waitset.waitForConditions(triggeredConditions, 5, TimeUnit.SECONDS);
                for (Condition cond : triggeredConditions) {
                    assertTrue("No matching conditions: ", util.objectCompareCheck("testOnDataOnReadersWaitset", cond, statusCond));
                }
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testOnDataOnReadersWaitset", e, false));
            }
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testOnDataOnReadersWaitset", e, false));
        } finally {
            try {
                waitset.detachCondition(statusCond);
                if (dw != null) {
                    dw.close();
                }
                if (dr != null) {
                    dr.close();
                }

            } catch (Exception e) {
                /* ignore */
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testOnSampleRejectedWaitset() {
        checkValidEntities();
        DataWriter<Msg> dw = null;
        DataReader<Msg> dr = null;
        StatusCondition<DataReader<Msg>> statusCond = null;
        try {
            int maxSamples = 4;
            int maxInstances = 2;
            int maxSamplesPerInstance = 2;

            DataReaderQos drq = subscriber.getDefaultDataReaderQos();
            History h = drq.getHistory().withKind(Kind.KEEP_LAST).withDepth(1);
            ResourceLimits rl = drq.getResourceLimits().withMaxSamples(maxSamples).withMaxInstances(maxInstances).withMaxSamplesPerInstance(maxSamplesPerInstance);
            drq = drq.withPolicy(h).withPolicy(rl);
            dr = subscriber.createDataReader(topic, drq);

            statusCond = dr.getStatusCondition();
            statusCond.setEnabledStatuses(SampleRejectedStatus.class);
            waitset.attachCondition(statusCond);

            dw = publisher.createDataWriter(topic);
            for (int i = 0; i <= maxSamples; i++) {
                message = new Msg(i, "testOnSampleRejectedWaitset");
                dw.write(message);
            }
            try {
                HashSet<Condition> triggeredConditions = new HashSet<Condition>();
                waitset.waitForConditions(triggeredConditions, 5, TimeUnit.SECONDS);
                for (Condition cond : triggeredConditions) {
                    assertTrue("No matching conditions: ", util.objectCompareCheck("testOnSampleRejectedWaitset", cond, statusCond));
                }
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testOnSampleRejectedWaitset", e, false));
            }
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testOnSampleRejectedWaitset", e, false));
        } finally {
            try {
                dr.take();
                waitset.detachCondition(statusCond);
                if (dw != null) {
                    dw.close();
                }
                if (dr != null) {
                    dr.close();
                }
            } catch (Exception e) {
                /* ignore */
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testOnOfferedIncompatibleQosWaitset() {
        checkValidEntities();
        DataReader<Msg> dr = null;
        DataWriter<Msg> dw = null;
        StatusCondition<DataWriter<Msg>> statusCond = null;
        try {
            /*
             * should trigger OnOfferedIncompatibleQos because of non matching
             * durability qos
             */
            DataReaderQos drq = subscriber.getDefaultDataReaderQos();
            Durability dur = drq.getDurability().withTransient();
            drq = drq.withPolicy(dur);
            dr = subscriber.createDataReader(topic, drq);

            dw = publisher.createDataWriter(topic);
            statusCond = dw.getStatusCondition();
            statusCond.setEnabledStatuses(OfferedIncompatibleQosStatus.class);
            waitset.attachCondition(statusCond);
            try {
                HashSet<Condition> triggeredConditions = new HashSet<Condition>();
                waitset.waitForConditions(triggeredConditions, 5, TimeUnit.SECONDS);
                for (Condition cond : triggeredConditions) {
                    assertTrue("No matching conditions: ", util.objectCompareCheck("testOnOfferedIncompatibleQosWaitset", cond, statusCond));
                }
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testOnOfferedIncompatibleQosWaitset", e, false));
            }
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testOnOfferedIncompatibleQosWaitset", e, false));
        } finally {
            waitset.detachCondition(statusCond);
            if (dw != null) {
                dw.close();
            }
            if (dr != null) {
                dr.close();
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testOnOfferedDeadlineMissedWaitset() {
        checkValidEntities();
        StatusCondition<DataWriter<Msg>> statusCond = null;
        DataWriter<Msg> dw = null;
        try {
            DataWriterQos dwq = publisher.getDefaultDataWriterQos();
            Deadline du = dwq.getDeadline().withPeriod(1, TimeUnit.SECONDS);
            dwq = dwq.withPolicy(du);
            dw = publisher.createDataWriter(topic, dwq);

            statusCond = dw.getStatusCondition();
            statusCond.setEnabledStatuses(OfferedDeadlineMissedStatus.class);
            waitset.attachCondition(statusCond);

            dw.write(message);
            /*
             * should trigger OnOfferedDeadlineMissed because of sleep >
             * deadline time
             */
            try {
                HashSet<Condition> triggeredConditions = new HashSet<Condition>();
                waitset.waitForConditions(triggeredConditions, 5, TimeUnit.SECONDS);
                for (Condition cond : triggeredConditions) {
                    assertTrue("No matching conditions: ", util.objectCompareCheck("testOnOfferedDeadlineMissedWaitset", cond, statusCond));
                }
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testOnOfferedDeadlineMissedWaitset", e, false));
            }
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testOnOfferedDeadlineMissedWaitset", e, false));
        } finally {
            try {
                waitset.detachCondition(statusCond);
                dw.close();
            } catch (Exception e) {
                /* ignore */
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testOnLivelinessLostWaitset() {
        checkValidEntities();
        DataWriter<Msg> dw = null;
        StatusCondition<DataWriter<Msg>> statusCond = null;
        try {
            /*
             * should trigger OnLivelinessLost as sleep > lease duration
             */
            DataWriterQos dwq = publisher.getDefaultDataWriterQos();
            Liveliness ll = dwq.getLiveliness().withManualByTopic().withLeaseDuration(2, TimeUnit.SECONDS);
            dwq = dwq.withPolicy(ll);
            dw = publisher.createDataWriter(topic, dwq);

            statusCond = dw.getStatusCondition();
            statusCond.setEnabledStatuses(LivelinessLostStatus.class);
            waitset.attachCondition(statusCond);

            try {
                HashSet<Condition> triggeredConditions = new HashSet<Condition>();
                waitset.waitForConditions(triggeredConditions, 5, TimeUnit.SECONDS);
                for (Condition cond : triggeredConditions) {
                    assertTrue("No matching conditions: ", util.objectCompareCheck("testOnLivelinessLostWaitset", cond, statusCond));
                }
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testOnLivelinessLostWaitset", e, false));
            }
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testOnLivelinessLostWaitset", e, false));
        } finally {
            try {
                waitset.detachCondition(statusCond);
                dw.close();
            } catch (Exception e) {
                /* ignore */
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testOnPublicationMatchedWaitset() {
        checkValidEntities();
        DataWriter<Msg> dw = null;
        DataReader<Msg> dr = null;
        StatusCondition<DataWriter<Msg>> statusCond = null;
        try {
            dw = publisher.createDataWriter(topic);

            statusCond = dw.getStatusCondition();
            statusCond.setEnabledStatuses(PublicationMatchedStatus.class);
            waitset.attachCondition(statusCond);

            dr = subscriber.createDataReader(topic);
            try {
                HashSet<Condition> triggeredConditions = new HashSet<Condition>();
                waitset.waitForConditions(triggeredConditions, 5, TimeUnit.SECONDS);
                for (Condition cond : triggeredConditions) {
                    assertTrue("No matching conditions: ", util.objectCompareCheck("testOnPublicationMatchedWaitset", cond, statusCond));
                }
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testOnPublicationMatchedWaitset", e, false));
            }
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testOnPublicationMatchedWaitset", e, false));
        } finally {
            try {
                waitset.detachCondition(statusCond);
                dw.close();
                dr.close();
            } catch (Exception e) {
                /* ignore */
            }
        }
    }

    @Test
    public void testStatusCondition() {
        checkValidEntities();
        DataWriter<Msg> dw = null;
        StatusCondition<DataWriter<Msg>> statusCond = null;
        String function = "testStatusCondition";
        try {
            dw = publisher.createDataWriter(topic);
            statusCond = dw.getStatusCondition();
            assertFalse("Check for false triggervalue failed: ", statusCond.getTriggerValue());
            assertTrue("Check for valid parent failed", util.objectCheck(function, statusCond.getParent()));
            assertTrue("Check for valid status environment failed", util.objectCheck(function, statusCond.getEnvironment()));

            Collection<Class<? extends Status>> statuses = new HashSet<Class<? extends Status>>();
            statuses.add(DataAvailableStatus.class);
            statuses.add(PublicationMatchedStatus.class);
            statusCond.setEnabledStatuses(statuses);
            assertTrue("Check for 2 entries in the status set failed", util.valueCompareCheck(function, statusCond.getEnabledStatuses().size(), 2, AbstractUtilities.Equality.EQ));
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck(function, e, false));
        } finally {
            try {
                dw.close();
            } catch (Exception e) {
                /* ignore */
            }
        }
    }

    @Test
    public void testGuardCondition() {
        checkValidEntities();
        GuardCondition terminated = null;
        String function = "testGuardCondition";
        try {
            terminated = env.getSPI().newGuardCondition();
            waitset.attachCondition(terminated);
            assertFalse("Check for false triggervalue failed: ", terminated.getTriggerValue());
            assertTrue("Check for valid status environment failed", util.objectCheck(function, terminated.getEnvironment()));
            GuardCondition dummy = GuardCondition.newGuardCondition(env);
            assertTrue("Check for valid GuardCondition failed: ", util.objectCheck(function, dummy));
            try {
                terminated.setTriggerValue(true);
                HashSet<Condition> triggeredConditions = new HashSet<Condition>();
                waitset.waitForConditions(triggeredConditions, 5, TimeUnit.SECONDS);
                for (Condition cond : triggeredConditions) {
                    assertTrue("No matching conditions: ", util.objectCompareCheck(function, cond, terminated));
                }
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck(function, e, false));
            }
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck(function, e, false));
        } finally {
            try {
                waitset.detachCondition(terminated);
            } catch (Exception e) {
                /* ignore */
            }
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.core.WaitSet#waitForConditions(java.util.Collection)}.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testWaitForConditionsCollectionOfCondition() {
        WaitSet w = null;
        DataWriter<Msg> dw = null;
        DataReader<Msg> dr = null;
        StatusCondition<DataReader<Msg>> statusCond = null;
        HashSet<Condition> triggeredConditions = new HashSet<Condition>();
        try {
            dr = subscriber.createDataReader(topic);

            w = env.getSPI().newWaitSet();
            statusCond = dr.getStatusCondition();
            statusCond.setEnabledStatuses(DataAvailableStatus.class);
            w.attachCondition(statusCond);

            dw = publisher.createDataWriter(topic);
            dw.write(message);

        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testWaitForConditionsCollectionOfCondition", e, false));
        }
        try {
            w.waitForConditions(triggeredConditions);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testWaitForConditionsCollectionOfCondition", e, false));
        } finally {
            try {
                w.detachCondition(statusCond);
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
        }
        assertTrue("Check for valid StatusCondition failed ", util.objectCompareCheck("testWaitForConditionsCollectionOfCondition", triggeredConditions.isEmpty(), false));
    }

    /**
     * Test method for
     * {@link org.omg.dds.core.WaitSet#waitForConditions(java.util.Collection)}.
     */
    @Test
    public void testWaitForConditionsCollectionOfConditionNull() {
        WaitSet w = null;
        boolean exceptionOccured = false;
        HashSet<Condition> triggeredConditions = null;
        try {
            w = env.getSPI().newWaitSet();
            w.waitForConditions(triggeredConditions);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e),
                    util.exceptionCheck("testWaitForConditionsCollectionOfConditionNull", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("Check for IllegalArgumentException occured failed", exceptionOccured);
    }

}
