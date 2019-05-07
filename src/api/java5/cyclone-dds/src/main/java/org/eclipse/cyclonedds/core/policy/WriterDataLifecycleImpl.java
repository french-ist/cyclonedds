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
import org.eclipse.cyclonedds.core.IllegalArgumentExceptionImpl;
import org.eclipse.cyclonedds.core.ServiceEnvironmentImpl;

public class WriterDataLifecycleImpl extends QosPolicyImpl implements
        WriterDataLifecycle {
    private static final long serialVersionUID = -3230343206569872870L;
    private final boolean autoDisposeUnregisteredInstances;
    private final Duration autoPurgeSuspendedSamplesDelay;
    private final Duration autoUnregisterInstanceDelay;

    public WriterDataLifecycleImpl(ServiceEnvironmentImpl environment) {
        super(environment);
        this.autoDisposeUnregisteredInstances = true;
        this.autoPurgeSuspendedSamplesDelay = this.environment.getSPI()
                .infiniteDuration();
        this.autoUnregisterInstanceDelay = this.environment.getSPI()
                .infiniteDuration();
    }

    public WriterDataLifecycleImpl(ServiceEnvironmentImpl environment,
            boolean autoDisposeUnregisteredInstances,
            Duration autoPurgeSuspendedSamplesDelay,
            Duration autoUnregisterInstanceDelay) {
        super(environment);
        this.autoDisposeUnregisteredInstances = autoDisposeUnregisteredInstances;

        if (autoPurgeSuspendedSamplesDelay == null) {
            throw new IllegalArgumentExceptionImpl(environment,
                    "Supplied autoPurgeSuspendedSamplesDelay duration invalid.");
        }
        if (autoUnregisterInstanceDelay == null) {
            throw new IllegalArgumentExceptionImpl(environment,
                    "Supplied autoUnregisterInstanceDelay duration invalid.");
        }
        this.autoPurgeSuspendedSamplesDelay = autoPurgeSuspendedSamplesDelay;
        this.autoUnregisterInstanceDelay = autoUnregisterInstanceDelay;
    }

    @Override
    public boolean isAutDisposeUnregisteredInstances() {
        return this.autoDisposeUnregisteredInstances;
    }

    @Override
    public WriterDataLifecycle withAutDisposeUnregisteredInstances(
            boolean autoDisposeUnregisteredInstances) {
        return new WriterDataLifecycleImpl(this.environment,
                autoDisposeUnregisteredInstances,
                this.autoPurgeSuspendedSamplesDelay,
                this.autoUnregisterInstanceDelay);
    }

    @Override
    public Class<? extends QosPolicy> getPolicyClass() {
        return WriterDataLifecycle.class;
    }

    @Override
    public Duration getAutoPurgeSuspendedSamplesDelay() {
        return this.autoPurgeSuspendedSamplesDelay;
    }

    @Override
    public Duration getAutoUnregisterInstanceDelay() {
        return this.autoUnregisterInstanceDelay;
    }

    @Override
    public WriterDataLifecycle withAutoPurgeSuspendedSamplesDelay(
            Duration duration) {
        return new WriterDataLifecycleImpl(this.environment,
                this.autoDisposeUnregisteredInstances, duration,
                this.autoUnregisterInstanceDelay);
    }

    @Override
    public WriterDataLifecycle withAutoPurgeSuspendedSamplesDelay(
            long duration, TimeUnit unit) {
        return withAutoPurgeSuspendedSamplesDelay(this.environment.getSPI()
                .newDuration(duration, unit));
    }

    @Override
    public WriterDataLifecycle withAutoUnregisterInstanceDelay(Duration duration) {
        return new WriterDataLifecycleImpl(this.environment,
                this.autoDisposeUnregisteredInstances,
                this.autoPurgeSuspendedSamplesDelay, duration);
    }

    @Override
    public WriterDataLifecycle withAutoUnregisterInstanceDelay(long duration,
            TimeUnit unit) {
        return withAutoUnregisterInstanceDelay(this.environment.getSPI()
                .newDuration(duration, unit));
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof WriterDataLifecycleImpl)) {
            return false;
        }
        WriterDataLifecycleImpl w = (WriterDataLifecycleImpl) other;

        if (w.autoDisposeUnregisteredInstances != this.autoDisposeUnregisteredInstances) {
            return false;
        }
        if (!w.autoPurgeSuspendedSamplesDelay
                .equals(this.autoPurgeSuspendedSamplesDelay)) {
            return false;
        }
        return w.autoUnregisterInstanceDelay
                .equals(this.autoUnregisterInstanceDelay);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 17;

        result = prime * result
                + (this.autoDisposeUnregisteredInstances ? 1 : 0);
        result = prime * result
                + this.autoPurgeSuspendedSamplesDelay.hashCode();

        return prime * result + this.autoUnregisterInstanceDelay.hashCode();
    }

}
