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
import com.sun.jna.Union;
import com.sun.jna.ptr.DoubleByReference;
import com.sun.jna.ptr.FloatByReference;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.LongByReference;
import com.sun.jna.ptr.ShortByReference;
/**
 */
public class dds_uptr_t extends Union {
	/** C type : uint8_t* */
	public Pointer p8;
	public Pointer getP8() {
		return p8;
	}
	public void setP8(Pointer p8) {
		this.p8 = p8;
	}
	/** C type : uint16_t* */
	public ShortByReference p16;
	public ShortByReference getP16() {
		return p16;
	}
	public void setP16(ShortByReference p16) {
		this.p16 = p16;
	}
	/** C type : uint32_t* */
	public IntByReference p32;
	public IntByReference getP32() {
		return p32;
	}
	public void setP32(IntByReference p32) {
		this.p32 = p32;
	}
	/** C type : uint64_t* */
	public LongByReference p64;
	public LongByReference getP64() {
		return p64;
	}
	public void setP64(LongByReference p64) {
		this.p64 = p64;
	}
	/** C type : float* */
	public FloatByReference pf;
	public FloatByReference getPf() {
		return pf;
	}
	public void setPf(FloatByReference pf) {
		this.pf = pf;
	}
	/** C type : double* */
	public DoubleByReference pd;
	public DoubleByReference getPd() {
		return pd;
	}
	public void setPd(DoubleByReference pd) {
		this.pd = pd;
	}
	/** C type : void* */
	public Pointer pv;
	public Pointer getPv() {
		return pv;
	}
	public void setPv(Pointer pv) {
		this.pv = pv;
	}
	public dds_uptr_t() {
		super();
	}
	/** @param p8_or_pv C type : uint8_t*, or C type : void* */
	public dds_uptr_t(Pointer p8_or_pv) {
		super();
		this.pv = this.p8 = p8_or_pv;
		setType(Pointer.class);
	}
	/** @param p16 C type : uint16_t* */
	public dds_uptr_t(ShortByReference p16) {
		super();
		this.p16 = p16;
		setType(ShortByReference.class);
	}
	/** @param p32 C type : uint32_t* */
	public dds_uptr_t(IntByReference p32) {
		super();
		this.p32 = p32;
		setType(IntByReference.class);
	}
	/** @param p64 C type : uint64_t* */
	public dds_uptr_t(LongByReference p64) {
		super();
		this.p64 = p64;
		setType(LongByReference.class);
	}
	/** @param pf C type : float* */
	public dds_uptr_t(FloatByReference pf) {
		super();
		this.pf = pf;
		setType(FloatByReference.class);
	}
	/** @param pd C type : double* */
	public dds_uptr_t(DoubleByReference pd) {
		super();
		this.pd = pd;
		setType(DoubleByReference.class);
	}
	public static class ByReference extends dds_uptr_t implements com.sun.jna.Structure.ByReference {
		
	};
	public static class ByValue extends dds_uptr_t implements com.sun.jna.Structure.ByValue {
		
	};
}
