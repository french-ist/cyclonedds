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
package org.eclipse.cyclonedds.core.policy;

/**
 * OpenSplice-specific extension to {@link org.omg.dds.core.policy.Reliability}
 * That specifies whether a DataWriter should wait for acknowledgements by all connected DataReaders that also have set a synchronous ReliabilityQosPolicy.
 * It is advised only to use this policy in combination with reliability, if used in combination with best effort data may not arrive at the DataReader
 * resulting in a timeout at the DataWriter indicating that the data has not been received.
 * Acknoledgments are always sent reliable so when the DataWriter encounters a timeout it is guaranteed that the DataReader hasn't received the data.

 */
public interface Reliability extends org.omg.dds.core.policy.Reliability{
    /**
     * @return true if synchronous property is set, false otherwise.
     */
    public boolean isSynchronous();

    /**
     * Copy this policy and override the value of the property.
     * @param synchronous       Specifies whether a DataWriter should wait for acknowledgements by all connected DataReaders
     *                          that also have set a synchronous ReliabilityQosPolicy.
     * @return a new Reliability policy
     */
    public Reliability withSynchronous(boolean synchronous);

}
