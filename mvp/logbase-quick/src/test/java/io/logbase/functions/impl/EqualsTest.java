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
public class EqualsTest {

  @Test
  public void test() throws Exception{
    Column testColumn;
    ColumnFactory columnFactory = new AppendOnlyColumnFactory();
    Integer[] values = {1, 2, 3, 4, 5, 6, 8, 8};
    Boolean[] validMatch = {false, false, false, false, false, false, true, true};
    int rowNum = 0;

    //Create a column and append the values
    testColumn = columnFactory.createColumn(Integer.class, "Test column", 0);
    for (Integer in: values) {
      testColumn.append(in, rowNum++);
    }

    // Find all entries matching "8"
    FunctionFactory factory = new FunctionFactory();
    Object[] operands = {testColumn, 8};
    Function func = factory.createFunction(
        FunctionFactory.FunctionOperator.EQUALS, operands);
    Column rowColumn = (Column)func.execute();


    //Validate the result
    SimpleColumnIterator itr = new SimpleColumnIterator(rowColumn, rowColumn.getRowCount());
    rowNum = 0;
    while(itr.hasNext()) {
      Boolean value = (Boolean)itr.next();
      if (validMatch[rowNum].booleanValue()) {
        assert(value.booleanValue() == true);
      } else {
        assert(value == null);
      }
      rowNum++;
    }
  }

}
