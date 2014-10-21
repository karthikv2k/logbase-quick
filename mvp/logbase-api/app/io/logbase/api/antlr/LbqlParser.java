package io.logbase.api.antlr;
// Generated from Lbql.g4 by ANTLR 4.3
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class LbqlParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__3=1, T__2=2, T__1=3, T__0=4, AND=5, OR=6, NOT=7, EQUALS=8, NOTEQUALS=9, 
		GREQUALS=10, LSEQUALS=11, GREATERTHAN=12, LESSTHAN=13, NUMBER=14, QTEXT=15, 
		UQTEXT=16, WS=17;
	public static final String[] tokenNames = {
		"<INVALID>", "'| show'", "'('", "')'", "','", "'AND'", "'OR'", "'NOT'", 
		"'='", "'!='", "'>='", "'<='", "'>'", "'<'", "NUMBER", "QTEXT", "UQTEXT", 
		"WS"
	};
	public static final int
		RULE_statement = 0, RULE_expr = 1, RULE_predicate = 2, RULE_command = 3, 
		RULE_text = 4;
	public static final String[] ruleNames = {
		"statement", "expr", "predicate", "command", "text"
	};

	@Override
	public String getGrammarFileName() { return "Lbql.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public LbqlParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class StatementContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public List<CommandContext> command() {
			return getRuleContexts(CommandContext.class);
		}
		public CommandContext command(int i) {
			return getRuleContext(CommandContext.class,i);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LbqlListener ) ((LbqlListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LbqlListener ) ((LbqlListener)listener).exitStatement(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_statement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(10); expr(0);
			setState(14);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__3) {
				{
				{
				setState(11); command();
				}
				}
				setState(16);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExprContext extends ParserRuleContext {
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
	 
		public ExprContext() { }
		public void copyFrom(ExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class PredicexprContext extends ExprContext {
		public PredicateContext predicate() {
			return getRuleContext(PredicateContext.class,0);
		}
		public PredicexprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LbqlListener ) ((LbqlListener)listener).enterPredicexpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LbqlListener ) ((LbqlListener)listener).exitPredicexpr(this);
		}
	}
	public static class TextexprContext extends ExprContext {
		public TextContext text() {
			return getRuleContext(TextContext.class,0);
		}
		public TextexprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LbqlListener ) ((LbqlListener)listener).enterTextexpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LbqlListener ) ((LbqlListener)listener).exitTextexpr(this);
		}
	}
	public static class ExpexpContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public ExpexpContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LbqlListener ) ((LbqlListener)listener).enterExpexp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LbqlListener ) ((LbqlListener)listener).exitExpexp(this);
		}
	}
	public static class ExprgroupContext extends ExprContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ExprgroupContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LbqlListener ) ((LbqlListener)listener).enterExprgroup(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LbqlListener ) ((LbqlListener)listener).exitExprgroup(this);
		}
	}
	public static class ExpopexpContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public ExpopexpContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LbqlListener ) ((LbqlListener)listener).enterExpopexp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LbqlListener ) ((LbqlListener)listener).exitExpopexp(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		return expr(0);
	}

	private ExprContext expr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExprContext _localctx = new ExprContext(_ctx, _parentState);
		ExprContext _prevctx = _localctx;
		int _startState = 2;
		enterRecursionRule(_localctx, 2, RULE_expr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(24);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				{
				_localctx = new PredicexprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(18); predicate();
				}
				break;

			case 2:
				{
				_localctx = new TextexprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(19); text();
				}
				break;

			case 3:
				{
				_localctx = new ExprgroupContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(20); match(T__2);
				setState(21); expr(0);
				setState(22); match(T__1);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(33);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(31);
					switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
					case 1:
						{
						_localctx = new ExpopexpContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(26);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(27);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << AND) | (1L << OR) | (1L << NOT))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						consume();
						setState(28); expr(6);
						}
						break;

					case 2:
						{
						_localctx = new ExpexpContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(29);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(30); expr(0);
						}
						break;
					}
					} 
				}
				setState(35);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class PredicateContext extends ParserRuleContext {
		public List<TextContext> text() {
			return getRuleContexts(TextContext.class);
		}
		public TextContext text(int i) {
			return getRuleContext(TextContext.class,i);
		}
		public PredicateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_predicate; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LbqlListener ) ((LbqlListener)listener).enterPredicate(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LbqlListener ) ((LbqlListener)listener).exitPredicate(this);
		}
	}

	public final PredicateContext predicate() throws RecognitionException {
		PredicateContext _localctx = new PredicateContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_predicate);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(36); text();
			setState(37);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << EQUALS) | (1L << NOTEQUALS) | (1L << GREQUALS) | (1L << LSEQUALS) | (1L << GREATERTHAN) | (1L << LESSTHAN))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			setState(38); text();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CommandContext extends ParserRuleContext {
		public CommandContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_command; }
	 
		public CommandContext() { }
		public void copyFrom(CommandContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ShowcsvContext extends CommandContext {
		public List<TextContext> text() {
			return getRuleContexts(TextContext.class);
		}
		public TextContext text(int i) {
			return getRuleContext(TextContext.class,i);
		}
		public ShowcsvContext(CommandContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LbqlListener ) ((LbqlListener)listener).enterShowcsv(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LbqlListener ) ((LbqlListener)listener).exitShowcsv(this);
		}
	}
	public static class ShowcmdContext extends CommandContext {
		public List<TextContext> text() {
			return getRuleContexts(TextContext.class);
		}
		public TextContext text(int i) {
			return getRuleContext(TextContext.class,i);
		}
		public ShowcmdContext(CommandContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LbqlListener ) ((LbqlListener)listener).enterShowcmd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LbqlListener ) ((LbqlListener)listener).exitShowcmd(this);
		}
	}

	public final CommandContext command() throws RecognitionException {
		CommandContext _localctx = new CommandContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_command);
		int _la;
		try {
			setState(56);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				_localctx = new ShowcmdContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(40); match(T__3);
				setState(44);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << NUMBER) | (1L << QTEXT) | (1L << UQTEXT))) != 0)) {
					{
					{
					setState(41); text();
					}
					}
					setState(46);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;

			case 2:
				_localctx = new ShowcsvContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(47); match(T__3);
				setState(48); text();
				setState(53);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__0) {
					{
					{
					setState(49); match(T__0);
					setState(50); text();
					}
					}
					setState(55);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TextContext extends ParserRuleContext {
		public TextContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_text; }
	 
		public TextContext() { }
		public void copyFrom(TextContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class UnquotedtxtContext extends TextContext {
		public TerminalNode UQTEXT() { return getToken(LbqlParser.UQTEXT, 0); }
		public UnquotedtxtContext(TextContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LbqlListener ) ((LbqlListener)listener).enterUnquotedtxt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LbqlListener ) ((LbqlListener)listener).exitUnquotedtxt(this);
		}
	}
	public static class QuotedtxtContext extends TextContext {
		public TerminalNode QTEXT() { return getToken(LbqlParser.QTEXT, 0); }
		public QuotedtxtContext(TextContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LbqlListener ) ((LbqlListener)listener).enterQuotedtxt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LbqlListener ) ((LbqlListener)listener).exitQuotedtxt(this);
		}
	}
	public static class NumbertxtContext extends TextContext {
		public TerminalNode NUMBER() { return getToken(LbqlParser.NUMBER, 0); }
		public NumbertxtContext(TextContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LbqlListener ) ((LbqlListener)listener).enterNumbertxt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LbqlListener ) ((LbqlListener)listener).exitNumbertxt(this);
		}
	}

	public final TextContext text() throws RecognitionException {
		TextContext _localctx = new TextContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_text);
		try {
			setState(61);
			switch (_input.LA(1)) {
			case NUMBER:
				_localctx = new NumbertxtContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(58); match(NUMBER);
				}
				break;
			case QTEXT:
				_localctx = new QuotedtxtContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(59); match(QTEXT);
				}
				break;
			case UQTEXT:
				_localctx = new UnquotedtxtContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(60); match(UQTEXT);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 1: return expr_sempred((ExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expr_sempred(ExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0: return precpred(_ctx, 5);

		case 1: return precpred(_ctx, 4);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\23B\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\3\2\3\2\7\2\17\n\2\f\2\16\2\22\13\2\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\5\3\33\n\3\3\3\3\3\3\3\3\3\3\3\7\3\"\n\3\f\3\16\3"+
		"%\13\3\3\4\3\4\3\4\3\4\3\5\3\5\7\5-\n\5\f\5\16\5\60\13\5\3\5\3\5\3\5\3"+
		"\5\7\5\66\n\5\f\5\16\59\13\5\5\5;\n\5\3\6\3\6\3\6\5\6@\n\6\3\6\2\3\4\7"+
		"\2\4\6\b\n\2\4\3\2\7\t\3\2\n\17F\2\f\3\2\2\2\4\32\3\2\2\2\6&\3\2\2\2\b"+
		":\3\2\2\2\n?\3\2\2\2\f\20\5\4\3\2\r\17\5\b\5\2\16\r\3\2\2\2\17\22\3\2"+
		"\2\2\20\16\3\2\2\2\20\21\3\2\2\2\21\3\3\2\2\2\22\20\3\2\2\2\23\24\b\3"+
		"\1\2\24\33\5\6\4\2\25\33\5\n\6\2\26\27\7\4\2\2\27\30\5\4\3\2\30\31\7\5"+
		"\2\2\31\33\3\2\2\2\32\23\3\2\2\2\32\25\3\2\2\2\32\26\3\2\2\2\33#\3\2\2"+
		"\2\34\35\f\7\2\2\35\36\t\2\2\2\36\"\5\4\3\b\37 \f\6\2\2 \"\5\4\3\2!\34"+
		"\3\2\2\2!\37\3\2\2\2\"%\3\2\2\2#!\3\2\2\2#$\3\2\2\2$\5\3\2\2\2%#\3\2\2"+
		"\2&\'\5\n\6\2\'(\t\3\2\2()\5\n\6\2)\7\3\2\2\2*.\7\3\2\2+-\5\n\6\2,+\3"+
		"\2\2\2-\60\3\2\2\2.,\3\2\2\2./\3\2\2\2/;\3\2\2\2\60.\3\2\2\2\61\62\7\3"+
		"\2\2\62\67\5\n\6\2\63\64\7\6\2\2\64\66\5\n\6\2\65\63\3\2\2\2\669\3\2\2"+
		"\2\67\65\3\2\2\2\678\3\2\2\28;\3\2\2\29\67\3\2\2\2:*\3\2\2\2:\61\3\2\2"+
		"\2;\t\3\2\2\2<@\7\20\2\2=@\7\21\2\2>@\7\22\2\2?<\3\2\2\2?=\3\2\2\2?>\3"+
		"\2\2\2@\13\3\2\2\2\n\20\32!#.\67:?";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}