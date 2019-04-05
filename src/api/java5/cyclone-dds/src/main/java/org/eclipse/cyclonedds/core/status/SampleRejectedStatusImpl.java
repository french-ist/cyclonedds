/**
  Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 
  This program and the accompanying materials are made available under the
  terms of the Eclipse Public License v. 2.0 which is available at
  http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
  v. 1.0 which is available at
  http://www.eclipse.org/org/documents/edl-v10.php.
 
  SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.cyclonedds.core.status;

import org.omg.dds.core.InstanceHandle;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.status.SampleRejectedStatus;
import org.eclipse.cyclonedds.core.CycloneServiceEnvironment;

public class SampleRejectedStatusImpl extends SampleRejectedStatus {
    private static final long serialVersionUID = 8920010890983446873L;
    private final transient CycloneServiceEnvironment environment;
    private final int totalCount;
    private final int totalCountChange;
    private final Kind lastReason;
    private final InstanceHandle lastInstanceHandle;

    public SampleRejectedStatusImpl(CycloneServiceEnvironment environment,
            int totalCount, int totalCountChange, Kind lastReason, InstanceHandle lastInstanceHandle) {
        this.environment = environment;
        this.totalCount = totalCount;
        this.totalCountChange = totalCountChange;
        this.lastReason = lastReason;
        this.lastInstanceHandle = lastInstanceHandle;
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
    public Kind getLastReason() {
        return this.lastReason;
    }

    @Override
    public InstanceHandle getLastInstanceHandle() {
        return this.lastInstanceHandle;
    }

}
