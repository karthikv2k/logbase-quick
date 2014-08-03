package io.logbase.column;

public interface ColumnType<E> extends Operand<E> {
  public void set(E value);

  public E get();

  public Class<E> getNativeJavaClass();

  public String toString();
}