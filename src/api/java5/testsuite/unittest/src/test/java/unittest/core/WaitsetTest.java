
package unittest.core;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.dds.core.AlreadyClosedException;
import org.omg.dds.core.Condition;
import org.omg.dds.core.Duration;
import org.omg.dds.core.PreconditionNotMetException;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.StatusCondition;
import org.omg.dds.core.WaitSet;
import org.omg.dds.core.status.DataAvailableStatus;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.domain.DomainParticipantFactory;
import org.omg.dds.pub.DataWriter;
import org.omg.dds.pub.Publisher;
import org.omg.dds.sub.DataReader;
import org.omg.dds.sub.Subscriber;
import org.omg.dds.topic.Topic;

import Test.Msg;

import org.eclipse.cyclonedds.test.AbstractUtilities;

public class WaitsetTest {

    private final static int                DOMAIN_ID   = 123;
    private static ServiceEnvironment       env;
    private static DomainParticipantFactory dpf;
    private static AbstractUtilities        util        = AbstractUtilities.getInstance(WaitsetTest.class);
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
            env = ServiceEnvironment.createInstance(WaitsetTest.class.getClassLoader());
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
     * Test method for
     * {@link org.omg.dds.core.WaitSet#newWaitSet(org.omg.dds.core.ServiceEnvironment)}
     * .
     */
    @Test
    public void testNewWaitSet() {
        WaitSet w = null;
        try {
            w = env.getSPI().newWaitSet();
            WaitSet dummy = WaitSet.newWaitSet(env);
            assertTrue("Check for valid WaitSet failed: ", util.objectCheck("testNewWaitSet", dummy));
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testNewWaitSet", e, false));
        }
        assertTrue("Check for valid WaitSet", util.objectCheck("testNewWaitSet", w));
    }




    /**
     * Test method for
     * {@link org.omg.dds.core.WaitSet#waitForConditions(org.omg.dds.core.Duration)}
     * .
     */
    @Test
    public void testWaitForConditionsDurationNull() {
        WaitSet w = null;
        boolean exceptionOccured = false;
        Duration duration = null;
        try {
            w = env.getSPI().newWaitSet();
            w.waitForConditions(duration);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testWaitForConditionsDurationNull", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("Check for IllegalArgumentException occured failed", exceptionOccured);
    }

    /**
     * Test method for
     * {@link org.omg.dds.core.WaitSet#waitForConditions(org.omg.dds.core.Duration)}
     * .
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testWaitForConditionsDuration() {
        WaitSet w = null;
        DataWriter<Msg> dw = null;
        DataReader<Msg> dr = null;
        StatusCondition<DataReader<Msg>> statusCond = null;
        Duration dur = Duration.newDuration(5, TimeUnit.SECONDS, env);
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
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testWaitForConditionsDuration", e, false));
        }
        try {
            w.waitForConditions(dur);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testWaitForConditionsDuration", e, false));
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
     * {@link org.omg.dds.core.WaitSet#waitForConditions(long, java.util.concurrent.TimeUnit)}
     * .
     */
    @Test
    public void testWaitForConditionsLongTimeUnitNull() {
        WaitSet w = null;
        boolean exceptionOccured = false;
        try {
            w = env.getSPI().newWaitSet();
            w.waitForConditions(5, null);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testWaitForConditionsLongTimeUnitNull", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("Check for IllegalArgumentException occured failed", exceptionOccured);
    }

    /**
     * Test method for
     * {@link org.omg.dds.core.WaitSet#waitForConditions(long, java.util.concurrent.TimeUnit)}
     * .
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testWaitForConditionsLongTimeUnit() {
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
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testWaitForConditionsLongTimeUnit", e, false));
        }
        try {
            w.waitForConditions(5, TimeUnit.SECONDS);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testWaitForConditionsLongTimeUnit", e, false));
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
     * {@link org.omg.dds.core.WaitSet#attachCondition(org.omg.dds.core.Condition)}
     * .
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testAttachCondition() {
        WaitSet w = null;
        DataReader<Msg> dr = null;
        StatusCondition<DataReader<Msg>> statusCond = null;
        Collection<Condition> result = null;
        try {
            dr = subscriber.createDataReader(topic);

            w = env.getSPI().newWaitSet();
            statusCond = dr.getStatusCondition();
            statusCond.setEnabledStatuses(DataAvailableStatus.class);
            w.attachCondition(statusCond);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testAttachCondition", e, false));
        }
        try {
            result = w.getConditions();
            assertTrue("Check for valid StatusCondition failed ", util.objectCompareCheck("testAttachCondition", result.contains(statusCond), true));
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testAttachCondition", e, false));
        } finally {
            w.detachCondition(statusCond);
        }

    }

    /**
     * Test method for
     * {@link org.omg.dds.core.WaitSet#attachCondition(org.omg.dds.core.Condition)}
     * .
     */
    @Test
    public void testAttachConditionNull() {
        WaitSet w = null;
        StatusCondition<DataReader<Msg>> statusCond = null;
        boolean exceptionOccured = false;
        try {
            w = env.getSPI().newWaitSet();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testAttachConditionNull", e, false));
        }
        try {
            w.attachCondition(statusCond);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testAttachConditionNull", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("IllegalArgumentException not occured", exceptionOccured);
    }

    /**
     * Test method for
     * {@link org.omg.dds.core.WaitSet#detachCondition(org.omg.dds.core.Condition)}
     * .
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testDetachCondition() {
        WaitSet w = null;
        DataReader<Msg> dr = null;
        StatusCondition<DataReader<Msg>> statusCond = null;
        Collection<Condition> result = null;
        try {
            dr = subscriber.createDataReader(topic);

            w = env.getSPI().newWaitSet();
            statusCond = dr.getStatusCondition();
            statusCond.setEnabledStatuses(DataAvailableStatus.class);
            w.attachCondition(statusCond);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testDetachCondition", e, false));
        }
        try {
            result = w.getConditions();
            assertTrue("Check for valid StatusCondition failed ", util.objectCompareCheck("testDetachCondition", result.contains(statusCond), true));
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testDetachCondition", e, false));
        }
        try {
            w.detachCondition(statusCond);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testDetachCondition", e, false));
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.core.WaitSet#detachCondition(org.omg.dds.core.Condition)}
     * .
     */
    @Test
    public void testDetachConditionEmpty() {
        WaitSet w = null;
        DataReader<Msg> dr = null;
        StatusCondition<DataReader<Msg>> statusCond = null;
        boolean exceptionOccured = false;
        try {
            w = env.getSPI().newWaitSet();
            dr = subscriber.createDataReader(topic);
            statusCond = dr.getStatusCondition();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testDetachConditionEmpty", e, false));
        }
        try {
            w.detachCondition(statusCond);
        } catch (Exception e) {
            assertTrue("PreconditionNotMetException expected but got: " + util.printException(e), util.exceptionCheck("testDetachConditionEmpty", e, e instanceof PreconditionNotMetException));
            exceptionOccured = true;
        }
        assertTrue("PreconditionNotMetException not occured", exceptionOccured);
    }

    /**
     * Test method for
     * {@link org.omg.dds.core.WaitSet#detachCondition(org.omg.dds.core.Condition)}
     * .
     */
    @Test
    public void testDetachConditionNull() {
        WaitSet w = null;
        StatusCondition<DataReader<Msg>> statusCond = null;
        boolean exceptionOccured = false;
        try {
            w = env.getSPI().newWaitSet();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testDetachConditionNull", e, false));
        }
        try {
            w.detachCondition(statusCond);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testDetachConditionNull", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("IllegalArgumentException not occured", exceptionOccured);
    }

    /**
     * Test method for {@link org.omg.dds.core.WaitSet#getConditions()}.
     */
    @Test
    public void testGetConditions() {
        WaitSet w = null;
        Collection<Condition> result = null;
        try {
            w = env.getSPI().newWaitSet();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetConditions", e, false));
        }
        try {
            result = w.getConditions();
            assertTrue("Check for empty StatusCondition Set failed ", util.objectCompareCheck("testGetConditions", result.isEmpty(), true));
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetConditions", e, false));
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
            senv = waitset.getEnvironment();
        } catch (Exception e) {
            fail("Exception occured while calling getEnvironment(): " + util.printException(e));
        }
        assertTrue("Check for valid ServiceEnvironment", senv != null);
    }

}
