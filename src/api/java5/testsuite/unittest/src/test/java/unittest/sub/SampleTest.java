
package unittest.sub;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Iterator;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.dds.core.AlreadyClosedException;
import org.omg.dds.core.InstanceHandle;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.Time;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.domain.DomainParticipantFactory;
import org.omg.dds.pub.DataWriter;
import org.omg.dds.pub.Publisher;
import org.omg.dds.sub.DataReader;
import org.omg.dds.sub.InstanceState;
import org.omg.dds.sub.Sample;
import org.omg.dds.sub.SampleState;
import org.omg.dds.sub.Subscriber;
import org.omg.dds.sub.ViewState;
import org.omg.dds.topic.Topic;

import Test.Msg;

import org.eclipse.cyclonedds.test.AbstractUtilities;

public class SampleTest {

    private final static int                DOMAIN_ID   = 123;
    private static ServiceEnvironment       env;
    private static DomainParticipantFactory dpf;
    private static AbstractUtilities        util        = AbstractUtilities.getInstance(SampleTest.class);
    private final static Properties         prop        = new Properties();
    private static DomainParticipant        participant = null;
    private static Publisher                publisher   = null;
    private static Subscriber               subscriber  = null;
    private static Topic<Msg>               topic       = null;
    private static DataReader<Msg>          dataReader  = null;
    private static DataWriter<Msg>          dataWriter  = null;
    private static String                   topicName   = "SampleTest";
    private static Msg                      message     = new Msg(0, "SampleTest");
    private static Sample<Msg>              sample      = null;

    @BeforeClass
    public static void init() {
        try {
            String serviceEnvironment = System.getProperty("JAVA5PSM_SERVICE_ENV");
            assertTrue("Check for valid ServiceEnvironment string", !serviceEnvironment.equals(""));
            System.setProperty(ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY, serviceEnvironment);
            env = ServiceEnvironment.createInstance(SampleTest.class.getClassLoader());
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
                sample = result.next();
                if (sample == null) {
                    fail("Failed to get a valid Sample");
                }
            } else {
                fail("Take failed to get a valid Sample");
            }
        } catch (Exception e) {
            fail("Exception occured while initiating the SampleTest class: " + util.printException(e));
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
     * Test method for {@link org.omg.dds.sub.Sample#getData()}.
     */
    @Test
    public void testGetData() {
        checkValidEntities();
        Msg result = null;
        try {
            result = sample.getData();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetData", e, false));
        }
        assertTrue("Check for valid Message object failed", result instanceof Msg);
    }

    /**
     * Test method for {@link org.omg.dds.sub.Sample#getSampleState()}.
     */
    @Test
    public void testGetSampleState() {
        checkValidEntities();
        SampleState result = null;
        try {
            result = sample.getSampleState();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetSampleState", e, false));
        }
        assertTrue("Check for valid SampleState object failed", result instanceof SampleState);
        SampleState[] values = null;
        try {
            values = SampleState.values();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetSampleState", e, false));
        }
        assertTrue("Check for valid SampleState[] object failed", values != null);
        SampleState value = null;
        try {
            value = SampleState.valueOf("READ");
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetSampleState", e, false));
        }
        assertTrue("Check for valid SampleState object failed", value != null);

    }

    /**
     * Test method for {@link org.omg.dds.sub.Sample#getViewState()}.
     */
    @Test
    public void testGetViewState() {
        checkValidEntities();
        ViewState result = null;
        try {
            result = sample.getViewState();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetViewState", e, false));
        }
        assertTrue("Check for valid ViewState object failed", result instanceof ViewState);
        ViewState[] values = null;
        try {
            values = ViewState.values();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetViewState", e, false));
        }
        assertTrue("Check for valid ViewState[] object failed", values != null);
        ViewState value = null;
        try {
            value = ViewState.valueOf("NEW");
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetViewState", e, false));
        }
        assertTrue("Check for valid ViewState object failed", value != null);
    }

    /**
     * Test method for {@link org.omg.dds.sub.Sample#getInstanceState()}.
     */
    @Test
    public void testGetInstanceState() {
        checkValidEntities();
        InstanceState result = null;
        try {
            result = sample.getInstanceState();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetInstanceState", e, false));
        }
        assertTrue("Check for valid InstanceState object failed", result instanceof InstanceState);
        InstanceState[] values = null;
        try {
            values = InstanceState.values();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetInstanceState", e, false));
        }
        assertTrue("Check for valid InstanceState[] object failed", values != null);
        InstanceState value = null;
        try {
            value = InstanceState.valueOf("ALIVE");
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetInstanceState", e, false));
        }
        assertTrue("Check for valid InstanceState object failed", value != null);
    }

    /**
     * Test method for {@link org.omg.dds.sub.Sample#getSourceTimestamp()}.
     */
    @Test
    public void testGetSourceTimestamp() {
        checkValidEntities();
        Time result = null;
        try {
            result = sample.getSourceTimestamp();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetSourceTimestamp", e, false));
        }
        assertTrue("Check for valid Time object failed", result instanceof Time);
    }

    /**
     * Test method for {@link org.omg.dds.sub.Sample#getInstanceHandle()}.
     */
    @Test
    public void testGetInstanceHandle() {
        checkValidEntities();
        InstanceHandle result = null;
        try {
            result = sample.getInstanceHandle();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetInstanceHandle", e, false));
        }
        assertTrue("Check for valid InstanceHandle object failed", result instanceof InstanceHandle);
    }

    /**
     * Test method for {@link org.omg.dds.sub.Sample#getPublicationHandle()}.
     */
    @Test
    public void testGetPublicationHandle() {
        checkValidEntities();
        InstanceHandle result = null;
        try {
            result = sample.getPublicationHandle();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetPublicationHandle", e, false));
        }
        assertTrue("Check for valid InstanceHandle object failed", result instanceof InstanceHandle);
    }

    /**
     * Test method for {@link org.omg.dds.sub.Sample#getDisposedGenerationCount()}.
     */
    @Test
    public void testGetDisposedGenerationCount() {
        checkValidEntities();
        int result = -1;
        try {
            result = sample.getDisposedGenerationCount();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetDisposedGenerationCount", e, false));
        }
        assertTrue("Check for valid DisposedGenerationCount failed", util.valueCompareCheck("testGetDisposedGenerationCount", result, 0, AbstractUtilities.Equality.GE));
    }

    /**
     * Test method for {@link org.omg.dds.sub.Sample#getNoWritersGenerationCount()}.
     */
    @Test
    public void testGetNoWritersGenerationCount() {
        checkValidEntities();
        int result = -1;
        try {
            result = sample.getNoWritersGenerationCount();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetNoWritersGenerationCount", e, false));
        }
        assertTrue("Check for valid NoWritersGenerationCount failed", util.valueCompareCheck("testGetNoWritersGenerationCount", result, 0, AbstractUtilities.Equality.GE));
    }

    /**
     * Test method for {@link org.omg.dds.sub.Sample#getSampleRank()}.
     */
    @Test
    public void testGetSampleRank() {
        checkValidEntities();
        int result = -1;
        try {
            result = sample.getSampleRank();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetSampleRank", e, false));
        }
        assertTrue("Check for valid SampleRank failed", util.valueCompareCheck("testGetSampleRank", result, 0, AbstractUtilities.Equality.GE));
    }

    /**
     * Test method for {@link org.omg.dds.sub.Sample#getGenerationRank()}.
     */
    @Test
    public void testGetGenerationRank() {
        checkValidEntities();
        int result = -1;
        try {
            result = sample.getGenerationRank();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetGenerationRank", e, false));
        }
        assertTrue("Check for valid GenerationRank failed", util.valueCompareCheck("testGetGenerationRank", result, 0, AbstractUtilities.Equality.GE));
    }

    /**
     * Test method for {@link org.omg.dds.sub.Sample#getAbsoluteGenerationRank()}.
     */
    @Test
    public void testGetAbsoluteGenerationRank() {
        checkValidEntities();
        int result = -1;
        try {
            result = sample.getAbsoluteGenerationRank();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetAbsoluteGenerationRank", e, false));
        }
        assertTrue("Check for valid AbsoluteGenerationRank failed", util.valueCompareCheck("testGetAbsoluteGenerationRank", result, 0, AbstractUtilities.Equality.GE));
    }

    /**
     * Test method for {@link org.omg.dds.sub.Sample#clone()}.
     */
    @Test
    public void testClone() {
        checkValidEntities();
        Sample<Msg> result = null;
        try {
            result = sample.clone();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testClone", e, false));
        }
        assertTrue("Check for valid Sample object failed", sample != result);
        assertTrue("Check for valid Sample object failed", sample.getClass() == result.getClass());
    }

    /**
     * Test method for {@link org.omg.dds.core.DDSObject#getEnvironment()}.
     */
    @Test
    public void testGetEnvironment() {
        checkValidEntities();
        ServiceEnvironment senv = null;
        try {
            senv = sample.getEnvironment();
        } catch (Exception e) {
            fail("Exception occured while calling getEnvironment(): " + util.printException(e));
        }
        assertTrue("Check for valid ServiceEnvironment", senv != null);
    }

}
