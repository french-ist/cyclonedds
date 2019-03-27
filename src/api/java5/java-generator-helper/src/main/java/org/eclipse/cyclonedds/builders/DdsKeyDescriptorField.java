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

public class DdsKeyDescriptorField{
	private String  name;
    private Integer index;
	
	public DdsKeyDescriptorField(String name, int index) {
		this.name = name;
		this.index = index;
	}

	public DdsKeyDescriptorField setName(String name) {
		this.name = name;
		return this;
	}
	
	public String getName() {
		return name;
	}
	
	public Integer getIndex() {
		return index;
	}
	
	public DdsKeyDescriptorField setIndex(int index) {
		this.index = index;
		return this;
	}    	
}