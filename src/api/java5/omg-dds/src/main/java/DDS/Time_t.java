package DDS;


/**
* DDS/Time_t.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /home/ndrianja/IST/cyclonedds/src/api/java5/omg-dds/src/main/idl/dds_dcps_builtintopics.idl
* jeudi 28 février 2019 10 h 26 CET
*/

public final class Time_t implements org.omg.CORBA.portable.IDLEntity
{
  public int sec = (int)0;
  public int nanosec = (int)0;

  public Time_t ()
  {
  } // ctor

  public Time_t (int _sec, int _nanosec)
  {
    sec = _sec;
    nanosec = _nanosec;
  } // ctor

} // class Time_t
