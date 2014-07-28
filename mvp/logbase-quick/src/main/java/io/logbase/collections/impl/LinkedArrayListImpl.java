package io.logbase.collections.impl;

import io.logbase.collections.LinkedArrayList;

import java.util.*;

public class LinkedArrayListImpl<E> implements LinkedArrayList<E> {
  private List<E[]> arrays = new LinkedList<E[]>();
  private int blockSize = 1024;
  private int currentBlock = 0;
  private int currentIndex = 0;

  LinkedArrayListImpl(Class<E> type) {
    if (type.equals(Integer.class)) {

    }
  }

  @Override
  public int size() {
    return 0;
  }

  @Override
  public boolean isEmpty() {
    return false;
  }

  @Override
  public boolean contains(Object o) {
    return false;
  }

  @Override
  public Iterator<E> iterator() {
    return null;
  }

  @Override
  public Object[] toArray() {
    return new Object[0];
  }

  @Override
  public <T> T[] toArray(T[] ts) {
    return null;
  }

  @Override
  public boolean add(E e) {
    return false;
  }

  @Override
  public boolean remove(Object o) {
    return false;
  }

  @Override
  public boolean containsAll(Collection<?> objects) {
    return false;
  }

  @Override
  public boolean addAll(Collection<? extends E> es) {
    return false;
  }

  @Override
  public boolean addAll(int i, Collection<? extends E> es) {
    return false;
  }

  @Override
  public boolean removeAll(Collection<?> objects) {
    return false;
  }

  @Override
  public boolean retainAll(Collection<?> objects) {
    return false;
  }

  @Override
  public void clear() {

  }

  @Override
  public E get(int i) {
    return null;
  }

  @Override
  public E set(int i, E e) {
    return null;
  }

  @Override
  public void add(int i, E e) {

  }

  @Override
  public E remove(int i) {
    return null;
  }

  @Override
  public int indexOf(Object o) {
    return 0;
  }

  @Override
  public int lastIndexOf(Object o) {
    return 0;
  }

  @Override
  public ListIterator<E> listIterator() {
    return null;
  }

  @Override
  public ListIterator<E> listIterator(int i) {
    return null;
  }

  @Override
  public List<E> subList(int i, int i2) {
    return null;
  }
}
