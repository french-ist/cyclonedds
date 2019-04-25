
package unittest.sub;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Iterator;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.dds.core.AlreadyClosedException;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.domain.DomainParticipantFactory;
import org.omg.dds.pub.DataWriter;
import org.omg.dds.pub.Publisher;
import org.omg.dds.sub.DataReader;
import org.omg.dds.sub.Sample;
import org.omg.dds.sub.Subscriber;
import org.omg.dds.topic.Topic;
import org.opensplice.dds.sub.SampleImpl;

import DDS.SampleInfo;
import Test.Msg;

import org.eclipse.cyclonedds.test.AbstractUtilities;

public class SampleopenspliceTest {

    private final static int                DOMAIN_ID   = 123;
    private static ServiceEnvironment       env;
    private static DomainParticipantFactory dpf;
    private static AbstractUtilities        util        = AbstractUtilities.getInstance(SampleopenspliceTest.class);
    private final static Properties         prop        = new Properties();
    private static DomainParticipant        participant = null;
    private static Publisher                publisher   = null;
    private static Subscriber               subscriber  = null;
    private static Topic<Msg>               topic       = null;
    private static DataReader<Msg>          dataReader  = null;
    private static DataWriter<Msg>          dataWriter  = null;
    private static String                   topicName   = "SampleopenspliceTest";
    private static Msg                      message     = new Msg(0, "SampleopenspliceTest");
    private static SampleImpl<Msg>          sample      = null;

    @BeforeClass
    public static void init() {
        try {
            String serviceEnvironment = System.getProperty("JAVA5PSM_SERVICE_ENV");
            assertTrue("Check for valid ServiceEnvironment string", !serviceEnvironment.equals(""));
            System.setProperty(ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY, serviceEnvironment);
            env = ServiceEnvironment.createInstance(SampleopenspliceTest.class.getClassLoader());
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
            dataWriter = publisher.createDataWriter(topic);
            assertTrue("Check for valid DataWriter object failed", dataWriter instanceof DataWriter);
            dataWriter.write(message);
            Thread.sleep(util.getWriteSleepTime());
            Iterator<Sample<Msg>> result = null;
            result = dataReader.take();
            if (result != null) {
                sample = (SampleImpl<Msg>) result.next();
                if (sample == null) {
                    fail("Failed to get a valid Sample");
                }
            } else {
                fail("Take failed to get a valid Sample");
            }
        } catch (Exception e) {
            fail("Exception occured while initiating the SampleopenspliceTest class: " + util.printException(e));
        }

    }

    private void checkValidEntities() {
        assertTrue("Check for valid DomainParticipant object failed", participant instanceof DomainParticipant);
        assertTrue("Check for valid Publisher object failed", publisher instanceof Publisher);
        assertTrue("Check for valid Subscriber object failed", subscriber instanceof Subscriber);
        assertTrue("Check for valid Topic object failed", topic instanceof Topic);
        assertTrue("Check for valid DataReader object failed", dataReader instanceof DataReader);
        assertTrue("Check for valid DataWriter object failed", dataWriter instanceof DataWriter);
        assertTrue("Check for valid Sample object failed", sample instanceof Sample<?>);
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
     * Test method for {@link org.opensplice.dds.sub.SampleImpl#getKeyValue()}.
     */
    @Test
    public void testGetKeyValue() {
        checkValidEntities();
        Msg result = null;
        Msg data = new Msg(1, "testSetData");
        try {
            sample.setData(data);
            result = sample.getKeyValue();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetData", e, false));
        }
        assertTrue("Check for valid Message Key value failed", result.userID == data.userID);
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.sub.SampleImpl#setInfo(DDS.SampleInfo)}.
     */
    @Test
    public void testSetInfo() {
        checkValidEntities();
        SampleInfo si = new SampleInfo();
        SampleInfo result = null;
        try {
            sample.setInfo(si);
            result = sample.getInfo();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testSetInfo", e, false));
        }
        assertTrue("Check for valid SampleInfo object failed", result != null);
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.sub.SampleImpl#setContent(java.lang.Object, DDS.SampleInfo)}
     * .
     */
    @Test
    public void testSetContent() {
        checkValidEntities();
        SampleInfo si = new SampleInfo();
        Msg data = new Msg(2, "testSetContent");
        SampleInfo sires = null;
        Msg msres = null;
        try {
            sample.setContent(data, si);
            sires = sample.getInfo();
            msres = sample.getKeyValue();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testSetContent", e, false));
        }
        assertTrue("Check for valid SampleInfo object failed", sires != null);
        assertTrue("Check for valid Message object failed", msres != null);
    }


}
