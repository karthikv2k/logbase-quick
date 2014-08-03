package io.logbase.collections.impl;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class BPIntHeapList extends BaseList<Integer> {
  public final long[] buf;
  public final int maxValue;
  public final int width;
  public final int listSize;
  public int size = 0;


  BPIntHeapList(int maxValue, int listSize){
    checkArgument(maxValue>0, "maxValue should be a positive number.");
    this.maxValue = maxValue;
    this.width = getWidthFromMaxInt(maxValue);
    this.listSize = listSize;
    int arraySize = (int) Math.ceil(((double)(listSize*width))/64)+1;
    buf = new long[arraySize];
  }

  /**
   * give the number of bits needed to encode an int given the max value
   * @param bound max int that we want to encode
   * @return the number of bits required
   */
  public static int getWidthFromMaxInt(int bound) {
    return 32 - Integer.numberOfLeadingZeros(bound);
  }

}
