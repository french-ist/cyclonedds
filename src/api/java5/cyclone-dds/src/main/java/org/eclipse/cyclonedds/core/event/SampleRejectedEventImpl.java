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
import org.omg.dds.core.event.SampleRejectedEvent;
import org.omg.dds.core.status.SampleRejectedStatus;
import org.omg.dds.sub.DataReader;
import org.eclipse.cyclonedds.core.CycloneServiceEnvironment;

public class SampleRejectedEventImpl<TYPE> extends SampleRejectedEvent<TYPE>{
    private static final long serialVersionUID = -5370093562127232024L;
    private final transient CycloneServiceEnvironment environment;
    private final SampleRejectedStatus status;

    public SampleRejectedEventImpl(CycloneServiceEnvironment environment,
            DataReader<TYPE> source, SampleRejectedStatus status) {
        super(source);
        this.environment = environment;
        this.status = status;
    }

    @Override
    public ServiceEnvironment getEnvironment() {
        return this.environment;
    }

    @Override
    public SampleRejectedStatus getStatus() {
        return this.status;
    }

    @Override
    public SampleRejectedEvent<TYPE> clone() {
        return new SampleRejectedEventImpl<TYPE>(this.environment, this.getSource(), this.status);
    }

    @SuppressWarnings("unchecked")
    @Override
    public DataReader<TYPE> getSource() {
        return (DataReader<TYPE>)this.source;
    }

}
