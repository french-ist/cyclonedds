/**
  Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 
  This program and the accompanying materials are made available under the
  terms of the Eclipse Public License v. 2.0 which is available at
  http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
  v. 1.0 which is available at
  http://www.eclipse.org/org/documents/edl-v10.php.
 
  SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.cyclonedds.core.status;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.policy.QosPolicy;
import org.omg.dds.core.policy.QosPolicyCount;
import org.omg.dds.core.status.RequestedIncompatibleQosStatus;
import org.eclipse.cyclonedds.core.ServiceEnvironmentImpl;
import org.eclipse.cyclonedds.core.policy.QosPolicyCountImpl;

public class RequestedIncompatibleQosStatusImpl extends
        RequestedIncompatibleQosStatus {
    private static final long serialVersionUID = 8046180416846302298L;
    private final transient ServiceEnvironmentImpl environment;
    private final int totalCount;
    private final int totalCountChange;
    private final Class<? extends QosPolicy> lastPolicyClass;
    private final List<QosPolicyCount> policies;

    public RequestedIncompatibleQosStatusImpl(
            ServiceEnvironmentImpl environment, int totalCount,
            int totalCountChange, Class<? extends QosPolicy> lastPolicyClass,
            QosPolicyCount... policies) {
        this.environment = environment;
        this.totalCount = totalCount;
        this.totalCountChange = totalCountChange;
        this.lastPolicyClass = lastPolicyClass;
        this.policies = Collections
                .synchronizedList(new ArrayList<QosPolicyCount>());

        for (QosPolicyCount p : policies) {
            this.policies.add(p);
        }
    }

    @Override
    public ServiceEnvironment getEnvironment() {
        return this.environment;
    }

    @Override
    public int getTotalCount() {
        return this.totalCount;
    }

    @Override
    public int getTotalCountChange() {
        return this.totalCountChange;
    }

    @Override
    public Class<? extends QosPolicy> getLastPolicyClass() {
        return this.lastPolicyClass;
    }

    @Override
    public Set<QosPolicyCount> getPolicies() {
        Set<QosPolicyCount> policyCount = new HashSet<QosPolicyCount>();

        for (QosPolicyCount qp : this.policies) {
            policyCount.add(new QosPolicyCountImpl(this.environment, qp
                    .getPolicyClass(), qp.getCount()));
        }
        return policyCount;
    }

}
