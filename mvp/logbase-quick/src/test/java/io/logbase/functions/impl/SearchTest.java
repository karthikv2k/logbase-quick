package io.logbase.functions.impl;

import io.logbase.column.Column;
import io.logbase.column.ColumnFactory;
import io.logbase.column.SimpleColumnIterator;
import io.logbase.column.appendonly.AppendOnlyColumnFactory;
import io.logbase.functions.Functions;
import org.junit.Test;

/**
 * Created by Kousik on 24/09/14.
 */
public class SearchTest {

  @Test
  public void test() {
    Column testColumn;
    ColumnFactory columnFactory = new AppendOnlyColumnFactory();
    String[] values = {" one ", "two", "three", "one ", "two", "three", " one\\", "one"};
    Boolean[] validMatch = {true, false, false, true, false, false, true, true};
    int rowNum = 0;

    //Create a column and append the values
    testColumn = columnFactory.createColumn(String.class, "Test column", 0);
    for (String str: values) {
      testColumn.append(str, rowNum++);
    }

    // Search for occurrence of text - "one"
    Functions func = new Search();
    Object[] operands = {testColumn, "one"};
    func.init(operands);
    func.execute();

    //Validate the result
    Column rowColumn = (Column)func.getOutput();
    SimpleColumnIterator itr = new SimpleColumnIterator(rowColumn, rowColumn.getRowCount());
    while(itr.hasNext()) {
      for (Boolean matches:validMatch) {
        if (matches) {
          // Search should have fetched this row
          assert(itr.next() != null);
        } else {
          assert(itr.next() == null);
        }
      }
    }
  }
}
