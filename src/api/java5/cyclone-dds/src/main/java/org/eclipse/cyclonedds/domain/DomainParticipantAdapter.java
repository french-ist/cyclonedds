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
package org.eclipse.cyclonedds.domain;

import org.eclipse.cyclonedds.core.event.AllDataDisposedEvent;

/**
 * Extension of {@link org.omg.dds.domain.DomainParticipantAdapter} to provide
 * empty implementation for onAllDataDisposed as well.
 */
public class DomainParticipantAdapter extends
        org.omg.dds.domain.DomainParticipantAdapter implements
        DomainParticipantListener {

    /**
     * Called whenever {@link org.eclipse.cyclonedds.topic.Topic#disposeAllData()}
     * has been performed.
     *
     * @see org.eclipse.cyclonedds.core.event.AllDataDisposedEvent
     * @see org.eclipse.cyclonedds.domain.DomainParticipantListener
     * @see org.eclipse.cyclonedds.topic.Topic#disposeAllData()
     */
    @Override
    public void onAllDataDisposed(AllDataDisposedEvent<?> status) {
    }

}