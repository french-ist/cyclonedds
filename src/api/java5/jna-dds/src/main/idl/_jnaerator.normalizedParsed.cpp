/**
 * Generated by Cyclone DDS IDL to C Translator<br>
 * File name: HelloWorldData.h<br>
 * Source: HelloWorldData.idl<br>
 * Generated: Thu Nov 29 11:54:25 CET 2018<br>
 * Cyclone DDS: V0.1.0<br>
 * ***************************************************************
 */
typedef struct HelloWorldData_Msg {
	int32_t userID;
	char* message;
} HelloWorldData_Msg;
extern const dds_topic_descriptor_t HelloWorldData_Msg_desc;
