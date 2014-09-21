package io.logbase.column.readonly;

import io.logbase.collections.BatchList;
import io.logbase.collections.BatchListIterator;
import io.logbase.collections.BatchListWriter;
import io.logbase.collections.impl.BitPackIntList;
import io.logbase.collections.impl.IntegerArrayList;
import io.logbase.collections.impl.StringList;
import io.logbase.collections.nativelists.IntList;
import io.logbase.collections.nativelists.IntListWriter;
import io.logbase.column.Column;

import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class StringDictionaryColumn extends ReadOnlyColumn<CharBuffer> {
  private final BatchList<CharBuffer> dict;
  private final IntList indexList;

  StringDictionaryColumn(Column<CharBuffer> column) {
    super(column, null);
    BatchListIterator<CharBuffer> iterator = column.getValuesIterator(column.getValuesCount());
    Map<CharBuffer, Integer> uniqValues = new HashMap<CharBuffer, Integer>();
    IntSummaryStatistics stats = new IntSummaryStatistics();
    IntegerArrayList tempIdxList = new IntegerArrayList();
    IntListWriter tempIdxListWriter = tempIdxList.primitiveWriter();

    int uniqs = 0;
    int cnt;
    while (iterator.hasNext()) {
      CharBuffer[] values = new CharBuffer[iterator.optimumBufferSize()];
      cnt = iterator.next(values, 0, values.length);
      for (int i = 0; i < cnt; i++) {
        Integer val = uniqValues.get(values[i]);
        if (val == null) {
          uniqValues.put(values[i], uniqs);
          tempIdxListWriter.addPrimitive(uniqs);
          stats.accept(values[i].length());
          uniqs++;
        } else {
          tempIdxListWriter.addPrimitive(val);
        }
      }
    }

    CharBuffer[] uniqValuesSorted = uniqValues.keySet().toArray(new CharBuffer[0]);
    Arrays.sort(uniqValuesSorted);

    int[] indexMap = new int[uniqValuesSorted.length];
    for (int i = 0; i < uniqValuesSorted.length; i++) {
      indexMap[uniqValues.get(uniqValuesSorted[i])] = i;
    }

    dict = new StringList(stats);
    BatchListWriter<CharBuffer> dictWriter = dict.writer();
    for (CharBuffer value : uniqValuesSorted) {
      dictWriter.add(value);
    }
    dictWriter.close();

    indexList = new BitPackIntList(stats);
    indexList.primitiveWriter().addAll(tempIdxList.iterator(tempIdxList.size())).close();
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
  public BatchListIterator<CharBuffer> getValuesIterator(long maxRowNum) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

}
