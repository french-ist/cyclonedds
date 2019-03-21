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
import org.eclipse.cyclonedds.ddsc.dds__types.DdscLibrary.dds_querycondition_filter_fn;
/**
 */
public class query extends Structure {
	/** C type : dds_querycondition_filter_fn */
	public dds_querycondition_filter_fn m_filter;
	public dds_querycondition_filter_fn getM_filter() {
		return m_filter;
	}
	public void setM_filter(dds_querycondition_filter_fn m_filter) {
		this.m_filter = m_filter;
	}
	public query() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("m_filter");
	}
	/** @param m_filter C type : dds_querycondition_filter_fn */
	public query(dds_querycondition_filter_fn m_filter) {
		super();
		this.m_filter = m_filter;
	}
	public query(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends query implements Structure.ByReference {
		
	};
	public static class ByValue extends query implements Structure.ByValue {
		
	};
}
