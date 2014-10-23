package io.logbase.api.antlr;

import io.logbase.querying.optiq.LBTable;

import java.util.Stack;

import org.antlr.v4.runtime.misc.NotNull;

public class LbqlSqlTranslator extends LbqlBaseListener {

  private String whereClause = "";
  private String selectClause = "";
  private String fromClause = "";
  private String sql = null;
  private Stack<String> stack = new Stack<String>();
  private final String RAW_EVENT_COL = "\"RawEvent.String\"";
  private final String DEFAULT_TABLE = "\"TEST\".\"TWITTER\"";

  @Override
  public void enterPredicate(@NotNull LbqlParser.PredicateContext ctx) {
    String value = ctx.getChild(2).getText();
    value = handleQuotation(value, ctx.getChild(0).getText());
    String exprString = "\"" + ctx.getChild(0).getText() + "\" "
        + ctx.getChild(1).getText() + " " + value;
    stack.push(exprString);
  }

  @Override
  public void exitExpopexp(@NotNull LbqlParser.ExpopexpContext ctx) {
    //Implies pop two entries in stack and include operator and oush back
    String rightSide = stack.pop();
    String leftSide = stack.pop();
    String exprString = leftSide + " " + ctx.getChild(1).getText() + " "
        + rightSide;
    stack.push(exprString);
  }

  @Override
  public void enterTextexpr(@NotNull LbqlParser.TextexprContext ctx) {
    String value = ctx.getChild(0).getText();
    value = handleQuotation(value,
        RAW_EVENT_COL.substring(1, RAW_EVENT_COL.length() - 1));
    String exprString = RAW_EVENT_COL + " LIKE " + value;
    stack.push(exprString);
  }


  @Override
  public void exitExprgroup(@NotNull LbqlParser.ExprgroupContext ctx) {
    // Pop last expression and add brackets
    String exprString = "( " + stack.pop() + " )";
    stack.push(exprString);
  }

  @Override
  public void exitStatement(@NotNull LbqlParser.StatementContext ctx) {
    whereClause = "WHERE " + stack.pop();
    if (selectClause.equals(""))
      selectClause = "SELECT " + RAW_EVENT_COL;
    if (fromClause.equals(""))
      fromClause = "FROM " + DEFAULT_TABLE;
    sql = selectClause + " " + fromClause + " " + whereClause;
  }

  @Override
  public void exitExpexp(@NotNull LbqlParser.ExpexpContext ctx) {
    String rightSide = stack.pop();
    String leftSide = stack.pop();
    String exprString = leftSide + " AND " + rightSide;
    stack.push(exprString);
  }

  // TODO
  // Improve this
  private String handleQuotation(String value, String columnName) {
    if (!value.startsWith("\"") && !value.endsWith("\"")) {
      if (LBTable.getJavaColumnType(columnName).equals(String.class))
        value = "\'" + value + "\'";
    } else {
      value = value.substring(1, value.length() - 1);
      value = "\'" + value + "\'";
    }
    return value;
  }

  @Override
  public void enterShowcmd(@NotNull LbqlParser.ShowcmdContext ctx) {
    int childCount = ctx.getChildCount();
    if (childCount > 1) {
      selectClause = "SELECT";
      for (int i = 1; i < childCount; i++) {
        if (i == 1)
          selectClause = selectClause + " \"" + ctx.getChild(i).getText()
              + "\"";
        else
          selectClause = selectClause + ", \"" + ctx.getChild(i).getText()
              + "\"";
      }
    }
  }

  public String getSql() {
    return sql;
  }

  public String getSelectClause() {
    return selectClause;
  }

  public String getWhereClause() {
    return whereClause;
  }

  public String getFromClause() {
    return fromClause;
  }

}
