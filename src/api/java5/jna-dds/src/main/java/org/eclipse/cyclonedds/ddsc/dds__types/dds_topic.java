package org.eclipse.cyclonedds.ddsc.dds__types;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
import org.eclipse.cyclonedds.ddsc.dds__types.DdscLibrary.dds_inconsistent_topic_status_t;
import org.eclipse.cyclonedds.ddsc.dds__types.DdscLibrary.dds_topic_intern_filter_fn;
import org.eclipse.cyclonedds.ddsc.dds__types.DdscLibrary.ddsi_sertopic;
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
	/** C type : ddsi_sertopic* */
	public ddsi_sertopic m_stopic;
	public ddsi_sertopic getM_stopic() {
		return m_stopic;
	}
	public void setM_stopic(ddsi_sertopic m_stopic) {
		this.m_stopic = m_stopic;
	}
	/** C type : dds_topic_intern_filter_fn */
	public dds_topic_intern_filter_fn filter_fn;
	public dds_topic_intern_filter_fn getFilter_fn() {
		return filter_fn;
	}
	public void setFilter_fn(dds_topic_intern_filter_fn filter_fn) {
		this.filter_fn = filter_fn;
	}
	/** C type : void* */
	public Pointer filter_ctx;
	public Pointer getFilter_ctx() {
		return filter_ctx;
	}
	public void setFilter_ctx(Pointer filter_ctx) {
		this.filter_ctx = filter_ctx;
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
		return Arrays.asList("m_entity", "m_stopic", "filter_fn", "filter_ctx", "m_inconsistent_topic_status");
	}
	/**
	 * @param m_entity C type : dds_entity<br>
	 * @param m_stopic C type : ddsi_sertopic*<br>
	 * @param filter_fn C type : dds_topic_intern_filter_fn<br>
	 * @param filter_ctx C type : void*<br>
	 * @param m_inconsistent_topic_status C type : dds_inconsistent_topic_status_t
	 */
	public dds_topic(dds_entity m_entity, ddsi_sertopic m_stopic, dds_topic_intern_filter_fn filter_fn, Pointer filter_ctx, dds_inconsistent_topic_status_t m_inconsistent_topic_status) {
		super();
		this.m_entity = m_entity;
		this.m_stopic = m_stopic;
		this.filter_fn = filter_fn;
		this.filter_ctx = filter_ctx;
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
