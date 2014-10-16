package io.logbase.collections.impl;

import io.logbase.collections.BatchList;
import org.junit.Before;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA File Template.
 * User: karthik
 */
public class LongArrayListTest implements BatchListFactory<Long> {
  public static long[] testData;

  @Before
  public void setUp() throws Exception {
    testData = (long[]) DataGen.genRndData(long.class, 1024);
  }

  @Test
  public void testList() {
    LongListTestSuite tester = new LongListTestSuite(this, testData);
    tester.testList();
  }

  @Override
  public BatchList<Long> newInstance() {
    return new LongArrayList();
  }
}
