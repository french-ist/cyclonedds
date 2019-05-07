package org.eclipse.cyclonedds.test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashSet;
import java.util.Properties;

public abstract class AbstractUtilities {

    private static AbstractUtilities utility = null;
    protected HashSet<String>        notImplementedFunctions = new HashSet<String>();

    public enum Equality {
        LE, /* Less or Equal */
        LT, /* Less */
        EQ, /* Equal */
        GT, /* Greater */
        GE, /* Greater or Equal */
        NE /* Not equal */
    };

    @SuppressWarnings("rawtypes")
    public static AbstractUtilities getInstance(Class cl) {
        String mode = System.getProperty("JAVA5PSM_MODE").trim();
        if (utility == null) {
            if (mode.equals("cyclone")) {
                utility = CycloneUtilities.getInstance(cl);
            }
        }
        return utility;
    }

    public boolean exceptionCheck(String function, Exception e, boolean expected) {
        boolean result = false;
        if (notImplementedFunctions.contains(function)) {
            result = e instanceof java.lang.UnsupportedOperationException;
        } else {
            result = expected;
        }
        return result;
    }

    public boolean objectCheck(String function, Object o) {
        boolean result = false;
        if (notImplementedFunctions.contains(function)) {
            result = true;
        } else {
            result = o != null;
        }
        return result;
    }

    public boolean objectCompareCheck(String function, Object o1, Object o2) {
        boolean result = false;
        if (notImplementedFunctions.contains(function)) {
            result = true;
        } else {
            result = o1 == o2;
        }
        return result;
    }

    public boolean valueCompareCheck(String function, double o1, double o2, Equality eq) {
        boolean result = false;
        if (notImplementedFunctions.contains(function)) {
            result = true;
        } else {
            switch (eq) {
                case LE:
                    result = o1 <= o2;
                    break;
                case LT:
                    result = o1 < o2;
                case EQ:
                    result = o1 == o2;
                    break;
                case GE:
                    result = o1 >= o2;
                    break;
                case GT:
                    result = o1 > o2;
                    break;
                case NE:
                    result = o1 != o2;
                    break;
                default:
                    result = false;
                    break;
            }
        }
        return result;
    }

    public abstract boolean beforeClass(Properties prop);

    public boolean afterClass(Properties prop) {
        utility = null;
        return true;
    }

    public abstract boolean beforeTest(Properties prop);

    public abstract boolean afterTest(Properties prop);

    public String printException(Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

    public abstract long getWriteSleepTime();

}
