package io.logbase.utils;

public class InFilter<E extends Comparable<E>> implements Filter<E> {

  E[] values;

  public InFilter(E... values) {
    this.values = values;
  }

  @Override
  public boolean accept(E value) {
    for (E item : values) {
      if (item.equals(value)) {
        return true;
      }
    }
    return false;
  }

}
