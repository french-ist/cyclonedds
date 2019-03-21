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
