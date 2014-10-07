package io.logbase.functions.impl;

import io.logbase.functions.FunctionFactory;
import org.junit.Test;

/**
 * Created by Kousik on 07/10/14.
 */
public class LesserThanEqualsTest {
  @Test
  public void test() throws Exception {
    Integer[] testData = {1, 2, 1, 4, 2, 8, 7, 8};
    Boolean[] validMatch = {true, true, true, false, true, false, false, false};
    Integer compareTo = 2;

    ComparatorTest funcTester = new ComparatorTest(testData, compareTo, validMatch,
      FunctionFactory.FunctionOperator.LESSERTHANEQUALS);
    funcTester.testFunction();
  }
}
