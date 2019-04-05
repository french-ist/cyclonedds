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
import org.eclipse.cyclonedds.builders.interfaces.DdsKeyDescriptorBuilder;

public class ConcreteDdsKeyDescriptorBuilder implements DdsKeyDescriptorBuilder {

	private ArrayList<DdsKeyDescriptor> propertiesList ;
	private Integer propertiesCount = 0;
	
	public ConcreteDdsKeyDescriptorBuilder() {
        propertiesList = new ArrayList<DdsKeyDescriptor>();
    }
    
	private int m_index;
    public DdsKeyDescriptorBuilder setM_index(int m_index){
        this.m_index = m_index;
        return this;
    }
    
    private String m_name;
    public DdsKeyDescriptorBuilder setM_name(String m_name){
        this.m_name = m_name;
        return this;
    }
    

    public String getJavaCode(){
    	
    	if(propertiesCount==0){
            return "";
        }
        
    	StringBuilder javaCode =new StringBuilder();
        for(int i=0;i<propertiesCount;i++) {
        	javaCode.append("\tprivate dds_key_descriptor[] "+propertiesList.get(i).propertyName+" = {\n");
        	
        	DdsKeyDescriptor list = propertiesList.get(i);
        	
        	for (int j=0;j<list.fieldsList.size(); j++){
                javaCode.append("\t\tnew dds_key_descriptor(stringToPointer("
                    + list.fieldsList.get(j).getName() 
                    +"), "+ list.fieldsList.get(j).getIndex()+")");
                if(j<list.fieldsList.size()-1){
                    javaCode.append(",\n");
                }
            }
        	javaCode.append("\n\t};\n");
        	
        }
        
        return javaCode.toString();
    }

    enum InternalState {
        NOTHING,
        NEW_DDS_KEY_DESCRIPTOR,
        NEXT,
        NAME,
        OPEN_BRACKET,
        COUNT, 
        MAIN_BRACE, 
        FIRST_BRACE,
        DKD_NAME,
        COMMA,
        ELEMENT_OK
    }
    
    InternalState internalState = InternalState.NOTHING;
    @Override 
    public void setState(BuildingState listenerState, String text) {
        switch (listenerState) {
            case DECLARATION_SPECIFIER:
                if(internalState == InternalState.NOTHING){
                    if(text.equals("dds_key_descriptor_t")){
                    	propertiesList.add(new DdsKeyDescriptor());
                    	propertiesCount++;
                        internalState = InternalState.NEW_DDS_KEY_DESCRIPTOR;                        
                    }
                }
                break;
            case DECLARATOR:            
                if(internalState == InternalState.NEW_DDS_KEY_DESCRIPTOR 
                    || internalState == InternalState.NEXT){
                        internalState = InternalState.NEXT;                        
                    }
                break;
            case DIRECT_DECLARATOR:
                if(internalState == InternalState.NEXT) {
                    if(text.indexOf("[") == -1 && text.indexOf("]")==-1){
                    	propertiesList.get(propertiesCount-1).setPropertyName(text);
                        internalState = InternalState.NAME;
                    }
                }
                break;
            case OPEN_BRACKET:
                if(internalState == InternalState.NAME){
                    internalState = InternalState.OPEN_BRACKET;
                }
                break;
            case PRIMARY_EXPRESSION:
                if(internalState == InternalState.OPEN_BRACKET){
                    internalState = InternalState.COUNT;
                } else if(internalState == InternalState.FIRST_BRACE){                    
                    setM_name(text);                	
                    internalState = InternalState.DKD_NAME;                    
                } else if(internalState == InternalState.COMMA){                    
                    setM_index(Integer.valueOf(text));
                    propertiesList.get(propertiesCount-1).addField(new DdsKeyDescriptorField(m_name, m_index));
                    internalState = InternalState.ELEMENT_OK;
                }
                break;
            case OPEN_BRACE:
                if(internalState == InternalState.COUNT){
                    internalState = InternalState.MAIN_BRACE;
                } else if(internalState == InternalState.MAIN_BRACE 
                    || internalState == InternalState.COMMA){
                    internalState = InternalState.FIRST_BRACE;
                }
                break;
            case CLOSE_BRACE:
                if(internalState == InternalState.ELEMENT_OK){                	
                    internalState = InternalState.MAIN_BRACE;
                } else if(internalState == InternalState.MAIN_BRACE){
                    internalState = InternalState.NOTHING;                    
                }
            case COMMA:
                if(internalState == InternalState.DKD_NAME 
                    || internalState == InternalState.ELEMENT_OK){
                    internalState = InternalState.COMMA;
                } else if(internalState == InternalState.ELEMENT_OK) {
                	internalState = InternalState.MAIN_BRACE;
                }
            default:
                break;
        }
    }


}