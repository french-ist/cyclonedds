package org.eclipse.cyclonedds.topic;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.cyclonedds.core.CycloneServiceEnvironment;


public class JnaUserClassFactory {

	public static UserClassHelper getJnaUserClassHelperInstance(CycloneServiceEnvironment environment,
			TopicImpl<?> topicImpl) {
        try {
			Class<?> clazz = (Class<?>) Class.forName(topicImpl.getTypeSupport().getTypeName()+"_Helper");
			Constructor<?> ctr = clazz.getConstructor(new Class[]{});
			return (UserClassHelper) ctr.newInstance(new Object[] {});
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
	
	@SuppressWarnings("unchecked")
	public static <TYPE> TYPE getJnaUserClassInstance(CycloneServiceEnvironment environment, TopicImpl<?> topicImpl) {
		try {			
			Class<TYPE> clazz = (Class<TYPE>) Class.forName(topicImpl.getTypeSupport().getTypeName());
			Constructor<?> ctr = clazz.getConstructor(new Class[]{});
			return (TYPE) ctr.newInstance(new Object[] {});
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
