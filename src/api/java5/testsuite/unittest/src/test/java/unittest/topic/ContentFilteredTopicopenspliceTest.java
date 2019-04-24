/**
 *
 */
package unittest.topic;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.dds.core.AlreadyClosedException;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.domain.DomainParticipantFactory;
import org.omg.dds.pub.Publisher;
import org.omg.dds.topic.ContentFilteredTopic;
import org.omg.dds.topic.Topic;
import org.omg.dds.topic.TopicDescription;
import org.omg.dds.type.TypeSupport;

import Test.Msg;

import org.eclipse.cyclonedds.test.AbstractUtilities;

public class ContentFilteredTopicopenspliceTest {

    private final static int                 DOMAIN_ID         = 123;
    private static ServiceEnvironment        env;
    private static DomainParticipantFactory  dpf;
    private static AbstractUtilities         util              = AbstractUtilities.getInstance(ContentFilteredTopicopenspliceTest.class);
    private final static Properties          prop              = new Properties();
    private static DomainParticipant         participant       = null;
    private static Publisher                 publisher         = null;
    private static ContentFilteredTopic<Msg> topicFilter       = null;
    private static Topic<Msg>                topic             = null;
    private static String                    topicName         = "ContentFilteredTopicTest";
    private static String                    filteredTopicName = "ContentFilteredTopicTestFilter";
    private static String                    filterExpression  = "userID < %0";
    private static List<String>              filterParams      = new ArrayList<String>();

    @BeforeClass
    public static void init() {
        try {
            String serviceEnvironment = System.getProperty("JAVA5PSM_SERVICE_ENV");
            assertTrue("Check for valid ServiceEnvironment string", !serviceEnvironment.equals(""));
            System.setProperty(ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY, serviceEnvironment);
            env = ServiceEnvironment.createInstance(ContentFilteredTopicopenspliceTest.class.getClassLoader());
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
            topic = participant.createTopic(topicName, Msg.class);
            assertTrue("Check for valid Topic object", topic instanceof Topic);
            filterParams.add("10");
            topicFilter = participant.createContentFilteredTopic(filteredTopicName, topic, filterExpression, filterParams);
            assertTrue("Check for valid ContentFilteredTopic object", topicFilter instanceof ContentFilteredTopic);
        } catch (Exception e) {
            fail("Exception occured while initiating the ContentFilteredTopicTest class: " + util.printException(e));
        }

    }

    private void checkValidEntities() {
        assertTrue("Check for valid DomainParticipant object", participant instanceof DomainParticipant);
        assertTrue("Check for valid Publisher object", publisher instanceof Publisher);
        assertTrue("Check for valid Topic object", topic instanceof Topic);
        assertTrue("Check for valid ContentFilteredTopic object", topicFilter instanceof ContentFilteredTopic);
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
     * Test method for {@link org.omg.dds.topic.ContentFilteredTopic#getFilterExpression()}.
     */
    @Test
    public void testGetFilterExpression() {
        checkValidEntities();
        String result = null;
        try {
            result = topicFilter.getFilterExpression();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetFilterExpression", e, false));
        }
        assertTrue("Check for valid FilterExpression value", util.objectCheck("testGetFilterExpression", result.equals(filterExpression)));
    }

    /**
     * Test method for {@link org.omg.dds.topic.ContentFilteredTopic#getExpressionParameters()}.
     */
    @Test
    public void testGetExpressionParameters() {
        checkValidEntities();
        List<String> result = null;
        try {
            result = topicFilter.getExpressionParameters();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetExpressionParameters", e, false));
        }
        assertTrue("Check for valid Parameters value", util.objectCheck("testGetExpressionParameters", result.get(0).equals("10")));
    }

    /**
     * Test method for {@link org.omg.dds.topic.ContentFilteredTopic#setExpressionParameters(java.util.List)}.
     */
    @Test
    public void testSetExpressionParametersListOfString() {
        checkValidEntities();
        List<String> result = null;
        List<String> params = new ArrayList<String>();
        try {
            params.add("5");
            topicFilter.setExpressionParameters(params);
            result = topicFilter.getExpressionParameters();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testSetExpressionParametersListOfString", e, false));
        }
        assertTrue("Check for valid Parameters value", util.objectCheck("testSetExpressionParametersListOfString", result.get(0).equals("5")));
    }

    /**
     * Test method for
     * {@link org.omg.dds.topic.ContentFilteredTopic#setExpressionParameters(java.util.List)}
     * .
     */
    @Test
    public void testSetExpressionParametersListOfStringNull() {
        checkValidEntities();
        List<String> params = null;
        boolean exceptionOccured = false;
        try {
            topicFilter.setExpressionParameters(params);
        } catch (Exception e) {
            assertTrue("Expected IllegalArgumentException but got: " + util.printException(e),
                    util.exceptionCheck("testSetExpressionParametersListOfStringNull", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException occured failed", exceptionOccured);
    }

    /**
     * Test method for
     * {@link org.omg.dds.topic.ContentFilteredTopic#setExpressionParameters(java.lang.String[])}
     * .
     */
    @Test
    public void testSetExpressionParametersStringArray() {
        checkValidEntities();
        List<String> result = null;
        String[] params = new String[1];
        try {
            params[0] = "4";
            topicFilter.setExpressionParameters(params);
            result = topicFilter.getExpressionParameters();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testSetExpressionParametersStringArray", e, false));
        }
        assertTrue("Check for valid Parameters value", util.objectCheck("testSetExpressionParametersStringArray", result.get(0).equals("4")));
    }

    /**
     * Test method for
     * {@link org.omg.dds.topic.ContentFilteredTopic#setExpressionParameters(java.lang.String[])}
     * .
     */
    @Test
    public void testSetExpressionParametersStringArrayNull() {
        checkValidEntities();
        String[] params = null;
        boolean exceptionOccured = false;
        try {
            topicFilter.setExpressionParameters(params);
        } catch (Exception e) {
            assertTrue("Expected IllegalArgumentException but got: " + util.printException(e),
                    util.exceptionCheck("testSetExpressionParametersStringArrayNull", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException occured failed", exceptionOccured);
    }

    /**
     * Test method for
     * {@link org.omg.dds.topic.ContentFilteredTopic#getRelatedTopic()}.
     */
    @Test
    public void testGetRelatedTopic() {
        checkValidEntities();
        Topic<? extends Msg> result = null;
        try {
            result = topicFilter.getRelatedTopic();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetRelatedTopic", e, false));
        }
        assertTrue("Check for valid Topic object", util.objectCheck("testGetRelatedTopic", result.getName().equals(topic.getName())));
    }

    /**
     * Test method for {@link org.omg.dds.topic.TopicDescription#close()}.
     */
    @Test
    public void testClose() {
        checkValidEntities();
        ContentFilteredTopic<Msg> topicFilterClose = null;
        boolean exceptionOccured = false;
        try {
            topicFilterClose = participant.createContentFilteredTopic("testCloseFilter", topic, filterExpression, filterParams);
            topicFilterClose.close();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testClose", e, false));
        }
        try {
            topicFilterClose.close();
        } catch (AlreadyClosedException e) {
            exceptionOccured = true;
        }
        assertTrue("Check if AlreadyClosedException occured failed", exceptionOccured);
    }

    /**
     * Test method for {@link org.omg.dds.topic.TopicDescription#getTypeSupport()}.
     */
    @Test
    public void testGetTypeSupport() {
        checkValidEntities();
        TypeSupport<Msg> result = null;
        try {
            result = topicFilter.getTypeSupport();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetTypeSupport", e, false));
        }
        assertTrue("Check for valid TypeSupport object", util.objectCheck("testGetTypeSupport", result));
    }

    /**
     * Test method for {@link org.omg.dds.topic.TopicDescription#cast()}.
     */
    @Test
    public void testCast() {
        checkValidEntities();
        TopicDescription<Object> result = null;
        try {
            result = topicFilter.cast();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testCast", e, false));
        }
        assertTrue("Check for valid Topic object", util.objectCheck("testCast", result));
    }

    /**
     * Test method for {@link org.omg.dds.topic.TopicDescription#getTypeName()}.
     */
    @Test
    public void testGetTypeName() {
        checkValidEntities();
        String result = null;
        try {
            result = topicFilter.getTypeName();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetTypeName", e, false));
        }
        assertTrue("Check for valid TypeName", util.objectCheck("testGetTypeName", result));
    }

    /**
     * Test method for {@link org.omg.dds.topic.TopicDescription#getName()}.
     */
    @Test
    public void testGetName() {
        checkValidEntities();
        String result = null;
        try {
            result = topicFilter.getName();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetName", e, false));
        }
        assertTrue("Check for valid Name value", util.objectCheck("testGetName", result));
        if (result != null) {
            assertTrue("Check for valid topicName", result.equals(filteredTopicName));
        }
    }

    /**
     * Test method for {@link org.omg.dds.topic.TopicDescription#getParent()}.
     */
    @Test
    public void testGetParent() {
        checkValidEntities();
        DomainParticipant result = null;
        try {
            result = topicFilter.getParent();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetParent", e, false));
        }
        assertTrue("Check for valid Domainparticipant object", util.objectCheck("testGetParent", result.equals(participant)));
    }

    /**
     * Test method for {@link org.omg.dds.core.DDSObject#getEnvironment()}.
     */
    @Test
    public void testGetEnvironment() {
        checkValidEntities();
        ServiceEnvironment senv = null;
        try {
            senv = topicFilter.getEnvironment();
        } catch (Exception e) {
            fail("Exception occured while calling getEnvironment(): " + util.printException(e));
        }
        assertTrue("Check for valid ServiceEnvironment", senv != null);
    }

}
