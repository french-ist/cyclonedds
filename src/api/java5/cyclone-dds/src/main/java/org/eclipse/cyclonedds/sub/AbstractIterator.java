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

import java.lang.reflect.Field;

import org.omg.dds.sub.Sample;
import org.eclipse.cyclonedds.core.AlreadyClosedExceptionImpl;
import org.eclipse.cyclonedds.core.ServiceEnvironmentImpl;
import org.eclipse.cyclonedds.core.UnsupportedOperationExceptionImpl;

//TODO FRCYC //TODO FRCYC import SampleInfoSeqHolder;

public abstract class AbstractIterator<TYPE> implements Sample.Iterator<TYPE> {
    protected final AbstractDataReader<TYPE> reader;
    protected final ServiceEnvironmentImpl environment;
    protected final SampleInfoSeqHolder infoSeqHolder;
    protected final Object sampleSeqHolder;
    protected final Field dataSeqHolderValue;
    private final SampleImpl<TYPE>[] samples;
    private int currentIndex;
    private int initUntil;

    public AbstractIterator(ServiceEnvironmentImpl environment,
            AbstractDataReader<TYPE> reader, Object sampleSeqHolder,
            Field dataSeqHolderValue, SampleInfoSeqHolder infoSeqHolder) {
        this.environment = environment;
        this.reader = reader;
        this.infoSeqHolder = infoSeqHolder;
        this.sampleSeqHolder = sampleSeqHolder;
        this.dataSeqHolderValue = dataSeqHolderValue;
        this.currentIndex = 0;
        this.initUntil = -1;
        this.samples = this.setupSampleList();
        this.reader.registerIterator(this);
    }

    protected abstract SampleImpl<TYPE>[] setupSampleList();

    protected abstract TYPE getData(int index);

    @Override
    public boolean hasNext() {
        if (this.currentIndex == -1) {
            throw new AlreadyClosedExceptionImpl(this.environment,
                    "Iterator already closed.");
        }
        /* TODO FRCYC 
        if (infoSeqHolder.value.length > this.currentIndex) {
            return true;
        }
        */
        return false;
    }

    @Override
    public boolean hasPrevious() {
        if (this.currentIndex == -1) {
            throw new AlreadyClosedExceptionImpl(this.environment,
                    "Iterator already closed.");
        }
        if ((this.currentIndex - 1) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public int nextIndex() {
        if (this.currentIndex == -1) {
            throw new AlreadyClosedExceptionImpl(this.environment,
                    "Iterator already closed.");
        }
        return this.currentIndex;
    }

    @Override
    public int previousIndex() {
        if (this.currentIndex == -1) {
            throw new AlreadyClosedExceptionImpl(this.environment,
                    "Iterator already closed.");
        }
        return this.currentIndex - 1;
    }

    @Override
    public void close() {
        if (this.currentIndex == -1) {
            throw new AlreadyClosedExceptionImpl(this.environment,
                    "Iterator already closed.");
        }
        this.currentIndex = -1;
        //TODO FRCYC this.reader.returnLoan(this.sampleSeqHolder, this.infoSeqHolder);
        this.reader.deregisterIterator(this);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationExceptionImpl(this.environment,
                "Cannot remove() from Sample.Iterator.");
    }

    @Override
    public void set(Sample<TYPE> o) {
        throw new UnsupportedOperationExceptionImpl(this.environment,
                "Cannot set() in Sample.Iterator.");
    }

    @Override
    public void add(Sample<TYPE> o) {
        throw new UnsupportedOperationExceptionImpl(this.environment,
                "Cannot add() to Sample.Iterator.");
    }

    @Override
    public Sample<TYPE> next() {
        if (this.currentIndex == -1) {
            throw new AlreadyClosedExceptionImpl(this.environment,
                    "Iterator already closed.");
        }
        int index = this.currentIndex++;
/* TODO FRCYC
        if (this.initUntil < index) {
            this.samples[index] = new SampleImpl<TYPE>(this.environment,
                    this.getData(index), this.infoSeqHolder.value[index]);
            this.initUntil++;
        }
        */
        return this.samples[index];
    }

    @Override
    public Sample<TYPE> previous() {
        if (this.currentIndex == -1) {
            throw new AlreadyClosedExceptionImpl(this.environment,
                    "Iterator already closed.");
        }
        return this.samples[--this.currentIndex];
    }
}
