/**
  Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 
  This program and the accompanying materials are made available under the
  terms of the Eclipse Public License v. 2.0 which is available at
  http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
  v. 1.0 which is available at
  http://www.eclipse.org/org/documents/edl-v10.php.
 
  SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.cyclonedds.domain;

import org.omg.dds.core.policy.EntityFactory;
import org.omg.dds.core.policy.QosPolicy.ForDomainParticipantFactory;
import org.omg.dds.domain.DomainParticipantFactoryQos;
import org.eclipse.cyclonedds.core.EntityQosImpl;
import org.eclipse.cyclonedds.core.IllegalArgumentExceptionImpl;
import org.eclipse.cyclonedds.core.CycloneServiceEnvironment;
import org.eclipse.cyclonedds.core.policy.EntityFactoryImpl;

public class DomainParticipantFactoryQosImpl extends
        EntityQosImpl<ForDomainParticipantFactory> implements
        DomainParticipantFactoryQos {
    private static final long serialVersionUID = -2614760295894669447L;

    public DomainParticipantFactoryQosImpl(CycloneServiceEnvironment environment) {
        super(environment);
    }

    public DomainParticipantFactoryQosImpl(CycloneServiceEnvironment environment,
            ForDomainParticipantFactory... domainParticipantFactories) {
        super(environment, domainParticipantFactories);
    }

    @Override
    protected void setupMissingPolicies() {
        synchronized (this.policies) {
            if (!this.policies.containsKey(EntityFactory.class)) {
                this.policies.put(EntityFactory.class, new EntityFactoryImpl(
                        environment));
            }
        }
    }

    @Override
    public EntityFactory getEntityFactory() {
        synchronized (this.policies) {
            return (EntityFactory) this.policies.get(EntityFactory.class);
        }
    }

    public static DomainParticipantFactoryQosImpl convert(
            CycloneServiceEnvironment env, DomainParticipantFactoryQos oldQos) {
        if (oldQos == null) {
            throw new IllegalArgumentExceptionImpl(env,
                    "oldQos parameter is null.");
        }

        DomainParticipantFactoryQosImpl qos = new DomainParticipantFactoryQosImpl(
                env);

        /*TODO FRCYC
        qos.put(EntityFactory.class, new EntityFactoryImpl(env,
                oldQos.entity_factory.autoenable_created_entities));
           */

        return qos;
    }

    /* TODO FRCYC
    public DomainParticipantFactoryQos convert() {
        DomainParticipantFactoryQos old = new DomainParticipantFactoryQos(
                new EntityFactoryQosPolicy());

        synchronized (this.policies) {
            old.entity_factory.autoenable_created_entities = ((EntityFactory) this.policies
                    .get(EntityFactory.class)).isAutoEnableCreatedEntities();
        }
        return old;
    }
    */

    @Override
    public DomainParticipantFactoryQos withPolicy(
            ForDomainParticipantFactory policy) {
        return this.withPolicies(policy);
    }

    @Override
    public DomainParticipantFactoryQos withPolicies(
            ForDomainParticipantFactory... policy) {

        DomainParticipantFactoryQosImpl result = new DomainParticipantFactoryQosImpl(
                this.environment);
        synchronized (this.policies) {
            result.putAll(this.policies);
        }

        for (ForDomainParticipantFactory f : policy) {
            result.put(getClassIdForPolicy(f), f);
        }
        return result;
    }
}
