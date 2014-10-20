package io.logbase.api.antlr;
// Generated from Lbql.g4 by ANTLR 4.3
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link LbqlParser}.
 */
public interface LbqlListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link LbqlParser#predicate}.
	 * @param ctx the parse tree
	 */
	void enterPredicate(@NotNull LbqlParser.PredicateContext ctx);
	/**
	 * Exit a parse tree produced by {@link LbqlParser#predicate}.
	 * @param ctx the parse tree
	 */
	void exitPredicate(@NotNull LbqlParser.PredicateContext ctx);

	/**
	 * Enter a parse tree produced by {@link LbqlParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(@NotNull LbqlParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link LbqlParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(@NotNull LbqlParser.StatementContext ctx);

	/**
	 * Enter a parse tree produced by {@link LbqlParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(@NotNull LbqlParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link LbqlParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(@NotNull LbqlParser.ExprContext ctx);

	/**
	 * Enter a parse tree produced by {@link LbqlParser#command}.
	 * @param ctx the parse tree
	 */
	void enterCommand(@NotNull LbqlParser.CommandContext ctx);
	/**
	 * Exit a parse tree produced by {@link LbqlParser#command}.
	 * @param ctx the parse tree
	 */
	void exitCommand(@NotNull LbqlParser.CommandContext ctx);
}