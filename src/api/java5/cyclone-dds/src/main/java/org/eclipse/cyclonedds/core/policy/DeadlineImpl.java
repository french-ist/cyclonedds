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
import org.omg.dds.core.policy.Deadline;
import org.omg.dds.core.policy.QosPolicy;
import org.eclipse.cyclonedds.core.IllegalArgumentExceptionImpl;
import org.eclipse.cyclonedds.core.ServiceEnvironmentImpl;

public class DeadlineImpl extends QosPolicyImpl implements Deadline {
    private static final long serialVersionUID = -3533726043203132941L;
    private final Duration period;

    public DeadlineImpl(ServiceEnvironmentImpl environment) {
        super(environment);
        this.period = environment.getSPI().infiniteDuration();
    }

    public DeadlineImpl(ServiceEnvironmentImpl environment, long period,
            TimeUnit unit) {
        super(environment);
        this.period = Duration.newDuration(period, unit, environment);
    }

    public DeadlineImpl(ServiceEnvironmentImpl environment, Duration period) {
        super(environment);
        this.period = period;

        if (period == null) {
            throw new IllegalArgumentExceptionImpl(environment,
                    "Supplied invalid period.");
        }
    }

    @Override
    public Comparable<Deadline> requestedOfferedContract() {
        return this;
    }

    @Override
    public int compareTo(Deadline o) {
        return this.period.compareTo(o.getPeriod());
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof DeadlineImpl)) {
            return false;
        }
        return this.period.equals(((DeadlineImpl) other).period);
    }

    @Override
    public int hashCode() {
        return 31 * 17 + this.period.hashCode();
    }

    @Override
    public Duration getPeriod() {
        return this.period;
    }

    @Override
    public Deadline withPeriod(Duration period) {
        return new DeadlineImpl(this.environment, period);
    }

    @Override
    public Deadline withPeriod(long period, TimeUnit unit) {
        return new DeadlineImpl(this.environment, period, unit);
    }

    @Override
    public Class<? extends QosPolicy> getPolicyClass() {
        return Deadline.class;
    }
}
