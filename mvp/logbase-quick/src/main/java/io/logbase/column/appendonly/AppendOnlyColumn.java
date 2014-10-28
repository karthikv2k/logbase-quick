package io.logbase.column.appendonly;

import io.logbase.collections.BatchList;
import io.logbase.collections.BatchListIterator;
import io.logbase.collections.BatchListReader;
import io.logbase.collections.BatchListWriter;
import io.logbase.collections.impl.BitsetList;
import io.logbase.collections.impl.IntegerArrayList;
import io.logbase.collections.nativelists.*;
import io.logbase.column.Column;
import io.logbase.column.SimpleColumnIterator;
import io.logbase.column.SimpleColumnReader;
import io.logbase.column.TypeUtils;
import io.logbase.exceptions.UnsupportedFunctionPredicateException;
import io.logbase.functions.Predicates.FunctionPredicate;

import java.util.Iterator;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class AppendOnlyColumn<E> implements Column<E> {

  protected final String columnName;
  protected final int arrayCount;
  protected long startRowNum = -1;
  protected final BatchList<Boolean> isPresent;
  protected final BatchListWriter<Boolean> isPresentWriter;
  protected final IntList arraySize;
  protected final IntListWriter arraySizeWriter;
  protected final IntList[] arrayIdx;
  protected final IntListWriter[] arrayIdxWriter;
  protected final BatchList<E> values;
  protected final BatchListWriter<E> valuesWriter;
  protected long maxRowNum = -1;
  protected int maxRowArraySize;

  public AppendOnlyColumn(String columnName, int arrayCount, BatchList<E> values) {
    this.columnName = columnName;
    this.arrayCount = arrayCount;

    isPresent = new BitsetList();
    isPresentWriter = isPresent.writer();
    if (arrayCount > 0) {
      arraySize = new IntegerArrayList();
      arraySizeWriter = arraySize.primitiveWriter();
      arrayIdx = new IntList[arrayCount];
      arrayIdxWriter = new IntListWriter[arrayCount];
      for (int i = 0; i < arrayIdx.length; i++) {
        arrayIdx[i] = new IntegerArrayList();
        arrayIdxWriter[i] = arrayIdx[i].primitiveWriter();
      }
    } else {
      arrayIdx = null;
      arraySizeWriter = null;
      arraySize = null;
      arrayIdxWriter = null;
    }
    this.values = values;
    valuesWriter = values.writer();
  }

  @Override
  public long getStartRowNum() {
    return startRowNum;
  }

  @Override
  public String getColumnName() {
    return columnName;
  }

  @Override
  public Class getColumnType() {
    return values.getClass();
  }

  @Override
  public void append(E value, long rowNum) {
    checkArgument(arrayCount == 0, "Can't append without arrayIdx");
    checkArgument(value != null, "Value can't be null");
    valuesWriter.add(value);
    appendNotPresent(rowNum - 1);
    isPresentWriter.add(true);
    maxRowNum = rowNum;
  }

  @Override
  public void append(E value, long rowNum, int[] arrayIdx) {
    checkArgument(this.arrayIdx.length != 0, "Can't append with arrayIdx. The column has no arrays");
    valuesWriter.add(value);

    //close any previously opened row
    if (rowNum != maxRowNum && maxRowNum > 0) {
      arraySizeWriter.addPrimitive(maxRowArraySize);
      maxRowArraySize = 0;
    }

    appendNotPresent(rowNum - 1);
    if (rowNum != maxRowNum) {
      isPresentWriter.add(true);
    }

    // this.arrayIdx.length is used here because the passed arrayIdx may be
    // bigger because it is reused
    for (int i = 0; i < this.arrayIdx.length; i++) {
      this.arrayIdxWriter[i].addPrimitive(arrayIdx[i]);
    }
    maxRowArraySize++;
    maxRowNum = rowNum;
  }

  private void appendNotPresent(long rowNum) {
    //TBA optimize the hell out of it
    for (long i = maxRowNum; i < rowNum; i++) {
      isPresentWriter.add(false);
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
    if (arrayIdx == null) {
      return 0;
    } else {
      return arrayIdx.length;
    }
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
  public BatchListIterator<E> getValuesIterator(long maxRowNum) {
    return values.iterator(maxRowNum);
  }

  @Override
  public IntListIterator getArraySizeIterator(long maxRowNum) {
    return arraySize.primitiveIterator(maxRowNum);
  }

  @Override
  public IntListIterator getArrayIndexIterator(int arrayNum, long maxRowNum) {
    return arrayIdx[arrayNum].primitiveIterator(maxRowNum);
  }

  @Override
  public SimpleColumnReader getSimpleReader(long maxRowNum) {
    return new SimpleColumnReader(this, maxRowNum);
  }

  @Override
  public BatchListReader<Boolean> getIsPresentReader(long maxRowNum) {
    return isPresent.reader(maxRowNum);
  }

  @Override
  public BatchListReader<E> getValuesReader(long maxRowNum) {
    return values.reader(maxRowNum);
  }

  @Override
  public IntListReader getArraySizeReader(long maxRowNum) {
    return (IntListReader)arraySize.reader(maxRowNum);
  }

  @Override
  public IntListReader getArrayIndexReader(int arrayNum, long maxRowNum) {
    return (IntListReader)arrayIdx[arrayNum].reader(maxRowNum);
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

  @Override
  public void execute(FunctionPredicate predicate, BooleanList list)
    throws UnsupportedFunctionPredicateException {
    BooleanList validRows =  new BitsetList();
    BooleanListIterator isPresentIterator = (BooleanListIterator)isPresent.iterator(isPresent.size());
    BooleanListWriter writer = list.primitiveWriter();
    boolean isNull = predicate.isNull();

    values.execute(predicate, validRows);
    BooleanListReader validRowReader = (BooleanListReader) validRows.reader(validRows.size());
    boolean[] buffer = new boolean[1024];
    int count = 0;
    int validRowIndex = 0;

    // TODO - Need to handle arrays in column
    int i;
    while (isPresentIterator.hasNext()) {
      count = isPresentIterator.nextPrimitive(buffer, 0, buffer.length);
      for (i = 0; i < count; i++) {
        if (buffer[i]) {
          writer.add(validRowReader.get(validRowIndex++) & !isNull);
        } else {
          writer.add(isNull);
        }
      }
    }
  }
}
