/**
 *
 */
package unittest.topic;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.dds.core.AlreadyClosedException;
import org.omg.dds.core.InstanceHandle;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.policy.DataRepresentation;
import org.omg.dds.core.policy.Deadline;
import org.omg.dds.core.policy.DestinationOrder;
import org.omg.dds.core.policy.Durability;
import org.omg.dds.core.policy.GroupData;
import org.omg.dds.core.policy.LatencyBudget;
import org.omg.dds.core.policy.Liveliness;
import org.omg.dds.core.policy.Ownership;
import org.omg.dds.core.policy.Partition;
import org.omg.dds.core.policy.Presentation;
import org.omg.dds.core.policy.Reliability;
import org.omg.dds.core.policy.TimeBasedFilter;
import org.omg.dds.core.policy.TopicData;
import org.omg.dds.core.policy.TypeConsistencyEnforcement;
import org.omg.dds.core.policy.UserData;
import org.omg.dds.core.status.SubscriptionMatchedStatus;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.domain.DomainParticipantFactory;
import org.omg.dds.pub.DataWriter;
import org.omg.dds.pub.Publisher;
import org.omg.dds.sub.DataReader;
import org.omg.dds.sub.Subscriber;
import org.omg.dds.topic.BuiltinTopicKey;
import org.omg.dds.topic.SubscriptionBuiltinTopicData;
import org.omg.dds.topic.Topic;
import org.omg.dds.type.typeobject.TypeObject;

import Test.Msg;

import org.eclipse.cyclonedds.test.AbstractUtilities;
import org.eclipse.cyclonedds.test.DataReaderListenerClass;

public class SubscriptionBuiltinTopicDataopenspliceTest {

    private final static int                    DOMAIN_ID   = 123;
    private static ServiceEnvironment           env;
    private static DomainParticipantFactory     dpf;
    private static AbstractUtilities            util        = AbstractUtilities.getInstance(SubscriptionBuiltinTopicDataopenspliceTest.class);
    private final static Properties             prop        = new Properties();
    private static DomainParticipant            participant = null;
    private static Publisher                    publisher   = null;
    private static Subscriber                   subscriber  = null;
    private static Topic<Msg>                   topic       = null;
    private static DataReader<Msg>              dataReader  = null;
    private static String                       topicName   = "SubscriptionBuiltinTopicDataTest";
    private static SubscriptionBuiltinTopicData sbtd        = null;
    private final static Semaphore              sem         = new Semaphore(0);

    @SuppressWarnings("unchecked")
    @BeforeClass
    public static void init() {
        try {
            String serviceEnvironment = System.getProperty("JAVA5PSM_SERVICE_ENV");
            assertTrue("Check for valid ServiceEnvironment string", !serviceEnvironment.equals(""));
            System.setProperty(ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY, serviceEnvironment);
            env = ServiceEnvironment.createInstance(SubscriptionBuiltinTopicDataopenspliceTest.class.getClassLoader());
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
            DataReaderListenerClass drl = new DataReaderListenerClass(sem);
            dataReader.setListener(drl, SubscriptionMatchedStatus.class);
            DataWriter<Msg> dw = publisher.createDataWriter(topic);
            assertTrue("Check for valid DataWriter object failed", dw instanceof DataWriter);

            boolean triggerOccured = false;
            try {
                triggerOccured = sem.tryAcquire(10, TimeUnit.SECONDS);
            } catch (Exception e) {
                fail("Exception occured while initiating the SubscriptionBuiltinTopicDataTest class: " + util.printException(e));
            }
            assertTrue("Check for SubscriptionMatchedStatus failed", triggerOccured);
            Set<InstanceHandle> subscriptions = dw.getMatchedSubscriptions();
            for (InstanceHandle ih : subscriptions) {
                SubscriptionBuiltinTopicData data = dw.getMatchedSubscriptionData(ih);
                if (data != null && data.getTopicName().equals(topicName)) {
                    sbtd = data;
                }
            }
            assertTrue("Check for valid SubscriptionBuiltinTopicData object failed", sbtd != null);
        } catch (Exception e) {
            fail("Exception occured while initiating the SubscriptionBuiltinTopicDataTest class: " + util.printException(e));
        }

    }

    private void checkValidEntities() {
        assertTrue("Check for valid DomainParticipant object failed", participant instanceof DomainParticipant);
        assertTrue("Check for valid Publisher object failed", publisher instanceof Publisher);
        assertTrue("Check for valid Subscriber object failed", subscriber instanceof Subscriber);
        assertTrue("Check for valid Topic object failed", topic instanceof Topic);
        assertTrue("Check for valid DataReader object failed", dataReader instanceof DataReader);
        assertTrue("Check for valid SubscriptionBuiltinTopicData object failed", sbtd != null);
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
     * Test method for {@link org.omg.dds.topic.SubscriptionBuiltinTopicData#getKey()}.
     */
    @Test
    public void testGetKey() {
        checkValidEntities();
        BuiltinTopicKey result = null;
        BuiltinTopicKey clone = null;
        try {
            result = sbtd.getKey();
            assertTrue("Check for valid BuiltinTopicKey object", result != null);
            clone = result.clone();
            assertTrue("Check for valid BuiltinTopicKey object", clone != null);
            clone.copyFrom(result);
            assertTrue("Check for valid BuiltinTopicKey object", clone != null);
            assertTrue("Check for valid value", result.getValue() != null);
            assertTrue("Check for valid env value", result.getEnvironment() != null);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetKey", e, false));
        }
        assertTrue("Check for valid BuiltinTopicKey object", util.objectCheck("testGetKey", result));
    }

    /**
     * Test method for {@link org.omg.dds.topic.SubscriptionBuiltinTopicData#getParticipantKey()}.
     */
    @Test
    public void testGetParticipantKey() {
        checkValidEntities();
        BuiltinTopicKey result = null;
        try {
            result = sbtd.getParticipantKey();
            assertTrue("Check for valid BuiltinTopicKey object", result != null);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetParticipantKey", e, false));
        }
        assertTrue("Check for valid BuiltinTopicKey object", util.objectCheck("testGetParticipantKey", result));
    }

    /**
     * Test method for
     * {@link org.omg.dds.topic.SubscriptionBuiltinTopicData#getTopicName()}.
     */
    @Test
    public void testGetTopicName() {
        checkValidEntities();
        String result = null;
        try {
            result = sbtd.getTopicName();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetTopicName", e, false));
        }
        assertTrue("Check for valid Name value", util.objectCheck("testGetTopicName", result));
        if (result != null) {
            assertTrue("Check for valid topicName", result.equals(topicName));
        }
    }

    /**
     * Test method for {@link org.omg.dds.topic.SubscriptionBuiltinTopicData#getTypeName()}.
     */
    @Test
    public void testGetTypeName() {
        checkValidEntities();
        String result = null;
        try {
            result = sbtd.getTypeName();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetTopicName", e, false));
        }
        assertTrue("Check for valid Name value", util.objectCheck("testGetTopicName", result));
        if (result != null) {
            assertTrue("Check for valid topicName", result.equals(topic.getTypeName()));
        }
    }

    /**
     * Test method for {@link org.omg.dds.topic.SubscriptionBuiltinTopicData#getEquivalentTypeName()}.
     */
    @Test
    public void testGetEquivalentTypeName() {
        checkValidEntities();
        List<String> result = null;
        try {
            result = sbtd.getEquivalentTypeName();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetEquivalentTypeName", e, false));
        }
        assertTrue("Check for valid Name value", util.objectCheck("testGetEquivalentTypeName", result));
    }

    /**
     * Test method for {@link org.omg.dds.topic.SubscriptionBuiltinTopicData#getBaseTypeName()}.
     */
    @Test
    public void testGetBaseTypeName() {
        checkValidEntities();
        List<String> result = null;
        try {
            result = sbtd.getBaseTypeName();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetBaseTypeName", e, false));
        }
        assertTrue("Check for valid Name value", util.objectCheck("testGetBaseTypeName", result));
    }

    /**
     * Test method for {@link org.omg.dds.topic.SubscriptionBuiltinTopicData#getType()}.
     */
    @Test
    public void testGetType() {
        checkValidEntities();
        TypeObject result = null;
        try {
            result = sbtd.getType();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetType", e, false));
        }
        assertTrue("Check for valid TypeObject object", util.objectCheck("testGetType", result));
    }

    /**
     * Test method for {@link org.omg.dds.topic.SubscriptionBuiltinTopicData#getDurability()}.
     */
    @Test
    public void testGetDurability() {
        checkValidEntities();
        Durability result = null;
        try {
            result = sbtd.getDurability();
        } catch (Exception ex) {
            fail("Failed to get the Durability qos from the SubscriptionBuiltinTopicData");
        }
        assertTrue("Check for valid Durability object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.topic.SubscriptionBuiltinTopicData#getDeadline()}.
     */
    @Test
    public void testGetDeadline() {
        checkValidEntities();
        Deadline result = null;
        try {
            result = sbtd.getDeadline();
        } catch (Exception ex) {
            fail("Failed to get the Deadline qos from the SubscriptionBuiltinTopicData");
        }
        assertTrue("Check for valid Deadline object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.topic.SubscriptionBuiltinTopicData#getLatencyBudget()}.
     */
    @Test
    public void testGetLatencyBudget() {
        checkValidEntities();
        LatencyBudget result = null;
        try {
            result = sbtd.getLatencyBudget();
        } catch (Exception ex) {
            fail("Failed to get the LatencyBudget qos from the SubscriptionBuiltinTopicData");
        }
        assertTrue("Check for valid LatencyBudget object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.topic.SubscriptionBuiltinTopicData#getLiveliness()}.
     */
    @Test
    public void testGetLiveliness() {
        checkValidEntities();
        Liveliness result = null;
        try {
            result = sbtd.getLiveliness();
        } catch (Exception ex) {
            fail("Failed to get the Liveliness qos from the SubscriptionBuiltinTopicData");
        }
        assertTrue("Check for valid Liveliness object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.topic.SubscriptionBuiltinTopicData#getReliability()}.
     */
    @Test
    public void testGetReliability() {
        checkValidEntities();
        Reliability result = null;
        try {
            result = sbtd.getReliability();
        } catch (Exception ex) {
            fail("Failed to get the Reliability qos from the SubscriptionBuiltinTopicData");
        }
        assertTrue("Check for valid Reliability object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.topic.SubscriptionBuiltinTopicData#getOwnership()}.
     */
    @Test
    public void testGetOwnership() {
        checkValidEntities();
        Ownership result = null;
        try {
            result = sbtd.getOwnership();
        } catch (Exception ex) {
            fail("Failed to get the Ownership qos from the SubscriptionBuiltinTopicData");
        }
        assertTrue("Check for valid Ownership object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.topic.SubscriptionBuiltinTopicData#getDestinationOrder()}.
     */
    @Test
    public void testGetDestinationOrder() {
        checkValidEntities();
        DestinationOrder result = null;
        try {
            result = sbtd.getDestinationOrder();
        } catch (Exception ex) {
            fail("Failed to get the DestinationOrder qos from the SubscriptionBuiltinTopicData");
        }
        assertTrue("Check for valid DestinationOrder object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.topic.SubscriptionBuiltinTopicData#getUserData()}.
     */
    @Test
    public void testGetUserData() {
        checkValidEntities();
        UserData result = null;
        try {
            result = sbtd.getUserData();
        } catch (Exception ex) {
            fail("Failed to get the UserData qos from the SubscriptionBuiltinTopicData");
        }
        assertTrue("Check for valid UserData object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.topic.SubscriptionBuiltinTopicData#getTimeBasedFilter()}.
     */
    @Test
    public void testGetTimeBasedFilter() {
        checkValidEntities();
        TimeBasedFilter result = null;
        try {
            result = sbtd.getTimeBasedFilter();
        } catch (Exception ex) {
            fail("Failed to get the TimeBasedFilter qos from the SubscriptionBuiltinTopicData");
        }
        assertTrue("Check for valid TimeBasedFilter object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.topic.SubscriptionBuiltinTopicData#getPresentation()}.
     */
    @Test
    public void testGetPresentation() {
        checkValidEntities();
        Presentation result = null;
        try {
            result = sbtd.getPresentation();
        } catch (Exception ex) {
            fail("Failed to get the Presentation qos from the SubscriptionBuiltinTopicData");
        }
        assertTrue("Check for valid Presentation object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.topic.SubscriptionBuiltinTopicData#getPartition()}.
     */
    @Test
    public void testGetPartition() {
        checkValidEntities();
        Partition result = null;
        try {
            result = sbtd.getPartition();
        } catch (Exception ex) {
            fail("Failed to get the Partition qos from the SubscriptionBuiltinTopicData");
        }
        assertTrue("Check for valid Partition object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.topic.SubscriptionBuiltinTopicData#getTopicData()}.
     */
    @Test
    public void testGetTopicData() {
        checkValidEntities();
        TopicData result = null;
        try {
            result = sbtd.getTopicData();
        } catch (Exception ex) {
            fail("Failed to get the TopicData qos from the SubscriptionBuiltinTopicData");
        }
        assertTrue("Check for valid TopicData object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.topic.SubscriptionBuiltinTopicData#getGroupData()}.
     */
    @Test
    public void testGetGroupData() {
        checkValidEntities();
        GroupData result = null;
        try {
            result = sbtd.getGroupData();
        } catch (Exception ex) {
            fail("Failed to get the GroupData qos from the SubscriptionBuiltinTopicData");
        }
        assertTrue("Check for valid GroupData object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.topic.SubscriptionBuiltinTopicData#getRepresentation()}.
     */
    @Test
    public void testGetRepresentation() {
        checkValidEntities();
        DataRepresentation result = null;
        try {
            result = sbtd.getRepresentation();
        } catch (Exception ex) {
            fail("Failed to get the DataRepresentation qos from the SubscriptionBuiltinTopicData");
        }
        assertTrue("Check for valid DataRepresentation object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.topic.SubscriptionBuiltinTopicData#getTypeConsistency()}.
     */
    @Test
    public void testGetTypeConsistency() {
        checkValidEntities();
        TypeConsistencyEnforcement result = null;
        try {
            result = sbtd.getTypeConsistency();
        } catch (Exception ex) {
            fail("Failed to get the TypeConsistencyEnforcement qos from the SubscriptionBuiltinTopicData");
        }
        assertTrue("Check for valid TypeConsistencyEnforcement object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.topic.SubscriptionBuiltinTopicData#copyFrom(org.omg.dds.topic.SubscriptionBuiltinTopicData)}.
     */
    @Test
    public void testCopyFrom() {
        checkValidEntities();
        SubscriptionBuiltinTopicData result = null;
        SubscriptionBuiltinTopicData clone = sbtd;
        try {
            result = sbtd.clone();
            result.copyFrom(clone);
        } catch (Exception ex) {
            fail("Failed to get the SubscriptionBuiltinTopicData object");
        }
        assertTrue("Check for valid SubscriptionBuiltinTopicData object failed", result != null);
    }

    /**
     * Test method for
     * {@link org.omg.dds.topic.SubscriptionBuiltinTopicData#copyFrom(org.omg.dds.topic.SubscriptionBuiltinTopicData)}
     * .
     */
    @Test
    public void testCopyFromNull() {
        checkValidEntities();
        SubscriptionBuiltinTopicData result = sbtd;
        boolean exceptionOccured = false;
        try {
            result.copyFrom(null);
        } catch (Exception e) {
            assertTrue("Expected IllegalArgumentException but got: " + util.printException(e), util.exceptionCheck("testCopyFromNull", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException has occured failed", exceptionOccured);
    }

    /**
     * Test method for
     * {@link org.omg.dds.topic.SubscriptionBuiltinTopicData#clone()}.
     */
    @Test
    public void testClone() {
        checkValidEntities();
        SubscriptionBuiltinTopicData result = null;
        try {
            result = sbtd.clone();
        } catch (Exception ex) {
            fail("Failed to get the SubscriptionBuiltinTopicData object");
        }
        assertTrue("Check for valid SubscriptionBuiltinTopicData object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.core.DDSObject#getEnvironment()}.
     */
    @Test
    public void testGetEnvironment() {
        checkValidEntities();
        ServiceEnvironment senv = null;
        try {
            senv = sbtd.getEnvironment();
        } catch (Exception e) {
            fail("Exception occured while calling getEnvironment(): " + util.printException(e));
        }
        assertTrue("Check for valid ServiceEnvironment", senv != null);
    }

}
