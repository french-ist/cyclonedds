package org.eclipse.cyclonedds.roundtrip.pong;

import com.sun.jna.Pointer;
import com.sun.jna.StringArray;
import com.sun.jna.ptr.PointerByReference;

import org.eclipse.cyclonedds.ddsc.dds.DdscLibrary;
import org.eclipse.cyclonedds.ddsc.dds.dds_sample_info;
import org.eclipse.cyclonedds.helper.NativeSize;
import org.eclipse.cyclonedds.roundtrip.RoundTripModule_DataType;
import org.eclipse.cyclonedds.roundtrip.RoundTripModule_DataType_Helper;

public class RoundtripPong 
{
    int DDS_CHECK_REPORT = org.eclipse.cyclonedds.ddsc.dds_public_error.DdscLibrary.DDS_CHECK_REPORT;
    int DDS_CHECK_EXIT = org.eclipse.cyclonedds.ddsc.dds_public_error.DdscLibrary.DDS_CHECK_EXIT;
    int MAX_SAMPLES = 100;
    int DDS_DOMAIN_DEFAULT = 0;
    RoundTripModule_DataType_Helper helper = new RoundTripModule_DataType_Helper();
    Pointer allocTake = org.eclipse.cyclonedds.ddsc.dds_public_alloc.DdscLibrary.dds_alloc(helper.getNativeSize("RoundTripModule_DataType"));

    int participant;
    int reader;
    int writer;
    int subscriber;
    int readCond;
    int waitSet;
    int status;
	

    public void assertTrue(boolean b){
        if(!b){
            System.err.println("True expected");
            System.exit(-1);
        }
    }

    private void ctrlHandler() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
               System.out.println("Interrupt received");
               DdscLibrary.dds_waitset_set_trigger (waitSet, (byte)1);           
            }
         });
    }

    public RoundtripPong(){
        System.out.println("PONG without usage of listeners");
        ctrlHandler();
        
        /* Create a Participant. */        
        participant = DdscLibrary.dds_create_participant (DDS_DOMAIN_DEFAULT, null, null);
        assertTrue(helper.dds_error_check(participant, DDS_CHECK_REPORT | DDS_CHECK_EXIT) > 0);

        prepareDds();

        /* while waitSet not triggered, wait sample from ping */
        PointerByReference wsresults = new PointerByReference();   //wsresults[1];
        NativeSize wsresultsize = new NativeSize(1);        
        long waitTimeout = 99999L;
        while (DdscLibrary.dds_triggered (waitSet) != 1)
        {
            /* Wait for a sample from ping */
            DdscLibrary.dds_waitset_wait (waitSet, wsresults, wsresultsize, waitTimeout);            

            /* Check if something available */
            dataAvailable (reader);
        }

        /* Clean up */
        finalizeDds(participant, allocTake);
    }
    
    public void prepareDds()
    {
        /* A DDS Topic is created for our sample type on the domain participant. */
        int topic = DdscLibrary.dds_create_topic(participant,
            helper.getRoundTripModule_DataType_desc(), "RoundTrip", null, null);
        assertTrue(helper.dds_error_check(topic, DDS_CHECK_REPORT | DDS_CHECK_EXIT) > 0);

        /* A DDS Publisher is created on the domain participant. */
        PointerByReference qos = org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_create_qos();
        String[] pubPartitions = { "pong" };
        org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_qset_partition (qos, 1, new StringArray(pubPartitions));

        int publisher = DdscLibrary.dds_create_publisher (participant, qos, null);
        assertTrue(helper.dds_error_check(publisher, DDS_CHECK_REPORT | DDS_CHECK_EXIT) > 0);
        org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_delete_qos(qos);
      
        /* A DDS DataWriter is created on the Publisher & Topic with a modififed Qos. */
        qos =  org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_create_qos ();
        org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_qset_reliability (qos, 
            org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_reliability_kind.DDS_RELIABILITY_RELIABLE, 10 * 1000000000); // DDS_SECS(10) 
        org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_qset_writer_data_lifecycle (qos, (byte)0);
        writer = DdscLibrary.dds_create_writer (publisher, topic, qos, null);
        assertTrue(helper.dds_error_check(writer, DDS_CHECK_REPORT | DDS_CHECK_EXIT) > 0);
        org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_delete_qos (qos);
      
        /* A DDS Subscriber is created on the domain participant. */
        qos = org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_create_qos ();
        String[] subPartitions = {"ping"};
        org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_qset_partition (qos, 1, new StringArray(subPartitions));
        subscriber = DdscLibrary.dds_create_subscriber (participant, qos, null);
        assertTrue(helper.dds_error_check(subscriber, DDS_CHECK_REPORT | DDS_CHECK_EXIT) > 0);
        org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_delete_qos (qos);
      
        /* A DDS DataReader is created on the Subscriber & Topic with a modified QoS. */
        qos = org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_create_qos ();        
        org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_qset_reliability (qos, org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_reliability_kind.DDS_RELIABILITY_RELIABLE, 10 * 1000000000); // DDS_SECS(10)
        reader = DdscLibrary.dds_create_reader (subscriber, topic, qos, null);
        assertTrue(helper.dds_error_check(reader, DDS_CHECK_REPORT | DDS_CHECK_EXIT) > 0);
        org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_delete_qos (qos);
        
        waitSet = DdscLibrary.dds_create_waitset (participant);
        readCond = DdscLibrary.dds_create_readcondition (reader, DdscLibrary.DDS_ANY_STATE);
        status = DdscLibrary.dds_waitset_attach (waitSet, readCond, reader);
        assertTrue(helper.dds_error_check(status, DDS_CHECK_REPORT | DDS_CHECK_EXIT) > 0);
        
        status = DdscLibrary.dds_waitset_attach (waitSet, waitSet, waitSet);
        assertTrue(helper.dds_error_check(status, DDS_CHECK_REPORT | DDS_CHECK_EXIT) > 0);

        System.out.println("Waiting for samples from ping to send back...\n");
    }

    /* define pointer for dds_take */
	PointerByReference samplePtr = new PointerByReference(allocTake);
	
	/* Infos */
	dds_sample_info.ByReference infosPtr = new dds_sample_info.ByReference();
	dds_sample_info[] infosArr = (dds_sample_info[]) infosPtr.toArray(MAX_SAMPLES);

	/* Call to data available */
	public void dataAvailable(int reader){
	    long sampleCount = DdscLibrary.dds_take(reader, samplePtr, infosPtr, new NativeSize(MAX_SAMPLES*Byte.SIZE), MAX_SAMPLES);
	    
	    /* Setup strictures to receive samples */
	    RoundTripModule_DataType arrayMsgRef = new RoundTripModule_DataType(samplePtr.getValue());
	    infosPtr.read();
	    arrayMsgRef.read();
	    RoundTripModule_DataType[] valid_sample = (RoundTripModule_DataType[]) arrayMsgRef.toArray(MAX_SAMPLES);
	
	    for (int j = 0; 0 == DdscLibrary.dds_triggered (waitSet) && j < (int)sampleCount; j++)
	    {
	        /* If writer has been disposed terminate pong */
	        if (infosArr[j].getInstance_state() == DdscLibrary.dds_instance_state.DDS_IST_NOT_ALIVE_DISPOSED)
	        {
	            System.out.println("Received termination request. Terminating.\n");
	            DdscLibrary.dds_waitset_set_trigger (waitSet, (byte)1);
	            break;
	        }
	        else if (infosArr[j].getValid_data() > 0)
	        {
	            /* If sample is valid, send it back to ping */
	            DdscLibrary.dds_write_ts (writer, valid_sample[j].getPointer(), infosArr[j].getSource_timestamp());                
	        }
	    }
	}

	public void finalizeDds(int part, Pointer... pointers)
	{
		int status = DdscLibrary.dds_delete (part);
		assert(helper.dds_error_check(status, DDS_CHECK_REPORT | DDS_CHECK_EXIT) > 0);

		for (Pointer pointer : pointers) {
			org.eclipse.cyclonedds.ddsc.dds_public_alloc.DdscLibrary.dds_sample_free(
					pointer,
					helper.getRoundTripModule_DataType_desc(),
					org.eclipse.cyclonedds.ddsc.dds_public_alloc.DdscLibrary.dds_free_op_t.DDS_FREE_CONTENTS);	
		}
	}

}