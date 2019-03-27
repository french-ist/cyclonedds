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
package org.eclipse.cyclonedds.builders;

import java.util.ArrayList;

import org.eclipse.cyclonedds.builders.interfaces.BuildingState;
import org.eclipse.cyclonedds.builders.interfaces.JavaCodeBuilder;

public class ConcreteMsgDescBuilder implements JavaCodeBuilder {

    enum InternalState{
        NOTHING,
        NEW_MSG_DESC,
        NAME,
        FIELD
    }    
    
    InternalState internalState = InternalState.NOTHING;    
    private String className = null;
	private String defaultClassName = null;    
	
    public ConcreteMsgDescBuilder(String defaultClassName, String className) {
    	this.defaultClassName  = defaultClassName;
    	this.className = className;
	}

    ArrayList<DdsMsgDescr> propertiesList = new ArrayList<DdsMsgDescr>();
    int propertiesCount = 0;

	@Override
    public void setState(BuildingState listenerState, String text){		
        switch (listenerState) {
            case DECLARATION_SPECIFIER:                
                if(internalState == InternalState.NOTHING){
                    if(text.equals("dds_topic_descriptor_t")){
                        internalState = InternalState.NEW_MSG_DESC;
                    }
                }
                break;
            case DIRECT_DECLARATOR:
                if(internalState == InternalState.NEW_MSG_DESC){                    
                    propertiesList.add(new DdsMsgDescr(text));
                    propertiesCount++;
                    internalState = InternalState.NAME;
                }                               
                break;                    
            case INITIALIZER:                
                if(internalState == InternalState.NAME){                    
                    if(text.indexOf("{") !=-1){
                        internalState = InternalState.FIELD;                                            
                    }
                } else if (internalState == InternalState.FIELD){
                    if(text.indexOf("|") != -1){
                        String[] fields = text.split("\\|");
                        propertiesList.get(propertiesCount-1).addDescriptor("");
                        for(int i=0;i<fields.length;i++){
                            String sep = "";
                            if(i<fields.length-1){
                                sep = " | ";
                            }                                
                            propertiesList.get(propertiesCount-1).appendToLast("DdscLibrary."+fields[i] + sep);
                        }
                    } else {
                        if(text.indexOf("(")!=-1 && text.indexOf(")")!=-1){
                            propertiesList.get(propertiesCount-1).addDescriptor(Remplacements.replace(text));
                        } else if (text.startsWith("\"") && text.endsWith("\"")){
                            propertiesList.get(propertiesCount-1).addDescriptor(Remplacements.stringToPointer(text));
                        } else if(text.indexOf("::") != -1){
                            propertiesList.get(propertiesCount-1).addDescriptor(text);
                        } else if (Remplacements.isNumber(text)){
                            propertiesList.get(propertiesCount-1).addDescriptor(text);
                        } else if (Remplacements.isCNumber(text)){
                            propertiesList.get(propertiesCount-1).addDescriptor(Remplacements.cToJavaNumber(text));
                        } else if (!text.startsWith("\"") && !text.endsWith("\"")){
                            if(text.endsWith("_keys")){
                                propertiesList.get(propertiesCount-1).addDescriptor("getByReference("+text+")");
                            } else if (text.endsWith("_ops")){
                                propertiesList.get(propertiesCount-1).addDescriptor("getIntByReference(get"+text+"())");
                            } else {
                            	if("NULL".equals(text)) {                            		
                                	propertiesList.get(propertiesCount-1).addDescriptor("null");
                            	} else {
                                    propertiesList.get(propertiesCount-1).addDescriptor("DdscLibrary."+text);
                            	}                            	
                            }
                        } else {
                            propertiesList.get(propertiesCount-1).addDescriptor("DdscLibrary."+text);
                        }
                    }
                }
                break; 
            case CLOSE_BRACE:
                if(internalState == InternalState.FIELD){
                    internalState = InternalState.NOTHING;
                }
            default:
                break;
        }
    }


    @Override
    public String getJavaCode() {
        
    	if (propertiesCount == 0){
            return "\t//no topic descriptor\n";
        }
    	StringBuilder ret = new StringBuilder();   
    	ret.append("\tpublic dds_topic_descriptor.ByReference getDdsTopicDescriptor(String topicName) {\n");
    	//ret.append("\t\tSystem.out.println(\"++++++++++\" + topicName);\n");
    	ret.append("\t\tHashMap<String, dds_topic_descriptor.ByReference> map = new HashMap<String, dds_topic_descriptor.ByReference>();\n");
    	for(int i=0;i<propertiesCount;i++) {
    		 StringBuilder javaCode = new StringBuilder();        
    	     javaCode.append("\t\tdds_topic_descriptor.ByReference "+propertiesList.get(i).getPropertyName()+" = new dds_topic_descriptor.ByReference();\n");
    	     if(className == null) {
    	    	 javaCode.append("\t\t"+propertiesList.get(i).getPropertyName()+".m_size = "+propertiesList.get(i).getPropertiesList().get(0)+" ;\n");
    	     } else {
    	    	 javaCode.append("\t\t"+propertiesList.get(i).getPropertyName()+".m_size = "+propertiesList.get(i).getPropertiesList().get(0).replace(defaultClassName+"_"+className, className) +" ;\n");
    	     }
    	     javaCode.append("\t\t"+propertiesList.get(i).getPropertyName()+".m_align = "+propertiesList.get(i).getPropertiesList().get(1)+";\n");
    	     javaCode.append("\t\t"+propertiesList.get(i).getPropertyName()+".m_flagset = "+propertiesList.get(i).getPropertiesList().get(2)+";\n");
    	     javaCode.append("\t\t"+propertiesList.get(i).getPropertyName()+".m_nkeys = "+propertiesList.get(i).getPropertiesList().get(3)+";\n");
    	     javaCode.append("\t\t"+propertiesList.get(i).getPropertyName()+".m_typename = "+propertiesList.get(i).getPropertiesList().get(4)+";\n");
    	     javaCode.append("\t\t"+propertiesList.get(i).getPropertyName()+".m_keys = "+propertiesList.get(i).getPropertiesList().get(5)+";\n");
    	     javaCode.append("\t\t"+propertiesList.get(i).getPropertyName()+".m_nops = "+propertiesList.get(i).getPropertiesList().get(6)+";\n");
    	     javaCode.append("\t\t"+propertiesList.get(i).getPropertyName()+".m_ops = "+propertiesList.get(i).getPropertiesList().get(7)+";\n");
    	     javaCode.append("\t\t"+propertiesList.get(i).getPropertyName()+".m_meta = "+propertiesList.get(i).getPropertiesList().get(8)+";\n");
    	     String topicName = propertiesList.get(i).getPropertyName().replace("_desc", "");
			 javaCode.append("\t\tmap.put(\""+topicName+"\", "+propertiesList.get(i).getPropertyName()+");\n\n");
    	     ret.append(javaCode);
    	}
    	ret.append("\t\treturn map.get(topicName);\n");
    	ret.append("\t}\n");
        return ret.toString();
        
    }

}