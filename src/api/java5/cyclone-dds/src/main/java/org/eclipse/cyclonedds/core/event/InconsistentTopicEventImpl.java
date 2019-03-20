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
package org.eclipse.cyclonedds.core.event;

import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.event.InconsistentTopicEvent;
import org.omg.dds.core.status.InconsistentTopicStatus;
import org.omg.dds.topic.Topic;
import org.eclipse.cyclonedds.core.CycloneServiceEnvironment;

public class InconsistentTopicEventImpl<TYPE> extends
        InconsistentTopicEvent<TYPE> {
    private static final long serialVersionUID = 8593632953038364165L;
    private final transient CycloneServiceEnvironment environment;
    private final InconsistentTopicStatus status;

    public InconsistentTopicEventImpl(CycloneServiceEnvironment environment,
            Topic<TYPE> source, InconsistentTopicStatus status) {
        super(source);
        this.environment = environment;
        this.status = status;
        this.source = source;
    }

    @Override
    public ServiceEnvironment getEnvironment() {
        return this.environment;
    }

    @Override
    public InconsistentTopicStatus getStatus() {
        return this.status;
    }

    @Override
    public InconsistentTopicEvent<TYPE> clone() {
        return new InconsistentTopicEventImpl<TYPE>(this.environment,
                this.getSource(), this.status);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Topic<TYPE> getSource() {
        return (Topic<TYPE>)this.source;
    }

}
