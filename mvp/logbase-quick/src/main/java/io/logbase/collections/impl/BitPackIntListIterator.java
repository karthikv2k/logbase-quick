package io.logbase.collections.impl;

import io.logbase.collections.BatchIterator;

import java.nio.LongBuffer;
import java.util.Iterator;

import static com.google.common.base.Preconditions.*;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class BitPackIntListIterator implements BatchIterator<Integer> {
  private final BitPackIntList listBuffer;
  private final LongBuffer longBuffer;
  private int arrayIndex = 0;
  private int bitIndex = 64; //index (1 indexed) where MSB of the input goes
  private int[] localBuf = new int[10*1024];
  private int localBufPos = 0;
  private int localBufSize = 0;
  private int totalReads = 0;


  public BitPackIntListIterator(BitPackIntList listBuffer) {
    this.listBuffer = listBuffer;
    this.longBuffer = listBuffer.readBuffer().asLongBuffer();
  }

  private int readInternal(int[] out, int offset, int count){
    //return -1 if the list has no values to read
    if(totalReads>= listBuffer.size()){
      return -1;
    }

    long cur;
    long cur1,cur2;
    long next;
    int i;
    for(i = offset; totalReads< listBuffer.size() && i<offset+count; i++, totalReads++){
      cur = longBuffer.get(arrayIndex);
      if(bitIndex >= listBuffer.width){
        cur1 = cur << (64 - bitIndex);
        cur2 = cur1 >>> (64 - listBuffer.width);
        bitIndex = bitIndex - listBuffer.width;
      }else{
        if(bitIndex>0){
          cur1 = cur << (64 - bitIndex);
          cur2 = cur1 >>> (64 - listBuffer.width);
        }else{
          cur2=0;
        }
        arrayIndex++;
        next = longBuffer.get(arrayIndex);
        bitIndex = 64 - (listBuffer.width - bitIndex);
        next = next >>> bitIndex;
        cur2 |= next;
      }
      out[offset + i] = (int)cur2 + listBuffer.minValue;
    }
    return i-offset;
  }

  @Override
  public int read(Integer[] buffer, int offset, int rows) {
    checkNotNull(buffer, "Null buffer is not permitted");
    checkArgument(offset+rows<=buffer.length, "length of buffer should be greater than offset+rows.");
    int[] holder = new int[buffer.length];
    int count = readInternal(holder, 0, rows);
    for(int i=0; i<holder.length && i<count; i++){
      buffer[offset+i] = holder[i];
    }
    return count;
  }

  @Override
  public boolean primitiveTypeSupport() {
    return true;
  }

  @Override
  public int readNative(Object buffer, int offset, int rows) throws ClassCastException {
    checkNotNull(buffer, "Null buffer is not permitted");
    checkArgument(buffer instanceof int[], "buffer must be int[], found " + buffer.getClass().getSimpleName());
    int[] nativeBuffer = (int[]) buffer;
    checkArgument(offset+rows<=nativeBuffer.length, "length of buffer should be greater than offset+rows.");
    return readInternal(nativeBuffer, offset, rows);
  }

  @Override
  public Iterator<Integer> iterator() {
    return this;
  }

  @Override
  public boolean hasNext() {
    return totalReads< listBuffer.size() || localBufPos<localBufSize;
  }

  @Override
  public Integer next() {
    if(localBufSize>0 && localBufPos<localBufSize){
      return localBuf[localBufPos++];
    } else {
      localBufSize = readInternal(localBuf, 0, localBuf.length);
      localBufPos = 0;
      if(localBufSize>0){
        return next();
      } else {
        checkState(hasNext(), "check hasNext() before calling next()");
        return null;
      }
    }
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException("Readonly list doesn't support remove().");
  }

}
