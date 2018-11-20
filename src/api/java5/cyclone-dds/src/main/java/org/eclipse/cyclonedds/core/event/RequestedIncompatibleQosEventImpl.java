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
package org.eclipse.cyclonedds.core.event;

import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.event.RequestedIncompatibleQosEvent;
import org.omg.dds.core.status.RequestedIncompatibleQosStatus;
import org.omg.dds.sub.DataReader;
import org.eclipse.cyclonedds.core.OsplServiceEnvironment;

public class RequestedIncompatibleQosEventImpl<TYPE> extends RequestedIncompatibleQosEvent<TYPE> {
    private static final long serialVersionUID = 3194689213798347497L;
    private final transient OsplServiceEnvironment environment;
    private final RequestedIncompatibleQosStatus status;

    public RequestedIncompatibleQosEventImpl(OsplServiceEnvironment environment,
            DataReader<TYPE> source, RequestedIncompatibleQosStatus status) {
        super(source);
        this.environment = environment;
        this.status = status;
    }

    @Override
    public ServiceEnvironment getEnvironment() {
        return this.environment;
    }

    @Override
    public RequestedIncompatibleQosStatus getStatus() {
        return this.status;
    }

    @Override
    public RequestedIncompatibleQosEvent<TYPE> clone() {
        return new RequestedIncompatibleQosEventImpl<TYPE>(this.environment, this.getSource(), this.status);
    }

    @SuppressWarnings("unchecked")
    @Override
    public DataReader<TYPE> getSource() {
        return (DataReader<TYPE>)this.source;
    }
}