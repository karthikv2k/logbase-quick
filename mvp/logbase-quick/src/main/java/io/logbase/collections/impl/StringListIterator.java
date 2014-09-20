package io.logbase.collections.impl;

import io.logbase.collections.BatchListIterator;
import io.logbase.collections.nativelists.IntListIterator;
import io.logbase.utils.Filter;

import java.nio.CharBuffer;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class StringListIterator implements BatchListIterator<CharBuffer> {
  private final CharBuffer stringBuffer;
  private final StringList list;
  private IntListIterator lengthIterator;
  private int offset = 0;
  private final int[] lengthBuf;
  private final long maxIndex;
  private long totalRead = 0;
  private int totalOffset = 0;

  public StringListIterator(StringList list, long maxIndex) {
    this.stringBuffer = list.getReadBuffer();
    this.list = list;
    this.maxIndex = Math.min(maxIndex, list.size());
    this.lengthIterator = list.lengthList.primitiveIterator(maxIndex);
    lengthBuf = new int[lengthIterator.optimumBufferSize()];
    rewind();
  }

  @Override
  public long remaining() {
    return maxIndex-totalRead;
  }

  @Override
  public boolean hasNext() {
    return lengthIterator.hasNext();
  }

  @Override
  public int next(Object buffer, int offset, int count) {
    checkNotNull(buffer, "Null buffer is not permitted");
    checkArgument(buffer instanceof CharBuffer[], "buffer must be int[], found " + buffer.getClass().getSimpleName());
    return nextPrimitive((CharBuffer[]) buffer, offset, count);
  }

  public int nextPrimitive(CharBuffer[] buffer, int offset, int bufferLimit) {
    bufferLimit = (int)Math.min(bufferLimit, remaining());
    int curCnt = 0;
    while(curCnt < bufferLimit){
      int cnt = lengthIterator.nextPrimitive(lengthBuf, 0, Math.min(lengthBuf.length,(bufferLimit-curCnt)));

      /*
       * If we don't have any more entries to read, then return
       */
      if (cnt ==0) {
        return curCnt;
      }

      for(int i=0; i<cnt; i++){
        buffer[offset+curCnt] = stringBuffer.subSequence(totalOffset, totalOffset + lengthBuf[i]);
        totalOffset+=lengthBuf[i];
        curCnt++;
      }
    }
    return curCnt;
  }

  @Override
  public void rewind() {
    lengthIterator = list.lengthList.primitiveIterator(maxIndex);
    totalOffset = 0;
  }

  @Override
  public Class getPrimitiveType() {
    return CharBuffer.class;
  }

}
