package io.logbase.column.readonly;

import io.logbase.collections.BatchIterator;
import io.logbase.collections.BatchList;
import io.logbase.collections.impl.*;
import io.logbase.column.Column;

import java.nio.CharBuffer;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class StringDictionaryColumn extends AbstractROColumn<CharBuffer> {
  private final BatchList<CharBuffer> dict;
  private final BatchList<Integer> indexList;

  StringDictionaryColumn(Column<CharBuffer> column) {
    super(column);
    BatchIterator<CharBuffer> iterator = column.getValuesIterator();
    Map<CharBuffer, Integer> uniqValues = new HashMap<CharBuffer, Integer>();
    IntSummaryStatistics stats = new IntSummaryStatistics();
    IntegerLinkedArrayList tempIdxList = new IntegerLinkedArrayList();

    int uniqs = 0;
    while (iterator.hasNext()) {
      CharBuffer value = iterator.next();
      Integer val = uniqValues.get(value);
      if(val == null){
        uniqValues.put(value, uniqs);
        tempIdxList.add(uniqs);
        stats.accept(value.length());
        uniqs++;
      }else{
        tempIdxList.add(val);
      }
    }

    CharBuffer[] uniqValuesSorted = uniqValues.keySet().toArray(new CharBuffer[0]);
    Arrays.sort(uniqValuesSorted);

    int[] indexMap = new int[uniqValuesSorted.length];
    for(int i=0; i<uniqValuesSorted.length ; i++){
      indexMap[uniqValues.get(uniqValuesSorted[i])] = i;
    }

    dict = new StringList(stats);
    for(CharBuffer value: uniqValuesSorted){
      dict.add(value);
    }
    dict.close();

    indexList = new BitPackIntListWriter(stats);

    BatchIterator<Integer> it = tempIdxList.batchIterator(tempIdxList.size());
    int[] holder = new int[1024];
    int cnt=0;
    while(it.hasNext()){
      cnt = it.readNative(holder, 0, holder.length);
      if(cnt>0){
        indexList.addPrimitiveArray(holder, 0, cnt);
      }
    }
    indexList.close();

  }

  @Override
  public Class getColumnType() {
    return CharBuffer.class;
  }

  @Override
  public long getValuesCount() {
    return indexList.size();
  }

  @Override
  public BatchIterator<CharBuffer> getValuesIterator() {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

}
