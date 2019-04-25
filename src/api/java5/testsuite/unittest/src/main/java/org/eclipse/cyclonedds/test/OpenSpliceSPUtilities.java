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

public class OpenSpliceSPUtilities extends AbstractUtilities {

    private static OpenSpliceSPUtilities opensplice_spUtility = null;
    private final String                 notImplementedFile   = "notImplementedForOpensplice_sp.txt";
    private int                          domainId             = 0;
    private String                       resourcesPath        = "";
    private final String                 uriTemplateName      = "template.xml";
    private final String                 defaultURI           = System.getenv("OSPL_URI");
    private static final long            writeSleepTime       = 0;
    /**
     * @param cl
     */

    @SuppressWarnings("rawtypes")
    public static OpenSpliceSPUtilities getInstance(Class cl) {
        if (opensplice_spUtility == null) {
            ClassLoader classLoader = OpenSpliceSPUtilities.class.getClassLoader();
            try {
                Class testClass = classLoader.loadClass(cl.getName() + ".opensplice_sp");

                try {
                    opensplice_spUtility = (OpenSpliceSPUtilities) testClass.newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                // System.out.println("Initiated special opensplice_sp utilities class for test: "
                // + testClass.getName());
            } catch (ClassNotFoundException e) {
                opensplice_spUtility = new OpenSpliceSPUtilities();
            }
        }
        return opensplice_spUtility;
    }

    public OpenSpliceSPUtilities() {
        URL location = OpenSpliceSPUtilities.class.getProtectionDomain().getCodeSource().getLocation();
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
            singleprocess.setTextContent("true");

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

    @Override
    public boolean beforeClass(Properties prop) {
        boolean result = false;
        domainId = prop.getProperty("domainid") != null ? Integer.parseInt(prop.getProperty("domainid")) : domainId;
        String uriname = prop.getProperty("uriname") != null ? prop.getProperty("uriname") : uriTemplateName;
        result = setURIdomainId(uriname, domainId);
        return result;
    }

    @Override
    public boolean afterClass(Properties prop) {
        opensplice_spUtility = null;
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
