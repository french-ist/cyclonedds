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
import org.omg.dds.core.event.OfferedDeadlineMissedEvent;
import org.omg.dds.core.status.OfferedDeadlineMissedStatus;
import org.omg.dds.pub.DataWriter;
import org.eclipse.cyclonedds.core.CycloneServiceEnvironment;

public class OfferedDeadlineMissedEventImpl<TYPE> extends
        OfferedDeadlineMissedEvent<TYPE> {
    private static final long serialVersionUID = 2240354051189759674L;
    private final transient CycloneServiceEnvironment environment;
    private final OfferedDeadlineMissedStatus status;

    public OfferedDeadlineMissedEventImpl(CycloneServiceEnvironment environment,
            DataWriter<TYPE> source, OfferedDeadlineMissedStatus status) {
        super(source);
        this.environment = environment;
        this.status = status;
    }

    @Override
    public ServiceEnvironment getEnvironment() {
        return this.environment;
    }

    @Override
    public OfferedDeadlineMissedStatus getStatus() {
        return this.status;
    }

    @Override
    public OfferedDeadlineMissedEvent<TYPE> clone() {
        return new OfferedDeadlineMissedEventImpl<TYPE>(this.environment,
                this.getSource(), this.status);
    }

    @SuppressWarnings("unchecked")
    @Override
    public DataWriter<TYPE> getSource() {
        return (DataWriter<TYPE>) this.source;
    }

}
