package io.logbase.column.readonly;

import io.logbase.collections.BatchIterator;
import io.logbase.collections.impl.BitPackIntBuffer;
import io.logbase.collections.impl.BitPackIntList;
import io.logbase.collections.impl.BitPackIntListIterator;
import io.logbase.column.Column;
import io.logbase.column.ColumnIterator;
import io.logbase.column.SimpleColumnIterator;

import java.util.Iterator;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class BitpackIntegerColumn extends AbstractROColumn<Integer>{
  private final BitPackIntList values;

  public BitpackIntegerColumn(Column<Integer> column){
    super(column);
    values = new BitPackIntList(column.getValuesIterator());
    values.addAll(column.getValuesIterator());
    values.close();
    checkArgument(values.getBuffer().getSize() == values.getBuffer().listSize, "list's final size doesn't match the " +
      "initial expected size");
  }

  @Override
  public Class getColumnType() {
    return Integer.class;
  }

  @Override
  public long getValuesCount() {
    return values.getBuffer().getSize();
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
    return values.batchIterator(values.sizeAsLong());
  }
}
