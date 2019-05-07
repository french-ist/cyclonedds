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
import org.omg.dds.core.event.RequestedIncompatibleQosEvent;
import org.omg.dds.core.status.RequestedIncompatibleQosStatus;
import org.omg.dds.sub.DataReader;
import org.eclipse.cyclonedds.core.ServiceEnvironmentImpl;

public class RequestedIncompatibleQosEventImpl<TYPE> extends RequestedIncompatibleQosEvent<TYPE> {
    private static final long serialVersionUID = 3194689213798347497L;
    private final transient ServiceEnvironmentImpl environment;
    private final RequestedIncompatibleQosStatus status;

    public RequestedIncompatibleQosEventImpl(ServiceEnvironmentImpl environment,
            DataReader<TYPE> source, RequestedIncompatibleQosStatus status) {
        super(source);
        this.environment = environment;
        this.status = status;
    }

    @Override
    public ServiceEnvironment getEnvironment() {
        return this.environment;
    }

    @Override
    public RequestedIncompatibleQosStatus getStatus() {
        return this.status;
    }

    @Override
    public RequestedIncompatibleQosEvent<TYPE> clone() {
        return new RequestedIncompatibleQosEventImpl<TYPE>(this.environment, this.getSource(), this.status);
    }

    @SuppressWarnings("unchecked")
    @Override
    public DataReader<TYPE> getSource() {
        return (DataReader<TYPE>)this.source;
    }
}
