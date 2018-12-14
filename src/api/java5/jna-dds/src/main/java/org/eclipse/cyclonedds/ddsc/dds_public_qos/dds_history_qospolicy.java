package org.eclipse.cyclonedds.ddsc.dds_public_qos;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : _dds_public_qos.h:88</i><br>
 */
public class dds_history_qospolicy extends Structure {
	/** C type : dds_history_kind_t */
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
	public dds_history_qospolicy() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("kind", "depth");
	}
	/** @param kind C type : dds_history_kind_t */
	public dds_history_qospolicy(int kind, int depth) {
		super();
		this.kind = kind;
		this.depth = depth;
	}
	public dds_history_qospolicy(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends dds_history_qospolicy implements Structure.ByReference {
		
	};
	public static class ByValue extends dds_history_qospolicy implements Structure.ByValue {
		
	};
}
