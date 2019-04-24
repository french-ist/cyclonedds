/**
 *
 */
package unittest.core;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.dds.core.Duration;
import org.omg.dds.core.ModifiableTime;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.Time;

import org.eclipse.cyclonedds.test.AbstractUtilities;


public class ModifiableTimeTest {

    private static ServiceEnvironment env;
    private static AbstractUtilities  util = AbstractUtilities.getInstance(ModifiableTimeTest.class);

    @BeforeClass
    public static void init() {
        try {
            String serviceEnvironment = System.getProperty("JAVA5PSM_SERVICE_ENV");
            assertTrue("Check for valid ServiceEnvironment string", !serviceEnvironment.equals(""));
            System.setProperty(ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY, serviceEnvironment);
            env = ServiceEnvironment.createInstance(ModifiableTimeTest.class.getClassLoader());
            assertTrue("Check for valid ServiceEnvironment object failed", env instanceof ServiceEnvironment);
        } catch (Exception e) {
            fail("Exception occured while initiating the ModifiableTimeTest class: " + util.printException(e));
        }

    }

    private void checkValidEntities() {
        assertTrue("Check for valid ServiceEnvironment object failed", env instanceof ServiceEnvironment);
    }

    @AfterClass
    public static void cleanup() {

    }


    /**
     * Test method for {@link org.omg.dds.core.ModifiableTime#copyFrom(org.omg.dds.core.Time)}.
     */
    @Test
    public void testCopyFrom() {
        checkValidEntities();
        ModifiableTime t = ModifiableTime.newTime(1, TimeUnit.SECONDS, env);
        ModifiableTime src = ModifiableTime.newTime(3, TimeUnit.SECONDS, env);
        try {
            t.copyFrom(src);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testCopyFrom", e, false));
        }
        assertTrue("Check for valid time value failed", t.getTime(TimeUnit.SECONDS) == 3);
    }

    /**
     * Test method for {@link org.omg.dds.core.ModifiableTime#immutableCopy()}.
     */
    @Test
    public void testImmutableCopy() {
        checkValidEntities();
        ModifiableTime t = ModifiableTime.newTime(1, TimeUnit.SECONDS, env);
        Time time = null;
        try {
            time = t.immutableCopy();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testImmutableCopy", e, false));
        }
        assertTrue("Check for valid time value failed", time.getTime(TimeUnit.SECONDS) == 1);
    }

    /**
     * Test method for {@link org.omg.dds.core.ModifiableTime#setTime(long, java.util.concurrent.TimeUnit)}.
     */
    @Test
    public void testSetTime() {
        checkValidEntities();
        ModifiableTime t = ModifiableTime.newTime(1, TimeUnit.SECONDS, env);
        try {
            t.setTime(2, TimeUnit.SECONDS);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testSetTime", e, false));
        }
        assertTrue("Check for valid time value failed", t.getTime(TimeUnit.SECONDS) == 2);
    }

    /**
     * Test method for
     * {@link org.omg.dds.core.ModifiableTime#setTime(long, java.util.concurrent.TimeUnit)}
     * .
     */
    @Test
    public void testSetTimeNull() {
        checkValidEntities();
        ModifiableTime t = ModifiableTime.newTime(1, TimeUnit.SECONDS, env);
        try {
            t.setTime(2, null);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testSetTimeNull", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for valid ModifiableTime value failed", t.getTime(TimeUnit.SECONDS) == 1);
    }

    /**
     * Test method for
     * {@link org.omg.dds.core.ModifiableTime#add(org.omg.dds.core.Duration)}.
     */
    @Test
    public void testAddDuration() {
        checkValidEntities();
        ModifiableTime t = null;
        try {
            t = ModifiableTime.newTime(3, TimeUnit.SECONDS, env);
            t.add(Duration.newDuration(2, TimeUnit.SECONDS, env));
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testAddTime", e, false));
        }
        assertTrue("Check for valid time value failed", t.getTime(TimeUnit.SECONDS) == 5);
    }

    /**
     * Test method for
     * {@link org.omg.dds.core.ModifiableTime#add(org.omg.dds.core.Duration)} .
     */
    @Test
    public void testAddTimeOverflow() {
        checkValidEntities();
        ModifiableTime t = null;
        try {
            t = ModifiableTime.newTime(5, TimeUnit.SECONDS, env);
            t.add(Duration.infiniteDuration(env));
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testAddTimeOverflow", e, false));
        }
        assertTrue("Check for valid ModifiableTime value failed", t.getTime(TimeUnit.SECONDS) == Long.MAX_VALUE);

        try {
            t = ModifiableTime.newTime(Long.MAX_VALUE, TimeUnit.SECONDS, env);
            t.add(Duration.newDuration(2, TimeUnit.SECONDS, env));
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testAddTimeOverflow", e, false));
        }
        assertTrue("Check for valid ModifiableTime value failed", t.getTime(TimeUnit.SECONDS) == Long.MAX_VALUE);
    }

    /**
     * Test method for
     * {@link org.omg.dds.core.ModifiableTime#add(org.omg.dds.core.Duration)} .
     */
    @Test
    public void testAddTimeNull() {
        checkValidEntities();
        ModifiableTime t = null;
        try {
            t = ModifiableTime.newTime(3, TimeUnit.SECONDS, env);
            t.add(null);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testAddTimeNull", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for valid ModifiableTime value failed", t.getTime(TimeUnit.SECONDS) == 3);
    }

    /**
     * Test method for
     * {@link org.omg.dds.core.ModifiableTime#add(long, java.util.concurrent.TimeUnit)}
     * .
     */
    @Test
    public void testAddLongTimeUnit() {
        checkValidEntities();
        ModifiableTime t = null;
        try {
            t = ModifiableTime.newTime(3, TimeUnit.SECONDS, env);
            t.add(2, TimeUnit.SECONDS);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testAddLongTimeUnit", e, false));
        }
        assertTrue("Check for valid ModifiableTime value failed", t.getTime(TimeUnit.SECONDS) == 5);
    }

    /**
     * Test method for
     * {@link org.omg.dds.core.ModifiableTime#add(long, java.util.concurrent.TimeUnit)}
     * .
     */
    @Test
    public void testAddLongTimeUnitNull() {
        checkValidEntities();
        ModifiableTime t = null;
        try {
            t = ModifiableTime.newTime(3, TimeUnit.SECONDS, env);
            t.add(0, null);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testAddLongTimeUnitNull", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for valid ModifiableTime value failed", t.getTime(TimeUnit.SECONDS) == 3);
    }

    /**
     * Test method for
     * {@link org.omg.dds.core.ModifiableTime#subtract(org.omg.dds.core.Duration)}
     * .
     */
    @Test
    public void testSubtractDuration() {
        checkValidEntities();
        ModifiableTime t = null;
        try {
            t = ModifiableTime.newTime(3, TimeUnit.SECONDS, env);
            t.subtract(Duration.newDuration(2, TimeUnit.SECONDS, env));
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testSubtractDuration", e, false));
        }
        assertTrue("Check for valid ModifiableTime value failed", t.getTime(TimeUnit.SECONDS) == 1);
    }

    /**
     * Test method for
     * {@link org.omg.dds.core.ModifiableTime#subtract(org.omg.dds.core.Duration)}
     * .
     */
    @Test
    public void testSubtractDurationOverflow() {
        checkValidEntities();
        ModifiableTime t = null;
        try {
            t = ModifiableTime.newTime(3, TimeUnit.SECONDS, env);
            t.subtract(Duration.infiniteDuration(env));
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testSubtractDurationOverflow", e, false));
        }
        assertTrue("Check for valid ModifiableTime value failed", t.getTime(TimeUnit.SECONDS) == Long.MAX_VALUE);
    }

    /**
     * Test method for
     * {@link org.omg.dds.core.ModifiableTime#subtract(org.omg.dds.core.Duration)}
     * .
     */
    @Test
    public void testSubtractDurationNull() {
        checkValidEntities();
        ModifiableTime t = null;
        try {
            t = ModifiableTime.newTime(3, TimeUnit.SECONDS, env);
            t.subtract(null);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testSubtractDurationNull", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for valid ModifiableTime value failed", t.getTime(TimeUnit.SECONDS) == 3);
    }

    /**
     * Test method for
     * {@link org.omg.dds.core.ModifiableTime#subtract(long, java.util.concurrent.TimeUnit)}
     * .
     */
    @Test
    public void testSubtractLongTimeUnit() {
        checkValidEntities();
        ModifiableTime t = null;
        try {
            t = ModifiableTime.newTime(3, TimeUnit.SECONDS, env);
            t.subtract(2, TimeUnit.SECONDS);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testSubtractLongTimeUnit", e, false));
        }
        assertTrue("Check for valid ModifiableTime value failed", t.getTime(TimeUnit.SECONDS) == 1);
    }

    /**
     * Test method for
     * {@link org.omg.dds.core.ModifiableTime#subtract(long, java.util.concurrent.TimeUnit)}
     * .
     */
    @Test
    public void testSubtractLongTimeUnitNull() {
        checkValidEntities();
        ModifiableTime t = null;
        try {
            t = ModifiableTime.newTime(3, TimeUnit.SECONDS, env);
            t.subtract(2, null);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testSubtractLongTimeUnitNull", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for valid ModifiableTime value failed", t.getTime(TimeUnit.SECONDS) == 3);
    }

}
