/**
 *
 */
package unittest.domain;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.dds.core.AlreadyClosedException;
import org.omg.dds.core.ServiceEnvironment;
import org.opensplice.dds.domain.DomainParticipant;
import org.omg.dds.domain.DomainParticipantFactory;
import org.eclipse.cyclonedds.test.AbstractUtilities;


public class DomainParticipantopenspliceTest {

    private final static int                DOMAIN_ID   = 123;
    private static ServiceEnvironment       env;
    private static DomainParticipantFactory dpf;
    private static AbstractUtilities        util        = AbstractUtilities.getInstance(DomainParticipantopenspliceTest.class);
    private final static Properties         prop        = new Properties();
    private static DomainParticipant        participant = null;

    @BeforeClass
    public static void init() {
        try {
            String serviceEnvironment = System.getProperty("JAVA5PSM_SERVICE_ENV");
            assertTrue("Check for valid ServiceEnvironment string", !serviceEnvironment.equals(""));
            System.setProperty(ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY, serviceEnvironment);
            env = ServiceEnvironment.createInstance(DomainParticipantopenspliceTest.class.getClassLoader());
            assertTrue("Check for valid ServiceEnvironment object", env instanceof ServiceEnvironment);
            dpf = DomainParticipantFactory.getInstance(env);
            assertTrue("Check for valid DomainParticipantFactory object", dpf instanceof DomainParticipantFactory);
            prop.setProperty("domainid", new Integer(DOMAIN_ID).toString());
            assertTrue("Check is deamon is correctly started", util.beforeClass(prop));
            participant = (DomainParticipant) dpf.createParticipant(DOMAIN_ID);
            assertTrue("Check for valid DomainParticipant object", participant instanceof DomainParticipant);
            assertTrue("Check for valid DomainParticipant with id " + DOMAIN_ID, participant.getDomainId() == DOMAIN_ID);
        } catch (Exception e) {
            fail("Exception occured while initiating the DomainParticipantQosopenspliceTest class: " + util.printException(e));
        }

    }

    private void checkValidEntities() {
        assertTrue("Check for valid DomainParticipant object", participant instanceof DomainParticipant);
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
     * Test method for {@link org.opensplice.dds.domain.DomainParticipantImpl#deleteHistoricalData()}.
     */
    @Test
    public void testDeleteHistoricalData() {
        checkValidEntities();
        try {
            String partitionExpression = "";
            String topicExpression = "";
            participant.deleteHistoricalData(partitionExpression,topicExpression);
        } catch (Exception ex) {
            fail("deleteHistoricalData function call failed");
        }
    }

    /**
     * Test method for {@link org.opensplice.dds.domain.DomainParticipantImpl#deleteHistoricalData()}.
     */
    @Test
    public void testDeleteHistoricalDataPartitionExpressionNull() {
        checkValidEntities();

        boolean exceptionOccured = false;
        try {
            String partitionExpression = null;
            String topicExpression = "";
            participant.deleteHistoricalData(partitionExpression,topicExpression);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testDeleteHistoricalDataPartitionExpressionNull", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException has occured failed", exceptionOccured);
    }

    /**
     * Test method for {@link org.opensplice.dds.domain.DomainParticipantImpl#deleteHistoricalData()}.
     */
    @Test
    public void testDeleteHistoricalDataTopicExpressionNull() {
        checkValidEntities();

        boolean exceptionOccured = false;
        try {
            String partitionExpression = "";
            String topicExpression = null;
            participant.deleteHistoricalData(partitionExpression,topicExpression);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testDeleteHistoricalDataTopicExpressionNull", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException has occured failed", exceptionOccured);
    }

    /**
     * Test method for {@link org.opensplice.dds.domain.DomainParticipantImpl#createPersistentSnapshot()}.
     */
    @Test
    public void testCreatePersistentSnapshot() {
        checkValidEntities();
        try {
            String partitionExpression = "";
            String topicExpression = "";
            String uri = "";
            participant.createPersistentSnapshot(partitionExpression,topicExpression,uri);
        } catch (Exception ex) {
            fail("deleteHistoricalData function call failed");
        }
    }

    /**
     * Test method for {@link org.opensplice.dds.domain.DomainParticipantImpl#createPersistentSnapshot()}.
     */
    @Test
    public void testCreatePersistentSnapshotPartitionExpressionNull() {
        checkValidEntities();

        boolean exceptionOccured = false;
        try {
            String partitionExpression = null;
            String topicExpression = "";
            String uri = "";
            participant.createPersistentSnapshot(partitionExpression,topicExpression,uri);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testCreatePersistentSnapshotPartitionExpressionNull", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException has occured failed", exceptionOccured);
    }

    /**
     * Test method for {@link org.opensplice.dds.domain.DomainParticipantImpl#createPersistentSnapshot()}.
     */
    @Test
    public void testCreatePersistentSnapshotTopicExpressionNull() {
        checkValidEntities();

        boolean exceptionOccured = false;
        try {
            String partitionExpression = "";
            String topicExpression = null;
            String uri = "";
            participant.createPersistentSnapshot(partitionExpression,topicExpression,uri);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testCreatePersistentSnapshotTopicExpressionNull", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException has occured failed", exceptionOccured);
    }

    /**
     * Test method for {@link org.opensplice.dds.domain.DomainParticipantImpl#createPersistentSnapshot()}.
     */
    @Test
    public void testCreatePersistentSnapshotUriNull() {
        checkValidEntities();

        boolean exceptionOccured = false;
        try {
            String partitionExpression = "";
            String topicExpression = "";
            String uri = null;
            participant.createPersistentSnapshot(partitionExpression,topicExpression,uri);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testCreatePersistentSnapshotUriNull", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException has occured failed", exceptionOccured);
    }

    @Test
    public void testSetPropertyEmpty() {
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        boolean exceptionOccured = false;
        try {
            ((org.opensplice.dds.domain.DomainParticipant)participant).setProperty("","");
        } catch (Exception e) {
            assertTrue("Check for UnsupportedOperationException but got exception:" + util.printException(e),
                    util.exceptionCheck("testSetPropertyEmpty", e, e instanceof UnsupportedOperationException));
            exceptionOccured = true;
        }
        assertTrue("Check if UnsupportedOperationException occured failed", exceptionOccured);
    }

    @Test
    public void testSetPropertyNullNull() {
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        boolean exceptionOccured = false;
        try {
            ((org.opensplice.dds.domain.DomainParticipant)participant).setProperty(null,null);
        } catch (Exception e) {
            assertTrue("Check for IllegalArgumentException but got exception:" + util.printException(e),
                    util.exceptionCheck("testSetPropertyNullNull", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException occured failed", exceptionOccured);
    }

    @Test
    public void testSetPropertyEmptyNull() {
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        boolean exceptionOccured = false;
        try {
            ((org.opensplice.dds.domain.DomainParticipant)participant).setProperty("",null);
        } catch (Exception e) {
            assertTrue("Check for IllegalArgumentException but got exception:" + util.printException(e),
                    util.exceptionCheck("testSetPropertyEmptyNull", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException occured failed", exceptionOccured);
    }

    @Test
    public void testSetPropertyNullEmpty() {
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        boolean exceptionOccured = false;
        try {
            ((org.opensplice.dds.domain.DomainParticipant)participant).setProperty(null,"");
        } catch (Exception e) {
            assertTrue("Check for IllegalArgumentException but got exception:" + util.printException(e),
                    util.exceptionCheck("testSetPropertyNullEmpty", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException occured failed", exceptionOccured);
    }

    @Test
    public void testSetPropertyUnsupportedProperty() {
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        boolean exceptionOccured = false;
        try {
            ((org.opensplice.dds.domain.DomainParticipant)participant).setProperty("foo","");
        } catch (Exception e) {
            assertTrue("Check for UnsupportedOperationException but got exception:" + util.printException(e),
                    util.exceptionCheck("testSetPropertyUnsupportedProperty", e, e instanceof UnsupportedOperationException));
            exceptionOccured = true;
        }
        assertTrue("Check if UnsupportedOperationException occured failed", exceptionOccured);
    }

    @Test
    public void testSetProperty() {
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        org.opensplice.dds.domain.DomainParticipant par = null;
        try {
            par = (org.opensplice.dds.domain.DomainParticipant) dpf.createParticipant(DOMAIN_ID);
            par.setProperty("isolateNode", "true");
        } catch (Exception e) {
            assertTrue("Exception occured while calling setProperty()" + util.printException(e), util.exceptionCheck("testSetProperty", e, false));
        } finally {
            par.setProperty("isolateNode", "false");
        }
    }


    @Test
    public void testGetPropertyUnsupportedProperty() {
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        boolean exceptionOccured = false;
        try {
            @SuppressWarnings("unused")
            String res = ((org.opensplice.dds.domain.DomainParticipant)participant).getProperty("foo");
        } catch (Exception e) {
            assertTrue("Check for UnsupportedOperationException but got exception:" + util.printException(e),
                    util.exceptionCheck("testGetPropertyUnsupportedProperty", e, e instanceof UnsupportedOperationException));
            exceptionOccured = true;
        }
        assertTrue("Check if UnsupportedOperationException occured failed", exceptionOccured);
    }

    @Test
    public void testGetPropertyNull() {
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        boolean exceptionOccured = false;
        try {
            @SuppressWarnings("unused")
            String res = ((org.opensplice.dds.domain.DomainParticipant)participant).getProperty(null);
        } catch (Exception e) {
            assertTrue("Check for IllegalArgumentException but got exception:" + util.printException(e),
                    util.exceptionCheck("testGetPropertyNull", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException occured failed", exceptionOccured);
    }

    @Test
    public void testGetPropertyEmpty() {
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        boolean exceptionOccured = false;
        try {
            @SuppressWarnings("unused")
            String res = ((org.opensplice.dds.domain.DomainParticipant)participant).getProperty("");
        } catch (Exception e) {
            assertTrue("Check for UnsupportedOperationException but got exception:" + util.printException(e),
                    util.exceptionCheck("testGetPropertyEmpty", e, e instanceof UnsupportedOperationException));
            exceptionOccured = true;
        }
        assertTrue("Check if UnsupportedOperationException occured failed", exceptionOccured);
    }


    @Test
    public void testGetProperty() {
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        String res = null;
        try {
            res = ((org.opensplice.dds.domain.DomainParticipant)participant).getProperty("isolateNode");
        } catch (Exception e) {
            assertTrue("Exception occured while calling testGetProperty()" + util.printException(e), util.exceptionCheck("testGetProperty", e, false));
        }
        assertTrue("Check for valid result ", res.equals("false"));
    }

    @Test
    public void testGetSetProperty() {
        assertTrue("Check for Participant object", participant instanceof DomainParticipant);
        String res = null;
        try {
            org.opensplice.dds.domain.DomainParticipant par = (org.opensplice.dds.domain.DomainParticipant) dpf.createParticipant(DOMAIN_ID);
            par.setProperty("isolateNode", "true");
            res = ((org.opensplice.dds.domain.DomainParticipant)participant).getProperty("isolateNode");
            assertTrue("Check for valid result ", res.equals("true"));
            par.setProperty("isolateNode", "false");
        } catch (Exception e) {
            assertTrue("Exception occured while calling testGetSetProperty()" + util.printException(e), util.exceptionCheck("testGetProperty", e, false));
        }
    }



}
