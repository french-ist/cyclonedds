package unittest.topic;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Iterator;
import java.util.Properties;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.dds.core.AlreadyClosedException;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.event.InconsistentTopicEvent;
import org.omg.dds.core.policy.Partition;
import org.omg.dds.core.policy.Presentation;
import org.omg.dds.core.status.InconsistentTopicStatus;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.domain.DomainParticipantFactory;
import org.omg.dds.pub.DataWriter;
import org.omg.dds.pub.Publisher;
import org.omg.dds.pub.PublisherQos;
import org.omg.dds.sub.DataReader;
import org.omg.dds.sub.Sample;
import org.omg.dds.sub.Subscriber;
import org.omg.dds.topic.Topic;
import org.opensplice.dds.core.event.AllDataDisposedEvent;
import org.opensplice.dds.core.status.AllDataDisposedStatus;
import org.opensplice.dds.topic.TopicImpl;

import Test.Msg;

import org.eclipse.cyclonedds.test.AbstractUtilities;
import org.eclipse.cyclonedds.test.TopicListeneropenspliceClass;

public class TopicListeneropenspliceTest {

    private final static int                    DOMAIN_ID   = 123;
    private static ServiceEnvironment           env;
    private static DomainParticipantFactory     dpf;
    private static AbstractUtilities            util        = AbstractUtilities.getInstance(TopicListeneropenspliceTest.class);
    private final static Properties             prop        = new Properties();
    private static DomainParticipant            participant = null;
    private static TopicImpl<Msg>               topic       = null;
    private static String                       topicName   = "TopicListeneropenspliceTest";
    private final static Semaphore              sem         = new Semaphore(0);
    private static TopicListeneropenspliceClass tl          = null;

    @BeforeClass
    public static void init() {
        try {
            String serviceEnvironment = System.getProperty("JAVA5PSM_SERVICE_ENV");
            assertTrue("Check for valid ServiceEnvironment string", !serviceEnvironment.equals(""));
            System.setProperty(ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY, serviceEnvironment);
            env = ServiceEnvironment.createInstance(TopicListeneropenspliceTest.class.getClassLoader());
            assertTrue("Check for valid ServiceEnvironment object failed", env instanceof ServiceEnvironment);
            dpf = DomainParticipantFactory.getInstance(env);
            assertTrue("Check for valid DomainParticipantFactory object failed", dpf instanceof DomainParticipantFactory);
            prop.setProperty("domainid", new Integer(DOMAIN_ID).toString());
            assertTrue("Check is deamon is correctly started", util.beforeClass(prop));
            participant = dpf.createParticipant(DOMAIN_ID);
            assertTrue("Check for valid DomainParticipant object failed", participant instanceof DomainParticipant);
            assertTrue("Check for valid DomainParticipant with id " + DOMAIN_ID, participant.getDomainId() == DOMAIN_ID);
            topic = (TopicImpl<Msg>) participant.createTopic(topicName, Msg.class);
            assertTrue("Check for valid Topic object failed", topic instanceof Topic);
            tl = new TopicListeneropenspliceClass(sem);
            assertTrue("Check for valid TopicListener object failed", tl instanceof TopicListeneropenspliceClass);
        } catch (Exception e) {
            fail("Exception occured while initiating the TopicListenerTest class: " + util.printException(e));
        }

    }

    private void checkValidEntities() {
        assertTrue("Check for valid DomainParticipant object failed", participant instanceof DomainParticipant);
        assertTrue("Check for valid Topic object failed", topic instanceof Topic);
        assertTrue("Check for valid TopicListener object failed", tl instanceof TopicListeneropenspliceClass);
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

    @SuppressWarnings("unchecked")
    //@tTest
    public void testOnInconsistentTopic() {
        checkValidEntities();
        boolean triggerOccured = false;
        String function = "testOnInconsistentTopic";
        try {
            topic.setListener(tl, InconsistentTopicStatus.class);

            Subscriber bsub = participant.getBuiltinSubscriber();
            DataReader<DDS.TopicBuiltinTopicData> dr = bsub.lookupDataReader("DCPSTopic");

            Iterator<Sample<DDS.TopicBuiltinTopicData>> result = null;
            result = dr.read();
            if (result != null) {
                while (result.hasNext()) {
                    DDS.TopicBuiltinTopicData mes = result.next().getData();
                    if (mes != null) {
                        if (mes.name.equals(topicName)) {
                            DomainParticipant par = bsub.getParent();
                            Topic<DDS.TopicBuiltinTopicData> top = (Topic<DDS.TopicBuiltinTopicData>) dr.getTopicDescription();
                            PublisherQos pq = par.getDefaultPublisherQos();
                            Partition p = pq.getPartition().withName("__BUILT-IN PARTITION__");
                            Presentation pres = pq.getPresentation().withTopic().withOrderedAccess(false).withCoherentAccess(false);
                            pq = pq.withPolicy(p).withPolicy(pres);
                            Publisher pub = par.createPublisher(pq);
                            DataWriter<DDS.TopicBuiltinTopicData> dw = pub.createDataWriter(top, pub.copyFromTopicQos(pub.getDefaultDataWriterQos(), top.getQos()));
                            mes.durability.kind = DDS.DurabilityQosPolicyKind.TRANSIENT_DURABILITY_QOS;
                            dw.write(mes);
                        }
                    }
                }
            }

            try {
                triggerOccured = sem.tryAcquire(10, TimeUnit.SECONDS);
                /* test status event */
                if (triggerOccured) {
                    assertTrue("check for valid qosevent failed ", util.objectCheck(function, tl.itStatus));
                    InconsistentTopicEvent<Msg> o = tl.itStatus.clone();
                    assertTrue("check for valid clone of qosevent failed ", util.objectCheck(function, o));
                    ServiceEnvironment se = o.getEnvironment();
                    assertTrue("check for valid ServiceEnvironment failed ", util.objectCheck(function, se));
                    Topic<Msg> src = o.getSource();
                    assertTrue("check for valid DataReader failed ", util.objectCheck(function, src));
                    InconsistentTopicStatus status = o.getStatus();
                    assertTrue("check for valid Status failed ", util.objectCheck(function, status));
                    /* test status */
                    if (status != null) {
                        assertTrue("Check for valid getTotalCount value failed", util.valueCompareCheck(function, status.getTotalCount(), 0, AbstractUtilities.Equality.GT));
                        assertTrue("Check for valid getTotalCountChange value failed", util.valueCompareCheck(function, status.getTotalCountChange(), 0, AbstractUtilities.Equality.GT));
                        assertTrue("Check for valid status environment failed", util.objectCheck(function, status.getEnvironment()));
                    }
                }
            } catch (Exception e) {
                assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck(function, e, false));
            }
            assertTrue("OnInconsistentTopic listener status failed to trigger", triggerOccured);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck(function, e, false));
        } finally {
            if (sem.availablePermits() > 0) {
                sem.drainPermits();
            }
        }
        assertTrue("Check for valid OnInconsistentTopic value", util.valueCompareCheck(function, tl.inconsistentTopicCounter, 0, AbstractUtilities.Equality.GT));
        tl.reset();
    }

    @SuppressWarnings("unchecked")
    //@tTest
    public void testOnAllDataDisposed() {
        checkValidEntities();
        boolean triggerOccured = false;
        String function = "testOnAllDataDisposed";
        try {
            topic.setListener(tl, AllDataDisposedStatus.class);
            Publisher pub = participant.createPublisher();
            Msg message = new Msg(1,"testOnAllDataDisposed");
            pub.createDataWriter(topic).write(message);
            Thread.sleep(util.getWriteSleepTime());
            topic.disposeAllData();
            triggerOccured = sem.tryAcquire(10, TimeUnit.SECONDS);
            /* test status event */
            if (triggerOccured) {
                assertTrue("check for valid qosevent failed ", util.objectCheck(function, tl.addStatus));
                AllDataDisposedEvent<Msg> o = tl.addStatus.clone();
                assertTrue("check for valid clone of qosevent failed ", util.objectCheck(function, o));
                ServiceEnvironment se = o.getEnvironment();
                assertTrue("check for valid ServiceEnvironment failed ", util.objectCheck(function, se));
                Topic<Msg> src = o.getSource();
                assertTrue("check for valid DataReader failed ", util.objectCheck(function, src));
                AllDataDisposedStatus status = o.getStatus();
                assertTrue("check for valid Status failed ", util.objectCheck(function, status));
                /* test status */
                if (status != null) {
                    assertTrue("Check for valid getTotalCount value failed", util.valueCompareCheck(function, status.getTotalCount(), 0, AbstractUtilities.Equality.GT));
                    assertTrue("Check for valid getTotalCountChange value failed", util.valueCompareCheck(function, status.getTotalCountChange(), 0, AbstractUtilities.Equality.GT));
                    assertTrue("Check for valid status environment failed", util.objectCheck(function, status.getEnvironment()));
                }
                AllDataDisposedStatus status1 = topic.getAllDataDisposedTopicStatus();
                assertTrue("check for valid Status failed ", util.objectCheck(function, status1));
                assertTrue("Check for valid getTotalCount value failed", util.valueCompareCheck(function, status1.getTotalCount(), 0, AbstractUtilities.Equality.GT));
                assertTrue("Check for valid getTotalCountChange value failed", util.valueCompareCheck(function, status1.getTotalCountChange(), 0, AbstractUtilities.Equality.EQ));
            }
            assertTrue("OnAllDataDisposed listener status failed to trigger", triggerOccured);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck(function, e, false));
        } finally {
            if (sem.availablePermits() > 0) {
                sem.drainPermits();
            }
        }
        assertTrue("Check for valid OnAllDataDisposed value", util.valueCompareCheck("testOnAllDataDisposed", tl.allDataDisposedCounter, 0, AbstractUtilities.Equality.GT));
        tl.reset();
    }

}
