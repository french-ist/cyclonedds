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

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

public class SmoothOptimizer implements Runnable {
	LinkedList<Allocator> pool;
	LinkedList<Allocator> toFreePool;	
	private int MAX_COUNT;
	private AtomicInteger currentCount = new AtomicInteger();
	
	ObjectAllocatorThread oat = new ObjectAllocatorThread();
	
	class ObjectAllocatorThread extends Thread {
		@Override
		public void run() {			
			super.run();
		}
		
		public void allocate(int nb) {
			pool = new LinkedList<Allocator>();
			toFreePool = new LinkedList<Allocator>();
			for(int i=0;i<nb;i++) {
				pool.add(new TakeAllocator());
			}			
			currentCount.set(pool.size());
		}
	};
	
	public SmoothOptimizer(int nb) {		
		MAX_COUNT = nb;
		currentCount.set(MAX_COUNT);
		oat.start();
		oat.allocate(MAX_COUNT);
	}
	
	public SmoothOptimizer() {
		this(1000);
	}
	
	public void clear() {
		for (Allocator takeObject : toFreePool) {						
			takeObject.free();
		}
		toFreePool.clear();
	}
	
	public void reset() {
		clear();
		oat.allocate(MAX_COUNT);
	}

	public Allocator next() {
		Allocator obj = pool.poll();
		if(obj==null) {
			reset();
			obj = pool.poll();
		} else if (currentCount.get() < MAX_COUNT/3) {
			oat.allocate(2*MAX_COUNT/3);
			obj = pool.poll();
		}
		toFreePool.add(obj);
		currentCount.getAndDecrement();
		return obj;
	}


	public void run() {		
	}
	
}
