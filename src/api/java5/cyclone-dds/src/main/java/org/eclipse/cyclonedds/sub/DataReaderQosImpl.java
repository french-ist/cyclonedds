/**
  Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 
  This program and the accompanying materials are made available under the
  terms of the Eclipse Public License v. 2.0 which is available at
  http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
  v. 1.0 which is available at
  http://www.eclipse.org/org/documents/edl-v10.php.
 
  SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.cyclonedds.sub;

import org.omg.dds.core.policy.DataRepresentation;
import org.omg.dds.core.policy.Deadline;
import org.omg.dds.core.policy.DestinationOrder;
import org.omg.dds.core.policy.Durability;
import org.omg.dds.core.policy.History;
import org.omg.dds.core.policy.LatencyBudget;
import org.omg.dds.core.policy.Liveliness;
import org.omg.dds.core.policy.Ownership;
import org.omg.dds.core.policy.QosPolicy.ForDataReader;
import org.omg.dds.core.policy.ReaderDataLifecycle;
import org.omg.dds.core.policy.Reliability;
import org.omg.dds.core.policy.ResourceLimits;
import org.omg.dds.core.policy.TimeBasedFilter;
import org.omg.dds.core.policy.TypeConsistencyEnforcement;
import org.omg.dds.core.policy.TypeConsistencyEnforcement.Kind;
import org.omg.dds.core.policy.UserData;
import org.omg.dds.sub.DataReaderQos;
import org.omg.dds.topic.TopicQos;
import org.eclipse.cyclonedds.core.EntityQosImpl;
import org.eclipse.cyclonedds.core.IllegalArgumentExceptionImpl;
import org.eclipse.cyclonedds.core.ServiceEnvironmentImpl;
import org.eclipse.cyclonedds.core.policy.DataRepresentationImpl;
import org.eclipse.cyclonedds.core.policy.DeadlineImpl;
import org.eclipse.cyclonedds.core.policy.DestinationOrderImpl;
import org.eclipse.cyclonedds.core.policy.DurabilityImpl;
import org.eclipse.cyclonedds.core.policy.HistoryImpl;
import org.eclipse.cyclonedds.core.policy.LatencyBudgetImpl;
import org.eclipse.cyclonedds.core.policy.LivelinessImpl;
import org.eclipse.cyclonedds.core.policy.OwnershipImpl;
import org.eclipse.cyclonedds.core.policy.PolicyConverter;
import org.eclipse.cyclonedds.core.policy.ReaderDataLifecycleImpl;
import org.eclipse.cyclonedds.core.policy.ReaderLifespan;
import org.eclipse.cyclonedds.core.policy.ReliabilityImpl;
import org.eclipse.cyclonedds.core.policy.ResourceLimitsImpl;
import org.eclipse.cyclonedds.core.policy.Share;
import org.eclipse.cyclonedds.core.policy.SubscriptionKeys;
import org.eclipse.cyclonedds.core.policy.TimeBasedFilterImpl;
import org.eclipse.cyclonedds.core.policy.TypeConsistencyEnforcementImpl;
import org.eclipse.cyclonedds.core.policy.UserDataImpl;

public class DataReaderQosImpl extends EntityQosImpl<ForDataReader> implements
org.eclipse.cyclonedds.sub.DataReaderQos {
    private static final long serialVersionUID = 7226579387926252059L;
    private final TypeConsistencyEnforcement typeConsistencyEnforcement;

    public DataReaderQosImpl(ServiceEnvironmentImpl environment,
            TypeConsistencyEnforcement typeConsistencyEnforcement,
            ForDataReader... policies) {
        super(environment, policies);
        this.typeConsistencyEnforcement = typeConsistencyEnforcement;

    }

    public DataReaderQosImpl(ServiceEnvironmentImpl environment) {
        super(environment);
        this.typeConsistencyEnforcement = new TypeConsistencyEnforcementImpl(
                environment, Kind.EXACT_TYPE_TYPE_CONSISTENCY);
    }

    private DataReaderQosImpl(DataReaderQosImpl source, ForDataReader... policy) {
        super(source.environment, source.policies.values());
        this.typeConsistencyEnforcement = source.typeConsistencyEnforcement;
        setupPolicies(policy);
    }

    @Override
    public Durability getDurability() {
        synchronized (this.policies) {
            return (Durability) this.policies.get(Durability.class);
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
    public UserData getUserData() {
        synchronized (this.policies) {
            return (UserData) this.policies.get(UserData.class);
        }
    }

    @Override
    public Ownership getOwnership() {
        synchronized (this.policies) {
            return (Ownership) this.policies.get(Ownership.class);
        }
    }

    @Override
    public TimeBasedFilter getTimeBasedFilter() {
        synchronized (this.policies) {
            return (TimeBasedFilter) this.policies.get(TimeBasedFilter.class);
        }
    }

    @Override
    public ReaderDataLifecycle getReaderDataLifecycle() {
        synchronized (this.policies) {
            return (ReaderDataLifecycle) this.policies
                .get(ReaderDataLifecycle.class);
        }
    }

    @Override
    public DataRepresentation getRepresentation() {
        synchronized (this.policies) {
            return (DataRepresentation) this.policies.get(DataRepresentation.class);
        }
    }

    @Override
    public ReaderLifespan getReaderLifespan() {
        synchronized (this.policies) {
            return (ReaderLifespan) this.policies.get(ReaderLifespan.class);
        }
    }

    @Override
    public Share getShare() {
        synchronized (this.policies) {
            return (Share) this.policies.get(Share.class);
        }
    }

    @Override
    public SubscriptionKeys getSubscriptionKeys() {
        synchronized (this.policies) {
            return (SubscriptionKeys) this.policies.get(SubscriptionKeys.class);
        }
    }

    @Override
    public TypeConsistencyEnforcement getTypeConsistency() {
        return this.typeConsistencyEnforcement;
    }

    @Override
    public DataReaderQos withPolicy(ForDataReader policy) {
        return this.withPolicies(policy);
    }

    @Override
    public DataReaderQos withPolicies(ForDataReader... policy) {
        synchronized (this.policies) {
            return new DataReaderQosImpl(this, policy);
        }
    }

    @Override
    protected void setupMissingPolicies() {
        synchronized (this.policies) {
            if (!this.policies.containsKey(Durability.class)) {
                this.policies.put(Durability.class, new DurabilityImpl(
                        this.environment));
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
            if (!this.policies.containsKey(TimeBasedFilter.class)) {
                this.policies.put(TimeBasedFilter.class, new TimeBasedFilterImpl(
                        this.environment));
            }
            if (!this.policies.containsKey(Ownership.class)) {
                this.policies.put(Ownership.class, new OwnershipImpl(
                        this.environment));
            }
            if (!this.policies.containsKey(DataRepresentation.class)) {
                this.policies.put(DataRepresentation.class,
                        new DataRepresentationImpl(this.environment));
            }
            if (!this.policies.containsKey(UserData.class)) {
                this.policies.put(UserData.class,
                        new UserDataImpl(this.environment));
            }
            if (!this.policies.containsKey(ReaderDataLifecycle.class)) {
                this.policies.put(ReaderDataLifecycle.class,
                        new ReaderDataLifecycleImpl(this.environment));
            }
        }
    }

    public DataReaderQos convert() {
    	/* TODO FRCYC
        DataReaderQos old = new DataReaderQos();

        synchronized (this.policies) {
            old.deadline = PolicyConverter.convert(this.environment,
                    ((Deadline) this.policies.get(Deadline.class)));
            old.destination_order = PolicyConverter.convert(this.environment,
                    ((DestinationOrder) this.policies
                            .get(DestinationOrder.class)));
            old.durability = PolicyConverter.convert(this.environment,
                    ((Durability) this.policies.get(Durability.class)));
            old.history = PolicyConverter.convert(this.environment,
                    ((History) this.policies.get(History.class)));
            old.latency_budget = PolicyConverter.convert(this.environment,
                    ((LatencyBudget) this.policies.get(LatencyBudget.class)));
            old.liveliness = PolicyConverter.convert(this.environment,
                    ((Liveliness) this.policies.get(Liveliness.class)));
            old.ownership = PolicyConverter.convert(this.environment,
                    ((Ownership) this.policies.get(Ownership.class)));
            old.reader_data_lifecycle = PolicyConverter.convert(
                    this.environment, ((ReaderDataLifecycle) this.policies
                            .get(ReaderDataLifecycle.class)));
            old.reader_lifespan = PolicyConverter.convert(this.environment,
                    ((ReaderLifespan) this.policies.get(ReaderLifespan.class)));
            old.reliability = PolicyConverter.convert(this.environment,
                    ((Reliability) this.policies.get(Reliability.class)));
            old.resource_limits = PolicyConverter.convert(this.environment,
                    ((ResourceLimits) this.policies.get(ResourceLimits.class)));
            old.share = PolicyConverter.convert(this.environment,
                    ((Share) this.policies.get(Share.class)));
            old.subscription_keys = PolicyConverter.convert(this.environment,
                    ((SubscriptionKeys) this.policies
                            .get(SubscriptionKeys.class)));
            ;
            old.time_based_filter = PolicyConverter
                    .convert(this.environment, ((TimeBasedFilter) this.policies
                            .get(TimeBasedFilter.class)));
            old.user_data = PolicyConverter.convert(this.environment,
                    ((UserData) this.policies.get(UserData.class)));
        }
        return old;
        */
    	return null;
    }

    public static DataReaderQosImpl convert(ServiceEnvironmentImpl env,
            DataReaderQos oldQos) {
        if (oldQos == null) {
            throw new IllegalArgumentExceptionImpl(env,
                    "oldQos parameter is null.");
        }
        /* TODO FRCYC
        DataReaderQosImpl qos = new DataReaderQosImpl(env);

        qos.put(Deadline.class, PolicyConverter.convert(env, oldQos.deadline));
        qos.put(DestinationOrder.class,
                PolicyConverter.convert(env, oldQos.destination_order));
        qos.put(Durability.class,
                PolicyConverter.convert(env, oldQos.durability));
        qos.put(History.class, PolicyConverter.convert(env, oldQos.history));
        qos.put(LatencyBudget.class,
                PolicyConverter.convert(env, oldQos.latency_budget));
        qos.put(Liveliness.class,
                PolicyConverter.convert(env, oldQos.liveliness));
        qos.put(Ownership.class, PolicyConverter.convert(env, oldQos.ownership));
        qos.put(ReaderDataLifecycle.class,
                PolicyConverter.convert(env, oldQos.reader_data_lifecycle));
        qos.put(Reliability.class,
                PolicyConverter.convert(env, oldQos.reliability));
        qos.put(ResourceLimits.class,
                PolicyConverter.convert(env, oldQos.resource_limits));

        qos.put(TimeBasedFilter.class,
                PolicyConverter.convert(env, oldQos.time_based_filter));
        qos.put(UserData.class, PolicyConverter.convert(env, oldQos.user_data));

        Share share = PolicyConverter.convert(env, oldQos.share);

        if (share != null) {
            qos.put(Share.class, share);
        }

        ReaderLifespan readerLifespan = PolicyConverter.convert(env,
                oldQos.reader_lifespan);

        if (readerLifespan != null) {
            qos.put(ReaderLifespan.class, readerLifespan);
        }
        SubscriptionKeys subscriptionKeys = PolicyConverter.convert(env,
                oldQos.subscription_keys);

        if (subscriptionKeys != null) {
            qos.put(SubscriptionKeys.class, subscriptionKeys);
        }
        return qos;
        */
        return null;
    }

    public void mergeTopicQos(TopicQos topicQos) {
        synchronized (this.policies) {
            this.policies.put(Deadline.class, topicQos.getDeadline());
            this.policies.put(DestinationOrder.class,
                    topicQos.getDestinationOrder());
            this.policies.put(Durability.class, topicQos.getDurability());
            this.policies.put(History.class, topicQos.getHistory());
            this.policies.put(LatencyBudget.class, topicQos.getLatencyBudget());
            this.policies.put(Liveliness.class, topicQos.getLiveliness());
            this.policies.put(Ownership.class, topicQos.getOwnership());
            this.policies.put(Reliability.class, topicQos.getReliability());
            this.policies.put(DataRepresentation.class,
                    topicQos.getRepresentation());
            this.policies.put(ResourceLimits.class,
                    topicQos.getResourceLimits());
        }
    }
}
