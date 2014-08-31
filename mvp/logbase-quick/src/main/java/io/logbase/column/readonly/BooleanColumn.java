package io.logbase.column.readonly;

import io.logbase.collections.BatchList;
import io.logbase.collections.BatchListIterator;
import io.logbase.collections.impl.BitsetList;
import io.logbase.column.Column;
import io.logbase.column.SimpleColumnIterator;

import java.util.Iterator;

/**
 * Created by Kousik on 23/08/14.
 */
public class BooleanColumn extends AbstractROColumn<Boolean> {
  private final BatchList<Boolean> values;

  /*
   * By default choose BitsetList which uses BitSet
   */
  public BooleanColumn(Column<Boolean> column) {
    super(column);
    values = new BitsetList();
    values.writer().addAll(column.getValuesIterator(column.getValuesCount())).close();
  }

  @Override
  public Class getColumnType() {
    return Boolean.class;
  }

  @Override
  public long getValuesCount() {
    return values.size();
  }

  @Override
  public Iterator<Object> getSimpleIterator(long maxRowNum) {
    return new SimpleColumnIterator(this, maxRowNum);
  }

  @Override
  public BatchListIterator<Boolean> getValuesIterator(long index) {
    return values.iterator(index);
  }

}
