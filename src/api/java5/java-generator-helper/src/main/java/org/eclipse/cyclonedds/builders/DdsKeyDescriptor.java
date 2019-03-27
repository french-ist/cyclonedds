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
package org.eclipse.cyclonedds.builders;

import java.util.ArrayList;

public class DdsKeyDescriptor {
	
	String propertyName;
	ArrayList<DdsKeyDescriptorField> fieldsList;
	private int fieldsCount = 0;
	
	public DdsKeyDescriptor() {    		
		fieldsList = new ArrayList<DdsKeyDescriptorField>();
	}
	
	public DdsKeyDescriptor(String propertyName) {
		this.propertyName = propertyName;
		fieldsList = new ArrayList<DdsKeyDescriptorField>();
	}
	
	public DdsKeyDescriptor setPropertyName(String propertyName) {
		this.propertyName = propertyName;
		return this;
	}
	
	public String getPropertyName() {
		return propertyName;
	}
	
	public ArrayList<DdsKeyDescriptorField> getFieldsList(){
		return fieldsList;
	}
	
	public DdsKeyDescriptor addField(String name, int index) {
		fieldsList.add(new DdsKeyDescriptorField(name, index));
		fieldsCount++;
		return this;
	}
	
	public DdsKeyDescriptor addField(DdsKeyDescriptorField field) {
		fieldsList.add(field);
		fieldsCount++;
		return this;
	}
	
	public DdsKeyDescriptorField getLast() {
		return fieldsList.get(fieldsCount-1);
	}
}
