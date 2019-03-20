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
 
/**
 * This class defines some simple error handling functions for use in Vortex Cafe examples.
 */
class ExampleError
{
    /**
    * Function to check for a null handle and throw an exception with meaningful output.
    * @param handle to check for null
    * @param where A string detailing where the error occurred
    */
    public static void CheckHandle(Object handle, String where) throws NullPointerException
    {
        if (handle == null)
        {
            throw new NullPointerException("Null pointer at: " + where);
        }
    }
}
