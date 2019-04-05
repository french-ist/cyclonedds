/**
  Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 
  This program and the accompanying materials are made available under the
  terms of the Eclipse Public License v. 2.0 which is available at
  http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
  v. 1.0 which is available at
  http://www.eclipse.org/org/documents/edl-v10.php.
 
  SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.cyclonedds.ddsc.dds_public_impl;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 */
public class dds_key_descriptor extends Structure {
	/** C type : const char* */
	public Pointer m_name;
	public Pointer getM_name() {
		return m_name;
	}
	public void setM_name(Pointer m_name) {
		this.m_name = m_name;
	}
	public int m_index;
	public int getM_index() {
		return m_index;
	}
	public void setM_index(int m_index) {
		this.m_index = m_index;
	}
	public dds_key_descriptor() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("m_name", "m_index");
	}
	/** @param m_name C type : const char* */
	public dds_key_descriptor(Pointer m_name, int m_index) {
		super();
		this.m_name = m_name;
		this.m_index = m_index;
	}
	public dds_key_descriptor(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends dds_key_descriptor implements Structure.ByReference {
		
	};
	public static class ByValue extends dds_key_descriptor implements Structure.ByValue {
		
	};
}
