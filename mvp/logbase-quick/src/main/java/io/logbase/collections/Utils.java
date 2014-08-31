package io.logbase.collections;

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
}
