package org.eclipse.cyclonedds.topic;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.cyclonedds.core.CycloneServiceEnvironment;

public class DdsTopicDescriptorFactory {

	public static DdsTopicDescriptor getDdsTopicDescriptor(CycloneServiceEnvironment environment,
			TopicImpl<?> topicImpl) {
		System.err.println("#DdsTopicDescriptorFactory # class to load: " + topicImpl.getTypeName() + "_Helper");
        try {
			Class<?> clazz = (Class<?>) Class.forName(topicImpl.getTypeSupport().getTypeName()+"_Helper");
			Constructor<?> ctr = clazz.getConstructor(new Class[]{});
			return (DdsTopicDescriptor) ctr.newInstance(new Object[] {});
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
}
