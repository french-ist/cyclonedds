package org.eclipse.cyclonedds.ddsc.dds_builtinTopics;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : _dds_builtinTopics.h:136</i><br>
 */
public class DDS_TimeBasedFilterQosPolicy extends Structure {
	/** C type : DDS_Duration_t */
	public DDS_Duration_t minimum_separation;
	public DDS_Duration_t getMinimum_separation() {
		return minimum_separation;
	}
	public void setMinimum_separation(DDS_Duration_t minimum_separation) {
		this.minimum_separation = minimum_separation;
	}
	public DDS_TimeBasedFilterQosPolicy() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("minimum_separation");
	}
	/** @param minimum_separation C type : DDS_Duration_t */
	public DDS_TimeBasedFilterQosPolicy(DDS_Duration_t minimum_separation) {
		super();
		this.minimum_separation = minimum_separation;
	}
	public DDS_TimeBasedFilterQosPolicy(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends DDS_TimeBasedFilterQosPolicy implements Structure.ByReference {
		
	};
	public static class ByValue extends DDS_TimeBasedFilterQosPolicy implements Structure.ByValue {
		
	};
}
