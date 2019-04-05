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

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import org.omg.dds.core.Entity;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.StatusCondition;
import org.omg.dds.core.status.Status;
import org.eclipse.cyclonedds.core.status.StatusConverter;

public class StatusConditionImpl<T extends Entity<?, ?>> implements
        StatusCondition<T>, Condition {
    private final StatusCondition old;
    private final T parent;
    private final CycloneServiceEnvironment environment;

    public StatusConditionImpl(CycloneServiceEnvironment environment,
            StatusCondition oldCondition, T parent) {
        this.old = oldCondition;
        this.parent = parent;
        this.environment = environment;
    }

    @Override
    public boolean getTriggerValue() {
        //TODO FRCYC return this.old.get_trigger_value();
    	return false;
    }

    @Override
    public ServiceEnvironment getEnvironment() {
        return this.environment;
    }

    @Override
    public T getParent() {
        return this.parent;
    }

    /* TODO FRCYC
    @Override
    public Set<Class<? extends Status>> getEnabledStatuses() {
        return StatusConverter.convertMask(this.environment,
                this.old.get_enabled_statuses());
    }

    @Override
    public void setEnabledStatuses(Collection<Class<? extends Status>> statuses) {
        int rc = this.old.set_enabled_statuses(StatusConverter.convertMask(
                this.environment, statuses));

        Utilities.checkReturnCode(rc, this.environment,
                "StatusCondition.withEnabledStatuses() failed.");
    }*/

    @Override
    public void setEnabledStatuses(Class<? extends Status>... statuses) {
        this.setEnabledStatuses(Arrays.asList(statuses));

    }



    @Override
    public String toString(){
        return "StatusCondition (" + Integer.toHexString(this.hashCode()) + ")";
    }

	@Override
	public Set<Class<? extends Status>> getEnabledStatuses() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setEnabledStatuses(Collection<Class<? extends Status>> statuses) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Condition getOldCondition() {
		// TODO Auto-generated method stub
		return null;
	}
}
