package io.logbase.functions.impl;

import io.logbase.functions.FunctionFactory;
import org.junit.Test;

/**
 * Created by Kousik on 07/10/14.
 */
public class GreaterThanTest {
  @Test
  public void test() throws Exception {
    Integer[] testData = {1, 2, 1, 4, 5, 8, 7, 8};
    Boolean[] validMatch = {false, false, false, false, false, true, false, true};
    Integer compareTo = 7;

    ComparatorTest funcTester = new ComparatorTest(testData, compareTo, validMatch,
      FunctionFactory.FunctionOperator.GREATERTHAN);
    funcTester.testFunction();
  }
}
