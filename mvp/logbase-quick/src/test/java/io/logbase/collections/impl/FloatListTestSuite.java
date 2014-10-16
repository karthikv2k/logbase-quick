package io.logbase.collections.impl;

import io.logbase.collections.nativelists.FloatList;
import io.logbase.collections.nativelists.FloatListIterator;
import io.logbase.collections.nativelists.FloatListReader;
import io.logbase.collections.nativelists.FloatListWriter;

import java.util.Arrays;

import static junit.framework.Assert.assertEquals;


/**
 * Created with IntelliJ IDEA file Template.
 * User: karthik
 */
public class FloatListTestSuite {
  private final BatchListFactory<Float> factory;
  private final float[] testData;

  FloatListTestSuite(BatchListFactory<Float> factory, float[] testData) {
    this.factory = factory;
    this.testData = testData;
  }

  public void testList() {
    FloatList list = (FloatList) factory.newInstance();
    FloatListWriter writer = list.primitiveWriter();
    long totalWrites = 0;

    //batch write primitive
    for (int i = 0; i < DataGen.randomInt(10, 20); i++) {
      writer.addPrimitive(testData, 0, testData.length);
      totalWrites += testData.length;
    }
    assertEquals(list.size(), totalWrites);

    //batch write
    for (int i = 0; i < DataGen.randomInt(10, 20); i++) {
      writer.add(testData, 0, testData.length);
      totalWrites += testData.length;
    }
    assertEquals(list.size(), totalWrites);

    //write by object
    for (int i = 0; i < testData.length; i++) {
      writer.add(testData[i]);
      totalWrites++;
    }
    assertEquals(list.size(), totalWrites);

    //write primitive
    for (int i = 0; i < testData.length; i++) {
      writer.add(testData[i]);
      totalWrites++;
    }
    assertEquals(list.size(), totalWrites);

    //iterator test
    long maxIndex = list.size() / 4;
    FloatListIterator it = list.primitiveIterator(maxIndex);
    float[] buffer;
    long index;

    index = 0;
    buffer = new float[1024];
    while (it.hasNext()) {
      index += it.nextPrimitive(buffer, 0, buffer.length);
      assertEquals(Arrays.toString(testData), Arrays.toString(buffer));
    }
    assertEquals(maxIndex, index);

    it.rewind();
    index = 0;
    buffer = new float[1024];
    while (it.hasNext()) {
      index += it.next(buffer, 0, buffer.length);
      assertEquals(Arrays.toString(testData), Arrays.toString(buffer));
    }
    assertEquals(maxIndex, index);

    maxIndex = list.size();
    it = list.primitiveIterator(maxIndex);
    index = 0;
    buffer = new float[1024];
    while (it.hasNext()) {
      index += it.nextPrimitive(buffer, 0, buffer.length);
      assertEquals(Arrays.toString(testData), Arrays.toString(buffer));
    }
    assertEquals(maxIndex, index);

    it.rewind();
    index = 0;
    buffer = new float[1024];
    while (it.hasNext()) {
      index += it.next(buffer, 0, buffer.length);
      assertEquals(Arrays.toString(testData), Arrays.toString(buffer));
    }
    assertEquals(maxIndex, index);

    //reader test
    maxIndex = list.size() / 4;
    FloatListReader reader = list.primitiveReader(maxIndex);
    for (int i = 0; i < 100; i++) {
      int j = DataGen.randomInt(0, (int) maxIndex);
      assertEquals(testData[j % testData.length], reader.getAsFloat(j));
    }
  }
}
