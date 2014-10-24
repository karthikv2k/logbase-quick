package io.logbase.collections.impl;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import io.logbase.collections.BatchList;
import io.logbase.collections.BatchListIterator;
import io.logbase.collections.BatchListReader;
import io.logbase.collections.BatchListWriter;
import io.logbase.collections.nativelists.IntList;


/**
 * Created by Kousik on 17/10/14.
 */
public class StringDictionaryList implements BatchList<String>{


  public final IntList tokenCount; //Keeps track of no of tokens in the given string/text
  public final IntList tokenIndex; //Stores the index of each of the token in the given string/text
  // TODO TBA - change memory inefficient BiMap to custom Map
  public final BiMap<String, Integer> dictionary;
  private long size;

  public StringDictionaryList() {
    tokenCount = new IntegerArrayList();
    tokenIndex = new IntegerArrayList();
    dictionary = HashBiMap.create();
  }

  @Override
  public long size() {
    return size;
  }

  @Override
  public BatchListIterator<String> iterator(long maxIndex) {
    return (BatchListIterator) new StringDictionaryListIterator(this, maxIndex);
  }

  @Override
  public BatchListReader<String> reader(long maxIndex) {
    return (BatchListReader) new StringDictionaryListReader(this, maxIndex);
  }

  @Override
  public BatchListWriter<String> writer() {
    return (BatchListWriter)new StringDictionaryListWriter(this);
  }

  @Override
  public Class<String> type() {
    return String.class;
  }

  @Override
  public long memSize() {
    // TODO - need to account for dictionary memory
    return tokenIndex.memSize() + tokenCount.memSize();
  }

  public void incSize() {
    size++;
  }
}
