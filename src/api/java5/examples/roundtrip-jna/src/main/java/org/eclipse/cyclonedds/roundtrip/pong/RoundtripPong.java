package org.eclipse.cyclonedds.roundtrip;

import java.nio.IntBuffer;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;

import org.eclipse.cyclonedds.ddsc.dds.DdscLibrary;
import org.eclipse.cyclonedds.ddsc.dds.dds_sample_info;
import org.eclipse.cyclonedds.helper.NativeSize;

public class RoundtripPong 
{
    int DDS_CHECK_REPORT = org.eclipse.cyclonedds.ddsc.dds_public_error.DdscLibrary.DDS_CHECK_REPORT;
    int DDS_CHECK_EXIT = org.eclipse.cyclonedds.ddsc.dds_public_error.DdscLibrary.DDS_CHECK_EXIT;
    int MAX_SAMPLES=10;
    int DDS_DOMAIN_DEFAULT=0;
    RoundTripModule_DataType.ByReference data;
    RoundTripModule_DataType_Helper helper = new RoundTripModule_DataType_Helper();

    int participant;
    int reader;
    int writer;
    int readCond;
    int waitSet;   

    public RoundtripPong(){
        System.err.println("PONG to compare without usage of listeners");

        /* Initialize sample data 
            memset (data, 0, sizeof (data));
            for (int i = 0; i < MAX_SAMPLES; i++)
            {
                samples[i] = &data[i];
            }
        */
        
        /* Create a Participant. */        
        int part = DdscLibrary.dds_create_participant (DDS_DOMAIN_DEFAULT, null, null);
        assert(helper.dds_error_check(part, DDS_CHECK_REPORT | DDS_CHECK_EXIT) > 0);

        /*  (void)prepare_dds(&writer, &reader, &readCond, listener);   */
        prepareDds(writer, reader, readCond);

        /* while waitSet not triggered, wait sample from ping */
        PointerByReference wsresults = null;   //wsresults[1];
        NativeSize wsresultsize = new NativeSize(1);        
        long waitTimeout = 99999L;
        while (DdscLibrary.dds_triggered (waitSet) != 1)
        {
            /* Wait for a sample from ping */
            int status = DdscLibrary.dds_waitset_wait (waitSet, wsresults, wsresultsize, waitTimeout);            
            assert(helper.dds_error_check(reader, DDS_CHECK_REPORT | DDS_CHECK_EXIT) > 0);

            /* Take samples */            
            dataAvailable (reader, 0);            
        }

        /* Clean up */
        finalizeDds(participant, data);
    }
    
    //TODO
    public int prepareDds(int writer, int reader, int readCond)
    {
        int subscriber;
        //dds_return_t status;
      
        /* A DDS Topic is created for our sample type on the domain participant. */
        int topic = DdscLibrary.dds_create_topic(participant,
            helper.getRoundTripModule_DataType_desc(), "RoundTrip", null, null);
        assert(helper.dds_error_check(topic, DDS_CHECK_REPORT | DDS_CHECK_EXIT) > 0);

        /* A DDS Publisher is created on the domain participant. */      
        PointerByReference qos = org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_create_qos();        
        org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_qset_partition (qos, 1, null); //TODO pubPartitions);        
        
        int publisher = DdscLibrary.dds_create_publisher (participant, qos, null);
        assert(helper.dds_error_check(topic, DDS_CHECK_REPORT | DDS_CHECK_EXIT) > 0);
        org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_delete_qos(qos);
      
        /* A DDS DataWriter is created on the Publisher & Topic with a modififed Qos. */  
        qos =  org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_create_qos ();
        org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_qset_reliability (qos, 
            org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_reliability_kind.DDS_RELIABILITY_RELIABLE, 10 * 1000000000); // DDS_SECS(10) 

        org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_qset_writer_data_lifecycle (qos, (byte)0);
        
        /*  writer = DdscLibrary.dds_create_writer (publisher, topic, qos, null);
        assert(helper.dds_error_check(writer, DDS_CHECK_REPORT | DDS_CHECK_EXIT) > 0);
        org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_delete_qos (qos);
      
        /* A DDS Subscriber is created on the domain participant. 
      
        qos = dds_create_qos ();
        dds_qset_partition (qos, 1, subPartitions);
      
        subscriber = dds_create_subscriber (participant, qos, NULL);
        DDS_ERR_CHECK (subscriber, DDS_CHECK_REPORT | DDS_CHECK_EXIT);
        dds_delete_qos (qos);
      
        /* A DDS DataReader is created on the Subscriber & Topic with a modified QoS. 
      
        qos = dds_create_qos ();
        dds_qset_reliability (qos, DDS_RELIABILITY_RELIABLE, DDS_SECS(10));
        *reader = dds_create_reader (subscriber, topic, qos, listener);
        DDS_ERR_CHECK (*reader, DDS_CHECK_REPORT | DDS_CHECK_EXIT);
        dds_delete_qos (qos);
      
        waitSet = dds_create_waitset (participant);
        if (listener == NULL) {
          *readCond = dds_create_readcondition (*reader, DDS_ANY_STATE);
          status = dds_waitset_attach (waitSet, *readCond, *reader);
          DDS_ERR_CHECK (status, DDS_CHECK_REPORT | DDS_CHECK_EXIT);
        } else {
          *readCond = 0;
        }
        status = dds_waitset_attach (waitSet, waitSet, waitSet);
        DDS_ERR_CHECK (status, DDS_CHECK_REPORT | DDS_CHECK_EXIT);
      
        printf ("Waiting for samples from ping to send back...\n");
        fflush (stdout);
      */
        return participant;

    }

    //TODO 
    public void finalizeDds(int participant, RoundTripModule_DataType.ByReference data){
    }

    //TODO
    public void dataAvailable(int reader, Object arg){

    }
}
