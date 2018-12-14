package org.eclipse.cyclonedds.ddsc.dds_builtinTopics;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : _dds_builtinTopics.h:108</i><br>
 */
public class DDS_PresentationQosPolicy extends Structure {
	/**
	 * @see DDS_PresentationQosPolicyAccessScopeKind<br>
	 * C type : DDS_PresentationQosPolicyAccessScopeKind
	 */
	public int access_scope;
	public int getAccess_scope() {
		return access_scope;
	}
	public void setAccess_scope(int access_scope) {
		this.access_scope = access_scope;
	}
	public byte coherent_access;
	public byte getCoherent_access() {
		return coherent_access;
	}
	public void setCoherent_access(byte coherent_access) {
		this.coherent_access = coherent_access;
	}
	public byte ordered_access;
	public byte getOrdered_access() {
		return ordered_access;
	}
	public void setOrdered_access(byte ordered_access) {
		this.ordered_access = ordered_access;
	}
	public DDS_PresentationQosPolicy() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("access_scope", "coherent_access", "ordered_access");
	}
	/**
	 * @param access_scope @see DDS_PresentationQosPolicyAccessScopeKind<br>
	 * C type : DDS_PresentationQosPolicyAccessScopeKind
	 */
	public DDS_PresentationQosPolicy(int access_scope, byte coherent_access, byte ordered_access) {
		super();
		this.access_scope = access_scope;
		this.coherent_access = coherent_access;
		this.ordered_access = ordered_access;
	}
	public DDS_PresentationQosPolicy(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends DDS_PresentationQosPolicy implements Structure.ByReference {
		
	};
	public static class ByValue extends DDS_PresentationQosPolicy implements Structure.ByValue {
		
	};
}
