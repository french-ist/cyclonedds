package HelloWorldData;


/**
* HelloWorldData/MsgHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /home/ndrianja/IST/jmobile/examples/helloworld/src/main/idl/HelloWorldData.idl
* lundi 25 février 2019 12 h 35 CET
*/

abstract public class MsgHelper
{
  private static String  _id = "IDL:HelloWorldData/Msg:1.0";

  public static void insert (org.omg.CORBA.Any a, HelloWorldData.Msg that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static HelloWorldData.Msg extract (org.omg.CORBA.Any a)
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
          org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember [2];
          org.omg.CORBA.TypeCode _tcOf_members0 = null;
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_long);
          _members0[0] = new org.omg.CORBA.StructMember (
            "userID",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[1] = new org.omg.CORBA.StructMember (
            "message",
            _tcOf_members0,
            null);
          __typeCode = org.omg.CORBA.ORB.init ().create_struct_tc (HelloWorldData.MsgHelper.id (), "Msg", _members0);
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

  public static HelloWorldData.Msg read (org.omg.CORBA.portable.InputStream istream)
  {
    HelloWorldData.Msg value = new HelloWorldData.Msg ();
    value.userID = istream.read_long ();
    value.message = istream.read_string ();
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, HelloWorldData.Msg value)
  {
    ostream.write_long (value.userID);
    ostream.write_string (value.message);
  }

}