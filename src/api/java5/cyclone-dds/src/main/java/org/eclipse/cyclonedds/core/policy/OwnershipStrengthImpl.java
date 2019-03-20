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

import org.omg.dds.core.policy.OwnershipStrength;
import org.omg.dds.core.policy.QosPolicy;
import org.eclipse.cyclonedds.core.CycloneServiceEnvironment;

public class OwnershipStrengthImpl extends QosPolicyImpl implements
        OwnershipStrength {
    private static final long serialVersionUID = -1785002609550828172L;
    private final int value;

    public OwnershipStrengthImpl(CycloneServiceEnvironment environment) {
        super(environment);
        this.value = 0;
    }

    public OwnershipStrengthImpl(CycloneServiceEnvironment environment, int value) {
        super(environment);
        this.value = value;
    }

    @Override
    public int getValue() {
        return this.value;
    }

    @Override
    public OwnershipStrength withValue(int value) {
        return new OwnershipStrengthImpl(this.environment, value);
    }

    @Override
    public Class<? extends QosPolicy> getPolicyClass() {
        return OwnershipStrength.class;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof OwnershipStrengthImpl)) {
            return false;
        }
        return (this.value == ((OwnershipStrengthImpl) other).value);
    }

    @Override
    public int hashCode() {
        return 17 * 31 + this.value;
    }
}
