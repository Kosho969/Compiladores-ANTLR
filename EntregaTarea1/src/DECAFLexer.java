// Generated from DECAF.g4 by ANTLR 4.5.3
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class DECAFLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.5.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		CLASS=1, STRUCT=2, TRUE=3, FALSE=4, INT=5, IF=6, ELSE=7, WHILE=8, RETURN=9, 
		CHAR=10, BOOLEAN=11, VOID=12, SCAN=13, PRINT=14, PROGRAM=15, WS=16, CHR=17, 
		ID=18, NUM=19, STRING=20, COMMENT=21, COMA=22, AND=23, OR=24, LBRACE=25, 
		RBRACE=26, DOTCOMMA=27, RCORCH=28, LCORCH=29, LPARENT=30, RPARENT=31, 
		EQ=32, DOT=33, PLUS=34, MINUS=35, EXC=36, AST=37, SLSH=38, PRCNT=39, MTHAN=40, 
		LTHAN=41, EQMTHAN=42, EQLTHAN=43, EQEQ=44, NOTEQ=45;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"CLASS", "STRUCT", "TRUE", "FALSE", "INT", "IF", "ELSE", "WHILE", "RETURN", 
		"CHAR", "BOOLEAN", "VOID", "SCAN", "PRINT", "PROGRAM", "LETTER", "WS", 
		"DIGIT", "CHR", "ID", "NUM", "STRING", "COMMENT", "COMA", "AND", "OR", 
		"LBRACE", "RBRACE", "DOTCOMMA", "RCORCH", "LCORCH", "LPARENT", "RPARENT", 
		"EQ", "DOT", "PLUS", "MINUS", "EXC", "AST", "SLSH", "PRCNT", "MTHAN", 
		"LTHAN", "EQMTHAN", "EQLTHAN", "EQEQ", "NOTEQ"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'class'", "'struct'", "'true'", "'false'", "'int'", "'if'", "'else'", 
		"'while'", "'return'", "'char'", "'boolean'", "'void'", "'scan'", "'print'", 
		"'Program'", null, null, null, null, null, null, "','", "'&&'", "'||'", 
		"'{'", "'}'", "';'", "']'", "'['", "'('", "')'", "'='", "'.'", "'+'", 
		"'-'", "'!'", "'*'", "'/'", "'%'", "'>'", "'<'", "'>='", "'<='", "'=='", 
		"'!='"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "CLASS", "STRUCT", "TRUE", "FALSE", "INT", "IF", "ELSE", "WHILE", 
		"RETURN", "CHAR", "BOOLEAN", "VOID", "SCAN", "PRINT", "PROGRAM", "WS", 
		"CHR", "ID", "NUM", "STRING", "COMMENT", "COMA", "AND", "OR", "LBRACE", 
		"RBRACE", "DOTCOMMA", "RCORCH", "LCORCH", "LPARENT", "RPARENT", "EQ", 
		"DOT", "PLUS", "MINUS", "EXC", "AST", "SLSH", "PRCNT", "MTHAN", "LTHAN", 
		"EQMTHAN", "EQLTHAN", "EQEQ", "NOTEQ"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public DECAFLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "DECAF.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
		switch (ruleIndex) {
		case 22:
			COMMENT_action((RuleContext)_localctx, actionIndex);
			break;
		}
	}
	private void COMMENT_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0:
			skip();
			break;
		}
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2/\u0132\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\3\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6"+
		"\3\6\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3"+
		"\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\f\3"+
		"\f\3\f\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17"+
		"\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\22\6\22"+
		"\u00bb\n\22\r\22\16\22\u00bc\3\22\3\22\3\23\3\23\3\24\3\24\3\24\3\24\3"+
		"\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\5\24\u00cf\n\24\3\24\3\24\3\25"+
		"\3\25\3\25\7\25\u00d6\n\25\f\25\16\25\u00d9\13\25\3\26\6\26\u00dc\n\26"+
		"\r\26\16\26\u00dd\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3"+
		"\27\3\27\6\27\u00ec\n\27\r\27\16\27\u00ed\3\27\3\27\3\30\3\30\3\30\3\30"+
		"\7\30\u00f6\n\30\f\30\16\30\u00f9\13\30\3\30\3\30\3\31\3\31\3\32\3\32"+
		"\3\32\3\33\3\33\3\33\3\34\3\34\3\35\3\35\3\36\3\36\3\37\3\37\3 \3 \3!"+
		"\3!\3\"\3\"\3#\3#\3$\3$\3%\3%\3&\3&\3\'\3\'\3(\3(\3)\3)\3*\3*\3+\3+\3"+
		",\3,\3-\3-\3-\3.\3.\3.\3/\3/\3/\3\60\3\60\3\60\2\2\61\3\3\5\4\7\5\t\6"+
		"\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\2#\22%\2"+
		"\'\23)\24+\25-\26/\27\61\30\63\31\65\32\67\339\34;\35=\36?\37A C!E\"G"+
		"#I$K%M&O\'Q(S)U*W+Y,[-]._/\3\2\b\4\2C\\c|\5\2\13\f\17\17\"\"\5\2$(,,`"+
		"`\n\2$$))..\60\60<>@Bbb\u0080\u0080\f\2$$))..\60\60<>@B^^bbuu\u0080\u0080"+
		"\4\2\f\f\17\17\u0149\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2"+
		"\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3"+
		"\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2"+
		"\2\2#\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2"+
		"\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2"+
		"\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2"+
		"\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U"+
		"\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2\2\2\3a\3\2"+
		"\2\2\5g\3\2\2\2\7n\3\2\2\2\ts\3\2\2\2\13y\3\2\2\2\r}\3\2\2\2\17\u0080"+
		"\3\2\2\2\21\u0085\3\2\2\2\23\u008b\3\2\2\2\25\u0092\3\2\2\2\27\u0097\3"+
		"\2\2\2\31\u009f\3\2\2\2\33\u00a4\3\2\2\2\35\u00a9\3\2\2\2\37\u00af\3\2"+
		"\2\2!\u00b7\3\2\2\2#\u00ba\3\2\2\2%\u00c0\3\2\2\2\'\u00c2\3\2\2\2)\u00d2"+
		"\3\2\2\2+\u00db\3\2\2\2-\u00df\3\2\2\2/\u00f1\3\2\2\2\61\u00fc\3\2\2\2"+
		"\63\u00fe\3\2\2\2\65\u0101\3\2\2\2\67\u0104\3\2\2\29\u0106\3\2\2\2;\u0108"+
		"\3\2\2\2=\u010a\3\2\2\2?\u010c\3\2\2\2A\u010e\3\2\2\2C\u0110\3\2\2\2E"+
		"\u0112\3\2\2\2G\u0114\3\2\2\2I\u0116\3\2\2\2K\u0118\3\2\2\2M\u011a\3\2"+
		"\2\2O\u011c\3\2\2\2Q\u011e\3\2\2\2S\u0120\3\2\2\2U\u0122\3\2\2\2W\u0124"+
		"\3\2\2\2Y\u0126\3\2\2\2[\u0129\3\2\2\2]\u012c\3\2\2\2_\u012f\3\2\2\2a"+
		"b\7e\2\2bc\7n\2\2cd\7c\2\2de\7u\2\2ef\7u\2\2f\4\3\2\2\2gh\7u\2\2hi\7v"+
		"\2\2ij\7t\2\2jk\7w\2\2kl\7e\2\2lm\7v\2\2m\6\3\2\2\2no\7v\2\2op\7t\2\2"+
		"pq\7w\2\2qr\7g\2\2r\b\3\2\2\2st\7h\2\2tu\7c\2\2uv\7n\2\2vw\7u\2\2wx\7"+
		"g\2\2x\n\3\2\2\2yz\7k\2\2z{\7p\2\2{|\7v\2\2|\f\3\2\2\2}~\7k\2\2~\177\7"+
		"h\2\2\177\16\3\2\2\2\u0080\u0081\7g\2\2\u0081\u0082\7n\2\2\u0082\u0083"+
		"\7u\2\2\u0083\u0084\7g\2\2\u0084\20\3\2\2\2\u0085\u0086\7y\2\2\u0086\u0087"+
		"\7j\2\2\u0087\u0088\7k\2\2\u0088\u0089\7n\2\2\u0089\u008a\7g\2\2\u008a"+
		"\22\3\2\2\2\u008b\u008c\7t\2\2\u008c\u008d\7g\2\2\u008d\u008e\7v\2\2\u008e"+
		"\u008f\7w\2\2\u008f\u0090\7t\2\2\u0090\u0091\7p\2\2\u0091\24\3\2\2\2\u0092"+
		"\u0093\7e\2\2\u0093\u0094\7j\2\2\u0094\u0095\7c\2\2\u0095\u0096\7t\2\2"+
		"\u0096\26\3\2\2\2\u0097\u0098\7d\2\2\u0098\u0099\7q\2\2\u0099\u009a\7"+
		"q\2\2\u009a\u009b\7n\2\2\u009b\u009c\7g\2\2\u009c\u009d\7c\2\2\u009d\u009e"+
		"\7p\2\2\u009e\30\3\2\2\2\u009f\u00a0\7x\2\2\u00a0\u00a1\7q\2\2\u00a1\u00a2"+
		"\7k\2\2\u00a2\u00a3\7f\2\2\u00a3\32\3\2\2\2\u00a4\u00a5\7u\2\2\u00a5\u00a6"+
		"\7e\2\2\u00a6\u00a7\7c\2\2\u00a7\u00a8\7p\2\2\u00a8\34\3\2\2\2\u00a9\u00aa"+
		"\7r\2\2\u00aa\u00ab\7t\2\2\u00ab\u00ac\7k\2\2\u00ac\u00ad\7p\2\2\u00ad"+
		"\u00ae\7v\2\2\u00ae\36\3\2\2\2\u00af\u00b0\7R\2\2\u00b0\u00b1\7t\2\2\u00b1"+
		"\u00b2\7q\2\2\u00b2\u00b3\7i\2\2\u00b3\u00b4\7t\2\2\u00b4\u00b5\7c\2\2"+
		"\u00b5\u00b6\7o\2\2\u00b6 \3\2\2\2\u00b7\u00b8\t\2\2\2\u00b8\"\3\2\2\2"+
		"\u00b9\u00bb\t\3\2\2\u00ba\u00b9\3\2\2\2\u00bb\u00bc\3\2\2\2\u00bc\u00ba"+
		"\3\2\2\2\u00bc\u00bd\3\2\2\2\u00bd\u00be\3\2\2\2\u00be\u00bf\b\22\2\2"+
		"\u00bf$\3\2\2\2\u00c0\u00c1\4\62;\2\u00c1&\3\2\2\2\u00c2\u00ce\7)\2\2"+
		"\u00c3\u00cf\5!\21\2\u00c4\u00cf\5%\23\2\u00c5\u00cf\7\"\2\2\u00c6\u00cf"+
		"\5M\'\2\u00c7\u00cf\t\4\2\2\u00c8\u00cf\5A!\2\u00c9\u00cf\5C\"\2\u00ca"+
		"\u00cf\5I%\2\u00cb\u00cf\7a\2\2\u00cc\u00cf\5K&\2\u00cd\u00cf\t\5\2\2"+
		"\u00ce\u00c3\3\2\2\2\u00ce\u00c4\3\2\2\2\u00ce\u00c5\3\2\2\2\u00ce\u00c6"+
		"\3\2\2\2\u00ce\u00c7\3\2\2\2\u00ce\u00c8\3\2\2\2\u00ce\u00c9\3\2\2\2\u00ce"+
		"\u00ca\3\2\2\2\u00ce\u00cb\3\2\2\2\u00ce\u00cc\3\2\2\2\u00ce\u00cd\3\2"+
		"\2\2\u00cf\u00d0\3\2\2\2\u00d0\u00d1\7)\2\2\u00d1(\3\2\2\2\u00d2\u00d7"+
		"\5!\21\2\u00d3\u00d6\5%\23\2\u00d4\u00d6\5!\21\2\u00d5\u00d3\3\2\2\2\u00d5"+
		"\u00d4\3\2\2\2\u00d6\u00d9\3\2\2\2\u00d7\u00d5\3\2\2\2\u00d7\u00d8\3\2"+
		"\2\2\u00d8*\3\2\2\2\u00d9\u00d7\3\2\2\2\u00da\u00dc\5%\23\2\u00db\u00da"+
		"\3\2\2\2\u00dc\u00dd\3\2\2\2\u00dd\u00db\3\2\2\2\u00dd\u00de\3\2\2\2\u00de"+
		",\3\2\2\2\u00df\u00eb\7$\2\2\u00e0\u00ec\5!\21\2\u00e1\u00ec\5%\23\2\u00e2"+
		"\u00ec\7\"\2\2\u00e3\u00ec\5M\'\2\u00e4\u00ec\t\4\2\2\u00e5\u00ec\5A!"+
		"\2\u00e6\u00ec\5C\"\2\u00e7\u00ec\5I%\2\u00e8\u00ec\7a\2\2\u00e9\u00ec"+
		"\5K&\2\u00ea\u00ec\t\6\2\2\u00eb\u00e0\3\2\2\2\u00eb\u00e1\3\2\2\2\u00eb"+
		"\u00e2\3\2\2\2\u00eb\u00e3\3\2\2\2\u00eb\u00e4\3\2\2\2\u00eb\u00e5\3\2"+
		"\2\2\u00eb\u00e6\3\2\2\2\u00eb\u00e7\3\2\2\2\u00eb\u00e8\3\2\2\2\u00eb"+
		"\u00e9\3\2\2\2\u00eb\u00ea\3\2\2\2\u00ec\u00ed\3\2\2\2\u00ed\u00eb\3\2"+
		"\2\2\u00ed\u00ee\3\2\2\2\u00ee\u00ef\3\2\2\2\u00ef\u00f0\7$\2\2\u00f0"+
		".\3\2\2\2\u00f1\u00f2\7\61\2\2\u00f2\u00f3\7\61\2\2\u00f3\u00f7\3\2\2"+
		"\2\u00f4\u00f6\n\7\2\2\u00f5\u00f4\3\2\2\2\u00f6\u00f9\3\2\2\2\u00f7\u00f5"+
		"\3\2\2\2\u00f7\u00f8\3\2\2\2\u00f8\u00fa\3\2\2\2\u00f9\u00f7\3\2\2\2\u00fa"+
		"\u00fb\b\30\3\2\u00fb\60\3\2\2\2\u00fc\u00fd\7.\2\2\u00fd\62\3\2\2\2\u00fe"+
		"\u00ff\7(\2\2\u00ff\u0100\7(\2\2\u0100\64\3\2\2\2\u0101\u0102\7~\2\2\u0102"+
		"\u0103\7~\2\2\u0103\66\3\2\2\2\u0104\u0105\7}\2\2\u01058\3\2\2\2\u0106"+
		"\u0107\7\177\2\2\u0107:\3\2\2\2\u0108\u0109\7=\2\2\u0109<\3\2\2\2\u010a"+
		"\u010b\7_\2\2\u010b>\3\2\2\2\u010c\u010d\7]\2\2\u010d@\3\2\2\2\u010e\u010f"+
		"\7*\2\2\u010fB\3\2\2\2\u0110\u0111\7+\2\2\u0111D\3\2\2\2\u0112\u0113\7"+
		"?\2\2\u0113F\3\2\2\2\u0114\u0115\7\60\2\2\u0115H\3\2\2\2\u0116\u0117\7"+
		"-\2\2\u0117J\3\2\2\2\u0118\u0119\7/\2\2\u0119L\3\2\2\2\u011a\u011b\7#"+
		"\2\2\u011bN\3\2\2\2\u011c\u011d\7,\2\2\u011dP\3\2\2\2\u011e\u011f\7\61"+
		"\2\2\u011fR\3\2\2\2\u0120\u0121\7\'\2\2\u0121T\3\2\2\2\u0122\u0123\7@"+
		"\2\2\u0123V\3\2\2\2\u0124\u0125\7>\2\2\u0125X\3\2\2\2\u0126\u0127\7@\2"+
		"\2\u0127\u0128\7?\2\2\u0128Z\3\2\2\2\u0129\u012a\7>\2\2\u012a\u012b\7"+
		"?\2\2\u012b\\\3\2\2\2\u012c\u012d\7?\2\2\u012d\u012e\7?\2\2\u012e^\3\2"+
		"\2\2\u012f\u0130\7#\2\2\u0130\u0131\7?\2\2\u0131`\3\2\2\2\13\2\u00bc\u00ce"+
		"\u00d5\u00d7\u00dd\u00eb\u00ed\u00f7\4\2\3\2\3\30\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}