
package org.eclipse.cyclonedds.builders;

import java.util.ArrayList;

import org.eclipse.cyclonedds.builders.interfaces.BuildingState;
import org.eclipse.cyclonedds.builders.interfaces.DdsKeyDescriptorBuilder;

public class ConcreteDdsKeyDescriptorBuilder implements DdsKeyDescriptorBuilder {

    private class DdsKeyDescriptor{
        String  name;
        Integer index;
        public DdsKeyDescriptor() {
        }
    }

    private ArrayList<DdsKeyDescriptor> ddsKeyDescriptorList ;
    private DdsKeyDescriptor ddsKeyDescriptor ;
    
    private String keysVariableName = null;
    private Integer ddsKeyDescCount = 0;

    public Integer getDdsKeyDescCount() {
        return ddsKeyDescCount;
    }

    public String getKeysVariableName() {
        return keysVariableName;
    }

    public ConcreteDdsKeyDescriptorBuilder() {
        ddsKeyDescriptorList = new ArrayList<DdsKeyDescriptor>();
    }

    public DdsKeyDescriptorBuilder setM_index(int m_index){
        ddsKeyDescriptor.index = m_index;
        return this;
    }
    
    public DdsKeyDescriptorBuilder setM_name(String m_name){
        ddsKeyDescriptor.name = m_name;
        return this;
    }

    public String getJavaCode(){
        if(keysVariableName==null){
            return "";
        }

        StringBuilder javaCode =new StringBuilder();        
        javaCode.append("\tprivate dds_key_descriptor[] "+keysVariableName+" = {\n");
        for (int i=0;i<getDdsKeyDescCount(); i++){
            javaCode.append("\t\tnew dds_key_descriptor(stringToPointer("
                + ddsKeyDescriptorList.get(i).name 
                +"), "+ddsKeyDescriptorList.get(i).index+")");
            if(i<getDdsKeyDescCount()-1){
                javaCode.append(",\n");
            }
        }
        javaCode.append("\n\t};\n");
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
        ELEMENT_OK,
        LAST_BRACE
    }
    
    InternalState internalState = InternalState.NOTHING;
    @Override 
    public void setState(BuildingState listenerState, String text) {
        switch (listenerState) {
            case DECLARATION_SPECIFIER:
                if(internalState == InternalState.NOTHING){
                    if(text.equals("dds_key_descriptor_t")){
                        ddsKeyDescriptor = new DdsKeyDescriptor();
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
                        keysVariableName = text;
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
                    ddsKeyDescCount = new Integer(text);
                    internalState = InternalState.COUNT;
                } else if(internalState == InternalState.FIRST_BRACE){                    
                    setM_name(text);
                    internalState = InternalState.DKD_NAME;                    
                } else if(internalState == InternalState.COMMA){                    
                    setM_index(new Integer(text));
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
                    ddsKeyDescriptorList.add(ddsKeyDescriptor);
                    ddsKeyDescriptor = new DdsKeyDescriptor();
                    internalState = InternalState.LAST_BRACE;
                } else if(internalState == InternalState.LAST_BRACE){
                    internalState = InternalState.NOTHING;                    
                }
            case COMMA:
                if(internalState == InternalState.DKD_NAME 
                    || internalState == InternalState.ELEMENT_OK){
                    internalState = InternalState.COMMA;
                }
            default:
                break;
        }
    }




}