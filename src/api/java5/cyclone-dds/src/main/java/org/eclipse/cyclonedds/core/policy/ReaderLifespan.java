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

import java.util.concurrent.TimeUnit;

import org.omg.dds.core.Duration;
import org.omg.dds.core.policy.QosPolicy;

/**
 * This {@link org.omg.dds.core.policy.QosPolicy} is similar to the
 * {@link org.omg.dds.core.policy.Lifespan} (applicable to
 * {@link org.omg.dds.topic.Topic} and {@link org.omg.dds.pub.DataWriter}), but
 * limited to the DataReader on which the policy is applied. The data is
 * automatically removed from the DataReader if it has not been taken yet after
 * the lifespan duration expires. The duration of the ReaderLifespan is added to
 * the insertion time of the data in the DataReader to determine the expiry
 * time.
 * <p>
 * When both the ReaderLifespan and a DataWriter Lifespan are applied to the
 * same data, only the earliest expiry time is taken into account. By default,
 * the ReaderLifespan is not used. The duration is set to
 * {@link org.omg.dds.core.Duration#infiniteDuration(org.omg.dds.core.ServiceEnvironment)}
 * <p>
 * This policy is applicable to a DataReader only, and is mutable even when the
 * DataReader is already enabled. If modified, the new setting will only be
 * applied to samples that are received after the modification took place.
 *
 * @see org.omg.dds.core.policy.Lifespan
 */
public interface ReaderLifespan extends QosPolicy.ForDataReader {
    /**
     * @return The duration of this ReaderLifespan.
     */
    public Duration getDuration();

    /**
     * Copy this policy and override the value of the property.
     * @param duration          The duration after which data loses validity and is removed
     * @return a new ReaderLifespan policy
     */
    public ReaderLifespan withDuration(Duration duration);

    /**
     * Copy this policy and override the value of the property.
     * @param duration          The duration after which data loses validity and is removed
     * @param unit              The TimeUnit which the period describes (i.e. TimeUnit.SECONDS or TimeUnit.MILLISECONDS)
     * @return a new ReaderLifespan policy
     */
    public ReaderLifespan withDuration(long duration, TimeUnit unit);
}
