package io.logbase.column.readonly;

import io.logbase.collections.BatchIterator;
import io.logbase.collections.impl.BitPackIntList;
import io.logbase.collections.impl.BitPackIntListIterator;
import io.logbase.collections.impl.BitPackIntListWriter;
import io.logbase.collections.impl.BitPackIntRange;
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
  private final BitPackIntList isNull;
  private final BitPackIntList arraySize;
  private BitPackIntRange valuesRange = new BitPackIntRange();
  private BitPackIntRange arraySizeRange = new BitPackIntRange();

  public BitpackIntegerColumn(Column<Integer> column){

    this.columnName = column.getColumnName();

    //find max and min of values and array sizes
    long startRowNum = -1;
    ColumnIterator<Object> iterator = column.getSimpleIterator();
    arrayCount = column.getArrayCount();
    int i =0;
    while(iterator.hasNext()){
      Object temp = iterator.next();
      if(temp!=null){
        if(arrayCount>0){
          Integer[] vals = (Integer[]) temp;
          valuesRange.update(vals);
          arraySizeRange.update(vals.length);
        }else{
          valuesRange.update((Integer) temp);
        }
        if(startRowNum==-1){
          startRowNum=i;
        }
        i++;
      }
    }
    this.startRowNum = startRowNum;

    values = new BitPackIntList(valuesRange.getMin(), valuesRange.getMax(), column.getValuesCount());
    BitPackIntListWriter valuesWriter = new BitPackIntListWriter(values);
    isNull = new BitPackIntList(0, 1, column.getRowCount());
    BitPackIntListWriter isNullWriter = new BitPackIntListWriter(isNull);
    BitPackIntListWriter arraySizeWriter;
    if(arrayCount>0){
      arraySize = new BitPackIntList(arraySizeRange.getMin(), arraySizeRange.getMax(), column.getValidRowCount());
      arraySizeWriter = new BitPackIntListWriter(arraySize);
    } else {
      arraySizeWriter = null;
      arraySize = null;
    }

    iterator = column.getSimpleIterator();
    while(iterator.hasNext()){
      Object temp = iterator.next();
      if(temp!=null){
        if(arrayCount>0){
          Integer[] values = (Integer[]) temp;
          arraySizeWriter.add(values.length);
          for(Integer value:values){
            addValue(isNullWriter, valuesWriter, value);
          }
        }else {
          addValue(isNullWriter, valuesWriter, (Integer) temp);
        }
      }else{
        isNullWriter.add(0);
      }
    }

    isNullWriter.close();
    valuesWriter.close();

    checkArgument(values.size == values.listSize, "list's final size doesn't match the initial expected size");
    checkArgument(isNull.size == isNull.listSize, "list's final size doesn't match the initial expected size");
    if(arrayCount>0){
      arraySizeWriter.close();
      checkArgument(arraySize.size == arraySize.listSize, "list's final size doesn't match the initial expected size");
    }
  }

  private void addValue(BitPackIntListWriter isNullWriter, BitPackIntListWriter valuesWriter, Integer value){
    isNullWriter.add(1);
    valuesWriter.add(value);
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
    return isNull.size;
  }

  @Override
  public long getValidRowCount() {
    return arraySize.size;
  }

  @Override
  public long getValuesCount() {
    return values.size;
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
    return new SimpleIterator(values.size);
  }

  @Override
  public BatchIterator<Boolean> getIsPresentIterator() {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public BatchIterator<Integer> getValuesIterator() {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public BatchIterator<Integer> getArraySizeIterator() {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public BatchIterator<Integer> getArrayIndexIterator(int arrayNum) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public int compareTo(Column o) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

  public class SimpleIterator implements ColumnIterator<Object>{
    private final long maxRowNum;
    private long validRowNum = 0;
    private long currentRowNum = 0;
    private final BitPackIntListIterator valuesReader;
    private final BitPackIntListIterator isNullReader;
    private final BitPackIntListIterator arraySizeReader;

    SimpleIterator(long maxRowNum){
      this.maxRowNum = maxRowNum;
      valuesReader = new BitPackIntListIterator(values);
      isNullReader = new BitPackIntListIterator(isNull);
      arraySizeReader = new BitPackIntListIterator(arraySize);
    }

    @Override
    public Iterator<Object> iterator() {
      return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean hasNext() {
      return isNullReader.hasNext() && currentRowNum<maxRowNum;
    }

    @Override
    public Object next() {
      currentRowNum++;
      if(isNullReader.next()==1){
        if(arrayCount>0){
          int n;
          if(arraySizeReader.hasNext()){
            n = arraySizeReader.next();
          } else {
            n = (int) (values.size - validRowNum);
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
