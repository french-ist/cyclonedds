package org.eclipse.cyclonedds;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;

import java.nio.IntBuffer;

import com.sun.jna.Memory;
import com.sun.jna.Native;

import org.eclipse.cyclonedds.ddsc.DdscLibrary;
import org.eclipse.cyclonedds.ddsc.dds_key_descriptor;
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
        //creating participant
        DdscLibrary.dds_entity_t part = DdscLibrary.dds_create_participant(0, null, null);

        //creating topic
        DdscLibrary.dds_entity_t topic = DdscLibrary.dds_create_topic(part, HelloWorldData_Helper.HelloWorldData_Msg_desc, "HelloWorldData_Msg", null, null);

        //creating writer
        DdscLibrary.dds_entity_t writer = DdscLibrary.dds_create_writer (part, topic, null, null);

        //check error
        DdscLibrary.dds_return_t ret = DdscLibrary.dds_set_enabled_status(writer, DdscLibrary.DDS_PUBLICATION_MATCHED_STATUS);

        while(true)
        {
          //ret = dds_get_status_changes (writer, &status);
          IntBuffer status = IntBuffer.allocate(Native.getNativeSize(Integer.class));
          ret = DdscLibrary.dds_get_status_changes(writer, status);

          //DDS_ERR_CHECK (ret, DDS_CHECK_REPORT | DDS_CHECK_EXIT);

          if (status.get() == DdscLibrary.DDS_PUBLICATION_MATCHED_STATUS) {
            System.out.println("DDS_PUBLICATION_MATCHED_STATUS");
            break;
          }
          try {
            System.out.println("waiting 5s...");
              Thread.sleep(5000);
          } catch (Exception e) {
              e.printStackTrace();
          }
        }

        //create message to write
        HelloWorldData_Msg msg = new  HelloWorldData_Msg.ByReference();
        msg.message = HelloWorldData_Helper.stringToPointer("Hello world!");
        msg.userID = 1;

        System.out.println("=== [Publisher]  Writing : ");
        System.out.println("Message ("+ msg.userID+", "+ msg.message.getString(0)+")");

        ret = DdscLibrary.dds_write(writer, (new PointerByReference(msg.getPointer()).getValue()));
        //DDS_ERR_CHECK (ret, DDS_CHECK_REPORT | DDS_CHECK_EXIT);
        System.out.println("RET: " + ret);

        assertTrue( true );
    }
}
