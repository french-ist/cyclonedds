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
#ifndef OS_PROJECT_H
#define OS_PROJECT_H

/* Set OS_ENDIANNESS to OS_BIG_ENDIAN or OS_LITTLE_ENDIAN */
#define OS_ENDIANNESS OS_LITTLE_ENDIAN

/* Sets OS_SIZEOF_VOID_P to sizeof(void*) */
#define OS_SIZEOF_VOID_P 8

#if (OS_SIZEOF_VOID_P == 8)
#define OS_64BIT
#endif

#define OS_VERSION "0.1.0"
#define OS_VERSION_MAJOR 0
#define OS_VERSION_MINOR 1
#define OS_VERSION_PATCH 0
#define OS_VERSION_TWEAK 
#define OS_PROJECT_NAME_NOSPACE_CAPS "CYCLONEDDS"
#define OS_PROJECT_NAME_NOSPACE_SMALL "cyclonedds"
#define OS_PROJECT_NAME_NOSPACE "CycloneDDS"
#define OS_PROJECT_NAME "CycloneDDS"

#define OS_HOST_NAME "Linux"
#define OS_TARGET_NAME "Linux"

#endif /* OS_PROJECT_H */
