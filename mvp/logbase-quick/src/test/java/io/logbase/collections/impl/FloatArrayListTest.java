package io.logbase.collections.impl;

import io.logbase.collections.BatchList;
import org.junit.Before;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA File Template.
 * User: karthik
 */
public class FloatArrayListTest implements BatchListFactory<Float> {
  public static float[] testData;

  @Before
  public void setUp() throws Exception {
    testData = (float[]) DataGen.genRndData(float.class, 1024);
  }

  @Test
  public void testList() {
    FloatListTestSuite tester = new FloatListTestSuite(this, testData);
    tester.testList();
  }

  @Override
  public BatchList<Float> newInstance() {
    return new FloatArrayList();
  }
}
