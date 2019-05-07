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
import org.omg.dds.topic.Topic;
import org.eclipse.cyclonedds.core.ServiceEnvironmentImpl;
import org.eclipse.cyclonedds.core.status.AllDataDisposedStatus;

public class AllDataDisposedEventImpl<TYPE> extends AllDataDisposedEvent<TYPE> {
    private static final long serialVersionUID = -227566280161289987L;
    private final transient ServiceEnvironmentImpl environment;
    private final AllDataDisposedStatus status;

    public AllDataDisposedEventImpl(ServiceEnvironmentImpl environment,
            Topic<TYPE> source, AllDataDisposedStatus status) {
        super(source);
        this.environment = environment;
        this.status = status;
    }

    @Override
    public ServiceEnvironment getEnvironment() {
        return this.environment;
    }

    @Override
    public AllDataDisposedStatus getStatus() {
        return this.status;
    }

    @Override
    public AllDataDisposedEvent<TYPE> clone() {
        return new AllDataDisposedEventImpl<TYPE>(this.environment,
                this.getSource(), this.status);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Topic<TYPE> getSource() {
        return (Topic<TYPE>) this.source;
    }

}
