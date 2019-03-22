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
import org.eclipse.cyclonedds.ddsc.dds__types.DdscLibrary.dds_attach_t;
/**
 */
public class dds_attachment extends Structure {
	/** C type : dds_entity* */
	public org.eclipse.cyclonedds.ddsc.dds__types.dds_entity.ByReference entity;
	public org.eclipse.cyclonedds.ddsc.dds__types.dds_entity.ByReference getEntity() {
		return entity;
	}
	public void setEntity(org.eclipse.cyclonedds.ddsc.dds__types.dds_entity.ByReference entity) {
		this.entity = entity;
	}
	/** C type : dds_attach_t */
	public dds_attach_t arg;
	public dds_attach_t getArg() {
		return arg;
	}
	public void setArg(dds_attach_t arg) {
		this.arg = arg;
	}
	/** C type : dds_attachment* */
	public dds_attachment.ByReference next;
	public dds_attachment.ByReference getNext() {
		return next;
	}
	public void setNext(dds_attachment.ByReference next) {
		this.next = next;
	}
	public dds_attachment() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("entity", "arg", "next");
	}
	/**
	 * @param entity C type : dds_entity*<br>
	 * @param arg C type : dds_attach_t<br>
	 * @param next C type : dds_attachment*
	 */
	public dds_attachment(org.eclipse.cyclonedds.ddsc.dds__types.dds_entity.ByReference entity, dds_attach_t arg, dds_attachment.ByReference next) {
		super();
		this.entity = entity;
		this.arg = arg;
		this.next = next;
	}
	public dds_attachment(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends dds_attachment implements Structure.ByReference {
		
	};
	public static class ByValue extends dds_attachment implements Structure.ByValue {
		
	};
}
