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
