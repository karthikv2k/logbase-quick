package io.logbase.functions.impl.Benchmark;

import io.logbase.collections.impl.DataGen;
import io.logbase.column.Column;
import io.logbase.column.ColumnFactory;
import io.logbase.column.TypeUtils;

import java.nio.CharBuffer;

/**
 * Create a pre populated column for a given type
 *
 * Created by Kousik on 28/10/14.
 */
public class ColumnGenerator {

  public static Column getColumn(Class type, int numRows) {

    if (!(type == Integer.class ||
        type == Float.class ||
        type == Long.class ||
        type == Double.class)) {
      throw new UnsupportedOperationException();
    }
    Column column = ColumnFactory.createAppendOnlyColumn(type, "Test column", 0);
    for (int i=0; i<numRows; i++) {
      column.append(DataGen.rand(TypeUtils.getPrimitiveType(type)), i);
    }
    return column;
  }
}
