/**
  Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 
  This program and the accompanying materials are made available under the
  terms of the Eclipse Public License v. 2.0 which is available at
  http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
  v. 1.0 which is available at
  http://www.eclipse.org/org/documents/edl-v10.php.
 
  SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.cyclonedds.pub;

import org.omg.dds.pub.DataWriterListener;
import org.omg.dds.pub.DataWriterQos;
import org.omg.dds.pub.Publisher;
import org.eclipse.cyclonedds.core.DomainEntityImpl;
import org.eclipse.cyclonedds.core.CycloneServiceEnvironment;

public abstract class AbstractDataWriter<TYPE>
        extends
        DomainEntityImpl<DataWriterQos, DataWriterListener<TYPE>, DataWriterListenerImpl<TYPE>>
        implements org.eclipse.cyclonedds.pub.DataWriter<TYPE> {

    private PublisherImpl parent;

	public AbstractDataWriter(CycloneServiceEnvironment environment,
            PublisherImpl parent) {
        super(environment);
        this.parent = parent;
    }

    @Override
    protected void destroy() {
        // TODO FRCYC this.parent.destroyDataWriter(this);
    }

    @Override
    public Publisher getParent() {
        return parent;
    }
}
