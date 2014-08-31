package io.logbase.column.readonly;

import io.logbase.collections.BatchListIterator;
import io.logbase.collections.impl.BitPackIntList;
import io.logbase.collections.nativelists.IntList;
import io.logbase.collections.nativelists.IntListIterator;
import io.logbase.collections.nativelists.IntListWriter;
import io.logbase.column.Column;
import io.logbase.column.SimpleColumnIterator;

import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class BitpackIntegerColumn extends AbstractROColumn<Integer> {
  private final IntList values;

  public BitpackIntegerColumn(Column<Integer> column) {
    super(column);
    values = new BitPackIntList(column.getValuesIterator(column.getValuesCount()));
    IntListWriter valuesWriter = values.primitiveWriter();
    ((IntListIterator) column.getValuesIterator(column.getValuesCount())).supplyTo(valuesWriter);
    valuesWriter.close();
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
  public Iterator<Object> getSimpleIterator(long maxRowNum) {
    return new SimpleColumnIterator(this, maxRowNum);
  }

  @Override
  public BatchListIterator<Integer> getValuesIterator(long maxIndex) {
    return values.iterator(maxIndex);
  }
}
