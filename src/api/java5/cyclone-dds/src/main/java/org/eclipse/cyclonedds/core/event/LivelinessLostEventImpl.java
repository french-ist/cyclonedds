/**
  Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 
  This program and the accompanying materials are made available under the
  terms of the Eclipse Public License v. 2.0 which is available at
  http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
  v. 1.0 which is available at
  http://www.eclipse.org/org/documents/edl-v10.php.
 
  SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.cyclonedds.core.event;

import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.event.LivelinessLostEvent;
import org.omg.dds.core.status.LivelinessLostStatus;
import org.omg.dds.pub.DataWriter;
import org.eclipse.cyclonedds.core.ServiceEnvironmentImpl;

public class LivelinessLostEventImpl<TYPE> extends LivelinessLostEvent<TYPE> {
    private static final long serialVersionUID = -2199224454283393818L;
    private final transient ServiceEnvironmentImpl environment;
    private final LivelinessLostStatus status;

    public LivelinessLostEventImpl(ServiceEnvironmentImpl environment,
            DataWriter<TYPE> source, LivelinessLostStatus status) {
        super(source);
        this.environment = environment;
        this.status = status;
    }

    @Override
    public ServiceEnvironment getEnvironment() {
        return this.environment;
    }

    @Override
    public LivelinessLostStatus getStatus() {
        return this.status;
    }

    @Override
    public LivelinessLostEvent<TYPE> clone() {
        return new LivelinessLostEventImpl<TYPE>(this.environment,
                this.getSource(), this.status);
    }

    @SuppressWarnings("unchecked")
    @Override
    public DataWriter<TYPE> getSource() {
        return (DataWriter<TYPE>) this.source;
    }

}
