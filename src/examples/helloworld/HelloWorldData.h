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
  Generated: Thu Feb 14 16:24:53 CET 2019
  Cyclone DDS: V0.1.0

*****************************************************************/

#include "ddsc/dds_public_impl.h"

#ifndef _DDSL_HELLOWORLDDATA_H_
#define _DDSL_HELLOWORLDDATA_H_


#ifdef __cplusplus
extern "C" {
#endif


typedef struct HelloWorldData_Msg
{
  int32_t userID;
  char * message;
} HelloWorldData_Msg;

extern const dds_topic_descriptor_t HelloWorldData_Msg_desc;

#define HelloWorldData_Msg__alloc() \
((HelloWorldData_Msg*) dds_alloc (sizeof (HelloWorldData_Msg)));

#define HelloWorldData_Msg_free(d,o) \
dds_sample_free ((d), &HelloWorldData_Msg_desc, (o))

#ifdef __cplusplus
}
#endif
#endif /* _DDSL_HELLOWORLDDATA_H_ */