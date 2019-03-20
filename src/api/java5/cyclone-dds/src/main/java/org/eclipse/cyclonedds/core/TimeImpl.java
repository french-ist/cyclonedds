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
package org.eclipse.cyclonedds.core;

import java.util.concurrent.TimeUnit;

import org.omg.dds.core.Duration;
import org.omg.dds.core.Time;

public class TimeImpl extends ModifiableTimeImpl {
    private static final long serialVersionUID = 7478771004119429231L;

    public TimeImpl(CycloneServiceEnvironment environment, long duration,
            TimeUnit unit) {
        super(environment, duration, unit);
    }

    public TimeImpl(CycloneServiceEnvironment environment, long seconds,
            long nanoseconds) {
        super(environment, seconds, nanoseconds);
    }

    @Override
    public TimeImpl normalize() {
        ModifiableTimeImpl modTime = super.normalize();

        return (TimeImpl) modTime.immutableCopy();
    }

    @Override
    public void copyFrom(Time src) {
        throw new UnsupportedOperationExceptionImpl(this.environment,
                "Time is not modifiable.");
    }

    @Override
    public Time immutableCopy() {
        return this;
    }

    @Override
    public void setTime(long time, TimeUnit unit) {
        throw new UnsupportedOperationExceptionImpl(this.environment,
                "Time is not modifiable.");
    }

    @Override
    public void add(Duration duration) {
        throw new UnsupportedOperationExceptionImpl(this.environment,
                "Time is not modifiable.");
    }

    @Override
    public void add(long duration, TimeUnit unit) {
        throw new UnsupportedOperationExceptionImpl(this.environment,
                "Time is not modifiable.");
    }

    @Override
    public void subtract(Duration duration) {
        throw new UnsupportedOperationExceptionImpl(this.environment,
                "Time is not modifiable.");
    }

    @Override
    public void subtract(long duration, TimeUnit unit) {
        throw new UnsupportedOperationExceptionImpl(this.environment,
                "Time is not modifiable.");
    }
}
