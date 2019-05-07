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
  File name: HelloWorldData.c
  Source: HelloWorldData.idl
  Generated: Fri Dec 14 12:06:55 CET 2018
  Cyclone DDS: V0.1.0

*****************************************************************/
#include "HelloWorldData.h"


static const dds_key_descriptor_t HelloWorldData_Msg_keys[1] =
{
  { "userID", 0 }
};

static const uint32_t HelloWorldData_Msg_ops [] =
{
  DDS_OP_ADR | DDS_OP_TYPE_4BY | DDS_OP_FLAG_KEY, offsetof (HelloWorldData_Msg, userID),
  DDS_OP_ADR | DDS_OP_TYPE_STR, offsetof (HelloWorldData_Msg, message),
  DDS_OP_RTS
};

const dds_topic_descriptor_t HelloWorldData_Msg_desc =
{
  sizeof (HelloWorldData_Msg),
  sizeof (char *),
  DDS_TOPIC_FIXED_KEY | DDS_TOPIC_NO_OPTIMIZE,
  1u,
  "HelloWorldData::Msg",
  HelloWorldData_Msg_keys,
  3,
  HelloWorldData_Msg_ops,
  "<MetaData version=\"1.0.0\"><Module name=\"HelloWorldData\"><Struct name=\"Msg\"><Member name=\"userID\"><Long/></Member><Member name=\"message\"><String/></Member></Struct></Module></MetaData>"
};