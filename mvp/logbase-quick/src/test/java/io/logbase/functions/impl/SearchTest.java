package io.logbase.functions.impl;

import io.logbase.column.Column;
import io.logbase.column.ColumnFactory;
import io.logbase.column.SimpleColumnIterator;
import io.logbase.column.appendonly.AppendOnlyColumnFactory;
import io.logbase.functions.Function;
import io.logbase.functions.FunctionFactory;
import org.junit.Test;

/**
 * Created by Kousik on 24/09/14.
 */
public class SearchTest {

  @Test
  public void test() throws Exception{
    Column testColumn;
    ColumnFactory columnFactory = new AppendOnlyColumnFactory();
    String[] values = {"test", "two", "three", "one ", "two", "three", " one\\", "one"};
    Boolean[] validMatch = {false, false, false, true, false, false, true, true};
    int rowNum = 0;

    //Create a column and append the values
    testColumn = columnFactory.createColumn(String.class, "Test column", 0);
    for (String str: values) {
      testColumn.append(str, rowNum++);
    }

    // Search for occurrence of text - "one"

    FunctionFactory factory = new FunctionFactory();
    Object[] operands = {testColumn, "one"};
    Function func = factory.createFunction(FunctionFactory.FunctionOperators.SEARCH, operands);
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
