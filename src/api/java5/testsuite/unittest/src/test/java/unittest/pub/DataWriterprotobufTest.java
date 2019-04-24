
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
import org.omg.dds.core.Duration;
import org.omg.dds.core.InconsistentPolicyException;
import org.omg.dds.core.InstanceHandle;
import org.omg.dds.core.PreconditionNotMetException;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.StatusCondition;
import org.omg.dds.core.Time;
import org.omg.dds.core.policy.DestinationOrder;
import org.omg.dds.core.policy.PolicyFactory;
import org.omg.dds.core.policy.Reliability;
import org.omg.dds.core.policy.Presentation.AccessScopeKind;
import org.omg.dds.core.status.DataAvailableStatus;
import org.omg.dds.core.status.LivelinessLostStatus;
import org.omg.dds.core.status.OfferedDeadlineMissedStatus;
import org.omg.dds.core.status.OfferedIncompatibleQosStatus;
import org.omg.dds.core.status.PublicationMatchedStatus;
import org.omg.dds.core.status.Status;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.domain.DomainParticipantFactory;
import org.omg.dds.pub.DataWriter;
import org.omg.dds.pub.DataWriterListener;
import org.omg.dds.pub.DataWriterQos;
import org.omg.dds.pub.Publisher;
import org.omg.dds.pub.PublisherQos;
import org.omg.dds.sub.DataReader;
import org.omg.dds.sub.Subscriber;
import org.omg.dds.topic.SubscriptionBuiltinTopicData;
import org.omg.dds.topic.Topic;
import org.omg.dds.topic.TopicQos;
import org.opensplice.dds.pub.DataWriterProtobuf;

import proto.Proto.ProtoMsg;
import org.eclipse.cyclonedds.test.AbstractUtilities;
import org.eclipse.cyclonedds.test.DataWriterListenerprotobufClass;

public class DataWriterprotobufTest {

    private final static int                                        DOMAIN_ID   = 123;
    private static ServiceEnvironment                               env;
    private static DomainParticipantFactory                         dpf;
    private static AbstractUtilities                                util        = AbstractUtilities.getInstance(DataWriterprotobufTest.class);
    private final static Properties                                 prop        = new Properties();
    private static DomainParticipant                                participant = null;
    private static Publisher                                        publisher   = null;
    private static Topic<ProtoMsg>                                  topic       = null;
    private static DataWriterProtobuf<ProtoMsg, proto.dds.ProtoMsg> dataWriter  = null;
    private static String                                           topicName   = "DataWriterprotobufTest";

    @SuppressWarnings("unchecked")
    @BeforeClass
    public static void init() {
        try {
            String serviceEnvironment = System.getProperty("JAVA5PSM_SERVICE_ENV");
            assertTrue("Check for valid ServiceEnvironment string", !serviceEnvironment.equals(""));
            System.setProperty(ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY, serviceEnvironment);
            env = ServiceEnvironment.createInstance(DataWriterprotobufTest.class.getClassLoader());
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
            topic = participant.createTopic(topicName, ProtoMsg.class);
            assertTrue("Check for valid Topic object", topic instanceof Topic);
            dataWriter = (DataWriterProtobuf<ProtoMsg, proto.dds.ProtoMsg>) publisher.createDataWriter(topic);
            assertTrue("Check for valid DataWriter object", dataWriter instanceof DataWriter);
        } catch (Exception e) {
            fail("Exception occured while initiating the DataWriterprotobufTest class: " + util.printException(e));
        }

    }

    private void checkValidEntities() {
        assertTrue("Check for valid DomainParticipant object", participant instanceof DomainParticipant);
        assertTrue("Check for valid Publisher object", publisher instanceof Publisher);
        assertTrue("Check for valid Topic object", topic instanceof Topic);
        assertTrue("Check for valid DataWriter object", dataWriter instanceof DataWriter);
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
     * Test method for {@link org.omg.dds.pub.DataWriter#cast()}.
     */
    @Test
    public void testCast() {
        checkValidEntities();
        DataWriter<Object> result = null;
        try {
            result = dataWriter.cast();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testCast", e, false));
        }
        assertTrue("Check for valid DataWriter object", util.objectCheck("testCast", result));
    }

    /**
     * Test method for {@link org.omg.dds.pub.DataWriter#cast()}.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Test
    public void testCastInvalid() {
        checkValidEntities();
        DataReader<ProtoMsg> result = null;
        try {
            result = (DataReader) dataWriter.cast();
        } catch (Exception e) {
            assertTrue("ClassCastException expected but got: " + util.printException(e), util.exceptionCheck("testCastInvalid", e, e instanceof ClassCastException));
        }
        assertTrue("Check for invalid DataWriter object", result == null);
    }

    /**
     * Test method for {@link org.omg.dds.pub.DataWriter#getTopic()}.
     */
    @Test
    public void testGetTopic() {
        checkValidEntities();
        Topic<ProtoMsg> result = null;
        try {
            result = dataWriter.getTopic();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetTopic", e, false));
        }
        assertTrue("Check for valid Topic object", util.objectCheck("testGetTopic", result));
    }

    /**
     * Test method for {@link org.omg.dds.pub.DataWriter#waitForAcknowledgments(org.omg.dds.core.Duration)}.
     */
    @Test
    public void testWaitForAcknowledgmentsDuration() {
        checkValidEntities();
        Duration timeout = Duration.newDuration(1, TimeUnit.SECONDS, env);
        try {
            dataWriter.waitForAcknowledgments(timeout);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testWaitForAcknowledgmentsDuration", e, e instanceof TimeoutException));
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.pub.DataWriter#waitForAcknowledgments(org.omg.dds.core.Duration)}
     * .
     */
    @Test
    public void testWaitForAcknowledgmentsDurationNull() {
        checkValidEntities();
        boolean exceptionOccured = false;
        try {
            dataWriter.waitForAcknowledgments(null);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testWaitForAcknowledgmentsDurationNull", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException has occured failed", exceptionOccured);
    }

    /**
     * Test method for
     * {@link org.omg.dds.pub.DataWriter#waitForAcknowledgments(org.omg.dds.core.Duration)}
     * .
     */
    @Test
    public void testWaitForAcknowledgmentsLongTimeUnitNull() {
        checkValidEntities();
        boolean exceptionOccured = false;
        try {
            dataWriter.waitForAcknowledgments(1, null);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testWaitForAcknowledgmentsDurationNull", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException has occured failed", exceptionOccured);
    }

    /**
     * Test method for
     * {@link org.omg.dds.pub.DataWriter#waitForAcknowledgments(long, java.util.concurrent.TimeUnit)}
     * .
     */
    @Test
    public void testWaitForAcknowledgmentsLongTimeUnit() {
        checkValidEntities();
        try {
            dataWriter.waitForAcknowledgments(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testWaitForAcknowledgmentsLongTimeUnit", e, e instanceof TimeoutException));
        }
    }

    /**
     * Test method for {@link org.omg.dds.pub.DataWriter#getLivelinessLostStatus()}.
     */
    @Test
    public void testGetLivelinessLostStatus() {
        checkValidEntities();
        LivelinessLostStatus result = null;
        try {
            result = dataWriter.getLivelinessLostStatus();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetLivelinessLostStatus", e, false));
        }
        assertTrue("Check for valid LivelinessLostStatus object", util.objectCheck("testGetLivelinessLostStatus", result));
    }

    /**
     * Test method for {@link org.omg.dds.pub.DataWriter#getOfferedDeadlineMissedStatus()}.
     */
    @Test
    public void testGetOfferedDeadlineMissedStatus() {
        checkValidEntities();
        OfferedDeadlineMissedStatus result = null;
        try {
            result = dataWriter.getOfferedDeadlineMissedStatus();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetOfferedDeadlineMissedStatus", e, false));
        }
        assertTrue("Check for valid OfferedDeadlineMissedStatus object", util.objectCheck("testGetOfferedDeadlineMissedStatus", result));
    }

    /**
     * Test method for {@link org.omg.dds.pub.DataWriter#getOfferedIncompatibleQosStatus()}.
     */
    @Test
    public void testGetOfferedIncompatibleQosStatus() {
        checkValidEntities();
        OfferedIncompatibleQosStatus result = null;
        try {
            result = dataWriter.getOfferedIncompatibleQosStatus();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetOfferedIncompatibleQosStatus", e, false));
        }
        assertTrue("Check for valid OfferedIncompatibleQosStatus object", util.objectCheck("testGetOfferedIncompatibleQosStatus", result));
    }


    /**
     * Test method for {@link org.omg.dds.pub.DataWriter#getPublicationMatchedStatus()}.
     */
    @Test
    public void testGetPublicationMatchedStatus() {
        checkValidEntities();
        PublicationMatchedStatus result = null;
        try {
            result = dataWriter.getPublicationMatchedStatus();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetPublicationMatchedStatus", e, false));
        }
        assertTrue("Check for valid PublicationMatchedStatus object", util.objectCheck("testGetPublicationMatchedStatus", result));
    }

    /**
     * Test method for {@link org.omg.dds.pub.DataWriter#assertLiveliness()}.
     */
    @Test
    public void testAssertLiveliness() {
        checkValidEntities();
        try {
            dataWriter.assertLiveliness();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testAssertLiveliness", e, false));
        }
    }

    /**
     * Test method for {@link org.omg.dds.pub.DataWriter#getMatchedSubscriptions()}.
     */
    @Test
    public void testGetMatchedSubscriptions() {
        checkValidEntities();
        Set<InstanceHandle> subscriptions = null;
        int nrOfSubscriptions = 0;
        try {
            subscriptions = dataWriter.getMatchedSubscriptions();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetMatchedSubscriptions", e, false));
        }
        assertTrue("Check for valid subscriptions object", util.objectCheck("testGetMatchedSubscriptions", subscriptions));
        if (subscriptions != null) {
            for (InstanceHandle ih : subscriptions) {
                try {
                    SubscriptionBuiltinTopicData sbtd = dataWriter.getMatchedSubscriptionData(ih);
                    assertTrue("Check for valid SubscriptionBuiltinTopicData object", util.objectCheck("testGetMatchedSubscriptions", sbtd));
                    nrOfSubscriptions++;
                } catch (Exception e) {
                    assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetMatchedSubscriptions", e, false));
                }
            }
        }
        assertTrue("Check for correct number of subscriptions in subscriptions set", nrOfSubscriptions == 0);
    }

    /**
     * Test method for {@link org.omg.dds.pub.DataWriter#getMatchedSubscriptionData(org.omg.dds.core.InstanceHandle)}.
     */
    @Test
    public void testGetMatchedSubscriptionData() {
        checkValidEntities();
        Subscriber sub = null;
        DataReader<ProtoMsg> dr = null;
        Set<InstanceHandle> subscriptions = null;
        int nrOfSubscriptions = 0;
        try {
            sub = participant.createSubscriber();
            dr = sub.createDataReader(topic);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetMatchedSubscriptionData", e, false));
        }
        try {
            Thread.sleep(100);
        } catch(InterruptedException e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetMatchedSubscriptionData", e, false));
        }
        try {
            subscriptions = dataWriter.getMatchedSubscriptions();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetMatchedSubscriptionData", e, false));
        }
        assertTrue("Check for valid subscriptions object", util.objectCheck("testGetMatchedSubscriptionData", subscriptions));
        if (subscriptions != null) {
            for (InstanceHandle ih : subscriptions) {
                try {
                    SubscriptionBuiltinTopicData sbtd = dataWriter.getMatchedSubscriptionData(ih);
                    assertTrue("Check for valid SubscriptionBuiltinTopicData object", util.objectCheck("testGetMatchedSubscriptionData", sbtd));
                    if (sbtd != null && sbtd.getTopicName().equals(topicName)) {
                        nrOfSubscriptions++;
                    }
                } catch (Exception e) {
                    assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetMatchedSubscriptionData", e, false));
                }
            }
        }
        try {
            dr.close();
            sub.close();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetMatchedSubscriptionData", e, false));
        }
        assertTrue("Check for correct number of subscriptions in subscriptions set", util.objectCompareCheck("testGetMatchedSubscriptionData", nrOfSubscriptions, 1));
    }

    /**
     * Test method for {@link org.omg.dds.pub.DataWriter#registerInstance(java.lang.Object)}.
     */
    @Test
    public void testRegisterInstanceTYPE() {
        checkValidEntities();
        ProtoMsg.Builder messageBuilder = ProtoMsg.newBuilder();
        messageBuilder.setId(0);
        messageBuilder.setName("testRegisterInstanceTYPE");
        ProtoMsg message = messageBuilder.build();

        messageBuilder = ProtoMsg.newBuilder();
        messageBuilder.setId(1);
        messageBuilder.setName("testRegisterInstanceTYPE");
        ProtoMsg message1 = messageBuilder.build();
        InstanceHandle ih = null;
        InstanceHandle ih1 = null;
        try {
            ih = dataWriter.registerInstance(message);
            ih1 = dataWriter.registerInstance(message1);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testRegisterInstanceTYPE", e, false));
        }
        assertTrue("Check for a valid InstanceHandle", util.objectCheck("testRegisterInstanceTYPE", ih));
        try {
            dataWriter.unregisterInstance(ih);
            dataWriter.unregisterInstance(ih1, message1);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testRegisterInstanceTYPE", e, false));
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.pub.DataWriter#registerInstance(java.lang.Object)}.
     */
    @Test
    public void testRegisterInstanceTYPENull() {
        checkValidEntities();
        InstanceHandle ih = null;
        boolean exceptionOccured = false;
        try {
            ih = dataWriter.registerInstance(null);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testRegisterInstanceTYPENull", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException has occured failed", exceptionOccured);
        assertTrue("Check for invalid InstanceHandle", ih == null);
    }

    /**
     * Test method for {@link org.omg.dds.pub.DataWriter#registerInstance(java.lang.Object, org.omg.dds.core.Time)}.
     */
    @Test
    public void testRegisterInstanceTYPETime() {
        checkValidEntities();

        ProtoMsg.Builder messageBuilder = ProtoMsg.newBuilder();
        messageBuilder.setId(1);
        messageBuilder.setName("testRegisterInstanceTYPETime");
        ProtoMsg message = messageBuilder.build();
        Time t = Time.newTime(5, TimeUnit.SECONDS, env);
        InstanceHandle ih = null;
        try {
            ih = dataWriter.registerInstance(message, t);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testRegisterInstanceTYPETime", e, false));
        }
        assertTrue("Check for a valid InstanceHandle", util.objectCheck("testRegisterInstanceTYPETime", ih));
        try {
            dataWriter.unregisterInstance(ih, message, t);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testRegisterInstanceTYPETime", e, false));
        }
    }

    /**
     * Test method for {@link org.omg.dds.pub.DataWriter#registerInstance(java.lang.Object, long, java.util.concurrent.TimeUnit)}.
     */
    @Test
    public void testRegisterInstanceTYPELongTimeUnit() {
        checkValidEntities();

        ProtoMsg.Builder messageBuilder = ProtoMsg.newBuilder();
        messageBuilder.setId(1);
        messageBuilder.setName("testRegisterInstanceTYPELongTimeUnit");
        ProtoMsg message = messageBuilder.build();
        InstanceHandle ih = null;
        long timestamp = 5;
        try {
            ih = dataWriter.registerInstance(message, timestamp, TimeUnit.SECONDS);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testRegisterInstanceTYPETime", e, false));
        }
        assertTrue("Check for a valid InstanceHandle", util.objectCheck("testRegisterInstanceTYPETime", ih));
        try {
            dataWriter.unregisterInstance(ih, message, timestamp, TimeUnit.SECONDS);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testRegisterInstanceTYPETime", e, false));
        }
    }

    /**
     * Test method for {@link org.omg.dds.pub.DataWriter#unregisterInstance(org.omg.dds.core.InstanceHandle)}.
     */
    @Test
    public void testUnregisterInstanceInstanceHandleNull() {
        checkValidEntities();
        boolean exceptionOccured = false;
        try {
            dataWriter.unregisterInstance(null);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e),
                    util.exceptionCheck("testUnregisterInstanceInstanceHandleNull", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException has occured failed", exceptionOccured);
    }

    /**
     * Test method for {@link org.omg.dds.pub.DataWriter#unregisterInstance(org.omg.dds.core.InstanceHandle, java.lang.Object)}.
     */
    @Test
    public void testUnregisterInstanceInstanceHandleTYPENull() {
        checkValidEntities();
        boolean exceptionOccured = false;
        ProtoMsg.Builder messageBuilder = ProtoMsg.newBuilder();
        messageBuilder.setId(0);
        messageBuilder.setName("testUnregisterInstanceInstanceHandleTYPENull");
        ProtoMsg message = messageBuilder.build();

        messageBuilder = ProtoMsg.newBuilder();
        messageBuilder.setId(1);
        messageBuilder.setName("testUnregisterInstanceInstanceHandleTYPENull");
        ProtoMsg message1 = messageBuilder.build();

        InstanceHandle ih = null;
        try {
            ih = dataWriter.registerInstance(message);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testUnregisterInstanceInstanceHandleTYPENull", e, false));
        }
        assertTrue("Check for a valid InstanceHandle", util.objectCheck("testUnregisterInstanceInstanceHandleTYPENull", ih));
        try {
            dataWriter.unregisterInstance(InstanceHandle.nilHandle(env), null);
        } catch (Exception e) {
            assertTrue("PreconditionNotMetException expected but got: " + util.printException(e),
                    util.exceptionCheck("testUnregisterInstanceInstanceHandleTYPENull", e, e instanceof PreconditionNotMetException));
            exceptionOccured = true;
        }
        assertTrue("Check if PreconditionNotMetException has occured failed", exceptionOccured);
        exceptionOccured = false;
        try {
            dataWriter.unregisterInstance(InstanceHandle.nilHandle(env), message1);
        } catch (Exception e) {
            assertTrue("PreconditionNotMetException expected but got: " + util.printException(e),
                    util.exceptionCheck("testUnregisterInstanceInstanceHandleTYPENull", e, e instanceof PreconditionNotMetException));
            exceptionOccured = true;
        }
        assertTrue("Check if PreconditionNotMetException has occured failed", exceptionOccured);
        exceptionOccured = false;
        try {
            dataWriter.unregisterInstance(InstanceHandle.nilHandle(env), message);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testUnregisterInstanceInstanceHandleTYPENull", e, false));
            exceptionOccured = true;
        }
        assertFalse("Check if no Exception has occured failed", exceptionOccured);
    }

    /**
     * Test method for {@link org.omg.dds.pub.DataWriter#unregisterInstance(org.omg.dds.core.InstanceHandle, java.lang.Object, org.omg.dds.core.Time)}.
     */
    @Test
    public void testUnregisterInstanceInstanceHandleTYPETime() {
        checkValidEntities();
        ProtoMsg.Builder messageBuilder = ProtoMsg.newBuilder();
        messageBuilder.setId(1);
        messageBuilder.setName("testUnregisterInstanceInstanceHandleTYPETime");
        ProtoMsg message = messageBuilder.build();

        InstanceHandle ih = null;
        boolean exceptionOccured = false;
        try {
            ih = dataWriter.registerInstance(message);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testUnregisterInstanceInstanceHandleTYPETime", e, false));
        }
        assertTrue("Check for a valid InstanceHandle", util.objectCheck("testUnregisterInstanceInstanceHandleTYPETime", ih));
        try {
            dataWriter.unregisterInstance(InstanceHandle.nilHandle(env), message, null);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e),
                    util.exceptionCheck("testUnregisterInstanceInstanceHandleTYPETime", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException has occured failed", exceptionOccured);
        try {
            dataWriter.unregisterInstance(ih);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testUnregisterInstanceInstanceHandleTYPETime", e, false));
        }
    }

    /**
     * Test method for {@link org.omg.dds.pub.DataWriter#unregisterInstance(org.omg.dds.core.InstanceHandle, java.lang.Object, long, java.util.concurrent.TimeUnit)}.
     */
    @Test
    public void testUnregisterInstanceInstanceHandleTYPELongTimeUnit() {
        checkValidEntities();
        ProtoMsg.Builder messageBuilder = ProtoMsg.newBuilder();
        messageBuilder.setId(6);
        messageBuilder.setName("testUnregisterInstanceInstanceHandleTYPELongTimeUnit");
        ProtoMsg message = messageBuilder.build();
        InstanceHandle ih = null;
        long timestamp = 5;
        boolean exceptionOccured = false;
        try {
            ih = dataWriter.registerInstance(message, timestamp, TimeUnit.SECONDS);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testUnregisterInstanceInstanceHandleTYPELongTimeUnit", e, false));
        }
        assertTrue("Check for a valid InstanceHandle", util.objectCheck("testUnregisterInstanceInstanceHandleTYPELongTimeUnit", ih));
        try {
            dataWriter.unregisterInstance(InstanceHandle.nilHandle(env), message, 3444363, null);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e),
                    util.exceptionCheck("testUnregisterInstanceInstanceHandleTYPELongTimeUnit", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException has occured failed", exceptionOccured);
        try {
            dataWriter.unregisterInstance(InstanceHandle.nilHandle(env), message, timestamp, TimeUnit.SECONDS);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testUnregisterInstanceInstanceHandleTYPELongTimeUnit", e, false));
        }
    }

    /**
     * Test method for {@link org.omg.dds.pub.DataWriter#write(java.lang.Object)}.
     */
    @Test
    public void testWriteTYPE() {
        checkValidEntities();
        ProtoMsg.Builder messageBuilder = ProtoMsg.newBuilder();
        messageBuilder.setId(1);
        messageBuilder.setName("testWriteTYPE");
        ProtoMsg message = messageBuilder.build();

        try {
            dataWriter.write(message);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testWriteTYPE", e, false));
        }
        try {
            dataWriter.dispose(dataWriter.lookupInstance(message));
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testWriteTYPE", e, false));
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.pub.DataWriter#write(java.lang.Object)}.
     */
    @Test
    public void testWriteTYPENull() {
        checkValidEntities();
        boolean exceptionOccured = false;
        try {
            dataWriter.write(null);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testWriteTYPENull", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException has occured failed", exceptionOccured);
    }

    /**
     * Test method for
     * {@link org.omg.dds.pub.DataWriter#write(java.lang.Object, org.omg.dds.core.Time)}
     * .
     */
    @Test
    public void testWriteTYPETime() {
        checkValidEntities();
        ProtoMsg.Builder messageBuilder = ProtoMsg.newBuilder();
        messageBuilder.setId(1);
        messageBuilder.setName("testWriteTYPETime");
        ProtoMsg message = messageBuilder.build();
        Time t = Time.newTime(5, TimeUnit.SECONDS, env);
        try {
            dataWriter.write(message, t);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testWriteTYPETime", e, false));
        }
        try {
            dataWriter.dispose(dataWriter.lookupInstance(message));
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testWriteTYPETime", e, false));
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.pub.DataWriter#write(java.lang.Object, org.omg.dds.core.Time)}
     * .
     */
    @Test
    public void testWriteTYPETimeNull() {
        checkValidEntities();
        ProtoMsg.Builder messageBuilder = ProtoMsg.newBuilder();
        messageBuilder.setId(1);
        messageBuilder.setName("testWriteTYPETimeNull");
        ProtoMsg message = messageBuilder.build();
        Time t = null;
        boolean exceptionOccured = false;
        try {
            dataWriter.write(message, t);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testWriteTYPETimeNull", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException has occured failed", exceptionOccured);
    }

    /**
     * Test method for
     * {@link org.omg.dds.pub.DataWriter#write(java.lang.Object, long, java.util.concurrent.TimeUnit)}
     * .
     */
    @Test
    public void testWriteTYPELongTimeUnit() {
        checkValidEntities();
        ProtoMsg.Builder messageBuilder = ProtoMsg.newBuilder();
        messageBuilder.setId(1);
        messageBuilder.setName("testWriteTYPELongTimeUnit");
        ProtoMsg message = messageBuilder.build();
        try {
            dataWriter.write(message, 5, TimeUnit.SECONDS);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testWriteTYPELongTimeUnit", e, false));
        }
        try {
            dataWriter.dispose(dataWriter.lookupInstance(message));
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testWriteTYPELongTimeUnit", e, false));
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.pub.DataWriter#write(java.lang.Object, long, java.util.concurrent.TimeUnit)}
     * .
     */
    @Test
    public void testWriteTYPELongTimeUnitNull() {
        checkValidEntities();
        ProtoMsg.Builder messageBuilder = ProtoMsg.newBuilder();
        messageBuilder.setId(1);
        messageBuilder.setName("testWriteTYPELongTimeUnitNull");
        ProtoMsg message = messageBuilder.build();
        boolean exceptionOccured = false;
        try {
            dataWriter.write(message, 5, null);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testWriteTYPETimeNull", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException has occured failed", exceptionOccured);
    }

    /**
     * Test method for
     * {@link org.omg.dds.pub.DataWriter#write(java.lang.Object, org.omg.dds.core.InstanceHandle)}
     * .
     */
    @Test
    public void testWriteTYPEInstanceHandle() {
        checkValidEntities();
        ProtoMsg.Builder messageBuilder = ProtoMsg.newBuilder();
        messageBuilder.setId(1);
        messageBuilder.setName("testWriteTYPEInstanceHandle");
        ProtoMsg message = messageBuilder.build();
        InstanceHandle ih = null;
        try {
            ih = dataWriter.registerInstance(message);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testWriteTYPEInstanceHandle", e, false));
        }
        try {
            dataWriter.write(message, ih);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testWriteTYPEInstanceHandle", e, false));
        }
        try {
            dataWriter.dispose(InstanceHandle.nilHandle(env), message);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testWriteTYPEInstanceHandle", e, false));
        }
    }

    /**
     * Test method for {@link org.omg.dds.pub.DataWriter#write(java.lang.Object, org.omg.dds.core.InstanceHandle, org.omg.dds.core.Time)}.
     */
    @Test
    public void testWriteTYPEInstanceHandleTime() {
        checkValidEntities();
        ProtoMsg.Builder messageBuilder = ProtoMsg.newBuilder();
        messageBuilder.setId(1);
        messageBuilder.setName("testWriteTYPEInstanceHandleTime");
        ProtoMsg message = messageBuilder.build();
        Time t = Time.newTime(5, TimeUnit.SECONDS, env);
        InstanceHandle ih = null;
        try {
            ih = dataWriter.registerInstance(message);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testWriteTYPEInstanceHandleTime", e, false));
        }
        try {
            dataWriter.write(message, ih, t);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testWriteTYPEInstanceHandleTime", e, false));
        }
        try {
            dataWriter.dispose(ih, message, t);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testWriteTYPEInstanceHandleTime", e, false));
        }
    }

    /**
     * Test method for {@link org.omg.dds.pub.DataWriter#write(java.lang.Object, org.omg.dds.core.InstanceHandle, long, java.util.concurrent.TimeUnit)}.
     */
    @Test
    public void testWriteTYPEInstanceHandleLongTimeUnit() {
        checkValidEntities();
        ProtoMsg.Builder messageBuilder = ProtoMsg.newBuilder();
        messageBuilder.setId(1);
        messageBuilder.setName("testWriteTYPEInstanceHandleLongTimeUnit");
        ProtoMsg message = messageBuilder.build();
        messageBuilder = ProtoMsg.newBuilder();
        messageBuilder.setId(2);
        messageBuilder.setName("testWriteTYPEInstanceHandleLongTimeUnit");
        ProtoMsg message1 = messageBuilder.build();
        InstanceHandle ih = null;
        boolean exceptionOccured = false;
        try {
            ih = dataWriter.registerInstance(message);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testWriteTYPEInstanceHandleLongTimeUnit", e, false));
        }
        try {
            dataWriter.write(message, ih, 5, TimeUnit.SECONDS);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testWriteTYPEInstanceHandleLongTimeUnit", e, false));
        }
        try {
            dataWriter.dispose(ih, message1, 5, TimeUnit.SECONDS);
        } catch (Exception e) {
            assertTrue("PreconditionNotMetException expected but got: " + util.printException(e),
                    util.exceptionCheck("testWriteTYPEInstanceHandleLongTimeUnit", e, e instanceof PreconditionNotMetException));
            exceptionOccured = true;
        }
        assertTrue("Check if PreconditionNotMetException has occured failed", exceptionOccured);
        try {
            dataWriter.dispose(ih, message, 5, TimeUnit.SECONDS);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testWriteTYPEInstanceHandleLongTimeUnit", e, false));
        }
    }

    /**
     * Test method for {@link org.omg.dds.pub.DataWriter#dispose(org.omg.dds.core.InstanceHandle)}.
     */
    @Test
    public void testDisposeInstanceHandleNull() {
        checkValidEntities();
        boolean exceptionOccured = false;
        try {
            dataWriter.dispose(null);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e),
                    util.exceptionCheck("testDisposeInstanceHandleNull", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException has occured failed", exceptionOccured);
    }

    /**
     * Test method for {@link org.omg.dds.pub.DataWriter#dispose(org.omg.dds.core.InstanceHandle, java.lang.Object)}.
     */
    @Test
    public void testDisposeInstanceHandleTYPENull() {
        checkValidEntities();
        boolean exceptionOccured = false;
        try {
            dataWriter.dispose(InstanceHandle.nilHandle(env), null);
        } catch (Exception e) {
            assertTrue(
                    "PreconditionNotMetException expected but got: "
                            + util.printException(e), util.exceptionCheck(
                            "testDisposeInstanceHandleTYPENull", e,
                            e instanceof PreconditionNotMetException));
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException has occured failed", exceptionOccured);
    }

    /**
     * Test method for {@link org.omg.dds.pub.DataWriter#dispose(org.omg.dds.core.InstanceHandle, java.lang.Object, org.omg.dds.core.Time)}.
     */
    @Test
    public void testDisposeInstanceHandleTYPETimeNull() {
        checkValidEntities();
        boolean exceptionOccured = false;
        ProtoMsg.Builder messageBuilder = ProtoMsg.newBuilder();
        messageBuilder.setId(1);
        messageBuilder.setName("testDisposeInstanceHandleTYPETimeNull");
        ProtoMsg message = messageBuilder.build();
        Time t = null;
        try {
            dataWriter.dispose(InstanceHandle.nilHandle(env), message, t);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testDisposeInstanceHandleTYPETimeNull", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException has occured failed", exceptionOccured);
    }

    /**
     * Test method for {@link org.omg.dds.pub.DataWriter#dispose(org.omg.dds.core.InstanceHandle, java.lang.Object, long, java.util.concurrent.TimeUnit)}.
     */
    @Test
    public void testDisposeInstanceHandleTYPELongTimeUnitNull() {
        checkValidEntities();
        boolean exceptionOccured = false;
        ProtoMsg.Builder messageBuilder = ProtoMsg.newBuilder();
        messageBuilder.setId(1);
        messageBuilder.setName("testDisposeInstanceHandleTYPELongTimeUnitNull");
        ProtoMsg message = messageBuilder.build();
        try {
            dataWriter.dispose(InstanceHandle.nilHandle(env), message, 5, null);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e),
                    util.exceptionCheck("testDisposeInstanceHandleTYPELongTimeUnitNull", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException has occured failed", exceptionOccured);
    }

    /**
     * Test method for {@link org.omg.dds.pub.DataWriter#getKeyValue(java.lang.Object, org.omg.dds.core.InstanceHandle)}.
     */
    @Test
    public void testGetKeyValueTYPEInstanceHandle() {
        checkValidEntities();
        ProtoMsg.Builder messageBuilder = ProtoMsg.newBuilder();
        messageBuilder.setId(1);
        messageBuilder.setName("testGetKeyValueTYPEInstanceHandle");
        ProtoMsg message = messageBuilder.build();
        ProtoMsg result = null;
        try {
            dataWriter.write(message);
            result = dataWriter.getKeyValue(message, dataWriter.lookupInstance(message));
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetKeyValueTYPEInstanceHandle", e, false));
        }
        try {
            dataWriter.dispose(dataWriter.lookupInstance(message));
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetKeyValueTYPEInstanceHandle", e, false));
        }
        assertTrue("Check if for valid ProtoMsg object", util.objectCheck("testGetKeyValueTYPEInstanceHandle", result));
    }

    /**
     * Test method for
     * {@link org.omg.dds.pub.DataWriter#getKeyValue(java.lang.Object, org.omg.dds.core.InstanceHandle)}
     * .
     */
    @Test
    public void testGetKeyValueTYPEInstanceHandleNull() {
        checkValidEntities();
        ProtoMsg result = null;
        DataWriterProtobuf<ProtoMsg, proto.dds.ProtoMsg> dw = (DataWriterProtobuf<ProtoMsg, proto.dds.ProtoMsg>) publisher.createDataWriter(topic);
        try {
            result = dw.getKeyValue(null, null);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testGetKeyValueTYPEInstanceHandleNull", e, e instanceof IllegalArgumentException));
        }
        dw.close();
        assertTrue("Check for invalid ProtoMsg object", result == null);
    }

    /**
     * Test method for
     * {@link org.omg.dds.pub.DataWriter#getKeyValue(org.omg.dds.core.InstanceHandle)}
     * .
     */
    @Test
    public void testGetKeyValueInstanceHandle() {
        checkValidEntities();
        ProtoMsg.Builder messageBuilder = ProtoMsg.newBuilder();
        messageBuilder.setId(1);
        messageBuilder.setName("testGetKeyValueInstanceHandle");
        ProtoMsg message = messageBuilder.build();
        ProtoMsg result = null;
        try {
            dataWriter.write(message);
            result = dataWriter.getKeyValue(dataWriter.lookupInstance(message));
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetKeyValueInstanceHandle", e, false));
        }
        try {
            dataWriter.dispose(dataWriter.lookupInstance(message));
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetKeyValueInstanceHandle", e, false));
        }
        assertTrue("Check if for valid ProtoMsg object", util.objectCheck("testGetKeyValueInstanceHandle", result));
    }

    /**
     * Test method for
     * {@link org.omg.dds.pub.DataWriter#getKeyValue(org.omg.dds.core.InstanceHandle)}
     * .
     */
    @Test
    public void testGetKeyValueInstanceHandleNull() {
        checkValidEntities();
        ProtoMsg result = null;
        DataWriterProtobuf<ProtoMsg, proto.dds.ProtoMsg> dw = (DataWriterProtobuf<ProtoMsg, proto.dds.ProtoMsg>) publisher.createDataWriter(topic);
        try {
            result = dw.getKeyValue(null);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testGetKeyValueInstanceHandleNull", e, e instanceof IllegalArgumentException));
        }
        dw.close();
        assertTrue("Check for invalid ProtoMsg object", result == null);
    }

    /**
     * Test method for
     * {@link org.omg.dds.pub.DataWriter#lookupInstance(java.lang.Object)}.
     */
    @Test
    public void testLookupInstance() {
        checkValidEntities();
        ProtoMsg.Builder messageBuilder = ProtoMsg.newBuilder();
        messageBuilder.setId(1);
        messageBuilder.setName("testLookupInstance");
        ProtoMsg message = messageBuilder.build();
        InstanceHandle result = null;
        InstanceHandle result1 = null;
        try {
            result = dataWriter.registerInstance(message);
            assertTrue("Check if for valid InstanceHandle object", util.objectCompareCheck("testLookupInstance", !result.isNil(), true));
            result1 = dataWriter.lookupInstance(message);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testLookupInstance", e, false));
        }
        assertTrue("Check if for valid InstanceHandle object", util.objectCompareCheck("testLookupInstance", !result1.isNil(), true));
    }

    /**
     * Test method for
     * {@link org.omg.dds.pub.DataWriter#lookupInstance(java.lang.Object)}.
     */
    @Test
    public void testLookupInstanceNull() {
        checkValidEntities();
        ProtoMsg message = null;
        InstanceHandle result = null;
        try {
            result = dataWriter.lookupInstance(message);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testLookupInstanceNull", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for invalid InstanceHandle object", result == null);
    }

    /**
     * Test method for {@link org.omg.dds.pub.DataWriter#getStatusCondition()}.
     */
    @Test
    public void testGetStatusCondition() {
        checkValidEntities();
        StatusCondition<DataWriter<ProtoMsg>> result = null;
        try {
            result = dataWriter.getStatusCondition();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetStatusCondition", e, false));
        }
        assertTrue("Check for valid StatusCondition object", util.objectCheck("testGetStatusCondition", result));
    }

    /**
     * Test method for {@link org.omg.dds.pub.DataWriter#getParent()}.
     */
    @Test
    public void testGetParent() {
        checkValidEntities();
        Publisher result = null;
        try {
            result = dataWriter.getParent();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetParent", e, false));
        }
        assertTrue("Check for valid Publisher object", util.objectCheck("testGetParent", result.equals(publisher)));
    }

    /**
     * Test method for {@link org.omg.dds.core.Entity#getListener()}.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetDataWriterListener() {
        checkValidEntities();
        DataWriterListener<ProtoMsg> dwl = new DataWriterListenerprotobufClass();
        DataWriterListener<ProtoMsg> dwl1 = null;
        Collection<Class<? extends Status>> statuses = new HashSet<Class<? extends Status>>();
        Class<? extends Status> status = null;
        statuses.add(DataAvailableStatus.class);
        try {
            dataWriter.setListener(dwl, statuses);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetDataWriterListener", e, false));
        }
        try {
            dwl1 = dataWriter.getListener();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetDataWriterListener", e, false));
        }
        assertTrue("Check for valid DataWriterListener object", util.objectCheck("testGetDataWriterListener", dwl1));
        /* reset listener to null */
        try {
            dataWriter.setListener(null, status);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetDataWriterListener", e, false));
        }
        try {
            dwl1 = dataWriter.getListener();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetDataWriterListener", e, false));
        }
        assertTrue("Check for null DataWriterListener object", dwl1 == null);
    }

    /**
     * Test method for
     * {@link org.omg.dds.core.Entity#setListener(java.util.EventListener)}.
     */
    @Test
    public void testDataWriterSetListener() {
        checkValidEntities();
        DataWriterListener<ProtoMsg> dwl = new DataWriterListenerprotobufClass();
        try {
            dataWriter.setListener(dwl);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testDataWriterSetListener", e, false));
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.core.Entity#setListener(java.util.EventListener, java.util.Collection)}
     * .
     */
    @Test
    public void testDataWriterSetListenerCollectionOfClassOfQextendsStatus() {
        checkValidEntities();
        DataWriterListener<ProtoMsg> dwl = new DataWriterListenerprotobufClass();
        Collection<Class<? extends Status>> statuses = new HashSet<Class<? extends Status>>();
        statuses.add(DataAvailableStatus.class);
        try {
            dataWriter.setListener(dwl, statuses);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testDataWriterSetListenerCollectionOfClassOfQextendsStatus", e, false));
        }
    }

    /**
     * Test method for {@link
     * org.omg.dds.core.Entity#setListener(java.util.EventListener,
     * java.lang.Class<? extends org.omg.dds.core.status.Status>[])}.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testDataWriterSetListenerClassOfQextendsStatusArray() {
        checkValidEntities();
        DataWriterListener<ProtoMsg> dwl = new DataWriterListenerprotobufClass();
        Class<? extends Status> status = DataAvailableStatus.class;
        try {
            dataWriter.setListener(dwl, status);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testDataWriterSetListenerClassOfQextendsStatusArray", e, false));
        }
    }

    /**
     * Test method for {@link org.omg.dds.core.Entity#getQos()}.
     */
    @Test
    public void testGetQos() {
        checkValidEntities();
        DataWriterQos dwq = null;
        try {
            dwq = dataWriter.getQos();
        } catch (Exception e) {
            fail("Exception occured while doing getQos(): " + util.printException(e));
        }
        assertTrue("Check for valid DataWriterQos", dwq != null);
    }

    /**
     * Test method for {@link org.omg.dds.core.Entity#setQos(org.omg.dds.core.EntityQos)}.
     */
    @Test
    public void testSetQos() {
        checkValidEntities();
        DataWriterQos dwq = null;
        Duration deadline = Duration.newDuration(5, TimeUnit.SECONDS, env);
        try {
            dwq = dataWriter.getQos();
        } catch (Exception e) {
            fail("Exception occured while calling getQos: " + util.printException(e));
        }
        assertTrue("Check for valid DataWriterQos", dwq != null);
        try {
            dwq = dwq.withPolicy(dwq.getDeadline().withPeriod(deadline));
            dataWriter.setQos(dwq);
        } catch (Exception ex) {
            fail("Failed to set the DataWriterQos");
        }
        try {
            dwq = dataWriter.getQos();
            assertTrue("Check for valid DataWriterQos value", dwq.getDeadline().getPeriod().getDuration(TimeUnit.SECONDS) == deadline.getDuration(TimeUnit.SECONDS));
            /* restore qos */
            dwq = dwq.withPolicy(dwq.getDeadline().withPeriod(Duration.infiniteDuration(env)));
            dataWriter.setQos(dwq);
            assertTrue("Check for valid DataWriterQos value", dwq.getDeadline().getPeriod().isInfinite());
        } catch (Exception ex) {
            fail("Failed to restore the DataWriterQos");
        }
    }

    /**
     * Test method for {@link org.omg.dds.core.Entity#enable()}.
     */
    @Test
    public void testEnable() {
        checkValidEntities();
        try {
            dataWriter.enable();
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
            statuses = dataWriter.getStatusChanges();
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
            ih = dataWriter.getInstanceHandle();
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
            dataWriter.retain();
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
            senv = dataWriter.getEnvironment();
        } catch (Exception e) {
            fail("Exception occured while calling getEnvironment(): " + util.printException(e));
        }
        assertTrue("Check for valid ServiceEnvironment", senv != null);
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.pub.DataWriter#writeDispose(java.lang.Object)}.
     */
    @Test
    public void testWriteDisposeTYPE() {
        checkValidEntities();
        ProtoMsg.Builder messageBuilder = ProtoMsg.newBuilder();
        messageBuilder.setId(1);
        messageBuilder.setName("testWriteDisposeTYPE");
        ProtoMsg message = messageBuilder.build();
        try {
            dataWriter.writeDispose(message);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testWriteDisposeTYPE", e, false));
        }
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.pub.DataWriter#writeDispose(java.lang.Object)}.
     */
    @Test
    public void testWriteDisposeTYPENull() {
        checkValidEntities();
        boolean exceptionOccured = false;
        try {
            dataWriter.writeDispose(null);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testWriteDisposeTYPENull", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException has occured failed", exceptionOccured);
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.pub.DataWriter#writeDispose(java.lang.Object, org.omg.dds.core.Time)}
     * .
     */
    @Test
    public void testWriteDisposeTYPETime() {
        checkValidEntities();
        ProtoMsg.Builder messageBuilder = ProtoMsg.newBuilder();
        messageBuilder.setId(1);
        messageBuilder.setName("testWriteDisposeTYPETime");
        ProtoMsg message = messageBuilder.build();
        Time t = Time.newTime(5, TimeUnit.SECONDS, env);
        try {
            dataWriter.writeDispose(message, t);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testWriteDisposeTYPETime", e, false));
        }
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.pub.DataWriter#writeDispose(java.lang.Object, org.omg.dds.core.Time)}
     * .
     */
    @Test
    public void testWriteDisposeTYPETimeNull() {
        checkValidEntities();
        ProtoMsg.Builder messageBuilder = ProtoMsg.newBuilder();
        messageBuilder.setId(1);
        messageBuilder.setName("testWriteDisposeTYPETimeNull");
        ProtoMsg message = messageBuilder.build();
        Time t = null;
        boolean exceptionOccured = false;
        try {
            dataWriter.writeDispose(message, t);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testWriteDisposeTYPETimeNull", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException has occured failed", exceptionOccured);
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.pub.DataWriter#writeDispose(java.lang.Object, long, java.util.concurrent.TimeUnit)}
     * .
     */
    @Test
    public void testWriteDisposeTYPELongTimeUnit() {
        checkValidEntities();
        ProtoMsg.Builder messageBuilder = ProtoMsg.newBuilder();
        messageBuilder.setId(1);
        messageBuilder.setName("testWriteDisposeTYPELongTimeUnit");
        ProtoMsg message = messageBuilder.build();
        try {
            dataWriter.writeDispose(message, 5, TimeUnit.SECONDS);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testWriteDisposeTYPELongTimeUnit", e, false));
        }
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.pub.DataWriter#writeDispose(java.lang.Object, long, java.util.concurrent.TimeUnit)}
     * .
     */
    @Test
    public void testWriteDisposeTYPELongTimeUnitNull() {
        checkValidEntities();
        ProtoMsg.Builder messageBuilder = ProtoMsg.newBuilder();
        messageBuilder.setId(1);
        messageBuilder.setName("testWriteDisposeTYPELongTimeUnitNull");
        ProtoMsg message = messageBuilder.build();
        boolean exceptionOccured = false;
        try {
            dataWriter.writeDispose(message, 5, null);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testWriteDisposeTYPETimeNull", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException has occured failed", exceptionOccured);
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.pub.DataWriter#writeDispose(java.lang.Object, org.omg.dds.core.InstanceHandle)}
     * .
     */
    @Test
    public void testWriteDisposeTYPEInstanceHandle() {
        checkValidEntities();
        ProtoMsg.Builder messageBuilder = ProtoMsg.newBuilder();
        messageBuilder.setId(1);
        messageBuilder.setName("testWriteDisposeTYPEInstanceHandle");
        ProtoMsg message = messageBuilder.build();
        InstanceHandle ih = null;
        try {
            ih = dataWriter.registerInstance(message);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testWriteDisposeTYPEInstanceHandle", e, false));
        }
        try {
            dataWriter.writeDispose(message, ih);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testWriteDisposeTYPEInstanceHandle", e, false));
        }
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.pub.DataWriter#writeDispose(java.lang.Object, org.omg.dds.core.InstanceHandle, org.omg.dds.core.Time)}
     * .
     */
    @Test
    public void testWriteDisposeTYPEInstanceHandleTime() {
        checkValidEntities();
        ProtoMsg.Builder messageBuilder = ProtoMsg.newBuilder();
        messageBuilder.setId(1);
        messageBuilder.setName("testWriteDisposeTYPEInstanceHandleTime");
        ProtoMsg message = messageBuilder.build();
        Time t = Time.newTime(5, TimeUnit.SECONDS, env);
        InstanceHandle ih = null;
        try {
            ih = dataWriter.registerInstance(message);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testWriteDisposeTYPEInstanceHandleTime", e, false));
        }
        try {
            dataWriter.writeDispose(message, ih, t);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testWriteDisposeTYPEInstanceHandleTime", e, false));
        }
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.pub.DataWriter#writeDispose(java.lang.Object, org.omg.dds.core.InstanceHandle, long, java.util.concurrent.TimeUnit)}
     * .
     */
    @Test
    public void testWriteDisposeTYPEInstanceHandleLongTimeUnit() {
        checkValidEntities();
        ProtoMsg.Builder messageBuilder = ProtoMsg.newBuilder();
        messageBuilder.setId(1);
        messageBuilder.setName("testWriteDisposeTYPEInstanceHandleLongTimeUnit");
        ProtoMsg message = messageBuilder.build();
        InstanceHandle ih = null;
        try {
            ih = dataWriter.registerInstance(message);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testWriteDisposeTYPEInstanceHandleLongTimeUnit", e, false));
        }
        try {
            dataWriter.writeDispose(message, ih, 5, TimeUnit.SECONDS);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testWriteDisposeTYPEInstanceHandleLongTimeUnit", e, false));
        }
    }

    /**
     * Test method for {@link org.opensplice.dds.pub.DataWriter#getKeyValue()}.
     */
    @Test
    public void testGetKeyValue() {
        checkValidEntities();
        ProtoMsg.Builder messageBuilder = ProtoMsg.newBuilder();
        messageBuilder.setId(1);
        messageBuilder.setName("testGetKeyValue");
        ProtoMsg message = messageBuilder.build();
        InstanceHandle ih = null;
        ProtoMsg result = null;
        try {
            ih = dataWriter.registerInstance(message);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetKeyValue", e, false));
        }
        try {
            result = dataWriter.getKeyValue(message, ih);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetKeyValue", e, false));
        }
        assertTrue("Check for valid Message Key value failed", result.getId() == message.getId());
    }

    /**
     * Test method for verifying same destination order is enforced by the
     * publisher when ordered access is configured for group scope.
     */
    @Test
    public void testGroupOrderedAccessSetInconsistentDestinationOrder() {
        TopicQos tq = null;
        Topic<ProtoMsg> t = null;
        PublisherQos pq = null;
        Publisher p = null;
        DataWriterQos wq = null;
        DataWriter<ProtoMsg> w1 = null;
        DataWriter<ProtoMsg> w2 = null;

        PolicyFactory policyFactory = env.getSPI().getPolicyFactory();

        try {
            pq = participant.getDefaultPublisherQos().withPolicies(
                policyFactory.Presentation().withAccessScope(AccessScopeKind.GROUP).withOrderedAccess(true),
                policyFactory.EntityFactory().withAutoEnableCreatedEntities(true));
            tq = participant.getDefaultTopicQos().withPolicies(
                policyFactory.DestinationOrder().withKind(DestinationOrder.Kind.BY_RECEPTION_TIMESTAMP),
                policyFactory.Reliability().withKind(Reliability.Kind.RELIABLE));

            p = participant.createPublisher(pq);
            assertTrue("Check for valid Publisher object", p instanceof Publisher);
            t = participant.createTopic("orderedAccessTopic", ProtoMsg.class);
            assertTrue("Check for valid Topic object", t instanceof Topic);

            wq = p.copyFromTopicQos(p.getDefaultDataWriterQos(), tq);
            w1 = p.createDataWriter(t, wq);
            assertTrue ("Check for valid DataWriter object", w1 instanceof DataWriter);
            wq = wq.withPolicies(
                policyFactory.DestinationOrder().withKind(DestinationOrder.Kind.BY_SOURCE_TIMESTAMP));
            w2 = p.createDataWriter(t, wq);
        } catch (InconsistentPolicyException e) {
            assertTrue ("Verify creation of second DataWriter object failed " + util.printException (e), w2 == null);
        } catch (Exception e) {
            fail("Exception occured while running test: " + util.printException(e));
        }
    }

    /**
     * Test method for verifying writers are allowed different destination
     * order policies when ordered access is configured for topic scope.
     */
    @Test
    public void testTopicOrderedAccessSetDestinationOrder() {
        TopicQos tq = null;
        Topic<ProtoMsg> t = null;
        PublisherQos pq = null;
        Publisher p = null;
        DataWriterQos wq = null;
        DataWriter<ProtoMsg> w1 = null;
        DataWriter<ProtoMsg> w2 = null;

        PolicyFactory policyFactory = env.getSPI().getPolicyFactory();

        try {
            pq = participant.getDefaultPublisherQos().withPolicies(
                policyFactory.Presentation().withAccessScope(AccessScopeKind.TOPIC).withOrderedAccess(true),
                policyFactory.EntityFactory().withAutoEnableCreatedEntities(true));
            tq = participant.getDefaultTopicQos().withPolicies(
                policyFactory.DestinationOrder().withKind(DestinationOrder.Kind.BY_RECEPTION_TIMESTAMP),
                policyFactory.Reliability().withKind(Reliability.Kind.RELIABLE));

            p = participant.createPublisher(pq);
            assertTrue("Check for valid Publisher object", p instanceof Publisher);
            t = participant.createTopic("orderedAccessTopic", ProtoMsg.class);
            assertTrue("Check for valid Topic object", t instanceof Topic);

            wq = p.copyFromTopicQos(p.getDefaultDataWriterQos(), tq);
            w1 = p.createDataWriter(t, wq);
            assertTrue ("Check for valid DataWriter object", w1 instanceof DataWriter);
            wq = wq.withPolicies(
                policyFactory.DestinationOrder().withKind(DestinationOrder.Kind.BY_SOURCE_TIMESTAMP));
            w2 = p.createDataWriter(t, wq);
            assertTrue ("Check for valid DataWriter object", w2 instanceof DataWriter);
        } catch (Exception e) {
            fail("Exception occured while running test: " + util.printException(e));
        }
    }
}
