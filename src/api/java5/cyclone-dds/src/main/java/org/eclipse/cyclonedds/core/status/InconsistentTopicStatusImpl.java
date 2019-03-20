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

import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.status.InconsistentTopicStatus;
import org.eclipse.cyclonedds.core.CycloneServiceEnvironment;

public class InconsistentTopicStatusImpl extends InconsistentTopicStatus {
    private static final long serialVersionUID = 3165565648541730575L;
    private final transient CycloneServiceEnvironment environment;
    private final int totalCount;
    private final int totalCountChange;

    public InconsistentTopicStatusImpl(CycloneServiceEnvironment environment,
            int totalCount, int totalCountChange) {
        this.environment = environment;
        this.totalCount = totalCount;
        this.totalCountChange = totalCountChange;
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

}
