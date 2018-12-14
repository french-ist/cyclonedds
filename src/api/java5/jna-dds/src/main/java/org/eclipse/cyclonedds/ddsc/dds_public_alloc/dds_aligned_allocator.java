package org.eclipse.cyclonedds.ddsc.dds_public_alloc;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Callback;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : _dds_public_alloc.h:77</i><br>
 */
public class dds_aligned_allocator extends Structure {
	/** C type : alloc_callback* */
	public dds_aligned_allocator.alloc_callback alloc;
	public dds_aligned_allocator.alloc_callback getAlloc() {
		return alloc;
	}
	public void setAlloc(dds_aligned_allocator.alloc_callback alloc) {
		this.alloc = alloc;
	}
	/** C type : free_callback* */
	public dds_aligned_allocator.free_callback free;
	public dds_aligned_allocator.free_callback getFree() {
		return free;
	}
	public void setFree(dds_aligned_allocator.free_callback free) {
		this.free = free;
	}
	/** <i>native declaration : _dds_public_alloc.h:75</i> */
	public interface alloc_callback extends Callback {
		Pointer apply(NativeSize size, NativeSize align);
	};
	/** <i>native declaration : _dds_public_alloc.h:76</i> */
	public interface free_callback extends Callback {
		void apply(NativeSize size, Pointer ptr);
	};
	public dds_aligned_allocator() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("alloc", "free");
	}
	/**
	 * @param alloc C type : alloc_callback*<br>
	 * @param free C type : free_callback*
	 */
	public dds_aligned_allocator(dds_aligned_allocator.alloc_callback alloc, dds_aligned_allocator.free_callback free) {
		super();
		this.alloc = alloc;
		this.free = free;
	}
	public dds_aligned_allocator(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends dds_aligned_allocator implements Structure.ByReference {
		
	};
	public static class ByValue extends dds_aligned_allocator implements Structure.ByValue {
		
	};
}
