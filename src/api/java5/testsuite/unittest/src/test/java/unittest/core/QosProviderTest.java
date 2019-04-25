/**
 *
 */
package unittest.core;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.dds.core.QosProvider;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.domain.DomainParticipantFactory;
import org.omg.dds.domain.DomainParticipantFactoryQos;
import org.omg.dds.domain.DomainParticipantQos;
import org.omg.dds.pub.DataWriter;
import org.omg.dds.pub.DataWriterQos;
import org.omg.dds.pub.Publisher;
import org.omg.dds.pub.PublisherQos;
import org.omg.dds.sub.DataReader;
import org.omg.dds.sub.DataReaderQos;
import org.omg.dds.sub.Subscriber;
import org.omg.dds.sub.SubscriberQos;
import org.omg.dds.topic.Topic;
import org.omg.dds.topic.TopicQos;

import Test.Msg;

import org.eclipse.cyclonedds.test.AbstractUtilities;

public class QosProviderTest {

    private final static int                DOMAIN_ID     = 123;
    private static DomainParticipantFactory dpf;
    private static ServiceEnvironment       env;
    private final static Properties         prop          = new Properties();
    private static AbstractUtilities        util          = AbstractUtilities.getInstance(QosProviderTest.class);
    private static String                   resourcesPath = "file://" + System.getenv("OSPL_OUTER_HOME_NORMALIZED") + "/testsuite/dbt/api/dcps/java5/src/test/resources/defaults.xml";
    private static QosProvider              qosProvider   = null;
    private static String                   profile       = "";
    private static DomainParticipant        participant   = null;
    private static Publisher                publisher     = null;
    private static Subscriber               subscriber    = null;
    private static Topic<Msg>               topic         = null;
    private static DataWriter<Msg>          dataWriter    = null;
    private static DataReader<Msg>          dataReader    = null;
    private static String                   topicName     = "QosProviderTest";

    @BeforeClass
    public static void init() {
        try {
            String serviceEnvironment = System.getProperty("JAVA5PSM_SERVICE_ENV");
            assertTrue("Check for valid ServiceEnvironment string", !serviceEnvironment.equals(""));
            System.setProperty(ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY, serviceEnvironment);
            env = ServiceEnvironment.createInstance(QosProviderTest.class.getClassLoader());
            assertTrue("Check for valid ServiceEnvironment object failed", env instanceof ServiceEnvironment);
            dpf = DomainParticipantFactory.getInstance(env);
            assertTrue("Check for valid DomainParticipantFactory object", dpf instanceof DomainParticipantFactory);
            prop.setProperty("domainid", new Integer(DOMAIN_ID).toString());
            assertTrue("Check is deamon is correctly started", util.beforeClass(prop));
            participant = dpf.createParticipant(DOMAIN_ID);
            assertTrue("Check for valid DomainParticipant object", participant instanceof DomainParticipant);
            assertTrue("Check for valid DomainParticipant with id " + DOMAIN_ID, participant.getDomainId() == DOMAIN_ID);
            publisher = participant.createPublisher();
            assertTrue("Check for valid Publisher object", publisher instanceof Publisher);
            subscriber = participant.createSubscriber();
            assertTrue("Check for valid Subscriber object", subscriber instanceof Subscriber);
            topic = participant.createTopic(topicName, Msg.class);
            assertTrue("Check for valid Topic object", topic instanceof Topic);
            dataWriter = publisher.createDataWriter(topic);
            assertTrue("Check for valid DataWriter object", dataWriter instanceof DataWriter);
            dataReader = subscriber.createDataReader(topic);
            assertTrue("Check for valid DataReader object", dataReader instanceof DataReader);
            qosProvider = QosProvider.newQosProvider(resourcesPath, profile, env);
            assertTrue("Check for valid QosProvider object failed", qosProvider instanceof QosProvider);
        } catch (Exception e) {
            fail("Exception occured while initiating the QosProviderTest class: " + util.printException(e));
        }

    }

    private void checkValidEntities() {
        assertTrue("Check for valid ServiceEnvironment object failed", env instanceof ServiceEnvironment);
        assertTrue("Check for valid QosProvider object failed", qosProvider instanceof QosProvider);
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
     * Test method for
     * {@link org.omg.dds.core.QosProvider#newQosProvider(java.lang.String, java.lang.String, org.omg.dds.core.ServiceEnvironment)}
     * .
     */
    //@tTest
    public void testNewQosProvider() {
        checkValidEntities();
        QosProvider qp = null;
        try {
            qp = QosProvider.newQosProvider(resourcesPath, profile, env);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testNewQosProvider", e, false));
        }
        assertTrue("Check for valid QosProvider object failed", qp != null);
    }

    /**
     * Test method for
     * {@link org.omg.dds.core.QosProvider#newQosProvider(java.lang.String, java.lang.String, org.omg.dds.core.ServiceEnvironment)}
     * .
     */
    //@tTest
    public void testNewQosProviderNullUri() {
        checkValidEntities();
        QosProvider qp = null;
        String uri = null;
        try {
            qp = QosProvider.newQosProvider(uri, profile, env);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testNewQosProviderNullUri", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for invalid QosProvider object failed", qp == null);
    }

    /**
     * Test method for
     * {@link org.omg.dds.core.QosProvider#newQosProvider(java.lang.String, java.lang.String, org.omg.dds.core.ServiceEnvironment)}
     * .
     */
    //@tTest
    public void testNewQosProviderNullenv() {
        checkValidEntities();
        QosProvider qp = null;
        try {
            qp = QosProvider.newQosProvider(resourcesPath, profile, null);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testNewQosProviderNullenv", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for invalid QosProvider object failed", qp == null);
    }

    /**
     * Test method for
     * {@link org.omg.dds.core.QosProvider#getDomainParticipantFactoryQos()}.
     */
    //@tTest
    public void testGetDomainParticipantFactoryQos() {
        checkValidEntities();
        DomainParticipantFactoryQos result = null;
        try {
            result = qosProvider.getDomainParticipantFactoryQos();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetDomainParticipantFactoryQos", e, false));
        }
        assertTrue("Check for valid DomainParticipantFactoryQos object failed", util.objectCheck("testGetDomainParticipantFactoryQos", result));
    }

    /**
     * Test method for {@link org.omg.dds.core.QosProvider#getDomainParticipantFactoryQos(java.lang.String)}.
     */
    //@tTest
    public void testGetDomainParticipantFactoryQosString() {
        checkValidEntities();
        DomainParticipantFactoryQos result = null;
        try {
            result = qosProvider.getDomainParticipantFactoryQos("Foo");
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetDomainParticipantFactoryQosString", e, false));
        }
        assertTrue("Check for invalid DomainParticipantFactoryQos object failed", result == null);
    }


    /**
     * Test method for
     * {@link org.omg.dds.core.QosProvider#getDomainParticipantQos()}.
     */
    //@tTest
    public void testGetDomainParticipantQos() {
        checkValidEntities();
        DomainParticipantQos result = null;
        try {
            result = qosProvider.getDomainParticipantQos();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetDomainParticipantQos", e, false));
        }
        assertTrue("Check for valid DomainParticipantQos object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.core.QosProvider#getDomainParticipantQos(java.lang.String)}.
     */
    //@tTest
    public void testGetDomainParticipantQosString() {
        checkValidEntities();
        DomainParticipantQos result = null;
        try {
            result = qosProvider.getDomainParticipantQos("Foo");
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetDomainParticipantQos", e, false));
        }
        assertTrue("Check for valid DomainParticipantQos object failed", result == null);
    }

    /**
     * Test method for {@link org.omg.dds.core.QosProvider#getTopicQos()}.
     */
    //@tTest
    public void testGetTopicQos() {
        checkValidEntities();
        TopicQos result = null;
        try {
            result = qosProvider.getTopicQos();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetTopicQos", e, false));
        }
        assertTrue("Check for valid TopicQos object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.core.QosProvider#getTopicQos(java.lang.String)}.
     */
    //@tTest
    public void testGetTopicQosString() {
        checkValidEntities();
        TopicQos result = null;
        try {
            result = qosProvider.getTopicQos("Foo");
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetTopicQos", e, false));
        }
        assertTrue("Check for valid TopicQos object failed", result == null);
    }

    /**
     * Test method for {@link org.omg.dds.core.QosProvider#getSubscriberQos()}.
     */
    //@tTest
    public void testGetSubscriberQos() {
        checkValidEntities();
        SubscriberQos result = null;
        try {
            result = qosProvider.getSubscriberQos();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetSubscriberQos", e, false));
        }
        assertTrue("Check for valid SubscriberQos object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.core.QosProvider#getSubscriberQos(java.lang.String)}.
     */
    //@tTest
    public void testGetSubscriberQosString() {
        checkValidEntities();
        SubscriberQos result = null;
        try {
            result = qosProvider.getSubscriberQos("Foo");
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetSubscriberQosString", e, false));
        }
        assertTrue("Check for valid SubscriberQos object failed", result == null);
    }

    /**
     * Test method for {@link org.omg.dds.core.QosProvider#getPublisherQos()}.
     */
    //@tTest
    public void testGetPublisherQos() {
        checkValidEntities();
        PublisherQos result = null;
        try {
            result = qosProvider.getPublisherQos();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetPublisherQos", e, false));
        }
        assertTrue("Check for valid PublisherQos object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.core.QosProvider#getPublisherQos(java.lang.String)}.
     */
    //@tTest
    public void testGetPublisherQosString() {
        checkValidEntities();
        PublisherQos result = null;
        try {
            result = qosProvider.getPublisherQos("Foo");
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetPublisherQosString", e, false));
        }
        assertTrue("Check for valid PublisherQos object failed", result == null);
    }

    /**
     * Test method for {@link org.omg.dds.core.QosProvider#getDataReaderQos()}.
     */
    //@tTest
    public void testGetDataReaderQos() {
        checkValidEntities();
        DataReaderQos result = null;
        try {
            result = qosProvider.getDataReaderQos();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetDataReaderQos", e, false));
        }
        assertTrue("Check for valid DataReaderQos object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.core.QosProvider#getDataReaderQos(java.lang.String)}.
     */
    //@tTest
    public void testGetDataReaderQosString() {
        checkValidEntities();
        DataReaderQos result = null;
        try {
            result = qosProvider.getDataReaderQos("Foo");
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetDataReaderQosString", e, false));
        }
        assertTrue("Check for valid DataReaderQos object failed", result == null);
    }

    /**
     * Test method for {@link org.omg.dds.core.QosProvider#getDataWriterQos()}.
     */
    //@tTest
    public void testGetDataWriterQos() {
        checkValidEntities();
        DataWriterQos result = null;
        try {
            result = qosProvider.getDataWriterQos();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetDataWriterQos", e, false));
        }
        assertTrue("Check for valid DataWriterQos object failed", result != null);
    }

    /**
     * Test method for {@link org.omg.dds.core.QosProvider#getDataWriterQos(java.lang.String)}.
     */
    //@tTest
    public void testGetDataWriterQosString() {
        checkValidEntities();
        DataWriterQos result = null;
        try {
            result = qosProvider.getDataWriterQos("Foo");
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetDataWriterQosString", e, false));
        }
        assertTrue("Check for valid DataWriterQos object failed", result == null);
    }

    /**
     * Test method for {@link org.omg.dds.core.DDSObject#getEnvironment()}.
     */
    //@tTest
    public void testGetEnvironment() {
        checkValidEntities();
        ServiceEnvironment senv = null;
        try {
            senv = qosProvider.getEnvironment();
        } catch (Exception e) {
            fail("Exception occured while calling getEnvironment(): " + util.printException(e));
        }
        assertTrue("Check for valid ServiceEnvironment", senv != null);
    }

}
