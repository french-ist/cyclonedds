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
    ArrayList<String> listParams = new ArrayList<String>();
    InternalState internalState = InternalState.NOTHING;    
    private String variableName;
    private int index = 0;
    
    private String className = null;
	private String defaultClassName = null;    
	
    public ConcreteMsgDescBuilder(String defaultClassName, String className) {
    	this.defaultClassName  = defaultClassName;
    	this.className = className;
    	index = 0;
	}



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
                    variableName = text;
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
                        listParams.add(index,"");                            
                        for(int i=0;i<fields.length;i++){
                            String sep = "";
                            if(i<fields.length-1){
                                sep = " | ";
                            }                                
                            listParams.set(index, listParams.get(index)+ "DdscLibrary."+fields[i] + sep);
                        }
                        index++;
                    } else {
                        if(text.indexOf("(")!=-1 && text.indexOf(")")!=-1){
                            listParams.add(index, Remplacements.replace(text));
                        } else if (text.startsWith("\"") && text.endsWith("\"")){
                            listParams.add(index, Remplacements.stringToPointer(text));
                        } else if(text.indexOf("::") != -1){
                            listParams.add(index, text);
                        } else if (Remplacements.isNumber(text)){
                            listParams.add(index, text);
                        } else if (Remplacements.isCNumber(text)){
                            listParams.add(index, Remplacements.cToJavaNumber(text));
                        } else if (!text.startsWith("\"") && !text.endsWith("\"")){
                            if(text.endsWith("_keys")){
                                listParams.add(index, "getByReference("+text+")");
                            } else if (text.endsWith("_ops")){
                                listParams.add(index, "getIntByReference(get"+text+"())");
                            } else {
                            	if("NULL".equals(text)) {                            		
                                	listParams.add(index, "null");
                            	} else {
                                    listParams.add(index, "DdscLibrary."+text);
                            	}                            	
                            }
                        } else {
                            listParams.add(index, "DdscLibrary."+text);
                        }
                        index++;
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

    

    public String getVariableName() {
        return variableName;
    }

    @Override
    public String getJavaCode() {
        if (variableName == null){
            return null;
        }
        
        StringBuilder javaCode = new StringBuilder();        
        javaCode.append("\tpublic dds_topic_descriptor.ByReference getDdsTopicDescriptor() {\n");
        javaCode.append("\t\tdds_topic_descriptor.ByReference ret = new dds_topic_descriptor.ByReference();\n");

        if(className == null) {
            javaCode.append("\t\tret.m_size = "+listParams.get(0)+" ;\n");
        } else {
        	javaCode.append("\t\tret.m_size = "+listParams.get(0).replace(defaultClassName+"_"+className, className) +" ;\n");
        }

        javaCode.append("\t\tret.m_align = "+listParams.get(1)+";\n");
        javaCode.append("\t\tret.m_flagset = "+listParams.get(2)+";\n");
        javaCode.append("\t\tret.m_nkeys = "+listParams.get(3)+";\n");
        javaCode.append("\t\tret.m_typename = "+listParams.get(4)+";\n");
        javaCode.append("\t\tret.m_keys = "+listParams.get(5)+";\n");
        javaCode.append("\t\tret.m_nops = "+listParams.get(6)+";\n");
        javaCode.append("\t\tret.m_ops = "+listParams.get(7)+";\n");
        javaCode.append("\t\tret.m_meta = "+listParams.get(8)+";\n");
        javaCode.append("\t\treturn ret;\n");

        javaCode.append("\t};\n");
        return javaCode.toString();
    }

}