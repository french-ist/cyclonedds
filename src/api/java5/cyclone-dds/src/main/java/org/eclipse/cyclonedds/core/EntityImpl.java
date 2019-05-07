/**
  Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 
  This program and the accompanying materials are made available under the
  terms of the Eclipse Public License v. 2.0 which is available at
  http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
  v. 1.0 which is available at
  http://www.eclipse.org/org/documents/edl-v10.php.
 
  SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.cyclonedds.core;

import java.util.EventListener;
import java.util.concurrent.atomic.AtomicInteger;

import org.omg.dds.core.EntityQos;
import org.omg.dds.core.ServiceEnvironment;

public abstract class EntityImpl
<
QOS extends EntityQos<?>, 
LISTENER extends EventListener, 
LISTENERIMPL extends Listener<LISTENER>
> 

implements org.omg.dds.core.Entity<LISTENER, QOS> {

	protected final transient ServiceEnvironmentImpl environment;
	protected LISTENERIMPL listener;
	private AtomicInteger refCount;
	private static java.util.HashMap<String, Integer> references = new java.util.HashMap<String, Integer>();

	public EntityImpl(ServiceEnvironmentImpl environment) {
		this.environment = environment;
		this.listener = null;
		this.refCount = new AtomicInteger(1);
		assert(addReference(this.getClass().getSimpleName()));
	}

	public static boolean printReferences() {
		boolean noMoreRefs = true;

		synchronized (references) {
			for (String name : references.keySet()) {
				int count = references.get(name);
				if (count != 0) {
					noMoreRefs = false;
				}
				System.out.println(name + "=" + count);
			}
			if (!noMoreRefs) {
				System.out.println("----------------");

				for (String name : references.keySet()) {
					int count = references.get(name);
					if (count != 0) {
						noMoreRefs = false;
					}
					System.out.println(name + "=" + count);
				}
				System.out.println("----------------");
			}
		}
		return noMoreRefs;
	}

	@Override
	public final LISTENER getListener(){
		if (this.listener != null) {
			return this.listener.getRealListener();
		}
		return null;
	}

	@Override
	public ServiceEnvironment getEnvironment() {
		return this.environment;
	}

	@Override
	public final void retain() {
		int newValue = this.refCount.incrementAndGet();

		if (newValue <= 0) {
			int refCount = this.refCount.decrementAndGet();
			throw new AlreadyClosedExceptionImpl(this.environment,
					"Entity already closed. refcount:" + refCount);
		}
		assert (newValue > newValue - 1);
		assert (newValue > 1);
	}

	@Override
	public final void close() {
		int newValue = this.refCount.decrementAndGet();

		if (newValue == 0) {
			this.destroy();
			assert (removeReference(this.getClass().getSimpleName()));
		} else if (newValue < 0) {
			throw new AlreadyClosedExceptionImpl(this.environment,
					"Entity already closed.");
		}
	}

	protected abstract void destroy();

	private static boolean addReference(String name) {
		synchronized (references) {
			Integer refs = references.get(name);
			if (refs == null) {
				references.put(name, 1);
			} else {
				references.put(name, refs.intValue() + 1);
			}
		}
		return true;
	}

	private static boolean removeReference(String name) {
		synchronized (references) {
			Integer refs = references.get(name);
			assert (refs != null);
			references.put(name, refs.intValue() - 1);
		}
		return true;
	}
}
