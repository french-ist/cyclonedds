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

import java.util.Arrays;

import org.omg.dds.core.policy.GroupData;
import org.omg.dds.core.policy.QosPolicy;
import org.eclipse.cyclonedds.core.CycloneServiceEnvironment;

public class GroupDataImpl extends QosPolicyImpl implements GroupData {
    private static final long serialVersionUID = -5108639314694283781L;
    private final byte[] value;

    public GroupDataImpl(CycloneServiceEnvironment environment) {
        super(environment);
        this.value = new byte[0];
    }

    public GroupDataImpl(CycloneServiceEnvironment environment, byte[] value) {
        super(environment);

        if(value != null){
            this.value = value.clone();
        } else {
            this.value = new byte[0];
        }
    }

    @Override
    public byte[] getValue() {
        if (this.value == null) {
            return null;
        }
        return this.value.clone();
    }

    @Override
    public GroupData withValue(byte[] value, int offset, int length) {
        return new GroupDataImpl(this.environment, value);
    }

    @Override
    public Class<? extends QosPolicy> getPolicyClass() {
        return GroupData.class;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof GroupDataImpl)) {
            return false;
        }
        return Arrays.equals(this.value, ((GroupDataImpl) other).value);
    }

    @Override
    public int hashCode(){
        final int prime = 31;
        int result = 17;

        for (byte b : this.value) {
            result = prime * result + b;
        }
        return result;
    }
}
