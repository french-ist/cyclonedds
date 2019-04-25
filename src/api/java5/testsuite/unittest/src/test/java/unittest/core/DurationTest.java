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
import org.omg.dds.core.ServiceEnvironment;

import org.eclipse.cyclonedds.test.AbstractUtilities;

public class DurationTest {

    private static ServiceEnvironment env;
    private static AbstractUtilities  util        = AbstractUtilities.getInstance(DurationTest.class);


    @BeforeClass
    public static void init() {
        try {
            String serviceEnvironment = System.getProperty("JAVA5PSM_SERVICE_ENV");
            assertTrue("Check for valid ServiceEnvironment string", !serviceEnvironment.equals(""));
            System.setProperty(ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY, serviceEnvironment);
            env = ServiceEnvironment.createInstance(DurationTest.class.getClassLoader());
            assertTrue("Check for valid ServiceEnvironment object failed", env instanceof ServiceEnvironment);
        } catch (Exception e) {
            fail("Exception occured while initiating the DurationTest class: " + util.printException(e));
        }

    }

    private void checkValidEntities() {
        assertTrue("Check for valid ServiceEnvironment object failed", env instanceof ServiceEnvironment);
    }

    @AfterClass
    public static void cleanup() {

    }

    /**
     * Test method for
     * {@link org.omg.dds.core.Duration#newDuration(long, java.util.concurrent.TimeUnit, org.omg.dds.core.ServiceEnvironment)}
     * .
     */
    @Test
    public void testNewDuration() {
        checkValidEntities();
        Duration dur = null;
        try {
            dur = Duration.newDuration(5, TimeUnit.SECONDS, env);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testNewDuration", e, false));
        }
        assertTrue("Check for valid Duration object failed", dur != null);
    }

    /**
     * Test method for {@link org.omg.dds.core.Duration#newDuration(long, java.util.concurrent.TimeUnit, org.omg.dds.core.ServiceEnvironment)}.
     */
    @Test
    public void testNewDurationNull() {
        checkValidEntities();
        Duration dur = null;
        try {
            dur = Duration.newDuration(5, TimeUnit.SECONDS, null);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testNewDurationNull", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for invalid Duration object failed", dur == null);

        try {
            dur = Duration.newDuration(5, null, env);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testNewDurationNull", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for invalid Duration object failed", dur == null);
    }


    /**
     * Test method for {@link org.omg.dds.core.Duration#infiniteDuration(org.omg.dds.core.ServiceEnvironment)}.
     */
    @Test
    public void testInfiniteDuration() {
        checkValidEntities();
        Duration dur = null;
        try {
            dur = Duration.infiniteDuration(env);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testInfiniteDuration", e, false));
        }
        assertTrue("Check for valid Duration object failed", dur.isInfinite());
    }

    /**
     * Test method for
     * {@link org.omg.dds.core.Duration#infiniteDuration(org.omg.dds.core.ServiceEnvironment)}
     * .
     */
    @Test
    public void testInfiniteDurationNull() {
        checkValidEntities();
        Duration dur = null;
        try {
            dur = Duration.infiniteDuration(null);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testInfiniteDurationNull", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for invalid Duration object failed", dur == null);
    }

    /**
     * Test method for
     * {@link org.omg.dds.core.Duration#zeroDuration(org.omg.dds.core.ServiceEnvironment)}
     * .
     */
    @Test
    public void testZeroDuration() {
        checkValidEntities();
        Duration dur = null;
        try {
            dur = Duration.zeroDuration(env);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testZeroDuration", e, false));
        }
        assertTrue("Check for valid Duration object failed", dur.isZero());
    }

    /**
     * Test method for
     * {@link org.omg.dds.core.Duration#zeroDuration(org.omg.dds.core.ServiceEnvironment)}
     * .
     */
    @Test
    public void testZeroDurationNull() {
        checkValidEntities();
        Duration dur = null;
        try {
            dur = Duration.zeroDuration(null);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testZeroDurationNull", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for invalid Duration object failed", dur == null);
    }

    /**
     * Test method for {@link org.omg.dds.core.Duration#getDuration(java.util.concurrent.TimeUnit)}.
     */
    @Test
    public void testGetDuration() {
        checkValidEntities();
        Duration dur = null;
        long time = 0;
        try {
            dur = Duration.newDuration(5, TimeUnit.SECONDS, env);
            time = dur.getDuration(TimeUnit.SECONDS);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetDuration", e, false));
        }
        assertTrue("Check for valid time value failed", time == 5);
    }

    /**
     * Test method for
     * {@link org.omg.dds.core.Duration#getDuration(java.util.concurrent.TimeUnit)}
     * .
     */
    @Test
    public void testGetDurationNull() {
        checkValidEntities();
        Duration dur = null;
        long time = -1;
        try {
            dur = Duration.newDuration(5, TimeUnit.SECONDS, env);
            time = dur.getDuration(null);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testGetDurationNull", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for invalid time value failed", time == -1);
    }

    /**
     * Test method for {@link org.omg.dds.core.Duration#getRemainder(java.util.concurrent.TimeUnit, java.util.concurrent.TimeUnit)}.
     */
    @Test
    public void testGetRemainder() {
        checkValidEntities();
        Duration dur = null;
        long time = 0;
        try {
            dur = Duration.newDuration(1111, TimeUnit.MILLISECONDS, env);
            time = dur.getRemainder(TimeUnit.SECONDS, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testGetRemainder", e, false));
        }
        assertTrue("Check for valid remainder value failed", time == 111);
    }

    /**
     * Test method for
     * {@link org.omg.dds.core.Duration#getRemainder(java.util.concurrent.TimeUnit, java.util.concurrent.TimeUnit)}
     * .
     */
    @Test
    public void testGetRemainderNullTimeUnit() {
        checkValidEntities();
        Duration dur = null;
        long time = -1;
        try {
            dur = Duration.newDuration(5, TimeUnit.SECONDS, env);
            time = dur.getRemainder(null, TimeUnit.SECONDS);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testGetRemainderNullTimeUnit", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for invalid time value failed", time == -1);
    }

    /**
     * Test method for
     * {@link org.omg.dds.core.Duration#getRemainder(java.util.concurrent.TimeUnit, java.util.concurrent.TimeUnit)}
     * .
     */
    @Test
    public void testGetRemainderTimeUnitNull() {
        checkValidEntities();
        Duration dur = null;
        long time = -1;
        try {
            dur = Duration.newDuration(5, TimeUnit.SECONDS, env);
            time = dur.getRemainder(TimeUnit.SECONDS, null);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testGetRemainderTimeUnitNull", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for invalid time value failed", time == -1);
    }

    /**
     * Test method for {@link org.omg.dds.core.Duration#isZero()}.
     */
    @Test
    public void testIsZero() {
        checkValidEntities();
        Duration dur = null;
        try {
            dur = Duration.newDuration(0, TimeUnit.SECONDS, env);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testIsZero", e, false));
        }
        assertTrue("Check for 0 time value failed", dur.isZero());
    }

    /**
     * Test method for {@link org.omg.dds.core.Duration#isInfinite()}.
     */
    @Test
    public void testIsInfinite() {
        checkValidEntities();
        Duration dur = null;
        try {
            dur = Duration.newDuration(Long.MAX_VALUE, TimeUnit.SECONDS, env);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testIsInfinite", e, false));
        }
        assertTrue("Check for infinite time value failed", dur.isInfinite());
    }

    /**
     * Test method for {@link org.omg.dds.core.Duration#add(org.omg.dds.core.Duration)}.
     */
    @Test
    public void testAddDuration() {
        checkValidEntities();
        Duration dur = null;
        try {
            dur = Duration.newDuration(3, TimeUnit.SECONDS, env);
            dur = dur.add(Duration.newDuration(2, TimeUnit.SECONDS, env));
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testAddDuration", e, false));
        }
        assertTrue("Check for valid time value failed", dur.getDuration(TimeUnit.SECONDS) == 5);
    }

    /**
     * Test method for
     * {@link org.omg.dds.core.Duration#add(org.omg.dds.core.Duration)}.
     */
    @Test
    public void testAddDurationOverflow() {
        checkValidEntities();
        Duration dur = null;
        try {
            dur = Duration.infiniteDuration(env);
            dur = dur.add(Duration.newDuration(5, TimeUnit.SECONDS, env));
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testAddDurationOverflow", e, false));
        }
        assertTrue("Check for valid time value failed", dur.isInfinite());

        try {
            dur = Duration.newDuration(3, TimeUnit.SECONDS, env);
            dur = dur.add(Duration.infiniteDuration(env));
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testAddDurationOverflow", e, false));
        }
        assertTrue("Check for valid time value failed", dur.isInfinite());
    }

    /**
     * Test method for
     * {@link org.omg.dds.core.Duration#add(org.omg.dds.core.Duration)}.
     */
    @Test
    public void testAddDurationNull() {
        checkValidEntities();
        Duration dur = null;
        try {
            dur = Duration.newDuration(3, TimeUnit.SECONDS, env);
            dur = dur.add(null);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testAddDurationNull", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for valid time value failed", dur.getDuration(TimeUnit.SECONDS) == 3);
    }

    /**
     * Test method for
     * {@link org.omg.dds.core.Duration#add(long, java.util.concurrent.TimeUnit)}
     * .
     */
    @Test
    public void testAddLongTimeUnit() {
        checkValidEntities();
        Duration dur = null;
        try {
            dur = Duration.newDuration(3, TimeUnit.SECONDS, env);
            dur = dur.add(2, TimeUnit.SECONDS);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testAddLongTimeUnit", e, false));
        }
        assertTrue("Check for valid time value failed", dur.getDuration(TimeUnit.SECONDS) == 5);
    }

    /**
     * Test method for
     * {@link org.omg.dds.core.Duration#add(long, java.util.concurrent.TimeUnit)}
     * .
     */
    @Test
    public void testAddLongTimeUnitNull() {
        checkValidEntities();
        Duration dur = null;
        try {
            dur = Duration.newDuration(3, TimeUnit.SECONDS, env);
            dur = dur.add(0, null);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testAddLongTimeUnitNull", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for valid time value failed", dur.getDuration(TimeUnit.SECONDS) == 3);
    }

    /**
     * Test method for {@link org.omg.dds.core.Duration#subtract(org.omg.dds.core.Duration)}.
     */
    @Test
    public void testSubtractDuration() {
        checkValidEntities();
        Duration dur = null;
        try {
            dur = Duration.newDuration(3, TimeUnit.SECONDS, env);
            dur = dur.subtract(Duration.newDuration(2, TimeUnit.SECONDS, env));
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testSubtractDuration", e, false));
        }
        assertTrue("Check for valid time value failed", dur.getDuration(TimeUnit.SECONDS) == 1);
    }

    /**
     * Test method for
     * {@link org.omg.dds.core.Duration#subtract(org.omg.dds.core.Duration)}.
     */
    @Test
    public void testSubtractDurationOverflow() {
        checkValidEntities();
        Duration dur = null;
        try {
            dur = Duration.newDuration(3, TimeUnit.SECONDS, env);
            dur = dur.subtract(Duration.infiniteDuration(env));
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testSubtractDurationOverflow", e, false));
        }
        assertTrue("Check for valid time value failed", dur.isInfinite());
    }

    /**
     * Test method for
     * {@link org.omg.dds.core.Duration#subtract(org.omg.dds.core.Duration)}.
     */
    @Test
    public void testSubtractDurationInfinite() {
        checkValidEntities();
        Duration dur = null;
        try {
            dur = Duration.infiniteDuration(env);
            dur = dur.subtract(Duration.infiniteDuration(env));
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testSubtractDurationInfinite", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for valid Duration value failed", dur.isInfinite());
    }

    /**
     * Test method for
     * {@link org.omg.dds.core.Duration#subtract(org.omg.dds.core.Duration)}.
     */
    @Test
    public void testSubtractDuration1() {
        checkValidEntities();
        Duration dur = null;
        try {
            dur = Duration.infiniteDuration(env);
            dur = dur.subtract(Duration.newDuration(3, TimeUnit.SECONDS, env));
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testSubtractDuration1", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for valid Duration value failed", dur.isInfinite());
    }

    /**
     * Test method for
     * {@link org.omg.dds.core.Duration#subtract(org.omg.dds.core.Duration)}.
     */
    @Test
    public void testSubtractDurationNull() {
        checkValidEntities();
        Duration dur = null;
        try {
            dur = Duration.newDuration(3, TimeUnit.SECONDS, env);
            dur = dur.subtract(null);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testSubtractDurationNull", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for valid time value failed", dur.getDuration(TimeUnit.SECONDS) == 3);
    }

    /**
     * Test method for {@link org.omg.dds.core.Duration#subtract(long, java.util.concurrent.TimeUnit)}.
     */
    @Test
    public void testSubtractLongTimeUnit() {
        checkValidEntities();
        Duration dur = null;
        try {
            dur = Duration.newDuration(3, TimeUnit.SECONDS, env);
            dur = dur.subtract(2, TimeUnit.SECONDS);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testSubtractLongTimeUnit", e, false));
        }
        assertTrue("Check for valid time value failed", dur.getDuration(TimeUnit.SECONDS) == 1);
    }

    /**
     * Test method for
     * {@link org.omg.dds.core.Duration#subtract(long, java.util.concurrent.TimeUnit)}
     * .
     */
    @Test
    public void testSubtractLongTimeUnitNull() {
        checkValidEntities();
        Duration dur = null;
        try {
            dur = Duration.newDuration(3, TimeUnit.SECONDS, env);
            dur = dur.subtract(2, null);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testSubtractLongTimeUnitNull", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for valid time value failed", dur.getDuration(TimeUnit.SECONDS) == 3);
    }

}
