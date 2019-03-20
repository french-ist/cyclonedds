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

import java.util.List;

import org.omg.dds.sub.Sample;

//TODO FRCYC import SampleInfoSeqHolder;

public interface PreAllocator<TYPE> {
    public void setSampleList(List<Sample<TYPE>> preAllocated);

    public void updateReferences();

    public Object getDataSeqHolder();

    public SampleInfoSeqHolder getInfoSeqHolder();

    public List<Sample<TYPE>> getSampleList();
}
