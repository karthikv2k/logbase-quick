package io.logbase.datamodel;

public interface ColumnType<E> extends Operand {
  public void set(E value);

  public E get();

  public Class<E> getNativeJavaClass();

  public String toString();
}
