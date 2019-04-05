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
import org.omg.dds.core.policy.TimeBasedFilter;
import org.eclipse.cyclonedds.core.IllegalArgumentExceptionImpl;
import org.eclipse.cyclonedds.core.CycloneServiceEnvironment;

public class TimeBasedFilterImpl extends QosPolicyImpl implements
        TimeBasedFilter {
    private static final long serialVersionUID = -7710151999422912449L;
    private final Duration minimumSeparation;

    public TimeBasedFilterImpl(CycloneServiceEnvironment environment) {
        super(environment);
        this.minimumSeparation = environment.getSPI().zeroDuration();
    }

    public TimeBasedFilterImpl(CycloneServiceEnvironment environment,
            Duration minimumSeparation) {
        super(environment);

        if(minimumSeparation == null){
            throw new IllegalArgumentExceptionImpl(environment,
                    "Supplied invalid mimimumSeparation duration.");
        }
        this.minimumSeparation = minimumSeparation;
    }

    @Override
    public Duration getMinimumSeparation() {
        return this.minimumSeparation;
    }

    @Override
    public TimeBasedFilter withMinimumSeparation(Duration minimumSeparation) {
        return new TimeBasedFilterImpl(this.environment, minimumSeparation);
    }

    @Override
    public TimeBasedFilter withMinimumSeparation(long minimumSeparation,
            TimeUnit unit) {
        return new TimeBasedFilterImpl(this.environment, this.environment
                .getSPI().newDuration(minimumSeparation, unit));
    }

    @Override
    public Class<? extends QosPolicy> getPolicyClass() {
        return TimeBasedFilter.class;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof TimeBasedFilterImpl)) {
            return false;
        }
        return this.minimumSeparation
                .equals(((TimeBasedFilterImpl) other).minimumSeparation);
    }

    @Override
    public int hashCode() {
        return 31 * 17 + this.minimumSeparation.hashCode();
    }
}
