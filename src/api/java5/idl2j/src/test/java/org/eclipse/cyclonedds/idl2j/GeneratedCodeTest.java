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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;


public class GeneratedCodeTest
{

   private static final String GEN_DIR = "target/generated-test-sources";
   private static final String DEFAULT_PKG_PREFIX = "default_pkg_prefix";

   // check code generated for test.idl file
   @Test
   public final void checkTestIDL()
   {
      // Shapes
      checkFileExists("one/two/three/Shapes.java");
      checkFileExists("one/two/three/ShapesHelper.java");
      checkFileExists("one/two/three/ShapesHolder.java");

      // ShapeType
      checkJavaFileContainsKeyList("one/two/three/ShapeType.java",
            "ShapeType", "color");
      checkFileExists("one/two/three/ShapeTypeHelper.java");
      checkFileExists("one/two/three/ShapeTypeHolder.java");

      // TempScale
      checkFileExists("one/two/TempScale.java");
      checkFileExists("one/two/TempScaleHelper.java");
      checkFileExists("one/two/TempScaleHolder.java");

      // TempSensorType
      checkJavaFileContainsKeyList("one/two/TempSensorType.java",
            "TempSensorType", "id", "scale");
      checkFileExists("one/two/TempSensorTypeHelper.java");
      checkFileExists("one/two/TempSensorTypeHolder.java");

      // SpatialPressureEstimate
      checkJavaFileContainsKeyList("one/two/SpatialPressureEstimate.java",
            "SpatialPressureEstimate", "x", "y", "z");
      checkFileExists("one/two/SpatialPressureEstimateHelper.java");
      checkFileExists("one/two/SpatialPressureEstimateHolder.java");

      // MeteoData
      checkFileExists("one/two/MeteoData.java");
      checkFileExists("one/two/MeteoDataHelper.java");
      checkFileExists("one/two/MeteoDataHolder.java");

      // FlightPlan
      checkJavaFileContainsKeyList("one/two/FlightPlan.java",
            "FlightPlan", "id", "meteo.id", "meteo.temp.id", "meteo.temp.scale");
      checkFileExists("one/two/FlightPlanHelper.java");
      checkFileExists("one/two/FlightPlanHolder.java");

      // Dummy
      checkJavaFileContainsKeyList("one/two/Dummy.java",
            "Dummy", "m_ushort", "m_ulong", "longlong_m", "m_ulonglong", "long_1");
      checkFileExists("one/two/DummyHelper.java");
      checkFileExists("one/two/DummyHolder.java");

      // BinaryFile
      checkJavaFileContainsKeyList("one/two/BinaryFile.java",
            "BinaryFile", "name");
      checkFileExists("one/two/BinaryFileHelper.java");
      checkFileExists("one/two/BinaryFileHolder.java");

      // TestStruct
      checkJavaFileContainsKeyList("one/two/TestStruct.java",
            "TestStruct", "struct_member1");
      checkFileExists("one/two/TestStructHelper.java");
      checkFileExists("one/two/TestStructHolder.java");

      // MyKeylessTopic
      checkJavaFileContainsEmptyKeyList("one/MyKeylessTopic.java",
            "MyKeylessTopic");
      checkFileExists("one/MyKeylessTopicHelper.java");
      checkFileExists("one/MyKeylessTopicHolder.java");

      // MyStructWithoutPragma
      checkJavaFileContainsNoKeyList("one/MyStructWithoutPragma.java");
      checkFileExists("one/MyStructWithoutPragmaHelper.java");
      checkFileExists("one/MyStructWithoutPragmaHolder.java");

      // TestScopedTypes
      checkJavaFileContainsKeyList("one/TestScopedTypes.java",
            "TestScopedTypes", "key1", "key2");
      checkFileExists("one/TestScopedTypesHelper.java");
      checkFileExists("one/TestScopedTypesHolder.java");

      // TestUnionWithScopedTypes
      checkFileExists("one/TestUnionWithScopedTypes.java");
      checkFileExists("one/TestUnionWithScopedTypesHelper.java");
      checkFileExists("one/TestUnionWithScopedTypesHolder.java");


      // MetricValue
      checkJavaFileContainsKeyList("org/opensplice/agent/metric/MetricValue.java",
            "MetricValue", "timestamp.a", "timestamp2.b");
      checkFileExists("org/opensplice/agent/metric/MetricValueHelper.java");
      checkFileExists("org/opensplice/agent/metric/MetricValueHolder.java");
   }

   // check code generated for dopgirf11.idl file
   @Test
   public final void checkDopgirf11IDL()
   {
      // C_struct
      checkJavaFileContainsKeyList(DEFAULT_PKG_PREFIX + "/C_struct.java",
            "C_struct", "e_short", "e_ushort", "e_octet");
      checkFileExists(DEFAULT_PKG_PREFIX + "/C_structHelper.java");
      checkFileExists(DEFAULT_PKG_PREFIX + "/C_structHolder.java");
      checkFileNotExists("C_struct.java");
      checkFileNotExists("C_structHelper.java");
      checkFileNotExists("C_structHolder.java");
   }

   // check code generated for RnR.idl file
   @Test
   public final void checkRnRIDL()
   {
      // ValueKind
      checkFileExists("RnR/ValueKind.java");
      checkFileExists("RnR/ValueKindHelper.java");
      checkFileExists("RnR/ValueKindHolder.java");
      // Value
      checkFileExists("RnR/Value.java");
      checkFileExists("RnR/ValueHelper.java");
      checkFileExists("RnR/ValueHolder.java");
      // Condition
      checkFileExists("RnR/Condition.java");
      checkFileExists("RnR/ConditionHelper.java");
      checkFileExists("RnR/ConditionHolder.java");
      // KeyValue
      checkFileExists("RnR/KeyValue.java");
      checkFileExists("RnR/KeyValueHelper.java");
      checkFileExists("RnR/KeyValueHolder.java");
      // AddRecordCommand
      checkFileExists("RnR/AddRecordCommand.java");
      checkFileExists("RnR/AddRecordCommandHelper.java");
      checkFileExists("RnR/AddRecordCommandHolder.java");
      // Kind
      checkFileExists("RnR/Kind.java");
      checkFileExists("RnR/KindHelper.java");
      checkFileExists("RnR/KindHolder.java");
      // Command
      checkJavaFileContainsKeyList("RnR/Command.java",
            "Command", "scenarioName");
      checkFileExists("RnR/CommandHelper.java");
      checkFileExists("RnR/CommandHolder.java");
      // ServiceStatus
      checkJavaFileContainsKeyList("RnR/ServiceStatus.java",
            "ServiceStatus", "rnrId");
      checkFileExists("RnR/ServiceStatusHelper.java");
      checkFileExists("RnR/ServiceStatusHolder.java");
      // ScenarioStatus
      checkJavaFileContainsKeyList("RnR/ScenarioStatus.java",
            "ScenarioStatus", "scenarioName", "rnrId");
      checkFileExists("RnR/ScenarioStatusHelper.java");
      checkFileExists("RnR/ScenarioStatusHolder.java");
      // StorageStatus
      checkJavaFileContainsKeyList("RnR/StorageStatus.java",
            "StorageStatus", "storageName", "rnrId");
      checkFileExists("RnR/StorageStatusHelper.java");
      checkFileExists("RnR/StorageStatusHolder.java");
      // StorageStatistics
      checkJavaFileContainsKeyList("RnR/StorageStatistics.java",
            "StorageStatistics", "storageName", "rnrId");
      checkFileExists("RnR/StorageStatisticsHelper.java");
      checkFileExists("RnR/StorageStatisticsHolder.java");

      // AddReplayCommand
      checkFileExists("RnR_V2/AddReplayCommand.java");
      checkFileExists("RnR_V2/AddReplayCommandHelper.java");
      checkFileExists("RnR_V2/AddReplayCommandHolder.java");
      // Kind
      checkFileExists("RnR_V2/Kind.java");
      checkFileExists("RnR_V2/KindHelper.java");
      checkFileExists("RnR_V2/KindHolder.java");
      // Command
      checkJavaFileContainsKeyList("RnR/Command.java",
            "Command", "scenarioName");
      checkFileExists("RnR/CommandHelper.java");
      checkFileExists("RnR/CommandHolder.java");

   }

   // check code generated for dopgirf11.idl file
   @Test
   public final void checkNonScopedShapeTypeIDL()
   {
      checkFileNotExists("ShapeType.java");
      checkFileNotExists("ShapeTypeHelper.java");
      checkFileNotExists("ShapeTypeHolder.java");
      checkFileExists(DEFAULT_PKG_PREFIX + "/ShapeType.java");
      checkFileExists(DEFAULT_PKG_PREFIX + "/ShapeTypeHelper.java");
      checkFileExists(DEFAULT_PKG_PREFIX + "/ShapeTypeHolder.java");
   }

   /**
    * check if IDL files in WEB-125 dir have been compiled
    */
   // @Test
   public final void checkWEB125()
   {
      // test that typedef with scoped-name and #ifdef
      checkJavaFileContainsKeyList("com/prismtech/cafe/demo/idl/ChatMessage.java",
            "ChatMessage", "user");
      checkFileExists("com/prismtech/cafe/demo/idl/ChatMessageHelper.java");
      checkFileExists("com/prismtech/cafe/demo/idl/ChatMessageHolder.java");

      // test recursive search of idl files
      checkJavaFileContainsKeyList("com/prismtech/cafe/demo/subDir1/subDir2/Address.java",
            "Address", "country", "zip");
      checkFileExists("com/prismtech/cafe/demo/subDir1/subDir2/AddressHelper.java");
      checkFileExists("com/prismtech/cafe/demo/subDir1/subDir2/AddressHolder.java");
   }


   private File checkFileExists(String file)
   {
      File f = new File(GEN_DIR + File.separatorChar + file);
      Assert.assertTrue("File '" + f + "' has not been generated.", f.exists());
      return f;
   }

   private File checkFileNotExists(String file)
   {
      File f = new File(GEN_DIR + File.separatorChar + file);
      Assert.assertFalse("File '" + f + "' has not been moved.", f.exists());
      return f;
   }


   private void checkJavaFileContainsKeyList(String file, String topicType, String... keys)
   {
      File f = checkFileExists(file);

      String expectedTopicTypeDecl = "    topicType = \"" + topicType + "\"";
      String expectedKeysDecl = "    keys = {";
      for (int i = 0; i < keys.length; i++)
      {
         expectedKeysDecl += "\"" + keys[i] + "\"";
         if (i != keys.length - 1)
            expectedKeysDecl += ", ";
      }
      expectedKeysDecl += '}';

      boolean keyListPresent = false;
      try
      {
         BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(f),
               "UTF-8"));
         String line = in.readLine();
         while (line != null)
         {
            if (line.contains("@KeyList("))
            {

               line = in.readLine();
               if ( (null == line) || ( !line.contains(expectedTopicTypeDecl)))
               {
                  break;
               }
               line = in.readLine();
               if ( (null == line) || ( !line.contains(expectedKeysDecl)))
               {
                  break;
               }
               line = in.readLine();
               if ( (null == line) || ( !line.contains(")")))
               {
                  break;
               }
               keyListPresent = true;
               break;
            }
            line = in.readLine();
         }
         in.close();
      }
      catch (FileNotFoundException e)
      {
         Assert.fail(e.getMessage());
      }
      catch (IOException e)
      {
         Assert.fail(e.getMessage());
      }
      Assert.assertTrue("In " + file + ": can't find expected KeyList for '" + topicType
            + "' with keys " + Arrays.toString(keys),
            keyListPresent);
   }

   private void checkJavaFileContainsNoKeyList(String file)
   {
      File f = checkFileExists(file);

      try
      {
         BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(f),
               "UTF-8"));
         String line = in.readLine();
         while (line != null)
         {
            if (line.contains("@KeyList("))
            {
               Assert.fail("In " + file + ": found unexpected @KeyList annotation.");
            }
            line = in.readLine();
         }
         in.close();
      }
      catch (FileNotFoundException e)
      {
         Assert.fail(e.getMessage());
      }
      catch (IOException e)
      {
         Assert.fail(e.getMessage());
      }
   }

   private void checkJavaFileContainsEmptyKeyList(String file, String topicType)
   {
      File f = checkFileExists(file);

      String expectedTopicTypeDecl = "    topicType = \"" + topicType + "\"";
      String expectedKeysDecl = "    keys = {}";

      boolean keyListPresent = false;
      try
      {
         BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(f),
               "UTF-8"));
         String line = in.readLine();
         while (line != null)
         {
            if (line.contains("@KeyList("))
            {
               line = in.readLine();
               if ( (null == line) || ( !line.contains(expectedTopicTypeDecl)))
               {
                  break;
               }
               line = in.readLine();
               if ( (null == line) || ( !line.contains(expectedKeysDecl)))
               {
                  break;
               }
               line = in.readLine();
               if ( (null == line) || ( !line.contains(")")))
               {
                  break;
               }
               keyListPresent = true;
               break;
            }
            line = in.readLine();
         }
         in.close();
      }
      catch (FileNotFoundException e)
      {
         Assert.fail(e.getMessage());
      }
      catch (IOException e)
      {
         Assert.fail(e.getMessage());
      }
      Assert.assertTrue("In " + file + ": can't find expected KeyList for '" + topicType + "'",
            keyListPresent);
   }


}
