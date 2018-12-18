package org.eclipse.cyclonedds.ddsc.dds__types;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
import org.eclipse.cyclonedds.ddsc.dds__types.DdscLibrary.dds_liveliness_lost_status_t;
import org.eclipse.cyclonedds.ddsc.dds__types.DdscLibrary.dds_offered_deadline_missed_status_t;
import org.eclipse.cyclonedds.ddsc.dds__types.DdscLibrary.dds_offered_incompatible_qos_status_t;
import org.eclipse.cyclonedds.ddsc.dds__types.DdscLibrary.dds_publication_matched_status_t;
import org.eclipse.cyclonedds.ddsc.dds__types.DdscLibrary.nn_xpack;
import org.eclipse.cyclonedds.ddsc.dds__types.DdscLibrary.whc;
import org.eclipse.cyclonedds.ddsc.dds__types.DdscLibrary.writer;
/**
 */
public class dds_writer extends Structure {
	/** C type : dds_entity */
	public dds_entity m_entity;
	public dds_entity getM_entity() {
		return m_entity;
	}
	public void setM_entity(dds_entity m_entity) {
		this.m_entity = m_entity;
	}
	/** C type : dds_topic* */
	public org.eclipse.cyclonedds.ddsc.dds__types.dds_topic.ByReference m_topic;
	public org.eclipse.cyclonedds.ddsc.dds__types.dds_topic.ByReference getM_topic() {
		return m_topic;
	}
	public void setM_topic(org.eclipse.cyclonedds.ddsc.dds__types.dds_topic.ByReference m_topic) {
		this.m_topic = m_topic;
	}
	/** C type : nn_xpack* */
	public nn_xpack m_xp;
	public nn_xpack getM_xp() {
		return m_xp;
	}
	public void setM_xp(nn_xpack m_xp) {
		this.m_xp = m_xp;
	}
	/** C type : writer* */
	public writer m_wr;
	public writer getM_wr() {
		return m_wr;
	}
	public void setM_wr(writer m_wr) {
		this.m_wr = m_wr;
	}
	/**
	 * FIXME: ownership still with underlying DDSI writer (cos of DDSI built-in writers )<br>
	 * C type : whc*
	 */
	public whc m_whc;
	public whc getM_whc() {
		return m_whc;
	}
	public void setM_whc(whc m_whc) {
		this.m_whc = m_whc;
	}
	/** C type : dds_liveliness_lost_status_t */
	public dds_liveliness_lost_status_t m_liveliness_lost_status;
	public dds_liveliness_lost_status_t getM_liveliness_lost_status() {
		return m_liveliness_lost_status;
	}
	public void setM_liveliness_lost_status(dds_liveliness_lost_status_t m_liveliness_lost_status) {
		this.m_liveliness_lost_status = m_liveliness_lost_status;
	}
	/** C type : dds_offered_deadline_missed_status_t */
	public dds_offered_deadline_missed_status_t m_offered_deadline_missed_status;
	public dds_offered_deadline_missed_status_t getM_offered_deadline_missed_status() {
		return m_offered_deadline_missed_status;
	}
	public void setM_offered_deadline_missed_status(dds_offered_deadline_missed_status_t m_offered_deadline_missed_status) {
		this.m_offered_deadline_missed_status = m_offered_deadline_missed_status;
	}
	/** C type : dds_offered_incompatible_qos_status_t */
	public dds_offered_incompatible_qos_status_t m_offered_incompatible_qos_status;
	public dds_offered_incompatible_qos_status_t getM_offered_incompatible_qos_status() {
		return m_offered_incompatible_qos_status;
	}
	public void setM_offered_incompatible_qos_status(dds_offered_incompatible_qos_status_t m_offered_incompatible_qos_status) {
		this.m_offered_incompatible_qos_status = m_offered_incompatible_qos_status;
	}
	/** C type : dds_publication_matched_status_t */
	public dds_publication_matched_status_t m_publication_matched_status;
	public dds_publication_matched_status_t getM_publication_matched_status() {
		return m_publication_matched_status;
	}
	public void setM_publication_matched_status(dds_publication_matched_status_t m_publication_matched_status) {
		this.m_publication_matched_status = m_publication_matched_status;
	}
	public dds_writer() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("m_entity", "m_topic", "m_xp", "m_wr", "m_whc", "m_liveliness_lost_status", "m_offered_deadline_missed_status", "m_offered_incompatible_qos_status", "m_publication_matched_status");
	}
	/**
	 * @param m_entity C type : dds_entity<br>
	 * @param m_topic C type : dds_topic*<br>
	 * @param m_xp C type : nn_xpack*<br>
	 * @param m_wr C type : writer*<br>
	 * @param m_whc FIXME: ownership still with underlying DDSI writer (cos of DDSI built-in writers )<br>
	 * C type : whc*<br>
	 * @param m_liveliness_lost_status C type : dds_liveliness_lost_status_t<br>
	 * @param m_offered_deadline_missed_status C type : dds_offered_deadline_missed_status_t<br>
	 * @param m_offered_incompatible_qos_status C type : dds_offered_incompatible_qos_status_t<br>
	 * @param m_publication_matched_status C type : dds_publication_matched_status_t
	 */
	public dds_writer(dds_entity m_entity, org.eclipse.cyclonedds.ddsc.dds__types.dds_topic.ByReference m_topic, nn_xpack m_xp, writer m_wr, whc m_whc, dds_liveliness_lost_status_t m_liveliness_lost_status, dds_offered_deadline_missed_status_t m_offered_deadline_missed_status, dds_offered_incompatible_qos_status_t m_offered_incompatible_qos_status, dds_publication_matched_status_t m_publication_matched_status) {
		super();
		this.m_entity = m_entity;
		this.m_topic = m_topic;
		this.m_xp = m_xp;
		this.m_wr = m_wr;
		this.m_whc = m_whc;
		this.m_liveliness_lost_status = m_liveliness_lost_status;
		this.m_offered_deadline_missed_status = m_offered_deadline_missed_status;
		this.m_offered_incompatible_qos_status = m_offered_incompatible_qos_status;
		this.m_publication_matched_status = m_publication_matched_status;
	}
	public dds_writer(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends dds_writer implements Structure.ByReference {
		
	};
	public static class ByValue extends dds_writer implements Structure.ByValue {
		
	};
}
