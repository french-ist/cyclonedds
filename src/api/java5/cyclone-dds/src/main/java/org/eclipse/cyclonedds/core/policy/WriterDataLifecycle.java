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

import java.util.concurrent.TimeUnit;

import org.omg.dds.core.Duration;

/**
 * This QosPolicy provides OpenSplice-specific extensions to the
 * {@link org.omg.dds.core.policy.WriterDataLifecycle}. Next to all attributes
 * in the original QosPolicy, it also provides:
 * <ul>
 * <li><b>autoPurgeSuspendedSamplesDelay</b> - specifies the duration after
 * which the {@link org.omg.dds.pub.DataWriter} will automatically remove a
 * sample from its history during periods in which its Publisher is suspended.
 * This duration is calculated based on the source timestamp of the written
 * sample. By default the duration value is set to infinite and therefore no
 * automatic purging of samples occurs. See
 * {@link org.omg.dds.pub.Publisher#suspendPublications()} for more information
 * on suspended publications
 * <li><b>autoUnregisterInstanceDelay</b> - specifies the Duration after which
 * the DataWriter will automatically unregister an instance after the
 * application wrote a sample for it and no further action is performed on the
 * same instance by this DataWriter afterwards. This means that when the
 * application writes a new sample for this instance, the duration is
 * recalculated from that action onwards. By default the duration value is
 * infinite and therefore no automatic unregistration occurs.
 * </ul>
 *
 */
public interface WriterDataLifecycle extends
        org.omg.dds.core.policy.WriterDataLifecycle {
    /**
     * @return the autoPurgeSuspendedSamplesDelay Duration
     */
    public Duration getAutoPurgeSuspendedSamplesDelay();

    /**
     * @return the autoUnregisterInstanceDelay Duration
     */
    public Duration getAutoUnregisterInstanceDelay();

    /**
     * Copy this policy and override the value of the property.
     * @param duration     Specifies the duration after which the DataWriter will automatically remove a sample from its
     *                     history during periods in which its Publisher is suspended. This duration is calculated based
     *                     on the source timestamp of the written sample. By default the duration value is set to
     *                     DURATION_INFINITE and therefore no automatic purging of samples occurs.
     * @return a new WriterDataLifecycle policy
     */
    public WriterDataLifecycle withAutoPurgeSuspendedSamplesDelay(
            Duration duration);

    /**
     * Copy this policy and override the value of the property.
     * @param duration     Specifies the duration after which the DataWriter will automatically remove a sample from its
     *                     history during periods in which its Publisher is suspended. This duration is calculated based
     *                     on the source timestamp of the written sample. By default the duration value is set to
     *                     DURATION_INFINITE and therefore no automatic purging of samples occurs.
     *
     * @param unit         The TimeUnit which the period describes (i.e. TimeUnit.SECONDS or TimeUnit.MILLISECONDS)
     * @return a new policy
     */
    public WriterDataLifecycle withAutoPurgeSuspendedSamplesDelay(
            long duration, TimeUnit unit);

    /**
     * Copy this policy and override the value of the property.
     * @param duration     Specifies the duration after which the DataWriter will automatically unregister an instance
     *                     after the application wrote a sample for it and no further action is performed on the same
     *                     instance by this DataWriter afterwards. This means that when the application writes a new
     *                     sample for this instance, the duration is recalculated from that action onwards.
     *                     By default the duration value is DURATION_INFINITE and therefore no automatic unregistration occurs.
     * @return a new WriterDataLifecycle policy
     */
    public WriterDataLifecycle withAutoUnregisterInstanceDelay(Duration duration);

    /**
     * Copy this policy and override the value of the property.
     * @param duration     Specifies the duration after which the DataWriter will automatically unregister an instance
     *                     after the application wrote a sample for it and no further action is performed on the same
     *                     instance by this DataWriter afterwards. This means that when the application writes a new
     *                     sample for this instance, the duration is recalculated from that action onwards.
     *                     By default the duration value is DURATION_INFINITE and therefore no automatic unregistration occurs.
     * @param unit         The TimeUnit which the period describes (i.e. TimeUnit.SECONDS or TimeUnit.MILLISECONDS)
     * @return a new WriterDataLifecycle policy
     */
    public WriterDataLifecycle withAutoUnregisterInstanceDelay(long duration,
            TimeUnit unit);
}
