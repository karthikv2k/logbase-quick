package io.logbase.api.antlr;

import java.util.Stack;

import org.antlr.v4.runtime.misc.NotNull;

public class LbqlSqlTranslator extends LbqlBaseListener {

  private String whereClause = "";
  private String selectClause = "";
  private Stack<String> stack = new Stack<String>();
  private final String RAW_EVENT_COL = "RawEvent.String";

  @Override
  public void enterPredicate(@NotNull LbqlParser.PredicateContext ctx) {
    System.out.println("Entering Predicate");
    String value = ctx.getChild(2).getText();
    if (!value.startsWith("\"") && !value.endsWith("\""))
      value = "\"" + value + "\"";
    String exprString = ctx.getChild(0).getText() + " "
        + ctx.getChild(1).getText() + " " + value;
    stack.push(exprString);
    System.out.println("Predicate output: " + exprString);
  }

  @Override
  public void exitExpopexp(@NotNull LbqlParser.ExpopexpContext ctx) {
    System.out.println("Exiting ExpOpExp");
    //Implies pop two entries in stack and include operator and oush back
    String rightSide = stack.pop();
    String leftSide = stack.pop();
    String exprString = leftSide + " " + ctx.getChild(1).getText() + " "
        + rightSide;
    stack.push(exprString);
    System.out.println("ExpOpExp output: " + exprString);
  }

  @Override
  public void enterTextexpr(@NotNull LbqlParser.TextexprContext ctx) {
    System.out.println("Entering TextExpr");
    String value = ctx.getChild(0).getText();
    if (!value.startsWith("\"") && !value.endsWith("\""))
      value = "\"" + value + "\"";
    String exprString = RAW_EVENT_COL + " = " + value;
    stack.push(exprString);
    System.out.println("TextExpr output: " + exprString);
  }


  @Override
  public void exitExprgroup(@NotNull LbqlParser.ExprgroupContext ctx) {
    System.out.println("Exiting ExprGroup");
    // Pop last expression and add brackets
    String exprString = "( " + stack.pop() + " )";
    stack.push(exprString);
    System.out.println("ExprGroup output: " + exprString);
  }

  @Override
  public void exitStatement(@NotNull LbqlParser.StatementContext ctx) {
    whereClause = stack.pop();
    System.out.println("Where clause: " + whereClause);
  }

  @Override
  public void exitExpexp(@NotNull LbqlParser.ExpexpContext ctx) {
    System.out.println("Exiting ExpExp");
    String rightSide = stack.pop();
    String leftSide = stack.pop();
    String exprString = leftSide + " AND " + rightSide;
    stack.push(exprString);
    System.out.println("ExpOpExp output: " + exprString);
  }

}
