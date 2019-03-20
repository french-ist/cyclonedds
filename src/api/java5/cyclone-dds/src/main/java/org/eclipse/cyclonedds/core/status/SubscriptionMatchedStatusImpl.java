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
package org.eclipse.cyclonedds.core.status;

import org.omg.dds.core.InstanceHandle;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.status.SubscriptionMatchedStatus;
import org.eclipse.cyclonedds.core.CycloneServiceEnvironment;

public class SubscriptionMatchedStatusImpl extends SubscriptionMatchedStatus {
    private static final long serialVersionUID = 5672304659614131159L;
    private final transient CycloneServiceEnvironment environment;
    private final int totalCount;
    private final int totalCountChange;
    private final int currentCount;
    private final int currentCountChange;
    private final InstanceHandle lastPublicationHandle;

    public SubscriptionMatchedStatusImpl(CycloneServiceEnvironment environment,
            int totalCount, int totalCountChange, int currentCount,
            int currentCountChange, InstanceHandle lastPublicationHandle) {
        this.environment = environment;
        this.totalCount = totalCount;
        this.totalCountChange = totalCountChange;
        this.currentCount = currentCount;
        this.currentCountChange = currentCountChange;
        this.lastPublicationHandle = lastPublicationHandle;
    }

    @Override
    public ServiceEnvironment getEnvironment() {
        return this.environment;
    }

    @Override
    public int getTotalCount() {
        return this.totalCount;
    }

    @Override
    public int getTotalCountChange() {
        return this.totalCountChange;
    }

    @Override
    public int getCurrentCount() {
        return this.currentCount;
    }

    @Override
    public int getCurrentCountChange() {
        return this.currentCountChange;
    }

    @Override
    public InstanceHandle getLastPublicationHandle() {
        return this.lastPublicationHandle;
    }
}
