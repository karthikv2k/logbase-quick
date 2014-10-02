package io.logbase.collections;

import io.logbase.collections.nativelists.IntList;
import io.logbase.collections.nativelists.IntListIterator;
import io.logbase.collections.nativelists.StringList;
import io.logbase.collections.nativelists.StringListIterator;

import java.nio.CharBuffer;
import java.util.IntSummaryStatistics;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class Utils {

  /**
   * converts native array to its corresponding object array. E.g int[] to Integer[]
   *
   * @param obj
   * @return
   */
  public static Object[] toObjectArray(Object obj) {
    if (obj instanceof int[]) {
      int[] values = (int[]) obj;
      Integer[] objArray = new Integer[values.length];
      for (int i = 0; i < values.length; i++) {
        objArray[i] = values[i];
      }
      return objArray;
    } else if (obj instanceof long[]) {
      long[] values = (long[]) obj;
      Long[] objArray = new Long[values.length];
      for (int i = 0; i < values.length; i++) {
        objArray[i] = values[i];
      }
      return objArray;
    } else if (obj instanceof float[]) {
      float[] values = (float[]) obj;
      Float[] objArray = new Float[values.length];
      for (int i = 0; i < values.length; i++) {
        objArray[i] = values[i];
      }
      return objArray;
    } else if (obj instanceof double[]) {
      double[] values = (double[]) obj;
      Double[] objArray = new Double[values.length];
      for (int i = 0; i < values.length; i++) {
        objArray[i] = values[i];
      }
      return objArray;
    } else if (obj instanceof boolean[]) {
      boolean[] values = (boolean[]) obj;
      Boolean[] objArray = new Boolean[values.length];
      for (int i = 0; i < values.length; i++) {
        objArray[i] = values[i];
      }
      return objArray;
    } else {
      return (Object[]) obj;
    }
  }

  /**
   * give the number of bits needed to encode an int given the max value
   *
   * @param bound max int that we want to encode
   * @return the number of bits required
   */
  public static int getWidthFromMaxInt(int bound) {
    return 32 - Integer.numberOfLeadingZeros(bound);
  }

  public static Object getListStats(BatchListIterator iterator){
   if(iterator.getPrimitiveType().equals(int.class)){
     IntSummaryStatistics stats = new IntSummaryStatistics();
     IntListIterator intListIterator = (IntListIterator) iterator;
     intListIterator.supplyTo(stats);
     return stats;
   } else if(iterator.getPrimitiveType().equals(CharBuffer.class)){
     IntSummaryStatistics stats = new IntSummaryStatistics();
     int size = iterator.optimumBufferSize();
     CharBuffer[] buf = new CharBuffer[size];
     int cnt;
     while (iterator.hasNext()){
       cnt = iterator.next(buf, 0, size);
       for(int i=0; i< cnt; i++){
         stats.accept(buf[i].length());
       }
     }
     return stats;
   } else {
     throw new UnsupportedOperationException();
   }

  }
}
