package io.logbase.column;

import io.logbase.collections.IntListFactory;
import io.logbase.collections.Utils;
import io.logbase.collections.impl.BitPackIntList;
import io.logbase.collections.impl.BitsetList;
import io.logbase.collections.impl.IntegerArrayList;
import io.logbase.collections.impl.ListBackedBatchList;
import io.logbase.collections.nativelists.IntList;
import io.logbase.collections.nativelists.IntListIterator;
import io.logbase.column.appendonly.AppendOnlyColumn;
import io.logbase.column.readonly.ReadOnlyColumn;

import java.util.IntSummaryStatistics;
import java.util.LongSummaryStatistics;

public class ColumnFactory {

  public final static ColumnFactory INSTANCE = new ColumnFactory();

  public static <T> Column createAppendOnlyColumn(Class<T> type, String name, int numArrays){
    if (type.equals(Integer.class)) {
      return new AppendOnlyColumn(name, numArrays, new IntegerArrayList());
    } else if (type.equals(Boolean.class)) {
      return new AppendOnlyColumn(name, numArrays, new BitsetList());
    } else {
      return new AppendOnlyColumn(name, numArrays, new ListBackedBatchList(TypeUtils.getPrimitiveType(type)));
    }
  }


  public static <T> Column createReadOnlyColumn(Column<T> appendOnly){

    if(appendOnly.getColumnType().equals(Integer.class)){
      Column<Integer> intColumn = (Column<Integer>) appendOnly;
      IntListIterator iterator = (IntListIterator) intColumn.getValuesIterator(intColumn.getValuesCount());
      IntSummaryStatistics columnStats = (IntSummaryStatistics)
        Utils.getListStats(iterator);
      iterator.rewind();
      IntList intList = IntListFactory.newReadOnlyList(columnStats, iterator, false);
     // ReadOnlyColumn<Integer> roList = new ReadOnlyColumn<>(intList, bitPackIntList);
    }
    return null;
  }


}
