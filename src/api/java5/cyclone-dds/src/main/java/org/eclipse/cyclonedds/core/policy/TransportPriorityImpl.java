/**
  Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 
  This program and the accompanying materials are made available under the
  terms of the Eclipse Public License v. 2.0 which is available at
  http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
  v. 1.0 which is available at
  http://www.eclipse.org/org/documents/edl-v10.php.
 
  SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.cyclonedds.core.policy;

import org.omg.dds.core.policy.QosPolicy;
import org.omg.dds.core.policy.TransportPriority;
import org.eclipse.cyclonedds.core.CycloneServiceEnvironment;

public class TransportPriorityImpl extends QosPolicyImpl implements
TransportPriority {
    private static final long serialVersionUID = -2681488718301095677L;
    private final int value;

    public TransportPriorityImpl(CycloneServiceEnvironment environment) {
        super(environment);
        this.value = 0;
    }

    public TransportPriorityImpl(CycloneServiceEnvironment environment, int value) {
        super(environment);
        this.value = value;
    }

    @Override
    public int getValue() {
        return this.value;
    }

    @Override
    public TransportPriority withValue(int value) {
        return new TransportPriorityImpl(this.environment, value);
    }

    @Override
    public Class<? extends QosPolicy> getPolicyClass() {
        return TransportPriority.class;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof TransportPriorityImpl)) {
            return false;
        }
        return (this.value == (((TransportPriorityImpl) other).value));
    }

    @Override
    public int hashCode() {
        return 31 * 17 + this.value;
    }

}
