/**
  Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 
  This program and the accompanying materials are made available under the
  terms of the Eclipse Public License v. 2.0 which is available at
  http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
  v. 1.0 which is available at
  http://www.eclipse.org/org/documents/edl-v10.php.
 
  SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.cyclonedds.pub;

import org.omg.dds.core.policy.EntityFactory;
import org.omg.dds.core.policy.GroupData;
import org.omg.dds.core.policy.Partition;
import org.omg.dds.core.policy.Presentation;
import org.omg.dds.core.policy.QosPolicy.ForPublisher;
import org.omg.dds.pub.PublisherQos;
import org.eclipse.cyclonedds.core.EntityQosImpl;
import org.eclipse.cyclonedds.core.IllegalArgumentExceptionImpl;
import org.eclipse.cyclonedds.core.ServiceEnvironmentImpl;
import org.eclipse.cyclonedds.core.policy.EntityFactoryImpl;
import org.eclipse.cyclonedds.core.policy.GroupDataImpl;
import org.eclipse.cyclonedds.core.policy.PartitionImpl;
import org.eclipse.cyclonedds.core.policy.PolicyConverter;
import org.eclipse.cyclonedds.core.policy.PresentationImpl;

public class PublisherQosImpl extends EntityQosImpl<ForPublisher> implements
        PublisherQos {

    private static final long serialVersionUID = 3160319098450969471L;

    public PublisherQosImpl(ServiceEnvironmentImpl environment,
            ForPublisher... policies) {
        super(environment, policies);
    }

    public PublisherQosImpl(ServiceEnvironmentImpl environment) {
        super(environment);
    }

    private PublisherQosImpl(PublisherQosImpl source,
            ForPublisher... policy) {
        super(source.environment, source.policies.values());
        setupPolicies(policy);
    }

    @Override
    protected void setupMissingPolicies() {
        synchronized(this.policies) {
            if (!this.containsKey(Presentation.class)) {
                this.put(Presentation.class, new PresentationImpl(this.environment));
            }
            if (!this.containsKey(Partition.class)) {
                this.put(Partition.class, new PartitionImpl(this.environment));
            }
            if (!this.containsKey(GroupData.class)) {
                this.put(GroupData.class, new GroupDataImpl(this.environment));
            }
            if (!this.containsKey(EntityFactory.class)) {
                this.put(EntityFactory.class, new EntityFactoryImpl(
                        this.environment));
            }
        }
    }

    @Override
    public Presentation getPresentation() {
        return this.get(Presentation.class);
    }

    @Override
    public Partition getPartition() {
        return this.get(Partition.class);
    }

    @Override
    public GroupData getGroupData() {
        return this.get(GroupData.class);
    }

    @Override
    public EntityFactory getEntityFactory() {
        return this.get(EntityFactory.class);
    }

    @Override
    public PublisherQos withPolicy(ForPublisher policy) {
        return this.withPolicies(policy);
    }

    @Override
    public PublisherQos withPolicies(ForPublisher... policy) {
        synchronized(this.policies) {
            return new PublisherQosImpl(this, policy);
        }
    }

    /* TODO FRCYC
    public static PublisherQosImpl convert(OsplServiceEnvironment env,
            PublisherQos oldQos) {
        if (oldQos == null) {
            throw new IllegalArgumentExceptionImpl(env,
                    "oldQos parameter is null.");
        }

        PublisherQosImpl qos = new PublisherQosImpl(env);

        qos.put(EntityFactory.class,
                PolicyConverter.convert(env, oldQos.entity_factory));
        qos.put(GroupData.class,
                PolicyConverter.convert(env, oldQos.group_data));
        qos.put(Partition.class, PolicyConverter.convert(env, oldQos.partition));
        qos.put(Presentation.class,
                PolicyConverter.convert(env, oldQos.presentation));

        return qos;
    }

    public PublisherQos convert() {
        PublisherQos old = new PublisherQos();

        synchronized (this.policies) {
            old.entity_factory = PolicyConverter.convert(this.environment,
                    ((EntityFactory) this.policies.get(EntityFactory.class)));
            old.group_data = PolicyConverter.convert(this.environment,
                    ((GroupData) this.policies.get(GroupData.class)));

            old.partition = PolicyConverter.convert(this.environment,
                    ((Partition) this.policies.get(Partition.class)));
            old.presentation = PolicyConverter.convert(this.environment,
                    ((Presentation) this.policies.get(Presentation.class)));
        }
        return old;
    }
    */
}
