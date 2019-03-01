package ThroughputModule;

public final class DataType {

    public long count;
    public byte[] payload = new byte[0];

    public DataType() {
    }

    public DataType(
        long _count,
        byte[] _payload)
    {
        count = _count;
        payload = _payload;
    }

}
