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
