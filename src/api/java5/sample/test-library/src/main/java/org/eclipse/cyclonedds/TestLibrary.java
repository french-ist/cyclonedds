package org.eclipse.cyclonedds;

/**
 * Hello world!
 *
 */

import org.eclipse.cyclonedds.ddsc.DdscLibrary;

public class TestLibrary 
{   
    public static void main( String[] args )
    {
        DdscLibrary.dds_entity_t part = DdscLibrary.dds_create_participant(0, null, null);
        DdscLibrary.dds_entity_t topic = DdscLibrary.dds_create_topic(part, null, "First-Topic", null, null);
        DdscLibrary.dds_entity_t writer = DdscLibrary.dds_create_writer (part, topic, null, null);
        System.out.println( "TOPIC: " + topic +", "+writer );
    }
}
