package io.logbase.buffer;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class LongHeapBuffer extends Buffer {
  private long[] buf;

  public LongHeapBuffer(int capacity){
    buf = new long[capacity];
  }

  @Override
  public long getLong(long index){
    return buf[(int)index];
  }

  @Override
  public void setLong(long index, long value){
    buf[(int)index]=  value;
  }

  @Override
  public long getLong(int index){
    return buf[index];
  }

  @Override
  public void setLong(int index, long value){
    buf[index]=  value;
  }

}
