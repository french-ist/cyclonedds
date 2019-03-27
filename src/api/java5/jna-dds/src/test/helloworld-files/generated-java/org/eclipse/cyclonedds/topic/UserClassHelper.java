package org.eclipse.cyclonedds.topic;

import org.eclipse.cyclonedds.ddsc.dds_public_impl.dds_topic_descriptor;

public interface UserClassHelper {
	public dds_topic_descriptor.ByReference getDdsTopicDescriptor(String topicName);
}
