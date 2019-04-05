/**
  Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 
  This program and the accompanying materials are made available under the
  terms of the Eclipse Public License v. 2.0 which is available at
  http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
  v. 1.0 which is available at
  http://www.eclipse.org/org/documents/edl-v10.php.
 
  SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.cyclonedds.core.policy;

/**
 * OpenSplice-specific extension of
 * {@link org.omg.dds.core.policy.PolicyFactory} that supports an extra set of
 * QosPolicy's.
 * 
 * @see org.eclipse.cyclonedds.core.policy.Share
 * @see org.eclipse.cyclonedds.core.policy.ReaderLifespan
 * @see org.eclipse.cyclonedds.core.policy.Scheduling.WatchdogScheduling
 * @see org.eclipse.cyclonedds.core.policy.Scheduling.ListenerScheduling
 * @see org.eclipse.cyclonedds.core.policy.SubscriptionKeys
 * @see org.eclipse.cyclonedds.core.policy.ViewKeys
 */
public abstract class PolicyFactory extends
        org.omg.dds.core.policy.PolicyFactory {
    /**
     * @return the default Share QosPolicy
     */
    public abstract Share Share();

    /**
     * @return the default ReaderLifespan QosPolicy
     */
    public abstract ReaderLifespan ReaderLifespan();

    /**
     * @return the default WatchdogScheduling QosPolicy
     */
    public abstract Scheduling.WatchdogScheduling WatchDogScheduling();

    /**
     * @return the default ListenerScheduling QosPolicy
     */
    public abstract Scheduling.ListenerScheduling ListenerScheduling();

    /**
     * @return the default SubscriptionKeys QosPolicy
     */
    public abstract SubscriptionKeys SubscriptionKeys();

    /**
     * @return the default ViewKeys QosPolicy
     */
    public abstract ViewKeys ViewKeys();
}
