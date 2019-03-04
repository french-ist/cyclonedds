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
package org.eclipse.cyclonedds.idl2j.stubs;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.model.Build;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.plugin.testing.stubs.MavenProjectStub;
import org.codehaus.plexus.util.ReaderFactory;


/**
 * This stub class will be used to initialize project variable in IDLMojo
 * in context of IDLMojoTest.
 * In pom.xml test, following configuration value should be set:
 * <project implementation="org.eclipse.cyclonedds.idl2j.stubs.IDLProjectStub"/>
 */
public class IDLProjectStub
   extends MavenProjectStub
{

   public IDLProjectStub()
   {
      MavenXpp3Reader pomReader = new MavenXpp3Reader();
      Model model;
      try
      {
         model = pomReader.read(ReaderFactory.newXmlReader(new File(getBasedir(), "pom.xml")));
         setModel(model);
      }
      catch (Exception e)
      {
         throw new RuntimeException(e);
      }

      setGroupId(model.getGroupId());
      setArtifactId(model.getArtifactId());
      setVersion(model.getVersion());
      setName(model.getName());
      setUrl(model.getUrl());
      setPackaging(model.getPackaging());

      Build build = new Build();
      build.setFinalName(model.getArtifactId());
      build.setDirectory(getBasedir() + "/target");
      build.setSourceDirectory(getBasedir() + "/src/main/java");
      build.setOutputDirectory(getBasedir() + "/target/classes");
      build.setTestSourceDirectory(getBasedir() + "/src/test/java");
      build.setTestOutputDirectory(getBasedir() + "/target/test-classes");
      setBuild(build);

      List<String> compileSourceRoots = new ArrayList<String>();
      compileSourceRoots.add(getBasedir() + "/src/main/java");
      setCompileSourceRoots(compileSourceRoots);

      List<String> testCompileSourceRoots = new ArrayList<String>();
      testCompileSourceRoots.add(getBasedir() + "/src/test/java");
      setTestCompileSourceRoots(testCompileSourceRoots);
   }

   /** {@inheritDoc} */
   public File getBasedir()
   {
      // base directory of test pom.xml
      return new File(super.getBasedir() + "/src/test/resources/");
   }

}
