package io.logbase.functions.impl;

import io.logbase.column.Column;
import io.logbase.column.ColumnFactory;
import io.logbase.column.SimpleColumnIterator;
import io.logbase.functions.Function;
import io.logbase.functions.FunctionFactory;
import org.junit.Test;

/**
 * Created by Kousik on 30/09/14.
 */
public class EqualsTest {
  @Test
  public void test() throws Exception {
    Integer[] testData = {1, 2, 1, 4, 5, 8, 7, 8};
    Boolean[] validMatch = {false, false, false, false, false, false, true, false};
    Integer compareTo = 7;

    ComparatorTest funcTester = new ComparatorTest(testData, compareTo, validMatch,
      FunctionFactory.FunctionOperator.EQUALS);
    funcTester.testFunction();
  }
}
