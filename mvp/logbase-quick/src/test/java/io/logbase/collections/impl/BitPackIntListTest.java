package io.logbase.collections.impl;

import io.logbase.collections.BatchList;
import org.junit.Before;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class BitPackIntListTest implements BatchListFactory<Integer> {
  public static int[] testData;
  int min;
  int max;
  int count = 1000*1000*10;

  @Before
  public void setUp() throws Exception {
    testData = new int[1024];
    min = DataGen.randomInt(0,100);
    max = min + DataGen.randomInt(1,100);
    for(int i=0; i<testData.length; i++){
      testData[i] = DataGen.randomInt(min,max);
    }
  }

  @Test
  public void testList(){
    IntListTestSuite tester = new IntListTestSuite(this, testData);
    tester.testList();
  }

  @Override
  public BatchList<Integer> newInstance() {
    return new BitPackIntList(min, max, count);
  }
}
