package org.eclipse.cyclonedds;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.eclipse.cyclonedds.ddsc.DdscLibrary;
import org.eclipse.cyclonedds.HelloWorldData.*;

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
        System.out.println("TEQT");
        DdscLibrary.dds_entity_t part = DdscLibrary.dds_create_participant(0, null, null);
        DdscLibrary.dds_entity_t topic = DdscLibrary.dds_create_topic(part, null, "First-Topic", null, null);
        DdscLibrary.dds_entity_t writer = DdscLibrary.dds_create_writer (part, topic, null, null);
        System.out.println( "TOPIC: " + topic +", "+writer );
        System.out.println("TEQT END");
        assertTrue( true );
    }
}
