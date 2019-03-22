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

import org.eclipse.cyclonedds.core.CycloneServiceEnvironment;
import org.eclipse.cyclonedds.core.UnsupportedOperationExceptionImpl;
import org.eclipse.cyclonedds.ddsc.dds.DdscLibrary.dds_topic_descriptor_t;

public class TypeDescriptorFactory {

	public static <TYPE> dds_topic_descriptor_t createDescriptor(CycloneServiceEnvironment environment,
			TopicImpl<TYPE> topicImpl) {
		throw new UnsupportedOperationExceptionImpl(environment, "NYI");
	}
}