package org.eclipse.cyclonedds.roundtrip.ping;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Memory;
import com.sun.jna.StringArray;
import com.sun.jna.ptr.PointerByReference;

import org.eclipse.cyclonedds.ddsc.dds.DdscLibrary;
import org.eclipse.cyclonedds.ddsc.dds.dds_sample_info;
import org.eclipse.cyclonedds.ddsc.dds_public_impl.dds_sequence;
import org.eclipse.cyclonedds.helper.NativeSize;
import org.eclipse.cyclonedds.roundtrip.RoundTripModule_DataType;
import org.eclipse.cyclonedds.roundtrip.RoundTripModule_DataType_Helper;

public class RoundtripPing
{
	RoundTripModule_DataType_Helper helper = new RoundTripModule_DataType_Helper();

	int participant;
	int DDS_CHECK_REPORT = org.eclipse.cyclonedds.ddsc.dds_public_error.DdscLibrary.DDS_CHECK_REPORT;
	int DDS_CHECK_EXIT = org.eclipse.cyclonedds.ddsc.dds_public_error.DdscLibrary.DDS_CHECK_EXIT;
	int MAX_SAMPLES = 100;    
	int DDS_DOMAIN_DEFAULT = 0;
	long DDS_NSECS_IN_USEC = org.eclipse.cyclonedds.ddsc.dds_public_time.DdscLibrary.DDS_NSECS_IN_USEC;
	long US_IN_ONE_SEC = 1000000L;

	/* initailize all needed variables */    
	long elapsed = 0;
	long startTime = 0;
	long preWriteTime = 0;
	long postWriteTime = 0;
	boolean warmUp = true;
	private NativeSize bufSize = new NativeSize(MAX_SAMPLES*Byte.SIZE);

	int waitSet;
	int reader;
	int writer;

	Pointer allocTake = org.eclipse.cyclonedds.ddsc.dds_public_alloc.DdscLibrary.dds_alloc(helper.getNativeSize("RoundTripModule_DataType"));
	Pointer allocWarmUp = org.eclipse.cyclonedds.ddsc.dds_public_alloc.DdscLibrary.dds_alloc(helper.getNativeSize("RoundTripModule_DataType"));
	Pointer allocPayload;

	public void prepareDds()
	{
		/* A DDS Topic is created for our sample type on the domain participant. */
		int topic = DdscLibrary.dds_create_topic(participant,
				helper.getRoundTripModule_DataType_desc(), "RoundTrip", null, null);
		assert(helper.dds_error_check(topic, DDS_CHECK_REPORT | DDS_CHECK_EXIT) > 0);

		/* A DDS Publisher is created on the domain participant. */
		PointerByReference pubQos = org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_create_qos();
		String[] pubPartitions = { "ping" };
		org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_qset_partition (pubQos, 1, new StringArray(pubPartitions));
		int publisher = DdscLibrary.dds_create_publisher (participant, pubQos, null);
		assert(helper.dds_error_check(publisher, DDS_CHECK_REPORT | DDS_CHECK_EXIT) > 0);
		org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_delete_qos(pubQos);

		/* A DDS DataWriter is created on the Publisher & Topic with a modififed Qos. */
		PointerByReference dwQos =  org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_create_qos ();
		org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_qset_reliability (dwQos, org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_reliability_kind.DDS_RELIABILITY_RELIABLE, ddsSeconds(10));
		org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_qset_writer_data_lifecycle (dwQos, cBoolean(false));
		writer = DdscLibrary.dds_create_writer (publisher, topic, dwQos, null);
		assert(helper.dds_error_check(writer, DDS_CHECK_REPORT | DDS_CHECK_EXIT) > 0);
		org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_delete_qos (dwQos);

		/* A DDS Subscriber is created on the domain participant. */
		PointerByReference subQos = org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_create_qos ();
		String[] subPartitions = {"pong"};
		org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_qset_partition (subQos, 1, new StringArray(subPartitions));
		int subscriber = DdscLibrary.dds_create_subscriber (participant, subQos, null);
		assert(helper.dds_error_check(subscriber, DDS_CHECK_REPORT | DDS_CHECK_EXIT) > 0);
		org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_delete_qos (subQos);

		/* A DDS DataReader is created on the Subscriber & Topic with a modified QoS. */
		PointerByReference drQos = org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_create_qos ();
		org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_qset_reliability (drQos, org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_reliability_kind.DDS_RELIABILITY_RELIABLE, ddsSeconds(10));
		reader = DdscLibrary.dds_create_reader (subscriber, topic, drQos, null);
		assert(helper.dds_error_check(reader, DDS_CHECK_REPORT | DDS_CHECK_EXIT) > 0);
		org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_delete_qos (drQos);

		waitSet = DdscLibrary.dds_create_waitset (participant);
		int readCond = DdscLibrary.dds_create_readcondition (reader, DdscLibrary.DDS_ANY_STATE);
		int status = DdscLibrary.dds_waitset_attach (waitSet, readCond, reader);
		assert(helper.dds_error_check(status, DDS_CHECK_REPORT | DDS_CHECK_EXIT) > 0);
		status = DdscLibrary.dds_waitset_attach (waitSet, waitSet, waitSet);
		assert(helper.dds_error_check(status, DDS_CHECK_REPORT | DDS_CHECK_EXIT) > 0);
	}


	public void dataAvailable(int reader, Pointer pointer)
	{
		long difference = 0;
		PointerByReference samplePtr = new PointerByReference(allocTake);
		dds_sample_info.ByReference infosPtr = new  dds_sample_info.ByReference();	
		dds_sample_info[] infosArr = (dds_sample_info[]) infosPtr.toArray(MAX_SAMPLES); 

		/* Take sample and check that it is valid */	    
		long preTakeTime = org.eclipse.cyclonedds.ddsc.dds_public_time.DdscLibrary.dds_time();
		DdscLibrary.dds_take(reader, samplePtr, infosPtr, bufSize, MAX_SAMPLES);  
		long postTakeTime = org.eclipse.cyclonedds.ddsc.dds_public_time.DdscLibrary.dds_time();	       

		/* Update stats */
		difference = (postWriteTime - preWriteTime)/DDS_NSECS_IN_USEC;
		writeAccess.exampleAddTimingToTimeStats (difference);
		writeAccessOverall.exampleAddTimingToTimeStats (difference);

		difference = (postTakeTime - preTakeTime)/DDS_NSECS_IN_USEC;
		readAccess.exampleAddTimingToTimeStats (difference);
		readAccessOverall.exampleAddTimingToTimeStats (difference);

		/* read infosArr[0] */          
		RoundTripModule_DataType sub_data = new RoundTripModule_DataType(samplePtr.getValue());
		infosPtr.read();
		sub_data.read();
		//sub_data.payload.read();

		/*if(takeReturn > 0 ) 
		{
			RoundTripModule_DataType[] msgArray = (RoundTripModule_DataType[])sub_data.toArray(MAX_SAMPLES);
			for (int i = 0; i < MAX_SAMPLES; i++) 
			{
				if(infosArr[i].getValid_data() > 0) 
				{
					msgArray[i].read();
					msgArray[i].payload.read();				
					//if (msgArray[i].payload.get_length() > 0) 
					{
						System.out.println("Data["+i+"] --> " + msgArray[i]);
					}				
				}
			}			
		}*/

		difference = (postTakeTime - infosArr[0].source_timestamp)/DDS_NSECS_IN_USEC;
		roundTrip.exampleAddTimingToTimeStats (difference);
		roundTripOverall.exampleAddTimingToTimeStats (difference);

		if (!warmUp) {
			/* Print stats each second */
			difference = (postTakeTime - startTime)/DDS_NSECS_IN_USEC;
			if (difference > US_IN_ONE_SEC)
			{
				System.out.printf("%9s %9d %8.0f %8s %9s %8s %10d %8.0f %8s %10d %8.0f %8s\n",
						(elapsed + 1),
						roundTrip.count(), 
						roundTrip.exampleGetMedianFromTimeStats() / 2,
						roundTrip.min() / 2,
						roundTrip.exampleGet99PercentileFromTimeStats () / 2,
						roundTrip.max() / 2,
						writeAccess.count(),
						writeAccess.exampleGetMedianFromTimeStats (),
						writeAccess.min(),
						readAccess.count(),
						readAccess.exampleGetMedianFromTimeStats (),
						readAccess.min());

				roundTrip.exampleResetTimeStats();
				writeAccess.exampleResetTimeStats();
				readAccess.exampleResetTimeStats();
				startTime = org.eclipse.cyclonedds.ddsc.dds_public_time.DdscLibrary.dds_time();
				elapsed++;
			}
		}

		preWriteTime = org.eclipse.cyclonedds.ddsc.dds_public_time.DdscLibrary.dds_time();
		DdscLibrary.dds_write_ts (writer, pointer, preWriteTime);
		postWriteTime = org.eclipse.cyclonedds.ddsc.dds_public_time.DdscLibrary.dds_time();
	}

	ExampleTimeStats roundTrip = new  ExampleTimeStats();
	ExampleTimeStats writeAccess = new  ExampleTimeStats();
	ExampleTimeStats readAccess = new  ExampleTimeStats();
	ExampleTimeStats roundTripOverall = new  ExampleTimeStats();
	ExampleTimeStats writeAccessOverall = new  ExampleTimeStats();
	ExampleTimeStats readAccessOverall = new  ExampleTimeStats();

	public RoundtripPing(){                
		this("0", "0", "0", true);        
	}

	public RoundtripPing(String _payloadSize, String _numSamples, String _timeOut){
		this(_payloadSize, _numSamples, _timeOut, false);
	}

	private RoundtripPing(String _payloadSize, String _numSamples, String _timeOut, boolean quit){
		System.out.print("Usage (parameters must be supplied in order):\n"
				+ "./ping [payloadSize (bytes, 0 - 100M)] [numSamples (0 = infinite)] [timeOut (seconds, 0 = infinite)]\n"
				+ "./ping quit - ping sends a quit signal to pong.\n"
				+ "Defaults:\n"
				+ "./ping 0 0 0\n");
		ctrlHandler();

		/* Create a Participant. */
		participant = DdscLibrary.dds_create_participant (DDS_DOMAIN_DEFAULT, null, null);
		//assert(helper.dds_error_check(participant, DDS_CHECK_REPORT | DDS_CHECK_EXIT) > 0);

		/* Prepare dds */
		prepareDds();

		/* test if quit */        
		testIfQuit(quit);

		/* shows inputs */
		Long payloadSize = Long.parseLong(_payloadSize);
		if (payloadSize > 100 * 1048576)
		{
			System.err.println("Invalid payloadSize");
			System.exit(-1);
		}
		Long numSamples = Long.parseLong(_numSamples);
		Long timeOut = Long.parseLong(_timeOut);
		System.out.print("# payloadSize: %" +payloadSize+ " | numSamples: %" +numSamples+ " | timeOut: %" +timeOut+ "\n\n");

		/* setting the payload for publication data */        
		dds_sequence dsPubData = new dds_sequence();
		dsPubData.set_length(Math.toIntExact(payloadSize));
		dsPubData.set_buffer(payloadSize > 0 ? payloadBuffer(payloadSize) : null);
		dsPubData.set_release(cBoolean(true)); //true
		dsPubData.set_maximum(0);                
		int memSize = Math.toIntExact(payloadSize) * Native.getNativeSize(Byte.TYPE);
		if(memSize > 0) {
			Pointer buffer = new Memory(memSize);
			for (int i = 0; i < payloadSize; i++)
			{
				buffer.setByte(i * Native.getNativeSize(Byte.TYPE), (byte)'a');
			}
			dsPubData.set_buffer(buffer);
		}
		RoundTripModule_DataType.ByReference pub_data = new RoundTripModule_DataType.ByReference();       
		pub_data.setPayload(dsPubData);

		/* warm up step */
		System.out.print("# Waiting for startup jitter to stabilise\n");        
		PointerByReference wsResults = new PointerByReference(); //dds_attach_t wsresults[1];
		long waitTimeout = ddsSeconds(1);
		long difference = 0L;		       
		long status;
		NativeSize wsResultSize = new NativeSize(1);

		dds_sample_info.ByReference infosPtr = new  dds_sample_info.ByReference();	    
		PointerByReference samplePtr = new PointerByReference(allocWarmUp);
		startTime = org.eclipse.cyclonedds.ddsc.dds_public_time.DdscLibrary.dds_time ();
		while (DdscLibrary.dds_triggered(waitSet) != 1 && difference < ddsSeconds(5))
		{
			status = DdscLibrary.dds_waitset_wait (waitSet, wsResults, wsResultSize, waitTimeout);			
			if (status > 0)
			{
				status = DdscLibrary.dds_take(reader, samplePtr, infosPtr, bufSize, MAX_SAMPLES);
				infosPtr.read();
			}

			long time = org.eclipse.cyclonedds.ddsc.dds_public_time.DdscLibrary.dds_time();
			difference = time - startTime;
		}

		if (DdscLibrary.dds_triggered (waitSet) != 1)
		{
			warmUp = false;
			System.out.print("# Warm up complete.\n\n");
			System.out.print("# Latency measurements (in us)\n");
			System.out.print("#             Latency [us]                                   Write-access time [us]       Read-access time [us]\n");
			System.out.print("# Seconds     Count   median      min      99%%      max      Count   median      min      Count   median      min\n");
		}

		roundTrip.exampleResetTimeStats ();
		writeAccess.exampleResetTimeStats ();
		readAccess.exampleResetTimeStats ();
		startTime = org.eclipse.cyclonedds.ddsc.dds_public_time.DdscLibrary.dds_time();

		/* Write a sample that pong can send back */
		preWriteTime = org.eclipse.cyclonedds.ddsc.dds_public_time.DdscLibrary.dds_time();
		status = DdscLibrary.dds_write_ts (writer, pub_data.getPointer(), preWriteTime);
		postWriteTime = org.eclipse.cyclonedds.ddsc.dds_public_time.DdscLibrary.dds_time();
		for (int i = 0; DdscLibrary.dds_triggered (waitSet) != 1 && ((numSamples > 0 && i < numSamples) || numSamples==0 ); i++)
		{
			status = DdscLibrary.dds_waitset_wait (waitSet, wsResults, wsResultSize, waitTimeout);            
			if (status != 0) {
				//assert(helper.dds_error_check(status, DDS_CHECK_REPORT | DDS_CHECK_EXIT) > 0);
				dataAvailable(reader, pub_data.getPointer());
			}            
		}

		if (!warmUp)
		{
			//Confirm with ping.c#381-395
			System.out.printf("%9s %9d %8.0f %8s %9s %8s %10d %8.0f %8s %10d %8.0f %8s\n",            
					"\n# Overall",
					roundTripOverall.count(),
					roundTripOverall.exampleGetMedianFromTimeStats () / 2,
					roundTripOverall.min() / 2,
					roundTripOverall.exampleGet99PercentileFromTimeStats () / 2,
					roundTripOverall.max() / 2,
					writeAccessOverall.count(),
					writeAccessOverall.exampleGetMedianFromTimeStats (),
					writeAccessOverall.min(),
					readAccessOverall.count(),
					readAccessOverall.exampleGetMedianFromTimeStats (),
					readAccessOverall.min() 
					);
		}

		finalizeDds(participant, allocPayload, allocTake, allocWarmUp);

	}

	private Pointer payloadBuffer(Long payloadSize) {		
		return allocPayload = org.eclipse.cyclonedds.ddsc.dds_public_alloc.DdscLibrary.dds_alloc(new NativeSize(payloadSize));
	}


	private void testIfQuit(boolean quit) {
		if(quit){
			System.out.println("Sending termination request.\n");
			/* pong uses a waitset which is triggered by instance disposal, and
              quits when it fires. */
			org.eclipse.cyclonedds.ddsc.dds_public_time.DdscLibrary.dds_sleepfor(ddsSeconds(1));
			dds_sequence dsPubData = new dds_sequence();
			dsPubData.set_length(0);
			dsPubData._buffer = null;
			dsPubData._release = cBoolean(true);
			dsPubData.set_maximum(0);
			RoundTripModule_DataType.ByReference pub_data = new RoundTripModule_DataType.ByReference();
			pub_data.setPayload(dsPubData);
			int status = DdscLibrary.dds_writedispose (writer, pub_data.getPointer());
			assert(helper.dds_error_check(status, DDS_CHECK_REPORT | DDS_CHECK_EXIT) > 0);
			org.eclipse.cyclonedds.ddsc.dds_public_time.DdscLibrary.dds_sleepfor(ddsSeconds(1));
			finalizeDds(participant, pub_data.getPointer());
			System.exit(0);
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

	private void ctrlHandler() {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				System.out.println("Interrupt received");
				DdscLibrary.dds_waitset_set_trigger (waitSet, cBoolean(true));               
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private long ddsSeconds(int seconds){
		return seconds * 1000000000;
	}

	private byte cBoolean(boolean b) {
		return b ? (byte)1:(byte)0;
	}
}
