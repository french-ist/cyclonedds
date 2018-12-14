package org.eclipse.cyclonedds.builders;

import java.util.ArrayList;

import org.eclipse.cyclonedds.builders.interfaces.BuildingState;
import org.eclipse.cyclonedds.builders.interfaces.JavaCodeBuilder;

public class ConcreteMessageOptionsBuilder implements JavaCodeBuilder{    
    public String arrayName;
    ArrayList<String> listParams = new ArrayList<String>();
    enum InternalState {
        NOTHING, 
        NEW_UINT32_T,
        ARRAY_OF_UINT32_T, 
        BRACE_OPEN, 
        FIELD
    }
    private int index = 0;
    InternalState internalState = InternalState.NOTHING;    
    
    @Override
    public void setState(BuildingState listenerState, String text){
        switch (listenerState) {
            case DECLARATION_SPECIFIER:                
                if(internalState == InternalState.NOTHING){
                    if(text.equals("uint32_t")){
                        internalState = InternalState.NEW_UINT32_T;
                    }
                }
                break;
            case DIRECT_DECLARATOR:
                if(internalState == InternalState.NEW_UINT32_T){
                    if(text.indexOf("[]") != -1){                        
                        internalState = InternalState.ARRAY_OF_UINT32_T;                        
                    }
                } else if(internalState == InternalState.ARRAY_OF_UINT32_T){
                    if(text.indexOf("[]") == -1){                                                                      
                        arrayName = text;
                    }
                }                               
                break;
            case CLOSE_BRACKET:
                if(internalState == InternalState.ARRAY_OF_UINT32_T){
                    internalState = InternalState.BRACE_OPEN;
                }
                break;            
            case INITIALIZER:                
                if(internalState == InternalState.BRACE_OPEN){                    
                    if(text.indexOf("{") !=-1){
                        internalState = InternalState.FIELD;                                            
                    }
                } else if (internalState == InternalState.FIELD){                    
                    if(text.indexOf("|") != -1){
                        String[] fields = text.split("\\|");
                        if(fields.length > 0){
                            listParams.add(index,"");
                            for(int i=0;i<fields.length;i++){
                                String sep = "";
                                if(i<fields.length-1){
                                    sep = " | ";
                                }                                
                                listParams.set(index, listParams.get(index)+ "DdscLibrary."+fields[i] + sep);
                            }
                        } else {
                            listParams.add(index, "DdscLibrary."+text);
                        }
                    } else {
                        if(text.indexOf("(") != -1 ){
                            listParams.add(index,  Remplacements.replace(text));
                        } else {
                            listParams.add(index, "DdscLibrary."+text);
                        }
                    }
                    index++;
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
        if (arrayName == null){
            return null;
        }

        StringBuilder javaCode = new StringBuilder();
        javaCode.append("\tpublic Integer[] "+arrayName+" = {\n");
        for(int i=0;i<listParams.size();i++){
            javaCode.append("\t\t" + listParams.get(i));
            if(i<listParams.size()-1){
                javaCode.append(",\n");
            }
        }
        javaCode.append("\n\t};\n");
        return javaCode.toString();
    }

}