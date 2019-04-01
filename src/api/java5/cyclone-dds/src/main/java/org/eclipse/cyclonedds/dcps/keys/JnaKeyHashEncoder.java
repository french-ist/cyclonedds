package org.eclipse.cyclonedds.dcps.keys;

import org.eclipse.cyclonedds.core.InstanceHandleImpl;
import org.eclipse.cyclonedds.ddsc.dds__key.DdscLibrary;
import org.eclipse.cyclonedds.ddsc.dds__key.DdscLibrary.dds_key_hash;
import org.eclipse.cyclonedds.ddsc.dds_public_impl.dds_topic_descriptor;
import org.omg.dds.core.InstanceHandle;

import com.sun.jna.Structure;

public class JnaKeyHashEncoder<TYPE> extends KeyHashEncoder <TYPE> {

	private Class<TYPE> clazz;
	public JnaKeyHashEncoder(Class<TYPE> clazz) {
		this.clazz = clazz;
	}
	@Override
	public InstanceHandle encode(TYPE instanceData) {
		Structure sample = (Structure)instanceData;
		dds_topic_descriptor.ByReference descriptor = null; //TODO
		dds_key_hash kh = new dds_key_hash();
		DdscLibrary.dds_key_gen(descriptor, kh, sample.getPointer());
		return new InstanceHandleImpl(kh.getPointer().getByteArray(0, 1));
	}
}
