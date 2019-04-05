/**
  Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 
  This program and the accompanying materials are made available under the
  terms of the Eclipse Public License v. 2.0 which is available at
  http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
  v. 1.0 which is available at
  http://www.eclipse.org/org/documents/edl-v10.php.
 
  SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.cyclonedds.core.policy;

import org.omg.dds.core.policy.QosPolicy;

/**
 * This QosPolicy specifies the scheduling parameters that will be used for a
 * thread that is spawned by the DomainParticipant.
 * <p>
 * Note that some scheduling parameters may not be supported by the underlying
 * Operating System, or that you may need special privileges to select
 * particular settings.
 * <p>
 * Although the behaviour of the SchedulingClass is highly dependent on the
 * underlying OS, in general it can be said that when running in a Timesharing
 * class your thread will have to yield execution to other threads of equal
 * priority regularly. In a Realtime class your thread normally runs until
 * completion, and can only be pre-empted by higher priority threads. Often the
 * highest range of priorities is not accessible through a Timesharing Class.
 * <p>
 * The SchedulingKind determines whether the specified priority should be
 * interpreted as an absolute priority, or whether it should be interpreted
 * relative to the priority of its creator, in this case the priority of the
 * thread that created the {@link org.omg.dds.domain.DomainParticipant}.
 */
public interface Scheduling extends QosPolicy.ForDomainParticipant {
    /**
     * @return the value of the priority.
     */
    public int getPriority();

    /**
     * @return the value of the SchedulingKind.
     */
    public SchedulingKind getKind();

    /**
     * @return the value of the SchedulingClass.
     */
    public SchedulingClass getSchedulingClass();

    /**
     * Copy this policy and override the value of the priority that will be
     * assigned to threads spawned by the
     * {@link org.omg.dds.domain.DomainParticipant}. Threads can only be spawned
     * with priorities that are supported by the underlying Operating System.
     *
     * @param priority          The priority to be set.
     * @return a new Scheduling policy
     */
    public Scheduling withPriority(int priority);

    /**
     * Copy this policy and override the value of the property.
     * @param schedulingKind    Specifies the scheduling type, which may be either RELATIVE or ABSOLUTE.
     * @return a new Scheduling policy
     */
    public Scheduling withKind(SchedulingKind schedulingKind);

    /**
     * Copy this policy and override the value of the property.
     * @param schedulingClass   Specifies the scheduling class used by the Operating System,
     *                          which may be DEFAULT, TIMESHARING or REALTIME. Threads can only
     *                          be spawned within the scheduling classes that are supported by
     *                          the underlying Operating System.
     * @return a new Scheduling policy
     */
    public Scheduling withSchedulingClass(SchedulingClass schedulingClass);

    /**
     * specifies the priority type, which may be either RELATIVE or ABSOLUTE.
     */
    public enum SchedulingKind {
        ABSOLUTE, RELATIVE
    }

    /**
     * specifies the scheduling class used by the Operating System, which may be
     * DEFAULT, REALTIME or TIMESHARING. Threads can only be spawned within the
     * scheduling classes that are supported by the underlying Operating System.
     */
    public enum SchedulingClass {
        DEFAULT, REALTIME, TIMESHARING
    }

    /**
     * Scheduling for the Watchdog thread of a
     * {@link org.omg.dds.domain.DomainParticipant}
     *
     * @see Scheduling
     */
    public static interface WatchdogScheduling extends Scheduling {
    };

    /**
     * Scheduling for the Listener thread of a
     * {@link org.omg.dds.domain.DomainParticipant}
     *
     * @see Scheduling
     */
    public static interface ListenerScheduling extends Scheduling {
    };
}
