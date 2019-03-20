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
 * This class provides OpenSplice-specific extensions to the
 * {@link org.omg.dds.core.policy.ReaderDataLifecycle} policy.
 *
 */
public interface ReaderDataLifecycle extends
        org.omg.dds.core.policy.ReaderDataLifecycle {
    /**
     * Instance state changes are communicated to a
     * {@link org.omg.dds.sub.DataReader} by means of the sample info
     * accompanying a {@link org.omg.dds.sub.Sample}. If no samples are
     * available in the DataReader, a so-called 'invalid sample' can be injected
     * with the sole purpose of notifying applications of the instance state.
     * <p>
     * ReaderDataLifecycle.Kind drives the behavior of the middleware concerning
     * these invalid samples.
     *
     * <ul>
     * <li><b>NONE</b> - applications will be notified of InstanceState changes
     * only if there is a sample available in the DataReader. The SampleInfo
     * belonging to this sample will contain the updated instance state.
     * <li><b>MINIMUM</b> - the middleware will try to update the
     * {@link org.omg.dds.sub.InstanceState} on available samples in the
     * DataReader. If no sample is available, an invalid sample will be
     * injected. These samples contain only the key values of the instance. The
     * SampleInfo for invalid samples will only have a key value (available
     * through {@link org.eclipse.cyclonedds.sub.Sample#getKeyValue()}), and contain
     * the updated {@link org.omg.dds.sub.InstanceState}. This is the default
     * value.
     * <li><b>ALL</b> - every change in the
     * {@link org.omg.dds.sub.InstanceState}will be communicated by a separate
     * invalid sample. Invalid samples are always visible, every time the of an
     * instance changes. This last option has not been implemented yet.
     * </ul>
     */
    public enum Kind {
        NONE, MINIMUM, ALL
    }

    /**
     * Whether or not instances that have been disposed by means of the
     * {@link org.eclipse.cyclonedds.topic.Topic#disposeAllData()} method will
     * automatically be purged by the middleware or not.
     *
     * @return true if instances are automatically purged, false otherwise.
     */
    public boolean getAutoPurgeDisposeAll();

    /**
     * Provides access to the ReaderDataLifecycle.Kind that drives invalid
     * sample visibility.
     *
     * @return The invalid sample visibility.
     */
    public Kind getInvalidSampleInvisibility();

    /**
     * Creates a copy of this ReaderDataLifecycle that has its
     * autoPurgeDisposeAll set to true.
     *
     * @return a copy of this policy with autoPurgeDisposeAll set to true.
     */
    public ReaderDataLifecycle withAutoPurgeDisposeAll();

    /**
     * Creates a copy of this ReaderDataLifecycle that has its invalid sample
     * visibility set to the supplied value.
     *
     * @param kind
     *            the invalid sample visibility kind to set.
     * @return a copy of this policy with supplied invalid sample visibility set
     *         to true.
     */
    public ReaderDataLifecycle withInvalidSampleInvisibility(Kind kind);
}
