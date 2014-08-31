package io.logbase.column.readonly;

import io.logbase.collections.BatchList;
import io.logbase.collections.BatchListIterator;
import io.logbase.collections.BatchListWriter;
import io.logbase.collections.impl.BitPackIntList;
import io.logbase.collections.impl.BitsetList;
import io.logbase.collections.nativelists.IntList;
import io.logbase.collections.nativelists.IntListIterator;
import io.logbase.collections.nativelists.IntListWriter;
import io.logbase.column.Column;
import io.logbase.column.SimpleColumnIterator;

import java.util.Iterator;

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
  protected final IntList arraySize;
  protected final IntList[] arrayIdx;

  AbstractROColumn(Column<E> column) {
    this.columnName = column.getColumnName();
    this.arrayCount = column.getArrayCount();
    this.startRowNum = column.getStartRowNum();

    isPresent = new BitsetList((int) column.getRowCount()); //TBA avoid int conversion
    BatchListWriter<Boolean> booleanWriter = isPresent.writer();
    booleanWriter.addAll(column.getIsPresentIterator(isPresent.size()));
    booleanWriter.close();

    if (arrayCount > 0) {
      arraySize = new BitPackIntList(column.getArraySizeIterator(column.getValidRowCount()));
      IntListWriter arraySizeWriter = arraySize.primitiveWriter();
      arraySizeWriter.addAll(column.getArraySizeIterator(column.getValidRowCount()));
      arraySizeWriter.close();
    } else {
      arraySize = null;
    }

    if (arrayCount > 0) {
      arrayIdx = new IntList[arrayCount];
      for (int i = 0; i < arrayIdx.length; i++) {
        arrayIdx[i] = new BitPackIntList(column.getArrayIndexIterator(i, column.getValuesCount()));
        IntListWriter arrayIdxWriter = arrayIdx[i].primitiveWriter();
        arrayIdxWriter.addAll(column.getArrayIndexIterator(i, column.getValuesCount()));
        arrayIdxWriter.close();
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
  public Iterator<Object> getSimpleIterator(long maxRowNum) {
    return new SimpleColumnIterator(this, maxRowNum);
  }

  @Override
  public BatchListIterator<Boolean> getIsPresentIterator(long maxRowNum) {
    return isPresent.iterator(maxRowNum);
  }

  @Override
  public abstract BatchListIterator<E> getValuesIterator(long maxRowNum);

  @Override
  public IntListIterator getArraySizeIterator(long maxRowNum) {
    return arraySize.primitiveIterator(maxRowNum);
  }

  @Override
  public IntListIterator getArrayIndexIterator(int arrayNum, long maxRowNum) {
    checkArgument(arrayNum >= 0 && arrayNum < arrayCount, "arrayNum should be in the range of [0, arrayCount]");
    return arrayIdx[arrayNum].primitiveIterator(maxRowNum);
  }

  @Override
  public int compareTo(Column o) {
    return columnName.compareTo(o.getColumnName());
  }
}
