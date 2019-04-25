/**
 *
 */
package unittest.sub;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Properties;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.domain.DomainParticipantFactory;
import org.omg.dds.pub.Publisher;
import org.omg.dds.sub.DataReader;
import org.omg.dds.sub.InstanceState;
import org.omg.dds.sub.SampleState;
import org.omg.dds.sub.Subscriber;
import org.omg.dds.sub.Subscriber.DataState;
import org.omg.dds.sub.ViewState;
import org.omg.dds.topic.Topic;
import org.opensplice.dds.core.OsplServiceEnvironment;
import org.opensplice.dds.sub.DataStateImpl;

import Test.Msg;

import org.eclipse.cyclonedds.test.AbstractUtilities;

/**
 * @author Thijs
 *
 */
public class DataStateopenspliceTest {

    private final static int                DOMAIN_ID   = 123;
    private static ServiceEnvironment       env;
    private static DomainParticipantFactory dpf;
    private static AbstractUtilities        util        = AbstractUtilities.getInstance(DataStateopenspliceTest.class);
    private final static Properties         prop        = new Properties();
    private static DomainParticipant        participant = null;
    private static Publisher                publisher   = null;
    private static Subscriber               subscriber  = null;
    private static Topic<Msg>               topic       = null;
    private static DataReader<Msg>          dataReader  = null;
    private static String                   topicName   = "DataStateopenspliceTest";
    private static DataStateImpl            dataState   = null;

    @BeforeClass
    public static void init() {
        try {
            String serviceEnvironment = System.getProperty("JAVA5PSM_SERVICE_ENV");
            assertTrue("Check for valid ServiceEnvironment string", !serviceEnvironment.equals(""));
            System.setProperty(ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY, serviceEnvironment);
            env = ServiceEnvironment.createInstance(DataStateopenspliceTest.class.getClassLoader());
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
            dataState = (DataStateImpl) subscriber.createDataState();
            dataState.withAnyInstanceState().withAnySampleState().withAnyViewState();
        } catch (Exception e) {
            fail("Exception occured while initiating the DataStateopenspliceTest class: " + util.printException(e));
        }

    }

    private void checkValidEntities() {
        assertTrue("Check for valid DomainParticipant object failed", participant instanceof DomainParticipant);
        assertTrue("Check for valid Publisher object failed", publisher instanceof Publisher);
        assertTrue("Check for valid Subscriber object failed", subscriber instanceof Subscriber);
        assertTrue("Check for valid Topic object failed", topic instanceof Topic);
        assertTrue("Check for valid DataReader object failed", dataReader instanceof DataReader);
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
     * Test method for {@link org.opensplice.dds.sub.DataStateImpl#getSampleStateFromOld(org.opensplice.dds.core.OsplServiceEnvironment, int)}.
     */
    @Test
    public void testGetSampleStateFromOld() {
        checkValidEntities();
        SampleState result = null;
        try {
            result = DataStateImpl.getSampleStateFromOld((OsplServiceEnvironment) env, DDS.READ_SAMPLE_STATE.value);
            assertTrue("Check for valid SampleState object failed", result == SampleState.READ);
            result = DataStateImpl.getSampleStateFromOld((OsplServiceEnvironment) env, DDS.NOT_READ_SAMPLE_STATE.value);
            assertTrue("Check for valid SampleState object failed", result == SampleState.NOT_READ);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetSampleStateFromOld", e, false));
        }
        assertTrue("Check for valid SampleState object failed", result != null);
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.sub.DataStateImpl#getSampleStateFromOld(org.opensplice.dds.core.OsplServiceEnvironment, int)}
     * .
     */
    @Test
    public void testGetSampleStateFromOldIllegal() {
        checkValidEntities();
        boolean exceptionOccured = false;
        try {
            DataStateImpl.getSampleStateFromOld((OsplServiceEnvironment) env, 99);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testGetSampleStateFromOldIllegal", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException occured failed", exceptionOccured);
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.sub.DataStateImpl#getViewStateFromOld(org.opensplice.dds.core.OsplServiceEnvironment, int)}
     * .
     */
    @Test
    public void testGetViewStateFromOld() {
        checkValidEntities();
        ViewState result = null;
        try {
            result = DataStateImpl.getViewStateFromOld((OsplServiceEnvironment) env, DDS.NEW_VIEW_STATE.value);
            assertTrue("Check for valid ViewState object failed", result == ViewState.NEW);
            result = DataStateImpl.getViewStateFromOld((OsplServiceEnvironment) env, DDS.NOT_NEW_VIEW_STATE.value);
            assertTrue("Check for valid ViewState object failed", result == ViewState.NOT_NEW);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetViewStateFromOld", e, false));
        }
        assertTrue("Check for valid ViewState object failed", result != null);
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.sub.DataStateImpl#getViewStateFromOld(org.opensplice.dds.core.OsplServiceEnvironment, int)}
     * .
     */
    @Test
    public void testGetViewStateFromOldIllegal() {
        checkValidEntities();
        boolean exceptionOccured = false;
        try {
            DataStateImpl.getViewStateFromOld((OsplServiceEnvironment) env, 99);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testGetViewStateFromOldIllegal", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException occured failed", exceptionOccured);
    }

    /**
     * Test method for {@link org.opensplice.dds.sub.DataStateImpl#getInstanceStateFromOld(org.opensplice.dds.core.OsplServiceEnvironment, int)}.
     */
    @Test
    public void testGetInstanceStateFromOld() {
        checkValidEntities();
        InstanceState result = null;
        try {
            result = DataStateImpl.getInstanceStateFromOld((OsplServiceEnvironment) env, DDS.ALIVE_INSTANCE_STATE.value);
            assertTrue("Check for valid InstanceState object failed", result == InstanceState.ALIVE);
            result = DataStateImpl.getInstanceStateFromOld((OsplServiceEnvironment) env, DDS.NOT_ALIVE_DISPOSED_INSTANCE_STATE.value);
            assertTrue("Check for valid InstanceState object failed", result == InstanceState.NOT_ALIVE_DISPOSED);
            result = DataStateImpl.getInstanceStateFromOld((OsplServiceEnvironment) env, DDS.NOT_ALIVE_NO_WRITERS_INSTANCE_STATE.value);
            assertTrue("Check for valid InstanceState object failed", result == InstanceState.NOT_ALIVE_NO_WRITERS);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetInstanceStateFromOld", e, false));
        }
        assertTrue("Check for valid InstanceState object failed", result != null);
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.sub.DataStateImpl#getInstanceStateFromOld(org.opensplice.dds.core.OsplServiceEnvironment, int)}
     * .
     */
    @Test
    public void testGetInstanceStateFromOldIllegal() {
        checkValidEntities();
        boolean exceptionOccured = false;
        try {
            DataStateImpl.getInstanceStateFromOld((OsplServiceEnvironment) env, 99);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testGetInstanceStateFromOldIllegal", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException occured failed", exceptionOccured);
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.sub.DataStateImpl#getEnvironment()}.
     */
    @Test
    public void testGetEnvironment() {
        checkValidEntities();
        ServiceEnvironment senv = null;
        try {
            senv = dataState.getEnvironment();
        } catch (Exception e) {
            fail("Exception occured while calling getEnvironment(): " + util.printException(e));
        }
        assertTrue("Check for valid ServiceEnvironment", senv != null);
    }

    /**
     * Test method for {@link org.opensplice.dds.sub.DataStateImpl#getSampleStates()}.
     */
    @Test
    public void testGetSampleStates() {
        checkValidEntities();
        Set<SampleState> result = null;
        try {
            result = dataState.getSampleStates();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetSampleStates", e, false));
        }
        assertTrue("Check for valid Set<SampleState> object failed", result != null);
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.sub.DataStateImpl#getInstanceStates()}.
     */
    @Test
    public void testGetInstanceStates() {
        checkValidEntities();
        Set<InstanceState> result = null;
        try {
            result = dataState.getInstanceStates();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetInstanceStates", e, false));
        }
        assertTrue("Check for valid Set<InstanceState> object failed", result != null);
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.sub.DataStateImpl#getViewStates()}.
     */
    @Test
    public void testGetViewStates() {
        checkValidEntities();
        Set<ViewState> result = null;
        try {
            result = dataState.getViewStates();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetInstanceStates", e, false));
        }
        assertTrue("Check for valid Set<ViewState> object failed", result != null);
    }

    /**
     * Test method for {@link org.opensplice.dds.sub.DataStateImpl#with(org.omg.dds.sub.SampleState)}.
     */
    @Test
    public void testWithSampleState() {
        checkValidEntities();
        DataState result = null;
        try {
            result = subscriber.createDataState().with(SampleState.READ);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testWithSampleState", e, false));
        }
        assertTrue("Check for valid DataState object failed", result != null);
        assertTrue("Check for valid DataState size failed", result.getSampleStates().size() == 1);
        assertTrue("Check for valid DataState size failed", result.getViewStates().size() == 0);
        assertTrue("Check for valid DataState size failed", result.getInstanceStates().size() == 0);
        assertTrue("Check for valid DataState SampleState object failed", result.getSampleStates().contains(SampleState.READ));
    }

    /**
     * Test method for {@link org.opensplice.dds.sub.DataStateImpl#with(org.omg.dds.sub.ViewState)}.
     */
    @Test
    public void testWithViewState() {
        checkValidEntities();
        DataState result = null;
        try {
            result = subscriber.createDataState().with(ViewState.NOT_NEW);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testWithViewState", e, false));
        }
        assertTrue("Check for valid DataState object failed", result != null);
        assertTrue("Check for valid DataState size failed", result.getSampleStates().size() == 0);
        assertTrue("Check for valid DataState size failed", result.getViewStates().size() == 1);
        assertTrue("Check for valid DataState size failed", result.getInstanceStates().size() == 0);
        assertTrue("Check for valid DataState ViewState object failed", result.getViewStates().contains(ViewState.NOT_NEW));
    }

    /**
     * Test method for {@link org.opensplice.dds.sub.DataStateImpl#with(org.omg.dds.sub.InstanceState)}.
     */
    @Test
    public void testWithInstanceState() {
        checkValidEntities();
        DataState result = null;
        try {
            result = subscriber.createDataState().with(InstanceState.ALIVE);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testWithInstanceState", e, false));
        }
        assertTrue("Check for valid DataState object failed", result != null);
        assertTrue("Check for valid DataState size failed", result.getSampleStates().size() == 0);
        assertTrue("Check for valid DataState size failed", result.getViewStates().size() == 0);
        assertTrue("Check for valid DataState size failed", result.getInstanceStates().size() == 1);
        assertTrue("Check for valid DataState InstanceState object failed", result.getInstanceStates().contains(InstanceState.ALIVE));
    }

    /**
     * Test method for {@link org.opensplice.dds.sub.DataStateImpl#withAnySampleState()}.
     */
    @Test
    public void testWithAnySampleState() {
        checkValidEntities();
        DataState result = null;
        try {
            result = subscriber.createDataState().withAnySampleState();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testWithAnySampleState", e, false));
        }
        assertTrue("Check for valid DataState object failed", result != null);
        assertTrue("Check for valid DataState size failed", result.getSampleStates().size() == 2);
        assertTrue("Check for valid DataState size failed", result.getViewStates().size() == 0);
        assertTrue("Check for valid DataState size failed", result.getInstanceStates().size() == 0);
        assertTrue("Check for valid DataState SampleState object failed", result.getSampleStates().contains(SampleState.READ));
        assertTrue("Check for valid DataState SampleState object failed", result.getSampleStates().contains(SampleState.NOT_READ));
    }

    /**
     * Test method for {@link org.opensplice.dds.sub.DataStateImpl#withAnyViewState()}.
     */
    @Test
    public void testWithAnyViewState() {
        checkValidEntities();
        DataState result = null;
        try {
            result = subscriber.createDataState().withAnyViewState();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testWithAnyViewState", e, false));
        }
        assertTrue("Check for valid DataState object failed", result != null);
        assertTrue("Check for valid DataState size failed", result.getSampleStates().size() == 0);
        assertTrue("Check for valid DataState size failed", result.getViewStates().size() == 2);
        assertTrue("Check for valid DataState size failed", result.getInstanceStates().size() == 0);
        assertTrue("Check for valid DataState ViewState object failed", result.getViewStates().contains(ViewState.NOT_NEW));
        assertTrue("Check for valid DataState ViewState object failed", result.getViewStates().contains(ViewState.NEW));
    }

    /**
     * Test method for {@link org.opensplice.dds.sub.DataStateImpl#withAnyInstanceState()}.
     */
    @Test
    public void testWithAnyInstanceState() {
        checkValidEntities();
        DataState result = null;
        try {
            result = subscriber.createDataState().withAnyInstanceState();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testWithAnyInstanceState", e, false));
        }
        assertTrue("Check for valid DataState object failed", result != null);
        assertTrue("Check for valid DataState size failed", result.getSampleStates().size() == 0);
        assertTrue("Check for valid DataState size failed", result.getViewStates().size() == 0);
        assertTrue("Check for valid DataState size failed", result.getInstanceStates().size() == 3);
        assertTrue("Check for valid DataState InstanceState object failed", result.getInstanceStates().contains(InstanceState.ALIVE));
        assertTrue("Check for valid DataState InstanceState object failed", result.getInstanceStates().contains(InstanceState.NOT_ALIVE_DISPOSED));
        assertTrue("Check for valid DataState InstanceState object failed", result.getInstanceStates().contains(InstanceState.NOT_ALIVE_NO_WRITERS));
    }

    /**
     * Test method for {@link org.opensplice.dds.sub.DataStateImpl#withNotAliveInstanceStates()}.
     */
    @Test
    public void testWithNotAliveInstanceStates() {
        checkValidEntities();
        DataState result = null;
        try {
            result = subscriber.createDataState().withNotAliveInstanceStates();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testWithNotAliveInstanceStates", e, false));
        }
        assertTrue("Check for valid DataState object failed", result != null);
        assertTrue("Check for valid DataState size failed", result.getSampleStates().size() == 0);
        assertTrue("Check for valid DataState size failed", result.getViewStates().size() == 0);
        assertTrue("Check for valid DataState size failed", result.getInstanceStates().size() == 2);
        assertFalse("Check for not present DataState InstanceState object failed", result.getInstanceStates().contains(InstanceState.ALIVE));
        assertTrue("Check for valid DataState InstanceState object failed", result.getInstanceStates().contains(InstanceState.NOT_ALIVE_DISPOSED));
        assertTrue("Check for valid DataState InstanceState object failed", result.getInstanceStates().contains(InstanceState.NOT_ALIVE_NO_WRITERS));
    }

    /**
     * Test method for {@link org.opensplice.dds.sub.DataStateImpl#withOldSampleState(int)}.
     */
    @Test
    public void testWithOldSampleState() {
        checkValidEntities();
        DataStateImpl result = null;
        try {
            result = ((DataStateImpl) subscriber.createDataState());
            assertTrue("Check for valid DataState object failed", result != null);
            result.withOldSampleState(DDS.ANY_SAMPLE_STATE.value);
            assertTrue("Check for valid DataState value failed", result.getOldSampleState() == DDS.ANY_SAMPLE_STATE.value);

            result = ((DataStateImpl) subscriber.createDataState());
            assertTrue("Check for valid DataState object failed", result != null);
            result.withOldSampleState(DDS.READ_SAMPLE_STATE.value);
            assertTrue("Check for valid DataState value failed", result.getOldSampleState() == DDS.READ_SAMPLE_STATE.value);

            result = ((DataStateImpl) subscriber.createDataState());
            assertTrue("Check for valid DataState object failed", result != null);
            result.withOldSampleState(DDS.NOT_READ_SAMPLE_STATE.value);
            assertTrue("Check for valid DataState value failed", result.getOldSampleState() == DDS.NOT_READ_SAMPLE_STATE.value);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testWithOldSampleState", e, false));
        }
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.sub.DataStateImpl#withOldSampleState(int)}.
     */
    @Test
    public void testWithOldSampleStateIllegal() {
        checkValidEntities();
        boolean exceptionOccured = false;
        DataStateImpl result = null;
        try {
            result = ((DataStateImpl) subscriber.createDataState());
            result.withOldSampleState(99);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testWithOldSampleStateIllegal", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException occured failed", exceptionOccured);
        exceptionOccured = false;
        try {
            result = ((DataStateImpl) subscriber.createDataState());
            result.getOldSampleState();
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testWithOldSampleStateIllegal", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException occured failed", exceptionOccured);
    }


    /**
     * Test method for {@link org.opensplice.dds.sub.DataStateImpl#withOldViewState(int)}.
     */
    @Test
    public void testWithOldViewState() {
        checkValidEntities();
        DataStateImpl result = null;
        try {
            result = ((DataStateImpl) subscriber.createDataState());
            assertTrue("Check for valid DataState object failed", result != null);
            result.withOldViewState(DDS.ANY_VIEW_STATE.value);
            assertTrue("Check for valid DataState value failed", result.getOldViewState() == DDS.ANY_VIEW_STATE.value);

            result = ((DataStateImpl) subscriber.createDataState());
            assertTrue("Check for valid DataState object failed", result != null);
            result.withOldViewState(DDS.NEW_VIEW_STATE.value);
            assertTrue("Check for valid DataState value failed", result.getOldViewState() == DDS.NEW_VIEW_STATE.value);

            result = ((DataStateImpl) subscriber.createDataState());
            assertTrue("Check for valid DataState object failed", result != null);
            result.withOldViewState(DDS.NOT_NEW_VIEW_STATE.value);
            assertTrue("Check for valid DataState value failed", result.getOldViewState() == DDS.NOT_NEW_VIEW_STATE.value);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testWithOldViewState", e, false));
        }
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.sub.DataStateImpl#withOldViewState(int)}.
     */
    @Test
    public void testWithOldViewStateIllegal() {
        checkValidEntities();
        boolean exceptionOccured = false;
        DataStateImpl result = null;
        try {
            result = ((DataStateImpl) subscriber.createDataState());
            result.withOldViewState(99);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testWithOldViewStateIllegal", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException occured failed", exceptionOccured);
        exceptionOccured = false;
        try {
            result = ((DataStateImpl) subscriber.createDataState());
            result.getOldViewState();
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testWithOldViewStateIllegal", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException occured failed", exceptionOccured);
    }

    /**
     * Test method for {@link org.opensplice.dds.sub.DataStateImpl#withOldInstanceState(int)}.
     */
    @Test
    public void testWithOldInstanceState() {
        checkValidEntities();
        DataStateImpl result = null;
        try {
            result = ((DataStateImpl) subscriber.createDataState());
            assertTrue("Check for valid DataState object failed", result != null);
            result.withOldInstanceState(DDS.ANY_INSTANCE_STATE.value);
            assertTrue("Check for valid DataState value failed", result.getOldInstanceState() == DDS.ANY_INSTANCE_STATE.value);

            result = ((DataStateImpl) subscriber.createDataState());
            assertTrue("Check for valid DataState object failed", result != null);
            result.withOldInstanceState(DDS.ALIVE_INSTANCE_STATE.value);
            assertTrue("Check for valid DataState value failed", result.getOldInstanceState() == DDS.ALIVE_INSTANCE_STATE.value);

            result = ((DataStateImpl) subscriber.createDataState());
            assertTrue("Check for valid DataState object failed", result != null);
            result.withOldInstanceState(DDS.NOT_ALIVE_INSTANCE_STATE.value);
            assertTrue("Check for valid DataState value failed", result.getOldInstanceState() == DDS.NOT_ALIVE_INSTANCE_STATE.value);

            result = ((DataStateImpl) subscriber.createDataState());
            assertTrue("Check for valid DataState object failed", result != null);
            result.withOldInstanceState(DDS.NOT_ALIVE_DISPOSED_INSTANCE_STATE.value);
            assertTrue("Check for valid DataState value failed", result.getOldInstanceState() == DDS.NOT_ALIVE_DISPOSED_INSTANCE_STATE.value);

            result = ((DataStateImpl) subscriber.createDataState());
            assertTrue("Check for valid DataState object failed", result != null);
            result.withOldInstanceState(DDS.NOT_ALIVE_NO_WRITERS_INSTANCE_STATE.value);
            assertTrue("Check for valid DataState value failed", result.getOldInstanceState() == DDS.NOT_ALIVE_NO_WRITERS_INSTANCE_STATE.value);

            result = ((DataStateImpl) subscriber.createDataState());
            assertTrue("Check for valid DataState object failed", result != null);
            result.withOldInstanceState(DDS.NOT_ALIVE_NO_WRITERS_INSTANCE_STATE.value);
            result.withOldInstanceState(DDS.NOT_ALIVE_DISPOSED_INSTANCE_STATE.value);
            assertTrue("Check for valid DataState value failed", result.getOldInstanceState() == DDS.NOT_ALIVE_INSTANCE_STATE.value);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testWithOldInstanceState", e, false));
        }
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.sub.DataStateImpl#withOldInstanceState(int)}.
     */
    @Test
    public void testWithOldInstanceStateIllegal() {
        checkValidEntities();
        boolean exceptionOccured = false;
        DataStateImpl result = null;
        try {
            result = ((DataStateImpl) subscriber.createDataState());
            result.withOldInstanceState(99);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testWithOldInstanceStateIllegal", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException occured failed", exceptionOccured);
        exceptionOccured = false;
        try {
            result = ((DataStateImpl) subscriber.createDataState());
            result.withOldInstanceState(DDS.NOT_ALIVE_DISPOSED_INSTANCE_STATE.value);
            result.withOldInstanceState(DDS.ALIVE_INSTANCE_STATE.value);
            assertTrue("Check for valid DataState value failed", result.getOldInstanceState() == 
                    (DDS.ALIVE_INSTANCE_STATE.value | DDS.NOT_ALIVE_DISPOSED_INSTANCE_STATE.value));
        } catch (Exception e) {
            exceptionOccured = true;
        }
        assertFalse("Check if IllegalArgumentException occured failed", exceptionOccured);
        exceptionOccured = false;
        try {
            result = ((DataStateImpl) subscriber.createDataState());
            result.getOldInstanceState();
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testWithOldInstanceStateIllegal", e, e instanceof IllegalArgumentException));
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException occured failed", exceptionOccured);
    }

}
