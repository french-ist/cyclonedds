package DDS;

/**
* DDS/DeadlineQosPolicyHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /home/ndrianja/IST/cyclonedds/src/api/java5/omg-dds/src/main/idl/dds_builtinTopics.idl
* jeudi 28 février 2019 10 h 26 CET
*/

public final class DeadlineQosPolicyHolder implements org.omg.CORBA.portable.Streamable
{
  public DDS.DeadlineQosPolicy value = null;

  public DeadlineQosPolicyHolder ()
  {
  }

  public DeadlineQosPolicyHolder (DDS.DeadlineQosPolicy initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = DDS.DeadlineQosPolicyHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    DDS.DeadlineQosPolicyHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return DDS.DeadlineQosPolicyHelper.type ();
  }

}
