/**
 *
 */
package unittest.sub;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.domain.DomainParticipantFactory;
import org.omg.dds.pub.Publisher;
import org.omg.dds.sub.DataReader;
import org.omg.dds.sub.QueryCondition;
import org.omg.dds.sub.Subscriber;
import org.omg.dds.topic.Topic;

import Test.Msg;

import org.eclipse.cyclonedds.test.AbstractUtilities;

/**
 * @author Thijs
 *
 */
public class QueryConditionTest {

    private final static int                DOMAIN_ID       = 123;
    private static ServiceEnvironment       env;
    private static DomainParticipantFactory dpf;
    private static AbstractUtilities        util            = AbstractUtilities.getInstance(ReadConditionTest.class);
    private final static Properties         prop            = new Properties();
    private static DomainParticipant        participant     = null;
    private static Publisher                publisher       = null;
    private static Subscriber               subscriber      = null;
    private static Topic<Msg>               topic           = null;
    private static DataReader<Msg>          dataReader      = null;
    private static String                   topicName       = "ReadConditionTest";
    private static QueryCondition<Msg>      condition       = null;
    private static String                   queryExpression = "userID < %0";
    private static ArrayList<String>        queryParameters = new ArrayList<String>();

    @BeforeClass
    public static void init() {
        try {
            String serviceEnvironment = System.getProperty("JAVA5PSM_SERVICE_ENV");
            assertTrue("Check for valid ServiceEnvironment string", !serviceEnvironment.equals(""));
            System.setProperty(ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY, serviceEnvironment);
            env = ServiceEnvironment.createInstance(ReadConditionTest.class.getClassLoader());
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
            queryParameters.add("2");
            condition = dataReader.createQueryCondition(queryExpression, queryParameters);
        } catch (Exception e) {
            fail("Exception occured while initiating the QueryConditionTest class: " + util.printException(e));
        }

    }

    private void checkValidEntities() {
        assertTrue("Check for valid DomainParticipant object failed", participant instanceof DomainParticipant);
        assertTrue("Check for valid Publisher object failed", publisher instanceof Publisher);
        assertTrue("Check for valid Subscriber object failed", subscriber instanceof Subscriber);
        assertTrue("Check for valid Topic object failed", topic instanceof Topic);
        assertTrue("Check for valid DataReader object failed", dataReader instanceof DataReader);
        assertTrue("Check for valid QueryCondition object failed", condition instanceof QueryCondition);
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
     * Test method for {@link org.omg.dds.sub.QueryCondition#getQueryExpression()}.
     */
    //@tTest
    public void testGetQueryExpression() {
        checkValidEntities();
        String result = null;
        try {
            result = condition.getQueryExpression();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetQueryExpression", e, false));
        }
        assertTrue("Check for valid QueryExpression object failed", result.equals(queryExpression));
    }

    /**
     * Test method for {@link org.omg.dds.sub.QueryCondition#getQueryParameters()}.
     */
    //@tTest
    public void testGetQueryParameters() {
        checkValidEntities();
        List<String> result = null;
        try {
            result = condition.getQueryParameters();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetQueryParameters", e, false));
        }
        assertTrue("Check for valid QueryParameters object failed", result.contains("2"));
    }

    /**
     * Test method for {@link org.omg.dds.sub.QueryCondition#setQueryParameters(java.util.List)}.
     */
    //@tTest
    public void testSetQueryParametersListOfString() {
        checkValidEntities();
        List<String> result = null;
        ArrayList<String> queryParam = new ArrayList<String>();
        queryParam.add("1");
        try {
            QueryCondition<Msg> Qcondition = dataReader.createQueryCondition(queryExpression, queryParam);
            Qcondition.setQueryParameters(queryParam);
            result = Qcondition.getQueryParameters();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testSetQueryParametersListOfString", e, false));
        }
        assertTrue("Check for valid QueryParameters object failed", result.contains("1"));
    }

    /**
     * Test method for
     * {@link org.omg.dds.sub.QueryCondition#setQueryParameters(java.util.List)}
     * .
     */
    //@tTest
    public void testSetQueryParametersListOfStringNull() {
        checkValidEntities();
        ArrayList<String> queryParam = null;
        boolean exceptionOccured = false;
        try {
            condition.setQueryParameters(queryParam);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testSetQueryParametersListOfStringNull", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException occured failed", exceptionOccured);
    }

    /**
     * Test method for
     * {@link org.omg.dds.sub.QueryCondition#setQueryParameters(java.util.List)}
     * .
     */
    //@tTest
    public void testSetQueryParametersListOfStringNullContent() {
        checkValidEntities();
        ArrayList<String> queryParam = new ArrayList<String>();
        queryParam.add(null);
        boolean exceptionOccured = false;
        try {
            condition.setQueryParameters(queryParam);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e),
                    util.exceptionCheck("testSetQueryParametersListOfStringNullContent", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException occured failed", exceptionOccured);
    }

    /**
     * Test method for
     * {@link org.omg.dds.sub.QueryCondition#setQueryParameters(java.lang.String[])}
     * .
     */
    //@tTest
    public void testSetQueryParametersStringArray() {
        checkValidEntities();
        List<String> result = null;
        String[] queryParam = { "3" };
        try {
            QueryCondition<Msg> Qcondition = dataReader.createQueryCondition(queryExpression, queryParam);
            Qcondition.setQueryParameters(queryParam);
            result = Qcondition.getQueryParameters();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testSetQueryParametersStringArray", e, false));
        }
        assertTrue("Check for valid QueryParameters object failed", result.contains("3"));
    }

    /**
     * Test method for
     * {@link org.omg.dds.sub.QueryCondition#setQueryParameters(java.lang.String[])}
     * .
     */
    //@tTest
    public void testSetQueryParametersStringArrayNull() {
        checkValidEntities();
        String[] queryParam = null;
        boolean exceptionOccured = false;
        try {
            condition.setQueryParameters(queryParam);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testSetQueryParametersStringArrayNull", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException occured failed", exceptionOccured);
    }

    /**
     * Test method for
     * {@link org.omg.dds.sub.QueryCondition#setQueryParameters(java.lang.String[])}
     * .
     */
    //@tTest
    public void testSetQueryParametersStringArrayNullContent() {
        checkValidEntities();
        String[] queryParam = { null };
        boolean exceptionOccured = false;
        try {
            condition.setQueryParameters(queryParam);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testSetQueryParametersStringArrayNull", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException occured failed", exceptionOccured);
    }

    /**
     * Test method for {@link org.omg.dds.core.Condition#getTriggerValue()}.
     */
    //@tTest
    public void testGetTriggerValue() {
        checkValidEntities();
        boolean result = true;
        try {
            result = condition.getTriggerValue();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetTriggerValue", e, false));
        }
        assertFalse("Check for invalid triggervalue object failed", result);
    }

    /**
     * Test method for {@link org.omg.dds.core.DDSObject#getEnvironment()}.
     */
    //@tTest
    public void testGetEnvironment() {
        checkValidEntities();
        ServiceEnvironment senv = null;
        try {
            senv = condition.getEnvironment();
        } catch (Exception e) {
            fail("Exception occured while calling getEnvironment(): " + util.printException(e));
        }
        assertTrue("Check for valid ServiceEnvironment", senv != null);
    }

}
