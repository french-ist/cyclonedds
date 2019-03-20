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

import org.omg.dds.core.status.Status;

/**
 * All instances for one or more {@link org.omg.dds.topic.Topic}s have been
 * disposed through {@link org.eclipse.cyclonedds.topic.Topic#disposeAllData()}.
 *
 * @see org.eclipse.cyclonedds.topic.Topic#disposeAllData()
 */
public abstract class AllDataDisposedStatus extends Status{
    private static final long serialVersionUID = 5333898527426448236L;

    /**
     * Total cumulative count of the times all instances for the corresponding
     * {@link org.omg.dds.topic.Topic} have been disposed.
     */
    public abstract int getTotalCount();

    /**
     * The incremental number of times all instances have been disposed for the
     * corresponding {@link org.omg.dds.topic.Topic} since the last time the
     * listener was called or the status was read.
     */
    public abstract int getTotalCountChange();
}
