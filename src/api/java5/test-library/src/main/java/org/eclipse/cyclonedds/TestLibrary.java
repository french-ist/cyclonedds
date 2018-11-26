package org.eclipse.cyclonedds;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

/**
 * Hello world!
 *
 */

import dds2.Dds2Library;

public class TestLibrary 
{   
    public static void main( String[] args )
    {
        //System.out.println(""+participant);
        dds2.Dds2Library.dds_entity_t topic = dds2.Dds2Library.ddsCreateParticipant(0, null, null);
        System.out.println( "Hello World!" );
    }
}
