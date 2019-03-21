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
package org.eclipse.cyclonedds.sample.callback;

import com.sun.jna.Callback;
import com.sun.jna.Structure;
import com.sun.jna.Structure.FieldOrder;

@FieldOrder({ Functions.VAL1, Functions.VAL2 })
public class Functions extends Structure {
  static final String VAL1 = "open";
  static final String VAL2 = "close";
  public static class ByValue extends Functions implements Structure.ByValue {
  }
  
  public interface OpenFuncInterface extends Callback {
    int invoke(String name, int options);
  }

  public interface CloseFuncInterface extends Callback {
    int invoke(int fd);
  }

  public OpenFuncInterface open;
  public CloseFuncInterface close;
}
