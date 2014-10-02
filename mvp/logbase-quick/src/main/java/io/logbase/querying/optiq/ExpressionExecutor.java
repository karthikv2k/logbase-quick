package io.logbase.querying.optiq;

import io.logbase.exceptions.InvalidOperandException;
import io.logbase.functions.Function;
import io.logbase.functions.FunctionFactory;

public class ExpressionExecutor {

  public static Object execute(Expression expression, FunctionFactory ff,
      Object master) {
    System.out.println("Going to exec expression...");
    Operation operation = null;
    Function function = null;
    Object output = null;
    while (!expression.isFullyExecuted()) {
      System.out.println("Going to exec operation...");
      operation = OperatorUtil.customize(expression.getNextOperation(), master);
      System.out.println("Got back context enriched operation");
      function = ff.createFunction(operation.getOperator(),
          operation.getOperands());
      try {
        output = function.execute();
      } catch (InvalidOperandException e) {
        System.err.println("Error during operation: " + e);
      }
      expression.storeLastOperationOutput(output);
    }
    System.out.println("Output of operation: "
        + expression.getLastOperationOutput().getClass());
    return expression.getLastOperationOutput();
  }

}
