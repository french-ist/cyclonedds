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
import org.omg.dds.core.event.SubscriptionMatchedEvent;
import org.omg.dds.core.status.SubscriptionMatchedStatus;
import org.omg.dds.sub.DataReader;
import org.eclipse.cyclonedds.core.CycloneServiceEnvironment;

public class SubscriptionMatchedEventImpl<TYPE> extends
        SubscriptionMatchedEvent<TYPE> {
    private static final long serialVersionUID = -5416089725013777311L;
    private final transient CycloneServiceEnvironment environment;
    private final SubscriptionMatchedStatus status;

    public SubscriptionMatchedEventImpl(CycloneServiceEnvironment environment,
            DataReader<TYPE> source, SubscriptionMatchedStatus status) {
        super(source);
        this.environment = environment;
        this.status = status;
    }

    @Override
    public ServiceEnvironment getEnvironment() {
        return this.environment;
    }

    @Override
    public SubscriptionMatchedStatus getStatus() {
        return this.status;
    }

    @Override
    public SubscriptionMatchedEvent<TYPE> clone() {
        return new SubscriptionMatchedEventImpl<TYPE>(this.environment,
                this.getSource(), this.status);
    }

    @SuppressWarnings("unchecked")
    @Override
    public DataReader<TYPE> getSource() {
        return (DataReader<TYPE>) this.source;
    }

}
