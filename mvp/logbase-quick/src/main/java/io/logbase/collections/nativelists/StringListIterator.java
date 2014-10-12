package io.logbase.collections.nativelists;

import io.logbase.collections.BatchList;
import io.logbase.collections.BatchListIterator;
import io.logbase.collections.StringArrayHolder;

import java.nio.CharBuffer;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public interface StringListIterator extends BatchListIterator<CharBuffer> {

  public int nextPrimitive(StringArrayHolder buffer, int offset, int count);

}
