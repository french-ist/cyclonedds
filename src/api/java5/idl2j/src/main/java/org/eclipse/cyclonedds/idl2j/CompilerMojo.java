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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.eclipse.cyclonedds.JavaGeneratorHelper;


/**
 * Vortex Cafe IDL Compiler.
 * 
 * @goal idl-compile
 * @phase generate-sources
 * @requiresDependencyResolution test
 */
public class CompilerMojo
   extends AbstractMojo
{

   /**
    * The Maven project
    * 
    * @parameter property="project"
    * @required
    * @readonly
    */
   private MavenProject project;

   /**
    * Location of the outputDirectory.
    * 
    * @parameter property="idl2j.outDir"
    *            default-value="${project.build.directory}/generated-sources/idl"
    */
   private File outDir;

   /**
    * include directories
    * 
    * @parameter
    */
   private File[] includeDirs;

   /**
    * IDL Directory
    * 
    * @parameter property="idl2j.idlDir"
    *            default-value="${basedir}/src/main/idl"
    */
   private File idlDir;

   /**
    * mode can be verbose (-v)
    * 
    * @parameter property="idl2j.mode" default-value=""
    */
   private String mode;

   /**
    * Default package prefix
    * 
    * @parameter property="idl2j.defaultPkgPrefix" default-value=""
    */
   private String defaultPkgPrefix;

   /**
    * Creates the IDL compiler command and processes every IDL
    * 
    * @throws MojoExecutionException
    */
   public void execute() throws MojoExecutionException
   {
      // check idlDir
      if ( !idlDir.exists() || !idlDir.isDirectory())
      {
         getLog().error("Can't find <idlDir> directory: "
               + idlDir.getAbsolutePath());
         throw new MojoExecutionException("Can't find <idlDir> directory: "
               + idlDir.getAbsolutePath());
      }

      // list all .idl files in idlDir recursively
      getLog().info("Search for idl files in " + idlDir.getAbsolutePath());
      Collection<File> idlFiles = FileUtils.listFiles(
            idlDir, new String[]
            {
                  "idl", "IDL"
            }, true);
      if (idlFiles.isEmpty())
      {
         getLog().warn("Can't find any IDL file in "
               + idlDir.getAbsolutePath());
         return;
      }

      getLog().debug("FOUND " + idlFiles.size() + " idl files:");
      for (File f : idlFiles)
      {
         getLog().debug("   " + f.getAbsolutePath());
      }

      // check outDir (create it if it doesn't exist)
      if ( !outDir.exists())
      {
         getLog().debug("Create outDir: " + outDir.getAbsolutePath());
         if ( !outDir.mkdirs())
         {
            throw new MojoExecutionException("Failed to create <outDir> directory: "
                  + outDir.getAbsolutePath());
         }
      }

      // prepare Compiler and arguments
      Compiler comp = new Compiler(getLog());
      String args[] = prepareCompilerArgs();

      getLog().debug("Compiler (idlj) arguments will be:");
      for (String arg : args)
      {
         getLog().debug("   " + arg);
      }

      // compile each IDL file
      for (File idlFile : idlFiles)
      {
         getLog().info("Compiling idl file: " + idlFile.getAbsolutePath());
         // set path to IDL file as last argument
         args[args.length - 1] = idlFile.getAbsolutePath();
         try
         {
            // run Compiler
        	comp = new Compiler(getLog());
            comp.parseArgs(args);
            getLog().debug("   run idlj");
            comp.runIdlj();
            getLog().debug("   patch generated code");
            comp.patchGeneratedCode();
            if (defaultPkgPrefix != null && !defaultPkgPrefix.isEmpty())
            {
               getLog().debug("   patch non-scoped generated code");
               comp.patchNonScopedGeneratedCode();
            }
            
            String pathIdlFile = args[args.length - 1];
            new JavaGeneratorHelper(pathIdlFile.replace(".idl", ""), comp.destDir, (new File(pathIdlFile)).getName().replace(".idl", ""), comp.className);
            
            getLog().debug("   Done!");
         }
         catch (Exception e)
         {
            throw new MojoExecutionException("", e);
         }
      }

      // add generated source dir to compile source root
      getLog().debug("Add " + outDir.getAbsolutePath() + " as source directory");
      project.addCompileSourceRoot(outDir.getAbsolutePath());
   }

   /**
    * Return the array of arguments to be passed to Compiler. Note that an
    * extra null element is added at the end of the array for
    * 
    * @return the array of arguments
    */
   private String[] prepareCompilerArgs() throws MojoExecutionException
   {
      ArrayList<String> arguments = new ArrayList<String>();

      // add path to idlj as first argument
      arguments.add(getIdljPath());

      // outDir
      arguments.add("-td");
      arguments.add(outDir.getAbsolutePath());

      // includeDirs
      if (includeDirs != null && includeDirs.length > 0)
      {
         for (int x = 0; x < includeDirs.length; x++)
         {
            arguments.add("-i");
            arguments.add(includeDirs[x].getAbsolutePath());
         }
      }

      // mode
      if (mode != null && mode.contains("-v"))
      {
         arguments.add(mode);
      }

      // default pkg prefix
      if (defaultPkgPrefix != null && !defaultPkgPrefix.isEmpty())
      {
         arguments.add("-defaultPkgPrefix");
         arguments.add(defaultPkgPrefix);
      }

      // Return array of arguments, adding an element for IDL filename
      // (to be set by caller)
      String[] result = new String[arguments.size() + 1];
      return arguments.toArray(result);
   }

   /**
    * @return Absolute Path to the IDL Compiler
    * @param executable the idl executable
    * @throws MojoExecutionException
    */
   protected String getIdljPath() throws MojoExecutionException
   {
      Map<String, String> env = System.getenv();
      String JAVA_HOME_VAR = env.get("JAVA_HOME");
      if (null == JAVA_HOME_VAR)
      {
         //TODO manage windows case throw new MojoExecutionException("JAVA_HOME not set");
         return "idlj";
      }
      return JAVA_HOME_VAR + File.separatorChar + "bin" + File.separatorChar + "idlj";
   }

}
