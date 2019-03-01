package org.eclipse.cyclonedds.examples.roundtrip.optimizer;

import java.util.LinkedList;

public class Optimizer implements Runnable {
	LinkedList<Allocator> pool;
	LinkedList<Allocator> toFreePool;	
	private int COUNT;
	
	public Optimizer(int nb) {		
		COUNT = nb;
		allocate();
	}
	
	public Optimizer() {
		COUNT=1000;
		allocate();
	}
	
	public void allocate() {
		pool = new LinkedList<Allocator>();
		toFreePool = new LinkedList<Allocator>();
		for(int i=0;i<COUNT;i++) {
			pool.add(new TakeAllocator());
		}
	}
	
	public void clear() {
		for (Allocator takeObject : toFreePool) {						
			takeObject.free();
		}
		toFreePool.clear();
	}
	
	public void reset() {
		clear();
		allocate();
	}

	public Allocator next() {
		Allocator obj = pool.poll();
		if(obj==null || pool.size() < COUNT/3) {
			reset();
			obj = pool.poll();
		}
		toFreePool.add(obj);
		return obj;
	}


	public void run() {		
	}
	
}
