/**
 *                             Vortex Cafe
 *
 *    This software and documentation are Copyright 2010 to 2019 ADLINK
 *    Technology Limited, its affiliated companies and licensors. All rights
 *    reserved.
 *
 *    Licensed under the ADLINK Software License Agreement Rev 2.7 2nd October
 *    2014 (the "License"); you may not use this file except in compliance with
 *    the License.
 *    You may obtain a copy of the License at:
 *                        docs/LICENSE.html
 *
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
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
