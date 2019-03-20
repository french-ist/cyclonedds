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
package org.eclipse.cyclonedds.idl2j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.maven.plugin.logging.Log;


public class StdoutGobbler
   extends Thread
{

   private BufferedReader reader;
   private Log log;

   public StdoutGobbler(InputStream is, Log log)
      throws UnsupportedEncodingException
   {
      this.reader = new BufferedReader(
            new InputStreamReader(is, "UTF-8"));
      this.log = log;
   }

   @Override
   public void run()
   {
      try
      {
         String line;
         while ( (line = reader.readLine()) != null)
         {
            // print logs from stdout
            log.info(line);
         }
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
   }

   public void close() throws IOException, InterruptedException
   {
      reader.close();
      this.join();
   }

}
