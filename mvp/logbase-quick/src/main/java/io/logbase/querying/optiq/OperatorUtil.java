package io.logbase.querying.optiq;

import com.fasterxml.jackson.annotation.JsonFormat.Value;

import io.logbase.column.Column;
import io.logbase.functions.FunctionFactory.FunctionOperator;
import io.logbase.view.View;

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

  public static Operation customize(Operation operation, Object master) {
    View view = (View) master;

    // TODO
    // master is view for us
    // If operator is EQUALS, change operand to column and value
    // Do any other customization for other operators
    if (operation.getOperator() == FunctionOperator.EQUALS) {
      // Change the variable to column
      String columnName = null;
      Column column = null;
      Object value = null;
      if (operation.getOperands()[1] instanceof String) {
        columnName = (String) operation.getOperands()[1];
        Class valueClass = getJavaColumnType(columnName);
        if (Value.class.equals(String.class)) {
          // trim value and cast to String
          String strValue = (String) operation.getOperands()[2];
          value = strValue.substring(1, strValue.length() - 1);
        } else if (Value.class.equals(Double.class)) {
          value = (Double) operation.getOperands()[2];
        } else if (Value.class.equals(Long.class)) {
          value = (Long) operation.getOperands()[2];
        } else if (Value.class.equals(Integer.class)) {
          value = (Integer) operation.getOperands()[2];
        } else if (Value.class.equals(Float.class)) {
          value = (Float) operation.getOperands()[2];
        } else {
          // Some unsupported type
          throw new UnsupportedOperationException();
        }
        column = view.getColumn(columnName);
        operation.setOperands(new Object[] { column, value });
      }
    }
    return operation;
  }

  private static Class getJavaColumnType(String columnName) {
    if (columnName.endsWith(".String"))
      return String.class;
    if (columnName.endsWith(".Double"))
      return Double.class;
    if (columnName.endsWith(".Float"))
      return Float.class;
    if (columnName.endsWith(".Int"))
      return Integer.class;
    if (columnName.endsWith(".Long"))
      return Long.class;
    else
      return null;
  }

}