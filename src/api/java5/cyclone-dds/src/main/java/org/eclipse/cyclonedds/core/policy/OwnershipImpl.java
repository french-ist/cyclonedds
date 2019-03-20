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

import org.omg.dds.core.policy.Ownership;
import org.omg.dds.core.policy.QosPolicy;
import org.eclipse.cyclonedds.core.IllegalArgumentExceptionImpl;
import org.eclipse.cyclonedds.core.CycloneServiceEnvironment;

public class OwnershipImpl extends QosPolicyImpl implements Ownership {
    private static final long serialVersionUID = 5349819428623413367L;
    private final Kind kind;

    public OwnershipImpl(CycloneServiceEnvironment environment) {
        super(environment);
        this.kind = Kind.SHARED;
    }

    public OwnershipImpl(CycloneServiceEnvironment environment, Kind kind) {
        super(environment);

        if (kind == null) {
            throw new IllegalArgumentExceptionImpl(environment,
                    "Supplied kind is invalid.");
        }
        this.kind = kind;
    }

    @Override
    public Comparable<Ownership> requestedOfferedContract() {
        return this;
    }

    @Override
    public int compareTo(Ownership o) {
        if (this.kind.equals(o.getKind())) {
            return 0;
        } else if (this.kind.equals(Kind.SHARED)) {
            return -1;
        }
        return 1;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof OwnershipImpl)) {
            return false;
        }
        return this.kind.equals(((OwnershipImpl) other).kind);
    }

    @Override
    public Kind getKind() {
        return this.kind;
    }

    @Override
    public Ownership withKind(Kind kind) {
        return new OwnershipImpl(this.environment, kind);
    }

    @Override
    public Ownership withShared() {
        return new OwnershipImpl(this.environment, Kind.SHARED);
    }

    @Override
    public Ownership withExclusive() {
        return new OwnershipImpl(this.environment, Kind.EXCLUSIVE);
    }

    @Override
    public Class<? extends QosPolicy> getPolicyClass() {
        return Ownership.class;
    }

    @Override
    public int hashCode() {
        return 17 * 31 + this.kind.hashCode();
    }
}
