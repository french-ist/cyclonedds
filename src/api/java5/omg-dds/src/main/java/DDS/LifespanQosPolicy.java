package DDS;


/**
* DDS/LifespanQosPolicy.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /home/ndrianja/IST/cyclonedds/src/api/java5/omg-dds/src/main/idl/dds_builtinTopics.idl
* jeudi 28 février 2019 10 h 26 CET
*/

public final class LifespanQosPolicy implements org.omg.CORBA.portable.IDLEntity
{
  public DDS.Duration_t duration = null;

  public LifespanQosPolicy ()
  {
  } // ctor

  public LifespanQosPolicy (DDS.Duration_t _duration)
  {
    duration = _duration;
  } // ctor

} // class LifespanQosPolicy
