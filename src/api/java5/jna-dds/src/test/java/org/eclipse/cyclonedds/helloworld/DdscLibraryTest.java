package org.eclipse.cyclonedds.helloworld;

import java.nio.IntBuffer;

import com.sun.jna.Native;
import com.sun.jna.ptr.PointerByReference;

import org.eclipse.cyclonedds.ddsc.dds.DdscLibrary;
import org.eclipse.cyclonedds.ddsc.dds.dds_sample_info;
import org.eclipse.cyclonedds.helper.NativeSize;
import org.junit.Test;

public class DdscLibraryTest {

    int DDS_CHECK_REPORT = org.eclipse.cyclonedds.ddsc.dds_public_error.DdscLibrary.DDS_CHECK_REPORT;
    int DDS_CHECK_EXIT = org.eclipse.cyclonedds.ddsc.dds_public_error.DdscLibrary.DDS_CHECK_EXIT;
    HelloWorldData_Helper helper = new HelloWorldData_Helper();

    private class Publisher extends Thread {

        public Publisher() {}

        public void run() {
            /* Create a Participant. */
            int part = DdscLibrary.dds_create_participant (0, null, null);
            helper.dds_error_check(part, DDS_CHECK_REPORT | DDS_CHECK_EXIT);

            /* Create a Topic. */
            int topic = DdscLibrary.dds_create_topic(part,
                helper.getHelloWorldData_Msg_desc(), "HelloWorldData_Msg", null, null);
            helper.dds_error_check(topic, DDS_CHECK_REPORT | DDS_CHECK_EXIT);

            /* Create a Writer. */
            int writer = DdscLibrary.dds_create_writer(part, topic, null, null);

            System.out.println("=== [Publisher]  Waiting for a reader to be discovered ...\n");

            DdscLibrary.dds_set_enabled_status(writer, DdscLibrary.DDS_PUBLICATION_MATCHED_STATUS);
            helper.dds_error_check(topic, DDS_CHECK_REPORT | DDS_CHECK_EXIT);

            while (true) {
                IntBuffer status = IntBuffer.allocate(Native.getNativeSize(Integer.class));
                int ret = DdscLibrary.dds_get_status_changes(writer, status);
                helper.dds_error_check(ret, DDS_CHECK_REPORT | DDS_CHECK_EXIT);

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
            helper.dds_error_check(ret, DDS_CHECK_REPORT | DDS_CHECK_EXIT);

            /* Deleting the participant will delete all its children recursively as well. */
            ret = DdscLibrary.dds_delete(part);
        }
    }

    private class Subscriber extends Thread {

        public void run(){
            /* Create a Participant. */
            int part = DdscLibrary.dds_create_participant (0, null, null);
            helper.dds_error_check(part, DDS_CHECK_REPORT | DDS_CHECK_EXIT);

            /* Create a Topic. */
            int topic = DdscLibrary.dds_create_topic(part,
                helper.getHelloWorldData_Msg_desc(), "HelloWorldData_Msg", null, null);
            helper.dds_error_check(topic, DDS_CHECK_REPORT | DDS_CHECK_EXIT);

            /* Create a reliable Reader. */
            org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_qos_t qos = org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_qos_create();
            DdscLibrary.dds_qset_reliability(qos,
                    org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_reliability_kind.DDS_RELIABILITY_RELIABLE,
                    10 * 1000000000); // DDS_SECS (10)

			int reader = DdscLibrary.dds_create_reader (part, topic, qos, null);
            helper.dds_error_check(reader, DDS_CHECK_REPORT | DDS_CHECK_EXIT);
            org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_qos_delete(qos);

            System.out.println("\n=== [Subscriber] Waiting for a sample ...");
            
            /*
             * TODO: check if really needed 
             * Initialize sample buffer, by pointing the void pointer within
             * the buffer array to a valid sample memory location. */
            //samples[0] = HelloWorldData_Msg__alloc ();
            /*Pointer samplesAlloc = org.eclipse.cyclonedds.ddsc.dds_public_alloc.DdscLibrary
                .dds_alloc(helper.getNativeSize("HelloWorldData_Msg"));*/
            PointerByReference samplePtr = new PointerByReference();
            dds_sample_info.ByReference infosPtr = new  dds_sample_info.ByReference();
            dds_sample_info[] infosArr = (dds_sample_info[]) infosPtr.toArray(1);
            
            while(true){               
                int readReturn = DdscLibrary.dds_read(reader, samplePtr, infosPtr, new NativeSize(1), 1);
                helper.dds_error_check(readReturn, DDS_CHECK_REPORT | DDS_CHECK_EXIT);
                HelloWorldData_Msg  arrayMsgRef = new HelloWorldData_Msg(samplePtr.getValue());
                arrayMsgRef.read();
                infosPtr.read();
                
                //Check if we read some data and it is valid                
                if (readReturn > 0 && infosArr[0].getValid_data() > 0)
                {
                    //Print Message
                    HelloWorldData_Msg[] msgArray = (HelloWorldData_Msg[])arrayMsgRef.toArray(1);                    
                    System.err.println("=== [Subscriber] Received : ");
                    System.err.println("Message ("+ msgArray[0].userID+","+ msgArray[0].message.getString(0)+")");
                    break;
                }
                else
                {
                    //Polling sleep
                    int milliSeconds = 20;
                    org.eclipse.cyclonedds.ddsc.dds_public_time.DdscLibrary.dds_sleepfor(milliSeconds*1000000L);
                }
            }

            /*
            TODO: check if really needed
            Free the data location.
            org.eclipse.cyclonedds.ddsc.dds_public_alloc.DdscLibrary.dds_sample_free(
                samplesAlloc,
                new org.eclipse.cyclonedds.ddsc.dds_public_alloc.DdscLibrary.dds_topic_descriptor(
                    helper.getHelloWorldData_Msg_desc().getPointer()),
                org.eclipse.cyclonedds.ddsc.dds_public_alloc.DdscLibrary.dds_free_op_t.DDS_FREE_ALL);

            /* Deleting the participant will delete all its children recursively as well. */
            int deleteStatus = DdscLibrary.dds_delete(part);
            helper.dds_error_check(deleteStatus, DDS_CHECK_REPORT | DDS_CHECK_EXIT);
        }
    }


    @Test
    public void helloWorldTest() {
        try {            
            
            System.out.println("STARTING SUBSCRIBER");
            Subscriber s = new Subscriber();
            s.start();        
            sleep(2000);

            System.out.println("STARTING PUBLISHER");
            Publisher p = new Publisher();
            p.start();
            sleep(2000);

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
