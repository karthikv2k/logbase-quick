package io.logbase.collections.impl;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.sun.corba.se.impl.encoding.OSFCodeSetRegistry;
import io.logbase.collections.BatchList;
import io.logbase.collections.BatchListIterator;
import io.logbase.collections.BatchListReader;
import io.logbase.collections.BatchListWriter;
import io.logbase.collections.nativelists.BooleanList;
import io.logbase.collections.nativelists.BooleanListWriter;
import io.logbase.collections.nativelists.IntList;
import io.logbase.collections.nativelists.IntListIterator;
import io.logbase.exceptions.UnsupportedFunctionPredicateException;
import io.logbase.functions.Predicates.FunctionPredicate;


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

  @Override
  public void execute(FunctionPredicate predicate, BooleanList list) {

    IntListIterator tokenCountIterator = tokenCount.primitiveIterator(tokenCount.size());
    IntListIterator tokenIndexIterator = tokenIndex.primitiveIterator(tokenIndex.size());
    int[] tokenCountBuffer = new int[1024];
    BooleanListWriter booleanListWriter = list.primitiveWriter();
    boolean indexFound = false;
    int index = 0;

    // Search the dictionary and get the index
    for (String key : dictionary.keySet()) {
      if (predicate.apply(key)) {
        index = dictionary.get(key);
        indexFound = true;
        break;
      }
    }

    // If index not found, then mark entry not found for all rows and return.
    if (!indexFound) {
      for (int i=0; i<tokenCount.size(); i++) {
        booleanListWriter.add(false);
      }
      return;
    }


    // If index found, search for rows matching the index
    //TODO - optimize
    int count, indexCount;
    boolean entryFound;
    while (tokenCountIterator.hasNext()) {
      count = tokenCountIterator.nextPrimitive(tokenCountBuffer, 0, tokenCountBuffer.length);
      for (int i=0; i<count; i++) {
        int[] tokenIndexBuffer = new int[tokenCountBuffer[i]];
        indexCount = tokenIndexIterator.nextPrimitive(tokenIndexBuffer, 0, tokenIndexBuffer.length);
        entryFound = false;
        assert(indexCount == tokenCountBuffer[i]);

        for (int j=0; j<indexCount; j++) {
          if (index == tokenIndexBuffer[j]) {
            entryFound = true;
            break;
          }
        }
        booleanListWriter.add(entryFound);
      }
    }
  }
}
