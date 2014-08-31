package io.logbase.collections.impl;

import io.logbase.collections.nativelists.IntList;
import io.logbase.collections.nativelists.IntListIterator;
import io.logbase.collections.nativelists.IntListReader;
import io.logbase.collections.nativelists.IntListWriter;
import junit.framework.Test;

import java.util.Arrays;

import static junit.framework.Assert.assertEquals;


/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class IntListTest {
  private final BatchListFactory<Integer> factory;
  private final int[] testData;

  IntListTest(BatchListFactory<Integer> factory, int[] testData){
    this.factory = factory;
    this.testData = testData;
  }

  public void testList(){
    IntList list = (IntList) factory.newInstance();
    IntListWriter writer = list.primitiveWriter();
    long totalWrites=0;

    //batch write primitive
    for(int i=0; i<DataGen.randomInt(10,20); i++){
      writer.addPrimitive(testData, 0, testData.length);
      totalWrites += testData.length;
    }
    assertEquals(list.size(), totalWrites);

    //batch write
    for(int i=0; i<DataGen.randomInt(10,20); i++){
      writer.add(testData, 0, testData.length);
      totalWrites += testData.length;
    }
    assertEquals(list.size(), totalWrites);

    //write by object
    for(int i=0; i<testData.length; i++){
      writer.add(testData[i]);
      totalWrites ++;
    }
    assertEquals(list.size(), totalWrites);

    //write primitive
    for(int i=0; i<testData.length; i++){
      writer.add(testData[i]);
      totalWrites ++;
    }
    assertEquals(list.size(), totalWrites);

    //iterator test
    long maxIndex = list.size()/4;
    IntListIterator it = list.primitiveIterator(maxIndex);
    int[] buffer = new int[1024];
    long index;

    index = 0;
    while(it.hasNext()){
      index += it.nextPrimitive(buffer, 0, buffer.length);
      assertEquals(Arrays.toString(testData),Arrays.toString(buffer));
    }
    assertEquals(maxIndex, index);

    it.reset();
    index = 0;
    while(it.hasNext()){
      index += it.next(buffer, 0, buffer.length);
      assertEquals(Arrays.toString(testData),Arrays.toString(buffer));
    }
    assertEquals(maxIndex, index);

    maxIndex = list.size();
    it = list.primitiveIterator(maxIndex);
    index = 0;
    while(it.hasNext()){
      index += it.nextPrimitive(buffer, 0, buffer.length);
      assertEquals(Arrays.toString(testData),Arrays.toString(buffer));
    }
    assertEquals(maxIndex, index);

    it.reset();
    index = 0;
    while(it.hasNext()){
      index += it.next(buffer, 0, buffer.length);
      assertEquals(Arrays.toString(testData),Arrays.toString(buffer));
    }
    assertEquals(maxIndex, index);

    //reader test
    maxIndex = list.size()/4;
    IntListReader reader = list.primitiveReader(maxIndex);
    for(int i=0;i<100;i++){
      int j = DataGen.randomInt(0, (int)maxIndex);
      assertEquals(testData[j%testData.length], reader.getAsInt(j));
    }
  }

}
