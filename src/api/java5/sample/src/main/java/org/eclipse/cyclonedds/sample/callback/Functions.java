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
