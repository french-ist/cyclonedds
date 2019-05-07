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

import org.omg.dds.core.AlreadyClosedException;
import org.omg.dds.core.ServiceEnvironment;

public class AlreadyClosedExceptionImpl extends AlreadyClosedException {
    private static final long serialVersionUID = 5229558243541096106L;
    private ServiceEnvironmentImpl environment;

    public AlreadyClosedExceptionImpl(ServiceEnvironmentImpl environment,
            String message) {
        super(message);
        this.environment = environment;
    }

    @Override
    public ServiceEnvironment getEnvironment() {
        return this.environment;
    }

    @Override
    public void printStackTrace() {
        System.err.println(this.toString());
    }

    @Override
    public String toString() {
        return Utilities.getOsplExceptionStack(this, this.getStackTrace());
    }
}