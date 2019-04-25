/**
 *
 */
package unittest.core;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.dds.core.Duration;
import org.omg.dds.core.ModifiableTime;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.Time;
import org.opensplice.dds.core.AlreadyClosedExceptionImpl;
import org.opensplice.dds.core.DDSExceptionImpl;
import org.opensplice.dds.core.IllegalArgumentExceptionImpl;
import org.opensplice.dds.core.IllegalOperationExceptionImpl;
import org.opensplice.dds.core.ImmutablePolicyExceptionImpl;
import org.opensplice.dds.core.InconsistentPolicyExceptionImpl;
import org.opensplice.dds.core.NotEnabledExceptionImpl;
import org.opensplice.dds.core.OsplServiceEnvironment;
import org.opensplice.dds.core.OutOfResourcesExceptionImpl;
import org.opensplice.dds.core.PreconditionNotMetExceptionImpl;
import org.opensplice.dds.core.TimeOutExceptionImpl;
import org.opensplice.dds.core.UnsupportedOperationExceptionImpl;
import org.opensplice.dds.core.Utilities;

import DDS.Duration_t;
import DDS.Time_t;

import org.eclipse.cyclonedds.test.AbstractUtilities;

public class UtilitiesopenspliceTest {

    private static ServiceEnvironment env;
    private static AbstractUtilities  util = AbstractUtilities.getInstance(UtilitiesopenspliceTest.class);
    int                               RETCODE_OK                   = 0;
    int                               RETCODE_ERROR                = 1;
    int                               RETCODE_UNSUPPORTED          = 2;
    int                               RETCODE_BAD_PARAMETER        = 3;
    int                               RETCODE_PRECONDITION_NOT_MET = 4;
    int                               RETCODE_OUT_OF_RESOURCES     = 5;
    int                               RETCODE_NOT_ENABLED          = 6;
    int                               RETCODE_IMMUTABLE_POLICY     = 7;
    int                               RETCODE_INCONSISTENT_POLICY  = 8;
    int                               RETCODE_ALREADY_DELETED      = 9;
    int                               RETCODE_TIMEOUT              = 10;
    int                               RETCODE_NO_DATA              = 11;
    int                               RETCODE_ILLEGAL_OPERATION    = 12;

    @BeforeClass
    public static void init() {
        try {
            String serviceEnvironment = System.getProperty("JAVA5PSM_SERVICE_ENV");
            assertTrue("Check for valid ServiceEnvironment string", !serviceEnvironment.equals(""));
            System.setProperty(ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY, serviceEnvironment);
            env = ServiceEnvironment.createInstance(UtilitiesopenspliceTest.class.getClassLoader());
            assertTrue("Check for valid ServiceEnvironment object failed", env instanceof ServiceEnvironment);

        } catch (Exception e) {
            fail("Exception occured while initiating the UtilitiesTest class: " + util.printException(e));
        }

    }

    private void checkValidEntities() {
        assertTrue("Check for valid ServiceEnvironment object failed", env instanceof ServiceEnvironment);
    }

    @AfterClass
    public static void cleanup() {

    }

    /**
     * Test method for {@link org.opensplice.dds.core.Utilities#Utilities()}.
     */
    @Test
    public void testUtilities() {
        checkValidEntities();
        Utilities ut = null;
        try {
            ut = new Utilities();
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck("testUtilities", e, false));
        }
        assertTrue("Check for valid Utilities object failed", ut != null);
    }

    /**
     * Test method for {@link org.opensplice.dds.core.Utilities#checkReturnCode(int, org.opensplice.dds.core.OsplServiceEnvironment, java.lang.String)}.
     */
    @Test
    public void testCheckReturnCode() {
        checkValidEntities();
        String function = "testCheckReturnCode";
        String message = "";
        boolean exceptionExpected = false;
        /* ignore System.err for stacktraceprints */
        PipedOutputStream pipeOut = new PipedOutputStream();
        System.setErr(new PrintStream(pipeOut));

        try {
            Utilities.checkReturnCode(RETCODE_OK, (OsplServiceEnvironment) env, message);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck(function, e, false));
        }
        try {
            Utilities.checkReturnCode(RETCODE_NO_DATA, (OsplServiceEnvironment) env, message);
        } catch (Exception e) {
            assertTrue("No exception expected but got: " + util.printException(e), util.exceptionCheck(function, e, false));
        }
        try {
            Utilities.checkReturnCode(RETCODE_ERROR, (OsplServiceEnvironment) env, message);
        } catch (DDSExceptionImpl e) {
            assertTrue("DDSExceptionImpl expected but got: " + util.printException(e), util.exceptionCheck(function, e, e instanceof DDSExceptionImpl));
            exceptionExpected = true;
            ServiceEnvironment senv = e.getEnvironment();
            assertTrue("Check if for valid ServiceEnvironment object", senv != null);
            e.printStackTrace();
        }
        assertTrue("Check if DDSExceptionImpl occured", exceptionExpected);
        exceptionExpected = false;
        try {
            Utilities.checkReturnCode(RETCODE_BAD_PARAMETER, (OsplServiceEnvironment) env, message);
        } catch (IllegalArgumentExceptionImpl e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck(function, e, e instanceof IllegalArgumentException));
            exceptionExpected = true;
            ServiceEnvironment senv = e.getEnvironment();
            assertTrue("Check if for valid ServiceEnvironment object", senv != null);
            e.printStackTrace();
        }
        assertTrue("Check if IllegalArgumentException occured", exceptionExpected);
        exceptionExpected = false;

        try {
            Utilities.checkReturnCodeWithTimeout(RETCODE_TIMEOUT, (OsplServiceEnvironment) env, message);
        } catch (Exception e) {
            assertTrue("TimeOutException expected but got: " + util.printException(e), util.exceptionCheck(function, e, e instanceof TimeOutExceptionImpl));
            exceptionExpected = true;
            ServiceEnvironment senv = ((TimeOutExceptionImpl) e).getEnvironment();
            assertTrue("Check if for valid ServiceEnvironment object", senv != null);
            e.printStackTrace();

        }
        assertTrue("Check if TimeOutException occured", exceptionExpected);
        exceptionExpected = false;

        try {
            Utilities.checkReturnCode(RETCODE_UNSUPPORTED, (OsplServiceEnvironment) env, message);
        } catch (UnsupportedOperationExceptionImpl e) {
            assertTrue("UnsupportedOperationExceptionImpl expected but got: " + util.printException(e), util.exceptionCheck(function, e, e instanceof UnsupportedOperationExceptionImpl));
            exceptionExpected = true;
            ServiceEnvironment senv = e.getEnvironment();
            assertTrue("Check if for valid ServiceEnvironment object", senv != null);
            e.printStackTrace();
        }
        assertTrue("Check if UnsupportedOperationExceptionImpl occured", exceptionExpected);
        exceptionExpected = false;

        try {
            Utilities.checkReturnCode(RETCODE_ALREADY_DELETED, (OsplServiceEnvironment) env, message);
        } catch (AlreadyClosedExceptionImpl e) {
            assertTrue("AlreadyClosedExceptionImpl expected but got: " + util.printException(e), util.exceptionCheck(function, e, e instanceof AlreadyClosedExceptionImpl));
            exceptionExpected = true;
            ServiceEnvironment senv = e.getEnvironment();
            assertTrue("Check if for valid ServiceEnvironment object", senv != null);
            e.printStackTrace();
        }
        assertTrue("Check if AlreadyClosedExceptionImpl occured", exceptionExpected);
        exceptionExpected = false;

        try {
            Utilities.checkReturnCode(RETCODE_ILLEGAL_OPERATION, (OsplServiceEnvironment) env, message);
        } catch (IllegalOperationExceptionImpl e) {
            assertTrue("IllegalOperationExceptionImpl expected but got: " + util.printException(e), util.exceptionCheck(function, e, e instanceof IllegalOperationExceptionImpl));
            exceptionExpected = true;
            ServiceEnvironment senv = e.getEnvironment();
            assertTrue("Check if for valid ServiceEnvironment object", senv != null);
            e.printStackTrace();
        }
        assertTrue("Check if IllegalOperationExceptionImpl occured", exceptionExpected);
        exceptionExpected = false;

        try {
            Utilities.checkReturnCode(RETCODE_NOT_ENABLED, (OsplServiceEnvironment) env, message);
        } catch (NotEnabledExceptionImpl e) {
            assertTrue("NotEnabledExceptionImpl expected but got: " + util.printException(e), util.exceptionCheck(function, e, e instanceof NotEnabledExceptionImpl));
            exceptionExpected = true;
            ServiceEnvironment senv = e.getEnvironment();
            assertTrue("Check if for valid ServiceEnvironment object", senv != null);
            e.printStackTrace();
        }
        assertTrue("Check if NotEnabledExceptionImpl occured", exceptionExpected);
        exceptionExpected = false;

        try {
            Utilities.checkReturnCode(RETCODE_PRECONDITION_NOT_MET, (OsplServiceEnvironment) env, message);
        } catch (PreconditionNotMetExceptionImpl e) {
            assertTrue("PreconditionNotMetExceptionImpl expected but got: " + util.printException(e), util.exceptionCheck(function, e, e instanceof PreconditionNotMetExceptionImpl));
            exceptionExpected = true;
            ServiceEnvironment senv = e.getEnvironment();
            assertTrue("Check if for valid ServiceEnvironment object", senv != null);
            e.printStackTrace();
        }
        assertTrue("Check if PreconditionNotMetExceptionImpl occured", exceptionExpected);
        exceptionExpected = false;

        try {
            Utilities.checkReturnCode(RETCODE_IMMUTABLE_POLICY, (OsplServiceEnvironment) env, message);
        } catch (ImmutablePolicyExceptionImpl e) {
            assertTrue("ImmutablePolicyExceptionImpl expected but got: " + util.printException(e), util.exceptionCheck(function, e, e instanceof ImmutablePolicyExceptionImpl));
            exceptionExpected = true;
            ServiceEnvironment senv = e.getEnvironment();
            assertTrue("Check if for valid ServiceEnvironment object", senv != null);
            e.printStackTrace();
        }
        assertTrue("Check if ImmutablePolicyExceptionImpl occured", exceptionExpected);
        exceptionExpected = false;

        try {
            Utilities.checkReturnCode(RETCODE_INCONSISTENT_POLICY, (OsplServiceEnvironment) env, message);
        } catch (InconsistentPolicyExceptionImpl e) {
            assertTrue("InconsistentPolicyExceptionImpl expected but got: " + util.printException(e), util.exceptionCheck(function, e, e instanceof InconsistentPolicyExceptionImpl));
            exceptionExpected = true;
            ServiceEnvironment senv = e.getEnvironment();
            assertTrue("Check if for valid ServiceEnvironment object", senv != null);
            e.printStackTrace();
        }
        assertTrue("Check if InconsistentPolicyExceptionImpl occured", exceptionExpected);
        exceptionExpected = false;

        try {
            Utilities.checkReturnCode(RETCODE_OUT_OF_RESOURCES, (OsplServiceEnvironment) env, message);
        } catch (OutOfResourcesExceptionImpl e) {
            assertTrue("OutOfResourcesExceptionImpl expected but got: " + util.printException(e), util.exceptionCheck(function, e, e instanceof OutOfResourcesExceptionImpl));
            exceptionExpected = true;
            ServiceEnvironment senv = e.getEnvironment();
            assertTrue("Check if for valid ServiceEnvironment object", senv != null);
            e.printStackTrace();
        }
        assertTrue("Check if OutOfResourcesExceptionImpl occured", exceptionExpected);
        exceptionExpected = false;


        /* restore System.err stream */
        System.setErr(System.err);
    }

    /**
     * Test method for {@link org.opensplice.dds.core.Utilities#convert(org.opensplice.dds.core.OsplServiceEnvironment, org.omg.dds.core.Duration)}.
     */
    @Test
    public void testConvertOsplServiceEnvironmentDuration() {
        checkValidEntities();
        Duration_t result = null;
        @SuppressWarnings("serial")
        Duration d = new Duration() {

            @Override
            public ServiceEnvironment getEnvironment() {

                return null;
            }

            @Override
            public int compareTo(Duration arg0) {

                return 0;
            }

            @Override
            public Duration subtract(long duration, TimeUnit unit) {

                return null;
            }

            @Override
            public Duration subtract(Duration duration) {

                return null;
            }

            @Override
            public boolean isZero() {

                return false;
            }

            @Override
            public boolean isInfinite() {

                return false;
            }

            @Override
            public long getRemainder(TimeUnit primaryUnit, TimeUnit remainderUnit) {

                return 0;
            }

            @Override
            public long getDuration(TimeUnit inThisUnit) {

                return 0;
            }

            @Override
            public Duration add(long duration, TimeUnit unit) {

                return null;
            }

            @Override
            public Duration add(Duration duration) {

                return null;
            }
        };
        try {
            result = Utilities.convert((OsplServiceEnvironment) env, d);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e),
                    util.exceptionCheck("testConvertOsplServiceEnvironmentDuration", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for invalid Duration object failed", result == null);
    }

    /**
     * Test method for {@link org.opensplice.dds.core.Utilities#convert(org.opensplice.dds.core.OsplServiceEnvironment, org.omg.dds.core.Time)}.
     */
    @Test
    public void testConvertOsplServiceEnvironmentTime() {
        checkValidEntities();
        Time_t result = null;
        @SuppressWarnings("serial")
        Time t = new Time() {

            @Override
            public ServiceEnvironment getEnvironment() {

                return null;
            }

            @Override
            public int compareTo(Time o) {

                return 0;
            }

            @Override
            public ModifiableTime modifiableCopy() {

                return null;
            }

            @Override
            public boolean isValid() {

                return false;
            }

            @Override
            public long getTime(TimeUnit inThisUnit) {

                return 0;
            }

            @Override
            public long getRemainder(TimeUnit primaryUnit, TimeUnit remainderUnit) {

                return 0;
            }
        };
        try {
            result = Utilities.convert((OsplServiceEnvironment) env, t);
        } catch (Exception e) {
            assertTrue("IllegalArgumentException expected but got: " + util.printException(e), util.exceptionCheck("testConvertOsplServiceEnvironmentTime", e, e instanceof IllegalArgumentException));
        }
        assertTrue("Check for invalid Time object failed", result == null);
    }

}
