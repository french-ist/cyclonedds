package org.eclipse.cyclonedds.ddsc.dds_public_alloc;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Callback;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : _dds_public_alloc.h:69</i><br>
 */
public class dds_allocator extends Structure {
	/** C type : malloc_callback* */
	public dds_allocator.malloc_callback malloc;
	public dds_allocator.malloc_callback getMalloc() {
		return malloc;
	}
	public void setMalloc(dds_allocator.malloc_callback malloc) {
		this.malloc = malloc;
	}
	/**
	 * if needed<br>
	 * C type : realloc_callback*
	 */
	public dds_allocator.realloc_callback realloc;
	public dds_allocator.realloc_callback getRealloc() {
		return realloc;
	}
	public void setRealloc(dds_allocator.realloc_callback realloc) {
		this.realloc = realloc;
	}
	/** C type : free_callback* */
	public org.eclipse.cyclonedds.ddsc.dds_public_alloc.dds_aligned_allocator.free_callback free;
	public org.eclipse.cyclonedds.ddsc.dds_public_alloc.dds_aligned_allocator.free_callback getFree() {
		return free;
	}
	public void setFree(org.eclipse.cyclonedds.ddsc.dds_public_alloc.dds_aligned_allocator.free_callback free) {
		this.free = free;
	}
	/** <i>native declaration : _dds_public_alloc.h:66</i> */
	public interface malloc_callback extends Callback {
		Pointer apply(NativeSize size);
	};
	/** <i>native declaration : _dds_public_alloc.h:67</i> */
	public interface realloc_callback extends Callback {
		Pointer apply(Pointer ptr, NativeSize size);
	};
	/** <i>native declaration : _dds_public_alloc.h:68</i> */
	public interface free_callback extends Callback {
		void apply(Pointer ptr);
	};
	public dds_allocator() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("malloc", "realloc", "free");
	}
	/**
	 * @param malloc C type : malloc_callback*<br>
	 * @param realloc if needed<br>
	 * C type : realloc_callback*<br>
	 * @param free C type : free_callback*
	 */
	public dds_allocator(dds_allocator.malloc_callback malloc, dds_allocator.realloc_callback realloc, org.eclipse.cyclonedds.ddsc.dds_public_alloc.dds_aligned_allocator.free_callback free) {
		super();
		this.malloc = malloc;
		this.realloc = realloc;
		this.free = free;
	}
	public dds_allocator(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends dds_allocator implements Structure.ByReference {
		
	};
	public static class ByValue extends dds_allocator implements Structure.ByValue {
		
	};
}
