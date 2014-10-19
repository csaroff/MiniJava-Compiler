// Generated from Minijava.g4 by ANTLR 4.4
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MinijavaLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.4", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__34=1, T__33=2, T__32=3, T__31=4, T__30=5, T__29=6, T__28=7, T__27=8, 
		T__26=9, T__25=10, T__24=11, T__23=12, T__22=13, T__21=14, T__20=15, T__19=16, 
		T__18=17, T__17=18, T__16=19, T__15=20, T__14=21, T__13=22, T__12=23, 
		T__11=24, T__10=25, T__9=26, T__8=27, T__7=28, T__6=29, T__5=30, T__4=31, 
		T__3=32, T__2=33, T__1=34, T__0=35, Identifier=36, IntegerLiteral=37;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"'\\u0000'", "'\\u0001'", "'\\u0002'", "'\\u0003'", "'\\u0004'", "'\\u0005'", 
		"'\\u0006'", "'\\u0007'", "'\b'", "'\t'", "'\n'", "'\\u000B'", "'\f'", 
		"'\r'", "'\\u000E'", "'\\u000F'", "'\\u0010'", "'\\u0011'", "'\\u0012'", 
		"'\\u0013'", "'\\u0014'", "'\\u0015'", "'\\u0016'", "'\\u0017'", "'\\u0018'", 
		"'\\u0019'", "'\\u001A'", "'\\u001B'", "'\\u001C'", "'\\u001D'", "'\\u001E'", 
		"'\\u001F'", "' '", "'!'", "'\"'", "'#'", "'$'", "'%'"
	};
	public static final String[] ruleNames = {
		"T__34", "T__33", "T__32", "T__31", "T__30", "T__29", "T__28", "T__27", 
		"T__26", "T__25", "T__24", "T__23", "T__22", "T__21", "T__20", "T__19", 
		"T__18", "T__17", "T__16", "T__15", "T__14", "T__13", "T__12", "T__11", 
		"T__10", "T__9", "T__8", "T__7", "T__6", "T__5", "T__4", "T__3", "T__2", 
		"T__1", "T__0", "Identifier", "JavaLetter", "JavaLetterOrDigit", "IntegerLiteral", 
		"DecimalIntegerLiteral", "IntegertypeSuffix", "DecimalNumeral", "Digits", 
		"Digit", "NonZeroDigit", "DigitsAndUnderscores", "DigitOrUnderscore", 
		"Underscores"
	};


	public MinijavaLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Minijava.g4"; }

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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\'\u013a\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\3\2\3\2\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3"+
		"\t\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\16\3"+
		"\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\21\3\21\3"+
		"\21\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3"+
		"\23\3\23\3\23\3\23\3\23\3\24\3\24\3\25\3\25\3\25\3\25\3\25\3\26\3\26\3"+
		"\26\3\26\3\26\3\26\3\26\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\30\3\30\3"+
		"\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3"+
		"\30\3\30\3\30\3\31\3\31\3\32\3\32\3\33\3\33\3\34\3\34\3\35\3\35\3\35\3"+
		"\35\3\35\3\35\3\35\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\37\3\37\3 \3 "+
		"\3 \3!\3!\3!\3!\3!\3\"\3\"\3\"\3\"\3#\3#\3#\3#\3#\3#\3$\3$\3%\3%\7%\u0103"+
		"\n%\f%\16%\u0106\13%\3&\3&\3\'\3\'\3(\3(\3)\3)\5)\u0110\n)\3*\3*\3+\3"+
		"+\3+\5+\u0117\n+\3+\3+\3+\5+\u011c\n+\5+\u011e\n+\3,\3,\5,\u0122\n,\3"+
		",\5,\u0125\n,\3-\3-\5-\u0129\n-\3.\3.\3/\6/\u012e\n/\r/\16/\u012f\3\60"+
		"\3\60\5\60\u0134\n\60\3\61\6\61\u0137\n\61\r\61\16\61\u0138\2\2\62\3\3"+
		"\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21"+
		"!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67\359\36;\37= ?!"+
		"A\"C#E$G%I&K\2M\2O\'Q\2S\2U\2W\2Y\2[\2]\2_\2a\2\3\2\6\6\2&&C\\aac|\7\2"+
		"&&\62;C\\aac|\4\2NNnn\3\2\63;\u0139\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2"+
		"\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23"+
		"\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2"+
		"\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2"+
		"\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3"+
		"\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2"+
		"\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2O\3\2\2\2\3c\3\2\2\2"+
		"\5e\3\2\2\2\7l\3\2\2\2\tn\3\2\2\2\13t\3\2\2\2\rv\3\2\2\2\17x\3\2\2\2\21"+
		"z\3\2\2\2\23|\3\2\2\2\25\177\3\2\2\2\27\u0083\3\2\2\2\31\u0085\3\2\2\2"+
		"\33\u008a\3\2\2\2\35\u0090\3\2\2\2\37\u0095\3\2\2\2!\u0097\3\2\2\2#\u009f"+
		"\3\2\2\2%\u00a4\3\2\2\2\'\u00ac\3\2\2\2)\u00ae\3\2\2\2+\u00b3\3\2\2\2"+
		"-\u00ba\3\2\2\2/\u00c1\3\2\2\2\61\u00d4\3\2\2\2\63\u00d6\3\2\2\2\65\u00d8"+
		"\3\2\2\2\67\u00da\3\2\2\29\u00dc\3\2\2\2;\u00e3\3\2\2\2=\u00ea\3\2\2\2"+
		"?\u00ec\3\2\2\2A\u00ef\3\2\2\2C\u00f4\3\2\2\2E\u00f8\3\2\2\2G\u00fe\3"+
		"\2\2\2I\u0100\3\2\2\2K\u0107\3\2\2\2M\u0109\3\2\2\2O\u010b\3\2\2\2Q\u010d"+
		"\3\2\2\2S\u0111\3\2\2\2U\u011d\3\2\2\2W\u011f\3\2\2\2Y\u0128\3\2\2\2["+
		"\u012a\3\2\2\2]\u012d\3\2\2\2_\u0133\3\2\2\2a\u0136\3\2\2\2cd\7_\2\2d"+
		"\4\3\2\2\2ef\7r\2\2fg\7w\2\2gh\7d\2\2hi\7n\2\2ij\7k\2\2jk\7e\2\2k\6\3"+
		"\2\2\2lm\7.\2\2m\b\3\2\2\2no\7y\2\2op\7j\2\2pq\7k\2\2qr\7n\2\2rs\7g\2"+
		"\2s\n\3\2\2\2tu\7]\2\2u\f\3\2\2\2vw\7/\2\2w\16\3\2\2\2xy\7,\2\2y\20\3"+
		"\2\2\2z{\7*\2\2{\22\3\2\2\2|}\7k\2\2}~\7h\2\2~\24\3\2\2\2\177\u0080\7"+
		"k\2\2\u0080\u0081\7p\2\2\u0081\u0082\7v\2\2\u0082\26\3\2\2\2\u0083\u0084"+
		"\7>\2\2\u0084\30\3\2\2\2\u0085\u0086\7o\2\2\u0086\u0087\7c\2\2\u0087\u0088"+
		"\7k\2\2\u0088\u0089\7p\2\2\u0089\32\3\2\2\2\u008a\u008b\7h\2\2\u008b\u008c"+
		"\7c\2\2\u008c\u008d\7n\2\2\u008d\u008e\7u\2\2\u008e\u008f\7g\2\2\u008f"+
		"\34\3\2\2\2\u0090\u0091\7x\2\2\u0091\u0092\7q\2\2\u0092\u0093\7k\2\2\u0093"+
		"\u0094\7f\2\2\u0094\36\3\2\2\2\u0095\u0096\7}\2\2\u0096 \3\2\2\2\u0097"+
		"\u0098\7g\2\2\u0098\u0099\7z\2\2\u0099\u009a\7v\2\2\u009a\u009b\7g\2\2"+
		"\u009b\u009c\7p\2\2\u009c\u009d\7f\2\2\u009d\u009e\7u\2\2\u009e\"\3\2"+
		"\2\2\u009f\u00a0\7g\2\2\u00a0\u00a1\7n\2\2\u00a1\u00a2\7u\2\2\u00a2\u00a3"+
		"\7g\2\2\u00a3$\3\2\2\2\u00a4\u00a5\7d\2\2\u00a5\u00a6\7q\2\2\u00a6\u00a7"+
		"\7q\2\2\u00a7\u00a8\7n\2\2\u00a8\u00a9\7g\2\2\u00a9\u00aa\7c\2\2\u00aa"+
		"\u00ab\7p\2\2\u00ab&\3\2\2\2\u00ac\u00ad\7\177\2\2\u00ad(\3\2\2\2\u00ae"+
		"\u00af\7v\2\2\u00af\u00b0\7t\2\2\u00b0\u00b1\7w\2\2\u00b1\u00b2\7g\2\2"+
		"\u00b2*\3\2\2\2\u00b3\u00b4\7u\2\2\u00b4\u00b5\7v\2\2\u00b5\u00b6\7c\2"+
		"\2\u00b6\u00b7\7v\2\2\u00b7\u00b8\7k\2\2\u00b8\u00b9\7e\2\2\u00b9,\3\2"+
		"\2\2\u00ba\u00bb\7n\2\2\u00bb\u00bc\7g\2\2\u00bc\u00bd\7p\2\2\u00bd\u00be"+
		"\7i\2\2\u00be\u00bf\7v\2\2\u00bf\u00c0\7j\2\2\u00c0.\3\2\2\2\u00c1\u00c2"+
		"\7U\2\2\u00c2\u00c3\7{\2\2\u00c3\u00c4\7u\2\2\u00c4\u00c5\7v\2\2\u00c5"+
		"\u00c6\7g\2\2\u00c6\u00c7\7o\2\2\u00c7\u00c8\7\60\2\2\u00c8\u00c9\7q\2"+
		"\2\u00c9\u00ca\7w\2\2\u00ca\u00cb\7v\2\2\u00cb\u00cc\7\60\2\2\u00cc\u00cd"+
		"\7r\2\2\u00cd\u00ce\7t\2\2\u00ce\u00cf\7k\2\2\u00cf\u00d0\7p\2\2\u00d0"+
		"\u00d1\7v\2\2\u00d1\u00d2\7n\2\2\u00d2\u00d3\7p\2\2\u00d3\60\3\2\2\2\u00d4"+
		"\u00d5\7+\2\2\u00d5\62\3\2\2\2\u00d6\u00d7\7\60\2\2\u00d7\64\3\2\2\2\u00d8"+
		"\u00d9\7-\2\2\u00d9\66\3\2\2\2\u00da\u00db\7?\2\2\u00db8\3\2\2\2\u00dc"+
		"\u00dd\7t\2\2\u00dd\u00de\7g\2\2\u00de\u00df\7v\2\2\u00df\u00e0\7w\2\2"+
		"\u00e0\u00e1\7t\2\2\u00e1\u00e2\7p\2\2\u00e2:\3\2\2\2\u00e3\u00e4\7U\2"+
		"\2\u00e4\u00e5\7v\2\2\u00e5\u00e6\7t\2\2\u00e6\u00e7\7k\2\2\u00e7\u00e8"+
		"\7p\2\2\u00e8\u00e9\7i\2\2\u00e9<\3\2\2\2\u00ea\u00eb\7=\2\2\u00eb>\3"+
		"\2\2\2\u00ec\u00ed\7(\2\2\u00ed\u00ee\7(\2\2\u00ee@\3\2\2\2\u00ef\u00f0"+
		"\7v\2\2\u00f0\u00f1\7j\2\2\u00f1\u00f2\7k\2\2\u00f2\u00f3\7u\2\2\u00f3"+
		"B\3\2\2\2\u00f4\u00f5\7p\2\2\u00f5\u00f6\7g\2\2\u00f6\u00f7\7y\2\2\u00f7"+
		"D\3\2\2\2\u00f8\u00f9\7e\2\2\u00f9\u00fa\7n\2\2\u00fa\u00fb\7c\2\2\u00fb"+
		"\u00fc\7u\2\2\u00fc\u00fd\7u\2\2\u00fdF\3\2\2\2\u00fe\u00ff\7#\2\2\u00ff"+
		"H\3\2\2\2\u0100\u0104\5K&\2\u0101\u0103\5M\'\2\u0102\u0101\3\2\2\2\u0103"+
		"\u0106\3\2\2\2\u0104\u0102\3\2\2\2\u0104\u0105\3\2\2\2\u0105J\3\2\2\2"+
		"\u0106\u0104\3\2\2\2\u0107\u0108\t\2\2\2\u0108L\3\2\2\2\u0109\u010a\t"+
		"\3\2\2\u010aN\3\2\2\2\u010b\u010c\5Q)\2\u010cP\3\2\2\2\u010d\u010f\5U"+
		"+\2\u010e\u0110\5S*\2\u010f\u010e\3\2\2\2\u010f\u0110\3\2\2\2\u0110R\3"+
		"\2\2\2\u0111\u0112\t\4\2\2\u0112T\3\2\2\2\u0113\u011e\7\62\2\2\u0114\u011b"+
		"\5[.\2\u0115\u0117\5W,\2\u0116\u0115\3\2\2\2\u0116\u0117\3\2\2\2\u0117"+
		"\u011c\3\2\2\2\u0118\u0119\5a\61\2\u0119\u011a\5W,\2\u011a\u011c\3\2\2"+
		"\2\u011b\u0116\3\2\2\2\u011b\u0118\3\2\2\2\u011c\u011e\3\2\2\2\u011d\u0113"+
		"\3\2\2\2\u011d\u0114\3\2\2\2\u011eV\3\2\2\2\u011f\u0124\5Y-\2\u0120\u0122"+
		"\5]/\2\u0121\u0120\3\2\2\2\u0121\u0122\3\2\2\2\u0122\u0123\3\2\2\2\u0123"+
		"\u0125\5Y-\2\u0124\u0121\3\2\2\2\u0124\u0125\3\2\2\2\u0125X\3\2\2\2\u0126"+
		"\u0129\7\62\2\2\u0127\u0129\5[.\2\u0128\u0126\3\2\2\2\u0128\u0127\3\2"+
		"\2\2\u0129Z\3\2\2\2\u012a\u012b\t\5\2\2\u012b\\\3\2\2\2\u012c\u012e\5"+
		"_\60\2\u012d\u012c\3\2\2\2\u012e\u012f\3\2\2\2\u012f\u012d\3\2\2\2\u012f"+
		"\u0130\3\2\2\2\u0130^\3\2\2\2\u0131\u0134\5Y-\2\u0132\u0134\7a\2\2\u0133"+
		"\u0131\3\2\2\2\u0133\u0132\3\2\2\2\u0134`\3\2\2\2\u0135\u0137\7a\2\2\u0136"+
		"\u0135\3\2\2\2\u0137\u0138\3\2\2\2\u0138\u0136\3\2\2\2\u0138\u0139\3\2"+
		"\2\2\u0139b\3\2\2\2\16\2\u0104\u010f\u0116\u011b\u011d\u0121\u0124\u0128"+
		"\u012f\u0133\u0138\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}