/**
 *
 */
package unittest.sub;

import Test.GetDataReaders;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Collection;
import java.util.Properties;

import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.status.Status;
import org.omg.dds.core.status.SubscriptionMatchedStatus;
import org.omg.dds.core.status.PublicationMatchedStatus;
import org.omg.dds.core.PreconditionNotMetException;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.domain.DomainParticipantFactory;
import org.omg.dds.pub.DataWriterQos;
import org.omg.dds.pub.DataWriter;
import org.omg.dds.pub.PublisherQos;
import org.omg.dds.pub.Publisher;
import org.omg.dds.sub.SubscriberQos;
import org.omg.dds.sub.Subscriber;
import org.omg.dds.sub.DataReaderQos;
import org.omg.dds.sub.DataReader;
import org.omg.dds.topic.TopicQos;
import org.omg.dds.topic.Topic;

import org.eclipse.cyclonedds.test.AbstractUtilities;

/**
 *
 * @author ernstv
 */
public class SubscriberGetDataReadersopenspliceTest {

    private final static int                DOMAIN_ID   = 123;
    private final static Properties         prop        = new Properties();
    private static ServiceEnvironment       env;
    private static DomainParticipantFactory dpf;
    private static DomainParticipant        participant = null;
    private static AbstractUtilities        util        = AbstractUtilities.getInstance(SubscriberGetDataReadersopenspliceTest.class);

    public class Counter
    {

        public int smpls = 0;
        public int cnt = 0;

        public int getSmpls() { return smpls;}
        public int getCnt()   { return cnt;}

        public Counter() {
        }
        public Counter(int a) { cnt = 0;
        }
    }
    public void test_sleep(int mSec)
    {
        try {
            Thread.sleep(mSec);
        } catch (Exception ex){ System.err.println("Exception!" + ex.getMessage());}

    }

    @BeforeClass
    public static void init() {
        try {
            String serviceEnvironment = System.getProperty("JAVA5PSM_SERVICE_ENV");
            assertTrue("Check for valid ServiceEnvironment string", !serviceEnvironment.equals(""));
            System.setProperty(ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY, serviceEnvironment);
            env = ServiceEnvironment.createInstance(SubscriberGetDataReadersopenspliceTest.class.getClassLoader());
            assertTrue("Check for valid ServiceEnvironment object", env instanceof ServiceEnvironment);
            dpf = DomainParticipantFactory.getInstance(env);
            assertTrue("Check for valid DomainParticipantFactory object", dpf instanceof DomainParticipantFactory);
            prop.setProperty("domainid", new Integer(DOMAIN_ID).toString());
            assertTrue("Check is deamon is correctly started", util.beforeClass(prop));
            participant = dpf.createParticipant(DOMAIN_ID);
            assertTrue("Check for valid DomainParticipant object", participant instanceof DomainParticipant);
            assertTrue("Check for valid DomainParticipant with id " + DOMAIN_ID, participant.getDomainId() == DOMAIN_ID);
        } catch (Exception e) {
            fail("Exception occured while initiating the DomainParticipant object: " + util.printException(e));
        }
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
     * Test testPresentationDefault
     */
    @SuppressWarnings("unchecked")
    //@tTest
    public void testPresentationDefault() {

        Publisher  pub = null;
        Subscriber sub = null;

        Topic<GetDataReaders> topicA = null;
        Topic<GetDataReaders> topicB = null;
        Topic<GetDataReaders> topicC = null;


        DataWriter<GetDataReaders> writerA = null;
        DataWriter<GetDataReaders> writerB = null;

        DataReader<GetDataReaders> readerA = null;
        DataReader<GetDataReaders> readerB = null;
        DataReader<GetDataReaders> readerB1 = null;
        DataReader<GetDataReaders> readerC = null;
        TopicQos tQos;
        DataWriterQos dwQos;
        DataReaderQos drQos;

        Collection<DataReader<?>> rv = null;
        Class<? extends Status> status = null;

        PublicationMatchedStatus pstatusA;
        PublicationMatchedStatus pstatusB;
        SubscriptionMatchedStatus sstatusA;
        SubscriptionMatchedStatus sstatusB;
        SubscriptionMatchedStatus sstatusB1;

        GetDataReaders TestData;

        int count = 0;

        /*
         * \addtogroup
         *
         * \test java5_GetDataReaders_000 <br>
         *
         * \testobjective
         * Initialize the test entities as a prerequisite to test the GetDataReaders with default QoS settings
         *
         * \testprocedure
         * - Initialize
         *    - Expect it all to succeed
         */
        try {
            pub = participant.createPublisher();
            assertTrue("Check for valid publisher failed", pub instanceof Publisher);

            sub = participant.createSubscriber();
            assertTrue("Check for valid subscriber failed", sub instanceof Subscriber);

            tQos = participant.getDefaultTopicQos();
            tQos = tQos.withPolicies(tQos.getReliability().withReliable(), tQos.getDurability().withTransient() );
            assertTrue("Setting topicQos failed", tQos instanceof TopicQos);

            topicA = participant.createTopic("SubscriberGetDataReadersTest1TopicA", GetDataReaders.class, tQos, null, status);
            topicB = participant.createTopic("SubscriberGetDataReadersTest1TopicB", GetDataReaders.class, tQos, null, status);
            topicC = participant.createTopic("SubscriberGetDataReadersTest1TopicC", GetDataReaders.class, tQos, null, status);
            assertTrue("Creating topicA failed", topicA instanceof Topic);
            assertTrue("Creating topicB failed", topicB instanceof Topic);
            assertTrue("Creating topicC failed", topicC instanceof Topic);

            dwQos = pub.getDefaultDataWriterQos();
            dwQos = dwQos.withPolicies(dwQos.getReliability().withReliable(), dwQos.getDurability().withTransient());
            assertTrue("Setting dataWriterQos failed", dwQos instanceof DataWriterQos);

            writerA = pub.createDataWriter(topicA, dwQos);
            writerB = pub.createDataWriter(topicB, dwQos);
            assertTrue("Creating writerA failed", writerA instanceof DataWriter);
            assertTrue("Creating writerB failed", writerB instanceof DataWriter);

            drQos = sub.getDefaultDataReaderQos();
            drQos = drQos.withPolicies(drQos.getReliability().withReliable(), drQos.getDurability().withTransient());
            assertTrue("Setting dataReaderQos failed", drQos instanceof DataReaderQos);

            readerA = sub.createDataReader(topicA, drQos);
            readerB = sub.createDataReader(topicB, drQos);
            readerB1 = sub.createDataReader(topicB, drQos);
            readerC = sub.createDataReader(topicC, drQos);
            assertTrue("Creating readerA failed", readerA instanceof DataReader);
            assertTrue("Creating readerB failed", readerB instanceof DataReader);
            assertTrue("Creating readerB1 failed", readerB1 instanceof DataReader);
            assertTrue("Creating readerC failed", readerC instanceof DataReader);

            do {
                test_sleep(50);

                pstatusA  = writerA.getPublicationMatchedStatus();
                pstatusB  = writerB.getPublicationMatchedStatus();
                sstatusA  = readerA.getSubscriptionMatchedStatus();
                sstatusB  = readerB.getSubscriptionMatchedStatus();
                sstatusB1 =readerB1.getSubscriptionMatchedStatus();
                count++;
            } while((pstatusA.getCurrentCount() != 1 ||
                     pstatusB.getCurrentCount() != 2 ||
                     sstatusA.getCurrentCount() != 1 ||
                     sstatusB.getCurrentCount() != 1 ||
                    sstatusB1.getCurrentCount() != 1) &&
                    count < 10);
            assertTrue("Timeout waiting for subscriber/publisher match", count != 10);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testPresentationDefault", e, false));
        }

        /**
         * \addtogroup
         *
         * \test java5_GetDataReaders_001 <br>
         *
         * \testobjective
         * Check that GetDataReaders returns a list of 0 readers when default QoS is
         * set and no data is present in any of the readers.
         *
         * \testprocedure
         * - Call GetDataReaders
         *    - Expect empty list
         */
        try {
            rv = sub.getDataReaders();
            assertTrue("getDataReaders returns no readers", rv.isEmpty());
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testPresentationDefault", e, false));
        }

        /**
         * \addtogroup
         *
         * \test java5_GetDataReaders_002 <br>
         *
         * \testobjective
         * Check that GetDataReaders returns a list of readers which have data on them when
         * default QoS is set and data is written, each reader should appear only once.
         *
         * \testprocedure
         * - Write data
         * - Call GetDataReaders
         *    - Expect readers list
         */
        try {
            Counter A = new Counter(), B = new Counter(), B1 = new Counter(), C = new Counter();

            TestData = new GetDataReaders(1,1,1);
            writerA.write(TestData);
            writerB.write(TestData);

            rv = sub.getDataReaders();
            for (DataReader<?> rp : rv) {
                DataReader<GetDataReaders> rpc = (DataReader<GetDataReaders>)rp;
                rpc.take();
                if (rpc == readerA) {
                    A.cnt++;
                }
                else if (rpc == readerB) {
                    B.cnt++;
                }
                else if (rpc == readerB1) {
                    B1.cnt++;
                }
                else if (rpc == readerC) {
                    C.cnt++;
                }
            }
            assertTrue ("Correct number of readers returned by getDataReaders", A.getCnt() == 1 && B.getCnt() == 1 && B1.getCnt() == 1 && C.getCnt() == 0);

        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testPresentationDefault", e, false));
        }

        /**
         * \addtogroup
         *
         * \test java5_GetDataReaders_003 <br>
         *
         * \testobjective
         * Check that an additional call to GetDataReaders after all data is taken
         * results in an empty list.
         *
         * \testprocedure
         * - Call GetDataReaders
         *    - Expect empty list
         */
        try {
            Counter A = new Counter(), B = new Counter(), B1 = new Counter(), C = new Counter();

            rv = sub.getDataReaders();
            for (DataReader<?> rp : rv) {
                DataReader<GetDataReaders> rpc = (DataReader<GetDataReaders>)rp;
                rpc.take();
                if (rpc == readerA) { A.cnt++; }
                else if (rpc == readerB) { B.cnt++;}
                else if (rpc == readerB1) { B1.cnt++;}
                else if (rpc == readerC) { C.cnt++;}
            }
            assertTrue ("No readers returned by getDataReaders", A.getCnt() == 0 && B.getCnt() == 0 && B1.getCnt() == 0 && C.getCnt() == 0);

        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testPresentationDefault", e, false));
        }
        try {
            participant.closeContainedEntities();
        } catch (Exception e) {
            /* ignore */
        }
    }

    /**
     * Test testPresentationGroupNotOrdered
     */
    @SuppressWarnings("unchecked")
    //@tTest
    public void testPresentationGroupNotOrdered() {

        Publisher  pub = null;
        Subscriber sub = null;

        Topic<GetDataReaders> topicA = null;
        Topic<GetDataReaders> topicB = null;
        Topic<GetDataReaders> topicC = null;

        DataWriter<GetDataReaders> writerA = null;
        DataWriter<GetDataReaders> writerB = null;

        DataReader<GetDataReaders> readerA = null;
        DataReader<GetDataReaders> readerB = null;
        DataReader<GetDataReaders> readerB1 = null;
        DataReader<GetDataReaders> readerC = null;
        TopicQos tQos;
        SubscriberQos subQos;
        PublisherQos  pubQos;
        DataWriterQos dwQos;
        DataReaderQos drQos;

        Collection<DataReader<?>> rv = null;
        Class<? extends Status> status = null;

        PublicationMatchedStatus pstatusA;
        PublicationMatchedStatus pstatusB;
        SubscriptionMatchedStatus sstatusA;
        SubscriptionMatchedStatus sstatusB;
        SubscriptionMatchedStatus sstatusB1;

        GetDataReaders TestData;

        int count = 0;
        /*
         * \addtogroup
         *
         * \test java5_GetDataReaders_100 <br>
         *
         * \testobjective
         * Initialize the test entities as a prerequisite to test the GetDataReaders with default QoS settings
         *
         * \testprocedure
         * - Initialize
         *    - Expect it all to succeed
         */
        try {
            pubQos = participant.getDefaultPublisherQos();
            pubQos = pubQos.withPolicies(pubQos.getPresentation().withGroup());
            pubQos = pubQos.withPolicies(pubQos.getPresentation().withCoherentAccess(true));
            pub = participant.createPublisher(pubQos);
            assertTrue("Check for valid publisher failed", pub instanceof Publisher);

            subQos = participant.getDefaultSubscriberQos();
            subQos = subQos.withPolicies(subQos.getPresentation().withGroup());
            subQos = subQos.withPolicies(subQos.getPresentation().withCoherentAccess(true));
            sub = participant.createSubscriber(subQos);
            assertTrue("Check for valid subscriber failed", sub instanceof Subscriber);

            tQos = participant.getDefaultTopicQos();
            tQos = tQos.withPolicies(tQos.getReliability().withReliable(), tQos.getDurability().withTransient() );
            assertTrue("Setting topicQos failed", tQos instanceof TopicQos);

            topicA = participant.createTopic("SubscriberGetDataReadersTest2TopicA", GetDataReaders.class, tQos, null, status);
            topicB = participant.createTopic("SubscriberGetDataReadersTest2TopicB", GetDataReaders.class, tQos, null, status);
            topicC = participant.createTopic("SubscriberGetDataReadersTest2TopicC", GetDataReaders.class, tQos, null, status);
            assertTrue("Creating topicA failed", topicA instanceof Topic);
            assertTrue("Creating topicB failed", topicB instanceof Topic);
            assertTrue("Creating topicC failed", topicC instanceof Topic);

            dwQos = pub.getDefaultDataWriterQos();
            dwQos = dwQos.withPolicies(dwQos.getReliability().withReliable(), dwQos.getDurability().withTransient(), dwQos.getHistory().withKeepAll());
            assertTrue("Setting dataWriterQos failed", dwQos instanceof DataWriterQos);

            writerA = pub.createDataWriter(topicA, dwQos);
            writerB = pub.createDataWriter(topicB, dwQos);
            assertTrue("Creating writerA failed", writerA instanceof DataWriter);
            assertTrue("Creating writerB failed", writerB instanceof DataWriter);

            drQos = sub.getDefaultDataReaderQos();
            drQos = drQos.withPolicies(drQos.getReliability().withReliable(), drQos.getDurability().withTransient());
            assertTrue("Setting dataReaderQos failed", drQos instanceof DataReaderQos);

            readerA = sub.createDataReader(topicA, drQos);
            readerB = sub.createDataReader(topicB, drQos);
            readerB1 = sub.createDataReader(topicB, drQos);
            readerC = sub.createDataReader(topicC, drQos);
            assertTrue("Creating readerA failed", readerA instanceof DataReader);
            assertTrue("Creating readerB failed", readerB instanceof DataReader);
            assertTrue("Creating readerB1 failed", readerB1 instanceof DataReader);
            assertTrue("Creating readerC failed", readerC instanceof DataReader);

            sub.enable();

            do {
                test_sleep(50);

                pstatusA  = writerA.getPublicationMatchedStatus();
                pstatusB  = writerB.getPublicationMatchedStatus();
                sstatusA  = readerA.getSubscriptionMatchedStatus();
                sstatusB  = readerB.getSubscriptionMatchedStatus();
                sstatusB1 =readerB1.getSubscriptionMatchedStatus();
                count++;
            } while((pstatusA.getCurrentCount() != 1 ||
                     pstatusB.getCurrentCount() != 2 ||
                     sstatusA.getCurrentCount() != 1 ||
                     sstatusB.getCurrentCount() != 1 ||
                    sstatusB1.getCurrentCount() != 1) &&
                    count < 10);
            assertTrue("Timeout waiting for subscriber/publisher match", count != 10);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testPresentationGroupNotOrdered", e, false));
        }
        /**
         * \addtogroup
         *
         * \test java5_GetDataReaders_101 <br>
         *
         * \testobjective
         * Check that GetDataReaders returns a list of 0 readers when Presentation QoS
         * access_scope=GROUP and ordered_access=FALSE are set, begin_coherent_changes
         * is called, data is written and end_coherent_changes is NOT called.
         *
         * \testprocedure
         * - Call begin_coherent_changes
         * - Write data
         * - Call GetDataReaders
         *    - Expect empty list
         * - Call end_coherent_changes
         */
        try {
            TestData = new GetDataReaders(1,1,1);

            pub.beginCoherentChanges();
            writerA.write(TestData);
            writerB.write(TestData);

            sub.beginAccess();
            rv = sub.getDataReaders();
            assertTrue("getDataReaders returns no readers", rv.isEmpty());
            sub.endAccess();
            pub.endCoherentChanges();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testPresentationGroupNotOrdered", e, false));
        }

        /**
        * \addtogroup
        *
        * \test java5_GetDataReaders_101a <br>
        *
        * \testobjective
        * Check that GetDataReaders returns PRECONDITION_NOT_MET when when Presentation QoS
        * access_scope=GROUP and ordered_access=FALSE are set and begin_coherent_changes
        * is not called.
        *
        * \testprocedure
        * - Call GetDataReaders
        *    - Expect PRECONDITION_NOT_MET
        */
        try {
            rv = sub.getDataReaders();
            assertTrue("getDataReaders didn't cause a \"precondition not met\" exception", false);
        } catch (PreconditionNotMetException e) {
            //Expected exeption
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testPresentationGroupNotOrdered", e, false));
        }

        /**
        * \addtogroup
        *
        * \test java5_GetDataReaders_102 <br>
        *
        * \testobjective
        * Check that GetDataReaders returns a list of readers which have data on them when
        * Presentation QoS access_scope=GROUP and ordered_access=FALSE are set, data is written
        * and coherent set has ended, each reader should appear only once.
        *
        * \testprocedure
        * - Call begin_access
        * - Call GetDataReaders
        *    - Expect readers list
        * - Call end_access
        */
        try {
            Counter A = new Counter(), B = new Counter(), B1 = new Counter(), C = new Counter();
            sub.beginAccess();

            rv = sub.getDataReaders();
            for (DataReader<?> rp : rv) {
                DataReader<GetDataReaders> rpc = (DataReader<GetDataReaders>)rp;
                rpc.take();
                if (rpc == readerA) {
                    A.cnt++;
                }
                else if (rpc == readerB) {
                    B.cnt++;
                }
                else if (rpc == readerB1) {
                    B1.cnt++;}

                else if (rpc == readerC) {
                    C.cnt++;
                }
            }
            assertTrue ("Correct number of readers returned by getDataReaders", A.getCnt() == 1 && B.getCnt() == 1 && B1.getCnt() == 1 && C.getCnt() == 0);
            sub.endAccess();

        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testPresentationGroupNotOrdered", e, false));
        }

        /**
         * \addtogroup
         *
         * \test java5_GetDataReaders_103 <br>
         *
         * \testobjective
         * Check that an additional call to GetDataReaders after all data is taken
         * results in an empty list.
         *
         * \testprocedure
         * - Call GetDataReaders
         *    - Expect empty list
         */
        try {
            Counter A = new Counter(0), B = new Counter(0), B1 = new Counter(0), C = new Counter(0);
            sub.beginAccess();
            rv = sub.getDataReaders();
            for (DataReader<?> rp : rv) {
                DataReader<GetDataReaders> rpc = (DataReader<GetDataReaders>)rp;
                rpc.take();
                if (rpc == readerA) {
                    A.cnt++;
                }
                else if (rpc == readerB) {
                    B.cnt++;}

                else if (rpc == readerB1) {
                    B1.cnt++;
                }
                else if (rpc == readerC) {
                    C.cnt++;
                }
            }
            assertTrue ("No readers returned by getDataReaders", A.getCnt() == 0 && B.getCnt() == 0 && B1.getCnt() == 0 && C.getCnt() == 0);
            sub.endAccess();

        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testPresentationGroupNotOrdered", e, false));
        }
        try {
            participant.closeContainedEntities();
        } catch (Exception e) {
            /* ignore */
        }
    }

     /**
     * Test testPresentationGroupOrdered
     */
    @SuppressWarnings("unchecked")
    //@tTest
    public void testPresentationGroupOrdered() {

        Publisher  pub = null;
        Publisher  pub1 = null;
        Subscriber sub = null;
        Subscriber sub1 = null;

        Topic<GetDataReaders> topicA = null;
        Topic<GetDataReaders> topicB = null;
        Topic<GetDataReaders> topicC = null;


        DataWriter<GetDataReaders> writerA = null;
        DataWriter<GetDataReaders> writerB = null;
        DataWriter<GetDataReaders> writer1A = null;
        DataWriter<GetDataReaders> writer1B = null;

        DataReader<GetDataReaders> readerA = null;
        DataReader<GetDataReaders> readerB = null;
        DataReader<GetDataReaders> readerB1 = null;
        DataReader<GetDataReaders> readerC = null;
        DataReader<GetDataReaders> reader1A = null;
        DataReader<GetDataReaders> reader1B = null;

        TopicQos tQos;
        SubscriberQos subQos;
        PublisherQos  pubQos;
        DataWriterQos dwQos;
        DataReaderQos drQos;

        Collection<DataReader<?>> rv = null;
        Class<? extends Status> status = null;

        PublicationMatchedStatus pstatusA;
        PublicationMatchedStatus pstatusB;
        SubscriptionMatchedStatus sstatusA;
        SubscriptionMatchedStatus sstatusB;
        SubscriptionMatchedStatus sstatusB1;

        GetDataReaders TestData;

        int count = 0;
        /*
         * \addtogroup
         *
         * \test java5_GetDataReaders_000 <br>
         *
         * \testobjective
         * Initialize the test entities as a prerequisite to test the GetDataReaders with default QoS settings
         *
         * \testprocedure
         * - Initialize
         *    - Expect it all to succeed
         */
        try {
            pubQos = participant.getDefaultPublisherQos();
            pubQos = pubQos.withPolicies(pubQos.getPresentation().withGroup());
            pubQos = pubQos.withPolicies(pubQos.getPresentation().withOrderedAccess(true));
            pub1 = participant.createPublisher(pubQos);
            pubQos = pubQos.withPolicies(pubQos.getPresentation().withCoherentAccess(true));
            pub = participant.createPublisher(pubQos);
            assertTrue("Check for valid publisher failed", pub instanceof Publisher);
            assertTrue("Check for valid publisher failed", pub1 instanceof Publisher);
            assertTrue("pub 1 not equal to pup2", pub != pub1);

            subQos = participant.getDefaultSubscriberQos();
            subQos = subQos.withPolicies(subQos.getPresentation().withGroup());
            subQos = subQos.withPolicies(subQos.getPresentation().withOrderedAccess(true));
            sub1 = participant.createSubscriber(subQos);
            subQos = subQos.withPolicies(subQos.getPresentation().withCoherentAccess(true));
            sub = participant.createSubscriber(subQos);
            assertTrue("Check for valid subscriber failed", sub instanceof Subscriber);
            assertTrue("Check for valid subscriber failed", sub1 instanceof Subscriber);
            assertTrue("sub 1 not equal to sup 2", sub != sub1);

            tQos = participant.getDefaultTopicQos();
            tQos = tQos.withPolicies(tQos.getReliability().withReliable(), tQos.getDurability().withTransient() );
            assertTrue("Setting topicQos failed", tQos instanceof TopicQos);

            topicA = participant.createTopic("SubscriberGetDataReadersTest3TopicA", GetDataReaders.class, tQos, null, status);
            topicB = participant.createTopic("SubscriberGetDataReadersTest3TopicB", GetDataReaders.class, tQos, null, status);
            topicC = participant.createTopic("SubscriberGetDataReadersTest3TopicC", GetDataReaders.class, tQos, null, status);
            assertTrue("Creating topicA failed", topicA instanceof Topic);
            assertTrue("Creating topicB failed", topicB instanceof Topic);
            assertTrue("Creating topicC failed", topicC instanceof Topic);

            dwQos = pub.getDefaultDataWriterQos();
            dwQos = dwQos.withPolicies(dwQos.getReliability().withReliable(), dwQos.getDurability().withTransient(), dwQos.getHistory().withKeepAll());
            assertTrue("Setting dataWriterQos failed", dwQos instanceof DataWriterQos);

            writerA = pub.createDataWriter(topicA, dwQos);
            writerB = pub.createDataWriter(topicB, dwQos);
            writer1A = pub1.createDataWriter(topicA, dwQos);
            writer1B = pub1.createDataWriter(topicB, dwQos);
            assertTrue("Creating writerA failed", writerA instanceof DataWriter);
            assertTrue("Creating writerB failed", writerB instanceof DataWriter);
            assertTrue("Creating writerA failed", writer1A instanceof DataWriter);
            assertTrue("Creating writerB failed", writer1B instanceof DataWriter);
            assertTrue("writer A not equal to writer1A", writerA != writer1A);
            assertTrue("writer B not equal to writer1B", writerB != writer1B);

            drQos = sub.getDefaultDataReaderQos();
            drQos = drQos.withPolicies(drQos.getReliability().withReliable(), drQos.getDurability().withTransient());
            assertTrue("Setting dataReaderQos failed", drQos instanceof DataReaderQos);

            readerA = sub.createDataReader(topicA, drQos);
            readerB = sub.createDataReader(topicB, drQos);
            readerB1 = sub.createDataReader(topicB, drQos);
            readerC = sub.createDataReader(topicC, drQos);
            reader1A = sub1.createDataReader(topicA, drQos);
            reader1B = sub1.createDataReader(topicB, drQos);
            assertTrue("Creating readerA failed", readerA instanceof DataReader);
            assertTrue("Creating readerB failed", readerB instanceof DataReader);
            assertTrue("Creating readerB1 failed", readerB1 instanceof DataReader);
            assertTrue("Creating readerC failed", readerC instanceof DataReader);
            assertTrue("Creating readerA failed", reader1A instanceof DataReader);
            assertTrue("Creating readerB failed", reader1B instanceof DataReader);
            assertTrue("reader A not equal to reader 1A", readerA != reader1A);
            assertTrue("reader B not equal to reader 1B", readerB != reader1B);

            sub.enable();

            do {
                test_sleep(50);

                pstatusA  = writerA.getPublicationMatchedStatus();
                pstatusB  = writerB.getPublicationMatchedStatus();
                sstatusA  = readerA.getSubscriptionMatchedStatus();
                sstatusB  = readerB.getSubscriptionMatchedStatus();
                sstatusB1 =readerB1.getSubscriptionMatchedStatus();
                count++;
            } while((pstatusA.getCurrentCount() != 2 ||
                     pstatusB.getCurrentCount() != 3 ||
                     sstatusA.getCurrentCount() != 1 ||
                     sstatusB.getCurrentCount() != 1 ||
                    sstatusB1.getCurrentCount() != 1) &&
                    count < 10);
             assertTrue("Timeout waiting for subscriber/publisher match", count != 10);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testPresentationGroupOrdered", e, false));
        }
        /**
         * \addtogroup
         *
         * \test java5_GetDataReaders_201 <br>
         *
         * \testobjective
         * Check that GetDataReaders returns a list of 0 readers when Presentation QoS
         * access_scope=GROUP and ordered_access=TRUE are set, begin_coherent_changes
         * is called, data is written and end_coherent_changes is NOT called.
         *
         * \testprocedure
         * - Call begin_coherent_changes
         * - Write data
         * - Call GetDataReaders
         *    - Expect empty list
         * - Call end_coherent_changes
         */
        try {
            TestData = new GetDataReaders(1,1,1);
            pub.beginCoherentChanges();
            writerA.write(TestData);
            TestData.long_1 = 2;
            writerA.write(TestData);
            TestData.long_1 = 3;
            writerB.write(TestData);
            writerA.write(TestData);

            sub.beginAccess();
            rv = sub.getDataReaders();
            assertTrue("getDataReaders returns no readers", rv.isEmpty());
            sub.endAccess();
            pub.endCoherentChanges();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testPresentationGroupOrdered", e, false));
        }

        /**
         * \addtogroup
         *
         * \test java5_GetDataReaders_201a <br>
         *
         * \testobjective
         * Check that GetDataReaders returns PRECONDITION_NOT_MET when when Presentation QoS
         * access_scope=GROUP and ordered_access=TRUE are set and begin_coherent_changes
         * is not called.
         *
         * \testprocedure
         * - Call GetDataReaders
         *    - Expect PRECONDITION_NOT_MET
         */
        try {
            rv = sub.getDataReaders();
            assertTrue("getDataReaders did not throuw Precondition Not Met exception", false);
        } catch (PreconditionNotMetException e) {
            //Expected exeption
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testPresentationGroupOrdered", e, false));
        }

        /**
         * \addtogroup
         *
         * \test java5_GetDataReaders_202 <br>
         *
         * \testobjective
         * Check that GetDataReaders returns a list of readers which have data on them when
         * Presentation QoS access_scope=GROUP and ordered_access=TRUE are set, data is written
         * and coherent set has ended, each reader can appear multiple times and ordering must
         * match write order.
         *
         * \testprocedure
         * - Call begin_access
         * - Call GetDataReaders
         *    - Expect ordered readers list
         * - Call end_access
         */
        try {
            Counter A = new Counter(), B = new Counter(), B1 = new Counter(), C = new Counter();
            StringBuilder ordering = new StringBuilder();

            sub.beginAccess();

            rv = sub.getDataReaders();
            for (DataReader<?> rp : rv) {
                DataReader<GetDataReaders> rpc = (DataReader<GetDataReaders>)rp;
                rpc.take();
                if (rpc == readerA) {
                    A.cnt++;
                    ordering.append("A,");
                }
                else if (rpc == readerB) {
                    B.cnt++;
                    ordering.append("B,");
                }
                else if (rpc == readerB1) {
                    B1.cnt++;
                    ordering.append("B1,");
                }
                else if (rpc == readerC) {
                    C.cnt++;
                    ordering.append("C,");
                }
            }
            if (ordering.length() > 1) {
                ordering.deleteCharAt(ordering.length()-1);
            }

            assertTrue ("Correct number of readers returned by getDataReaders", A.getCnt() == 3 && B.getCnt() == 1 && B1.getCnt() == 1 && C.getCnt() == 0);
            assertTrue("Correct order of readers returned by getDataReaders", ordering.toString().contentEquals("A,A,B,B1,A") || ordering.toString().contentEquals("A,A,B1,B,A"));
            sub.endAccess();

        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testPresentationGroupOrdered", e, false));
        }

        /**
     * \addtogroup
     *
     * \test java5_GetDataReaders_203 <br>
     *
     * \testobjective
     * Check that an additional call to GetDataReaders after all data is taken
     * results in an empty list.
     *
     * \testprocedure
     * - Call GetDataReaders
     *    - Expect empty list
     */
        try {
            Counter A = new Counter(), B = new Counter(), B1 = new Counter(), C = new Counter();
            sub.beginAccess();
            rv = sub.getDataReaders();
            for (DataReader<?> rp : rv) {
                DataReader<GetDataReaders> rpc = (DataReader<GetDataReaders>)rp;
                rpc.take();
                if (rpc == readerA) {
                    A.cnt++;
                }
                else if (rpc == readerB) {
                    B.cnt++;
                }
                else if (rpc == readerB1) {
                    B1.cnt++;
                }
                else if (rpc == readerC) {
                    C.cnt++;
                }
            }
            assertTrue ("No readers returned by getDataReaders", A.getCnt() == 0 && B.getCnt() == 0 && B1.getCnt() == 0 && C.getCnt() == 0);
            sub.endAccess();

        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testPresentationGroupOrdered", e, false));
        }

        /**
         * \addtogroup
         *
         * \test java5_GetDataReaders_204 <br>
         *
         * \testobjective
         * Check that GetDataReaders returns a list of readers which have data on
         * them when Presentation QoS access_scope=GROUP, ordered_access=TRUE and
         * coherent_updates=FALSE are set, reader A must appear multiple times,
         * reader B must appear only once and ordering must match write order.
         *
         * \testprocedure
         * - Call begin_access
         * - Call GetDataReaders
         *    - Expect ordered readers list
         * - Call end_access
         */
        try {
            Counter A = new Counter(), B = new Counter();
            StringBuilder ordering = new StringBuilder();

            TestData = new GetDataReaders(1,1,1);
            writer1A.write(TestData);
            TestData.long_1 = 2;
            writer1A.write(TestData);
            TestData.long_1 = 3;
            writer1B.write(TestData);
            writer1A.write(TestData);
            sub1.beginAccess();

            rv = sub1.getDataReaders();
            for (DataReader<?> rp : rv) {
                DataReader<GetDataReaders> rpc = (DataReader<GetDataReaders>)rp;
                rpc.take();
                if (rpc == reader1A) {
                    A.cnt++;
                    ordering.append("A,");
                }
                else if (rpc == reader1B) {
                    B.cnt++;
                    ordering.append("B,");
                }
            }
            if (ordering.length() > 1) {
                ordering.deleteCharAt(ordering.length()-1);
            }

            assertTrue ("Correct number of readers returned by getDataReaders", A.getCnt() == 3 && B.getCnt() == 1);
            assertTrue("Correct order of readers returned by getDataReaders", ordering.toString().contentEquals("A,A,B,A"));
            sub1.endAccess();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testPresentationGroupOrdered", e, false));
        }

        try {
            participant.closeContainedEntities();
        } catch (Exception e) {
            /* ignore */
        }
    }
}
