/**
  Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 
  This program and the accompanying materials are made available under the
  terms of the Eclipse Public License v. 2.0 which is available at
  http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
  v. 1.0 which is available at
  http://www.eclipse.org/org/documents/edl-v10.php.
 
  SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.cyclonedds.helloworld;

import org.eclipse.cyclonedds.ddsc.dds.DdscLibrary;
import org.eclipse.cyclonedds.ddsc.dds.dds_sample_info;
import org.eclipse.cyclonedds.helper.NativeSize;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;

/**
 * Hello world!
 *
 */
public class HelloWorldSub 
{
    int DDS_CHECK_REPORT = org.eclipse.cyclonedds.ddsc.dds_public_error.DdscLibrary.DDS_CHECK_REPORT;
    int DDS_CHECK_EXIT = org.eclipse.cyclonedds.ddsc.dds_public_error.DdscLibrary.DDS_CHECK_EXIT;
    HelloWorldData_Helper helper = new HelloWorldData_Helper();

    public HelloWorldSub(){
        /* Create a Participant. */
        int part = DdscLibrary.dds_create_participant (0, null, null);
        assert(helper.dds_error_check(part, DDS_CHECK_REPORT | DDS_CHECK_EXIT) > 0);

        /* Create a Topic. */
        int topic = DdscLibrary.dds_create_topic(part,
            helper.getHelloWorldData_Msg_desc(), "Msg", null, null);
        assert(helper.dds_error_check(topic, DDS_CHECK_REPORT | DDS_CHECK_EXIT) > 0);

        /* Create a reliable Reader. */
        PointerByReference qos = org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_create_qos();
        org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_qset_reliability(qos,
                org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_reliability_kind.DDS_RELIABILITY_RELIABLE,
                10 * 1000000000); // DDS_SECS (10)

        int reader = DdscLibrary.dds_create_reader (part, topic, qos, null);
        assert(helper.dds_error_check(reader, DDS_CHECK_REPORT | DDS_CHECK_EXIT) > 0);

        org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_qos_delete(qos);

        System.out.println("\n=== [Subscriber] Waiting for a sample ...");
        
        /*
        * Initialize sample buffer, by pointing the void pointer within
        * the buffer array to a valid sample memory location. */
        //samples[0] = HelloWorldData_Msg__alloc ();
        Pointer samplesAlloc = org.eclipse.cyclonedds.ddsc.dds_public_alloc.DdscLibrary.dds_alloc(helper.getNativeSize("Msg"));
        PointerByReference samplePtr = new PointerByReference(samplesAlloc);
        dds_sample_info.ByReference infosPtr = new dds_sample_info.ByReference();
        dds_sample_info[] infosArr = (dds_sample_info[]) infosPtr.toArray(1);
        
        while(true){               
            int readReturn = DdscLibrary.dds_read(reader, samplePtr, infosPtr, new NativeSize(1), 1);
            assert(helper.dds_error_check(readReturn, DDS_CHECK_REPORT | DDS_CHECK_EXIT) > 0);
            Msg  arrayMsgRef = new Msg(samplePtr.getValue());
            arrayMsgRef.read();
            infosPtr.read();
            
            //Check if we read some data and it is valid                
            if (readReturn > 0 && infosArr[0].getValid_data() > 0)
            {
                //Print Message
                Msg[] msgArray = (Msg[])arrayMsgRef.toArray(1);                    
                System.err.println("=== [Subscriber] Received : ");
                System.err.println("Message ("+ msgArray[0].userID+","+ msgArray[0].message+")");
                break;
            }
            else
            {
                //Polling sleep
                int milliSeconds = 20;
                org.eclipse.cyclonedds.ddsc.dds_public_time.DdscLibrary.dds_sleepfor(milliSeconds*1000000L);
            }
        }

        /* Free the data location. */
        org.eclipse.cyclonedds.ddsc.dds_public_alloc.DdscLibrary.dds_sample_free(
            samplesAlloc, 
            helper.getHelloWorldData_Msg_desc(),
            org.eclipse.cyclonedds.ddsc.dds_public_alloc.DdscLibrary.dds_free_op_t.DDS_FREE_ALL);

        /* Deleting the participant will delete all its children recursively as well. */
        int deleteStatus = DdscLibrary.dds_delete(part);
        assert(helper.dds_error_check(deleteStatus, DDS_CHECK_REPORT | DDS_CHECK_EXIT) > 0);
    }

    public static void main( String[] args )
    {
        new HelloWorldSub();
    }
}