package org.eclipse.cyclonedds.ddsc.dds_builtinTopics;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 */
public class DDS_HistoryQosPolicy extends Structure {
	/**
	 * @see DDS_HistoryQosPolicyKind<br>
	 * C type : DDS_HistoryQosPolicyKind
	 */
	public int kind;
	public int getKind() {
		return kind;
	}
	public void setKind(int kind) {
		this.kind = kind;
	}
	public int depth;
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public DDS_HistoryQosPolicy() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("kind", "depth");
	}
	/**
	 * @param kind @see DDS_HistoryQosPolicyKind<br>
	 * C type : DDS_HistoryQosPolicyKind
	 */
	public DDS_HistoryQosPolicy(int kind, int depth) {
		super();
		this.kind = kind;
		this.depth = depth;
	}
	public DDS_HistoryQosPolicy(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends DDS_HistoryQosPolicy implements Structure.ByReference {
		
	};
	public static class ByValue extends DDS_HistoryQosPolicy implements Structure.ByValue {
		
	};
}
