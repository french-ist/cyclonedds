package org.eclipse.cyclonedds.topic;

import org.eclipse.cyclonedds.ddsc.dds_public_impl.dds_topic_descriptor;

public abstract class DdsTopicDescriptor {
	public abstract dds_topic_descriptor.ByReference getDdsTopicDescriptor();
}
