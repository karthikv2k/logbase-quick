package io.logbase.api.antlr;
// Generated from Lbql.g4 by ANTLR 4.3
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class LbqlLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__12=1, T__11=2, T__10=3, T__9=4, T__8=5, T__7=6, T__6=7, T__5=8, T__4=9, 
		T__3=10, T__2=11, T__1=12, T__0=13, TEXT=14, WS=15;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"'\\u0000'", "'\\u0001'", "'\\u0002'", "'\\u0003'", "'\\u0004'", "'\\u0005'", 
		"'\\u0006'", "'\\u0007'", "'\b'", "'\t'", "'\n'", "'\\u000B'", "'\f'", 
		"'\r'", "'\\u000E'", "'\\u000F'"
	};
	public static final String[] ruleNames = {
		"T__12", "T__11", "T__10", "T__9", "T__8", "T__7", "T__6", "T__5", "T__4", 
		"T__3", "T__2", "T__1", "T__0", "TEXT", "ESC", "WS"
	};


	public LbqlLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Lbql.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\21g\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\3\2\3\2\3"+
		"\2\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\7\3\7\3\b\3\b"+
		"\3\t\3\t\3\t\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3"+
		"\r\3\r\3\16\3\16\3\17\3\17\3\17\7\17N\n\17\f\17\16\17Q\13\17\3\17\3\17"+
		"\6\17U\n\17\r\17\16\17V\5\17Y\n\17\3\20\3\20\3\20\3\20\5\20_\n\20\3\21"+
		"\6\21b\n\21\r\21\16\21c\3\21\3\21\3O\2\22\3\3\5\4\7\5\t\6\13\7\r\b\17"+
		"\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\2!\21\3\2\4\5\2\f\f\17\17"+
		"\"\"\5\2\13\f\17\17\"\"k\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2"+
		"\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25"+
		"\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2!\3\2\2"+
		"\2\3#\3\2\2\2\5&\3\2\2\2\7)\3\2\2\2\t-\3\2\2\2\13/\3\2\2\2\r\61\3\2\2"+
		"\2\17\65\3\2\2\2\21\67\3\2\2\2\23:\3\2\2\2\25=\3\2\2\2\27D\3\2\2\2\31"+
		"F\3\2\2\2\33H\3\2\2\2\35X\3\2\2\2\37^\3\2\2\2!a\3\2\2\2#$\7#\2\2$%\7?"+
		"\2\2%\4\3\2\2\2&\'\7@\2\2\'(\7?\2\2(\6\3\2\2\2)*\7C\2\2*+\7P\2\2+,\7F"+
		"\2\2,\b\3\2\2\2-.\7>\2\2.\n\3\2\2\2/\60\7?\2\2\60\f\3\2\2\2\61\62\7P\2"+
		"\2\62\63\7Q\2\2\63\64\7V\2\2\64\16\3\2\2\2\65\66\7@\2\2\66\20\3\2\2\2"+
		"\678\7Q\2\289\7T\2\29\22\3\2\2\2:;\7>\2\2;<\7?\2\2<\24\3\2\2\2=>\7~\2"+
		"\2>?\7\"\2\2?@\7u\2\2@A\7j\2\2AB\7q\2\2BC\7y\2\2C\26\3\2\2\2DE\7*\2\2"+
		"E\30\3\2\2\2FG\7+\2\2G\32\3\2\2\2HI\7.\2\2I\34\3\2\2\2JO\7$\2\2KN\5\37"+
		"\20\2LN\13\2\2\2MK\3\2\2\2ML\3\2\2\2NQ\3\2\2\2OP\3\2\2\2OM\3\2\2\2PR\3"+
		"\2\2\2QO\3\2\2\2RY\7$\2\2SU\n\2\2\2TS\3\2\2\2UV\3\2\2\2VT\3\2\2\2VW\3"+
		"\2\2\2WY\3\2\2\2XJ\3\2\2\2XT\3\2\2\2Y\36\3\2\2\2Z[\7^\2\2[_\7$\2\2\\]"+
		"\7^\2\2]_\7^\2\2^Z\3\2\2\2^\\\3\2\2\2_ \3\2\2\2`b\t\3\2\2a`\3\2\2\2bc"+
		"\3\2\2\2ca\3\2\2\2cd\3\2\2\2de\3\2\2\2ef\b\21\2\2f\"\3\2\2\2\t\2MOVX^"+
		"c\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}