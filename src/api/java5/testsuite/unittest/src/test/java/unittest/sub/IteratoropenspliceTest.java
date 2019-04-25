/**
 *
 */
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
import org.opensplice.dds.sub.DataReaderImpl;

import Test.Msg;

import org.eclipse.cyclonedds.test.AbstractUtilities;

/**
 * @author Thijs
 *
 */
public class IteratoropenspliceTest {

    private final static int                DOMAIN_ID   = 123;
    private static ServiceEnvironment       env;
    private static DomainParticipantFactory dpf;
    private static AbstractUtilities        util        = AbstractUtilities.getInstance(IteratoropenspliceTest.class);
    private final static Properties         prop        = new Properties();
    private static DomainParticipant        participant = null;
    private static Publisher                publisher   = null;
    private static Subscriber               subscriber  = null;
    private static Topic<Msg>               topic       = null;
    private static DataReaderImpl<Msg>                  dataReader  = null;
    private static DataWriter<Msg>          dataWriter  = null;
    private static String                   topicName   = "IteratoropenspliceTest";

    @BeforeClass
    public static void init() {
        try {
            String serviceEnvironment = System.getProperty("JAVA5PSM_SERVICE_ENV");
            assertTrue("Check for valid ServiceEnvironment string", !serviceEnvironment.equals(""));
            System.setProperty(ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY, serviceEnvironment);
            env = ServiceEnvironment.createInstance(IteratoropenspliceTest.class.getClassLoader());
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
            dataReader = (DataReaderImpl<Msg>) subscriber.createDataReader(topic);
            assertTrue("Check for valid DataReader object failed", dataReader instanceof DataReader);
            dataWriter = publisher.createDataWriter(topic);
            assertTrue("Check for valid DataWriter object failed", dataWriter instanceof DataWriter);
        } catch (Exception e) {
            fail("Exception occured while initiating the IteratoropenspliceTest class: " + util.printException(e));
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

    private Iterator<Msg> getIterator() {
        Iterator<Msg> iter = null;
        try {
            for (int i = 0; i < 10; i++) {
                Msg message = new Msg(i, "IteratoropenspliceTest");
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
    //@tTest
    public void testHasNext() {
        checkValidEntities();
        Iterator<Msg> copy = getIterator();
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
    //@tTest
    public void testNext() {
        checkValidEntities();
        Iterator<Msg> copy = getIterator();
        Sample<Msg> result = null;
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
    //@tTest
    public void testHasPrevious() {
        checkValidEntities();
        Iterator<Msg> copy = getIterator();
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
    //@tTest
    public void testPrevious() {
        checkValidEntities();
        Iterator<Msg> copy = getIterator();
        Sample<Msg> result = null;
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
    //@tTest
    public void testNextIndex() {
        checkValidEntities();
        Iterator<Msg> copy = getIterator();
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
    //@tTest
    public void testPreviousIndex() {
        checkValidEntities();
        Iterator<Msg> copy = getIterator();
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
    //@tTest
    public void testClose() {
        checkValidEntities();
        Iterator<Msg> copy = getIterator();
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
    //@tTest
    public void testRemove() {
        checkValidEntities();
        Iterator<Msg> copy = getIterator();
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
    //@tTest
    public void testSet() {
        checkValidEntities();
        Iterator<Msg> copy = getIterator();
        boolean exceptionOccured = false;
        Sample<Msg> o = null;
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
    //@tTest
    public void testAdd() {
        checkValidEntities();
        Iterator<Msg> copy = getIterator();
        boolean exceptionOccured = false;
        Sample<Msg> o = null;
        try {
            copy.add(o);
        } catch (Exception e) {
            assertTrue("Expected UnsupportedOperationException exception but got: " + util.printException(e), util.exceptionCheck("testAdd", e, e instanceof UnsupportedOperationException));
            exceptionOccured = true;
        }
        assertTrue("Check if UnsupportedOperationException occured failed", exceptionOccured);
    }

}
