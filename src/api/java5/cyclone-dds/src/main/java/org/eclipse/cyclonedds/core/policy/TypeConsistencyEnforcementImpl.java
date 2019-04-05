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

import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.policy.TypeConsistencyEnforcement;
import org.eclipse.cyclonedds.core.CycloneServiceEnvironment;

public class TypeConsistencyEnforcementImpl implements TypeConsistencyEnforcement {
    private static final long serialVersionUID = -5160265825622794754L;
    private final Kind kind;
    private final transient CycloneServiceEnvironment environment;

    public TypeConsistencyEnforcementImpl(CycloneServiceEnvironment environment, Kind kind){
        this.environment = environment;
        this.kind = kind;
    }

    @Override
    public ServiceEnvironment getEnvironment() {
        return this.environment;
    }

    @Override
    public Comparable<TypeConsistencyEnforcement> requestedOfferedContract() {
        return this;
    }

    @Override
    public int compareTo(TypeConsistencyEnforcement o) {
        Kind other = o.getKind();
        
        if(other.equals(this.kind)){
            return 0;
        } else if(this.kind.equals(Kind.EXACT_TYPE_TYPE_CONSISTENCY)){
            return 1;
        } else if(this.kind.equals(Kind.EXACT_NAME_TYPE_CONSISTENCY)){
            if(other.equals(Kind.EXACT_TYPE_TYPE_CONSISTENCY)){
                return -1;
            }
            return 1;
        } else if(this.kind.equals(Kind.DECLARED_TYPE_CONSISTENCY)){
            if(other.equals(Kind.ASSIGNABLE_TYPE_CONSISTENCY)){
                return 1;
            }
            return -1;
        }
        return -1;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof TypeConsistencyEnforcementImpl) {
            if (this.compareTo((TypeConsistencyEnforcementImpl) other) == 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 31 * 17 + this.kind.ordinal();
    }

    @Override
    public Kind getKind() {
        return this.kind;
    }

    @Override
    public TypeConsistencyEnforcement withKind(Kind kind) {
        return new TypeConsistencyEnforcementImpl(this.environment, kind);
    }

    @Override
    public TypeConsistencyEnforcement withExactTypeTypeConsistency() {
        return new TypeConsistencyEnforcementImpl(this.environment, Kind.EXACT_TYPE_TYPE_CONSISTENCY);
    }

    @Override
    public TypeConsistencyEnforcement withExactNameTypeConsistency() {
        return new TypeConsistencyEnforcementImpl(this.environment, Kind.EXACT_NAME_TYPE_CONSISTENCY);
    }

    @Override
    public TypeConsistencyEnforcement withDeclaredTypeConsistency() {
        return new TypeConsistencyEnforcementImpl(this.environment, Kind.DECLARED_TYPE_CONSISTENCY);
    }

    @Override
    public TypeConsistencyEnforcement withAssignableTypeConsistency() {
        return new TypeConsistencyEnforcementImpl(this.environment, Kind.ASSIGNABLE_TYPE_CONSISTENCY);
    }

}
