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
package org.eclipse.cyclonedds.core.policy;

import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.policy.QosPolicy;
import org.omg.dds.core.policy.QosPolicyCount;
import org.eclipse.cyclonedds.core.CycloneServiceEnvironment;

public class QosPolicyCountImpl implements QosPolicyCount {
    private static final long serialVersionUID = -5717335162269137308L;
    private final transient CycloneServiceEnvironment environment;
    private final Class<? extends QosPolicy> policy;
    private final int count;

    public QosPolicyCountImpl(CycloneServiceEnvironment environment, Class<? extends QosPolicy> policy, int count){
        this.environment = environment;
        this.policy = policy;
        this.count = count;
    }

    @Override
    public ServiceEnvironment getEnvironment() {
        return this.environment;
    }

    @Override
    public Class<? extends QosPolicy> getPolicyClass() {
        return this.policy;
    }

    @Override
    public int getCount() {
        return this.count;
    }

}
