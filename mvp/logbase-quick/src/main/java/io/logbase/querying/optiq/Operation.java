package io.logbase.querying.optiq;

import io.logbase.functions.FunctionFactory.FunctionOperator;

public class Operation {
  FunctionOperator operator;
  Object[] operands;

  public Operation(FunctionOperator operator, Object[] operands) {
    this.operands = operands;
    this.operator = operator;
  }

  public FunctionOperator getOperator() {
    return operator;
  }

  public void setOperator(FunctionOperator operator) {
    this.operator = operator;
  }

  public Object[] getOperands() {
    return operands;
  }

  public void setOperands(Object[] operands) {
    this.operands = operands;
  }
}
