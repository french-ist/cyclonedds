/**
  Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 
  This program and the accompanying materials are made available under the
  terms of the Eclipse Public License v. 2.0 which is available at
  http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
  v. 1.0 which is available at
  http://www.eclipse.org/org/documents/edl-v10.php.
 
  SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.cyclonedds.core;

import org.omg.dds.core.GuardCondition;
import org.omg.dds.core.ServiceEnvironment;

public class GuardConditionImpl extends GuardCondition implements org.eclipse.cyclonedds.core.Condition<Condition> {
    private final ServiceEnvironmentImpl environment;
    //TODO FRCYC private final GuardCondition oldGuardCondition;


    public GuardConditionImpl(ServiceEnvironmentImpl environment){
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
