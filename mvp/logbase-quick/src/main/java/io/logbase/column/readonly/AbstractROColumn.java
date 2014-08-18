package io.logbase.column.readonly;

import io.logbase.collections.BatchIterator;
import io.logbase.collections.BatchList;
import io.logbase.collections.impl.BitPackIntBuffer;
import io.logbase.collections.impl.BitPackIntList;
import io.logbase.collections.impl.BitPackIntListIterator;
import io.logbase.collections.impl.IntToBooleanIterator;
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
  protected final BitPackIntList isPresent;
  protected final long startRowNum;
  protected final BatchList<Integer> arraySize;
  protected final BitPackIntList[] arrayIdx;

  AbstractROColumn(Column<E> column){
    this.columnName = column.getColumnName();
    this.arrayCount = column.getArrayCount();

    BatchIterator<Boolean> isPresentIt = column.getIsPresentIterator();
    isPresent = new BitPackIntList(new BitPackIntBuffer(0, 1, column.getRowCount()));
    long startRowNum = -1;
    int cnt = 0;
    while (isPresentIt.hasNext()) {
      boolean value = isPresentIt.next();
      if (value && startRowNum == -1) {
        startRowNum = cnt;
      }
      cnt++;
      isPresent.add(value ? 1 : 0);
    }
    this.startRowNum = startRowNum;
    checkArgument(isPresent.getBuffer().getSize() == isPresent.getBuffer().listSize, "list's final size doesn't match the " +
      "initial expected size");

    if(arrayCount>0){
      arraySize = new BitPackIntList(column.getArraySizeIterator());
      arraySize.addAll(column.getArraySizeIterator());
      arraySize.close();
      checkArgument(((BitPackIntList)arraySize).getBuffer().getSize() == ((BitPackIntList)arraySize).getBuffer().listSize, "list's final size doesn't match " +
        "the initial expected size");
    } else {
      arraySize = null;
    }

    if(arrayCount>0){
      arrayIdx = new BitPackIntList[arrayCount];
      for (int i = 0; i < arrayIdx.length; i++) {
        arrayIdx[i] = new BitPackIntList(column.getArrayIndexIterator(i));
        arrayIdx[i].addAll(column.getArrayIndexIterator(i));
        arrayIdx[i].close();
        checkArgument(arrayIdx[i].getBuffer().getSize() == arrayIdx[i].getBuffer().listSize, "list's final size doesn't " +
          "match the initial expected size");
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
    return isPresent.getBuffer().getSize();
  }

  @Override
  public long getValidRowCount() {
    return arraySize.sizeAsLong();
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
    return new IntToBooleanIterator(new BitPackIntListIterator(isPresent.getBuffer()));
  }

  @Override
  public abstract BatchIterator<E> getValuesIterator();

  @Override
  public BatchIterator<Integer> getArraySizeIterator() {
    return new BitPackIntListIterator(((BitPackIntList)arraySize).getBuffer());
  }

  @Override
  public BatchIterator<Integer> getArrayIndexIterator(int arrayNum) {
    checkArgument(arrayNum>=0 && arrayNum<arrayCount, "arrayNum should be in the range of [0, arrayCount]");
    return new BitPackIntListIterator(arrayIdx[arrayNum].getBuffer());
  }

  @Override
  public int compareTo(Column o) {
    return columnName.compareTo(o.getColumnName());
  }
}
