/**
 * Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
 * v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.cyclonedds.sub;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.omg.dds.sub.Sample;
import org.eclipse.cyclonedds.core.DDSExceptionImpl;
import org.eclipse.cyclonedds.core.IllegalArgumentExceptionImpl;
import org.eclipse.cyclonedds.core.CycloneServiceEnvironment;

//TODO FRCYC import SampleInfo;
//TODO FRCYC import SampleInfoSeqHolder;

public class PreAllocatorImpl<TYPE> implements PreAllocator<TYPE> {
    private final CycloneServiceEnvironment environment;
    private final Class<?> dataSeqHolderClaz;
    private final Class<TYPE> dataClaz;
    private final Field dataSeqHolderValueField;

    private SampleInfoSeqHolder infoSeqHolder;
    private Object dataSeqHolder;
    private List<Sample<TYPE>> sampleList;
    private int lastLength;

    public PreAllocatorImpl(CycloneServiceEnvironment environment,
            Class<?> dataSeqHolderClaz, Field dataSeqHolderValueField,
            Class<TYPE> dataClaz, List<Sample<TYPE>> preAllocated) {
        this.environment = environment;
        this.sampleList = preAllocated;
        this.dataSeqHolderClaz = dataSeqHolderClaz;
        this.dataClaz = dataClaz;
        this.dataSeqHolderValueField = dataSeqHolderValueField;
        this.lastLength = -1;
        this.setSampleList(preAllocated);
    }

    @Override
    public void setSampleList(List<Sample<TYPE>> preAllocated) {
        try {
            if (preAllocated == null) {
                this.sampleList = new ArrayList<Sample<TYPE>>();
                this.infoSeqHolder = new SampleInfoSeqHolder();
                this.dataSeqHolder = dataSeqHolderClaz.newInstance();
            } else if (preAllocated == this.sampleList) {
                if (this.lastLength != preAllocated.size()) {
                    if (this.lastLength == -1) {
                        this.infoSeqHolder = new SampleInfoSeqHolder();
                        this.dataSeqHolder = dataSeqHolderClaz.newInstance();
                    }/* TODO FRCYC
                    this.infoSeqHolder.value = new SampleInfo[preAllocated
                            .size()];
                    this.dataSeqHolderValueField.set(this.dataSeqHolder,
                            Array.newInstance(dataClaz, preAllocated.size()));
                    this.copyData();
                    */
                }
            } else {
                this.sampleList = preAllocated;
                this.infoSeqHolder = new SampleInfoSeqHolder();
                this.dataSeqHolder = dataSeqHolderClaz.newInstance();
                this.copyData();
            }
            this.lastLength = this.sampleList.size();
        } catch (InstantiationException e) {
            throw new DDSExceptionImpl(this.environment, "Internal error ("
                    + e.getMessage() + ").");
        } catch (IllegalAccessException e) {
            throw new DDSExceptionImpl(this.environment, "Internal error ("
                    + e.getMessage() + ").");
        } catch (ClassCastException ce) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Usage of non-OpenSplice Sample implementation is not supported.");
        }
    }

    private void copyData() {
        int i = 0;

        try {
            //TODO FRCYC this.infoSeqHolder.value = new SampleInfo[this.sampleList.size()];
            this.dataSeqHolderValueField.set(this.dataSeqHolder,
                    Array.newInstance(dataClaz, this.sampleList.size()));
            Object dataValue = this.dataSeqHolderValueField
                    .get(this.dataSeqHolder);

            for (Sample<TYPE> sample : this.sampleList) {
            	/* TODO FRCYC 
                this.infoSeqHolder.value[i] = ((SampleImpl<TYPE>) sample)
                        .getInfo();
                Array.set(dataValue, i++, sample.getData());
                */
            }
        } catch (IllegalAccessException e) {
            throw new DDSExceptionImpl(this.environment, "Internal error ("
                    + e.getMessage() + ").");
        } catch (ClassCastException ce) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Usage of non-OpenSplice Sample implementation is not supported.");
        }
    }

    @Override
    public void updateReferences() {
        this.updateReferencesImproved();
    }

    @SuppressWarnings("unchecked")
    private void updateReferencesImproved() {
        int index;

        assert (this.lastLength == this.sampleList.size());
        /* TODO FRCYC
        if (this.infoSeqHolder.value.length > this.lastLength) {
            try {
                Object dataValue = this.dataSeqHolderValueField
                        .get(this.dataSeqHolder);
                index = this.lastLength;

                while (index < this.infoSeqHolder.value.length) {
                    this.sampleList.add(new SampleImpl<TYPE>(
                            this.environment,
                            (TYPE)Array.get(dataValue, index),
                            this.infoSeqHolder.value[index]));
                    index++;
                }
            } catch (IllegalArgumentException e) {
                throw new DDSExceptionImpl(this.environment, "Internal error ("
                        + e.getMessage() + ").");
            } catch (IllegalAccessException e) {
                throw new DDSExceptionImpl(this.environment, "Internal error ("
                        + e.getMessage() + ").");
            }
        } else if (this.infoSeqHolder.value.length < this.lastLength) {
            index = this.infoSeqHolder.value.length;
            int curLength = this.lastLength;

            while (index < curLength) {
                this.sampleList.remove(index);
                curLength--;
            }
        }
        this.lastLength = this.sampleList.size();
        */
    }

    @Override
    public Object getDataSeqHolder() {
        return this.dataSeqHolder;
    }

    @Override
    public SampleInfoSeqHolder getInfoSeqHolder() {
        return this.infoSeqHolder;
    }

    @Override
    public List<Sample<TYPE>> getSampleList() {
        return this.sampleList;
    }

}
