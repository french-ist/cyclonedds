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

import org.omg.dds.core.policy.Durability;
import org.omg.dds.core.policy.QosPolicy;
import org.eclipse.cyclonedds.core.IllegalArgumentExceptionImpl;
import org.eclipse.cyclonedds.core.ServiceEnvironmentImpl;

public class DurabilityImpl extends QosPolicyImpl implements Durability {
    private static final long serialVersionUID = -3661996204819852834L;
    private final Kind kind;

    public DurabilityImpl(ServiceEnvironmentImpl environment) {
        super(environment);
        this.kind = Kind.VOLATILE;
    }

    public DurabilityImpl(ServiceEnvironmentImpl environment, Kind kind) {
        super(environment);
        this.kind = kind;

        if (this.kind == null) {
            throw new IllegalArgumentExceptionImpl(environment,
                    "Supplied kind is null.");
        }
    }

    @Override
    public Comparable<Durability> requestedOfferedContract() {
        return this;
    }

    @Override
    public int compareTo(Durability o) {
        // VOLATILE<TRANSIENT_LOCAL<TRANSIENT<PERSISTENT
        Kind other = o.getKind();

        if (other.equals(this.kind)) {
            return 0;
        } else if (other.equals(Kind.VOLATILE)) {
            return 1;
        } else if (other.equals(Kind.TRANSIENT_LOCAL)) {
            if (this.kind.equals(Kind.VOLATILE)) {
                return -1;
            }
            return 1;
        } else if (other.equals(Kind.TRANSIENT)) {
            if (this.kind.equals(Kind.VOLATILE)
                    || this.kind.equals(Kind.TRANSIENT_LOCAL)) {
                return -1;
            }
            return 1;
        }
        return -1;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof DurabilityImpl)) {
            return false;
        }
        return (this.kind == ((DurabilityImpl) other).kind);
    }

    @Override
    public int hashCode() {
        return 31 * 17 + this.kind.hashCode();
    }

    @Override
    public Kind getKind() {
        return this.kind;
    }

    @Override
    public Durability withKind(Kind kind) {
        return new DurabilityImpl(this.environment, kind);
    }

    @Override
    public Durability withVolatile() {
        return new DurabilityImpl(this.environment, Kind.VOLATILE);
    }

    @Override
    public Durability withTransientLocal() {
        return new DurabilityImpl(this.environment, Kind.TRANSIENT_LOCAL);
    }

    @Override
    public Durability withTransient() {
        return new DurabilityImpl(this.environment, Kind.TRANSIENT);
    }

    @Override
    public Durability withPersistent() {
        return new DurabilityImpl(this.environment, Kind.PERSISTENT);
    }

    @Override
    public Class<? extends QosPolicy> getPolicyClass() {
        return Durability.class;
    }
}