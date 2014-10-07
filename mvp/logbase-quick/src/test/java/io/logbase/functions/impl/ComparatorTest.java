package io.logbase.functions.impl;

import io.logbase.column.Column;
import io.logbase.column.ColumnFactory;
import io.logbase.column.SimpleColumnIterator;
import io.logbase.functions.Function;
import io.logbase.functions.FunctionFactory;
import org.junit.Test;

/**
 * Created by Kousik on 07/10/14.
 */
public class ComparatorTest {

  private Integer[] values;
  private Boolean[] validMatch;
  private FunctionFactory.FunctionOperator operator;
  private Integer compareTo;

  public ComparatorTest(Integer[] testData, Integer compareTo,
                        Boolean[] validMatch, FunctionFactory.FunctionOperator operator) {
    this.values = testData;
    this.validMatch = validMatch;
    this.operator = operator;
    this.compareTo = compareTo;
  }

  public void testFunction() throws Exception {
    Column testColumn;
    ColumnFactory columnFactory = ColumnFactory.INSTANCE;
    int rowNum = 0;

    //Create a column and append the values
    testColumn = columnFactory.createAppendOnlyColumn(Integer.class, "Test column", 0);
    for (Integer in : values) {
      testColumn.append(in, rowNum++);
    }

    //Execute the function
    FunctionFactory factory = new FunctionFactory();
    Object[] operands = {testColumn, compareTo};
    Function func = factory.createFunction(operator, operands);
    Column rowColumn = (Column) func.execute();


    //Validate the result
    SimpleColumnIterator itr = new SimpleColumnIterator(rowColumn, rowColumn.getRowCount());
    rowNum = 0;
    while (itr.hasNext()) {
      Boolean value = (Boolean) itr.next();
      if (validMatch[rowNum].booleanValue()) {
        assert (value.booleanValue() == true);
      } else {
        assert (value == null);
      }
      rowNum++;
    }
  }
}
