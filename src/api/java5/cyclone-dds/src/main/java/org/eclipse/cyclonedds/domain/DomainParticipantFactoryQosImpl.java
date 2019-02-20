/*
 *                         Vortex OpenSplice
 *
 *   This software and documentation are Copyright 2006 to TO_YEAR ADLINK
 *   Technology Limited, its affiliated companies and licensors. All rights
 *   reserved.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */
package org.eclipse.cyclonedds.domain;

import org.omg.dds.core.policy.EntityFactory;
import org.omg.dds.core.policy.QosPolicy.ForDomainParticipantFactory;
import org.omg.dds.domain.DomainParticipantFactoryQos;
import org.eclipse.cyclonedds.core.EntityQosImpl;
import org.eclipse.cyclonedds.core.IllegalArgumentExceptionImpl;
import org.eclipse.cyclonedds.core.OsplServiceEnvironment;
import org.eclipse.cyclonedds.core.policy.EntityFactoryImpl;

public class DomainParticipantFactoryQosImpl extends
        EntityQosImpl<ForDomainParticipantFactory> implements
        DomainParticipantFactoryQos {
    private static final long serialVersionUID = -2614760295894669447L;

    public DomainParticipantFactoryQosImpl(OsplServiceEnvironment environment) {
        super(environment);
    }

    public DomainParticipantFactoryQosImpl(OsplServiceEnvironment environment,
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
            OsplServiceEnvironment env, DomainParticipantFactoryQos oldQos) {
        if (oldQos == null) {
            throw new IllegalArgumentExceptionImpl(env,
                    "oldQos parameter is null.");
        }

        DomainParticipantFactoryQosImpl qos = new DomainParticipantFactoryQosImpl(
                env);

        qos.put(EntityFactory.class, new EntityFactoryImpl(env,
                oldQos.entity_factory.autoenable_created_entities));

        return qos;
    }

    public DomainParticipantFactoryQos convert() {
        DomainParticipantFactoryQos old = new DomainParticipantFactoryQos(
                new EntityFactoryQosPolicy());

        synchronized (this.policies) {
            old.entity_factory.autoenable_created_entities = ((EntityFactory) this.policies
                    .get(EntityFactory.class)).isAutoEnableCreatedEntities();
        }
        return old;
    }

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
