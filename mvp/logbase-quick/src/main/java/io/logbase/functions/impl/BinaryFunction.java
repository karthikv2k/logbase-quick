package io.logbase.functions.impl;

import io.logbase.collections.impl.BitsetList;
import io.logbase.column.Column;
import io.logbase.column.appendonly.AppendOnlyColumn;
import io.logbase.functions.Function;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Kousik on 30/09/14.
 */
public abstract class BinaryFunction implements Function {
  private Column[] operands;
  private BitsetList validRowList;
  private Column rows;

  public BinaryFunction(Object[] operands) {
    this.operands = new Column[2];
    this.validRowList = new BitsetList();
    this.rows = new AppendOnlyColumn("Valid Rows", 0, validRowList);

    checkArgument(operands.length == 2, "Only 2 operands expected");
    for (Object operand: operands) {
      checkNotNull(operand, "Operand(s) should not be NULL");
    }
    checkArgument(operands[0] instanceof Column, "First operand should be a Column");
    checkArgument(operands[1] instanceof Column, "Second operand should be the search string");

    this.operands[0] = (Column)operands[0];
    this.operands[1] = (Column)operands[1];

  }

  protected Column[] getOperands() {
    return operands;
  }

  protected Column getRowColumn() {
    return rows;
  }
}
