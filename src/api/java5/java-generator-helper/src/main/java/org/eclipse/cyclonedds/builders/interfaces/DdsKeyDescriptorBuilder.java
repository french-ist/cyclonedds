package org.eclipse.cyclonedds.builders.interfaces;

import org.eclipse.cyclonedds.builders.interfaces.JavaCodeBuilder;

public interface DdsKeyDescriptorBuilder extends JavaCodeBuilder{
    public DdsKeyDescriptorBuilder setM_index(int m_index);
    public DdsKeyDescriptorBuilder setM_name(String m_name);
}