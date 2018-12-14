package org.eclipse.cyclonedds.ddsc.dds__types;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : _dds__types.h:214</i><br>
 */
public class dds_iid extends Structure {
	public long counter;
	public long getCounter() {
		return counter;
	}
	public void setCounter(long counter) {
		this.counter = counter;
	}
	/** C type : uint32_t[4] */
	public int[] key = new int[4];
	public int[] getKey() {
		return key;
	}
	public void setKey(int key[]) {
		this.key = key;
	}
	public dds_iid() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("counter", "key");
	}
	/** @param key C type : uint32_t[4] */
	public dds_iid(long counter, int key[]) {
		super();
		this.counter = counter;
		if ((key.length != this.key.length)) 
			throw new IllegalArgumentException("Wrong array size !");
		this.key = key;
	}
	public dds_iid(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends dds_iid implements Structure.ByReference {
		
	};
	public static class ByValue extends dds_iid implements Structure.ByValue {
		
	};
}
