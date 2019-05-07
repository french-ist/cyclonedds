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

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import org.omg.dds.core.policy.DataRepresentation;
import org.omg.dds.core.policy.QosPolicy;
import org.eclipse.cyclonedds.core.IllegalArgumentExceptionImpl;
import org.eclipse.cyclonedds.core.ServiceEnvironmentImpl;

public class DataRepresentationImpl extends QosPolicyImpl implements
        DataRepresentation {
    private static final long serialVersionUID = -3169717693569991730L;
    private final ArrayList<Short> value;

    public DataRepresentationImpl(ServiceEnvironmentImpl environment) {
        super(environment);
        this.value = new ArrayList<Short>();
    }

    public DataRepresentationImpl(ServiceEnvironmentImpl environment,
            List<Short> value) {
        super(environment);

        if (value == null) {
            throw new IllegalArgumentExceptionImpl(environment,
                    "Supplied invalid list of values.");
        }
        this.value = new ArrayList<Short>(value);
    }

    public DataRepresentationImpl(ServiceEnvironmentImpl environment,
            short... value) {
        super(environment);
        this.value = new ArrayList<Short>(value.length);

        for (short val : value) {
            this.value.add(val);
        }
    }

    @Override
    public Comparable<DataRepresentation> requestedOfferedContract() {
        return this;
    }

    @Override
    public int compareTo(DataRepresentation arg0) {
        if (arg0 == null) {
            return 1;
        }
        return this.value.hashCode() - arg0.getValue().hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof DataRepresentationImpl)) {
            return false;
        }
        DataRepresentationImpl d = (DataRepresentationImpl) other;

        if (d.value.size() != this.value.size()) {
            return false;
        }
        for (int i = 0; i < this.value.size(); i++) {
            if (!(this.value.get(i).equals(d.value.get(i)))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 17;

        for (short s : this.value) {
            result = prime * result + s;
        }
        return result;
    }

    @Override
    public List<Short> getValue() {
        return Collections.unmodifiableList(this.value);
    }

    @Override
    public DataRepresentation withValue(List<Short> value) {
        return new DataRepresentationImpl(this.environment, value);
    }

    @Override
    public DataRepresentation withValue(short... value) {
        return new DataRepresentationImpl(this.environment, value);
    }

    @Override
    public Class<? extends QosPolicy> getPolicyClass() {
        return DataRepresentation.class;
    }

}
