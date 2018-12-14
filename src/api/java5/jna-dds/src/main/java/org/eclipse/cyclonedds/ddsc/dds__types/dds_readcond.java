package org.eclipse.cyclonedds.ddsc.dds__types;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
import org.eclipse.cyclonedds.ddsc.dds__types.DdscLibrary.nn_guid_t;
import org.eclipse.cyclonedds.ddsc.dds__types.DdscLibrary.rhc;
/**
 * <i>native declaration : _dds__types.h:197</i><br>
 */
public class dds_readcond extends Structure {
	/** C type : dds_entity */
	public dds_entity m_entity;
	public dds_entity getM_entity() {
		return m_entity;
	}
	public void setM_entity(dds_entity m_entity) {
		this.m_entity = m_entity;
	}
	/** C type : rhc* */
	public rhc m_rhc;
	public rhc getM_rhc() {
		return m_rhc;
	}
	public void setM_rhc(rhc m_rhc) {
		this.m_rhc = m_rhc;
	}
	public int m_qminv;
	public int getM_qminv() {
		return m_qminv;
	}
	public void setM_qminv(int m_qminv) {
		this.m_qminv = m_qminv;
	}
	public int m_sample_states;
	public int getM_sample_states() {
		return m_sample_states;
	}
	public void setM_sample_states(int m_sample_states) {
		this.m_sample_states = m_sample_states;
	}
	public int m_view_states;
	public int getM_view_states() {
		return m_view_states;
	}
	public void setM_view_states(int m_view_states) {
		this.m_view_states = m_view_states;
	}
	public int m_instance_states;
	public int getM_instance_states() {
		return m_instance_states;
	}
	public void setM_instance_states(int m_instance_states) {
		this.m_instance_states = m_instance_states;
	}
	/** C type : nn_guid_t */
	public nn_guid_t m_rd_guid;
	public nn_guid_t getM_rd_guid() {
		return m_rd_guid;
	}
	public void setM_rd_guid(nn_guid_t m_rd_guid) {
		this.m_rd_guid = m_rd_guid;
	}
	/** C type : dds_readcond* */
	public dds_readcond.ByReference m_rhc_next;
	public dds_readcond.ByReference getM_rhc_next() {
		return m_rhc_next;
	}
	public void setM_rhc_next(dds_readcond.ByReference m_rhc_next) {
		this.m_rhc_next = m_rhc_next;
	}
	/** C type : query */
	public query m_query;
	public query getM_query() {
		return m_query;
	}
	public void setM_query(query m_query) {
		this.m_query = m_query;
	}
	public dds_readcond() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("m_entity", "m_rhc", "m_qminv", "m_sample_states", "m_view_states", "m_instance_states", "m_rd_guid", "m_rhc_next", "m_query");
	}
	/**
	 * @param m_entity C type : dds_entity<br>
	 * @param m_rhc C type : rhc*<br>
	 * @param m_rd_guid C type : nn_guid_t<br>
	 * @param m_rhc_next C type : dds_readcond*<br>
	 * @param m_query C type : query
	 */
	public dds_readcond(dds_entity m_entity, rhc m_rhc, int m_qminv, int m_sample_states, int m_view_states, int m_instance_states, nn_guid_t m_rd_guid, dds_readcond.ByReference m_rhc_next, query m_query) {
		super();
		this.m_entity = m_entity;
		this.m_rhc = m_rhc;
		this.m_qminv = m_qminv;
		this.m_sample_states = m_sample_states;
		this.m_view_states = m_view_states;
		this.m_instance_states = m_instance_states;
		this.m_rd_guid = m_rd_guid;
		this.m_rhc_next = m_rhc_next;
		this.m_query = m_query;
	}
	public dds_readcond(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends dds_readcond implements Structure.ByReference {
		
	};
	public static class ByValue extends dds_readcond implements Structure.ByValue {
		
	};
}
