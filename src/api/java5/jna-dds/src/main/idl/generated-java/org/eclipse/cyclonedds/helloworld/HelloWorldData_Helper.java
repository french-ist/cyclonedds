package org.eclipse.cyclonedds.helloworld;
import org.eclipse.cyclonedds.ddsc.dds_key_descriptor;
import org.eclipse.cyclonedds.ddsc.dds_topic_descriptor;

import com.sun.jna.Pointer;
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.ptr.IntByReference;

import org.eclipse.cyclonedds.ddsc.NativeSize;
import org.eclipse.cyclonedds.ddsc.DdscLibrary;

public class HelloWorldData_Helper
{
    
    public static Pointer stringToPointer(String str) {        
        Pointer pointer = new Memory(str.length()+1);
        pointer.setString(0, str);
        return pointer;
    }
    
    public static int offsetof(int a, int b){
        return 0;
    }

    public final static  dds_key_descriptor[] HelloWorldData_Msg_keys = {
        new dds_key_descriptor.ByReference()
    };
    
    public final static Integer[]  HelloWorldData_Msg_ops = {
        DdscLibrary.DDS_OP_ADR | DdscLibrary.DDS_OP_TYPE_4BY | DdscLibrary.DDS_OP_FLAG_KEY, 
        //offsetof(HelloWorldData_Msg, userID),
        DdscLibrary.DDS_OP_ADR | DdscLibrary.DDS_OP_TYPE_STR, 
        //offsetof (HelloWorldData_Msg, message),
        DdscLibrary.DDS_OP_RTS
    };    
    
    static Pointer array_ops = new Memory(HelloWorldData_Msg_ops.length * Native.getNativeSize(Integer.class));    
    public final static dds_topic_descriptor HelloWorldData_Msg_desc = new dds_topic_descriptor();
    static {
        for (int i=0;i<HelloWorldData_Msg_ops.length;i++){
            array_ops.setInt(i, HelloWorldData_Msg_ops[i]);            
        }

        HelloWorldData_Msg_desc.setM_size(new NativeSize(Native.getNativeSize(HelloWorldData_Msg.class)));
        HelloWorldData_Msg_desc.setM_align(Native.getNativeSize(String.class));
        HelloWorldData_Msg_desc.setM_flagset(DdscLibrary.DDS_TOPIC_FIXED_KEY | DdscLibrary.DDS_TOPIC_NO_OPTIMIZE);
        HelloWorldData_Msg_desc.setM_nkeys(1);
        HelloWorldData_Msg_desc.setM_typename(stringToPointer("HelloWorldData::Msg"));

        HelloWorldData_Msg_keys[0].setM_name(stringToPointer("userID"));
        HelloWorldData_Msg_keys[0].setM_index(0);
        HelloWorldData_Msg_desc.setM_keys((dds_key_descriptor.ByReference)HelloWorldData_Msg_keys[0]);

        HelloWorldData_Msg_desc.setM_nops(3);
        HelloWorldData_Msg_desc.setM_ops(new IntByReference(new Integer(array_ops.getByte(0))));
        HelloWorldData_Msg_desc.setM_meta(stringToPointer("<MetaData version=\"1.0.0\"><Module name=\"HelloWorldData\"><Struct name=\"Msg\"><Member name=\"userID\"><Long/></Member><Member name=\"message\"><String/></Member></Struct></Module></MetaData>"));
    }
    
    


    /*
    new dds_topic_descriptor(
        new NativeSize(Native.getNativeSize(HelloWorldData_Msg.class)), 
        Native.getNativeSize(String.class), 
        DdscLibrary.DDS_TOPIC_FIXED_KEY | DdscLibrary.DDS_TOPIC_NO_OPTIMIZE, 
        1,
        stringToPointer("HelloWorldData::Msg"), 
        new dds_key_descriptor.ByReference(), //org.eclipse.cyclonedds.ddsc.dds_key_descriptor.ByReference m_keys, 
        3, 
        new IntByReference(new Integer(array_ops.getByte(0))), //IntByReference m_ops,
        stringToPointer("<MetaData version=\"1.0.0\"><Module name=\"HelloWorldData\"><Struct name=\"Msg\"><Member name=\"userID\"><Long/></Member><Member name=\"message\"><String/></Member></Struct></Module></MetaData>")
     );
    */

    
    
    
         

}