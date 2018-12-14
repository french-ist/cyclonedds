package org.eclipse.cyclonedds.ddsc.dds_dcps_builtintopics;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : dds_builtinTopics.h:214</i><br>
 */
public class DDS_UserKeyQosPolicy extends Structure {
	public byte enable;
	public byte getEnable() {
		return enable;
	}
	public void setEnable(byte enable) {
		this.enable = enable;
	}
	/** C type : char* */
	public Pointer expression;
	public Pointer getExpression() {
		return expression;
	}
	public void setExpression(Pointer expression) {
		this.expression = expression;
	}
	public DDS_UserKeyQosPolicy() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("enable", "expression");
	}
	/** @param expression C type : char* */
	public DDS_UserKeyQosPolicy(byte enable, Pointer expression) {
		super();
		this.enable = enable;
		this.expression = expression;
	}
	public DDS_UserKeyQosPolicy(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends DDS_UserKeyQosPolicy implements Structure.ByReference {
		
	};
	public static class ByValue extends DDS_UserKeyQosPolicy implements Structure.ByValue {
		
	};
}
