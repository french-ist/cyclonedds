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

import org.omg.dds.core.InstanceHandle;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.Time;
import org.omg.dds.sub.InstanceState;
import org.omg.dds.sub.SampleState;
import org.omg.dds.sub.ViewState;

import org.eclipse.cyclonedds.core.InstanceHandleImpl;
import org.eclipse.cyclonedds.core.ModifiableTimeImpl;

import java.util.concurrent.TimeUnit;

import org.eclipse.cyclonedds.core.ServiceEnvironmentImpl;
import org.eclipse.cyclonedds.core.UnsupportedOperationExceptionImpl;
import org.eclipse.cyclonedds.core.Utilities;
import org.eclipse.cyclonedds.utils.TimeHelper;

public class SampleImpl<TYPE> implements org.eclipse.cyclonedds.sub.Sample<TYPE> {
    private static final long serialVersionUID = 1010323161410625511L;
    private transient ServiceEnvironmentImpl environment;
    private TYPE data;
    private SampleInfo info;

    public SampleImpl(ServiceEnvironmentImpl environment, TYPE data,
            SampleInfo info) {
        this.environment = environment;
        this.data = data;
        this.info = info;
    }

    @Override
    public ServiceEnvironment getEnvironment() {
        return this.environment;
    }

    @Override
    public TYPE getData() {
        if (this.info.valid_data == false) {
            return null;
        }
        return this.data;
    }

    @Override
    public TYPE getKeyValue() {
        return this.data;
    }

    public void setData(TYPE data){
        this.data = data;
    }

    public void setInfo(SampleInfo info){
        this.info = info;
    }

    public SampleInfo getInfo(){
        return this.info;
    }

    public void setContent(TYPE data, SampleInfo info){
        this.data = data;
        this.info = info;
    }

    @Override
    public SampleState getSampleState() {
        return DataStateImpl.getSampleStateFromJna(this.environment,
                this.info.sample_state);
    }

    @Override
    public ViewState getViewState() {
        return DataStateImpl.getViewStateFromJna(this.environment,
                this.info.view_state);
    }

    @Override
    public InstanceState getInstanceState() {
        return DataStateImpl.getInstanceStateFromJna(this.environment,
                this.info.instance_state);
    }

    @Override
    public Time getSourceTimestamp() {    	
    	
    	return new ModifiableTimeImpl(environment, 
    			info.source_timestamp == null ? TimeHelper.TIME_INVALID:
    			info.source_timestamp.getDuration(TimeUnit.NANOSECONDS));
    }

    @Override
    public InstanceHandle getInstanceHandle() {
        return new InstanceHandleImpl(this.environment,
                this.info.instance_handle);
    }

    @Override
    public InstanceHandle getPublicationHandle() {
        return new InstanceHandleImpl(this.environment,
                this.info.publication_handle);
    }

    @Override
    public int getDisposedGenerationCount() {
        return this.info.disposed_generation_count;
    }

    @Override
    public int getNoWritersGenerationCount() {
        return this.info.no_writers_generation_count;
    }

    @Override
    public int getSampleRank() {
        return this.info.sample_rank;
    }

    @Override
    public int getGenerationRank() {
        return this.info.generation_rank;
    }

    @Override
    public int getAbsoluteGenerationRank() {
        return this.info.absolute_generation_rank;
    }

    @Override
    public SampleImpl<TYPE> clone() {
        try {
            @SuppressWarnings("unchecked")
            SampleImpl<TYPE> cloned = (SampleImpl<TYPE>) super.clone();
            cloned.environment = this.environment;
            cloned.data = this.data;
            cloned.info.absolute_generation_rank = this.info.absolute_generation_rank;
            cloned.info.disposed_generation_count = this.info.disposed_generation_count;
            cloned.info.generation_rank = this.info.generation_rank;
            cloned.info.instance_handle = this.info.instance_handle;
            cloned.info.no_writers_generation_count = this.info.no_writers_generation_count;
            cloned.info.publication_handle = this.info.publication_handle;
            /* TODO FRCYC
            cloned.info.reception_timestamp.sec = this.info.reception_timestamp.sec;
            cloned.info.reception_timestamp.nanosec = this.info.reception_timestamp.nanosec;
            cloned.info.sample_rank = this.info.sample_rank;
            cloned.info.sample_state = this.info.sample_state;
            cloned.info.source_timestamp.sec = this.info.source_timestamp.sec;
            cloned.info.source_timestamp.nanosec = this.info.source_timestamp.nanosec;*/
            cloned.info.valid_data = this.info.valid_data;
            cloned.info.view_state = this.info.view_state;

            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new UnsupportedOperationExceptionImpl(this.environment,
                    "Cloning of Sample not supported.");
        }
    }
}
