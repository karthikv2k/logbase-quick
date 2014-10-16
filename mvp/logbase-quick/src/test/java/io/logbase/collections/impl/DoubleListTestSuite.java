package io.logbase.collections.impl;

import io.logbase.collections.nativelists.DoubleList;
import io.logbase.collections.nativelists.DoubleListIterator;
import io.logbase.collections.nativelists.DoubleListReader;
import io.logbase.collections.nativelists.DoubleListWriter;

import java.util.Arrays;

import static junit.framework.Assert.assertEquals;


/**
 * Created with IntelliJ IDEA file Template.
 * User: karthik
 */
public class DoubleListTestSuite {
  private final BatchListFactory<Double> factory;
  private final double[] testData;

  DoubleListTestSuite(BatchListFactory<Double> factory, double[] testData) {
    this.factory = factory;
    this.testData = testData;
  }

  public void testList() {
    DoubleList list = (DoubleList) factory.newInstance();
    DoubleListWriter writer = list.primitiveWriter();
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
    DoubleListIterator it = list.primitiveIterator(maxIndex);
    double[] buffer;
    long index;

    index = 0;
    buffer = new double[1024];
    while (it.hasNext()) {
      index += it.nextPrimitive(buffer, 0, buffer.length);
      assertEquals(Arrays.toString(testData), Arrays.toString(buffer));
    }
    assertEquals(maxIndex, index);

    it.rewind();
    index = 0;
    buffer = new double[1024];
    while (it.hasNext()) {
      index += it.next(buffer, 0, buffer.length);
      assertEquals(Arrays.toString(testData), Arrays.toString(buffer));
    }
    assertEquals(maxIndex, index);

    maxIndex = list.size();
    it = list.primitiveIterator(maxIndex);
    index = 0;
    buffer = new double[1024];
    while (it.hasNext()) {
      index += it.nextPrimitive(buffer, 0, buffer.length);
      assertEquals(Arrays.toString(testData), Arrays.toString(buffer));
    }
    assertEquals(maxIndex, index);

    it.rewind();
    index = 0;
    buffer = new double[1024];
    while (it.hasNext()) {
      index += it.next(buffer, 0, buffer.length);
      assertEquals(Arrays.toString(testData), Arrays.toString(buffer));
    }
    assertEquals(maxIndex, index);

    //reader test
    maxIndex = list.size() / 4;
    DoubleListReader reader = list.primitiveReader(maxIndex);
    for (int i = 0; i < 100; i++) {
      int j = DataGen.randomInt(0, (int) maxIndex);
      assertEquals(testData[j % testData.length], reader.getAsDouble(j));
    }
  }
}
