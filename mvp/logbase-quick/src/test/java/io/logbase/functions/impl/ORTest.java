package io.logbase.functions.impl;

import io.logbase.column.Column;
import io.logbase.column.ColumnFactory;
import io.logbase.column.SimpleColumnIterator;
import io.logbase.column.appendonly.AppendOnlyColumnFactory;
import io.logbase.functions.Function;
import io.logbase.functions.FunctionFactory;
import org.junit.Test;

/**
 * Created by Kousik on 30/09/14.
 */
public class ORTest {
  @Test
  public void test() throws Exception{
    Column testColumnA;
    Column testColumnB;
    ColumnFactory columnFactory = new AppendOnlyColumnFactory();
    Boolean[] valuesA = {true, false, false, false, true, false, false, false};
    Boolean[] valuesB = {false, false, false, false, true, false, false, true};

    Boolean[] validMatch = {true, false, false, false, true, false, false, true};
    int rowNum = 0;

    //Create a column and append the values
    testColumnA = columnFactory.createColumn(Boolean.class, "Test columnA", 0);
    testColumnB = columnFactory.createColumn(Boolean.class, "Test columnB", 0);

    for (int i=0; i<valuesA.length; i++) {
      testColumnA.append(valuesA[i].booleanValue(), rowNum);
      testColumnB.append(valuesB[i].booleanValue(), rowNum);
      rowNum++;
    }

    // Execute the function
    FunctionFactory factory = new FunctionFactory();
    Object[] operands = {testColumnA, testColumnB};
    Function func = factory.createFunction(FunctionFactory.FunctionOperator.OR,
        operands);
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
