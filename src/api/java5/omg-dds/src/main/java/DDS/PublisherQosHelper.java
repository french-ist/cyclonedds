package DDS;


/**
* DDS/PublisherQosHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /home/ndrianja/IST/cyclonedds/src/api/java5/omg-dds/src/main/idl/dds_dcps_builtintopics.idl
* jeudi 28 février 2019 10 h 26 CET
*/

abstract public class PublisherQosHelper
{
  private static String  _id = "IDL:DDS/PublisherQos:1.0";

  public static void insert (org.omg.CORBA.Any a, DDS.PublisherQos that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static DDS.PublisherQos extract (org.omg.CORBA.Any a)
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
          org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember [4];
          org.omg.CORBA.TypeCode _tcOf_members0 = null;
          _tcOf_members0 = DDS.PresentationQosPolicyHelper.type ();
          _members0[0] = new org.omg.CORBA.StructMember (
            "presentation",
            _tcOf_members0,
            null);
          _tcOf_members0 = DDS.PartitionQosPolicyHelper.type ();
          _members0[1] = new org.omg.CORBA.StructMember (
            "partition",
            _tcOf_members0,
            null);
          _tcOf_members0 = DDS.GroupDataQosPolicyHelper.type ();
          _members0[2] = new org.omg.CORBA.StructMember (
            "group_data",
            _tcOf_members0,
            null);
          _tcOf_members0 = DDS.EntityFactoryQosPolicyHelper.type ();
          _members0[3] = new org.omg.CORBA.StructMember (
            "entity_factory",
            _tcOf_members0,
            null);
          __typeCode = org.omg.CORBA.ORB.init ().create_struct_tc (DDS.PublisherQosHelper.id (), "PublisherQos", _members0);
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

  public static DDS.PublisherQos read (org.omg.CORBA.portable.InputStream istream)
  {
    DDS.PublisherQos value = new DDS.PublisherQos ();
    value.presentation = DDS.PresentationQosPolicyHelper.read (istream);
    value.partition = DDS.PartitionQosPolicyHelper.read (istream);
    value.group_data = DDS.GroupDataQosPolicyHelper.read (istream);
    value.entity_factory = DDS.EntityFactoryQosPolicyHelper.read (istream);
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, DDS.PublisherQos value)
  {
    DDS.PresentationQosPolicyHelper.write (ostream, value.presentation);
    DDS.PartitionQosPolicyHelper.write (ostream, value.partition);
    DDS.GroupDataQosPolicyHelper.write (ostream, value.group_data);
    DDS.EntityFactoryQosPolicyHelper.write (ostream, value.entity_factory);
  }

}
