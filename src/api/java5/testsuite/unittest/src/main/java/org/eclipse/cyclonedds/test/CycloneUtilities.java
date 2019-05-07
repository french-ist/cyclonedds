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

public class CycloneUtilities extends AbstractUtilities {

    private static CycloneUtilities cycloneUtility = null;
    private final String         notImplementedFile = "notImplementedForCyclone.txt";
    private final long           writeSleepTime     = 2000;
    /**
     * @param cl
     */

    @SuppressWarnings("rawtypes")
    public static CycloneUtilities getInstance(Class cl) {
        if (cycloneUtility == null) {
            ClassLoader classLoader = CycloneUtilities.class.getClassLoader();
            try {
                Class testClass = classLoader.loadClass(cl.getName() + ".cyclone");
                try {
                    cycloneUtility = (CycloneUtilities) testClass.newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                // System.out.println("Initiated special cyclone utilities class for test: "
                // + testClass.getName());
            } catch (ClassNotFoundException e) {
                cycloneUtility = new CycloneUtilities();
            }
        }
        return cycloneUtility;
    }


    public CycloneUtilities() {
        URL location = CycloneUtilities.class.getProtectionDomain().getCodeSource().getLocation();
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
        cycloneUtility = null;
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
