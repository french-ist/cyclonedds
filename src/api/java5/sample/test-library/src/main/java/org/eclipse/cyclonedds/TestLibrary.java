package org.eclipse.cyclonedds;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

/**
 * Hello world!
 *
 */

import org.eclipse.cyclonedds.ddsc.DdscLibrary;

public class TestLibrary 
{   
    public static void main( String[] args )
    {
        //System.out.println(""+participant);
        DdscLibrary.dds_entity_t topic = DdscLibrary.ddsCreateParticipant(0, null, null);
        System.out.println( "Hello World!" );
    }
}
