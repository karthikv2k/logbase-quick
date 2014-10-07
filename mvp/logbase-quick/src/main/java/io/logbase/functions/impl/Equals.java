package io.logbase.functions.impl;

import io.logbase.collections.impl.BitsetList;
import io.logbase.column.Column;
import io.logbase.column.SimpleColumnIterator;
import io.logbase.column.appendonly.AppendOnlyColumn;
import io.logbase.functions.Function;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Kousik on 30/09/14.
 */
public class Equals<E> extends Comparator {

  public Equals(Object[] operands) {
    super(operands);
  }

  @Override
  public Object execute() {
    SimpleColumnIterator itr = new SimpleColumnIterator(column, column.getRowCount());
    long rowNum = 0;
    Object obj;
    while(itr.hasNext()) {
      obj = itr.next();
      if(obj != null && compareTo(obj) == 0) {
        rows.append(true, rowNum);
      }
      rowNum++;
    }
    return rows;
  }
}
