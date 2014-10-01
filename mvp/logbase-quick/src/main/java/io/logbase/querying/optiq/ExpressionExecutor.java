package io.logbase.querying.optiq;

import io.logbase.exceptions.InvalidOperandException;
import io.logbase.functions.Function;
import io.logbase.functions.FunctionFactory;
import io.logbase.view.View;

public class ExpressionExecutor {

  public static Object execute(Expression expression, FunctionFactory ff,
      Object master) {

    Operation operation = null;
    Function function = null;
    Object output = null;
    while (!expression.isFullyExecuted()) {
      operation = OperatorUtil.customize(expression.getNextOperation(), master);
      function = ff.createFunction(operation.getOperator(),
          operation.getOperands());
      try {
        output = function.execute();
      } catch (InvalidOperandException e) {
        e.printStackTrace();
      }
      expression.storeLastOperationOutput(output);
    }
    return expression.getLastOperationOutput();
  }

}
