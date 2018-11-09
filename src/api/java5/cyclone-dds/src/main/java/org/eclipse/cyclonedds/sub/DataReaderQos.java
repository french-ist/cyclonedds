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
