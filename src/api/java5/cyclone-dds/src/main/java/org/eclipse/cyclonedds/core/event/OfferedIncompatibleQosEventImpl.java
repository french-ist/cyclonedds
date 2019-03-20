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
import org.omg.dds.core.event.OfferedIncompatibleQosEvent;
import org.omg.dds.core.status.OfferedIncompatibleQosStatus;
import org.omg.dds.pub.DataWriter;
import org.eclipse.cyclonedds.core.CycloneServiceEnvironment;

public class OfferedIncompatibleQosEventImpl<TYPE> extends
        OfferedIncompatibleQosEvent<TYPE> {
    private static final long serialVersionUID = 9072235932842085188L;
    private final transient CycloneServiceEnvironment environment;
    private final OfferedIncompatibleQosStatus status;

    public OfferedIncompatibleQosEventImpl(CycloneServiceEnvironment environment,
            DataWriter<TYPE> source, OfferedIncompatibleQosStatus status) {
        super(source);
        this.environment = environment;
        this.status = status;
    }

    @Override
    public ServiceEnvironment getEnvironment() {
        return this.environment;
    }

    @Override
    public OfferedIncompatibleQosStatus getStatus() {
        return this.status;
    }

    @Override
    public OfferedIncompatibleQosEvent<TYPE> clone() {
        return new OfferedIncompatibleQosEventImpl<TYPE>(this.environment,
                this.getSource(), this.status);
    }

    @SuppressWarnings("unchecked")
    @Override
    public DataWriter<TYPE> getSource() {
        return (DataWriter<TYPE>) this.source;
    }

}
