/**
 *
 */
package unittest.core;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.Time;

import org.eclipse.cyclonedds.test.AbstractUtilities;

public class TimeTest {

    private static ServiceEnvironment env;
    private static AbstractUtilities  util = AbstractUtilities.getInstance(TimeTest.class);

    @BeforeClass
    public static void init() {
        try {
            String serviceEnvironment = System.getProperty("JAVA5PSM_SERVICE_ENV");
            assertTrue("Check for valid ServiceEnvironment string", !serviceEnvironment.equals(""));
            System.setProperty(ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY, serviceEnvironment);
            env = ServiceEnvironment.createInstance(TimeTest.class.getClassLoader());
            assertTrue("Check for valid ServiceEnvironment object failed", env instanceof ServiceEnvironment);
        } catch (Exception e) {
            fail("Exception occured while initiating the TimeTest class: " + util.printException(e));
        }

    }

    private void checkValidEntities() {
        assertTrue("Check for valid ServiceEnvironment object failed", env instanceof ServiceEnvironment);
    }

    @AfterClass
    public static void cleanup() {

    }

    /**
     * Test method for {@link org.omg.dds.core.Time#newTime(long, java.util.concurrent.TimeUnit, org.omg.dds.core.ServiceEnvironment)}.
     */
    //@tTest
    public void testNewTime() {
        checkValidEntities();
        Time t = null;
        try {
            t = Time.newTime(5, TimeUnit.SECONDS, env);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testNewTime", e, false));
        }
        assertTrue("Check for valid Time object failed", t != null);
    }

    /**
     * Test method for
     * {@link org.omg.dds.core.Time#newTime(long, java.util.concurrent.TimeUnit, org.omg.dds.core.ServiceEnvironment)}
     * .
     */
    //@tTest
    public void testNewTimeNull() {
        checkValidEntities();
        Time t = null;
        try {
            t = Time.newTime(5, TimeUnit.SECONDS, null);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testNewTimeNull", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for invalid Duration object failed", t == null);

        try {
            t = Time.newTime(5, null, env);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testNewTimeNull", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for invalid Duration object failed", t == null);
    }

    /**
     * Test method for {@link org.omg.dds.core.Time#invalidTime(org.omg.dds.core.ServiceEnvironment)}.
     */
    //@tTest
    public void testInvalidTime() {
        checkValidEntities();
        Time t = null;
        try {
            t = Time.invalidTime(env);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testInvalidTime", e, false));
        }
        assertFalse("Check for invalid Time object failed", t.isValid());
    }

    /**
     * Test method for
     * {@link org.omg.dds.core.Time#invalidTime(org.omg.dds.core.ServiceEnvironment)}
     * .
     */
    //@tTest
    public void testInvalidTimeNull() {
        checkValidEntities();
        Time t = null;
        try {
            t = Time.invalidTime(null);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testInvalidTimeNull", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for invalid Duration object failed", t == null);
    }

    /**
     * Test method for
     * {@link org.omg.dds.core.Time#getTime(java.util.concurrent.TimeUnit)}.
     */
    //@tTest
    public void testGetTime() {
        checkValidEntities();
        Time t = null;
        long time = 0;
        try {
            t = Time.newTime(5, TimeUnit.SECONDS, env);
            time = t.getTime(TimeUnit.SECONDS);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetTime", e, false));
        }
        assertTrue("Check for valid Time value failed", time == 5);
    }

    /**
     * Test method for
     * {@link org.omg.dds.core.Time#getTime(java.util.concurrent.TimeUnit)}.
     */
    //@tTest
    public void testGetTimeNull() {
        checkValidEntities();
        Time t = null;
        long time = -1;
        try {
            t = Time.newTime(5, TimeUnit.SECONDS, env);
            time = t.getTime(null);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testInvalidTimeNull", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for invalid Time value object failed", time == -1);
    }

    /**
     * Test method for {@link org.omg.dds.core.Time#getRemainder(java.util.concurrent.TimeUnit, java.util.concurrent.TimeUnit)}.
     */
    //@tTest
    public void testGetRemainder() {
        checkValidEntities();
        Time t = null;
        long time = 0;
        try {
            /* 1.111 seconds */
            t = Time.newTime(1111, TimeUnit.MILLISECONDS, env);
            time = t.getRemainder(TimeUnit.SECONDS, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetRemainder", e, false));
        }
        assertTrue("Check for valid time value failed expected 111 milliseconds but got: " + time, time == 111);
    }

    /**
     * Test method for
     * {@link org.omg.dds.core.Time#getRemainder(java.util.concurrent.TimeUnit, java.util.concurrent.TimeUnit)}
     * .
     */
    //@tTest
    public void testGetRemainderNullTimeUnit() {
        checkValidEntities();
        Time t = null;
        long time = -1;
        try {
            t = Time.newTime(5, TimeUnit.SECONDS, env);
            time = t.getRemainder(null, TimeUnit.SECONDS);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testGetRemainderNullTimeUnit", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for invalid time value failed", time == -1);
    }

    /**
     * Test method for
     * {@link org.omg.dds.core.Time#getRemainder(java.util.concurrent.TimeUnit, java.util.concurrent.TimeUnit)}
     * .
     */
    //@tTest
    public void testGetRemainderTimeUnitNull() {
        checkValidEntities();
        Time t = null;
        long time = -1;
        try {
            t = Time.newTime(5, TimeUnit.SECONDS, env);
            time = t.getRemainder(TimeUnit.SECONDS, null);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testGetRemainderTimeUnitNull", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for invalid time value failed", time == -1);
    }

    /**
     * Test method for {@link org.omg.dds.core.Time#isValid()}.
     */
    //@tTest
    public void testIsValid() {
        checkValidEntities();
        Time t = null;
        try {
            t = Time.newTime(5, TimeUnit.SECONDS, env);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testIsValid", e, false));
        }
        assertTrue("Check for valid Time object failed", t.isValid());
    }

    /**
     * Test method for {@link org.omg.dds.core.Time#modifiableCopy()}.
     */
    //@tTest
    public void testModifiableCopy() {
        checkValidEntities();
        Time t = null;
        Time copy = null;
        try {
            t = Time.newTime(5, TimeUnit.SECONDS, env);
            copy = t.modifiableCopy();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testModifiableCopy", e, false));
        }
        assertTrue("Check for valid Time object failed", copy.isValid());
    }



}
