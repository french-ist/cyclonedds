// Example #15: Send/Receive a Struct Containing an Array of Structs to C
package org.eclipse.cyclonedds.sample.example15;

import com.sun.jna.Library;
import com.sun.jna.Structure;
import com.sun.jna.Structure.FieldOrder;

public interface CLibrary extends Library {
    @FieldOrder({ Example15StructA.VAL2 })
    public static class Example15StructA extends Structure {
        static final String VAL2 = "val";

        public static class ByReference extends Example15StructA implements Structure.ByReference {
        }

        public int val;
    }

    @FieldOrder({"numAs", Example15StructB.AS2 })
    public static class Example15StructB extends Structure {
        static final String AS2 = "as";

        public static class ByValue extends Example15StructB implements Structure.ByValue {
        }
        public static class ByReference extends Example15StructB implements Structure.ByReference {
        }

        public int numAs;
        public Example15StructA.ByReference as;
    }

    // Senda Struct Containing an Array of Structs to C
    public void example15_send(Example15StructB.ByReference ext);

    // Receive a a Struct Containing an Array of Structs from C
    public Example15StructB.ByValue example15_get();

    public void example15_cleanup(Example15StructB.ByValue sVal);
}
