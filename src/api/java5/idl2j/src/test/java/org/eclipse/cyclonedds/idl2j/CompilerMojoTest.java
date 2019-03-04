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

import java.io.File;

import org.junit.Assert;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;


public class CompilerMojoTest
   extends AbstractMojoTestCase
{

   // private CompilerMojo mojo;
   public void setUp() throws Exception
   {
      System.out.println("-------- setUp");
      super.setUp();

      // File pom = new File( getBasedir(), "src/test/resources/validIdlFiles.xml" );
      // assertNotNull( pom );
      // assertTrue( pom.exists() );
      // mojo = (CompilerMojo) lookupMojo("idl-compile", pom);
      // assertNotNull( "Mojo found.", mojo );
      // MavenProject mavenProject = (MavenProject) getVariableValueFromObject( mojo, "project" );
      // assertNotNull( mavenProject );

   }

   // Load a POM file and return the Mojo for idl-compile goal
   private CompilerMojo loadPom(String pomPath) throws Exception
   {
      System.out.println("-------- setloadPom " + pomPath);
      File pom = new File(getBasedir(), pomPath);
      assertNotNull(pom);
      assertTrue(pom.exists());
      CompilerMojo mojo = (CompilerMojo) lookupMojo("idl-compile", pom);
      assertNotNull("Mojo found.", mojo);
      System.out.println("-------- setloadPom returns " + mojo);
      return mojo;
   }

   /**
    * Test injection of fields values from POM into MOJO
    */
   public void testMojoAttributes() throws Exception
   {
      try
      {
         System.out.println("-------- testMojoAttributes ");
         CompilerMojo mojo = loadPom("src/test/resources/pom.xml");

         File[] includeDirs = (File[]) getVariableValueFromObject(mojo, "includeDirs");
         assertEquals(3, includeDirs.length);
         assertEquals("myDir1", includeDirs[0].getName());
         assertEquals("myDir2", includeDirs[1].getName());
         assertEquals("WEB-125", includeDirs[2].getName());

         String mode = (String) getVariableValueFromObject(mojo, "mode");
         assertEquals("-v", mode);

         File idlDir = (File) getVariableValueFromObject(mojo, "idlDir");
         assertEquals("valid-idl", idlDir.getName());

         File outDir = (File) getVariableValueFromObject(mojo, "outDir");
         assertEquals("generated-test-sources", outDir.getName());

         String defaultPkgPrefix = (String) getVariableValueFromObject(mojo, "defaultPkgPrefix");
         assertEquals("default_pkg_prefix", defaultPkgPrefix);

      }
      catch (Exception e)
      {
         e.printStackTrace();
         Assert.fail(e.getMessage());
      }
   }


   /**
    * Test IDL compilation (generated code is checked in GeneratedCodeTest.java)
    */
   public void testIDLCompilation() throws Exception
   {
      System.out.println("-------- testIDLCompilation ");
      try
      {
         CompilerMojo mojo = loadPom("src/test/resources/pom.xml");

         mojo.execute();

      }
      catch (Exception e)
      {
         e.printStackTrace();
         Assert.fail(e.getMessage());
      }
   }

   public void testFailureCases() throws Exception
   {
      try
      {
         CompilerMojo mojo = loadPom("src/test/resources/test-poms/nonExistingIdlDir.xml");

         mojo.execute();
         Assert.fail("MojoExecutionException was expected");

      }
      catch (MojoExecutionException e)
      {
         // MojoExecutionException is expected
         Assert.assertTrue(true);
      }
      catch (Exception e)
      {
         e.printStackTrace();
         Assert.fail(e.getMessage());
      }

      try
      {
         CompilerMojo mojo = loadPom("src/test/resources/test-poms/wrongIdl.xml");
         mojo.execute();
         Assert.fail("MojoExecutionException was expected");

      }
      catch (MojoExecutionException e)
      {
         // MojoExecutionException is expected
         Assert.assertTrue(true);
      }
      catch (Exception e)
      {
         e.printStackTrace();
         Assert.fail(e.getMessage());
      }

   }

}
