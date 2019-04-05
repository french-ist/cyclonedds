/**
  Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 
  This program and the accompanying materials are made available under the
  terms of the Eclipse Public License v. 2.0 which is available at
  http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
  v. 1.0 which is available at
  http://www.eclipse.org/org/documents/edl-v10.php.
 
  SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
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
