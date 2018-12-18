package org.eclipse.cyclonedds.ddsc.dds__types;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
import org.eclipse.cyclonedds.ddsc.dds__types.DdscLibrary.dds_inconsistent_topic_status_t;
import org.eclipse.cyclonedds.ddsc.dds__types.DdscLibrary.dds_topic_descriptor_t;
import org.eclipse.cyclonedds.ddsc.dds__types.DdscLibrary.sertopic;
/**
 */
public class dds_topic extends Structure {
	/** C type : dds_entity */
	public dds_entity m_entity;
	public dds_entity getM_entity() {
		return m_entity;
	}
	public void setM_entity(dds_entity m_entity) {
		this.m_entity = m_entity;
	}
	/** C type : sertopic* */
	public sertopic m_stopic;
	public sertopic getM_stopic() {
		return m_stopic;
	}
	public void setM_stopic(sertopic m_stopic) {
		this.m_stopic = m_stopic;
	}
	/** C type : const dds_topic_descriptor_t* */
	public dds_topic_descriptor_t m_descriptor;
	public dds_topic_descriptor_t getM_descriptor() {
		return m_descriptor;
	}
	public void setM_descriptor(dds_topic_descriptor_t m_descriptor) {
		this.m_descriptor = m_descriptor;
	}
	/** C type : dds_inconsistent_topic_status_t */
	public dds_inconsistent_topic_status_t m_inconsistent_topic_status;
	public dds_inconsistent_topic_status_t getM_inconsistent_topic_status() {
		return m_inconsistent_topic_status;
	}
	public void setM_inconsistent_topic_status(dds_inconsistent_topic_status_t m_inconsistent_topic_status) {
		this.m_inconsistent_topic_status = m_inconsistent_topic_status;
	}
	public dds_topic() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("m_entity", "m_stopic", "m_descriptor", "m_inconsistent_topic_status");
	}
	/**
	 * @param m_entity C type : dds_entity<br>
	 * @param m_stopic C type : sertopic*<br>
	 * @param m_descriptor C type : const dds_topic_descriptor_t*<br>
	 * @param m_inconsistent_topic_status C type : dds_inconsistent_topic_status_t
	 */
	public dds_topic(dds_entity m_entity, sertopic m_stopic, dds_topic_descriptor_t m_descriptor, dds_inconsistent_topic_status_t m_inconsistent_topic_status) {
		super();
		this.m_entity = m_entity;
		this.m_stopic = m_stopic;
		this.m_descriptor = m_descriptor;
		this.m_inconsistent_topic_status = m_inconsistent_topic_status;
	}
	public dds_topic(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends dds_topic implements Structure.ByReference {
		
	};
	public static class ByValue extends dds_topic implements Structure.ByValue {
		
	};
}
