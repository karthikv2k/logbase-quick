package io.logbase.querying.optiq;

import io.logbase.column.Column;
import io.logbase.functions.FunctionFactory.FunctionOperator;
import io.logbase.table.Table;
import io.logbase.view.View;

public class OperatorUtil {

  public static FunctionOperator checkOperator(String input) {
    if (input.equals("="))
      return FunctionOperator.EQUALS;
    else if (input.equals("AND"))
      return FunctionOperator.AND;
    else if (input.equals("OR"))
      return FunctionOperator.OR;
    else if (input.equals("LIKE"))
      return FunctionOperator.SEARCH;
    else
      return null;
  }

  public static Operation addOperationContext(Operation operation, Object master) {
    Table table = (Table) master;

    // TODO
    // master is view for us
    // If operator is EQUALS, change operand to column and value
    // Do any other customization for other operators
    if ((operation.getOperator() == FunctionOperator.EQUALS)
        || (operation.getOperator() == FunctionOperator.SEARCH)) {
      // Change the variable to column
      String columnName = null;
      Column column = null;
      Object value = null;
      if (operation.getOperands()[0] instanceof String) {
        columnName = (String) operation.getOperands()[0];
        Class valueClass = LBTable.getJavaColumnType(columnName);
        String strValue = (String) operation.getOperands()[1];
        if (valueClass.equals(String.class)) {
          // trim value and cast to String
          if (strValue.startsWith("\"") && strValue.endsWith("\""))
            value = strValue.substring(1, strValue.length() - 1);
          else
            value = strValue;
        } else if (valueClass.equals(Double.class)) {
          value = Double.parseDouble(strValue);
        } else if (valueClass.equals(Long.class)) {
          value = Long.parseLong(strValue);
        } else if (valueClass.equals(Integer.class)) {
          value = Integer.parseInt(strValue);
        } else if (valueClass.equals(Float.class)) {
          value = Float.parseFloat(strValue);
        } else {
          // Some unsupported type
          throw new UnsupportedOperationException();
        }
        column = table.getColumn(columnName);
        operation.setOperands(new Object[] { column, value });
      }
    }
    return operation;
  }

}
