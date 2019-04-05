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
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
import org.eclipse.cyclonedds.ddsc.dds__types.DdscLibrary.dds_entity_callback;
/**
 */
public class dds_entity_observer extends Structure {
	/** C type : dds_entity_callback */
	public dds_entity_callback m_cb;
	public dds_entity_callback getM_cb() {
		return m_cb;
	}
	public void setM_cb(dds_entity_callback m_cb) {
		this.m_cb = m_cb;
	}
	/** C type : dds_entity_t */
	public int m_observer;
	public int getM_observer() {
		return m_observer;
	}
	public void setM_observer(int m_observer) {
		this.m_observer = m_observer;
	}
	/** C type : dds_entity_observer* */
	public dds_entity_observer.ByReference m_next;
	public dds_entity_observer.ByReference getM_next() {
		return m_next;
	}
	public void setM_next(dds_entity_observer.ByReference m_next) {
		this.m_next = m_next;
	}
	public dds_entity_observer() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("m_cb", "m_observer", "m_next");
	}
	/**
	 * @param m_cb C type : dds_entity_callback<br>
	 * @param m_observer C type : dds_entity_t<br>
	 * @param m_next C type : dds_entity_observer*
	 */
	public dds_entity_observer(dds_entity_callback m_cb, int m_observer, dds_entity_observer.ByReference m_next) {
		super();
		this.m_cb = m_cb;
		this.m_observer = m_observer;
		this.m_next = m_next;
	}
	public dds_entity_observer(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends dds_entity_observer implements Structure.ByReference {
		
	};
	public static class ByValue extends dds_entity_observer implements Structure.ByValue {
		
	};
}
