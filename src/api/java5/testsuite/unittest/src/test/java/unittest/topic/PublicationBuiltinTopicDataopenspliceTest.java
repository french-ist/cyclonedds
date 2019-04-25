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
import org.omg.dds.core.policy.DurabilityService;
import org.omg.dds.core.policy.GroupData;
import org.omg.dds.core.policy.LatencyBudget;
import org.omg.dds.core.policy.Lifespan;
import org.omg.dds.core.policy.Liveliness;
import org.omg.dds.core.policy.Ownership;
import org.omg.dds.core.policy.OwnershipStrength;
import org.omg.dds.core.policy.Partition;
import org.omg.dds.core.policy.Presentation;
import org.omg.dds.core.policy.Reliability;
import org.omg.dds.core.policy.TopicData;
import org.omg.dds.core.policy.TypeConsistencyEnforcement;
import org.omg.dds.core.policy.UserData;
import org.omg.dds.core.status.PublicationMatchedStatus;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.domain.DomainParticipantFactory;
import org.omg.dds.pub.DataWriter;
import org.omg.dds.pub.Publisher;
import org.omg.dds.sub.DataReader;
import org.omg.dds.sub.Subscriber;
import org.omg.dds.topic.BuiltinTopicKey;
import org.omg.dds.topic.PublicationBuiltinTopicData;
import org.omg.dds.topic.Topic;
import org.omg.dds.type.typeobject.TypeObject;

import Test.Msg;

import org.eclipse.cyclonedds.test.AbstractUtilities;
import org.eclipse.cyclonedds.test.DataWriterListenerClass;

/**
 * @author Thijs
 *
 */
public class PublicationBuiltinTopicDataopenspliceTest {

    private final static int                   DOMAIN_ID   = 123;
    private static ServiceEnvironment          env;
    private static DomainParticipantFactory    dpf;
    private static AbstractUtilities           util        = AbstractUtilities.getInstance(PublicationBuiltinTopicDataopenspliceTest.class);
    private final static Properties            prop        = new Properties();
    private static DomainParticipant           participant = null;
    private static Publisher                   publisher   = null;
    private static Subscriber                  subscriber  = null;
    private static Topic<Msg>                  topic       = null;
    private static DataReader<Msg>             dataReader  = null;
    private static String                      topicName   = "PublicationBuiltinTopicDataTest";
    private static PublicationBuiltinTopicData pbtd        = null;
    private final static Semaphore             sem         = new Semaphore(0);

    @SuppressWarnings("unchecked")
    @BeforeClass
    public static void init() {
        try {
            String serviceEnvironment = System.getProperty("JAVA5PSM_SERVICE_ENV");
            assertTrue("Check for valid ServiceEnvironment string", !serviceEnvironment.equals(""));
            System.setProperty(ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY, serviceEnvironment);
            env = ServiceEnvironment.createInstance(PublicationBuiltinTopicDataopenspliceTest.class.getClassLoader());
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
            DataWriter<Msg> dw = publisher.createDataWriter(topic);
            assertTrue("Check for valid DataWriter object failed", dw instanceof DataWriter);
            DataWriterListenerClass dwl = new DataWriterListenerClass(sem);
            dw.setListener(dwl, PublicationMatchedStatus.class);
            dataReader = subscriber.createDataReader(topic);
            assertTrue("Check for valid DataReader object failed", dataReader instanceof DataReader);

            boolean triggerOccured = false;
            try {
                triggerOccured = sem.tryAcquire(10, TimeUnit.SECONDS);
            } catch (Exception e) {
                fail("Exception occured while initiating the PublicationBuiltinTopicDataTest class: " + util.printException(e));
            }
            assertTrue("Check for PublicationMatchedStatus failed", triggerOccured);

            Set<InstanceHandle> subscriptions = dataReader.getMatchedPublications();
            for (InstanceHandle ih : subscriptions) {
                PublicationBuiltinTopicData data = dataReader.getMatchedPublicationData(ih);
                if (data != null && data.getTopicName().equals(topicName)) {
                    pbtd = data;
                }
            }
            assertTrue("Check for valid PublicationBuiltinTopicData object failed", pbtd != null);
        } catch (Exception e) {
            fail("Exception occured while initiating the PublicationBuiltinTopicDataTest class: " + util.printException(e));
        }

    }

    private void checkValidEntities() {
        assertTrue("Check for valid DomainParticipant object failed", participant instanceof DomainParticipant);
        assertTrue("Check for valid Publisher object failed", publisher instanceof Publisher);
        assertTrue("Check for valid Subscriber object failed", subscriber instanceof Subscriber);
        assertTrue("Check for valid Topic object failed", topic instanceof Topic);
        assertTrue("Check for valid DataReader object failed", dataReader instanceof DataReader);
        assertTrue("Check for valid PublicationBuiltinTopicData object failed", pbtd != null);
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
     * Test method for {@link org.omg.dds.topic.PublicationBuiltinTopicData#getKey()}.
     */
    //@tTest
    public void testGetKey() {
        checkValidEntities();
        BuiltinTopicKey result = null;
        BuiltinTopicKey clone = null;
        try {
            result = pbtd.getKey();
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
     * Test method for {@link org.omg.dds.topic.PublicationBuiltinTopicData#getParticipantKey()}.
     */
    //@tTest
    public void testGetParticipantKey() {
        checkValidEntities();
        BuiltinTopicKey result = null;
        try {
            result = pbtd.getParticipantKey();
            assertTrue("Check for valid BuiltinTopicKey object", result != null);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetParticipantKey", e, false));
        }
        assertTrue("Check for valid BuiltinTopicKey object", util.objectCheck("testGetParticipantKey", result));
    }

    /**
     * Test method for {@link org.omg.dds.topic.BuiltinTopicKey#getValue()}.
     */
    //@tTest
    public void testBuiltinTopicKeyGetValue() {
        checkValidEntities();
        BuiltinTopicKey result = null;
        try {
            result = pbtd.getParticipantKey();
            assertTrue("Check for valid BuiltinTopicKey object", result != null);
            int[] value = result.getValue();
            assertTrue("Check for valid BuiltinTopicKey object", value != null);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testBuiltinTopicKeyGetValue", e, false));
        }
        assertTrue("Check for valid BuiltinTopicKey value", util.objectCheck("testBuiltinTopicKeyGetValue", result));
    }

    /**
     * Test method for
     * {@link org.omg.dds.topic.BuiltinTopicKey#copyFrom(org.omg.dds.topic.BuiltinTopicKey)}
     * .
     */
    //@tTest
    public void testBuiltinTopicKeyCopyFrom() {
        checkValidEntities();
        BuiltinTopicKey result = null;
        try {
            result = pbtd.getParticipantKey();
            assertTrue("Check for valid BuiltinTopicKey object", result != null);
            result.copyFrom(pbtd.getParticipantKey().clone());
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testBuiltinTopicKeyCopyFrom", e, false));
        }
        assertTrue("Check for valid BuiltinTopicKey value", util.objectCheck("testBuiltinTopicKeyCopyFrom", result));
    }

    /**
     * Test method for
     * {@link org.omg.dds.topic.BuiltinTopicKey#copyFrom(org.omg.dds.topic.BuiltinTopicKey)}
     * .
     */
    //@tTest
    public void testBuiltinTopicKeyCopyFromNull() {
        checkValidEntities();
        BuiltinTopicKey result = null;
        boolean exceptionOccured = false;
        try {
            result = pbtd.getParticipantKey();
            assertTrue("Check for valid BuiltinTopicKey object", result != null);
            result.copyFrom(null);
        } catch (Exception e) {
            assertTrue("Expected IllegalArgumentException but got: " + util.printException(e), util.exceptionCheck("testBuiltinTopicKeyCopyFromNull", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException has occured failed", exceptionOccured);
    }

    /**
     * Test method for {@link org.omg.dds.topic.BuiltinTopicKey#clone()}.
     */
    //@tTest
    public void testBuiltinTopicKeyClone() {
        checkValidEntities();
        BuiltinTopicKey result = null;
        BuiltinTopicKey clone = null;
        try {
            result = pbtd.getParticipantKey();
            assertTrue("Check for valid BuiltinTopicKey object", result != null);
            clone = result.clone();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testBuiltinTopicKeyClone", e, false));
        }
        assertTrue("Check for valid BuiltinTopicKey value", util.objectCheck("testBuiltinTopicKeyClone", clone));
    }

    /**
     * Test method for {@link org.omg.dds.core.DDSObject#getEnvironment()}.
     */
    //@tTest
    public void testBuiltinTopicKeyGetEnvironment() {
        checkValidEntities();
        BuiltinTopicKey result = null;
        try {
            result = pbtd.getParticipantKey();
            assertTrue("Check for valid BuiltinTopicKey object", result != null);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testBuiltinTopicKeyClone", e, false));
        }
        assertTrue("Check for valid BuiltinTopicKey env object", result.getEnvironment() != null);
    }

    /**
     * Test method for
     * {@link org.omg.dds.topic.PublicationBuiltinTopicData#getTopicName()}.
     */
    //@tTest
    public void testGetTopicName() {
        checkValidEntities();
        String result = null;
        try {
            result = pbtd.getTopicName();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetTopicName", e, false));
        }
        assertTrue("Check for valid Name value", util.objectCheck("testGetTopicName", result));
        if (result != null) {
            assertTrue("Check for valid topicName", result.equals(topicName));
        }
    }

    /**
     * Test method for {@link org.omg.dds.topic.PublicationBuiltinTopicData#getTypeName()}.
     */
    //@tTest
    public void testGetTypeName() {
        checkValidEntities();
        String result = null;
        try {
            result = pbtd.getTypeName();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetTopicName", e, false));
        }
        assertTrue("Check for valid Name value", util.objectCheck("testGetTopicName", result));
        if (result != null) {
            assertTrue("Check for valid topicName", result.equals(topic.getTypeName()));
        }
    }

    /**
     * Test method for {@link org.omg.dds.topic.PublicationBuiltinTopicData#getEquivalentTypeName()}.
     */
    //@tTest
    public void testGetEquivalentTypeName() {
        checkValidEntities();
        List<String> result = null;
        try {
            result = pbtd.getEquivalentTypeName();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetEquivalentTypeName", e, false));
        }
        assertTrue("Check for valid Name value", util.objectCheck("testGetEquivalentTypeName", result));
    }

    /**
     * Test method for {@link org.omg.dds.topic.PublicationBuiltinTopicData#getBaseTypeName()}.
     */
    //@tTest
    public void testGetBaseTypeName() {
        checkValidEntities();
        List<String> result = null;
        try {
            result = pbtd.getBaseTypeName();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetBaseTypeName", e, false));
        }
        assertTrue("Check for valid Name value", util.objectCheck("testGetBaseTypeName", result));
    }

    /**
     * Test method for {@link org.omg.dds.topic.PublicationBuiltinTopicData#getType()}.
     */
    //@tTest
    public void testGetType() {
        checkValidEntities();
        TypeObject result = null;
        try {
            result = pbtd.getType();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetType", e, false));
        }
        assertTrue("Check for valid TypeObject object", util.objectCheck("testGetType", result));
    }

    /**
     * Test method for {@link org.omg.dds.topic.PublicationBuiltinTopicData#getDurability()}.
     */
    //@tTest
    public void testGetDurability() {
        checkValidEntities();
        Durability result = null;
        try {
            result = pbtd.getDurability();
        } catch (Exception ex) {
            fail("Failed to get the Durability qos from the PublicationBuiltinTopicData");
        }
        assertTrue("Check for valid Durability object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.topic.PublicationBuiltinTopicData#getDurabilityService()}.
     */
    //@tTest
    public void testGetDurabilityService() {
        checkValidEntities();
        DurabilityService result = null;
        try {
            result = pbtd.getDurabilityService();
        } catch (Exception e) {
            assertTrue("UnsupportedOperationException expected but got: " + util.printException(e), util.exceptionCheck("testGetDurabilityService", e, e instanceof UnsupportedOperationException));
        }
        assertTrue("Check for invalid DurabilityService object failed", result == null);
    }

    /**
     * Test method for {@link org.omg.dds.topic.PublicationBuiltinTopicData#getDeadline()}.
     */
    //@tTest
    public void testGetDeadline() {
        checkValidEntities();
        Deadline result = null;
        try {
            result = pbtd.getDeadline();
        } catch (Exception ex) {
            fail("Failed to get the Deadline qos from the PublicationBuiltinTopicData");
        }
        assertTrue("Check for valid Deadline object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.topic.PublicationBuiltinTopicData#getLatencyBudget()}.
     */
    //@tTest
    public void testGetLatencyBudget() {
        checkValidEntities();
        LatencyBudget result = null;
        try {
            result = pbtd.getLatencyBudget();
        } catch (Exception ex) {
            fail("Failed to get the LatencyBudget qos from the PublicationBuiltinTopicData");
        }
        assertTrue("Check for valid LatencyBudget object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.topic.PublicationBuiltinTopicData#getLiveliness()}.
     */
    //@tTest
    public void testGetLiveliness() {
        checkValidEntities();
        Liveliness result = null;
        try {
            result = pbtd.getLiveliness();
        } catch (Exception ex) {
            fail("Failed to get the Liveliness qos from the PublicationBuiltinTopicData");
        }
        assertTrue("Check for valid Liveliness object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.topic.PublicationBuiltinTopicData#getReliability()}.
     */
    //@tTest
    public void testGetReliability() {
        checkValidEntities();
        Reliability result = null;
        try {
            result = pbtd.getReliability();
        } catch (Exception ex) {
            fail("Failed to get the Reliability qos from the PublicationBuiltinTopicData");
        }
        assertTrue("Check for valid Reliability object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.topic.PublicationBuiltinTopicData#getLifespan()}.
     */
    //@tTest
    public void testGetLifespan() {
        checkValidEntities();
        Lifespan result = null;
        try {
            result = pbtd.getLifespan();
        } catch (Exception ex) {
            fail("Failed to get the Lifespan qos from the PublicationBuiltinTopicData");
        }
        assertTrue("Check for valid Lifespan object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.topic.PublicationBuiltinTopicData#getUserData()}.
     */
    //@tTest
    public void testGetUserData() {
        checkValidEntities();
        UserData result = null;
        try {
            result = pbtd.getUserData();
        } catch (Exception ex) {
            fail("Failed to get the UserData qos from the PublicationBuiltinTopicData");
        }
        assertTrue("Check for valid UserData object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.topic.PublicationBuiltinTopicData#getOwnership()}.
     */
    //@tTest
    public void testGetOwnership() {
        checkValidEntities();
        Ownership result = null;
        try {
            result = pbtd.getOwnership();
        } catch (Exception ex) {
            fail("Failed to get the Ownership qos from the PublicationBuiltinTopicData");
        }
        assertTrue("Check for valid Ownership object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.topic.PublicationBuiltinTopicData#getOwnershipStrength()}.
     */
    //@tTest
    public void testGetOwnershipStrength() {
        checkValidEntities();
        OwnershipStrength result = null;
        try {
            result = pbtd.getOwnershipStrength();
        } catch (Exception ex) {
            fail("Failed to get the OwnershipStrength qos from the PublicationBuiltinTopicData");
        }
        assertTrue("Check for valid OwnershipStrength object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.topic.PublicationBuiltinTopicData#getDestinationOrder()}.
     */
    //@tTest
    public void testGetDestinationOrder() {
        checkValidEntities();
        DestinationOrder result = null;
        try {
            result = pbtd.getDestinationOrder();
        } catch (Exception ex) {
            fail("Failed to get the DestinationOrder qos from the PublicationBuiltinTopicData");
        }
        assertTrue("Check for valid DestinationOrder object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.topic.PublicationBuiltinTopicData#getPresentation()}.
     */
    //@tTest
    public void testGetPresentation() {
        checkValidEntities();
        Presentation result = null;
        try {
            result = pbtd.getPresentation();
        } catch (Exception ex) {
            fail("Failed to get the Presentation qos from the PublicationBuiltinTopicData");
        }
        assertTrue("Check for valid Presentation object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.topic.PublicationBuiltinTopicData#getPartition()}.
     */
    //@tTest
    public void testGetPartition() {
        checkValidEntities();
        Partition result = null;
        try {
            result = pbtd.getPartition();
        } catch (Exception ex) {
            fail("Failed to get the Partition qos from the PublicationBuiltinTopicData");
        }
        assertTrue("Check for valid Partition object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.topic.PublicationBuiltinTopicData#getTopicData()}.
     */
    //@tTest
    public void testGetTopicData() {
        checkValidEntities();
        TopicData result = null;
        try {
            result = pbtd.getTopicData();
        } catch (Exception ex) {
            fail("Failed to get the TopicData qos from the PublicationBuiltinTopicData");
        }
        assertTrue("Check for valid TopicData object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.topic.PublicationBuiltinTopicData#getGroupData()}.
     */
    //@tTest
    public void testGetGroupData() {
        checkValidEntities();
        GroupData result = null;
        try {
            result = pbtd.getGroupData();
        } catch (Exception ex) {
            fail("Failed to get the GroupData qos from the PublicationBuiltinTopicData");
        }
        assertTrue("Check for valid GroupData object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.topic.PublicationBuiltinTopicData#getRepresentation()}.
     */
    //@tTest
    public void testGetRepresentation() {
        checkValidEntities();
        DataRepresentation result = null;
        try {
            result = pbtd.getRepresentation();
        } catch (Exception ex) {
            fail("Failed to get the DataRepresentation qos from the PublicationBuiltinTopicData");
        }
        assertTrue("Check for valid DataRepresentation object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.topic.PublicationBuiltinTopicData#getTypeConsistency()}.
     */
    //@tTest
    public void testGetTypeConsistency() {
        checkValidEntities();
        TypeConsistencyEnforcement result = null;
        try {
            result = pbtd.getTypeConsistency();
        } catch (Exception ex) {
            fail("Failed to get the TypeConsistencyEnforcement qos from the PublicationBuiltinTopicData");
        }
        assertTrue("Check for valid TypeConsistencyEnforcement object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.topic.PublicationBuiltinTopicData#copyFrom(org.omg.dds.topic.PublicationBuiltinTopicData)}.
     */
    //@tTest
    public void testCopyFrom() {
        checkValidEntities();
        PublicationBuiltinTopicData result = null;
        PublicationBuiltinTopicData clone = pbtd;
        try {
            result = pbtd.clone();
            result.copyFrom(clone);
        } catch (Exception ex) {
            fail("Failed to get the PublicationBuiltinTopicData object");
        }
        assertTrue("Check for valid PublicationBuiltinTopicData object failed", result != null);
    }

    /**
     * Test method for
     * {@link org.omg.dds.topic.PublicationBuiltinTopicData#copyFrom(org.omg.dds.topic.PublicationBuiltinTopicData)}
     * .
     */
    //@tTest
    public void testCopyFromNull() {
        checkValidEntities();
        PublicationBuiltinTopicData result = pbtd;
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
     * {@link org.omg.dds.topic.PublicationBuiltinTopicData#clone()}.
     */
    //@tTest
    public void testClone() {
        checkValidEntities();
        PublicationBuiltinTopicData result = null;
        try {
            result = pbtd.clone();
        } catch (Exception ex) {
            fail("Failed to get the PublicationBuiltinTopicData object");
        }
        assertTrue("Check for valid PublicationBuiltinTopicData object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.core.DDSObject#getEnvironment()}.
     */
    //@tTest
    public void testGetEnvironment() {
        checkValidEntities();
        ServiceEnvironment senv = null;
        try {
            senv = pbtd.getEnvironment();
        } catch (Exception e) {
            fail("Exception occured while calling getEnvironment(): " + util.printException(e));
        }
        assertTrue("Check for valid ServiceEnvironment", senv != null);
    }

}
