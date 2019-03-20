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
package org.eclipse.cyclonedds.core;

import org.omg.dds.core.InstanceHandle;
import org.omg.dds.core.ServiceEnvironment;

public class InstanceHandleImpl extends InstanceHandle {
    private static final long serialVersionUID = 8433681503549822293L;
    private final transient CycloneServiceEnvironment environment;
    private final long keyHash;

    public InstanceHandleImpl(CycloneServiceEnvironment environment, long keyHashValue) {
        this.environment = environment;
        this.keyHash = keyHashValue;
    }

    @Override
    public int compareTo(InstanceHandle o) {
        InstanceHandleImpl other = null;

        if (o == null) {
            return -1;
        }

        try {
            other = (InstanceHandleImpl)o;
        } catch(ClassCastException cce){
            throw new IllegalOperationExceptionImpl(this.environment,
                    "Cannot compare Cyclone InstanceHandle to non-Cyclone InstanceHandle");
        }
        if (this.keyHash == other.getValue()) {
            return 0;
        }

        if (this.keyHash < other.getValue()) {
            return -1;
        }
        return 1;
    }

    @Override
    public ServiceEnvironment getEnvironment() {
        return this.environment;
    }

    @Override
    public boolean isNil() {
        //TODO FRCYC return (this.value == HANDLE_NIL.value);
    	return false;
    }

    public long getValue(){
        return this.keyHash;
    }

    @Override
    public boolean equals(Object other){
        if(other instanceof InstanceHandleImpl){
            return (((InstanceHandleImpl)other).keyHash == this.keyHash);
        }
        return false;
    }

    @Override
    public String toString(){
        return "InstanceHandle (" + this.keyHash + ")";
    }

    @Override
    public int hashCode(){
        return 31 * 17 + (int) (this.keyHash ^ (this.keyHash >>> 32));
    }
}
