/**
 * Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
 * v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.cyclonedds.examples.roundtrip.pong;

import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.eclipse.cyclonedds.ddsc.dds.DdscLibrary;
import org.eclipse.cyclonedds.ddsc.dds.dds_sample_info;
import org.eclipse.cyclonedds.ddsc.dds_public_impl.dds_sequence;
import org.eclipse.cyclonedds.examples.roundtrip.Dds;
import org.eclipse.cyclonedds.examples.roundtrip.RoundTripModule_DataType;
import org.eclipse.cyclonedds.examples.roundtrip.optimizer.Optimizer;
import org.eclipse.cyclonedds.examples.roundtrip.optimizer.TakeAllocator;
import org.eclipse.cyclonedds.helper.NativeSize;

public class RoundtripPong 
{
    private void ctrlHandler() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
               System.out.println("Interrupt received");
               DdscLibrary.dds_waitset_set_trigger (Dds.waitSet, (byte)1);           
            }
         });
    }

    public RoundtripPong(){
        System.out.println("PONG without usage of listeners");
        ExecutorService e = Executors.newFixedThreadPool(2);		
		e.execute(opt);
        ctrlHandler();
        Dds.preparePongDds();

        /* while waitSet not triggered, wait sample from ping */
        PointerByReference wsResults = new PointerByReference();
        NativeSize wsResultSize = new NativeSize(1);        
        long waitTimeout = 100000L;
        while (DdscLibrary.dds_triggered(Dds.waitSet) != 1)
        {
            /* Wait for a sample from ping */
            DdscLibrary.dds_waitset_wait(Dds.waitSet, wsResults, wsResultSize, waitTimeout);            

            /* Check if something available */
            dataAvailable(Dds.pongReader);
        }

        /* Clean up */
        Dds.finalizeDds(Dds.participant, Dds.allocTake);
    }

    Optimizer opt = new Optimizer();
	public void dataAvailable(int reader){
		/* define pointer for dds_take */
		TakeAllocator tObj = (TakeAllocator)opt.next();
		PointerByReference samplePtr = tObj.samplePtr;  //new PointerByReference(Dds.allocTake);
		dds_sample_info.ByReference infosPtr = tObj.infosPtr;//= new dds_sample_info.ByReference();
		dds_sample_info[] infosArr = tObj.infosArr;	//(dds_sample_info[]) infosPtr.toArray(Dds.MAX_SAMPLES);
		
	    long sampleCount = DdscLibrary.dds_take(reader, samplePtr, infosPtr, new NativeSize(Dds.MAX_SAMPLES*Byte.SIZE), Dds.MAX_SAMPLES);
	    
	    /* Setup strictures to receive samples */
	    RoundTripModule_DataType arrayMsgRef = new RoundTripModule_DataType(samplePtr.getValue());
	    infosPtr.read();
	    arrayMsgRef.read();
	    RoundTripModule_DataType[] valid_sample = (RoundTripModule_DataType[]) arrayMsgRef.toArray(Dds.MAX_SAMPLES);
	
	    for (int j = 0; 0 == DdscLibrary.dds_triggered(Dds.waitSet) && j < (int)sampleCount; j++)
	    {
	        /* If writer has been disposed terminate pong */
	        if (infosArr[j].getInstance_state() == DdscLibrary.dds_instance_state.DDS_IST_NOT_ALIVE_DISPOSED)
	        {
	            System.out.println("Received termination request. Terminating.\n");
	            DdscLibrary.dds_waitset_set_trigger (Dds.waitSet, (byte)1);
	            break;
	        }
	        else if (infosArr[j].getValid_data() > 0)
	        {
	        	/*
	        	System.out.println(" valid_sample:" + valid_sample[j].payload.get_maximum());	        		        	
	        	int i;
	        	for (i = 0; i < valid_sample[j].payload._length; i++) {
	        		System.out.print((char)valid_sample[j].payload._buffer.getByte(i));
				}*/

	        	/* If sample is valid, send it back to ping */
	        	//RoundTripModule_DataType.ByReference payload = setPayload();
	        	valid_sample[j].write();
	            DdscLibrary.dds_write_ts (Dds.pongWriter, valid_sample[j].getPointer(), infosArr[j].getSource_timestamp());                
	        }
	    }
	}
	
	@SuppressWarnings("unused")
	private RoundTripModule_DataType.ByReference setPayload() {
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
				buffer.setByte(i * Native.getNativeSize(Byte.TYPE), i%3==0? (byte)'c':(byte)'d');
			}
			dsPubData.set_buffer(buffer);
		}
		RoundTripModule_DataType.ByReference pub_data = new RoundTripModule_DataType.ByReference();       
		pub_data.setPayload(dsPubData);
		return pub_data;
	}
}