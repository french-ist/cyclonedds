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
package org.eclipse.cyclonedds.domain;

import org.eclipse.cyclonedds.core.policy.Scheduling.ListenerScheduling;
import org.eclipse.cyclonedds.core.policy.Scheduling.WatchdogScheduling;

/**
 * OpenSplice-specific extension to
 * {@link org.omg.dds.domain.DomainParticipantQos} with support to control
 * scheduling class and priorities of listener and watchdog threads created by
 * the middleware.
 * 
 * @see org.eclipse.cyclonedds.core.policy.Scheduling
 * @see org.eclipse.cyclonedds.core.policy.Scheduling.ListenerScheduling
 * @see org.eclipse.cyclonedds.core.policy.Scheduling.WatchdogScheduling
 */
public interface DomainParticipantQos extends
        org.omg.dds.domain.DomainParticipantQos {

    /**
     * Scheduling for the Listener thread of a
     * {@link org.omg.dds.domain.DomainParticipant}
     * 
     * @return The scheduling for the Listener thread of a DomainParticipant.
     * 
     * @see org.eclipse.cyclonedds.core.policy.Scheduling.ListenerScheduling
     */
    public ListenerScheduling getListenerScheduling();

    /**
     * Scheduling for the Watchdog thread of a
     * {@link org.omg.dds.domain.DomainParticipant}
     * 
     * @return The scheduling for the Watchdog thread of a DomainParticipant.
     * 
     * @see org.eclipse.cyclonedds.core.policy.Scheduling.WatchdogScheduling
     */
    public WatchdogScheduling getWatchdogScheduling();
}
