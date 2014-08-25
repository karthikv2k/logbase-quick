package io.logbase.column.readonly;

import io.logbase.collections.BatchIterator;
import io.logbase.collections.BatchList;
import io.logbase.collections.impl.*;
import io.logbase.column.Column;
import io.logbase.column.ColumnIterator;
import io.logbase.column.SimpleColumnIterator;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public abstract class AbstractROColumn<E> implements Column<E> {

  protected final String columnName;
  protected final int arrayCount;
  protected final long startRowNum;
  protected final BatchList<Boolean> isPresent;
  protected final BatchList<Integer> arraySize;
  protected final BatchList<Integer>[] arrayIdx;

  AbstractROColumn(Column<E> column){
    this.columnName = column.getColumnName();
    this.arrayCount = column.getArrayCount();

    BatchIterator<Boolean> isPresentIt = column.getIsPresentIterator();
    isPresent = new BooleanList();
    long startRowNum = -1;
    int cnt = 0;
    while (isPresentIt.hasNext()) {
      boolean value = isPresentIt.next();
      if (value && startRowNum == -1) {
        startRowNum = cnt;
      }
      cnt++;
      isPresent.add(value);
    }
    isPresent.close();
    this.startRowNum = startRowNum;

    if(arrayCount>0){
      BitPackIntListWriter arraySizeWriter = new BitPackIntListWriter(column.getArraySizeIterator());
      arraySizeWriter.addAll(column.getArraySizeIterator());
      arraySizeWriter.close();
    } else {
      arraySize = null;
    }

    if(arrayCount>0){
      arrayIdx = new BitPackIntListWriter[arrayCount];
      for (int i = 0; i < arrayIdx.length; i++) {
        arrayIdx[i] = new BitPackIntListWriter(column.getArrayIndexIterator(i));
        arrayIdx[i].addAll(column.getArrayIndexIterator(i));
        arrayIdx[i].close();
      }
    } else {
      arrayIdx = null;
    }
  }

  @Override
  public long getStartRowNum() {
    return this.startRowNum;
  }

  @Override
  public String getColumnName() {
    return columnName;
  }

  @Override
  public abstract Class getColumnType();

  @Override
  public void append(E value, long rowNum) {
    throw new UnsupportedOperationException("Readonly column, can append values.");
  }

  @Override
  public void append(E value, long rowNum, int[] arrayIdx) {
    throw new UnsupportedOperationException("Readonly column, can append values.");
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
  public abstract long getValuesCount();

  @Override
  public int getArrayCount() {
    return arrayCount;
  }

  @Override
  public ColumnIterator<Object> getSimpleIterator(long maxRowNum){
    return new SimpleColumnIterator(this, maxRowNum);
  }

  @Override
  public ColumnIterator<Object> getSimpleIterator(){
    return new SimpleColumnIterator(this, getRowCount());
  }

  @Override
  public BatchIterator<Boolean> getIsPresentIterator() {
    return isPresent.batchIterator(isPresent.size());
  }

  @Override
  public abstract BatchIterator<E> getValuesIterator();

  @Override
  public BatchIterator<Integer> getArraySizeIterator() {
    return arraySize.batchIterator(arraySize.size());
  }

  @Override
  public BatchIterator<Integer> getArrayIndexIterator(int arrayNum) {
    checkArgument(arrayNum>=0 && arrayNum<arrayCount, "arrayNum should be in the range of [0, arrayCount]");
    return arrayIdx[arrayNum].batchIterator(arrayIdx[arrayNum].size());
  }

  @Override
  public int compareTo(Column o) {
    return columnName.compareTo(o.getColumnName());
  }
}
