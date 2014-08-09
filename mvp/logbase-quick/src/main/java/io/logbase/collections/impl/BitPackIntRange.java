package io.logbase.collections.impl;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class BitPackIntRange {
  private int min = Integer.MAX_VALUE;
  private int max = Integer.MIN_VALUE;

  public int getMax() {
    return max;
  }

  public int getMin() {
    return min;
  }

  public void update(int value){
    min = Math.min(value, min);
    max = Math.max(value, max);
  }

  public void update(int[] values){
    if(values!=null){
      for(int value: values){
        update(value);
      }
    }
  }

  public void update(Integer[] values){
    if(values!=null){
      for(int value: values){
        update(value);
      }
    }
  }

  public void update(Integer value){
    if(value!=null){
      update((int)value);
    }
  }
}
