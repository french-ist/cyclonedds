package org.eclipse.cyclonedds.builders.interfaces;

public interface JavaCodeBuilder {
    public void setState(BuildingState listenerState, String text);
    public String getJavaCode();
}