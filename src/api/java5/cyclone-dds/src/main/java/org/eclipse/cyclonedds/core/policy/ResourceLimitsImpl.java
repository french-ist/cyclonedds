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
import org.omg.dds.core.policy.ResourceLimits;
import org.eclipse.cyclonedds.core.ServiceEnvironmentImpl;

public class ResourceLimitsImpl extends QosPolicyImpl implements ResourceLimits {
    private static final long serialVersionUID = 918915709322634268L;
    private final int maxSamples;
    private final int maxInstances;
    private final int maxSamplesPerInstance;

    public ResourceLimitsImpl(ServiceEnvironmentImpl environment) {
        super(environment);
        this.maxSamples = -1;
        this.maxInstances = -1;
        this.maxSamplesPerInstance = -1;
    }

    public ResourceLimitsImpl(ServiceEnvironmentImpl environment,
            int maxSamples, int maxInstances, int maxSamplesPerInstance) {
        super(environment);
        this.maxSamples = maxSamples;
        this.maxInstances = maxInstances;
        this.maxSamplesPerInstance = maxSamplesPerInstance;
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
    public ResourceLimits withMaxSamples(int maxSamples) {
        return new ResourceLimitsImpl(this.environment, maxSamples,
                this.maxInstances, this.maxSamplesPerInstance);
    }

    @Override
    public ResourceLimits withMaxInstances(int maxInstances) {
        return new ResourceLimitsImpl(this.environment, this.maxSamples,
                maxInstances, this.maxSamplesPerInstance);
    }

    @Override
    public ResourceLimits withMaxSamplesPerInstance(int maxSamplesPerInstance) {
        return new ResourceLimitsImpl(this.environment, this.maxSamples,
                this.maxInstances, maxSamplesPerInstance);
    }

    @Override
    public Class<? extends QosPolicy> getPolicyClass() {
        return ResourceLimits.class;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ResourceLimitsImpl)) {
            return false;
        }
        ResourceLimitsImpl r = (ResourceLimitsImpl) other;

        if (this.maxInstances != r.maxInstances) {
            return false;
        }
        if (this.maxSamples != r.maxSamples) {
            return false;
        }
        return (this.maxSamplesPerInstance == r.maxSamplesPerInstance);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 17;

        result = prime * result + this.maxInstances;
        result = prime * result + this.maxSamples;

        return prime * result + this.maxSamplesPerInstance;
    }
}
