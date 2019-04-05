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
import org.omg.dds.core.policy.DurabilityService;
import org.omg.dds.core.policy.History;
import org.omg.dds.core.policy.History.Kind;
import org.eclipse.cyclonedds.core.IllegalArgumentExceptionImpl;
import org.eclipse.cyclonedds.core.CycloneServiceEnvironment;
import org.omg.dds.core.policy.QosPolicy;

public class DurabilityServiceImpl extends QosPolicyImpl implements
        DurabilityService {
    private static final long serialVersionUID = -3906397729567497050L;
    private final Duration serviceCleanupDelay;
    private final History history;
    private final int maxSamples;
    private final int maxInstances;
    private final int maxSamplesPerInstance;

    public DurabilityServiceImpl(CycloneServiceEnvironment environment) {
        super(environment);
        this.serviceCleanupDelay = environment.getSPI().zeroDuration();
        this.history = new HistoryImpl(environment, Kind.KEEP_LAST, 1);
        this.maxSamples = -1;
        this.maxInstances = -1;
        this.maxSamplesPerInstance = -1;
    }

    public DurabilityServiceImpl(CycloneServiceEnvironment environment,
            Duration serviceCleanupDelay, Kind historyKind, int historyDepth,
            int maxSamples, int maxInstances, int maxSamplesPerInstance) {
        super(environment);
        this.serviceCleanupDelay = serviceCleanupDelay;
        this.history = new HistoryImpl(environment, historyKind, historyDepth);
        this.maxSamples = maxSamples;
        this.maxInstances = maxInstances;
        this.maxSamplesPerInstance = maxSamplesPerInstance;

        if (serviceCleanupDelay == null) {
            throw new IllegalArgumentExceptionImpl(environment,
                    "Supplied invalid service cleanup delay.");
        }
    }

    @Override
    public Duration getServiceCleanupDelay() {
        return this.serviceCleanupDelay;
    }

    @Override
    public Kind getHistoryKind() {
        return this.history.getKind();
    }

    @Override
    public int getHistoryDepth() {
        return this.history.getDepth();
    }

    @Override
    public int getMaxSamples() {
        return this.maxSamples;
    }

    @Override
    public int getMaxInstances() {
        return this.maxInstances;
    }

    @Override
    public int getMaxSamplesPerInstance() {
        return this.maxSamplesPerInstance;
    }

    @Override
    public DurabilityService withServiceCleanupDelay(
            Duration serviceCleanupDelay) {
        return new DurabilityServiceImpl(this.environment, serviceCleanupDelay,
                this.history.getKind(), this.history.getDepth(), this.maxSamples,
                this.maxInstances, this.maxSamplesPerInstance);
    }

    @Override
    public DurabilityService withServiceCleanupDelay(long serviceCleanupDelay,
            TimeUnit unit) {
        return new DurabilityServiceImpl(this.environment, this.environment
                .getSPI().newDuration(serviceCleanupDelay, unit),
                this.history.getKind(), this.history.getDepth(), this.maxSamples,
                this.maxInstances, this.maxSamplesPerInstance);
    }

    @Override
    public DurabilityService withHistoryKind(Kind historyKind) {
        return new DurabilityServiceImpl(this.environment,
                this.serviceCleanupDelay, historyKind, this.history.getDepth(),
                this.maxSamples, this.maxInstances, this.maxSamplesPerInstance);
    }

    @Override
    public DurabilityService withHistoryDepth(int historyDepth) {
        return new DurabilityServiceImpl(this.environment,
                this.serviceCleanupDelay, this.history.getKind(), historyDepth,
                this.maxSamples, this.maxInstances, this.maxSamplesPerInstance);
    }

    @Override
    public DurabilityService withMaxSamples(int maxSamples) {
        return new DurabilityServiceImpl(this.environment,
                this.serviceCleanupDelay, this.history.getKind(), this.history.getDepth(),
                maxSamples, this.maxInstances, this.maxSamplesPerInstance);
    }

    @Override
    public DurabilityService withMaxInstances(int maxInstances) {
        return new DurabilityServiceImpl(this.environment,
                this.serviceCleanupDelay, this.history.getKind(), this.history.getDepth(),
                this.maxSamples, maxInstances, this.maxSamplesPerInstance);
    }

    @Override
    public DurabilityService withMaxSamplesPerInstance(int maxSamplesPerInstance) {
        return new DurabilityServiceImpl(this.environment,
                this.serviceCleanupDelay, this.history.getKind(), this.history.getDepth(),
                this.maxSamples, this.maxInstances, maxSamplesPerInstance);
    }

    @Override
    public Class<? extends QosPolicy> getPolicyClass() {
        return DurabilityService.class;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof DurabilityServiceImpl)) {
            return false;
        }
        DurabilityServiceImpl d = (DurabilityServiceImpl) other;

        if (!this.history.equals(d.history)) {
            return false;
        }
        if (this.maxInstances != d.maxInstances) {
            return false;
        }
        if (this.maxSamples != d.maxSamples) {
            return false;
        }
        if (this.maxSamplesPerInstance != d.maxSamplesPerInstance) {
            return false;
        }
        return this.serviceCleanupDelay.equals(d.serviceCleanupDelay);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 17;

        result = prime * result + this.history.hashCode();
        result = prime * result + this.maxInstances;
        result = prime * result + this.maxSamples;
        result = prime * result + this.maxSamplesPerInstance;
        result = prime * result + this.serviceCleanupDelay.hashCode();

        return result;
    }
}
