

#line 1 "/home/ndrianja/IST/cyclonedds/src/api/java5/jna-dds/src/main/idl/generated-c/HelloWorldData.h" 1
/*
 * Copyright(c) 2006 to 2018 ADLINK Technology Limited and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
 * v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
/****************************************************************

  Generated by Cyclone DDS IDL to C Translator
  File name: HelloWorldData.h
  Source: HelloWorldData.idl
  Generated: Thu Nov 29 11:47:28 CET 2018
  Cyclone DDS: V0.1.0

*****************************************************************/


#line 1 "/home/ndrianja/IST/cyclonedds/src/api/java5/jna-dds/src/main/idl/generated-c/HelloWorldData.h" 1










typedef struct HelloWorldData_Msg
{
  int32_t userID;
  char * message;
} HelloWorldData_Msg;

extern const dds_topic_descriptor_t HelloWorldData_Msg_desc;











