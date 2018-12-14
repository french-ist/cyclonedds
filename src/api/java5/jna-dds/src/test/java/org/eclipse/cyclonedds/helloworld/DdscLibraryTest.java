package org.eclipse.cyclonedds;


import java.nio.IntBuffer;

import com.sun.jna.Native;
import com.sun.jna.ptr.PointerByReference;

import org.eclipse.cyclonedds.ddsc.dds.dds_sample_info;
import org.eclipse.cyclonedds.helper.NativeSize;
import org.eclipse.cyclonedds.helloworld.*;
import org.eclipse.cyclonedds.ddsc.dds_public_impl.dds_key_descriptor;
import org.eclipse.cyclonedds.ddsc.dds_public_impl.dds_key_descriptor.ByReference;
import org.eclipse.cyclonedds.ddsc.dds_public_impl.dds_topic_descriptor;
import org.eclipse.cyclonedds.ddsc.dds.DdscLibrary;

import org.junit.Test;

public class DdscLibraryTest {

    private class Publisher extends Thread {

        HelloWorldData_Helper helper = new HelloWorldData_Helper();

        public void run() {
            // creating participant
            int part  = DdscLibrary.dds_create_participant(0, null, null);

            // creating topic
            int topic = DdscLibrary.dds_create_topic(part,
                helper.HelloWorldData_Msg_desc, "HelloWorldData_Msg", null, null);

            // creating writer
            int writer = DdscLibrary.dds_create_writer(part, topic, null, null);

            // check error
            int ret = DdscLibrary.dds_set_enabled_status(writer,
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
            msg.message = helper.stringToPointer("Hello world!");
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

        HelloWorldData_Helper helper = new HelloWorldData_Helper();

        public void run(){
            // creating participant
            int part = DdscLibrary.dds_create_participant (0, null, null);

            // creating topic
            int topic = DdscLibrary.dds_create_topic(part,
                helper.HelloWorldData_Msg_desc, "HelloWorldData_Msg", null, null);

            //Create a reliable Reader
            //PointerByReference qos = org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_qos_create();
            
            //TODO
            //DdscLibrary.dds_qset_reliability (qos, DdscLibrary.dds_entity_kind.DDS_RELIABILITY_RELIABLE, 10);
            
            int reader = DdscLibrary.dds_create_reader (part, topic, null, null);
            
            
            //DDS_ERR_CHECK (reader, DDS_CHECK_REPORT | DDS_CHECK_EXIT);
            //org.eclipse.cyclonedds.ddsc.dds_public_qos.dds_qos_delete(qos);

            System.out.println("\n=== [Subscriber] Waiting for a sample ...\n");
            
            PointerByReference samples = new PointerByReference(); //DdscLibrary.dds_alloc(helper.sizeof("HelloWorldData_Msg"));
            
            dds_sample_info infos= new dds_sample_info.ByReference();
            
            while(true){               
                int ret = DdscLibrary.dds_read(reader, samples, infos, new NativeSize(1) , 1);
                
                //Check if we read some data and it is valid
                System.out.println("---------------> "  + infos.getValid_data());
                if (ret != 0 && infos.getValid_data() > 0) //(ret > 0) && (infos[0].valid_data)
                {
                    //Print Message
                    HelloWorldData_Msg msg = new HelloWorldData_Msg.ByReference();                    
                    System.err.println("=== [Subscriber] Received : ");
                    System.err.println("Message ("+ msg.userID+","+ msg.message+") + samples.getValue(): " + samples.getValue());
                    break;
                }
                else
                {
                    //Polling sleep
                    sleep(1000);
                }
            }

            int ret = DdscLibrary.dds_delete(part);
        }

        public void sleep(int s){
            try {
                System.out.println("Sleep "+s);
                Thread.sleep(s);                
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }

    @Test
    public void helloWorldTest() {

        try {
            System.out.println("STARTING SUBSCRIBER");
            Subscriber s = new Subscriber();
            s.start();        
            sleep(3000);

            /*
            System.out.println("STARTING PUBLISHER");
            Publisher p = new Publisher();
            p.start();
            sleep(10000);
            System.out.println("END");    */
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sleep(int s){
        try {
            Thread.sleep(s);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
