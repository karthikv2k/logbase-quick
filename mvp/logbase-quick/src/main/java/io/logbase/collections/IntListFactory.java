package io.logbase.collections;

import io.logbase.collections.impl.BitPackIntList;
import io.logbase.collections.impl.IntegerArrayList;
import io.logbase.collections.nativelists.IntList;
import io.logbase.collections.nativelists.IntListIterator;

import java.util.IntSummaryStatistics;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class IntListFactory {

  public static IntList newReadOnlyList(IntSummaryStatistics stats, IntListIterator source, boolean randomRead){
    if(Utils.getWidthFromMaxInt(stats.getMax())==32){
      IntList intList = new IntegerArrayList((int)Math.min(Integer.MAX_VALUE,stats.getCount()));
      source.supplyTo(intList.primitiveWriter());
      return intList;
    } else {
      return new BitPackIntList(stats);
    }
  }

  public static IntList newAppendOnlyList(IntSummaryStatistics stats, boolean isCompressed){
    return new IntegerArrayList();
  }
}
