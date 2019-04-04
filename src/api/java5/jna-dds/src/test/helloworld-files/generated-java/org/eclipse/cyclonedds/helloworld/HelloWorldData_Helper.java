package org.eclipse.cyclonedds.helloworld;

import org.eclipse.cyclonedds.ddsc.dds_public_impl.dds_key_descriptor;
import org.eclipse.cyclonedds.ddsc.dds_public_impl.dds_topic_descriptor;
import org.eclipse.cyclonedds.ddsc.dds_public_impl.DdscLibrary;
import org.eclipse.cyclonedds.helper.NativeSize;
import java.util.HashMap;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.Native;
import java.lang.reflect.Method;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import org.eclipse.cyclonedds.topic.UserClassHelper;
import com.sun.jna.Memory;


public class HelloWorldData_Helper implements UserClassHelper {

	private dds_key_descriptor[] HelloWorldData_Msg_keys = {
		new dds_key_descriptor(stringToPointer("userID"), 0)
	};

	public Integer[] getHelloWorldData_Msg_ops() {
			Integer[] HelloWorldData_Msg_ops = {
				DdscLibrary.DDS_OP_ADR | DdscLibrary.DDS_OP_TYPE_4BY | DdscLibrary.DDS_OP_FLAG_KEY,
				offsetof("Msg","userID"),
				DdscLibrary.DDS_OP_ADR | DdscLibrary.DDS_OP_TYPE_STR,
				offsetof("Msg","message"),
				DdscLibrary.DDS_OP_RTS,

		};
		return HelloWorldData_Msg_ops;
	}

	public dds_topic_descriptor.ByReference getDdsTopicDescriptor(String topicName) {
		HashMap<String, dds_topic_descriptor.ByReference> map = new HashMap<String, dds_topic_descriptor.ByReference>();
		dds_topic_descriptor.ByReference HelloWorldData_Msg_desc = new dds_topic_descriptor.ByReference();
		HelloWorldData_Msg_desc.m_size = getIntSize("Msg") ;
		HelloWorldData_Msg_desc.m_align = sizeof("char*");
		HelloWorldData_Msg_desc.m_flagset = DdscLibrary.DDS_TOPIC_FIXED_KEY | DdscLibrary.DDS_TOPIC_NO_OPTIMIZE;
		HelloWorldData_Msg_desc.m_nkeys = cToJavaNumber("1u");
		HelloWorldData_Msg_desc.m_typename = stringToPointer("HelloWorldData::Msg");
		HelloWorldData_Msg_desc.m_keys = getByReference(HelloWorldData_Msg_keys);
		HelloWorldData_Msg_desc.m_nops = 3;
		HelloWorldData_Msg_desc.m_ops = getIntByReference(getHelloWorldData_Msg_ops());
		HelloWorldData_Msg_desc.m_meta = stringToPointer("<MetaData version=\"1.0.0\"><Module name=\"HelloWorldData\"><Struct name=\"Msg\"><Member name=\"userID\"><Long/></Member><Member name=\"message\"><String/></Member></Struct></Module></MetaData>");
		map.put("Msg", HelloWorldData_Msg_desc);

		return map.get(topicName);
	}

    public HelloWorldData_Helper(){}

    private IntByReference getIntByReference(Integer[] helloWorldData_Msg_ops) {        
        IntByReference x = new IntByReference();
        Pointer p = new Memory(helloWorldData_Msg_ops.length * Native.getNativeSize(Integer.TYPE));
        for(int i=0; i < helloWorldData_Msg_ops.length; i++){
            p.setInt(i * Native.getNativeSize(Integer.TYPE), helloWorldData_Msg_ops[i]);
        }        
        x.setPointer(p);
        return x;
    }
    
    private dds_key_descriptor.ByReference getByReference(dds_key_descriptor[] helloWorldData_Msg_keys) {
        dds_key_descriptor.ByReference ref = new dds_key_descriptor.ByReference();
        if(helloWorldData_Msg_keys.length==0){
            return null;
        } else {
            dds_key_descriptor[] struct = (dds_key_descriptor[]) ref.toArray(helloWorldData_Msg_keys.length);
            for(int i=0;i<helloWorldData_Msg_keys.length;i++){
                struct[i].m_index = helloWorldData_Msg_keys[i].m_index;
                struct[i].m_name = helloWorldData_Msg_keys[i].m_name;
            }            
        }        
        return ref;
    }
    
    public NativeSize getNativeSize(String string) {
        //System.out.println("### getNativeSize("+string+")");
        return new NativeSize(sizeof(string));
    }
    
    public int getIntSize(String string) {
        //System.out.println("### getIntSize("+string+")");
        return (int)sizeof(string);
    }
    
    public Integer offsetof(String clazz, String field){
        try {
        	//System.out.println("### offsetof("+clazz+","+field+")");
            String clazzFullName = (getClass().getPackage() + "." +clazz).replace("package ", "");
            Class<?> c = Class.forName(clazzFullName);        
            Object instance = c.newInstance();
            if(instance instanceof Structure){
                Structure s = (Structure)instance;            
                Class<?> parameterTypes[] = {String.class};
                Method method = s.getClass().getSuperclass().getDeclaredMethod("fieldOffset", parameterTypes);
                method.setAccessible(true);
                return (Integer)method.invoke(instance, field);
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    
    public Integer sizeof(String clazz){
    	//System.out.println("### sizeof("+clazz+")");
        if(clazz.equals("char*")){
            return 8;
        } else {
            try {
                String clazzFullName = (getClass().getPackage() + "." +clazz).replace("package ", "");
                Class<?> c = Class.forName(clazzFullName);
                Class<?> parameterTypes[] = {};
                Method method = c.getMethod("size", parameterTypes);
                int size = (Integer) method.invoke(c.newInstance(), new Object[] {});            
                return size;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }
    
    public Integer cToJavaNumber(String number){
        for(int i=0;i<number.length();i++){
            try{
                Integer.parseInt(""+number.charAt(i));
            } catch (NumberFormatException e){
                return Integer.parseInt(number.substring(0,i));
            }
        }
        return null;
    }
    
    public Pointer stringToPointer(String str){
        Pointer pointer = new Memory(str.length()+1);
        pointer.setString(0, str);
        return pointer;
    }
    
    public int dds_error_check(int entity, int error){
        byte ret = org.eclipse.cyclonedds.ddsc.dds_public_error.DdscLibrary.dds_err_check(entity,
            org.eclipse.cyclonedds.ddsc.dds_public_error.DdscLibrary.DDS_CHECK_REPORT | org.eclipse.cyclonedds.ddsc.dds_public_error.DdscLibrary.DDS_CHECK_EXIT,
            (new java.io.File(".")).getAbsolutePath());
        if(ret != 1){
            try {
                throw new Exception("dds_error_check");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ret;
    }
}