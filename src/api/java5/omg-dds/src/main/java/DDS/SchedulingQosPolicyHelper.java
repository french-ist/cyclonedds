package DDS;


/**
* DDS/SchedulingQosPolicyHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /home/ndrianja/IST/cyclonedds/src/api/java5/omg-dds/src/main/idl/dds_dcps_builtintopics.idl
* jeudi 28 février 2019 10 h 26 CET
*/

abstract public class SchedulingQosPolicyHelper
{
  private static String  _id = "IDL:DDS/SchedulingQosPolicy:1.0";

  public static void insert (org.omg.CORBA.Any a, DDS.SchedulingQosPolicy that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static DDS.SchedulingQosPolicy extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  private static boolean __active = false;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      synchronized (org.omg.CORBA.TypeCode.class)
      {
        if (__typeCode == null)
        {
          if (__active)
          {
            return org.omg.CORBA.ORB.init().create_recursive_tc ( _id );
          }
          __active = true;
          org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember [3];
          org.omg.CORBA.TypeCode _tcOf_members0 = null;
          _tcOf_members0 = DDS.SchedulingClassQosPolicyHelper.type ();
          _members0[0] = new org.omg.CORBA.StructMember (
            "scheduling_class",
            _tcOf_members0,
            null);
          _tcOf_members0 = DDS.SchedulingPriorityQosPolicyHelper.type ();
          _members0[1] = new org.omg.CORBA.StructMember (
            "scheduling_priority_kind",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_long);
          _members0[2] = new org.omg.CORBA.StructMember (
            "scheduling_priority",
            _tcOf_members0,
            null);
          __typeCode = org.omg.CORBA.ORB.init ().create_struct_tc (DDS.SchedulingQosPolicyHelper.id (), "SchedulingQosPolicy", _members0);
          __active = false;
        }
      }
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static DDS.SchedulingQosPolicy read (org.omg.CORBA.portable.InputStream istream)
  {
    DDS.SchedulingQosPolicy value = new DDS.SchedulingQosPolicy ();
    value.scheduling_class = DDS.SchedulingClassQosPolicyHelper.read (istream);
    value.scheduling_priority_kind = DDS.SchedulingPriorityQosPolicyHelper.read (istream);
    value.scheduling_priority = istream.read_long ();
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, DDS.SchedulingQosPolicy value)
  {
    DDS.SchedulingClassQosPolicyHelper.write (ostream, value.scheduling_class);
    DDS.SchedulingPriorityQosPolicyHelper.write (ostream, value.scheduling_priority_kind);
    ostream.write_long (value.scheduling_priority);
  }

}
