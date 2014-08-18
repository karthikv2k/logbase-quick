package io.logbase.column.readonly;

import io.logbase.collections.BatchIterator;
import io.logbase.collections.impl.*;
import io.logbase.column.Column;
import io.logbase.column.ColumnIterator;

import java.nio.CharBuffer;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class StringDictionaryColumn extends AbstractROColumn<CharBuffer> {
  private final StringList dict;

  StringDictionaryColumn(Column<CharBuffer> column) {
    super(column);
    BatchIterator<CharBuffer> iterator = column.getValuesIterator();
    Map<CharBuffer, Integer> uniqValues = new HashMap<CharBuffer, Integer>();
    IntSummaryStatistics stats = new IntSummaryStatistics();

    int uniqs = 0;
    IntegerLinkedArrayList indexList = new IntegerLinkedArrayList();
    while (iterator.hasNext()) {
      CharBuffer value = iterator.next();
      Integer val = uniqValues.get(value);
      if(val == null){
        uniqValues.put(value, uniqs);
        indexList.add(uniqs);
        stats.accept(value.length());
        uniqs++;
      }else{
        indexList.add(val);
      }
    }

    CharBuffer[] uniqValuesSorted = uniqValues.keySet().toArray(new CharBuffer[0]);
    Arrays.sort(uniqValuesSorted);

    Map<Integer,Integer> indexMap = new HashMap<Integer, Integer>(2*uniqValuesSorted.length);
    for(int i=0; i<uniqValuesSorted.length ; i++){
      indexMap.put(uniqValues.get(uniqValuesSorted[i]), i);
    }



    dict = new StringList(stats);
    //dict.addAll(new BatchIteratorWrapper<CharBuffer>(stringConsumer.iterator()));

  }

  @Override
  public Class getColumnType() {
    return CharBuffer.class;
  }

  @Override
  public long getValuesCount() {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public ColumnIterator<Object> getSimpleIterator(long maxRowNum) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public ColumnIterator<Object> getSimpleIterator() {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public BatchIterator<CharBuffer> getValuesIterator() {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

}
