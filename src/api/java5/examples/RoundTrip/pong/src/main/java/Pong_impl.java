/**
  Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 
  This program and the accompanying materials are made available under the
  terms of the Eclipse Public License v. 2.0 which is available at
  http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
  v. 1.0 which is available at
  http://www.eclipse.org/org/documents/edl-v10.php.
 
  SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */

import java.util.ArrayList;
import java.util.List;
import org.omg.dds.core.Duration;
import org.omg.dds.sub.InstanceState;
import org.omg.dds.sub.Sample;

/**
 * The RoundTrip example consists of a Ping and a Pong application. Ping sends
 * sample to Pong by writing to the Ping partition which Pong subscribes to.
 * Pong them sends them back to Ping by writing on the Pong partition which Ping
 * subscribes to. Ping measure the amount of time taken to write and read each
 * sample as well as the total round trip time to send a sample to Pong and
 * receive it back.
 * 
 * This class performs the Pong role in this example.
 */
class Pong_impl {

	/**
	 * Performs the Pong role in this example.
	 */
	public void run(String args[]) {
		try {

			/*
			 * Initialize entities
			 */
			final Entities e = new Entities("pong", "ping");

			Runtime.getRuntime().addShutdownHook(new Thread() {
				public void run() {
					e.terminated.setTriggerValue(true);
				}
			});

			System.out.println("Waiting for samples from ping to send back...");

			Duration waitTimeout = e.env.getSPI().infiniteDuration();

			List<Sample<RoundTripModule.DataType>> samples = new ArrayList<Sample<RoundTripModule.DataType>>(
					10);

			while (!e.terminated.getTriggerValue()) {
				/*
				 * Wait for a sample from ping
				 */
				e.waitSet.waitForConditions(waitTimeout);
				/*
				 * Take sample
				 */
				e.reader.take(samples);

				for (int i = 0; !e.terminated.getTriggerValue()
						&& i < samples.size(); i++) {
					/*
					 * If writer has been disposed terminate pong
					 */
					Sample<RoundTripModule.DataType> sample = samples.get(i);
					if (sample.getInstanceState() == InstanceState.NOT_ALIVE_DISPOSED) {
						System.out
								.println("Received termination request. Terminating.");
						e.terminated.setTriggerValue(true);
						break;
					}
					/*
					 * If sample is valid, send it back to ping
					 */
					else if (sample.getData() != null) {
						e.writer.write(sample.getData());
					}

				}
			}
			e.participant.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
