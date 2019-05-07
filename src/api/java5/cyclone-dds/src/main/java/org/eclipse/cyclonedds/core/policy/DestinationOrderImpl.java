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

import org.omg.dds.core.policy.DestinationOrder;
import org.omg.dds.core.policy.QosPolicy;
import org.eclipse.cyclonedds.core.IllegalArgumentExceptionImpl;
import org.eclipse.cyclonedds.core.ServiceEnvironmentImpl;

public class DestinationOrderImpl extends QosPolicyImpl implements
        DestinationOrder {
    private static final long serialVersionUID = 2722089115467848342L;
    private final Kind kind;

    public DestinationOrderImpl(ServiceEnvironmentImpl environment) {
        super(environment);
        this.kind = Kind.BY_RECEPTION_TIMESTAMP;
    }

    public DestinationOrderImpl(ServiceEnvironmentImpl environment, Kind kind) {
        super(environment);

        if (kind == null) {
            throw new IllegalArgumentExceptionImpl(environment,
                    "Supplied invalid Kind.");
        }
        this.kind = kind;
    }

    @Override
    public Comparable<DestinationOrder> requestedOfferedContract() {
        return this;
    }

    @Override
    public int compareTo(DestinationOrder o) {
        // BY_RECEPTION_TIMESTAMP < BY_SOURCE_TIMESTAMP
        if (this.kind.equals(o.getKind())) {
            return 0;
        } else if (this.kind.equals(Kind.BY_RECEPTION_TIMESTAMP)) {
            return -1;
        }
        return 1;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof DestinationOrderImpl)) {
            return false;
        }
        return (this.kind == ((DestinationOrderImpl) other).kind);
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
    public DestinationOrder withKind(Kind kind) {
        return new DestinationOrderImpl(this.environment, kind);
    }

    @Override
    public DestinationOrder withReceptionTimestamp() {
        return new DestinationOrderImpl(this.environment,
                Kind.BY_RECEPTION_TIMESTAMP);
    }

    @Override
    public DestinationOrder withSourceTimestamp() {
        return new DestinationOrderImpl(this.environment,
                Kind.BY_SOURCE_TIMESTAMP);
    }

    @Override
    public Class<? extends QosPolicy> getPolicyClass() {
        return DestinationOrder.class;
    }

}
