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

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.omg.dds.core.policy.QosPolicy;
import org.eclipse.cyclonedds.core.ServiceEnvironmentImpl;

public class ViewKeysImpl extends QosPolicyImpl implements ViewKeys {
    private static final long serialVersionUID = -4786321587154092502L;
    private final HashSet<String> keyList;

    public ViewKeysImpl(ServiceEnvironmentImpl environment, String... keyValue) {
        super(environment);
        this.keyList = new HashSet<String>();

        for (String k : keyValue) {
            this.keyList.add(k);
        }
    }

    public ViewKeysImpl(ServiceEnvironmentImpl environment,
            Collection<String> keyValue) {
        super(environment);
        this.keyList = new HashSet<String>();

        if (keyValue != null) {
            for (String k : keyValue) {
                this.keyList.add(k);
            }
        }
    }

    @Override
    public Set<String> getKey() {
        return Collections.unmodifiableSet(this.keyList);
    }

    @Override
    public ViewKeys withKey(Collection<String> keyList) {
        return new ViewKeysImpl(this.environment, keyList);
    }

    @Override
    public ViewKeys withKey(String keyList) {
        return new ViewKeysImpl(this.environment, keyList);
    }

    @Override
    public ViewKeys withKey(String... keyList) {
        return new ViewKeysImpl(this.environment, keyList);
    }

    @Override
    public Class<? extends QosPolicy> getPolicyClass() {
        return ViewKeys.class;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ViewKeysImpl)) {
            return false;
        }
        ViewKeysImpl v = (ViewKeysImpl) other;

        if (v.keyList.size() != this.keyList.size()) {
            return false;
        }
        return v.keyList.containsAll(this.keyList);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 17;

        for (String key : this.keyList) {
            result = prime * result + key.hashCode();
        }
        return result;
    }
}
