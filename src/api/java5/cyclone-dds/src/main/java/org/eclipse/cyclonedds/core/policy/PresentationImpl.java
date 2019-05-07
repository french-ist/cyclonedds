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

import org.omg.dds.core.policy.Presentation;
import org.omg.dds.core.policy.QosPolicy;
import org.eclipse.cyclonedds.core.IllegalArgumentExceptionImpl;
import org.eclipse.cyclonedds.core.ServiceEnvironmentImpl;

public class PresentationImpl extends QosPolicyImpl implements Presentation {
    private static final long serialVersionUID = 2403290580864742451L;
    private final boolean coherentAccess;
    private final boolean orderedAccess;
    private final AccessScopeKind accessScope;

    public PresentationImpl(ServiceEnvironmentImpl environment) {
        super(environment);
        this.coherentAccess = false;
        this.orderedAccess = false;
        this.accessScope = AccessScopeKind.INSTANCE;
    }

    public PresentationImpl(ServiceEnvironmentImpl environment,
            AccessScopeKind accessScope, boolean coherentAccess,
            boolean orderedAccess) {
        super(environment);

        if (accessScope == null) {
            throw new IllegalArgumentExceptionImpl(environment,
                    "Supplied access scope is invalid.");
        }
        this.accessScope = accessScope;
        this.coherentAccess = coherentAccess;
        this.orderedAccess = orderedAccess;
    }

    @Override
    public Comparable<Presentation> requestedOfferedContract() {
        return this;
    }

    @Override
    public int compareTo(Presentation o) {
        AccessScopeKind other = o.getAccessScope();

        /* INSTANCE < TOPIC < GROUP */
        /* offered access_scope >= requested access_scope */
        if (other.equals(this.accessScope)) {
            if (this.coherentAccess == o.isCoherentAccess()) {
                if (this.orderedAccess == o.isOrderedAccess()) {
                    return 0;
                } else if (this.orderedAccess) {
                    return 1;
                }
                return -1;
            } else if (this.orderedAccess == o.isOrderedAccess()) {
                if (this.coherentAccess) {
                    return 1;
                }
                return -1;
            } else if (this.coherentAccess) {
                return 1;
            }
            return -1;
        } else if (this.accessScope.equals(AccessScopeKind.INSTANCE)) {
            return -1;
        } else if (this.accessScope.equals(AccessScopeKind.TOPIC)) {
            if (other.equals(AccessScopeKind.INSTANCE)) {
                return 1;
            }
            return -1;
        }
        return 1;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof PresentationImpl)) {
            return false;
        }
        PresentationImpl p = (PresentationImpl) other;

        if (p.coherentAccess != this.coherentAccess) {
            return false;
        }
        if (p.orderedAccess != this.orderedAccess) {
            return false;
        }
        if (p.accessScope != this.accessScope) {
            return false;
        }
        return true;
    }

    @Override
    public AccessScopeKind getAccessScope() {
        return this.accessScope;
    }

    @Override
    public boolean isCoherentAccess() {
        return this.coherentAccess;
    }

    @Override
    public boolean isOrderedAccess() {
        return this.orderedAccess;
    }

    @Override
    public Presentation withAccessScope(AccessScopeKind accessScope) {
        return new PresentationImpl(this.environment, accessScope,
                this.coherentAccess, this.orderedAccess);
    }

    @Override
    public Presentation withCoherentAccess(boolean coherentAccess) {
        return new PresentationImpl(this.environment, this.accessScope,
                coherentAccess, this.orderedAccess);
    }

    @Override
    public Presentation withOrderedAccess(boolean orderedAccess) {
        return new PresentationImpl(this.environment, this.accessScope,
                this.coherentAccess, orderedAccess);
    }

    @Override
    public Presentation withInstance() {
        return new PresentationImpl(this.environment, AccessScopeKind.INSTANCE,
                this.coherentAccess, this.orderedAccess);
    }

    @Override
    public Presentation withTopic() {
        return new PresentationImpl(this.environment, AccessScopeKind.TOPIC,
                this.coherentAccess, this.orderedAccess);
    }

    @Override
    public Presentation withGroup() {
        return new PresentationImpl(this.environment, AccessScopeKind.GROUP,
                this.coherentAccess, this.orderedAccess);
    }

    @Override
    public Class<? extends QosPolicy> getPolicyClass() {
        return Presentation.class;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 17;

        result = prime * result + this.accessScope.hashCode();
        result = prime * result + (this.coherentAccess ? 1 : 0);

        return prime * result + (this.orderedAccess ? 1 : 0);
    }
}
