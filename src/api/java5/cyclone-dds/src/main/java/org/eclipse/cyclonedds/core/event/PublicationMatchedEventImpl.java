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
import org.omg.dds.core.event.PublicationMatchedEvent;
import org.omg.dds.core.status.PublicationMatchedStatus;
import org.omg.dds.pub.DataWriter;
import org.eclipse.cyclonedds.core.ServiceEnvironmentImpl;

public class PublicationMatchedEventImpl<TYPE> extends PublicationMatchedEvent<TYPE> {
    private static final long serialVersionUID = -8206681336933599009L;
    private final transient ServiceEnvironmentImpl environment;
    private final PublicationMatchedStatus status;

    public PublicationMatchedEventImpl(ServiceEnvironmentImpl environment,
            DataWriter<TYPE> source, PublicationMatchedStatus status) {
        super(source);
        this.environment = environment;
        this.status = status;
    }

    @Override
    public ServiceEnvironment getEnvironment() {
        return this.environment;
    }

    @Override
    public PublicationMatchedStatus getStatus() {
        return this.status;
    }

    @Override
    public PublicationMatchedEvent<TYPE> clone() {
        return new PublicationMatchedEventImpl<TYPE>(this.environment, this.getSource(), this.status);
    }

    @SuppressWarnings("unchecked")
    @Override
    public DataWriter<TYPE> getSource() {
        return (DataWriter<TYPE>)this.source;
    }

}
