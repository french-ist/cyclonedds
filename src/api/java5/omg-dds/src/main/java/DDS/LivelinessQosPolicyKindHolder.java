package DDS;

/**
* DDS/LivelinessQosPolicyKindHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /home/ndrianja/IST/cyclonedds/src/api/java5/omg-dds/src/main/idl/dds_builtinTopics.idl
* jeudi 28 février 2019 10 h 26 CET
*/

public final class LivelinessQosPolicyKindHolder implements org.omg.CORBA.portable.Streamable
{
  public DDS.LivelinessQosPolicyKind value = null;

  public LivelinessQosPolicyKindHolder ()
  {
  }

  public LivelinessQosPolicyKindHolder (DDS.LivelinessQosPolicyKind initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = DDS.LivelinessQosPolicyKindHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    DDS.LivelinessQosPolicyKindHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return DDS.LivelinessQosPolicyKindHelper.type ();
  }

}
