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

import org.omg.dds.core.GuardCondition;
import org.omg.dds.core.ServiceEnvironment;

public class GuardConditionImpl extends GuardCondition implements org.eclipse.cyclonedds.core.Condition<Condition> {
    private final OsplServiceEnvironment environment;
    //TODO FRCYC private final GuardCondition oldGuardCondition;


    public GuardConditionImpl(OsplServiceEnvironment environment){
        this.environment = environment;
        //this.oldGuardCondition = new GuardCondition();
    }

    /*
    public boolean getTriggerValue() {
        return this.oldGuardCondition.get_trigger_value();
    }

    @Override
    public ServiceEnvironment getEnvironment() {
        return this.environment;
    }

    @Override
    public void setTriggerValue(boolean value) {
        this.oldGuardCondition.set_trigger_value(value);
    }

    @Override
    public GuardCondition getOldCondition() {
        return this.oldGuardCondition;
    }
    */

    @Override
    public String toString() {
        return "GuardCondition (" + Integer.toHexString(hashCode()) + ")";
    }

	@Override
	public boolean getTriggerValue() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ServiceEnvironment getEnvironment() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Condition getOldCondition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTriggerValue(boolean value) {
		// TODO Auto-generated method stub
		
	}
}
