package org.eclipse.cyclonedds.ddsc.dds_builtinTopics;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : _dds_builtinTopics.h:199</i><br>
 */
public class DDS_InvalidSampleVisibilityQosPolicy extends Structure {
	/**
	 * @see DDS_InvalidSampleVisibilityQosPolicyKind<br>
	 * C type : DDS_InvalidSampleVisibilityQosPolicyKind
	 */
	public int kind;
	public int getKind() {
		return kind;
	}
	public void setKind(int kind) {
		this.kind = kind;
	}
	public DDS_InvalidSampleVisibilityQosPolicy() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("kind");
	}
	/**
	 * @param kind @see DDS_InvalidSampleVisibilityQosPolicyKind<br>
	 * C type : DDS_InvalidSampleVisibilityQosPolicyKind
	 */
	public DDS_InvalidSampleVisibilityQosPolicy(int kind) {
		super();
		this.kind = kind;
	}
	public DDS_InvalidSampleVisibilityQosPolicy(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends DDS_InvalidSampleVisibilityQosPolicy implements Structure.ByReference {
		
	};
	public static class ByValue extends DDS_InvalidSampleVisibilityQosPolicy implements Structure.ByValue {
		
	};
}
