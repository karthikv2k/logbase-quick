package io.logbase.collections.impl;

import io.logbase.collections.BatchListIterator;
import io.logbase.collections.nativelists.IntListIterator;

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

  public int nextPrimitive(CharBuffer[] buffer, int offset, int count) {
    count = (int)Math.min(count, remaining());
    int curCnt = 0;
    while(curCnt < count){
      int cnt = lengthIterator.nextPrimitive(lengthBuf, 0, Math.min(lengthBuf.length,(count-curCnt)));
      for(int i=0; i<cnt; i++){
        buffer[curCnt] = stringBuffer.subSequence(totalOffset, lengthBuf[i]);
        curCnt++;
      }
    }
    return curCnt;
  }

    @Override
  public void rewind() {
    lengthIterator = list.lengthList.primitiveIterator(maxIndex);
  }

  @Override
  public Class getPrimitiveType() {
    return CharBuffer.class;
  }

}
