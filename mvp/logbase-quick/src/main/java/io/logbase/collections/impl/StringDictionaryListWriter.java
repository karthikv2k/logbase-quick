package io.logbase.collections.impl;

import io.logbase.collections.BatchListWriter;
import io.logbase.collections.nativelists.IntListWriter;
import io.logbase.utils.SpecialCharacterTokenizer;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created by Kousik on 17/10/14.
 */
public class StringDictionaryListWriter implements BatchListWriter {

  private StringDictionaryList list;
  private IntListWriter tokenIndexWriter;
  private IntListWriter tokenCountWriter;
  private int dictionaryIndex = 0;

  StringDictionaryListWriter(StringDictionaryList list) {
    this.list = list;
    tokenCountWriter = list.tokenCount.primitiveWriter();
    tokenIndexWriter = list.tokenIndex.primitiveWriter();
  }

  @Override
  public void add(Object values, int offset, int length) {
    checkArgument(values instanceof String[], "values should be instance of String[]");
    checkArgument(values != null, "values can't be null");
    String[] stringArray = (String[])values;
    for (int i=offset; i<length; i++) {
      add(stringArray[i]);
    }
  }

  @Override
  public void add(Object value) {
    checkArgument(value instanceof String, "value should be instance of String");
    checkArgument(value != null, "value can't be null");

    SpecialCharacterTokenizer tokenizer = new SpecialCharacterTokenizer((String)value);
    int index;
    int tokenCount = 0;

    // Tokenize the value and add it to the dictionary and appropriate lists
    for(String token: tokenizer) {
      if(list.dictionary.containsKey(token)) {
        index = (list.dictionary.get(token)).intValue();
      } else {
        index = dictionaryIndex;
        list.dictionary.put(token, new Integer(index));
        dictionaryIndex++;
      }
      tokenIndexWriter.add(index);
      tokenCount++;
    }
    tokenCountWriter.add(tokenCount);
    list.incSize();
  }

  @Override
  public boolean close() {
    return false;
  }
}
