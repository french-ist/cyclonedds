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
import org.omg.dds.core.ServiceEnvironment;

public class DurationImpl extends Duration {
    private static final long serialVersionUID = -3177410803404504645L;
    private final transient CycloneServiceEnvironment environment;
    private final long seconds;
    private final long nanoseconds;
    private final long totalNanos;
    public final static int INFINITE_SECONDS = Integer.MAX_VALUE;//TODO FRCYC DURATION_INFINITE_SEC.value;
    public final static int INFINITE_NANOSECONDS = Integer.MAX_VALUE;//TODO FRCYC DURATION_INFINITE_NSEC.value;

    public DurationImpl(CycloneServiceEnvironment environment, long duration,
            TimeUnit unit) {
        super();
        this.environment = environment;

        if (unit == null) {
            throw new IllegalArgumentExceptionImpl(environment,
                    "Invalid TimeUnit (null) provided.");
        }

        if (duration == Long.MAX_VALUE) {
            this.seconds = INFINITE_SECONDS;
            this.nanoseconds = INFINITE_NANOSECONDS;
            this.totalNanos = Long.MAX_VALUE;
        } else {
            this.seconds = TimeUnit.SECONDS.convert(duration, unit);
            this.totalNanos = TimeUnit.NANOSECONDS.convert(duration, unit);
            this.nanoseconds = this.totalNanos
                    - (this.seconds * 1000 * 1000 * 1000);
        }
    }

    public DurationImpl(CycloneServiceEnvironment environment, long seconds,
            long nanoseconds) {
        super();
        this.environment = environment;
        this.seconds = seconds;
        this.nanoseconds = nanoseconds;

        if (this.seconds == INFINITE_SECONDS
                && this.nanoseconds == INFINITE_NANOSECONDS) {
            this.totalNanos = Long.MAX_VALUE;
        } else {
            this.totalNanos = this.seconds * 1000 * 1000 * 1000
                    + this.nanoseconds;
        }
    }

    public DurationImpl normalize() {
        long sec = this.seconds;
        long nsec = this.nanoseconds;

        if (sec == INFINITE_SECONDS) {
            return new DurationImpl(this.environment, INFINITE_SECONDS,
                    INFINITE_NANOSECONDS);
        }
        while (nsec >= 1000000000) {
            sec += 1;
            nsec -= 1000000000;

            if (sec == INFINITE_SECONDS) {
                return new DurationImpl(this.environment, INFINITE_SECONDS,
                        INFINITE_NANOSECONDS);
            }
        }
        return new DurationImpl(this.environment, sec, nsec);
    }

    @Override
    public int compareTo(Duration o) {
        if (o == null) {
            return 1;
        }
        if (this.isInfinite() && o.isInfinite()) {
            return 0;
        }
        long sec = o.getDuration(TimeUnit.SECONDS);
        long nsec = o.getRemainder(TimeUnit.SECONDS, TimeUnit.NANOSECONDS);

        if ((this.nanoseconds >= 1000000000)
                && ((this.nanoseconds != INFINITE_NANOSECONDS))) {
            throw new IllegalArgumentExceptionImpl(this.environment,"Illegal duration "
                    + this.seconds + " seconds, " + this.nanoseconds
                    + " nanoseconds.");
        }
        if ((nsec >= 1000000000)
                && ((nsec != Long.MAX_VALUE) || (sec != Long.MAX_VALUE))) {
            throw new IllegalArgumentExceptionImpl(this.environment,"Illegal duration " + sec
                    + " seconds, " + nsec + " nanoseconds.");
        }
        if (this.seconds > sec)
            return 1;
        if (this.seconds < sec)
            return -1;
        if (this.nanoseconds > nsec)
            return 1;
        if (this.nanoseconds < nsec)
            return -1;
        return 0;
    }

    @Override
    public ServiceEnvironment getEnvironment() {
        return this.environment;
    }

    @Override
    public long getDuration(TimeUnit inThisUnit) {
        if (inThisUnit == null) {
            throw new IllegalArgumentException(
                    "Invalid TimeUnit (null) provided.");
        }
        if (this.isInfinite()) {
            return Long.MAX_VALUE;
        }
        return inThisUnit.convert(this.totalNanos, TimeUnit.NANOSECONDS);
    }

    @Override
    public long getRemainder(TimeUnit primaryUnit, TimeUnit remainderUnit) {
        if ((primaryUnit == null) || (remainderUnit == null)) {
            throw new IllegalArgumentException(
                    "Invalid TimeUnit (null) provided.");
        }
        if (remainderUnit.compareTo(primaryUnit) >= 0) {
            return 0;
        }
        if (this.isInfinite()) {
            return Long.MAX_VALUE;
        }
        long primaryDuration = primaryUnit.convert(this.totalNanos,
                TimeUnit.NANOSECONDS);
        long primaryNanoDuration = TimeUnit.NANOSECONDS.convert(
                primaryDuration, primaryUnit);
        long leftOverNanos = this.totalNanos - primaryNanoDuration;

        return remainderUnit.convert(leftOverNanos, TimeUnit.NANOSECONDS);
    }

    @Override
    public boolean isZero() {
        return (this.seconds == 0 && this.nanoseconds == 0) ? true : false;
    }

    @Override
    public boolean isInfinite() {
        return (this.totalNanos == Long.MAX_VALUE) ? true
                : false;
    }

    @Override
    public Duration add(Duration duration) {
        if (duration == null) {
            throw new IllegalArgumentException(
                    "Invalid Duration (null) provided.");
        }
        long sec = duration.getDuration(TimeUnit.SECONDS);
        long nsec = duration.getRemainder(TimeUnit.SECONDS,
                TimeUnit.NANOSECONDS);

        if (!((this.nanoseconds < 1000000000) || (this.nanoseconds == INFINITE_NANOSECONDS))) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Illegal duration");
        }
        if (!((nsec < 1000000000) || (nsec == Long.MAX_VALUE))) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Illegal duration");
        }
        if (this.nanoseconds == INFINITE_NANOSECONDS) {
            if (this.seconds == INFINITE_SECONDS) {
                return new DurationImpl(this.environment, INFINITE_SECONDS,
                        INFINITE_NANOSECONDS); /* result stays infinite. */
            }
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Illegal duration " + this.seconds + " seconds, "
                            + this.nanoseconds + " nanoseconds.");
        }
        if (nsec == Long.MAX_VALUE) {
            if (sec == Long.MAX_VALUE) {
                return new DurationImpl(this.environment, INFINITE_SECONDS,
                        INFINITE_NANOSECONDS); /* result stays infinite. */
            }
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Illegal duration " + sec + " seconds, " + nsec
                            + " nanoseconds.");

        }
        if (sec > 0) {
            if (INFINITE_SECONDS - sec <= this.seconds) {
                /*
                 * is identical to 'INFINITE_SECONDS <= this.seconds + sec' In
                 * other words the sum is larger than infinite, so results must
                 * be infinite.
                 */
                return new DurationImpl(this.environment, INFINITE_SECONDS,
                        INFINITE_NANOSECONDS);
            }
        }
        long s = this.seconds + sec;
        long ns = this.nanoseconds + nsec;
        return new DurationImpl(this.environment, s, ns).normalize();

    }

    @Override
    public Duration add(long duration, TimeUnit unit) {
        DurationImpl temp = new DurationImpl(this.environment, duration, unit)
                .normalize();

        return this.add(temp);
    }

    @Override
    public Duration subtract(Duration duration) {
        if (duration == null) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Invalid Duration (null) provided.");
        }
        if(this.isInfinite() && duration.isInfinite()){
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Cannot subtract two infinite times.");
        }
        long sec = duration.getDuration(TimeUnit.SECONDS);
        long nsec = duration.getRemainder(TimeUnit.SECONDS,
                TimeUnit.NANOSECONDS);


        if (!(this.nanoseconds < 1000000000)
                || (this.nanoseconds == INFINITE_NANOSECONDS)) {
            throw new IllegalArgumentExceptionImpl(this.environment,"Illegal duration "
                    + this.seconds + " seconds, " + this.nanoseconds
                    + " nanoseconds.");
        }
        if (!((nsec < 1000000000) || (nsec == Long.MAX_VALUE))) {
            throw new IllegalArgumentExceptionImpl(this.environment,"Illegal duration " + sec
                    + " seconds, " + nsec + " nanoseconds.");
        }

        if (this.nanoseconds == INFINITE_NANOSECONDS) {
            if (this.seconds == INFINITE_SECONDS) {
                return new DurationImpl(this.environment, INFINITE_SECONDS,
                        INFINITE_NANOSECONDS); /* result stays infinite. */
            }
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Illegal duration " + this.seconds + " seconds, "
                            + this.nanoseconds + " nanoseconds.");
        }
        if (nsec == Long.MAX_VALUE) {
            if (sec == Long.MAX_VALUE) {
                return new DurationImpl(this.environment, INFINITE_SECONDS,
                        INFINITE_NANOSECONDS); /* result stays infinite. */
            }
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Illegal duration " + sec + " seconds, " + nsec
                            + " nanoseconds.");
        }
        if ((sec <= 0) && (INFINITE_SECONDS + sec <= this.seconds)) {
            /* is identical to '-(INFINITE_SECONDS >= this.seconds - sec)' In
             * other words the sum is larger than infinite, so results must be
             * infinite. */
            return new DurationImpl(this.environment, INFINITE_SECONDS,
                    INFINITE_NANOSECONDS);
        }
        long s = this.seconds - sec;

        if (s == INFINITE_SECONDS) {
            return new DurationImpl(this.environment, INFINITE_SECONDS,
                    INFINITE_NANOSECONDS);
        }
        long ns = this.nanoseconds - nsec;

        if (ns < 0) {
            s--;
            ns += 1000000000;
        }
        return new DurationImpl(this.environment, s, ns).normalize();

    }

    @Override
    public Duration subtract(long duration, TimeUnit unit) {
        DurationImpl temp = new DurationImpl(this.environment, duration, unit)
                .normalize();

        return this.subtract(temp);
    }

    /*TODO FRCYC
    public Duration_t convert() {
        return new Duration_t((int) this.seconds, (int) this.nanoseconds);
    }*/

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof DurationImpl)) {
            return false;
        }
        DurationImpl d = (DurationImpl) other;

        if (d.totalNanos != this.totalNanos) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return 31 * 17 + (int) (this.totalNanos ^ (this.totalNanos >>> 32));
    }

    @Override
    public String toString(){
        if(this.isInfinite()){
            return "infinite";
        }
        return this.totalNanos + " ns";
    }
}
