/**
  Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 
  This program and the accompanying materials are made available under the
  terms of the Eclipse Public License v. 2.0 which is available at
  http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
  v. 1.0 which is available at
  http://www.eclipse.org/org/documents/edl-v10.php.
 
  SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.cyclonedds.sub;

import java.util.Collection;
import java.util.Set;

import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.sub.DataReader;
import org.omg.dds.sub.InstanceState;
import org.omg.dds.sub.ReadCondition;
import org.omg.dds.sub.SampleState;
import org.omg.dds.sub.ViewState;
import org.eclipse.cyclonedds.core.Condition;
import org.eclipse.cyclonedds.core.IllegalArgumentExceptionImpl;
import org.eclipse.cyclonedds.core.CycloneServiceEnvironment;
import org.eclipse.cyclonedds.core.Utilities;

public class ReadConditionImpl<TYPE> implements ReadCondition<TYPE>,
        Condition<Condition> {
    protected final CycloneServiceEnvironment environment;
    protected ReadCondition old;
    protected final AbstractDataReader<TYPE> parent;
    protected final DataStateImpl state;

    public ReadConditionImpl(CycloneServiceEnvironment environment,
            AbstractDataReader<TYPE> parent,
            Collection<SampleState> sampleState,
            Collection<ViewState> viewState,
            Collection<InstanceState> instanceState) {
        this(environment, parent, new DataStateImpl(environment, sampleState,
                viewState, instanceState), true);
    }

    public ReadConditionImpl(CycloneServiceEnvironment environment,
            AbstractDataReader<TYPE> parent, DataStateImpl state) {
        this(environment, parent, state, true);
    }

    public ReadConditionImpl(CycloneServiceEnvironment environment,
            AbstractDataReader<TYPE> parent) {
        this(environment, parent, DataStateImpl.any(environment), true);
    }

    public ReadConditionImpl(CycloneServiceEnvironment environment,
            AbstractDataReader<TYPE> parent, boolean setupCondition) {
        this(environment, parent, DataStateImpl.any(environment),
                setupCondition);
    }

    public ReadConditionImpl(CycloneServiceEnvironment environment,
            AbstractDataReader<TYPE> parent, DataStateImpl state,
            boolean setupCondition) {
        this.environment = environment;
        this.parent = parent;
        this.state = state;

        if (state == null) {
            throw new IllegalArgumentExceptionImpl(environment,
                    "Illegal DataState (null) provided.");
        }

        /* TODO FRCYC
        if (setupCondition) {
            this.old = parent.getOld().create_readcondition(
                    this.state.getOldSampleState(),
                    this.state.getOldViewState(),
                    this.state.getOldInstanceState());

            if (this.old == null) {
                Utilities.throwLastErrorException(this.environment);
            }
        }
        */
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
    public Set<SampleState> getSampleStates() {
        return this.state.getSampleStates();
    }

    @Override
    public Set<ViewState> getViewStates() {
        return this.state.getViewStates();
    }

    @Override
    public Set<InstanceState> getInstanceStates() {
        return this.state.getInstanceStates();
    }

    @Override
    public DataReader<TYPE> getParent() {
        return this.parent;
    }

    public ReadCondition getOld() {
        return this.old;
    }

    @Override
    public void close() {
        this.parent.destroyReadCondition(this);
    }

    public DataStateImpl getState(){
        return this.state.clone();
    }

	@Override
	public Condition getOldCondition() {
		// TODO Auto-generated method stub
		return null;
	}

   
}
