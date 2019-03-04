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


public class StderrGobbler
   extends Thread
{

   private BufferedReader reader;
   private Log log;
   private boolean wasErrorPrinted = false;

   public StderrGobbler(InputStream is, Log log)
      throws UnsupportedEncodingException
   {
      this.reader = new BufferedReader(
            new InputStreamReader(is, "UTF-8"));
      this.log = log;
   }

   public boolean wasErrorPrinted()
   {
      return wasErrorPrinted;
   }

   @Override
   public void run()
   {
      try
      {
         String line;
         while ( (line = reader.readLine()) != null)
         {
            // print logs from stderr (ignoring warnings about 'pragma prefix keylist')
            if (line.contains("`keylist'"))
            {
               // JMOB-1199: we can't do simpler because of possible Japanese or Chinese logs
               String line2 = reader.readLine();
               String line3 = reader.readLine();
               if (line2 != null && line2.matches("\\s*#pragma\\s+keylist\\s+.*")
                     && line3 != null && line3.matches("\\s+\\^\\s*"))
               {
                  // do nothing; the 3 lines are ignored
               }
               else
               {
                  // the 3 lines don't match with a 'pragma prefix keylist' warning.
                  // log them
                  log.error(line);
                  if (line2 != null)
                  {
                     log.error(line2);
                  }
                  if (line3 != null)
                  {
                     log.error(line3);
                  }
                  wasErrorPrinted = true;
               }
            }
            else
            {
               log.error(line);
               // NOTE: in some cases idlj returns with exit status == 0
               // even if there are error messages !!
               // Here we detect such messages to throw an exception
               wasErrorPrinted = true;
            }
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
