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
package org.eclipse.cyclonedds.dcps.keys;

import java.lang.reflect.Method;

import org.eclipse.cyclonedds.core.InstanceHandleImpl;
import org.eclipse.cyclonedds.ddsc.dds__key.DdscLibrary;
import org.eclipse.cyclonedds.ddsc.dds__key.DdscLibrary.dds_key_hash;
import org.eclipse.cyclonedds.ddsc.dds_public_impl.dds_topic_descriptor;
import org.eclipse.cyclonedds.ddsc.dds_public_impl.dds_topic_descriptor.ByReference;
import org.eclipse.cyclonedds.topic.UserClassHelper;
import org.omg.dds.core.InstanceHandle;

import com.sun.jna.Structure;

public class JnaKeyHashEncoder<TYPE> extends KeyHashEncoder <TYPE> {

	private Class<TYPE> clazz;
	dds_topic_descriptor.ByReference ddsTopicDescriptor;
	
	public JnaKeyHashEncoder(Class<TYPE> clazz) {
		this.clazz = clazz;		
		String helperClass = clazz.getCanonicalName() + "_Helper";
		System.out.println("\n\nhelperClass: " + helperClass);
		try {
	            Class<?> c = Class.forName(helperClass);        
	            UserClassHelper instance = (UserClassHelper) c.newInstance();
                Class<?> parameterTypes[] = {String.class};
                Method method = instance.getClass().getDeclaredMethod("getDdsTopicDescriptor", parameterTypes);
                method.setAccessible(true);
                
                String topicName = (getClass().getPackage() + "." +clazz).replace("package ", "").replace(".", "_");
                System.out.println("topicName: " + topicName+"\n");
                
                ddsTopicDescriptor =  (ByReference) method.invoke(instance, topicName);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	@Override
	public InstanceHandle encode(TYPE instanceData) {
		Structure sample = (Structure)instanceData;
		dds_topic_descriptor.ByReference descriptor = ddsTopicDescriptor;
		dds_key_hash kh = new dds_key_hash();
		DdscLibrary.dds_key_gen(descriptor, kh, sample.getPointer());
		return new InstanceHandleImpl(kh.getPointer().getByteArray(0, 1));
	}
}
