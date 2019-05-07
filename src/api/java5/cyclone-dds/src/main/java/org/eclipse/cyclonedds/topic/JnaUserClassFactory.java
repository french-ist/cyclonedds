/**
  Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 
  This program and the accompanying materials are made available under the
  terms of the Eclipse Public License v. 2.0 which is available at
  http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
  v. 1.0 which is available at
  http://www.eclipse.org/org/documents/edl-v10.php.
 
  SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.cyclonedds.topic;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.cyclonedds.core.ServiceEnvironmentImpl;


public class JnaUserClassFactory {

	public static UserClassHelper getJnaUserClassHelperInstance(ServiceEnvironmentImpl environment,
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
	public static <TYPE> TYPE getJnaUserClassInstance(ServiceEnvironmentImpl environment, TopicImpl<?> topicImpl) {
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
