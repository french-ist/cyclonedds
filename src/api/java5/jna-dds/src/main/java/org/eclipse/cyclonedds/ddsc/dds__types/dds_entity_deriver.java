/**
  Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 
  This program and the accompanying materials are made available under the
  terms of the Eclipse Public License v. 2.0 which is available at
  http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
  v. 1.0 which is available at
  http://www.eclipse.org/org/documents/edl-v10.php.
 
  SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.cyclonedds.ddsc.dds__types;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Callback;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.ptr.LongByReference;
import java.util.Arrays;
import java.util.List;
import org.eclipse.cyclonedds.ddsc.dds__types.DdscLibrary.dds_qos_t;
/**
 */
public class dds_entity_deriver extends Structure {
	/** C type : close_callback* */
	public dds_entity_deriver.close_callback close;
	public dds_entity_deriver.close_callback getClose() {
		return close;
	}
	public void setClose(dds_entity_deriver.close_callback close) {
		this.close = close;
	}
	/** C type : delete_callback* */
	public dds_entity_deriver.delete_callback delete;
	public dds_entity_deriver.delete_callback getDelete() {
		return delete;
	}
	public void setDelete(dds_entity_deriver.delete_callback delete) {
		this.delete = delete;
	}
	/** C type : set_qos_callback* */
	public dds_entity_deriver.set_qos_callback set_qos;
	public dds_entity_deriver.set_qos_callback getSet_qos() {
		return set_qos;
	}
	public void setSet_qos(dds_entity_deriver.set_qos_callback set_qos) {
		this.set_qos = set_qos;
	}
	/** C type : validate_status_callback* */
	public dds_entity_deriver.validate_status_callback validate_status;
	public dds_entity_deriver.validate_status_callback getValidate_status() {
		return validate_status;
	}
	public void setValidate_status(dds_entity_deriver.validate_status_callback validate_status) {
		this.validate_status = validate_status;
	}
	/** C type : propagate_status_callback* */
	public dds_entity_deriver.propagate_status_callback propagate_status;
	public dds_entity_deriver.propagate_status_callback getPropagate_status() {
		return propagate_status;
	}
	public void setPropagate_status(dds_entity_deriver.propagate_status_callback propagate_status) {
		this.propagate_status = propagate_status;
	}
	/** C type : get_instance_hdl_callback* */
	public dds_entity_deriver.get_instance_hdl_callback get_instance_hdl;
	public dds_entity_deriver.get_instance_hdl_callback getGet_instance_hdl() {
		return get_instance_hdl;
	}
	public void setGet_instance_hdl(dds_entity_deriver.get_instance_hdl_callback get_instance_hdl) {
		this.get_instance_hdl = get_instance_hdl;
	}
	public interface close_callback extends Callback {
		int apply(dds_entity e);
	};
	public interface delete_callback extends Callback {
		int apply(dds_entity e);
	};
	public interface set_qos_callback extends Callback {
		int apply(dds_entity e, dds_qos_t qos, byte enabled);
	};
	public interface validate_status_callback extends Callback {
		int apply(int mask);
	};
	public interface propagate_status_callback extends Callback {
		int apply(dds_entity e, int mask, byte set);
	};
	public interface get_instance_hdl_callback extends Callback {
		int apply(dds_entity e, LongByReference i);
	};
	public dds_entity_deriver() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("close", "delete", "set_qos", "validate_status", "propagate_status", "get_instance_hdl");
	}
	/**
	 * @param close C type : close_callback*<br>
	 * @param delete C type : delete_callback*<br>
	 * @param set_qos C type : set_qos_callback*<br>
	 * @param validate_status C type : validate_status_callback*<br>
	 * @param propagate_status C type : propagate_status_callback*<br>
	 * @param get_instance_hdl C type : get_instance_hdl_callback*
	 */
	public dds_entity_deriver(dds_entity_deriver.close_callback close, dds_entity_deriver.delete_callback delete, dds_entity_deriver.set_qos_callback set_qos, dds_entity_deriver.validate_status_callback validate_status, dds_entity_deriver.propagate_status_callback propagate_status, dds_entity_deriver.get_instance_hdl_callback get_instance_hdl) {
		super();
		this.close = close;
		this.delete = delete;
		this.set_qos = set_qos;
		this.validate_status = validate_status;
		this.propagate_status = propagate_status;
		this.get_instance_hdl = get_instance_hdl;
	}
	public dds_entity_deriver(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends dds_entity_deriver implements Structure.ByReference {
		
	};
	public static class ByValue extends dds_entity_deriver implements Structure.ByValue {
		
	};
}
