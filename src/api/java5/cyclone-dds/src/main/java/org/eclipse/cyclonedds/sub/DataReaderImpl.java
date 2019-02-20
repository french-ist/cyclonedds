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
package org.eclipse.cyclonedds.sub;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.omg.dds.core.InstanceHandle;
import org.omg.dds.core.status.Status;
import org.omg.dds.sub.DataReaderListener;
import org.omg.dds.sub.DataReaderQos;
import org.omg.dds.sub.Sample;
import org.omg.dds.sub.Sample.Iterator;
import org.eclipse.cyclonedds.core.IllegalArgumentExceptionImpl;
import org.eclipse.cyclonedds.core.OsplServiceEnvironment;
import org.eclipse.cyclonedds.core.Utilities;
import org.eclipse.cyclonedds.core.status.StatusConverter;
import org.eclipse.cyclonedds.topic.TopicDescriptionExt;

public class DataReaderImpl<TYPE> extends AbstractDataReader<TYPE> {
    private final ReflectionDataReader<TYPE, TYPE> reflectionReader;
    private final ArrayList<PreAllocatorImpl<TYPE>> preallocteList;

    public DataReaderImpl(OsplServiceEnvironment environment,
            SubscriberImpl parent, TopicDescriptionExt<TYPE> topicDescription,
            DataReader old) {
        super(environment, parent, topicDescription);
        this.setOld(old);
        this.reflectionReader = new ReflectionDataReader<TYPE, TYPE>(
                this.environment, this, topicDescription.getTypeSupport()
                        .getType());
        this.preallocteList = new ArrayList<PreAllocatorImpl<TYPE>>();

        this.topicDescription.retain();
    }

    public DataReaderImpl(OsplServiceEnvironment environment,
            SubscriberImpl parent, TopicDescriptionExt<TYPE> topicDescription,
            DataReaderQos qos, DataReaderListener<TYPE> listener,
            Collection<Class<? extends Status>> statuses) {
        super(environment, parent, topicDescription);

        if (qos == null) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Supplied DataReaderQos is null.");
        }
        if (topicDescription == null) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Supplied TopicDescription is null.");
        }

        DataReaderQos oldQos;

        try {
            oldQos = ((DataReaderQosImpl) qos).convert();
        } catch (ClassCastException e) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Cannot create DataReader with non-OpenSplice qos");
        }

        if (listener != null) {
            this.listener = new DataReaderListenerImpl<TYPE>(this.environment,
                    this, listener, true);
        } else {
            this.listener = null;
        }
        /* TODO FRCYC
        DataReader old = this.parent.getOld().create_datareader(
                topicDescription.getOld(), oldQos, this.listener,
                StatusConverter.convertMask(this.environment, statuses));

        if (old == null) {
            Utilities.throwLastErrorException(this.environment);
        }
        
        this.setOld(old);
        */
        this.reflectionReader = new ReflectionDataReader<TYPE, TYPE>(
                this.environment, this, topicDescription.getTypeSupport()
                        .getType());
        this.preallocteList = new ArrayList<PreAllocatorImpl<TYPE>>();
        this.topicDescription.retain();

        if (this.listener != null) {
            this.listener.setInitialised();
        }
    }

    @Override
    protected void destroy() {
        super.destroy();
        this.topicDescription.close();
    }

    @Override
    protected ReflectionDataReader<?, TYPE> getReflectionReader() {
        return this.reflectionReader;
    }

    @Override
    public PreAllocator<TYPE> getPreAllocator(List<Sample<TYPE>> samples,
            Class<?> sampleSeqHolderClz, Field sampleSeqHolderValueField) {
        PreAllocatorImpl<TYPE> pa = null;

        synchronized (this.preallocteList) {
            if (samples != null) {
                for (PreAllocatorImpl<TYPE> paImpl : this.preallocteList) {
                    if (paImpl.getSampleList() == samples) {
                        pa = paImpl;
                        break;
                    }
                }
            }
            if (pa == null) {
                pa = new PreAllocatorImpl<TYPE>(this.environment,
                        sampleSeqHolderClz, sampleSeqHolderValueField, this
                                .getTopicDescription().getTypeSupport()
                                .getType(), samples);
                this.preallocteList.add(pa);
            } else {
                pa.setSampleList(samples);
            }
        }
        return pa;
    }

    @Override
    public TYPE getKeyValue(TYPE keyHolder, InstanceHandle handle) {
        return this.reflectionReader.getKeyValue(keyHolder, handle);
    }

    @Override
    public TYPE getKeyValue(InstanceHandle handle) {
        return this.reflectionReader.getKeyValue(handle);
    }

    @Override
    public InstanceHandle lookupInstance(TYPE keyHolder) {
        return this.reflectionReader.lookupInstance(keyHolder);
    }

    @Override
    public boolean readNextSample(Sample<TYPE> sample) {
        return this.reflectionReader.readNextSample((SampleImpl<TYPE>) sample);
    }

    @Override
    public boolean takeNextSample(Sample<TYPE> sample) {
        return this.reflectionReader.takeNextSample((SampleImpl<TYPE>) sample);
    }

    /*
    @Override
    public Iterator<TYPE> createIterator(Object sampleSeqHolder,
            Field sampleSeqHolderValueField, SampleInfoSeqHolder info) {
        return new IteratorImpl<TYPE>(this.environment, this, sampleSeqHolder,
                sampleSeqHolderValueField, info);
    }*/

	@Override
	public void enable() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<Class<? extends Status>> getStatusChanges() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InstanceHandle getInstanceHandle() {
		// TODO Auto-generated method stub
		return null;
	}
}
