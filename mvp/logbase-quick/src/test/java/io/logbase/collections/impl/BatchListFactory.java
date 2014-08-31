package io.logbase.collections.impl;

import io.logbase.collections.BatchList;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public interface BatchListFactory<E> {

  public BatchList<E> newInstance();

}
