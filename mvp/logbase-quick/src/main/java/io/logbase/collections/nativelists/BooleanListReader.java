package io.logbase.collections.nativelists;

import io.logbase.collections.BatchListReader;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public interface BooleanListReader extends BatchListReader<Boolean> {

  public boolean getAsBoolean(long index);

}
