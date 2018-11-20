package org.eclipse.cyclonedds.sample.example0;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.ptr.IntByReference;

// cf.http://java-native-access.github.io/jna/5.0.0/javadoc/overview-summary.html#marshalling
public class Launcher {
    public interface CLibrary extends Library {
        CLibrary INSTANCE = (CLibrary) Native.load((Platform.isWindows() ? "msvcrt" : "c"), CLibrary.class);

        void printf(String format, Object... args);
    }

    public static void main(String[] args) {
        CLibrary.INSTANCE.printf("Using CLibrary : Hello, World\n");
        for (int i = 0; i < args.length; i++) {
            CLibrary.INSTANCE.printf("Argument %d: %s\n", i, args[i]);
        }
        CSample.INSTANCE.display("Bonjour tout le monde\n");
        CSample.INSTANCE.printf("J'ai %d chats\n", 2);

        // Structure Pointer-to-Structure Arguments
        CSample.Point pt = new CSample.Point();
        CSample.Point result = CSample.INSTANCE.oper_with_pointer_to_struct_args(pt, 100, 100);
        System.out.println("[JAVA] Pointer-to-Structure : pt.x = " + pt.x + " - pt.y = " + pt.y);
        System.out.println("[JAVA] Pointer-to-Structure : result.x = " + result.x + " - result.y = " + result.y);

        // Structure by Value Arguments/Return
        CSample.Point_by_value.ByValue ptv = new CSample.Point_by_value.ByValue();
        CSample.Point_by_value resultv = CSample.INSTANCE.oper_with_by_value_args(ptv, 100, 100);
        System.out.println("[JAVA]  by Value Arguments/Return : ptv.x = " + ptv.x + " - ptv.y = " + ptv.y);
        System.out.println(
                "[JAVA]  by Value Arguments/Return : resultv.x = " + resultv.x + " - resultv.y = " + resultv.y);

        // Array-of-Structure Arguments
        int size = 10;
        CSample.Point[] in_out = new CSample.Point[size];
        CSample.INSTANCE.get_points(in_out, in_out.length);
        System.out.println("[JAVA]  Array-of-Structure get_points 'in_out' Arguments");
        for (CSample.Point p : in_out) {
            System.out.println("   " + p.x + " " + p.y);
        }
        // Alternatively, you can reallocate a single Structure instance into an array
        // as follows:
        CSample.Point point = new CSample.Point();
        // As an array of Point
        CSample.Point[] in_out2 = (CSample.Point[]) point.toArray(size);
        CSample.INSTANCE.get_points(in_out2, in_out2.length);
        System.out.println("[JAVA]  Array-of-Structure get_points 'in_out' Arguments (Alternative)");
        for (CSample.Point p : in_out2) {
            System.out.println("   " + p.x + " " + p.y);
        }
        // set and get
        // CSample.Point[] in = new CSample.Point[size]; => EXCEPTION : Structure array elements
        // must use contiguous memory (bad backing address at Structure array index 1)
        // use toArray
         CSample.Point[] in = (CSample.Point[]) new CSample.Point().toArray(size);
         for (int i = 0; i < size; i++) {
            in[i].x = i;
            in[i].y = i;
        } 
        
        CSample.Point[] in_out3 = new CSample.Point[size];
        CSample.INSTANCE.set_points(in_out3, in, size);
        System.out.println("[JAVA]  Array-of-Structure set_points 'in_out and in' Arguments");
        for (CSample.Point p : in_out3) {
            System.out.println("   " + p.x + " " + p.y);
        }

        // Alternatively, you can reallocate a single Structure instance into an array
        // as follows:
        CSample.Point point2 = new CSample.Point();
        // As an array of Point
        CSample.Point[] in_out4 = (CSample.Point[]) point2.toArray(size);
        CSample.INSTANCE.set_points(in_out4, in_out3, in.length);
        System.out.println("[JAVA]  Array-of-Structure set_points 'in_out and in' Arguments (Alternative)");
        for (CSample.Point p : in_out4) {
            System.out.println("   " + p.x + " " + p.y);
        }
        // Returning an Array of struct
        IntByReference pcount = new IntByReference();
        pcount.setValue(10);
        CSample.Point d = CSample.INSTANCE.return_points(pcount);
        CSample.Point[] _points = (CSample.Point[]) d.toArray(pcount.getValue());
        System.out.println("[JAVA]  Returning an Array of struct");
        for (CSample.Point p : _points) {
            System.out.println("   " + p.x + " " + p.y);
        }
        System.out.println("[JAVA]  freeing the returned memory");
        CSample.INSTANCE.free_points(_points);

        // Nested Structure Definitions
        CSample.Line l = new CSample.Line();
        CSample.Line ll = CSample.INSTANCE.oper_with_nested_structs(l, 100, 100);
        System.out.println("[JAVA] Nested Structure Definitions : ll.start = (" + ll.start.x + ", " + ll.start.y + ")");
        System.out.println("[JAVA] Nested Structure Definitions : ll.end = (" + ll.end.x + ", " + ll.end.y + ")");

        // Nested Structure Definitions : pointer to a structure within your structure
        // CSample.Line2.ByReference l2 = new CSample.Line2.ByReference();
        // CSample.INSTANCE.oper_with_nested_structs(l2, 1000, 1000);
        // System.out.println("[JAVA] Nested Structure Definitions : ll.start = (" +
        // l2.start.x + ", " + l2.start.y + ")");
        // System.out.println("[JAVA] Nested Structure Definitions : ll.end = (" +
        // l2.end.x + ", " + l2.end.y + ")");

        // Nested Structure Definitions : just a pointer to memory.
        CSample.Line3 l3 = new CSample.Line3();
        CSample.Point p1, p2;
        p1 = new CSample.Point();
        p1.x = 0;
        p1.y = 0;
        p2 = new CSample.Point();
        p2.x = 100;
        p2.y = 100;
        l3.p1 = p1.getPointer();
        l3.p2 = p2.getPointer();
        int[] pp1 = { 0, 1 };
        int[] pp2 = { 100, 200 };
        // we have to call write before calling the operation
        l3.p1.write(0, pp1, 0, 2);
        l3.p2.write(0, pp2, 0, 2);
        CSample.INSTANCE.oper_with_nested_structs2(l3, 1000, 1000);
        // we have to call read after calling the operation
        l3.p1.read(0, pp1, 0, 2);
        l3.p2.read(0, pp2, 0, 2);
        System.out.println("[JAVA] Nested Structure Definitions - just a pointer to memory: ll.start = (" + pp1[0]
                + ", " + pp1[1] + ")");
        System.out.println("[JAVA] Nested Structure Definitions - just a pointer to memory: ll.start = (" + pp2[0]
                + ", " + pp2[1] + ")");

    }
}