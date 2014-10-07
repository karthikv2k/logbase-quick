package io.logbase.functions.impl;

import io.logbase.functions.FunctionFactory;
import org.junit.Test;

/**
 * Created by Kousik on 07/10/14.
 */
public class GreaterThanEqualsTest {
  @Test
  public void test() throws Exception {
    Integer[] testData = {1, 2, 1, 7, 5, 8, 7, 8};
    Boolean[] validMatch = {false, false, false, true, false, true, true, true};
    Integer compareTo = 7;

    ComparatorTest funcTester = new ComparatorTest(testData, compareTo, validMatch,
      FunctionFactory.FunctionOperator.GREATERTHANEQUALS);
    funcTester.testFunction();
  }
}
