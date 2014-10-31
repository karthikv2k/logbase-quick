package io.logbase.column;

import io.logbase.collections.BatchListReader;
import io.logbase.collections.impl.IteratorWrapper;
import io.logbase.collections.nativelists.IntListIterator;
import io.logbase.collections.nativelists.IntListReader;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Kousik on 24/10/14.
 */
public class SimpleColumnReader implements ColumnReader<Object> {
  private final long maxRowNum;
  private final boolean hasArrays;
  private final BatchListReader<Boolean> isPresentReader;
  private final BatchListReader<Object> valuesReader;
  private final IntListReader arraySizeReader;
  private Iterator<Integer> arraySizeIteratorWrapper;
  private final IntListIterator arraySizeIterator;
  private long previousIndex;
  private long previousValueIndex;

  public SimpleColumnReader(Column column, long maxRowNum) {
    this.maxRowNum = maxRowNum;
    this.isPresentReader = column.getIsPresentReader(maxRowNum);
    this.valuesReader = column.getValuesReader(Integer.MAX_VALUE);
    this.hasArrays = column.getArrayCount() > 0;
    if (hasArrays) {
      this.arraySizeReader = column.getArraySizeReader(maxRowNum);
      this.arraySizeIterator = column.getArraySizeIterator(maxRowNum);
      this.arraySizeIteratorWrapper = new IteratorWrapper<Integer>(arraySizeIterator);
    } else {
      this.arraySizeReader = null;
      this.arraySizeIterator = null;
      this.arraySizeIteratorWrapper = null;
    }
  }

  public Object get(long index) {

    if (index > maxRowNum) {
      assert(false);
      return null;
    }

    if (!isPresentReader.get(index).booleanValue()) {
      return null;
    }

    long valueIndex = getValueIndex(index);
    if (!hasArrays) {
      // Increment value index, since we have returned one value
      previousValueIndex++;
      return TypeUtils.castToSQL(valuesReader.get(valueIndex));
    }

    if (arraySizeIteratorWrapper.hasNext()) {
      int arraySize = arraySizeIteratorWrapper.next().intValue();
      Object[] values = new Object[arraySize];
      for (int i=0; i<arraySize; i++) {
        values[i] = TypeUtils.castToSQL(valuesReader.get(valueIndex++));
      }
      previousValueIndex += arraySize;
      return values;
    } else {
      List values = new LinkedList();
      // All the remaining values belong to his row. Read till the end.
      while(true) {
        try {
          values.add(TypeUtils.castToSQL(valuesReader.get(valueIndex++)));
        } catch (ArrayIndexOutOfBoundsException e) {
          return values.toArray();
        }
      }
    }
  }

  /*
   * Value index is different from isPresent list index. Compute
   * the value index based on isPresent list and array size list.
   */
  public long getValueIndex(long index) {
    long valueIndex = previousValueIndex;

    if (index <= previousIndex) {
      valueIndex  = 0;
      previousIndex = 0;

      if (hasArrays) {
        arraySizeIterator.rewind();
        arraySizeIteratorWrapper = new IteratorWrapper(arraySizeIterator);
      }
    }

    for (long iter = previousIndex; iter < index; iter++) {
      if (isPresentReader.get(iter).booleanValue()) {
        if (hasArrays && arraySizeIteratorWrapper.hasNext()) {
          valueIndex+= arraySizeIteratorWrapper.next();
        } else {
          valueIndex++;
        }
      }
    }

    previousValueIndex = valueIndex;
    previousIndex = index;
    return valueIndex;
  }
}
