package io.logbase.functions.impl;

import io.logbase.collections.impl.NativeBitSet;
import io.logbase.column.SimpleColumnIterator;

/**
 * Created by Kousik on 07/10/14.
 */
public class NotEquals<E> extends Comparator {

  public NotEquals(Object[] operands) {
    super(operands);
  }

  @Override
  public Object execute() {
    SimpleColumnIterator itr = new SimpleColumnIterator(column, column.getRowCount());
    long rowNum = 0;
    Object obj;
    while(itr.hasNext()) {
      obj = itr.next();
      if(obj != null && compareTo(obj) != 0) {
        rows.append(true, rowNum);
      }
      rowNum++;
    }
    return rows;
  }
}
