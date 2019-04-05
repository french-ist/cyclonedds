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
import org.omg.dds.core.policy.QosPolicy;
import org.omg.dds.core.policy.QosPolicy.ForDomainParticipant;
import org.eclipse.cyclonedds.core.EntityQosImpl;
import org.eclipse.cyclonedds.core.IllegalArgumentExceptionImpl;
import org.eclipse.cyclonedds.core.CycloneServiceEnvironment;
import org.eclipse.cyclonedds.core.policy.EntityFactoryImpl;
import org.eclipse.cyclonedds.core.policy.PolicyConverter;
import org.eclipse.cyclonedds.core.policy.SchedulingImpl;
import org.eclipse.cyclonedds.core.policy.UserDataImpl;
import org.eclipse.cyclonedds.core.policy.Scheduling.ListenerScheduling;
import org.eclipse.cyclonedds.core.policy.Scheduling.WatchdogScheduling;
import org.eclipse.cyclonedds.domain.DomainParticipantQos;
import org.omg.dds.core.policy.UserData;

import com.sun.jna.ptr.PointerByReference;

public class DomainParticipantQosImpl extends
EntityQosImpl<ForDomainParticipant> implements DomainParticipantQos {
    private static final long serialVersionUID = -1811553017861487660L;

    public DomainParticipantQosImpl(CycloneServiceEnvironment environment) {
        super(environment);
    }

    public DomainParticipantQosImpl(CycloneServiceEnvironment environment,
            ForDomainParticipant... domainParticipants) {
        super(environment, domainParticipants);

    }

    private DomainParticipantQosImpl(DomainParticipantQosImpl source,
            ForDomainParticipant... policy) {
        super(source.environment, source.policies.values());
        setupPolicies(policy);
    }

    @Override
    protected void setupMissingPolicies() {
        synchronized (this.policies) {
            if (!this.policies.containsKey(EntityFactory.class)) {
                this.policies.put(EntityFactory.class, new EntityFactoryImpl(
                        this.environment));
            }
            if (!this.policies.containsKey(UserData.class)) {
                this.policies.put(UserData.class,
                        new UserDataImpl(this.environment));
            }
            if (!this.policies.containsKey(ListenerScheduling.class)) {
                this.policies.put(ListenerScheduling.class, new SchedulingImpl(
                        this.environment));
            }
            if (!this.policies.containsKey(WatchdogScheduling.class)) {
                this.policies.put(WatchdogScheduling.class, new SchedulingImpl(
                        this.environment));
            }
        }
    }

    @Override
    public UserData getUserData() {
        synchronized (this.policies) {
            return (UserData) this.policies.get(UserData.class);
        }
    }

    @Override
    public EntityFactory getEntityFactory() {
        synchronized (this.policies) {
            return (EntityFactory) this.policies.get(EntityFactory.class);
        }
    }

    @Override
    public ListenerScheduling getListenerScheduling() {
        synchronized (this.policies) {
            return (ListenerScheduling) this.policies.get(ListenerScheduling.class);
        }
    }

    @Override
    public WatchdogScheduling getWatchdogScheduling() {
        synchronized (this.policies) {
            return (WatchdogScheduling) this.policies.get(WatchdogScheduling.class);
        }
    }

    @Override
    public DomainParticipantQos withPolicy(QosPolicy.ForDomainParticipant policy) {
        return this.withPolicies(policy);
    }

    @Override
    public DomainParticipantQos withPolicies(
            QosPolicy.ForDomainParticipant... policy) {
        synchronized (this.policies) {
            return new DomainParticipantQosImpl(this, policy);
        }
    }

	public static org.omg.dds.domain.DomainParticipantQos convert(CycloneServiceEnvironment environment, PointerByReference rc) {
		return null;
	}

    /* TODO FRCYC
    public static DomainParticipantQosImpl convert(OsplServiceEnvironment env,
            DomainParticipantQos oldQos) {

        if (oldQos == null) {
            throw new IllegalArgumentExceptionImpl(env,
                    "oldQos parameter is null.");
        }

        DomainParticipantQosImpl qos = new DomainParticipantQosImpl(env);

        qos.put(EntityFactory.class,
                PolicyConverter.convert(env, oldQos.entity_factory));
        qos.put(UserData.class, PolicyConverter.convert(env, oldQos.user_data));
        qos.put(ListenerScheduling.class,
                PolicyConverter.convert(env, oldQos.listener_scheduling));
        qos.put(WatchdogScheduling.class,
                PolicyConverter.convert(env, oldQos.watchdog_scheduling));

        return qos;
    }

    public DomainParticipantQos convert() {
        DomainParticipantQos old = new DomainParticipantQos();

        synchronized (this.policies) {
            old.entity_factory = PolicyConverter.convert(this.environment,
                    ((EntityFactory) this.policies.get(EntityFactory.class)));
            old.user_data = PolicyConverter.convert(this.environment,
                    ((UserData) this.policies.get(UserData.class)));
            old.listener_scheduling = PolicyConverter.convert(this.environment,
                    (ListenerScheduling) this.policies
                    .get(ListenerScheduling.class));
            old.watchdog_scheduling = PolicyConverter.convert(this.environment,
                    (WatchdogScheduling) this.policies
                    .get(WatchdogScheduling.class));
        }
        return old;
    }*/
}
