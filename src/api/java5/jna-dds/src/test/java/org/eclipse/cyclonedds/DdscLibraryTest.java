package org.eclipse.cyclonedds;

import com.sun.jna.ptr.PointerByReference;
import java.nio.IntBuffer;
import com.sun.jna.Native;

import org.eclipse.cyclonedds.ddsc.dds_sample_info;
import org.eclipse.cyclonedds.ddsc.DdscLibrary;
import org.eclipse.cyclonedds.ddsc.NativeSize;
import org.eclipse.cyclonedds.helloworld.*;
import org.junit.Assert;
import org.junit.Test;

import java.lang.Thread;

/**
 * Unit test for simple App.
 */
public class DdscLibraryTest {

    private class Publisher extends Thread {

        public void run() {
            // creating participant
            DdscLibrary.dds_entity_t part = DdscLibrary.dds_create_participant(0, null, null);

            // creating topic
            DdscLibrary.dds_entity_t topic = DdscLibrary.dds_create_topic(part,
                    HelloWorldData_Helper.HelloWorldData_Msg_desc, "HelloWorldData_Msg", null, null);

            // creating writer
            DdscLibrary.dds_entity_t writer = DdscLibrary.dds_create_writer(part, topic, null, null);

            // check error
            DdscLibrary.dds_return_t ret = DdscLibrary.dds_set_enabled_status(writer,
                    DdscLibrary.DDS_PUBLICATION_MATCHED_STATUS);

            while (true) {
                IntBuffer status = IntBuffer.allocate(Native.getNativeSize(Integer.class));
                ret = DdscLibrary.dds_get_status_changes(writer, status);

                // DDS_ERR_CHECK (ret, DDS_CHECK_REPORT | DDS_CHECK_EXIT);

                if (status.get() == DdscLibrary.DDS_PUBLICATION_MATCHED_STATUS) {
                    System.out.println("DDS_PUBLICATION_MATCHED_STATUS");
                    break;
                }
                sleep(1000);
            }

            // create message to write
            HelloWorldData_Msg msg = new HelloWorldData_Msg.ByReference();
            msg.message = HelloWorldData_Helper.stringToPointer("Hello world!");
            msg.userID = 1;

            System.out.println("=== [Publisher]  Writing : ");
            System.out.println("Message (" + msg.userID + ", " + msg.message.getString(0) + ")");

            ret = DdscLibrary.dds_write(writer, (new PointerByReference(msg.getPointer()).getValue()));
            // DDS_ERR_CHECK (ret, DDS_CHECK_REPORT | DDS_CHECK_EXIT);

            ret = DdscLibrary.dds_delete(part);
        }

        public void sleep(int s){
            try {
                Thread.sleep(s);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }

    private class Subscriber extends Thread {
        public void run(){
            // creating participant
            DdscLibrary.dds_entity_t part = DdscLibrary.dds_create_participant (0, null, null);

            // creating topic
            DdscLibrary.dds_entity_t topic = DdscLibrary.dds_create_topic(part,
                    HelloWorldData_Helper.HelloWorldData_Msg_desc, "HelloWorldData_Msg", null, null);

            //Create a reliable Reader
            DdscLibrary.dds_qos_t qos = DdscLibrary.dds_qos_create();
            
            //TODO
            //DdscLibrary.dds_qset_reliability (qos, DdscLibrary.dds_entity_kind.DDS_RELIABILITY_RELIABLE, 10);
            
            DdscLibrary.dds_entity_t reader = DdscLibrary.dds_create_reader (part, topic, qos, null);
            
            //DDS_ERR_CHECK (reader, DDS_CHECK_REPORT | DDS_CHECK_EXIT);
            DdscLibrary.dds_qos_delete(qos);

            System.out.println("\n=== [Subscriber] Waiting for a sample ...\n");
            
            PointerByReference buf = new  PointerByReference();
            dds_sample_info si = new dds_sample_info.ByReference();
            while(true){               
                DdscLibrary.dds_return_t ret = DdscLibrary.dds_read(reader, buf, si, new NativeSize(1), 1);
                
                //Check if we read some data and it is valid
                if (ret != null) //(ret > 0) && (infos[0].valid_data)
                {
                    //Print Message
                    HelloWorldData_Msg msg = new HelloWorldData_Msg.ByReference();                    
                    System.err.println("=== [Subscriber] Received : ");
                    System.err.println("Message ("+ msg.userID+","+ msg.message);
                    break;
                }
                else
                {
                    //Polling sleep
                    sleep(1000);
                }
            }

            DdscLibrary.dds_return_t ret = DdscLibrary.dds_delete(part);
        }

        public void sleep(int s){
            try {
                Thread.sleep(s);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }

    @Test
    public void helloWorldTest() {
        System.out.println("STARTING SUBSCRIBER");
        Subscriber s = new Subscriber();
        s.start();
        sleep(3000);
        System.out.println("STARTING PUBLISHER");
        Publisher p = new Publisher();
        p.start();
        sleep(10000);
        System.out.println("END");
    }

    public void sleep(int s){
        try {
            Thread.sleep(s);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
