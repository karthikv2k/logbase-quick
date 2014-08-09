package io.logbase.column;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Each leaf node in a nested data model forms a column. A column is composed of values same type. A differentiation is
 * made between null values and the column not being present. Since a
 * @param <E>
 */

public interface Column<E> extends Operand<Column> {

  public static Map EMPTY_MAP = new HashMap(1);
  public static List EMPTY_LIST = new LinkedList();
  public static Object NULL = new Object();

  /**
   * First valid row number for this column
   */
  public long getStartRowNum();

  /**
   * Column name is unique identifier in a table.
   *
   * @return
   */
  public String getColumnName();

  /**
   * A column has values of only one type @ColumnType.
   *
   * @return
   */
  public Class getColumnType();

  /**
   * A column supports only appends. Each append should specify the row number associated with the value.
   *
   * @param value  The value to be appended.
   * @param rowNum The row number should be greater than the row number used in previous append call.
   */
  public void append(E value, long rowNum);

  /**
   * Columns can have multiple arrays in its path. Each array's position is stored along with the value so that its
   * nested structure is perfectly preserved.
   *
   * @param value    The value to be appended.
   * @param rowNum   The row number should be greater than the row number used in previous append call.
   * @param arrayIdx Array of indices to each array in the column.
   */
  public void append(E value, long rowNum, int[] arrayIdx);

  /**
   * Gets number of rows in the column.
   *
   * @return
   */
  public long getRowCount();

  /**
   * Gets number of rows in the column where a value is present.
   *
   * @return
   */
  public long getValidRowCount();

  /**
   * get number of valid values in this column. If the column has arrays then this may be greater than tht number of
   * rows in the column
   *
   * @return
   */
  public long getValuesCount();

  /**
   * gets number of arrays in the column's path
   * @return
   */
  public int getArrayCount();

  /**
   * Gets an iterator to iterate through values of the column.
   *
   * @return
   */
  public ColumnIterator<Object> getSimpleIterator(long maxRowNum);

  /**
   * Gets an iterator to iterate through values of the column. Here the maxRowNum is the maximum row number at call time.
   *
   * @return
   */
  public ColumnIterator<Object> getSimpleIterator();

}
