// $ANTLR 2.7.7 (2006-11-01): "vhdl.g" -> "VhdlParser.java"$
package com.simplifide.base.vhdl.parse.grammar;


import com.simplifide.base.sourcefile.antlr.tok.*;



import antlr.TokenBuffer;
import antlr.TokenStreamException;
import antlr.TokenStreamIOException;
import antlr.ANTLRException;
import com.simplifide.base.sourcefile.antlr.grammar.BaseParser;      import com.simplifide.base.sourcefile.antlr.grammar.BaseVhdlParser;
import antlr.Token;
import antlr.TokenStream;
import antlr.RecognitionException;
import antlr.NoViableAltException;
import antlr.MismatchedTokenException;
import antlr.SemanticException;
import antlr.ParserSharedInputState;
import antlr.collections.impl.BitSet;
import antlr.collections.AST;
import java.util.Hashtable;
import antlr.ASTFactory;
import antlr.ASTPair;
import antlr.collections.impl.ASTArray;

public class VhdlParser extends BaseVhdlParser       implements VhdlTokenTypes
 {

   

protected VhdlParser(TokenBuffer tokenBuf, int k) {
  super(tokenBuf,k);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

public VhdlParser(TokenBuffer tokenBuf) {
  this(tokenBuf,3);
}

protected VhdlParser(TokenStream lexer, int k) {
  super(lexer,k);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

public VhdlParser(TokenStream lexer) {
  this(lexer,3);
}

public VhdlParser(ParserSharedInputState state) {
  super(state,3);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

	public final void protected_type_definition() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST protected_type_definition_AST = null;
		
		try {      // for error handling
			if ((LA(1)==PROTECTED) && (_tokenSet_0.member(LA(2)))) {
				protected_type_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				protected_type_definition_AST = (AST)currentAST.root;
			}
			else if ((LA(1)==PROTECTED) && (LA(2)==BODY)) {
				protected_type_body();
				astFactory.addASTChild(currentAST, returnAST);
				protected_type_definition_AST = (AST)currentAST.root;
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_1);
			} else {
			  throw ex;
			}
		}
		returnAST = protected_type_definition_AST;
	}
	
	public final void protected_type_declaration() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST protected_type_declaration_AST = null;
		
		try {      // for error handling
			AST tmp1_AST = null;
			tmp1_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp1_AST);
			match(PROTECTED);
			protected_type_declarative_part();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp2_AST = null;
			tmp2_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp2_AST);
			match(END);
			AST tmp3_AST = null;
			tmp3_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp3_AST);
			match(PROTECTED);
			{
			switch ( LA(1)) {
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			{
				name();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case SEMI:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			protected_type_declaration_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_1);
			} else {
			  throw ex;
			}
		}
		returnAST = protected_type_declaration_AST;
	}
	
	public final void protected_type_body() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST protected_type_body_AST = null;
		
		try {      // for error handling
			AST tmp4_AST = null;
			tmp4_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp4_AST);
			match(PROTECTED);
			AST tmp5_AST = null;
			tmp5_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp5_AST);
			match(BODY);
			protected_type_body_declarative_part();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp6_AST = null;
			tmp6_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp6_AST);
			match(END);
			AST tmp7_AST = null;
			tmp7_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp7_AST);
			match(PROTECTED);
			AST tmp8_AST = null;
			tmp8_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp8_AST);
			match(BODY);
			{
			switch ( LA(1)) {
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			{
				name();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case SEMI:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			protected_type_body_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_1);
			} else {
			  throw ex;
			}
		}
		returnAST = protected_type_body_AST;
	}
	
	public final void protected_type_declarative_part() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST protected_type_declarative_part_AST = null;
		
		try {      // for error handling
			{
			_loop6:
			do {
				if ((_tokenSet_2.member(LA(1)))) {
					protected_type_declarative_item();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop6;
				}
				
			} while (true);
			}
			protected_type_declarative_part_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_3);
			} else {
			  throw ex;
			}
		}
		returnAST = protected_type_declarative_part_AST;
	}
	
	public final void name() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST name_AST = null;
		
		try {      // for error handling
			{
			{
			identifier();
			astFactory.addASTChild(currentAST, returnAST);
			}
			{
			_loop454:
			do {
				if ((_tokenSet_4.member(LA(1))) && (_tokenSet_5.member(LA(2))) && (_tokenSet_6.member(LA(3)))) {
					{
					switch ( LA(1)) {
					case DOT:
					{
						name_dot();
						astFactory.addASTChild(currentAST, returnAST);
						break;
					}
					case APOSTROPHE:
					case LBRACKET:
					{
						name_apostrophe_or_aggregate();
						astFactory.addASTChild(currentAST, returnAST);
						break;
					}
					case LPAREN:
					{
						name_expression();
						astFactory.addASTChild(currentAST, returnAST);
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
				}
				else {
					break _loop454;
				}
				
			} while (true);
			}
			}
			if ( inputState.guessing==0 ) {
				name_AST = (AST)currentAST.root;
				name_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(NAME,"Name")).add(name_AST));
				currentAST.root = name_AST;
				currentAST.child = name_AST!=null &&name_AST.getFirstChild()!=null ?
					name_AST.getFirstChild() : name_AST;
				currentAST.advanceChildToEnd();
			}
			name_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_7);
			} else {
			  throw ex;
			}
		}
		returnAST = name_AST;
	}
	
	public final void protected_type_declarative_item() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST protected_type_declarative_item_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case PROCEDURE:
			case FUNCTION:
			case PURE:
			case IMPURE:
			{
				subprogram_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				protected_type_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case ATTRIBUTE:
			{
				attribute_specification();
				astFactory.addASTChild(currentAST, returnAST);
				protected_type_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case USE:
			case CONTEXT:
			{
				use_clause();
				astFactory.addASTChild(currentAST, returnAST);
				protected_type_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_0);
			} else {
			  throw ex;
			}
		}
		returnAST = protected_type_declarative_item_AST;
	}
	
	public final void subprogram_declaration() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST subprogram_declaration_AST = null;
		
		try {      // for error handling
			subprogram_specification();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp9_AST = null;
			tmp9_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp9_AST);
			match(SEMI);
			if ( inputState.guessing==0 ) {
				subprogram_declaration_AST = (AST)currentAST.root;
				subprogram_declaration_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(SUBPROGRAMDECLARATION,"SubprogramDeclaration")).add(subprogram_declaration_AST));
				currentAST.root = subprogram_declaration_AST;
				currentAST.child = subprogram_declaration_AST!=null &&subprogram_declaration_AST.getFirstChild()!=null ?
					subprogram_declaration_AST.getFirstChild() : subprogram_declaration_AST;
				currentAST.advanceChildToEnd();
			}
			subprogram_declaration_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_8);
			} else {
			  throw ex;
			}
		}
		returnAST = subprogram_declaration_AST;
	}
	
	public final void attribute_specification() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST attribute_specification_AST = null;
		
		try {      // for error handling
			AST tmp10_AST = null;
			tmp10_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp10_AST);
			match(ATTRIBUTE);
			attribute_designator();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp11_AST = null;
			tmp11_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp11_AST);
			match(OF);
			entity_specification();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp12_AST = null;
			tmp12_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp12_AST);
			match(IS);
			expression();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp13_AST = null;
			tmp13_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp13_AST);
			match(SEMI);
			if ( inputState.guessing==0 ) {
				attribute_specification_AST = (AST)currentAST.root;
				attribute_specification_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(ATTRIBUTESPECIFICATION,"attribute_specification")).add(attribute_specification_AST));
				currentAST.root = attribute_specification_AST;
				currentAST.child = attribute_specification_AST!=null &&attribute_specification_AST.getFirstChild()!=null ?
					attribute_specification_AST.getFirstChild() : attribute_specification_AST;
				currentAST.advanceChildToEnd();
			}
			attribute_specification_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_8);
			} else {
			  throw ex;
			}
		}
		returnAST = attribute_specification_AST;
	}
	
	public final void use_clause() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST use_clause_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case USE:
			{
				AST tmp14_AST = null;
				tmp14_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp14_AST);
				match(USE);
				break;
			}
			case CONTEXT:
			{
				AST tmp15_AST = null;
				tmp15_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp15_AST);
				match(CONTEXT);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			name();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop704:
			do {
				if ((LA(1)==COMMA)) {
					AST tmp16_AST = null;
					tmp16_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp16_AST);
					match(COMMA);
					name();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop704;
				}
				
			} while (true);
			}
			AST tmp17_AST = null;
			tmp17_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp17_AST);
			match(SEMI);
			if ( inputState.guessing==0 ) {
				use_clause_AST = (AST)currentAST.root;
				use_clause_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(USECLAUSE,"Use_Clause")).add(use_clause_AST));
				currentAST.root = use_clause_AST;
				currentAST.child = use_clause_AST!=null &&use_clause_AST.getFirstChild()!=null ?
					use_clause_AST.getFirstChild() : use_clause_AST;
				currentAST.advanceChildToEnd();
			}
			use_clause_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_9);
			} else {
			  throw ex;
			}
		}
		returnAST = use_clause_AST;
	}
	
	public final void protected_type_body_declarative_part() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST protected_type_body_declarative_part_AST = null;
		
		try {      // for error handling
			{
			_loop12:
			do {
				if ((_tokenSet_10.member(LA(1)))) {
					protected_type_body_declarative_item();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop12;
				}
				
			} while (true);
			}
			protected_type_body_declarative_part_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_3);
			} else {
			  throw ex;
			}
		}
		returnAST = protected_type_body_declarative_part_AST;
	}
	
	public final void protected_type_body_declarative_item() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST protected_type_body_declarative_item_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case TYPE:
			{
				type_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				protected_type_body_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case SUBTYPE:
			{
				subtype_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				protected_type_body_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case CONSTANT:
			{
				constant_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				protected_type_body_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case VARIABLE:
			case SHARED:
			{
				variable_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				protected_type_body_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case FILE:
			{
				file_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				protected_type_body_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case ALIAS:
			{
				alias_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				protected_type_body_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case USE:
			case CONTEXT:
			{
				use_clause();
				astFactory.addASTChild(currentAST, returnAST);
				protected_type_body_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			default:
				boolean synPredMatched15 = false;
				if (((_tokenSet_11.member(LA(1))) && (_tokenSet_12.member(LA(2))) && (_tokenSet_13.member(LA(3))))) {
					int _m15 = mark();
					synPredMatched15 = true;
					inputState.guessing++;
					try {
						{
						subprogram_declaration();
						}
					}
					catch (RecognitionException pe) {
						synPredMatched15 = false;
					}
					rewind(_m15);
inputState.guessing--;
				}
				if ( synPredMatched15 ) {
					subprogram_declaration();
					astFactory.addASTChild(currentAST, returnAST);
					protected_type_body_declarative_item_AST = (AST)currentAST.root;
				}
				else if ((_tokenSet_11.member(LA(1))) && (_tokenSet_12.member(LA(2))) && (_tokenSet_14.member(LA(3)))) {
					subprogram_body();
					astFactory.addASTChild(currentAST, returnAST);
					protected_type_body_declarative_item_AST = (AST)currentAST.root;
				}
				else if ((LA(1)==PACKAGE) && ((LA(2) >= BASIC_IDENTIFIER && LA(2) <= REFERENCE))) {
					package_declaration();
					astFactory.addASTChild(currentAST, returnAST);
					protected_type_body_declarative_item_AST = (AST)currentAST.root;
				}
				else if ((LA(1)==PACKAGE) && (LA(2)==BODY)) {
					package_body();
					astFactory.addASTChild(currentAST, returnAST);
					protected_type_body_declarative_item_AST = (AST)currentAST.root;
				}
				else if ((LA(1)==ATTRIBUTE) && ((LA(2) >= BASIC_IDENTIFIER && LA(2) <= REFERENCE)) && (LA(3)==COLON)) {
					attribute_declaration();
					astFactory.addASTChild(currentAST, returnAST);
					protected_type_body_declarative_item_AST = (AST)currentAST.root;
				}
				else if ((LA(1)==ATTRIBUTE) && (_tokenSet_15.member(LA(2))) && (LA(3)==OF)) {
					attribute_specification();
					astFactory.addASTChild(currentAST, returnAST);
					protected_type_body_declarative_item_AST = (AST)currentAST.root;
				}
				else if ((LA(1)==GROUP) && ((LA(2) >= BASIC_IDENTIFIER && LA(2) <= REFERENCE)) && (LA(3)==IS)) {
					group_template_declaration();
					astFactory.addASTChild(currentAST, returnAST);
					protected_type_body_declarative_item_AST = (AST)currentAST.root;
				}
				else if ((LA(1)==GROUP) && ((LA(2) >= BASIC_IDENTIFIER && LA(2) <= REFERENCE)) && (LA(3)==COLON)) {
					group_declaration();
					astFactory.addASTChild(currentAST, returnAST);
					protected_type_body_declarative_item_AST = (AST)currentAST.root;
				}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_16);
			} else {
			  throw ex;
			}
		}
		returnAST = protected_type_body_declarative_item_AST;
	}
	
	public final void subprogram_body() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST subprogram_body_AST = null;
		
		try {      // for error handling
			subprogram_specification();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp18_AST = null;
			tmp18_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp18_AST);
			match(IS);
			subprogram_declarative_part();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp19_AST = null;
			tmp19_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp19_AST);
			match(BEGIN);
			subprogram_statement_part();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp20_AST = null;
			tmp20_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp20_AST);
			match(END);
			{
			switch ( LA(1)) {
			case PROCEDURE:
			case FUNCTION:
			{
				subprogram_kind();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case SEMI:
			case STRING_LITERAL:
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			switch ( LA(1)) {
			case STRING_LITERAL:
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			{
				designator();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case SEMI:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			AST tmp21_AST = null;
			tmp21_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp21_AST);
			match(SEMI);
			if ( inputState.guessing==0 ) {
				subprogram_body_AST = (AST)currentAST.root;
				subprogram_body_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(SUBPROGRAMDBODY,"subprogram_body")).add(subprogram_body_AST));
				currentAST.root = subprogram_body_AST;
				currentAST.child = subprogram_body_AST!=null &&subprogram_body_AST.getFirstChild()!=null ?
					subprogram_body_AST.getFirstChild() : subprogram_body_AST;
				currentAST.advanceChildToEnd();
			}
			subprogram_body_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_8);
			} else {
			  throw ex;
			}
		}
		returnAST = subprogram_body_AST;
	}
	
	public final void package_declaration() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST package_declaration_AST = null;
		
		try {      // for error handling
			AST tmp22_AST = null;
			tmp22_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp22_AST);
			match(PACKAGE);
			identifier();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp23_AST = null;
			tmp23_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp23_AST);
			match(IS);
			package_declarative_part();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp24_AST = null;
			tmp24_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp24_AST);
			match(END);
			{
			switch ( LA(1)) {
			case PACKAGE:
			{
				AST tmp25_AST = null;
				tmp25_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp25_AST);
				match(PACKAGE);
				break;
			}
			case SEMI:
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			switch ( LA(1)) {
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			{
				identifier();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case SEMI:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			AST tmp26_AST = null;
			tmp26_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp26_AST);
			match(SEMI);
			if ( inputState.guessing==0 ) {
				package_declaration_AST = (AST)currentAST.root;
				package_declaration_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(PACKAGEDECLARATION,"PackageDeclaration")).add(package_declaration_AST));
				currentAST.root = package_declaration_AST;
				currentAST.child = package_declaration_AST!=null &&package_declaration_AST.getFirstChild()!=null ?
					package_declaration_AST.getFirstChild() : package_declaration_AST;
				currentAST.advanceChildToEnd();
			}
			package_declaration_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_17);
			} else {
			  throw ex;
			}
		}
		returnAST = package_declaration_AST;
	}
	
	public final void package_body() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST package_body_AST = null;
		
		try {      // for error handling
			AST tmp27_AST = null;
			tmp27_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp27_AST);
			match(PACKAGE);
			AST tmp28_AST = null;
			tmp28_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp28_AST);
			match(BODY);
			identifier();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp29_AST = null;
			tmp29_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp29_AST);
			match(IS);
			package_body_declarative_part();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp30_AST = null;
			tmp30_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp30_AST);
			match(END);
			{
			switch ( LA(1)) {
			case PACKAGE:
			{
				AST tmp31_AST = null;
				tmp31_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp31_AST);
				match(PACKAGE);
				AST tmp32_AST = null;
				tmp32_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp32_AST);
				match(BODY);
				break;
			}
			case SEMI:
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			switch ( LA(1)) {
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			{
				identifier();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case SEMI:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			AST tmp33_AST = null;
			tmp33_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp33_AST);
			match(SEMI);
			if ( inputState.guessing==0 ) {
				package_body_AST = (AST)currentAST.root;
				package_body_AST= (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(PACKAGEBODY,"PackageBody")).add(package_body_AST));
				currentAST.root = package_body_AST;
				currentAST.child = package_body_AST!=null &&package_body_AST.getFirstChild()!=null ?
					package_body_AST.getFirstChild() : package_body_AST;
				currentAST.advanceChildToEnd();
			}
			package_body_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_17);
			} else {
			  throw ex;
			}
		}
		returnAST = package_body_AST;
	}
	
	public final void type_declaration() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST type_declaration_AST = null;
		
		try {      // for error handling
			AST tmp34_AST = null;
			tmp34_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp34_AST);
			match(TYPE);
			identifier();
			astFactory.addASTChild(currentAST, returnAST);
			{
			switch ( LA(1)) {
			case IS:
			{
				AST tmp35_AST = null;
				tmp35_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp35_AST);
				match(IS);
				type_definition();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case SEMI:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			AST tmp36_AST = null;
			tmp36_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp36_AST);
			match(SEMI);
			if ( inputState.guessing==0 ) {
				type_declaration_AST = (AST)currentAST.root;
				type_declaration_AST= (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(TYPEDECLARATION,"TypeDeclaration")).add(type_declaration_AST));
				currentAST.root = type_declaration_AST;
				currentAST.child = type_declaration_AST!=null &&type_declaration_AST.getFirstChild()!=null ?
					type_declaration_AST.getFirstChild() : type_declaration_AST;
				currentAST.advanceChildToEnd();
			}
			type_declaration_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_8);
			} else {
			  throw ex;
			}
		}
		returnAST = type_declaration_AST;
	}
	
	public final void subtype_declaration() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST subtype_declaration_AST = null;
		
		try {      // for error handling
			AST tmp37_AST = null;
			tmp37_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp37_AST);
			match(SUBTYPE);
			identifier();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp38_AST = null;
			tmp38_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp38_AST);
			match(IS);
			subtype_indication();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp39_AST = null;
			tmp39_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp39_AST);
			match(SEMI);
			if ( inputState.guessing==0 ) {
				subtype_declaration_AST = (AST)currentAST.root;
				subtype_declaration_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(SUBTYPEDECLARATION,"SubtypeDeclaration")).add(subtype_declaration_AST));
				currentAST.root = subtype_declaration_AST;
				currentAST.child = subtype_declaration_AST!=null &&subtype_declaration_AST.getFirstChild()!=null ?
					subtype_declaration_AST.getFirstChild() : subtype_declaration_AST;
				currentAST.advanceChildToEnd();
			}
			subtype_declaration_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_8);
			} else {
			  throw ex;
			}
		}
		returnAST = subtype_declaration_AST;
	}
	
	public final void constant_declaration() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST constant_declaration_AST = null;
		
		try {      // for error handling
			AST tmp40_AST = null;
			tmp40_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp40_AST);
			match(CONSTANT);
			identifier_list();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp41_AST = null;
			tmp41_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp41_AST);
			match(COLON);
			subtype_indication();
			astFactory.addASTChild(currentAST, returnAST);
			var_assign();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp42_AST = null;
			tmp42_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp42_AST);
			match(SEMI);
			if ( inputState.guessing==0 ) {
				constant_declaration_AST = (AST)currentAST.root;
				constant_declaration_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(CONSTANTDECLARATION,"ConstantDeclaration")).add(constant_declaration_AST));
				currentAST.root = constant_declaration_AST;
				currentAST.child = constant_declaration_AST!=null &&constant_declaration_AST.getFirstChild()!=null ?
					constant_declaration_AST.getFirstChild() : constant_declaration_AST;
				currentAST.advanceChildToEnd();
			}
			constant_declaration_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_8);
			} else {
			  throw ex;
			}
		}
		returnAST = constant_declaration_AST;
	}
	
	public final void variable_declaration() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST variable_declaration_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case SHARED:
			{
				AST tmp43_AST = null;
				tmp43_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp43_AST);
				match(SHARED);
				break;
			}
			case VARIABLE:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			AST tmp44_AST = null;
			tmp44_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp44_AST);
			match(VARIABLE);
			identifier_list();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp45_AST = null;
			tmp45_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp45_AST);
			match(COLON);
			subtype_indication();
			astFactory.addASTChild(currentAST, returnAST);
			var_assign();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp46_AST = null;
			tmp46_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp46_AST);
			match(SEMI);
			if ( inputState.guessing==0 ) {
				variable_declaration_AST = (AST)currentAST.root;
				variable_declaration_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(VARIABLEDECLARATION,"VariableDeclaration")).add(variable_declaration_AST));
				currentAST.root = variable_declaration_AST;
				currentAST.child = variable_declaration_AST!=null &&variable_declaration_AST.getFirstChild()!=null ?
					variable_declaration_AST.getFirstChild() : variable_declaration_AST;
				currentAST.advanceChildToEnd();
			}
			variable_declaration_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_8);
			} else {
			  throw ex;
			}
		}
		returnAST = variable_declaration_AST;
	}
	
	public final void file_declaration() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST file_declaration_AST = null;
		
		try {      // for error handling
			AST tmp47_AST = null;
			tmp47_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp47_AST);
			match(FILE);
			identifier_list();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp48_AST = null;
			tmp48_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp48_AST);
			match(COLON);
			subtype_indication();
			astFactory.addASTChild(currentAST, returnAST);
			{
			switch ( LA(1)) {
			case OPEN:
			case IS:
			{
				file_open_information();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case SEMI:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			AST tmp49_AST = null;
			tmp49_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp49_AST);
			match(SEMI);
			if ( inputState.guessing==0 ) {
				file_declaration_AST = (AST)currentAST.root;
				file_declaration_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(FILEDECLARATION,"FileDeclaration")).add(file_declaration_AST));
				currentAST.root = file_declaration_AST;
				currentAST.child = file_declaration_AST!=null &&file_declaration_AST.getFirstChild()!=null ?
					file_declaration_AST.getFirstChild() : file_declaration_AST;
				currentAST.advanceChildToEnd();
			}
			file_declaration_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_8);
			} else {
			  throw ex;
			}
		}
		returnAST = file_declaration_AST;
	}
	
	public final void alias_declaration() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST alias_declaration_AST = null;
		
		try {      // for error handling
			AST tmp50_AST = null;
			tmp50_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp50_AST);
			match(ALIAS);
			alias_designator();
			astFactory.addASTChild(currentAST, returnAST);
			alias_type();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp51_AST = null;
			tmp51_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp51_AST);
			match(IS);
			name_without_attribute();
			astFactory.addASTChild(currentAST, returnAST);
			{
			switch ( LA(1)) {
			case LBRACKET:
			{
				signature();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case SEMI:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			AST tmp52_AST = null;
			tmp52_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp52_AST);
			match(SEMI);
			if ( inputState.guessing==0 ) {
				alias_declaration_AST = (AST)currentAST.root;
				alias_declaration_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(ALIASDECLARATION,"AliaseDeclaration")).add(alias_declaration_AST));
				currentAST.root = alias_declaration_AST;
				currentAST.child = alias_declaration_AST!=null &&alias_declaration_AST.getFirstChild()!=null ?
					alias_declaration_AST.getFirstChild() : alias_declaration_AST;
				currentAST.advanceChildToEnd();
			}
			alias_declaration_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_8);
			} else {
			  throw ex;
			}
		}
		returnAST = alias_declaration_AST;
	}
	
	public final void attribute_declaration() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST attribute_declaration_AST = null;
		
		try {      // for error handling
			AST tmp53_AST = null;
			tmp53_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp53_AST);
			match(ATTRIBUTE);
			label_colon();
			astFactory.addASTChild(currentAST, returnAST);
			name();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp54_AST = null;
			tmp54_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp54_AST);
			match(SEMI);
			if ( inputState.guessing==0 ) {
				attribute_declaration_AST = (AST)currentAST.root;
				attribute_declaration_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(ATTRIBUTEDECLARATION,"attribute_declaration")).add(attribute_declaration_AST));
				currentAST.root = attribute_declaration_AST;
				currentAST.child = attribute_declaration_AST!=null &&attribute_declaration_AST.getFirstChild()!=null ?
					attribute_declaration_AST.getFirstChild() : attribute_declaration_AST;
				currentAST.advanceChildToEnd();
			}
			attribute_declaration_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_8);
			} else {
			  throw ex;
			}
		}
		returnAST = attribute_declaration_AST;
	}
	
	public final void group_template_declaration() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST group_template_declaration_AST = null;
		
		try {      // for error handling
			AST tmp55_AST = null;
			tmp55_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp55_AST);
			match(GROUP);
			identifier();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp56_AST = null;
			tmp56_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp56_AST);
			match(IS);
			AST tmp57_AST = null;
			tmp57_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp57_AST);
			match(LPAREN);
			entity_class_entry_list();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp58_AST = null;
			tmp58_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp58_AST);
			match(RPAREN);
			AST tmp59_AST = null;
			tmp59_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp59_AST);
			match(SEMI);
			group_template_declaration_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_8);
			} else {
			  throw ex;
			}
		}
		returnAST = group_template_declaration_AST;
	}
	
	public final void group_declaration() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST group_declaration_AST = null;
		
		try {      // for error handling
			AST tmp60_AST = null;
			tmp60_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp60_AST);
			match(GROUP);
			label_colon();
			astFactory.addASTChild(currentAST, returnAST);
			name();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp61_AST = null;
			tmp61_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp61_AST);
			match(LPAREN);
			group_constituent_list();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp62_AST = null;
			tmp62_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp62_AST);
			match(RPAREN);
			AST tmp63_AST = null;
			tmp63_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp63_AST);
			match(SEMI);
			group_declaration_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_8);
			} else {
			  throw ex;
			}
		}
		returnAST = group_declaration_AST;
	}
	
	public final void abstract_literal() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST abstract_literal_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case DECIMAL_LITERAL:
			{
				AST tmp64_AST = null;
				tmp64_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp64_AST);
				match(DECIMAL_LITERAL);
				abstract_literal_AST = (AST)currentAST.root;
				break;
			}
			case BASED_LITERAL:
			{
				AST tmp65_AST = null;
				tmp65_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp65_AST);
				match(BASED_LITERAL);
				abstract_literal_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_18);
			} else {
			  throw ex;
			}
		}
		returnAST = abstract_literal_AST;
	}
	
	public final void access_type_definition() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST access_type_definition_AST = null;
		
		try {      // for error handling
			AST tmp66_AST = null;
			tmp66_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp66_AST);
			match(ACCESS);
			subtype_indication();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				access_type_definition_AST = (AST)currentAST.root;
				access_type_definition_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(ACCESSTYPEDEFINITION,"AccessTypeDefinition")).add(access_type_definition_AST));
				currentAST.root = access_type_definition_AST;
				currentAST.child = access_type_definition_AST!=null &&access_type_definition_AST.getFirstChild()!=null ?
					access_type_definition_AST.getFirstChild() : access_type_definition_AST;
				currentAST.advanceChildToEnd();
			}
			access_type_definition_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_1);
			} else {
			  throw ex;
			}
		}
		returnAST = access_type_definition_AST;
	}
	
	public final void subtype_indication() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST subtype_indication_AST = null;
		
		try {      // for error handling
			subtype_first_name();
			astFactory.addASTChild(currentAST, returnAST);
			{
			switch ( LA(1)) {
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			{
				name();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case VARASGN:
			case ACROSS:
			case OPEN:
			case LPAREN:
			case RPAREN:
			case COMMA:
			case IS:
			case SEMI:
			case GENERATE:
			case REPORT:
			case SEVERITY:
			case ARROW:
			case RANGETOK:
			case THROUGH:
			case TOLERANCE:
			case FOR:
			case WHEN:
			case BAR:
			case LE:
			case ELSE:
			case INERTIAL:
			case TO:
			case DOWNTO:
			case AFTER:
			case SIGNAL:
			case UNITS:
			case DOUBLESTAR:
			case EQ:
			case NEQ:
			case LOWERTHAN:
			case GREATERTHAN:
			case GE:
			case AND:
			case OR:
			case NAND:
			case NOR:
			case XOR:
			case XNOR:
			case SLL:
			case SRL:
			case SLA:
			case SRA:
			case ROL:
			case ROR:
			case PLUS:
			case MINUS:
			case AMPERSAND:
			case MUL:
			case DIV:
			case MOD:
			case REM:
			case THEN:
			case BUS:
			case LOOP:
			case SELECT:
			case ON:
			case REGISTER:
			case SPECTRUM:
			case NOISE:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			switch ( LA(1)) {
			case LPAREN:
			case RANGETOK:
			{
				constraint();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case VARASGN:
			case ACROSS:
			case OPEN:
			case RPAREN:
			case COMMA:
			case IS:
			case SEMI:
			case GENERATE:
			case REPORT:
			case SEVERITY:
			case ARROW:
			case THROUGH:
			case TOLERANCE:
			case FOR:
			case WHEN:
			case BAR:
			case LE:
			case ELSE:
			case INERTIAL:
			case TO:
			case DOWNTO:
			case AFTER:
			case SIGNAL:
			case UNITS:
			case DOUBLESTAR:
			case EQ:
			case NEQ:
			case LOWERTHAN:
			case GREATERTHAN:
			case GE:
			case AND:
			case OR:
			case NAND:
			case NOR:
			case XOR:
			case XNOR:
			case SLL:
			case SRL:
			case SLA:
			case SRA:
			case ROL:
			case ROR:
			case PLUS:
			case MINUS:
			case AMPERSAND:
			case MUL:
			case DIV:
			case MOD:
			case REM:
			case THEN:
			case BUS:
			case LOOP:
			case SELECT:
			case ON:
			case REGISTER:
			case SPECTRUM:
			case NOISE:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			boolean synPredMatched678 = false;
			if (((LA(1)==TOLERANCE) && (_tokenSet_19.member(LA(2))) && (_tokenSet_20.member(LA(3))))) {
				int _m678 = mark();
				synPredMatched678 = true;
				inputState.guessing++;
				try {
					{
					match(TOLERANCE);
					}
				}
				catch (RecognitionException pe) {
					synPredMatched678 = false;
				}
				rewind(_m678);
inputState.guessing--;
			}
			if ( synPredMatched678 ) {
				tolerance_aspect();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else if ((_tokenSet_21.member(LA(1))) && (_tokenSet_22.member(LA(2))) && (_tokenSet_23.member(LA(3)))) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			if ( inputState.guessing==0 ) {
				subtype_indication_AST = (AST)currentAST.root;
				subtype_indication_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(SUBTYPEINDICATION,"SubtypeIndication")).add(subtype_indication_AST));
				currentAST.root = subtype_indication_AST;
				currentAST.child = subtype_indication_AST!=null &&subtype_indication_AST.getFirstChild()!=null ?
					subtype_indication_AST.getFirstChild() : subtype_indication_AST;
				currentAST.advanceChildToEnd();
			}
			subtype_indication_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_21);
			} else {
			  throw ex;
			}
		}
		returnAST = subtype_indication_AST;
	}
	
	public final void across_aspect() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST across_aspect_AST = null;
		
		try {      // for error handling
			identifier_list();
			astFactory.addASTChild(currentAST, returnAST);
			{
			switch ( LA(1)) {
			case TOLERANCE:
			{
				tolerance_aspect();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case VARASGN:
			case ACROSS:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			switch ( LA(1)) {
			case VARASGN:
			{
				AST tmp67_AST = null;
				tmp67_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp67_AST);
				match(VARASGN);
				expression();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case ACROSS:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			AST tmp68_AST = null;
			tmp68_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp68_AST);
			match(ACROSS);
			across_aspect_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_24);
			} else {
			  throw ex;
			}
		}
		returnAST = across_aspect_AST;
	}
	
	public final void identifier_list() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST identifier_list_AST = null;
		
		try {      // for error handling
			identifier();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop341:
			do {
				if ((LA(1)==COMMA)) {
					AST tmp69_AST = null;
					tmp69_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp69_AST);
					match(COMMA);
					identifier();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop341;
				}
				
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				identifier_list_AST = (AST)currentAST.root;
				identifier_list_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(IDENTIFIERLIST,"IdentifierList")).add(identifier_list_AST));
				currentAST.root = identifier_list_AST;
				currentAST.child = identifier_list_AST!=null &&identifier_list_AST.getFirstChild()!=null ?
					identifier_list_AST.getFirstChild() : identifier_list_AST;
				currentAST.advanceChildToEnd();
			}
			identifier_list_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_25);
			} else {
			  throw ex;
			}
		}
		returnAST = identifier_list_AST;
	}
	
	public final void tolerance_aspect() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST tolerance_aspect_AST = null;
		
		try {      // for error handling
			AST tmp70_AST = null;
			tmp70_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp70_AST);
			match(TOLERANCE);
			expression();
			astFactory.addASTChild(currentAST, returnAST);
			tolerance_aspect_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_21);
			} else {
			  throw ex;
			}
		}
		returnAST = tolerance_aspect_AST;
	}
	
	public final void expression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST expression_AST = null;
		
		try {      // for error handling
			unary_op();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop291:
			do {
				if ((_tokenSet_26.member(LA(1))) && (_tokenSet_19.member(LA(2))) && (_tokenSet_20.member(LA(3)))) {
					combined_operator();
					astFactory.addASTChild(currentAST, returnAST);
					unary_op();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop291;
				}
				
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				expression_AST = (AST)currentAST.root;
				expression_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(EXPRESSION,"Expression")).add(expression_AST));
				currentAST.root = expression_AST;
				currentAST.child = expression_AST!=null &&expression_AST.getFirstChild()!=null ?
					expression_AST.getFirstChild() : expression_AST;
				currentAST.advanceChildToEnd();
			}
			expression_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_21);
			} else {
			  throw ex;
			}
		}
		returnAST = expression_AST;
	}
	
	public final void actual_designator() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST actual_designator_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case DECIMAL_LITERAL:
			case BASED_LITERAL:
			case LPAREN:
			case CHARACTER_LITERAL:
			case STRING_LITERAL:
			case NEW:
			case PLUS:
			case MINUS:
			case ABS:
			case NOT:
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			case NULLTOK:
			case BIT_STRING_LITERAL:
			{
				expression();
				astFactory.addASTChild(currentAST, returnAST);
				actual_designator_AST = (AST)currentAST.root;
				break;
			}
			case OPEN:
			{
				AST tmp71_AST = null;
				tmp71_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp71_AST);
				match(OPEN);
				actual_designator_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_27);
			} else {
			  throw ex;
			}
		}
		returnAST = actual_designator_AST;
	}
	
	public final void actual_parameter_part() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST actual_parameter_part_AST = null;
		
		try {      // for error handling
			association_list();
			astFactory.addASTChild(currentAST, returnAST);
			actual_parameter_part_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_28);
			} else {
			  throw ex;
			}
		}
		returnAST = actual_parameter_part_AST;
	}
	
	public final void association_list() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST association_list_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case DECIMAL_LITERAL:
			case BASED_LITERAL:
			case OPEN:
			case LPAREN:
			case CHARACTER_LITERAL:
			case STRING_LITERAL:
			case NEW:
			case PLUS:
			case MINUS:
			case ABS:
			case NOT:
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			case NULLTOK:
			case BIT_STRING_LITERAL:
			{
				association_element();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case RPAREN:
			case COMMA:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			_loop97:
			do {
				if ((LA(1)==COMMA)) {
					AST tmp72_AST = null;
					tmp72_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp72_AST);
					match(COMMA);
					association_element();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop97;
				}
				
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				association_list_AST = (AST)currentAST.root;
				association_list_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(ASSOCIATIONLIST,"AssociationList")).add(association_list_AST));
				currentAST.root = association_list_AST;
				currentAST.child = association_list_AST!=null &&association_list_AST.getFirstChild()!=null ?
					association_list_AST.getFirstChild() : association_list_AST;
				currentAST.advanceChildToEnd();
			}
			association_list_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_28);
			} else {
			  throw ex;
			}
		}
		returnAST = association_list_AST;
	}
	
	public final void actual_part() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST actual_part_AST = null;
		
		try {      // for error handling
			boolean synPredMatched25 = false;
			if ((((LA(1) >= BASIC_IDENTIFIER && LA(1) <= REFERENCE)) && (_tokenSet_4.member(LA(2))) && (_tokenSet_5.member(LA(3))))) {
				int _m25 = mark();
				synPredMatched25 = true;
				inputState.guessing++;
				try {
					{
					name();
					match(LPAREN);
					}
				}
				catch (RecognitionException pe) {
					synPredMatched25 = false;
				}
				rewind(_m25);
inputState.guessing--;
			}
			if ( synPredMatched25 ) {
				name();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp73_AST = null;
				tmp73_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp73_AST);
				match(LPAREN);
				actual_designator();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp74_AST = null;
				tmp74_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp74_AST);
				match(RPAREN);
				actual_part_AST = (AST)currentAST.root;
			}
			else if ((_tokenSet_29.member(LA(1))) && (_tokenSet_30.member(LA(2))) && (_tokenSet_31.member(LA(3)))) {
				actual_designator();
				astFactory.addASTChild(currentAST, returnAST);
				actual_part_AST = (AST)currentAST.root;
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_27);
			} else {
			  throw ex;
			}
		}
		returnAST = actual_part_AST;
	}
	
	public final void aggregate() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST aggregate_AST = null;
		
		try {      // for error handling
			AST tmp75_AST = null;
			tmp75_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp75_AST);
			match(LPAREN);
			element_association();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop28:
			do {
				if ((LA(1)==COMMA)) {
					AST tmp76_AST = null;
					tmp76_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp76_AST);
					match(COMMA);
					element_association();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop28;
				}
				
			} while (true);
			}
			AST tmp77_AST = null;
			tmp77_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp77_AST);
			match(RPAREN);
			if ( inputState.guessing==0 ) {
				aggregate_AST = (AST)currentAST.root;
				aggregate_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(AGGREGATE,"Aggregate")).add(aggregate_AST));
				currentAST.root = aggregate_AST;
				currentAST.child = aggregate_AST!=null &&aggregate_AST.getFirstChild()!=null ?
					aggregate_AST.getFirstChild() : aggregate_AST;
				currentAST.advanceChildToEnd();
			}
			aggregate_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_32);
			} else {
			  throw ex;
			}
		}
		returnAST = aggregate_AST;
	}
	
	public final void element_association() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST element_association_AST = null;
		
		try {      // for error handling
			{
			boolean synPredMatched232 = false;
			if (((_tokenSet_33.member(LA(1))) && (_tokenSet_34.member(LA(2))) && (_tokenSet_35.member(LA(3))))) {
				int _m232 = mark();
				synPredMatched232 = true;
				inputState.guessing++;
				try {
					{
					choices();
					match(ARROW);
					}
				}
				catch (RecognitionException pe) {
					synPredMatched232 = false;
				}
				rewind(_m232);
inputState.guessing--;
			}
			if ( synPredMatched232 ) {
				choices();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp78_AST = null;
				tmp78_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp78_AST);
				match(ARROW);
			}
			else if ((_tokenSet_19.member(LA(1))) && (_tokenSet_30.member(LA(2))) && (_tokenSet_36.member(LA(3)))) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			expression();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				element_association_AST = (AST)currentAST.root;
				element_association_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(ELEMENTASSOCIATION,"ElementAssociation")).add(element_association_AST));
				currentAST.root = element_association_AST;
				currentAST.child = element_association_AST!=null &&element_association_AST.getFirstChild()!=null ?
					element_association_AST.getFirstChild() : element_association_AST;
				currentAST.advanceChildToEnd();
			}
			element_association_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_27);
			} else {
			  throw ex;
			}
		}
		returnAST = element_association_AST;
	}
	
	public final void alias_type() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST alias_type_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case COLON:
			{
				AST tmp79_AST = null;
				tmp79_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp79_AST);
				match(COLON);
				alias_indication();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case IS:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				alias_type_AST = (AST)currentAST.root;
				alias_type_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(ALIASTYPE,"AliasType")).add(alias_type_AST));
				currentAST.root = alias_type_AST;
				currentAST.child = alias_type_AST!=null &&alias_type_AST.getFirstChild()!=null ?
					alias_type_AST.getFirstChild() : alias_type_AST;
				currentAST.advanceChildToEnd();
			}
			alias_type_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_37);
			} else {
			  throw ex;
			}
		}
		returnAST = alias_type_AST;
	}
	
	public final void alias_indication() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST alias_indication_AST = null;
		
		try {      // for error handling
			boolean synPredMatched36 = false;
			if ((((LA(1) >= BASIC_IDENTIFIER && LA(1) <= REFERENCE)) && (_tokenSet_38.member(LA(2))) && (_tokenSet_5.member(LA(3))))) {
				int _m36 = mark();
				synPredMatched36 = true;
				inputState.guessing++;
				try {
					{
					subnature_indication();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched36 = false;
				}
				rewind(_m36);
inputState.guessing--;
			}
			if ( synPredMatched36 ) {
				subnature_indication();
				astFactory.addASTChild(currentAST, returnAST);
				alias_indication_AST = (AST)currentAST.root;
			}
			else if ((_tokenSet_39.member(LA(1))) && (_tokenSet_40.member(LA(2))) && (_tokenSet_41.member(LA(3)))) {
				subtype_indication();
				astFactory.addASTChild(currentAST, returnAST);
				alias_indication_AST = (AST)currentAST.root;
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_37);
			} else {
			  throw ex;
			}
		}
		returnAST = alias_indication_AST;
	}
	
	public final void alias_designator() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST alias_designator_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			{
				identifier();
				astFactory.addASTChild(currentAST, returnAST);
				alias_designator_AST = (AST)currentAST.root;
				break;
			}
			case CHARACTER_LITERAL:
			{
				AST tmp80_AST = null;
				tmp80_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp80_AST);
				match(CHARACTER_LITERAL);
				alias_designator_AST = (AST)currentAST.root;
				break;
			}
			case STRING_LITERAL:
			{
				AST tmp81_AST = null;
				tmp81_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp81_AST);
				match(STRING_LITERAL);
				alias_designator_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_42);
			} else {
			  throw ex;
			}
		}
		returnAST = alias_designator_AST;
	}
	
	public final void name_without_attribute() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST name_without_attribute_AST = null;
		
		try {      // for error handling
			{
			{
			identifier();
			astFactory.addASTChild(currentAST, returnAST);
			}
			{
			_loop442:
			do {
				if ((LA(1)==LPAREN||LA(1)==APOSTROPHE||LA(1)==DOT)) {
					{
					switch ( LA(1)) {
					case DOT:
					{
						name_dot();
						astFactory.addASTChild(currentAST, returnAST);
						break;
					}
					case APOSTROPHE:
					{
						name_apostrophy();
						astFactory.addASTChild(currentAST, returnAST);
						break;
					}
					default:
						boolean synPredMatched439 = false;
						if (((LA(1)==LPAREN) && (_tokenSet_29.member(LA(2))) && (_tokenSet_43.member(LA(3))))) {
							int _m439 = mark();
							synPredMatched439 = true;
							inputState.guessing++;
							try {
								{
								match(LPAREN);
								expression();
								{
								_loop438:
								do {
									if ((LA(1)==COMMA)) {
										match(COMMA);
										expression();
									}
									else {
										break _loop438;
									}
									
								} while (true);
								}
								match(RPAREN);
								}
							}
							catch (RecognitionException pe) {
								synPredMatched439 = false;
							}
							rewind(_m439);
inputState.guessing--;
						}
						if ( synPredMatched439 ) {
							name_expression();
							astFactory.addASTChild(currentAST, returnAST);
						}
						else {
							boolean synPredMatched441 = false;
							if (((LA(1)==LPAREN) && (_tokenSet_44.member(LA(2))) && (_tokenSet_45.member(LA(3))))) {
								int _m441 = mark();
								synPredMatched441 = true;
								inputState.guessing++;
								try {
									{
									match(LPAREN);
									actual_parameter_part();
									match(RPAREN);
									}
								}
								catch (RecognitionException pe) {
									synPredMatched441 = false;
								}
								rewind(_m441);
inputState.guessing--;
							}
							if ( synPredMatched441 ) {
								name_parameter_part();
								astFactory.addASTChild(currentAST, returnAST);
							}
							else if ((LA(1)==LPAREN) && (_tokenSet_19.member(LA(2))) && (_tokenSet_46.member(LA(3)))) {
								name_range();
								astFactory.addASTChild(currentAST, returnAST);
							}
						else {
							throw new NoViableAltException(LT(1), getFilename());
						}
						}}
						}
					}
					else {
						break _loop442;
					}
					
				} while (true);
				}
				}
				if ( inputState.guessing==0 ) {
					name_without_attribute_AST = (AST)currentAST.root;
					name_without_attribute_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(NAME,"Name")).add(name_without_attribute_AST));
					currentAST.root = name_without_attribute_AST;
					currentAST.child = name_without_attribute_AST!=null &&name_without_attribute_AST.getFirstChild()!=null ?
						name_without_attribute_AST.getFirstChild() : name_without_attribute_AST;
					currentAST.advanceChildToEnd();
				}
				name_without_attribute_AST = (AST)currentAST.root;
			}
			catch (RecognitionException ex) {
				if (inputState.guessing==0) {
					reportError(ex);
					recover(ex,_tokenSet_47);
				} else {
				  throw ex;
				}
			}
			returnAST = name_without_attribute_AST;
		}
		
	public final void signature() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST signature_AST = null;
		
		try {      // for error handling
			AST tmp82_AST = null;
			tmp82_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp82_AST);
			match(LBRACKET);
			{
			switch ( LA(1)) {
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			{
				name();
				astFactory.addASTChild(currentAST, returnAST);
				{
				_loop643:
				do {
					if ((LA(1)==COMMA)) {
						AST tmp83_AST = null;
						tmp83_AST = astFactory.create(LT(1));
						astFactory.addASTChild(currentAST, tmp83_AST);
						match(COMMA);
						name();
						astFactory.addASTChild(currentAST, returnAST);
					}
					else {
						break _loop643;
					}
					
				} while (true);
				}
				break;
			}
			case RETURN:
			case RBRACKET:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			switch ( LA(1)) {
			case RETURN:
			{
				AST tmp84_AST = null;
				tmp84_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp84_AST);
				match(RETURN);
				name();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case RBRACKET:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			AST tmp85_AST = null;
			tmp85_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp85_AST);
			match(RBRACKET);
			signature_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_48);
			} else {
			  throw ex;
			}
		}
		returnAST = signature_AST;
	}
	
	public final void identifier() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST identifier_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case BASIC_IDENTIFIER:
			{
				AST tmp86_AST = null;
				tmp86_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp86_AST);
				match(BASIC_IDENTIFIER);
				break;
			}
			case EXTENDED_IDENTIFIER:
			{
				AST tmp87_AST = null;
				tmp87_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp87_AST);
				match(EXTENDED_IDENTIFIER);
				break;
			}
			case REFERENCE:
			{
				AST tmp88_AST = null;
				tmp88_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp88_AST);
				match(REFERENCE);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				identifier_AST = (AST)currentAST.root;
				identifier_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(IDENTIFIER,"Identifier")).add(identifier_AST));
				currentAST.root = identifier_AST;
				currentAST.child = identifier_AST!=null &&identifier_AST.getFirstChild()!=null ?
					identifier_AST.getFirstChild() : identifier_AST;
				currentAST.advanceChildToEnd();
			}
			identifier_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_49);
			} else {
			  throw ex;
			}
		}
		returnAST = identifier_AST;
	}
	
	public final void subnature_indication() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST subnature_indication_AST = null;
		
		try {      // for error handling
			name();
			astFactory.addASTChild(currentAST, returnAST);
			{
			switch ( LA(1)) {
			case LPAREN:
			{
				index_constraint();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case END:
			case RPAREN:
			case IS:
			case SEMI:
			case TOLERANCE:
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			switch ( LA(1)) {
			case TOLERANCE:
			{
				AST tmp89_AST = null;
				tmp89_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp89_AST);
				match(TOLERANCE);
				expression();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp90_AST = null;
				tmp90_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp90_AST);
				match(ACROSS);
				expression();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp91_AST = null;
				tmp91_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp91_AST);
				match(THROUGH);
				break;
			}
			case END:
			case RPAREN:
			case IS:
			case SEMI:
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			subnature_indication_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_50);
			} else {
			  throw ex;
			}
		}
		returnAST = subnature_indication_AST;
	}
	
	public final void allocator() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST allocator_AST = null;
		
		try {      // for error handling
			AST tmp92_AST = null;
			tmp92_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp92_AST);
			match(NEW);
			{
			boolean synPredMatched40 = false;
			if ((((LA(1) >= BASIC_IDENTIFIER && LA(1) <= REFERENCE)) && (_tokenSet_4.member(LA(2))) && (_tokenSet_5.member(LA(3))))) {
				int _m40 = mark();
				synPredMatched40 = true;
				inputState.guessing++;
				try {
					{
					name();
					match(APOSTROPHE);
					}
				}
				catch (RecognitionException pe) {
					synPredMatched40 = false;
				}
				rewind(_m40);
inputState.guessing--;
			}
			if ( synPredMatched40 ) {
				qualified_expression();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else if ((_tokenSet_39.member(LA(1))) && (_tokenSet_51.member(LA(2))) && (_tokenSet_52.member(LA(3)))) {
				subtype_indication();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			allocator_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_21);
			} else {
			  throw ex;
			}
		}
		returnAST = allocator_AST;
	}
	
	public final void qualified_expression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST qualified_expression_AST = null;
		
		try {      // for error handling
			name();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp93_AST = null;
			tmp93_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp93_AST);
			match(APOSTROPHE);
			{
			boolean synPredMatched550 = false;
			if (((LA(1)==LPAREN) && (_tokenSet_33.member(LA(2))) && (_tokenSet_53.member(LA(3))))) {
				int _m550 = mark();
				synPredMatched550 = true;
				inputState.guessing++;
				try {
					{
					aggregate();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched550 = false;
				}
				rewind(_m550);
inputState.guessing--;
			}
			if ( synPredMatched550 ) {
				aggregate();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else if ((LA(1)==LPAREN) && (_tokenSet_19.member(LA(2))) && (_tokenSet_54.member(LA(3)))) {
				AST tmp94_AST = null;
				tmp94_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp94_AST);
				match(LPAREN);
				expression();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp95_AST = null;
				tmp95_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp95_AST);
				match(RPAREN);
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			qualified_expression_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_21);
			} else {
			  throw ex;
			}
		}
		returnAST = qualified_expression_AST;
	}
	
	public final void architecture_body() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST architecture_body_AST = null;
		
		try {      // for error handling
			AST tmp96_AST = null;
			tmp96_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp96_AST);
			match(ARCHITECTURE);
			identifier();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp97_AST = null;
			tmp97_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp97_AST);
			match(OF);
			name();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp98_AST = null;
			tmp98_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp98_AST);
			match(IS);
			architecture_declarative_part();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp99_AST = null;
			tmp99_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp99_AST);
			match(BEGIN);
			architecture_statement_part();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp100_AST = null;
			tmp100_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp100_AST);
			match(END);
			{
			switch ( LA(1)) {
			case ARCHITECTURE:
			{
				AST tmp101_AST = null;
				tmp101_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp101_AST);
				match(ARCHITECTURE);
				break;
			}
			case SEMI:
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			switch ( LA(1)) {
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			{
				identifier();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case SEMI:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			AST tmp102_AST = null;
			tmp102_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp102_AST);
			match(SEMI);
			if ( inputState.guessing==0 ) {
				architecture_body_AST = (AST)currentAST.root;
				architecture_body_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(ARCHITECTUREBODY,"ArchitectureBody")).add(architecture_body_AST));
				currentAST.root = architecture_body_AST;
				currentAST.child = architecture_body_AST!=null &&architecture_body_AST.getFirstChild()!=null ?
					architecture_body_AST.getFirstChild() : architecture_body_AST;
				currentAST.advanceChildToEnd();
			}
			architecture_body_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_55);
			} else {
			  throw ex;
			}
		}
		returnAST = architecture_body_AST;
	}
	
	public final void architecture_declarative_part() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST architecture_declarative_part_AST = null;
		
		try {      // for error handling
			{
			_loop46:
			do {
				if ((_tokenSet_56.member(LA(1)))) {
					block_declarative_item();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop46;
				}
				
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				architecture_declarative_part_AST = (AST)currentAST.root;
				architecture_declarative_part_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(ARCHITECTUREDECLARATIVEPART,"ArchitectureDeclarativePart")).add(architecture_declarative_part_AST));
				currentAST.root = architecture_declarative_part_AST;
				currentAST.child = architecture_declarative_part_AST!=null &&architecture_declarative_part_AST.getFirstChild()!=null ?
					architecture_declarative_part_AST.getFirstChild() : architecture_declarative_part_AST;
				currentAST.advanceChildToEnd();
			}
			architecture_declarative_part_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_57);
			} else {
			  throw ex;
			}
		}
		returnAST = architecture_declarative_part_AST;
	}
	
	public final void architecture_statement_part() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST architecture_statement_part_AST = null;
		
		try {      // for error handling
			{
			_loop77:
			do {
				if ((_tokenSet_58.member(LA(1)))) {
					architecture_statement();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop77;
				}
				
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				architecture_statement_part_AST = (AST)currentAST.root;
				architecture_statement_part_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(ARCHITECTURESTATEMENTPART,"ArchitectureStatementPart")).add(architecture_statement_part_AST));
				currentAST.root = architecture_statement_part_AST;
				currentAST.child = architecture_statement_part_AST!=null &&architecture_statement_part_AST.getFirstChild()!=null ?
					architecture_statement_part_AST.getFirstChild() : architecture_statement_part_AST;
				currentAST.advanceChildToEnd();
			}
			architecture_statement_part_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_3);
			} else {
			  throw ex;
			}
		}
		returnAST = architecture_statement_part_AST;
	}
	
	public final void block_declarative_item() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST block_declarative_item_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case TYPE:
			{
				type_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				block_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case SUBTYPE:
			{
				subtype_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				block_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case CONSTANT:
			{
				constant_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				block_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case SIGNAL:
			{
				signal_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				block_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case VARIABLE:
			case SHARED:
			{
				variable_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				block_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case FILE:
			{
				file_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				block_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case ALIAS:
			{
				alias_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				block_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case COMPONENT:
			{
				component_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				block_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case FOR:
			{
				configuration_specification();
				astFactory.addASTChild(currentAST, returnAST);
				block_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case DISCONNECT:
			{
				disconnection_specification();
				astFactory.addASTChild(currentAST, returnAST);
				block_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case USE:
			case CONTEXT:
			{
				use_clause();
				astFactory.addASTChild(currentAST, returnAST);
				block_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case NATURE:
			{
				nature_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				block_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case SUBNATURE:
			{
				subnature_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				block_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case QUANTITY:
			{
				quantity_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				block_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case TERMINAL:
			{
				terminal_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				if ( inputState.guessing==0 ) {
					block_declarative_item_AST = (AST)currentAST.root;
					block_declarative_item_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(BLOCKDECLARATIVEITEM,"BlockDeclarativeItem")).add(block_declarative_item_AST));
					currentAST.root = block_declarative_item_AST;
					currentAST.child = block_declarative_item_AST!=null &&block_declarative_item_AST.getFirstChild()!=null ?
						block_declarative_item_AST.getFirstChild() : block_declarative_item_AST;
					currentAST.advanceChildToEnd();
				}
				block_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			default:
				boolean synPredMatched113 = false;
				if (((_tokenSet_11.member(LA(1))) && (_tokenSet_12.member(LA(2))) && (_tokenSet_13.member(LA(3))))) {
					int _m113 = mark();
					synPredMatched113 = true;
					inputState.guessing++;
					try {
						{
						subprogram_declaration();
						}
					}
					catch (RecognitionException pe) {
						synPredMatched113 = false;
					}
					rewind(_m113);
inputState.guessing--;
				}
				if ( synPredMatched113 ) {
					subprogram_declaration();
					astFactory.addASTChild(currentAST, returnAST);
					block_declarative_item_AST = (AST)currentAST.root;
				}
				else if ((_tokenSet_11.member(LA(1))) && (_tokenSet_12.member(LA(2))) && (_tokenSet_14.member(LA(3)))) {
					subprogram_body();
					astFactory.addASTChild(currentAST, returnAST);
					block_declarative_item_AST = (AST)currentAST.root;
				}
				else if ((LA(1)==ATTRIBUTE) && ((LA(2) >= BASIC_IDENTIFIER && LA(2) <= REFERENCE)) && (LA(3)==COLON)) {
					attribute_declaration();
					astFactory.addASTChild(currentAST, returnAST);
					block_declarative_item_AST = (AST)currentAST.root;
				}
				else if ((LA(1)==ATTRIBUTE) && (_tokenSet_15.member(LA(2))) && (LA(3)==OF)) {
					attribute_specification();
					astFactory.addASTChild(currentAST, returnAST);
					block_declarative_item_AST = (AST)currentAST.root;
				}
				else if ((LA(1)==GROUP) && ((LA(2) >= BASIC_IDENTIFIER && LA(2) <= REFERENCE)) && (LA(3)==IS)) {
					group_template_declaration();
					astFactory.addASTChild(currentAST, returnAST);
					block_declarative_item_AST = (AST)currentAST.root;
				}
				else if ((LA(1)==GROUP) && ((LA(2) >= BASIC_IDENTIFIER && LA(2) <= REFERENCE)) && (LA(3)==COLON)) {
					group_declaration();
					astFactory.addASTChild(currentAST, returnAST);
					block_declarative_item_AST = (AST)currentAST.root;
				}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_59);
			} else {
			  throw ex;
			}
		}
		returnAST = block_declarative_item_AST;
	}
	
	public final void architecture_statement() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST architecture_statement_AST = null;
		
		try {      // for error handling
			{
			if (((LA(1) >= BASIC_IDENTIFIER && LA(1) <= REFERENCE)) && (LA(2)==COLON) && (LA(3)==BLOCK)) {
				block_statement();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				boolean synPredMatched52 = false;
				if (((_tokenSet_60.member(LA(1))) && (_tokenSet_61.member(LA(2))) && (_tokenSet_62.member(LA(3))))) {
					int _m52 = mark();
					synPredMatched52 = true;
					inputState.guessing++;
					try {
						{
						{
						switch ( LA(1)) {
						case BASIC_IDENTIFIER:
						case EXTENDED_IDENTIFIER:
						case REFERENCE:
						{
							label_colon();
							break;
						}
						case POSTPONED:
						case PROCESS:
						{
							break;
						}
						default:
						{
							throw new NoViableAltException(LT(1), getFilename());
						}
						}
						}
						{
						switch ( LA(1)) {
						case POSTPONED:
						{
							match(POSTPONED);
							break;
						}
						case PROCESS:
						{
							break;
						}
						default:
						{
							throw new NoViableAltException(LT(1), getFilename());
						}
						}
						}
						match(PROCESS);
						}
					}
					catch (RecognitionException pe) {
						synPredMatched52 = false;
					}
					rewind(_m52);
inputState.guessing--;
				}
				if ( synPredMatched52 ) {
					process_statement();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					boolean synPredMatched56 = false;
					if (((_tokenSet_63.member(LA(1))) && (_tokenSet_64.member(LA(2))) && (_tokenSet_65.member(LA(3))))) {
						int _m56 = mark();
						synPredMatched56 = true;
						inputState.guessing++;
						try {
							{
							{
							if (((LA(1) >= BASIC_IDENTIFIER && LA(1) <= REFERENCE)) && (LA(2)==COLON)) {
								label_colon();
							}
							else if ((_tokenSet_63.member(LA(1))) && (_tokenSet_66.member(LA(2)))) {
							}
							else {
								throw new NoViableAltException(LT(1), getFilename());
							}
							
							}
							{
							switch ( LA(1)) {
							case POSTPONED:
							{
								match(POSTPONED);
								break;
							}
							case BASIC_IDENTIFIER:
							case EXTENDED_IDENTIFIER:
							case REFERENCE:
							{
								break;
							}
							default:
							{
								throw new NoViableAltException(LT(1), getFilename());
							}
							}
							}
							procedure_call();
							match(SEMI);
							}
						}
						catch (RecognitionException pe) {
							synPredMatched56 = false;
						}
						rewind(_m56);
inputState.guessing--;
					}
					if ( synPredMatched56 ) {
						concurrent_procedure_call_statement();
						astFactory.addASTChild(currentAST, returnAST);
					}
					else {
						boolean synPredMatched60 = false;
						if (((_tokenSet_67.member(LA(1))) && (_tokenSet_68.member(LA(2))) && (_tokenSet_69.member(LA(3))))) {
							int _m60 = mark();
							synPredMatched60 = true;
							inputState.guessing++;
							try {
								{
								{
								switch ( LA(1)) {
								case BASIC_IDENTIFIER:
								case EXTENDED_IDENTIFIER:
								case REFERENCE:
								{
									label_colon();
									break;
								}
								case POSTPONED:
								case ASSERT:
								{
									break;
								}
								default:
								{
									throw new NoViableAltException(LT(1), getFilename());
								}
								}
								}
								{
								switch ( LA(1)) {
								case POSTPONED:
								{
									match(POSTPONED);
									break;
								}
								case ASSERT:
								{
									break;
								}
								default:
								{
									throw new NoViableAltException(LT(1), getFilename());
								}
								}
								}
								match(ASSERT);
								}
							}
							catch (RecognitionException pe) {
								synPredMatched60 = false;
							}
							rewind(_m60);
inputState.guessing--;
						}
						if ( synPredMatched60 ) {
							concurrent_assertion_statement();
							astFactory.addASTChild(currentAST, returnAST);
						}
						else {
							boolean synPredMatched65 = false;
							if (((_tokenSet_70.member(LA(1))) && (_tokenSet_71.member(LA(2))) && (_tokenSet_72.member(LA(3))))) {
								int _m65 = mark();
								synPredMatched65 = true;
								inputState.guessing++;
								try {
									{
									{
									if (((LA(1) >= BASIC_IDENTIFIER && LA(1) <= REFERENCE)) && (LA(2)==COLON)) {
										label_colon();
									}
									else if ((_tokenSet_70.member(LA(1))) && (_tokenSet_73.member(LA(2)))) {
									}
									else {
										throw new NoViableAltException(LT(1), getFilename());
									}
									
									}
									{
									switch ( LA(1)) {
									case POSTPONED:
									{
										match(POSTPONED);
										break;
									}
									case LPAREN:
									case BASIC_IDENTIFIER:
									case EXTENDED_IDENTIFIER:
									case REFERENCE:
									case WITH:
									{
										break;
									}
									default:
									{
										throw new NoViableAltException(LT(1), getFilename());
									}
									}
									}
									{
									switch ( LA(1)) {
									case LPAREN:
									case BASIC_IDENTIFIER:
									case EXTENDED_IDENTIFIER:
									case REFERENCE:
									{
										conditional_signal_assignment();
										break;
									}
									case WITH:
									{
										selected_signal_assignment();
										break;
									}
									default:
									{
										throw new NoViableAltException(LT(1), getFilename());
									}
									}
									}
									}
								}
								catch (RecognitionException pe) {
									synPredMatched65 = false;
								}
								rewind(_m65);
inputState.guessing--;
							}
							if ( synPredMatched65 ) {
								concurrent_signal_assignment_statement();
								astFactory.addASTChild(currentAST, returnAST);
							}
							else {
								boolean synPredMatched67 = false;
								if ((((LA(1) >= BASIC_IDENTIFIER && LA(1) <= REFERENCE)) && (LA(2)==COLON) && (_tokenSet_74.member(LA(3))))) {
									int _m67 = mark();
									synPredMatched67 = true;
									inputState.guessing++;
									try {
										{
										label_colon();
										instantiated_unit();
										}
									}
									catch (RecognitionException pe) {
										synPredMatched67 = false;
									}
									rewind(_m67);
inputState.guessing--;
								}
								if ( synPredMatched67 ) {
									component_instantiation_statement();
									astFactory.addASTChild(currentAST, returnAST);
								}
								else {
									boolean synPredMatched69 = false;
									if ((((LA(1) >= BASIC_IDENTIFIER && LA(1) <= REFERENCE)) && (LA(2)==COLON) && (LA(3)==FOR||LA(3)==IF))) {
										int _m69 = mark();
										synPredMatched69 = true;
										inputState.guessing++;
										try {
											{
											label_colon();
											generation_scheme();
											match(GENERATE);
											}
										}
										catch (RecognitionException pe) {
											synPredMatched69 = false;
										}
										rewind(_m69);
inputState.guessing--;
									}
									if ( synPredMatched69 ) {
										generate_statement();
										astFactory.addASTChild(currentAST, returnAST);
									}
									else {
										boolean synPredMatched74 = false;
										if (((_tokenSet_75.member(LA(1))) && (_tokenSet_76.member(LA(2))) && (_tokenSet_77.member(LA(3))))) {
											int _m74 = mark();
											synPredMatched74 = true;
											inputState.guessing++;
											try {
												{
												{
												switch ( LA(1)) {
												case BASIC_IDENTIFIER:
												case EXTENDED_IDENTIFIER:
												case REFERENCE:
												{
													label_colon();
													break;
												}
												case BREAK:
												{
													break;
												}
												default:
												{
													throw new NoViableAltException(LT(1), getFilename());
												}
												}
												}
												match(BREAK);
												{
												if ((_tokenSet_78.member(LA(1)))) {
													break_list();
												}
												else if ((true)) {
												}
												else {
													throw new NoViableAltException(LT(1), getFilename());
												}
												
												}
												{
												if ((LA(1)==ON)) {
													sensitivity_clause();
												}
												else {
												}
												
												}
												}
											}
											catch (RecognitionException pe) {
												synPredMatched74 = false;
											}
											rewind(_m74);
inputState.guessing--;
										}
										if ( synPredMatched74 ) {
											concurrent_break_statement();
											astFactory.addASTChild(currentAST, returnAST);
										}
										else {
											throw new NoViableAltException(LT(1), getFilename());
										}
										}}}}}}}
										}
										architecture_statement_AST = (AST)currentAST.root;
									}
									catch (RecognitionException ex) {
										if (inputState.guessing==0) {
											reportError(ex);
											recover(ex,_tokenSet_79);
										} else {
										  throw ex;
										}
									}
									returnAST = architecture_statement_AST;
								}
								
	public final void block_statement() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST block_statement_AST = null;
		
		try {      // for error handling
			label_colon();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp103_AST = null;
			tmp103_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp103_AST);
			match(BLOCK);
			{
			switch ( LA(1)) {
			case LPAREN:
			{
				AST tmp104_AST = null;
				tmp104_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp104_AST);
				match(LPAREN);
				expression();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp105_AST = null;
				tmp105_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp105_AST);
				match(RPAREN);
				break;
			}
			case ALIAS:
			case IS:
			case BEGIN:
			case ATTRIBUTE:
			case USE:
			case FOR:
			case QUANTITY:
			case COMPONENT:
			case CONSTANT:
			case DISCONNECT:
			case PROCEDURE:
			case FUNCTION:
			case TYPE:
			case SUBTYPE:
			case SIGNAL:
			case VARIABLE:
			case GROUP:
			case FILE:
			case NATURE:
			case SUBNATURE:
			case TERMINAL:
			case GENERIC:
			case PORT:
			case PURE:
			case IMPURE:
			case CONTEXT:
			case SHARED:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			switch ( LA(1)) {
			case IS:
			{
				AST tmp106_AST = null;
				tmp106_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp106_AST);
				match(IS);
				break;
			}
			case ALIAS:
			case BEGIN:
			case ATTRIBUTE:
			case USE:
			case FOR:
			case QUANTITY:
			case COMPONENT:
			case CONSTANT:
			case DISCONNECT:
			case PROCEDURE:
			case FUNCTION:
			case TYPE:
			case SUBTYPE:
			case SIGNAL:
			case VARIABLE:
			case GROUP:
			case FILE:
			case NATURE:
			case SUBNATURE:
			case TERMINAL:
			case GENERIC:
			case PORT:
			case PURE:
			case IMPURE:
			case CONTEXT:
			case SHARED:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			block_header();
			astFactory.addASTChild(currentAST, returnAST);
			block_declarative_part();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp107_AST = null;
			tmp107_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp107_AST);
			match(BEGIN);
			block_statement_part();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp108_AST = null;
			tmp108_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp108_AST);
			match(END);
			AST tmp109_AST = null;
			tmp109_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp109_AST);
			match(BLOCK);
			{
			switch ( LA(1)) {
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			{
				identifier();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case SEMI:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			AST tmp110_AST = null;
			tmp110_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp110_AST);
			match(SEMI);
			if ( inputState.guessing==0 ) {
				block_statement_AST = (AST)currentAST.root;
				block_statement_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(BLOCKSTATEMENT,"BlockStatement")).add(block_statement_AST));
				currentAST.root = block_statement_AST;
				currentAST.child = block_statement_AST!=null &&block_statement_AST.getFirstChild()!=null ?
					block_statement_AST.getFirstChild() : block_statement_AST;
				currentAST.advanceChildToEnd();
			}
			block_statement_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_79);
			} else {
			  throw ex;
			}
		}
		returnAST = block_statement_AST;
	}
	
	public final void label_colon() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST label_colon_AST = null;
		
		try {      // for error handling
			identifier();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp111_AST = null;
			tmp111_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp111_AST);
			match(COLON);
			if ( inputState.guessing==0 ) {
				label_colon_AST = (AST)currentAST.root;
				label_colon_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(LABELCOLON,"LabelColon")).add(label_colon_AST));
				currentAST.root = label_colon_AST;
				currentAST.child = label_colon_AST!=null &&label_colon_AST.getFirstChild()!=null ?
					label_colon_AST.getFirstChild() : label_colon_AST;
				currentAST.advanceChildToEnd();
			}
			label_colon_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_80);
			} else {
			  throw ex;
			}
		}
		returnAST = label_colon_AST;
	}
	
	public final void process_statement() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST process_statement_AST = null;
		
		try {      // for error handling
			process_head();
			astFactory.addASTChild(currentAST, returnAST);
			process_declarative_part();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp112_AST = null;
			tmp112_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp112_AST);
			match(BEGIN);
			process_statement_part();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp113_AST = null;
			tmp113_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp113_AST);
			match(END);
			{
			switch ( LA(1)) {
			case POSTPONED:
			{
				AST tmp114_AST = null;
				tmp114_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp114_AST);
				match(POSTPONED);
				break;
			}
			case PROCESS:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			AST tmp115_AST = null;
			tmp115_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp115_AST);
			match(PROCESS);
			{
			switch ( LA(1)) {
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			{
				identifier();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case SEMI:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			AST tmp116_AST = null;
			tmp116_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp116_AST);
			match(SEMI);
			if ( inputState.guessing==0 ) {
				process_statement_AST = (AST)currentAST.root;
				process_statement_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(PROCESSSTATEMENT,"ProcessStatement")).add(process_statement_AST));
				currentAST.root = process_statement_AST;
				currentAST.child = process_statement_AST!=null &&process_statement_AST.getFirstChild()!=null ?
					process_statement_AST.getFirstChild() : process_statement_AST;
				currentAST.advanceChildToEnd();
			}
			process_statement_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_79);
			} else {
			  throw ex;
			}
		}
		returnAST = process_statement_AST;
	}
	
	public final void procedure_call() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST procedure_call_AST = null;
		
		try {      // for error handling
			name();
			astFactory.addASTChild(currentAST, returnAST);
			{
			switch ( LA(1)) {
			case LPAREN:
			{
				AST tmp117_AST = null;
				tmp117_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp117_AST);
				match(LPAREN);
				actual_parameter_part();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp118_AST = null;
				tmp118_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp118_AST);
				match(RPAREN);
				break;
			}
			case SEMI:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			procedure_call_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_1);
			} else {
			  throw ex;
			}
		}
		returnAST = procedure_call_AST;
	}
	
	public final void concurrent_procedure_call_statement() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST concurrent_procedure_call_statement_AST = null;
		
		try {      // for error handling
			{
			if (((LA(1) >= BASIC_IDENTIFIER && LA(1) <= REFERENCE)) && (LA(2)==COLON)) {
				label_colon();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else if ((_tokenSet_63.member(LA(1))) && (_tokenSet_66.member(LA(2)))) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			{
			switch ( LA(1)) {
			case POSTPONED:
			{
				AST tmp119_AST = null;
				tmp119_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp119_AST);
				match(POSTPONED);
				break;
			}
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			procedure_call();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp120_AST = null;
			tmp120_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp120_AST);
			match(SEMI);
			concurrent_procedure_call_statement_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_79);
			} else {
			  throw ex;
			}
		}
		returnAST = concurrent_procedure_call_statement_AST;
	}
	
	public final void concurrent_assertion_statement() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST concurrent_assertion_statement_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			{
				label_colon();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case POSTPONED:
			case ASSERT:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			switch ( LA(1)) {
			case POSTPONED:
			{
				AST tmp121_AST = null;
				tmp121_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp121_AST);
				match(POSTPONED);
				break;
			}
			case ASSERT:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			assertion();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp122_AST = null;
			tmp122_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp122_AST);
			match(SEMI);
			if ( inputState.guessing==0 ) {
				concurrent_assertion_statement_AST = (AST)currentAST.root;
				concurrent_assertion_statement_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(CONCURRENTASSERTIONSTATEMENT,"concurrent_assertion_statement")).add(concurrent_assertion_statement_AST));
				currentAST.root = concurrent_assertion_statement_AST;
				currentAST.child = concurrent_assertion_statement_AST!=null &&concurrent_assertion_statement_AST.getFirstChild()!=null ?
					concurrent_assertion_statement_AST.getFirstChild() : concurrent_assertion_statement_AST;
				currentAST.advanceChildToEnd();
			}
			concurrent_assertion_statement_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_79);
			} else {
			  throw ex;
			}
		}
		returnAST = concurrent_assertion_statement_AST;
	}
	
	public final void conditional_signal_assignment() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST conditional_signal_assignment_AST = null;
		
		try {      // for error handling
			target();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp123_AST = null;
			tmp123_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp123_AST);
			match(LE);
			opts();
			astFactory.addASTChild(currentAST, returnAST);
			conditional_waveforms();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp124_AST = null;
			tmp124_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp124_AST);
			match(SEMI);
			if ( inputState.guessing==0 ) {
				conditional_signal_assignment_AST = (AST)currentAST.root;
				conditional_signal_assignment_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(CONDITIONALSIGNALASSIGNMENT,"ConditionalSignalAssignment")).add(conditional_signal_assignment_AST));
				currentAST.root = conditional_signal_assignment_AST;
				currentAST.child = conditional_signal_assignment_AST!=null &&conditional_signal_assignment_AST.getFirstChild()!=null ?
					conditional_signal_assignment_AST.getFirstChild() : conditional_signal_assignment_AST;
				currentAST.advanceChildToEnd();
			}
			conditional_signal_assignment_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_79);
			} else {
			  throw ex;
			}
		}
		returnAST = conditional_signal_assignment_AST;
	}
	
	public final void selected_signal_assignment() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST selected_signal_assignment_AST = null;
		
		try {      // for error handling
			AST tmp125_AST = null;
			tmp125_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp125_AST);
			match(WITH);
			expression();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp126_AST = null;
			tmp126_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp126_AST);
			match(SELECT);
			target();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp127_AST = null;
			tmp127_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp127_AST);
			match(LE);
			opts();
			astFactory.addASTChild(currentAST, returnAST);
			selected_waveforms();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp128_AST = null;
			tmp128_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp128_AST);
			match(SEMI);
			if ( inputState.guessing==0 ) {
				selected_signal_assignment_AST = (AST)currentAST.root;
				selected_signal_assignment_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(SELECTEDSIGNALASSIGNMENT,"SelectedSignalAssignment")).add(selected_signal_assignment_AST));
				currentAST.root = selected_signal_assignment_AST;
				currentAST.child = selected_signal_assignment_AST!=null &&selected_signal_assignment_AST.getFirstChild()!=null ?
					selected_signal_assignment_AST.getFirstChild() : selected_signal_assignment_AST;
				currentAST.advanceChildToEnd();
			}
			selected_signal_assignment_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_79);
			} else {
			  throw ex;
			}
		}
		returnAST = selected_signal_assignment_AST;
	}
	
	public final void concurrent_signal_assignment_statement() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST concurrent_signal_assignment_statement_AST = null;
		
		try {      // for error handling
			label_colon_wrap();
			astFactory.addASTChild(currentAST, returnAST);
			postponedQ();
			astFactory.addASTChild(currentAST, returnAST);
			{
			switch ( LA(1)) {
			case LPAREN:
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			{
				conditional_signal_assignment();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case WITH:
			{
				selected_signal_assignment();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				concurrent_signal_assignment_statement_AST = (AST)currentAST.root;
				concurrent_signal_assignment_statement_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(CONCURRENTSIGNALASSIGNMENTSTATEMENT,"ConcurrentSignalAssignmentStatement")).add(concurrent_signal_assignment_statement_AST));
				currentAST.root = concurrent_signal_assignment_statement_AST;
				currentAST.child = concurrent_signal_assignment_statement_AST!=null &&concurrent_signal_assignment_statement_AST.getFirstChild()!=null ?
					concurrent_signal_assignment_statement_AST.getFirstChild() : concurrent_signal_assignment_statement_AST;
				currentAST.advanceChildToEnd();
			}
			concurrent_signal_assignment_statement_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_79);
			} else {
			  throw ex;
			}
		}
		returnAST = concurrent_signal_assignment_statement_AST;
	}
	
	public final void instantiated_unit() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST instantiated_unit_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case COMPONENT:
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			{
				inst_unit_comp();
				astFactory.addASTChild(currentAST, returnAST);
				instantiated_unit_AST = (AST)currentAST.root;
				break;
			}
			case ENTITY:
			{
				inst_unit_ent();
				astFactory.addASTChild(currentAST, returnAST);
				instantiated_unit_AST = (AST)currentAST.root;
				break;
			}
			case CONFIGURATION:
			{
				inst_unit_conf();
				astFactory.addASTChild(currentAST, returnAST);
				instantiated_unit_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_81);
			} else {
			  throw ex;
			}
		}
		returnAST = instantiated_unit_AST;
	}
	
	public final void component_instantiation_statement() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST component_instantiation_statement_AST = null;
		
		try {      // for error handling
			label_colon();
			astFactory.addASTChild(currentAST, returnAST);
			instantiated_unit();
			astFactory.addASTChild(currentAST, returnAST);
			{
			generic_map_aspect();
			astFactory.addASTChild(currentAST, returnAST);
			}
			{
			port_map_aspect();
			astFactory.addASTChild(currentAST, returnAST);
			}
			AST tmp129_AST = null;
			tmp129_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp129_AST);
			match(SEMI);
			if ( inputState.guessing==0 ) {
				component_instantiation_statement_AST = (AST)currentAST.root;
				component_instantiation_statement_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(COMPONENTINSTANTIAIONSTATEMENT,"ComponentInstantiationStatement")).add(component_instantiation_statement_AST));
				currentAST.root = component_instantiation_statement_AST;
				currentAST.child = component_instantiation_statement_AST!=null &&component_instantiation_statement_AST.getFirstChild()!=null ?
					component_instantiation_statement_AST.getFirstChild() : component_instantiation_statement_AST;
				currentAST.advanceChildToEnd();
			}
			component_instantiation_statement_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_79);
			} else {
			  throw ex;
			}
		}
		returnAST = component_instantiation_statement_AST;
	}
	
	public final void generation_scheme() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST generation_scheme_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case FOR:
			{
				generate_for();
				astFactory.addASTChild(currentAST, returnAST);
				generation_scheme_AST = (AST)currentAST.root;
				break;
			}
			case IF:
			{
				generate_if();
				astFactory.addASTChild(currentAST, returnAST);
				generation_scheme_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_82);
			} else {
			  throw ex;
			}
		}
		returnAST = generation_scheme_AST;
	}
	
	public final void generate_statement() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST generate_statement_AST = null;
		
		try {      // for error handling
			label_colon();
			astFactory.addASTChild(currentAST, returnAST);
			generation_scheme();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp130_AST = null;
			tmp130_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp130_AST);
			match(GENERATE);
			generate_declarative_part();
			astFactory.addASTChild(currentAST, returnAST);
			generate_statement_part();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp131_AST = null;
			tmp131_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp131_AST);
			match(END);
			AST tmp132_AST = null;
			tmp132_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp132_AST);
			match(GENERATE);
			{
			switch ( LA(1)) {
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			{
				identifier();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case SEMI:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			AST tmp133_AST = null;
			tmp133_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp133_AST);
			match(SEMI);
			if ( inputState.guessing==0 ) {
				generate_statement_AST = (AST)currentAST.root;
				generate_statement_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(GENERATESTATEMENT,"generate_statement")).add(generate_statement_AST));
				currentAST.root = generate_statement_AST;
				currentAST.child = generate_statement_AST!=null &&generate_statement_AST.getFirstChild()!=null ?
					generate_statement_AST.getFirstChild() : generate_statement_AST;
				currentAST.advanceChildToEnd();
			}
			generate_statement_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_79);
			} else {
			  throw ex;
			}
		}
		returnAST = generate_statement_AST;
	}
	
	public final void break_list() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST break_list_AST = null;
		
		try {      // for error handling
			break_element();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop142:
			do {
				if ((LA(1)==COMMA)) {
					AST tmp134_AST = null;
					tmp134_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp134_AST);
					match(COMMA);
					break_element();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop142;
				}
				
			} while (true);
			}
			break_list_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_83);
			} else {
			  throw ex;
			}
		}
		returnAST = break_list_AST;
	}
	
	public final void sensitivity_clause() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST sensitivity_clause_AST = null;
		
		try {      // for error handling
			AST tmp135_AST = null;
			tmp135_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp135_AST);
			match(ON);
			sensitivity_list();
			astFactory.addASTChild(currentAST, returnAST);
			sensitivity_clause_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_84);
			} else {
			  throw ex;
			}
		}
		returnAST = sensitivity_clause_AST;
	}
	
	public final void concurrent_break_statement() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST concurrent_break_statement_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			{
				label_colon();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case BREAK:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			AST tmp136_AST = null;
			tmp136_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp136_AST);
			match(BREAK);
			{
			switch ( LA(1)) {
			case FOR:
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			{
				break_list();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case SEMI:
			case WHEN:
			case ON:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			switch ( LA(1)) {
			case ON:
			{
				sensitivity_clause();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case SEMI:
			case WHEN:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			switch ( LA(1)) {
			case WHEN:
			{
				AST tmp137_AST = null;
				tmp137_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp137_AST);
				match(WHEN);
				condition();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case SEMI:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			AST tmp138_AST = null;
			tmp138_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp138_AST);
			match(SEMI);
			concurrent_break_statement_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_79);
			} else {
			  throw ex;
			}
		}
		returnAST = concurrent_break_statement_AST;
	}
	
	public final void array_nature_definition() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST array_nature_definition_AST = null;
		
		try {      // for error handling
			boolean synPredMatched80 = false;
			if (((LA(1)==ARRAY) && (LA(2)==LPAREN) && ((LA(3) >= BASIC_IDENTIFIER && LA(3) <= REFERENCE)))) {
				int _m80 = mark();
				synPredMatched80 = true;
				inputState.guessing++;
				try {
					{
					match(ARRAY);
					match(LPAREN);
					index_subtype_definition();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched80 = false;
				}
				rewind(_m80);
inputState.guessing--;
			}
			if ( synPredMatched80 ) {
				unconstrained_nature_definition();
				astFactory.addASTChild(currentAST, returnAST);
				array_nature_definition_AST = (AST)currentAST.root;
			}
			else if ((LA(1)==ARRAY) && (LA(2)==LPAREN) && (_tokenSet_19.member(LA(3)))) {
				constrained_nature_definition();
				astFactory.addASTChild(currentAST, returnAST);
				array_nature_definition_AST = (AST)currentAST.root;
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_1);
			} else {
			  throw ex;
			}
		}
		returnAST = array_nature_definition_AST;
	}
	
	public final void index_subtype_definition() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST index_subtype_definition_AST = null;
		
		try {      // for error handling
			name();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp139_AST = null;
			tmp139_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp139_AST);
			match(RANGETOK);
			AST tmp140_AST = null;
			tmp140_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp140_AST);
			match(BOX);
			if ( inputState.guessing==0 ) {
				index_subtype_definition_AST = (AST)currentAST.root;
				index_subtype_definition_AST= (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(INDEXSUBTYPEDEFINITION,"index_subtype_definition")).add(index_subtype_definition_AST));
				currentAST.root = index_subtype_definition_AST;
				currentAST.child = index_subtype_definition_AST!=null &&index_subtype_definition_AST.getFirstChild()!=null ?
					index_subtype_definition_AST.getFirstChild() : index_subtype_definition_AST;
				currentAST.advanceChildToEnd();
			}
			index_subtype_definition_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_27);
			} else {
			  throw ex;
			}
		}
		returnAST = index_subtype_definition_AST;
	}
	
	public final void unconstrained_nature_definition() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST unconstrained_nature_definition_AST = null;
		
		try {      // for error handling
			AST tmp141_AST = null;
			tmp141_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp141_AST);
			match(ARRAY);
			AST tmp142_AST = null;
			tmp142_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp142_AST);
			match(LPAREN);
			index_subtype_definition();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop700:
			do {
				if ((LA(1)==COMMA)) {
					AST tmp143_AST = null;
					tmp143_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp143_AST);
					match(COMMA);
					index_subtype_definition();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop700;
				}
				
			} while (true);
			}
			AST tmp144_AST = null;
			tmp144_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp144_AST);
			match(RPAREN);
			AST tmp145_AST = null;
			tmp145_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp145_AST);
			match(OF);
			subnature_indication();
			astFactory.addASTChild(currentAST, returnAST);
			unconstrained_nature_definition_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_1);
			} else {
			  throw ex;
			}
		}
		returnAST = unconstrained_nature_definition_AST;
	}
	
	public final void constrained_nature_definition() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST constrained_nature_definition_AST = null;
		
		try {      // for error handling
			AST tmp146_AST = null;
			tmp146_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp146_AST);
			match(ARRAY);
			index_constraint();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp147_AST = null;
			tmp147_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp147_AST);
			match(OF);
			subnature_indication();
			astFactory.addASTChild(currentAST, returnAST);
			constrained_nature_definition_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_1);
			} else {
			  throw ex;
			}
		}
		returnAST = constrained_nature_definition_AST;
	}
	
	public final void array_type_definition() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST array_type_definition_AST = null;
		
		try {      // for error handling
			boolean synPredMatched83 = false;
			if (((LA(1)==ARRAY) && (LA(2)==LPAREN) && ((LA(3) >= BASIC_IDENTIFIER && LA(3) <= REFERENCE)))) {
				int _m83 = mark();
				synPredMatched83 = true;
				inputState.guessing++;
				try {
					{
					unconstrained_array_definition();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched83 = false;
				}
				rewind(_m83);
inputState.guessing--;
			}
			if ( synPredMatched83 ) {
				unconstrained_array_definition();
				astFactory.addASTChild(currentAST, returnAST);
				array_type_definition_AST = (AST)currentAST.root;
			}
			else if ((LA(1)==ARRAY) && (LA(2)==LPAREN) && (_tokenSet_19.member(LA(3)))) {
				constrained_array_definition();
				astFactory.addASTChild(currentAST, returnAST);
				array_type_definition_AST = (AST)currentAST.root;
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_1);
			} else {
			  throw ex;
			}
		}
		returnAST = array_type_definition_AST;
	}
	
	public final void unconstrained_array_definition() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST unconstrained_array_definition_AST = null;
		
		try {      // for error handling
			AST tmp148_AST = null;
			tmp148_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp148_AST);
			match(ARRAY);
			AST tmp149_AST = null;
			tmp149_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp149_AST);
			match(LPAREN);
			index_subtype_definition();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop697:
			do {
				if ((LA(1)==COMMA)) {
					AST tmp150_AST = null;
					tmp150_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp150_AST);
					match(COMMA);
					index_subtype_definition();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop697;
				}
				
			} while (true);
			}
			AST tmp151_AST = null;
			tmp151_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp151_AST);
			match(RPAREN);
			AST tmp152_AST = null;
			tmp152_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp152_AST);
			match(OF);
			subtype_indication();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				unconstrained_array_definition_AST = (AST)currentAST.root;
				unconstrained_array_definition_AST= (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(UNCONSTRAINEDARRAYDEFINITION,"unconstrained_array_definition")).add(unconstrained_array_definition_AST));
				currentAST.root = unconstrained_array_definition_AST;
				currentAST.child = unconstrained_array_definition_AST!=null &&unconstrained_array_definition_AST.getFirstChild()!=null ?
					unconstrained_array_definition_AST.getFirstChild() : unconstrained_array_definition_AST;
				currentAST.advanceChildToEnd();
			}
			unconstrained_array_definition_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_1);
			} else {
			  throw ex;
			}
		}
		returnAST = unconstrained_array_definition_AST;
	}
	
	public final void constrained_array_definition() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST constrained_array_definition_AST = null;
		
		try {      // for error handling
			AST tmp153_AST = null;
			tmp153_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp153_AST);
			match(ARRAY);
			index_constraint();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp154_AST = null;
			tmp154_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp154_AST);
			match(OF);
			subtype_indication();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				constrained_array_definition_AST = (AST)currentAST.root;
				constrained_array_definition_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(CONSTRAINEDARRAYDEFINITION,"ConstrainedArrayDefinition")).add(constrained_array_definition_AST));
				currentAST.root = constrained_array_definition_AST;
				currentAST.child = constrained_array_definition_AST!=null &&constrained_array_definition_AST.getFirstChild()!=null ?
					constrained_array_definition_AST.getFirstChild() : constrained_array_definition_AST;
				currentAST.advanceChildToEnd();
			}
			constrained_array_definition_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_1);
			} else {
			  throw ex;
			}
		}
		returnAST = constrained_array_definition_AST;
	}
	
	public final void assertion() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST assertion_AST = null;
		
		try {      // for error handling
			AST tmp155_AST = null;
			tmp155_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp155_AST);
			match(ASSERT);
			condition();
			astFactory.addASTChild(currentAST, returnAST);
			{
			switch ( LA(1)) {
			case REPORT:
			{
				AST tmp156_AST = null;
				tmp156_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp156_AST);
				match(REPORT);
				expression();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case SEMI:
			case SEVERITY:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			switch ( LA(1)) {
			case SEVERITY:
			{
				AST tmp157_AST = null;
				tmp157_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp157_AST);
				match(SEVERITY);
				expression();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case SEMI:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			assertion_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_1);
			} else {
			  throw ex;
			}
		}
		returnAST = assertion_AST;
	}
	
	public final void condition() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST condition_AST = null;
		
		try {      // for error handling
			expression();
			astFactory.addASTChild(currentAST, returnAST);
			condition_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_85);
			} else {
			  throw ex;
			}
		}
		returnAST = condition_AST;
	}
	
	public final void assertion_statement() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST assertion_statement_AST = null;
		
		try {      // for error handling
			assertion();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp158_AST = null;
			tmp158_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp158_AST);
			match(SEMI);
			if ( inputState.guessing==0 ) {
				assertion_statement_AST = (AST)currentAST.root;
				assertion_statement_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(ASSERTIONSTATEMENT,"AssertionStatement")).add(assertion_statement_AST));
				currentAST.root = assertion_statement_AST;
				currentAST.child = assertion_statement_AST!=null &&assertion_statement_AST.getFirstChild()!=null ?
					assertion_statement_AST.getFirstChild() : assertion_statement_AST;
				currentAST.advanceChildToEnd();
			}
			assertion_statement_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_86);
			} else {
			  throw ex;
			}
		}
		returnAST = assertion_statement_AST;
	}
	
	public final void association_element() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST association_element_AST = null;
		
		try {      // for error handling
			boolean synPredMatched90 = false;
			if ((((LA(1) >= BASIC_IDENTIFIER && LA(1) <= REFERENCE)) && (_tokenSet_87.member(LA(2))) && (_tokenSet_88.member(LA(3))))) {
				int _m90 = mark();
				synPredMatched90 = true;
				inputState.guessing++;
				try {
					{
					formal_part();
					match(ARROW);
					}
				}
				catch (RecognitionException pe) {
					synPredMatched90 = false;
				}
				rewind(_m90);
inputState.guessing--;
			}
			if ( synPredMatched90 ) {
				association_arrow();
				astFactory.addASTChild(currentAST, returnAST);
				association_element_AST = (AST)currentAST.root;
			}
			else if ((_tokenSet_29.member(LA(1))) && (_tokenSet_30.member(LA(2))) && (_tokenSet_31.member(LA(3)))) {
				association_no_arrow();
				astFactory.addASTChild(currentAST, returnAST);
				association_element_AST = (AST)currentAST.root;
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_27);
			} else {
			  throw ex;
			}
		}
		returnAST = association_element_AST;
	}
	
	public final void formal_part() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST formal_part_AST = null;
		
		try {      // for error handling
			name();
			astFactory.addASTChild(currentAST, returnAST);
			{
			switch ( LA(1)) {
			case LPAREN:
			{
				AST tmp159_AST = null;
				tmp159_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp159_AST);
				match(LPAREN);
				name();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp160_AST = null;
				tmp160_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp160_AST);
				match(RPAREN);
				break;
			}
			case ARROW:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			formal_part_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_89);
			} else {
			  throw ex;
			}
		}
		returnAST = formal_part_AST;
	}
	
	public final void association_arrow() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST association_arrow_AST = null;
		
		try {      // for error handling
			formal_part();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp161_AST = null;
			tmp161_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp161_AST);
			match(ARROW);
			{
			switch ( LA(1)) {
			case DECIMAL_LITERAL:
			case BASED_LITERAL:
			case OPEN:
			case LPAREN:
			case CHARACTER_LITERAL:
			case STRING_LITERAL:
			case NEW:
			case PLUS:
			case MINUS:
			case ABS:
			case NOT:
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			case NULLTOK:
			case BIT_STRING_LITERAL:
			{
				actual_part();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case RPAREN:
			case COMMA:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				association_arrow_AST = (AST)currentAST.root;
				association_arrow_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(ASSOCIATIONARROW,"AssociationArrow")).add(association_arrow_AST));
				currentAST.root = association_arrow_AST;
				currentAST.child = association_arrow_AST!=null &&association_arrow_AST.getFirstChild()!=null ?
					association_arrow_AST.getFirstChild() : association_arrow_AST;
				currentAST.advanceChildToEnd();
			}
			association_arrow_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_27);
			} else {
			  throw ex;
			}
		}
		returnAST = association_arrow_AST;
	}
	
	public final void association_no_arrow() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST association_no_arrow_AST = null;
		
		try {      // for error handling
			actual_part();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				association_no_arrow_AST = (AST)currentAST.root;
				association_no_arrow_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(ASSOCIATIONNOARROW,"AssociationNoArrow")).add(association_no_arrow_AST));
				currentAST.root = association_no_arrow_AST;
				currentAST.child = association_no_arrow_AST!=null &&association_no_arrow_AST.getFirstChild()!=null ?
					association_no_arrow_AST.getFirstChild() : association_no_arrow_AST;
				currentAST.advanceChildToEnd();
			}
			association_no_arrow_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_27);
			} else {
			  throw ex;
			}
		}
		returnAST = association_no_arrow_AST;
	}
	
	public final void attribute_designator() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST attribute_designator_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			{
				identifier();
				astFactory.addASTChild(currentAST, returnAST);
				attribute_designator_AST = (AST)currentAST.root;
				break;
			}
			case RANGETOK:
			{
				AST tmp162_AST = null;
				tmp162_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp162_AST);
				match(RANGETOK);
				attribute_designator_AST = (AST)currentAST.root;
				break;
			}
			case ACROSS:
			{
				AST tmp163_AST = null;
				tmp163_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp163_AST);
				match(ACROSS);
				attribute_designator_AST = (AST)currentAST.root;
				break;
			}
			case THROUGH:
			{
				AST tmp164_AST = null;
				tmp164_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp164_AST);
				match(THROUGH);
				attribute_designator_AST = (AST)currentAST.root;
				break;
			}
			case TOLERANCE:
			{
				AST tmp165_AST = null;
				tmp165_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp165_AST);
				match(TOLERANCE);
				attribute_designator_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_90);
			} else {
			  throw ex;
			}
		}
		returnAST = attribute_designator_AST;
	}
	
	public final void entity_specification() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST entity_specification_AST = null;
		
		try {      // for error handling
			entity_name_list();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp166_AST = null;
			tmp166_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp166_AST);
			match(COLON);
			entity_class();
			astFactory.addASTChild(currentAST, returnAST);
			entity_specification_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_37);
			} else {
			  throw ex;
			}
		}
		returnAST = entity_specification_AST;
	}
	
	public final void base_unit_declaration() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST base_unit_declaration_AST = null;
		
		try {      // for error handling
			identifier();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp167_AST = null;
			tmp167_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp167_AST);
			match(SEMI);
			base_unit_declaration_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_91);
			} else {
			  throw ex;
			}
		}
		returnAST = base_unit_declaration_AST;
	}
	
	public final void binding_indication() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST binding_indication_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case USE:
			{
				AST tmp168_AST = null;
				tmp168_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp168_AST);
				match(USE);
				entity_aspect();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case SEMI:
			case GENERIC:
			case PORT:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			generic_map_aspect();
			astFactory.addASTChild(currentAST, returnAST);
			}
			{
			port_map_aspect();
			astFactory.addASTChild(currentAST, returnAST);
			}
			binding_indication_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_1);
			} else {
			  throw ex;
			}
		}
		returnAST = binding_indication_AST;
	}
	
	public final void entity_aspect() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST entity_aspect_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case ENTITY:
			{
				AST tmp169_AST = null;
				tmp169_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp169_AST);
				match(ENTITY);
				name();
				astFactory.addASTChild(currentAST, returnAST);
				{
				switch ( LA(1)) {
				case LPAREN:
				{
					AST tmp170_AST = null;
					tmp170_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp170_AST);
					match(LPAREN);
					identifier();
					astFactory.addASTChild(currentAST, returnAST);
					AST tmp171_AST = null;
					tmp171_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp171_AST);
					match(RPAREN);
					break;
				}
				case SEMI:
				case GENERIC:
				case PORT:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				entity_aspect_AST = (AST)currentAST.root;
				break;
			}
			case CONFIGURATION:
			{
				AST tmp172_AST = null;
				tmp172_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp172_AST);
				match(CONFIGURATION);
				name();
				astFactory.addASTChild(currentAST, returnAST);
				entity_aspect_AST = (AST)currentAST.root;
				break;
			}
			case OPEN:
			{
				AST tmp173_AST = null;
				tmp173_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp173_AST);
				match(OPEN);
				entity_aspect_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_81);
			} else {
			  throw ex;
			}
		}
		returnAST = entity_aspect_AST;
	}
	
	public final void generic_map_aspect() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST generic_map_aspect_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case GENERIC:
			{
				AST tmp174_AST = null;
				tmp174_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp174_AST);
				match(GENERIC);
				AST tmp175_AST = null;
				tmp175_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp175_AST);
				match(MAP);
				AST tmp176_AST = null;
				tmp176_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp176_AST);
				match(LPAREN);
				association_list();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp177_AST = null;
				tmp177_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp177_AST);
				match(RPAREN);
				break;
			}
			case SEMI:
			case PORT:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				generic_map_aspect_AST = (AST)currentAST.root;
				generic_map_aspect_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(GENERICMAPASPECT,"GenericMapAspect")).add(generic_map_aspect_AST));
				currentAST.root = generic_map_aspect_AST;
				currentAST.child = generic_map_aspect_AST!=null &&generic_map_aspect_AST.getFirstChild()!=null ?
					generic_map_aspect_AST.getFirstChild() : generic_map_aspect_AST;
				currentAST.advanceChildToEnd();
			}
			generic_map_aspect_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_92);
			} else {
			  throw ex;
			}
		}
		returnAST = generic_map_aspect_AST;
	}
	
	public final void port_map_aspect() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST port_map_aspect_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case PORT:
			{
				AST tmp178_AST = null;
				tmp178_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp178_AST);
				match(PORT);
				AST tmp179_AST = null;
				tmp179_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp179_AST);
				match(MAP);
				AST tmp180_AST = null;
				tmp180_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp180_AST);
				match(LPAREN);
				association_list();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp181_AST = null;
				tmp181_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp181_AST);
				match(RPAREN);
				break;
			}
			case SEMI:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				port_map_aspect_AST = (AST)currentAST.root;
				port_map_aspect_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(PORTMAPASPECT,"PortMapAspect")).add(port_map_aspect_AST));
				currentAST.root = port_map_aspect_AST;
				currentAST.child = port_map_aspect_AST!=null &&port_map_aspect_AST.getFirstChild()!=null ?
					port_map_aspect_AST.getFirstChild() : port_map_aspect_AST;
				currentAST.advanceChildToEnd();
			}
			port_map_aspect_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_1);
			} else {
			  throw ex;
			}
		}
		returnAST = port_map_aspect_AST;
	}
	
	public final void block_configuration() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST block_configuration_AST = null;
		
		try {      // for error handling
			AST tmp182_AST = null;
			tmp182_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp182_AST);
			match(FOR);
			block_specification();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop108:
			do {
				if ((LA(1)==USE||LA(1)==CONTEXT)) {
					use_clause();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop108;
				}
				
			} while (true);
			}
			{
			_loop110:
			do {
				if ((LA(1)==FOR)) {
					configuration_item();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop110;
				}
				
			} while (true);
			}
			AST tmp183_AST = null;
			tmp183_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp183_AST);
			match(END);
			AST tmp184_AST = null;
			tmp184_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp184_AST);
			match(FOR);
			AST tmp185_AST = null;
			tmp185_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp185_AST);
			match(SEMI);
			block_configuration_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_93);
			} else {
			  throw ex;
			}
		}
		returnAST = block_configuration_AST;
	}
	
	public final void block_specification() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST block_specification_AST = null;
		
		try {      // for error handling
			boolean synPredMatched124 = false;
			if ((((LA(1) >= BASIC_IDENTIFIER && LA(1) <= REFERENCE)) && (_tokenSet_94.member(LA(2))) && (_tokenSet_95.member(LA(3))))) {
				int _m124 = mark();
				synPredMatched124 = true;
				inputState.guessing++;
				try {
					{
					identifier();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched124 = false;
				}
				rewind(_m124);
inputState.guessing--;
			}
			if ( synPredMatched124 ) {
				identifier();
				astFactory.addASTChild(currentAST, returnAST);
				{
				switch ( LA(1)) {
				case LPAREN:
				{
					AST tmp186_AST = null;
					tmp186_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp186_AST);
					match(LPAREN);
					index_specification();
					astFactory.addASTChild(currentAST, returnAST);
					AST tmp187_AST = null;
					tmp187_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp187_AST);
					match(RPAREN);
					break;
				}
				case END:
				case USE:
				case FOR:
				case CONTEXT:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				block_specification_AST = (AST)currentAST.root;
			}
			else if (((LA(1) >= BASIC_IDENTIFIER && LA(1) <= REFERENCE)) && (_tokenSet_96.member(LA(2))) && (_tokenSet_97.member(LA(3)))) {
				name();
				astFactory.addASTChild(currentAST, returnAST);
				block_specification_AST = (AST)currentAST.root;
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_98);
			} else {
			  throw ex;
			}
		}
		returnAST = block_specification_AST;
	}
	
	public final void configuration_item() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST configuration_item_AST = null;
		
		try {      // for error handling
			if ((LA(1)==FOR) && ((LA(2) >= BASIC_IDENTIFIER && LA(2) <= REFERENCE)) && (_tokenSet_96.member(LA(3)))) {
				block_configuration();
				astFactory.addASTChild(currentAST, returnAST);
				configuration_item_AST = (AST)currentAST.root;
			}
			else if ((LA(1)==FOR) && (_tokenSet_99.member(LA(2))) && (LA(3)==COMMA||LA(3)==COLON)) {
				component_configuration();
				astFactory.addASTChild(currentAST, returnAST);
				configuration_item_AST = (AST)currentAST.root;
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_93);
			} else {
			  throw ex;
			}
		}
		returnAST = configuration_item_AST;
	}
	
	public final void signal_declaration() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST signal_declaration_AST = null;
		
		try {      // for error handling
			AST tmp188_AST = null;
			tmp188_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp188_AST);
			match(SIGNAL);
			identifier_list();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp189_AST = null;
			tmp189_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp189_AST);
			match(COLON);
			subtype_indication();
			astFactory.addASTChild(currentAST, returnAST);
			signal_kind_or_var_assign();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp190_AST = null;
			tmp190_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp190_AST);
			match(SEMI);
			if ( inputState.guessing==0 ) {
				signal_declaration_AST = (AST)currentAST.root;
				signal_declaration_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(SIGNALDECLARATION,"SignalDeclaration")).add(signal_declaration_AST));
				currentAST.root = signal_declaration_AST;
				currentAST.child = signal_declaration_AST!=null &&signal_declaration_AST.getFirstChild()!=null ?
					signal_declaration_AST.getFirstChild() : signal_declaration_AST;
				currentAST.advanceChildToEnd();
			}
			signal_declaration_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_100);
			} else {
			  throw ex;
			}
		}
		returnAST = signal_declaration_AST;
	}
	
	public final void component_declaration() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST component_declaration_AST = null;
		
		try {      // for error handling
			AST tmp191_AST = null;
			tmp191_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp191_AST);
			match(COMPONENT);
			identifier();
			astFactory.addASTChild(currentAST, returnAST);
			component_is_dummy();
			astFactory.addASTChild(currentAST, returnAST);
			entity_header();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp192_AST = null;
			tmp192_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp192_AST);
			match(END);
			AST tmp193_AST = null;
			tmp193_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp193_AST);
			match(COMPONENT);
			{
			switch ( LA(1)) {
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			{
				identifier();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case SEMI:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			AST tmp194_AST = null;
			tmp194_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp194_AST);
			match(SEMI);
			if ( inputState.guessing==0 ) {
				component_declaration_AST = (AST)currentAST.root;
				component_declaration_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(COMPONENTDECLARATION,"ComponentDeclaration")).add(component_declaration_AST));
				currentAST.root = component_declaration_AST;
				currentAST.child = component_declaration_AST!=null &&component_declaration_AST.getFirstChild()!=null ?
					component_declaration_AST.getFirstChild() : component_declaration_AST;
				currentAST.advanceChildToEnd();
			}
			component_declaration_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_101);
			} else {
			  throw ex;
			}
		}
		returnAST = component_declaration_AST;
	}
	
	public final void configuration_specification() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST configuration_specification_AST = null;
		
		try {      // for error handling
			AST tmp195_AST = null;
			tmp195_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp195_AST);
			match(FOR);
			component_specification();
			astFactory.addASTChild(currentAST, returnAST);
			binding_indication();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp196_AST = null;
			tmp196_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp196_AST);
			match(SEMI);
			configuration_specification_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_59);
			} else {
			  throw ex;
			}
		}
		returnAST = configuration_specification_AST;
	}
	
	public final void disconnection_specification() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST disconnection_specification_AST = null;
		
		try {      // for error handling
			AST tmp197_AST = null;
			tmp197_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp197_AST);
			match(DISCONNECT);
			guarded_signal_specification();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp198_AST = null;
			tmp198_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp198_AST);
			match(AFTER);
			expression();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp199_AST = null;
			tmp199_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp199_AST);
			match(SEMI);
			disconnection_specification_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_101);
			} else {
			  throw ex;
			}
		}
		returnAST = disconnection_specification_AST;
	}
	
	public final void nature_declaration() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST nature_declaration_AST = null;
		
		try {      // for error handling
			AST tmp200_AST = null;
			tmp200_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp200_AST);
			match(NATURE);
			identifier();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp201_AST = null;
			tmp201_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp201_AST);
			match(IS);
			nature_definition();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp202_AST = null;
			tmp202_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp202_AST);
			match(SEMI);
			nature_declaration_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_101);
			} else {
			  throw ex;
			}
		}
		returnAST = nature_declaration_AST;
	}
	
	public final void subnature_declaration() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST subnature_declaration_AST = null;
		
		try {      // for error handling
			AST tmp203_AST = null;
			tmp203_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp203_AST);
			match(SUBNATURE);
			identifier();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp204_AST = null;
			tmp204_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp204_AST);
			match(IS);
			subnature_indication();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp205_AST = null;
			tmp205_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp205_AST);
			match(SEMI);
			subnature_declaration_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_101);
			} else {
			  throw ex;
			}
		}
		returnAST = subnature_declaration_AST;
	}
	
	public final void quantity_declaration() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST quantity_declaration_AST = null;
		
		try {      // for error handling
			boolean synPredMatched553 = false;
			if (((LA(1)==QUANTITY) && ((LA(2) >= BASIC_IDENTIFIER && LA(2) <= REFERENCE)) && (LA(3)==COMMA||LA(3)==COLON))) {
				int _m553 = mark();
				synPredMatched553 = true;
				inputState.guessing++;
				try {
					{
					free_quantity_declaration();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched553 = false;
				}
				rewind(_m553);
inputState.guessing--;
			}
			if ( synPredMatched553 ) {
				free_quantity_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				quantity_declaration_AST = (AST)currentAST.root;
			}
			else {
				boolean synPredMatched555 = false;
				if (((LA(1)==QUANTITY) && ((LA(2) >= BASIC_IDENTIFIER && LA(2) <= REFERENCE)) && (_tokenSet_102.member(LA(3))))) {
					int _m555 = mark();
					synPredMatched555 = true;
					inputState.guessing++;
					try {
						{
						branch_quantity_declaration();
						}
					}
					catch (RecognitionException pe) {
						synPredMatched555 = false;
					}
					rewind(_m555);
inputState.guessing--;
				}
				if ( synPredMatched555 ) {
					branch_quantity_declaration();
					astFactory.addASTChild(currentAST, returnAST);
					quantity_declaration_AST = (AST)currentAST.root;
				}
				else if ((LA(1)==QUANTITY) && ((LA(2) >= BASIC_IDENTIFIER && LA(2) <= REFERENCE)) && (LA(3)==COMMA||LA(3)==COLON)) {
					source_quantity_declaration();
					astFactory.addASTChild(currentAST, returnAST);
					quantity_declaration_AST = (AST)currentAST.root;
				}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
			catch (RecognitionException ex) {
				if (inputState.guessing==0) {
					reportError(ex);
					recover(ex,_tokenSet_100);
				} else {
				  throw ex;
				}
			}
			returnAST = quantity_declaration_AST;
		}
		
	public final void terminal_declaration() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST terminal_declaration_AST = null;
		
		try {      // for error handling
			AST tmp206_AST = null;
			tmp206_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp206_AST);
			match(TERMINAL);
			identifier_list();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp207_AST = null;
			tmp207_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp207_AST);
			match(COLON);
			subnature_indication();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp208_AST = null;
			tmp208_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp208_AST);
			match(SEMI);
			terminal_declaration_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_100);
			} else {
			  throw ex;
			}
		}
		returnAST = terminal_declaration_AST;
	}
	
	public final void block_declarative_part() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST block_declarative_part_AST = null;
		
		try {      // for error handling
			{
			_loop116:
			do {
				if ((_tokenSet_56.member(LA(1)))) {
					block_declarative_item();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop116;
				}
				
			} while (true);
			}
			block_declarative_part_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_57);
			} else {
			  throw ex;
			}
		}
		returnAST = block_declarative_part_AST;
	}
	
	public final void block_header() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST block_header_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case GENERIC:
			{
				generic_clause();
				astFactory.addASTChild(currentAST, returnAST);
				{
				switch ( LA(1)) {
				case SEMI:
				case GENERIC:
				{
					generic_map_aspect();
					astFactory.addASTChild(currentAST, returnAST);
					AST tmp209_AST = null;
					tmp209_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp209_AST);
					match(SEMI);
					break;
				}
				case ALIAS:
				case BEGIN:
				case ATTRIBUTE:
				case USE:
				case FOR:
				case QUANTITY:
				case COMPONENT:
				case CONSTANT:
				case DISCONNECT:
				case PROCEDURE:
				case FUNCTION:
				case TYPE:
				case SUBTYPE:
				case SIGNAL:
				case VARIABLE:
				case GROUP:
				case FILE:
				case NATURE:
				case SUBNATURE:
				case TERMINAL:
				case PORT:
				case PURE:
				case IMPURE:
				case CONTEXT:
				case SHARED:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				break;
			}
			case ALIAS:
			case BEGIN:
			case ATTRIBUTE:
			case USE:
			case FOR:
			case QUANTITY:
			case COMPONENT:
			case CONSTANT:
			case DISCONNECT:
			case PROCEDURE:
			case FUNCTION:
			case TYPE:
			case SUBTYPE:
			case SIGNAL:
			case VARIABLE:
			case GROUP:
			case FILE:
			case NATURE:
			case SUBNATURE:
			case TERMINAL:
			case PORT:
			case PURE:
			case IMPURE:
			case CONTEXT:
			case SHARED:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			switch ( LA(1)) {
			case PORT:
			{
				port_clause();
				astFactory.addASTChild(currentAST, returnAST);
				{
				switch ( LA(1)) {
				case SEMI:
				case PORT:
				{
					port_map_aspect();
					astFactory.addASTChild(currentAST, returnAST);
					AST tmp210_AST = null;
					tmp210_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp210_AST);
					match(SEMI);
					break;
				}
				case ALIAS:
				case BEGIN:
				case ATTRIBUTE:
				case USE:
				case FOR:
				case QUANTITY:
				case COMPONENT:
				case CONSTANT:
				case DISCONNECT:
				case PROCEDURE:
				case FUNCTION:
				case TYPE:
				case SUBTYPE:
				case SIGNAL:
				case VARIABLE:
				case GROUP:
				case FILE:
				case NATURE:
				case SUBNATURE:
				case TERMINAL:
				case PURE:
				case IMPURE:
				case CONTEXT:
				case SHARED:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				break;
			}
			case ALIAS:
			case BEGIN:
			case ATTRIBUTE:
			case USE:
			case FOR:
			case QUANTITY:
			case COMPONENT:
			case CONSTANT:
			case DISCONNECT:
			case PROCEDURE:
			case FUNCTION:
			case TYPE:
			case SUBTYPE:
			case SIGNAL:
			case VARIABLE:
			case GROUP:
			case FILE:
			case NATURE:
			case SUBNATURE:
			case TERMINAL:
			case PURE:
			case IMPURE:
			case CONTEXT:
			case SHARED:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			block_header_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_59);
			} else {
			  throw ex;
			}
		}
		returnAST = block_header_AST;
	}
	
	public final void generic_clause() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST generic_clause_AST = null;
		
		try {      // for error handling
			AST tmp211_AST = null;
			tmp211_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp211_AST);
			match(GENERIC);
			AST tmp212_AST = null;
			tmp212_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp212_AST);
			match(LPAREN);
			port_list();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp213_AST = null;
			tmp213_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp213_AST);
			match(RPAREN);
			AST tmp214_AST = null;
			tmp214_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp214_AST);
			match(SEMI);
			if ( inputState.guessing==0 ) {
				generic_clause_AST = (AST)currentAST.root;
				generic_clause_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(GENERICCLAUSE,"GenericClause")).add(generic_clause_AST));
				currentAST.root = generic_clause_AST;
				currentAST.child = generic_clause_AST!=null &&generic_clause_AST.getFirstChild()!=null ?
					generic_clause_AST.getFirstChild() : generic_clause_AST;
				currentAST.advanceChildToEnd();
			}
			generic_clause_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_103);
			} else {
			  throw ex;
			}
		}
		returnAST = generic_clause_AST;
	}
	
	public final void port_clause() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST port_clause_AST = null;
		
		try {      // for error handling
			AST tmp215_AST = null;
			tmp215_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp215_AST);
			match(PORT);
			AST tmp216_AST = null;
			tmp216_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp216_AST);
			match(LPAREN);
			port_list();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp217_AST = null;
			tmp217_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp217_AST);
			match(RPAREN);
			AST tmp218_AST = null;
			tmp218_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp218_AST);
			match(SEMI);
			if ( inputState.guessing==0 ) {
				port_clause_AST = (AST)currentAST.root;
				port_clause_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(PORTCLAUSE,"PortClause")).add(port_clause_AST));
				currentAST.root = port_clause_AST;
				currentAST.child = port_clause_AST!=null &&port_clause_AST.getFirstChild()!=null ?
					port_clause_AST.getFirstChild() : port_clause_AST;
				currentAST.advanceChildToEnd();
			}
			port_clause_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_104);
			} else {
			  throw ex;
			}
		}
		returnAST = port_clause_AST;
	}
	
	public final void index_specification() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST index_specification_AST = null;
		
		try {      // for error handling
			boolean synPredMatched356 = false;
			if (((_tokenSet_19.member(LA(1))) && (_tokenSet_105.member(LA(2))) && (_tokenSet_106.member(LA(3))))) {
				int _m356 = mark();
				synPredMatched356 = true;
				inputState.guessing++;
				try {
					{
					discrete_range();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched356 = false;
				}
				rewind(_m356);
inputState.guessing--;
			}
			if ( synPredMatched356 ) {
				discrete_range();
				astFactory.addASTChild(currentAST, returnAST);
				index_specification_AST = (AST)currentAST.root;
			}
			else if ((_tokenSet_19.member(LA(1))) && (_tokenSet_54.member(LA(2))) && (_tokenSet_106.member(LA(3)))) {
				expression();
				astFactory.addASTChild(currentAST, returnAST);
				index_specification_AST = (AST)currentAST.root;
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_28);
			} else {
			  throw ex;
			}
		}
		returnAST = index_specification_AST;
	}
	
	public final void block_statement_part() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST block_statement_part_AST = null;
		
		try {      // for error handling
			{
			_loop132:
			do {
				if ((_tokenSet_58.member(LA(1)))) {
					architecture_statement();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop132;
				}
				
			} while (true);
			}
			block_statement_part_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_3);
			} else {
			  throw ex;
			}
		}
		returnAST = block_statement_part_AST;
	}
	
	public final void branch_quantity_declaration() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST branch_quantity_declaration_AST = null;
		
		try {      // for error handling
			AST tmp219_AST = null;
			tmp219_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp219_AST);
			match(QUANTITY);
			{
			boolean synPredMatched136 = false;
			if ((((LA(1) >= BASIC_IDENTIFIER && LA(1) <= REFERENCE)) && (_tokenSet_107.member(LA(2))) && (_tokenSet_19.member(LA(3))))) {
				int _m136 = mark();
				synPredMatched136 = true;
				inputState.guessing++;
				try {
					{
					across_aspect();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched136 = false;
				}
				rewind(_m136);
inputState.guessing--;
			}
			if ( synPredMatched136 ) {
				across_aspect();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else if (((LA(1) >= BASIC_IDENTIFIER && LA(1) <= REFERENCE)) && (_tokenSet_108.member(LA(2))) && (_tokenSet_109.member(LA(3)))) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			{
			if (((LA(1) >= BASIC_IDENTIFIER && LA(1) <= REFERENCE)) && (_tokenSet_110.member(LA(2)))) {
				through_aspect();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else if (((LA(1) >= BASIC_IDENTIFIER && LA(1) <= REFERENCE)) && (_tokenSet_111.member(LA(2)))) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			terminal_aspect();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp220_AST = null;
			tmp220_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp220_AST);
			match(SEMI);
			branch_quantity_declaration_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_100);
			} else {
			  throw ex;
			}
		}
		returnAST = branch_quantity_declaration_AST;
	}
	
	public final void through_aspect() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST through_aspect_AST = null;
		
		try {      // for error handling
			identifier_list();
			astFactory.addASTChild(currentAST, returnAST);
			{
			switch ( LA(1)) {
			case TOLERANCE:
			{
				tolerance_aspect();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case VARASGN:
			case THROUGH:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			switch ( LA(1)) {
			case VARASGN:
			{
				AST tmp221_AST = null;
				tmp221_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp221_AST);
				match(VARASGN);
				expression();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case THROUGH:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			AST tmp222_AST = null;
			tmp222_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp222_AST);
			match(THROUGH);
			through_aspect_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_24);
			} else {
			  throw ex;
			}
		}
		returnAST = through_aspect_AST;
	}
	
	public final void terminal_aspect() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST terminal_aspect_AST = null;
		
		try {      // for error handling
			name();
			astFactory.addASTChild(currentAST, returnAST);
			{
			switch ( LA(1)) {
			case TO:
			{
				AST tmp223_AST = null;
				tmp223_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp223_AST);
				match(TO);
				name();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case SEMI:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			terminal_aspect_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_1);
			} else {
			  throw ex;
			}
		}
		returnAST = terminal_aspect_AST;
	}
	
	public final void break_element() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST break_element_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case FOR:
			{
				break_selector_clause();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			name();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp224_AST = null;
			tmp224_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp224_AST);
			match(ARROW);
			expression();
			astFactory.addASTChild(currentAST, returnAST);
			break_element_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_112);
			} else {
			  throw ex;
			}
		}
		returnAST = break_element_AST;
	}
	
	public final void break_selector_clause() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST break_selector_clause_AST = null;
		
		try {      // for error handling
			AST tmp225_AST = null;
			tmp225_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp225_AST);
			match(FOR);
			name();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp226_AST = null;
			tmp226_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp226_AST);
			match(USE);
			break_selector_clause_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_24);
			} else {
			  throw ex;
			}
		}
		returnAST = break_selector_clause_AST;
	}
	
	public final void break_statement() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST break_statement_AST = null;
		
		try {      // for error handling
			AST tmp227_AST = null;
			tmp227_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp227_AST);
			match(BREAK);
			{
			switch ( LA(1)) {
			case FOR:
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			{
				break_list();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case SEMI:
			case WHEN:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			switch ( LA(1)) {
			case WHEN:
			{
				AST tmp228_AST = null;
				tmp228_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp228_AST);
				match(WHEN);
				condition();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case SEMI:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			AST tmp229_AST = null;
			tmp229_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp229_AST);
			match(SEMI);
			if ( inputState.guessing==0 ) {
				break_statement_AST = (AST)currentAST.root;
				break_statement_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(BREAKSTATEMENT,"BreakStatement")).add(break_statement_AST));
				currentAST.root = break_statement_AST;
				currentAST.child = break_statement_AST!=null &&break_statement_AST.getFirstChild()!=null ?
					break_statement_AST.getFirstChild() : break_statement_AST;
				currentAST.advanceChildToEnd();
			}
			break_statement_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_86);
			} else {
			  throw ex;
			}
		}
		returnAST = break_statement_AST;
	}
	
	public final void case_statement() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST case_statement_AST = null;
		
		try {      // for error handling
			label_colon_wrap();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp230_AST = null;
			tmp230_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp230_AST);
			match(CASE);
			expression();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp231_AST = null;
			tmp231_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp231_AST);
			match(IS);
			{
			int _cnt149=0;
			_loop149:
			do {
				if ((LA(1)==WHEN)) {
					case_statement_alternative();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					if ( _cnt149>=1 ) { break _loop149; } else {throw new NoViableAltException(LT(1), getFilename());}
				}
				
				_cnt149++;
			} while (true);
			}
			case_end();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				case_statement_AST = (AST)currentAST.root;
				case_statement_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(CASESTATEMENT,"CaseStatement")).add(case_statement_AST));
				currentAST.root = case_statement_AST;
				currentAST.child = case_statement_AST!=null &&case_statement_AST.getFirstChild()!=null ?
					case_statement_AST.getFirstChild() : case_statement_AST;
				currentAST.advanceChildToEnd();
			}
			case_statement_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_86);
			} else {
			  throw ex;
			}
		}
		returnAST = case_statement_AST;
	}
	
	public final void label_colon_wrap() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST label_colon_wrap_AST = null;
		
		try {      // for error handling
			{
			if (((LA(1) >= BASIC_IDENTIFIER && LA(1) <= REFERENCE)) && (LA(2)==COLON)) {
				label_colon();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else if ((_tokenSet_113.member(LA(1))) && (_tokenSet_114.member(LA(2)))) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			if ( inputState.guessing==0 ) {
				label_colon_wrap_AST = (AST)currentAST.root;
				label_colon_wrap_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(LABELCOLONWRAP,"label_colon_wrap")).add(label_colon_wrap_AST));
				currentAST.root = label_colon_wrap_AST;
				currentAST.child = label_colon_wrap_AST!=null &&label_colon_wrap_AST.getFirstChild()!=null ?
					label_colon_wrap_AST.getFirstChild() : label_colon_wrap_AST;
				currentAST.advanceChildToEnd();
			}
			label_colon_wrap_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_113);
			} else {
			  throw ex;
			}
		}
		returnAST = label_colon_wrap_AST;
	}
	
	public final void case_statement_alternative() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST case_statement_alternative_AST = null;
		
		try {      // for error handling
			AST tmp232_AST = null;
			tmp232_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp232_AST);
			match(WHEN);
			choices();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp233_AST = null;
			tmp233_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp233_AST);
			match(ARROW);
			sequence_of_statements();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				case_statement_alternative_AST = (AST)currentAST.root;
				case_statement_alternative_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(CASESTATEMENTALTERNATIVE,"CaseStatementAlternative")).add(case_statement_alternative_AST));
				currentAST.root = case_statement_alternative_AST;
				currentAST.child = case_statement_alternative_AST!=null &&case_statement_alternative_AST.getFirstChild()!=null ?
					case_statement_alternative_AST.getFirstChild() : case_statement_alternative_AST;
				currentAST.advanceChildToEnd();
			}
			case_statement_alternative_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_115);
			} else {
			  throw ex;
			}
		}
		returnAST = case_statement_alternative_AST;
	}
	
	public final void case_end() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST case_end_AST = null;
		
		try {      // for error handling
			AST tmp234_AST = null;
			tmp234_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp234_AST);
			match(END);
			AST tmp235_AST = null;
			tmp235_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp235_AST);
			match(CASE);
			{
			switch ( LA(1)) {
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			{
				identifier();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case SEMI:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			AST tmp236_AST = null;
			tmp236_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp236_AST);
			match(SEMI);
			if ( inputState.guessing==0 ) {
				case_end_AST = (AST)currentAST.root;
				case_end_AST  = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(CASEEND,"CaseEnd")).add(case_end_AST));
				currentAST.root = case_end_AST;
				currentAST.child = case_end_AST!=null &&case_end_AST.getFirstChild()!=null ?
					case_end_AST.getFirstChild() : case_end_AST;
				currentAST.advanceChildToEnd();
			}
			case_end_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_86);
			} else {
			  throw ex;
			}
		}
		returnAST = case_end_AST;
	}
	
	public final void choices() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST choices_AST = null;
		
		try {      // for error handling
			choice();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop157:
			do {
				if ((LA(1)==BAR)) {
					AST tmp237_AST = null;
					tmp237_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp237_AST);
					match(BAR);
					choice();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop157;
				}
				
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				choices_AST = (AST)currentAST.root;
				choices_AST  = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(CHOICES,"Choices")).add(choices_AST));
				currentAST.root = choices_AST;
				currentAST.child = choices_AST!=null &&choices_AST.getFirstChild()!=null ?
					choices_AST.getFirstChild() : choices_AST;
				currentAST.advanceChildToEnd();
			}
			choices_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_116);
			} else {
			  throw ex;
			}
		}
		returnAST = choices_AST;
	}
	
	public final void sequence_of_statements() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST sequence_of_statements_AST = null;
		
		try {      // for error handling
			{
			_loop596:
			do {
				if ((_tokenSet_117.member(LA(1)))) {
					sequential_statement();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop596;
				}
				
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				sequence_of_statements_AST = (AST)currentAST.root;
				sequence_of_statements_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(SEQUENCEOFSTATEMENTS,"SequenceOfStatements")).add(sequence_of_statements_AST));
				currentAST.root = sequence_of_statements_AST;
				currentAST.child = sequence_of_statements_AST!=null &&sequence_of_statements_AST.getFirstChild()!=null ?
					sequence_of_statements_AST.getFirstChild() : sequence_of_statements_AST;
				currentAST.advanceChildToEnd();
			}
			sequence_of_statements_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_118);
			} else {
			  throw ex;
			}
		}
		returnAST = sequence_of_statements_AST;
	}
	
	public final void choice() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST choice_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case DECIMAL_LITERAL:
			case BASED_LITERAL:
			case LPAREN:
			case CHARACTER_LITERAL:
			case STRING_LITERAL:
			case NEW:
			case PLUS:
			case MINUS:
			case ABS:
			case NOT:
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			case NULLTOK:
			case BIT_STRING_LITERAL:
			{
				expression();
				astFactory.addASTChild(currentAST, returnAST);
				{
				switch ( LA(1)) {
				case TO:
				case DOWNTO:
				{
					direction();
					astFactory.addASTChild(currentAST, returnAST);
					expression();
					astFactory.addASTChild(currentAST, returnAST);
					break;
				}
				case COMMA:
				case SEMI:
				case ARROW:
				case BAR:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				choice_AST = (AST)currentAST.root;
				break;
			}
			case OTHERS:
			{
				AST tmp238_AST = null;
				tmp238_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp238_AST);
				match(OTHERS);
				choice_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_119);
			} else {
			  throw ex;
			}
		}
		returnAST = choice_AST;
	}
	
	public final void direction() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST direction_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case TO:
			{
				AST tmp239_AST = null;
				tmp239_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp239_AST);
				match(TO);
				direction_AST = (AST)currentAST.root;
				break;
			}
			case DOWNTO:
			{
				AST tmp240_AST = null;
				tmp240_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp240_AST);
				match(DOWNTO);
				direction_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_19);
			} else {
			  throw ex;
			}
		}
		returnAST = direction_AST;
	}
	
	public final void component_configuration() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST component_configuration_AST = null;
		
		try {      // for error handling
			AST tmp241_AST = null;
			tmp241_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp241_AST);
			match(FOR);
			component_specification();
			astFactory.addASTChild(currentAST, returnAST);
			{
			switch ( LA(1)) {
			case SEMI:
			case USE:
			case GENERIC:
			case PORT:
			{
				binding_indication();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp242_AST = null;
				tmp242_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp242_AST);
				match(SEMI);
				break;
			}
			case END:
			case FOR:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			switch ( LA(1)) {
			case FOR:
			{
				block_configuration();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case END:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			AST tmp243_AST = null;
			tmp243_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp243_AST);
			match(END);
			AST tmp244_AST = null;
			tmp244_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp244_AST);
			match(FOR);
			AST tmp245_AST = null;
			tmp245_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp245_AST);
			match(SEMI);
			component_configuration_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_93);
			} else {
			  throw ex;
			}
		}
		returnAST = component_configuration_AST;
	}
	
	public final void component_specification() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST component_specification_AST = null;
		
		try {      // for error handling
			instantiation_list();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp246_AST = null;
			tmp246_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp246_AST);
			match(COLON);
			name();
			astFactory.addASTChild(currentAST, returnAST);
			component_specification_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_120);
			} else {
			  throw ex;
			}
		}
		returnAST = component_specification_AST;
	}
	
	public final void component_is_dummy() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST component_is_dummy_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case IS:
			{
				AST tmp247_AST = null;
				tmp247_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp247_AST);
				match(IS);
				break;
			}
			case END:
			case GENERIC:
			case PORT:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				component_is_dummy_AST = (AST)currentAST.root;
				component_is_dummy_AST  = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(COMPONENTISDUMMY,"ComponentIsDummy")).add(component_is_dummy_AST));
				currentAST.root = component_is_dummy_AST;
				currentAST.child = component_is_dummy_AST!=null &&component_is_dummy_AST.getFirstChild()!=null ?
					component_is_dummy_AST.getFirstChild() : component_is_dummy_AST;
				currentAST.advanceChildToEnd();
			}
			component_is_dummy_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_121);
			} else {
			  throw ex;
			}
		}
		returnAST = component_is_dummy_AST;
	}
	
	public final void entity_header() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST entity_header_AST = null;
		
		try {      // for error handling
			generic_clause_dummy();
			astFactory.addASTChild(currentAST, returnAST);
			port_clause_dummy();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				entity_header_AST = (AST)currentAST.root;
				entity_header_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(ENTITYHEADER,"EntityHeader")).add(entity_header_AST));
				currentAST.root = entity_header_AST;
				currentAST.child = entity_header_AST!=null &&entity_header_AST.getFirstChild()!=null ?
					entity_header_AST.getFirstChild() : entity_header_AST;
				currentAST.advanceChildToEnd();
			}
			entity_header_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_122);
			} else {
			  throw ex;
			}
		}
		returnAST = entity_header_AST;
	}
	
	public final void instantiation_list() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST instantiation_list_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			{
				identifier();
				astFactory.addASTChild(currentAST, returnAST);
				{
				_loop367:
				do {
					if ((LA(1)==COMMA)) {
						AST tmp248_AST = null;
						tmp248_AST = astFactory.create(LT(1));
						astFactory.addASTChild(currentAST, tmp248_AST);
						match(COMMA);
						identifier();
						astFactory.addASTChild(currentAST, returnAST);
					}
					else {
						break _loop367;
					}
					
				} while (true);
				}
				instantiation_list_AST = (AST)currentAST.root;
				break;
			}
			case OTHERS:
			{
				AST tmp249_AST = null;
				tmp249_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp249_AST);
				match(OTHERS);
				instantiation_list_AST = (AST)currentAST.root;
				break;
			}
			case ALL:
			{
				AST tmp250_AST = null;
				tmp250_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp250_AST);
				match(ALL);
				instantiation_list_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_123);
			} else {
			  throw ex;
			}
		}
		returnAST = instantiation_list_AST;
	}
	
	public final void composite_nature_definition() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST composite_nature_definition_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case ARRAY:
			{
				array_nature_definition();
				astFactory.addASTChild(currentAST, returnAST);
				composite_nature_definition_AST = (AST)currentAST.root;
				break;
			}
			case RECORD:
			{
				record_nature_definition();
				astFactory.addASTChild(currentAST, returnAST);
				composite_nature_definition_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_1);
			} else {
			  throw ex;
			}
		}
		returnAST = composite_nature_definition_AST;
	}
	
	public final void record_nature_definition() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST record_nature_definition_AST = null;
		
		try {      // for error handling
			AST tmp251_AST = null;
			tmp251_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp251_AST);
			match(RECORD);
			{
			int _cnt567=0;
			_loop567:
			do {
				if (((LA(1) >= BASIC_IDENTIFIER && LA(1) <= REFERENCE))) {
					nature_element_declaration();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					if ( _cnt567>=1 ) { break _loop567; } else {throw new NoViableAltException(LT(1), getFilename());}
				}
				
				_cnt567++;
			} while (true);
			}
			AST tmp252_AST = null;
			tmp252_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp252_AST);
			match(END);
			AST tmp253_AST = null;
			tmp253_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp253_AST);
			match(RECORD);
			{
			switch ( LA(1)) {
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			{
				identifier();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case SEMI:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			record_nature_definition_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_1);
			} else {
			  throw ex;
			}
		}
		returnAST = record_nature_definition_AST;
	}
	
	public final void composite_type_definition() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST composite_type_definition_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case ARRAY:
			{
				array_type_definition();
				astFactory.addASTChild(currentAST, returnAST);
				composite_type_definition_AST = (AST)currentAST.root;
				break;
			}
			case RECORD:
			{
				record_type_definition();
				astFactory.addASTChild(currentAST, returnAST);
				composite_type_definition_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_1);
			} else {
			  throw ex;
			}
		}
		returnAST = composite_type_definition_AST;
	}
	
	public final void record_type_definition() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST record_type_definition_AST = null;
		
		try {      // for error handling
			AST tmp254_AST = null;
			tmp254_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp254_AST);
			match(RECORD);
			record_element();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp255_AST = null;
			tmp255_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp255_AST);
			match(END);
			AST tmp256_AST = null;
			tmp256_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp256_AST);
			match(RECORD);
			{
			switch ( LA(1)) {
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			{
				identifier();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case SEMI:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				record_type_definition_AST = (AST)currentAST.root;
				record_type_definition_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(RECORDTYPEDEFINITION,"RecordTypeDefinition")).add(record_type_definition_AST));
				currentAST.root = record_type_definition_AST;
				currentAST.child = record_type_definition_AST!=null &&record_type_definition_AST.getFirstChild()!=null ?
					record_type_definition_AST.getFirstChild() : record_type_definition_AST;
				currentAST.advanceChildToEnd();
			}
			record_type_definition_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_1);
			} else {
			  throw ex;
			}
		}
		returnAST = record_type_definition_AST;
	}
	
	public final void postponedQ() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST postponedQ_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case POSTPONED:
			{
				AST tmp257_AST = null;
				tmp257_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp257_AST);
				match(POSTPONED);
				break;
			}
			case LPAREN:
			case PROCESS:
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			case WITH:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				postponedQ_AST = (AST)currentAST.root;
				postponedQ_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(POSTPONEDQ,"postponedQ")).add(postponedQ_AST));
				currentAST.root = postponedQ_AST;
				currentAST.child = postponedQ_AST!=null &&postponedQ_AST.getFirstChild()!=null ?
					postponedQ_AST.getFirstChild() : postponedQ_AST;
				currentAST.advanceChildToEnd();
			}
			postponedQ_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_124);
			} else {
			  throw ex;
			}
		}
		returnAST = postponedQ_AST;
	}
	
	public final void condition_clause() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST condition_clause_AST = null;
		
		try {      // for error handling
			AST tmp258_AST = null;
			tmp258_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp258_AST);
			match(UNTIL);
			condition();
			astFactory.addASTChild(currentAST, returnAST);
			condition_clause_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_125);
			} else {
			  throw ex;
			}
		}
		returnAST = condition_clause_AST;
	}
	
	public final void target() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST target_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			{
				name();
				astFactory.addASTChild(currentAST, returnAST);
				target_AST = (AST)currentAST.root;
				break;
			}
			case LPAREN:
			{
				aggregate();
				astFactory.addASTChild(currentAST, returnAST);
				if ( inputState.guessing==0 ) {
					target_AST = (AST)currentAST.root;
					target_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(TARGET,"Target")).add(target_AST));
					currentAST.root = target_AST;
					currentAST.child = target_AST!=null &&target_AST.getFirstChild()!=null ?
						target_AST.getFirstChild() : target_AST;
					currentAST.advanceChildToEnd();
				}
				target_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_126);
			} else {
			  throw ex;
			}
		}
		returnAST = target_AST;
	}
	
	public final void opts() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST opts_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case GUARDED:
			{
				AST tmp259_AST = null;
				tmp259_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp259_AST);
				match(GUARDED);
				break;
			}
			case DECIMAL_LITERAL:
			case BASED_LITERAL:
			case LPAREN:
			case CHARACTER_LITERAL:
			case STRING_LITERAL:
			case NEW:
			case TRANSPORT:
			case REJECT:
			case INERTIAL:
			case PLUS:
			case MINUS:
			case ABS:
			case NOT:
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			case NULLTOK:
			case BIT_STRING_LITERAL:
			case UNAFFECTED:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			switch ( LA(1)) {
			case TRANSPORT:
			case REJECT:
			case INERTIAL:
			{
				delay_mechanism();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case DECIMAL_LITERAL:
			case BASED_LITERAL:
			case LPAREN:
			case CHARACTER_LITERAL:
			case STRING_LITERAL:
			case NEW:
			case PLUS:
			case MINUS:
			case ABS:
			case NOT:
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			case NULLTOK:
			case BIT_STRING_LITERAL:
			case UNAFFECTED:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				opts_AST = (AST)currentAST.root;
				opts_AST= (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(OPTS,"opts")).add(opts_AST));
				currentAST.root = opts_AST;
				currentAST.child = opts_AST!=null &&opts_AST.getFirstChild()!=null ?
					opts_AST.getFirstChild() : opts_AST;
				currentAST.advanceChildToEnd();
			}
			opts_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_127);
			} else {
			  throw ex;
			}
		}
		returnAST = opts_AST;
	}
	
	public final void conditional_waveforms() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST conditional_waveforms_AST = null;
		
		try {      // for error handling
			waveform();
			astFactory.addASTChild(currentAST, returnAST);
			{
			boolean synPredMatched192 = false;
			if (((LA(1)==WHEN) && (_tokenSet_19.member(LA(2))) && (_tokenSet_128.member(LA(3))))) {
				int _m192 = mark();
				synPredMatched192 = true;
				inputState.guessing++;
				try {
					{
					match(WHEN);
					condition();
					match(ELSE);
					}
				}
				catch (RecognitionException pe) {
					synPredMatched192 = false;
				}
				rewind(_m192);
inputState.guessing--;
			}
			if ( synPredMatched192 ) {
				conditional_waveforms_bi();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else if ((LA(1)==SEMI||LA(1)==WHEN) && (_tokenSet_129.member(LA(2))) && (_tokenSet_130.member(LA(3)))) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			{
			switch ( LA(1)) {
			case WHEN:
			{
				AST tmp260_AST = null;
				tmp260_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp260_AST);
				match(WHEN);
				condition();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case SEMI:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				conditional_waveforms_AST = (AST)currentAST.root;
				conditional_waveforms_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(CONDITIONALWAVEFORMS,"ConditionalWaveforms")).add(conditional_waveforms_AST));
				currentAST.root = conditional_waveforms_AST;
				currentAST.child = conditional_waveforms_AST!=null &&conditional_waveforms_AST.getFirstChild()!=null ?
					conditional_waveforms_AST.getFirstChild() : conditional_waveforms_AST;
				currentAST.advanceChildToEnd();
			}
			conditional_waveforms_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_1);
			} else {
			  throw ex;
			}
		}
		returnAST = conditional_waveforms_AST;
	}
	
	public final void waveform() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST waveform_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case DECIMAL_LITERAL:
			case BASED_LITERAL:
			case LPAREN:
			case CHARACTER_LITERAL:
			case STRING_LITERAL:
			case NEW:
			case PLUS:
			case MINUS:
			case ABS:
			case NOT:
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			case NULLTOK:
			case BIT_STRING_LITERAL:
			{
				waveform_element();
				astFactory.addASTChild(currentAST, returnAST);
				{
				_loop714:
				do {
					if ((LA(1)==COMMA)) {
						AST tmp261_AST = null;
						tmp261_AST = astFactory.create(LT(1));
						astFactory.addASTChild(currentAST, tmp261_AST);
						match(COMMA);
						waveform_element();
						astFactory.addASTChild(currentAST, returnAST);
					}
					else {
						break _loop714;
					}
					
				} while (true);
				}
				waveform_AST = (AST)currentAST.root;
				break;
			}
			case UNAFFECTED:
			{
				AST tmp262_AST = null;
				tmp262_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp262_AST);
				match(UNAFFECTED);
				waveform_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_131);
			} else {
			  throw ex;
			}
		}
		returnAST = waveform_AST;
	}
	
	public final void conditional_waveforms_bi() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST conditional_waveforms_bi_AST = null;
		
		try {      // for error handling
			AST tmp263_AST = null;
			tmp263_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp263_AST);
			match(WHEN);
			condition();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp264_AST = null;
			tmp264_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp264_AST);
			match(ELSE);
			waveform();
			astFactory.addASTChild(currentAST, returnAST);
			{
			boolean synPredMatched197 = false;
			if (((LA(1)==WHEN) && (_tokenSet_19.member(LA(2))) && (_tokenSet_128.member(LA(3))))) {
				int _m197 = mark();
				synPredMatched197 = true;
				inputState.guessing++;
				try {
					{
					match(WHEN);
					condition();
					match(ELSE);
					}
				}
				catch (RecognitionException pe) {
					synPredMatched197 = false;
				}
				rewind(_m197);
inputState.guessing--;
			}
			if ( synPredMatched197 ) {
				conditional_waveforms_bi();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else if ((LA(1)==SEMI||LA(1)==WHEN) && (_tokenSet_129.member(LA(2))) && (_tokenSet_130.member(LA(3)))) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			if ( inputState.guessing==0 ) {
				conditional_waveforms_bi_AST = (AST)currentAST.root;
				conditional_waveforms_bi_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(CONDITIONALWAVEFORMSBI,"ConditionalWaveformsBi")).add(conditional_waveforms_bi_AST));
				currentAST.root = conditional_waveforms_bi_AST;
				currentAST.child = conditional_waveforms_bi_AST!=null &&conditional_waveforms_bi_AST.getFirstChild()!=null ?
					conditional_waveforms_bi_AST.getFirstChild() : conditional_waveforms_bi_AST;
				currentAST.advanceChildToEnd();
			}
			conditional_waveforms_bi_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_131);
			} else {
			  throw ex;
			}
		}
		returnAST = conditional_waveforms_bi_AST;
	}
	
	public final void configuration_declaration() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST configuration_declaration_AST = null;
		
		try {      // for error handling
			AST tmp265_AST = null;
			tmp265_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp265_AST);
			match(CONFIGURATION);
			identifier();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp266_AST = null;
			tmp266_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp266_AST);
			match(OF);
			name();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp267_AST = null;
			tmp267_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp267_AST);
			match(IS);
			configuration_declarative_part();
			astFactory.addASTChild(currentAST, returnAST);
			block_configuration();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp268_AST = null;
			tmp268_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp268_AST);
			match(END);
			{
			switch ( LA(1)) {
			case CONFIGURATION:
			{
				AST tmp269_AST = null;
				tmp269_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp269_AST);
				match(CONFIGURATION);
				break;
			}
			case SEMI:
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			switch ( LA(1)) {
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			{
				identifier();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case SEMI:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			AST tmp270_AST = null;
			tmp270_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp270_AST);
			match(SEMI);
			configuration_declaration_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_55);
			} else {
			  throw ex;
			}
		}
		returnAST = configuration_declaration_AST;
	}
	
	public final void configuration_declarative_part() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST configuration_declarative_part_AST = null;
		
		try {      // for error handling
			{
			_loop204:
			do {
				if ((_tokenSet_132.member(LA(1)))) {
					configuration_declarative_item();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop204;
				}
				
			} while (true);
			}
			configuration_declarative_part_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_133);
			} else {
			  throw ex;
			}
		}
		returnAST = configuration_declarative_part_AST;
	}
	
	public final void configuration_declarative_item() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST configuration_declarative_item_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case USE:
			case CONTEXT:
			{
				use_clause();
				astFactory.addASTChild(currentAST, returnAST);
				configuration_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case ATTRIBUTE:
			{
				attribute_specification();
				astFactory.addASTChild(currentAST, returnAST);
				configuration_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case GROUP:
			{
				group_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				configuration_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_134);
			} else {
			  throw ex;
			}
		}
		returnAST = configuration_declarative_item_AST;
	}
	
	public final void var_assign() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST var_assign_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case VARASGN:
			{
				AST tmp271_AST = null;
				tmp271_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp271_AST);
				match(VARASGN);
				expression();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case SEMI:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				var_assign_AST = (AST)currentAST.root;
				var_assign_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(VARASSIGN,"VarAssign")).add(var_assign_AST));
				currentAST.root = var_assign_AST;
				currentAST.child = var_assign_AST!=null &&var_assign_AST.getFirstChild()!=null ?
					var_assign_AST.getFirstChild() : var_assign_AST;
				currentAST.advanceChildToEnd();
			}
			var_assign_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				var_assign_exception(ex);
			} else {
				throw ex;
			}
		}
		returnAST = var_assign_AST;
	}
	
	public final void index_constraint() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST index_constraint_AST = null;
		
		try {      // for error handling
			AST tmp272_AST = null;
			tmp272_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp272_AST);
			match(LPAREN);
			discrete_range();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop353:
			do {
				if ((LA(1)==COMMA)) {
					AST tmp273_AST = null;
					tmp273_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp273_AST);
					match(COMMA);
					discrete_range();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop353;
				}
				
			} while (true);
			}
			AST tmp274_AST = null;
			tmp274_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp274_AST);
			match(RPAREN);
			if ( inputState.guessing==0 ) {
				index_constraint_AST = (AST)currentAST.root;
				index_constraint_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(INDEXCONSTRAINT,"IndexConstraint")).add(index_constraint_AST));
				currentAST.root = index_constraint_AST;
				currentAST.child = index_constraint_AST!=null &&index_constraint_AST.getFirstChild()!=null ?
					index_constraint_AST.getFirstChild() : index_constraint_AST;
				currentAST.advanceChildToEnd();
			}
			index_constraint_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_135);
			} else {
			  throw ex;
			}
		}
		returnAST = index_constraint_AST;
	}
	
	public final void constraint() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST constraint_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case RANGETOK:
			{
				range_constraint();
				astFactory.addASTChild(currentAST, returnAST);
				constraint_AST = (AST)currentAST.root;
				break;
			}
			case LPAREN:
			{
				index_constraint();
				astFactory.addASTChild(currentAST, returnAST);
				constraint_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_21);
			} else {
			  throw ex;
			}
		}
		returnAST = constraint_AST;
	}
	
	public final void range_constraint() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST range_constraint_AST = null;
		
		try {      // for error handling
			AST tmp275_AST = null;
			tmp275_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp275_AST);
			match(RANGETOK);
			range();
			astFactory.addASTChild(currentAST, returnAST);
			range_constraint_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_21);
			} else {
			  throw ex;
			}
		}
		returnAST = range_constraint_AST;
	}
	
	public final void context_clause() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST context_clause_AST = null;
		
		try {      // for error handling
			{
			_loop213:
			do {
				if ((LA(1)==USE||LA(1)==LIBRARY||LA(1)==CONTEXT)) {
					context_item();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop213;
				}
				
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				context_clause_AST = (AST)currentAST.root;
				context_clause_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(CONTEXTCLAUSE,"ContextClause")).add(context_clause_AST));
				currentAST.root = context_clause_AST;
				currentAST.child = context_clause_AST!=null &&context_clause_AST.getFirstChild()!=null ?
					context_clause_AST.getFirstChild() : context_clause_AST;
				currentAST.advanceChildToEnd();
			}
			context_clause_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_136);
			} else {
			  throw ex;
			}
		}
		returnAST = context_clause_AST;
	}
	
	public final void context_item() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST context_item_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case LIBRARY:
			{
				library_clause();
				astFactory.addASTChild(currentAST, returnAST);
				context_item_AST = (AST)currentAST.root;
				break;
			}
			case USE:
			case CONTEXT:
			{
				use_clause();
				astFactory.addASTChild(currentAST, returnAST);
				context_item_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_137);
			} else {
			  throw ex;
			}
		}
		returnAST = context_item_AST;
	}
	
	public final void library_clause() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST library_clause_AST = null;
		
		try {      // for error handling
			AST tmp276_AST = null;
			tmp276_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp276_AST);
			match(LIBRARY);
			logical_name_list();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp277_AST = null;
			tmp277_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp277_AST);
			match(SEMI);
			if ( inputState.guessing==0 ) {
				library_clause_AST = (AST)currentAST.root;
				library_clause_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(LIBRARYCLAUSE,"LibraryClause")).add(library_clause_AST));
				currentAST.root = library_clause_AST;
				currentAST.child = library_clause_AST!=null &&library_clause_AST.getFirstChild()!=null ?
					library_clause_AST.getFirstChild() : library_clause_AST;
				currentAST.advanceChildToEnd();
			}
			library_clause_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_137);
			} else {
			  throw ex;
			}
		}
		returnAST = library_clause_AST;
	}
	
	public final void delay_mechanism() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST delay_mechanism_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case TRANSPORT:
			{
				AST tmp278_AST = null;
				tmp278_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp278_AST);
				match(TRANSPORT);
				delay_mechanism_AST = (AST)currentAST.root;
				break;
			}
			case REJECT:
			case INERTIAL:
			{
				{
				switch ( LA(1)) {
				case REJECT:
				{
					AST tmp279_AST = null;
					tmp279_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp279_AST);
					match(REJECT);
					expression();
					astFactory.addASTChild(currentAST, returnAST);
					break;
				}
				case INERTIAL:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				AST tmp280_AST = null;
				tmp280_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp280_AST);
				match(INERTIAL);
				delay_mechanism_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_127);
			} else {
			  throw ex;
			}
		}
		returnAST = delay_mechanism_AST;
	}
	
	public final void source_text() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST source_text_AST = null;
		
		try {      // for error handling
			{
			_loop219:
			do {
				if ((_tokenSet_137.member(LA(1)))) {
					design_unit();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop219;
				}
				
			} while (true);
			}
			AST tmp281_AST = null;
			tmp281_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp281_AST);
			match(Token.EOF_TYPE);
			if ( inputState.guessing==0 ) {
				source_text_AST = (AST)currentAST.root;
				source_text_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(ROOT,"Root")).add(source_text_AST));
				currentAST.root = source_text_AST;
				currentAST.child = source_text_AST!=null &&source_text_AST.getFirstChild()!=null ?
					source_text_AST.getFirstChild() : source_text_AST;
				currentAST.advanceChildToEnd();
			}
			source_text_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_138);
			} else {
			  throw ex;
			}
		}
		returnAST = source_text_AST;
	}
	
	public final void design_unit() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST design_unit_AST = null;
		
		try {      // for error handling
			context_clause();
			astFactory.addASTChild(currentAST, returnAST);
			library_unit();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				design_unit_AST = (AST)currentAST.root;
				design_unit_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(DESIGNUNIT,"DesignUnit")).add(design_unit_AST));
				currentAST.root = design_unit_AST;
				currentAST.child = design_unit_AST!=null &&design_unit_AST.getFirstChild()!=null ?
					design_unit_AST.getFirstChild() : design_unit_AST;
				currentAST.advanceChildToEnd();
			}
			design_unit_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_55);
			} else {
			  throw ex;
			}
		}
		returnAST = design_unit_AST;
	}
	
	public final void source_text_name() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST source_text_name_AST = null;
		
		try {      // for error handling
			name();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				source_text_name_AST = (AST)currentAST.root;
				source_text_name_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(ROOT,"Root")).add(source_text_name_AST));
				currentAST.root = source_text_name_AST;
				currentAST.child = source_text_name_AST!=null &&source_text_name_AST.getFirstChild()!=null ?
					source_text_name_AST.getFirstChild() : source_text_name_AST;
				currentAST.advanceChildToEnd();
			}
			source_text_name_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_138);
			} else {
			  throw ex;
			}
		}
		returnAST = source_text_name_AST;
	}
	
	public final void library_unit() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST library_unit_AST = null;
		
		try {      // for error handling
			{
			boolean synPredMatched411 = false;
			if (((LA(1)==ARCHITECTURE||LA(1)==PACKAGE) && (_tokenSet_139.member(LA(2))) && (_tokenSet_140.member(LA(3))))) {
				int _m411 = mark();
				synPredMatched411 = true;
				inputState.guessing++;
				try {
					{
					switch ( LA(1)) {
					case ARCHITECTURE:
					{
						match(ARCHITECTURE);
						break;
					}
					case PACKAGE:
					{
						match(PACKAGE);
						match(BODY);
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
				}
				catch (RecognitionException pe) {
					synPredMatched411 = false;
				}
				rewind(_m411);
inputState.guessing--;
			}
			if ( synPredMatched411 ) {
				secondary_unit();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else if ((LA(1)==CONFIGURATION||LA(1)==ENTITY||LA(1)==PACKAGE) && ((LA(2) >= BASIC_IDENTIFIER && LA(2) <= REFERENCE)) && (LA(3)==IS||LA(3)==OF)) {
				primary_unit();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			if ( inputState.guessing==0 ) {
				library_unit_AST = (AST)currentAST.root;
				library_unit_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(LIBRARYUNIT,"LibraryUnit")).add(library_unit_AST));
				currentAST.root = library_unit_AST;
				currentAST.child = library_unit_AST!=null &&library_unit_AST.getFirstChild()!=null ?
					library_unit_AST.getFirstChild() : library_unit_AST;
				currentAST.advanceChildToEnd();
			}
			library_unit_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_55);
			} else {
			  throw ex;
			}
		}
		returnAST = library_unit_AST;
	}
	
	public final void designator() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST designator_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			{
				identifier();
				astFactory.addASTChild(currentAST, returnAST);
				designator_AST = (AST)currentAST.root;
				break;
			}
			case STRING_LITERAL:
			{
				AST tmp282_AST = null;
				tmp282_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp282_AST);
				match(STRING_LITERAL);
				designator_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_141);
			} else {
			  throw ex;
			}
		}
		returnAST = designator_AST;
	}
	
	public final void guarded_signal_specification() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST guarded_signal_specification_AST = null;
		
		try {      // for error handling
			signal_list();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp283_AST = null;
			tmp283_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp283_AST);
			match(COLON);
			name();
			astFactory.addASTChild(currentAST, returnAST);
			guarded_signal_specification_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_142);
			} else {
			  throw ex;
			}
		}
		returnAST = guarded_signal_specification_AST;
	}
	
	public final void discrete_range() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST discrete_range_AST = null;
		
		try {      // for error handling
			{
			boolean synPredMatched228 = false;
			if (((_tokenSet_19.member(LA(1))) && (_tokenSet_143.member(LA(2))) && (_tokenSet_144.member(LA(3))))) {
				int _m228 = mark();
				synPredMatched228 = true;
				inputState.guessing++;
				try {
					{
					range();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched228 = false;
				}
				rewind(_m228);
inputState.guessing--;
			}
			if ( synPredMatched228 ) {
				range();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else if ((_tokenSet_39.member(LA(1))) && (_tokenSet_145.member(LA(2))) && (_tokenSet_146.member(LA(3)))) {
				subtype_indication();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			if ( inputState.guessing==0 ) {
				discrete_range_AST = (AST)currentAST.root;
				discrete_range_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(DISCRETERANGE,"DiscreteRange")).add(discrete_range_AST));
				currentAST.root = discrete_range_AST;
				currentAST.child = discrete_range_AST!=null &&discrete_range_AST.getFirstChild()!=null ?
					discrete_range_AST.getFirstChild() : discrete_range_AST;
				currentAST.advanceChildToEnd();
			}
			discrete_range_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_147);
			} else {
			  throw ex;
			}
		}
		returnAST = discrete_range_AST;
	}
	
	public final void range() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST range_AST = null;
		
		try {      // for error handling
			{
			boolean synPredMatched563 = false;
			if (((_tokenSet_19.member(LA(1))) && (_tokenSet_148.member(LA(2))) && (_tokenSet_35.member(LA(3))))) {
				int _m563 = mark();
				synPredMatched563 = true;
				inputState.guessing++;
				try {
					{
					simple_expression();
					direction();
					simple_expression();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched563 = false;
				}
				rewind(_m563);
inputState.guessing--;
			}
			if ( synPredMatched563 ) {
				simple_expression();
				astFactory.addASTChild(currentAST, returnAST);
				direction();
				astFactory.addASTChild(currentAST, returnAST);
				simple_expression();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else if (((LA(1) >= BASIC_IDENTIFIER && LA(1) <= REFERENCE)) && (_tokenSet_149.member(LA(2))) && (_tokenSet_52.member(LA(3)))) {
				name();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			if ( inputState.guessing==0 ) {
				range_AST = (AST)currentAST.root;
				range_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(RANGE,"Range")).add(range_AST));
				currentAST.root = range_AST;
				currentAST.child = range_AST!=null &&range_AST.getFirstChild()!=null ?
					range_AST.getFirstChild() : range_AST;
				currentAST.advanceChildToEnd();
			}
			range_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_21);
			} else {
			  throw ex;
			}
		}
		returnAST = range_AST;
	}
	
	public final void element_declaration() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST element_declaration_AST = null;
		
		try {      // for error handling
			identifier_list();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp284_AST = null;
			tmp284_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp284_AST);
			match(COLON);
			element_subtype_definition();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp285_AST = null;
			tmp285_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp285_AST);
			match(SEMI);
			if ( inputState.guessing==0 ) {
				element_declaration_AST = (AST)currentAST.root;
				element_declaration_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(ELEMENTDECLARATION,"ElementDeclaration")).add(element_declaration_AST));
				currentAST.root = element_declaration_AST;
				currentAST.child = element_declaration_AST!=null &&element_declaration_AST.getFirstChild()!=null ?
					element_declaration_AST.getFirstChild() : element_declaration_AST;
				currentAST.advanceChildToEnd();
			}
			element_declaration_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_91);
			} else {
			  throw ex;
			}
		}
		returnAST = element_declaration_AST;
	}
	
	public final void element_subtype_definition() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST element_subtype_definition_AST = null;
		
		try {      // for error handling
			subtype_indication();
			astFactory.addASTChild(currentAST, returnAST);
			element_subtype_definition_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_1);
			} else {
			  throw ex;
			}
		}
		returnAST = element_subtype_definition_AST;
	}
	
	public final void element_subnature_definition() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST element_subnature_definition_AST = null;
		
		try {      // for error handling
			subnature_indication();
			astFactory.addASTChild(currentAST, returnAST);
			element_subnature_definition_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_91);
			} else {
			  throw ex;
			}
		}
		returnAST = element_subnature_definition_AST;
	}
	
	public final void entity_class() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST entity_class_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case ENTITY:
			{
				AST tmp286_AST = null;
				tmp286_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp286_AST);
				match(ENTITY);
				entity_class_AST = (AST)currentAST.root;
				break;
			}
			case ARCHITECTURE:
			{
				AST tmp287_AST = null;
				tmp287_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp287_AST);
				match(ARCHITECTURE);
				entity_class_AST = (AST)currentAST.root;
				break;
			}
			case CONFIGURATION:
			{
				AST tmp288_AST = null;
				tmp288_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp288_AST);
				match(CONFIGURATION);
				entity_class_AST = (AST)currentAST.root;
				break;
			}
			case PROCEDURE:
			{
				AST tmp289_AST = null;
				tmp289_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp289_AST);
				match(PROCEDURE);
				entity_class_AST = (AST)currentAST.root;
				break;
			}
			case FUNCTION:
			{
				AST tmp290_AST = null;
				tmp290_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp290_AST);
				match(FUNCTION);
				entity_class_AST = (AST)currentAST.root;
				break;
			}
			case PACKAGE:
			{
				AST tmp291_AST = null;
				tmp291_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp291_AST);
				match(PACKAGE);
				entity_class_AST = (AST)currentAST.root;
				break;
			}
			case TYPE:
			{
				AST tmp292_AST = null;
				tmp292_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp292_AST);
				match(TYPE);
				entity_class_AST = (AST)currentAST.root;
				break;
			}
			case SUBTYPE:
			{
				AST tmp293_AST = null;
				tmp293_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp293_AST);
				match(SUBTYPE);
				entity_class_AST = (AST)currentAST.root;
				break;
			}
			case CONSTANT:
			{
				AST tmp294_AST = null;
				tmp294_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp294_AST);
				match(CONSTANT);
				entity_class_AST = (AST)currentAST.root;
				break;
			}
			case SIGNAL:
			{
				AST tmp295_AST = null;
				tmp295_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp295_AST);
				match(SIGNAL);
				entity_class_AST = (AST)currentAST.root;
				break;
			}
			case VARIABLE:
			{
				AST tmp296_AST = null;
				tmp296_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp296_AST);
				match(VARIABLE);
				entity_class_AST = (AST)currentAST.root;
				break;
			}
			case COMPONENT:
			{
				AST tmp297_AST = null;
				tmp297_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp297_AST);
				match(COMPONENT);
				entity_class_AST = (AST)currentAST.root;
				break;
			}
			case LABEL:
			{
				AST tmp298_AST = null;
				tmp298_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp298_AST);
				match(LABEL);
				entity_class_AST = (AST)currentAST.root;
				break;
			}
			case LITERAL:
			{
				AST tmp299_AST = null;
				tmp299_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp299_AST);
				match(LITERAL);
				entity_class_AST = (AST)currentAST.root;
				break;
			}
			case UNITS:
			{
				AST tmp300_AST = null;
				tmp300_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp300_AST);
				match(UNITS);
				entity_class_AST = (AST)currentAST.root;
				break;
			}
			case GROUP:
			{
				AST tmp301_AST = null;
				tmp301_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp301_AST);
				match(GROUP);
				entity_class_AST = (AST)currentAST.root;
				break;
			}
			case FILE:
			{
				AST tmp302_AST = null;
				tmp302_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp302_AST);
				match(FILE);
				entity_class_AST = (AST)currentAST.root;
				break;
			}
			case NATURE:
			{
				AST tmp303_AST = null;
				tmp303_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp303_AST);
				match(NATURE);
				entity_class_AST = (AST)currentAST.root;
				break;
			}
			case SUBNATURE:
			{
				AST tmp304_AST = null;
				tmp304_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp304_AST);
				match(SUBNATURE);
				entity_class_AST = (AST)currentAST.root;
				break;
			}
			case QUANTITY:
			{
				AST tmp305_AST = null;
				tmp305_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp305_AST);
				match(QUANTITY);
				entity_class_AST = (AST)currentAST.root;
				break;
			}
			case TERMINAL:
			{
				AST tmp306_AST = null;
				tmp306_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp306_AST);
				match(TERMINAL);
				entity_class_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_150);
			} else {
			  throw ex;
			}
		}
		returnAST = entity_class_AST;
	}
	
	public final void entity_class_entry() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST entity_class_entry_AST = null;
		
		try {      // for error handling
			entity_class();
			astFactory.addASTChild(currentAST, returnAST);
			{
			switch ( LA(1)) {
			case BOX:
			{
				AST tmp307_AST = null;
				tmp307_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp307_AST);
				match(BOX);
				break;
			}
			case RPAREN:
			case COMMA:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			entity_class_entry_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_27);
			} else {
			  throw ex;
			}
		}
		returnAST = entity_class_entry_AST;
	}
	
	public final void entity_class_entry_list() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST entity_class_entry_list_AST = null;
		
		try {      // for error handling
			entity_class_entry();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop243:
			do {
				if ((LA(1)==COMMA)) {
					AST tmp308_AST = null;
					tmp308_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp308_AST);
					match(COMMA);
					entity_class_entry();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop243;
				}
				
			} while (true);
			}
			entity_class_entry_list_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_28);
			} else {
			  throw ex;
			}
		}
		returnAST = entity_class_entry_list_AST;
	}
	
	public final void entity_declaration() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST entity_declaration_AST = null;
		
		try {      // for error handling
			AST tmp309_AST = null;
			tmp309_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp309_AST);
			match(ENTITY);
			identifier();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp310_AST = null;
			tmp310_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp310_AST);
			match(IS);
			entity_header();
			astFactory.addASTChild(currentAST, returnAST);
			entity_declarative_part();
			astFactory.addASTChild(currentAST, returnAST);
			ent_dec1();
			astFactory.addASTChild(currentAST, returnAST);
			ent_dec2();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				entity_declaration_AST = (AST)currentAST.root;
				entity_declaration_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(ENTITYDECLARATION,"EntityDeclaration")).add(entity_declaration_AST));
				currentAST.root = entity_declaration_AST;
				currentAST.child = entity_declaration_AST!=null &&entity_declaration_AST.getFirstChild()!=null ?
					entity_declaration_AST.getFirstChild() : entity_declaration_AST;
				currentAST.advanceChildToEnd();
			}
			entity_declaration_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_55);
			} else {
			  throw ex;
			}
		}
		returnAST = entity_declaration_AST;
	}
	
	public final void entity_declarative_part() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST entity_declarative_part_AST = null;
		
		try {      // for error handling
			{
			_loop255:
			do {
				if ((_tokenSet_151.member(LA(1)))) {
					entity_declarative_item();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop255;
				}
				
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				entity_declarative_part_AST = (AST)currentAST.root;
				entity_declarative_part_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(ENTITYDECLARATIVEPART,"EntityDeclarativePart")).add(entity_declarative_part_AST));
				currentAST.root = entity_declarative_part_AST;
				currentAST.child = entity_declarative_part_AST!=null &&entity_declarative_part_AST.getFirstChild()!=null ?
					entity_declarative_part_AST.getFirstChild() : entity_declarative_part_AST;
				currentAST.advanceChildToEnd();
			}
			entity_declarative_part_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_152);
			} else {
			  throw ex;
			}
		}
		returnAST = entity_declarative_part_AST;
	}
	
	public final void ent_dec1() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST ent_dec1_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case BEGIN:
			{
				AST tmp311_AST = null;
				tmp311_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp311_AST);
				match(BEGIN);
				entity_statement_part();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case END:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				ent_dec1_AST = (AST)currentAST.root;
				ent_dec1_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(ENTDEC1,"EntDec1")).add(ent_dec1_AST));
				currentAST.root = ent_dec1_AST;
				currentAST.child = ent_dec1_AST!=null &&ent_dec1_AST.getFirstChild()!=null ?
					ent_dec1_AST.getFirstChild() : ent_dec1_AST;
				currentAST.advanceChildToEnd();
			}
			ent_dec1_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_3);
			} else {
			  throw ex;
			}
		}
		returnAST = ent_dec1_AST;
	}
	
	public final void ent_dec2() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST ent_dec2_AST = null;
		
		try {      // for error handling
			AST tmp312_AST = null;
			tmp312_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp312_AST);
			match(END);
			{
			switch ( LA(1)) {
			case ENTITY:
			{
				AST tmp313_AST = null;
				tmp313_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp313_AST);
				match(ENTITY);
				break;
			}
			case SEMI:
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			switch ( LA(1)) {
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			{
				identifier();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case SEMI:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			AST tmp314_AST = null;
			tmp314_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp314_AST);
			match(SEMI);
			if ( inputState.guessing==0 ) {
				ent_dec2_AST = (AST)currentAST.root;
				ent_dec2_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(ENTDEC2,"EntDec2")).add(ent_dec2_AST));
				currentAST.root = ent_dec2_AST;
				currentAST.child = ent_dec2_AST!=null &&ent_dec2_AST.getFirstChild()!=null ?
					ent_dec2_AST.getFirstChild() : ent_dec2_AST;
				currentAST.advanceChildToEnd();
			}
			ent_dec2_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_55);
			} else {
			  throw ex;
			}
		}
		returnAST = ent_dec2_AST;
	}
	
	public final void entity_statement_part() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST entity_statement_part_AST = null;
		
		try {      // for error handling
			{
			_loop278:
			do {
				if ((_tokenSet_153.member(LA(1)))) {
					entity_statement();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop278;
				}
				
			} while (true);
			}
			entity_statement_part_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_3);
			} else {
			  throw ex;
			}
		}
		returnAST = entity_statement_part_AST;
	}
	
	public final void entity_declarative_item() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST entity_declarative_item_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case TYPE:
			{
				type_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				entity_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case SUBTYPE:
			{
				subtype_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				entity_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case CONSTANT:
			{
				constant_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				entity_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case SIGNAL:
			{
				signal_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				entity_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case VARIABLE:
			case SHARED:
			{
				variable_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				entity_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case FILE:
			{
				file_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				entity_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case ALIAS:
			{
				alias_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				entity_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case DISCONNECT:
			{
				disconnection_specification();
				astFactory.addASTChild(currentAST, returnAST);
				entity_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case USE:
			case CONTEXT:
			{
				use_clause();
				astFactory.addASTChild(currentAST, returnAST);
				entity_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case NATURE:
			{
				nature_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				entity_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case SUBNATURE:
			{
				subnature_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				entity_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case QUANTITY:
			{
				quantity_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				entity_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case TERMINAL:
			{
				terminal_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				entity_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			default:
				boolean synPredMatched252 = false;
				if (((_tokenSet_11.member(LA(1))) && (_tokenSet_12.member(LA(2))) && (_tokenSet_13.member(LA(3))))) {
					int _m252 = mark();
					synPredMatched252 = true;
					inputState.guessing++;
					try {
						{
						subprogram_declaration();
						}
					}
					catch (RecognitionException pe) {
						synPredMatched252 = false;
					}
					rewind(_m252);
inputState.guessing--;
				}
				if ( synPredMatched252 ) {
					subprogram_declaration();
					astFactory.addASTChild(currentAST, returnAST);
					entity_declarative_item_AST = (AST)currentAST.root;
				}
				else if ((_tokenSet_11.member(LA(1))) && (_tokenSet_12.member(LA(2))) && (_tokenSet_14.member(LA(3)))) {
					subprogram_body();
					astFactory.addASTChild(currentAST, returnAST);
					entity_declarative_item_AST = (AST)currentAST.root;
				}
				else if ((LA(1)==ATTRIBUTE) && ((LA(2) >= BASIC_IDENTIFIER && LA(2) <= REFERENCE)) && (LA(3)==COLON)) {
					attribute_declaration();
					astFactory.addASTChild(currentAST, returnAST);
					entity_declarative_item_AST = (AST)currentAST.root;
				}
				else if ((LA(1)==ATTRIBUTE) && (_tokenSet_15.member(LA(2))) && (LA(3)==OF)) {
					attribute_specification();
					astFactory.addASTChild(currentAST, returnAST);
					entity_declarative_item_AST = (AST)currentAST.root;
				}
				else if ((LA(1)==GROUP) && ((LA(2) >= BASIC_IDENTIFIER && LA(2) <= REFERENCE)) && (LA(3)==IS)) {
					group_template_declaration();
					astFactory.addASTChild(currentAST, returnAST);
					entity_declarative_item_AST = (AST)currentAST.root;
				}
				else if ((LA(1)==GROUP) && ((LA(2) >= BASIC_IDENTIFIER && LA(2) <= REFERENCE)) && (LA(3)==COLON)) {
					group_declaration();
					astFactory.addASTChild(currentAST, returnAST);
					entity_declarative_item_AST = (AST)currentAST.root;
				}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_122);
			} else {
			  throw ex;
			}
		}
		returnAST = entity_declarative_item_AST;
	}
	
	public final void entity_designator() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST entity_designator_AST = null;
		
		try {      // for error handling
			entity_tag();
			astFactory.addASTChild(currentAST, returnAST);
			{
			switch ( LA(1)) {
			case LBRACKET:
			{
				signature();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case COMMA:
			case COLON:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			entity_designator_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_154);
			} else {
			  throw ex;
			}
		}
		returnAST = entity_designator_AST;
	}
	
	public final void entity_tag() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST entity_tag_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			{
				identifier();
				astFactory.addASTChild(currentAST, returnAST);
				entity_tag_AST = (AST)currentAST.root;
				break;
			}
			case CHARACTER_LITERAL:
			{
				AST tmp315_AST = null;
				tmp315_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp315_AST);
				match(CHARACTER_LITERAL);
				entity_tag_AST = (AST)currentAST.root;
				break;
			}
			case STRING_LITERAL:
			{
				AST tmp316_AST = null;
				tmp316_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp316_AST);
				match(STRING_LITERAL);
				entity_tag_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_155);
			} else {
			  throw ex;
			}
		}
		returnAST = entity_tag_AST;
	}
	
	public final void generic_clause_dummy() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST generic_clause_dummy_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case GENERIC:
			{
				generic_clause();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case END:
			case ALIAS:
			case BEGIN:
			case ATTRIBUTE:
			case USE:
			case QUANTITY:
			case CONSTANT:
			case DISCONNECT:
			case PROCEDURE:
			case FUNCTION:
			case TYPE:
			case SUBTYPE:
			case SIGNAL:
			case VARIABLE:
			case GROUP:
			case FILE:
			case NATURE:
			case SUBNATURE:
			case TERMINAL:
			case PORT:
			case PURE:
			case IMPURE:
			case CONTEXT:
			case SHARED:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				generic_clause_dummy_AST = (AST)currentAST.root;
				generic_clause_dummy_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(GENERICCLAUSEDUMMY,"GeneriClauseDummy")).add(generic_clause_dummy_AST));
				currentAST.root = generic_clause_dummy_AST;
				currentAST.child = generic_clause_dummy_AST!=null &&generic_clause_dummy_AST.getFirstChild()!=null ?
					generic_clause_dummy_AST.getFirstChild() : generic_clause_dummy_AST;
				currentAST.advanceChildToEnd();
			}
			generic_clause_dummy_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_156);
			} else {
			  throw ex;
			}
		}
		returnAST = generic_clause_dummy_AST;
	}
	
	public final void port_clause_dummy() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST port_clause_dummy_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case PORT:
			{
				port_clause();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case END:
			case ALIAS:
			case BEGIN:
			case ATTRIBUTE:
			case USE:
			case QUANTITY:
			case CONSTANT:
			case DISCONNECT:
			case PROCEDURE:
			case FUNCTION:
			case TYPE:
			case SUBTYPE:
			case SIGNAL:
			case VARIABLE:
			case GROUP:
			case FILE:
			case NATURE:
			case SUBNATURE:
			case TERMINAL:
			case PURE:
			case IMPURE:
			case CONTEXT:
			case SHARED:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				port_clause_dummy_AST = (AST)currentAST.root;
				port_clause_dummy_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(PORTCLAUSEDUMMY,"PortClauseDummy")).add(port_clause_dummy_AST));
				currentAST.root = port_clause_dummy_AST;
				currentAST.child = port_clause_dummy_AST!=null &&port_clause_dummy_AST.getFirstChild()!=null ?
					port_clause_dummy_AST.getFirstChild() : port_clause_dummy_AST;
				currentAST.advanceChildToEnd();
			}
			port_clause_dummy_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_122);
			} else {
			  throw ex;
			}
		}
		returnAST = port_clause_dummy_AST;
	}
	
	public final void entity_name_list() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST entity_name_list_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case CHARACTER_LITERAL:
			case STRING_LITERAL:
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			{
				entity_designator();
				astFactory.addASTChild(currentAST, returnAST);
				{
				_loop265:
				do {
					if ((LA(1)==COMMA)) {
						AST tmp317_AST = null;
						tmp317_AST = astFactory.create(LT(1));
						astFactory.addASTChild(currentAST, tmp317_AST);
						match(COMMA);
						entity_designator();
						astFactory.addASTChild(currentAST, returnAST);
					}
					else {
						break _loop265;
					}
					
				} while (true);
				}
				entity_name_list_AST = (AST)currentAST.root;
				break;
			}
			case OTHERS:
			{
				AST tmp318_AST = null;
				tmp318_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp318_AST);
				match(OTHERS);
				entity_name_list_AST = (AST)currentAST.root;
				break;
			}
			case ALL:
			{
				AST tmp319_AST = null;
				tmp319_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp319_AST);
				match(ALL);
				entity_name_list_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_123);
			} else {
			  throw ex;
			}
		}
		returnAST = entity_name_list_AST;
	}
	
	public final void entity_statement() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST entity_statement_AST = null;
		
		try {      // for error handling
			boolean synPredMatched271 = false;
			if (((_tokenSet_67.member(LA(1))) && (_tokenSet_68.member(LA(2))) && (_tokenSet_69.member(LA(3))))) {
				int _m271 = mark();
				synPredMatched271 = true;
				inputState.guessing++;
				try {
					{
					{
					switch ( LA(1)) {
					case BASIC_IDENTIFIER:
					case EXTENDED_IDENTIFIER:
					case REFERENCE:
					{
						label_colon();
						break;
					}
					case POSTPONED:
					case ASSERT:
					{
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					{
					switch ( LA(1)) {
					case POSTPONED:
					{
						match(POSTPONED);
						break;
					}
					case ASSERT:
					{
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					match(ASSERT);
					}
				}
				catch (RecognitionException pe) {
					synPredMatched271 = false;
				}
				rewind(_m271);
inputState.guessing--;
			}
			if ( synPredMatched271 ) {
				concurrent_assertion_statement();
				astFactory.addASTChild(currentAST, returnAST);
				entity_statement_AST = (AST)currentAST.root;
			}
			else {
				boolean synPredMatched275 = false;
				if (((_tokenSet_60.member(LA(1))) && (_tokenSet_61.member(LA(2))) && (_tokenSet_62.member(LA(3))))) {
					int _m275 = mark();
					synPredMatched275 = true;
					inputState.guessing++;
					try {
						{
						{
						switch ( LA(1)) {
						case BASIC_IDENTIFIER:
						case EXTENDED_IDENTIFIER:
						case REFERENCE:
						{
							label_colon();
							break;
						}
						case POSTPONED:
						case PROCESS:
						{
							break;
						}
						default:
						{
							throw new NoViableAltException(LT(1), getFilename());
						}
						}
						}
						{
						switch ( LA(1)) {
						case POSTPONED:
						{
							match(POSTPONED);
							break;
						}
						case PROCESS:
						{
							break;
						}
						default:
						{
							throw new NoViableAltException(LT(1), getFilename());
						}
						}
						}
						match(PROCESS);
						}
					}
					catch (RecognitionException pe) {
						synPredMatched275 = false;
					}
					rewind(_m275);
inputState.guessing--;
				}
				if ( synPredMatched275 ) {
					process_statement();
					astFactory.addASTChild(currentAST, returnAST);
					entity_statement_AST = (AST)currentAST.root;
				}
				else if ((_tokenSet_63.member(LA(1))) && (_tokenSet_64.member(LA(2))) && (_tokenSet_157.member(LA(3)))) {
					concurrent_procedure_call_statement();
					astFactory.addASTChild(currentAST, returnAST);
					entity_statement_AST = (AST)currentAST.root;
				}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
			catch (RecognitionException ex) {
				if (inputState.guessing==0) {
					reportError(ex);
					recover(ex,_tokenSet_158);
				} else {
				  throw ex;
				}
			}
			returnAST = entity_statement_AST;
		}
		
	public final void enumeration_literal() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST enumeration_literal_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			{
				identifier();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case CHARACTER_LITERAL:
			{
				character_lit();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				enumeration_literal_AST = (AST)currentAST.root;
				enumeration_literal_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(ENUMERATIONLITERAL,"EnumerationLiteral")).add(enumeration_literal_AST));
				currentAST.root = enumeration_literal_AST;
				currentAST.child = enumeration_literal_AST!=null &&enumeration_literal_AST.getFirstChild()!=null ?
					enumeration_literal_AST.getFirstChild() : enumeration_literal_AST;
				currentAST.advanceChildToEnd();
			}
			enumeration_literal_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_21);
			} else {
			  throw ex;
			}
		}
		returnAST = enumeration_literal_AST;
	}
	
	public final void character_lit() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST character_lit_AST = null;
		
		try {      // for error handling
			AST tmp320_AST = null;
			tmp320_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp320_AST);
			match(CHARACTER_LITERAL);
			if ( inputState.guessing==0 ) {
				character_lit_AST = (AST)currentAST.root;
				character_lit_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(CHARACTERLIT,"CharacterLit")).add(character_lit_AST));
				currentAST.root = character_lit_AST;
				currentAST.child = character_lit_AST!=null &&character_lit_AST.getFirstChild()!=null ?
					character_lit_AST.getFirstChild() : character_lit_AST;
				currentAST.advanceChildToEnd();
			}
			character_lit_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_21);
			} else {
			  throw ex;
			}
		}
		returnAST = character_lit_AST;
	}
	
	public final void enumeration_type_definition() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST enumeration_type_definition_AST = null;
		
		try {      // for error handling
			AST tmp321_AST = null;
			tmp321_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp321_AST);
			match(LPAREN);
			enumeration_literal();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop285:
			do {
				if ((LA(1)==COMMA)) {
					AST tmp322_AST = null;
					tmp322_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp322_AST);
					match(COMMA);
					enumeration_literal();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop285;
				}
				
			} while (true);
			}
			AST tmp323_AST = null;
			tmp323_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp323_AST);
			match(RPAREN);
			if ( inputState.guessing==0 ) {
				enumeration_type_definition_AST = (AST)currentAST.root;
				enumeration_type_definition_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(ENUMERATIONTYPEDEFINITION,"EnumerationTypeDefinition")).add(enumeration_type_definition_AST));
				currentAST.root = enumeration_type_definition_AST;
				currentAST.child = enumeration_type_definition_AST!=null &&enumeration_type_definition_AST.getFirstChild()!=null ?
					enumeration_type_definition_AST.getFirstChild() : enumeration_type_definition_AST;
				currentAST.advanceChildToEnd();
			}
			enumeration_type_definition_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_1);
			} else {
			  throw ex;
			}
		}
		returnAST = enumeration_type_definition_AST;
	}
	
	public final void exit_statement() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST exit_statement_AST = null;
		
		try {      // for error handling
			AST tmp324_AST = null;
			tmp324_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp324_AST);
			match(EXIT);
			{
			switch ( LA(1)) {
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			{
				identifier();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case SEMI:
			case WHEN:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			switch ( LA(1)) {
			case WHEN:
			{
				AST tmp325_AST = null;
				tmp325_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp325_AST);
				match(WHEN);
				condition();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case SEMI:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			AST tmp326_AST = null;
			tmp326_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp326_AST);
			match(SEMI);
			if ( inputState.guessing==0 ) {
				exit_statement_AST = (AST)currentAST.root;
				exit_statement_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(EXITSTATEMENT,"ExitStatement")).add(exit_statement_AST));
				currentAST.root = exit_statement_AST;
				currentAST.child = exit_statement_AST!=null &&exit_statement_AST.getFirstChild()!=null ?
					exit_statement_AST.getFirstChild() : exit_statement_AST;
				currentAST.advanceChildToEnd();
			}
			exit_statement_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_86);
			} else {
			  throw ex;
			}
		}
		returnAST = exit_statement_AST;
	}
	
	public final void unary_op() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST unary_op_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case PLUS:
			{
				AST tmp327_AST = null;
				tmp327_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp327_AST);
				match(PLUS);
				break;
			}
			case MINUS:
			{
				AST tmp328_AST = null;
				tmp328_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp328_AST);
				match(MINUS);
				break;
			}
			case ABS:
			{
				AST tmp329_AST = null;
				tmp329_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp329_AST);
				match(ABS);
				break;
			}
			case NOT:
			{
				AST tmp330_AST = null;
				tmp330_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp330_AST);
				match(NOT);
				break;
			}
			case DECIMAL_LITERAL:
			case BASED_LITERAL:
			case LPAREN:
			case CHARACTER_LITERAL:
			case STRING_LITERAL:
			case NEW:
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			case NULLTOK:
			case BIT_STRING_LITERAL:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			primary();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				unary_op_AST = (AST)currentAST.root;
				unary_op_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(UNARYOP,"UnaryOp")).add(unary_op_AST));
				currentAST.root = unary_op_AST;
				currentAST.child = unary_op_AST!=null &&unary_op_AST.getFirstChild()!=null ?
					unary_op_AST.getFirstChild() : unary_op_AST;
				currentAST.advanceChildToEnd();
			}
			unary_op_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_21);
			} else {
			  throw ex;
			}
		}
		returnAST = unary_op_AST;
	}
	
	public final void combined_operator() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST combined_operator_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case AND:
			case OR:
			case NAND:
			case NOR:
			case XOR:
			case XNOR:
			{
				logical_operator();
				astFactory.addASTChild(currentAST, returnAST);
				combined_operator_AST = (AST)currentAST.root;
				break;
			}
			case LE:
			case EQ:
			case NEQ:
			case LOWERTHAN:
			case GREATERTHAN:
			case GE:
			{
				relational_operator();
				astFactory.addASTChild(currentAST, returnAST);
				combined_operator_AST = (AST)currentAST.root;
				break;
			}
			case SLL:
			case SRL:
			case SLA:
			case SRA:
			case ROL:
			case ROR:
			{
				shift_operator();
				astFactory.addASTChild(currentAST, returnAST);
				combined_operator_AST = (AST)currentAST.root;
				break;
			}
			case PLUS:
			case MINUS:
			case AMPERSAND:
			{
				adding_operator();
				astFactory.addASTChild(currentAST, returnAST);
				combined_operator_AST = (AST)currentAST.root;
				break;
			}
			case MUL:
			case DIV:
			case MOD:
			case REM:
			{
				multiplying_operator();
				astFactory.addASTChild(currentAST, returnAST);
				combined_operator_AST = (AST)currentAST.root;
				break;
			}
			case DOUBLESTAR:
			{
				AST tmp331_AST = null;
				tmp331_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp331_AST);
				match(DOUBLESTAR);
				combined_operator_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_19);
			} else {
			  throw ex;
			}
		}
		returnAST = combined_operator_AST;
	}
	
	public final void simple_expression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST simple_expression_AST = null;
		
		try {      // for error handling
			unary_op();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop294:
			do {
				if ((_tokenSet_26.member(LA(1))) && (_tokenSet_19.member(LA(2))) && (_tokenSet_20.member(LA(3)))) {
					combined_operator();
					astFactory.addASTChild(currentAST, returnAST);
					unary_op();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop294;
				}
				
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				simple_expression_AST = (AST)currentAST.root;
				simple_expression_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(SIMPLEEXPRESSION,"SimpleExpression")).add(simple_expression_AST));
				currentAST.root = simple_expression_AST;
				currentAST.child = simple_expression_AST!=null &&simple_expression_AST.getFirstChild()!=null ?
					simple_expression_AST.getFirstChild() : simple_expression_AST;
				currentAST.advanceChildToEnd();
			}
			simple_expression_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_21);
			} else {
			  throw ex;
			}
		}
		returnAST = simple_expression_AST;
	}
	
	public final void logical_operator() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST logical_operator_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case AND:
			{
				AST tmp332_AST = null;
				tmp332_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp332_AST);
				match(AND);
				logical_operator_AST = (AST)currentAST.root;
				break;
			}
			case OR:
			{
				AST tmp333_AST = null;
				tmp333_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp333_AST);
				match(OR);
				logical_operator_AST = (AST)currentAST.root;
				break;
			}
			case NAND:
			{
				AST tmp334_AST = null;
				tmp334_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp334_AST);
				match(NAND);
				logical_operator_AST = (AST)currentAST.root;
				break;
			}
			case NOR:
			{
				AST tmp335_AST = null;
				tmp335_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp335_AST);
				match(NOR);
				logical_operator_AST = (AST)currentAST.root;
				break;
			}
			case XOR:
			{
				AST tmp336_AST = null;
				tmp336_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp336_AST);
				match(XOR);
				logical_operator_AST = (AST)currentAST.root;
				break;
			}
			case XNOR:
			{
				AST tmp337_AST = null;
				tmp337_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp337_AST);
				match(XNOR);
				logical_operator_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_19);
			} else {
			  throw ex;
			}
		}
		returnAST = logical_operator_AST;
	}
	
	public final void relational_operator() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST relational_operator_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case EQ:
			{
				AST tmp338_AST = null;
				tmp338_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp338_AST);
				match(EQ);
				relational_operator_AST = (AST)currentAST.root;
				break;
			}
			case NEQ:
			{
				AST tmp339_AST = null;
				tmp339_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp339_AST);
				match(NEQ);
				relational_operator_AST = (AST)currentAST.root;
				break;
			}
			case LOWERTHAN:
			{
				AST tmp340_AST = null;
				tmp340_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp340_AST);
				match(LOWERTHAN);
				relational_operator_AST = (AST)currentAST.root;
				break;
			}
			case LE:
			{
				AST tmp341_AST = null;
				tmp341_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp341_AST);
				match(LE);
				relational_operator_AST = (AST)currentAST.root;
				break;
			}
			case GREATERTHAN:
			{
				AST tmp342_AST = null;
				tmp342_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp342_AST);
				match(GREATERTHAN);
				relational_operator_AST = (AST)currentAST.root;
				break;
			}
			case GE:
			{
				AST tmp343_AST = null;
				tmp343_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp343_AST);
				match(GE);
				relational_operator_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_19);
			} else {
			  throw ex;
			}
		}
		returnAST = relational_operator_AST;
	}
	
	public final void shift_operator() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST shift_operator_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case SLL:
			{
				AST tmp344_AST = null;
				tmp344_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp344_AST);
				match(SLL);
				shift_operator_AST = (AST)currentAST.root;
				break;
			}
			case SRL:
			{
				AST tmp345_AST = null;
				tmp345_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp345_AST);
				match(SRL);
				shift_operator_AST = (AST)currentAST.root;
				break;
			}
			case SLA:
			{
				AST tmp346_AST = null;
				tmp346_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp346_AST);
				match(SLA);
				shift_operator_AST = (AST)currentAST.root;
				break;
			}
			case SRA:
			{
				AST tmp347_AST = null;
				tmp347_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp347_AST);
				match(SRA);
				shift_operator_AST = (AST)currentAST.root;
				break;
			}
			case ROL:
			{
				AST tmp348_AST = null;
				tmp348_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp348_AST);
				match(ROL);
				shift_operator_AST = (AST)currentAST.root;
				break;
			}
			case ROR:
			{
				AST tmp349_AST = null;
				tmp349_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp349_AST);
				match(ROR);
				shift_operator_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_19);
			} else {
			  throw ex;
			}
		}
		returnAST = shift_operator_AST;
	}
	
	public final void adding_operator() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST adding_operator_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case PLUS:
			{
				AST tmp350_AST = null;
				tmp350_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp350_AST);
				match(PLUS);
				adding_operator_AST = (AST)currentAST.root;
				break;
			}
			case MINUS:
			{
				AST tmp351_AST = null;
				tmp351_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp351_AST);
				match(MINUS);
				adding_operator_AST = (AST)currentAST.root;
				break;
			}
			case AMPERSAND:
			{
				AST tmp352_AST = null;
				tmp352_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp352_AST);
				match(AMPERSAND);
				adding_operator_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_19);
			} else {
			  throw ex;
			}
		}
		returnAST = adding_operator_AST;
	}
	
	public final void multiplying_operator() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST multiplying_operator_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case MUL:
			{
				AST tmp353_AST = null;
				tmp353_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp353_AST);
				match(MUL);
				multiplying_operator_AST = (AST)currentAST.root;
				break;
			}
			case DIV:
			{
				AST tmp354_AST = null;
				tmp354_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp354_AST);
				match(DIV);
				multiplying_operator_AST = (AST)currentAST.root;
				break;
			}
			case MOD:
			{
				AST tmp355_AST = null;
				tmp355_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp355_AST);
				match(MOD);
				multiplying_operator_AST = (AST)currentAST.root;
				break;
			}
			case REM:
			{
				AST tmp356_AST = null;
				tmp356_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp356_AST);
				match(REM);
				multiplying_operator_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_19);
			} else {
			  throw ex;
			}
		}
		returnAST = multiplying_operator_AST;
	}
	
	public final void primary() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST primary_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case LPAREN:
			{
				aggregate();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case DECIMAL_LITERAL:
			case BASED_LITERAL:
			case CHARACTER_LITERAL:
			case STRING_LITERAL:
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			case NULLTOK:
			case BIT_STRING_LITERAL:
			{
				literal();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case NEW:
			{
				allocator();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				primary_AST = (AST)currentAST.root;
				primary_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(PRIMARY,"Primary")).add(primary_AST));
				currentAST.root = primary_AST;
				currentAST.child = primary_AST!=null &&primary_AST.getFirstChild()!=null ?
					primary_AST.getFirstChild() : primary_AST;
				currentAST.advanceChildToEnd();
			}
			primary_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_21);
			} else {
			  throw ex;
			}
		}
		returnAST = primary_AST;
	}
	
	public final void file_open_information() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST file_open_information_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case OPEN:
			{
				AST tmp357_AST = null;
				tmp357_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp357_AST);
				match(OPEN);
				expression();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case IS:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			AST tmp358_AST = null;
			tmp358_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp358_AST);
			match(IS);
			file_logical_name();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				file_open_information_AST = (AST)currentAST.root;
				file_open_information_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(FILEOPENINFORMATION,"FileOpenInformation")).add(file_open_information_AST));
				currentAST.root = file_open_information_AST;
				currentAST.child = file_open_information_AST!=null &&file_open_information_AST.getFirstChild()!=null ?
					file_open_information_AST.getFirstChild() : file_open_information_AST;
				currentAST.advanceChildToEnd();
			}
			file_open_information_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_1);
			} else {
			  throw ex;
			}
		}
		returnAST = file_open_information_AST;
	}
	
	public final void file_logical_name() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST file_logical_name_AST = null;
		
		try {      // for error handling
			expression();
			astFactory.addASTChild(currentAST, returnAST);
			file_logical_name_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_1);
			} else {
			  throw ex;
			}
		}
		returnAST = file_logical_name_AST;
	}
	
	public final void file_type_definition() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST file_type_definition_AST = null;
		
		try {      // for error handling
			AST tmp359_AST = null;
			tmp359_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp359_AST);
			match(FILE);
			AST tmp360_AST = null;
			tmp360_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp360_AST);
			match(OF);
			name();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				file_type_definition_AST = (AST)currentAST.root;
				file_type_definition_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(FILETYPEDEFINITION,"FileTypeDefinition")).add(file_type_definition_AST));
				currentAST.root = file_type_definition_AST;
				currentAST.child = file_type_definition_AST!=null &&file_type_definition_AST.getFirstChild()!=null ?
					file_type_definition_AST.getFirstChild() : file_type_definition_AST;
				currentAST.advanceChildToEnd();
			}
			file_type_definition_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_1);
			} else {
			  throw ex;
			}
		}
		returnAST = file_type_definition_AST;
	}
	
	public final void formal_parameter_list() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST formal_parameter_list_AST = null;
		
		try {      // for error handling
			port_list();
			astFactory.addASTChild(currentAST, returnAST);
			formal_parameter_list_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_28);
			} else {
			  throw ex;
			}
		}
		returnAST = formal_parameter_list_AST;
	}
	
	public final void port_list() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST port_list_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case QUANTITY:
			case CONSTANT:
			case SIGNAL:
			case VARIABLE:
			case FILE:
			case TERMINAL:
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			{
				port_element();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case RPAREN:
			case SEMI:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			_loop393:
			do {
				if ((LA(1)==SEMI)) {
					semi_port_element();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop393;
				}
				
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				port_list_AST = (AST)currentAST.root;
				port_list_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(PORTLIST,"PortList")).add(port_list_AST));
				currentAST.root = port_list_AST;
				currentAST.child = port_list_AST!=null &&port_list_AST.getFirstChild()!=null ?
					port_list_AST.getFirstChild() : port_list_AST;
				currentAST.advanceChildToEnd();
			}
			port_list_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_28);
			} else {
			  throw ex;
			}
		}
		returnAST = port_list_AST;
	}
	
	public final void free_quantity_declaration() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST free_quantity_declaration_AST = null;
		
		try {      // for error handling
			AST tmp361_AST = null;
			tmp361_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp361_AST);
			match(QUANTITY);
			identifier_list();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp362_AST = null;
			tmp362_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp362_AST);
			match(COLON);
			subtype_indication();
			astFactory.addASTChild(currentAST, returnAST);
			{
			switch ( LA(1)) {
			case VARASGN:
			{
				AST tmp363_AST = null;
				tmp363_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp363_AST);
				match(VARASGN);
				expression();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case SEMI:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			AST tmp364_AST = null;
			tmp364_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp364_AST);
			match(SEMI);
			free_quantity_declaration_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_100);
			} else {
			  throw ex;
			}
		}
		returnAST = free_quantity_declaration_AST;
	}
	
	public final void function_call() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST function_call_AST = null;
		
		try {      // for error handling
			name();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp365_AST = null;
			tmp365_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp365_AST);
			match(LPAREN);
			actual_parameter_part();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp366_AST = null;
			tmp366_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp366_AST);
			match(RPAREN);
			function_call_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_138);
			} else {
			  throw ex;
			}
		}
		returnAST = function_call_AST;
	}
	
	public final void generate_declarative_part() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST generate_declarative_part_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case ALIAS:
			case BEGIN:
			case ATTRIBUTE:
			case USE:
			case FOR:
			case QUANTITY:
			case COMPONENT:
			case CONSTANT:
			case DISCONNECT:
			case PROCEDURE:
			case FUNCTION:
			case TYPE:
			case SUBTYPE:
			case SIGNAL:
			case VARIABLE:
			case GROUP:
			case FILE:
			case NATURE:
			case SUBNATURE:
			case TERMINAL:
			case PURE:
			case IMPURE:
			case CONTEXT:
			case SHARED:
			{
				{
				_loop320:
				do {
					if ((_tokenSet_56.member(LA(1)))) {
						block_declarative_item();
						astFactory.addASTChild(currentAST, returnAST);
					}
					else {
						break _loop320;
					}
					
				} while (true);
				}
				AST tmp367_AST = null;
				tmp367_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp367_AST);
				match(BEGIN);
				break;
			}
			case END:
			case LPAREN:
			case POSTPONED:
			case PROCESS:
			case ASSERT:
			case BREAK:
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			case WITH:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				generate_declarative_part_AST = (AST)currentAST.root;
				generate_declarative_part_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(GENERATEDECLARATIVEPART,"generate_declarative_part")).add(generate_declarative_part_AST));
				currentAST.root = generate_declarative_part_AST;
				currentAST.child = generate_declarative_part_AST!=null &&generate_declarative_part_AST.getFirstChild()!=null ?
					generate_declarative_part_AST.getFirstChild() : generate_declarative_part_AST;
				currentAST.advanceChildToEnd();
			}
			generate_declarative_part_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_79);
			} else {
			  throw ex;
			}
		}
		returnAST = generate_declarative_part_AST;
	}
	
	public final void generate_statement_part() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST generate_statement_part_AST = null;
		
		try {      // for error handling
			{
			_loop323:
			do {
				if ((_tokenSet_58.member(LA(1)))) {
					architecture_statement();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop323;
				}
				
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				generate_statement_part_AST = (AST)currentAST.root;
				generate_statement_part_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(GENERATESTATEMENTPART,"generate_statement_part")).add(generate_statement_part_AST));
				currentAST.root = generate_statement_part_AST;
				currentAST.child = generate_statement_part_AST!=null &&generate_statement_part_AST.getFirstChild()!=null ?
					generate_statement_part_AST.getFirstChild() : generate_statement_part_AST;
				currentAST.advanceChildToEnd();
			}
			generate_statement_part_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_3);
			} else {
			  throw ex;
			}
		}
		returnAST = generate_statement_part_AST;
	}
	
	public final void generate_for() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST generate_for_AST = null;
		
		try {      // for error handling
			AST tmp368_AST = null;
			tmp368_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp368_AST);
			match(FOR);
			parameter_specification();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				generate_for_AST = (AST)currentAST.root;
				generate_for_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(GENERATEFOR,"generate_for")).add(generate_for_AST));
				currentAST.root = generate_for_AST;
				currentAST.child = generate_for_AST!=null &&generate_for_AST.getFirstChild()!=null ?
					generate_for_AST.getFirstChild() : generate_for_AST;
				currentAST.advanceChildToEnd();
			}
			generate_for_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_82);
			} else {
			  throw ex;
			}
		}
		returnAST = generate_for_AST;
	}
	
	public final void generate_if() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST generate_if_AST = null;
		
		try {      // for error handling
			AST tmp369_AST = null;
			tmp369_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp369_AST);
			match(IF);
			condition();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				generate_if_AST = (AST)currentAST.root;
				generate_if_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(GENERATEIF,"generate_if")).add(generate_if_AST));
				currentAST.root = generate_if_AST;
				currentAST.child = generate_if_AST!=null &&generate_if_AST.getFirstChild()!=null ?
					generate_if_AST.getFirstChild() : generate_if_AST;
				currentAST.advanceChildToEnd();
			}
			generate_if_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_82);
			} else {
			  throw ex;
			}
		}
		returnAST = generate_if_AST;
	}
	
	public final void parameter_specification() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST parameter_specification_AST = null;
		
		try {      // for error handling
			identifier();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp370_AST = null;
			tmp370_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp370_AST);
			match(IN);
			discrete_range();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				parameter_specification_AST = (AST)currentAST.root;
				parameter_specification_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(PARAMETERSPECIFICATION,"ParameterSpecification")).add(parameter_specification_AST));
				currentAST.root = parameter_specification_AST;
				currentAST.child = parameter_specification_AST!=null &&parameter_specification_AST.getFirstChild()!=null ?
					parameter_specification_AST.getFirstChild() : parameter_specification_AST;
				currentAST.advanceChildToEnd();
			}
			parameter_specification_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_159);
			} else {
			  throw ex;
			}
		}
		returnAST = parameter_specification_AST;
	}
	
	public final void group_constituent() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST group_constituent_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			{
				name();
				astFactory.addASTChild(currentAST, returnAST);
				group_constituent_AST = (AST)currentAST.root;
				break;
			}
			case CHARACTER_LITERAL:
			{
				AST tmp371_AST = null;
				tmp371_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp371_AST);
				match(CHARACTER_LITERAL);
				group_constituent_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_27);
			} else {
			  throw ex;
			}
		}
		returnAST = group_constituent_AST;
	}
	
	public final void group_constituent_list() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST group_constituent_list_AST = null;
		
		try {      // for error handling
			group_constituent();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop333:
			do {
				if ((LA(1)==COMMA)) {
					AST tmp372_AST = null;
					tmp372_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp372_AST);
					match(COMMA);
					group_constituent();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop333;
				}
				
			} while (true);
			}
			group_constituent_list_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_28);
			} else {
			  throw ex;
			}
		}
		returnAST = group_constituent_list_AST;
	}
	
	public final void signal_list() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST signal_list_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			{
				name();
				astFactory.addASTChild(currentAST, returnAST);
				{
				_loop639:
				do {
					if ((LA(1)==COMMA)) {
						AST tmp373_AST = null;
						tmp373_AST = astFactory.create(LT(1));
						astFactory.addASTChild(currentAST, tmp373_AST);
						match(COMMA);
						name();
						astFactory.addASTChild(currentAST, returnAST);
					}
					else {
						break _loop639;
					}
					
				} while (true);
				}
				signal_list_AST = (AST)currentAST.root;
				break;
			}
			case OTHERS:
			{
				AST tmp374_AST = null;
				tmp374_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp374_AST);
				match(OTHERS);
				signal_list_AST = (AST)currentAST.root;
				break;
			}
			case ALL:
			{
				AST tmp375_AST = null;
				tmp375_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp375_AST);
				match(ALL);
				signal_list_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_123);
			} else {
			  throw ex;
			}
		}
		returnAST = signal_list_AST;
	}
	
	public final void if_statement() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST if_statement_AST = null;
		
		try {      // for error handling
			label_colon_wrap();
			astFactory.addASTChild(currentAST, returnAST);
			if_first_cond();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop344:
			do {
				if ((LA(1)==ELSIF)) {
					if_cond();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop344;
				}
				
			} while (true);
			}
			{
			switch ( LA(1)) {
			case ELSE:
			{
				if_last_cond();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case END:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if_end();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				if_statement_AST = (AST)currentAST.root;
				if_statement_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(IFSTATEMENT,"IfStatement")).add(if_statement_AST));
				currentAST.root = if_statement_AST;
				currentAST.child = if_statement_AST!=null &&if_statement_AST.getFirstChild()!=null ?
					if_statement_AST.getFirstChild() : if_statement_AST;
				currentAST.advanceChildToEnd();
			}
			if_statement_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_86);
			} else {
			  throw ex;
			}
		}
		returnAST = if_statement_AST;
	}
	
	public final void if_first_cond() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST if_first_cond_AST = null;
		
		try {      // for error handling
			AST tmp376_AST = null;
			tmp376_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp376_AST);
			match(IF);
			condition();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp377_AST = null;
			tmp377_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp377_AST);
			match(THEN);
			sequence_of_statements();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				if_first_cond_AST = (AST)currentAST.root;
				if_first_cond_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(IFFIRSTCOND,"IfFirstCond")).add(if_first_cond_AST));
				currentAST.root = if_first_cond_AST;
				currentAST.child = if_first_cond_AST!=null &&if_first_cond_AST.getFirstChild()!=null ?
					if_first_cond_AST.getFirstChild() : if_first_cond_AST;
				currentAST.advanceChildToEnd();
			}
			if_first_cond_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_160);
			} else {
			  throw ex;
			}
		}
		returnAST = if_first_cond_AST;
	}
	
	public final void if_cond() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST if_cond_AST = null;
		
		try {      // for error handling
			AST tmp378_AST = null;
			tmp378_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp378_AST);
			match(ELSIF);
			condition();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp379_AST = null;
			tmp379_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp379_AST);
			match(THEN);
			sequence_of_statements();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				if_cond_AST = (AST)currentAST.root;
				if_cond_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(IFCOND,"IfCond")).add(if_cond_AST));
				currentAST.root = if_cond_AST;
				currentAST.child = if_cond_AST!=null &&if_cond_AST.getFirstChild()!=null ?
					if_cond_AST.getFirstChild() : if_cond_AST;
				currentAST.advanceChildToEnd();
			}
			if_cond_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_160);
			} else {
			  throw ex;
			}
		}
		returnAST = if_cond_AST;
	}
	
	public final void if_last_cond() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST if_last_cond_AST = null;
		
		try {      // for error handling
			AST tmp380_AST = null;
			tmp380_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp380_AST);
			match(ELSE);
			sequence_of_statements();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				if_last_cond_AST = (AST)currentAST.root;
				if_last_cond_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(IFLASTCOND,"IfLastCond")).add(if_last_cond_AST));
				currentAST.root = if_last_cond_AST;
				currentAST.child = if_last_cond_AST!=null &&if_last_cond_AST.getFirstChild()!=null ?
					if_last_cond_AST.getFirstChild() : if_last_cond_AST;
				currentAST.advanceChildToEnd();
			}
			if_last_cond_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_3);
			} else {
			  throw ex;
			}
		}
		returnAST = if_last_cond_AST;
	}
	
	public final void if_end() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST if_end_AST = null;
		
		try {      // for error handling
			AST tmp381_AST = null;
			tmp381_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp381_AST);
			match(END);
			AST tmp382_AST = null;
			tmp382_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp382_AST);
			match(IF);
			{
			switch ( LA(1)) {
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			{
				identifier();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case SEMI:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			AST tmp383_AST = null;
			tmp383_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp383_AST);
			match(SEMI);
			if ( inputState.guessing==0 ) {
				if_end_AST = (AST)currentAST.root;
				if_end_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(IFEND,"IfEnd")).add(if_end_AST));
				currentAST.root = if_end_AST;
				currentAST.child = if_end_AST!=null &&if_end_AST.getFirstChild()!=null ?
					if_end_AST.getFirstChild() : if_end_AST;
				currentAST.advanceChildToEnd();
			}
			if_end_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_86);
			} else {
			  throw ex;
			}
		}
		returnAST = if_end_AST;
	}
	
	public final void inst_unit_comp_opt() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST inst_unit_comp_opt_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case COMPONENT:
			{
				AST tmp384_AST = null;
				tmp384_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp384_AST);
				match(COMPONENT);
				break;
			}
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				inst_unit_comp_opt_AST = (AST)currentAST.root;
				inst_unit_comp_opt_AST= (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(INSTUNITCOMPOPT,"inst_unit_comp_opt")).add(inst_unit_comp_opt_AST));
				currentAST.root = inst_unit_comp_opt_AST;
				currentAST.child = inst_unit_comp_opt_AST!=null &&inst_unit_comp_opt_AST.getFirstChild()!=null ?
					inst_unit_comp_opt_AST.getFirstChild() : inst_unit_comp_opt_AST;
				currentAST.advanceChildToEnd();
			}
			inst_unit_comp_opt_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_24);
			} else {
			  throw ex;
			}
		}
		returnAST = inst_unit_comp_opt_AST;
	}
	
	public final void inst_unit_comp() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST inst_unit_comp_AST = null;
		
		try {      // for error handling
			inst_unit_comp_opt();
			astFactory.addASTChild(currentAST, returnAST);
			name();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				inst_unit_comp_AST = (AST)currentAST.root;
				inst_unit_comp_AST= (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(INSTUNITCOMP,"inst_unit_comp")).add(inst_unit_comp_AST));
				currentAST.root = inst_unit_comp_AST;
				currentAST.child = inst_unit_comp_AST!=null &&inst_unit_comp_AST.getFirstChild()!=null ?
					inst_unit_comp_AST.getFirstChild() : inst_unit_comp_AST;
				currentAST.advanceChildToEnd();
			}
			inst_unit_comp_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_81);
			} else {
			  throw ex;
			}
		}
		returnAST = inst_unit_comp_AST;
	}
	
	public final void inst_unit_ent() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST inst_unit_ent_AST = null;
		
		try {      // for error handling
			AST tmp385_AST = null;
			tmp385_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp385_AST);
			match(ENTITY);
			name_dot_only();
			astFactory.addASTChild(currentAST, returnAST);
			{
			switch ( LA(1)) {
			case LPAREN:
			{
				AST tmp386_AST = null;
				tmp386_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp386_AST);
				match(LPAREN);
				identifier();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp387_AST = null;
				tmp387_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp387_AST);
				match(RPAREN);
				break;
			}
			case SEMI:
			case GENERIC:
			case PORT:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				inst_unit_ent_AST = (AST)currentAST.root;
				inst_unit_ent_AST= (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(INSTUNITENT,"inst_unit_ent")).add(inst_unit_ent_AST));
				currentAST.root = inst_unit_ent_AST;
				currentAST.child = inst_unit_ent_AST!=null &&inst_unit_ent_AST.getFirstChild()!=null ?
					inst_unit_ent_AST.getFirstChild() : inst_unit_ent_AST;
				currentAST.advanceChildToEnd();
			}
			inst_unit_ent_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_81);
			} else {
			  throw ex;
			}
		}
		returnAST = inst_unit_ent_AST;
	}
	
	public final void name_dot_only() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST name_dot_only_AST = null;
		
		try {      // for error handling
			{
			{
			identifier();
			astFactory.addASTChild(currentAST, returnAST);
			}
			{
			_loop448:
			do {
				if ((LA(1)==DOT)) {
					{
					name_dot();
					astFactory.addASTChild(currentAST, returnAST);
					}
				}
				else {
					break _loop448;
				}
				
			} while (true);
			}
			}
			if ( inputState.guessing==0 ) {
				name_dot_only_AST = (AST)currentAST.root;
				name_dot_only_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(NAME,"Name")).add(name_dot_only_AST));
				currentAST.root = name_dot_only_AST;
				currentAST.child = name_dot_only_AST!=null &&name_dot_only_AST.getFirstChild()!=null ?
					name_dot_only_AST.getFirstChild() : name_dot_only_AST;
				currentAST.advanceChildToEnd();
			}
			name_dot_only_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_161);
			} else {
			  throw ex;
			}
		}
		returnAST = name_dot_only_AST;
	}
	
	public final void inst_unit_conf() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST inst_unit_conf_AST = null;
		
		try {      // for error handling
			AST tmp388_AST = null;
			tmp388_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp388_AST);
			match(CONFIGURATION);
			name();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				inst_unit_conf_AST = (AST)currentAST.root;
				inst_unit_conf_AST= (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(INSTUNITCONF,"inst_unit_conf")).add(inst_unit_conf_AST));
				currentAST.root = inst_unit_conf_AST;
				currentAST.child = inst_unit_conf_AST!=null &&inst_unit_conf_AST.getFirstChild()!=null ?
					inst_unit_conf_AST.getFirstChild() : inst_unit_conf_AST;
				currentAST.advanceChildToEnd();
			}
			inst_unit_conf_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_81);
			} else {
			  throw ex;
			}
		}
		returnAST = inst_unit_conf_AST;
	}
	
	public final void port_assignment() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST port_assignment_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case VARASGN:
			{
				AST tmp389_AST = null;
				tmp389_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp389_AST);
				match(VARASGN);
				expression();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case RPAREN:
			case SEMI:
			case SIGNAL:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			switch ( LA(1)) {
			case SIGNAL:
			{
				AST tmp390_AST = null;
				tmp390_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp390_AST);
				match(SIGNAL);
				break;
			}
			case RPAREN:
			case SEMI:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				port_assignment_AST = (AST)currentAST.root;
				port_assignment_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(PORTASSIGNMENT,"port_assignment")).add(port_assignment_AST));
				currentAST.root = port_assignment_AST;
				currentAST.child = port_assignment_AST!=null &&port_assignment_AST.getFirstChild()!=null ?
					port_assignment_AST.getFirstChild() : port_assignment_AST;
				currentAST.advanceChildToEnd();
			}
			port_assignment_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_162);
			} else {
			  throw ex;
			}
		}
		returnAST = port_assignment_AST;
	}
	
	public final void constant_port_dec() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST constant_port_dec_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case CONSTANT:
			{
				AST tmp391_AST = null;
				tmp391_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp391_AST);
				match(CONSTANT);
				break;
			}
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				constant_port_dec_AST = (AST)currentAST.root;
				constant_port_dec_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(CONSTANTPORTDEC,"ConstantPortDec")).add(constant_port_dec_AST));
				currentAST.root = constant_port_dec_AST;
				currentAST.child = constant_port_dec_AST!=null &&constant_port_dec_AST.getFirstChild()!=null ?
					constant_port_dec_AST.getFirstChild() : constant_port_dec_AST;
				currentAST.advanceChildToEnd();
			}
			constant_port_dec_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_24);
			} else {
			  throw ex;
			}
		}
		returnAST = constant_port_dec_AST;
	}
	
	public final void constant_port_inq() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST constant_port_inq_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case IN:
			{
				AST tmp392_AST = null;
				tmp392_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp392_AST);
				match(IN);
				break;
			}
			case LPAREN:
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				constant_port_inq_AST = (AST)currentAST.root;
				constant_port_inq_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(CONSTANTPORTINQ,"constant_port_inq")).add(constant_port_inq_AST));
				currentAST.root = constant_port_inq_AST;
				currentAST.child = constant_port_inq_AST!=null &&constant_port_inq_AST.getFirstChild()!=null ?
					constant_port_inq_AST.getFirstChild() : constant_port_inq_AST;
				currentAST.advanceChildToEnd();
			}
			constant_port_inq_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_39);
			} else {
			  throw ex;
			}
		}
		returnAST = constant_port_inq_AST;
	}
	
	public final void constant_port() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST constant_port_AST = null;
		
		try {      // for error handling
			constant_port_dec();
			astFactory.addASTChild(currentAST, returnAST);
			identifier_list();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp393_AST = null;
			tmp393_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp393_AST);
			match(COLON);
			constant_port_inq();
			astFactory.addASTChild(currentAST, returnAST);
			subtype_indication();
			astFactory.addASTChild(currentAST, returnAST);
			port_assignment();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				constant_port_AST = (AST)currentAST.root;
				constant_port_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(CONSTANTPORT,"ConstantPort")).add(constant_port_AST));
				currentAST.root = constant_port_AST;
				currentAST.child = constant_port_AST!=null &&constant_port_AST.getFirstChild()!=null ?
					constant_port_AST.getFirstChild() : constant_port_AST;
				currentAST.advanceChildToEnd();
			}
			constant_port_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_162);
			} else {
			  throw ex;
			}
		}
		returnAST = constant_port_AST;
	}
	
	public final void signal_port_dec() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST signal_port_dec_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case SIGNAL:
			{
				AST tmp394_AST = null;
				tmp394_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp394_AST);
				match(SIGNAL);
				break;
			}
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				signal_port_dec_AST = (AST)currentAST.root;
				signal_port_dec_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(SIGNALPORTDEC,"SignalPortDec")).add(signal_port_dec_AST));
				currentAST.root = signal_port_dec_AST;
				currentAST.child = signal_port_dec_AST!=null &&signal_port_dec_AST.getFirstChild()!=null ?
					signal_port_dec_AST.getFirstChild() : signal_port_dec_AST;
				currentAST.advanceChildToEnd();
			}
			signal_port_dec_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_24);
			} else {
			  throw ex;
			}
		}
		returnAST = signal_port_dec_AST;
	}
	
	public final void signal_port_modeQ() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST signal_port_modeQ_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case IN:
			case OUT:
			case INOUT:
			case BUFFER:
			case LINKAGE:
			{
				mode();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case LPAREN:
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				signal_port_modeQ_AST = (AST)currentAST.root;
				signal_port_modeQ_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(SIGNALPORTMODEQ,"signal_port_modeQ")).add(signal_port_modeQ_AST));
				currentAST.root = signal_port_modeQ_AST;
				currentAST.child = signal_port_modeQ_AST!=null &&signal_port_modeQ_AST.getFirstChild()!=null ?
					signal_port_modeQ_AST.getFirstChild() : signal_port_modeQ_AST;
				currentAST.advanceChildToEnd();
			}
			signal_port_modeQ_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_39);
			} else {
			  throw ex;
			}
		}
		returnAST = signal_port_modeQ_AST;
	}
	
	public final void mode() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST mode_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case IN:
			{
				AST tmp395_AST = null;
				tmp395_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp395_AST);
				match(IN);
				mode_AST = (AST)currentAST.root;
				break;
			}
			case OUT:
			{
				AST tmp396_AST = null;
				tmp396_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp396_AST);
				match(OUT);
				mode_AST = (AST)currentAST.root;
				break;
			}
			case INOUT:
			{
				AST tmp397_AST = null;
				tmp397_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp397_AST);
				match(INOUT);
				mode_AST = (AST)currentAST.root;
				break;
			}
			case BUFFER:
			{
				AST tmp398_AST = null;
				tmp398_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp398_AST);
				match(BUFFER);
				mode_AST = (AST)currentAST.root;
				break;
			}
			case LINKAGE:
			{
				AST tmp399_AST = null;
				tmp399_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp399_AST);
				match(LINKAGE);
				if ( inputState.guessing==0 ) {
					mode_AST = (AST)currentAST.root;
					mode_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(MODE,"Mode")).add(mode_AST));
					currentAST.root = mode_AST;
					currentAST.child = mode_AST!=null &&mode_AST.getFirstChild()!=null ?
						mode_AST.getFirstChild() : mode_AST;
					currentAST.advanceChildToEnd();
				}
				mode_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_39);
			} else {
			  throw ex;
			}
		}
		returnAST = mode_AST;
	}
	
	public final void signal_port_busQ() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST signal_port_busQ_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case BUS:
			{
				AST tmp400_AST = null;
				tmp400_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp400_AST);
				match(BUS);
				break;
			}
			case VARASGN:
			case RPAREN:
			case SEMI:
			case SIGNAL:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				signal_port_busQ_AST = (AST)currentAST.root;
				signal_port_busQ_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(SIGNALPORTBUSQ,"signal_port_busQ")).add(signal_port_busQ_AST));
				currentAST.root = signal_port_busQ_AST;
				currentAST.child = signal_port_busQ_AST!=null &&signal_port_busQ_AST.getFirstChild()!=null ?
					signal_port_busQ_AST.getFirstChild() : signal_port_busQ_AST;
				currentAST.advanceChildToEnd();
			}
			signal_port_busQ_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_163);
			} else {
			  throw ex;
			}
		}
		returnAST = signal_port_busQ_AST;
	}
	
	public final void signal_port() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST signal_port_AST = null;
		
		try {      // for error handling
			signal_port_dec();
			astFactory.addASTChild(currentAST, returnAST);
			identifier_list();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp401_AST = null;
			tmp401_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp401_AST);
			match(COLON);
			signal_port_modeQ();
			astFactory.addASTChild(currentAST, returnAST);
			subtype_indication();
			astFactory.addASTChild(currentAST, returnAST);
			signal_port_busQ();
			astFactory.addASTChild(currentAST, returnAST);
			port_assignment();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				signal_port_AST = (AST)currentAST.root;
				signal_port_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(SIGNALPORT,"SignalPort")).add(signal_port_AST));
				currentAST.root = signal_port_AST;
				currentAST.child = signal_port_AST!=null &&signal_port_AST.getFirstChild()!=null ?
					signal_port_AST.getFirstChild() : signal_port_AST;
				currentAST.advanceChildToEnd();
			}
			signal_port_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_162);
			} else {
			  throw ex;
			}
		}
		returnAST = signal_port_AST;
	}
	
	public final void interface_declaration() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST interface_declaration_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case FILE:
			{
				interface_file_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				interface_declaration_AST = (AST)currentAST.root;
				break;
			}
			case TERMINAL:
			{
				interface_terminal_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				interface_declaration_AST = (AST)currentAST.root;
				break;
			}
			case QUANTITY:
			{
				interface_quantity_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				interface_declaration_AST = (AST)currentAST.root;
				break;
			}
			default:
				boolean synPredMatched385 = false;
				if (((_tokenSet_164.member(LA(1))) && (_tokenSet_165.member(LA(2))) && (_tokenSet_166.member(LA(3))))) {
					int _m385 = mark();
					synPredMatched385 = true;
					inputState.guessing++;
					try {
						{
						constant_port();
						}
					}
					catch (RecognitionException pe) {
						synPredMatched385 = false;
					}
					rewind(_m385);
inputState.guessing--;
				}
				if ( synPredMatched385 ) {
					constant_port();
					astFactory.addASTChild(currentAST, returnAST);
					interface_declaration_AST = (AST)currentAST.root;
				}
				else {
					boolean synPredMatched387 = false;
					if (((_tokenSet_167.member(LA(1))) && (_tokenSet_165.member(LA(2))) && (_tokenSet_168.member(LA(3))))) {
						int _m387 = mark();
						synPredMatched387 = true;
						inputState.guessing++;
						try {
							{
							signal_port();
							}
						}
						catch (RecognitionException pe) {
							synPredMatched387 = false;
						}
						rewind(_m387);
inputState.guessing--;
					}
					if ( synPredMatched387 ) {
						signal_port();
						astFactory.addASTChild(currentAST, returnAST);
						interface_declaration_AST = (AST)currentAST.root;
					}
					else if ((_tokenSet_169.member(LA(1))) && (_tokenSet_165.member(LA(2))) && (_tokenSet_168.member(LA(3)))) {
						interface_variable_declaration();
						astFactory.addASTChild(currentAST, returnAST);
						interface_declaration_AST = (AST)currentAST.root;
					}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}}
			}
			catch (RecognitionException ex) {
				if (inputState.guessing==0) {
					reportError(ex);
					recover(ex,_tokenSet_162);
				} else {
				  throw ex;
				}
			}
			returnAST = interface_declaration_AST;
		}
		
	public final void interface_variable_declaration() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST interface_variable_declaration_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case VARIABLE:
			{
				AST tmp402_AST = null;
				tmp402_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp402_AST);
				match(VARIABLE);
				break;
			}
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			identifier_list();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp403_AST = null;
			tmp403_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp403_AST);
			match(COLON);
			{
			switch ( LA(1)) {
			case IN:
			case OUT:
			case INOUT:
			case BUFFER:
			case LINKAGE:
			{
				mode();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case LPAREN:
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			subtype_indication();
			astFactory.addASTChild(currentAST, returnAST);
			{
			switch ( LA(1)) {
			case VARASGN:
			{
				AST tmp404_AST = null;
				tmp404_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp404_AST);
				match(VARASGN);
				expression();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case RPAREN:
			case SEMI:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				interface_variable_declaration_AST = (AST)currentAST.root;
				interface_variable_declaration_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(INTERFACEVARIABLEDECLARATION,"InterfaceVariableDeclaration")).add(interface_variable_declaration_AST));
				currentAST.root = interface_variable_declaration_AST;
				currentAST.child = interface_variable_declaration_AST!=null &&interface_variable_declaration_AST.getFirstChild()!=null ?
					interface_variable_declaration_AST.getFirstChild() : interface_variable_declaration_AST;
				currentAST.advanceChildToEnd();
			}
			interface_variable_declaration_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_162);
			} else {
			  throw ex;
			}
		}
		returnAST = interface_variable_declaration_AST;
	}
	
	public final void interface_file_declaration() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST interface_file_declaration_AST = null;
		
		try {      // for error handling
			AST tmp405_AST = null;
			tmp405_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp405_AST);
			match(FILE);
			identifier_list();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp406_AST = null;
			tmp406_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp406_AST);
			match(COLON);
			subtype_indication();
			astFactory.addASTChild(currentAST, returnAST);
			interface_file_declaration_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_162);
			} else {
			  throw ex;
			}
		}
		returnAST = interface_file_declaration_AST;
	}
	
	public final void interface_terminal_declaration() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST interface_terminal_declaration_AST = null;
		
		try {      // for error handling
			AST tmp407_AST = null;
			tmp407_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp407_AST);
			match(TERMINAL);
			identifier_list();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp408_AST = null;
			tmp408_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp408_AST);
			match(COLON);
			subnature_indication();
			astFactory.addASTChild(currentAST, returnAST);
			interface_terminal_declaration_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_162);
			} else {
			  throw ex;
			}
		}
		returnAST = interface_terminal_declaration_AST;
	}
	
	public final void interface_quantity_declaration() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST interface_quantity_declaration_AST = null;
		
		try {      // for error handling
			AST tmp409_AST = null;
			tmp409_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp409_AST);
			match(QUANTITY);
			identifier_list();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp410_AST = null;
			tmp410_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp410_AST);
			match(COLON);
			{
			switch ( LA(1)) {
			case IN:
			{
				AST tmp411_AST = null;
				tmp411_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp411_AST);
				match(IN);
				break;
			}
			case OUT:
			{
				AST tmp412_AST = null;
				tmp412_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp412_AST);
				match(OUT);
				break;
			}
			case LPAREN:
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			subtype_indication();
			astFactory.addASTChild(currentAST, returnAST);
			{
			switch ( LA(1)) {
			case VARASGN:
			{
				AST tmp413_AST = null;
				tmp413_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp413_AST);
				match(VARASGN);
				expression();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case RPAREN:
			case SEMI:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			interface_quantity_declaration_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_162);
			} else {
			  throw ex;
			}
		}
		returnAST = interface_quantity_declaration_AST;
	}
	
	public final void port_element() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST port_element_AST = null;
		
		try {      // for error handling
			interface_declaration();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				port_element_AST = (AST)currentAST.root;
				port_element_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(PORTELEMENT,"PortElement")).add(port_element_AST));
				currentAST.root = port_element_AST;
				currentAST.child = port_element_AST!=null &&port_element_AST.getFirstChild()!=null ?
					port_element_AST.getFirstChild() : port_element_AST;
				currentAST.advanceChildToEnd();
			}
			port_element_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_162);
			} else {
			  throw ex;
			}
		}
		returnAST = port_element_AST;
	}
	
	public final void semi_port_element() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST semi_port_element_AST = null;
		
		try {      // for error handling
			AST tmp414_AST = null;
			tmp414_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp414_AST);
			match(SEMI);
			port_element();
			astFactory.addASTChild(currentAST, returnAST);
			semi_port_element_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_162);
			} else {
			  throw ex;
			}
		}
		returnAST = semi_port_element_AST;
	}
	
	public final void iteration_scheme() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST iteration_scheme_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case WHILE:
			{
				iteration_scheme_while();
				astFactory.addASTChild(currentAST, returnAST);
				iteration_scheme_AST = (AST)currentAST.root;
				break;
			}
			case FOR:
			{
				iteration_scheme_for();
				astFactory.addASTChild(currentAST, returnAST);
				iteration_scheme_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_170);
			} else {
			  throw ex;
			}
		}
		returnAST = iteration_scheme_AST;
	}
	
	public final void iteration_scheme_while() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST iteration_scheme_while_AST = null;
		
		try {      // for error handling
			AST tmp415_AST = null;
			tmp415_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp415_AST);
			match(WHILE);
			condition();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				iteration_scheme_while_AST = (AST)currentAST.root;
				iteration_scheme_while_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(ITERATIONSCHEMEWHILE,"iteration_scheme_while")).add(iteration_scheme_while_AST));
				currentAST.root = iteration_scheme_while_AST;
				currentAST.child = iteration_scheme_while_AST!=null &&iteration_scheme_while_AST.getFirstChild()!=null ?
					iteration_scheme_while_AST.getFirstChild() : iteration_scheme_while_AST;
				currentAST.advanceChildToEnd();
			}
			iteration_scheme_while_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_170);
			} else {
			  throw ex;
			}
		}
		returnAST = iteration_scheme_while_AST;
	}
	
	public final void iteration_scheme_for() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST iteration_scheme_for_AST = null;
		
		try {      // for error handling
			AST tmp416_AST = null;
			tmp416_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp416_AST);
			match(FOR);
			parameter_specification();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				iteration_scheme_for_AST = (AST)currentAST.root;
				iteration_scheme_for_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(ITERATIONSCHEMEFOR,"iteration_scheme_for")).add(iteration_scheme_for_AST));
				currentAST.root = iteration_scheme_for_AST;
				currentAST.child = iteration_scheme_for_AST!=null &&iteration_scheme_for_AST.getFirstChild()!=null ?
					iteration_scheme_for_AST.getFirstChild() : iteration_scheme_for_AST;
				currentAST.advanceChildToEnd();
			}
			iteration_scheme_for_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_170);
			} else {
			  throw ex;
			}
		}
		returnAST = iteration_scheme_for_AST;
	}
	
	public final void logical_name_list() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST logical_name_list_AST = null;
		
		try {      // for error handling
			logical_name();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop425:
			do {
				if ((LA(1)==COMMA)) {
					AST tmp417_AST = null;
					tmp417_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp417_AST);
					match(COMMA);
					logical_name();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop425;
				}
				
			} while (true);
			}
			logical_name_list_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_1);
			} else {
			  throw ex;
			}
		}
		returnAST = logical_name_list_AST;
	}
	
	public final void secondary_unit() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST secondary_unit_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case ARCHITECTURE:
			{
				architecture_body();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case PACKAGE:
			{
				package_body();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				secondary_unit_AST = (AST)currentAST.root;
				secondary_unit_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(SECONDARYUNIT,"SecondaryUnit")).add(secondary_unit_AST));
				currentAST.root = secondary_unit_AST;
				currentAST.child = secondary_unit_AST!=null &&secondary_unit_AST.getFirstChild()!=null ?
					secondary_unit_AST.getFirstChild() : secondary_unit_AST;
				currentAST.advanceChildToEnd();
			}
			secondary_unit_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_55);
			} else {
			  throw ex;
			}
		}
		returnAST = secondary_unit_AST;
	}
	
	public final void primary_unit() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST primary_unit_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case ENTITY:
			{
				entity_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				primary_unit_AST = (AST)currentAST.root;
				break;
			}
			case CONFIGURATION:
			{
				configuration_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				primary_unit_AST = (AST)currentAST.root;
				break;
			}
			case PACKAGE:
			{
				package_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				if ( inputState.guessing==0 ) {
					primary_unit_AST = (AST)currentAST.root;
					primary_unit_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(PRIMARYUNIT,"PrimaryUnit")).add(primary_unit_AST));
					currentAST.root = primary_unit_AST;
					currentAST.child = primary_unit_AST!=null &&primary_unit_AST.getFirstChild()!=null ?
						primary_unit_AST.getFirstChild() : primary_unit_AST;
					currentAST.advanceChildToEnd();
				}
				primary_unit_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_55);
			} else {
			  throw ex;
			}
		}
		returnAST = primary_unit_AST;
	}
	
	public final void literal() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST literal_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case NULLTOK:
			{
				AST tmp418_AST = null;
				tmp418_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp418_AST);
				match(NULLTOK);
				break;
			}
			case BIT_STRING_LITERAL:
			{
				bit_string_lit();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case STRING_LITERAL:
			{
				string_lit();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			default:
				boolean synPredMatched419 = false;
				if (((_tokenSet_171.member(LA(1))) && (_tokenSet_21.member(LA(2))) && (_tokenSet_22.member(LA(3))))) {
					int _m419 = mark();
					synPredMatched419 = true;
					inputState.guessing++;
					try {
						{
						enumeration_literal();
						}
					}
					catch (RecognitionException pe) {
						synPredMatched419 = false;
					}
					rewind(_m419);
inputState.guessing--;
				}
				if ( synPredMatched419 ) {
					enumeration_literal();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else if ((_tokenSet_172.member(LA(1))) && (_tokenSet_173.member(LA(2))) && (_tokenSet_52.member(LA(3)))) {
					numeric_literal();
					astFactory.addASTChild(currentAST, returnAST);
				}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				literal_AST = (AST)currentAST.root;
				literal_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(LITERAL,"Literal")).add(literal_AST));
				currentAST.root = literal_AST;
				currentAST.child = literal_AST!=null &&literal_AST.getFirstChild()!=null ?
					literal_AST.getFirstChild() : literal_AST;
				currentAST.advanceChildToEnd();
			}
			literal_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_21);
			} else {
			  throw ex;
			}
		}
		returnAST = literal_AST;
	}
	
	public final void bit_string_lit() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST bit_string_lit_AST = null;
		
		try {      // for error handling
			AST tmp419_AST = null;
			tmp419_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp419_AST);
			match(BIT_STRING_LITERAL);
			if ( inputState.guessing==0 ) {
				bit_string_lit_AST = (AST)currentAST.root;
				bit_string_lit_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(BITSTRINGLIT,"BitStringLit")).add(bit_string_lit_AST));
				currentAST.root = bit_string_lit_AST;
				currentAST.child = bit_string_lit_AST!=null &&bit_string_lit_AST.getFirstChild()!=null ?
					bit_string_lit_AST.getFirstChild() : bit_string_lit_AST;
				currentAST.advanceChildToEnd();
			}
			bit_string_lit_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_21);
			} else {
			  throw ex;
			}
		}
		returnAST = bit_string_lit_AST;
	}
	
	public final void string_lit() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST string_lit_AST = null;
		
		try {      // for error handling
			AST tmp420_AST = null;
			tmp420_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp420_AST);
			match(STRING_LITERAL);
			if ( inputState.guessing==0 ) {
				string_lit_AST = (AST)currentAST.root;
				string_lit_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(STRINGLIT,"StringLit")).add(string_lit_AST));
				currentAST.root = string_lit_AST;
				currentAST.child = string_lit_AST!=null &&string_lit_AST.getFirstChild()!=null ?
					string_lit_AST.getFirstChild() : string_lit_AST;
				currentAST.advanceChildToEnd();
			}
			string_lit_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_21);
			} else {
			  throw ex;
			}
		}
		returnAST = string_lit_AST;
	}
	
	public final void numeric_literal() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST numeric_literal_AST = null;
		
		try {      // for error handling
			{
			boolean synPredMatched483 = false;
			if (((LA(1)==DECIMAL_LITERAL||LA(1)==BASED_LITERAL) && (_tokenSet_21.member(LA(2))) && (_tokenSet_22.member(LA(3))))) {
				int _m483 = mark();
				synPredMatched483 = true;
				inputState.guessing++;
				try {
					{
					abstract_literal();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched483 = false;
				}
				rewind(_m483);
inputState.guessing--;
			}
			if ( synPredMatched483 ) {
				abstract_literal();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else if ((_tokenSet_172.member(LA(1))) && (_tokenSet_173.member(LA(2))) && (_tokenSet_52.member(LA(3)))) {
				physical_literal();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			if ( inputState.guessing==0 ) {
				numeric_literal_AST = (AST)currentAST.root;
				numeric_literal_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(NUMERICLITERAL,"NumericLiteral")).add(numeric_literal_AST));
				currentAST.root = numeric_literal_AST;
				currentAST.child = numeric_literal_AST!=null &&numeric_literal_AST.getFirstChild()!=null ?
					numeric_literal_AST.getFirstChild() : numeric_literal_AST;
				currentAST.advanceChildToEnd();
			}
			numeric_literal_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_21);
			} else {
			  throw ex;
			}
		}
		returnAST = numeric_literal_AST;
	}
	
	public final void logical_name() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST logical_name_AST = null;
		
		try {      // for error handling
			identifier();
			astFactory.addASTChild(currentAST, returnAST);
			logical_name_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_174);
			} else {
			  throw ex;
			}
		}
		returnAST = logical_name_AST;
	}
	
	public final void loop_statement() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST loop_statement_AST = null;
		
		try {      // for error handling
			label_colon_wrap();
			astFactory.addASTChild(currentAST, returnAST);
			iteration_scheme_q();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp421_AST = null;
			tmp421_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp421_AST);
			match(LOOP);
			sequence_of_statements();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp422_AST = null;
			tmp422_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp422_AST);
			match(END);
			AST tmp423_AST = null;
			tmp423_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp423_AST);
			match(LOOP);
			{
			switch ( LA(1)) {
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			{
				identifier();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case SEMI:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			AST tmp424_AST = null;
			tmp424_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp424_AST);
			match(SEMI);
			if ( inputState.guessing==0 ) {
				loop_statement_AST = (AST)currentAST.root;
				loop_statement_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(LOOPSTATEMENT,"LoopStatement")).add(loop_statement_AST));
				currentAST.root = loop_statement_AST;
				currentAST.child = loop_statement_AST!=null &&loop_statement_AST.getFirstChild()!=null ?
					loop_statement_AST.getFirstChild() : loop_statement_AST;
				currentAST.advanceChildToEnd();
			}
			loop_statement_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_86);
			} else {
			  throw ex;
			}
		}
		returnAST = loop_statement_AST;
	}
	
	public final void iteration_scheme_q() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST iteration_scheme_q_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case FOR:
			case WHILE:
			{
				iteration_scheme();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case LOOP:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				iteration_scheme_q_AST = (AST)currentAST.root;
				iteration_scheme_q_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(ITERATIONSCHEMEQ,"iteration_scheme_q")).add(iteration_scheme_q_AST));
				currentAST.root = iteration_scheme_q_AST;
				currentAST.child = iteration_scheme_q_AST!=null &&iteration_scheme_q_AST.getFirstChild()!=null ?
					iteration_scheme_q_AST.getFirstChild() : iteration_scheme_q_AST;
				currentAST.advanceChildToEnd();
			}
			iteration_scheme_q_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_170);
			} else {
			  throw ex;
			}
		}
		returnAST = iteration_scheme_q_AST;
	}
	
	public final void name_dot() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST name_dot_AST = null;
		
		try {      // for error handling
			AST tmp425_AST = null;
			tmp425_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp425_AST);
			match(DOT);
			suffix();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				name_dot_AST = (AST)currentAST.root;
				name_dot_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(NAMEDOT,"NameDot")).add(name_dot_AST));
				currentAST.root = name_dot_AST;
				currentAST.child = name_dot_AST!=null &&name_dot_AST.getFirstChild()!=null ?
					name_dot_AST.getFirstChild() : name_dot_AST;
				currentAST.advanceChildToEnd();
			}
			name_dot_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_32);
			} else {
			  throw ex;
			}
		}
		returnAST = name_dot_AST;
	}
	
	public final void name_apostrophy() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST name_apostrophy_AST = null;
		
		try {      // for error handling
			AST tmp426_AST = null;
			tmp426_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp426_AST);
			match(APOSTROPHE);
			aggregate();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				name_apostrophy_AST = (AST)currentAST.root;
				name_apostrophy_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(NAMEAPOSTROPHY,"NameApostrophy")).add(name_apostrophy_AST));
				currentAST.root = name_apostrophy_AST;
				currentAST.child = name_apostrophy_AST!=null &&name_apostrophy_AST.getFirstChild()!=null ?
					name_apostrophy_AST.getFirstChild() : name_apostrophy_AST;
				currentAST.advanceChildToEnd();
			}
			name_apostrophy_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_175);
			} else {
			  throw ex;
			}
		}
		returnAST = name_apostrophy_AST;
	}
	
	public final void name_expression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST name_expression_AST = null;
		
		try {      // for error handling
			AST tmp427_AST = null;
			tmp427_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp427_AST);
			match(LPAREN);
			expression_or_range();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop466:
			do {
				if ((LA(1)==COMMA)) {
					AST tmp428_AST = null;
					tmp428_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp428_AST);
					match(COMMA);
					expression_or_range();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop466;
				}
				
			} while (true);
			}
			AST tmp429_AST = null;
			tmp429_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp429_AST);
			match(RPAREN);
			if ( inputState.guessing==0 ) {
				name_expression_AST = (AST)currentAST.root;
				name_expression_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(NAMEEXPRESSION,"NameExpression")).add(name_expression_AST));
				currentAST.root = name_expression_AST;
				currentAST.child = name_expression_AST!=null &&name_expression_AST.getFirstChild()!=null ?
					name_expression_AST.getFirstChild() : name_expression_AST;
				currentAST.advanceChildToEnd();
			}
			name_expression_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_32);
			} else {
			  throw ex;
			}
		}
		returnAST = name_expression_AST;
	}
	
	public final void name_parameter_part() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST name_parameter_part_AST = null;
		
		try {      // for error handling
			AST tmp430_AST = null;
			tmp430_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp430_AST);
			match(LPAREN);
			actual_parameter_part();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp431_AST = null;
			tmp431_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp431_AST);
			match(RPAREN);
			if ( inputState.guessing==0 ) {
				name_parameter_part_AST = (AST)currentAST.root;
				name_parameter_part_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(NAMEPARAMETERPART,"NameParameterPart")).add(name_parameter_part_AST));
				currentAST.root = name_parameter_part_AST;
				currentAST.child = name_parameter_part_AST!=null &&name_parameter_part_AST.getFirstChild()!=null ?
					name_parameter_part_AST.getFirstChild() : name_parameter_part_AST;
				currentAST.advanceChildToEnd();
			}
			name_parameter_part_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_175);
			} else {
			  throw ex;
			}
		}
		returnAST = name_parameter_part_AST;
	}
	
	public final void name_range() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST name_range_AST = null;
		
		try {      // for error handling
			AST tmp432_AST = null;
			tmp432_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp432_AST);
			match(LPAREN);
			discrete_range();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop470:
			do {
				if ((LA(1)==COMMA)) {
					AST tmp433_AST = null;
					tmp433_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp433_AST);
					match(COMMA);
					discrete_range();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop470;
				}
				
			} while (true);
			}
			AST tmp434_AST = null;
			tmp434_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp434_AST);
			match(RPAREN);
			if ( inputState.guessing==0 ) {
				name_range_AST = (AST)currentAST.root;
				name_range_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(NAMERANGE,"NameRange")).add(name_range_AST));
				currentAST.root = name_range_AST;
				currentAST.child = name_range_AST!=null &&name_range_AST.getFirstChild()!=null ?
					name_range_AST.getFirstChild() : name_range_AST;
				currentAST.advanceChildToEnd();
			}
			name_range_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_175);
			} else {
			  throw ex;
			}
		}
		returnAST = name_range_AST;
	}
	
	public final void name_apostrophe_or_aggregate() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST name_apostrophe_or_aggregate_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case LBRACKET:
			{
				signature();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case APOSTROPHE:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			AST tmp435_AST = null;
			tmp435_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp435_AST);
			match(APOSTROPHE);
			{
			switch ( LA(1)) {
			case LPAREN:
			{
				aggregate();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case ACROSS:
			case RANGETOK:
			case THROUGH:
			case TOLERANCE:
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			{
				attribute_designator();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				name_apostrophe_or_aggregate_AST = (AST)currentAST.root;
				name_apostrophe_or_aggregate_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(NAMEATTRIBUTE,"NameAttribute")).add(name_apostrophe_or_aggregate_AST));
				currentAST.root = name_apostrophe_or_aggregate_AST;
				currentAST.child = name_apostrophe_or_aggregate_AST!=null &&name_apostrophe_or_aggregate_AST.getFirstChild()!=null ?
					name_apostrophe_or_aggregate_AST.getFirstChild() : name_apostrophe_or_aggregate_AST;
				currentAST.advanceChildToEnd();
			}
			name_apostrophe_or_aggregate_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_32);
			} else {
			  throw ex;
			}
		}
		returnAST = name_apostrophe_or_aggregate_AST;
	}
	
	public final void suffix() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST suffix_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			{
				identifier();
				astFactory.addASTChild(currentAST, returnAST);
				suffix_AST = (AST)currentAST.root;
				break;
			}
			case CHARACTER_LITERAL:
			{
				AST tmp436_AST = null;
				tmp436_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp436_AST);
				match(CHARACTER_LITERAL);
				suffix_AST = (AST)currentAST.root;
				break;
			}
			case STRING_LITERAL:
			{
				AST tmp437_AST = null;
				tmp437_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp437_AST);
				match(STRING_LITERAL);
				suffix_AST = (AST)currentAST.root;
				break;
			}
			case ALL:
			{
				AST tmp438_AST = null;
				tmp438_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp438_AST);
				match(ALL);
				suffix_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_32);
			} else {
			  throw ex;
			}
		}
		returnAST = suffix_AST;
	}
	
	public final void name_attribute() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST name_attribute_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case LBRACKET:
			{
				signature();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case APOSTROPHE:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			AST tmp439_AST = null;
			tmp439_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp439_AST);
			match(APOSTROPHE);
			attribute_designator();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				name_attribute_AST = (AST)currentAST.root;
				name_attribute_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(NAMEATTRIBUTE,"NameAttribute")).add(name_attribute_AST));
				currentAST.root = name_attribute_AST;
				currentAST.child = name_attribute_AST!=null &&name_attribute_AST.getFirstChild()!=null ?
					name_attribute_AST.getFirstChild() : name_attribute_AST;
				currentAST.advanceChildToEnd();
			}
			name_attribute_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_138);
			} else {
			  throw ex;
			}
		}
		returnAST = name_attribute_AST;
	}
	
	public final void expression_or_range() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST expression_or_range_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case DECIMAL_LITERAL:
			case BASED_LITERAL:
			case LPAREN:
			case CHARACTER_LITERAL:
			case STRING_LITERAL:
			case NEW:
			case PLUS:
			case MINUS:
			case ABS:
			case NOT:
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			case NULLTOK:
			case BIT_STRING_LITERAL:
			{
				expression();
				astFactory.addASTChild(currentAST, returnAST);
				{
				switch ( LA(1)) {
				case ARROW:
				case TO:
				case DOWNTO:
				{
					{
					switch ( LA(1)) {
					case TO:
					case DOWNTO:
					{
						direction();
						astFactory.addASTChild(currentAST, returnAST);
						break;
					}
					case ARROW:
					{
						AST tmp440_AST = null;
						tmp440_AST = astFactory.create(LT(1));
						astFactory.addASTChild(currentAST, tmp440_AST);
						match(ARROW);
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					expression();
					astFactory.addASTChild(currentAST, returnAST);
					break;
				}
				case RPAREN:
				case COMMA:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				expression_or_range_AST = (AST)currentAST.root;
				break;
			}
			case OPEN:
			{
				AST tmp441_AST = null;
				tmp441_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp441_AST);
				match(OPEN);
				if ( inputState.guessing==0 ) {
					expression_or_range_AST = (AST)currentAST.root;
					expression_or_range_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(EXPRESSIONORRANGE,"ExpressionOrRange")).add(expression_or_range_AST));
					currentAST.root = expression_or_range_AST;
					currentAST.child = expression_or_range_AST!=null &&expression_or_range_AST.getFirstChild()!=null ?
						expression_or_range_AST.getFirstChild() : expression_or_range_AST;
					currentAST.advanceChildToEnd();
				}
				expression_or_range_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_27);
			} else {
			  throw ex;
			}
		}
		returnAST = expression_or_range_AST;
	}
	
	public final void nature_definition() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST nature_definition_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			{
				scalar_nature_definition();
				astFactory.addASTChild(currentAST, returnAST);
				nature_definition_AST = (AST)currentAST.root;
				break;
			}
			case ARRAY:
			case RECORD:
			{
				composite_nature_definition();
				astFactory.addASTChild(currentAST, returnAST);
				nature_definition_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_1);
			} else {
			  throw ex;
			}
		}
		returnAST = nature_definition_AST;
	}
	
	public final void scalar_nature_definition() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST scalar_nature_definition_AST = null;
		
		try {      // for error handling
			name();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp442_AST = null;
			tmp442_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp442_AST);
			match(ACROSS);
			name();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp443_AST = null;
			tmp443_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp443_AST);
			match(THROUGH);
			name();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp444_AST = null;
			tmp444_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp444_AST);
			match(REFERENCE);
			scalar_nature_definition_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_1);
			} else {
			  throw ex;
			}
		}
		returnAST = scalar_nature_definition_AST;
	}
	
	public final void nature_element_declaration() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST nature_element_declaration_AST = null;
		
		try {      // for error handling
			identifier_list();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp445_AST = null;
			tmp445_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp445_AST);
			match(COLON);
			element_subnature_definition();
			astFactory.addASTChild(currentAST, returnAST);
			nature_element_declaration_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_91);
			} else {
			  throw ex;
			}
		}
		returnAST = nature_element_declaration_AST;
	}
	
	public final void next_statement() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST next_statement_AST = null;
		
		try {      // for error handling
			AST tmp446_AST = null;
			tmp446_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp446_AST);
			match(NEXT);
			{
			switch ( LA(1)) {
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			{
				identifier();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case SEMI:
			case WHEN:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			switch ( LA(1)) {
			case WHEN:
			{
				AST tmp447_AST = null;
				tmp447_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp447_AST);
				match(WHEN);
				condition();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case SEMI:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			AST tmp448_AST = null;
			tmp448_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp448_AST);
			match(SEMI);
			if ( inputState.guessing==0 ) {
				next_statement_AST = (AST)currentAST.root;
				next_statement_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(NEXTSTATEMENT,"NextStatement")).add(next_statement_AST));
				currentAST.root = next_statement_AST;
				currentAST.child = next_statement_AST!=null &&next_statement_AST.getFirstChild()!=null ?
					next_statement_AST.getFirstChild() : next_statement_AST;
				currentAST.advanceChildToEnd();
			}
			next_statement_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_86);
			} else {
			  throw ex;
			}
		}
		returnAST = next_statement_AST;
	}
	
	public final void physical_literal() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST physical_literal_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case DECIMAL_LITERAL:
			case BASED_LITERAL:
			{
				abstract_literal();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			name();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				physical_literal_AST = (AST)currentAST.root;
				physical_literal_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(PHYSICALLITERAL,"PhysicalLiteral")).add(physical_literal_AST));
				currentAST.root = physical_literal_AST;
				currentAST.child = physical_literal_AST!=null &&physical_literal_AST.getFirstChild()!=null ?
					physical_literal_AST.getFirstChild() : physical_literal_AST;
				currentAST.advanceChildToEnd();
			}
			physical_literal_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_21);
			} else {
			  throw ex;
			}
		}
		returnAST = physical_literal_AST;
	}
	
	public final void object_declaration() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST object_declaration_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case CONSTANT:
			{
				constant_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				object_declaration_AST = (AST)currentAST.root;
				break;
			}
			case SIGNAL:
			{
				signal_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				object_declaration_AST = (AST)currentAST.root;
				break;
			}
			case VARIABLE:
			case SHARED:
			{
				variable_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				object_declaration_AST = (AST)currentAST.root;
				break;
			}
			case FILE:
			{
				file_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				object_declaration_AST = (AST)currentAST.root;
				break;
			}
			case TERMINAL:
			{
				terminal_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				object_declaration_AST = (AST)currentAST.root;
				break;
			}
			case QUANTITY:
			{
				quantity_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				object_declaration_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_138);
			} else {
			  throw ex;
			}
		}
		returnAST = object_declaration_AST;
	}
	
	public final void package_body_declarative_part() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST package_body_declarative_part_AST = null;
		
		try {      // for error handling
			{
			_loop496:
			do {
				if ((_tokenSet_176.member(LA(1)))) {
					package_body_declarative_item();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop496;
				}
				
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				package_body_declarative_part_AST = (AST)currentAST.root;
				package_body_declarative_part_AST= (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(PACKAGEBODYDECLARATIVEPART,"PackageBodyDeclarativePart")).add(package_body_declarative_part_AST));
				currentAST.root = package_body_declarative_part_AST;
				currentAST.child = package_body_declarative_part_AST!=null &&package_body_declarative_part_AST.getFirstChild()!=null ?
					package_body_declarative_part_AST.getFirstChild() : package_body_declarative_part_AST;
				currentAST.advanceChildToEnd();
			}
			package_body_declarative_part_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_3);
			} else {
			  throw ex;
			}
		}
		returnAST = package_body_declarative_part_AST;
	}
	
	public final void package_body_declarative_item() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST package_body_declarative_item_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case TYPE:
			{
				type_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				package_body_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case SUBTYPE:
			{
				subtype_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				package_body_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case CONSTANT:
			{
				constant_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				package_body_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case VARIABLE:
			case SHARED:
			{
				variable_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				package_body_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case FILE:
			{
				file_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				package_body_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case ALIAS:
			{
				alias_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				package_body_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case USE:
			case CONTEXT:
			{
				use_clause();
				astFactory.addASTChild(currentAST, returnAST);
				package_body_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			default:
				boolean synPredMatched493 = false;
				if (((_tokenSet_11.member(LA(1))) && (_tokenSet_12.member(LA(2))) && (_tokenSet_13.member(LA(3))))) {
					int _m493 = mark();
					synPredMatched493 = true;
					inputState.guessing++;
					try {
						{
						subprogram_declaration();
						}
					}
					catch (RecognitionException pe) {
						synPredMatched493 = false;
					}
					rewind(_m493);
inputState.guessing--;
				}
				if ( synPredMatched493 ) {
					subprogram_declaration();
					astFactory.addASTChild(currentAST, returnAST);
					package_body_declarative_item_AST = (AST)currentAST.root;
				}
				else if ((_tokenSet_11.member(LA(1))) && (_tokenSet_12.member(LA(2))) && (_tokenSet_14.member(LA(3)))) {
					subprogram_body();
					astFactory.addASTChild(currentAST, returnAST);
					package_body_declarative_item_AST = (AST)currentAST.root;
				}
				else if ((LA(1)==GROUP) && ((LA(2) >= BASIC_IDENTIFIER && LA(2) <= REFERENCE)) && (LA(3)==IS)) {
					group_template_declaration();
					astFactory.addASTChild(currentAST, returnAST);
					package_body_declarative_item_AST = (AST)currentAST.root;
				}
				else if ((LA(1)==GROUP) && ((LA(2) >= BASIC_IDENTIFIER && LA(2) <= REFERENCE)) && (LA(3)==COLON)) {
					group_declaration();
					astFactory.addASTChild(currentAST, returnAST);
					package_body_declarative_item_AST = (AST)currentAST.root;
				}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_177);
			} else {
			  throw ex;
			}
		}
		returnAST = package_body_declarative_item_AST;
	}
	
	public final void package_declarative_part() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST package_declarative_part_AST = null;
		
		try {      // for error handling
			{
			_loop503:
			do {
				if ((_tokenSet_178.member(LA(1)))) {
					package_declarative_item();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop503;
				}
				
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				package_declarative_part_AST = (AST)currentAST.root;
				package_declarative_part_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(PACKAGEDECLARATIVEPART,"PackageDeclarativePart")).add(package_declarative_part_AST));
				currentAST.root = package_declarative_part_AST;
				currentAST.child = package_declarative_part_AST!=null &&package_declarative_part_AST.getFirstChild()!=null ?
					package_declarative_part_AST.getFirstChild() : package_declarative_part_AST;
				currentAST.advanceChildToEnd();
			}
			package_declarative_part_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_3);
			} else {
			  throw ex;
			}
		}
		returnAST = package_declarative_part_AST;
	}
	
	public final void package_declarative_item() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST package_declarative_item_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case PROCEDURE:
			case FUNCTION:
			case PURE:
			case IMPURE:
			{
				subprogram_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				package_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case TYPE:
			{
				type_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				package_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case SUBTYPE:
			{
				subtype_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				package_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case CONSTANT:
			{
				constant_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				package_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case SIGNAL:
			{
				signal_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				package_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case VARIABLE:
			case SHARED:
			{
				variable_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				package_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case FILE:
			{
				file_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				package_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case ALIAS:
			{
				alias_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				package_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case COMPONENT:
			{
				component_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				package_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case DISCONNECT:
			{
				disconnection_specification();
				astFactory.addASTChild(currentAST, returnAST);
				package_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case USE:
			case CONTEXT:
			{
				use_clause();
				astFactory.addASTChild(currentAST, returnAST);
				package_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case NATURE:
			{
				nature_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				package_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case SUBNATURE:
			{
				subnature_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				package_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case TERMINAL:
			{
				terminal_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				package_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			default:
				if ((LA(1)==ATTRIBUTE) && ((LA(2) >= BASIC_IDENTIFIER && LA(2) <= REFERENCE)) && (LA(3)==COLON)) {
					attribute_declaration();
					astFactory.addASTChild(currentAST, returnAST);
					package_declarative_item_AST = (AST)currentAST.root;
				}
				else if ((LA(1)==ATTRIBUTE) && (_tokenSet_15.member(LA(2))) && (LA(3)==OF)) {
					attribute_specification();
					astFactory.addASTChild(currentAST, returnAST);
					package_declarative_item_AST = (AST)currentAST.root;
				}
				else if ((LA(1)==GROUP) && ((LA(2) >= BASIC_IDENTIFIER && LA(2) <= REFERENCE)) && (LA(3)==IS)) {
					group_template_declaration();
					astFactory.addASTChild(currentAST, returnAST);
					package_declarative_item_AST = (AST)currentAST.root;
				}
				else if ((LA(1)==GROUP) && ((LA(2) >= BASIC_IDENTIFIER && LA(2) <= REFERENCE)) && (LA(3)==COLON)) {
					group_declaration();
					astFactory.addASTChild(currentAST, returnAST);
					package_declarative_item_AST = (AST)currentAST.root;
				}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_179);
			} else {
			  throw ex;
			}
		}
		returnAST = package_declarative_item_AST;
	}
	
	public final void physical_type_definition() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST physical_type_definition_AST = null;
		
		try {      // for error handling
			range_constraint();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp449_AST = null;
			tmp449_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp449_AST);
			match(UNITS);
			base_unit_declaration();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop509:
			do {
				if (((LA(1) >= BASIC_IDENTIFIER && LA(1) <= REFERENCE))) {
					secondary_unit_declaration();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop509;
				}
				
			} while (true);
			}
			AST tmp450_AST = null;
			tmp450_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp450_AST);
			match(END);
			AST tmp451_AST = null;
			tmp451_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp451_AST);
			match(UNITS);
			{
			switch ( LA(1)) {
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			{
				identifier();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case SEMI:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			physical_type_definition_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_1);
			} else {
			  throw ex;
			}
		}
		returnAST = physical_type_definition_AST;
	}
	
	public final void secondary_unit_declaration() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST secondary_unit_declaration_AST = null;
		
		try {      // for error handling
			identifier();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp452_AST = null;
			tmp452_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp452_AST);
			match(EQ);
			physical_literal();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp453_AST = null;
			tmp453_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp453_AST);
			match(SEMI);
			secondary_unit_declaration_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_91);
			} else {
			  throw ex;
			}
		}
		returnAST = secondary_unit_declaration_AST;
	}
	
	public final void paren_op() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST paren_op_AST = null;
		
		try {      // for error handling
			AST tmp454_AST = null;
			tmp454_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp454_AST);
			match(LPAREN);
			expression();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp455_AST = null;
			tmp455_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp455_AST);
			match(RPAREN);
			if ( inputState.guessing==0 ) {
				paren_op_AST = (AST)currentAST.root;
				paren_op_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(PARENOP,"ParenOp")).add(paren_op_AST));
				currentAST.root = paren_op_AST;
				currentAST.child = paren_op_AST!=null &&paren_op_AST.getFirstChild()!=null ?
					paren_op_AST.getFirstChild() : paren_op_AST;
				currentAST.advanceChildToEnd();
			}
			paren_op_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_138);
			} else {
			  throw ex;
			}
		}
		returnAST = paren_op_AST;
	}
	
	public final void procedural_declarative_item() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST procedural_declarative_item_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case TYPE:
			{
				type_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				procedural_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case SUBTYPE:
			{
				subtype_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				procedural_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case CONSTANT:
			{
				constant_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				procedural_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case VARIABLE:
			case SHARED:
			{
				variable_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				procedural_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case ALIAS:
			{
				alias_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				procedural_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case USE:
			case CONTEXT:
			{
				use_clause();
				astFactory.addASTChild(currentAST, returnAST);
				procedural_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			default:
				boolean synPredMatched520 = false;
				if (((_tokenSet_11.member(LA(1))) && (_tokenSet_12.member(LA(2))) && (_tokenSet_13.member(LA(3))))) {
					int _m520 = mark();
					synPredMatched520 = true;
					inputState.guessing++;
					try {
						{
						subprogram_declaration();
						}
					}
					catch (RecognitionException pe) {
						synPredMatched520 = false;
					}
					rewind(_m520);
inputState.guessing--;
				}
				if ( synPredMatched520 ) {
					subprogram_declaration();
					astFactory.addASTChild(currentAST, returnAST);
					procedural_declarative_item_AST = (AST)currentAST.root;
				}
				else if ((_tokenSet_11.member(LA(1))) && (_tokenSet_12.member(LA(2))) && (_tokenSet_14.member(LA(3)))) {
					subprogram_body();
					astFactory.addASTChild(currentAST, returnAST);
					procedural_declarative_item_AST = (AST)currentAST.root;
				}
				else if ((LA(1)==ATTRIBUTE) && ((LA(2) >= BASIC_IDENTIFIER && LA(2) <= REFERENCE)) && (LA(3)==COLON)) {
					attribute_declaration();
					astFactory.addASTChild(currentAST, returnAST);
					procedural_declarative_item_AST = (AST)currentAST.root;
				}
				else if ((LA(1)==ATTRIBUTE) && (_tokenSet_15.member(LA(2))) && (LA(3)==OF)) {
					attribute_specification();
					astFactory.addASTChild(currentAST, returnAST);
					procedural_declarative_item_AST = (AST)currentAST.root;
				}
				else if ((LA(1)==GROUP) && ((LA(2) >= BASIC_IDENTIFIER && LA(2) <= REFERENCE)) && (LA(3)==IS)) {
					group_template_declaration();
					astFactory.addASTChild(currentAST, returnAST);
					procedural_declarative_item_AST = (AST)currentAST.root;
				}
				else if ((LA(1)==GROUP) && ((LA(2) >= BASIC_IDENTIFIER && LA(2) <= REFERENCE)) && (LA(3)==COLON)) {
					group_declaration();
					astFactory.addASTChild(currentAST, returnAST);
					procedural_declarative_item_AST = (AST)currentAST.root;
				}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_180);
			} else {
			  throw ex;
			}
		}
		returnAST = procedural_declarative_item_AST;
	}
	
	public final void procedural_declarative_part() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST procedural_declarative_part_AST = null;
		
		try {      // for error handling
			{
			_loop523:
			do {
				if ((_tokenSet_181.member(LA(1)))) {
					procedural_declarative_item();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop523;
				}
				
			} while (true);
			}
			procedural_declarative_part_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_138);
			} else {
			  throw ex;
			}
		}
		returnAST = procedural_declarative_part_AST;
	}
	
	public final void procedural_statement_part() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST procedural_statement_part_AST = null;
		
		try {      // for error handling
			{
			_loop526:
			do {
				if ((_tokenSet_117.member(LA(1)))) {
					sequential_statement();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop526;
				}
				
			} while (true);
			}
			procedural_statement_part_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_138);
			} else {
			  throw ex;
			}
		}
		returnAST = procedural_statement_part_AST;
	}
	
	public final void sequential_statement() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST sequential_statement_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case ASSERT:
			{
				assertion_statement();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case REPORT:
			{
				report_statement();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case NEXT:
			{
				next_statement();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case EXIT:
			{
				exit_statement();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case RETURN:
			{
				return_statement();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case NULLTOK:
			{
				AST tmp456_AST = null;
				tmp456_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp456_AST);
				match(NULLTOK);
				AST tmp457_AST = null;
				tmp457_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp457_AST);
				match(SEMI);
				break;
			}
			case BREAK:
			{
				break_statement();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			default:
				boolean synPredMatched602 = false;
				if (((_tokenSet_182.member(LA(1))) && (_tokenSet_183.member(LA(2))) && (_tokenSet_184.member(LA(3))))) {
					int _m602 = mark();
					synPredMatched602 = true;
					inputState.guessing++;
					try {
						{
						match(WAIT);
						}
					}
					catch (RecognitionException pe) {
						synPredMatched602 = false;
					}
					rewind(_m602);
inputState.guessing--;
				}
				if ( synPredMatched602 ) {
					label_colon_wrap();
					astFactory.addASTChild(currentAST, returnAST);
					wait_statement();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					boolean synPredMatched608 = false;
					if (((_tokenSet_39.member(LA(1))) && (_tokenSet_185.member(LA(2))) && (_tokenSet_186.member(LA(3))))) {
						int _m608 = mark();
						synPredMatched608 = true;
						inputState.guessing++;
						try {
							{
							target();
							match(LE);
							}
						}
						catch (RecognitionException pe) {
							synPredMatched608 = false;
						}
						rewind(_m608);
inputState.guessing--;
					}
					if ( synPredMatched608 ) {
						signal_assignment_statement();
						astFactory.addASTChild(currentAST, returnAST);
					}
					else {
						boolean synPredMatched610 = false;
						if (((_tokenSet_39.member(LA(1))) && (_tokenSet_187.member(LA(2))) && (_tokenSet_35.member(LA(3))))) {
							int _m610 = mark();
							synPredMatched610 = true;
							inputState.guessing++;
							try {
								{
								target();
								match(VARASGN);
								}
							}
							catch (RecognitionException pe) {
								synPredMatched610 = false;
							}
							rewind(_m610);
inputState.guessing--;
						}
						if ( synPredMatched610 ) {
							variable_assignment_statement();
							astFactory.addASTChild(currentAST, returnAST);
						}
						else {
							boolean synPredMatched612 = false;
							if (((_tokenSet_188.member(LA(1))) && (_tokenSet_189.member(LA(2))) && (_tokenSet_190.member(LA(3))))) {
								int _m612 = mark();
								synPredMatched612 = true;
								inputState.guessing++;
								try {
									{
									label_colon_wrap();
									match(IF);
									}
								}
								catch (RecognitionException pe) {
									synPredMatched612 = false;
								}
								rewind(_m612);
inputState.guessing--;
							}
							if ( synPredMatched612 ) {
								if_statement();
								astFactory.addASTChild(currentAST, returnAST);
							}
							else {
								boolean synPredMatched614 = false;
								if (((_tokenSet_191.member(LA(1))) && (_tokenSet_189.member(LA(2))) && (_tokenSet_192.member(LA(3))))) {
									int _m614 = mark();
									synPredMatched614 = true;
									inputState.guessing++;
									try {
										{
										label_colon_wrap();
										match(CASE);
										}
									}
									catch (RecognitionException pe) {
										synPredMatched614 = false;
									}
									rewind(_m614);
inputState.guessing--;
								}
								if ( synPredMatched614 ) {
									case_statement();
									astFactory.addASTChild(currentAST, returnAST);
								}
								else {
									boolean synPredMatched617 = false;
									if (((_tokenSet_193.member(LA(1))) && (_tokenSet_194.member(LA(2))) && (_tokenSet_195.member(LA(3))))) {
										int _m617 = mark();
										synPredMatched617 = true;
										inputState.guessing++;
										try {
											{
											label_colon_wrap();
											{
											switch ( LA(1)) {
											case FOR:
											case WHILE:
											{
												iteration_scheme();
												break;
											}
											case LOOP:
											{
												break;
											}
											default:
											{
												throw new NoViableAltException(LT(1), getFilename());
											}
											}
											}
											match(LOOP);
											}
										}
										catch (RecognitionException pe) {
											synPredMatched617 = false;
										}
										rewind(_m617);
inputState.guessing--;
									}
									if ( synPredMatched617 ) {
										loop_statement();
										astFactory.addASTChild(currentAST, returnAST);
									}
									else if (((LA(1) >= BASIC_IDENTIFIER && LA(1) <= REFERENCE)) && (_tokenSet_196.member(LA(2))) && (_tokenSet_197.member(LA(3)))) {
										procedure_call_statement();
										astFactory.addASTChild(currentAST, returnAST);
									}
								else {
									throw new NoViableAltException(LT(1), getFilename());
								}
								}}}}}}
								}
								if ( inputState.guessing==0 ) {
									sequential_statement_AST = (AST)currentAST.root;
									sequential_statement_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(SEQUENTIALSTATEMENT,"SequentialStatement")).add(sequential_statement_AST));
									currentAST.root = sequential_statement_AST;
									currentAST.child = sequential_statement_AST!=null &&sequential_statement_AST.getFirstChild()!=null ?
										sequential_statement_AST.getFirstChild() : sequential_statement_AST;
									currentAST.advanceChildToEnd();
								}
								sequential_statement_AST = (AST)currentAST.root;
							}
							catch (RecognitionException ex) {
								if (inputState.guessing==0) {
									reportError(ex);
									recover(ex,_tokenSet_86);
								} else {
								  throw ex;
								}
							}
							returnAST = sequential_statement_AST;
						}
						
	public final void procedure_call_statement() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST procedure_call_statement_AST = null;
		
		try {      // for error handling
			{
			if (((LA(1) >= BASIC_IDENTIFIER && LA(1) <= REFERENCE)) && (LA(2)==COLON)) {
				label_colon();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else if (((LA(1) >= BASIC_IDENTIFIER && LA(1) <= REFERENCE)) && (_tokenSet_175.member(LA(2)))) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			procedure_call();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp458_AST = null;
			tmp458_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp458_AST);
			match(SEMI);
			if ( inputState.guessing==0 ) {
				procedure_call_statement_AST = (AST)currentAST.root;
				procedure_call_statement_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(PROCEDURECALLSTATEMENT,"ProcedureCallStatement")).add(procedure_call_statement_AST));
				currentAST.root = procedure_call_statement_AST;
				currentAST.child = procedure_call_statement_AST!=null &&procedure_call_statement_AST.getFirstChild()!=null ?
					procedure_call_statement_AST.getFirstChild() : procedure_call_statement_AST;
				currentAST.advanceChildToEnd();
			}
			procedure_call_statement_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_86);
			} else {
			  throw ex;
			}
		}
		returnAST = procedure_call_statement_AST;
	}
	
	public final void process_declarative_item() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST process_declarative_item_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case TYPE:
			{
				type_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				process_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case SUBTYPE:
			{
				subtype_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				process_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case CONSTANT:
			{
				constant_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				process_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case VARIABLE:
			case SHARED:
			{
				variable_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				process_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case FILE:
			{
				file_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				process_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case ALIAS:
			{
				alias_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				process_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case USE:
			case CONTEXT:
			{
				use_clause();
				astFactory.addASTChild(currentAST, returnAST);
				process_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			default:
				boolean synPredMatched533 = false;
				if (((_tokenSet_11.member(LA(1))) && (_tokenSet_12.member(LA(2))) && (_tokenSet_13.member(LA(3))))) {
					int _m533 = mark();
					synPredMatched533 = true;
					inputState.guessing++;
					try {
						{
						subprogram_declaration();
						}
					}
					catch (RecognitionException pe) {
						synPredMatched533 = false;
					}
					rewind(_m533);
inputState.guessing--;
				}
				if ( synPredMatched533 ) {
					subprogram_declaration();
					astFactory.addASTChild(currentAST, returnAST);
					process_declarative_item_AST = (AST)currentAST.root;
				}
				else if ((_tokenSet_11.member(LA(1))) && (_tokenSet_12.member(LA(2))) && (_tokenSet_14.member(LA(3)))) {
					subprogram_body();
					astFactory.addASTChild(currentAST, returnAST);
					process_declarative_item_AST = (AST)currentAST.root;
				}
				else if ((LA(1)==ATTRIBUTE) && ((LA(2) >= BASIC_IDENTIFIER && LA(2) <= REFERENCE)) && (LA(3)==COLON)) {
					attribute_declaration();
					astFactory.addASTChild(currentAST, returnAST);
					process_declarative_item_AST = (AST)currentAST.root;
				}
				else if ((LA(1)==ATTRIBUTE) && (_tokenSet_15.member(LA(2))) && (LA(3)==OF)) {
					attribute_specification();
					astFactory.addASTChild(currentAST, returnAST);
					process_declarative_item_AST = (AST)currentAST.root;
				}
				else if ((LA(1)==GROUP) && ((LA(2) >= BASIC_IDENTIFIER && LA(2) <= REFERENCE)) && (LA(3)==IS)) {
					group_template_declaration();
					astFactory.addASTChild(currentAST, returnAST);
					process_declarative_item_AST = (AST)currentAST.root;
				}
				else if ((LA(1)==GROUP) && ((LA(2) >= BASIC_IDENTIFIER && LA(2) <= REFERENCE)) && (LA(3)==COLON)) {
					group_declaration();
					astFactory.addASTChild(currentAST, returnAST);
					process_declarative_item_AST = (AST)currentAST.root;
				}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_198);
			} else {
			  throw ex;
			}
		}
		returnAST = process_declarative_item_AST;
	}
	
	public final void process_declarative_part() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST process_declarative_part_AST = null;
		
		try {      // for error handling
			{
			_loop536:
			do {
				if ((_tokenSet_199.member(LA(1)))) {
					process_declarative_item();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop536;
				}
				
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				process_declarative_part_AST = (AST)currentAST.root;
				process_declarative_part_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(PROCESSDECLARATIVEPART,"ProcessDeclarativePart")).add(process_declarative_part_AST));
				currentAST.root = process_declarative_part_AST;
				currentAST.child = process_declarative_part_AST!=null &&process_declarative_part_AST.getFirstChild()!=null ?
					process_declarative_part_AST.getFirstChild() : process_declarative_part_AST;
				currentAST.advanceChildToEnd();
			}
			process_declarative_part_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_57);
			} else {
			  throw ex;
			}
		}
		returnAST = process_declarative_part_AST;
	}
	
	public final void process_head() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST process_head_AST = null;
		
		try {      // for error handling
			label_colon_wrap();
			astFactory.addASTChild(currentAST, returnAST);
			postponedQ();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp459_AST = null;
			tmp459_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp459_AST);
			match(PROCESS);
			{
			switch ( LA(1)) {
			case LPAREN:
			{
				AST tmp460_AST = null;
				tmp460_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp460_AST);
				match(LPAREN);
				{
				switch ( LA(1)) {
				case BASIC_IDENTIFIER:
				case EXTENDED_IDENTIFIER:
				case REFERENCE:
				{
					sensitivity_list();
					astFactory.addASTChild(currentAST, returnAST);
					break;
				}
				case ALL:
				{
					AST tmp461_AST = null;
					tmp461_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp461_AST);
					match(ALL);
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				AST tmp462_AST = null;
				tmp462_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp462_AST);
				match(RPAREN);
				break;
			}
			case ALIAS:
			case IS:
			case BEGIN:
			case ATTRIBUTE:
			case USE:
			case CONSTANT:
			case PROCEDURE:
			case FUNCTION:
			case TYPE:
			case SUBTYPE:
			case VARIABLE:
			case GROUP:
			case FILE:
			case PURE:
			case IMPURE:
			case CONTEXT:
			case SHARED:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			switch ( LA(1)) {
			case IS:
			{
				AST tmp463_AST = null;
				tmp463_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp463_AST);
				match(IS);
				break;
			}
			case ALIAS:
			case BEGIN:
			case ATTRIBUTE:
			case USE:
			case CONSTANT:
			case PROCEDURE:
			case FUNCTION:
			case TYPE:
			case SUBTYPE:
			case VARIABLE:
			case GROUP:
			case FILE:
			case PURE:
			case IMPURE:
			case CONTEXT:
			case SHARED:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				process_head_AST = (AST)currentAST.root;
				process_head_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(PROCESSHEAD,"ProcessHead")).add(process_head_AST));
				currentAST.root = process_head_AST;
				currentAST.child = process_head_AST!=null &&process_head_AST.getFirstChild()!=null ?
					process_head_AST.getFirstChild() : process_head_AST;
				currentAST.advanceChildToEnd();
			}
			process_head_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_198);
			} else {
			  throw ex;
			}
		}
		returnAST = process_head_AST;
	}
	
	public final void process_statement_part() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST process_statement_part_AST = null;
		
		try {      // for error handling
			{
			_loop546:
			do {
				if ((_tokenSet_117.member(LA(1)))) {
					sequential_statement();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop546;
				}
				
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				process_statement_part_AST = (AST)currentAST.root;
				process_statement_part_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(PROCESSSTATEMENTPART,"ProcessStatementPart")).add(process_statement_part_AST));
				currentAST.root = process_statement_part_AST;
				currentAST.child = process_statement_part_AST!=null &&process_statement_part_AST.getFirstChild()!=null ?
					process_statement_part_AST.getFirstChild() : process_statement_part_AST;
				currentAST.advanceChildToEnd();
			}
			process_statement_part_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_3);
			} else {
			  throw ex;
			}
		}
		returnAST = process_statement_part_AST;
	}
	
	public final void sensitivity_list() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST sensitivity_list_AST = null;
		
		try {      // for error handling
			name();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop593:
			do {
				if ((LA(1)==COMMA)) {
					AST tmp464_AST = null;
					tmp464_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp464_AST);
					match(COMMA);
					name();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop593;
				}
				
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				sensitivity_list_AST = (AST)currentAST.root;
				sensitivity_list_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(SENSITIVITYLIST,"SensitivityList")).add(sensitivity_list_AST));
				currentAST.root = sensitivity_list_AST;
				currentAST.child = sensitivity_list_AST!=null &&sensitivity_list_AST.getFirstChild()!=null ?
					sensitivity_list_AST.getFirstChild() : sensitivity_list_AST;
				currentAST.advanceChildToEnd();
			}
			sensitivity_list_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_200);
			} else {
			  throw ex;
			}
		}
		returnAST = sensitivity_list_AST;
	}
	
	public final void source_quantity_declaration() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST source_quantity_declaration_AST = null;
		
		try {      // for error handling
			AST tmp465_AST = null;
			tmp465_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp465_AST);
			match(QUANTITY);
			identifier_list();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp466_AST = null;
			tmp466_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp466_AST);
			match(COLON);
			subtype_indication();
			astFactory.addASTChild(currentAST, returnAST);
			source_aspect();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp467_AST = null;
			tmp467_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp467_AST);
			match(SEMI);
			source_quantity_declaration_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_100);
			} else {
			  throw ex;
			}
		}
		returnAST = source_quantity_declaration_AST;
	}
	
	public final void quantity_list() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST quantity_list_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			{
				name();
				astFactory.addASTChild(currentAST, returnAST);
				{
				_loop558:
				do {
					if ((LA(1)==COMMA)) {
						AST tmp468_AST = null;
						tmp468_AST = astFactory.create(LT(1));
						astFactory.addASTChild(currentAST, tmp468_AST);
						match(COMMA);
						name();
						astFactory.addASTChild(currentAST, returnAST);
					}
					else {
						break _loop558;
					}
					
				} while (true);
				}
				quantity_list_AST = (AST)currentAST.root;
				break;
			}
			case OTHERS:
			{
				AST tmp469_AST = null;
				tmp469_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp469_AST);
				match(OTHERS);
				quantity_list_AST = (AST)currentAST.root;
				break;
			}
			case ALL:
			{
				AST tmp470_AST = null;
				tmp470_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp470_AST);
				match(ALL);
				quantity_list_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_123);
			} else {
			  throw ex;
			}
		}
		returnAST = quantity_list_AST;
	}
	
	public final void quantity_specification() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST quantity_specification_AST = null;
		
		try {      // for error handling
			quantity_list();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp471_AST = null;
			tmp471_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp471_AST);
			match(COLON);
			name();
			astFactory.addASTChild(currentAST, returnAST);
			quantity_specification_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_138);
			} else {
			  throw ex;
			}
		}
		returnAST = quantity_specification_AST;
	}
	
	public final void record_element() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST record_element_AST = null;
		
		try {      // for error handling
			{
			int _cnt573=0;
			_loop573:
			do {
				if (((LA(1) >= BASIC_IDENTIFIER && LA(1) <= REFERENCE))) {
					element_declaration();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					if ( _cnt573>=1 ) { break _loop573; } else {throw new NoViableAltException(LT(1), getFilename());}
				}
				
				_cnt573++;
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				record_element_AST = (AST)currentAST.root;
				record_element_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(RECORDELEMENT,"RecordElement")).add(record_element_AST));
				currentAST.root = record_element_AST;
				currentAST.child = record_element_AST!=null &&record_element_AST.getFirstChild()!=null ?
					record_element_AST.getFirstChild() : record_element_AST;
				currentAST.advanceChildToEnd();
			}
			record_element_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_3);
			} else {
			  throw ex;
			}
		}
		returnAST = record_element_AST;
	}
	
	public final void report_statement() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST report_statement_AST = null;
		
		try {      // for error handling
			AST tmp472_AST = null;
			tmp472_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp472_AST);
			match(REPORT);
			expression();
			astFactory.addASTChild(currentAST, returnAST);
			{
			switch ( LA(1)) {
			case SEVERITY:
			{
				AST tmp473_AST = null;
				tmp473_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp473_AST);
				match(SEVERITY);
				expression();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case SEMI:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			AST tmp474_AST = null;
			tmp474_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp474_AST);
			match(SEMI);
			if ( inputState.guessing==0 ) {
				report_statement_AST = (AST)currentAST.root;
				report_statement_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(REPORTSTATEMENT,"ReportStatement")).add(report_statement_AST));
				currentAST.root = report_statement_AST;
				currentAST.child = report_statement_AST!=null &&report_statement_AST.getFirstChild()!=null ?
					report_statement_AST.getFirstChild() : report_statement_AST;
				currentAST.advanceChildToEnd();
			}
			report_statement_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_86);
			} else {
			  throw ex;
			}
		}
		returnAST = report_statement_AST;
	}
	
	public final void return_statement() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST return_statement_AST = null;
		
		try {      // for error handling
			AST tmp475_AST = null;
			tmp475_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp475_AST);
			match(RETURN);
			{
			switch ( LA(1)) {
			case DECIMAL_LITERAL:
			case BASED_LITERAL:
			case LPAREN:
			case CHARACTER_LITERAL:
			case STRING_LITERAL:
			case NEW:
			case PLUS:
			case MINUS:
			case ABS:
			case NOT:
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			case NULLTOK:
			case BIT_STRING_LITERAL:
			{
				expression();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case SEMI:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			AST tmp476_AST = null;
			tmp476_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp476_AST);
			match(SEMI);
			if ( inputState.guessing==0 ) {
				return_statement_AST = (AST)currentAST.root;
				return_statement_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(RETURNSTATEMENT,"ReturnStatement")).add(return_statement_AST));
				currentAST.root = return_statement_AST;
				currentAST.child = return_statement_AST!=null &&return_statement_AST.getFirstChild()!=null ?
					return_statement_AST.getFirstChild() : return_statement_AST;
				currentAST.advanceChildToEnd();
			}
			return_statement_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_86);
			} else {
			  throw ex;
			}
		}
		returnAST = return_statement_AST;
	}
	
	public final void scalar_type_definition() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST scalar_type_definition_AST = null;
		
		try {      // for error handling
			boolean synPredMatched581 = false;
			if (((LA(1)==RANGETOK) && (_tokenSet_19.member(LA(2))) && (_tokenSet_201.member(LA(3))))) {
				int _m581 = mark();
				synPredMatched581 = true;
				inputState.guessing++;
				try {
					{
					range_constraint();
					match(UNITS);
					}
				}
				catch (RecognitionException pe) {
					synPredMatched581 = false;
				}
				rewind(_m581);
inputState.guessing--;
			}
			if ( synPredMatched581 ) {
				physical_type_definition();
				astFactory.addASTChild(currentAST, returnAST);
				scalar_type_definition_AST = (AST)currentAST.root;
			}
			else if ((LA(1)==LPAREN)) {
				enumeration_type_definition();
				astFactory.addASTChild(currentAST, returnAST);
				scalar_type_definition_AST = (AST)currentAST.root;
			}
			else if ((LA(1)==RANGETOK) && (_tokenSet_19.member(LA(2))) && (_tokenSet_202.member(LA(3)))) {
				range_constraint();
				astFactory.addASTChild(currentAST, returnAST);
				scalar_type_definition_AST = (AST)currentAST.root;
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_1);
			} else {
			  throw ex;
			}
		}
		returnAST = scalar_type_definition_AST;
	}
	
	public final void selected_waveforms() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST selected_waveforms_AST = null;
		
		try {      // for error handling
			selected_waveform_item();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop588:
			do {
				if ((LA(1)==COMMA)) {
					AST tmp477_AST = null;
					tmp477_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp477_AST);
					match(COMMA);
					selected_waveform_item();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop588;
				}
				
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				selected_waveforms_AST = (AST)currentAST.root;
				selected_waveforms_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(SELECTEDWAVEFORMS,"selected_waveforms")).add(selected_waveforms_AST));
				currentAST.root = selected_waveforms_AST;
				currentAST.child = selected_waveforms_AST!=null &&selected_waveforms_AST.getFirstChild()!=null ?
					selected_waveforms_AST.getFirstChild() : selected_waveforms_AST;
				currentAST.advanceChildToEnd();
			}
			selected_waveforms_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_1);
			} else {
			  throw ex;
			}
		}
		returnAST = selected_waveforms_AST;
	}
	
	public final void selected_waveform_item() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST selected_waveform_item_AST = null;
		
		try {      // for error handling
			waveform();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp478_AST = null;
			tmp478_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp478_AST);
			match(WHEN);
			choices();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				selected_waveform_item_AST = (AST)currentAST.root;
				selected_waveform_item_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(SELECTEDWAVEFORMITEM,"selected_waveform_item")).add(selected_waveform_item_AST));
				currentAST.root = selected_waveform_item_AST;
				currentAST.child = selected_waveform_item_AST!=null &&selected_waveform_item_AST.getFirstChild()!=null ?
					selected_waveform_item_AST.getFirstChild() : selected_waveform_item_AST;
				currentAST.advanceChildToEnd();
			}
			selected_waveform_item_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_174);
			} else {
			  throw ex;
			}
		}
		returnAST = selected_waveform_item_AST;
	}
	
	public final void wait_statement() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST wait_statement_AST = null;
		
		try {      // for error handling
			AST tmp479_AST = null;
			tmp479_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp479_AST);
			match(WAIT);
			{
			switch ( LA(1)) {
			case ON:
			{
				sensitivity_clause();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case SEMI:
			case FOR:
			case UNTIL:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			switch ( LA(1)) {
			case UNTIL:
			{
				condition_clause();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case SEMI:
			case FOR:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			switch ( LA(1)) {
			case FOR:
			{
				timeout_clause();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case SEMI:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			AST tmp480_AST = null;
			tmp480_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp480_AST);
			match(SEMI);
			if ( inputState.guessing==0 ) {
				wait_statement_AST = (AST)currentAST.root;
				wait_statement_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(WAITSTATEMENT,"WaitStatement")).add(wait_statement_AST));
				currentAST.root = wait_statement_AST;
				currentAST.child = wait_statement_AST!=null &&wait_statement_AST.getFirstChild()!=null ?
					wait_statement_AST.getFirstChild() : wait_statement_AST;
				currentAST.advanceChildToEnd();
			}
			wait_statement_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_86);
			} else {
			  throw ex;
			}
		}
		returnAST = wait_statement_AST;
	}
	
	public final void signal_assignment_statement() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST signal_assignment_statement_AST = null;
		
		try {      // for error handling
			target();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp481_AST = null;
			tmp481_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp481_AST);
			match(LE);
			{
			switch ( LA(1)) {
			case TRANSPORT:
			case REJECT:
			case INERTIAL:
			{
				delay_mechanism();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case DECIMAL_LITERAL:
			case BASED_LITERAL:
			case LPAREN:
			case CHARACTER_LITERAL:
			case STRING_LITERAL:
			case NEW:
			case PLUS:
			case MINUS:
			case ABS:
			case NOT:
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			case NULLTOK:
			case BIT_STRING_LITERAL:
			case UNAFFECTED:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			waveform();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp482_AST = null;
			tmp482_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp482_AST);
			match(SEMI);
			if ( inputState.guessing==0 ) {
				signal_assignment_statement_AST = (AST)currentAST.root;
				signal_assignment_statement_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(SIGNALASSIGNMENTSTATEMENT,"SignalAssignmentStatement")).add(signal_assignment_statement_AST));
				currentAST.root = signal_assignment_statement_AST;
				currentAST.child = signal_assignment_statement_AST!=null &&signal_assignment_statement_AST.getFirstChild()!=null ?
					signal_assignment_statement_AST.getFirstChild() : signal_assignment_statement_AST;
				currentAST.advanceChildToEnd();
			}
			signal_assignment_statement_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_86);
			} else {
			  throw ex;
			}
		}
		returnAST = signal_assignment_statement_AST;
	}
	
	public final void variable_assignment_statement() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST variable_assignment_statement_AST = null;
		
		try {      // for error handling
			target();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp483_AST = null;
			tmp483_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp483_AST);
			match(VARASGN);
			expression();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp484_AST = null;
			tmp484_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp484_AST);
			match(SEMI);
			if ( inputState.guessing==0 ) {
				variable_assignment_statement_AST = (AST)currentAST.root;
				variable_assignment_statement_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(VARIABLEASSIGNMENTSTATEMENT,"VariableAssignmentStatement")).add(variable_assignment_statement_AST));
				currentAST.root = variable_assignment_statement_AST;
				currentAST.child = variable_assignment_statement_AST!=null &&variable_assignment_statement_AST.getFirstChild()!=null ?
					variable_assignment_statement_AST.getFirstChild() : variable_assignment_statement_AST;
				currentAST.advanceChildToEnd();
			}
			variable_assignment_statement_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_86);
			} else {
			  throw ex;
			}
		}
		returnAST = variable_assignment_statement_AST;
	}
	
	public final void signal_kind_or_var_assign() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST signal_kind_or_var_assign_AST = null;
		
		try {      // for error handling
			signal_kind();
			astFactory.addASTChild(currentAST, returnAST);
			var_assign();
			astFactory.addASTChild(currentAST, returnAST);
			signal_kind_or_var_assign_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_1);
			} else {
			  throw ex;
			}
		}
		returnAST = signal_kind_or_var_assign_AST;
	}
	
	public final void signal_kind() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST signal_kind_AST = null;
		
		try {      // for error handling
			{
			{
			switch ( LA(1)) {
			case REGISTER:
			{
				AST tmp485_AST = null;
				tmp485_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp485_AST);
				match(REGISTER);
				break;
			}
			case BUS:
			{
				AST tmp486_AST = null;
				tmp486_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp486_AST);
				match(BUS);
				break;
			}
			case VARASGN:
			case SEMI:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			}
			if ( inputState.guessing==0 ) {
				signal_kind_AST = (AST)currentAST.root;
				signal_kind_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(SIGNALKIND,"SignalKind")).add(signal_kind_AST));
				currentAST.root = signal_kind_AST;
				currentAST.child = signal_kind_AST!=null &&signal_kind_AST.getFirstChild()!=null ?
					signal_kind_AST.getFirstChild() : signal_kind_AST;
				currentAST.advanceChildToEnd();
			}
			signal_kind_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				signal_kind_exception(ex);
			} else {
				throw ex;
			}
		}
		returnAST = signal_kind_AST;
	}
	
	public final void source_aspect() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST source_aspect_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case SPECTRUM:
			{
				AST tmp487_AST = null;
				tmp487_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp487_AST);
				match(SPECTRUM);
				simple_expression();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp488_AST = null;
				tmp488_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp488_AST);
				match(COMMA);
				simple_expression();
				astFactory.addASTChild(currentAST, returnAST);
				source_aspect_AST = (AST)currentAST.root;
				break;
			}
			case NOISE:
			{
				AST tmp489_AST = null;
				tmp489_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp489_AST);
				match(NOISE);
				simple_expression();
				astFactory.addASTChild(currentAST, returnAST);
				source_aspect_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_1);
			} else {
			  throw ex;
			}
		}
		returnAST = source_aspect_AST;
	}
	
	public final void subprogram_specification() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST subprogram_specification_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case PROCEDURE:
			{
				procedure_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				subprogram_specification_AST = (AST)currentAST.root;
				break;
			}
			case FUNCTION:
			case PURE:
			case IMPURE:
			{
				function_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				subprogram_specification_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_203);
			} else {
			  throw ex;
			}
		}
		returnAST = subprogram_specification_AST;
	}
	
	public final void subprogram_declarative_part() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST subprogram_declarative_part_AST = null;
		
		try {      // for error handling
			{
			_loop660:
			do {
				if ((_tokenSet_199.member(LA(1)))) {
					subprogram_declarative_item();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop660;
				}
				
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				subprogram_declarative_part_AST = (AST)currentAST.root;
				subprogram_declarative_part_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(SUBPROGRAMDECLARATIVEPART,"SubprogramDeclarativePart")).add(subprogram_declarative_part_AST));
				currentAST.root = subprogram_declarative_part_AST;
				currentAST.child = subprogram_declarative_part_AST!=null &&subprogram_declarative_part_AST.getFirstChild()!=null ?
					subprogram_declarative_part_AST.getFirstChild() : subprogram_declarative_part_AST;
				currentAST.advanceChildToEnd();
			}
			subprogram_declarative_part_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_57);
			} else {
			  throw ex;
			}
		}
		returnAST = subprogram_declarative_part_AST;
	}
	
	public final void subprogram_statement_part() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST subprogram_statement_part_AST = null;
		
		try {      // for error handling
			{
			_loop671:
			do {
				if ((_tokenSet_117.member(LA(1)))) {
					sequential_statement();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop671;
				}
				
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				subprogram_statement_part_AST = (AST)currentAST.root;
				subprogram_statement_part_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(SUBPROGRAMSTATEMENTPART,"SubprogramStatementPart")).add(subprogram_statement_part_AST));
				currentAST.root = subprogram_statement_part_AST;
				currentAST.child = subprogram_statement_part_AST!=null &&subprogram_statement_part_AST.getFirstChild()!=null ?
					subprogram_statement_part_AST.getFirstChild() : subprogram_statement_part_AST;
				currentAST.advanceChildToEnd();
			}
			subprogram_statement_part_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_3);
			} else {
			  throw ex;
			}
		}
		returnAST = subprogram_statement_part_AST;
	}
	
	public final void subprogram_kind() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST subprogram_kind_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case PROCEDURE:
			{
				AST tmp490_AST = null;
				tmp490_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp490_AST);
				match(PROCEDURE);
				subprogram_kind_AST = (AST)currentAST.root;
				break;
			}
			case FUNCTION:
			{
				AST tmp491_AST = null;
				tmp491_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp491_AST);
				match(FUNCTION);
				subprogram_kind_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_204);
			} else {
			  throw ex;
			}
		}
		returnAST = subprogram_kind_AST;
	}
	
	public final void subprogram_declarative_item() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST subprogram_declarative_item_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case TYPE:
			{
				type_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				subprogram_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case SUBTYPE:
			{
				subtype_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				subprogram_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case CONSTANT:
			{
				constant_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				subprogram_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case VARIABLE:
			case SHARED:
			{
				variable_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				subprogram_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case FILE:
			{
				file_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				subprogram_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case ALIAS:
			{
				alias_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				subprogram_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			case USE:
			case CONTEXT:
			{
				use_clause();
				astFactory.addASTChild(currentAST, returnAST);
				subprogram_declarative_item_AST = (AST)currentAST.root;
				break;
			}
			default:
				boolean synPredMatched657 = false;
				if (((_tokenSet_11.member(LA(1))) && (_tokenSet_12.member(LA(2))) && (_tokenSet_13.member(LA(3))))) {
					int _m657 = mark();
					synPredMatched657 = true;
					inputState.guessing++;
					try {
						{
						subprogram_declaration();
						}
					}
					catch (RecognitionException pe) {
						synPredMatched657 = false;
					}
					rewind(_m657);
inputState.guessing--;
				}
				if ( synPredMatched657 ) {
					subprogram_declaration();
					astFactory.addASTChild(currentAST, returnAST);
					subprogram_declarative_item_AST = (AST)currentAST.root;
				}
				else if ((_tokenSet_11.member(LA(1))) && (_tokenSet_12.member(LA(2))) && (_tokenSet_14.member(LA(3)))) {
					subprogram_body();
					astFactory.addASTChild(currentAST, returnAST);
					subprogram_declarative_item_AST = (AST)currentAST.root;
				}
				else if ((LA(1)==ATTRIBUTE) && ((LA(2) >= BASIC_IDENTIFIER && LA(2) <= REFERENCE)) && (LA(3)==COLON)) {
					attribute_declaration();
					astFactory.addASTChild(currentAST, returnAST);
					subprogram_declarative_item_AST = (AST)currentAST.root;
				}
				else if ((LA(1)==ATTRIBUTE) && (_tokenSet_15.member(LA(2))) && (LA(3)==OF)) {
					attribute_specification();
					astFactory.addASTChild(currentAST, returnAST);
					subprogram_declarative_item_AST = (AST)currentAST.root;
				}
				else if ((LA(1)==GROUP) && ((LA(2) >= BASIC_IDENTIFIER && LA(2) <= REFERENCE)) && (LA(3)==IS)) {
					group_template_declaration();
					astFactory.addASTChild(currentAST, returnAST);
					subprogram_declarative_item_AST = (AST)currentAST.root;
				}
				else if ((LA(1)==GROUP) && ((LA(2) >= BASIC_IDENTIFIER && LA(2) <= REFERENCE)) && (LA(3)==COLON)) {
					group_declaration();
					astFactory.addASTChild(currentAST, returnAST);
					subprogram_declarative_item_AST = (AST)currentAST.root;
				}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_198);
			} else {
			  throw ex;
			}
		}
		returnAST = subprogram_declarative_item_AST;
	}
	
	public final void procedure_declaration() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST procedure_declaration_AST = null;
		
		try {      // for error handling
			AST tmp492_AST = null;
			tmp492_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp492_AST);
			match(PROCEDURE);
			designator();
			astFactory.addASTChild(currentAST, returnAST);
			function_parameter_list();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				procedure_declaration_AST = (AST)currentAST.root;
				procedure_declaration_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(PROCEDUREDECLARATION,"ProcedureDeclaration")).add(procedure_declaration_AST));
				currentAST.root = procedure_declaration_AST;
				currentAST.child = procedure_declaration_AST!=null &&procedure_declaration_AST.getFirstChild()!=null ?
					procedure_declaration_AST.getFirstChild() : procedure_declaration_AST;
				currentAST.advanceChildToEnd();
			}
			procedure_declaration_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_203);
			} else {
			  throw ex;
			}
		}
		returnAST = procedure_declaration_AST;
	}
	
	public final void function_declaration() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST function_declaration_AST = null;
		
		try {      // for error handling
			function_pure();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp493_AST = null;
			tmp493_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp493_AST);
			match(FUNCTION);
			designator();
			astFactory.addASTChild(currentAST, returnAST);
			function_parameter_list();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp494_AST = null;
			tmp494_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp494_AST);
			match(RETURN);
			name();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				function_declaration_AST = (AST)currentAST.root;
				function_declaration_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(FUNCTIONDECLARATION,"FunctionDeclaration")).add(function_declaration_AST));
				currentAST.root = function_declaration_AST;
				currentAST.child = function_declaration_AST!=null &&function_declaration_AST.getFirstChild()!=null ?
					function_declaration_AST.getFirstChild() : function_declaration_AST;
				currentAST.advanceChildToEnd();
			}
			function_declaration_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_203);
			} else {
			  throw ex;
			}
		}
		returnAST = function_declaration_AST;
	}
	
	public final void function_parameter_list() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST function_parameter_list_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case LPAREN:
			{
				AST tmp495_AST = null;
				tmp495_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp495_AST);
				match(LPAREN);
				formal_parameter_list();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp496_AST = null;
				tmp496_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp496_AST);
				match(RPAREN);
				break;
			}
			case IS:
			case SEMI:
			case RETURN:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				function_parameter_list_AST = (AST)currentAST.root;
				function_parameter_list_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(FUNCTIONPARAMETERLIST,"function_parameter_list")).add(function_parameter_list_AST));
				currentAST.root = function_parameter_list_AST;
				currentAST.child = function_parameter_list_AST!=null &&function_parameter_list_AST.getFirstChild()!=null ?
					function_parameter_list_AST.getFirstChild() : function_parameter_list_AST;
				currentAST.advanceChildToEnd();
			}
			function_parameter_list_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_205);
			} else {
			  throw ex;
			}
		}
		returnAST = function_parameter_list_AST;
	}
	
	public final void function_pure() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST function_pure_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case PURE:
			{
				AST tmp497_AST = null;
				tmp497_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp497_AST);
				match(PURE);
				break;
			}
			case IMPURE:
			{
				AST tmp498_AST = null;
				tmp498_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp498_AST);
				match(IMPURE);
				break;
			}
			case FUNCTION:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				function_pure_AST = (AST)currentAST.root;
				function_pure_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(FUNCTIONPURE,"FunctionPure")).add(function_pure_AST));
				currentAST.root = function_pure_AST;
				currentAST.child = function_pure_AST!=null &&function_pure_AST.getFirstChild()!=null ?
					function_pure_AST.getFirstChild() : function_pure_AST;
				currentAST.advanceChildToEnd();
			}
			function_pure_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_206);
			} else {
			  throw ex;
			}
		}
		returnAST = function_pure_AST;
	}
	
	public final void subtype_first_name() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST subtype_first_name_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case BASIC_IDENTIFIER:
			case EXTENDED_IDENTIFIER:
			case REFERENCE:
			{
				name();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case LPAREN:
			{
				{
				AST tmp499_AST = null;
				tmp499_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp499_AST);
				match(LPAREN);
				name();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp500_AST = null;
				tmp500_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp500_AST);
				match(RPAREN);
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				subtype_first_name_AST = (AST)currentAST.root;
				subtype_first_name_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(SUBTYPEFIRSTNAME,"SubtypeFirstName")).add(subtype_first_name_AST));
				currentAST.root = subtype_first_name_AST;
				currentAST.child = subtype_first_name_AST!=null &&subtype_first_name_AST.getFirstChild()!=null ?
					subtype_first_name_AST.getFirstChild() : subtype_first_name_AST;
				currentAST.advanceChildToEnd();
			}
			subtype_first_name_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_207);
			} else {
			  throw ex;
			}
		}
		returnAST = subtype_first_name_AST;
	}
	
	public final void timeout_clause() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST timeout_clause_AST = null;
		
		try {      // for error handling
			AST tmp501_AST = null;
			tmp501_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp501_AST);
			match(FOR);
			expression();
			astFactory.addASTChild(currentAST, returnAST);
			timeout_clause_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_1);
			} else {
			  throw ex;
			}
		}
		returnAST = timeout_clause_AST;
	}
	
	public final void type_definition() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST type_definition_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case LPAREN:
			case RANGETOK:
			{
				scalar_type_definition();
				astFactory.addASTChild(currentAST, returnAST);
				type_definition_AST = (AST)currentAST.root;
				break;
			}
			case ARRAY:
			case RECORD:
			{
				composite_type_definition();
				astFactory.addASTChild(currentAST, returnAST);
				type_definition_AST = (AST)currentAST.root;
				break;
			}
			case ACCESS:
			{
				access_type_definition();
				astFactory.addASTChild(currentAST, returnAST);
				type_definition_AST = (AST)currentAST.root;
				break;
			}
			case FILE:
			{
				file_type_definition();
				astFactory.addASTChild(currentAST, returnAST);
				type_definition_AST = (AST)currentAST.root;
				break;
			}
			case PROTECTED:
			{
				protected_type_definition();
				astFactory.addASTChild(currentAST, returnAST);
				type_definition_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_1);
			} else {
			  throw ex;
			}
		}
		returnAST = type_definition_AST;
	}
	
	public final void waveform_element() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST waveform_element_AST = null;
		
		try {      // for error handling
			expression();
			astFactory.addASTChild(currentAST, returnAST);
			{
			switch ( LA(1)) {
			case AFTER:
			{
				AST tmp502_AST = null;
				tmp502_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp502_AST);
				match(AFTER);
				expression();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case COMMA:
			case SEMI:
			case WHEN:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			waveform_element_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_208);
			} else {
			  throw ex;
			}
		}
		returnAST = waveform_element_AST;
	}
	
	
	public static final String[] _tokenNames = {
		"<0>",
		"EOF",
		"<2>",
		"NULL_TREE_LOOKAHEAD",
		"ROOT",
		"DESIGNUNIT",
		"CONTEXTCLAUSE",
		"LIBRARYCLAUSE",
		"LIBRARYUNIT",
		"USECLAUSE",
		"PRIMARYUNIT",
		"SECONDARYUNIT",
		"ARCHITECTUREBODY",
		"ARCHITECTUREDECLARATIVEPART",
		"ARCHITECTURESTATEMENTPART",
		"ARCHITECTURESTATEMENT",
		"BLOCKDECLARATIVEITEM",
		"SUBPROGRAMDECLARATION",
		"PACKAGEBODY",
		"PACKAGEDECLARATION",
		"PACKAGEDECLARATIVEPART",
		"PACKAGEBODYDECLARATIVEPART",
		"ENTITYDECLARATION",
		"ENTITYHEADER",
		"ENTITYDECLARATIVEPART",
		"ENTDEC1",
		"ENTDEC2",
		"GENERICCLAUSE",
		"PORTCLAUSE",
		"PORTLIST",
		"PORTELEMENT",
		"CONSTANTPORT",
		"SIGNALPORT",
		"INTERFACEVARIABLEDECLARATION",
		"SUBTYPEINDICATION",
		"SUBTYPEFIRSTNAME",
		"MODE",
		"CONSTANTDECLARATION",
		"SIGNALDECLARATION",
		"VARIABLEDECLARATION",
		"VARASSIGN",
		"SIGNALKIND",
		"SUBTYPEDECLARATION",
		"TYPEDECLARATION",
		"ENUMERATIONTYPEDEFINITION",
		"FILETYPEDEFINITION",
		"ACCESSTYPEDEFINITION",
		"RECORDTYPEDEFINITION",
		"RECORDELEMENT",
		"ELEMENTDECLARATION",
		"UNCONSTRAINEDARRAYDEFINITION",
		"CONSTRAINEDARRAYDEFINITION",
		"INDEXSUBTYPEDEFINITION",
		"INDEXCONSTRAINT",
		"DISCRETERANGE",
		"RANGE",
		"IDENTIFIERLIST",
		"IDENTIFIER",
		"NAME",
		"NAMEDOT",
		"NAMEAPOSTROPHY",
		"NAMEATTRIBUTE",
		"NAMEEXPRESSION",
		"NAMEPARAMETERPART",
		"NAMERANGE",
		"LABELCOLON",
		"COMPONENTINSTANTIAIONSTATEMENT",
		"GENERICMAPASPECT",
		"PORTMAPASPECT",
		"ASSOCIATIONLIST",
		"ASSOCIATIONARROW",
		"ASSOCIATIONNOARROW",
		"EXPRESSION",
		"RELATION",
		"SHIFTEXPRESSION",
		"SIMPLEEXPRESSION",
		"TERM",
		"FACTOR",
		"PRIMARY",
		"PARENOP",
		"ELEMENTASSOCIATION",
		"AGGREGATE",
		"PROCESSSTATEMENT",
		"PROCESSHEAD",
		"PROCESSDECLARATIVEPART",
		"PROCESSSTATEMENTPART",
		"IFSTATEMENT",
		"IFFIRSTCOND",
		"IFCOND",
		"IFLASTCOND",
		"IFEND",
		"CASESTATEMENT",
		"CASESTATEMENTALTERNATIVE",
		"CASEEND",
		"CHOICES",
		"SEQUENCEOFSTATEMENTS",
		"CONCURRENTSIGNALASSIGNMENTSTATEMENT",
		"CONDITIONALSIGNALASSIGNMENT",
		"CONDITIONALWAVEFORMS",
		"CONDITIONALWAVEFORMSBI",
		"SELECTEDSIGNALASSIGNMENT",
		"SELECTEDWAVEFORMS",
		"SELECTEDWAVEFORMITEM",
		"BITSTRINGLIT",
		"STRINGLIT",
		"\"literal\"",
		"ADDINGTERM",
		"UNARYOP",
		"ENUMERATIONLITERAL",
		"CHARACTERLIT",
		"NUMERICLITERAL",
		"TARGET",
		"FUNCTIONDECLARATION",
		"PROCEDUREDECLARATION",
		"FUNCTIONPARAMETERLIST",
		"COMPONENTDECLARATION",
		"SEQUENTIALSTATEMENT",
		"SIGNALASSIGNMENTSTATEMENT",
		"VARIABLEASSIGNMENTSTATEMENT",
		"SENSITIVITYLIST",
		"SUBPROGRAMDBODY",
		"SUBPROGRAMDECLARATIVEPART",
		"SUBPROGRAMSTATEMENTPART",
		"WAITSTATEMENT",
		"ASSERTIONSTATEMENT",
		"REPORTSTATEMENT",
		"NEXTSTATEMENT",
		"EXITSTATEMENT",
		"RETURNSTATEMENT",
		"BREAKSTATEMENT",
		"PROCEDURECALLSTATEMENT",
		"LOOPSTATEMENT",
		"ITERATIONSCHEMEQ",
		"ITERATIONSCHEMEWHILE",
		"ITERATIONSCHEMEFOR",
		"SIGNALPORTDEC",
		"CONSTANTPORTDEC",
		"ALIASTYPE",
		"ALIASDECLARATION",
		"INSTUNITCOMPOPT",
		"INSTUNITCOMP",
		"INSTUNITENT",
		"INSTUNITCONF",
		"COMPONENTISDUMMY",
		"GENERICCLAUSEDUMMY",
		"PORTCLAUSEDUMMY",
		"CONCURRENTASSERTIONSTATEMENT",
		"LABELCOLONWRAP",
		"PORTASSIGNMENT",
		"CONSTANTPORTINQ",
		"SIGNALPORTMODEQ",
		"SIGNALPORTBUSQ",
		"ATTRIBUTEDECLARATION",
		"ATTRIBUTESPECIFICATION",
		"GENERATESTATEMENT",
		"GENERATEDECLARATIVEPART",
		"GENERATESTATEMENTPART",
		"GENERATEFOR",
		"GENERATEIF",
		"PARAMETERSPECIFICATION",
		"FILEDECLARATION",
		"FILEOPENINFORMATION",
		"OPTS",
		"POSTPONEDQ",
		"FUNCTIONPURE",
		"BLOCKSTATEMENT",
		"PHYSICALLITERAL",
		"EXPRESSIONORRANGE",
		"\"protected\"",
		"\"end\"",
		"\"body\"",
		"DECIMAL_LITERAL",
		"BASED_LITERAL",
		"\"access\"",
		"VARASGN",
		"\"across\"",
		"\"open\"",
		"LPAREN",
		"RPAREN",
		"COMMA",
		"COLON",
		"\"alias\"",
		"\"is\"",
		"SEMI",
		"CHARACTER_LITERAL",
		"STRING_LITERAL",
		"\"new\"",
		"APOSTROPHE",
		"\"architecture\"",
		"\"of\"",
		"\"begin\"",
		"\"postponed\"",
		"\"process\"",
		"\"assert\"",
		"\"generate\"",
		"\"break\"",
		"\"array\"",
		"\"report\"",
		"\"severity\"",
		"ARROW",
		"\"attribute\"",
		"\"range\"",
		"\"through\"",
		"\"tolerance\"",
		"\"use\"",
		"\"for\"",
		"\"block\"",
		"\"quantity\"",
		"\"when\"",
		"\"case\"",
		"\"others\"",
		"BAR",
		"\"component\"",
		"\"until\"",
		"LE",
		"\"else\"",
		"\"configuration\"",
		"\"constant\"",
		"\"transport\"",
		"\"reject\"",
		"\"inertial\"",
		"\"to\"",
		"\"downto\"",
		"\"disconnect\"",
		"\"after\"",
		"\"entity\"",
		"\"procedure\"",
		"\"function\"",
		"\"package\"",
		"\"type\"",
		"\"subtype\"",
		"\"signal\"",
		"\"variable\"",
		"\"label\"",
		"\"units\"",
		"\"group\"",
		"\"file\"",
		"\"nature\"",
		"\"subnature\"",
		"\"terminal\"",
		"BOX",
		"\"all\"",
		"\"exit\"",
		"DOUBLESTAR",
		"EQ",
		"NEQ",
		"LOWERTHAN",
		"GREATERTHAN",
		"GE",
		"\"and\"",
		"\"or\"",
		"\"nand\"",
		"\"nor\"",
		"\"xor\"",
		"\"xnor\"",
		"\"sll\"",
		"\"srl\"",
		"\"sla\"",
		"\"sra\"",
		"\"rol\"",
		"\"ror\"",
		"PLUS",
		"MINUS",
		"AMPERSAND",
		"MUL",
		"DIV",
		"\"mod\"",
		"\"rem\"",
		"\"abs\"",
		"\"not\"",
		"\"if\"",
		"\"generic\"",
		"\"map\"",
		"BASIC_IDENTIFIER",
		"EXTENDED_IDENTIFIER",
		"\"reference\"",
		"\"then\"",
		"\"elsif\"",
		"\"in\"",
		"\"bus\"",
		"\"out\"",
		"\"while\"",
		"\"library\"",
		"\"null\"",
		"BIT_STRING_LITERAL",
		"DBLQUOTE",
		"\"loop\"",
		"\"inout\"",
		"\"buffer\"",
		"\"linkage\"",
		"DOT",
		"\"next\"",
		"\"guarded\"",
		"\"port\"",
		"\"record\"",
		"\"return\"",
		"\"with\"",
		"\"select\"",
		"\"on\"",
		"\"wait\"",
		"\"register\"",
		"LBRACKET",
		"RBRACKET",
		"\"spectrum\"",
		"\"noise\"",
		"\"pure\"",
		"\"impure\"",
		"\"context\"",
		"\"shared\"",
		"\"unaffected\"",
		"\"procedural\"",
		"WS_",
		"NEWLINE",
		"COMMENT",
		"ASSIGN",
		"BACKSLASH",
		"OTHER_SPECIAL_CHARACTER",
		"ALL_CHARACTER",
		"INTEGER",
		"EXPONENT",
		"BASED_INTEGER"
	};
	
	protected void buildTokenTypeASTClassMap() {
		tokenTypeToASTClassMap=null;
	};
	
	private static final long[] mk_tokenSet_0() {
		long[] data = new long[10];
		data[2]=2199023255552L;
		data[3]=51539611904L;
		data[4]=3940649673949184L;
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	private static final long[] mk_tokenSet_1() {
		long[] data = { 0L, 0L, 36028797018963968L, 0L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
	private static final long[] mk_tokenSet_2() {
		long[] data = new long[10];
		data[3]=51539611904L;
		data[4]=3940649673949184L;
		return data;
	}
	public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
	private static final long[] mk_tokenSet_3() {
		long[] data = { 0L, 0L, 2199023255552L, 0L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
	private static final long[] mk_tokenSet_4() {
		long[] data = new long[10];
		data[2]=577023702256844800L;
		data[4]=35201551958016L;
		return data;
	}
	public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
	private static final long[] mk_tokenSet_5() {
		long[] data = new long[10];
		data[2]=505414708963049472L;
		data[3]=562949953424896L;
		data[4]=70918903574624L;
		return data;
	}
	public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
	private static final long[] mk_tokenSet_6() {
		long[] data = new long[10];
		data[0]=2L;
		data[2]=1143872523910250496L;
		data[3]=-2246845821796636L;
		data[4]=2804460512264191L;
		return data;
	}
	public static final BitSet _tokenSet_6 = new BitSet(mk_tokenSet_6());
	private static final long[] mk_tokenSet_7() {
		long[] data = new long[10];
		data[0]=2L;
		data[2]=639442977365688320L;
		data[3]=-2246845822058780L;
		data[4]=2769258557640703L;
		return data;
	}
	public static final BitSet _tokenSet_7 = new BitSet(mk_tokenSet_7());
	private static final long[] mk_tokenSet_8() {
		long[] data = new long[10];
		data[0]=2L;
		data[2]=4620695416705384448L;
		data[3]=274862909206784L;
		data[4]=8444249301319680L;
		return data;
	}
	public static final BitSet _tokenSet_8 = new BitSet(mk_tokenSet_8());
	private static final long[] mk_tokenSet_9() {
		long[] data = new long[10];
		data[0]=2L;
		data[2]=5773616921312231424L;
		data[3]=274871515918592L;
		data[4]=8444249368428544L;
		return data;
	}
	public static final BitSet _tokenSet_9 = new BitSet(mk_tokenSet_9());
	private static final long[] mk_tokenSet_10() {
		long[] data = new long[10];
		data[2]=9007199254740992L;
		data[3]=28020400197888L;
		data[4]=8444249301319680L;
		return data;
	}
	public static final BitSet _tokenSet_10 = new BitSet(mk_tokenSet_10());
	private static final long[] mk_tokenSet_11() {
		long[] data = new long[10];
		data[3]=51539607552L;
		data[4]=1688849860263936L;
		return data;
	}
	public static final BitSet _tokenSet_11 = new BitSet(mk_tokenSet_11());
	private static final long[] mk_tokenSet_12() {
		long[] data = new long[10];
		data[2]=144115188075855872L;
		data[3]=34359738368L;
		data[4]=917504L;
		return data;
	}
	public static final BitSet _tokenSet_12 = new BitSet(mk_tokenSet_12());
	private static final long[] mk_tokenSet_13() {
		long[] data = new long[10];
		data[2]=180706935048241152L;
		data[4]=549756731392L;
		return data;
	}
	public static final BitSet _tokenSet_13 = new BitSet(mk_tokenSet_13());
	private static final long[] mk_tokenSet_14() {
		long[] data = new long[10];
		data[2]=162692536538759168L;
		data[4]=549756731392L;
		return data;
	}
	public static final BitSet _tokenSet_14 = new BitSet(mk_tokenSet_14());
	private static final long[] mk_tokenSet_15() {
		long[] data = new long[10];
		data[2]=140737488355328L;
		data[3]=3584L;
		data[4]=917504L;
		return data;
	}
	public static final BitSet _tokenSet_15 = new BitSet(mk_tokenSet_15());
	private static final long[] mk_tokenSet_16() {
		long[] data = new long[10];
		data[2]=9009398277996544L;
		data[3]=28020400197888L;
		data[4]=8444249301319680L;
		return data;
	}
	public static final BitSet _tokenSet_16 = new BitSet(mk_tokenSet_16());
	private static final long[] mk_tokenSet_17() {
		long[] data = new long[10];
		data[0]=2L;
		data[2]=1161930902884843520L;
		data[3]=28029006909696L;
		data[4]=8444249368428544L;
		return data;
	}
	public static final BitSet _tokenSet_17 = new BitSet(mk_tokenSet_17());
	private static final long[] mk_tokenSet_18() {
		long[] data = new long[10];
		data[2]=57913476458217472L;
		data[3]=-2246845824160540L;
		data[4]=446402804977663L;
		return data;
	}
	public static final BitSet _tokenSet_18 = new BitSet(mk_tokenSet_18());
	private static final long[] mk_tokenSet_19() {
		long[] data = new long[10];
		data[2]=504992496497983488L;
		data[4]=403583072L;
		return data;
	}
	public static final BitSet _tokenSet_19 = new BitSet(mk_tokenSet_19());
	private static final long[] mk_tokenSet_20() {
		long[] data = new long[10];
		data[2]=1139366725259624448L;
		data[3]=-2246845823898396L;
		data[4]=481604759601151L;
		return data;
	}
	public static final BitSet _tokenSet_20 = new BitSet(mk_tokenSet_20());
	private static final long[] mk_tokenSet_21() {
		long[] data = new long[10];
		data[2]=57913476458217472L;
		data[3]=-2246845824160540L;
		data[4]=446402804060159L;
		return data;
	}
	public static final BitSet _tokenSet_21 = new BitSet(mk_tokenSet_21());
	private static final long[] mk_tokenSet_22() {
		long[] data = new long[10];
		data[0]=2L;
		data[2]=-1152963286048702464L;
		data[3]=-846632761442321L;
		data[4]=18014039254695935L;
		return data;
	}
	public static final BitSet _tokenSet_22 = new BitSet(mk_tokenSet_22());
	private static final long[] mk_tokenSet_23() {
		long[] data = new long[10];
		data[0]=2L;
		data[2]=-36283883716608L;
		data[3]=-2199023255569L;
		data[4]=18014382919254015L;
		return data;
	}
	public static final BitSet _tokenSet_23 = new BitSet(mk_tokenSet_23());
	private static final long[] mk_tokenSet_24() {
		long[] data = new long[10];
		data[4]=917504L;
		return data;
	}
	public static final BitSet _tokenSet_24 = new BitSet(mk_tokenSet_24());
	private static final long[] mk_tokenSet_25() {
		long[] data = new long[8];
		data[2]=4714705859903488L;
		data[3]=3072L;
		return data;
	}
	public static final BitSet _tokenSet_25 = new BitSet(mk_tokenSet_25());
	private static final long[] mk_tokenSet_26() {
		long[] data = new long[10];
		data[3]=-2251799809490944L;
		data[4]=4095L;
		return data;
	}
	public static final BitSet _tokenSet_26 = new BitSet(mk_tokenSet_26());
	private static final long[] mk_tokenSet_27() {
		long[] data = { 0L, 0L, 3377699720527872L, 0L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_27 = new BitSet(mk_tokenSet_27());
	private static final long[] mk_tokenSet_28() {
		long[] data = { 0L, 0L, 1125899906842624L, 0L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_28 = new BitSet(mk_tokenSet_28());
	private static final long[] mk_tokenSet_29() {
		long[] data = new long[10];
		data[2]=505273971474694144L;
		data[4]=403583072L;
		return data;
	}
	public static final BitSet _tokenSet_29 = new BitSet(mk_tokenSet_29());
	private static final long[] mk_tokenSet_30() {
		long[] data = new long[10];
		data[2]=1084830948521934848L;
		data[3]=-2251799809228800L;
		data[4]=35201955545087L;
		return data;
	}
	public static final BitSet _tokenSet_30 = new BitSet(mk_tokenSet_30());
	private static final long[] mk_tokenSet_31() {
		long[] data = new long[10];
		data[0]=2L;
		data[2]=1121281958005964800L;
		data[3]=-1688848244666752L;
		data[4]=106257894490111L;
		return data;
	}
	public static final BitSet _tokenSet_31 = new BitSet(mk_tokenSet_31());
	private static final long[] mk_tokenSet_32() {
		long[] data = new long[10];
		data[0]=2L;
		data[2]=639442977365688320L;
		data[3]=-2246845822058780L;
		data[4]=2804460109598719L;
		return data;
	}
	public static final BitSet _tokenSet_32 = new BitSet(mk_tokenSet_32());
	private static final long[] mk_tokenSet_33() {
		long[] data = new long[10];
		data[2]=504992496497983488L;
		data[3]=262144L;
		data[4]=403583072L;
		return data;
	}
	public static final BitSet _tokenSet_33 = new BitSet(mk_tokenSet_33());
	private static final long[] mk_tokenSet_34() {
		long[] data = new long[10];
		data[2]=1081453248801406976L;
		data[3]=-2251798198091648L;
		data[4]=35201955545087L;
		return data;
	}
	public static final BitSet _tokenSet_34 = new BitSet(mk_tokenSet_34());
	private static final long[] mk_tokenSet_35() {
		long[] data = new long[10];
		data[2]=1085253160987000832L;
		data[3]=-1688848244666752L;
		data[4]=106120455536639L;
		return data;
	}
	public static final BitSet _tokenSet_35 = new BitSet(mk_tokenSet_35());
	private static final long[] mk_tokenSet_36() {
		long[] data = new long[10];
		data[0]=2L;
		data[2]=1143872523910250496L;
		data[3]=-1683895868375324L;
		data[4]=2804460512264191L;
		return data;
	}
	public static final BitSet _tokenSet_36 = new BitSet(mk_tokenSet_36());
	private static final long[] mk_tokenSet_37() {
		long[] data = { 0L, 0L, 18014398509481984L, 0L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_37 = new BitSet(mk_tokenSet_37());
	private static final long[] mk_tokenSet_38() {
		long[] data = new long[10];
		data[2]=595038100766326784L;
		data[3]=2048L;
		data[4]=35201551958016L;
		return data;
	}
	public static final BitSet _tokenSet_38 = new BitSet(mk_tokenSet_38());
	private static final long[] mk_tokenSet_39() {
		long[] data = new long[10];
		data[2]=562949953421312L;
		data[4]=917504L;
		return data;
	}
	public static final BitSet _tokenSet_39 = new BitSet(mk_tokenSet_39());
	private static final long[] mk_tokenSet_40() {
		long[] data = new long[10];
		data[2]=595038100766326784L;
		data[3]=2560L;
		data[4]=35201552875520L;
		return data;
	}
	public static final BitSet _tokenSet_40 = new BitSet(mk_tokenSet_40());
	private static final long[] mk_tokenSet_41() {
		long[] data = new long[10];
		data[2]=1101015759682797568L;
		data[3]=562949953424896L;
		data[4]=106120455532640L;
		return data;
	}
	public static final BitSet _tokenSet_41 = new BitSet(mk_tokenSet_41());
	private static final long[] mk_tokenSet_42() {
		long[] data = { 0L, 0L, 22517998136852480L, 0L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_42 = new BitSet(mk_tokenSet_42());
	private static final long[] mk_tokenSet_43() {
		long[] data = new long[10];
		data[2]=1084830948521934848L;
		data[3]=-2251798198615936L;
		data[4]=35201955545087L;
		return data;
	}
	public static final BitSet _tokenSet_43 = new BitSet(mk_tokenSet_43());
	private static final long[] mk_tokenSet_44() {
		long[] data = new long[10];
		data[2]=508651671195222016L;
		data[4]=403583072L;
		return data;
	}
	public static final BitSet _tokenSet_44 = new BitSet(mk_tokenSet_44());
	private static final long[] mk_tokenSet_45() {
		long[] data = new long[10];
		data[2]=1121141220517609472L;
		data[3]=-2251799809228672L;
		data[4]=35201955545087L;
		return data;
	}
	public static final BitSet _tokenSet_45 = new BitSet(mk_tokenSet_45());
	private static final long[] mk_tokenSet_46() {
		long[] data = new long[10];
		data[2]=1084830948521934848L;
		data[3]=-2251798198613504L;
		data[4]=35201955545087L;
		return data;
	}
	public static final BitSet _tokenSet_46 = new BitSet(mk_tokenSet_46());
	private static final long[] mk_tokenSet_47() {
		long[] data = new long[10];
		data[2]=36028797018963968L;
		data[4]=35184372088832L;
		return data;
	}
	public static final BitSet _tokenSet_47 = new BitSet(mk_tokenSet_47());
	private static final long[] mk_tokenSet_48() {
		long[] data = { 0L, 0L, 619244948763443200L, 0L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_48 = new BitSet(mk_tokenSet_48());
	private static final long[] mk_tokenSet_49() {
		long[] data = new long[10];
		data[0]=2L;
		data[2]=2945285986579382272L;
		data[3]=-2246845822058780L;
		data[4]=2804460113793023L;
		return data;
	}
	public static final BitSet _tokenSet_49 = new BitSet(mk_tokenSet_49());
	private static final long[] mk_tokenSet_50() {
		long[] data = new long[10];
		data[2]=55171294458544128L;
		data[4]=917504L;
		return data;
	}
	public static final BitSet _tokenSet_50 = new BitSet(mk_tokenSet_50());
	private static final long[] mk_tokenSet_51() {
		long[] data = new long[10];
		data[2]=634937178715062272L;
		data[3]=-2246845824160028L;
		data[4]=481604356935679L;
		return data;
	}
	public static final BitSet _tokenSet_51 = new BitSet(mk_tokenSet_51());
	private static final long[] mk_tokenSet_52() {
		long[] data = new long[10];
		data[0]=2L;
		data[2]=-1152963286048702464L;
		data[3]=-283682808021009L;
		data[4]=18014039254695935L;
		return data;
	}
	public static final BitSet _tokenSet_52 = new BitSet(mk_tokenSet_52());
	private static final long[] mk_tokenSet_53() {
		long[] data = new long[10];
		data[2]=1084830948521934848L;
		data[3]=-2251798198091648L;
		data[4]=35201955545087L;
		return data;
	}
	public static final BitSet _tokenSet_53 = new BitSet(mk_tokenSet_53());
	private static final long[] mk_tokenSet_54() {
		long[] data = new long[10];
		data[2]=1082579148708249600L;
		data[3]=-2251799809228800L;
		data[4]=35201955545087L;
		return data;
	}
	public static final BitSet _tokenSet_54 = new BitSet(mk_tokenSet_54());
	private static final long[] mk_tokenSet_55() {
		long[] data = new long[10];
		data[0]=2L;
		data[2]=1152921504606846976L;
		data[3]=77326192640L;
		data[4]=2251799880794112L;
		return data;
	}
	public static final BitSet _tokenSet_55 = new BitSet(mk_tokenSet_55());
	private static final long[] mk_tokenSet_56() {
		long[] data = new long[10];
		data[2]=9007199254740992L;
		data[3]=274794189730048L;
		data[4]=8444249301319680L;
		return data;
	}
	public static final BitSet _tokenSet_56 = new BitSet(mk_tokenSet_56());
	private static final long[] mk_tokenSet_57() {
		long[] data = { 0L, 0L, 4611686018427387904L, 0L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_57 = new BitSet(mk_tokenSet_57());
	private static final long[] mk_tokenSet_58() {
		long[] data = new long[10];
		data[2]=-9222809086901354496L;
		data[3]=11L;
		data[4]=1099512545280L;
		return data;
	}
	public static final BitSet _tokenSet_58 = new BitSet(mk_tokenSet_58());
	private static final long[] mk_tokenSet_59() {
		long[] data = new long[10];
		data[2]=4620693217682128896L;
		data[3]=274794189730048L;
		data[4]=8444249301319680L;
		return data;
	}
	public static final BitSet _tokenSet_59 = new BitSet(mk_tokenSet_59());
	private static final long[] mk_tokenSet_60() {
		long[] data = new long[10];
		data[2]=-9223372036854775808L;
		data[3]=1L;
		data[4]=917504L;
		return data;
	}
	public static final BitSet _tokenSet_60 = new BitSet(mk_tokenSet_60());
	private static final long[] mk_tokenSet_61() {
		long[] data = new long[10];
		data[2]=4643774165772402688L;
		data[3]=27951680721153L;
		data[4]=8444249301319680L;
		return data;
	}
	public static final BitSet _tokenSet_61 = new BitSet(mk_tokenSet_61());
	private static final long[] mk_tokenSet_62() {
		long[] data = new long[10];
		data[2]=-4367785752084348928L;
		data[3]=1716801541127979L;
		data[4]=8453630752342016L;
		return data;
	}
	public static final BitSet _tokenSet_62 = new BitSet(mk_tokenSet_62());
	private static final long[] mk_tokenSet_63() {
		long[] data = new long[10];
		data[2]=-9223372036854775808L;
		data[4]=917504L;
		return data;
	}
	public static final BitSet _tokenSet_63 = new BitSet(mk_tokenSet_63());
	private static final long[] mk_tokenSet_64() {
		long[] data = new long[10];
		data[2]=617556098903179264L;
		data[4]=35201552875520L;
		return data;
	}
	public static final BitSet _tokenSet_64 = new BitSet(mk_tokenSet_64());
	private static final long[] mk_tokenSet_65() {
		long[] data = new long[10];
		data[2]=-8102087879825555456L;
		data[3]=562949953424907L;
		data[4]=107219967160416L;
		return data;
	}
	public static final BitSet _tokenSet_65 = new BitSet(mk_tokenSet_65());
	private static final long[] mk_tokenSet_66() {
		long[] data = new long[10];
		data[2]=613052499275808768L;
		data[4]=35201552875520L;
		return data;
	}
	public static final BitSet _tokenSet_66 = new BitSet(mk_tokenSet_66());
	private static final long[] mk_tokenSet_67() {
		long[] data = new long[10];
		data[2]=-9223372036854775808L;
		data[3]=2L;
		data[4]=917504L;
		return data;
	}
	public static final BitSet _tokenSet_67 = new BitSet(mk_tokenSet_67());
	private static final long[] mk_tokenSet_68() {
		long[] data = new long[10];
		data[2]=509496096125353984L;
		data[3]=2L;
		data[4]=403583072L;
		return data;
	}
	public static final BitSet _tokenSet_68 = new BitSet(mk_tokenSet_68());
	private static final long[] mk_tokenSet_69() {
		long[] data = new long[10];
		data[2]=-8105889991034404864L;
		data[3]=-2251799809228702L;
		data[4]=35201955545087L;
		return data;
	}
	public static final BitSet _tokenSet_69 = new BitSet(mk_tokenSet_69());
	private static final long[] mk_tokenSet_70() {
		long[] data = new long[10];
		data[2]=-9222809086901354496L;
		data[4]=1099512545280L;
		return data;
	}
	public static final BitSet _tokenSet_70 = new BitSet(mk_tokenSet_70());
	private static final long[] mk_tokenSet_71() {
		long[] data = new long[10];
		data[2]=1085956848428777472L;
		data[3]=4456448L;
		data[4]=36301467168864L;
		return data;
	}
	public static final BitSet _tokenSet_71 = new BitSet(mk_tokenSet_71());
	private static final long[] mk_tokenSet_72() {
		long[] data = new long[10];
		data[2]=-8138118875867774976L;
		data[3]=-1688847774904704L;
		data[4]=9116686964637695L;
		return data;
	}
	public static final BitSet _tokenSet_72 = new BitSet(mk_tokenSet_72());
	private static final long[] mk_tokenSet_73() {
		long[] data = new long[10];
		data[2]=1081453248801406976L;
		data[3]=4456448L;
		data[4]=36301467168864L;
		return data;
	}
	public static final BitSet _tokenSet_73 = new BitSet(mk_tokenSet_73());
	private static final long[] mk_tokenSet_74() {
		long[] data = new long[10];
		data[3]=8607760384L;
		data[4]=917504L;
		return data;
	}
	public static final BitSet _tokenSet_74 = new BitSet(mk_tokenSet_74());
	private static final long[] mk_tokenSet_75() {
		long[] data = new long[10];
		data[3]=8L;
		data[4]=917504L;
		return data;
	}
	public static final BitSet _tokenSet_75 = new BitSet(mk_tokenSet_75());
	private static final long[] mk_tokenSet_76() {
		long[] data = new long[10];
		data[2]=40532396646334464L;
		data[3]=73728L;
		data[4]=4398047428608L;
		return data;
	}
	public static final BitSet _tokenSet_76 = new BitSet(mk_tokenSet_76());
	private static final long[] mk_tokenSet_77() {
		long[] data = new long[10];
		data[2]=-8141916589030113280L;
		data[3]=139L;
		data[4]=36301467168864L;
		return data;
	}
	public static final BitSet _tokenSet_77 = new BitSet(mk_tokenSet_77());
	private static final long[] mk_tokenSet_78() {
		long[] data = new long[10];
		data[3]=8192L;
		data[4]=917504L;
		return data;
	}
	public static final BitSet _tokenSet_78 = new BitSet(mk_tokenSet_78());
	private static final long[] mk_tokenSet_79() {
		long[] data = new long[10];
		data[2]=-9222806887878098944L;
		data[3]=11L;
		data[4]=1099512545280L;
		return data;
	}
	public static final BitSet _tokenSet_79 = new BitSet(mk_tokenSet_79());
	private static final long[] mk_tokenSet_80() {
		long[] data = new long[10];
		data[2]=-9222809086901354496L;
		data[3]=8607916043L;
		data[4]=9896712880128L;
		return data;
	}
	public static final BitSet _tokenSet_80 = new BitSet(mk_tokenSet_80());
	private static final long[] mk_tokenSet_81() {
		long[] data = new long[10];
		data[2]=36028797018963968L;
		data[4]=137438986240L;
		return data;
	}
	public static final BitSet _tokenSet_81 = new BitSet(mk_tokenSet_81());
	private static final long[] mk_tokenSet_82() {
		long[] data = new long[8];
		data[3]=4L;
		return data;
	}
	public static final BitSet _tokenSet_82 = new BitSet(mk_tokenSet_82());
	private static final long[] mk_tokenSet_83() {
		long[] data = new long[10];
		data[2]=36028797018963968L;
		data[3]=65536L;
		data[4]=4398046511104L;
		return data;
	}
	public static final BitSet _tokenSet_83 = new BitSet(mk_tokenSet_83());
	private static final long[] mk_tokenSet_84() {
		long[] data = new long[8];
		data[2]=36028797018963968L;
		data[3]=2170880L;
		return data;
	}
	public static final BitSet _tokenSet_84 = new BitSet(mk_tokenSet_84());
	private static final long[] mk_tokenSet_85() {
		long[] data = new long[10];
		data[2]=36028797018963968L;
		data[3]=8396900L;
		data[4]=1074790400L;
		return data;
	}
	public static final BitSet _tokenSet_85 = new BitSet(mk_tokenSet_85());
	private static final long[] mk_tokenSet_86() {
		long[] data = new long[10];
		data[0]=2L;
		data[2]=565148976676864L;
		data[3]=1125899915436074L;
		data[4]=9381453119488L;
		return data;
	}
	public static final BitSet _tokenSet_86 = new BitSet(mk_tokenSet_86());
	private static final long[] mk_tokenSet_87() {
		long[] data = new long[10];
		data[2]=577023702256844800L;
		data[3]=128L;
		data[4]=35201551958016L;
		return data;
	}
	public static final BitSet _tokenSet_87 = new BitSet(mk_tokenSet_87());
	private static final long[] mk_tokenSet_88() {
		long[] data = new long[10];
		data[2]=508792408683577344L;
		data[3]=562949953424896L;
		data[4]=70918903574624L;
		return data;
	}
	public static final BitSet _tokenSet_88 = new BitSet(mk_tokenSet_88());
	private static final long[] mk_tokenSet_89() {
		long[] data = new long[8];
		data[3]=128L;
		return data;
	}
	public static final BitSet _tokenSet_89 = new BitSet(mk_tokenSet_89());
	private static final long[] mk_tokenSet_90() {
		long[] data = new long[10];
		data[0]=2L;
		data[2]=2945285986579382272L;
		data[3]=-2246845822058780L;
		data[4]=2804460109598719L;
		return data;
	}
	public static final BitSet _tokenSet_90 = new BitSet(mk_tokenSet_90());
	private static final long[] mk_tokenSet_91() {
		long[] data = new long[10];
		data[2]=2199023255552L;
		data[4]=917504L;
		return data;
	}
	public static final BitSet _tokenSet_91 = new BitSet(mk_tokenSet_91());
	private static final long[] mk_tokenSet_92() {
		long[] data = new long[10];
		data[2]=36028797018963968L;
		data[4]=137438953472L;
		return data;
	}
	public static final BitSet _tokenSet_92 = new BitSet(mk_tokenSet_92());
	private static final long[] mk_tokenSet_93() {
		long[] data = new long[8];
		data[2]=2199023255552L;
		data[3]=8192L;
		return data;
	}
	public static final BitSet _tokenSet_93 = new BitSet(mk_tokenSet_93());
	private static final long[] mk_tokenSet_94() {
		long[] data = new long[10];
		data[2]=565148976676864L;
		data[3]=12288L;
		data[4]=2251799813685248L;
		return data;
	}
	public static final BitSet _tokenSet_94 = new BitSet(mk_tokenSet_94());
	private static final long[] mk_tokenSet_95() {
		long[] data = new long[10];
		data[2]=504992496497983488L;
		data[3]=562949953691648L;
		data[4]=403583072L;
		return data;
	}
	public static final BitSet _tokenSet_95 = new BitSet(mk_tokenSet_95());
	private static final long[] mk_tokenSet_96() {
		long[] data = new long[10];
		data[2]=577025901280100352L;
		data[3]=12288L;
		data[4]=2287001365643264L;
		return data;
	}
	public static final BitSet _tokenSet_96 = new BitSet(mk_tokenSet_96());
	private static final long[] mk_tokenSet_97() {
		long[] data = new long[10];
		data[2]=505414708963049472L;
		data[3]=562949953695232L;
		data[4]=70918903574624L;
		return data;
	}
	public static final BitSet _tokenSet_97 = new BitSet(mk_tokenSet_97());
	private static final long[] mk_tokenSet_98() {
		long[] data = new long[10];
		data[2]=2199023255552L;
		data[3]=12288L;
		data[4]=2251799813685248L;
		return data;
	}
	public static final BitSet _tokenSet_98 = new BitSet(mk_tokenSet_98());
	private static final long[] mk_tokenSet_99() {
		long[] data = new long[10];
		data[3]=562949953683456L;
		data[4]=917504L;
		return data;
	}
	public static final BitSet _tokenSet_99 = new BitSet(mk_tokenSet_99());
	private static final long[] mk_tokenSet_100() {
		long[] data = new long[10];
		data[0]=2L;
		data[2]=4620695416705384448L;
		data[3]=274794189730048L;
		data[4]=8444249301319680L;
		return data;
	}
	public static final BitSet _tokenSet_100 = new BitSet(mk_tokenSet_100());
	private static final long[] mk_tokenSet_101() {
		long[] data = new long[10];
		data[2]=4620695416705384448L;
		data[3]=274794189730048L;
		data[4]=8444249301319680L;
		return data;
	}
	public static final BitSet _tokenSet_101 = new BitSet(mk_tokenSet_101());
	private static final long[] mk_tokenSet_102() {
		long[] data = new long[10];
		data[2]=615515405322027008L;
		data[3]=536873984L;
		data[4]=35201551958016L;
		return data;
	}
	public static final BitSet _tokenSet_102 = new BitSet(mk_tokenSet_102());
	private static final long[] mk_tokenSet_103() {
		long[] data = new long[10];
		data[2]=4656724213724348416L;
		data[3]=274794189730048L;
		data[4]=8444386740305920L;
		return data;
	}
	public static final BitSet _tokenSet_103 = new BitSet(mk_tokenSet_103());
	private static final long[] mk_tokenSet_104() {
		long[] data = new long[10];
		data[2]=4656724213724348416L;
		data[3]=274794189730048L;
		data[4]=8444386740273152L;
		return data;
	}
	public static final BitSet _tokenSet_104 = new BitSet(mk_tokenSet_104());
	private static final long[] mk_tokenSet_105() {
		long[] data = new long[10];
		data[2]=1082579148708249600L;
		data[3]=-2251798198613504L;
		data[4]=35201955545087L;
		return data;
	}
	public static final BitSet _tokenSet_105 = new BitSet(mk_tokenSet_105());
	private static final long[] mk_tokenSet_106() {
		long[] data = new long[10];
		data[2]=1085255360010256384L;
		data[3]=-1688848244654464L;
		data[4]=2357920269221887L;
		return data;
	}
	public static final BitSet _tokenSet_106 = new BitSet(mk_tokenSet_106());
	private static final long[] mk_tokenSet_107() {
		long[] data = new long[8];
		data[2]=2462906046218240L;
		data[3]=2048L;
		return data;
	}
	public static final BitSet _tokenSet_107 = new BitSet(mk_tokenSet_107());
	private static final long[] mk_tokenSet_108() {
		long[] data = new long[10];
		data[2]=615374667833671680L;
		data[3]=536873984L;
		data[4]=35201551958016L;
		return data;
	}
	public static final BitSet _tokenSet_108 = new BitSet(mk_tokenSet_108());
	private static final long[] mk_tokenSet_109() {
		long[] data = new long[10];
		data[0]=2L;
		data[2]=5126110125668433920L;
		data[3]=837744143154944L;
		data[4]=8515168204894304L;
		return data;
	}
	public static final BitSet _tokenSet_109 = new BitSet(mk_tokenSet_109());
	private static final long[] mk_tokenSet_110() {
		long[] data = new long[8];
		data[2]=2322168557862912L;
		data[3]=3072L;
		return data;
	}
	public static final BitSet _tokenSet_110 = new BitSet(mk_tokenSet_110());
	private static final long[] mk_tokenSet_111() {
		long[] data = new long[10];
		data[2]=613052499275808768L;
		data[3]=536870912L;
		data[4]=35201551958016L;
		return data;
	}
	public static final BitSet _tokenSet_111 = new BitSet(mk_tokenSet_111());
	private static final long[] mk_tokenSet_112() {
		long[] data = new long[10];
		data[2]=38280596832649216L;
		data[3]=65536L;
		data[4]=4398046511104L;
		return data;
	}
	public static final BitSet _tokenSet_112 = new BitSet(mk_tokenSet_112());
	private static final long[] mk_tokenSet_113() {
		long[] data = new long[10];
		data[2]=-9222809086901354496L;
		data[3]=139265L;
		data[4]=9896712880128L;
		return data;
	}
	public static final BitSet _tokenSet_113 = new BitSet(mk_tokenSet_113());
	private static final long[] mk_tokenSet_114() {
		long[] data = new long[10];
		data[2]=5756191861035237376L;
		data[3]=1153851594256683L;
		data[4]=8494330130886752L;
		return data;
	}
	public static final BitSet _tokenSet_114 = new BitSet(mk_tokenSet_114());
	private static final long[] mk_tokenSet_115() {
		long[] data = new long[8];
		data[2]=2199023255552L;
		data[3]=65536L;
		return data;
	}
	public static final BitSet _tokenSet_115 = new BitSet(mk_tokenSet_115());
	private static final long[] mk_tokenSet_116() {
		long[] data = new long[8];
		data[2]=38280596832649216L;
		data[3]=128L;
		return data;
	}
	public static final BitSet _tokenSet_116 = new BitSet(mk_tokenSet_116());
	private static final long[] mk_tokenSet_117() {
		long[] data = new long[10];
		data[2]=562949953421312L;
		data[3]=1125899906981930L;
		data[4]=9381451022336L;
		return data;
	}
	public static final BitSet _tokenSet_117 = new BitSet(mk_tokenSet_117());
	private static final long[] mk_tokenSet_118() {
		long[] data = new long[10];
		data[2]=2199023255552L;
		data[3]=8454144L;
		data[4]=2097152L;
		return data;
	}
	public static final BitSet _tokenSet_118 = new BitSet(mk_tokenSet_118());
	private static final long[] mk_tokenSet_119() {
		long[] data = new long[8];
		data[2]=38280596832649216L;
		data[3]=524416L;
		return data;
	}
	public static final BitSet _tokenSet_119 = new BitSet(mk_tokenSet_119());
	private static final long[] mk_tokenSet_120() {
		long[] data = new long[10];
		data[2]=36030996042219520L;
		data[3]=12288L;
		data[4]=137438986240L;
		return data;
	}
	public static final BitSet _tokenSet_120 = new BitSet(mk_tokenSet_120());
	private static final long[] mk_tokenSet_121() {
		long[] data = new long[10];
		data[2]=2199023255552L;
		data[4]=137438986240L;
		return data;
	}
	public static final BitSet _tokenSet_121 = new BitSet(mk_tokenSet_121());
	private static final long[] mk_tokenSet_122() {
		long[] data = new long[10];
		data[2]=4620695416705384448L;
		data[3]=274794188673280L;
		data[4]=8444249301319680L;
		return data;
	}
	public static final BitSet _tokenSet_122 = new BitSet(mk_tokenSet_122());
	private static final long[] mk_tokenSet_123() {
		long[] data = { 0L, 0L, 4503599627370496L, 0L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_123 = new BitSet(mk_tokenSet_123());
	private static final long[] mk_tokenSet_124() {
		long[] data = new long[10];
		data[2]=562949953421312L;
		data[3]=1L;
		data[4]=1099512545280L;
		return data;
	}
	public static final BitSet _tokenSet_124 = new BitSet(mk_tokenSet_124());
	private static final long[] mk_tokenSet_125() {
		long[] data = new long[8];
		data[2]=36028797018963968L;
		data[3]=8192L;
		return data;
	}
	public static final BitSet _tokenSet_125 = new BitSet(mk_tokenSet_125());
	private static final long[] mk_tokenSet_126() {
		long[] data = new long[8];
		data[2]=70368744177664L;
		data[3]=4194304L;
		return data;
	}
	public static final BitSet _tokenSet_126 = new BitSet(mk_tokenSet_126());
	private static final long[] mk_tokenSet_127() {
		long[] data = new long[10];
		data[2]=504992496497983488L;
		data[4]=9007199658324064L;
		return data;
	}
	public static final BitSet _tokenSet_127 = new BitSet(mk_tokenSet_127());
	private static final long[] mk_tokenSet_128() {
		long[] data = new long[10];
		data[2]=1081453248801406976L;
		data[3]=-2251799800840192L;
		data[4]=35201955545087L;
		return data;
	}
	public static final BitSet _tokenSet_128 = new BitSet(mk_tokenSet_128());
	private static final long[] mk_tokenSet_129() {
		long[] data = new long[10];
		data[2]=-8718377341333536768L;
		data[3]=11L;
		data[4]=1099915210848L;
		return data;
	}
	public static final BitSet _tokenSet_129 = new BitSet(mk_tokenSet_129());
	private static final long[] mk_tokenSet_130() {
		long[] data = new long[10];
		data[2]=6913614766246199296L;
		data[3]=-2223848128417529L;
		data[4]=8484948815003647L;
		return data;
	}
	public static final BitSet _tokenSet_130 = new BitSet(mk_tokenSet_130());
	private static final long[] mk_tokenSet_131() {
		long[] data = new long[8];
		data[2]=36028797018963968L;
		data[3]=65536L;
		return data;
	}
	public static final BitSet _tokenSet_131 = new BitSet(mk_tokenSet_131());
	private static final long[] mk_tokenSet_132() {
		long[] data = new long[10];
		data[3]=8796093026560L;
		data[4]=2251799813685248L;
		return data;
	}
	public static final BitSet _tokenSet_132 = new BitSet(mk_tokenSet_132());
	private static final long[] mk_tokenSet_133() {
		long[] data = new long[8];
		data[3]=8192L;
		return data;
	}
	public static final BitSet _tokenSet_133 = new BitSet(mk_tokenSet_133());
	private static final long[] mk_tokenSet_134() {
		long[] data = new long[10];
		data[3]=8796093034752L;
		data[4]=2251799813685248L;
		return data;
	}
	public static final BitSet _tokenSet_134 = new BitSet(mk_tokenSet_134());
	private static final long[] mk_tokenSet_135() {
		long[] data = new long[10];
		data[2]=2363758684695166976L;
		data[3]=-2246845824160540L;
		data[4]=446402804977663L;
		return data;
	}
	public static final BitSet _tokenSet_135 = new BitSet(mk_tokenSet_135());
	private static final long[] mk_tokenSet_136() {
		long[] data = new long[8];
		data[2]=1152921504606846976L;
		data[3]=77326188544L;
		return data;
	}
	public static final BitSet _tokenSet_136 = new BitSet(mk_tokenSet_136());
	private static final long[] mk_tokenSet_137() {
		long[] data = new long[10];
		data[2]=1152921504606846976L;
		data[3]=77326192640L;
		data[4]=2251799880794112L;
		return data;
	}
	public static final BitSet _tokenSet_137 = new BitSet(mk_tokenSet_137());
	private static final long[] mk_tokenSet_138() {
		long[] data = { 2L, 0L, 0L, 0L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_138 = new BitSet(mk_tokenSet_138());
	private static final long[] mk_tokenSet_139() {
		long[] data = new long[10];
		data[2]=4398046511104L;
		data[4]=917504L;
		return data;
	}
	public static final BitSet _tokenSet_139 = new BitSet(mk_tokenSet_139());
	private static final long[] mk_tokenSet_140() {
		long[] data = new long[10];
		data[2]=2305843009213693952L;
		data[4]=917504L;
		return data;
	}
	public static final BitSet _tokenSet_140 = new BitSet(mk_tokenSet_140());
	private static final long[] mk_tokenSet_141() {
		long[] data = new long[10];
		data[2]=54606145481867264L;
		data[4]=549755813888L;
		return data;
	}
	public static final BitSet _tokenSet_141 = new BitSet(mk_tokenSet_141());
	private static final long[] mk_tokenSet_142() {
		long[] data = new long[8];
		data[3]=4294967296L;
		return data;
	}
	public static final BitSet _tokenSet_142 = new BitSet(mk_tokenSet_142());
	private static final long[] mk_tokenSet_143() {
		long[] data = new long[10];
		data[2]=1084830948521934848L;
		data[3]=-2251798198616060L;
		data[4]=35203029286911L;
		return data;
	}
	public static final BitSet _tokenSet_143 = new BitSet(mk_tokenSet_143());
	private static final long[] mk_tokenSet_144() {
		long[] data = new long[10];
		data[2]=-1157466885676072960L;
		data[3]=-283751529594897L;
		data[4]=9006702558871551L;
		return data;
	}
	public static final BitSet _tokenSet_144 = new BitSet(mk_tokenSet_144());
	private static final long[] mk_tokenSet_145() {
		long[] data = new long[10];
		data[2]=580401401977372672L;
		data[3]=2564L;
		data[4]=35202626617344L;
		return data;
	}
	public static final BitSet _tokenSet_145 = new BitSet(mk_tokenSet_145());
	private static final long[] mk_tokenSet_146() {
		long[] data = new long[10];
		data[2]=-1157466885676072960L;
		data[3]=-283751529857041L;
		data[4]=9006702558871551L;
		return data;
	}
	public static final BitSet _tokenSet_146 = new BitSet(mk_tokenSet_146());
	private static final long[] mk_tokenSet_147() {
		long[] data = new long[10];
		data[2]=3377699720527872L;
		data[3]=4L;
		data[4]=1073741824L;
		return data;
	}
	public static final BitSet _tokenSet_147 = new BitSet(mk_tokenSet_147());
	private static final long[] mk_tokenSet_148() {
		long[] data = new long[10];
		data[2]=1081453248801406976L;
		data[3]=-2251798198616064L;
		data[4]=35201955545087L;
		return data;
	}
	public static final BitSet _tokenSet_148 = new BitSet(mk_tokenSet_148());
	private static final long[] mk_tokenSet_149() {
		long[] data = new long[10];
		data[2]=634937178715062272L;
		data[3]=-2246845824160540L;
		data[4]=481604356018175L;
		return data;
	}
	public static final BitSet _tokenSet_149 = new BitSet(mk_tokenSet_149());
	private static final long[] mk_tokenSet_150() {
		long[] data = new long[8];
		data[2]=21392098230009856L;
		data[3]=281474976710656L;
		return data;
	}
	public static final BitSet _tokenSet_150 = new BitSet(mk_tokenSet_150());
	private static final long[] mk_tokenSet_151() {
		long[] data = new long[10];
		data[2]=9007199254740992L;
		data[3]=274794188673280L;
		data[4]=8444249301319680L;
		return data;
	}
	public static final BitSet _tokenSet_151 = new BitSet(mk_tokenSet_151());
	private static final long[] mk_tokenSet_152() {
		long[] data = { 0L, 0L, 4611688217450643456L, 0L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_152 = new BitSet(mk_tokenSet_152());
	private static final long[] mk_tokenSet_153() {
		long[] data = new long[10];
		data[2]=-9223372036854775808L;
		data[3]=3L;
		data[4]=917504L;
		return data;
	}
	public static final BitSet _tokenSet_153 = new BitSet(mk_tokenSet_153());
	private static final long[] mk_tokenSet_154() {
		long[] data = { 0L, 0L, 6755399441055744L, 0L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_154 = new BitSet(mk_tokenSet_154());
	private static final long[] mk_tokenSet_155() {
		long[] data = new long[10];
		data[2]=6755399441055744L;
		data[4]=35184372088832L;
		return data;
	}
	public static final BitSet _tokenSet_155 = new BitSet(mk_tokenSet_155());
	private static final long[] mk_tokenSet_156() {
		long[] data = new long[10];
		data[2]=4620695416705384448L;
		data[3]=274794188673280L;
		data[4]=8444386740273152L;
		return data;
	}
	public static final BitSet _tokenSet_156 = new BitSet(mk_tokenSet_156());
	private static final long[] mk_tokenSet_157() {
		long[] data = new long[10];
		data[2]=-8102087879825555456L;
		data[3]=562949953424899L;
		data[4]=106120455532640L;
		return data;
	}
	public static final BitSet _tokenSet_157 = new BitSet(mk_tokenSet_157());
	private static final long[] mk_tokenSet_158() {
		long[] data = new long[10];
		data[2]=-9223369837831520256L;
		data[3]=3L;
		data[4]=917504L;
		return data;
	}
	public static final BitSet _tokenSet_158 = new BitSet(mk_tokenSet_158());
	private static final long[] mk_tokenSet_159() {
		long[] data = new long[10];
		data[3]=4L;
		data[4]=1073741824L;
		return data;
	}
	public static final BitSet _tokenSet_159 = new BitSet(mk_tokenSet_159());
	private static final long[] mk_tokenSet_160() {
		long[] data = new long[10];
		data[2]=2199023255552L;
		data[3]=8388608L;
		data[4]=2097152L;
		return data;
	}
	public static final BitSet _tokenSet_160 = new BitSet(mk_tokenSet_160());
	private static final long[] mk_tokenSet_161() {
		long[] data = new long[10];
		data[2]=36591746972385280L;
		data[4]=137438986240L;
		return data;
	}
	public static final BitSet _tokenSet_161 = new BitSet(mk_tokenSet_161());
	private static final long[] mk_tokenSet_162() {
		long[] data = { 0L, 0L, 37154696925806592L, 0L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_162 = new BitSet(mk_tokenSet_162());
	private static final long[] mk_tokenSet_163() {
		long[] data = new long[8];
		data[2]=37225065669984256L;
		data[3]=549755813888L;
		return data;
	}
	public static final BitSet _tokenSet_163 = new BitSet(mk_tokenSet_163());
	private static final long[] mk_tokenSet_164() {
		long[] data = new long[10];
		data[3]=33554432L;
		data[4]=917504L;
		return data;
	}
	public static final BitSet _tokenSet_164 = new BitSet(mk_tokenSet_164());
	private static final long[] mk_tokenSet_165() {
		long[] data = new long[10];
		data[2]=6755399441055744L;
		data[4]=917504L;
		return data;
	}
	public static final BitSet _tokenSet_165 = new BitSet(mk_tokenSet_165());
	private static final long[] mk_tokenSet_166() {
		long[] data = new long[10];
		data[2]=7318349394477056L;
		data[4]=5111808L;
		return data;
	}
	public static final BitSet _tokenSet_166 = new BitSet(mk_tokenSet_166());
	private static final long[] mk_tokenSet_167() {
		long[] data = new long[10];
		data[3]=549755813888L;
		data[4]=917504L;
		return data;
	}
	public static final BitSet _tokenSet_167 = new BitSet(mk_tokenSet_167());
	private static final long[] mk_tokenSet_168() {
		long[] data = new long[10];
		data[2]=7318349394477056L;
		data[4]=15054274560L;
		return data;
	}
	public static final BitSet _tokenSet_168 = new BitSet(mk_tokenSet_168());
	private static final long[] mk_tokenSet_169() {
		long[] data = new long[10];
		data[3]=1099511627776L;
		data[4]=917504L;
		return data;
	}
	public static final BitSet _tokenSet_169 = new BitSet(mk_tokenSet_169());
	private static final long[] mk_tokenSet_170() {
		long[] data = new long[10];
		data[4]=1073741824L;
		return data;
	}
	public static final BitSet _tokenSet_170 = new BitSet(mk_tokenSet_170());
	private static final long[] mk_tokenSet_171() {
		long[] data = new long[10];
		data[2]=72057594037927936L;
		data[4]=917504L;
		return data;
	}
	public static final BitSet _tokenSet_171 = new BitSet(mk_tokenSet_171());
	private static final long[] mk_tokenSet_172() {
		long[] data = new long[10];
		data[2]=26388279066624L;
		data[4]=917504L;
		return data;
	}
	public static final BitSet _tokenSet_172 = new BitSet(mk_tokenSet_172());
	private static final long[] mk_tokenSet_173() {
		long[] data = new long[10];
		data[2]=634937178715062272L;
		data[3]=-2246845824160540L;
		data[4]=481604356935679L;
		return data;
	}
	public static final BitSet _tokenSet_173 = new BitSet(mk_tokenSet_173());
	private static final long[] mk_tokenSet_174() {
		long[] data = { 0L, 0L, 38280596832649216L, 0L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_174 = new BitSet(mk_tokenSet_174());
	private static final long[] mk_tokenSet_175() {
		long[] data = new long[10];
		data[2]=613052499275808768L;
		data[4]=35201551958016L;
		return data;
	}
	public static final BitSet _tokenSet_175 = new BitSet(mk_tokenSet_175());
	private static final long[] mk_tokenSet_176() {
		long[] data = new long[10];
		data[2]=9007199254740992L;
		data[3]=27951680720896L;
		data[4]=8444249301319680L;
		return data;
	}
	public static final BitSet _tokenSet_176 = new BitSet(mk_tokenSet_176());
	private static final long[] mk_tokenSet_177() {
		long[] data = new long[10];
		data[2]=9009398277996544L;
		data[3]=27951680720896L;
		data[4]=8444249301319680L;
		return data;
	}
	public static final BitSet _tokenSet_177 = new BitSet(mk_tokenSet_177());
	private static final long[] mk_tokenSet_178() {
		long[] data = new long[10];
		data[2]=9007199254740992L;
		data[3]=274794189689088L;
		data[4]=8444249301319680L;
		return data;
	}
	public static final BitSet _tokenSet_178 = new BitSet(mk_tokenSet_178());
	private static final long[] mk_tokenSet_179() {
		long[] data = new long[10];
		data[2]=9009398277996544L;
		data[3]=274794189689088L;
		data[4]=8444249301319680L;
		return data;
	}
	public static final BitSet _tokenSet_179 = new BitSet(mk_tokenSet_179());
	private static final long[] mk_tokenSet_180() {
		long[] data = new long[10];
		data[0]=2L;
		data[2]=9007199254740992L;
		data[3]=10359494676736L;
		data[4]=8444249301319680L;
		return data;
	}
	public static final BitSet _tokenSet_180 = new BitSet(mk_tokenSet_180());
	private static final long[] mk_tokenSet_181() {
		long[] data = new long[10];
		data[2]=9007199254740992L;
		data[3]=10359494676736L;
		data[4]=8444249301319680L;
		return data;
	}
	public static final BitSet _tokenSet_181 = new BitSet(mk_tokenSet_181());
	private static final long[] mk_tokenSet_182() {
		long[] data = new long[10];
		data[4]=8796093939712L;
		return data;
	}
	public static final BitSet _tokenSet_182 = new BitSet(mk_tokenSet_182());
	private static final long[] mk_tokenSet_183() {
		long[] data = new long[10];
		data[2]=40532396646334464L;
		data[3]=2105344L;
		data[4]=4398046511104L;
		return data;
	}
	public static final BitSet _tokenSet_183 = new BitSet(mk_tokenSet_183());
	private static final long[] mk_tokenSet_184() {
		long[] data = new long[10];
		data[0]=2L;
		data[2]=504994695521239040L;
		data[3]=1125899915436074L;
		data[4]=9381721567328L;
		return data;
	}
	public static final BitSet _tokenSet_184 = new BitSet(mk_tokenSet_184());
	private static final long[] mk_tokenSet_185() {
		long[] data = new long[10];
		data[2]=1081453248801406976L;
		data[3]=4456448L;
		data[4]=35201955541088L;
		return data;
	}
	public static final BitSet _tokenSet_185 = new BitSet(mk_tokenSet_185());
	private static final long[] mk_tokenSet_186() {
		long[] data = new long[10];
		data[2]=1085253160987000832L;
		data[3]=-1688847774904704L;
		data[4]=9113319710277631L;
		return data;
	}
	public static final BitSet _tokenSet_186 = new BitSet(mk_tokenSet_186());
	private static final long[] mk_tokenSet_187() {
		long[] data = new long[10];
		data[2]=1081523617545584640L;
		data[3]=262144L;
		data[4]=35201955541088L;
		return data;
	}
	public static final BitSet _tokenSet_187 = new BitSet(mk_tokenSet_187());
	private static final long[] mk_tokenSet_188() {
		long[] data = new long[10];
		data[4]=933888L;
		return data;
	}
	public static final BitSet _tokenSet_188 = new BitSet(mk_tokenSet_188());
	private static final long[] mk_tokenSet_189() {
		long[] data = new long[10];
		data[2]=509496096125353984L;
		data[4]=403583072L;
		return data;
	}
	public static final BitSet _tokenSet_189 = new BitSet(mk_tokenSet_189());
	private static final long[] mk_tokenSet_190() {
		long[] data = new long[10];
		data[2]=1081453248801406976L;
		data[3]=-2251799809228800L;
		data[4]=35201956610047L;
		return data;
	}
	public static final BitSet _tokenSet_190 = new BitSet(mk_tokenSet_190());
	private static final long[] mk_tokenSet_191() {
		long[] data = new long[10];
		data[3]=131072L;
		data[4]=917504L;
		return data;
	}
	public static final BitSet _tokenSet_191 = new BitSet(mk_tokenSet_191());
	private static final long[] mk_tokenSet_192() {
		long[] data = new long[10];
		data[2]=1099467647310888960L;
		data[3]=-2251799809097728L;
		data[4]=35201955545087L;
		return data;
	}
	public static final BitSet _tokenSet_192 = new BitSet(mk_tokenSet_192());
	private static final long[] mk_tokenSet_193() {
		long[] data = new long[10];
		data[3]=8192L;
		data[4]=1108213760L;
		return data;
	}
	public static final BitSet _tokenSet_193 = new BitSet(mk_tokenSet_193());
	private static final long[] mk_tokenSet_194() {
		long[] data = new long[10];
		data[2]=509498295148609536L;
		data[3]=1125899906981930L;
		data[4]=9381719470176L;
		return data;
	}
	public static final BitSet _tokenSet_194 = new BitSet(mk_tokenSet_194());
	private static final long[] mk_tokenSet_195() {
		long[] data = new long[10];
		data[2]=1122058213215174656L;
		data[3]=-1125899900084182L;
		data[4]=48981322137599L;
		return data;
	}
	public static final BitSet _tokenSet_195 = new BitSet(mk_tokenSet_195());
	private static final long[] mk_tokenSet_196() {
		long[] data = new long[10];
		data[2]=617556098903179264L;
		data[4]=35201551958016L;
		return data;
	}
	public static final BitSet _tokenSet_196 = new BitSet(mk_tokenSet_196());
	private static final long[] mk_tokenSet_197() {
		long[] data = new long[10];
		data[0]=2L;
		data[2]=508794607706832896L;
		data[3]=1688849868860970L;
		data[4]=79750465744992L;
		return data;
	}
	public static final BitSet _tokenSet_197 = new BitSet(mk_tokenSet_197());
	private static final long[] mk_tokenSet_198() {
		long[] data = new long[10];
		data[2]=4620693217682128896L;
		data[3]=27951680721152L;
		data[4]=8444249301319680L;
		return data;
	}
	public static final BitSet _tokenSet_198 = new BitSet(mk_tokenSet_198());
	private static final long[] mk_tokenSet_199() {
		long[] data = new long[10];
		data[2]=9007199254740992L;
		data[3]=27951680721152L;
		data[4]=8444249301319680L;
		return data;
	}
	public static final BitSet _tokenSet_199 = new BitSet(mk_tokenSet_199());
	private static final long[] mk_tokenSet_200() {
		long[] data = new long[8];
		data[2]=37154696925806592L;
		data[3]=2170880L;
		return data;
	}
	public static final BitSet _tokenSet_200 = new BitSet(mk_tokenSet_200());
	private static final long[] mk_tokenSet_201() {
		long[] data = new long[10];
		data[2]=1081453248801406976L;
		data[3]=-2247400152104960L;
		data[4]=35201955545087L;
		return data;
	}
	public static final BitSet _tokenSet_201 = new BitSet(mk_tokenSet_201());
	private static final long[] mk_tokenSet_202() {
		long[] data = new long[10];
		data[2]=1117482045820370944L;
		data[3]=-2251798198616064L;
		data[4]=35201955545087L;
		return data;
	}
	public static final BitSet _tokenSet_202 = new BitSet(mk_tokenSet_202());
	private static final long[] mk_tokenSet_203() {
		long[] data = { 0L, 0L, 54043195528445952L, 0L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_203 = new BitSet(mk_tokenSet_203());
	private static final long[] mk_tokenSet_204() {
		long[] data = new long[10];
		data[2]=180143985094819840L;
		data[4]=917504L;
		return data;
	}
	public static final BitSet _tokenSet_204 = new BitSet(mk_tokenSet_204());
	private static final long[] mk_tokenSet_205() {
		long[] data = new long[10];
		data[2]=54043195528445952L;
		data[4]=549755813888L;
		return data;
	}
	public static final BitSet _tokenSet_205 = new BitSet(mk_tokenSet_205());
	private static final long[] mk_tokenSet_206() {
		long[] data = new long[8];
		data[3]=34359738368L;
		return data;
	}
	public static final BitSet _tokenSet_206 = new BitSet(mk_tokenSet_206());
	private static final long[] mk_tokenSet_207() {
		long[] data = new long[10];
		data[2]=58476426411638784L;
		data[3]=-2246845824160028L;
		data[4]=446402804977663L;
		return data;
	}
	public static final BitSet _tokenSet_207 = new BitSet(mk_tokenSet_207());
	private static final long[] mk_tokenSet_208() {
		long[] data = new long[8];
		data[2]=38280596832649216L;
		data[3]=65536L;
		return data;
	}
	public static final BitSet _tokenSet_208 = new BitSet(mk_tokenSet_208());
	
	}
