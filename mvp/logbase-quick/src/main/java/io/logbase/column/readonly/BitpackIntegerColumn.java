package io.logbase.column.readonly;

import io.logbase.collections.impl.BitPackIntList;
import io.logbase.collections.impl.BitPackIntListReader;
import io.logbase.collections.impl.BitPackIntListWriter;
import io.logbase.column.Column;
import io.logbase.column.ColumnIterator;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class BitpackIntegerColumn implements Column<Integer> {
  public final int maxValue;
  public final int minValue;
  public final long startRowNum;
  public final int arrayCount;
  public final String columnName;
  private final BitPackIntList values;
  private final BitPackIntList isNull;

  private int maxValueTemp;
  private int minValueTemp;
  private int startRowNumTemp;

  public BitpackIntegerColumn(Column<Integer> column){

    this.columnName = column.getColumnName();

    /*** convert values list into values bitpacked list ****/
    //find max and min
    int maxValue=Integer.MIN_VALUE;
    int minValue=Integer.MAX_VALUE;
    long startRowNum = -1;
    ColumnIterator<Object> iterator = column.getSimpleIterator();
    arrayCount = column.getArrayCount();
    int i =0;
    while(iterator.hasNext()){
      Object temp = iterator.hasNext();
      if(temp!=null){
        if(arrayCount>0){
          Integer[] values = (Integer[]) temp;
          for(Integer value:values){
            updateMinMax(value);
          }
        }else {
          updateMinMax((Integer) temp);
        }
        if(startRowNum==-1){
          startRowNum=i;
        }
        i++;
      }
    }
    this.maxValue = maxValue;
    this.minValue = minValue;
    this.startRowNum = startRowNum;

    values = new BitPackIntList(maxValue - minValue, column.getValuesCount());
    BitPackIntListWriter valuesWriter = new BitPackIntListWriter(values);
    isNull = new BitPackIntList(1, column.getSize());
    BitPackIntListWriter isNullWriter = new BitPackIntListWriter(isNull);

    iterator = column.getSimpleIterator();
    while(iterator.hasNext()){
      Object temp = iterator.hasNext();
      if(temp!=null){
        if(arrayCount>0){
          Integer[] values = (Integer[]) temp;
          for(Integer value:values){
            addValue(isNullWriter, valuesWriter, value);
          }
        }else {
          addValue(isNullWriter, valuesWriter, (Integer) temp);
        }
      }else{
        isNullWriter.append(0);
      }
    }

    isNullWriter.close();
    valuesWriter.close();

  }

  private void updateMinMax(Integer value){
      maxValueTemp = Math.max(maxValueTemp, value);
      minValueTemp = Math.min(minValueTemp, value);
  }

  private void addValue(BitPackIntListWriter isNullWriter, BitPackIntListWriter valuesWriter, Integer value){
    isNullWriter.append(1);
    valuesWriter.append(value- this.minValue);
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
  public long getSize() {
    return isNull.size;
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
     return null;
  }

  @Override
  public ColumnIterator<Object> getSimpleIterator() {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public int compareTo(Column o) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

  public class SimpleIterator implements ColumnIterator<Object>{
    private final long maxRowNum;
    private final BitPackIntListReader valuesReader;
    private final BitPackIntListReader isNullReader;

    SimpleIterator(long maxRowNum){
      this.maxRowNum = maxRowNum;
      valuesReader = new BitPackIntListReader(values);
      isNullReader = new BitPackIntListReader(isNull);
    }

    @Override
    public boolean skip(long rows) {
      return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean hasNext() {
      return isNullReader.hasNext();
    }

    @Override
    public Object next() {
      if(isNullReader.next()==1){
        return null;
      }else{
        return null;
      }
    }

    @Override
    public void remove() {
      //To change body of implemented methods use File | Settings | File Templates.
    }
  }
}
