package DDS;


/**
* DDS/CMParticipantBuiltinTopicData.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /home/ndrianja/IST/cyclonedds/src/api/java5/omg-dds/src/main/idl/dds_builtinTopics.idl
* jeudi 28 février 2019 10 h 26 CET
*/

public final class CMParticipantBuiltinTopicData implements org.omg.CORBA.portable.IDLEntity
{
  public int key[] = null;
  public DDS.ProductDataQosPolicy product = null;

  public CMParticipantBuiltinTopicData ()
  {
  } // ctor

  public CMParticipantBuiltinTopicData (int[] _key, DDS.ProductDataQosPolicy _product)
  {
    key = _key;
    product = _product;
  } // ctor

} // class CMParticipantBuiltinTopicData
