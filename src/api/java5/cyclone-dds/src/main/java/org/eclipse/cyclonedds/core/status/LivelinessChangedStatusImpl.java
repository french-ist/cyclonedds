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
import org.omg.dds.core.status.LivelinessChangedStatus;
import org.eclipse.cyclonedds.core.CycloneServiceEnvironment;

public class LivelinessChangedStatusImpl extends LivelinessChangedStatus {
    private static final long serialVersionUID = -8177984046769155908L;
    private final transient CycloneServiceEnvironment environment;
    private final int aliveCount;
    private final int notAliveCount;
    private final int aliveCountChange;
    private final int notAliveCountChange;
    private final InstanceHandle lastPublicationHandle;

    public LivelinessChangedStatusImpl(CycloneServiceEnvironment environment,
            int aliveCount, int notAliveCount, int aliveCountChange,
            int notAliveCountChange, InstanceHandle lastPublicationHandle) {
        this.environment = environment;
        this.aliveCount = aliveCount;
        this.notAliveCount = notAliveCount;
        this.aliveCountChange = aliveCountChange;
        this.notAliveCountChange = notAliveCountChange;
        this.lastPublicationHandle = lastPublicationHandle;
    }

    @Override
    public ServiceEnvironment getEnvironment() {
        return this.environment;
    }

    @Override
    public int getAliveCount() {
        return this.aliveCount;
    }

    @Override
    public int getNotAliveCount() {
        return this.notAliveCount;
    }

    @Override
    public int getAliveCountChange() {
        return this.aliveCountChange;
    }

    @Override
    public int getNotAliveCountChange() {
        return this.notAliveCountChange;
    }

    @Override
    public InstanceHandle getLastPublicationHandle() {
        return this.lastPublicationHandle;
    }

}
