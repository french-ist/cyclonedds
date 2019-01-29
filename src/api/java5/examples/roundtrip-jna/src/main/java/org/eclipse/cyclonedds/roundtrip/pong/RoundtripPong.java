package org.eclipse.cyclonedds.roundtrip.pong;

import com.sun.jna.ptr.PointerByReference;

import org.eclipse.cyclonedds.ddsc.dds.DdscLibrary;
import org.eclipse.cyclonedds.ddsc.dds.dds_sample_info;
import org.eclipse.cyclonedds.helper.NativeSize;
import org.eclipse.cyclonedds.roundtrip.Dds;
import org.eclipse.cyclonedds.roundtrip.RoundTripModule_DataType;

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

	public void dataAvailable(int reader){
		/* define pointer for dds_take */
		PointerByReference samplePtr = new PointerByReference(Dds.allocTake);
		dds_sample_info.ByReference infosPtr = new dds_sample_info.ByReference();
		dds_sample_info[] infosArr = (dds_sample_info[]) infosPtr.toArray(Dds.MAX_SAMPLES);
		
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
	        	/*System.out.println("valid_sample:" + (char)valid_sample[j].payload._buffer.getByte(0));	        	
	        	int i;
	        	for (i = 0; i < valid_sample[j].payload._length; i++) {
	        		System.out.print((char)valid_sample[j].payload._buffer.getByte(i));
				}*/

	        	/* If sample is valid, send it back to ping */
	        	valid_sample[j].write();
	            DdscLibrary.dds_write_ts (Dds.pongWriter, valid_sample[j].getPointer(), infosArr[j].getSource_timestamp());                
	        }
	    }
	}
}
