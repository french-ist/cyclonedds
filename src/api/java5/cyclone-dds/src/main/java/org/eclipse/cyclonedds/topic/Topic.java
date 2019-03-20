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
package org.eclipse.cyclonedds.topic;

import org.eclipse.cyclonedds.core.status.AllDataDisposedStatus;

/**
 * Topic provides OpenSplice-specific extensions to
 * {@link org.omg.dds.topic.Topic}.
 *
 * @param <TYPE>
 *            The concrete type of the data that will be published and/ or
 *            subscribed by the readers and writers that use this topic.
 */
public interface Topic<TYPE> extends org.omg.dds.topic.Topic<TYPE> {
    /**
     * This operation allows the application to dispose of all of the instances
     * for a particular {@link org.eclipse.cyclonedds.topic.Topic} without the
     * network overhead of using a separate
     * {@link org.omg.dds.pub.DataWriter#dispose(org.omg.dds.core.InstanceHandle)}
     * call for each instance.
     *  <p>
     * Its effect is equivalent to invoking a separate dispose operation for
     * each individual instance on the DataWriter that owns it.
     * <p>
     * This operation only sets the instance state of the instances concerned to
     * {@link org.omg.dds.sub.InstanceState#NOT_ALIVE_DISPOSED}. It does not
     * unregister the instances, and so does not automatically clean up the
     * memory that is claimed by the instances in both the DataReaders and
     * DataWriters.
     *  <p>
     * The blocking (or nonblocking) behavior of this call is undefined.
     * <p>
     * If there are subsequent calls to this function before the action has been
     * completed (completion of the disposes on all nodes, not simply return
     * from the method), then the behavior is undefined.
     * <p>
     * Other notes:<br>
     * The effect of this call on disposedGenerationCount, generationRank and
     * absoluteGenerationRank is undefined.<br>
     * This call is an asynchronous C&amp;M operation that is not part of a coherent
     * update meaning that it operates on the DataReaders history cache and not on the
     * incomplete transactions. The DisposeAllData is effectuated as soon as a
     * transaction becomes complete and is inserted into the DataReaders history cache,
     * at that point messages will be inserted according to the destination_order qos policy.
     * For BY_SOURCE_TIMESTAMP all messages older than the dispose_all_data will be disposed
     * and all newer will be alive, for BY_RECEPTION_TIMESTAMP all messages will be alive if
     * the transaction is completed after receiving the dispose_all_data command.
     */
    public void disposeAllData();

    /**
     * This operation obtains the
     * {@link org.eclipse.cyclonedds.core.status.AllDataDisposedStatus} of the
     * {@link org.eclipse.cyclonedds.topic.Topic}. The AllDataDisposedStatus can
     * also be monitored using a {@link org.eclipse.cyclonedds.topic.TopicListener}
     * or by using the associated StatusCondition.
     *
     * @return The AllDataDisposedStatus of the Topic.
     */
    public AllDataDisposedStatus getAllDataDisposedTopicStatus();
}
