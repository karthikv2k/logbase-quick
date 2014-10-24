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
public class ReadOnlyColumn<E> implements Column<E> {

  protected final String columnName;
  protected final int arrayCount;
  protected final long startRowNum;
  protected final BatchList<Boolean> isPresent;
  protected final IntList arraySize;
  protected final IntList[] arrayIdx;
  protected final BatchList<E> values;

  public ReadOnlyColumn(Column<E> sourceColumn, BatchList<E> valuesROList) {

    this.values = valuesROList;
    this.columnName = sourceColumn.getColumnName();
    this.arrayCount = sourceColumn.getArrayCount();
    this.startRowNum = sourceColumn.getStartRowNum();

    //converting isPresent component
    isPresent = new BitsetList((int) sourceColumn.getRowCount()); //TBA avoid int conversion
    BatchListWriter<Boolean> booleanWriter = isPresent.writer();
    booleanWriter.addAll(sourceColumn.getIsPresentIterator(isPresent.size()));
    booleanWriter.close();

    //converting array size component
    if (arrayCount > 0) {
      arraySize = null;//new BitPackIntList(column.getArraySizeIterator(column.getValidRowCount()));
      IntListWriter arraySizeWriter = arraySize.primitiveWriter();
      arraySizeWriter.addAll(sourceColumn.getArraySizeIterator(sourceColumn.getValidRowCount()));
      arraySizeWriter.close();
    } else {
      arraySize = null;
    }

    //converting array index component
    if (arrayCount > 0) {
      arrayIdx = new IntList[arrayCount];
      for (int i = 0; i < arrayIdx.length; i++) {
        arrayIdx[i] = null;//new BitPackIntList(column.getArrayIndexIterator(i, column.getValuesCount()));
        IntListWriter arrayIdxWriter = arrayIdx[i].primitiveWriter();
        arrayIdxWriter.addAll(sourceColumn.getArrayIndexIterator(i, sourceColumn.getValuesCount()));
        arrayIdxWriter.close();
      }
    } else {
      arrayIdx = null;
    }

    //converting values component
    BatchListWriter<E> writer = valuesROList.writer();
    writer.addAll(sourceColumn.getValuesIterator(sourceColumn.getValuesCount()));
    writer.close();

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
  public Class getColumnType(){
    throw new UnsupportedOperationException(); //TBD
  }

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
  public long getValuesCount(){
    return values.size();
  }

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
  public BatchListIterator<E> getValuesIterator(long maxRowNum){
    return values.iterator(maxRowNum);
  }

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

  @Override
  public long memSize() {
    long memSize = 0;

    if (isPresent != null) {
      memSize = memSize + isPresent.memSize();
    }
    if (values != null) {
      memSize = memSize + values.memSize();
    }
    if (arraySize != null) {
      memSize = memSize + arraySize.memSize();
    }

    if (arrayIdx != null) {
      for (IntList list : arrayIdx) {
        memSize = memSize + list.memSize();
      }
    }
    return memSize;
  }
}
