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

import java.io.Serializable;
import java.util.EventListener;
import java.util.concurrent.CountDownLatch;

import org.eclipse.cyclonedds.core.AbstractDDSObject;
import org.omg.dds.core.ServiceEnvironment;

public abstract class Listener<T> extends AbstractDDSObject implements EventListener, Serializable {
    private static final long serialVersionUID = 5928369585907075474L;
    private final CountDownLatch initialised;
    protected ServiceEnvironmentImpl environment;
    protected T listener;

    public Listener(ServiceEnvironmentImpl environment, T listener,
            boolean waitUntilInitialised) {
        this.environment = environment;
        this.listener = listener;

        if (waitUntilInitialised) {
            this.initialised = new CountDownLatch(1);
        } else {
            this.initialised = new CountDownLatch(0);
        }
    }

    public ServiceEnvironment getEnvironment() {
        return this.environment;
    }

    public T getRealListener(){
        return this.listener;
    }

    public void setInitialised() {
        this.initialised.countDown();
    }

    public void waitUntilInitialised() {
        try {
            this.initialised.await();
        } catch (InterruptedException e) {
            throw new AlreadyClosedExceptionImpl(this.environment,
                    "Listener interrupted.");
        }
    }
}
