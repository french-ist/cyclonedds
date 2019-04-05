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

public class DdsOptions {
	
	private String propertyName;
	private ArrayList<String> optionsList;
	private int optionsCount = 0;
	
	public DdsOptions() {
		optionsList = new ArrayList<String>();
	}
	
	public DdsOptions(String propertyName) {
		this.propertyName = propertyName;
		optionsList = new ArrayList<String>();
	}
	
	public DdsOptions setPropertyName(String propertyName) {
		this.propertyName = propertyName;
		return this;
	}
	
	public String getPropertyName() {
		return propertyName;
	}
	
	public DdsOptions addOption(String option) {
		optionsList.add(option);
		optionsCount ++;
		return this;
	}
	
	public String getLast() {
		return optionsList.get(optionsCount-1);
	}
	
	public void appendToLast(String append) {
		optionsList.set(optionsCount-1, getLast() + append);
	}
	
	public ArrayList<String> getOptionsList(){
		return optionsList;
	}
}
