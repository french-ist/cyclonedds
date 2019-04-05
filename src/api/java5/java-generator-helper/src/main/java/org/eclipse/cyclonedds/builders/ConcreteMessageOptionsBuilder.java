/**
  Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 
  This program and the accompanying materials are made available under the
  terms of the Eclipse Public License v. 2.0 which is available at
  http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
  v. 1.0 which is available at
  http://www.eclipse.org/org/documents/edl-v10.php.
 
  SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.cyclonedds.builders;

import java.util.ArrayList;

import org.eclipse.cyclonedds.builders.interfaces.BuildingState;
import org.eclipse.cyclonedds.builders.interfaces.JavaCodeBuilder;

public class ConcreteMessageOptionsBuilder implements JavaCodeBuilder{    
    enum InternalState {
        NOTHING, 
        NEW_UINT32_T,
        ARRAY_OF_UINT32_T, 
        OPEN_BRACE, 
        FIELD
    }
    
    InternalState internalState = InternalState.NOTHING;
	private String newClassName = null;
	private String oldClassName = null;    
    
    public ConcreteMessageOptionsBuilder(String oldClassName, String newClassName) {
    	this.oldClassName  = oldClassName;
    	this.newClassName = newClassName;
	}
    
    ArrayList<DdsOptions> propertiesList = new ArrayList<DdsOptions>();
    int propertiesCount = 0;

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
                        propertiesList.add(new DdsOptions(text));
                        propertiesCount++;
                    }
                }                               
                break;
            case CLOSE_BRACKET:
                if(internalState == InternalState.ARRAY_OF_UINT32_T){
                    internalState = InternalState.OPEN_BRACE;
                }
                break;            
            case INITIALIZER:                
                if(internalState == InternalState.OPEN_BRACE){                    
                    if(text.indexOf("{") !=-1){
                        internalState = InternalState.FIELD;                                            
                    }
                } else if (internalState == InternalState.FIELD){                    
                    if(text.indexOf("|") != -1){
                        String[] fields = text.split("\\|");
                        if(fields.length > 0){
                            propertiesList.get(propertiesCount-1).addOption("");
                            for(int i=0;i<fields.length;i++){
                                String sep = "";
                                if(i<fields.length-1){
                                    sep = " | ";
                                }
                                propertiesList.get(propertiesCount-1).appendToLast("DdscLibrary."+fields[i] + sep);
                            }
                        } else {
                        	propertiesList.get(propertiesCount-1).addOption("DdscLibrary."+text);
                        }
                    } else {
                        if(text.indexOf("(") != -1 ){
                        	if(text.indexOf("offsetof") != -1) {
                        		propertiesList.get(propertiesCount-1).addOption(Remplacements.replace(text).replace(oldClassName, newClassName));
                        	} else {
                        		propertiesList.get(propertiesCount-1).addOption(Remplacements.replace(text));
                        	}
                            
                        } else {
                        	propertiesList.get(propertiesCount-1).addOption("DdscLibrary."+text);
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
            return "\t//no options\n";
        }
    	
    	StringBuilder retJavaCode = new StringBuilder();
        for(int i=0;i<propertiesCount;i++) {
        	StringBuilder javaCode = new StringBuilder();
        	javaCode.append("\tInteger[] "+propertiesList.get(i).getPropertyName()+" = {\n");
        	ArrayList<String> list = propertiesList.get(i).getOptionsList();
        	for(int j=0;j<list.size();j++){
                javaCode.append("\t\t\t\t" + list.get(j));
                if(i<list.size()-1){
                    javaCode.append(",\n");
                }
            }
        	javaCode.append("\n\t\t};\n");
        	retJavaCode.append("\tpublic Integer[] get"+propertiesList.get(i).getPropertyName()+"() {\n");
            retJavaCode.append("\t\t"+javaCode.toString());
            retJavaCode.append("\t\treturn "+propertiesList.get(i).getPropertyName()+";\n");
            retJavaCode.append("\t}\n");
        }
        
        
        
        return retJavaCode.toString();
    }
    

}