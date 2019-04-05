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

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.omg.dds.core.Condition;
import org.omg.dds.core.Duration;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.WaitSet;

public class WaitSetImpl extends WaitSet {
    private final CycloneServiceEnvironment environment;
    //TODO FRCYC private final WaitSet oldWaitSet;
    private ConcurrentHashMap<Condition, org.omg.dds.core.Condition> conditions;

    public WaitSetImpl(CycloneServiceEnvironment environment) {
        this.environment = environment;
        //this.oldWaitSet = new WaitSet();
        this.conditions = new ConcurrentHashMap<Condition, org.omg.dds.core.Condition>();
    }

    @Override
    public ServiceEnvironment getEnvironment() {
        return this.environment;
    }

    /* TODO FRCYC
    @Override
    public void waitForConditions() {
        ConditionSeqHolder holder = new ConditionSeqHolder();
        int rc = this.oldWaitSet._wait(holder, DURATION_INFINITE.value);

        Utilities.checkReturnCode(rc, this.environment,
                "Waitset.waitForConditions() failed.");

        return;
    }

    @Override
    public void waitForConditions(Collection<Condition> activeConditions) {
        if (activeConditions == null) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Illegal Collection<Condition> (null) provided.");
        }

        ConditionSeqHolder holder = new ConditionSeqHolder();
        int rc = this.oldWaitSet._wait(holder, DURATION_INFINITE.value);

        Utilities.checkReturnCode(rc, this.environment,
                "Waitset.waitForConditions() failed.");

        activeConditions.clear();

        for (Condition cond : holder.value) {
            activeConditions.add(this.conditions.get(cond));
        }
    }

    @Override
    public void waitForConditions(Duration timeout) throws TimeoutException {
        if (timeout == null) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Illegal Duration (null) provided.");
        }
        ConditionSeqHolder holder = new ConditionSeqHolder();
        Duration_t oldTimeout = Utilities
                .convert(this.environment, timeout);

        int rc = this.oldWaitSet._wait(holder, oldTimeout);

        Utilities.checkReturnCodeWithTimeout(rc, this.environment,
                "Waitset.waitForConditions() failed.");
    }

    @Override
    public void waitForConditions(long timeout, TimeUnit unit)
            throws TimeoutException {
        this.waitForConditions(this.environment.getSPI().newDuration(timeout,
                unit));
    }

    @Override
    public void waitForConditions(Collection<Condition> activeConditions,
            Duration timeout) throws TimeoutException {

        if (activeConditions == null) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Illegal Collection<Condition> (null) provided.");

        }
        ConditionSeqHolder holder = new ConditionSeqHolder();
        Duration_t oldTimeout = Utilities
                .convert(this.environment, timeout);

        int rc = this.oldWaitSet._wait(holder, oldTimeout);

        Utilities.checkReturnCode(rc, this.environment,
                "Waitset.waitForConditions() failed.");

        activeConditions.clear();

        for (Condition cond : holder.value) {
            activeConditions.add(this.conditions.get(cond));
        }

    }

    @Override
    public void waitForConditions(Collection<Condition> activeConditions,
            long timeout, TimeUnit unit) throws TimeoutException {
        this.waitForConditions(activeConditions, this.environment.getSPI()
                .newDuration(timeout, unit));

    }

    @Override
    public void attachCondition(Condition cond) {
        org.eclipse.cyclonedds.core.Condition<?> c;

        if (cond == null) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Illegal Condition (null) provided.");
        }
        try {
            c = (org.eclipse.cyclonedds.core.Condition<?>) cond;
        } catch (ClassCastException cce) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Attaching non-OpenSplice Condition implementation is not supported.");
        }
        Condition old = c.getOldCondition();
        int rc = this.oldWaitSet.attach_condition(old);
        Utilities.checkReturnCode(rc, this.environment,
                "Attaching condition failed.");

        this.conditions.put(old, cond);

    }

    @Override
    public void detachCondition(Condition cond) {
        org.eclipse.cyclonedds.core.Condition<?> c;

        if (cond == null) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Illegal Condition (null) provided.");
        }
        try {
            c = (org.eclipse.cyclonedds.core.Condition<?>) cond;
        } catch (ClassCastException cce) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Detaching non-OpenSplice Condition implementation is not supported.");
        }
        Condition old = c.getOldCondition();
        int rc = this.oldWaitSet.detach_condition(old);
        Utilities.checkReturnCode(rc, this.environment,
                "Detaching condition failed.");

        this.conditions.remove(old);

    }
    */

    @Override
    public Collection<Condition> getConditions() {
        return Collections.unmodifiableCollection(conditions.values());
    }

    @Override
    public String toString() {
        return "WaitSet (" + Integer.toHexString(this.hashCode()) + ")";
    }

	@Override
	public void waitForConditions() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void waitForConditions(Collection<Condition> activeConditions) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void waitForConditions(Duration timeout) throws TimeoutException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void waitForConditions(long timeout, TimeUnit unit) throws TimeoutException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void waitForConditions(Collection<Condition> activeConditions, Duration timeout) throws TimeoutException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void waitForConditions(Collection<Condition> activeConditions, long timeout, TimeUnit unit)
			throws TimeoutException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void attachCondition(Condition cond) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void detachCondition(Condition cond) {
		// TODO Auto-generated method stub
		
	}
}
