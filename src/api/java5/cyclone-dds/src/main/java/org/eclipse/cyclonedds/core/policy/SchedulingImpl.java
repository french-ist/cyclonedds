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
package org.eclipse.cyclonedds.core.policy;

import org.omg.dds.core.policy.QosPolicy;
import org.eclipse.cyclonedds.core.IllegalArgumentExceptionImpl;
import org.eclipse.cyclonedds.core.CycloneServiceEnvironment;
import org.eclipse.cyclonedds.core.policy.Scheduling.ListenerScheduling;
import org.eclipse.cyclonedds.core.policy.Scheduling.WatchdogScheduling;

public class SchedulingImpl extends QosPolicyImpl implements
        ListenerScheduling, WatchdogScheduling {
    private static final long serialVersionUID = -4704717290713490662L;
    private final int priority;
    private final SchedulingKind schedulingKind;
    private final SchedulingClass schedulingClass;

    public SchedulingImpl(CycloneServiceEnvironment environment) {
        super(environment);
        this.priority = 0;
        this.schedulingKind = SchedulingKind.ABSOLUTE;
        this.schedulingClass = SchedulingClass.DEFAULT;
    }

    public SchedulingImpl(CycloneServiceEnvironment environment,
            SchedulingClass schedulingClass, SchedulingKind schedulingKind,
            int priority) {
        super(environment);

        if (schedulingClass == null) {
            throw new IllegalArgumentExceptionImpl(environment,
                    "Invalid scheduling class supplied.");
        }
        if (schedulingKind == null) {
            throw new IllegalArgumentExceptionImpl(environment,
                    "Invalid scheduling kind supplied.");
        }
        this.schedulingClass = schedulingClass;
        this.schedulingKind = schedulingKind;
        this.priority = priority;
    }

    @Override
    public int getPriority() {
        return this.priority;
    }

    @Override
    public SchedulingKind getKind() {
        return this.schedulingKind;
    }

    @Override
    public SchedulingClass getSchedulingClass() {
        return this.schedulingClass;
    }

    @Override
    public Scheduling withPriority(int priority) {
        return new SchedulingImpl(this.environment, this.schedulingClass,
                this.schedulingKind, priority);
    }

    @Override
    public Scheduling withKind(SchedulingKind schedulingKind) {
        return new SchedulingImpl(this.environment, this.schedulingClass,
                schedulingKind, this.priority);
    }

    @Override
    public Scheduling withSchedulingClass(SchedulingClass schedulingClass) {
        return new SchedulingImpl(this.environment, schedulingClass,
                this.schedulingKind, this.priority);
    }

    @Override
    public Class<? extends QosPolicy> getPolicyClass() {
        return Scheduling.class;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof SchedulingImpl)) {
            return false;
        }
        SchedulingImpl s = (SchedulingImpl) other;

        if (s.priority != this.priority) {
            return false;
        }
        if (s.schedulingClass != this.schedulingClass) {
            return false;
        }
        return (s.schedulingKind == this.schedulingKind);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 17;

        result = prime * result + this.priority;
        result = prime * result + this.schedulingClass.hashCode();

        return prime * result + this.schedulingKind.hashCode();
    }

}
