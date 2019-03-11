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
