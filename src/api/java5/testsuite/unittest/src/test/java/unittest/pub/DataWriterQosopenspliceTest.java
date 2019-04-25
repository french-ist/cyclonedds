package unittest.pub;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.dds.core.AlreadyClosedException;
import org.omg.dds.core.Duration;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.domain.DomainParticipantFactory;
import org.omg.dds.pub.DataWriter;
import org.omg.dds.pub.DataWriterQos;
import org.omg.dds.pub.Publisher;
import org.omg.dds.topic.Topic;
import org.opensplice.dds.core.policy.OwnershipStrengthImpl;
import org.opensplice.dds.core.policy.WriterDataLifecycleImpl;
import org.opensplice.dds.pub.DataWriterQosImpl;

import Test.Msg;

import org.eclipse.cyclonedds.test.AbstractUtilities;

public class DataWriterQosopenspliceTest {

    private final static int                DOMAIN_ID   = 123;
    private static ServiceEnvironment       env;
    private static DomainParticipantFactory dpf;
    private static AbstractUtilities        util        = AbstractUtilities.getInstance(DataWriterQosopenspliceTest.class);
    private final static Properties         prop        = new Properties();
    private static DomainParticipant        participant = null;
    private static Publisher                publisher   = null;
    private static Topic<Msg>               topic       = null;
    private static DataWriter<Msg>          dataWriter  = null;
    private static String                   topicName   = "DataWriterQosopenspliceTest";
    private static DataWriterQosImpl        dwq         = null;

    @BeforeClass
    public static void init() {
        try {
            String serviceEnvironment = System.getProperty("JAVA5PSM_SERVICE_ENV");
            assertTrue("Check for valid ServiceEnvironment string", !serviceEnvironment.equals(""));
            System.setProperty(ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY, serviceEnvironment);
            env = ServiceEnvironment.createInstance(DataWriterQosopenspliceTest.class.getClassLoader());
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
            topic = participant.createTopic(topicName, Msg.class);
            assertTrue("Check for valid Topic object failed", topic instanceof Topic);
            dataWriter = publisher.createDataWriter(topic);
            assertTrue("Check for valid DataWriter object failed", dataWriter instanceof DataWriter);
            dwq = (DataWriterQosImpl) dataWriter.getQos();
            assertTrue("Check for valid DataWriterQos object failed", dwq instanceof DataWriterQos);
        } catch (Exception e) {
            fail("Exception occured while initiating the DataWriterQosopenspliceTest class: " + util.printException(e));
        }

    }

    private void checkValidEntities() {
        assertTrue("Check for valid DomainParticipant object failed", participant instanceof DomainParticipant);
        assertTrue("Check for valid Publisher object failed", publisher instanceof Publisher);
        assertTrue("Check for valid Topic object failed", topic instanceof Topic);
        assertTrue("Check for valid DataWriter object failed", dataWriter instanceof DataWriter);
        assertTrue("Check for valid DataWriterQos object failed", dwq instanceof DataWriterQos);
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
     * {@link org.opensplice.dds.core.policy.DataWriterQosImpl#getWriterDataLifecycle()}
     * .
     */
    //@tTest
    public void testGetWriterDataLifecycle() {
        checkValidEntities();
        WriterDataLifecycleImpl result = null;
        try {
            result = (WriterDataLifecycleImpl) dwq.getWriterDataLifecycle();
            assertTrue("Check for valid WriterDataLifecycle object failed", result != null);
            assertTrue("Check for valid WriterDataLifecycle PolicyClass object", result.getPolicyClass() != null);
            Duration d = Duration.newDuration(5, TimeUnit.SECONDS, env);
            result = (WriterDataLifecycleImpl) result.withAutoPurgeSuspendedSamplesDelay(d);
            assertTrue("Check for valid WriterDataLifecycle object", result != null);
            assertTrue("Check for valid Duration object", result.getAutoPurgeSuspendedSamplesDelay().getDuration(TimeUnit.SECONDS) == 5);

            result = (WriterDataLifecycleImpl) result.withAutoPurgeSuspendedSamplesDelay(3, TimeUnit.SECONDS);
            assertTrue("Check for valid WriterDataLifecycle object", result != null);
            assertTrue("Check for valid Duration object", result.getAutoPurgeSuspendedSamplesDelay().getDuration(TimeUnit.SECONDS) == 3);

            result = (WriterDataLifecycleImpl) result.withAutoUnregisterInstanceDelay(d);
            assertTrue("Check for valid WriterDataLifecycle object", result != null);
            assertTrue("Check for valid Duration object", result.getAutoUnregisterInstanceDelay().getDuration(TimeUnit.SECONDS) == 5);

            result = (WriterDataLifecycleImpl) result.withAutoUnregisterInstanceDelay(3, TimeUnit.SECONDS);
            assertTrue("Check for valid WriterDataLifecycle object", result != null);
            assertTrue("Check for valid Duration object", result.getAutoUnregisterInstanceDelay().getDuration(TimeUnit.SECONDS) == 3);

        } catch (Exception ex) {
            fail("Failed to get the WriterDataLifecycle qos from the DataWriterQos");
        }
        assertTrue("Check for valid WriterDataLifecycle object failed", result != null);
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.core.policy.DataWriterQosImpl#getOwnershipStrength()}
     * .
     */
    //@tTest
    public void testGetOwnershipStrength() {
        checkValidEntities();
        OwnershipStrengthImpl result = null;
        try {
            result = (OwnershipStrengthImpl) dwq.getOwnershipStrength();
            assertTrue("Check for valid OwnershipStrength object", result != null);
            assertTrue("Check for valid OwnershipStrength PolicyClass object", result.getPolicyClass() != null);
        } catch (Exception ex) {
            fail("Failed to get the OwnershipStrength qos from the DataWriterQos");
        }
        assertTrue("Check for valid OwnershipStrength object failed", result != null);
    }
}
