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

class stats {

    public String name;
    public long average;
    public long min;
    public long max;
    public int count;

    public stats (
        String stats_name
        )
    {
	name = stats_name;
        this.init_stats ();
    }

    public void
    add_stats (
        long data
        )
    {
        average = (count * average + data)/(count + 1);
        min     = (count == 0 || data < min) ? data : min;
        max     = (count == 0 || data > max) ? data : max;
        count++;
    }

    public void
    init_stats (
	)
    {
        count   = 0;
        average = 0L;
        min     = 0L;
        max     = 0L; 
    }

}
