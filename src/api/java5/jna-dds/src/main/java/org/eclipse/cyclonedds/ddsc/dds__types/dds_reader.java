package org.eclipse.cyclonedds.ddsc.dds__types;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
import org.eclipse.cyclonedds.ddsc.dds__types.DdscLibrary.dds_liveliness_changed_status_t;
import org.eclipse.cyclonedds.ddsc.dds__types.DdscLibrary.dds_requested_deadline_missed_status_t;
import org.eclipse.cyclonedds.ddsc.dds__types.DdscLibrary.dds_requested_incompatible_qos_status_t;
import org.eclipse.cyclonedds.ddsc.dds__types.DdscLibrary.dds_sample_lost_status_t;
import org.eclipse.cyclonedds.ddsc.dds__types.DdscLibrary.dds_sample_rejected_status_t;
import org.eclipse.cyclonedds.ddsc.dds__types.DdscLibrary.dds_subscription_matched_status_t;
import org.eclipse.cyclonedds.ddsc.dds__types.DdscLibrary.reader;
/**
 */
public class dds_reader extends Structure {
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
	/** C type : reader* */
	public reader m_rd;
	public reader getM_rd() {
		return m_rd;
	}
	public void setM_rd(reader m_rd) {
		this.m_rd = m_rd;
	}
	public byte m_data_on_readers;
	public byte getM_data_on_readers() {
		return m_data_on_readers;
	}
	public void setM_data_on_readers(byte m_data_on_readers) {
		this.m_data_on_readers = m_data_on_readers;
	}
	public byte m_loan_out;
	public byte getM_loan_out() {
		return m_loan_out;
	}
	public void setM_loan_out(byte m_loan_out) {
		this.m_loan_out = m_loan_out;
	}
	/** C type : char* */
	public Pointer m_loan;
	public Pointer getM_loan() {
		return m_loan;
	}
	public void setM_loan(Pointer m_loan) {
		this.m_loan = m_loan;
	}
	public int m_loan_size;
	public int getM_loan_size() {
		return m_loan_size;
	}
	public void setM_loan_size(int m_loan_size) {
		this.m_loan_size = m_loan_size;
	}
	/** C type : dds_sample_rejected_status_t */
	public dds_sample_rejected_status_t m_sample_rejected_status;
	public dds_sample_rejected_status_t getM_sample_rejected_status() {
		return m_sample_rejected_status;
	}
	public void setM_sample_rejected_status(dds_sample_rejected_status_t m_sample_rejected_status) {
		this.m_sample_rejected_status = m_sample_rejected_status;
	}
	/** C type : dds_liveliness_changed_status_t */
	public dds_liveliness_changed_status_t m_liveliness_changed_status;
	public dds_liveliness_changed_status_t getM_liveliness_changed_status() {
		return m_liveliness_changed_status;
	}
	public void setM_liveliness_changed_status(dds_liveliness_changed_status_t m_liveliness_changed_status) {
		this.m_liveliness_changed_status = m_liveliness_changed_status;
	}
	/** C type : dds_requested_deadline_missed_status_t */
	public dds_requested_deadline_missed_status_t m_requested_deadline_missed_status;
	public dds_requested_deadline_missed_status_t getM_requested_deadline_missed_status() {
		return m_requested_deadline_missed_status;
	}
	public void setM_requested_deadline_missed_status(dds_requested_deadline_missed_status_t m_requested_deadline_missed_status) {
		this.m_requested_deadline_missed_status = m_requested_deadline_missed_status;
	}
	/** C type : dds_requested_incompatible_qos_status_t */
	public dds_requested_incompatible_qos_status_t m_requested_incompatible_qos_status;
	public dds_requested_incompatible_qos_status_t getM_requested_incompatible_qos_status() {
		return m_requested_incompatible_qos_status;
	}
	public void setM_requested_incompatible_qos_status(dds_requested_incompatible_qos_status_t m_requested_incompatible_qos_status) {
		this.m_requested_incompatible_qos_status = m_requested_incompatible_qos_status;
	}
	/** C type : dds_sample_lost_status_t */
	public dds_sample_lost_status_t m_sample_lost_status;
	public dds_sample_lost_status_t getM_sample_lost_status() {
		return m_sample_lost_status;
	}
	public void setM_sample_lost_status(dds_sample_lost_status_t m_sample_lost_status) {
		this.m_sample_lost_status = m_sample_lost_status;
	}
	/** C type : dds_subscription_matched_status_t */
	public dds_subscription_matched_status_t m_subscription_matched_status;
	public dds_subscription_matched_status_t getM_subscription_matched_status() {
		return m_subscription_matched_status;
	}
	public void setM_subscription_matched_status(dds_subscription_matched_status_t m_subscription_matched_status) {
		this.m_subscription_matched_status = m_subscription_matched_status;
	}
	public dds_reader() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("m_entity", "m_topic", "m_rd", "m_data_on_readers", "m_loan_out", "m_loan", "m_loan_size", "m_sample_rejected_status", "m_liveliness_changed_status", "m_requested_deadline_missed_status", "m_requested_incompatible_qos_status", "m_sample_lost_status", "m_subscription_matched_status");
	}
	public dds_reader(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends dds_reader implements Structure.ByReference {
		
	};
	public static class ByValue extends dds_reader implements Structure.ByValue {
		
	};
}
