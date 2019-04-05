/**
  Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 
  This program and the accompanying materials are made available under the
  terms of the Eclipse Public License v. 2.0 which is available at
  http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
  v. 1.0 which is available at
  http://www.eclipse.org/org/documents/edl-v10.php.
 
  SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.cyclonedds.core.policy;

import org.omg.dds.core.ServiceEnvironment;
import org.eclipse.cyclonedds.core.DDSExceptionImpl;
import org.eclipse.cyclonedds.core.CycloneServiceEnvironment;

public abstract class QosPolicyImpl implements QosPolicy {
	private static final long serialVersionUID = -1576883735576551398L;
	protected CycloneServiceEnvironment environment;

	public QosPolicyImpl(CycloneServiceEnvironment environment){
		this.environment = environment;

        if (this.environment == null) {
            throw new DDSExceptionImpl(null, "Supplied Environment is invalid.");
        }
	}

	@Override
	public ServiceEnvironment getEnvironment() {
		return this.environment;
	}

    public abstract Class<? extends org.omg.dds.core.policy.QosPolicy> getPolicyClass();

    @Override
    public abstract boolean equals(Object other);

    @Override
    public abstract int hashCode();

}
