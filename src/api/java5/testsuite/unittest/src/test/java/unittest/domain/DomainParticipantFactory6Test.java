/**
 *
 */
package unittest.domain;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.dds.core.IllegalOperationException;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.policy.EntityFactory;
import org.omg.dds.core.policy.PolicyFactory;
import org.omg.dds.core.policy.QosPolicy.ForDomainParticipantFactory;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.domain.DomainParticipantFactory;
import org.omg.dds.domain.DomainParticipantFactoryQos;
import org.omg.dds.domain.DomainParticipantQos;

import org.eclipse.cyclonedds.test.AbstractUtilities;


public class DomainParticipantFactory6Test {

    private final static int                DOMAIN_ID = 123;
    private static ServiceEnvironment       env;
    private static DomainParticipantFactory dpf;
    private static AbstractUtilities        util        = AbstractUtilities.getInstance(DomainParticipantFactory6Test.class);
    private final static Properties         prop        = new Properties();
    private static DomainParticipant        participant = null;

    @BeforeClass
    public static void init() {
        String serviceEnvironment = System.getProperty("JAVA5PSM_SERVICE_ENV");
        assertTrue("Check for valid ServiceEnvironment string", !serviceEnvironment.equals(""));
        System.setProperty(ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY, serviceEnvironment);
        env = ServiceEnvironment.createInstance(DomainParticipantFactory6Test.class.getClassLoader());
        assertTrue("Check for valid ServiceEnvironment object", env instanceof ServiceEnvironment);
        dpf = DomainParticipantFactory.getInstance(env);
        assertTrue("Check for valid DomainParticipantFactory object", dpf instanceof DomainParticipantFactory);
        prop.setProperty("domainid", new Integer(DOMAIN_ID).toString());
        assertTrue("Check is deamon is correctly started", util.beforeClass(prop));
        participant = dpf.createParticipant(DOMAIN_ID);
        assertTrue("Check for valid DomainParticipant object", participant instanceof DomainParticipant);
        assertTrue("Check for valid DomainParticipant with id " + DOMAIN_ID, participant.getDomainId() == DOMAIN_ID);

    }

    @AfterClass
    public static void cleanup() {
        assertTrue("Check is deamon is correctly stopped", util.afterClass(prop));

    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipantFactory#lookupParticipant(int)}
     */

    //@tTest
    public void testLookupParticipant() {
        DomainParticipant participant1 = null;
        try {
            participant1 = dpf.lookupParticipant(DOMAIN_ID);
        } catch (Exception ex) {
            assertTrue("Catch unsupported operation exception", util.exceptionCheck("testLookupParticipant", ex, ex instanceof UnsupportedOperationException));
        }
        assertTrue("Check for valid DomainParticipant object", util.objectCheck("testLookupParticipant", participant1));
        if (participant1 != null) {
            assertSame("Check for valid DomainParticipant object", participant, participant1);

            /* lookup non existing domain */
            DomainParticipant participant2 = dpf.lookupParticipant(-1);
            assertNotSame("Check for invalid DomainParticipant object", participant, participant2);
            assertNull("Check for null DomainParticipant object", participant2);
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipantFactory#getQos()}.
     */

    //@tTest
    public void testGetQos() {
        try {
            DomainParticipantFactoryQos dpfq = dpf.getQos();
            assertTrue("Check for valid DomainParticipantFactoryQos object", dpfq instanceof DomainParticipantFactoryQos);
            assertTrue("Check for valid DomainParticipantFactoryQos value", dpfq.getEntityFactory().isAutoEnableCreatedEntities());
        } catch (Exception ex) {
            assertTrue("No exception expected but got: " + util.printException(ex), util.exceptionCheck("testGetQos", ex, false));
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipantFactory#setQos(org.omg.dds.domain.DomainParticipantFactoryQos)}
     * .
     */

    //@tTest
    public void testSetQos() {
        DomainParticipantFactoryQos dpfq = null;
        try {
            dpfq = dpf.getQos();
            assertTrue("Check for valid DomainParticipantFactoryQos object", dpfq instanceof DomainParticipantFactoryQos);
            dpfq = dpfq.withPolicy(dpfq.getEntityFactory().withAutoEnableCreatedEntities(false));
            assertFalse("Check for valid DomainParticipantFactoryQos value", dpfq.getEntityFactory().isAutoEnableCreatedEntities());
            dpf.setQos(dpfq);
            dpfq = dpf.getQos();
            assertFalse("Check for valid DomainParticipantFactoryQos value", dpfq.getEntityFactory().isAutoEnableCreatedEntities());
            /* restore qos */
            dpfq = dpfq.withPolicy(dpfq.getEntityFactory().withAutoEnableCreatedEntities(true));
            dpf.setQos(dpfq);
            assertTrue("Check for valid DomainParticipantQos value", dpfq.getEntityFactory().isAutoEnableCreatedEntities());
        } catch (Exception ex) {
            fail("Failed to set the DomainParticipantFactoryQos");
        }
    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipantFactory#setQos(org.omg.dds.domain.DomainParticipantFactoryQos)}
     * .
     */
    //@tTest
    public void testSetQosNull() {
        boolean exceptionOccured = false;
        try {
            dpf.setQos(null);
        } catch (Exception e) {
            assertTrue("Check for IllegalArgumentException but got exception:" + util.printException(e), e instanceof IllegalArgumentException);
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException occured failed", exceptionOccured);
    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipantFactory#setQos(org.omg.dds.domain.DomainParticipantFactoryQos)}
     * .
     */
    //@tTest
    public void testSetQosInvalid() {
        boolean exceptionOccured = false;
        try {
            @SuppressWarnings("serial")
            DomainParticipantFactoryQos dpfq = new DomainParticipantFactoryQos() {

                @Override
                public ServiceEnvironment getEnvironment() {

                    return null;
                }

                @Override
                public Collection<ForDomainParticipantFactory> values() {

                    return null;
                }

                @Override
                public int size() {

                    return 0;
                }

                @Override
                public ForDomainParticipantFactory remove(Object key) {

                    return null;
                }

                @Override
                public void putAll(Map<? extends Class<? extends ForDomainParticipantFactory>, ? extends ForDomainParticipantFactory> m) {


                }

                @Override
                public ForDomainParticipantFactory put(Class<? extends ForDomainParticipantFactory> key, ForDomainParticipantFactory value) {

                    return null;
                }

                @Override
                public Set<Class<? extends ForDomainParticipantFactory>> keySet() {

                    return null;
                }

                @Override
                public boolean isEmpty() {

                    return false;
                }

                @Override
                public ForDomainParticipantFactory get(Object key) {

                    return null;
                }

                @Override
                public Set<Entry<Class<? extends ForDomainParticipantFactory>, ForDomainParticipantFactory>> entrySet() {

                    return null;
                }

                @Override
                public boolean containsValue(Object value) {

                    return false;
                }

                @Override
                public boolean containsKey(Object key) {

                    return false;
                }

                @Override
                public void clear() {


                }

                @Override
                public PolicyFactory getPolicyFactory() {

                    return null;
                }

                @Override
                public <POLICY extends ForDomainParticipantFactory> POLICY get(Class<POLICY> id) {

                    return null;
                }

                @Override
                public DomainParticipantFactoryQos withPolicy(ForDomainParticipantFactory policy) {

                    return null;
                }

                @Override
                public DomainParticipantFactoryQos withPolicies(ForDomainParticipantFactory... policy) {

                    return null;
                }

                @Override
                public EntityFactory getEntityFactory() {

                    return null;
                }
            };
            dpf.setQos(dpfq);
        } catch (Exception e) {
            assertTrue("Check for IllegalOperationException but got exception:" + util.printException(e), e instanceof IllegalOperationException);
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalOperationException occured failed", exceptionOccured);
    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipantFactory#getDefaultParticipantQos()}
     * .
     */

    //@tTest
    public void testGetDefaultParticipantQos() {
        DomainParticipantQos dpq = dpf.getDefaultParticipantQos();
        assertTrue("Check for valid DomainParticipantQos object", dpq instanceof DomainParticipantQos);
        assertTrue("Check for valid DomainParticipantQos value", dpq.getEntityFactory().isAutoEnableCreatedEntities());
    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipantFactory#setDefaultParticipantQos(org.omg.dds.domain.DomainParticipantQos)}
     * .
     */

    //@tTest
    public void testSetDefaultParticipantQosNull() {
        boolean exceptionOccured = false;
        try {
            dpf.setDefaultParticipantQos(null);
        } catch (Exception e) {
            assertTrue("Check for IllegalArgumentException but got exception:" + util.printException(e), e instanceof IllegalArgumentException);
            exceptionOccured = true;
        }
        assertTrue("Check if IllegalArgumentException occured failed", exceptionOccured);
    }

    /**
     * Test method for
     * {@link org.omg.dds.domain.DomainParticipantFactory#setDefaultParticipantQos(org.omg.dds.domain.DomainParticipantQos)}
     * .
     */

    //@tTest
    public void testSetDefaultParticipantQos() {
        DomainParticipantQos dpq = dpf.getDefaultParticipantQos();
        assertTrue("Check for valid DomainParticipantQos object", dpq instanceof DomainParticipantQos);
        try {
            dpq = dpq.withPolicy(dpq.getEntityFactory().withAutoEnableCreatedEntities(false));
            assertFalse("Check for valid DomainParticipantQos value", dpq.getEntityFactory().isAutoEnableCreatedEntities());
            dpf.setDefaultParticipantQos(dpq);
            dpq = dpf.getDefaultParticipantQos();
            assertFalse("Check for valid DomainParticipantQos value", dpq.getEntityFactory().isAutoEnableCreatedEntities());
            /* restore qos */
            dpq = dpq.withPolicy(dpq.getEntityFactory().withAutoEnableCreatedEntities(true));
            dpf.setDefaultParticipantQos(dpq);
            assertTrue("Check for valid DomainParticipantQos value", dpq.getEntityFactory().isAutoEnableCreatedEntities());
        } catch (Exception ex) {
            assertTrue("No exception expected but got: " + util.printException(ex), util.exceptionCheck("testSetDefaultParticipantQos", ex, false));
        }

    }


}
