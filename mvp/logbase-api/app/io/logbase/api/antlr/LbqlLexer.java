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
		T__3=1, T__2=2, T__1=3, T__0=4, AND=5, OR=6, NOT=7, EQUALS=8, NOTEQUALS=9, 
		GREQUALS=10, LSEQUALS=11, GREATERTHAN=12, LESSTHAN=13, NUMBER=14, QTEXT=15, 
		UQTEXT=16, WS=17;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"'\\u0000'", "'\\u0001'", "'\\u0002'", "'\\u0003'", "'\\u0004'", "'\\u0005'", 
		"'\\u0006'", "'\\u0007'", "'\b'", "'\t'", "'\n'", "'\\u000B'", "'\f'", 
		"'\r'", "'\\u000E'", "'\\u000F'", "'\\u0010'", "'\\u0011'"
	};
	public static final String[] ruleNames = {
		"T__3", "T__2", "T__1", "T__0", "AND", "OR", "NOT", "EQUALS", "NOTEQUALS", 
		"GREQUALS", "LSEQUALS", "GREATERTHAN", "LESSTHAN", "NUMBER", "QTEXT", 
		"UQTEXT", "DIGIT", "ESC", "WS"
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\23\u0086\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\4\3\4"+
		"\3\5\3\5\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\t\3\t\3\n\3\n\3"+
		"\n\3\13\3\13\3\13\3\f\3\f\3\f\3\r\3\r\3\16\3\16\3\17\6\17R\n\17\r\17\16"+
		"\17S\3\17\6\17W\n\17\r\17\16\17X\3\17\3\17\6\17]\n\17\r\17\16\17^\3\17"+
		"\3\17\6\17c\n\17\r\17\16\17d\5\17g\n\17\3\20\3\20\3\20\7\20l\n\20\f\20"+
		"\16\20o\13\20\3\20\3\20\3\21\6\21t\n\21\r\21\16\21u\3\22\3\22\3\23\3\23"+
		"\3\23\3\23\5\23~\n\23\3\24\6\24\u0081\n\24\r\24\16\24\u0082\3\24\3\24"+
		"\3m\2\25\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17"+
		"\35\20\37\21!\22#\2%\2\'\23\3\2\5\b\2\f\f\17\17\"#*+..>@\3\2\62;\5\2\13"+
		"\f\17\17\"\"\u008e\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13"+
		"\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2"+
		"\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2"+
		"!\3\2\2\2\2\'\3\2\2\2\3)\3\2\2\2\5\60\3\2\2\2\7\62\3\2\2\2\t\64\3\2\2"+
		"\2\13\66\3\2\2\2\r:\3\2\2\2\17=\3\2\2\2\21A\3\2\2\2\23C\3\2\2\2\25F\3"+
		"\2\2\2\27I\3\2\2\2\31L\3\2\2\2\33N\3\2\2\2\35f\3\2\2\2\37h\3\2\2\2!s\3"+
		"\2\2\2#w\3\2\2\2%}\3\2\2\2\'\u0080\3\2\2\2)*\7~\2\2*+\7\"\2\2+,\7u\2\2"+
		",-\7j\2\2-.\7q\2\2./\7y\2\2/\4\3\2\2\2\60\61\7*\2\2\61\6\3\2\2\2\62\63"+
		"\7+\2\2\63\b\3\2\2\2\64\65\7.\2\2\65\n\3\2\2\2\66\67\7C\2\2\678\7P\2\2"+
		"89\7F\2\29\f\3\2\2\2:;\7Q\2\2;<\7T\2\2<\16\3\2\2\2=>\7P\2\2>?\7Q\2\2?"+
		"@\7V\2\2@\20\3\2\2\2AB\7?\2\2B\22\3\2\2\2CD\7#\2\2DE\7?\2\2E\24\3\2\2"+
		"\2FG\7@\2\2GH\7?\2\2H\26\3\2\2\2IJ\7>\2\2JK\7?\2\2K\30\3\2\2\2LM\7@\2"+
		"\2M\32\3\2\2\2NO\7>\2\2O\34\3\2\2\2PR\5#\22\2QP\3\2\2\2RS\3\2\2\2SQ\3"+
		"\2\2\2ST\3\2\2\2Tg\3\2\2\2UW\5#\22\2VU\3\2\2\2WX\3\2\2\2XV\3\2\2\2XY\3"+
		"\2\2\2YZ\3\2\2\2Z\\\7\60\2\2[]\5#\22\2\\[\3\2\2\2]^\3\2\2\2^\\\3\2\2\2"+
		"^_\3\2\2\2_g\3\2\2\2`b\7\60\2\2ac\5#\22\2ba\3\2\2\2cd\3\2\2\2db\3\2\2"+
		"\2de\3\2\2\2eg\3\2\2\2fQ\3\2\2\2fV\3\2\2\2f`\3\2\2\2g\36\3\2\2\2hm\7$"+
		"\2\2il\5%\23\2jl\13\2\2\2ki\3\2\2\2kj\3\2\2\2lo\3\2\2\2mn\3\2\2\2mk\3"+
		"\2\2\2np\3\2\2\2om\3\2\2\2pq\7$\2\2q \3\2\2\2rt\n\2\2\2sr\3\2\2\2tu\3"+
		"\2\2\2us\3\2\2\2uv\3\2\2\2v\"\3\2\2\2wx\t\3\2\2x$\3\2\2\2yz\7^\2\2z~\7"+
		"$\2\2{|\7^\2\2|~\7^\2\2}y\3\2\2\2}{\3\2\2\2~&\3\2\2\2\177\u0081\t\4\2"+
		"\2\u0080\177\3\2\2\2\u0081\u0082\3\2\2\2\u0082\u0080\3\2\2\2\u0082\u0083"+
		"\3\2\2\2\u0083\u0084\3\2\2\2\u0084\u0085\b\24\2\2\u0085(\3\2\2\2\r\2S"+
		"X^dfkmu}\u0082\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}