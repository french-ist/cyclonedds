package org.eclipse.cyclonedds.ddsc.dds__entity;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
/**
 * JNA Wrapper for library <b>ddsc</b><br>
 */
public class DdscLibrary implements Library {
	public static final String JNA_LIBRARY_NAME = "ddsc";
	public static final NativeLibrary JNA_NATIVE_LIB = NativeLibrary.getInstance(DdscLibrary.JNA_LIBRARY_NAME);
	static {
		Native.register(DdscLibrary.class, DdscLibrary.JNA_NATIVE_LIB);
	}
	public static final int DDS_INTERNAL_STATUS_MASK = (int)(0xFF000000);
	public static final int DDS_WAITSET_TRIGGER_STATUS = (int)(0x01000000);
	public static final int DDS_DELETING_STATUS = (int)(0x02000000);
	public static final int DDS_HEADBANG_TIMEOUT_MS = (int)(10);
	public static final int DDS_ENTITY_ENABLED = (int)0x0001;
	public static final int DDS_ENTITY_IMPLICIT = (int)0x0002;
}
