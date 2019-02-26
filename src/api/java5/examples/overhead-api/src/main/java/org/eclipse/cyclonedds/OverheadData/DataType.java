package org.eclipse.cyclonedds.OverheadData;

public final class DataType {

    public byte[] payload = new byte[0];

    public DataType() {
    }

    public DataType(
        byte[] _payload)
    {
        payload = _payload;
    }

}
