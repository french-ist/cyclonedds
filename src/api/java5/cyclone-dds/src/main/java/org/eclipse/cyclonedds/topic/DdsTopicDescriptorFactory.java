package org.eclipse.cyclonedds.topic;

import java.lang.reflect.Constructor;

import org.eclipse.cyclonedds.core.CycloneServiceEnvironment;


public class DdsTopicDescriptorFactory {

	public static DdsTopicDescriptor getDdsTopicDescriptor(CycloneServiceEnvironment environment,
			TopicImpl<?> topicImpl) {

		System.err.println("#DdsTopicDescriptorFactory # class to load: " + topicImpl.getTypeName() + "_Helper");
		
		try
		{
			Class<? extends DdsTopicDescriptor> helperClass = (Class<? extends DdsTopicDescriptor>) topicImpl.getClass().getClassLoader().loadClass( (topicImpl.getTypeName() + "_Helper"));
			Constructor<? extends DdsTopicDescriptor> cons = helperClass.getConstructor();
			Object[] obj = {};
			return (DdsTopicDescriptor) cons.newInstance(obj);
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

}
