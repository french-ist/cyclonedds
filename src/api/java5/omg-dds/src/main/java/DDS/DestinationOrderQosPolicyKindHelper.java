package DDS;


/**
* DDS/DestinationOrderQosPolicyKindHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /home/ndrianja/IST/cyclonedds/src/api/java5/omg-dds/src/main/idl/dds_builtinTopics.idl
* jeudi 28 février 2019 10 h 26 CET
*/

abstract public class DestinationOrderQosPolicyKindHelper
{
  private static String  _id = "IDL:DDS/DestinationOrderQosPolicyKind:1.0";

  public static void insert (org.omg.CORBA.Any a, DDS.DestinationOrderQosPolicyKind that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static DDS.DestinationOrderQosPolicyKind extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_enum_tc (DDS.DestinationOrderQosPolicyKindHelper.id (), "DestinationOrderQosPolicyKind", new String[] { "BY_RECEPTION_TIMESTAMP_DESTINATIONORDER_QOS", "BY_SOURCE_TIMESTAMP_DESTINATIONORDER_QOS"} );
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static DDS.DestinationOrderQosPolicyKind read (org.omg.CORBA.portable.InputStream istream)
  {
    return DDS.DestinationOrderQosPolicyKind.from_int (istream.read_long ());
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, DDS.DestinationOrderQosPolicyKind value)
  {
    ostream.write_long (value.value ());
  }

}
