package unittest.domain;

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
import org.opensplice.dds.core.status.AllDataDisposedStatus;
import org.opensplice.dds.domain.DomainParticipantAdapter;
import org.opensplice.dds.topic.TopicImpl;

import Test.Msg;

import org.eclipse.cyclonedds.test.AbstractUtilities;
import org.eclipse.cyclonedds.test.DomainParticipantListeneropenspliceClass;

public class DomainParticipantListeneropenspliceTest {

    private final static int                                DOMAIN_ID   = 123;
    private static ServiceEnvironment                       env;
    private static DomainParticipantFactory                 dpf;
    private static AbstractUtilities                        util        = AbstractUtilities.getInstance(DomainParticipantListeneropenspliceTest.class);
    private final static Properties                         prop        = new Properties();
    private static DomainParticipant                        participant = null;
    private static Publisher                                publisher   = null;
    private static Subscriber                               subscriber  = null;
    private static TopicImpl<Msg>                           topic       = null;
    private static String                                   topicName   = "DomainParticipantListeneropenspliceTest";
    private static Msg                                      message     = new Msg(0, "DomainParticipantListeneropenspliceTest");
    private final static Semaphore                          sem         = new Semaphore(0);
    private static DomainParticipantListeneropenspliceClass dpl         = new DomainParticipantListeneropenspliceClass(sem);

    @BeforeClass
    public static void init() {
        try {
            String serviceEnvironment = System.getProperty("JAVA5PSM_SERVICE_ENV");
            assertTrue("Check for valid ServiceEnvironment string", !serviceEnvironment.equals(""));
            System.setProperty(ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY, serviceEnvironment);
            env = ServiceEnvironment.createInstance(DomainParticipantListeneropenspliceTest.class.getClassLoader());
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
            topic = (TopicImpl<Msg>) participant.createTopic(topicName, Msg.class);
            assertTrue("Check for valid Topic object failed", topic instanceof Topic);
            assertTrue("Check for valid DomainParticipantAdapter object failed", dpl instanceof DomainParticipantAdapter);
        } catch (Exception e) {
            fail("Exception occured while initiating the DomainParticipantListeneropenspliceTest class: " + util.printException(e));
        }

    }

    private void checkValidEntities() {
        assertTrue("Check for valid DomainParticipant object failed", participant instanceof DomainParticipant);
        assertTrue("Check for valid Publisher object failed", publisher instanceof Publisher);
        assertTrue("Check for valid Subscriber object failed", subscriber instanceof Subscriber);
        assertTrue("Check for valid Topic object failed", topic instanceof TopicImpl);
        assertTrue("Check for valid DomainParticipantListener object failed", dpl instanceof DomainParticipantListeneropenspliceClass);
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
    @Test
    public void testOnAllDataDisposed() {
        checkValidEntities();
        boolean triggerOccured = false;
        String function = "testOnAllDataDisposed";
        try {
            participant.setListener(dpl, AllDataDisposedStatus.class);
            publisher.createDataWriter(topic).write(message);
            Thread.sleep(util.getWriteSleepTime());
            topic.disposeAllData();
            triggerOccured = sem.tryAcquire(10, TimeUnit.SECONDS);
            assertTrue("OnAllDataDisposed listener status failed to trigger", triggerOccured);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck(function, e, false));
        } finally {
            if (sem.availablePermits() > 0) {
                sem.drainPermits();
            }
        }
        assertTrue("Check for valid OnAllDataDisposed value", util.valueCompareCheck("testOnAllDataDisposed", dpl.allDataDisposedCounter, 0, AbstractUtilities.Equality.GT));
        dpl.reset();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testOnInconsistentTopic() {
        checkValidEntities();
        boolean triggerOccured = false;
        String function = "testOnInconsistentTopic";
        try {
            participant.setListener(dpl, InconsistentTopicStatus.class);

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
        assertTrue("Check for valid OnInconsistentTopic value", util.valueCompareCheck(function, dpl.inconsistentTopicCounter, 0, AbstractUtilities.Equality.GT));
        dpl.reset();
    }

}
