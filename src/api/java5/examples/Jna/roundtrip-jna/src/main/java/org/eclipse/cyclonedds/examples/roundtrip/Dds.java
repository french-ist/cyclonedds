package org.eclipse.cyclonedds.examples.roundtrip;

import org.eclipse.cyclonedds.ddsc.dds.DdscLibrary;
import org.eclipse.cyclonedds.ddsc.dds.dds_sample_info;
import org.eclipse.cyclonedds.ddsc.dds_public_impl.dds_sequence;
import org.eclipse.cyclonedds.examples.roundtrip.ping.Stats;
import org.eclipse.cyclonedds.helper.NativeSize;

import com.sun.jna.Pointer;
import com.sun.jna.StringArray;
import com.sun.jna.ptr.PointerByReference;

public class Dds {
	
	public static int DDS_CHECK_REPORT = org.eclipse.cyclonedds.ddsc.dds_public_error.DdscLibrary.DDS_CHECK_REPORT;
	public static int DDS_CHECK_EXIT = org.eclipse.cyclonedds.ddsc.dds_public_error.DdscLibrary.DDS_CHECK_EXIT;	
	public static int MAX_SAMPLES = 10;    
	public static int DDS_DOMAIN_DEFAULT = 0;	
	public static long US_IN_ONE_SEC = 1000000L;
	public static long DDS_NSECS_IN_USEC = org.eclipse.cyclonedds.ddsc.dds_public_time.DdscLibrary.DDS_NSECS_IN_USEC;
	
	public static RoundTripModule_DataType_Helper helper = new RoundTripModule_DataType_Helper();

	public static int topic;
	public static int participant;
	public static int waitSet;
	public static Pointer allocTake = org.eclipse.cyclonedds.ddsc.dds_public_alloc.DdscLibrary.dds_alloc(helper.getNativeSize("RoundTripModule_DataType"));
	public static Pointer pingAllocWarmUp = org.eclipse.cyclonedds.ddsc.dds_public_alloc.DdscLibrary.dds_alloc(helper.getNativeSize("RoundTripModule_DataType"));
	
	public static int pingReader;
	public static int pingWriter;
	
	
	public static int pongReader;
	public static int pongWriter;
	public static int pongReadCond;
	
	//warmup
	public static PointerByReference blobList = new PointerByReference();
	public static NativeSize blobListSize = new NativeSize(1);
	public static long waitTimeout = Dds.ddsSeconds(1);
	
	//params for ping
	public static long payloadSize;
	public static long numSamples;
	public static long timeOut;

	public static boolean shouldWarmUp = true;
	
	public static Stats roundTrip = new Stats();
	public static Stats writeAccess = new Stats();
	public static Stats readAccess = new Stats();
	public static Stats roundTripOverall = new Stats();
	public static Stats writeAccessOverall = new Stats();
	public static Stats readAccessOverall = new Stats();
	
		
	public static void warmUp() {
		System.out.print("# Waiting for startup jitter to stabilise\n");
		long difference = 0L;		       
		long status;
		dds_sample_info.ByReference infosPtr = new  dds_sample_info.ByReference();	    
		PointerByReference samplePtr = new PointerByReference(Dds.pingAllocWarmUp);
		Stats.startTime = System.nanoTime();//org.eclipse.cyclonedds.ddsc.dds_public_time.DdscLibrary.dds_time ();
		while (DdscLibrary.dds_triggered(Dds.waitSet) != 1 && difference < Dds.ddsSeconds(5))
		{
			status = DdscLibrary.dds_waitset_wait (Dds.waitSet, Dds.blobList, Dds.blobListSize, Dds.waitTimeout);			
			if (status > 0)
			{
				status = DdscLibrary.dds_take(Dds.pingReader, samplePtr, infosPtr, new NativeSize(Dds.MAX_SAMPLES*Byte.SIZE), Dds.MAX_SAMPLES);
				infosPtr.read();
			}

			long time = System.nanoTime();//org.eclipse.cyclonedds.ddsc.dds_public_time.DdscLibrary.dds_time();
			difference = time - Stats.startTime;
		}

		if (DdscLibrary.dds_triggered(Dds.waitSet) != 1)
		{
			shouldWarmUp = false;
			System.out.print("# Warm up complete.\n\n");
			System.out.print("# Latency measurements (in us)\n");
			System.out.print("#             Latency [us]                                   Write-access time [us]       Read-access time [us]\n");
			System.out.print("# Seconds     Count   median      min      99%%      max      Count   median      min      Count   median      min\n");
		}
		
		roundTrip.exampleResetTimeStats ();
		writeAccess.exampleResetTimeStats ();
		readAccess.exampleResetTimeStats ();
		Stats.startTime = System.nanoTime(); //org.eclipse.cyclonedds.ddsc.dds_public_time.DdscLibrary.dds_time();
	}
	
	public static void checkParameters(String _payloadSize, String _numSamples, String _timeOut) {
		/* shows inputs */
		Dds.payloadSize = Long.parseLong(_payloadSize);
		if (Dds.payloadSize > 100 * 1048576)
		{
			System.err.println("Invalid payloadSize");
			printUsage();
		}
		Dds.numSamples = Long.parseLong(_numSamples);
		Dds.timeOut = Long.parseLong(_timeOut);
		System.out.print("# payloadSize: %" +Dds.payloadSize+ " | numSamples: %" +Dds.numSamples+ " | timeOut: %" +Dds.timeOut+ "\n\n");
	}
	
	public static void testIfQuit(boolean quit) {
		if(quit){
			System.out.println("Sending termination request.\n");
			/* pong uses a waitset which is triggered by instance disposal, and
              quits when it fires. */
			org.eclipse.cyclonedds.ddsc.dds_public_time.DdscLibrary.dds_sleepfor(Dds.ddsSeconds(1));
			dds_sequence dsPubData = new dds_sequence();
			dsPubData.set_length(0);
			dsPubData._buffer = null;
			dsPubData._release = Dds.cBoolean(true);
			dsPubData.set_maximum(0);
			RoundTripModule_DataType.ByReference pub_data = new RoundTripModule_DataType.ByReference();
			pub_data.setPayload(dsPubData);
			int status = DdscLibrary.dds_writedispose (Dds.pingWriter, pub_data.getPointer());
			assert(Dds.helper.dds_error_check(status, Dds.DDS_CHECK_REPORT | Dds.DDS_CHECK_EXIT) > 0);
			org.eclipse.cyclonedds.ddsc.dds_public_time.DdscLibrary.dds_sleepfor(Dds.ddsSeconds(1));
			Dds.finalizeDds(Dds.participant, pub_data.getPointer());
			System.exit(0);
		}
	}
	
	public static void ctrlHandler() {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				System.out.println("Interrupt received");
				DdscLibrary.dds_waitset_set_trigger (Dds.waitSet, Dds.cBoolean(true));               
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void preparePingDds()
	{
		/* Create a Participant. */
		Dds.participant = DdscLibrary.dds_create_participant (Dds.DDS_DOMAIN_DEFAULT, null, null);
		assert(Dds.helper.dds_error_check(Dds.participant, Dds.DDS_CHECK_REPORT | Dds.DDS_CHECK_EXIT) > 0);
		
		/* A DDS Topic is created for our sample type on the domain participant. */
		Dds.topic = DdscLibrary.dds_create_topic(Dds.participant,
				Dds.helper.getRoundTripModule_DataType_desc(), "RoundTrip", null, null);
		assert(Dds.helper.dds_error_check(Dds.topic, Dds.DDS_CHECK_REPORT | Dds.DDS_CHECK_EXIT) > 0);

		/* A DDS Publisher is created on the domain participant. */
		PointerByReference pubQos = org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_create_qos();
		String[] pubPartitions = { "ping" };
		org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_qset_partition (pubQos, 1, new StringArray(pubPartitions));
		int publisher = DdscLibrary.dds_create_publisher (Dds.participant, pubQos, null);
		assert(Dds.helper.dds_error_check(publisher, Dds.DDS_CHECK_REPORT | Dds.DDS_CHECK_EXIT) > 0);
		org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_delete_qos(pubQos);

		/* A DDS DataWriter is created on the Publisher & Topic with a modififed Qos. */
		PointerByReference dwQos =  org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_create_qos ();
		org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_qset_reliability (dwQos, org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_reliability_kind.DDS_RELIABILITY_RELIABLE, ddsSeconds(10));
		org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_qset_writer_data_lifecycle (dwQos, Dds.cBoolean(false));
		Dds.pingWriter = DdscLibrary.dds_create_writer (publisher, Dds.topic, dwQos, null);
		assert(Dds.helper.dds_error_check(Dds.pingWriter, Dds.DDS_CHECK_REPORT | Dds.DDS_CHECK_EXIT) > 0);
		org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_delete_qos (dwQos);

		/* A DDS Subscriber is created on the domain participant. */
		PointerByReference subQos = org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_create_qos ();
		String[] subPartitions = {"pong"};
		org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_qset_partition (subQos, 1, new StringArray(subPartitions));
		int subscriber = DdscLibrary.dds_create_subscriber (Dds.participant, subQos, null);
		assert(Dds.helper.dds_error_check(subscriber, Dds.DDS_CHECK_REPORT | Dds.DDS_CHECK_EXIT) > 0);
		org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_delete_qos (subQos);

		/* A DDS DataReader is created on the Subscriber & Topic with a modified QoS. */
		PointerByReference drQos = org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_create_qos ();
		org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_qset_reliability (drQos, org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_reliability_kind.DDS_RELIABILITY_RELIABLE, ddsSeconds(10));
		Dds.pingReader = DdscLibrary.dds_create_reader (subscriber, Dds.topic, drQos, null);
		assert(Dds.helper.dds_error_check(Dds.pingReader, Dds.DDS_CHECK_REPORT | Dds.DDS_CHECK_EXIT) > 0);
		org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_delete_qos (drQos);

		Dds.waitSet = DdscLibrary.dds_create_waitset (Dds.participant);
		int readCond = DdscLibrary.dds_create_readcondition (Dds.pingReader, DdscLibrary.DDS_ANY_STATE);
		int status = DdscLibrary.dds_waitset_attach (Dds.waitSet, readCond, Dds.pingReader);
		assert(Dds.helper.dds_error_check(status, Dds.DDS_CHECK_REPORT | Dds.DDS_CHECK_EXIT) > 0);
		status = DdscLibrary.dds_waitset_attach (Dds.waitSet, Dds.waitSet, Dds.waitSet);
		assert(Dds.helper.dds_error_check(status, Dds.DDS_CHECK_REPORT | Dds.DDS_CHECK_EXIT) > 0);
	}
	
	public static void preparePongDds()
    {
		 /* Create a Participant. */        
        Dds.participant = DdscLibrary.dds_create_participant (Dds.DDS_DOMAIN_DEFAULT, null, null);
        assertTrue(Dds.helper.dds_error_check(Dds.participant, Dds.DDS_CHECK_REPORT | Dds.DDS_CHECK_EXIT) > 0);

		/* A DDS Topic is created for our sample type on the domain participant. */
    	Dds.topic = DdscLibrary.dds_create_topic(Dds.participant,
            Dds.helper.getRoundTripModule_DataType_desc(), "RoundTrip", null, null);
        assertTrue(Dds.helper.dds_error_check(Dds.topic, Dds.DDS_CHECK_REPORT | Dds.DDS_CHECK_EXIT) > 0);

        /* A DDS Publisher is created on the domain participant. */
        PointerByReference qos = org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_create_qos();
        String[] pubPartitions = { "pong" };
        org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_qset_partition (qos, 1, new StringArray(pubPartitions));

        int publisher = DdscLibrary.dds_create_publisher (Dds.participant, qos, null);
        assertTrue(Dds.helper.dds_error_check(publisher, Dds.DDS_CHECK_REPORT | Dds.DDS_CHECK_EXIT) > 0);
        org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_delete_qos(qos);
      
        /* A DDS DataWriter is created on the Publisher & Topic with a modififed Qos. */
        qos =  org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_create_qos ();
        org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_qset_reliability (qos, 
            org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_reliability_kind.DDS_RELIABILITY_RELIABLE, 10 * 1000000000); // Dds.DDS_SECS(10) 
        org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_qset_writer_data_lifecycle (qos, (byte)0);
        Dds.pongWriter = DdscLibrary.dds_create_writer (publisher, Dds.topic, qos, null);
        assertTrue(Dds.helper.dds_error_check(Dds.pongWriter, Dds.DDS_CHECK_REPORT | Dds.DDS_CHECK_EXIT) > 0);
        org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_delete_qos (qos);
      
        /* A DDS Subscriber is created on the domain participant. */
        qos = org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_create_qos ();
        String[] subPartitions = {"ping"};
        org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_qset_partition (qos, 1, new StringArray(subPartitions));
        int subscriber = DdscLibrary.dds_create_subscriber (Dds.participant, qos, null);
        assertTrue(Dds.helper.dds_error_check(subscriber, Dds.DDS_CHECK_REPORT | Dds.DDS_CHECK_EXIT) > 0);
        org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_delete_qos (qos);
      
        /* A DDS DataReader is created on the Subscriber & Topic with a modified QoS. */
        qos = org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_create_qos ();        
        org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_qset_reliability (qos, org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_reliability_kind.DDS_RELIABILITY_RELIABLE, 10 * 1000000000); // Dds.DDS_SECS(10)
        Dds.pongReader = DdscLibrary.dds_create_reader (subscriber, Dds.topic, qos, null);
        assertTrue(Dds.helper.dds_error_check(Dds.pongReader, Dds.DDS_CHECK_REPORT | Dds.DDS_CHECK_EXIT) > 0);
        org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_delete_qos (qos);
        
        Dds.waitSet = DdscLibrary.dds_create_waitset (Dds.participant);
        Dds.pongReadCond = DdscLibrary.dds_create_readcondition (Dds.pongReader, DdscLibrary.DDS_ANY_STATE);
        int status = DdscLibrary.dds_waitset_attach (Dds.waitSet, Dds.pongReadCond, Dds.pongReader);
        assertTrue(Dds.helper.dds_error_check(status, Dds.DDS_CHECK_REPORT | Dds.DDS_CHECK_EXIT) > 0);
        
        status = DdscLibrary.dds_waitset_attach (Dds.waitSet, Dds.waitSet, Dds.waitSet);
        assertTrue(Dds.helper.dds_error_check(status, Dds.DDS_CHECK_REPORT | Dds.DDS_CHECK_EXIT) > 0);

        System.out.println("Waiting for samples from ping to send back...\n");
    }
	
    public static void assertTrue(boolean b){
        if(!b){
            System.err.println("True expected");
            System.exit(-1);
        }
    }
	
	public static void finalizeDds(int part, Pointer... pointers)
	{
		int status = DdscLibrary.dds_delete (part);
		assert(Dds.helper.dds_error_check(status, Dds.DDS_CHECK_REPORT | Dds.DDS_CHECK_EXIT) > 0);

		for (Pointer pointer : pointers) {
			org.eclipse.cyclonedds.ddsc.dds_public_alloc.DdscLibrary.dds_sample_free(
					pointer,
					Dds.helper.getRoundTripModule_DataType_desc(),
					org.eclipse.cyclonedds.ddsc.dds_public_alloc.DdscLibrary.dds_free_op_t.DDS_FREE_CONTENTS);	
		}
	}

	public static long ddsSeconds(int seconds){
		return seconds * 1000000000;
	}

	public static byte cBoolean(boolean b) {
		return b ? (byte)1:(byte)0;
	}
	
	public static void printUsage()
	{
	  System.out.println ("Usage (parameters must be supplied in order):\n"
	          + "./ping [-l] [payloadSize (bytes, 0 - 100M)] [numSamples (0 = infinite)] [timeOut (seconds, 0 = infinite)]\n"
	          + "./ping quit - ping sends a quit signal to pong.\n"
	          + "Defaults:\n"
	          + "./ping 0 0 0\n");
	  System.exit(-1);
	}
	
}
