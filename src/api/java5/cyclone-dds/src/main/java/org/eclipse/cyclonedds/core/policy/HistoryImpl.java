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

import org.omg.dds.core.policy.History;
import org.omg.dds.core.policy.QosPolicy;
import org.eclipse.cyclonedds.core.IllegalArgumentExceptionImpl;
import org.eclipse.cyclonedds.core.CycloneServiceEnvironment;

public class HistoryImpl extends QosPolicyImpl implements History{
    private static final long serialVersionUID = 8740123506122702059L;
    private final Kind kind;
    private final int depth;

    public HistoryImpl(CycloneServiceEnvironment environment) {
        super(environment);
        this.kind = Kind.KEEP_LAST;
        this.depth = 1;
    }

    public HistoryImpl(CycloneServiceEnvironment environment, Kind kind, int depth) {
        super(environment);

        if (kind == null) {
            throw new IllegalArgumentExceptionImpl(environment,
                    "Supplied invalid kind.");
        }
        this.kind = kind;
        this.depth = depth;
    }

    @Override
    public Kind getKind() {
        return this.kind;
    }

    @Override
    public int getDepth() {
        return this.depth;
    }

    @Override
    public History withKind(Kind kind) {
        return new HistoryImpl(this.environment, kind, this.depth);
    }

    @Override
    public History withDepth(int depth) {
        return new HistoryImpl(this.environment, this.kind, depth);

    }

    @Override
    public History withKeepAll() {
        return new HistoryImpl(this.environment, Kind.KEEP_ALL, this.depth);

    }

    @Override
    public History withKeepLast(int depth) {
        return new HistoryImpl(this.environment, Kind.KEEP_LAST, depth);

    }

    @Override
    public Class<? extends QosPolicy> getPolicyClass() {
        return History.class;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof HistoryImpl)) {
            return false;
        }
        HistoryImpl h = (HistoryImpl) other;

        if (this.kind != h.kind) {
            return false;
        }
        return (this.depth == h.depth);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 17;

        result = prime * result + this.kind.hashCode();
        result = prime * result + this.depth;

        return result;
    }
}
