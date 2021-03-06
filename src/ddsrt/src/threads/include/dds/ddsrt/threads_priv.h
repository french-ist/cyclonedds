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
#ifndef DDSRT_THREAD_TYPES_H
#define DDSRT_THREAD_TYPES_H

#include "dds/ddsrt/threads.h"

typedef struct {
  char *name;
  ddsrt_thread_routine_t routine;
  void *arg;
} thread_context_t;

/** \brief Internal structure used to store cleanup handlers (private) */
typedef struct {
  void *prev;
  void (*routine)(void *);
  void *arg;
} thread_cleanup_t;

#endif /* DDSRT_THREAD_TYPES_H */
