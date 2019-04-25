/**
 *
 */
package unittest.core;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.dds.core.ServiceConfigurationException;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.ServiceEnvironment.ServiceProviderInterface;

import org.eclipse.cyclonedds.test.AbstractUtilities;


public class ServiceEnvironmentTest {

    private static AbstractUtilities util = AbstractUtilities.getInstance(ServiceEnvironmentTest.class);
    private static ServiceEnvironment env;

    @BeforeClass
    public static void init() {
        try {
            String serviceEnvironment = System.getProperty("JAVA5PSM_SERVICE_ENV");
            assertTrue("Check for valid ServiceEnvironment string", !serviceEnvironment.equals(""));
            System.setProperty(ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY, serviceEnvironment);
            env = ServiceEnvironment.createInstance(ServiceEnvironmentTest.class.getClassLoader());
            assertTrue("Check for valid ServiceEnvironment object", env instanceof ServiceEnvironment);
        } catch (Exception e) {
            fail("Exception occured while initiating the TopicTest class: " + util.printException(e));
        }

    }

    private void checkValidEntities() {
        assertTrue("Check for valid ServiceEnvironment object", env instanceof ServiceEnvironment);
    }

    /**
     * Test method for
     * {@link org.omg.dds.core.ServiceEnvironment#createInstance(java.lang.String, java.util.Map, java.lang.ClassLoader)}
     * .
     */
    //@tTest
    public void testCreateInstanceStringErrorMapOfStringObjectClassLoader() {
        ServiceEnvironment senv = null;
        boolean exceptionOccured = false;
        try {
            senv = ServiceEnvironment.createInstance("Foo", null, ServiceEnvironmentTest.class.getClassLoader());

        } catch (Exception e) {
            assertTrue("Check for ServiceConfigurationException but got exception:" + util.printException(e), e instanceof ServiceConfigurationException);
            exceptionOccured = true;
        }
        assertTrue("Check if ServiceConfigurationException occured failed", exceptionOccured);
        try {
            System.setProperty(ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY, "FooBar");
            senv = ServiceEnvironment.createInstance("FooBar", null, ServiceEnvironmentTest.class.getClassLoader());
        } catch (Exception e) {
            assertTrue("Check for ServiceConfigurationException but got exception:" + util.printException(e), e instanceof ServiceConfigurationException);
            exceptionOccured = true;
        }
        assertTrue("Check if ServiceConfigurationException occured failed", exceptionOccured);
        assertTrue("check for invalid ServiceEnvironment failed ", senv == null);
    }

    /**
     * Test method for
     * {@link org.omg.dds.core.ServiceEnvironment#createInstance(java.lang.String, java.util.Map, java.lang.ClassLoader)}
     * .
     */
    //@tTest
    public void testCreateInstanceStringMapOfStringObjectClassLoader() {
        String serviceEnv = System.getProperty("JAVA5PSM_SERVICE_ENV");
        assertTrue("Check for valid ServiceEnvironment string", !serviceEnv.equals(""));
        System.setProperty(ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY, serviceEnv);
        ServiceEnvironment senv = null;
        try {
            senv = ServiceEnvironment.createInstance(null);
            assertTrue("Check for valid ServiceEnvironment object", senv instanceof ServiceEnvironment);
        }
        catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testCreateInstanceStringMapOfStringObjectClassLoader", e, false));
        }
        assertTrue("Check for valid ServiceEnvironment object", util.objectCheck("testCreateInstanceStringMapOfStringObjectClassLoader", senv));

    }

    /**
     * Test method for {@link org.omg.dds.core.ServiceEnvironment#getSPI()}.
     */
    //@tTest
    public void testGetSPI() {
        checkValidEntities();
        ServiceProviderInterface result = null;
        try {
            result = env.getSPI();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetSPI", e, false));
        }
        assertTrue("Check for valid ServiceProviderInterface object", util.objectCheck("testGetSPI", result));
    }

    /**
     * Test method for {@link org.omg.dds.core.ServiceEnvironment#getEnvironment()}.
     */
    //@tTest
    public void testGetEnvironment() {
        checkValidEntities();
        ServiceEnvironment senv = null;
        try {
            senv = env.getEnvironment();
        } catch (Exception e) {
            fail("Exception occured while calling getEnvironment(): " + util.printException(e));
        }
        assertTrue("Check for valid ServiceEnvironment", senv != null);
    }

}
