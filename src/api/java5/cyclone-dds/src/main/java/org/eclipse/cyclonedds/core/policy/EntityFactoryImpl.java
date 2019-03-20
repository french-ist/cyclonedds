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

import org.omg.dds.core.policy.EntityFactory;
import org.omg.dds.core.policy.QosPolicy;
import org.eclipse.cyclonedds.core.CycloneServiceEnvironment;

public class EntityFactoryImpl extends QosPolicyImpl implements EntityFactory {
    private static final long serialVersionUID = -775163554877034009L;
    private final boolean autoEnableCreatedEntities;

    public EntityFactoryImpl(CycloneServiceEnvironment environment) {
        super(environment);
        this.autoEnableCreatedEntities = true;
    }

    public EntityFactoryImpl(CycloneServiceEnvironment environment,
            boolean autoEnableCreatedEntities) {
        super(environment);
        this.autoEnableCreatedEntities = autoEnableCreatedEntities;
    }

    @Override
    public boolean isAutoEnableCreatedEntities() {
        return this.autoEnableCreatedEntities;
    }

    @Override
    public EntityFactory withAutoEnableCreatedEntities(
            boolean autoEnableCreatedEntities) {
        return new EntityFactoryImpl(this.environment,
                autoEnableCreatedEntities);
    }

    @Override
    public String toString(){
        return "autoEnableCreatedEntities = " + 
                (this.autoEnableCreatedEntities==true?"true":"false");
    }

    @Override
    public Class<? extends QosPolicy> getPolicyClass() {
        return EntityFactory.class;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof EntityFactoryImpl)) {
            return false;
        }
        return (this.autoEnableCreatedEntities == ((EntityFactoryImpl) other).autoEnableCreatedEntities);
    }

    @Override
    public int hashCode() {
        return 31 * 17 + (this.autoEnableCreatedEntities ? 1 : 0);
    }
}
