package io.logbase.column.appendonly;

import io.logbase.collections.BatchIterator;
import io.logbase.collections.impl.BatchIteratorWrapper;
import io.logbase.column.Column;
import io.logbase.column.ColumnIterator;
import io.logbase.column.SimpleColumnIterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

public class ListBackedColumn<E> implements Column<E> {

  private String name;
  private List values = new ArrayList(10000);
  private List<Boolean> isPresent = new ArrayList<Boolean>(10000);
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
    values.stream();
    checkArgument(this.arrayIdx.length == 0, "Can't append without arrayIdx");
    checkArgument(value != null, "Value can't be null");
    values.add(value);
    appendNotPresent(rowNum - 1);
    isPresent.add(true);
    maxRowNum = rowNum;
  }

  @Override
  public void append(E value, long rowNum, int[] arrayIdx) {
    checkArgument(this.arrayIdx.length != 0, "Can't append with arrayIdx. The column has no arrays");
    values.add(value);

    //close any previously opened row
    if (rowNum != maxRowNum && maxRowNum >= 0) {
      arraySize.add(maxRowArraySize);
      maxRowArraySize = 0;
    }

    appendNotPresent(rowNum - 1);
    if (rowNum != maxRowNum) {
      isPresent.add(true);
    }

    // this.arrayIdx.length is used here because the passed arrayIdx may be
    // bigger because it is reused
    for (int i = 0; i < this.arrayIdx.length; i++) {
      this.arrayIdx[i].add(arrayIdx[i]);
    }
    maxRowArraySize++;
    maxRowNum = rowNum;
  }

  private void appendNotPresent(long rowNum) {
    for (long i = maxRowNum; i < rowNum; i++) {
      isPresent.add(false);
    }
  }

  @Override
  public long getRowCount() {
    return isPresent.size();
  }

  @Override
  public long getValidRowCount() {
    return arraySize.size();
  }

  @Override
  public long getValuesCount() {
    return values.size();
  }

  @Override
  public int getArrayCount() {
    return arrayIdx.length;
  }

  @Override
  public ColumnIterator<Object> getSimpleIterator(long maxRowNum) {
    return new SimpleColumnIterator(this, maxRowNum);
  }

  @Override
  public ColumnIterator<Object> getSimpleIterator() {
    return new SimpleColumnIterator(this, maxRowNum);
  }

  @Override
  public BatchIterator<Boolean> getIsPresentIterator() {
    return new BatchIteratorWrapper<Boolean>(isPresent.iterator());
  }

  @Override
  public BatchIterator<E> getValuesIterator() {
    return new BatchIteratorWrapper<E>(values.iterator());
  }

  @Override
  public BatchIterator<Integer> getArraySizeIterator() {
    return new BatchIteratorWrapper<Integer>(arraySize.iterator());
  }

  @Override
  public BatchIterator<Integer> getArrayIndexIterator(int arrayNum) {
    return new BatchIteratorWrapper<Integer>(arrayIdx[arrayNum].iterator());
  }

  @Override
  public int compareTo(Column column) {
    return this.name.compareTo(column.getColumnName());
  }

}
