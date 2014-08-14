package io.logbase.column.readonly;

import io.logbase.collections.BatchIterator;
import io.logbase.collections.impl.BitPackIntBuffer;
import io.logbase.collections.impl.BitPackIntList;
import io.logbase.collections.impl.BitPackIntListIterator;
import io.logbase.column.Column;
import io.logbase.column.ColumnIterator;

import java.util.Iterator;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class BitpackIntegerColumn implements Column<Integer> {

  public final long startRowNum;
  public final int arrayCount;
  public final String columnName;
  private final BitPackIntList values;
  private final BitPackIntList isPresent;
  private final BitPackIntList arraySize;
  private final BitPackIntList[] arrayIdx;

  public BitpackIntegerColumn(Column<Integer> column){

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
    checkArgument(isPresent.getBuffer().size == isPresent.getBuffer().listSize, "list's final size doesn't match the " +
      "initial expected size");

    if(arrayCount>0){
      arraySize = new BitPackIntList(column.getArraySizeIterator());
      arraySize.writeAll(column.getArraySizeIterator());
      arraySize.close();
      checkArgument(arraySize.getBuffer().size == arraySize.getBuffer().listSize, "list's final size doesn't match " +
        "the initial expected size");
    } else {
      arraySize = null;
    }

    values = new BitPackIntList(column.getValuesIterator());
    values.writeAll(column.getValuesIterator());
    values.close();
    checkArgument(values.getBuffer().size == values.getBuffer().listSize, "list's final size doesn't match the " +
      "initial expected size");

    if(arrayCount>0){
      arrayIdx = new BitPackIntList[arrayCount];
      for (int i = 0; i < arrayIdx.length; i++) {
        arrayIdx[i] = new BitPackIntList(column.getArrayIndexIterator(i));
        arrayIdx[i].writeAll(column.getValuesIterator());
        arrayIdx[i].close();
        checkArgument(arrayIdx[i].getBuffer().size == arrayIdx[i].getBuffer().listSize, "list's final size doesn't " +
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
  public Class getColumnType() {
    return Integer.class;
  }

  @Override
  public void append(Integer value, long rowNum) {
    throw new UnsupportedOperationException("Readonly column, can append values.");
  }

  @Override
  public void append(Integer value, long rowNum, int[] arrayIdx) {
    throw new UnsupportedOperationException("Readonly column, can append values.");
  }

  @Override
  public long getRowCount() {
    return isPresent.getBuffer().size;
  }

  @Override
  public long getValidRowCount() {
    return arraySize.getBuffer().size;
  }

  @Override
  public long getValuesCount() {
    return values.getBuffer().size;
  }

  @Override
  public int getArrayCount() {
    return arrayCount;
  }

  @Override
  public ColumnIterator<Object> getSimpleIterator(long maxRowNum) {
     return new SimpleIterator(maxRowNum);
  }

  @Override
  public ColumnIterator<Object> getSimpleIterator() {
    return new SimpleIterator(values.getBuffer().size);
  }

  @Override
  public BatchIterator<Boolean> getIsPresentIterator() {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public BatchIterator<Integer> getValuesIterator() {
    return new BitPackIntListIterator(values.getBuffer());
  }

  @Override
  public BatchIterator<Integer> getArraySizeIterator() {
    return new BitPackIntListIterator(arraySize.getBuffer());
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

  public class SimpleIterator implements ColumnIterator<Object>{
    private final long maxRowNum;
    private long validRowNum = 0;
    private long currentRowNum = 0;
    private final BitPackIntListIterator valuesReader;
    private final BitPackIntListIterator isPresentReader;
    private final BitPackIntListIterator arraySizeReader;

    SimpleIterator(long maxRowNum){
      this.maxRowNum = maxRowNum;
      valuesReader = new BitPackIntListIterator(values.getBuffer());
      isPresentReader = new BitPackIntListIterator(isPresent.getBuffer());
      if (arrayCount > 0) {
        arraySizeReader = new BitPackIntListIterator(arraySize.getBuffer());
      } else {
        arraySizeReader = null;
      }
    }

    @Override
    public Iterator<Object> iterator() {
      return this;
    }

    @Override
    public boolean hasNext() {
      return isPresentReader.hasNext() && currentRowNum < maxRowNum;
    }

    @Override
    public Object next() {
      currentRowNum++;
      if (isPresentReader.next() == 1) {
        if(arrayCount>0){
          int n;
          if(arraySizeReader.hasNext()){
            n = arraySizeReader.next();
          } else {
            n = (int) (values.getBuffer().size - validRowNum);
          }
          Integer[] holder = new Integer[n];
          for(int i=0; i<n && valuesReader.hasNext(); i++){
            holder[i] = valuesReader.next();
          }
          return  holder;
        }else{
          validRowNum++;
          return valuesReader.next();
        }
      }else{
        return null;
      }
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException("Remove is not supported.");
    }

    @Override
    public int read(Object[] buffer, int offset, int count) {
      throw new UnsupportedOperationException();
    }

    @Override
    public boolean primitiveTypeSupport() {
      //TBA
      return false;
    }

    @Override
    public int readNative(Object buffer, int offset, int count) {
      //TBA
      throw new UnsupportedOperationException();
    }
  }
}
