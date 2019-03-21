/**
 * Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
 * v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.cyclonedds;
import com.sun.jna.*;

import main.java.org.eclpise.cyclonedds.CHelloItf;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        CHelloItf.INSTANCE.display("Bonjour tout le monde\n");
        CHelloItf.INSTANCE.printf("J'ai %d chats\n", 2);
    }
}
