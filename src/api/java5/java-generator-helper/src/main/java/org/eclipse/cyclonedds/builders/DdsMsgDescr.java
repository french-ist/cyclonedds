/**
  Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 
  This program and the accompanying materials are made available under the
  terms of the Eclipse Public License v. 2.0 which is available at
  http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
  v. 1.0 which is available at
  http://www.eclipse.org/org/documents/edl-v10.php.
 
  SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.cyclonedds.builders;

import java.util.ArrayList;

public class DdsMsgDescr {
	
	private ArrayList<String> propertiesList;
	private int propertiesCount;
	private String propertyName;

	public DdsMsgDescr() {
		propertiesList = new ArrayList<String>();
		propertiesCount = 0;
	}
	
	public DdsMsgDescr(String propertyName) {
		this();
		this.propertyName = propertyName;		
	}
	
	public DdsMsgDescr addDescriptor(String descr) {
		propertiesList.add(descr);
		propertiesCount++;
		return this;
	}
	
	public String getLast() {
		return propertiesList.get(propertiesCount-1);
	}
	
	public DdsMsgDescr appendToLast(String descr) {
		propertiesList.set(propertiesCount-1, getLast() + descr);
		return this;
	}
	
	public ArrayList<String> getPropertiesList(){
		return propertiesList;
	}

	public String getPropertyName() {
		return propertyName;
	}
}
