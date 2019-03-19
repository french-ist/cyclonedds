package org.eclipse.cyclonedds.core;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

public interface JnaData {
	public Structure.ByReference getStructureReference();
	public Structure getNewStructureFrom(Pointer peer);
}
