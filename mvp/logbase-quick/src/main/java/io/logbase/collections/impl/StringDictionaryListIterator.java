package io.logbase.collections.impl;

import io.logbase.collections.BatchListIterator;
import io.logbase.collections.nativelists.IntListIterator;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Kousik on 17/10/14.
 */
public class StringDictionaryListIterator implements BatchListIterator {

  private StringDictionaryList list;
  private IntListIterator tokenCountIterator;
  private IntListIterator tokenIndexIterator;
  private final long maxIndex;
  private long currentIndex = 0;

  public StringDictionaryListIterator(StringDictionaryList list, long maxIndex) {
    this.list = list;
    this.tokenCountIterator = list.tokenCount.primitiveIterator(maxIndex);
    this.tokenIndexIterator = list.tokenIndex.primitiveIterator(list.tokenIndex.size());
    this.maxIndex = Math.min(maxIndex, list.size());
  }

  @Override
  public int next(Object buffer, int offset, int count) {
    checkNotNull(buffer, "Null buffer is not permitted");
    checkArgument(buffer instanceof String[], "buffer must be String[], found " + buffer.getClass().getSimpleName());

    String[] values = (String[])buffer;
    checkArgument((values.length - offset) >=  count, "buffer size is insufficient");
    int curCount = 0;

    while(remaining()>0 && curCount<count) {
      values[curCount] = next();
      curCount++;
    }

    return curCount;
  }

  private String next() {
    int[] tokenCount = new int[1];
    tokenCountIterator.next(tokenCount, 0, 1);
    String value = "";

    int[] tokenIndexes = new int[tokenCount[0]];
    tokenIndexIterator.next(tokenIndexes, 0, tokenCount[0]);

    for(int i=0; i<tokenIndexes.length; i++) {
      value = value.concat(list.dictionary.inverse().get(tokenIndexes[i]));
    }

    currentIndex++;
    return value;
  }

  @Override
  public void rewind() {
    tokenCountIterator.rewind();
    tokenIndexIterator.rewind();
    currentIndex = 0;
  }

  @Override
  public long remaining() {
    return maxIndex - currentIndex;
  }

  @Override
  public Class getPrimitiveType() {
    return String.class;
  }

  public boolean hasNext() {
    return remaining() > 0;
  }
}
