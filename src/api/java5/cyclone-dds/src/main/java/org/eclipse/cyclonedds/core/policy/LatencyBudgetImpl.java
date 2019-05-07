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

import java.util.concurrent.TimeUnit;

import org.omg.dds.core.Duration;
import org.omg.dds.core.policy.LatencyBudget;
import org.omg.dds.core.policy.QosPolicy;
import org.eclipse.cyclonedds.core.IllegalArgumentExceptionImpl;
import org.eclipse.cyclonedds.core.ServiceEnvironmentImpl;

public class LatencyBudgetImpl extends QosPolicyImpl implements LatencyBudget {
    private static final long serialVersionUID = 1583305102265712684L;
    private final Duration duration;

    public LatencyBudgetImpl(ServiceEnvironmentImpl environment) {
        super(environment);
        this.duration = environment.getSPI().zeroDuration();
    }

    public LatencyBudgetImpl(ServiceEnvironmentImpl environment,
            Duration duration) {
        super(environment);

        if (duration == null) {
            throw new IllegalArgumentExceptionImpl(environment,
                    "Supplied invalid duration.");
        }
        this.duration = duration;
    }

    @Override
    public Comparable<LatencyBudget> requestedOfferedContract() {
        return this;
    }

    @Override
    public int compareTo(LatencyBudget o) {
        return this.duration.compareTo(o.getDuration());
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof LatencyBudgetImpl)) {
            return false;
        }
        return this.duration.equals(((LatencyBudgetImpl) other).duration);
    }

    @Override
    public Duration getDuration() {
        return this.duration;
    }

    @Override
    public LatencyBudget withDuration(Duration duration) {
        return new LatencyBudgetImpl(this.environment, duration);
    }

    @Override
    public LatencyBudget withDuration(long duration, TimeUnit unit) {
        return new LatencyBudgetImpl(this.environment, this.environment
                .getSPI().newDuration(duration, unit));
    }

    @Override
    public Class<? extends QosPolicy> getPolicyClass() {
        return LatencyBudget.class;
    }

    @Override
    public int hashCode() {
        return 31 * 17 + this.duration.hashCode();
    }

}
