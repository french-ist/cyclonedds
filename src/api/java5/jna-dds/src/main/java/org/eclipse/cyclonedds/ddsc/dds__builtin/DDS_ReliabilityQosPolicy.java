package org.eclipse.cyclonedds.ddsc.dds__builtin;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : dds_builtinTopics.h:148</i><br>
 */
public class DDS_ReliabilityQosPolicy extends Structure {
	/**
	 * @see DDS_ReliabilityQosPolicyKind<br>
	 * C type : DDS_ReliabilityQosPolicyKind
	 */
	public int kind;
	public int getKind() {
		return kind;
	}
	public void setKind(int kind) {
		this.kind = kind;
	}
	/** C type : DDS_Duration_t */
	public DDS_Duration_t max_blocking_time;
	public DDS_Duration_t getMax_blocking_time() {
		return max_blocking_time;
	}
	public void setMax_blocking_time(DDS_Duration_t max_blocking_time) {
		this.max_blocking_time = max_blocking_time;
	}
	public byte synchronous;
	public byte getSynchronous() {
		return synchronous;
	}
	public void setSynchronous(byte synchronous) {
		this.synchronous = synchronous;
	}
	public DDS_ReliabilityQosPolicy() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("kind", "max_blocking_time", "synchronous");
	}
	/**
	 * @param kind @see DDS_ReliabilityQosPolicyKind<br>
	 * C type : DDS_ReliabilityQosPolicyKind<br>
	 * @param max_blocking_time C type : DDS_Duration_t
	 */
	public DDS_ReliabilityQosPolicy(int kind, DDS_Duration_t max_blocking_time, byte synchronous) {
		super();
		this.kind = kind;
		this.max_blocking_time = max_blocking_time;
		this.synchronous = synchronous;
	}
	public DDS_ReliabilityQosPolicy(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends DDS_ReliabilityQosPolicy implements Structure.ByReference {
		
	};
	public static class ByValue extends DDS_ReliabilityQosPolicy implements Structure.ByValue {
		
	};
}
