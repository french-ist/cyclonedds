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

package unittest.keys;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.cyclonedds.core.InstanceHandleImpl;
import org.junit.Assert;
import org.junit.Test;


public class InstanceHandleTest
{

   @Test
   public void InstanceHandleTest1()
   {
      byte[] keyHash = new byte[]
      {
            1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16
      };

      Map<InstanceHandleImpl, Object> map = new HashMap<InstanceHandleImpl, Object>();


      InstanceHandleImpl handle1 = new InstanceHandleImpl(keyHash);
      InstanceHandleImpl handle2 = new InstanceHandleImpl(keyHash);

      map.put(handle1, new Object());

      Assert.assertNotNull(map.get(handle2));

   }
}
