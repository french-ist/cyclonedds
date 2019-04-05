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
import org.omg.dds.core.policy.QosPolicy;
import org.eclipse.cyclonedds.core.DurationImpl;
import org.eclipse.cyclonedds.core.IllegalArgumentExceptionImpl;
import org.eclipse.cyclonedds.core.CycloneServiceEnvironment;

public class ReaderLifespanImpl extends QosPolicyImpl implements ReaderLifespan {
    private static final long serialVersionUID = 6766092830787145265L;
    private final Duration duration;

    public ReaderLifespanImpl(CycloneServiceEnvironment environment,
            Duration duration) {
        super(environment);

        if (duration == null) {
            throw new IllegalArgumentExceptionImpl(environment,
                    "Supplied invalid duration.");
        }
        this.duration = duration;
    }

    public ReaderLifespanImpl(CycloneServiceEnvironment environment) {
        super(environment);
        this.duration = environment.getSPI().infiniteDuration();
    }

    @Override
    public Duration getDuration() {
        return this.duration;
    }

    @Override
    public ReaderLifespan withDuration(Duration duration) {
        return new ReaderLifespanImpl(this.environment, duration);
    }

    @Override
    public ReaderLifespan withDuration(long duration, TimeUnit unit) {
        return new ReaderLifespanImpl(this.environment, new DurationImpl(
                this.environment, duration, unit));
    }

    @Override
    public Class<? extends QosPolicy> getPolicyClass() {
        return ReaderLifespan.class;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ReaderLifespanImpl)) {
            return false;
        }
        return this.duration.equals(((ReaderLifespanImpl) other).duration);
    }

    @Override
    public int hashCode() {
        return 31 * 17 + this.duration.hashCode();
    }

}
