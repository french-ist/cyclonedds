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
import org.omg.dds.core.event.RequestedDeadlineMissedEvent;
import org.omg.dds.core.status.RequestedDeadlineMissedStatus;
import org.omg.dds.sub.DataReader;
import org.eclipse.cyclonedds.core.ServiceEnvironmentImpl;

public class RequestedDeadlineMissedEventImpl<TYPE> extends RequestedDeadlineMissedEvent<TYPE> {
    private static final long serialVersionUID = 2098881791326037014L;
    private final transient ServiceEnvironmentImpl environment;
    private final RequestedDeadlineMissedStatus status;

    public RequestedDeadlineMissedEventImpl(ServiceEnvironmentImpl environment,
            DataReader<TYPE> source, RequestedDeadlineMissedStatus status) {
        super(source);
        this.environment = environment;
        this.status = status;
    }

    @Override
    public ServiceEnvironment getEnvironment() {
        return this.environment;
    }

    @Override
    public RequestedDeadlineMissedStatus getStatus() {
        return this.status;
    }

    @Override
    public RequestedDeadlineMissedEvent<TYPE> clone() {
        return new RequestedDeadlineMissedEventImpl<TYPE>(this.environment, this.getSource(), this.status);
    }

    @SuppressWarnings("unchecked")
    @Override
    public DataReader<TYPE> getSource() {
        return (DataReader<TYPE>)this.source;
    }

}
