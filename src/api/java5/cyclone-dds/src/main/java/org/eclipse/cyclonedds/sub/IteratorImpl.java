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

import java.lang.reflect.Field;

import org.eclipse.cyclonedds.core.DDSExceptionImpl;
import org.eclipse.cyclonedds.core.IllegalArgumentExceptionImpl;
import org.eclipse.cyclonedds.core.CycloneServiceEnvironment;

//TODO FRCYC //TODO FRCYC import SampleInfoSeqHolder;

public class IteratorImpl<TYPE> extends AbstractIterator<TYPE> {
    private TYPE[] data;

    public IteratorImpl(CycloneServiceEnvironment environment,
            DataReaderImpl<TYPE> reader, Object sampleSeqHolder,
            Field dataSeqHolderValue, SampleInfoSeqHolder infoSeqHolder) {
        super(environment, reader, sampleSeqHolder, dataSeqHolderValue,
                infoSeqHolder);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected SampleImpl<TYPE>[] setupSampleList() {
        try {
            this.data = (TYPE[]) dataSeqHolderValue.get(sampleSeqHolder);

        } catch (SecurityException e) {
            throw new IllegalArgumentExceptionImpl(environment,
                    "Not allowed to access field "
                            + dataSeqHolderValue.getName() + " in "
                            + dataSeqHolderValue.getClass().getName() + "("
                            + e.getMessage() + ").");
        } catch (IllegalArgumentException e) {
            throw new DDSExceptionImpl(environment, "Cannot find "
                    + dataSeqHolderValue.getName() + " in "
                    + dataSeqHolderValue.getClass().getName() + "("
                    + e.getMessage() + ").");
        } catch (IllegalAccessException e) {
            throw new DDSExceptionImpl(environment, "No access to field "
                    + dataSeqHolderValue.getName() + " in "
                    + dataSeqHolderValue.getClass().getName() + "("
                    + e.getMessage() + ").");
        }
        return new SampleImpl[this.data.length];
    }

    @Override
    protected TYPE getData(int index) {
        return this.data[index];
    }
}
