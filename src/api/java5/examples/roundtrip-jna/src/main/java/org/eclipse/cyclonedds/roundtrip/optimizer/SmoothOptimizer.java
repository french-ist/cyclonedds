package org.eclipse.cyclonedds.roundtrip.optimizer;

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
