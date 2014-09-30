package io.logbase.functions.impl;

import io.logbase.column.Column;
import io.logbase.column.SimpleColumnIterator;

/**
 * Created by Kousik on 30/09/14.
 */
public class AND extends BinaryFunction {

  public AND(Object[] operands) {
    super(operands);
  }

  @Override
  public Object execute() {
    Column rows = super.getRowColumn();
    Column[] operands = super.getOperands();
    SimpleColumnIterator itrA = new SimpleColumnIterator(operands[0], operands[0].getRowCount());
    SimpleColumnIterator itrB = new SimpleColumnIterator(operands[1], operands[1].getRowCount());
    long rowNum = 0;

    while (itrA.hasNext() && itrB.hasNext()) {
      Boolean bufferA = (Boolean)itrA.next();
      Boolean bufferB = (Boolean)itrB.next();

      if (bufferA != null && bufferB != null &&
          bufferA.booleanValue() && bufferB.booleanValue()) {
        rows.append(true, rowNum);
      }
      rowNum++;
    }
    return rows;
  }
}
