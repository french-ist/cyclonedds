package org.eclipse.cyclonedds.examples.roundtrip.optimizer;

import org.eclipse.cyclonedds.ddsc.dds.dds_sample_info;
import org.eclipse.cyclonedds.examples.roundtrip.Dds;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;

public class TakeAllocator implements Allocator {
	public Pointer alloc;
	public PointerByReference samplePtr;
	public dds_sample_info.ByReference infosPtr;
	public dds_sample_info[] infosArr;
	
	public TakeAllocator() {
		samplePtr = new PointerByReference(Dds.allocTake);
		infosPtr = new  dds_sample_info.ByReference();	
		infosArr = (dds_sample_info[]) infosPtr.toArray(Dds.MAX_SAMPLES);
	}
	
	public void free() {
		infosPtr.clear();		
	}
}
