package org.eclipse.cyclonedds.ddsc.dds_dcps_builtintopics;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : dds_builtinTopics.h:203</i><br>
 */
public class DDS_SubscriptionKeyQosPolicy extends Structure {
	public byte use_key_list;
	public byte getUse_key_list() {
		return use_key_list;
	}
	public void setUse_key_list(byte use_key_list) {
		this.use_key_list = use_key_list;
	}
	/** C type : DDS_StringSeq */
	public DDS_StringSeq key_list;
	public DDS_StringSeq getKey_list() {
		return key_list;
	}
	public void setKey_list(DDS_StringSeq key_list) {
		this.key_list = key_list;
	}
	public DDS_SubscriptionKeyQosPolicy() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("use_key_list", "key_list");
	}
	/** @param key_list C type : DDS_StringSeq */
	public DDS_SubscriptionKeyQosPolicy(byte use_key_list, DDS_StringSeq key_list) {
		super();
		this.use_key_list = use_key_list;
		this.key_list = key_list;
	}
	public DDS_SubscriptionKeyQosPolicy(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends DDS_SubscriptionKeyQosPolicy implements Structure.ByReference {
		
	};
	public static class ByValue extends DDS_SubscriptionKeyQosPolicy implements Structure.ByValue {
		
	};
}
