/**
  Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 
  This program and the accompanying materials are made available under the
  terms of the Eclipse Public License v. 2.0 which is available at
  http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
  v. 1.0 which is available at
  http://www.eclipse.org/org/documents/edl-v10.php.
 
  SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.cyclonedds.core;

import java.nio.ByteOrder;

import org.omg.dds.core.InstanceHandle;
import org.omg.dds.core.ServiceEnvironment;

public class InstanceHandleImpl extends InstanceHandle {
    private static final long serialVersionUID = 8433681503549822293L;
    
    private final transient CycloneServiceEnvironment environment;
    private long keyHash;
	private long keyHash1;
	private long keyHash2;
	private byte[] keyHashBytes;

    public InstanceHandleImpl(CycloneServiceEnvironment environment, long keyHashValue) {
        this.environment = environment;
        this.keyHash = keyHashValue;
    }

    public InstanceHandleImpl(byte[] keyHashBytes)
    {
    	environment = null;
    	this.keyHashBytes = keyHashBytes;
        keyHash1 = bytesToLong(keyHashBytes, 0, ByteOrder.BIG_ENDIAN);
        keyHash2 = bytesToLong(keyHashBytes, 8, ByteOrder.BIG_ENDIAN);
    }

	@Override
    public int compareTo(InstanceHandle o) {
        InstanceHandleImpl other = null;

        if (o == null) {
            return -1;
        }

        try {
            other = (InstanceHandleImpl)o;
        } catch(ClassCastException cce){
            throw new IllegalOperationExceptionImpl(this.environment,
                    "Cannot compare Cyclone InstanceHandle to non-Cyclone InstanceHandle");
        }
        if (this.keyHash == other.getValue()) {
            return 0;
        }

        if (this.keyHash < other.getValue()) {
            return -1;
        }
        return 1;
    }

    @Override
    public ServiceEnvironment getEnvironment() {
        return this.environment;
    }

    @Override
    public boolean isNil() {
        //TODO FRCYC return (this.value == HANDLE_NIL.value);
    	return false;
    }

    public long getValue(){
        return this.keyHash;
    }

    @Override
    public boolean equals(Object other){
        if(other instanceof InstanceHandleImpl){
            return (((InstanceHandleImpl)other).keyHash == this.keyHash);
        }
        return false;
    }

    @Override
    public String toString(){
        return "InstanceHandle (" + this.keyHash + ")";
    }

    @Override
    public int hashCode(){
        return 31 * 17 + (int) (this.keyHash ^ (this.keyHash >>> 32));
    }
    
    /**
     * Convert 8 bytes to an long.
     * 
     * @param bytes the byte array where to read 8 bytes
     * @param offset the index in bytes array where to read 8 bytes
     * @param endianness the endianness to use for conversion
     * @return the long
     */
    public static long bytesToLong(byte[] bytes, int offset, ByteOrder endianness)
    {
       if (bytes.length < offset + 8)
       {
          throw new IllegalArgumentException(
                "Cannot read 8 bytes at offset " + offset +
                      " in a " + bytes.length + " bytes length buffer");
       }

       if (endianness == ByteOrder.BIG_ENDIAN)
       {
          return ( ((long) 0xff & bytes[offset]) << 56)
                | ( ((long) 0xff & bytes[offset + 1]) << 48)
                | ( ((long) 0xff & bytes[offset + 2]) << 40)
                | ( ((long) 0xff & bytes[offset + 3]) << 32)
                | ( ((long) 0xff & bytes[offset + 4]) << 24)
                | ( ((long) 0xff & bytes[offset + 5]) << 16)
                | ( ((long) 0xff & bytes[offset + 6]) << 8)
                | ( ((long) 0xff & bytes[offset + 7]));
       }
       else
       {
          return ( ((long) 0xff & bytes[offset + 7]) << 56)
                | ( ((long) 0xff & bytes[offset + 6]) << 48)
                | ( ((long) 0xff & bytes[offset + 5]) << 40)
                | ( ((long) 0xff & bytes[offset + 4]) << 32)
                | ( ((long) 0xff & bytes[offset + 3]) << 24)
                | ( ((long) 0xff & bytes[offset + 2]) << 16)
                | ( ((long) 0xff & bytes[offset + 1]) << 8)
                | ( ((long) 0xff & bytes[offset]));
       }
    }

	public byte[] getKeyHash() {		
		return keyHashBytes;
	}
}
