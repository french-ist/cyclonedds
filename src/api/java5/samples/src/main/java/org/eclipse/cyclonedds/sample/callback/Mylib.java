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
package org.eclipse.cyclonedds.sample.callback;

import com.sun.jna.Library;

public interface Mylib extends Library {
    int myfunc(String name);

    int myfunc2(MyCallback myc, String name);

    void registerCallback(MyCallback myc);

    public void open_triggerCallback(Functions.OpenFuncInterface callback);

    public void close_triggerCallback(Functions.CloseFuncInterface callback);

    // define an implementation of the callback interface
    public class OpenFuncImplementation implements Functions.OpenFuncInterface {
        @Override
        public int invoke(String name, int option) {
            System.out.println("[Java] open(name : " + name + ", option : " + option + ")");
            return 2000;
        }
    }

    // define an implementation of the callback interface
    public class CloseFuncImplementation implements Functions.CloseFuncInterface {
        @Override
        public int invoke(int fd) {
            System.out.println("[Java] close(fd " + fd + ")");
            return 3000;
        }
    }

    // use of structure of callbacks
    void init(Functions.ByValue funcs);
    int myopen(String name);
    int myclose(int fd);
}