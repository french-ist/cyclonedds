/**
 * Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
 * v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.cyclonedds.sub;

import org.omg.dds.core.policy.EntityFactory;
import org.omg.dds.core.policy.GroupData;
import org.omg.dds.core.policy.Partition;
import org.omg.dds.core.policy.Presentation;
import org.omg.dds.core.policy.QosPolicy.ForSubscriber;
import org.omg.dds.sub.SubscriberQos;
import org.eclipse.cyclonedds.core.EntityQosImpl;
import org.eclipse.cyclonedds.core.IllegalArgumentExceptionImpl;
import org.eclipse.cyclonedds.core.CycloneServiceEnvironment;
import org.eclipse.cyclonedds.core.policy.EntityFactoryImpl;
import org.eclipse.cyclonedds.core.policy.GroupDataImpl;
import org.eclipse.cyclonedds.core.policy.PartitionImpl;
import org.eclipse.cyclonedds.core.policy.PolicyConverter;
import org.eclipse.cyclonedds.core.policy.PresentationImpl;
import org.eclipse.cyclonedds.core.policy.Share;

public class SubscriberQosImpl extends EntityQosImpl<ForSubscriber> implements
        org.eclipse.cyclonedds.sub.SubscriberQos {
    private static final long serialVersionUID = 5350093533137522289L;

    public SubscriberQosImpl(CycloneServiceEnvironment environment,
            ForSubscriber... policies) {
        super(environment, policies);
    }

    public SubscriberQosImpl(CycloneServiceEnvironment environment) {
        super(environment);
    }

    private SubscriberQosImpl(SubscriberQosImpl source, ForSubscriber... policy) {
        super(source.environment, source.policies.values());
        setupPolicies(policy);
    }

    @Override
    protected void setupMissingPolicies() {
        synchronized(this.policies) {
            if (!this.policies.containsKey(Presentation.class)) {
                this.policies.put(Presentation.class, new PresentationImpl(
                        this.environment));
            }
            if (!this.policies.containsKey(Partition.class)) {
                this.policies.put(Partition.class, new PartitionImpl(
                        this.environment));
            }
            if (!this.policies.containsKey(GroupData.class)) {
                this.policies.put(GroupData.class, new GroupDataImpl(
                        this.environment));
            }
            if (!this.policies.containsKey(EntityFactory.class)) {
                this.policies.put(EntityFactory.class, new EntityFactoryImpl(
                        this.environment));
            }
        }
    }

    @Override
    public Presentation getPresentation() {
        synchronized(this.policies) {
            return (Presentation) this.policies.get(Presentation.class);
        }
    }

    @Override
    public Partition getPartition() {
        synchronized(this.policies) {
            return (Partition) this.policies.get(Partition.class);
        }
    }

    @Override
    public GroupData getGroupData() {
        synchronized(this.policies) {
            return (GroupData) this.policies.get(GroupData.class);
        }
    }

    @Override
    public EntityFactory getEntityFactory() {
        synchronized(this.policies) {
            return (EntityFactory) this.policies.get(EntityFactory.class);
        }
    }

    @Override
    public Share getShare() {
        synchronized(this.policies) {
            return (Share) this.policies.get(Share.class);
        }
    }

    @Override
    public SubscriberQos withPolicy(ForSubscriber policy) {
        return this.withPolicies(policy);
    }

    @Override
    public SubscriberQos withPolicies(ForSubscriber... policy) {
        synchronized (this.policies) {
            return new SubscriberQosImpl(this, policy);
        }
    }

    public static SubscriberQosImpl convert(CycloneServiceEnvironment env,
            SubscriberQos oldQos) {
    	/* TODO FRCYC
        if (oldQos == null) {
            throw new IllegalArgumentExceptionImpl(env,
                    "oldQos parameter is null.");
        }

        SubscriberQosImpl qos = new SubscriberQosImpl(env);

        qos.put(EntityFactory.class,
                PolicyConverter.convert(env, oldQos.entity_factory));
        qos.put(GroupData.class,
                PolicyConverter.convert(env, oldQos.group_data));
        qos.put(Partition.class, PolicyConverter.convert(env, oldQos.partition));
        qos.put(Presentation.class,
                PolicyConverter.convert(env, oldQos.presentation));

        Share share = PolicyConverter.convert(env, oldQos.share);

        if (share != null) {
            qos.put(Share.class, share);
        }

        return qos;
        */
    	return null;
    }

    public SubscriberQos convert() {
    	/* TODO FRCYC
        SubscriberQos old = new SubscriberQos();

        synchronized (this.policies) {
            old.entity_factory = PolicyConverter
.convert(this.environment,
                    ((EntityFactory) this.policies
                            .get(EntityFactory.class)));
            old.group_data = PolicyConverter.convert(this.environment,
                    ((GroupData) this.policies
                    .get(GroupData.class)));

            old.partition = PolicyConverter.convert(this.environment,
                    ((Partition) this.policies
                    .get(Partition.class)));
            old.presentation = PolicyConverter
.convert(this.environment,
                    ((Presentation) this.policies
                            .get(Presentation.class)));
            old.share = PolicyConverter.convert(this.environment,
                    ((Share) this.policies
                    .get(Share.class)));
        }
        return old;
        */
    	return null;
    }

}
