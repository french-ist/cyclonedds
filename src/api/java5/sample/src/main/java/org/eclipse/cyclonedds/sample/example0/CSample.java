package org.eclipse.cyclonedds.sample.example0;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.Structure.FieldOrder;
import com.sun.jna.ptr.IntByReference;

public interface CSample extends Library {
  CSample INSTANCE = (CSample) Native.load("sample", CSample.class);

  void display(String g);

  int printf(String format, Object... args); // From stdio

  // Equivalent JNA mapping of Pointer-to-Structure Arguments
  @FieldOrder({ "x", "y" })
  class Point extends Structure {
    public static class ByReference extends Point implements Structure.ByReference {
    }

    public int x;
    public int y;
  }

  Point oper_with_pointer_to_struct_args(Point pt, int x, int y);

  // Equivalent JNA mapping of Structure by Value Arguments/Return
  @FieldOrder({ "x", "y" })
  class Point_by_value extends Structure {
    public static class ByValue extends Point_by_value implements Structure.ByValue {
    }

    public int x, y;
  }

  Point_by_value.ByValue oper_with_by_value_args(Point_by_value.ByValue pt, int x, int y);

  // Equivalent JNA mapping of Array-of-Structure Arguments
  void get_points(Point[] out, int size);

  void set_points(Point[] out, Point[] in, int size);

  // Equivalent JNA mapping of Returning an Array of struct
  Point return_points(IntByReference pcount);

  void free_points(Point[] out);

   // Equivalent JNA mapping of nested structures
  @FieldOrder({ "start", "end" })
  class Line extends Structure {
    public Point start;
    public Point end;
  }

  Line oper_with_nested_structs(Line l, int dx, int dy);

   /* Does not work why ? - method below works  (use of pointer)
  @FieldOrder({ "start", "end" })
  class Line2 extends Structure {
  public static class ByReference extends Line2 implements
  Structure.ByReference {
  }

  public Point.ByReference start;
  public Point.ByReference end;
  }

  void oper_with_nested_structs(Line2.ByReference l, int dx, int dy); 
  */

  // Equivalent JNA mapping of nested structures :
  // The more general case is just a pointer to memory. This allows you to define
  // the field without necessarily defining the inner structure itself, similar to
  // declaring a struct without defining it in C
  @FieldOrder({ "p1", "p2" })
  class Line3 extends Structure {
    public Pointer p1;
    public Pointer p2;
  }

  void oper_with_nested_structs2(Line3 l, int dx, int dy);
}