package io.logbase.functions.impl;

import io.logbase.column.SimpleColumnIterator;
import io.logbase.exceptions.InvalidOperandException;

/**
 * Created by Kousik on 07/10/14.
 */
public class LesserThanEquals<E> extends Comparator {

  public LesserThanEquals(Object[] operands) {
    super(operands);
  }

  @Override
  public Object execute() throws InvalidOperandException {
    SimpleColumnIterator itr = new SimpleColumnIterator(column, column.getRowCount());
    long rowNum = 0;
    Object obj;
    while(itr.hasNext()) {
      obj = itr.next();
      if(obj != null && compareTo(obj) >= 0) {
        rows.append(true, rowNum);
      }
      rowNum++;
    }
    return rows;
  }
}
