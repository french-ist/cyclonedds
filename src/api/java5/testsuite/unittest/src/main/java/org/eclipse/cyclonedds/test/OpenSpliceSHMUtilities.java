/**
 *
 */
package org.eclipse.cyclonedds.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class OpenSpliceSHMUtilities extends AbstractUtilities {

    private static final long             writeSleepTime        = 0;
    private static OpenSpliceSHMUtilities opensplice_shmUtility = null;
    private final String                  notImplementedFile    = "notImplementedForOpensplice_shm.txt";
    private int                           domainId              = 0;
    private String                        resourcesPath         = "";
    private final String                  uriTemplateName       = "template.xml";
    private final String                  defaultURI            = System.getenv("OSPL_URI");
    private final HashSet<Integer>        runningDomains        = new HashSet<Integer>();
    /**
     * @param cl
     */

    @SuppressWarnings("rawtypes")
    public static OpenSpliceSHMUtilities getInstance(Class cl) {
        if (opensplice_shmUtility == null) {
            ClassLoader classLoader = CafeUtilities.class.getClassLoader();
            try {
                Class testClass = classLoader.loadClass(cl.getName() + ".opensplice_shm");
                try {
                    opensplice_shmUtility = (OpenSpliceSHMUtilities) testClass.newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                // System.out.println("Initiated special opensplice_shm utilities class for test: "
                // + testClass.getName());
            } catch (ClassNotFoundException e) {
                opensplice_shmUtility = new OpenSpliceSHMUtilities();
            }
        }
        return opensplice_shmUtility;
    }

    public OpenSpliceSHMUtilities() {
        URL location = OpenSpliceSHMUtilities.class.getProtectionDomain().getCodeSource().getLocation();
        resourcesPath = location.getPath() + "../../src/test/resources/";
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

    public boolean setURIdomainId(String uriName, int id) {
        boolean result = true;
        try {
            String filepath = uriName != null ? resourcesPath + uriName : resourcesPath + uriTemplateName;
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(filepath);

            Node domainId = doc.getElementsByTagName("Id").item(0);
            domainId.setTextContent(new Integer(id).toString());

            Node singleprocess = doc.getElementsByTagName("SingleProcess").item(0);
            singleprocess.setTextContent("false");

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult sresult = new StreamResult(new File(defaultURI.replace("file://", "")));
            transformer.transform(source, sresult);

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
            result = false;
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
            result = false;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            result = false;
        } catch (SAXException sae) {
            sae.printStackTrace();
            result = false;
        }

        return result;
    }

    public boolean isDomainRunning(int id) {
        boolean result = false;
        if (runningDomains.contains(id)) {
            result = true;
        }
        return result;
    }

    public boolean stopAllDomains() {
        boolean result = true;
        for (Iterator<Integer> i = runningDomains.iterator(); i.hasNext();) {
            Integer id = i.next();
            result = stopOpenSplice(id);
            if (!result) {
                break;
            }
        }
        return result;
    }


    public boolean startOpenSplice(int id) {
        boolean result = true;
        if (!isDomainRunning(id)) {
            if (setURIdomainId(null, id)) {
                String executable = "ospl";
                String command = "start";
                ProcessBuilder pb = new ProcessBuilder(executable, command, defaultURI);
                try {
                    Process p = pb.start();
                    p.waitFor();
                    if (p.exitValue() != 0) {
                        result = false;
                    } else {
                        runningDomains.add(id);
                        System.out.println("Started spliced");
                    }
                    try {
                        // wait 2 seconds for spliced to start */
                        Thread.sleep(2);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    result = false;
                }
            } else {
                result = false;
            }
        }
        return result;
    }


    public boolean stopOpenSplice(int id) {
        boolean result = true;
        if (isDomainRunning(id)) {
            if (setURIdomainId(null, id)) {
                String executable = "ospl";
                String command = "stop";
                ProcessBuilder pb = new ProcessBuilder(executable, command, defaultURI);
                try {
                    Process p = pb.start();
                    p.waitFor();
                    if (p.exitValue() != 0) {
                        result = false;
                    } else {
                        System.out.println("Stopped spliced");
                        runningDomains.remove(id);
                    }
                    try {
                        // wait 2 seconds for spliced to stop */
                        Thread.sleep(2);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    result = false;
                }
            } else {
                result = false;
            }
        }
        return result;
    }

    @Override
    public boolean exceptionCheck(String function, Exception e, boolean expected) {
        boolean result = true;
        if (notImplementedFunctions.contains(function)) {
            result = e instanceof java.lang.UnsupportedOperationException;
        } else {
            result = expected;
        }
        return result;
    }

    @Override
    public boolean beforeClass(Properties prop) {
        boolean result = false;
        domainId = prop.getProperty("domainid") != null ? Integer.parseInt(prop.getProperty("domainid")) : domainId;
        String uriname = prop.getProperty("uriname") != null ? prop.getProperty("uriname") : uriTemplateName;
        result = setURIdomainId(uriname, domainId);
        if (result) {
            result = startOpenSplice(domainId);
        }
        return result;
    }

    @Override
    public boolean afterClass(Properties prop) {
        boolean result = false;
        opensplice_shmUtility = null;
        result = stopAllDomains();
        if (result) {
            result = super.afterClass(prop);
        }
        return result;
    }

    @Override
    public boolean beforeTest(Properties prop) {
        return false;
    }

    @Override
    public boolean afterTest(Properties prop) {
        return false;
    }

    @Override
    public long getWriteSleepTime() {
        return writeSleepTime;
    }

}
