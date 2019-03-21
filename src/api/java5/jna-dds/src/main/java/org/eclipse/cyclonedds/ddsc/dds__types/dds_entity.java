/**
 * Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
 * v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.cyclonedds.ddsc.dds__types;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
import org.eclipse.cyclonedds.ddsc.dds__types.DdscLibrary.dds_qos_t;
import org.eclipse.cyclonedds.ddsc.dds__types.DdscLibrary.nn_guid_t;
import org.eclipse.cyclonedds.ddsc.dds__types.DdscLibrary.os_cond;
import org.eclipse.cyclonedds.ddsc.dds__types.DdscLibrary.os_mutex;
import org.eclipse.cyclonedds.ddsc.dds__types.DdscLibrary.ut_handle_t;
import org.eclipse.cyclonedds.ddsc.dds__types.DdscLibrary.ut_handlelink;
/**
 */
public class dds_entity extends Structure {
	/** C type : ut_handle_t */
	public ut_handle_t m_hdl;
	public ut_handle_t getM_hdl() {
		return m_hdl;
	}
	public void setM_hdl(ut_handle_t m_hdl) {
		this.m_hdl = m_hdl;
	}
	/** C type : dds_entity_deriver */
	public dds_entity_deriver m_deriver;
	public dds_entity_deriver getM_deriver() {
		return m_deriver;
	}
	public void setM_deriver(dds_entity_deriver m_deriver) {
		this.m_deriver = m_deriver;
	}
	public int m_refc;
	public int getM_refc() {
		return m_refc;
	}
	public void setM_refc(int m_refc) {
		this.m_refc = m_refc;
	}
	/** C type : dds_entity* */
	public dds_entity.ByReference m_next;
	public dds_entity.ByReference getM_next() {
		return m_next;
	}
	public void setM_next(dds_entity.ByReference m_next) {
		this.m_next = m_next;
	}
	/** C type : dds_entity* */
	public dds_entity.ByReference m_parent;
	public dds_entity.ByReference getM_parent() {
		return m_parent;
	}
	public void setM_parent(dds_entity.ByReference m_parent) {
		this.m_parent = m_parent;
	}
	/** C type : dds_entity* */
	public dds_entity.ByReference m_children;
	public dds_entity.ByReference getM_children() {
		return m_children;
	}
	public void setM_children(dds_entity.ByReference m_children) {
		this.m_children = m_children;
	}
	/** C type : dds_entity* */
	public dds_entity.ByReference m_participant;
	public dds_entity.ByReference getM_participant() {
		return m_participant;
	}
	public void setM_participant(dds_entity.ByReference m_participant) {
		this.m_participant = m_participant;
	}
	/** C type : dds_domain* */
	public org.eclipse.cyclonedds.ddsc.dds__types.dds_domain.ByReference m_domain;
	public org.eclipse.cyclonedds.ddsc.dds__types.dds_domain.ByReference getM_domain() {
		return m_domain;
	}
	public void setM_domain(org.eclipse.cyclonedds.ddsc.dds__types.dds_domain.ByReference m_domain) {
		this.m_domain = m_domain;
	}
	/** C type : dds_qos_t* */
	public dds_qos_t m_qos;
	public dds_qos_t getM_qos() {
		return m_qos;
	}
	public void setM_qos(dds_qos_t m_qos) {
		this.m_qos = m_qos;
	}
	/** C type : dds_domainid_t */
	public int m_domainid;
	public int getM_domainid() {
		return m_domainid;
	}
	public void setM_domainid(int m_domainid) {
		this.m_domainid = m_domainid;
	}
	/** C type : nn_guid_t */
	public nn_guid_t m_guid;
	public nn_guid_t getM_guid() {
		return m_guid;
	}
	public void setM_guid(nn_guid_t m_guid) {
		this.m_guid = m_guid;
	}
	public int m_status_enable;
	public int getM_status_enable() {
		return m_status_enable;
	}
	public void setM_status_enable(int m_status_enable) {
		this.m_status_enable = m_status_enable;
	}
	public int m_flags;
	public int getM_flags() {
		return m_flags;
	}
	public void setM_flags(int m_flags) {
		this.m_flags = m_flags;
	}
	public int m_cb_count;
	public int getM_cb_count() {
		return m_cb_count;
	}
	public void setM_cb_count(int m_cb_count) {
		this.m_cb_count = m_cb_count;
	}
	/** C type : os_mutex */
	public os_mutex m_mutex;
	public os_mutex getM_mutex() {
		return m_mutex;
	}
	public void setM_mutex(os_mutex m_mutex) {
		this.m_mutex = m_mutex;
	}
	/** C type : os_cond */
	public os_cond m_cond;
	public os_cond getM_cond() {
		return m_cond;
	}
	public void setM_cond(os_cond m_cond) {
		this.m_cond = m_cond;
	}
	/** C type : c_listener_t */
	public c_listener m_listener;
	public c_listener getM_listener() {
		return m_listener;
	}
	public void setM_listener(c_listener m_listener) {
		this.m_listener = m_listener;
	}
	public int m_trigger;
	public int getM_trigger() {
		return m_trigger;
	}
	public void setM_trigger(int m_trigger) {
		this.m_trigger = m_trigger;
	}
	/** C type : os_mutex */
	public os_mutex m_observers_lock;
	public os_mutex getM_observers_lock() {
		return m_observers_lock;
	}
	public void setM_observers_lock(os_mutex m_observers_lock) {
		this.m_observers_lock = m_observers_lock;
	}
	/** C type : dds_entity_observer* */
	public org.eclipse.cyclonedds.ddsc.dds__types.dds_entity_observer.ByReference m_observers;
	public org.eclipse.cyclonedds.ddsc.dds__types.dds_entity_observer.ByReference getM_observers() {
		return m_observers;
	}
	public void setM_observers(org.eclipse.cyclonedds.ddsc.dds__types.dds_entity_observer.ByReference m_observers) {
		this.m_observers = m_observers;
	}
	/** C type : ut_handlelink* */
	public ut_handlelink m_hdllink;
	public ut_handlelink getM_hdllink() {
		return m_hdllink;
	}
	public void setM_hdllink(ut_handlelink m_hdllink) {
		this.m_hdllink = m_hdllink;
	}
	public dds_entity() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("m_hdl", "m_deriver", "m_refc", "m_next", "m_parent", "m_children", "m_participant", "m_domain", "m_qos", "m_domainid", "m_guid", "m_status_enable", "m_flags", "m_cb_count", "m_mutex", "m_cond", "m_listener", "m_trigger", "m_observers_lock", "m_observers", "m_hdllink");
	}
	public dds_entity(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends dds_entity implements Structure.ByReference {
		
	};
	public static class ByValue extends dds_entity implements Structure.ByValue {
		
	};
}
