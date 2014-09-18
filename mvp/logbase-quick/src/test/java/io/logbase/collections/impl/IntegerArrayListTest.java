package io.logbase.collections.impl;

import io.logbase.collections.BatchList;
import org.junit.Before;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class IntegerArrayListTest implements BatchListFactory<Integer> {
  public static int[] testData;
  @Before
  public void setUp() throws Exception {
    testData = (int[]) DataGen.genRndData(int.class, 1024);
  }

  @Test
  public void testList(){
    IntListTestSuite tester = new IntListTestSuite(this, testData);
    tester.testList();
  }

  @Override
  public BatchList<Integer> newInstance() {
    return new IntegerArrayList();
  }
}
