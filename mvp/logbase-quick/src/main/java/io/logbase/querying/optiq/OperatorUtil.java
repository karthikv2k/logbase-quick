package io.logbase.querying.optiq;

import io.logbase.functions.FunctionFactory.FunctionOperator;

public class OperatorUtil {

  public static FunctionOperator checkOperator(String input) {
    if (input.equals("="))
      return FunctionOperator.EQUALS;
    else if (input.equals("AND"))
      return FunctionOperator.AND;
    else if (input.equals("OR"))
      return FunctionOperator.OR;
    else
      return null;
  }

}
