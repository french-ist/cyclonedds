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
package org.eclipse.cyclonedds.ddsc.dds_public_stream;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
import org.eclipse.cyclonedds.ddsc.dds_public_stream.DdscLibrary._Bool;
/**
 */
public class dds_stream extends Structure {
	/**
	 * Union of pointers to start of buffer<br>
	 * C type : dds_uptr_t
	 */
	public dds_uptr_t m_buffer;
	public dds_uptr_t getM_buffer() {
		return m_buffer;
	}
	public void setM_buffer(dds_uptr_t m_buffer) {
		this.m_buffer = m_buffer;
	}
	/** Buffer size */
	public int m_size;
	public int getM_size() {
		return m_size;
	}
	public void setM_size(int m_size) {
		this.m_size = m_size;
	}
	/** Read/write offset from start of buffer */
	public int m_index;
	public int getM_index() {
		return m_index;
	}
	public void setM_index(int m_index) {
		this.m_index = m_index;
	}
	/**
	 * Endian: big (false) or little (true)<br>
	 * C type : _Bool
	 */
	public _Bool m_endian;
	public _Bool getM_endian() {
		return m_endian;
	}
	public void setM_endian(_Bool m_endian) {
		this.m_endian = m_endian;
	}
	/**
	 * Attempt made to read beyond end of buffer<br>
	 * C type : _Bool
	 */
	public _Bool m_failed;
	public _Bool getM_failed() {
		return m_failed;
	}
	public void setM_failed(_Bool m_failed) {
		this.m_failed = m_failed;
	}
	public dds_stream() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("m_buffer", "m_size", "m_index", "m_endian", "m_failed");
	}
	/**
	 * @param m_buffer Union of pointers to start of buffer<br>
	 * C type : dds_uptr_t<br>
	 * @param m_size Buffer size<br>
	 * @param m_index Read/write offset from start of buffer<br>
	 * @param m_endian Endian: big (false) or little (true)<br>
	 * C type : _Bool<br>
	 * @param m_failed Attempt made to read beyond end of buffer<br>
	 * C type : _Bool
	 */
	public dds_stream(dds_uptr_t m_buffer, int m_size, int m_index, _Bool m_endian, _Bool m_failed) {
		super();
		this.m_buffer = m_buffer;
		this.m_size = m_size;
		this.m_index = m_index;
		this.m_endian = m_endian;
		this.m_failed = m_failed;
	}
	public dds_stream(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends dds_stream implements Structure.ByReference {
		
	};
	public static class ByValue extends dds_stream implements Structure.ByValue {
		
	};
}
