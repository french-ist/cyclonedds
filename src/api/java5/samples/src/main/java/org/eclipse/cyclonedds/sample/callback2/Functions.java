package org.eclipse.cyclonedds.sample.callback2;

import com.sun.jna.Callback;
import com.sun.jna.Structure;
import com.sun.jna.Structure.FieldOrder;

// If your native code initializes function pointers within a struct.
// JNA will automatically generate a Callback instance matching the declared type.
// This enables you to easily call the function supplied by native code using proper Java syntax.

@FieldOrder({ Functions.VAL1, Functions.VAL2 })
public class Functions extends Structure {
  static final String VAL1 = "open";
  static final String VAL2 = "close";
  
  public static class ByReference extends Functions implements Structure.ByReference {
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