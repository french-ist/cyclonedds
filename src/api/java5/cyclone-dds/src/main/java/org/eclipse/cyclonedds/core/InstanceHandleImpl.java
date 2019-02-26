/*
 *                         Vortex OpenSplice
 *
 *   This software and documentation are Copyright 2006 to TO_YEAR ADLINK
 *   Technology Limited, its affiliated companies and licensors. All rights
 *   reserved.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */
package org.eclipse.cyclonedds.core;

import org.omg.dds.core.InstanceHandle;
import org.omg.dds.core.ServiceEnvironment;

public class InstanceHandleImpl extends InstanceHandle {
    private static final long serialVersionUID = 8433681503549822293L;
    private final transient CycloneServiceEnvironment environment;
    private final long value;

    public InstanceHandleImpl(CycloneServiceEnvironment environment, long value) {
        this.environment = environment;
        this.value = value;
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
                    "Cannot compare OpenSplice InstanceHandle to non-OpenSplice InstanceHandle");
        }
        if (this.value == other.getValue()) {
            return 0;
        }

        if (this.value < other.getValue()) {
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
        return this.value;
    }

    @Override
    public boolean equals(Object other){
        if(other instanceof InstanceHandleImpl){
            return (((InstanceHandleImpl)other).value == this.value);
        }
        return false;
    }

    @Override
    public String toString(){
        return "InstanceHandle (" + this.value + ")";
    }

    @Override
    public int hashCode(){
        return 31 * 17 + (int) (this.value ^ (this.value >>> 32));
    }
}
