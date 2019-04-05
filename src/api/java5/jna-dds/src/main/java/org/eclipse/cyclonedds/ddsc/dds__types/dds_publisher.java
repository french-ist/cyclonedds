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
/**
 */
public class dds_publisher extends Structure {
	/** C type : dds_entity */
	public dds_entity m_entity;
	public dds_entity getM_entity() {
		return m_entity;
	}
	public void setM_entity(dds_entity m_entity) {
		this.m_entity = m_entity;
	}
	public dds_publisher() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("m_entity");
	}
	/** @param m_entity C type : dds_entity */
	public dds_publisher(dds_entity m_entity) {
		super();
		this.m_entity = m_entity;
	}
	public dds_publisher(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends dds_publisher implements Structure.ByReference {
		
	};
	public static class ByValue extends dds_publisher implements Structure.ByValue {
		
	};
}
