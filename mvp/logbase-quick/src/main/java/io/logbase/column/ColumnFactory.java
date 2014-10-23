package io.logbase.column;

import io.logbase.collections.IntListFactory;
import io.logbase.collections.Utils;
import io.logbase.collections.impl.*;
import io.logbase.collections.nativelists.IntList;
import io.logbase.collections.nativelists.IntListIterator;
import io.logbase.column.appendonly.AppendOnlyColumn;
import io.logbase.column.readonly.ReadOnlyColumn;

import java.util.IntSummaryStatistics;;

public class ColumnFactory {

  public final static ColumnFactory INSTANCE = new ColumnFactory();

  public static <T> Column createAppendOnlyColumn(Class<T> type, String name, int numArrays){
    if (type.equals(Integer.class)) {
      return new AppendOnlyColumn(name, numArrays, new IntegerArrayList());
    } else if (type.equals(Boolean.class)) {
      return new AppendOnlyColumn(name, numArrays, new BitsetList());
    } else if (type.equals(Float.class)) {
      return new AppendOnlyColumn(name, numArrays, new FloatArrayList());
    } else if (type.equals(Double.class)) {
      return new AppendOnlyColumn(name, numArrays, new DoubleArrayList());
    } else if (type.equals(Long.class)) {
      return new AppendOnlyColumn(name, numArrays, new LongArrayList());
    } else if (type.equals(String.class)) {
      return new AppendOnlyColumn(name, numArrays, new StringDictionaryList());
    } else {
      return new AppendOnlyColumn(name, numArrays, new ListBackedBatchList(TypeUtils.getPrimitiveType(type)));
    }
  }


  public static <T> Column createReadOnlyColumn(Column<T> sourceColumn){
    if(TypeUtils.getBaseType(sourceColumn.getColumnType()).equals(Integer.class)){
      Column<Integer> intSourceColumn = (Column<Integer>) sourceColumn;
      IntListIterator iterator = (IntListIterator) intSourceColumn.getValuesIterator(intSourceColumn.getValuesCount());
      IntSummaryStatistics columnStats = (IntSummaryStatistics)
        Utils.getListStats(iterator);
      iterator.rewind();
      IntList intList = IntListFactory.newReadOnlyList(columnStats, iterator, false);
      ReadOnlyColumn<Integer> roColumn = new ReadOnlyColumn<>(intSourceColumn, intList);
      return roColumn;
    }else if(TypeUtils.getBaseType(sourceColumn.getColumnType()).equals(String.class)){
      //TBD
    }else if(TypeUtils.getBaseType(sourceColumn.getColumnType()).equals(Boolean.class)){
      //TBD
    }
    return null;
  }


}
