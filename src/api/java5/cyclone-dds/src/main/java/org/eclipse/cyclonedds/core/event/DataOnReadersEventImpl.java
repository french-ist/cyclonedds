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
package org.eclipse.cyclonedds.core.event;

import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.event.DataOnReadersEvent;
import org.omg.dds.core.status.DataOnReadersStatus;
import org.omg.dds.sub.Subscriber;
import org.eclipse.cyclonedds.core.CycloneServiceEnvironment;

public class DataOnReadersEventImpl extends DataOnReadersEvent {
    private static final long serialVersionUID = 1343357191707849872L;
    private final transient CycloneServiceEnvironment environment;
    private final DataOnReadersStatus status;

    public DataOnReadersEventImpl(CycloneServiceEnvironment environment,
            Subscriber source, DataOnReadersStatus status) {
        super(source);
        this.environment = environment;
        this.status = status;
    }

    @Override
    public ServiceEnvironment getEnvironment() {
        return this.environment;
    }

    @Override
    public DataOnReadersStatus getStatus() {
        return this.status;
    }

    @Override
    public DataOnReadersEvent clone() {
        return new DataOnReadersEventImpl(this.environment, this.getSource(),
                this.status);
    }

    @Override
    public Subscriber getSource() {
        return (Subscriber)this.source;
    }

}
