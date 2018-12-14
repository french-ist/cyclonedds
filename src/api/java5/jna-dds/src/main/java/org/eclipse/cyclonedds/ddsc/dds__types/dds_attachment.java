package org.eclipse.cyclonedds.ddsc.dds__types;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.ptr.IntByReference;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : _dds__types.h:205</i><br>
 */
public class dds_attachment extends Structure {
	/** C type : dds_entity* */
	public org.eclipse.cyclonedds.ddsc.dds__types.dds_entity.ByReference entity;
	public org.eclipse.cyclonedds.ddsc.dds__types.dds_entity.ByReference getEntity() {
		return entity;
	}
	public void setEntity(org.eclipse.cyclonedds.ddsc.dds__types.dds_entity.ByReference entity) {
		this.entity = entity;
	}
	/** C type : dds_attach_t */
	public IntByReference arg;
	public IntByReference getArg() {
		return arg;
	}
	public void setArg(IntByReference arg) {
		this.arg = arg;
	}
	/** C type : dds_attachment* */
	public dds_attachment.ByReference next;
	public dds_attachment.ByReference getNext() {
		return next;
	}
	public void setNext(dds_attachment.ByReference next) {
		this.next = next;
	}
	public dds_attachment() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("entity", "arg", "next");
	}
	/**
	 * @param entity C type : dds_entity*<br>
	 * @param arg C type : dds_attach_t<br>
	 * @param next C type : dds_attachment*
	 */
	public dds_attachment(org.eclipse.cyclonedds.ddsc.dds__types.dds_entity.ByReference entity, IntByReference arg, dds_attachment.ByReference next) {
		super();
		this.entity = entity;
		this.arg = arg;
		this.next = next;
	}
	public dds_attachment(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends dds_attachment implements Structure.ByReference {
		
	};
	public static class ByValue extends dds_attachment implements Structure.ByValue {
		
	};
}
