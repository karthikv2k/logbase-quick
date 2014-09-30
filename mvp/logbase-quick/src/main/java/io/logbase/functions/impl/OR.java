package io.logbase.functions.impl;

import io.logbase.column.Column;
import io.logbase.column.SimpleColumnIterator;

/**
 * Created by Kousik on 30/09/14.
 */
public class OR extends BinaryFunction {
  @Override
  public void execute() {
    Column[] operands = super.getOperands();
    SimpleColumnIterator itrA = new SimpleColumnIterator(operands[0], operands[0].getRowCount());
    SimpleColumnIterator itrB = new SimpleColumnIterator(operands[1], operands[1].getRowCount());
    long rowNum = 0;

    while (itrA.hasNext() || itrB.hasNext()) {
      Boolean bufferA = (Boolean)itrA.next();
      Boolean bufferB = (Boolean)itrB.next();

      if ((bufferA != null && bufferA.booleanValue()) ||
          (bufferB != null && bufferB.booleanValue())) {
        super.append(true, rowNum);
      }
      rowNum++;
    }
  }
}
