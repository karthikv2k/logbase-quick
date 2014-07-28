package io.logbase.utils;

public interface Filter<E> {

  public boolean accept(E value);

}
