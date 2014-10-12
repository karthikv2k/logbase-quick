package io.logbase.collections.nativelists;

import io.logbase.collections.BatchListIterator;
import io.logbase.collections.BatchListReader;

import java.nio.CharBuffer;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public interface StringListReader extends BatchListReader<CharBuffer> {

  public CharBuffer getAsCharBuffer(long index);

}
