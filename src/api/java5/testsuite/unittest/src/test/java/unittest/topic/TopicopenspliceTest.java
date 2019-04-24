/**
 *
 */
package unittest.topic;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.dds.core.AlreadyClosedException;
import org.omg.dds.core.Duration;
import org.omg.dds.core.PreconditionNotMetException;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.status.Status;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.domain.DomainParticipantFactory;
import org.omg.dds.pub.Publisher;
import org.omg.dds.topic.Topic;
import org.omg.dds.topic.TopicListener;
import org.omg.dds.topic.TopicQos;
import org.omg.dds.type.TypeSupport;
import org.opensplice.dds.core.OsplServiceEnvironment;
import org.opensplice.dds.core.status.AllDataDisposedStatus;
import org.opensplice.dds.domain.DomainParticipantImpl;
import org.opensplice.dds.topic.TopicImpl;
import org.opensplice.dds.type.AbstractTypeSupport;

import Test.Msg;

import org.eclipse.cyclonedds.test.AbstractUtilities;

public class TopicopenspliceTest {

    private final static int                DOMAIN_ID   = 123;
    private static ServiceEnvironment       env;
    private static DomainParticipantFactory dpf;
    private static AbstractUtilities        util        = AbstractUtilities.getInstance(TopicopenspliceTest.class);
    private final static Properties         prop        = new Properties();
    private static DomainParticipant        participant = null;
    private static Publisher                publisher   = null;
    private static TopicImpl<Msg>           topic       = null;
    private static String                   topicName   = "TopicopenspliceTest";

    @BeforeClass
    public static void init() {
        try {
            String serviceEnvironment = System.getProperty("JAVA5PSM_SERVICE_ENV");
            assertTrue("Check for valid ServiceEnvironment string", !serviceEnvironment.equals(""));
            System.setProperty(ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY, serviceEnvironment);
            env = ServiceEnvironment.createInstance(TopicopenspliceTest.class.getClassLoader());
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
            topic = (TopicImpl<Msg>) participant.createTopic(topicName, Msg.class);
        } catch (Exception e) {
            fail("Exception occured while initiating the TopicopenspliceTest class: " + util.printException(e));
        }

    }

    private void checkValidEntities() {
        assertTrue("Check for valid DomainParticipant object", participant instanceof DomainParticipant);
        assertTrue("Check for valid Publisher object", publisher instanceof Publisher);
        assertTrue("Check for valid Topic object", topic instanceof Topic);
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
     * {@link org.opensplice.dds.topic.TopicImpl#getAllDataDisposedTopicStatus()}
     * .
     */
    @Test
    public void testGetAllDataDisposedTopicStatus() {
        checkValidEntities();
        AllDataDisposedStatus result = null;
        String function = "testGetAllDataDisposedTopicStatus";
        try {
            result = topic.getAllDataDisposedTopicStatus();
            assertTrue("check for valid Status failed ", util.objectCheck(function, result));
            assertTrue("Check for valid getTotalCount value failed", util.valueCompareCheck(function, result.getTotalCount(), 0, AbstractUtilities.Equality.EQ));
            assertTrue("Check for valid getTotalCountChange value failed", util.valueCompareCheck(function, result.getTotalCountChange(), 0, AbstractUtilities.Equality.EQ));
        } catch (Exception e) {
            assertTrue("Exception occured while calling retain()" + util.printException(e), util.exceptionCheck(function, e, false));
        }
        assertTrue("check for valid Status failed ", util.objectCheck(function, result));
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.topic.TopicImpl#TopicImpl(org.opensplice.dds.core.OsplServiceEnvironment, org.opensplice.dds.domain.DomainParticipantImpl, java.lang.String, org.opensplice.dds.type.AbstractTypeSupport, org.omg.dds.topic.TopicQos, org.omg.dds.topic.TopicListener, java.util.Collection)}
     * .
     */
    @Test
    public void testTopicImplQosNull() {
        checkValidEntities();
        Topic<Msg> result = null;
        TopicQos tq = null;
        TopicListener<Msg> listener = null;
        Collection<Class<? extends Status>> statuses = null;
        TypeSupport<Msg> typeSupport = TypeSupport.newTypeSupport(Msg.class, "testCreateTopicStringTypeSupportOfTYPE", env);
        boolean exceptionOccured = false;
        String name = "testTopicImplQosNull";
        try {
            result = new TopicImpl<Msg>(((OsplServiceEnvironment) env), ((DomainParticipantImpl) participant), name, (AbstractTypeSupport<Msg>) typeSupport, tq, listener, statuses);

        } catch (Exception e) {
            assertTrue("Check for IllegalArgumentException but got exception:" + util.printException(e), e instanceof IllegalArgumentException);
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException occured failed", exceptionOccured);
        assertTrue("check for invalid Topic failed ", result == null);
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.topic.TopicImpl#TopicImpl(org.opensplice.dds.core.OsplServiceEnvironment, org.opensplice.dds.domain.DomainParticipantImpl, java.lang.String, org.opensplice.dds.type.AbstractTypeSupport, org.omg.dds.topic.TopicQos, org.omg.dds.topic.TopicListener, java.util.Collection)}
     * .
     */
    @Test
    public void testTopicImplTypeSupportNull() {
        checkValidEntities();
        Topic<Msg> result = null;
        TopicQos tq = participant.getDefaultTopicQos();
        assertTrue("Check for valid TopicQos object", tq instanceof TopicQos);
        TopicListener<Msg> listener = null;
        Collection<Class<? extends Status>> statuses = null;
        TypeSupport<Msg> typeSupport = null;
        boolean exceptionOccured = false;
        String name = "testTopicImplQosNull";
        try {
            result = new TopicImpl<Msg>(((OsplServiceEnvironment) env), ((DomainParticipantImpl) participant), name, (AbstractTypeSupport<Msg>) typeSupport, tq, listener, statuses);

        } catch (Exception e) {
            assertTrue("Check for IllegalArgumentException but got exception:" + util.printException(e), e instanceof IllegalArgumentException);
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException occured failed", exceptionOccured);
        assertTrue("check for invalid Topic failed ", result == null);
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.topic.TopicImpl#TopicImpl(org.opensplice.dds.core.OsplServiceEnvironment, org.opensplice.dds.domain.DomainParticipantImpl, java.lang.String, DDS.Topic)}
     * .
     */
    @Test
    public void testTopicImplTopicNameNull() {
        Topic<Msg> result = null;
        String name = null;
        DDS.Topic dds_topic = null;
        boolean exceptionOccured = false;
        try {
            result = new TopicImpl<Msg>(((OsplServiceEnvironment) env), ((DomainParticipantImpl) participant), name, dds_topic);

        } catch (Exception e) {
            assertTrue("Check for IllegalArgumentException but got exception:" + util.printException(e), e instanceof IllegalArgumentException);
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException occured failed", exceptionOccured);
        assertTrue("check for invalid Topic failed ", result == null);
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.topic.TopicImpl#TopicImpl(org.opensplice.dds.core.OsplServiceEnvironment, org.opensplice.dds.domain.DomainParticipantImpl, java.lang.String, DDS.Topic)}
     * .
     */
    @Test
    public void testTopicImplTopicDDSTopicNull() {
        Topic<Msg> result = null;
        String name = "Foo";
        DDS.Topic dds_topic = null;
        boolean exceptionOccured = false;
        try {
            result = new TopicImpl<Msg>(((OsplServiceEnvironment) env), ((DomainParticipantImpl) participant), name, dds_topic);

        } catch (Exception e) {
            assertTrue("Check for IllegalArgumentException but got exception:" + util.printException(e), e instanceof IllegalArgumentException);
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException occured failed", exceptionOccured);
        assertTrue("check for invalid Topic failed ", result == null);
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.topic.TopicImpl#TopicImpl(org.opensplice.dds.core.OsplServiceEnvironment, org.opensplice.dds.domain.DomainParticipantImpl, java.lang.String, DDS.Topic)}
     * .
     */
    @Test
    public void testTopicImplBuiltinTopics() {
        Topic<Msg> result = null;
        String[] names = { "DCPSParticipant", "DCPSTopic", "CMSubscriber", "CMPublisher", "CMParticipant", "DCPSSubscription", "CMDataReader", "DCPSPublication", "CMDataWriter", "DCPSType" };
        Duration timeout = Duration.newDuration(2, TimeUnit.SECONDS, env);
        boolean exceptionOccured = false;
        try {
            for (String name : names) {
                result = participant.findTopic(name, timeout);
                assertTrue("check for valid Topic failed ", result != null);
                try {
                    result.getTypeSupport();
                } catch (Exception e) {
                    assertTrue("Check for PreconditionNotMetException but got exception:" + util.printException(e), e instanceof PreconditionNotMetException);
                }
            }
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testTopicImplBuiltinTopics", e, false));
            exceptionOccured = true;
        }
        assertFalse("Check if no exception has occured failed", exceptionOccured);
    }
}
