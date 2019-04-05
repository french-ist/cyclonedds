/**
  Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 
  This program and the accompanying materials are made available under the
  terms of the Eclipse Public License v. 2.0 which is available at
  http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
  v. 1.0 which is available at
  http://www.eclipse.org/org/documents/edl-v10.php.
 
  SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */

class time {

    public long _time;

    public time (
	)
    {
	_time = 0L;
    }

    public time (
	long t
	)
    {
	_time = t;
    }

    public void timeGet (
	)
    {
	_time = java.lang.System.nanoTime()/1000L;
    }

    public long get (
	)
    {
	return _time;
    }

    public void set (
	long t
	)
    {
	_time = t;
    }

    public long sub (
        time t
	)
    {
	long nt = _time - t.get();

	return nt;
    }

}
