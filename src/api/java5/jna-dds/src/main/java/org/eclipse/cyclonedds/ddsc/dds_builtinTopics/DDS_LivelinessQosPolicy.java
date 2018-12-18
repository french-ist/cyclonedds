package org.eclipse.cyclonedds.ddsc.dds_builtinTopics;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 */
public class DDS_LivelinessQosPolicy extends Structure {
	/**
	 * @see DDS_LivelinessQosPolicyKind<br>
	 * C type : DDS_LivelinessQosPolicyKind
	 */
	public int kind;
	public int getKind() {
		return kind;
	}
	public void setKind(int kind) {
		this.kind = kind;
	}
	/** C type : DDS_Duration_t */
	public DDS_Duration_t lease_duration;
	public DDS_Duration_t getLease_duration() {
		return lease_duration;
	}
	public void setLease_duration(DDS_Duration_t lease_duration) {
		this.lease_duration = lease_duration;
	}
	public DDS_LivelinessQosPolicy() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("kind", "lease_duration");
	}
	/**
	 * @param kind @see DDS_LivelinessQosPolicyKind<br>
	 * C type : DDS_LivelinessQosPolicyKind<br>
	 * @param lease_duration C type : DDS_Duration_t
	 */
	public DDS_LivelinessQosPolicy(int kind, DDS_Duration_t lease_duration) {
		super();
		this.kind = kind;
		this.lease_duration = lease_duration;
	}
	public DDS_LivelinessQosPolicy(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends DDS_LivelinessQosPolicy implements Structure.ByReference {
		
	};
	public static class ByValue extends DDS_LivelinessQosPolicy implements Structure.ByValue {
		
	};
}
