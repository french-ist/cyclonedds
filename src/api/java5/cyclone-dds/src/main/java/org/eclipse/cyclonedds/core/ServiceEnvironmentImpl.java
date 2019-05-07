/**
  Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 
  This program and the accompanying materials are made available under the
  terms of the Eclipse Public License v. 2.0 which is available at
  http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
  v. 1.0 which is available at
  http://www.eclipse.org/org/documents/edl-v10.php.
 
  SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.cyclonedds.core;

import org.omg.dds.core.ServiceEnvironment;

public class ServiceEnvironmentImpl extends ServiceEnvironment {
    
	private final org.eclipse.cyclonedds.core.ServiceProviderInterfaceImpl cycJnaSpi;
    
	public ServiceEnvironmentImpl() {
        this.cycJnaSpi = new org.eclipse.cyclonedds.core.ServiceProviderInterfaceImpl(this);
    }

    @Override
    public ServiceProviderInterface getSPI() {
        return cycJnaSpi;
    }
}
