package org.eclipse.cyclonedds.roundtrip.ping;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Memory;

import com.sun.jna.ptr.PointerByReference;

import org.eclipse.cyclonedds.ddsc.dds.DdscLibrary;
import org.eclipse.cyclonedds.ddsc.dds.dds_sample_info;
import org.eclipse.cyclonedds.ddsc.dds_public_impl.dds_sequence;
import org.eclipse.cyclonedds.helper.NativeSize;
import org.eclipse.cyclonedds.roundtrip.Dds;
import org.eclipse.cyclonedds.roundtrip.RoundTripModule_DataType;

public class RoundtripPing
{

	public void dataAvailable(int reader, Pointer pointer)
	{
		long difference = 0;
		PointerByReference samplePtr = new PointerByReference(Dds.allocTake);
		dds_sample_info.ByReference infosPtr = new  dds_sample_info.ByReference();	
		dds_sample_info[] infosArr = (dds_sample_info[]) infosPtr.toArray(Dds.MAX_SAMPLES); 

		/* Take sample and check that it is valid */	    
		long preTakeTime = org.eclipse.cyclonedds.ddsc.dds_public_time.DdscLibrary.dds_time();
		DdscLibrary.dds_take(reader, samplePtr, infosPtr, new NativeSize(Dds.MAX_SAMPLES*Byte.SIZE), Dds.MAX_SAMPLES);  
		long postTakeTime = org.eclipse.cyclonedds.ddsc.dds_public_time.DdscLibrary.dds_time();	       

		/* Update stats */
		difference = (Stats.postWriteTime - Stats.preWriteTime)/Dds.DDS_NSECS_IN_USEC;
		Dds.writeAccess.exampleAddTimingToTimeStats (difference);
		Dds.writeAccessOverall.exampleAddTimingToTimeStats (difference);

		difference = (postTakeTime - preTakeTime)/Dds.DDS_NSECS_IN_USEC;
		Dds.readAccess.exampleAddTimingToTimeStats (difference);
		Dds.readAccessOverall.exampleAddTimingToTimeStats (difference);

		/* read infosArr[0] */          
		RoundTripModule_DataType sub_data = new RoundTripModule_DataType(samplePtr.getValue());
		infosPtr.read();
		sub_data.read();

		difference = (postTakeTime - infosArr[0].source_timestamp)/Dds.DDS_NSECS_IN_USEC;
		Dds.roundTrip.exampleAddTimingToTimeStats (difference);
		Dds.roundTripOverall.exampleAddTimingToTimeStats (difference);

		if (!Dds.shouldWarmUp) {
			/* Print stats each second */
			difference = (postTakeTime - Stats.startTime)/Dds.DDS_NSECS_IN_USEC;
			if (difference > Dds.US_IN_ONE_SEC)
			{
				System.out.printf("%9s %9d %8.0f %8s %9s %8s %10d %8.0f %8s %10d %8.0f %8s\n",
						(Stats.elapsed + 1),
						Dds.roundTrip.count(), 
						Dds.roundTrip.exampleGetMedianFromTimeStats() / 2,
						Dds.roundTrip.min() / 2,
						Dds.roundTrip.exampleGet99PercentileFromTimeStats () / 2,
						Dds.roundTrip.max() / 2,
						Dds.writeAccess.count(),
						Dds.writeAccess.exampleGetMedianFromTimeStats (),
						Dds.writeAccess.min(),
						Dds.readAccess.count(),
						Dds.readAccess.exampleGetMedianFromTimeStats (),
						Dds.readAccess.min());

				Dds.roundTrip.exampleResetTimeStats();
				Dds.writeAccess.exampleResetTimeStats();
				Dds.readAccess.exampleResetTimeStats();
				Stats.startTime = org.eclipse.cyclonedds.ddsc.dds_public_time.DdscLibrary.dds_time();
				Stats.elapsed ++;
			}
		}

		Stats.preWriteTime = org.eclipse.cyclonedds.ddsc.dds_public_time.DdscLibrary.dds_time();
		DdscLibrary.dds_write_ts (Dds.pingWriter, pointer, Stats.preWriteTime);
		Stats.postWriteTime = org.eclipse.cyclonedds.ddsc.dds_public_time.DdscLibrary.dds_time();
	}

	public RoundtripPing(){                
		this("0", "0", "0", true);        
	}

	public RoundtripPing(String payloadSize, String numSamples, String timeOut){
		this(payloadSize, numSamples, timeOut, false);
	}

	private RoundtripPing(String _payloadSize, String _numSamples, String _timeOut, boolean quit){
		Dds.ctrlHandler();
		Dds.preparePingDds();
		Dds.testIfQuit(quit);
		Dds.checkParameters(_payloadSize, _numSamples, _timeOut);

		/* setting the payload for publication data */
		dds_sequence dsPubData = new dds_sequence();
		dsPubData.set_length(Math.toIntExact(Dds.payloadSize));		
		dsPubData.set_release(Dds.cBoolean(true)); //true
		dsPubData.set_maximum(0);                
		int memSize = Math.toIntExact(Dds.payloadSize) * Native.getNativeSize(Byte.TYPE);
		if(memSize > 0) {
			Pointer buffer = new Memory(memSize);
			for (int i = 0; i < Dds.payloadSize; i++)
			{
				buffer.setByte(i * Native.getNativeSize(Byte.TYPE), (byte)'a');
			}
			dsPubData.set_buffer(buffer);
		}
		RoundTripModule_DataType.ByReference pub_data = new RoundTripModule_DataType.ByReference();       
		pub_data.setPayload(dsPubData);
		
		Dds.warmUp();		
		ping(pub_data);

		for (int i = 0; DdscLibrary.dds_triggered(Dds.waitSet) != 1 
				&& ((Dds.numSamples > 0 && i < Dds.numSamples) || Dds.numSamples==0 ); i++)
		{
			int status = DdscLibrary.dds_waitset_wait(Dds.waitSet,Dds.blobList, Dds.blobListSize, Dds.waitTimeout);
			Dds.assertTrue(Dds.helper.dds_error_check(status, Dds.DDS_CHECK_REPORT | Dds.DDS_CHECK_EXIT) > 0);
			dataAvailable(Dds.pingReader, pub_data.getPointer());
		}

		if (!Dds.shouldWarmUp)
		{
			//Confirm with ping.c#381-395
			System.out.printf("%9s %9d %8.0f %8s %9s %8s %10d %8.0f %8s %10d %8.0f %8s\n",            
					"\n# Overall",
					Dds.roundTripOverall.count(),
					Dds.roundTripOverall.exampleGetMedianFromTimeStats () / 2,
					Dds.roundTripOverall.min() / 2,
					Dds.roundTripOverall.exampleGet99PercentileFromTimeStats () / 2,
					Dds.roundTripOverall.max() / 2,
					Dds.writeAccessOverall.count(),
					Dds.writeAccessOverall.exampleGetMedianFromTimeStats (),
					Dds.writeAccessOverall.min(),
					Dds.readAccessOverall.count(),
					Dds.readAccessOverall.exampleGetMedianFromTimeStats (),
					Dds.readAccessOverall.min() 
					);
		}

		Dds.finalizeDds(Dds.participant, Dds.pingAllocPayload, Dds.allocTake, Dds.pingAllocWarmUp);
	}

	public void ping(RoundTripModule_DataType.ByReference pub_data) {
		/* Write a sample that pong can send back */
		pub_data.write();
		Stats.preWriteTime = org.eclipse.cyclonedds.ddsc.dds_public_time.DdscLibrary.dds_time();
		DdscLibrary.dds_write_ts (Dds.pingWriter, pub_data.getPointer(), Stats.preWriteTime);
		Stats.postWriteTime = org.eclipse.cyclonedds.ddsc.dds_public_time.DdscLibrary.dds_time();
	}
}
