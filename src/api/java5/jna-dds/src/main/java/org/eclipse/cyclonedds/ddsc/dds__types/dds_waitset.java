package org.eclipse.cyclonedds.ddsc.dds__types;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : _dds__types.h:210</i><br>
 */
public class dds_waitset extends Structure {
	/** C type : dds_entity */
	public dds_entity m_entity;
	public dds_entity getM_entity() {
		return m_entity;
	}
	public void setM_entity(dds_entity m_entity) {
		this.m_entity = m_entity;
	}
	/** C type : dds_attachment* */
	public org.eclipse.cyclonedds.ddsc.dds__types.dds_attachment.ByReference observed;
	public org.eclipse.cyclonedds.ddsc.dds__types.dds_attachment.ByReference getObserved() {
		return observed;
	}
	public void setObserved(org.eclipse.cyclonedds.ddsc.dds__types.dds_attachment.ByReference observed) {
		this.observed = observed;
	}
	/** C type : dds_attachment* */
	public org.eclipse.cyclonedds.ddsc.dds__types.dds_attachment.ByReference triggered;
	public org.eclipse.cyclonedds.ddsc.dds__types.dds_attachment.ByReference getTriggered() {
		return triggered;
	}
	public void setTriggered(org.eclipse.cyclonedds.ddsc.dds__types.dds_attachment.ByReference triggered) {
		this.triggered = triggered;
	}
	public dds_waitset() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("m_entity", "observed", "triggered");
	}
	/**
	 * @param m_entity C type : dds_entity<br>
	 * @param observed C type : dds_attachment*<br>
	 * @param triggered C type : dds_attachment*
	 */
	public dds_waitset(dds_entity m_entity, org.eclipse.cyclonedds.ddsc.dds__types.dds_attachment.ByReference observed, org.eclipse.cyclonedds.ddsc.dds__types.dds_attachment.ByReference triggered) {
		super();
		this.m_entity = m_entity;
		this.observed = observed;
		this.triggered = triggered;
	}
	public dds_waitset(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends dds_waitset implements Structure.ByReference {
		
	};
	public static class ByValue extends dds_waitset implements Structure.ByValue {
		
	};
}
