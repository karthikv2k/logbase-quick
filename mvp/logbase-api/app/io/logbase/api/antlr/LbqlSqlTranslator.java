package io.logbase.api.antlr;

import org.antlr.v4.runtime.misc.NotNull;

public class LbqlSqlTranslator extends LbqlBaseListener {

  private String sql = "";
  private boolean insidePredicate = false;
  private String whereClause = "";
  private String selectClause = "";

  @Override
  public void enterPredicate(@NotNull LbqlParser.PredicateContext ctx) {
    insidePredicate = true;
    System.out.println("Entering Predicate");
    String value = ctx.getChild(2).getText();
    if (!value.startsWith("\"") && !value.endsWith("\""))
      value = "\"" + value + "\"";

    whereClause = whereClause + ctx.getChild(0).getText() + " "
        + ctx.getChild(1).getText() + " " + value + " ";
    System.out.println("Where clause: " + whereClause);
  }

  @Override
  public void exitPredicate(@NotNull LbqlParser.PredicateContext ctx) {
    System.out.println("Exiting Predicate");
    insidePredicate = false;
  }

  @Override
  public void enterExpopexp(@NotNull LbqlParser.ExpopexpContext ctx) {
    System.out.println("Entering ExpOpExp");
  }

  @Override
  public void exitExpopexp(@NotNull LbqlParser.ExpopexpContext ctx) {
    System.out.println("Exiting ExpOpExp");
  }

}
