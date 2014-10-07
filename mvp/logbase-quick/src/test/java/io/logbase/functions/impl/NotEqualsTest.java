package io.logbase.functions.impl;

import io.logbase.functions.FunctionFactory;
import org.junit.Test;

/**
 * Created by Kousik on 07/10/14.
 */
public class NotEqualsTest {
  @Test
  public void test() throws Exception {
    Integer[] testData = {1, 2, 1, 4, 8, 8, 7, 1};
    Boolean[] validMatch = {true, true, true, true, false, false, true, true};
    Integer compareTo = 8;

    ComparatorTest funcTester = new ComparatorTest(testData, compareTo, validMatch,
      FunctionFactory.FunctionOperator.NOTEQUALS);
    funcTester.testFunction();
  }
}
