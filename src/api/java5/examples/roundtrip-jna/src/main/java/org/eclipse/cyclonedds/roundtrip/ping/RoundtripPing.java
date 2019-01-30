package org.eclipse.cyclonedds.roundtrip.ping;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Memory;

import com.sun.jna.ptr.PointerByReference;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.eclipse.cyclonedds.ddsc.dds.DdscLibrary;
import org.eclipse.cyclonedds.ddsc.dds.dds_sample_info;
import org.eclipse.cyclonedds.ddsc.dds_public_impl.dds_sequence;
import org.eclipse.cyclonedds.helper.NativeSize;
import org.eclipse.cyclonedds.roundtrip.Dds;
import org.eclipse.cyclonedds.roundtrip.RoundTripModule_DataType;
import org.eclipse.cyclonedds.roundtrip.optimizer.Optimizer;
import org.eclipse.cyclonedds.roundtrip.optimizer.TakeAllocator;

public class RoundtripPing
{

	Optimizer opt = new Optimizer();
	
	public void dataAvailable(int reader, Pointer pointer)
	{		
		TakeAllocator tObj = (TakeAllocator)opt.next();

		PointerByReference samplePtr = tObj.samplePtr ;//new PointerByReference(Dds.allocTake);
		dds_sample_info.ByReference infosPtr = tObj.infosPtr; //new  dds_sample_info.ByReference();	
		dds_sample_info[] infosArr = tObj.infosArr; //(dds_sample_info[]) infosPtr.toArray(Dds.MAX_SAMPLES); 

		/* Take sample and check that it is valid */	    
		long preTakeTime = org.eclipse.cyclonedds.ddsc.dds_public_time.DdscLibrary.dds_time();
		DdscLibrary.dds_take(reader, samplePtr, infosPtr, new NativeSize(Dds.MAX_SAMPLES*Byte.SIZE), Dds.MAX_SAMPLES);  
		long postTakeTime = org.eclipse.cyclonedds.ddsc.dds_public_time.DdscLibrary.dds_time();	       

		/* read sub_data  NO NEED TO READ PONG DATA 
		RoundTripModule_DataType sub_data = new RoundTripModule_DataType(samplePtr.getValue());
		infosPtr.read();
		sub_data.read();
		System.out.println(" valid_sample:" + sub_data.payload._maximum);	
    	/*for (int i = 0; i < sub_data.payload._length; i++) {
    		System.out.print((char)sub_data.payload._buffer.getByte(i));
		}*/
		
		Stats.statsStuf(infosArr, preTakeTime, postTakeTime);

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
		ExecutorService e = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() - 4);		
		e.execute(opt);
		e.execute(Dds.roundTrip);
		e.execute(Dds.writeAccess);
		e.execute(Dds.readAccess);
		e.execute(Dds.roundTripOverall);
		e.execute(Dds.writeAccessOverall);
		e.execute(Dds.readAccessOverall);				
		
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
				buffer.setByte(i * Native.getNativeSize(Byte.TYPE), i%3==0? (byte)'a':(byte)'b');
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

		Dds.finalizeDds(Dds.participant, Dds.pingAllocWarmUp);
	}

	public void ping(RoundTripModule_DataType.ByReference pub_data) {
		pub_data.write();
		Stats.preWriteTime = org.eclipse.cyclonedds.ddsc.dds_public_time.DdscLibrary.dds_time();
		DdscLibrary.dds_write_ts (Dds.pingWriter, pub_data.getPointer(), Stats.preWriteTime);
		Stats.postWriteTime = org.eclipse.cyclonedds.ddsc.dds_public_time.DdscLibrary.dds_time();
	}
}
