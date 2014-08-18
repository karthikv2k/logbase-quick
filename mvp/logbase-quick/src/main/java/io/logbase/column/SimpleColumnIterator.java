package io.logbase.column;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class SimpleColumnIterator implements ColumnIterator<Object> {
  private final long maxRowNum;
  private long rowNum = 0;
  private final boolean hasArrays;
  private final Iterator<Boolean> isPresentIterator;
  private final Iterator<Object> valuesIterator;
  private final Iterator<Integer> arraySizeIterator;

  public SimpleColumnIterator(Column column, long maxRowNum) {
    this.maxRowNum = maxRowNum;
    this.isPresentIterator = column.getIsPresentIterator();
    this.valuesIterator = column.getValuesIterator();
    this.hasArrays = column.getArrayCount()>0;
    if(hasArrays){
      this.arraySizeIterator = column.getArraySizeIterator();
    }else{
      this.arraySizeIterator = null;
    }
  }

  @Override
  public Iterator<Object> iterator() {
    return this;
  }

  @Override
  public boolean hasNext() {
    return isPresentIterator.hasNext() && rowNum <= maxRowNum;
  }

  @Override
  public Object next() {
    checkArgument(hasNext(), "Check hashNext() before calling next().");
    rowNum++;
    if (!isPresentIterator.next()) {
      return null;
    } else {
      if (hasArrays) {
        if (arraySizeIterator.hasNext()) {
          int arraySize = arraySizeIterator.next();
          Object[] values = new Object[arraySize];
          for (int i = 0; i < arraySize && valuesIterator.hasNext(); i++) {
            values[i] = TypeUtils.castToSQL(valuesIterator.next());
          }
          return values;
        } else {
          List values = new LinkedList();
          while (valuesIterator.hasNext()) {
            values.add(TypeUtils.castToSQL(valuesIterator.next()));
          }
          return values.toArray();
        }
      } else {
        Object value = TypeUtils.castToSQL(valuesIterator.next());
        return value;
      }
    }
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException("Cant remove any item. Append only list.");
  }

  @Override
  public int read(Object[] buffer, int offset, int count) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean primitiveTypeSupport() {
    return false;
  }

  @Override
  public int readNative(Object buffer, int offset, int count) {
    throw new UnsupportedOperationException();
  }
}
