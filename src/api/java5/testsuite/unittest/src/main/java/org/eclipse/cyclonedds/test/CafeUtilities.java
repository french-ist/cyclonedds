/**
 *
 */
package org.eclipse.cyclonedds.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class CafeUtilities extends AbstractUtilities {

    private static CafeUtilities cafeUtility        = null;
    private final String         notImplementedFile = "notImplementedForCafe.txt";
    private final long           writeSleepTime     = 2000;
    /**
     * @param cl
     */

    @SuppressWarnings("rawtypes")
    public static CafeUtilities getInstance(Class cl) {
        if (cafeUtility == null) {
            ClassLoader classLoader = CafeUtilities.class.getClassLoader();
            try {
                Class testClass = classLoader.loadClass(cl.getName() + ".cafe");
                try {
                    cafeUtility = (CafeUtilities) testClass.newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                // System.out.println("Initiated special cafe utilities class for test: "
                // + testClass.getName());
            } catch (ClassNotFoundException e) {
                cafeUtility = new CafeUtilities();
            }
        }
        return cafeUtility;
    }


    public CafeUtilities() {
        URL location = CafeUtilities.class.getProtectionDomain().getCodeSource().getLocation();
        String resourcesPath = location.getPath() + "../../src/test/resources/";
        BufferedReader reader = null;
        boolean finished = false;
        try {
            reader = new BufferedReader(new FileReader(resourcesPath + notImplementedFile));
        } catch (FileNotFoundException e) {
            finished = true;
        }
        if (!finished) {
            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (!line.equals("")) {
                        notImplementedFunctions.add(line);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public boolean beforeClass(Properties prop) {
        return true;
    }

    @Override
    public boolean afterClass(Properties prop) {
        cafeUtility = null;
        return super.afterClass(prop);
    }

    @Override
    public boolean beforeTest(Properties prop) {
        return true;
    }

    @Override
    public boolean afterTest(Properties prop) {
        return true;
    }

    @Override
    public long getWriteSleepTime() {
        return writeSleepTime;
    }

}
