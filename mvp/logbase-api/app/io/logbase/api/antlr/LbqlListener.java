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
	 * Enter a parse tree produced by the {@code predicexpr}
	 * labeled alternative in {@link LbqlParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterPredicexpr(@NotNull LbqlParser.PredicexprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code predicexpr}
	 * labeled alternative in {@link LbqlParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitPredicexpr(@NotNull LbqlParser.PredicexprContext ctx);

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
	 * Enter a parse tree produced by the {@code showcsv}
	 * labeled alternative in {@link LbqlParser#command}.
	 * @param ctx the parse tree
	 */
	void enterShowcsv(@NotNull LbqlParser.ShowcsvContext ctx);
	/**
	 * Exit a parse tree produced by the {@code showcsv}
	 * labeled alternative in {@link LbqlParser#command}.
	 * @param ctx the parse tree
	 */
	void exitShowcsv(@NotNull LbqlParser.ShowcsvContext ctx);

	/**
	 * Enter a parse tree produced by the {@code showcmd}
	 * labeled alternative in {@link LbqlParser#command}.
	 * @param ctx the parse tree
	 */
	void enterShowcmd(@NotNull LbqlParser.ShowcmdContext ctx);
	/**
	 * Exit a parse tree produced by the {@code showcmd}
	 * labeled alternative in {@link LbqlParser#command}.
	 * @param ctx the parse tree
	 */
	void exitShowcmd(@NotNull LbqlParser.ShowcmdContext ctx);

	/**
	 * Enter a parse tree produced by the {@code textexpr}
	 * labeled alternative in {@link LbqlParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterTextexpr(@NotNull LbqlParser.TextexprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code textexpr}
	 * labeled alternative in {@link LbqlParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitTextexpr(@NotNull LbqlParser.TextexprContext ctx);

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
	 * Enter a parse tree produced by the {@code expexp}
	 * labeled alternative in {@link LbqlParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpexp(@NotNull LbqlParser.ExpexpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expexp}
	 * labeled alternative in {@link LbqlParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpexp(@NotNull LbqlParser.ExpexpContext ctx);

	/**
	 * Enter a parse tree produced by the {@code unquotedtxt}
	 * labeled alternative in {@link LbqlParser#text}.
	 * @param ctx the parse tree
	 */
	void enterUnquotedtxt(@NotNull LbqlParser.UnquotedtxtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unquotedtxt}
	 * labeled alternative in {@link LbqlParser#text}.
	 * @param ctx the parse tree
	 */
	void exitUnquotedtxt(@NotNull LbqlParser.UnquotedtxtContext ctx);

	/**
	 * Enter a parse tree produced by the {@code exprgroup}
	 * labeled alternative in {@link LbqlParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExprgroup(@NotNull LbqlParser.ExprgroupContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprgroup}
	 * labeled alternative in {@link LbqlParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExprgroup(@NotNull LbqlParser.ExprgroupContext ctx);

	/**
	 * Enter a parse tree produced by the {@code quotedtxt}
	 * labeled alternative in {@link LbqlParser#text}.
	 * @param ctx the parse tree
	 */
	void enterQuotedtxt(@NotNull LbqlParser.QuotedtxtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code quotedtxt}
	 * labeled alternative in {@link LbqlParser#text}.
	 * @param ctx the parse tree
	 */
	void exitQuotedtxt(@NotNull LbqlParser.QuotedtxtContext ctx);

	/**
	 * Enter a parse tree produced by the {@code expopexp}
	 * labeled alternative in {@link LbqlParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpopexp(@NotNull LbqlParser.ExpopexpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expopexp}
	 * labeled alternative in {@link LbqlParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpopexp(@NotNull LbqlParser.ExpopexpContext ctx);

	/**
	 * Enter a parse tree produced by the {@code numbertxt}
	 * labeled alternative in {@link LbqlParser#text}.
	 * @param ctx the parse tree
	 */
	void enterNumbertxt(@NotNull LbqlParser.NumbertxtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code numbertxt}
	 * labeled alternative in {@link LbqlParser#text}.
	 * @param ctx the parse tree
	 */
	void exitNumbertxt(@NotNull LbqlParser.NumbertxtContext ctx);
}