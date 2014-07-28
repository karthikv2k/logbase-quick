package io.logbase.node.impl.realtime.data.columns;

import io.logbase.datamodel.Column;
import io.logbase.datamodel.ColumnIterator;
import io.logbase.datamodel.ColumnType;
import io.logbase.datamodel.types.NullType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

public class ListBackedColumn<E extends ColumnType> implements Column<E> {

  private String name;
  private List values = new ArrayList(10000);
  private List<Boolean> isNull = new ArrayList<Boolean>(10000);
  private List<Integer>[] arrayIdx;
  private List<Integer> arraySize = new ArrayList<Integer>(10000);
  private long startRowNum = -1;
  private long maxRowNum = -1;
  private int maxRowArraySize = 0;

  public ListBackedColumn(String name, int numArrays) {
    this.name = name;
    arrayIdx = new List[numArrays];
    for (int i = 0; i < arrayIdx.length; i++) {
      arrayIdx[i] = new ArrayList<Integer>(10000);
    }
  }

  @Override
  public long getStartRowNum() {
    return startRowNum;
  }

  @Override
  public String getColumnName() {
    return name;
  }

  @Override
  public Class getColumnType() {
    return null;
  }

  @Override
  public void append(E value, long rowNum) {
    checkArgument(this.arrayIdx.length == 0, "Can't append without arrayIdx");
    checkArgument(value != null && (value.get() != null || value instanceof NullType), "Value can't be null");
    values.add(value.get());
    appendNull(rowNum - 1);
    isNull.add(false);
    maxRowNum = rowNum;
  }

  @Override
  public void append(E value, long rowNum, int[] arrayIdx) {
    checkArgument(this.arrayIdx.length != 0, "Can't append with arrayIdx. The column has no arrays");
    values.add(value.get());

    //close any previously opened row
    if (rowNum != maxRowNum && maxRowNum >= 0) {
      arraySize.add(maxRowArraySize);
      maxRowArraySize = 0;
    }

    appendNull(rowNum - 1);
    if (rowNum != maxRowNum) {
      isNull.add(false);
    }

    // this.arrayIdx.length is used here because the passed arrayIdx may be
    // bigger because it is reused
    for (int i = 0; i < this.arrayIdx.length; i++) {
      this.arrayIdx[i].add(arrayIdx[i]);
    }
    maxRowArraySize++;
    maxRowNum = rowNum;
  }

  private void appendNull(long rowNum) {
    for (long i = maxRowNum; i < rowNum; i++) {
      isNull.add(true);
    }
  }

  @Override
  public long getSize() {
    return values.size();
  }

  @Override
  public long getValuesCount() {
    return 0;
  }

  @Override
  public ColumnIterator<Object> getSimpleIterator(long maxRowNum) {
    return new SimpleColumnIterator(maxRowNum);
  }

  @Override
  public ColumnIterator<Object> getSimpleIterator() {
    return new SimpleColumnIterator(maxRowNum);
  }

  private class SimpleColumnIterator implements ColumnIterator<Object> {
    private final long maxRowNum;
    private long rowNum = 0;
    Iterator<Boolean> isNullIterator;
    Iterator<E> valuesIterator;
    Iterator<Integer> arraySizeIterator;

    SimpleColumnIterator(long maxRowNum) {
      this.maxRowNum = maxRowNum;
      isNullIterator = isNull.iterator();
      valuesIterator = values.iterator();
      arraySizeIterator = arraySize.iterator();
    }

    @Override
    public boolean hasNext() {
      return isNullIterator.hasNext() && rowNum <= maxRowNum;
    }

    @Override
    public Object next() {
      checkArgument(hasNext(), "Check hashNext() before calling next().");
      rowNum++;
      if (isNullIterator.next()) {
        return null;
      } else {
        if (arrayIdx.length > 0) {
          if (arraySizeIterator.hasNext()) {
            int arraySize = arraySizeIterator.next();
            Object[] values = new Object[arraySize];
            for (int i = 0; i < arraySize && valuesIterator.hasNext(); i++) {
              values[i] = valuesIterator.next();
            }
            return values;
          } else {
            List values = new LinkedList();
            while (valuesIterator.hasNext()) {
              values.add(valuesIterator.next());
            }
            return values.toArray();
          }
        } else {
          Object value = valuesIterator.next();
          return value;
        }
      }
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException("Cant remove any item. Append only list.");
    }

    @Override
    public boolean skip(long rows) {
      for (int i = 0; i < rows; i++) {
        if (hasNext()) {
          next();
        } else {
          return false;
        }
      }
      return true;
    }

  }

}
