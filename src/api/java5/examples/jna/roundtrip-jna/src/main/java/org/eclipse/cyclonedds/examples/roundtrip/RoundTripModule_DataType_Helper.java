/**
  Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 
  This program and the accompanying materials are made available under the
  terms of the Eclipse Public License v. 2.0 which is available at
  http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
  v. 1.0 which is available at
  http://www.eclipse.org/org/documents/edl-v10.php.
 
  SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.cyclonedds.examples.roundtrip;

import org.eclipse.cyclonedds.ddsc.dds_public_impl.dds_key_descriptor;
import org.eclipse.cyclonedds.ddsc.dds_public_impl.dds_key_descriptor.ByReference;
import org.eclipse.cyclonedds.ddsc.dds_public_impl.dds_topic_descriptor;
import org.eclipse.cyclonedds.ddsc.dds_public_impl.DdscLibrary;
import org.eclipse.cyclonedds.helper.NativeSize;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.Native;
import java.util.List;
import java.lang.reflect.Method;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.Memory;


public class RoundTripModule_DataType_Helper {

	private Integer[] RoundTripModule_DataType_ops = {
        DdscLibrary.DDS_OP_ADR | DdscLibrary.DDS_OP_TYPE_SEQ | DdscLibrary.DDS_OP_SUBTYPE_1BY,
		offsetof("RoundTripModule_DataType","payload"),
		DdscLibrary.DDS_OP_RTS
	};

	public dds_topic_descriptor.ByReference getRoundTripModule_DataType_desc() {
		dds_topic_descriptor.ByReference ret = new dds_topic_descriptor.ByReference();
		ret.m_size = getIntSize("RoundTripModule_DataType") ;
		ret.m_align = sizeof("char*");
		ret.m_flagset = DdscLibrary.DDS_TOPIC_NO_OPTIMIZE;
		ret.m_nkeys = cToJavaNumber("0u");
		ret.m_typename = stringToPointer("RoundTripModule::DataType");
		ret.m_keys = null;
		ret.m_nops = 2;
		ret.m_ops = getIntByReference(RoundTripModule_DataType_ops);
		ret.m_meta = stringToPointer("<MetaData version=\"1.0.0\"><Module name=\"RoundTripModule\"><Struct name=\"DataType\"><Member name=\"payload\"><Sequence><Octet/></Sequence></Member></Struct></Module></MetaData>");
		return ret;
	};

    public RoundTripModule_DataType_Helper(){}

    private IntByReference getIntByReference(Integer[] RoundTripModule_DataType_ops) {        
        IntByReference x = new IntByReference();
        Pointer p = new Memory(RoundTripModule_DataType_ops.length * Native.getNativeSize(Integer.TYPE));
        for(int i=0; i < RoundTripModule_DataType_ops.length; i++){
            p.setInt(i * Native.getNativeSize(Integer.TYPE), RoundTripModule_DataType_ops[i]);
        }        
        x.setPointer(p);
        return x;
    }
    
    private dds_key_descriptor.ByReference getByReference(dds_key_descriptor[] RoundTripModule_DataType_keys) {
        dds_key_descriptor.ByReference ref = new dds_key_descriptor.ByReference();
        if(RoundTripModule_DataType_keys.length==0){
            return null;
        } else {
            dds_key_descriptor[] struct = (dds_key_descriptor[]) ref.toArray(RoundTripModule_DataType_keys.length);
            for(int i=0;i<RoundTripModule_DataType_keys.length;i++){
                struct[i].m_index = RoundTripModule_DataType_keys[i].m_index;
                struct[i].m_name = RoundTripModule_DataType_keys[i].m_name;
            }            
        }        
        return ref;
    }
    
    public NativeSize getNativeSize(String string) {
        return new NativeSize(sizeof(string));
    }
    
    public int getIntSize(String string) {
        return (int)sizeof(string);
    }
    
    public Integer offsetof(String clazz, String field){
        try {
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
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
    public Integer sizeof(String clazz){
        if(clazz.equals("char*")){
            return 8;
        } else {
            try {
                String clazzFullName = (getClass().getPackage() + "." +clazz).replace("package ", "");
                Class<?> c = Class.forName(clazzFullName);
                Class<?> parameterTypes[] = {};
                Method method = c.getMethod("size", parameterTypes);
                int size = (Integer) method.invoke(c.newInstance(), (Object[])null);            
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