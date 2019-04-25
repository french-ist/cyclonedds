
package unittest.sub;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
import org.omg.dds.sub.Sample.Iterator;
import org.omg.dds.sub.Subscriber;
import org.omg.dds.topic.Topic;
import org.opensplice.dds.sub.DataReaderProtobuf;

import proto.Proto.ProtoMsg;

import org.eclipse.cyclonedds.test.AbstractUtilities;


public class IteratorprotobufTest {

    private final static int                                        DOMAIN_ID   = 123;
    private static ServiceEnvironment                               env;
    private static DomainParticipantFactory                         dpf;
    private static AbstractUtilities                                util        = AbstractUtilities.getInstance(IteratorprotobufTest.class);
    private final static Properties                                 prop        = new Properties();
    private static DomainParticipant                                participant = null;
    private static Publisher                                        publisher   = null;
    private static Subscriber                                       subscriber  = null;
    private static Topic<ProtoMsg>                                  topic       = null;
    private static DataReaderProtobuf<ProtoMsg, proto.dds.ProtoMsg> dataReader  = null;
    private static DataWriter<ProtoMsg>                             dataWriter  = null;
    private static String                                           topicName   = "IteratorprotobufTest";

    @SuppressWarnings("unchecked")
    @BeforeClass
    public static void init() {
        try {
            String serviceEnvironment = System.getProperty("JAVA5PSM_SERVICE_ENV");
            assertTrue("Check for valid ServiceEnvironment string", !serviceEnvironment.equals(""));
            System.setProperty(ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY, serviceEnvironment);
            env = ServiceEnvironment.createInstance(IteratorprotobufTest.class.getClassLoader());
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
            topic = participant.createTopic(topicName, ProtoMsg.class);
            assertTrue("Check for valid Topic object failed", topic instanceof Topic);
            dataReader = (DataReaderProtobuf<ProtoMsg, proto.dds.ProtoMsg>) subscriber.createDataReader(topic);
            assertTrue("Check for valid DataReader object failed", dataReader instanceof DataReader);
            dataWriter = publisher.createDataWriter(topic);
            assertTrue("Check for valid DataWriter object failed", dataWriter instanceof DataWriter);
        } catch (Exception e) {
            fail("Exception occured while initiating the IteratorprotobufTest class: " + util.printException(e));
        }

    }

    private void checkValidEntities() {
        assertTrue("Check for valid DomainParticipant object failed", participant instanceof DomainParticipant);
        assertTrue("Check for valid Publisher object failed", publisher instanceof Publisher);
        assertTrue("Check for valid Subscriber object failed", subscriber instanceof Subscriber);
        assertTrue("Check for valid Topic object failed", topic instanceof Topic);
        assertTrue("Check for valid DataReader object failed", dataReader instanceof DataReader);
        assertTrue("Check for valid DataWriter object failed", dataWriter instanceof DataWriter);
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

    private Iterator<ProtoMsg> getIterator() {
        Iterator<ProtoMsg> iter = null;
        try {
            for (int i = 0; i < 10; i++) {
                ProtoMsg.Builder messageBuilder = ProtoMsg.newBuilder();
                messageBuilder.setId(i);
                messageBuilder.setName("IteratoropenspliceTest");
                ProtoMsg message = messageBuilder.build();
                dataWriter.write(message);
            }
            Thread.sleep(util.getWriteSleepTime());
            iter = dataReader.take();
        } catch (Exception e) {
            fail("Exception occured while getting an Iterator:" + util.printException(e));
        }
        return iter;
    }

    /**
     * Test method for {@link org.opensplice.dds.sub.IteratorImpl#hasNext()}.
     */
    @Test
    public void testHasNext() {
        checkValidEntities();
        Iterator<ProtoMsg> copy = getIterator();
        boolean result = false;
        boolean exceptionOccured = false;
        try {
            result = copy.hasNext();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testHasNext", e, false));
        }
        assertTrue("Check for next iter object failed", result);
        try {
            copy.close();
            result = copy.hasNext();
        } catch (Exception e) {
            assertTrue("Expected AlreadyClosedException exception but got: " + util.printException(e), util.exceptionCheck("testHasNext", e, e instanceof AlreadyClosedException));
            exceptionOccured = true;
        }
        assertTrue("Check if AlreadyClosedException occured failed", exceptionOccured);
    }

    /**
     * Test method for {@link org.opensplice.dds.sub.IteratorImpl#next()}.
     */
    @Test
    public void testNext() {
        checkValidEntities();
        Iterator<ProtoMsg> copy = getIterator();
        Sample<ProtoMsg> result = null;
        boolean exceptionOccured = false;
        try {
            result = copy.next();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testHasNext", e, false));
        }
        assertTrue("Check for next message in iter object failed", result != null);
        try {
            copy.close();
            result = copy.next();
        } catch (Exception e) {
            assertTrue("Expected AlreadyClosedException exception but got: " + util.printException(e), util.exceptionCheck("testHasNext", e, e instanceof AlreadyClosedException));
            exceptionOccured = true;
        }
        assertTrue("Check if AlreadyClosedException occured failed", exceptionOccured);
    }

    /**
     * Test method for {@link org.opensplice.dds.sub.IteratorImpl#hasPrevious()}.
     */
    @Test
    public void testHasPrevious() {
        checkValidEntities();
        Iterator<ProtoMsg> copy = getIterator();
        boolean result = false;
        boolean exceptionOccured = false;
        try {
            copy.next();
            result = copy.hasPrevious();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testHasPrevious", e, false));
        }
        assertFalse("Check for previous message in iter object failed", result);
        try {
            copy.next();
            copy.next();
            result = copy.hasPrevious();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testHasPrevious", e, false));
        }
        assertTrue("Check for previous message in iter object failed", result);
        try {
            copy.close();
            result = copy.hasPrevious();
        } catch (Exception e) {
            assertTrue("Expected AlreadyClosedException exception but got: " + util.printException(e), util.exceptionCheck("testHasPrevious", e, e instanceof AlreadyClosedException));
            exceptionOccured = true;
        }
        assertTrue("Check if AlreadyClosedException occured failed", exceptionOccured);
    }

    /**
     * Test method for {@link org.opensplice.dds.sub.IteratorImpl#previous()}.
     */
    @Test
    public void testPrevious() {
        checkValidEntities();
        Iterator<ProtoMsg> copy = getIterator();
        Sample<ProtoMsg> result = null;
        boolean exceptionOccured = false;
        try {
            copy.next();
            result = copy.previous();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testPrevious", e, false));
        }
        assertTrue("Check for previous message object failed", result != null);
        try {
            copy.close();
            result = copy.previous();
        } catch (Exception e) {
            assertTrue("Expected AlreadyClosedException exception but got: " + util.printException(e), util.exceptionCheck("testPrevious", e, e instanceof AlreadyClosedException));
            exceptionOccured = true;
        }
        assertTrue("Check if AlreadyClosedException occured failed", exceptionOccured);
    }

    /**
     * Test method for {@link org.opensplice.dds.sub.IteratorImpl#nextIndex()}.
     */
    @Test
    public void testNextIndex() {
        checkValidEntities();
        Iterator<ProtoMsg> copy = getIterator();
        int result = 0;
        boolean exceptionOccured = false;
        try {
            result = copy.nextIndex();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testNextIndex", e, false));
        }
        assertTrue("Check for valid index value failed", result == 0);
        try {
            copy.close();
            result = copy.nextIndex();
        } catch (Exception e) {
            assertTrue("Expected AlreadyClosedException exception but got: " + util.printException(e), util.exceptionCheck("testNextIndex", e, e instanceof AlreadyClosedException));
            exceptionOccured = true;
        }
        assertTrue("Check if AlreadyClosedException occured failed", exceptionOccured);
    }

    /**
     * Test method for {@link org.opensplice.dds.sub.IteratorImpl#previousIndex()}.
     */
    @Test
    public void testPreviousIndex() {
        checkValidEntities();
        Iterator<ProtoMsg> copy = getIterator();
        int result = -1;
        boolean exceptionOccured = false;
        try {
            copy.next();
            result = copy.previousIndex();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testPreviousIndex", e, false));
        }
        assertTrue("Check for valid index value failed", result == 0);
        try {
            copy.close();
            result = copy.previousIndex();
        } catch (Exception e) {
            assertTrue("Expected AlreadyClosedException exception but got: " + util.printException(e), util.exceptionCheck("testPreviousIndex", e, e instanceof AlreadyClosedException));
            exceptionOccured = true;
        }
        assertTrue("Check if AlreadyClosedException occured failed", exceptionOccured);
    }

    /**
     * Test method for {@link org.opensplice.dds.sub.IteratorImpl#close()}.
     */
    @Test
    public void testClose() {
        checkValidEntities();
        Iterator<ProtoMsg> copy = getIterator();
        boolean exceptionOccured = false;
        try {
            copy.close();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testClose", e, false));
        }

        try {
            copy.close();
        } catch (Exception e) {
            assertTrue("Expected AlreadyClosedException exception but got: " + util.printException(e), util.exceptionCheck("testClose", e, e instanceof AlreadyClosedException));
            exceptionOccured = true;
        }
        assertTrue("Check if AlreadyClosedException occured failed", exceptionOccured);
    }

    /**
     * Test method for {@link org.opensplice.dds.sub.IteratorImpl#remove()}.
     */
    @Test
    public void testRemove() {
        checkValidEntities();
        Iterator<ProtoMsg> copy = getIterator();
        boolean exceptionOccured = false;
        try {
            copy.remove();
        } catch (Exception e) {
            assertTrue("Expected UnsupportedOperationException exception but got: " + util.printException(e), util.exceptionCheck("testRemove", e, e instanceof UnsupportedOperationException));
            exceptionOccured = true;
        }
        assertTrue("Check if UnsupportedOperationException occured failed", exceptionOccured);
    }

    /**
     * Test method for {@link org.opensplice.dds.sub.IteratorImpl#set(org.omg.dds.sub.Sample)}.
     */
    @Test
    public void testSet() {
        checkValidEntities();
        Iterator<ProtoMsg> copy = getIterator();
        boolean exceptionOccured = false;
        Sample<ProtoMsg> o = null;
        try {
            copy.set(o);
        } catch (Exception e) {
            assertTrue("Expected UnsupportedOperationException exception but got: " + util.printException(e), util.exceptionCheck("testSet", e, e instanceof UnsupportedOperationException));
            exceptionOccured = true;
        }
        assertTrue("Check if UnsupportedOperationException occured failed", exceptionOccured);
    }

    /**
     * Test method for {@link org.opensplice.dds.sub.IteratorImpl#add(org.omg.dds.sub.Sample)}.
     */
    @Test
    public void testAdd() {
        checkValidEntities();
        Iterator<ProtoMsg> copy = getIterator();
        boolean exceptionOccured = false;
        Sample<ProtoMsg> o = null;
        try {
            copy.add(o);
        } catch (Exception e) {
            assertTrue("Expected UnsupportedOperationException exception but got: " + util.printException(e), util.exceptionCheck("testAdd", e, e instanceof UnsupportedOperationException));
            exceptionOccured = true;
        }
        assertTrue("Check if UnsupportedOperationException occured failed", exceptionOccured);
    }

}
