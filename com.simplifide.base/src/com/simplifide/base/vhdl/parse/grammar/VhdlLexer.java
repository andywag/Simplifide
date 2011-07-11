// $ANTLR 2.7.7 (2006-11-01): "vhdl.g" -> "VhdlLexer.java"$
package com.simplifide.base.vhdl.parse.grammar;


import com.simplifide.base.sourcefile.antlr.tok.*;



import java.io.InputStream;
import antlr.TokenStreamException;
import antlr.TokenStreamIOException;
import antlr.TokenStreamRecognitionException;
import antlr.CharStreamException;
import antlr.CharStreamIOException;
import antlr.ANTLRException;
import java.io.Reader;
import java.util.Hashtable;
import com.simplifide.base.sourcefile.antlr.grammar.BaseLexer;
import antlr.InputBuffer;
import antlr.ByteBuffer;
import antlr.CharBuffer;
import antlr.Token;
import antlr.CommonToken;
import antlr.RecognitionException;
import antlr.NoViableAltForCharException;
import antlr.MismatchedCharException;
import antlr.TokenStream;
import antlr.ANTLRHashString;
import antlr.LexerSharedInputState;
import antlr.collections.impl.BitSet;
import antlr.SemanticException;

public class VhdlLexer extends BaseLexer implements VhdlTokenTypes, TokenStream
 {
public VhdlLexer(InputStream in) {
	this(new ByteBuffer(in));
}
public VhdlLexer(Reader in) {
	this(new CharBuffer(in));
}
public VhdlLexer(InputBuffer ib) {
	this(new LexerSharedInputState(ib));
}
public VhdlLexer(LexerSharedInputState state) {
	super(state);
	caseSensitiveLiterals = false;
	setCaseSensitive(false);
	literals = new Hashtable();
	literals.put(new ANTLRHashString("until", this), new Integer(213));
	literals.put(new ANTLRHashString("quantity", this), new Integer(207));
	literals.put(new ANTLRHashString("xor", this), new Integer(253));
	literals.put(new ANTLRHashString("procedural", this), new Integer(310));
	literals.put(new ANTLRHashString("entity", this), new Integer(225));
	literals.put(new ANTLRHashString("process", this), new Integer(192));
	literals.put(new ANTLRHashString("variable", this), new Integer(232));
	literals.put(new ANTLRHashString("use", this), new Integer(204));
	literals.put(new ANTLRHashString("ror", this), new Integer(260));
	literals.put(new ANTLRHashString("with", this), new Integer(296));
	literals.put(new ANTLRHashString("pure", this), new Integer(305));
	literals.put(new ANTLRHashString("wait", this), new Integer(299));
	literals.put(new ANTLRHashString("nand", this), new Integer(251));
	literals.put(new ANTLRHashString("through", this), new Integer(202));
	literals.put(new ANTLRHashString("break", this), new Integer(195));
	literals.put(new ANTLRHashString("sra", this), new Integer(258));
	literals.put(new ANTLRHashString("for", this), new Integer(205));
	literals.put(new ANTLRHashString("else", this), new Integer(215));
	literals.put(new ANTLRHashString("is", this), new Integer(182));
	literals.put(new ANTLRHashString("range", this), new Integer(201));
	literals.put(new ANTLRHashString("of", this), new Integer(189));
	literals.put(new ANTLRHashString("and", this), new Integer(249));
	literals.put(new ANTLRHashString("after", this), new Integer(224));
	literals.put(new ANTLRHashString("architecture", this), new Integer(188));
	literals.put(new ANTLRHashString("begin", this), new Integer(190));
	literals.put(new ANTLRHashString("units", this), new Integer(234));
	literals.put(new ANTLRHashString("procedure", this), new Integer(226));
	literals.put(new ANTLRHashString("postponed", this), new Integer(191));
	literals.put(new ANTLRHashString("sll", this), new Integer(255));
	literals.put(new ANTLRHashString("open", this), new Integer(176));
	literals.put(new ANTLRHashString("function", this), new Integer(227));
	literals.put(new ANTLRHashString("downto", this), new Integer(222));
	literals.put(new ANTLRHashString("rol", this), new Integer(259));
	literals.put(new ANTLRHashString("register", this), new Integer(300));
	literals.put(new ANTLRHashString("label", this), new Integer(233));
	literals.put(new ANTLRHashString("unaffected", this), new Integer(309));
	literals.put(new ANTLRHashString("not", this), new Integer(269));
	literals.put(new ANTLRHashString("record", this), new Integer(294));
	literals.put(new ANTLRHashString("all", this), new Integer(241));
	literals.put(new ANTLRHashString("configuration", this), new Integer(216));
	literals.put(new ANTLRHashString("in", this), new Integer(278));
	literals.put(new ANTLRHashString("file", this), new Integer(236));
	literals.put(new ANTLRHashString("generic", this), new Integer(271));
	literals.put(new ANTLRHashString("subtype", this), new Integer(230));
	literals.put(new ANTLRHashString("guarded", this), new Integer(292));
	literals.put(new ANTLRHashString("shared", this), new Integer(308));
	literals.put(new ANTLRHashString("reject", this), new Integer(219));
	literals.put(new ANTLRHashString("nor", this), new Integer(252));
	literals.put(new ANTLRHashString("component", this), new Integer(212));
	literals.put(new ANTLRHashString("null", this), new Integer(283));
	literals.put(new ANTLRHashString("when", this), new Integer(208));
	literals.put(new ANTLRHashString("attribute", this), new Integer(200));
	literals.put(new ANTLRHashString("elsif", this), new Integer(277));
	literals.put(new ANTLRHashString("across", this), new Integer(175));
	literals.put(new ANTLRHashString("library", this), new Integer(282));
	literals.put(new ANTLRHashString("port", this), new Integer(293));
	literals.put(new ANTLRHashString("buffer", this), new Integer(288));
	literals.put(new ANTLRHashString("to", this), new Integer(221));
	literals.put(new ANTLRHashString("case", this), new Integer(209));
	literals.put(new ANTLRHashString("next", this), new Integer(291));
	literals.put(new ANTLRHashString("spectrum", this), new Integer(303));
	literals.put(new ANTLRHashString("inout", this), new Integer(287));
	literals.put(new ANTLRHashString("subnature", this), new Integer(238));
	literals.put(new ANTLRHashString("severity", this), new Integer(198));
	literals.put(new ANTLRHashString("others", this), new Integer(210));
	literals.put(new ANTLRHashString("abs", this), new Integer(268));
	literals.put(new ANTLRHashString("block", this), new Integer(206));
	literals.put(new ANTLRHashString("constant", this), new Integer(217));
	literals.put(new ANTLRHashString("terminal", this), new Integer(239));
	literals.put(new ANTLRHashString("xnor", this), new Integer(254));
	literals.put(new ANTLRHashString("tolerance", this), new Integer(203));
	literals.put(new ANTLRHashString("then", this), new Integer(276));
	literals.put(new ANTLRHashString("sla", this), new Integer(257));
	literals.put(new ANTLRHashString("impure", this), new Integer(306));
	literals.put(new ANTLRHashString("protected", this), new Integer(168));
	literals.put(new ANTLRHashString("nature", this), new Integer(237));
	literals.put(new ANTLRHashString("transport", this), new Integer(218));
	literals.put(new ANTLRHashString("or", this), new Integer(250));
	literals.put(new ANTLRHashString("if", this), new Integer(270));
	literals.put(new ANTLRHashString("literal", this), new Integer(105));
	literals.put(new ANTLRHashString("srl", this), new Integer(256));
	literals.put(new ANTLRHashString("loop", this), new Integer(286));
	literals.put(new ANTLRHashString("return", this), new Integer(295));
	literals.put(new ANTLRHashString("group", this), new Integer(235));
	literals.put(new ANTLRHashString("new", this), new Integer(186));
	literals.put(new ANTLRHashString("array", this), new Integer(196));
	literals.put(new ANTLRHashString("rem", this), new Integer(267));
	literals.put(new ANTLRHashString("assert", this), new Integer(193));
	literals.put(new ANTLRHashString("generate", this), new Integer(194));
	literals.put(new ANTLRHashString("report", this), new Integer(197));
	literals.put(new ANTLRHashString("end", this), new Integer(169));
	literals.put(new ANTLRHashString("context", this), new Integer(307));
	literals.put(new ANTLRHashString("body", this), new Integer(170));
	literals.put(new ANTLRHashString("mod", this), new Integer(266));
	literals.put(new ANTLRHashString("disconnect", this), new Integer(223));
	literals.put(new ANTLRHashString("on", this), new Integer(298));
	literals.put(new ANTLRHashString("inertial", this), new Integer(220));
	literals.put(new ANTLRHashString("reference", this), new Integer(275));
	literals.put(new ANTLRHashString("select", this), new Integer(297));
	literals.put(new ANTLRHashString("map", this), new Integer(272));
	literals.put(new ANTLRHashString("linkage", this), new Integer(289));
	literals.put(new ANTLRHashString("while", this), new Integer(281));
	literals.put(new ANTLRHashString("signal", this), new Integer(231));
	literals.put(new ANTLRHashString("package", this), new Integer(228));
	literals.put(new ANTLRHashString("noise", this), new Integer(304));
	literals.put(new ANTLRHashString("type", this), new Integer(229));
	literals.put(new ANTLRHashString("bus", this), new Integer(279));
	literals.put(new ANTLRHashString("access", this), new Integer(173));
	literals.put(new ANTLRHashString("alias", this), new Integer(181));
	literals.put(new ANTLRHashString("out", this), new Integer(280));
	literals.put(new ANTLRHashString("exit", this), new Integer(242));
}

public Token nextToken() throws TokenStreamException {
	Token theRetToken=null;
tryAgain:
	for (;;) {
		Token _token = null;
		int _ttype = Token.INVALID_TYPE;
		resetText();
		try {   // for char stream error handling
			try {   // for lexical error handling
				switch ( LA(1)) {
				case '\t':  case '\u000c':  case ' ':
				{
					mWS_(true);
					theRetToken=_returnToken;
					break;
				}
				case '\n':  case '\r':
				{
					mNEWLINE(true);
					theRetToken=_returnToken;
					break;
				}
				case ';':
				{
					mSEMI(true);
					theRetToken=_returnToken;
					break;
				}
				case ',':
				{
					mCOMMA(true);
					theRetToken=_returnToken;
					break;
				}
				case '&':
				{
					mAMPERSAND(true);
					theRetToken=_returnToken;
					break;
				}
				case '\'':
				{
					mAPOSTROPHE(true);
					theRetToken=_returnToken;
					break;
				}
				case '(':
				{
					mLPAREN(true);
					theRetToken=_returnToken;
					break;
				}
				case ')':
				{
					mRPAREN(true);
					theRetToken=_returnToken;
					break;
				}
				case '[':
				{
					mLBRACKET(true);
					theRetToken=_returnToken;
					break;
				}
				case ']':
				{
					mRBRACKET(true);
					theRetToken=_returnToken;
					break;
				}
				case '+':
				{
					mPLUS(true);
					theRetToken=_returnToken;
					break;
				}
				case '|':
				{
					mBAR(true);
					theRetToken=_returnToken;
					break;
				}
				case '.':
				{
					mDOT(true);
					theRetToken=_returnToken;
					break;
				}
				case '0':  case '1':  case '2':  case '3':
				case '4':  case '5':  case '6':  case '7':
				case '8':  case '9':
				{
					mDECIMAL_LITERAL(true);
					theRetToken=_returnToken;
					break;
				}
				default:
					if ((LA(1)=='-') && (LA(2)=='-')) {
						mCOMMENT(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='*') && (LA(2)=='*')) {
						mDOUBLESTAR(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='=') && (LA(2)=='=')) {
						mASSIGN(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='<') && (LA(2)=='=')) {
						mLE(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='>') && (LA(2)=='=')) {
						mGE(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='=') && (LA(2)=='>')) {
						mARROW(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='/') && (LA(2)=='=')) {
						mNEQ(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)==':') && (LA(2)=='=')) {
						mVARASGN(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='<') && (LA(2)=='>')) {
						mBOX(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='\\') && (_tokenSet_0.member(LA(2)))) {
						mEXTENDED_IDENTIFIER(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='b'||LA(1)=='o'||LA(1)=='x') && (LA(2)=='"')) {
						mBIT_STRING_LITERAL(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='"') && ((LA(2) >= '\u0000' && LA(2) <= '\u00ff'))) {
						mSTRING_LITERAL(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='"') && (true)) {
						mDBLQUOTE(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)==':') && (true)) {
						mCOLON(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='*') && (true)) {
						mMUL(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='/') && (true)) {
						mDIV(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='-') && (true)) {
						mMINUS(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='<') && (true)) {
						mLOWERTHAN(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='>') && (true)) {
						mGREATERTHAN(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='=') && (true)) {
						mEQ(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='\\') && (true)) {
						mBACKSLASH(true);
						theRetToken=_returnToken;
					}
					else if (((LA(1) >= 'a' && LA(1) <= 'z')) && (true)) {
						mBASIC_IDENTIFIER(true);
						theRetToken=_returnToken;
					}
				else {
					if (LA(1)==EOF_CHAR) {uponEOF(); _returnToken = makeToken(Token.EOF_TYPE);}
				else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());}
				}
				}
				if ( _returnToken==null ) continue tryAgain; // found SKIP token
				_ttype = _returnToken.getType();
				_ttype = testLiteralsTable(_ttype);
				_returnToken.setType(_ttype);
				return _returnToken;
			}
			catch (RecognitionException e) {
				throw new TokenStreamRecognitionException(e);
			}
		}
		catch (CharStreamException cse) {
			if ( cse instanceof CharStreamIOException ) {
				throw new TokenStreamIOException(((CharStreamIOException)cse).io);
			}
			else {
				throw new TokenStreamException(cse.getMessage());
			}
		}
	}
}

	public final void mWS_(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = WS_;
		int _saveIndex;
		
		{
		switch ( LA(1)) {
		case ' ':
		{
			match(' ');
			break;
		}
		case '\t':
		{
			match('\t');
			break;
		}
		case '\u000c':
		{
			match('\f');
			break;
		}
		default:
		{
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		}
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mNEWLINE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = NEWLINE;
		int _saveIndex;
		
		{
		{
		if ((LA(1)=='\r') && (LA(2)=='\n')) {
			match("\r\n");
		}
		else if ((LA(1)=='\r') && (true)) {
			match('\r');
		}
		else if ((LA(1)=='\n')) {
			match('\n');
		}
		else {
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		
		}
		if ( inputState.guessing==0 ) {
			newline();
		}
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mCOMMENT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = COMMENT;
		int _saveIndex;
		
		match("--");
		{
		_loop724:
		do {
			if ((_tokenSet_1.member(LA(1)))) {
				matchNot('\n');
			}
			else {
				break _loop724;
			}
			
		} while (true);
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mDOUBLESTAR(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = DOUBLESTAR;
		int _saveIndex;
		
		match("**");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mASSIGN(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = ASSIGN;
		int _saveIndex;
		
		match("==");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mLE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = LE;
		int _saveIndex;
		
		match("<=");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mGE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = GE;
		int _saveIndex;
		
		match(">=");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mARROW(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = ARROW;
		int _saveIndex;
		
		match("=>");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mNEQ(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = NEQ;
		int _saveIndex;
		
		match("/=");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mVARASGN(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = VARASGN;
		int _saveIndex;
		
		match(":=");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mBOX(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = BOX;
		int _saveIndex;
		
		match("<>");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mDBLQUOTE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = DBLQUOTE;
		int _saveIndex;
		
		match('\"');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mSEMI(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = SEMI;
		int _saveIndex;
		
		match(';');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mCOMMA(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = COMMA;
		int _saveIndex;
		
		match(',');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mAMPERSAND(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = AMPERSAND;
		int _saveIndex;
		
		match('&');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mAPOSTROPHE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = APOSTROPHE;
		int _saveIndex;
		
		match('\'');
		{
		boolean synPredMatched741 = false;
		if (((_tokenSet_2.member(LA(1))))) {
			int _m741 = mark();
			synPredMatched741 = true;
			inputState.guessing++;
			try {
				{
				{
				switch ( LA(1)) {
				case 'a':  case 'b':  case 'c':  case 'd':
				case 'e':  case 'f':  case 'g':  case 'h':
				case 'i':  case 'j':  case 'k':  case 'l':
				case 'm':  case 'n':  case 'o':  case 'p':
				case 'q':  case 'r':  case 's':  case 't':
				case 'u':  case 'v':  case 'w':  case 'x':
				case 'y':  case 'z':
				{
					matchRange('a','z');
					break;
				}
				case '0':  case '1':  case '2':  case '3':
				case '4':  case '5':  case '6':  case '7':
				case '8':  case '9':
				{
					matchRange('0','9');
					break;
				}
				case '&':
				{
					match('&');
					break;
				}
				case '\'':
				{
					match('\'');
					break;
				}
				case '?':
				{
					match('?');
					break;
				}
				case '+':
				{
					match('+');
					break;
				}
				case ',':
				{
					match(',');
					break;
				}
				case '-':
				{
					match('-');
					break;
				}
				case '.':
				{
					match('.');
					break;
				}
				case '/':
				{
					match('/');
					break;
				}
				case ':':
				{
					match(':');
					break;
				}
				case ';':
				{
					match(';');
					break;
				}
				case '<':
				{
					match('<');
					break;
				}
				case '=':
				{
					match('=');
					break;
				}
				case '>':
				{
					match('>');
					break;
				}
				case '|':
				{
					match('|');
					break;
				}
				case '$':
				{
					match('$');
					break;
				}
				case ' ':
				{
					match(' ');
					break;
				}
				case '\u00a0':  case '\u00a1':  case '\u00a2':  case '\u00a3':
				case '\u00a4':  case '\u00a5':  case '\u00a6':  case '\u00a7':
				case '\u00a8':  case '\u00a9':  case '\u00aa':  case '\u00ab':
				case '\u00ac':  case '\u00ad':  case '\u00ae':  case '\u00af':
				case '\u00b0':  case '\u00b1':  case '\u00b2':  case '\u00b3':
				case '\u00b4':  case '\u00b5':  case '\u00b6':  case '\u00b7':
				case '\u00b8':  case '\u00b9':  case '\u00ba':  case '\u00bb':
				case '\u00bc':  case '\u00bd':  case '\u00be':  case '\u00bf':
				case '\u00c0':  case '\u00c1':  case '\u00c2':  case '\u00c3':
				case '\u00c4':  case '\u00c5':  case '\u00c6':  case '\u00c7':
				case '\u00c8':  case '\u00c9':  case '\u00ca':  case '\u00cb':
				case '\u00cc':  case '\u00cd':  case '\u00ce':  case '\u00cf':
				case '\u00d0':  case '\u00d1':  case '\u00d2':  case '\u00d3':
				case '\u00d4':  case '\u00d5':  case '\u00d6':  case '\u00d7':
				case '\u00d8':  case '\u00d9':  case '\u00da':  case '\u00db':
				case '\u00dc':  case '\u00dd':  case '\u00de':  case '\u00df':
				case '\u00e0':  case '\u00e1':  case '\u00e2':  case '\u00e3':
				case '\u00e4':  case '\u00e5':  case '\u00e6':  case '\u00e7':
				case '\u00e8':  case '\u00e9':  case '\u00ea':  case '\u00eb':
				case '\u00ec':  case '\u00ed':  case '\u00ee':  case '\u00ef':
				case '\u00f0':  case '\u00f1':  case '\u00f2':  case '\u00f3':
				case '\u00f4':  case '\u00f5':  case '\u00f6':  case '\u00f7':
				case '\u00f8':  case '\u00f9':  case '\u00fa':  case '\u00fb':
				case '\u00fc':  case '\u00fd':  case '\u00fe':  case '\u00ff':
				{
					mOTHER_SPECIAL_CHARACTER(false);
					break;
				}
				case '\\':
				{
					match('\\');
					break;
				}
				case '#':
				{
					match('#');
					break;
				}
				case '[':
				{
					match('[');
					break;
				}
				case ']':
				{
					match(']');
					break;
				}
				case '_':
				{
					match('_');
					break;
				}
				case '*':
				{
					match('*');
					break;
				}
				case '"':
				{
					match('"');
					break;
				}
				default:
				{
					throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
				}
				}
				}
				match('\'');
				}
			}
			catch (RecognitionException pe) {
				synPredMatched741 = false;
			}
			rewind(_m741);
inputState.guessing--;
		}
		if ( synPredMatched741 ) {
			{
			switch ( LA(1)) {
			case 'a':  case 'b':  case 'c':  case 'd':
			case 'e':  case 'f':  case 'g':  case 'h':
			case 'i':  case 'j':  case 'k':  case 'l':
			case 'm':  case 'n':  case 'o':  case 'p':
			case 'q':  case 'r':  case 's':  case 't':
			case 'u':  case 'v':  case 'w':  case 'x':
			case 'y':  case 'z':
			{
				matchRange('a','z');
				break;
			}
			case '0':  case '1':  case '2':  case '3':
			case '4':  case '5':  case '6':  case '7':
			case '8':  case '9':
			{
				matchRange('0','9');
				break;
			}
			case '&':
			{
				match('&');
				break;
			}
			case '\'':
			{
				match('\'');
				break;
			}
			case '(':
			{
				match('(');
				break;
			}
			case ')':
			{
				match(')');
				break;
			}
			case '?':
			{
				match('?');
				break;
			}
			case '+':
			{
				match('+');
				break;
			}
			case ',':
			{
				match(',');
				break;
			}
			case '-':
			{
				match('-');
				break;
			}
			case '.':
			{
				match('.');
				break;
			}
			case '/':
			{
				match('/');
				break;
			}
			case ':':
			{
				match(':');
				break;
			}
			case ';':
			{
				match(';');
				break;
			}
			case '<':
			{
				match('<');
				break;
			}
			case '=':
			{
				match('=');
				break;
			}
			case '>':
			{
				match('>');
				break;
			}
			case '|':
			{
				match('|');
				break;
			}
			case '$':
			{
				match('$');
				break;
			}
			case ' ':
			{
				match(' ');
				break;
			}
			case '\u00a0':  case '\u00a1':  case '\u00a2':  case '\u00a3':
			case '\u00a4':  case '\u00a5':  case '\u00a6':  case '\u00a7':
			case '\u00a8':  case '\u00a9':  case '\u00aa':  case '\u00ab':
			case '\u00ac':  case '\u00ad':  case '\u00ae':  case '\u00af':
			case '\u00b0':  case '\u00b1':  case '\u00b2':  case '\u00b3':
			case '\u00b4':  case '\u00b5':  case '\u00b6':  case '\u00b7':
			case '\u00b8':  case '\u00b9':  case '\u00ba':  case '\u00bb':
			case '\u00bc':  case '\u00bd':  case '\u00be':  case '\u00bf':
			case '\u00c0':  case '\u00c1':  case '\u00c2':  case '\u00c3':
			case '\u00c4':  case '\u00c5':  case '\u00c6':  case '\u00c7':
			case '\u00c8':  case '\u00c9':  case '\u00ca':  case '\u00cb':
			case '\u00cc':  case '\u00cd':  case '\u00ce':  case '\u00cf':
			case '\u00d0':  case '\u00d1':  case '\u00d2':  case '\u00d3':
			case '\u00d4':  case '\u00d5':  case '\u00d6':  case '\u00d7':
			case '\u00d8':  case '\u00d9':  case '\u00da':  case '\u00db':
			case '\u00dc':  case '\u00dd':  case '\u00de':  case '\u00df':
			case '\u00e0':  case '\u00e1':  case '\u00e2':  case '\u00e3':
			case '\u00e4':  case '\u00e5':  case '\u00e6':  case '\u00e7':
			case '\u00e8':  case '\u00e9':  case '\u00ea':  case '\u00eb':
			case '\u00ec':  case '\u00ed':  case '\u00ee':  case '\u00ef':
			case '\u00f0':  case '\u00f1':  case '\u00f2':  case '\u00f3':
			case '\u00f4':  case '\u00f5':  case '\u00f6':  case '\u00f7':
			case '\u00f8':  case '\u00f9':  case '\u00fa':  case '\u00fb':
			case '\u00fc':  case '\u00fd':  case '\u00fe':  case '\u00ff':
			{
				mOTHER_SPECIAL_CHARACTER(false);
				break;
			}
			case '\\':
			{
				match('\\');
				break;
			}
			case '#':
			{
				match('#');
				break;
			}
			case '[':
			{
				match('[');
				break;
			}
			case ']':
			{
				match(']');
				break;
			}
			case '_':
			{
				match('_');
				break;
			}
			case '*':
			{
				match('*');
				break;
			}
			case '"':
			{
				match('"');
				break;
			}
			default:
			{
				throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
			}
			}
			}
			match('\'');
			if ( inputState.guessing==0 ) {
				_ttype = CHARACTER_LITERAL;
			}
		}
		else {
		}
		
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mOTHER_SPECIAL_CHARACTER(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = OTHER_SPECIAL_CHARACTER;
		int _saveIndex;
		
		matchRange('\u00a0','\u00ff');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mLPAREN(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = LPAREN;
		int _saveIndex;
		
		match('(');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mRPAREN(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = RPAREN;
		int _saveIndex;
		
		match(')');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mLBRACKET(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = LBRACKET;
		int _saveIndex;
		
		match('[');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mRBRACKET(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = RBRACKET;
		int _saveIndex;
		
		match(']');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mCOLON(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = COLON;
		int _saveIndex;
		
		match(':');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mMUL(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = MUL;
		int _saveIndex;
		
		match('*');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mDIV(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = DIV;
		int _saveIndex;
		
		match('/');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mPLUS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = PLUS;
		int _saveIndex;
		
		match('+');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mMINUS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = MINUS;
		int _saveIndex;
		
		match('-');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mLOWERTHAN(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = LOWERTHAN;
		int _saveIndex;
		
		match('<');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mGREATERTHAN(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = GREATERTHAN;
		int _saveIndex;
		
		match('>');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mEQ(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = EQ;
		int _saveIndex;
		
		match('=');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mBAR(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = BAR;
		int _saveIndex;
		
		match('|');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mDOT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = DOT;
		int _saveIndex;
		
		match('.');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mBACKSLASH(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = BACKSLASH;
		int _saveIndex;
		
		match('\\');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mBASIC_IDENTIFIER(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = BASIC_IDENTIFIER;
		int _saveIndex;
		
		matchRange('a','z');
		{
		_loop760:
		do {
			switch ( LA(1)) {
			case 'a':  case 'b':  case 'c':  case 'd':
			case 'e':  case 'f':  case 'g':  case 'h':
			case 'i':  case 'j':  case 'k':  case 'l':
			case 'm':  case 'n':  case 'o':  case 'p':
			case 'q':  case 'r':  case 's':  case 't':
			case 'u':  case 'v':  case 'w':  case 'x':
			case 'y':  case 'z':
			{
				matchRange('a','z');
				break;
			}
			case '0':  case '1':  case '2':  case '3':
			case '4':  case '5':  case '6':  case '7':
			case '8':  case '9':
			{
				matchRange('0','9');
				break;
			}
			case '_':
			{
				match('_');
				break;
			}
			default:
			{
				break _loop760;
			}
			}
		} while (true);
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mEXTENDED_IDENTIFIER(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = EXTENDED_IDENTIFIER;
		int _saveIndex;
		
		match('\\');
		{
		int _cnt763=0;
		_loop763:
		do {
			switch ( LA(1)) {
			case 'a':  case 'b':  case 'c':  case 'd':
			case 'e':  case 'f':  case 'g':  case 'h':
			case 'i':  case 'j':  case 'k':  case 'l':
			case 'm':  case 'n':  case 'o':  case 'p':
			case 'q':  case 'r':  case 's':  case 't':
			case 'u':  case 'v':  case 'w':  case 'x':
			case 'y':  case 'z':
			{
				matchRange('a','z');
				break;
			}
			case '0':  case '1':  case '2':  case '3':
			case '4':  case '5':  case '6':  case '7':
			case '8':  case '9':
			{
				matchRange('0','9');
				break;
			}
			case '&':
			{
				match('&');
				break;
			}
			case '\'':
			{
				match('\'');
				break;
			}
			case '(':
			{
				match('(');
				break;
			}
			case ')':
			{
				match(')');
				break;
			}
			case '+':
			{
				match('+');
				break;
			}
			case ',':
			{
				match(',');
				break;
			}
			case '-':
			{
				match('-');
				break;
			}
			case '.':
			{
				match('.');
				break;
			}
			case '/':
			{
				match('/');
				break;
			}
			case ':':
			{
				match(':');
				break;
			}
			case ';':
			{
				match(';');
				break;
			}
			case '<':
			{
				match('<');
				break;
			}
			case '=':
			{
				match('=');
				break;
			}
			case '>':
			{
				match('>');
				break;
			}
			case '|':
			{
				match('|');
				break;
			}
			case ' ':
			{
				match(' ');
				break;
			}
			case '\u00a0':  case '\u00a1':  case '\u00a2':  case '\u00a3':
			case '\u00a4':  case '\u00a5':  case '\u00a6':  case '\u00a7':
			case '\u00a8':  case '\u00a9':  case '\u00aa':  case '\u00ab':
			case '\u00ac':  case '\u00ad':  case '\u00ae':  case '\u00af':
			case '\u00b0':  case '\u00b1':  case '\u00b2':  case '\u00b3':
			case '\u00b4':  case '\u00b5':  case '\u00b6':  case '\u00b7':
			case '\u00b8':  case '\u00b9':  case '\u00ba':  case '\u00bb':
			case '\u00bc':  case '\u00bd':  case '\u00be':  case '\u00bf':
			case '\u00c0':  case '\u00c1':  case '\u00c2':  case '\u00c3':
			case '\u00c4':  case '\u00c5':  case '\u00c6':  case '\u00c7':
			case '\u00c8':  case '\u00c9':  case '\u00ca':  case '\u00cb':
			case '\u00cc':  case '\u00cd':  case '\u00ce':  case '\u00cf':
			case '\u00d0':  case '\u00d1':  case '\u00d2':  case '\u00d3':
			case '\u00d4':  case '\u00d5':  case '\u00d6':  case '\u00d7':
			case '\u00d8':  case '\u00d9':  case '\u00da':  case '\u00db':
			case '\u00dc':  case '\u00dd':  case '\u00de':  case '\u00df':
			case '\u00e0':  case '\u00e1':  case '\u00e2':  case '\u00e3':
			case '\u00e4':  case '\u00e5':  case '\u00e6':  case '\u00e7':
			case '\u00e8':  case '\u00e9':  case '\u00ea':  case '\u00eb':
			case '\u00ec':  case '\u00ed':  case '\u00ee':  case '\u00ef':
			case '\u00f0':  case '\u00f1':  case '\u00f2':  case '\u00f3':
			case '\u00f4':  case '\u00f5':  case '\u00f6':  case '\u00f7':
			case '\u00f8':  case '\u00f9':  case '\u00fa':  case '\u00fb':
			case '\u00fc':  case '\u00fd':  case '\u00fe':  case '\u00ff':
			{
				mOTHER_SPECIAL_CHARACTER(false);
				break;
			}
			case '#':
			{
				match('#');
				break;
			}
			case '[':
			{
				match('[');
				break;
			}
			case ']':
			{
				match(']');
				break;
			}
			case '_':
			{
				match('_');
				break;
			}
			default:
			{
				if ( _cnt763>=1 ) { break _loop763; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());}
			}
			}
			_cnt763++;
		} while (true);
		}
		match('\\');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mBIT_STRING_LITERAL(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = BIT_STRING_LITERAL;
		int _saveIndex;
		
		{
		switch ( LA(1)) {
		case 'b':
		{
			match('b');
			break;
		}
		case 'o':
		{
			match('o');
			break;
		}
		case 'x':
		{
			match('x');
			break;
		}
		default:
		{
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		}
		}
		match('\"');
		{
		switch ( LA(1)) {
		case '0':  case '1':  case '2':  case '3':
		case '4':  case '5':  case '6':  case '7':
		case '8':  case '9':  case 'a':  case 'b':
		case 'c':  case 'd':  case 'e':  case 'f':
		{
			mBASED_INTEGER(false);
			break;
		}
		case '\'':
		{
			match('\'');
			break;
		}
		case '(':
		{
			match('(');
			break;
		}
		case ')':
		{
			match(')');
			break;
		}
		case '+':
		{
			match('+');
			break;
		}
		case ',':
		{
			match(',');
			break;
		}
		case '-':
		{
			match('-');
			break;
		}
		case '.':
		{
			match('.');
			break;
		}
		case '/':
		{
			match('/');
			break;
		}
		case ':':
		{
			match(':');
			break;
		}
		case ';':
		{
			match(';');
			break;
		}
		case '<':
		{
			match('<');
			break;
		}
		case '=':
		{
			match('=');
			break;
		}
		case '>':
		{
			match('>');
			break;
		}
		case '|':
		{
			match('|');
			break;
		}
		default:
		{
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		}
		}
		match('\"');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mBASED_INTEGER(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = BASED_INTEGER;
		int _saveIndex;
		
		{
		switch ( LA(1)) {
		case '0':  case '1':  case '2':  case '3':
		case '4':  case '5':  case '6':  case '7':
		case '8':  case '9':
		{
			matchRange('0','9');
			break;
		}
		case 'a':  case 'b':  case 'c':  case 'd':
		case 'e':  case 'f':
		{
			matchRange('a','f');
			break;
		}
		default:
		{
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		}
		}
		{
		_loop791:
		do {
			switch ( LA(1)) {
			case '_':
			{
				match('_');
				break;
			}
			case '0':  case '1':  case '2':  case '3':
			case '4':  case '5':  case '6':  case '7':
			case '8':  case '9':
			{
				matchRange('0','9');
				break;
			}
			case 'a':  case 'b':  case 'c':  case 'd':
			case 'e':  case 'f':
			{
				matchRange('a','f');
				break;
			}
			default:
			{
				break _loop791;
			}
			}
		} while (true);
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mSTRING_LITERAL(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = STRING_LITERAL;
		int _saveIndex;
		
		match('\"');
		{
		_loop769:
		do {
			if ((_tokenSet_3.member(LA(1)))) {
				matchNot('\"');
			}
			else {
				break _loop769;
			}
			
		} while (true);
		}
		match('\"');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mDECIMAL_LITERAL(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = DECIMAL_LITERAL;
		int _saveIndex;
		
		mINTEGER(false);
		{
		switch ( LA(1)) {
		case '#':
		{
			{
			match('#');
			mBASED_INTEGER(false);
			{
			switch ( LA(1)) {
			case '.':
			{
				match('.');
				mBASED_INTEGER(false);
				break;
			}
			case '#':
			{
				break;
			}
			default:
			{
				throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
			}
			}
			}
			match('#');
			{
			if ((LA(1)=='e')) {
				mEXPONENT(false);
			}
			else {
			}
			
			}
			}
			if ( inputState.guessing==0 ) {
				_ttype = BASED_LITERAL;
			}
			break;
		}
		case 'b':  case 'o':  case 's':  case 'u':
		case 'x':
		{
			{
			switch ( LA(1)) {
			case 's':
			{
				match('s');
				break;
			}
			case 'u':
			{
				match('u');
				break;
			}
			case 'b':  case 'o':  case 'x':
			{
				break;
			}
			default:
			{
				throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
			}
			}
			}
			{
			switch ( LA(1)) {
			case 'b':
			{
				match('b');
				break;
			}
			case 'o':
			{
				match('o');
				break;
			}
			case 'x':
			{
				match('x');
				break;
			}
			default:
			{
				throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
			}
			}
			}
			match('\"');
			{
			switch ( LA(1)) {
			case '0':  case '1':  case '2':  case '3':
			case '4':  case '5':  case '6':  case '7':
			case '8':  case '9':  case 'a':  case 'b':
			case 'c':  case 'd':  case 'e':  case 'f':
			{
				mBASED_INTEGER(false);
				break;
			}
			case '\'':
			{
				match('\'');
				break;
			}
			case '(':
			{
				match('(');
				break;
			}
			case ')':
			{
				match(')');
				break;
			}
			case '+':
			{
				match('+');
				break;
			}
			case ',':
			{
				match(',');
				break;
			}
			case '-':
			{
				match('-');
				break;
			}
			case '.':
			{
				match('.');
				break;
			}
			case '/':
			{
				match('/');
				break;
			}
			case ':':
			{
				match(':');
				break;
			}
			case ';':
			{
				match(';');
				break;
			}
			case '<':
			{
				match('<');
				break;
			}
			case '=':
			{
				match('=');
				break;
			}
			case '>':
			{
				match('>');
				break;
			}
			case '|':
			{
				match('|');
				break;
			}
			default:
			{
				throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
			}
			}
			}
			match('\"');
			break;
		}
		default:
			{
				{
				{
				if ((LA(1)=='.')) {
					match('.');
					mINTEGER(false);
				}
				else {
				}
				
				}
				{
				if ((LA(1)=='e')) {
					mEXPONENT(false);
				}
				else {
				}
				
				}
				}
			}
		}
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mINTEGER(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = INTEGER;
		int _saveIndex;
		
		matchRange('0','9');
		{
		_loop785:
		do {
			switch ( LA(1)) {
			case '_':
			{
				match('_');
				break;
			}
			case '0':  case '1':  case '2':  case '3':
			case '4':  case '5':  case '6':  case '7':
			case '8':  case '9':
			{
				matchRange('0','9');
				break;
			}
			default:
			{
				break _loop785;
			}
			}
		} while (true);
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mEXPONENT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = EXPONENT;
		int _saveIndex;
		
		match('e');
		{
		switch ( LA(1)) {
		case '+':
		{
			match('+');
			break;
		}
		case '-':
		{
			match('-');
			break;
		}
		case '0':  case '1':  case '2':  case '3':
		case '4':  case '5':  case '6':  case '7':
		case '8':  case '9':
		{
			break;
		}
		default:
		{
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		}
		}
		mINTEGER(false);
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mALL_CHARACTER(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = ALL_CHARACTER;
		int _saveIndex;
		
		matchRange('\u0000','\u00ff');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	
	private static final long[] mk_tokenSet_0() {
		long[] data = new long[12];
		data[0]=9223367402585063424L;
		data[1]=1729382251138908160L;
		data[2]=-4294967296L;
		data[3]=-1L;
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	private static final long[] mk_tokenSet_1() {
		long[] data = new long[8];
		data[0]=-1025L;
		for (int i = 1; i<=3; i++) { data[i]=-1L; }
		return data;
	}
	public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
	private static final long[] mk_tokenSet_2() {
		long[] data = new long[12];
		data[0]=-150323855360L;
		data[1]=1729382251407343616L;
		data[2]=-4294967296L;
		data[3]=-1L;
		return data;
	}
	public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
	private static final long[] mk_tokenSet_3() {
		long[] data = new long[8];
		data[0]=-17179869185L;
		for (int i = 1; i<=3; i++) { data[i]=-1L; }
		return data;
	}
	public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
	
	}
