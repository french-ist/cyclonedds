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

import org.eclipse.cyclonedds.core.policy.ReaderLifespan;
import org.eclipse.cyclonedds.core.policy.Share;
import org.eclipse.cyclonedds.core.policy.SubscriptionKeys;

/**
 * OpenSplice-specific extension of {@link org.omg.dds.sub.DataReaderQos} with
 * support for {@link org.eclipse.cyclonedds.core.policy.ReaderLifespan},
 * {@link org.eclipse.cyclonedds.core.policy.Share} and
 * {@link org.eclipse.cyclonedds.core.policy.SubscriptionKeys}
 *
 * @see org.eclipse.cyclonedds.core.policy.ReaderLifespan
 * @see org.eclipse.cyclonedds.core.policy.Share
 * @see org.eclipse.cyclonedds.core.policy.SubscriptionKeys
 */
public interface DataReaderQos extends org.omg.dds.sub.DataReaderQos {
    /**
     * @return the ReaderLifeSpan QosPolicy.
     * 
     * @see org.eclipse.cyclonedds.core.policy.ReaderLifespan
     */
    public ReaderLifespan getReaderLifespan();

    /**
     * @return the Share QosPolicy.
     * 
     * @see org.eclipse.cyclonedds.core.policy.Share
     */
    public Share getShare();

    /**
     * @return the SubscriptionKeys QosPolicy.
     * 
     * @see org.eclipse.cyclonedds.core.policy.SubscriptionKeys
     */
    public SubscriptionKeys getSubscriptionKeys();
}
