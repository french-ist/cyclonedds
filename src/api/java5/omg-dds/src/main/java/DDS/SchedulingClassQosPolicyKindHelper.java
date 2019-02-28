package DDS;


/**
* DDS/SchedulingClassQosPolicyKindHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /home/ndrianja/IST/cyclonedds/src/api/java5/omg-dds/src/main/idl/dds_dcps_builtintopics.idl
* jeudi 28 février 2019 10 h 26 CET
*/

abstract public class SchedulingClassQosPolicyKindHelper
{
  private static String  _id = "IDL:DDS/SchedulingClassQosPolicyKind:1.0";

  public static void insert (org.omg.CORBA.Any a, DDS.SchedulingClassQosPolicyKind that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static DDS.SchedulingClassQosPolicyKind extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_enum_tc (DDS.SchedulingClassQosPolicyKindHelper.id (), "SchedulingClassQosPolicyKind", new String[] { "SCHEDULE_DEFAULT", "SCHEDULE_TIMESHARING", "SCHEDULE_REALTIME"} );
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static DDS.SchedulingClassQosPolicyKind read (org.omg.CORBA.portable.InputStream istream)
  {
    return DDS.SchedulingClassQosPolicyKind.from_int (istream.read_long ());
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, DDS.SchedulingClassQosPolicyKind value)
  {
    ostream.write_long (value.value ());
  }

}
