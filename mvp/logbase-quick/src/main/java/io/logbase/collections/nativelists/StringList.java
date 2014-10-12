package io.logbase.collections.nativelists;

import io.logbase.collections.BatchList;

import java.nio.CharBuffer;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public interface StringList extends BatchList<CharBuffer> {

  public StringListIterator primitiveIterator(long maxIndex);

  public StringListReader primitiveReader(long maxIndex);

  public StringListWriter primitiveWriter();


  @Override
  public default Class<CharBuffer> type(){
    return CharBuffer.class;
  }

}
