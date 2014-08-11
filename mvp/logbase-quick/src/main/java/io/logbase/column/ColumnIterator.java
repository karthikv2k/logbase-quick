package io.logbase.column;

import io.logbase.collections.BatchIterator;

import java.util.Iterator;

/**
 * This interface is used to iterate through values of a Column in a efficient manner than using point
 * get method @Column.getValue(int rowNum)
 */
public interface ColumnIterator<E> extends BatchIterator<E> {

}
