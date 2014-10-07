package io.logbase.functions.impl;

import io.logbase.collections.impl.BitsetList;
import io.logbase.column.Column;
import io.logbase.column.SimpleColumnIterator;
import io.logbase.column.appendonly.AppendOnlyColumn;
import io.logbase.functions.Function;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Kousik on 07/10/14.
 */
public abstract class Comparator<E> implements Function {
  Column column;
  E value;
  protected BitsetList validRowList;
  protected Column rows;

  public Comparator(Object[] operands) {
    this.validRowList = new BitsetList();
    this.rows = new AppendOnlyColumn("Valid Rows", 0, validRowList);

    checkArgument(operands.length == 2, "Only 2 operands expected");
    for (Object operand: operands) {
      checkNotNull(operand, "Operand(s) should not be NULL");
    }
    checkArgument(operands[0] instanceof Column, "First operand should be a Column");
    // TODO type checking for second operand

    this.column = (Column)operands[0];
    this.value = (E)operands[1];
  }

  protected int compareTo(Object obj) {
    int result = -1;

    if(this.value instanceof Integer) {
      result = ((Integer) this.value).compareTo((Integer)obj);
    } else if (this.value instanceof Float) {
      result = ((Float) this.value).compareTo((Float)obj);
    } else if (this.value instanceof Double) {
      result = ((Double) this.value).compareTo((Double)obj);
    } else if (this.value instanceof String) {
      result = ((String) this.value).compareTo((String)obj);
    } else if (this.value instanceof Boolean) {
      result = ((Boolean) this.value).compareTo((Boolean)obj);
    } else {
      assert(false);
    }

    return result;
  }
}
