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
import org.omg.dds.core.status.OfferedDeadlineMissedStatus;
import org.eclipse.cyclonedds.core.ServiceEnvironmentImpl;

public class OfferedDeadlineMissedStatusImpl extends
        OfferedDeadlineMissedStatus {
    private static final long serialVersionUID = -3228413512117529180L;
    private final transient ServiceEnvironmentImpl environment;
    private final int totalCount;
    private final int totalCountChange;
    private final InstanceHandle lastInstanceHandle;

    public OfferedDeadlineMissedStatusImpl(ServiceEnvironmentImpl environment,
            int totalCount, int totalCountChange,
            InstanceHandle lastInstanceHandle) {
        this.environment = environment;
        this.totalCount = totalCount;
        this.totalCountChange = totalCountChange;
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
    public InstanceHandle getLastInstanceHandle() {
        return this.lastInstanceHandle;
    }

}
