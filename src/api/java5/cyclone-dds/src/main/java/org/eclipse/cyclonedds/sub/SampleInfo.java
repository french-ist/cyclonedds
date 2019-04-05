/**
  Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 
  This program and the accompanying materials are made available under the
  terms of the Eclipse Public License v. 2.0 which is available at
  http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
  v. 1.0 which is available at
  http://www.eclipse.org/org/documents/edl-v10.php.
 
  SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.cyclonedds.sub;

import org.omg.dds.core.Duration;

public class SampleInfo {

	public boolean valid_data;
	//TODO FRCYC
	public int sample_state;
	public int view_state;
	public int instance_state;
	public Duration source_timestamp;
	public long instance_handle;
	public long publication_handle;
	public int disposed_generation_count;
	public int no_writers_generation_count;
	public int sample_rank;
	public int generation_rank;
	public int absolute_generation_rank;
	public Object reception_timestamp;
}
