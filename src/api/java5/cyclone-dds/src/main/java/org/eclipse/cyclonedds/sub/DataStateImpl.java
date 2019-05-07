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
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.sub.InstanceState;
import org.omg.dds.sub.SampleState;
import org.omg.dds.sub.Subscriber;
import org.omg.dds.sub.Subscriber.DataState;
import org.eclipse.cyclonedds.core.IllegalArgumentExceptionImpl;
import org.eclipse.cyclonedds.core.ServiceEnvironmentImpl;
import org.eclipse.cyclonedds.core.UnsupportedOperationExceptionImpl;
import org.eclipse.cyclonedds.ddsc.dds.DdscLibrary;
import org.omg.dds.sub.ViewState;

public class DataStateImpl implements Subscriber.DataState {
    private ServiceEnvironmentImpl environment;
    private HashSet<SampleState> sampleState;
    private HashSet<ViewState> viewState;
    private HashSet<InstanceState> instanceState;

    public DataStateImpl(ServiceEnvironmentImpl environment,
            Collection<SampleState> sampleState,
            Collection<ViewState> viewState,
            Collection<InstanceState> instanceState) {
        this.environment = environment;
        this.sampleState = new HashSet<SampleState>(sampleState);
        this.viewState = new HashSet<ViewState>(viewState);
        this.instanceState = new HashSet<InstanceState>(instanceState);
    }

    public DataStateImpl(ServiceEnvironmentImpl environment) {
        this.environment = environment;
        this.sampleState = new HashSet<SampleState>();
        this.viewState = new HashSet<ViewState>();
        this.instanceState = new HashSet<InstanceState>();
    }

    public static DataStateImpl getAnyStateDataState(ServiceEnvironmentImpl env) {
        return (DataStateImpl) new DataStateImpl(env).withAnySampleState()
                .withAnyViewState().withAnyInstanceState();
    }

    public static SampleState getSampleStateFromJna(ServiceEnvironmentImpl env,
            int state) {
    	switch (state) {
        case DdscLibrary.DDS_READ_SAMPLE_STATE:
            return SampleState.READ;
        case DdscLibrary.DDS_NOT_READ_SAMPLE_STATE:
            return SampleState.NOT_READ;
        default:
            throw new IllegalArgumentExceptionImpl(env, "Invalid SampleState");
        }
    }

    public static ViewState getViewStateFromJna(ServiceEnvironmentImpl env,
            int state) {
        switch (state) {
        case DdscLibrary.DDS_NEW_VIEW_STATE:
            return ViewState.NEW;
        case DdscLibrary.DDS_NOT_NEW_VIEW_STATE:
            return ViewState.NOT_NEW;
        default:
            throw new IllegalArgumentExceptionImpl(env, "Invalid ViewState");
        }
    }

    public static InstanceState getInstanceStateFromJna(
            ServiceEnvironmentImpl env, int state) {
        switch (state) {
        case DdscLibrary.DDS_ALIVE_INSTANCE_STATE:
            return InstanceState.ALIVE;
        case DdscLibrary.DDS_NOT_ALIVE_DISPOSED_INSTANCE_STATE:
            return InstanceState.NOT_ALIVE_DISPOSED;
        case DdscLibrary.DDS_NOT_ALIVE_NO_WRITERS_INSTANCE_STATE:
            return InstanceState.NOT_ALIVE_NO_WRITERS;
        default:
            throw new IllegalArgumentExceptionImpl(env, "Invalid InstanceState");
        }
    }

    @Override
    public ServiceEnvironment getEnvironment() {
        return this.environment;
    }

    @Override
    public Set<SampleState> getSampleStates() {
        return Collections.unmodifiableSet(this.sampleState);
    }

    @Override
    public Set<ViewState> getViewStates() {
        return Collections.unmodifiableSet(this.viewState);
    }

    @Override
    public Set<InstanceState> getInstanceStates() {
        return Collections.unmodifiableSet(this.instanceState);
    }

    @Override
    public DataState with(SampleState state) {
        HashSet<SampleState> s = new HashSet<SampleState>(this.sampleState);
        s.add(state);

        return new DataStateImpl(this.environment, s, this.viewState,
                this.instanceState);
    }

    @Override
    public DataState with(ViewState state) {
        HashSet<ViewState> s = new HashSet<ViewState>(this.viewState);
        s.add(state);

        return new DataStateImpl(this.environment, this.sampleState, s,
                this.instanceState);
    }

    @Override
    public DataState with(InstanceState state) {
        HashSet<InstanceState> s = new HashSet<InstanceState>(
                this.instanceState);
        s.add(state);

        return new DataStateImpl(this.environment, this.sampleState,
                this.viewState, s);
    }

    @Override
    public DataState withAnySampleState() {
        HashSet<SampleState> s = new HashSet<SampleState>(this.sampleState);

        s.add(SampleState.READ);
        s.add(SampleState.NOT_READ);

        return new DataStateImpl(this.environment, s, this.viewState,
                this.instanceState);
    }

    @Override
    public DataState withAnyViewState() {
        HashSet<ViewState> s = new HashSet<ViewState>(this.viewState);

        s.add(ViewState.NEW);
        s.add(ViewState.NOT_NEW);

        return new DataStateImpl(this.environment, this.sampleState, s,
                this.instanceState);
    }

    @Override
    public DataState withAnyInstanceState() {
        HashSet<InstanceState> s = new HashSet<InstanceState>(
                this.instanceState);

        s.add(InstanceState.ALIVE);
        s.add(InstanceState.NOT_ALIVE_DISPOSED);
        s.add(InstanceState.NOT_ALIVE_NO_WRITERS);

        return new DataStateImpl(this.environment, this.sampleState,
                this.viewState, s);
    }

    @Override
    public DataState withNotAliveInstanceStates() {
        HashSet<InstanceState> s = new HashSet<InstanceState>(
                this.instanceState);

        s.remove(InstanceState.ALIVE);
        s.add(InstanceState.NOT_ALIVE_DISPOSED);
        s.add(InstanceState.NOT_ALIVE_NO_WRITERS);

        return new DataStateImpl(this.environment, this.sampleState,
                this.viewState, s);
    }

    public int getOldSampleState() {
    	/* TODO FRCYC
        int result;

        boolean read = this.sampleState.contains(SampleState.READ);
        boolean notRead = this.sampleState.contains(SampleState.NOT_READ);

        if (read && notRead) {
            result = ANY_SAMPLE_STATE.value;
        } else if (read) {
            result = READ_SAMPLE_STATE.value;
        } else if (notRead) {
            result = NOT_READ_SAMPLE_STATE.value;
        } else {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Incomplete DataState: no SampleState set.");
        }
        return result;
        */
        return 0;
    }

    public void withOldSampleState(int state) {
    	/* TODO FRCYC
        switch (state) {
        case ANY_INSTANCE_STATE.value:
            this.sampleState.add(SampleState.READ);
            this.sampleState.add(SampleState.NOT_READ);
            break;
        case READ_SAMPLE_STATE.value:
            this.sampleState.add(SampleState.READ);
            break;
        case NOT_READ_SAMPLE_STATE.value:
            this.sampleState.add(SampleState.NOT_READ);
            break;
        default:
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Invalid SampleState");
        }
        */
    }

    public int getOldViewState() {
    	/* TODO FRCYC
        int result;

        boolean _new = this.viewState.contains(ViewState.NEW);
        boolean notNew = this.viewState.contains(ViewState.NOT_NEW);

        if (_new && notNew) {
            result = ANY_VIEW_STATE.value;
        } else if (_new) {
            result = NEW_VIEW_STATE.value;
        } else if (notNew) {
            result = NOT_NEW_VIEW_STATE.value;
        } else {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Incomplete DataState: no ViewState set.");
        }
        return result;
        */
    	return 0;
    }

    public void withOldViewState(int state) {
    	/* TODO FRCYC
        switch (state) {
        case ANY_VIEW_STATE.value:
            this.viewState.add(ViewState.NEW);
            this.viewState.add(ViewState.NOT_NEW);
            break;
        case NEW_VIEW_STATE.value:
            this.viewState.add(ViewState.NEW);
            break;
        case NOT_NEW_VIEW_STATE.value:
            this.viewState.add(ViewState.NOT_NEW);
            break;
        default:
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Invalid ViewState");
        }
        */
    }

    public int getOldInstanceState() {
        int result = 0;

        /* TODO FRCYC
        boolean alive = this.instanceState.contains(InstanceState.ALIVE);
        boolean disposed = this.instanceState
                .contains(InstanceState.NOT_ALIVE_DISPOSED);
        boolean noWriters = this.instanceState
                .contains(InstanceState.NOT_ALIVE_NO_WRITERS);

        if (alive) {
            result |= ALIVE_INSTANCE_STATE.value;
        }
        if (disposed) {
            result |= NOT_ALIVE_DISPOSED_INSTANCE_STATE.value;
        }
        if (noWriters) {
            result |= NOT_ALIVE_NO_WRITERS_INSTANCE_STATE.value;
        }
        
        if (alive && disposed && noWriters) {
            result = ANY_INSTANCE_STATE.value;
        }
        if (result == 0) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Incomplete DataState: no InstanceState set.");
        }*/
        return result;
        
    }

    public void withOldInstanceState(int state) {
    	/* TODO FRCYC
        switch (state) {
        case ANY_INSTANCE_STATE.value:
            this.instanceState.add(InstanceState.ALIVE);
            this.instanceState.add(InstanceState.NOT_ALIVE_DISPOSED);
            this.instanceState.add(InstanceState.NOT_ALIVE_NO_WRITERS);
            break;
        case NOT_ALIVE_INSTANCE_STATE.value:
            this.instanceState.remove(InstanceState.ALIVE);
            this.instanceState.add(InstanceState.NOT_ALIVE_DISPOSED);
            this.instanceState.add(InstanceState.NOT_ALIVE_NO_WRITERS);
            break;
        case ALIVE_INSTANCE_STATE.value:
            this.instanceState.add(InstanceState.ALIVE);
            break;
        case NOT_ALIVE_DISPOSED_INSTANCE_STATE.value:
            this.instanceState.add(InstanceState.NOT_ALIVE_DISPOSED);
            break;
        case NOT_ALIVE_NO_WRITERS_INSTANCE_STATE.value:
            this.instanceState.add(InstanceState.NOT_ALIVE_NO_WRITERS);
            break;
        default:
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Invalid InstanceState");
        }
        */
    }

    public static DataStateImpl any(ServiceEnvironmentImpl environment) {
        return (DataStateImpl) new DataStateImpl(environment)
                .withAnySampleState().withAnyViewState().withAnyInstanceState();
    }

    @SuppressWarnings("unchecked")
    @Override
    public DataStateImpl clone() {
        try {
            DataStateImpl cloned = (DataStateImpl) super.clone();

            cloned.environment = this.environment;
            cloned.instanceState = (HashSet<InstanceState>) this.instanceState
                    .clone();
            cloned.sampleState = (HashSet<SampleState>) this.sampleState
                    .clone();
            cloned.viewState = (HashSet<ViewState>) this.viewState.clone();

            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new UnsupportedOperationExceptionImpl(this.environment,
                    "Cloning of DataState not supported.");
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof DataStateImpl)) {
            return false;
        }
        DataStateImpl d = (DataStateImpl) other;

        if (this.instanceState.size() != d.instanceState.size()) {
            return false;
        }
        if (!d.instanceState.containsAll(this.instanceState)) {
            return false;
        }
        if (this.sampleState.size() != d.sampleState.size()) {
            return false;
        }
        if (!d.sampleState.containsAll(this.sampleState)) {
            return false;
        }
        if (this.viewState.size() != d.viewState.size()) {
            return false;
        }
        if (!d.viewState.containsAll(this.viewState)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 17;

        for (InstanceState s : this.instanceState) {
            result = prime * result + s.hashCode();
        }
        for (SampleState s : this.sampleState) {
            result = prime * result + s.hashCode();
        }
        for (ViewState s : this.viewState) {
            result = prime * result + s.hashCode();
        }
        return result;
    }
}
