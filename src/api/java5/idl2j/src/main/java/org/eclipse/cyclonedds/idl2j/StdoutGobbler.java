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
