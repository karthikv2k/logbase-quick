package io.logbase.column;

import io.logbase.collections.impl.BitPackIntList;
import io.logbase.collections.impl.BitsetList;
import io.logbase.collections.impl.IntegerArrayList;
import io.logbase.collections.impl.ListBackedBatchList;
import io.logbase.collections.nativelists.IntList;
import io.logbase.column.appendonly.AppendOnlyColumn;
import io.logbase.column.readonly.ReadOnlyColumn;

import java.util.IntSummaryStatistics;
import java.util.LongSummaryStatistics;

public class ColumnFactory {

  public final static ColumnFactory INSTANCE = new ColumnFactory();

  public <T> Column createAppendOnlyColumn(Class<T> type, String name, int numArrays){
    if (type.equals(Integer.class)) {
      return new AppendOnlyColumn(name, numArrays, new IntegerArrayList());
    } else if (type.equals(Boolean.class)) {
      return new AppendOnlyColumn(name, numArrays, new BitsetList());
    } else {
      return new AppendOnlyColumn(name, numArrays, new ListBackedBatchList(TypeUtils.getPrimitiveType(type)));
    }
  }


  public <T> Column createReadOnlyColumn(Column<T> appendOnly){

    IntSummaryStatistics columnStats = new IntSummaryStatistics();
    if(appendOnly.getColumnType() instanceof ){
      IntList intList = (IntList) appendOnly;
      intList.primitiveIterator(intList.size()).supplyTo(columnStats);
      //logic to decide on which int list impl goes here
      BitPackIntList bitPackIntList = new BitPackIntList(columnStats);
      ReadOnlyColumn<Integer> roList = new ReadOnlyColumn<>(intList, bitPackIntList);
    }
  }


}
