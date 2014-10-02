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
public class ANDTest {
  // TODO generalise function test cases
  @Test
  public void test() throws Exception{
    Column testColumnA;
    Column testColumnB;
    ColumnFactory columnFactory = ColumnFactory.INSTANCE;
    Boolean[] valuesA = {true, false, false, false, true, false, false, false};
    Boolean[] valuesB = {false, false, false, false, true, false, false, true};

    Boolean[] validMatch = {false, false, false, false, true, false, false, false};
    int rowNum = 0;

    //Create a column and append the values
    testColumnA = columnFactory.createAppendOnlyColumn(Boolean.class, "Test columnA", 0);
    testColumnB = columnFactory.createAppendOnlyColumn(Boolean.class, "Test columnB", 0);

    for (int i=0; i<valuesA.length; i++) {
      testColumnA.append(valuesA[i].booleanValue(), rowNum);
      testColumnB.append(valuesB[i].booleanValue(), rowNum);
      rowNum++;
    }

    // Execute the function
    FunctionFactory factory = new FunctionFactory();
    Object[] operands = {testColumnA, testColumnB};
    Function func = factory.createFunction(
        FunctionFactory.FunctionOperator.AND, operands);
    Column rowColumn = (Column)func.execute();

    //Validate the result
    SimpleColumnIterator itr = new SimpleColumnIterator(rowColumn, rowColumn.getRowCount());
    rowNum = 0;
    while(itr.hasNext()) {
      Boolean value = (Boolean)itr.next();
      if (validMatch[rowNum].booleanValue()) {
        assert(value.booleanValue());
      } else {
        assert(value == null);
      }
      rowNum++;
    }
  }
}
