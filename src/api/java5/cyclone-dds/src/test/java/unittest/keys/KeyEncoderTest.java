/**
  Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 
  This program and the accompanying materials are made available under the
  terms of the Eclipse Public License v. 2.0 which is available at
  http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
  v. 1.0 which is available at
  http://www.eclipse.org/org/documents/edl-v10.php.
 
  SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */

package unittest.keys;

import one.two.MeteoData;

import org.eclipse.cyclonedds.core.InstanceHandleImpl;
import org.eclipse.cyclonedds.dcps.keys.KeyHashEncoder;
import org.eclipse.cyclonedds.dcps.keys.KeyHashEncoderFactory;
import org.junit.Assert;
import org.junit.Test;


public class KeyEncoderTest
{

   byte[] test1ExpectedkeyHash =
   {
         (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x0c,
         (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01,
         (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
         (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00
   }; // value from opensplice
   byte[] test2ExpectedkeyHash =
   {
         (byte) 0xc5, (byte) 0xeb, (byte) 0x6c, (byte) 0xc7,
         (byte) 0xbe, (byte) 0x1a, (byte) 0x93, (byte) 0x53,
         (byte) 0x99, (byte) 0xc6, (byte) 0x5d, (byte) 0x8c,
         (byte) 0x9a, (byte) 0xae, (byte) 0x53, (byte) 0xa8
   }; // value from opensplice
   byte[] test3ExpectedkeyHash =
   {
         (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x0c,
         (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x0f,
         (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x11,
         (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00
   }; // value from opensplice
   byte[] test4ExpectedkeyHash =
   {
         (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x18,
         (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x20,
         (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x0c,
         (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01
   }; // value from opensplice

   //@Test
   public void keyEncoderTest1()
   {
      @SuppressWarnings("unchecked")
      KeyHashEncoder<one.two.TempSensorType> tempsSensorEncoder = KeyHashEncoderFactory
            .createEncoder(one.two.TempSensorType.class);

      Assert.assertNotNull(tempsSensorEncoder);

      one.two.TempSensorType tempSensor = new one.two.TempSensorType(
            12,
            one.two.TempScale.FARENHEIGHT,
            22.5F,
            12.3F);

      InstanceHandleImpl ih = (InstanceHandleImpl) tempsSensorEncoder.encode(tempSensor);

      Assert.assertNotNull(ih);
      Assert.assertArrayEquals(test1ExpectedkeyHash, ih.getKeyHash());

      /*
       * System.out.println(ih);
       * CDROutputStream outputstream = new CDROutputStream();
       * TempSensorTypeHelper.write(outputstream, tempSensor);
       * InputStream is = outputstream.create_input_stream();
       * byte[] encodedValue;
       * try
       * {
       * encodedValue = new byte[is.available()];
       * is.read(encodedValue);
       * System.out.println(BufferHelper.BufferToReadableString(encodedValue, 0,
       * encodedValue.length));
       * }
       * catch (IOException e)
       * {
       * // NOTHING
       * }
       */
   }

   //@Test
   public void keyEncoderTest2()
   {
      @SuppressWarnings("unchecked")
      KeyHashEncoder<one.two.three.ShapeType> shapeEncoder = KeyHashEncoderFactory.createEncoder(
            one.two.three.ShapeType.class);

      Assert.assertNotNull(shapeEncoder);

      one.two.three.ShapeType shape = new one.two.three.ShapeType(
            "o123456789o123456789o123456789o123456789o123456789o123456789",
            1,
            2,
            3);

      InstanceHandleImpl ih = (InstanceHandleImpl) shapeEncoder.encode(shape);

      Assert.assertNotNull(ih);
      Assert.assertArrayEquals(test2ExpectedkeyHash, ih.getKeyHash());
   }

   //@Test
   public void keyEncoderTest3()
   {
      @SuppressWarnings("unchecked")
      KeyHashEncoder<one.two.SpatialPressureEstimate> spatialPressureEncoder = KeyHashEncoderFactory
            .createEncoder(one.two.SpatialPressureEstimate.class);

      Assert.assertNotNull(spatialPressureEncoder);

      one.two.SpatialPressureEstimate spatialPressure = new one.two.SpatialPressureEstimate(12, 15,
            17, 22.5F);

      InstanceHandleImpl ih = (InstanceHandleImpl) spatialPressureEncoder.encode(spatialPressure);

      Assert.assertNotNull(ih);

      Assert.assertArrayEquals(test3ExpectedkeyHash, ih.getKeyHash());
   }

   //@Test
   public void keyEncoderTest4()
   {
      @SuppressWarnings("unchecked")
      KeyHashEncoder<one.two.FlightPlan> flightPlanEncoder = KeyHashEncoderFactory.createEncoder(
            one.two.FlightPlan.class);

      Assert.assertNotNull(flightPlanEncoder);

      one.two.TempSensorType temp = new one.two.TempSensorType(
            12,
            one.two.TempScale.FARENHEIGHT,
            22.5F,
            12.3F);
      one.two.SpatialPressureEstimate spatialPressure = new one.two.SpatialPressureEstimate(12, 15,
            17, 22.5F);
      one.two.MeteoData meteo = new MeteoData(32, temp, spatialPressure);

      one.two.FlightPlan flightPlan = new one.two.FlightPlan(24, meteo);

      InstanceHandleImpl ih = (InstanceHandleImpl) flightPlanEncoder.encode(flightPlan);

      Assert.assertNotNull(ih);

      Assert.assertArrayEquals(test4ExpectedkeyHash, ih.getKeyHash());
   }
}
