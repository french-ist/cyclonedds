/**
  Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 
  This program and the accompanying materials are made available under the
  terms of the Eclipse Public License v. 2.0 which is available at
  http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
  v. 1.0 which is available at
  http://www.eclipse.org/org/documents/edl-v10.php.
 
  SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.cyclonedds.topic;

import org.omg.dds.core.policy.DataRepresentation;
import org.omg.dds.core.policy.Deadline;
import org.omg.dds.core.policy.DestinationOrder;
import org.omg.dds.core.policy.Durability;
import org.omg.dds.core.policy.DurabilityService;
import org.omg.dds.core.policy.EntityFactory;
import org.omg.dds.core.policy.GroupData;
import org.omg.dds.core.policy.History;
import org.omg.dds.core.policy.LatencyBudget;
import org.omg.dds.core.policy.Lifespan;
import org.omg.dds.core.policy.Liveliness;
import org.omg.dds.core.policy.Ownership;
import org.omg.dds.core.policy.Partition;
import org.omg.dds.core.policy.Presentation;
import org.omg.dds.core.policy.QosPolicy;
import org.omg.dds.core.policy.ReaderDataLifecycle;
import org.omg.dds.core.policy.QosPolicy.ForTopic;
import org.omg.dds.core.policy.Reliability;
import org.omg.dds.core.policy.ResourceLimits;
import org.omg.dds.core.policy.TimeBasedFilter;
import org.omg.dds.core.policy.TopicData;
import org.omg.dds.core.policy.TransportPriority;
import org.omg.dds.core.policy.TypeConsistencyEnforcement;
import org.omg.dds.core.policy.UserData;
import org.omg.dds.core.policy.TypeConsistencyEnforcement.Kind;
import org.omg.dds.topic.TopicQos;
import org.eclipse.cyclonedds.core.EntityQosImpl;
import org.eclipse.cyclonedds.core.IllegalArgumentExceptionImpl;
import org.eclipse.cyclonedds.core.ServiceEnvironmentImpl;
import org.eclipse.cyclonedds.core.policy.DataRepresentationImpl;
import org.eclipse.cyclonedds.core.policy.DeadlineImpl;
import org.eclipse.cyclonedds.core.policy.DestinationOrderImpl;
import org.eclipse.cyclonedds.core.policy.DurabilityImpl;
import org.eclipse.cyclonedds.core.policy.DurabilityServiceImpl;
import org.eclipse.cyclonedds.core.policy.EntityFactoryImpl;
import org.eclipse.cyclonedds.core.policy.GroupDataImpl;
import org.eclipse.cyclonedds.core.policy.HistoryImpl;
import org.eclipse.cyclonedds.core.policy.LatencyBudgetImpl;
import org.eclipse.cyclonedds.core.policy.LifespanImpl;
import org.eclipse.cyclonedds.core.policy.LivelinessImpl;
import org.eclipse.cyclonedds.core.policy.OwnershipImpl;
import org.eclipse.cyclonedds.core.policy.PartitionImpl;
import org.eclipse.cyclonedds.core.policy.PolicyConverter;
import org.eclipse.cyclonedds.core.policy.PresentationImpl;
import org.eclipse.cyclonedds.core.policy.ReaderDataLifecycleImpl;
import org.eclipse.cyclonedds.core.policy.ReliabilityImpl;
import org.eclipse.cyclonedds.core.policy.ResourceLimitsImpl;
import org.eclipse.cyclonedds.core.policy.TimeBasedFilterImpl;
import org.eclipse.cyclonedds.core.policy.TopicDataImpl;
import org.eclipse.cyclonedds.core.policy.TransportPriorityImpl;
import org.eclipse.cyclonedds.core.policy.TypeConsistencyEnforcementImpl;
import org.eclipse.cyclonedds.core.policy.UserDataImpl;

public class TopicQosImpl extends EntityQosImpl<ForTopic> implements TopicQos {
    private static final long serialVersionUID = -1401988185243587729L;
    private final TypeConsistencyEnforcement typeConsistencyEnforcement;

    public TopicQosImpl(ServiceEnvironmentImpl environment,
            TypeConsistencyEnforcement typeConsistencyEnforcement,
            ForTopic... policies) {
        super(environment, policies);
        this.typeConsistencyEnforcement = typeConsistencyEnforcement;
    }

    public TopicQosImpl(ServiceEnvironmentImpl environment) {
        super(environment);
        this.typeConsistencyEnforcement = new TypeConsistencyEnforcementImpl(
                environment, Kind.EXACT_TYPE_TYPE_CONSISTENCY);
    }

    private TopicQosImpl(TopicQosImpl source, ForTopic... policy) {
        super(source.environment, source.policies.values());
        this.typeConsistencyEnforcement = source.typeConsistencyEnforcement;
        setupPolicies(policy);
    }

    @Override
    public TopicData getTopicData() {
        synchronized (this.policies) {
            return (TopicData) this.policies.get(TopicData.class);
        }
    }

    @Override
    public Durability getDurability() {
        synchronized (this.policies) {
            return (Durability) this.policies.get(Durability.class);
        }
    }

    @Override
    public DurabilityService getDurabilityService() {
        synchronized (this.policies) {
            return (DurabilityService) this.policies.get(DurabilityService.class);
        }
    }

    @Override
    public Deadline getDeadline() {
        synchronized (this.policies) {
            return (Deadline) this.policies.get(Deadline.class);
        }
    }

    @Override
    public LatencyBudget getLatencyBudget() {
        synchronized (this.policies) {
            return (LatencyBudget) this.policies.get(LatencyBudget.class);
        }
    }

    @Override
    public Liveliness getLiveliness() {
        synchronized (this.policies) {
            return (Liveliness) this.policies.get(Liveliness.class);
        }
    }

    @Override
    public Reliability getReliability() {
        synchronized (this.policies) {
            return (Reliability) this.policies.get(Reliability.class);
        }
    }

    @Override
    public DestinationOrder getDestinationOrder() {
        synchronized (this.policies) {
            return (DestinationOrder) this.policies.get(DestinationOrder.class);
        }
    }

    @Override
    public History getHistory() {
        synchronized (this.policies) {
            return (History) this.policies.get(History.class);
        }
    }

    @Override
    public ResourceLimits getResourceLimits() {
        synchronized (this.policies) {
            return (ResourceLimits) this.policies.get(ResourceLimits.class);
        }
    }

    @Override
    public TransportPriority getTransportPriority() {
        synchronized (this.policies) {
            return (TransportPriority) this.policies.get(TransportPriority.class);
        }
    }

    @Override
    public Lifespan getLifespan() {
        synchronized (this.policies) {
            return (Lifespan) this.policies.get(Lifespan.class);
        }
    }

    @Override
    public Ownership getOwnership() {
        synchronized (this.policies) {
            return (Ownership) this.policies.get(Ownership.class);
        }
    }

    @Override
    public DataRepresentation getRepresentation() {
        synchronized (this.policies) {
            return (DataRepresentation) this.policies.get(DataRepresentation.class);
        }
    }

    @Override
    public TypeConsistencyEnforcement getTypeConsistency() {
        return this.typeConsistencyEnforcement;
    }

    @Override
    protected void setupMissingPolicies() {
        synchronized (this.policies) {
            if (!this.policies.containsKey(TopicData.class)) {
                this.policies.put(TopicData.class, new TopicDataImpl(
                        environment));
            }
            if (!this.policies.containsKey(Durability.class)) {
                this.policies.put(Durability.class, new DurabilityImpl(
                        this.environment));
            }
            if (!this.policies.containsKey(DurabilityService.class)) {
                this.policies.put(DurabilityService.class,
                        new DurabilityServiceImpl(this.environment));
            }
            if (!this.policies.containsKey(Deadline.class)) {
                this.policies.put(Deadline.class,
                        new DeadlineImpl(this.environment));
            }
            if (!this.policies.containsKey(LatencyBudget.class)) {
                this.policies.put(LatencyBudget.class, new LatencyBudgetImpl(
                        this.environment));
            }
            if (!this.policies.containsKey(Liveliness.class)) {
                this.policies.put(Liveliness.class, new LivelinessImpl(
                        this.environment));
            }
            if (!this.policies.containsKey(Reliability.class)) {
                this.policies.put(Reliability.class, new ReliabilityImpl(
                        this.environment));
            }
            if (!this.policies.containsKey(DestinationOrder.class)) {
                this.policies.put(DestinationOrder.class, new DestinationOrderImpl(
                        this.environment));
            }
            if (!this.policies.containsKey(History.class)) {
                this.policies.put(History.class, new HistoryImpl(this.environment));
            }
            if (!this.policies.containsKey(ResourceLimits.class)) {
                this.policies.put(ResourceLimits.class, new ResourceLimitsImpl(
                        this.environment));
            }
            if (!this.policies.containsKey(TransportPriority.class)) {
                this.policies.put(TransportPriority.class, new TransportPriorityImpl(
                        this.environment));
            }
            if (!this.policies.containsKey(Lifespan.class)) {
                this.policies.put(Lifespan.class,
                        new LifespanImpl(this.environment));
            }
            if (!this.policies.containsKey(Ownership.class)) {
                this.policies.put(Ownership.class, new OwnershipImpl(
                        this.environment));
            }
            if (!this.policies.containsKey(DataRepresentation.class)) {
                this.policies.put(DataRepresentation.class, new DataRepresentationImpl(
                        this.environment));
            }
        }
    }

    @Override
    public TopicQos withPolicy(QosPolicy.ForTopic policy) {
        return this.withPolicies(policy);
    }

    @Override
    public TopicQos withPolicies(QosPolicy.ForTopic... policy) {
        synchronized (this.policies) {
            return new TopicQosImpl(this, policy);
        }
    }

    public static TopicQosImpl convert(ServiceEnvironmentImpl env,
            TopicQos oldQos) {

        if (oldQos == null) {
            throw new IllegalArgumentExceptionImpl(env,
                    "oldQos parameter is null.");
        }

        TopicQosImpl qos = new TopicQosImpl(env);
/* TODO FRCYC
        qos.put(Deadline.class, PolicyConverter.convert(env, oldQos.deadline));
        qos.put(DestinationOrder.class,
                PolicyConverter.convert(env, oldQos.destination_order));
        qos.put(Durability.class,
                PolicyConverter.convert(env, oldQos.durability));
        qos.put(DurabilityService.class,
                PolicyConverter.convert(env, oldQos.durability_service));
        qos.put(History.class, PolicyConverter.convert(env, oldQos.history));
        qos.put(LatencyBudget.class,
                PolicyConverter.convert(env, oldQos.latency_budget));
        qos.put(Lifespan.class, PolicyConverter.convert(env, oldQos.lifespan));
        qos.put(Liveliness.class,
                PolicyConverter.convert(env, oldQos.liveliness));
        qos.put(Ownership.class, PolicyConverter.convert(env, oldQos.ownership));
        qos.put(Reliability.class,
                PolicyConverter.convert(env, oldQos.reliability));
        qos.put(ResourceLimits.class,
                PolicyConverter.convert(env, oldQos.resource_limits));
        qos.put(TopicData.class,
                PolicyConverter.convert(env, oldQos.topic_data));
        qos.put(TransportPriority.class,
                PolicyConverter.convert(env, oldQos.transport_priority));
*/
        return qos;
    }

    public TopicQos convert() { /* TODO FRCYC
        TopicQos old = new TopicQos();

        synchronized (this.policies) {
            old.deadline = PolicyConverter.convert(this.environment,
                    ((Deadline) this.policies
                    .get(Deadline.class)));
            old.destination_order = PolicyConverter
.convert(this.environment,
                    ((DestinationOrder) this.policies
                            .get(DestinationOrder.class)));
            old.durability = PolicyConverter
.convert(this.environment,
                    ((Durability) this.policies.get(Durability.class)));
            old.durability_service = PolicyConverter
.convert(this.environment,
                    ((DurabilityService) this.policies
                            .get(DurabilityService.class)));
            old.history = PolicyConverter.convert(this.environment,
                    ((History) this.policies
                    .get(History.class)));
            old.latency_budget = PolicyConverter
.convert(this.environment,
                    ((LatencyBudget) this.policies
                            .get(LatencyBudget.class)));
            old.lifespan = PolicyConverter.convert(this.environment,
                    ((Lifespan) this.policies
                    .get(Lifespan.class)));
            old.liveliness = PolicyConverter
.convert(this.environment,
                    ((Liveliness) this.policies.get(Liveliness.class)));
            old.ownership = PolicyConverter.convert(this.environment,
                    ((Ownership) this.policies
                    .get(Ownership.class)));
            old.reliability = PolicyConverter
.convert(this.environment,
                    ((Reliability) this.policies
                            .get(Reliability.class)));
            old.resource_limits = PolicyConverter
.convert(this.environment,
                    ((ResourceLimits) this.policies
                            .get(ResourceLimits.class)));
            old.topic_data = PolicyConverter.convert(this.environment,
                    ((TopicData) this.policies
                    .get(TopicData.class)));
            old.transport_priority = PolicyConverter
.convert(this.environment,
                    ((TransportPriority) this.policies
                            .get(TransportPriority.class)));
        }

        return old;
    */
    	return null;
    }

}
