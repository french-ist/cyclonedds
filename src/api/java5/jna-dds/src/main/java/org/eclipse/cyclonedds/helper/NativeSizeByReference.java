/**
 * Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
 * v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.cyclonedds.helper;

import com.sun.jna.ptr.ByReference;

public class NativeSizeByReference extends ByReference {
    public NativeSizeByReference() {
        this(new NativeSize(0));
    }

    public NativeSizeByReference(NativeSize value) {
        super(NativeSize.SIZE);
        setValue(value);
    }

    public void setValue(NativeSize value) {
        if (NativeSize.SIZE == 4)
			getPointer().setInt(0, value.intValue());
		else if (NativeSize.SIZE == 8)
			getPointer().setLong(0, value.longValue());
		else
			throw new RuntimeException("GCCLong has to be either 4 or 8 bytes.");
    }

    public NativeSize getValue() {
		if (NativeSize.SIZE == 4)
			return new NativeSize(getPointer().getInt(0));
		else if (NativeSize.SIZE == 8)
			return new NativeSize(getPointer().getLong(0));
		else
			throw new RuntimeException("GCCLong has to be either 4 or 8 bytes.");
    }
}
