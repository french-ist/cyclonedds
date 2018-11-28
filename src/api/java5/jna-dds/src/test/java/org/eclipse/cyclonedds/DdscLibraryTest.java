package org.eclipse.cyclonedds;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


import org.eclipse.cyclonedds.ddsc.DdscLibrary;
import org.eclipse.cyclonedds.ddsc.dds_topic_descriptor;
import org.eclipse.cyclonedds.helloworld.*;

/**
 * Unit test for simple App.
 */
public class DdscLibraryTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public DdscLibraryTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( DdscLibraryTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        DdscLibrary.dds_entity_t part = DdscLibrary.dds_create_participant(0, null, null);
        dds_topic_descriptor x = (dds_topic_descriptor)HelloWorldData_Msg.newInstance(dds_topic_descriptor.class);
        DdscLibrary.dds_entity_t topic = DdscLibrary.dds_create_topic(part, x, "HelloWorldData_Msg", null, null);
        DdscLibrary.dds_entity_t writer = DdscLibrary.dds_create_writer (part, topic, null, null);
        System.out.println( "TOPIC: " + topic +", "+writer );
        assertTrue( true );
    }
}
