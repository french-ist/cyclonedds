/**
  Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 
  This program and the accompanying materials are made available under the
  terms of the Eclipse Public License v. 2.0 which is available at
  http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
  v. 1.0 which is available at
  http://www.eclipse.org/org/documents/edl-v10.php.
 
  SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.cyclonedds.topic;

import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.topic.BuiltinTopicKey;
import org.eclipse.cyclonedds.core.IllegalArgumentExceptionImpl;
import org.eclipse.cyclonedds.core.ServiceEnvironmentImpl;

public class BuiltinTopicKeyImpl implements BuiltinTopicKey {
    private static final long serialVersionUID = 4755982116057745495L;
    private final transient ServiceEnvironmentImpl environment;
    private int[] value;

    public BuiltinTopicKeyImpl(ServiceEnvironmentImpl environment, int[] value) {
        this.environment = environment;

        if (value == null) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Invalid BuiltinTopicKey provided.");
        }
        this.value = new int[value.length];
        System.arraycopy(value, 0, this.value, 0, value.length);
    }

    @Override
    public ServiceEnvironment getEnvironment() {
        return this.environment;
    }

    @Override
    public int[] getValue() {
        int[] result = new int[this.value.length];
        System.arraycopy(this.value, 0, result, 0, this.value.length);
        return result;
    }

    @Override
    public void copyFrom(BuiltinTopicKey src) {
        if (src == null) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Illegal BuiltinTopicKey (null) provided.");
        }
        this.value = src.getValue();
    }

    @Override
    public BuiltinTopicKey clone() {
        return new BuiltinTopicKeyImpl(this.environment, this.value);
    }
}
