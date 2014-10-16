package io.logbase.collections.impl;

import io.logbase.collections.BatchList;
import org.junit.Before;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA File Template.
 * User: karthik
 */
public class DoubleArrayListTest implements BatchListFactory<Double> {
  public static double[] testData;

  @Before
  public void setUp() throws Exception {
    testData = (double[]) DataGen.genRndData(double.class, 1024);
  }

  @Test
  public void testList() {
    DoubleListTestSuite tester = new DoubleListTestSuite(this, testData);
    tester.testList();
  }

  @Override
  public BatchList<Double> newInstance() {
    return new DoubleArrayList();
  }
}
