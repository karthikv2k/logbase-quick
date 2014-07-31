package io.logbase.collections.impl;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class BitPackedIntegerList extends BaseList<Integer> {
  long[] buf;
  final int maxValue;
  final int width;
  final int listSize;
  int size = 0;
  int arrayIndex = 0;
  int bitIndex = 64; //index (1 indexed) where MSB of the input goes

  BitPackedIntegerList(int maxValue, int listSize){
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

  public void write(int[] values){
    long cur;
    for(int value: values){
      cur = value;
      if(bitIndex>=width){ //minimum free space required to insert a value
        bitIndex = bitIndex - width;
      }else{
        cur = cur >>> (width - bitIndex);
        buf[arrayIndex] |= cur;
        cur = value;
        bitIndex = 64 - (width - bitIndex);
        arrayIndex++;
      }
      cur = cur << bitIndex;
      buf[arrayIndex] |= cur;
      size++;
    }
  }

  public void read(){
    int bi = 64;
    int ai = 0;
    long cur;
    long cur1,cur2;
    long next;
    int[] out = new int[size];
    int cnt=0;
    while(ai<arrayIndex){
      cur = buf[ai];
      if(bi >= width){
        cur1 = cur << (64 - bi);
        cur2 = cur1 >>> (64 - width);
        bi = bi - width;
      }else{
        if(bi>0){
          cur1 = cur << (64 - bi);
          cur2 = cur1 >>> (64 - width);
        }else{
          cur2=0;
        }
        ai++;
        next = buf[ai];
        bi = 64 - (width - bi);
        next = next >>> bi;
        cur2 |= next;
      }
      out[cnt] = (int)cur2;
      cnt++;
    }
  }

}
