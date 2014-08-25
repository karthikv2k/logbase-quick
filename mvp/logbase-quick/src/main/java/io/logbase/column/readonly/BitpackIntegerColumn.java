package io.logbase.column.readonly;

import io.logbase.collections.BatchIterator;
import io.logbase.collections.BatchList;
import io.logbase.collections.impl.BitPackIntListWriter;
import io.logbase.column.Column;
import io.logbase.column.ColumnIterator;
import io.logbase.column.SimpleColumnIterator;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class BitpackIntegerColumn extends AbstractROColumn<Integer>{
  private final BatchList<Integer> values;

  public BitpackIntegerColumn(Column<Integer> column){
    super(column);
    values = new BitPackIntListWriter(column.getValuesIterator());
    values.addAll(column.getValuesIterator());
    values.close();
  }

  @Override
  public Class getColumnType() {
    return Integer.class;
  }

  @Override
  public long getValuesCount() {
    return values.size();
  }

  @Override
  public ColumnIterator<Object> getSimpleIterator(long maxRowNum) {
    return new SimpleColumnIterator(this, maxRowNum);
  }

  @Override
  public ColumnIterator<Object> getSimpleIterator() {
    return new SimpleColumnIterator(this, getRowCount());
  }

  @Override
  public BatchIterator<Integer> getValuesIterator() {
    return values.batchIterator(values.size());
  }
}
