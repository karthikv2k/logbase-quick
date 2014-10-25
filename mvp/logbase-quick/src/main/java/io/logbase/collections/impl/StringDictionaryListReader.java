package io.logbase.collections.impl;

import io.logbase.collections.BatchList;
import io.logbase.collections.BatchListReader;
import io.logbase.collections.nativelists.IntList;
import io.logbase.collections.nativelists.IntListReader;

import java.util.HashMap;

/**
 * Created by Kousik on 17/10/14.
 */
public class StringDictionaryListReader implements BatchListReader {
  private StringDictionaryList list;
  private IntListReader tokenIndexReader;
  private IntListReader tokenCountReader;
  private long maxIndex;
  private int[] offset;

  public StringDictionaryListReader(StringDictionaryList list, long maxIndex) {
    this.list = list;
    this.maxIndex = Math.min(maxIndex, list.tokenCount.size());
    this.tokenCountReader = list.tokenCount.primitiveReader(this.maxIndex);
    this.offset = new int[(int)this.maxIndex + 1];
    offset[0] = 0;

    for (int i=0; i < this.maxIndex; i++) {
      offset[i+1] = offset[i] + tokenCountReader.get(i);
    }

    this.tokenIndexReader = list.tokenIndex.primitiveReader(offset[(int)this.maxIndex]);
  }

  @Override
  public Object get(long index) {
    int dictionaryIndex;
    String value = "";
    int tokenIndexStart = offset[(int)index];
    int tokenIndexEnd = offset[(int)index+1];
    for (int i=tokenIndexStart; i<tokenIndexEnd; i++) {
      dictionaryIndex = tokenIndexReader.get(i);
      value = value.concat(list.dictionary.inverse().get(dictionaryIndex));
    }
    return value;
  }
}
