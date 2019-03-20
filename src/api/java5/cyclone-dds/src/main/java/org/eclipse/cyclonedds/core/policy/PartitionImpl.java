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
package org.eclipse.cyclonedds.core.policy;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.omg.dds.core.policy.Partition;
import org.omg.dds.core.policy.QosPolicy;
import org.eclipse.cyclonedds.core.IllegalArgumentExceptionImpl;
import org.eclipse.cyclonedds.core.CycloneServiceEnvironment;

public class PartitionImpl extends QosPolicyImpl implements Partition {
    private static final long serialVersionUID = 3060990234546666051L;
    private final HashSet<String> name;

    public PartitionImpl(CycloneServiceEnvironment environment) {
        super(environment);
        this.name = new HashSet<String>();
        this.name.add("");
    }

    public PartitionImpl(CycloneServiceEnvironment environment,
            Collection<String> name) {
        super(environment);

        if (name == null) {
            throw new IllegalArgumentExceptionImpl(environment,
                    "Supplied name is invalid.");
        }
        this.name = new HashSet<String>(name);

        if(this.name.size() == 0){
            this.name.add("");
        }
    }

    public PartitionImpl(CycloneServiceEnvironment environment,
            String... name) {
        super(environment);
        this.name = new HashSet<String>();

        for(String partition: name){
            this.name.add(partition);
        }
        if(this.name.size() == 0){
            this.name.add("");
        }
    }

    @Override
    public Set<String> getName() {
        return Collections.unmodifiableSet(this.name);
    }

    @Override
    public Partition withName(Collection<String> name) {
        return new PartitionImpl(this.environment, name);
    }

    @Override
    public Partition withName(String... names) {
        return new PartitionImpl(this.environment, names);
    }

    @Override
    public Class<? extends QosPolicy> getPolicyClass() {
        return Partition.class;
    }

    @Override
    public Partition withName(String name) {
        return new PartitionImpl(this.environment, name);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof PartitionImpl)) {
            return false;
        }
        PartitionImpl p = (PartitionImpl) other;

        if (this.name.size() != p.name.size()) {
            return false;
        }
        if (!this.name.containsAll(p.name)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 17;

        for (String name : this.name) {
            result = prime * result + name.hashCode();
        }
        return result;
    }
}
