package io.logbase.collections.impl;

import io.logbase.collections.BatchListIterator;
import io.logbase.collections.StringArrayHolder;
import io.logbase.collections.nativelists.IntListIterator;
import io.logbase.collections.nativelists.StringListIterator;

import java.nio.CharBuffer;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class StringBufListIterator implements StringListIterator {
  private final CharBuffer stringBuffer;
  private final StringBufList list;
  private IntListIterator lengthIterator;
  private final long maxIndex;
  private long totalRead = 0;
  private int totalOffset = 0;

  public StringBufListIterator(StringBufList list, long maxIndex) {
    this.stringBuffer = list.getReadBuffer();
    this.list = list;
    this.maxIndex = Math.min(maxIndex, list.size());
    this.lengthIterator = list.lengthList.primitiveIterator(maxIndex);
    rewind();
  }

  @Override
  public long remaining() {
    return maxIndex-totalRead;
  }

  @Override
  public int next(Object buffer, int offset, int count) {
    checkNotNull(buffer, "Null buffer is not permitted");
    checkArgument(buffer instanceof StringArrayHolder, "buffer must be StringArrayHolder, found " + buffer.getClass().getSimpleName());
    return nextPrimitive((StringArrayHolder) buffer, offset, count);
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

  @Override
  public int nextPrimitive(StringArrayHolder buffer, int offset, int count) {
    count = (int)Math.min(count, remaining());
    int cnt = lengthIterator.nextPrimitive(buffer.offsets, 0, count);
    assert cnt==count;
    int sum = 0;
    for(int i=0; i<count; i++){
      sum = sum +buffer.offsets[i];
    }
    stringBuffer.subSequence(totalOffset, totalOffset+sum);
    totalOffset = totalOffset + sum;
    totalRead = totalRead + count;
    return count;
  }
}
