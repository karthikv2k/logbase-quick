package io.logbase.collections.nativelists;

import io.logbase.collections.BatchList;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public interface BooleanListReader extends BatchList<Boolean> {

  public boolean getAsBoolean(long index);

}
