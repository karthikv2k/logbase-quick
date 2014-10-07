package io.logbase.functions.impl;

import io.logbase.functions.FunctionFactory;
import org.junit.Test;

/**
 * Created by Kousik on 07/10/14.
 */
public class LesserThanTest {
  @Test
  public void test() throws Exception {
    Integer[] testData = {1, 2, 1, 4, 5, 8, 7, 1};
    Boolean[] validMatch = {true, false, true, false, false, false, false, true};
    Integer compareTo = 2;

    ComparatorTest funcTester = new ComparatorTest(testData, compareTo, validMatch,
      FunctionFactory.FunctionOperator.LESSERTHAN);
    funcTester.testFunction();
  }
}
