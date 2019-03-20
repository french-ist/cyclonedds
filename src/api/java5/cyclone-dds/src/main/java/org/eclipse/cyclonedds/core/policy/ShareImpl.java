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
import org.eclipse.cyclonedds.core.CycloneServiceEnvironment;

public class ShareImpl extends QosPolicyImpl implements Share {
    private static final long serialVersionUID = -1731793312659549354L;
    private final String name;

    public ShareImpl(CycloneServiceEnvironment environment, String name) {
        super(environment);

        if (name != null) {
            this.name = name;
        } else {
            this.name = "";
        }
    }

    public ShareImpl(CycloneServiceEnvironment environment) {
        this(environment, "");
    }

    @Override
    public ServiceEnvironment getEnvironment() {
        return this.environment;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Share withName(String name) {
        return new ShareImpl(this.environment, name);
    }

    @Override
    public Class<? extends QosPolicy> getPolicyClass() {
        return Share.class;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ShareImpl)) {
            return false;
        }
        return this.name.equals(((ShareImpl) other).name);
    }

    @Override
    public int hashCode() {
        return 31 * 17 + this.name.hashCode();
    }

}
