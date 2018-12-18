package org.eclipse.cyclonedds.helloworld;


import java.nio.IntBuffer;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.ptr.ByReference;
import com.sun.jna.ptr.PointerByReference;

import org.eclipse.cyclonedds.ddsc.dds.dds_sample_info;
import org.eclipse.cyclonedds.ddsc.dds.DdscLibrary.dds_topic_descriptor_t;
import org.eclipse.cyclonedds.ddsc.dds_public_impl.dds_topic_descriptor;
import org.eclipse.cyclonedds.helper.NativeSize;
import org.eclipse.cyclonedds.ddsc.dds.DdscLibrary;

import org.junit.Test;

public class DdscLibraryTest {

    private class Publisher extends Thread {

        HelloWorldData_Helper helper = new HelloWorldData_Helper();

        public Publisher(){}

        public void run() {
            // creating participant
            int part  = DdscLibrary.dds_create_participant(0, null, null);

            // creating topic
            
            int topic = DdscLibrary.dds_create_topic(part,
                helper.getHelloWorldData_Msg_desc(), "HelloWorldData_Msg", null, null);

            // creating writer
            int writer = DdscLibrary.dds_create_writer(part, topic, null, (DdscLibrary.dds_listener_t) null);

            // check error
            int ret = DdscLibrary.dds_set_enabled_status(writer,
                    DdscLibrary.DDS_PUBLICATION_MATCHED_STATUS);

            //TODO check if ret == DdsCLibrary.DD_RETCODE_SUCCESS

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
            HelloWorldData_Msg.ByReference msg = new HelloWorldData_Msg.ByReference();
            msg.userID = 1;
            msg.message = helper.stringToPointer("Hello World!");
            System.out.println("=== [Publisher]  Writing : ");
            System.out.println("Message (" + msg.userID + ", " + msg.message.getString(0) + ")");

            ret = DdscLibrary.dds_write(writer, msg.getPointer());
            // DDS_ERR_CHECK (ret, DDS_CHECK_REPORT | DDS_CHECK_EXIT);

            ret = DdscLibrary.dds_delete(part);
        }

        public void sleep(int s){
            try {
                Thread.sleep(s);
            } catch (Exception e) {
                e.printStackTrace();
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
                helper.getHelloWorldData_Msg_desc(), "HelloWorldData_Msg", null, null);

            //Create a reliable Reader
            PointerByReference qos = org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_qos_create();
            DdscLibrary.dds_qset_reliability (qos,
                org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_reliability_kind.DDS_RELIABILITY_RELIABLE,
                10);
            int reader = DdscLibrary.dds_create_reader (part, topic, null, null);
            int ret = helper.dds_error_check(reader, 
                org.eclipse.cyclonedds.ddsc.dds_public_error.DdscLibrary.DDS_CHECK_REPORT 
                | org.eclipse.cyclonedds.ddsc.dds_public_error.DdscLibrary.DDS_CHECK_EXIT);            
            org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_qos_delete(qos);
            System.out.println("\n=== [Subscriber] Waiting for a sample ...\n");
            
            /* Initialize sample buffer, by pointing the void pointer within
             * the buffer array to a valid sample memory location. */
            //samples[0] = HelloWorldData_Msg__alloc ();
            Pointer samplesPtr = org.eclipse.cyclonedds.ddsc.dds_public_alloc.DdscLibrary
                .dds_alloc(new NativeSize(Long.parseLong(helper.sizeof("HelloWorldData_Msg")+"")));
            
            PointerByReference samples = new PointerByReference(samplesPtr);
            dds_sample_info.ByReference infos = new  dds_sample_info.ByReference();

            //dds_sample_info.ByReference ddsSampleInfos = new dds_sample_info.ByReference();
            //dds_sample_info[] samplesStrictures = (dds_sample_info[]) ddsSampleInfos.toArray(1);
            //Pointer infos = samplesStrictures[0].getPointer();
            
            while(true){               
                ret = DdscLibrary.dds_read(reader, samples, infos, new NativeSize(1), 1);
                HelloWorldData_Msg  arrayMsgRef = new HelloWorldData_Msg(samples.getValue());
                arrayMsgRef.read();
                infos.read();
                
                System.out.println("\n###\n---> samples:" + samples+"#\n---> infos:"+infos);
                
                //Check if we read some data and it is valid                
                if (ret > 0 && infos.getValid_data() > 0)
                {
                    //Print Message
                    HelloWorldData_Msg[] msgArray = (HelloWorldData_Msg[])arrayMsgRef.toArray(1);                    
                    System.err.println("=== [Subscriber] Received : ");
                    System.err.println("Message ("+ msgArray[0].userID+","+ msgArray[0].message+")");                                        
                    break;
                }
                else
                {
                    //Polling sleep
                    sleep(1000);
                }
            }

            ret = DdscLibrary.dds_delete(part);
        }

        public void sleep(int s){
            try {
                Thread.sleep(s);                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void helloWorldTest() {
        try {            
            /*
            System.out.println("STARTING PUBLISHER");
            Publisher p = new Publisher();
            p.start();
            sleep(5000);
            */
            
            System.out.println("STARTING SUBSCRIBER");
            Subscriber s = new Subscriber();
            s.start();        
            sleep(5000);

            System.out.println("END");            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sleep(int s){
        try {
            Thread.sleep(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
