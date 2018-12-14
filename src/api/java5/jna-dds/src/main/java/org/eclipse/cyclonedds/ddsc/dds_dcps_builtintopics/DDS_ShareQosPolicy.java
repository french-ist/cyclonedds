package org.eclipse.cyclonedds.ddsc.dds_dcps_builtintopics;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : dds_builtinTopics.h:186</i><br>
 */
public class DDS_ShareQosPolicy extends Structure {
	/** C type : char* */
	public Pointer name;
	public Pointer getName() {
		return name;
	}
	public void setName(Pointer name) {
		this.name = name;
	}
	public byte enable;
	public byte getEnable() {
		return enable;
	}
	public void setEnable(byte enable) {
		this.enable = enable;
	}
	public DDS_ShareQosPolicy() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("name", "enable");
	}
	/** @param name C type : char* */
	public DDS_ShareQosPolicy(Pointer name, byte enable) {
		super();
		this.name = name;
		this.enable = enable;
	}
	public DDS_ShareQosPolicy(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends DDS_ShareQosPolicy implements Structure.ByReference {
		
	};
	public static class ByValue extends DDS_ShareQosPolicy implements Structure.ByValue {
		
	};
}
