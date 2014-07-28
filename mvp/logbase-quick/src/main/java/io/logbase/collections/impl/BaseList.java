package io.logbase.collections.impl;

import io.logbase.collections.LinkedArrayList;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public abstract class BaseList<E> implements LinkedArrayList<E> {

  @Override
  public int size() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean isEmpty() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean contains(Object o) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Iterator<E> iterator() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Object[] toArray() {
    throw new UnsupportedOperationException();
  }

  @Override
  public <T> T[] toArray(T[] ts) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean add(E e) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean remove(Object o) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean containsAll(Collection<?> objects) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean addAll(Collection<? extends E> es) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean addAll(int i, Collection<? extends E> es) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean removeAll(Collection<?> objects) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean retainAll(Collection<?> objects) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void clear() {
    throw new UnsupportedOperationException();
  }

  @Override
  public E get(int i) {
    throw new UnsupportedOperationException();
  }

  @Override
  public E set(int i, E e) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void add(int i, E e) {
    throw new UnsupportedOperationException();
  }

  @Override
  public E remove(int i) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int indexOf(Object o) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int lastIndexOf(Object o) {
    throw new UnsupportedOperationException();
  }

  @Override
  public ListIterator<E> listIterator() {
    throw new UnsupportedOperationException();
  }

  @Override
  public ListIterator<E> listIterator(int i) {
    throw new UnsupportedOperationException();
  }

  @Override
  public List<E> subList(int i, int i2) {
    throw new UnsupportedOperationException();
  }
}
