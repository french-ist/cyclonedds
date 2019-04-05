/**
  Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 
  This program and the accompanying materials are made available under the
  terms of the Eclipse Public License v. 2.0 which is available at
  http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
  v. 1.0 which is available at
  http://www.eclipse.org/org/documents/edl-v10.php.
 
  SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.cyclonedds.examples.roundtrip;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
import org.eclipse.cyclonedds.ddsc.dds_public_impl.dds_sequence;

/**
 * Manually written
 */
public class RoundTripModule_DataType extends Structure {
    
    public dds_sequence payload;

	public RoundTripModule_DataType() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("payload");
	}
	/** @param message C type : char* */
	public RoundTripModule_DataType(dds_sequence payload) {
		super();
		this.payload = payload;
	}

	public void setPayload(dds_sequence payload) {
		this.payload = payload;
	}

	public RoundTripModule_DataType(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends RoundTripModule_DataType implements Structure.ByReference {
		
	};
	public static class ByValue extends RoundTripModule_DataType implements Structure.ByValue {
		
	};
}
