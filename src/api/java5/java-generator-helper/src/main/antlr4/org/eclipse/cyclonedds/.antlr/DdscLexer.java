// Generated from /home/ndrianja/IST/cyclonedds/src/api/java5/java-generator-helper/src/main/antlr4/org/eclipse/cyclonedds/Ddsc.g4 by ANTLR 4.7.1
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class DdscLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
		"T__9", "T__10", "T__11", "T__12", "T__13"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'#include '", "'('", "')'", "'static'", "'*'", "':'", "'__extension__'", 
		"'__builtin_va_arg'", "','", "'__builtin_offsetof'", "'['", "']'", "'{'", 
		"'}'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
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


	public DdscLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Ddsc.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\20t\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3"+
		"\2\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\7\3\7"+
		"\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3"+
		"\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\13\3"+
		"\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3"+
		"\13\3\13\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16\3\17\3\17\2\2\20\3\3\5\4"+
		"\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\3\2\2\2"+
		"s\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2"+
		"\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2"+
		"\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\3\37\3\2\2\2\5)\3\2\2\2\7+\3\2"+
		"\2\2\t-\3\2\2\2\13\64\3\2\2\2\r\66\3\2\2\2\178\3\2\2\2\21F\3\2\2\2\23"+
		"W\3\2\2\2\25Y\3\2\2\2\27l\3\2\2\2\31n\3\2\2\2\33p\3\2\2\2\35r\3\2\2\2"+
		"\37 \7%\2\2 !\7k\2\2!\"\7p\2\2\"#\7e\2\2#$\7n\2\2$%\7w\2\2%&\7f\2\2&\'"+
		"\7g\2\2\'(\7\"\2\2(\4\3\2\2\2)*\7*\2\2*\6\3\2\2\2+,\7+\2\2,\b\3\2\2\2"+
		"-.\7u\2\2./\7v\2\2/\60\7c\2\2\60\61\7v\2\2\61\62\7k\2\2\62\63\7e\2\2\63"+
		"\n\3\2\2\2\64\65\7,\2\2\65\f\3\2\2\2\66\67\7<\2\2\67\16\3\2\2\289\7a\2"+
		"\29:\7a\2\2:;\7g\2\2;<\7z\2\2<=\7v\2\2=>\7g\2\2>?\7p\2\2?@\7u\2\2@A\7"+
		"k\2\2AB\7q\2\2BC\7p\2\2CD\7a\2\2DE\7a\2\2E\20\3\2\2\2FG\7a\2\2GH\7a\2"+
		"\2HI\7d\2\2IJ\7w\2\2JK\7k\2\2KL\7n\2\2LM\7v\2\2MN\7k\2\2NO\7p\2\2OP\7"+
		"a\2\2PQ\7x\2\2QR\7c\2\2RS\7a\2\2ST\7c\2\2TU\7t\2\2UV\7i\2\2V\22\3\2\2"+
		"\2WX\7.\2\2X\24\3\2\2\2YZ\7a\2\2Z[\7a\2\2[\\\7d\2\2\\]\7w\2\2]^\7k\2\2"+
		"^_\7n\2\2_`\7v\2\2`a\7k\2\2ab\7p\2\2bc\7a\2\2cd\7q\2\2de\7h\2\2ef\7h\2"+
		"\2fg\7u\2\2gh\7g\2\2hi\7v\2\2ij\7q\2\2jk\7h\2\2k\26\3\2\2\2lm\7]\2\2m"+
		"\30\3\2\2\2no\7_\2\2o\32\3\2\2\2pq\7}\2\2q\34\3\2\2\2rs\7\177\2\2s\36"+
		"\3\2\2\2\3\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}