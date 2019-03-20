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

import org.omg.dds.core.event.StatusChangedEvent;
import org.omg.dds.topic.Topic;
import org.eclipse.cyclonedds.core.status.AllDataDisposedStatus;

/**
 * All instances published for this topic have been disposed by means of a call
 * to {@link org.eclipse.cyclonedds.topic.Topic#disposeAllData()}
 *
 * @param <TYPE>
 *            The data type of the source {@link org.omg.dds.topic.Topic}
 *
 * @see AllDataDisposedStatus
 */
public abstract class AllDataDisposedEvent<TYPE> extends
        StatusChangedEvent<Topic<TYPE>> {
    private static final long serialVersionUID = 6035423504123023667L;

    protected AllDataDisposedEvent(Topic<TYPE> source) {
        super(source);
    }

    /**
     * Get access to the corresponding AllDataDisposedStatus.
     *
     * @return The corresponding AllDataDisposedStatus
     */
    public abstract AllDataDisposedStatus getStatus();

    @Override
    public abstract AllDataDisposedEvent<TYPE> clone();
}
