package org.eclipse.cyclonedds.helloworld;

import java.nio.IntBuffer;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;

import org.eclipse.cyclonedds.ddsc.dds.DdscLibrary;
import org.eclipse.cyclonedds.ddsc.dds.dds_sample_info;
import org.eclipse.cyclonedds.helper.NativeSize;

/**
 * Hello world!
 *
 */
public class HelloWorldPub 
{
    int DDS_CHECK_REPORT = org.eclipse.cyclonedds.ddsc.dds_public_error.DdscLibrary.DDS_CHECK_REPORT;
    int DDS_CHECK_EXIT = org.eclipse.cyclonedds.ddsc.dds_public_error.DdscLibrary.DDS_CHECK_EXIT;
    HelloWorldData_Helper helper = new HelloWorldData_Helper();

    public HelloWorldPub(){
        /* Create a Participant. */
        int part = DdscLibrary.dds_create_participant (0, null, null);
        assert(helper.dds_error_check(part, DDS_CHECK_REPORT | DDS_CHECK_EXIT) > 0);

        /* Create a Topic. */
        int topic = DdscLibrary.dds_create_topic(part, helper.getHelloWorldData_Msg_desc(), "HelloWorldData_Msg", null, null);
        assert(helper.dds_error_check(topic, DDS_CHECK_REPORT | DDS_CHECK_EXIT) > 0);

        /* Create a Writer. */
        int writer = DdscLibrary.dds_create_writer(part, topic, null, null);

        System.out.println("=== [Publisher]  Waiting for a reader to be discovered ...\n");

        DdscLibrary.dds_set_enabled_status(writer, DdscLibrary.DDS_PUBLICATION_MATCHED_STATUS);
        assert(helper.dds_error_check(topic, DDS_CHECK_REPORT | DDS_CHECK_EXIT) > 0) ;

        while (true) {
            IntBuffer status = IntBuffer.allocate(Native.getNativeSize(Integer.class));
            int ret = DdscLibrary.dds_get_status_changes(writer, status);
            assert(helper.dds_error_check(ret, DDS_CHECK_REPORT | DDS_CHECK_EXIT) > 0);

            if (status.get() == DdscLibrary.DDS_PUBLICATION_MATCHED_STATUS) {
                System.out.println("DDS_PUBLICATION_MATCHED_STATUS");
                break;
            }

            /* Polling sleep. */
            int milliSeconds = 20;
            org.eclipse.cyclonedds.ddsc.dds_public_time.DdscLibrary.dds_sleepfor(milliSeconds*1000000L);
        }

        /* Create a message to write. */
        HelloWorldData_Msg.ByReference msg = new HelloWorldData_Msg.ByReference();
        msg.userID = 1;
        msg.message = helper.stringToPointer("Hello World!");
        msg.write();
        System.out.println("=== [Publisher]  Writing : ");
        System.out.println("Message (" + msg.userID + ", " + msg.message.getString(0) + ")");

        int ret = DdscLibrary.dds_write(writer, msg.getPointer());
        assert(helper.dds_error_check(ret, DDS_CHECK_REPORT | DDS_CHECK_EXIT) > 0);

        /* Deleting the participant will delete all its children recursively as well. */
        ret = DdscLibrary.dds_delete(part);
    }

    public static void main( String[] args )
    {
        new HelloWorldPub();
    }
}
