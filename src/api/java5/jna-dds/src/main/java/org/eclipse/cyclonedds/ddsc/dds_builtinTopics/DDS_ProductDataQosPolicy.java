package org.eclipse.cyclonedds.ddsc.dds_builtinTopics;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : _dds_builtinTopics.h:179</i><br>
 */
public class DDS_ProductDataQosPolicy extends Structure {
	/** C type : char* */
	public Pointer value;
	public Pointer getValue() {
		return value;
	}
	public void setValue(Pointer value) {
		this.value = value;
	}
	public DDS_ProductDataQosPolicy() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("value");
	}
	/** @param value C type : char* */
	public DDS_ProductDataQosPolicy(Pointer value) {
		super();
		this.value = value;
	}
	public static class ByReference extends DDS_ProductDataQosPolicy implements Structure.ByReference {
		
	};
	public static class ByValue extends DDS_ProductDataQosPolicy implements Structure.ByValue {
		
	};
}
