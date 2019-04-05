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

import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.policy.DataRepresentation;
import org.omg.dds.core.policy.TypeConsistencyEnforcement;
import org.omg.dds.core.policy.TypeConsistencyEnforcement.Kind;
import org.eclipse.cyclonedds.core.CycloneServiceEnvironment;
import org.eclipse.cyclonedds.core.policy.Scheduling.WatchdogScheduling;

public class PolicyFactoryImpl extends PolicyFactory {
    private final CycloneServiceEnvironment environment;

    public PolicyFactoryImpl(CycloneServiceEnvironment environment) {
        this.environment = environment;
    }

    @Override
    public ServiceEnvironment getEnvironment() {
        return this.environment;
    }

    @Override
    public org.omg.dds.core.policy.Durability Durability() {
        return new DurabilityImpl(this.environment);
    }

    @Override
    public org.omg.dds.core.policy.Deadline Deadline() {
        return new DeadlineImpl(this.environment);
    }

    @Override
    public org.omg.dds.core.policy.LatencyBudget LatencyBudget() {
        return new LatencyBudgetImpl(this.environment);
    }

    @Override
    public org.omg.dds.core.policy.Liveliness Liveliness() {
        return new LivelinessImpl(this.environment);
    }

    @Override
    public org.omg.dds.core.policy.DestinationOrder DestinationOrder() {
        return new DestinationOrderImpl(this.environment);
    }

    @Override
    public org.omg.dds.core.policy.History History() {
        return new HistoryImpl(this.environment);
    }

    @Override
    public org.omg.dds.core.policy.ResourceLimits ResourceLimits() {
        return new ResourceLimitsImpl(this.environment);
    }

    @Override
    public org.omg.dds.core.policy.UserData UserData() {
        return new UserDataImpl(this.environment);
    }

    @Override
    public org.omg.dds.core.policy.Ownership Ownership() {
        return new OwnershipImpl(this.environment);
    }

    @Override
    public org.omg.dds.core.policy.TimeBasedFilter TimeBasedFilter() {
        return new TimeBasedFilterImpl(this.environment);
    }

    @Override
    public org.omg.dds.core.policy.ReaderDataLifecycle ReaderDataLifecycle() {
        return new ReaderDataLifecycleImpl(this.environment);
    }

    @Override
    public DataRepresentation DataRepresentation() {
        return new DataRepresentationImpl(this.environment);
    }

    @Override
    public TypeConsistencyEnforcement TypeConsistency() {
        return new TypeConsistencyEnforcementImpl(this.environment,
                Kind.EXACT_TYPE_TYPE_CONSISTENCY);
    }

    @Override
    public org.omg.dds.core.policy.DurabilityService DurabilityService() {
        return new DurabilityServiceImpl(this.environment);
    }

    @Override
    public org.omg.dds.core.policy.Reliability Reliability() {
        return new ReliabilityImpl(this.environment);
    }

    @Override
    public org.omg.dds.core.policy.TransportPriority TransportPriority() {
        return new TransportPriorityImpl(this.environment);
    }

    @Override
    public org.omg.dds.core.policy.Lifespan Lifespan() {
        return new LifespanImpl(this.environment);
    }

    @Override
    public org.omg.dds.core.policy.OwnershipStrength OwnershipStrength() {
        return new OwnershipStrengthImpl(this.environment);
    }

    @Override
    public org.omg.dds.core.policy.WriterDataLifecycle WriterDataLifecycle() {
        return new WriterDataLifecycleImpl(this.environment);
    }

    @Override
    public org.omg.dds.core.policy.Partition Partition() {
        return new PartitionImpl(this.environment);
    }

    @Override
    public org.omg.dds.core.policy.GroupData GroupData() {
        return new GroupDataImpl(this.environment);
    }

    @Override
    public org.omg.dds.core.policy.EntityFactory EntityFactory() {
        return new EntityFactoryImpl(this.environment);
    }

    @Override
    public org.omg.dds.core.policy.Presentation Presentation() {
        return new PresentationImpl(this.environment);
    }

    @Override
    public org.omg.dds.core.policy.TopicData TopicData() {
        return new TopicDataImpl(this.environment);
    }

    @Override
    public org.eclipse.cyclonedds.core.policy.Share Share() {
        return new ShareImpl(this.environment);
    }

    @Override
    public org.eclipse.cyclonedds.core.policy.ReaderLifespan ReaderLifespan() {
        return new ReaderLifespanImpl(this.environment);
    }

    @Override
    public WatchdogScheduling WatchDogScheduling() {
        return new SchedulingImpl(this.environment);
    }

    @Override
    public org.eclipse.cyclonedds.core.policy.Scheduling.ListenerScheduling ListenerScheduling() {
        return new SchedulingImpl(this.environment);
    }

    @Override
    public org.eclipse.cyclonedds.core.policy.SubscriptionKeys SubscriptionKeys() {
        return new SubscriptionKeysImpl(this.environment);
    }

    @Override
    public org.eclipse.cyclonedds.core.policy.ViewKeys ViewKeys() {
        return new ViewKeysImpl(this.environment);
    }
}
