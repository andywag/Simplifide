// $ANTLR 2.7.7 (2006-11-01): "verilog.g" -> "VerilogParser.java"$
package com.simplifide.base.verilog.parse.grammar.verilog;


import com.simplifide.base.sourcefile.antlr.tok.*;



import antlr.TokenBuffer;
import antlr.TokenStreamException;
import antlr.TokenStreamIOException;
import antlr.ANTLRException;
import com.simplifide.base.sourcefile.antlr.grammar.BaseParser;      import com.simplifide.base.sourcefile.antlr.grammar.BaseVerilogParser;
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

public class VerilogParser extends BaseVerilogParser       implements VerilogTokenTypes
 {

 


protected VerilogParser(TokenBuffer tokenBuf, int k) {
  super(tokenBuf,k);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

public VerilogParser(TokenBuffer tokenBuf) {
  this(tokenBuf,2);
}

protected VerilogParser(TokenStream lexer, int k) {
  super(lexer,k);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

public VerilogParser(TokenStream lexer) {
  this(lexer,2);
}

public VerilogParser(ParserSharedInputState state) {
  super(state,2);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

	public final void source_text() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST source_text_AST = null;
		
		try {      // for error handling
			{
			_loop3:
			do {
				switch ( LA(1)) {
				case TICKIFDEF:
				case TICKIFNDEF:
				case TICKDEFINE:
				case TICKINCLUDE:
				case TICKTIMESCALE:
				{
					context_item();
					astFactory.addASTChild(currentAST, returnAST);
					break;
				}
				case LITERAL_module:
				case LITERAL_macromodule:
				{
					design_unit();
					astFactory.addASTChild(currentAST, returnAST);
					break;
				}
				default:
				{
					break _loop3;
				}
				}
			} while (true);
			}
			AST tmp1_AST = null;
			tmp1_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp1_AST);
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
				recover(ex,_tokenSet_0);
			} else {
			  throw ex;
			}
		}
		returnAST = source_text_AST;
	}
	
	public final void context_item() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST context_item_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case TICKDEFINE:
			{
				define_directive();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case TICKINCLUDE:
			{
				include_directive();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case TICKIFDEF:
			case TICKIFNDEF:
			{
				ifdef_directive();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case TICKTIMESCALE:
			{
				timescale_directive();
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
				context_item_AST = (AST)currentAST.root;
				context_item_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(CONTEXTITEM,"ContextItem")).add(context_item_AST));
				currentAST.root = context_item_AST;
				currentAST.child = context_item_AST!=null &&context_item_AST.getFirstChild()!=null ?
					context_item_AST.getFirstChild() : context_item_AST;
				currentAST.advanceChildToEnd();
			}
			context_item_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_1);
			} else {
			  throw ex;
			}
		}
		returnAST = context_item_AST;
	}
	
	public final void design_unit() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST design_unit_AST = null;
		
		try {      // for error handling
			module();
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
				recover(ex,_tokenSet_1);
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
				recover(ex,_tokenSet_0);
			} else {
			  throw ex;
			}
		}
		returnAST = source_text_name_AST;
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
			_loop387:
			do {
				if ((_tokenSet_2.member(LA(1))) && (_tokenSet_3.member(LA(2)))) {
					{
					switch ( LA(1)) {
					case DOT:
					{
						name_dot();
						astFactory.addASTChild(currentAST, returnAST);
						break;
					}
					case LBRACK:
					{
						name_range();
						astFactory.addASTChild(currentAST, returnAST);
						break;
					}
					case LPAREN:
					{
						name_expression();
						astFactory.addASTChild(currentAST, returnAST);
						break;
					}
					case POUND:
					{
						name_pound_expression();
						astFactory.addASTChild(currentAST, returnAST);
						break;
					}
					case DOUBLECOLON:
					{
						name_colon();
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
					break _loop387;
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
				recover(ex,_tokenSet_4);
			} else {
			  throw ex;
			}
		}
		returnAST = name_AST;
	}
	
	public final void module() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST module_AST = null;
		
		try {      // for error handling
			module_dec();
			astFactory.addASTChild(currentAST, returnAST);
			module_body();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp2_AST = null;
			tmp2_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp2_AST);
			match(LITERAL_endmodule);
			if ( inputState.guessing==0 ) {
				module_AST = (AST)currentAST.root;
				module_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(MODULE,"Module")).add(module_AST));
				currentAST.root = module_AST;
				currentAST.child = module_AST!=null &&module_AST.getFirstChild()!=null ?
					module_AST.getFirstChild() : module_AST;
				currentAST.advanceChildToEnd();
			}
			module_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_1);
			} else {
			  throw ex;
			}
		}
		returnAST = module_AST;
	}
	
	public final void module_dec() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST module_dec_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case LITERAL_module:
			{
				AST tmp3_AST = null;
				tmp3_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp3_AST);
				match(LITERAL_module);
				break;
			}
			case LITERAL_macromodule:
			{
				AST tmp4_AST = null;
				tmp4_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp4_AST);
				match(LITERAL_macromodule);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			module_name();
			astFactory.addASTChild(currentAST, returnAST);
			{
			switch ( LA(1)) {
			case LPAREN:
			{
				port_clause();
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
			AST tmp5_AST = null;
			tmp5_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp5_AST);
			match(SEMI);
			if ( inputState.guessing==0 ) {
				module_dec_AST = (AST)currentAST.root;
				module_dec_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(MODULEDEC,"ModuleDec")).add(module_dec_AST));
				currentAST.root = module_dec_AST;
				currentAST.child = module_dec_AST!=null &&module_dec_AST.getFirstChild()!=null ?
					module_dec_AST.getFirstChild() : module_dec_AST;
				currentAST.advanceChildToEnd();
			}
			module_dec_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_5);
			} else {
			  throw ex;
			}
		}
		returnAST = module_dec_AST;
	}
	
	public final void module_body() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST module_body_AST = null;
		
		try {      // for error handling
			{
			_loop13:
			do {
				if ((_tokenSet_6.member(LA(1)))) {
					module_item();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop13;
				}
				
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				module_body_AST = (AST)currentAST.root;
				module_body_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(MODULEBODY,"Module_Body")).add(module_body_AST));
				currentAST.root = module_body_AST;
				currentAST.child = module_body_AST!=null &&module_body_AST.getFirstChild()!=null ?
					module_body_AST.getFirstChild() : module_body_AST;
				currentAST.advanceChildToEnd();
			}
			module_body_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_7);
			} else {
			  throw ex;
			}
		}
		returnAST = module_body_AST;
	}
	
	public final void module_name() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST module_name_AST = null;
		
		try {      // for error handling
			name_of_module();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				module_name_AST = (AST)currentAST.root;
				module_name_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(MODULENAME,"ModuleName")).add(module_name_AST));
				currentAST.root = module_name_AST;
				currentAST.child = module_name_AST!=null &&module_name_AST.getFirstChild()!=null ?
					module_name_AST.getFirstChild() : module_name_AST;
				currentAST.advanceChildToEnd();
			}
			module_name_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_8);
			} else {
			  throw ex;
			}
		}
		returnAST = module_name_AST;
	}
	
	public final void port_clause() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST port_clause_AST = null;
		
		try {      // for error handling
			AST tmp6_AST = null;
			tmp6_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp6_AST);
			match(LPAREN);
			port_list();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp7_AST = null;
			tmp7_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp7_AST);
			match(RPAREN);
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
				recover(ex,_tokenSet_9);
			} else {
			  throw ex;
			}
		}
		returnAST = port_clause_AST;
	}
	
	public final void name_of_module() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST name_of_module_AST = null;
		
		try {      // for error handling
			local_identifier();
			astFactory.addASTChild(currentAST, returnAST);
			name_of_module_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_8);
			} else {
			  throw ex;
			}
		}
		returnAST = name_of_module_AST;
	}
	
	public final void module_item() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST module_item_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case LITERAL_generate:
			{
				generate_item();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case LITERAL_and:
			case LITERAL_nand:
			case LITERAL_or:
			case LITERAL_nor:
			case LITERAL_xor:
			case LITERAL_xnor:
			case LITERAL_buf:
			case 187:
			case 188:
			case LITERAL_not:
			case 190:
			case 191:
			case LITERAL_pulldown:
			case LITERAL_pullup:
			case LITERAL_nmos:
			case LITERAL_rnmos:
			case LITERAL_pmos:
			case LITERAL_rpmos:
			case LITERAL_cmos:
			case LITERAL_rcmos:
			case LITERAL_tran:
			case LITERAL_rtran:
			case 202:
			case 203:
			case 204:
			{
				gate_instantiation();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case IDENTIFIER:
			case ESCAPED_IDENTIFIER:
			case DOLLAR_IDENTIFIER:
			{
				instantiation();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case LITERAL_defparam:
			{
				parameter_override();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case LITERAL_assign:
			{
				continuous_assign();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case LITERAL_specify:
			{
				specify_block();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case LITERAL_initial:
			case LITERAL_final:
			{
				initial_statement();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case LITERAL_always:
			case LITERAL_always_latch:
			case LITERAL_always_comb:
			case LITERAL_always_ff:
			{
				always_construct();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case LITERAL_task:
			{
				task_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case LITERAL_function:
			{
				function_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			module_item_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_10);
			} else {
			  throw ex;
			}
		}
		returnAST = module_item_AST;
	}
	
	public final void port_list() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST port_list_AST = null;
		
		try {      // for error handling
			{
			{
			switch ( LA(1)) {
			case IDENTIFIER:
			case ESCAPED_IDENTIFIER:
			case DOLLAR_IDENTIFIER:
			{
				port_element();
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
			_loop19:
			do {
				if ((LA(1)==COMMA)) {
					AST tmp8_AST = null;
					tmp8_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp8_AST);
					match(COMMA);
					port_element();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop19;
				}
				
			} while (true);
			}
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
				recover(ex,_tokenSet_11);
			} else {
			  throw ex;
			}
		}
		returnAST = port_list_AST;
	}
	
	public final void port_element() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST port_element_AST = null;
		
		try {      // for error handling
			{
			port_no_dot();
			astFactory.addASTChild(currentAST, returnAST);
			}
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
				recover(ex,_tokenSet_12);
			} else {
			  throw ex;
			}
		}
		returnAST = port_element_AST;
	}
	
	public final void port_no_dot() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST port_no_dot_AST = null;
		
		try {      // for error handling
			identifier();
			astFactory.addASTChild(currentAST, returnAST);
			{
			switch ( LA(1)) {
			case LBRACK:
			{
				range();
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
				port_no_dot_AST = (AST)currentAST.root;
				port_no_dot_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(PORTNODOT,"PortNoDot")).add(port_no_dot_AST));
				currentAST.root = port_no_dot_AST;
				currentAST.child = port_no_dot_AST!=null &&port_no_dot_AST.getFirstChild()!=null ?
					port_no_dot_AST.getFirstChild() : port_no_dot_AST;
				currentAST.advanceChildToEnd();
			}
			port_no_dot_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_12);
			} else {
			  throw ex;
			}
		}
		returnAST = port_no_dot_AST;
	}
	
	public final void identifier() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST identifier_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case IDENTIFIER:
			{
				AST tmp9_AST = null;
				tmp9_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp9_AST);
				match(IDENTIFIER);
				identifier_AST = (AST)currentAST.root;
				break;
			}
			case ESCAPED_IDENTIFIER:
			{
				AST tmp10_AST = null;
				tmp10_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp10_AST);
				match(ESCAPED_IDENTIFIER);
				identifier_AST = (AST)currentAST.root;
				break;
			}
			case DOLLAR_IDENTIFIER:
			{
				AST tmp11_AST = null;
				tmp11_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp11_AST);
				match(DOLLAR_IDENTIFIER);
				identifier_AST = (AST)currentAST.root;
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
				recover(ex,_tokenSet_13);
			} else {
			  throw ex;
			}
		}
		returnAST = identifier_AST;
	}
	
	public final void range() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST range_AST = null;
		
		try {      // for error handling
			{
			boolean synPredMatched105 = false;
			if (((LA(1)==LBRACK) && (_tokenSet_14.member(LA(2))))) {
				int _m105 = mark();
				synPredMatched105 = true;
				inputState.guessing++;
				try {
					{
					match(LBRACK);
					range_param();
					match(COLON);
					}
				}
				catch (RecognitionException pe) {
					synPredMatched105 = false;
				}
				rewind(_m105);
inputState.guessing--;
			}
			if ( synPredMatched105 ) {
				AST tmp12_AST = null;
				tmp12_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp12_AST);
				match(LBRACK);
				range_param();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp13_AST = null;
				tmp13_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp13_AST);
				match(COLON);
				{
				switch ( LA(1)) {
				case LPAREN:
				case NUMBER:
				case PLUS:
				case MINUS:
				case IDENTIFIER:
				case LCURLY:
				case STRING:
				case LNOT:
				case BNOT:
				case BAND:
				case RNAND:
				case BOR:
				case RNOR:
				case BXOR:
				case RXNOR:
				case PLUSPLUS:
				case MINMIN:
				case ESCAPED_IDENTIFIER:
				case DOLLAR_IDENTIFIER:
				{
					expression();
					astFactory.addASTChild(currentAST, returnAST);
					break;
				}
				case DOLLAR:
				{
					AST tmp14_AST = null;
					tmp14_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp14_AST);
					match(DOLLAR);
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				AST tmp15_AST = null;
				tmp15_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp15_AST);
				match(RBRACK);
			}
			else if ((LA(1)==LBRACK) && (_tokenSet_14.member(LA(2)))) {
				AST tmp16_AST = null;
				tmp16_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp16_AST);
				match(LBRACK);
				{
				switch ( LA(1)) {
				case LPAREN:
				case NUMBER:
				case PLUS:
				case MINUS:
				case IDENTIFIER:
				case LCURLY:
				case STRING:
				case LNOT:
				case BNOT:
				case BAND:
				case RNAND:
				case BOR:
				case RNOR:
				case BXOR:
				case RXNOR:
				case PLUSPLUS:
				case MINMIN:
				case ESCAPED_IDENTIFIER:
				case DOLLAR_IDENTIFIER:
				{
					expression();
					astFactory.addASTChild(currentAST, returnAST);
					break;
				}
				case DOLLAR:
				{
					AST tmp17_AST = null;
					tmp17_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp17_AST);
					match(DOLLAR);
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				AST tmp18_AST = null;
				tmp18_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp18_AST);
				match(RBRACK);
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			if ( inputState.guessing==0 ) {
				range_AST = (AST)currentAST.root;
				range_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(VARRANGE,"VarRange")).add(range_AST));
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
				recover(ex,_tokenSet_15);
			} else {
			  throw ex;
			}
		}
		returnAST = range_AST;
	}
	
	public final void port_expression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST port_expression_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case IDENTIFIER:
			case ESCAPED_IDENTIFIER:
			case DOLLAR_IDENTIFIER:
			{
				port_reference();
				astFactory.addASTChild(currentAST, returnAST);
				port_expression_AST = (AST)currentAST.root;
				break;
			}
			case LBRACE:
			{
				AST tmp19_AST = null;
				tmp19_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp19_AST);
				match(LBRACE);
				port_reference();
				astFactory.addASTChild(currentAST, returnAST);
				{
				_loop26:
				do {
					if ((LA(1)==COMMA)) {
						AST tmp20_AST = null;
						tmp20_AST = astFactory.create(LT(1));
						astFactory.addASTChild(currentAST, tmp20_AST);
						match(COMMA);
						port_reference();
						astFactory.addASTChild(currentAST, returnAST);
					}
					else {
						break _loop26;
					}
					
				} while (true);
				}
				AST tmp21_AST = null;
				tmp21_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp21_AST);
				match(RBRACE);
				port_expression_AST = (AST)currentAST.root;
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
		returnAST = port_expression_AST;
	}
	
	public final void port_reference() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST port_reference_AST = null;
		
		try {      // for error handling
			boolean synPredMatched29 = false;
			if (((LA(1)==IDENTIFIER||LA(1)==ESCAPED_IDENTIFIER||LA(1)==DOLLAR_IDENTIFIER) && (LA(2)==LBRACK))) {
				int _m29 = mark();
				synPredMatched29 = true;
				inputState.guessing++;
				try {
					{
					name_of_variable();
					match(LBRACK);
					expression();
					match(COLON);
					}
				}
				catch (RecognitionException pe) {
					synPredMatched29 = false;
				}
				rewind(_m29);
inputState.guessing--;
			}
			if ( synPredMatched29 ) {
				name_of_variable();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp22_AST = null;
				tmp22_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp22_AST);
				match(LBRACK);
				expression();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp23_AST = null;
				tmp23_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp23_AST);
				match(COLON);
				expression();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp24_AST = null;
				tmp24_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp24_AST);
				match(RBRACK);
				port_reference_AST = (AST)currentAST.root;
			}
			else {
				boolean synPredMatched31 = false;
				if (((LA(1)==IDENTIFIER||LA(1)==ESCAPED_IDENTIFIER||LA(1)==DOLLAR_IDENTIFIER) && (LA(2)==LBRACK))) {
					int _m31 = mark();
					synPredMatched31 = true;
					inputState.guessing++;
					try {
						{
						name_of_variable();
						match(LBRACK);
						}
					}
					catch (RecognitionException pe) {
						synPredMatched31 = false;
					}
					rewind(_m31);
inputState.guessing--;
				}
				if ( synPredMatched31 ) {
					name_of_variable();
					astFactory.addASTChild(currentAST, returnAST);
					AST tmp25_AST = null;
					tmp25_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp25_AST);
					match(LBRACK);
					expression();
					astFactory.addASTChild(currentAST, returnAST);
					AST tmp26_AST = null;
					tmp26_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp26_AST);
					match(RBRACK);
					port_reference_AST = (AST)currentAST.root;
				}
				else if ((LA(1)==IDENTIFIER||LA(1)==ESCAPED_IDENTIFIER||LA(1)==DOLLAR_IDENTIFIER) && (LA(2)==EOF||LA(2)==COMMA||LA(2)==RBRACE)) {
					name_of_variable();
					astFactory.addASTChild(currentAST, returnAST);
					port_reference_AST = (AST)currentAST.root;
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
			returnAST = port_reference_AST;
		}
		
	public final void name_of_variable() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST name_of_variable_AST = null;
		
		try {      // for error handling
			local_identifier();
			astFactory.addASTChild(currentAST, returnAST);
			name_of_variable_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_17);
			} else {
			  throw ex;
			}
		}
		returnAST = name_of_variable_AST;
	}
	
	public final void expression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST expression_AST = null;
		
		try {      // for error handling
			exp6();
			astFactory.addASTChild(currentAST, returnAST);
			expression_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_18);
			} else {
			  throw ex;
			}
		}
		returnAST = expression_AST;
	}
	
	public final void generate_item() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST generate_item_AST = null;
		
		try {      // for error handling
			AST tmp27_AST = null;
			tmp27_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp27_AST);
			match(LITERAL_generate);
			{
			_loop36:
			do {
				if ((_tokenSet_6.member(LA(1)))) {
					module_item();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop36;
				}
				
			} while (true);
			}
			AST tmp28_AST = null;
			tmp28_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp28_AST);
			match(LITERAL_endgenerate);
			if ( inputState.guessing==0 ) {
				generate_item_AST = (AST)currentAST.root;
				generate_item_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(GENERATEITEM,"generate_item")).add(generate_item_AST));
				currentAST.root = generate_item_AST;
				currentAST.child = generate_item_AST!=null &&generate_item_AST.getFirstChild()!=null ?
					generate_item_AST.getFirstChild() : generate_item_AST;
				currentAST.advanceChildToEnd();
			}
			generate_item_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_10);
			} else {
			  throw ex;
			}
		}
		returnAST = generate_item_AST;
	}
	
	public final void gate_instantiation() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST gate_instantiation_AST = null;
		
		try {      // for error handling
			gate_type();
			astFactory.addASTChild(currentAST, returnAST);
			{
			if ((LA(1)==LPAREN) && (_tokenSet_19.member(LA(2)))) {
				drive_strength();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else if ((_tokenSet_20.member(LA(1))) && (_tokenSet_21.member(LA(2)))) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			{
			switch ( LA(1)) {
			case POUND:
			{
				delay();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case LPAREN:
			case LBRACK:
			case IDENTIFIER:
			case ESCAPED_IDENTIFIER:
			case DOLLAR_IDENTIFIER:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			gate_instance();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop115:
			do {
				if ((LA(1)==COMMA)) {
					AST tmp29_AST = null;
					tmp29_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp29_AST);
					match(COMMA);
					gate_instance();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop115;
				}
				
			} while (true);
			}
			AST tmp30_AST = null;
			tmp30_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp30_AST);
			match(SEMI);
			gate_instantiation_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_10);
			} else {
			  throw ex;
			}
		}
		returnAST = gate_instantiation_AST;
	}
	
	public final void instantiation() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST instantiation_AST = null;
		
		try {      // for error handling
			identifier();
			astFactory.addASTChild(currentAST, returnAST);
			module_instance();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp31_AST = null;
			tmp31_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp31_AST);
			match(SEMI);
			if ( inputState.guessing==0 ) {
				instantiation_AST = (AST)currentAST.root;
				instantiation_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(MODINSTANCETOP,"ModInstanceTop")).add(instantiation_AST));
				currentAST.root = instantiation_AST;
				currentAST.child = instantiation_AST!=null &&instantiation_AST.getFirstChild()!=null ?
					instantiation_AST.getFirstChild() : instantiation_AST;
				currentAST.advanceChildToEnd();
			}
			instantiation_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_10);
			} else {
			  throw ex;
			}
		}
		returnAST = instantiation_AST;
	}
	
	public final void parameter_override() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST parameter_override_AST = null;
		
		try {      // for error handling
			AST tmp32_AST = null;
			tmp32_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp32_AST);
			match(LITERAL_defparam);
			list_of_param_assignments();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp33_AST = null;
			tmp33_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp33_AST);
			match(SEMI);
			if ( inputState.guessing==0 ) {
				parameter_override_AST = (AST)currentAST.root;
				parameter_override_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(PARAMETEROVERRIDE,"parameter_override")).add(parameter_override_AST));
				currentAST.root = parameter_override_AST;
				currentAST.child = parameter_override_AST!=null &&parameter_override_AST.getFirstChild()!=null ?
					parameter_override_AST.getFirstChild() : parameter_override_AST;
				currentAST.advanceChildToEnd();
			}
			parameter_override_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_10);
			} else {
			  throw ex;
			}
		}
		returnAST = parameter_override_AST;
	}
	
	public final void continuous_assign() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST continuous_assign_AST = null;
		
		try {      // for error handling
			AST tmp34_AST = null;
			tmp34_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp34_AST);
			match(LITERAL_assign);
			drive_strengthQ();
			astFactory.addASTChild(currentAST, returnAST);
			delayQ();
			astFactory.addASTChild(currentAST, returnAST);
			list_of_assignments();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp35_AST = null;
			tmp35_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp35_AST);
			match(SEMI);
			if ( inputState.guessing==0 ) {
				continuous_assign_AST = (AST)currentAST.root;
				continuous_assign_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(CONTINUOUS_ASSIGN,"Continuous_ASSIGN")).add(continuous_assign_AST));
				currentAST.root = continuous_assign_AST;
				currentAST.child = continuous_assign_AST!=null &&continuous_assign_AST.getFirstChild()!=null ?
					continuous_assign_AST.getFirstChild() : continuous_assign_AST;
				currentAST.advanceChildToEnd();
			}
			continuous_assign_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_10);
			} else {
			  throw ex;
			}
		}
		returnAST = continuous_assign_AST;
	}
	
	public final void specify_block() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST specify_block_AST = null;
		
		try {      // for error handling
			AST tmp36_AST = null;
			tmp36_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp36_AST);
			match(LITERAL_specify);
			specify_entries();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp37_AST = null;
			tmp37_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp37_AST);
			match(LITERAL_endspecify);
			if ( inputState.guessing==0 ) {
				specify_block_AST = (AST)currentAST.root;
				specify_block_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(SPECIFYBLOCK,"SpecifyBlock")).add(specify_block_AST));
				currentAST.root = specify_block_AST;
				currentAST.child = specify_block_AST!=null &&specify_block_AST.getFirstChild()!=null ?
					specify_block_AST.getFirstChild() : specify_block_AST;
				currentAST.advanceChildToEnd();
			}
			specify_block_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_10);
			} else {
			  throw ex;
			}
		}
		returnAST = specify_block_AST;
	}
	
	public final void initial_statement() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST initial_statement_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case LITERAL_initial:
			{
				AST tmp38_AST = null;
				tmp38_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp38_AST);
				match(LITERAL_initial);
				break;
			}
			case LITERAL_final:
			{
				AST tmp39_AST = null;
				tmp39_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp39_AST);
				match(LITERAL_final);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			statement();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				initial_statement_AST = (AST)currentAST.root;
				initial_statement_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(INITIALORFINAL,"InitialFinal")).add(initial_statement_AST));
				currentAST.root = initial_statement_AST;
				currentAST.child = initial_statement_AST!=null &&initial_statement_AST.getFirstChild()!=null ?
					initial_statement_AST.getFirstChild() : initial_statement_AST;
				currentAST.advanceChildToEnd();
			}
			initial_statement_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_10);
			} else {
			  throw ex;
			}
		}
		returnAST = initial_statement_AST;
	}
	
	public final void always_construct() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST always_construct_AST = null;
		
		try {      // for error handling
			always_keyword();
			astFactory.addASTChild(currentAST, returnAST);
			always_head();
			astFactory.addASTChild(currentAST, returnAST);
			statement();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				always_construct_AST = (AST)currentAST.root;
				always_construct_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(ALWAYSTOP,"AlwaysTop")).add(always_construct_AST));
				currentAST.root = always_construct_AST;
				currentAST.child = always_construct_AST!=null &&always_construct_AST.getFirstChild()!=null ?
					always_construct_AST.getFirstChild() : always_construct_AST;
				currentAST.advanceChildToEnd();
			}
			always_construct_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_10);
			} else {
			  throw ex;
			}
		}
		returnAST = always_construct_AST;
	}
	
/********
 ******** Start of commented out rules related to table entries.
 ********

combinational_entry :level_input_list COLON output_symbol SEMI;
sequential_entry :input_list COLON state COLON next_state SEMI;

input_list :
	(level_input_list) => level_input_list |
	edge_input_list
	;

level_input_list :
	(level_symbol)+
	;

edge_input_list :
	(level_symbol)* edge (level_symbol)*
	;

edge :
	LPAREN level_symbol level_symbol RPAREN |
	edge_symbol
	;

state :
	level_symbol
	;

next_state :
	output_symbol |
	MINUS
	;

   // Next 3 rules use semantic predicates to determine whether a matched
   // NUMBER or IDENTIFIER is a valid special value in the given context.
   // This kludge avoids having the special values in the Literals table,
   // thus avoiding a lexical conflict.
output_symbol:
	n:NUMBER
	{ n.getText()=="0" || n.getText()=="1"}?
      |
	i:IDENTIFIER
	{ i.getText()=="x" || i.getText()=="X"}?
	;

level_symbol:
	QUESTION
      |
	n:NUMBER
	{ n.getText()=="0" || n.getText()=="1"}?
      |
	i:IDENTIFIER
	{ i.getText()=="x" || i.getText()=="X" ||
	  i.getText()=="b" || i.getText()=="B" }?
	;

edge_symbol:
	STAR
      |
	i:IDENTIFIER
	{ i->getText()=="r" || i->getText()=="R" ||
	  i->getText()=="f" || i->getText()=="F" ||
	  i->getText()=="p" || i->getText()=="P" ||
	  i->getText()=="n" || i->getText()=="N" }?
	;

 ********
 ******** End of commented out rules related to table entries.
 ********/
	public final void task_declaration() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST task_declaration_AST = null;
		
		try {      // for error handling
			AST tmp40_AST = null;
			tmp40_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp40_AST);
			match(LITERAL_task);
			name_of_task();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp41_AST = null;
			tmp41_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp41_AST);
			match(SEMI);
			task_var_declaration();
			astFactory.addASTChild(currentAST, returnAST);
			statement_or_null();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp42_AST = null;
			tmp42_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp42_AST);
			match(LITERAL_endtask);
			if ( inputState.guessing==0 ) {
				task_declaration_AST = (AST)currentAST.root;
				task_declaration_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(TASK,"task")).add(task_declaration_AST));
				currentAST.root = task_declaration_AST;
				currentAST.child = task_declaration_AST!=null &&task_declaration_AST.getFirstChild()!=null ?
					task_declaration_AST.getFirstChild() : task_declaration_AST;
				currentAST.advanceChildToEnd();
			}
			task_declaration_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_10);
			} else {
			  throw ex;
			}
		}
		returnAST = task_declaration_AST;
	}
	
	public final void function_declaration() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST function_declaration_AST = null;
		
		try {      // for error handling
			AST tmp43_AST = null;
			tmp43_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp43_AST);
			match(LITERAL_function);
			automaticq();
			astFactory.addASTChild(currentAST, returnAST);
			range_or_typeq();
			astFactory.addASTChild(currentAST, returnAST);
			name_of_function();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp44_AST = null;
			tmp44_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp44_AST);
			match(SEMI);
			function_var_declaration();
			astFactory.addASTChild(currentAST, returnAST);
			statement();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp45_AST = null;
			tmp45_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp45_AST);
			match(LITERAL_endfunction);
			if ( inputState.guessing==0 ) {
				function_declaration_AST = (AST)currentAST.root;
				function_declaration_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(FUNCTION,"function_declaration")).add(function_declaration_AST));
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
				recover(ex,_tokenSet_10);
			} else {
			  throw ex;
			}
		}
		returnAST = function_declaration_AST;
	}
	
	public final void init_val() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST init_val_AST = null;
		Token  n = null;
		AST n_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case 134:
			{
				AST tmp46_AST = null;
				tmp46_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp46_AST);
				match(134);
				init_val_AST = (AST)currentAST.root;
				break;
			}
			case 135:
			{
				AST tmp47_AST = null;
				tmp47_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp47_AST);
				match(135);
				init_val_AST = (AST)currentAST.root;
				break;
			}
			case 136:
			{
				AST tmp48_AST = null;
				tmp48_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp48_AST);
				match(136);
				init_val_AST = (AST)currentAST.root;
				break;
			}
			case NUMBER:
			{
				n = LT(1);
				n_AST = astFactory.create(n);
				astFactory.addASTChild(currentAST, n_AST);
				match(NUMBER);
				if (!( n.getText()=="0" || n.getText()=="1"))
				  throw new SemanticException(" n.getText()==\"0\" || n.getText()==\"1\"");
				init_val_AST = (AST)currentAST.root;
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
		returnAST = init_val_AST;
	}
	
	public final void primitive_definition() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST primitive_definition_AST = null;
		
		try {      // for error handling
			AST tmp49_AST = null;
			tmp49_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp49_AST);
			match(LITERAL_primitive);
			primitive_entries();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp50_AST = null;
			tmp50_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp50_AST);
			match(LITERAL_endprimitive);
			if ( inputState.guessing==0 ) {
				primitive_definition_AST = (AST)currentAST.root;
				primitive_definition_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(PRIMITIVEDEFINITION,"primitive_definition")).add(primitive_definition_AST));
				currentAST.root = primitive_definition_AST;
				currentAST.child = primitive_definition_AST!=null &&primitive_definition_AST.getFirstChild()!=null ?
					primitive_definition_AST.getFirstChild() : primitive_definition_AST;
				currentAST.advanceChildToEnd();
			}
			primitive_definition_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_0);
			} else {
			  throw ex;
			}
		}
		returnAST = primitive_definition_AST;
	}
	
	public final void primitive_entries() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST primitive_entries_AST = null;
		
		try {      // for error handling
			{
			int _cnt43=0;
			_loop43:
			do {
				if ((_tokenSet_22.member(LA(1)))) {
					{
					AST tmp51_AST = null;
					tmp51_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp51_AST);
					match(_tokenSet_22);
					}
				}
				else {
					if ( _cnt43>=1 ) { break _loop43; } else {throw new NoViableAltException(LT(1), getFilename());}
				}
				
				_cnt43++;
			} while (true);
			}
			primitive_entries_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_23);
			} else {
			  throw ex;
			}
		}
		returnAST = primitive_entries_AST;
	}
	
	public final void table_definition() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST table_definition_AST = null;
		
		try {      // for error handling
			AST tmp52_AST = null;
			tmp52_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp52_AST);
			match(LITERAL_table);
			table_entries();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp53_AST = null;
			tmp53_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp53_AST);
			match(LITERAL_endtable);
			if ( inputState.guessing==0 ) {
				table_definition_AST = (AST)currentAST.root;
				table_definition_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(TABLEDEFINITION,"table_definition")).add(table_definition_AST));
				currentAST.root = table_definition_AST;
				currentAST.child = table_definition_AST!=null &&table_definition_AST.getFirstChild()!=null ?
					table_definition_AST.getFirstChild() : table_definition_AST;
				currentAST.advanceChildToEnd();
			}
			table_definition_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_0);
			} else {
			  throw ex;
			}
		}
		returnAST = table_definition_AST;
	}
	
	public final void table_entries() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST table_entries_AST = null;
		
		try {      // for error handling
			{
			_loop49:
			do {
				if ((_tokenSet_24.member(LA(1)))) {
					{
					int _cnt48=0;
					_loop48:
					do {
						if ((_tokenSet_24.member(LA(1)))) {
							{
							AST tmp54_AST = null;
							tmp54_AST = astFactory.create(LT(1));
							astFactory.addASTChild(currentAST, tmp54_AST);
							match(_tokenSet_24);
							}
						}
						else {
							if ( _cnt48>=1 ) { break _loop48; } else {throw new NoViableAltException(LT(1), getFilename());}
						}
						
						_cnt48++;
					} while (true);
					}
					AST tmp55_AST = null;
					tmp55_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp55_AST);
					match(SEMI);
				}
				else {
					break _loop49;
				}
				
			} while (true);
			}
			table_entries_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_25);
			} else {
			  throw ex;
			}
		}
		returnAST = table_entries_AST;
	}
	
	public final void name_of_task() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST name_of_task_AST = null;
		
		try {      // for error handling
			name();
			astFactory.addASTChild(currentAST, returnAST);
			name_of_task_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_8);
			} else {
			  throw ex;
			}
		}
		returnAST = name_of_task_AST;
	}
	
	public final void task_var_declaration() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST task_var_declaration_AST = null;
		
		try {      // for error handling
			{
			_loop58:
			do {
				if ((LA(1)==LITERAL_QQQQQDDDDDEEEEEEEFFFFFFFf)) {
					variable_declaration();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop58;
				}
				
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				task_var_declaration_AST = (AST)currentAST.root;
				task_var_declaration_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(TASKVARDECLARATION,"task_var_declaration")).add(task_var_declaration_AST));
				currentAST.root = task_var_declaration_AST;
				currentAST.child = task_var_declaration_AST!=null &&task_var_declaration_AST.getFirstChild()!=null ?
					task_var_declaration_AST.getFirstChild() : task_var_declaration_AST;
				currentAST.advanceChildToEnd();
			}
			task_var_declaration_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_26);
			} else {
			  throw ex;
			}
		}
		returnAST = task_var_declaration_AST;
	}
	
	public final void statement_or_null() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST statement_or_null_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_assign:
			case POUND:
			case LITERAL_if:
			case LITERAL_unique:
			case LITERAL_priority:
			case LITERAL_case:
			case LITERAL_casez:
			case LITERAL_casex:
			case LITERAL_forever:
			case LITERAL_repeat:
			case LITERAL_while:
			case LITERAL_for:
			case LITERAL_wait:
			case TRIGGER:
			case LITERAL_disable:
			case LITERAL_begin:
			case LITERAL_fork:
			case LITERAL_deassign:
			case LITERAL_force:
			case LITERAL_release:
			case IDENTIFIER:
			case LCURLY:
			case ESCAPED_IDENTIFIER:
			case DOLLAR_IDENTIFIER:
			case AT:
			{
				statement();
				astFactory.addASTChild(currentAST, returnAST);
				statement_or_null_AST = (AST)currentAST.root;
				break;
			}
			case SEMI:
			{
				AST tmp56_AST = null;
				tmp56_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp56_AST);
				match(SEMI);
				statement_or_null_AST = (AST)currentAST.root;
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
		returnAST = statement_or_null_AST;
	}
	
	public final void automaticq() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST automaticq_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case LITERAL_automatic:
			{
				AST tmp57_AST = null;
				tmp57_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp57_AST);
				match(LITERAL_automatic);
				break;
			}
			case LBRACK:
			case LITERAL_integer:
			case LITERAL_real:
			case IDENTIFIER:
			case ESCAPED_IDENTIFIER:
			case DOLLAR_IDENTIFIER:
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
				automaticq_AST = (AST)currentAST.root;
				automaticq_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(AUTOMATICQ,"automatic_q")).add(automaticq_AST));
				currentAST.root = automaticq_AST;
				currentAST.child = automaticq_AST!=null &&automaticq_AST.getFirstChild()!=null ?
					automaticq_AST.getFirstChild() : automaticq_AST;
				currentAST.advanceChildToEnd();
			}
			automaticq_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_28);
			} else {
			  throw ex;
			}
		}
		returnAST = automaticq_AST;
	}
	
	public final void range_or_typeq() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST range_or_typeq_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case LBRACK:
			case LITERAL_integer:
			case LITERAL_real:
			{
				range_or_type();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case IDENTIFIER:
			case ESCAPED_IDENTIFIER:
			case DOLLAR_IDENTIFIER:
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
				range_or_typeq_AST = (AST)currentAST.root;
				range_or_typeq_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(RANGEORTYPEQ,"range_or_typeQ")).add(range_or_typeq_AST));
				currentAST.root = range_or_typeq_AST;
				currentAST.child = range_or_typeq_AST!=null &&range_or_typeq_AST.getFirstChild()!=null ?
					range_or_typeq_AST.getFirstChild() : range_or_typeq_AST;
				currentAST.advanceChildToEnd();
			}
			range_or_typeq_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_29);
			} else {
			  throw ex;
			}
		}
		returnAST = range_or_typeq_AST;
	}
	
	public final void name_of_function() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST name_of_function_AST = null;
		
		try {      // for error handling
			identifier();
			astFactory.addASTChild(currentAST, returnAST);
			name_of_function_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_9);
			} else {
			  throw ex;
			}
		}
		returnAST = name_of_function_AST;
	}
	
	public final void function_var_declaration() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST function_var_declaration_AST = null;
		
		try {      // for error handling
			{
			_loop61:
			do {
				if ((LA(1)==LITERAL_QQQQQDDDDDEEEEEEEFFFFFFFf)) {
					variable_declaration();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop61;
				}
				
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				function_var_declaration_AST = (AST)currentAST.root;
				function_var_declaration_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(FUNCTIONVARDECLARATION,"function_var_declaration")).add(function_var_declaration_AST));
				currentAST.root = function_var_declaration_AST;
				currentAST.child = function_var_declaration_AST!=null &&function_var_declaration_AST.getFirstChild()!=null ?
					function_var_declaration_AST.getFirstChild() : function_var_declaration_AST;
				currentAST.advanceChildToEnd();
			}
			function_var_declaration_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_30);
			} else {
			  throw ex;
			}
		}
		returnAST = function_var_declaration_AST;
	}
	
	public final void statement() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST statement_AST = null;
		
		try {      // for error handling
			boolean synPredMatched165 = false;
			if (((LA(1)==LITERAL_if||LA(1)==LITERAL_unique||LA(1)==LITERAL_priority) && (LA(2)==LPAREN||LA(2)==LITERAL_if))) {
				int _m165 = mark();
				synPredMatched165 = true;
				inputState.guessing++;
				try {
					{
					conditional_statement();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched165 = false;
				}
				rewind(_m165);
inputState.guessing--;
			}
			if ( synPredMatched165 ) {
				conditional_statement();
				astFactory.addASTChild(currentAST, returnAST);
				statement_AST = (AST)currentAST.root;
			}
			else if ((_tokenSet_31.member(LA(1))) && (_tokenSet_32.member(LA(2)))) {
				statement_no_condition();
				astFactory.addASTChild(currentAST, returnAST);
				statement_AST = (AST)currentAST.root;
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
		returnAST = statement_AST;
	}
	
	public final void range_or_type() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST range_or_type_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case LBRACK:
			{
				range();
				astFactory.addASTChild(currentAST, returnAST);
				range_or_type_AST = (AST)currentAST.root;
				break;
			}
			case LITERAL_integer:
			{
				AST tmp58_AST = null;
				tmp58_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp58_AST);
				match(LITERAL_integer);
				range_or_type_AST = (AST)currentAST.root;
				break;
			}
			case LITERAL_real:
			{
				AST tmp59_AST = null;
				tmp59_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp59_AST);
				match(LITERAL_real);
				if ( inputState.guessing==0 ) {
					range_or_type_AST = (AST)currentAST.root;
					range_or_type_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(RANGEORTYPE,"range_or_type")).add(range_or_type_AST));
					currentAST.root = range_or_type_AST;
					currentAST.child = range_or_type_AST!=null &&range_or_type_AST.getFirstChild()!=null ?
						range_or_type_AST.getFirstChild() : range_or_type_AST;
					currentAST.advanceChildToEnd();
				}
				range_or_type_AST = (AST)currentAST.root;
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
				recover(ex,_tokenSet_29);
			} else {
			  throw ex;
			}
		}
		returnAST = range_or_type_AST;
	}
	
/** Crap methods which are overriden by systemverilog */
	public final void variable_declaration() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST variable_declaration_AST = null;
		
		try {      // for error handling
			AST tmp60_AST = null;
			tmp60_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp60_AST);
			match(LITERAL_QQQQQDDDDDEEEEEEEFFFFFFFf);
			{
			switch ( LA(1)) {
			case LBRACK:
			{
				range();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case IDENTIFIER:
			case ESCAPED_IDENTIFIER:
			case DOLLAR_IDENTIFIER:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			list_of_variables();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp61_AST = null;
			tmp61_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp61_AST);
			match(SEMI);
			variable_declaration_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_33);
			} else {
			  throw ex;
			}
		}
		returnAST = variable_declaration_AST;
	}
	
	public final void list_of_variables() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST list_of_variables_AST = null;
		
		try {      // for error handling
			variable_name();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop72:
			do {
				if ((LA(1)==COMMA)) {
					AST tmp62_AST = null;
					tmp62_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp62_AST);
					match(COMMA);
					variable_name();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop72;
				}
				
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				list_of_variables_AST = (AST)currentAST.root;
				list_of_variables_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(VARLIST,"VarList")).add(list_of_variables_AST));
				currentAST.root = list_of_variables_AST;
				currentAST.child = list_of_variables_AST!=null &&list_of_variables_AST.getFirstChild()!=null ?
					list_of_variables_AST.getFirstChild() : list_of_variables_AST;
				currentAST.advanceChildToEnd();
			}
			list_of_variables_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_9);
			} else {
			  throw ex;
			}
		}
		returnAST = list_of_variables_AST;
	}
	
	public final void io_declaration() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST io_declaration_AST = null;
		
		try {      // for error handling
			AST tmp63_AST = null;
			tmp63_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp63_AST);
			match(LITERAL_PPPPPQQDDDDDEEEEEEEFFFFFFFf);
			{
			switch ( LA(1)) {
			case LBRACK:
			{
				range();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case IDENTIFIER:
			case ESCAPED_IDENTIFIER:
			case DOLLAR_IDENTIFIER:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			list_of_variables();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp64_AST = null;
			tmp64_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp64_AST);
			match(SEMI);
			io_declaration_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_0);
			} else {
			  throw ex;
			}
		}
		returnAST = io_declaration_AST;
	}
	
/** End of Crap Methods */
	public final void list_of_param_assignments() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST list_of_param_assignments_AST = null;
		
		try {      // for error handling
			param_assign();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop69:
			do {
				if ((LA(1)==COMMA)) {
					AST tmp65_AST = null;
					tmp65_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp65_AST);
					match(COMMA);
					param_assign();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop69;
				}
				
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				list_of_param_assignments_AST = (AST)currentAST.root;
				list_of_param_assignments_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(VARLIST,"VarList")).add(list_of_param_assignments_AST));
				currentAST.root = list_of_param_assignments_AST;
				currentAST.child = list_of_param_assignments_AST!=null &&list_of_param_assignments_AST.getFirstChild()!=null ?
					list_of_param_assignments_AST.getFirstChild() : list_of_param_assignments_AST;
				currentAST.advanceChildToEnd();
			}
			list_of_param_assignments_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_9);
			} else {
			  throw ex;
			}
		}
		returnAST = list_of_param_assignments_AST;
	}
	
	public final void param_assign() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST param_assign_AST = null;
		
		try {      // for error handling
			param_assignment();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				param_assign_AST = (AST)currentAST.root;
				param_assign_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(PARASSIGN,"ParAssign")).add(param_assign_AST));
				currentAST.root = param_assign_AST;
				currentAST.child = param_assign_AST!=null &&param_assign_AST.getFirstChild()!=null ?
					param_assign_AST.getFirstChild() : param_assign_AST;
				currentAST.advanceChildToEnd();
			}
			param_assign_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_34);
			} else {
			  throw ex;
			}
		}
		returnAST = param_assign_AST;
	}
	
	public final void variable_name() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST variable_name_AST = null;
		
		try {      // for error handling
			name_of_variable();
			astFactory.addASTChild(currentAST, returnAST);
			variable_name_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_35);
			} else {
			  throw ex;
			}
		}
		returnAST = variable_name_AST;
	}
	
	public final void list_of_register_variables() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST list_of_register_variables_AST = null;
		
		try {      // for error handling
			register_variable();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop75:
			do {
				if ((LA(1)==COMMA)) {
					AST tmp66_AST = null;
					tmp66_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp66_AST);
					match(COMMA);
					register_variable();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop75;
				}
				
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				list_of_register_variables_AST = (AST)currentAST.root;
				list_of_register_variables_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(VARLIST,"VarList")).add(list_of_register_variables_AST));
				currentAST.root = list_of_register_variables_AST;
				currentAST.child = list_of_register_variables_AST!=null &&list_of_register_variables_AST.getFirstChild()!=null ?
					list_of_register_variables_AST.getFirstChild() : list_of_register_variables_AST;
				currentAST.advanceChildToEnd();
			}
			list_of_register_variables_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_0);
			} else {
			  throw ex;
			}
		}
		returnAST = list_of_register_variables_AST;
	}
	
	public final void register_variable() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST register_variable_AST = null;
		
		try {      // for error handling
			variable_name();
			astFactory.addASTChild(currentAST, returnAST);
			{
			switch ( LA(1)) {
			case LBRACK:
			{
				register_memory_array();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case EOF:
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
				register_variable_AST = (AST)currentAST.root;
				register_variable_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(REGMEM,"RegMem")).add(register_variable_AST));
				currentAST.root = register_variable_AST;
				currentAST.child = register_variable_AST!=null &&register_variable_AST.getFirstChild()!=null ?
					register_variable_AST.getFirstChild() : register_variable_AST;
				currentAST.advanceChildToEnd();
			}
			register_variable_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_36);
			} else {
			  throw ex;
			}
		}
		returnAST = register_variable_AST;
	}
	
	public final void param_assignment() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST param_assignment_AST = null;
		
		try {      // for error handling
			variable_name();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp67_AST = null;
			tmp67_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp67_AST);
			match(ASSIGN);
			number();
			astFactory.addASTChild(currentAST, returnAST);
			param_assignment_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_34);
			} else {
			  throw ex;
			}
		}
		returnAST = param_assignment_AST;
	}
	
	public final void number() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST number_AST = null;
		
		try {      // for error handling
			AST tmp68_AST = null;
			tmp68_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp68_AST);
			match(NUMBER);
			number_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_37);
			} else {
			  throw ex;
			}
		}
		returnAST = number_AST;
	}
	
	public final void register_memory_array() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST register_memory_array_AST = null;
		
		try {      // for error handling
			AST tmp69_AST = null;
			tmp69_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp69_AST);
			match(LBRACK);
			expression();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp70_AST = null;
			tmp70_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp70_AST);
			match(COLON);
			expression();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp71_AST = null;
			tmp71_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp71_AST);
			match(RBRACK);
			if ( inputState.guessing==0 ) {
				register_memory_array_AST = (AST)currentAST.root;
				register_memory_array_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(REGMEMARRAY,"RegMemArray")).add(register_memory_array_AST));
				currentAST.root = register_memory_array_AST;
				currentAST.child = register_memory_array_AST!=null &&register_memory_array_AST.getFirstChild()!=null ?
					register_memory_array_AST.getFirstChild() : register_memory_array_AST;
				currentAST.advanceChildToEnd();
			}
			register_memory_array_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_36);
			} else {
			  throw ex;
			}
		}
		returnAST = register_memory_array_AST;
	}
	
	public final void net_type() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST net_type_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_tri:
			{
				AST tmp72_AST = null;
				tmp72_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp72_AST);
				match(LITERAL_tri);
				net_type_AST = (AST)currentAST.root;
				break;
			}
			case 153:
			{
				AST tmp73_AST = null;
				tmp73_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp73_AST);
				match(153);
				net_type_AST = (AST)currentAST.root;
				break;
			}
			case 154:
			{
				AST tmp74_AST = null;
				tmp74_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp74_AST);
				match(154);
				net_type_AST = (AST)currentAST.root;
				break;
			}
			case LITERAL_wand:
			{
				AST tmp75_AST = null;
				tmp75_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp75_AST);
				match(LITERAL_wand);
				net_type_AST = (AST)currentAST.root;
				break;
			}
			case LITERAL_triand:
			{
				AST tmp76_AST = null;
				tmp76_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp76_AST);
				match(LITERAL_triand);
				net_type_AST = (AST)currentAST.root;
				break;
			}
			case 157:
			{
				AST tmp77_AST = null;
				tmp77_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp77_AST);
				match(157);
				net_type_AST = (AST)currentAST.root;
				break;
			}
			case 158:
			{
				AST tmp78_AST = null;
				tmp78_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp78_AST);
				match(158);
				net_type_AST = (AST)currentAST.root;
				break;
			}
			case LITERAL_wor:
			{
				AST tmp79_AST = null;
				tmp79_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp79_AST);
				match(LITERAL_wor);
				net_type_AST = (AST)currentAST.root;
				break;
			}
			case LITERAL_trior:
			{
				AST tmp80_AST = null;
				tmp80_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp80_AST);
				match(LITERAL_trior);
				net_type_AST = (AST)currentAST.root;
				break;
			}
			case LITERAL_trireg:
			{
				AST tmp81_AST = null;
				tmp81_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp81_AST);
				match(LITERAL_trireg);
				net_type_AST = (AST)currentAST.root;
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
		returnAST = net_type_AST;
	}
	
	public final void expandrange() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST expandrange_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_scalared:
			{
				AST tmp82_AST = null;
				tmp82_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp82_AST);
				match(LITERAL_scalared);
				range();
				astFactory.addASTChild(currentAST, returnAST);
				expandrange_AST = (AST)currentAST.root;
				break;
			}
			case LITERAL_vectored:
			{
				AST tmp83_AST = null;
				tmp83_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp83_AST);
				match(LITERAL_vectored);
				range();
				astFactory.addASTChild(currentAST, returnAST);
				expandrange_AST = (AST)currentAST.root;
				break;
			}
			case LBRACK:
			{
				range();
				astFactory.addASTChild(currentAST, returnAST);
				expandrange_AST = (AST)currentAST.root;
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
		returnAST = expandrange_AST;
	}
	
	public final void drive_strengthQ() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST drive_strengthQ_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case LPAREN:
			{
				drive_strength();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case POUND:
			case IDENTIFIER:
			case LCURLY:
			case ESCAPED_IDENTIFIER:
			case DOLLAR_IDENTIFIER:
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
				drive_strengthQ_AST = (AST)currentAST.root;
				drive_strengthQ_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(DRIVESTRENGTHQ,"DriveStrengthQ")).add(drive_strengthQ_AST));
				currentAST.root = drive_strengthQ_AST;
				currentAST.child = drive_strengthQ_AST!=null &&drive_strengthQ_AST.getFirstChild()!=null ?
					drive_strengthQ_AST.getFirstChild() : drive_strengthQ_AST;
				currentAST.advanceChildToEnd();
			}
			drive_strengthQ_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_38);
			} else {
			  throw ex;
			}
		}
		returnAST = drive_strengthQ_AST;
	}
	
	public final void delayQ() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST delayQ_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case POUND:
			{
				delay();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case IDENTIFIER:
			case LCURLY:
			case ESCAPED_IDENTIFIER:
			case DOLLAR_IDENTIFIER:
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
				delayQ_AST = (AST)currentAST.root;
				delayQ_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(DELAYQ,"delayQ")).add(delayQ_AST));
				currentAST.root = delayQ_AST;
				currentAST.child = delayQ_AST!=null &&delayQ_AST.getFirstChild()!=null ?
					delayQ_AST.getFirstChild() : delayQ_AST;
				currentAST.advanceChildToEnd();
			}
			delayQ_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_39);
			} else {
			  throw ex;
			}
		}
		returnAST = delayQ_AST;
	}
	
	public final void list_of_assignments() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST list_of_assignments_AST = null;
		
		try {      // for error handling
			assignment();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop91:
			do {
				if ((LA(1)==COMMA)) {
					AST tmp84_AST = null;
					tmp84_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp84_AST);
					match(COMMA);
					assignment();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop91;
				}
				
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				list_of_assignments_AST = (AST)currentAST.root;
				list_of_assignments_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(ASSIGNLIST,"AssignList")).add(list_of_assignments_AST));
				currentAST.root = list_of_assignments_AST;
				currentAST.child = list_of_assignments_AST!=null &&list_of_assignments_AST.getFirstChild()!=null ?
					list_of_assignments_AST.getFirstChild() : list_of_assignments_AST;
				currentAST.advanceChildToEnd();
			}
			list_of_assignments_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_9);
			} else {
			  throw ex;
			}
		}
		returnAST = list_of_assignments_AST;
	}
	
	public final void drive_strength() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST drive_strength_AST = null;
		
		try {      // for error handling
			if ((LA(1)==LPAREN) && (_tokenSet_40.member(LA(2)))) {
				AST tmp85_AST = null;
				tmp85_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp85_AST);
				match(LPAREN);
				strength0();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp86_AST = null;
				tmp86_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp86_AST);
				match(COMMA);
				strength1();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp87_AST = null;
				tmp87_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp87_AST);
				match(RPAREN);
				drive_strength_AST = (AST)currentAST.root;
			}
			else if ((LA(1)==LPAREN) && (_tokenSet_41.member(LA(2)))) {
				AST tmp88_AST = null;
				tmp88_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp88_AST);
				match(LPAREN);
				strength1();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp89_AST = null;
				tmp89_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp89_AST);
				match(COMMA);
				strength0();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp90_AST = null;
				tmp90_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp90_AST);
				match(RPAREN);
				drive_strength_AST = (AST)currentAST.root;
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
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
		returnAST = drive_strength_AST;
	}
	
	public final void delay() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST delay_AST = null;
		
		try {      // for error handling
			if ((LA(1)==POUND) && (LA(2)==NUMBER)) {
				AST tmp91_AST = null;
				tmp91_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp91_AST);
				match(POUND);
				number();
				astFactory.addASTChild(currentAST, returnAST);
				delay_AST = (AST)currentAST.root;
			}
			else if ((LA(1)==POUND) && (LA(2)==IDENTIFIER||LA(2)==ESCAPED_IDENTIFIER||LA(2)==DOLLAR_IDENTIFIER)) {
				AST tmp92_AST = null;
				tmp92_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp92_AST);
				match(POUND);
				identifier();
				astFactory.addASTChild(currentAST, returnAST);
				delay_AST = (AST)currentAST.root;
			}
			else if ((LA(1)==POUND) && (LA(2)==LPAREN)) {
				AST tmp93_AST = null;
				tmp93_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp93_AST);
				match(POUND);
				AST tmp94_AST = null;
				tmp94_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp94_AST);
				match(LPAREN);
				mintypmax_expression();
				astFactory.addASTChild(currentAST, returnAST);
				{
				switch ( LA(1)) {
				case COMMA:
				{
					AST tmp95_AST = null;
					tmp95_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp95_AST);
					match(COMMA);
					mintypmax_expression();
					astFactory.addASTChild(currentAST, returnAST);
					{
					switch ( LA(1)) {
					case COMMA:
					{
						AST tmp96_AST = null;
						tmp96_AST = astFactory.create(LT(1));
						astFactory.addASTChild(currentAST, tmp96_AST);
						match(COMMA);
						mintypmax_expression();
						astFactory.addASTChild(currentAST, returnAST);
						break;
					}
					case RPAREN:
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
				case RPAREN:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				AST tmp97_AST = null;
				tmp97_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp97_AST);
				match(RPAREN);
				delay_AST = (AST)currentAST.root;
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_43);
			} else {
			  throw ex;
			}
		}
		returnAST = delay_AST;
	}
	
	public final void assignment() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST assignment_AST = null;
		
		try {      // for error handling
			lvalue();
			astFactory.addASTChild(currentAST, returnAST);
			assign_op();
			astFactory.addASTChild(currentAST, returnAST);
			expression();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				assignment_AST = (AST)currentAST.root;
				assignment_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(ASSIGNMENT,"Assignment")).add(assignment_AST));
				currentAST.root = assignment_AST;
				currentAST.child = assignment_AST!=null &&assignment_AST.getFirstChild()!=null ?
					assignment_AST.getFirstChild() : assignment_AST;
				currentAST.advanceChildToEnd();
			}
			assignment_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_44);
			} else {
			  throw ex;
			}
		}
		returnAST = assignment_AST;
	}
	
	public final void list_of_assigned_variables() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST list_of_assigned_variables_AST = null;
		
		try {      // for error handling
			name_of_variable();
			astFactory.addASTChild(currentAST, returnAST);
			{
			switch ( LA(1)) {
			case ASSIGN:
			{
				AST tmp98_AST = null;
				tmp98_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp98_AST);
				match(ASSIGN);
				expression();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case EOF:
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
					AST tmp99_AST = null;
					tmp99_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp99_AST);
					match(COMMA);
					name_of_variable();
					astFactory.addASTChild(currentAST, returnAST);
					{
					switch ( LA(1)) {
					case ASSIGN:
					{
						AST tmp100_AST = null;
						tmp100_AST = astFactory.create(LT(1));
						astFactory.addASTChild(currentAST, tmp100_AST);
						match(ASSIGN);
						expression();
						astFactory.addASTChild(currentAST, returnAST);
						break;
					}
					case EOF:
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
				}
				else {
					break _loop97;
				}
				
			} while (true);
			}
			list_of_assigned_variables_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_0);
			} else {
			  throw ex;
			}
		}
		returnAST = list_of_assigned_variables_AST;
	}
	
	public final void charge_strength() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST charge_strength_AST = null;
		
		try {      // for error handling
			if ((LA(1)==LPAREN) && (LA(2)==LITERAL_small)) {
				AST tmp101_AST = null;
				tmp101_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp101_AST);
				match(LPAREN);
				AST tmp102_AST = null;
				tmp102_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp102_AST);
				match(LITERAL_small);
				AST tmp103_AST = null;
				tmp103_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp103_AST);
				match(RPAREN);
				charge_strength_AST = (AST)currentAST.root;
			}
			else if ((LA(1)==LPAREN) && (LA(2)==LITERAL_medium)) {
				AST tmp104_AST = null;
				tmp104_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp104_AST);
				match(LPAREN);
				AST tmp105_AST = null;
				tmp105_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp105_AST);
				match(LITERAL_medium);
				AST tmp106_AST = null;
				tmp106_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp106_AST);
				match(RPAREN);
				charge_strength_AST = (AST)currentAST.root;
			}
			else if ((LA(1)==LPAREN) && (LA(2)==LITERAL_large)) {
				AST tmp107_AST = null;
				tmp107_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp107_AST);
				match(LPAREN);
				AST tmp108_AST = null;
				tmp108_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp108_AST);
				match(LITERAL_large);
				AST tmp109_AST = null;
				tmp109_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp109_AST);
				match(RPAREN);
				charge_strength_AST = (AST)currentAST.root;
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
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
		returnAST = charge_strength_AST;
	}
	
	public final void strength0() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST strength0_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case 154:
			{
				AST tmp110_AST = null;
				tmp110_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp110_AST);
				match(154);
				strength0_AST = (AST)currentAST.root;
				break;
			}
			case 169:
			{
				AST tmp111_AST = null;
				tmp111_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp111_AST);
				match(169);
				strength0_AST = (AST)currentAST.root;
				break;
			}
			case 170:
			{
				AST tmp112_AST = null;
				tmp112_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp112_AST);
				match(170);
				strength0_AST = (AST)currentAST.root;
				break;
			}
			case 171:
			{
				AST tmp113_AST = null;
				tmp113_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp113_AST);
				match(171);
				strength0_AST = (AST)currentAST.root;
				break;
			}
			case 172:
			{
				AST tmp114_AST = null;
				tmp114_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp114_AST);
				match(172);
				strength0_AST = (AST)currentAST.root;
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
				recover(ex,_tokenSet_12);
			} else {
			  throw ex;
			}
		}
		returnAST = strength0_AST;
	}
	
	public final void strength1() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST strength1_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case 158:
			{
				AST tmp115_AST = null;
				tmp115_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp115_AST);
				match(158);
				strength1_AST = (AST)currentAST.root;
				break;
			}
			case 173:
			{
				AST tmp116_AST = null;
				tmp116_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp116_AST);
				match(173);
				strength1_AST = (AST)currentAST.root;
				break;
			}
			case 174:
			{
				AST tmp117_AST = null;
				tmp117_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp117_AST);
				match(174);
				strength1_AST = (AST)currentAST.root;
				break;
			}
			case 175:
			{
				AST tmp118_AST = null;
				tmp118_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp118_AST);
				match(175);
				strength1_AST = (AST)currentAST.root;
				break;
			}
			case 176:
			{
				AST tmp119_AST = null;
				tmp119_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp119_AST);
				match(176);
				strength1_AST = (AST)currentAST.root;
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
				recover(ex,_tokenSet_12);
			} else {
			  throw ex;
			}
		}
		returnAST = strength1_AST;
	}
	
	public final void range_param() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST range_param_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case LPAREN:
			case NUMBER:
			case PLUS:
			case MINUS:
			case IDENTIFIER:
			case LCURLY:
			case STRING:
			case LNOT:
			case BNOT:
			case BAND:
			case RNAND:
			case BOR:
			case RNOR:
			case BXOR:
			case RXNOR:
			case PLUSPLUS:
			case MINMIN:
			case ESCAPED_IDENTIFIER:
			case DOLLAR_IDENTIFIER:
			{
				expression();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case DOLLAR:
			{
				AST tmp120_AST = null;
				tmp120_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp120_AST);
				match(DOLLAR);
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
			case PLUS:
			{
				AST tmp121_AST = null;
				tmp121_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp121_AST);
				match(PLUS);
				break;
			}
			case MINUS:
			{
				AST tmp122_AST = null;
				tmp122_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp122_AST);
				match(MINUS);
				break;
			}
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
			if ( inputState.guessing==0 ) {
				range_param_AST = (AST)currentAST.root;
				range_param_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(RANGEPARAM,"RangeParam")).add(range_param_AST));
				currentAST.root = range_param_AST;
				currentAST.child = range_param_AST!=null &&range_param_AST.getFirstChild()!=null ?
					range_param_AST.getFirstChild() : range_param_AST;
				currentAST.advanceChildToEnd();
			}
			range_param_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_45);
			} else {
			  throw ex;
			}
		}
		returnAST = range_param_AST;
	}
	
	public final void gate_type() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST gate_type_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_and:
			{
				AST tmp123_AST = null;
				tmp123_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp123_AST);
				match(LITERAL_and);
				gate_type_AST = (AST)currentAST.root;
				break;
			}
			case LITERAL_nand:
			{
				AST tmp124_AST = null;
				tmp124_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp124_AST);
				match(LITERAL_nand);
				gate_type_AST = (AST)currentAST.root;
				break;
			}
			case LITERAL_or:
			{
				AST tmp125_AST = null;
				tmp125_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp125_AST);
				match(LITERAL_or);
				gate_type_AST = (AST)currentAST.root;
				break;
			}
			case LITERAL_nor:
			{
				AST tmp126_AST = null;
				tmp126_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp126_AST);
				match(LITERAL_nor);
				gate_type_AST = (AST)currentAST.root;
				break;
			}
			case LITERAL_xor:
			{
				AST tmp127_AST = null;
				tmp127_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp127_AST);
				match(LITERAL_xor);
				gate_type_AST = (AST)currentAST.root;
				break;
			}
			case LITERAL_xnor:
			{
				AST tmp128_AST = null;
				tmp128_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp128_AST);
				match(LITERAL_xnor);
				gate_type_AST = (AST)currentAST.root;
				break;
			}
			case LITERAL_buf:
			{
				AST tmp129_AST = null;
				tmp129_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp129_AST);
				match(LITERAL_buf);
				gate_type_AST = (AST)currentAST.root;
				break;
			}
			case 187:
			{
				AST tmp130_AST = null;
				tmp130_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp130_AST);
				match(187);
				gate_type_AST = (AST)currentAST.root;
				break;
			}
			case 188:
			{
				AST tmp131_AST = null;
				tmp131_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp131_AST);
				match(188);
				gate_type_AST = (AST)currentAST.root;
				break;
			}
			case LITERAL_not:
			{
				AST tmp132_AST = null;
				tmp132_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp132_AST);
				match(LITERAL_not);
				gate_type_AST = (AST)currentAST.root;
				break;
			}
			case 190:
			{
				AST tmp133_AST = null;
				tmp133_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp133_AST);
				match(190);
				gate_type_AST = (AST)currentAST.root;
				break;
			}
			case 191:
			{
				AST tmp134_AST = null;
				tmp134_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp134_AST);
				match(191);
				gate_type_AST = (AST)currentAST.root;
				break;
			}
			case LITERAL_pulldown:
			{
				AST tmp135_AST = null;
				tmp135_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp135_AST);
				match(LITERAL_pulldown);
				gate_type_AST = (AST)currentAST.root;
				break;
			}
			case LITERAL_pullup:
			{
				AST tmp136_AST = null;
				tmp136_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp136_AST);
				match(LITERAL_pullup);
				gate_type_AST = (AST)currentAST.root;
				break;
			}
			case LITERAL_nmos:
			{
				AST tmp137_AST = null;
				tmp137_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp137_AST);
				match(LITERAL_nmos);
				gate_type_AST = (AST)currentAST.root;
				break;
			}
			case LITERAL_rnmos:
			{
				AST tmp138_AST = null;
				tmp138_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp138_AST);
				match(LITERAL_rnmos);
				gate_type_AST = (AST)currentAST.root;
				break;
			}
			case LITERAL_pmos:
			{
				AST tmp139_AST = null;
				tmp139_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp139_AST);
				match(LITERAL_pmos);
				gate_type_AST = (AST)currentAST.root;
				break;
			}
			case LITERAL_rpmos:
			{
				AST tmp140_AST = null;
				tmp140_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp140_AST);
				match(LITERAL_rpmos);
				gate_type_AST = (AST)currentAST.root;
				break;
			}
			case LITERAL_cmos:
			{
				AST tmp141_AST = null;
				tmp141_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp141_AST);
				match(LITERAL_cmos);
				gate_type_AST = (AST)currentAST.root;
				break;
			}
			case LITERAL_rcmos:
			{
				AST tmp142_AST = null;
				tmp142_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp142_AST);
				match(LITERAL_rcmos);
				gate_type_AST = (AST)currentAST.root;
				break;
			}
			case LITERAL_tran:
			{
				AST tmp143_AST = null;
				tmp143_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp143_AST);
				match(LITERAL_tran);
				gate_type_AST = (AST)currentAST.root;
				break;
			}
			case LITERAL_rtran:
			{
				AST tmp144_AST = null;
				tmp144_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp144_AST);
				match(LITERAL_rtran);
				gate_type_AST = (AST)currentAST.root;
				break;
			}
			case 202:
			{
				AST tmp145_AST = null;
				tmp145_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp145_AST);
				match(202);
				gate_type_AST = (AST)currentAST.root;
				break;
			}
			case 203:
			{
				AST tmp146_AST = null;
				tmp146_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp146_AST);
				match(203);
				gate_type_AST = (AST)currentAST.root;
				break;
			}
			case 204:
			{
				AST tmp147_AST = null;
				tmp147_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp147_AST);
				match(204);
				gate_type_AST = (AST)currentAST.root;
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
				recover(ex,_tokenSet_20);
			} else {
			  throw ex;
			}
		}
		returnAST = gate_type_AST;
	}
	
	public final void gate_instance() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST gate_instance_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case IDENTIFIER:
			case ESCAPED_IDENTIFIER:
			case DOLLAR_IDENTIFIER:
			{
				name_of_gate_instance();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case LPAREN:
			case LBRACK:
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
			_loop125:
			do {
				if ((LA(1)==LBRACK)) {
					range();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop125;
				}
				
			} while (true);
			}
			AST tmp148_AST = null;
			tmp148_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp148_AST);
			match(LPAREN);
			terminal();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop127:
			do {
				if ((LA(1)==COMMA)) {
					AST tmp149_AST = null;
					tmp149_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp149_AST);
					match(COMMA);
					terminal();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop127;
				}
				
			} while (true);
			}
			AST tmp150_AST = null;
			tmp150_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp150_AST);
			match(RPAREN);
			gate_instance_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_34);
			} else {
			  throw ex;
			}
		}
		returnAST = gate_instance_AST;
	}
	
	public final void mintypmax_expression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST mintypmax_expression_AST = null;
		
		try {      // for error handling
			expression();
			astFactory.addASTChild(currentAST, returnAST);
			{
			switch ( LA(1)) {
			case COLON:
			{
				AST tmp151_AST = null;
				tmp151_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp151_AST);
				match(COLON);
				expression();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp152_AST = null;
				tmp152_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp152_AST);
				match(COLON);
				expression();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case SEMI:
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
			mintypmax_expression_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_44);
			} else {
			  throw ex;
			}
		}
		returnAST = mintypmax_expression_AST;
	}
	
	public final void time_literal() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST time_literal_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case NUMBER:
			{
				number();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case LITERAL_s:
			case LITERAL_ms:
			case LITERAL_us:
			case LITERAL_ns:
			case LITERAL_ps:
			case LITERAL_fs:
			case LITERAL_step:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			time_unit();
			astFactory.addASTChild(currentAST, returnAST);
			time_literal_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_0);
			} else {
			  throw ex;
			}
		}
		returnAST = time_literal_AST;
	}
	
	public final void time_unit() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST time_unit_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_s:
			{
				AST tmp153_AST = null;
				tmp153_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp153_AST);
				match(LITERAL_s);
				time_unit_AST = (AST)currentAST.root;
				break;
			}
			case LITERAL_ms:
			{
				AST tmp154_AST = null;
				tmp154_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp154_AST);
				match(LITERAL_ms);
				time_unit_AST = (AST)currentAST.root;
				break;
			}
			case LITERAL_us:
			{
				AST tmp155_AST = null;
				tmp155_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp155_AST);
				match(LITERAL_us);
				time_unit_AST = (AST)currentAST.root;
				break;
			}
			case LITERAL_ns:
			{
				AST tmp156_AST = null;
				tmp156_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp156_AST);
				match(LITERAL_ns);
				time_unit_AST = (AST)currentAST.root;
				break;
			}
			case LITERAL_ps:
			{
				AST tmp157_AST = null;
				tmp157_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp157_AST);
				match(LITERAL_ps);
				time_unit_AST = (AST)currentAST.root;
				break;
			}
			case LITERAL_fs:
			{
				AST tmp158_AST = null;
				tmp158_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp158_AST);
				match(LITERAL_fs);
				time_unit_AST = (AST)currentAST.root;
				break;
			}
			case LITERAL_step:
			{
				AST tmp159_AST = null;
				tmp159_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp159_AST);
				match(LITERAL_step);
				time_unit_AST = (AST)currentAST.root;
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
				recover(ex,_tokenSet_46);
			} else {
			  throw ex;
			}
		}
		returnAST = time_unit_AST;
	}
	
	public final void name_of_gate_instance() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST name_of_gate_instance_AST = null;
		
		try {      // for error handling
			local_identifier();
			astFactory.addASTChild(currentAST, returnAST);
			name_of_gate_instance_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_47);
			} else {
			  throw ex;
			}
		}
		returnAST = name_of_gate_instance_AST;
	}
	
	public final void terminal() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST terminal_AST = null;
		
		try {      // for error handling
			expression();
			astFactory.addASTChild(currentAST, returnAST);
			terminal_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_12);
			} else {
			  throw ex;
			}
		}
		returnAST = terminal_AST;
	}
	
	public final void udp_instance() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST udp_instance_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case IDENTIFIER:
			case ESCAPED_IDENTIFIER:
			case DOLLAR_IDENTIFIER:
			{
				name_of_UDP_instance();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case LPAREN:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			AST tmp160_AST = null;
			tmp160_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp160_AST);
			match(LPAREN);
			terminal();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop131:
			do {
				if ((LA(1)==COMMA)) {
					AST tmp161_AST = null;
					tmp161_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp161_AST);
					match(COMMA);
					terminal();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop131;
				}
				
			} while (true);
			}
			AST tmp162_AST = null;
			tmp162_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp162_AST);
			match(RPAREN);
			udp_instance_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_0);
			} else {
			  throw ex;
			}
		}
		returnAST = udp_instance_AST;
	}
	
	public final void name_of_UDP_instance() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST name_of_UDP_instance_AST = null;
		
		try {      // for error handling
			local_identifier();
			astFactory.addASTChild(currentAST, returnAST);
			name_of_UDP_instance_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_48);
			} else {
			  throw ex;
			}
		}
		returnAST = name_of_UDP_instance_AST;
	}
	
	public final void module_instance() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST module_instance_AST = null;
		
		try {      // for error handling
			identifier();
			astFactory.addASTChild(currentAST, returnAST);
			list_of_module_connections();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				module_instance_AST = (AST)currentAST.root;
				module_instance_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(MODINSTANCE,"ModInstance")).add(module_instance_AST));
				currentAST.root = module_instance_AST;
				currentAST.child = module_instance_AST!=null &&module_instance_AST.getFirstChild()!=null ?
					module_instance_AST.getFirstChild() : module_instance_AST;
				currentAST.advanceChildToEnd();
			}
			module_instance_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_9);
			} else {
			  throw ex;
			}
		}
		returnAST = module_instance_AST;
	}
	
	public final void parameter_value_assignment() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST parameter_value_assignment_AST = null;
		
		try {      // for error handling
			AST tmp163_AST = null;
			tmp163_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp163_AST);
			match(POUND);
			AST tmp164_AST = null;
			tmp164_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp164_AST);
			match(LPAREN);
			expression();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop136:
			do {
				if ((LA(1)==COMMA)) {
					AST tmp165_AST = null;
					tmp165_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp165_AST);
					match(COMMA);
					expression();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop136;
				}
				
			} while (true);
			}
			AST tmp166_AST = null;
			tmp166_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp166_AST);
			match(RPAREN);
			parameter_value_assignment_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_0);
			} else {
			  throw ex;
			}
		}
		returnAST = parameter_value_assignment_AST;
	}
	
	public final void list_of_module_connections() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST list_of_module_connections_AST = null;
		
		try {      // for error handling
			AST tmp167_AST = null;
			tmp167_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp167_AST);
			match(LPAREN);
			{
			switch ( LA(1)) {
			case DOT:
			case IDENTIFIER:
			case LCURLY:
			case ESCAPED_IDENTIFIER:
			case DOLLAR_IDENTIFIER:
			{
				module_port_connection();
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
			_loop141:
			do {
				if ((LA(1)==COMMA)) {
					AST tmp168_AST = null;
					tmp168_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp168_AST);
					match(COMMA);
					module_port_connection();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop141;
				}
				
			} while (true);
			}
			AST tmp169_AST = null;
			tmp169_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp169_AST);
			match(RPAREN);
			if ( inputState.guessing==0 ) {
				list_of_module_connections_AST = (AST)currentAST.root;
				list_of_module_connections_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(INSTANCEPORTLIST,"InstanceList")).add(list_of_module_connections_AST));
				currentAST.root = list_of_module_connections_AST;
				currentAST.child = list_of_module_connections_AST!=null &&list_of_module_connections_AST.getFirstChild()!=null ?
					list_of_module_connections_AST.getFirstChild() : list_of_module_connections_AST;
				currentAST.advanceChildToEnd();
			}
			list_of_module_connections_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_9);
			} else {
			  throw ex;
			}
		}
		returnAST = list_of_module_connections_AST;
	}
	
	public final void module_port_connection() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST module_port_connection_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case IDENTIFIER:
			case LCURLY:
			case ESCAPED_IDENTIFIER:
			case DOLLAR_IDENTIFIER:
			{
				port_connection();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case DOT:
			{
				named_port_connection();
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
				module_port_connection_AST = (AST)currentAST.root;
				module_port_connection_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(INSTANCEPORT,"InstancePort")).add(module_port_connection_AST));
				currentAST.root = module_port_connection_AST;
				currentAST.child = module_port_connection_AST!=null &&module_port_connection_AST.getFirstChild()!=null ?
					module_port_connection_AST.getFirstChild() : module_port_connection_AST;
				currentAST.advanceChildToEnd();
			}
			module_port_connection_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_12);
			} else {
			  throw ex;
			}
		}
		returnAST = module_port_connection_AST;
	}
	
	public final void port_connection() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST port_connection_AST = null;
		
		try {      // for error handling
			var_ident();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				port_connection_AST = (AST)currentAST.root;
				port_connection_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(PORTCON,"PortConnection")).add(port_connection_AST));
				currentAST.root = port_connection_AST;
				currentAST.child = port_connection_AST!=null &&port_connection_AST.getFirstChild()!=null ?
					port_connection_AST.getFirstChild() : port_connection_AST;
				currentAST.advanceChildToEnd();
			}
			port_connection_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_12);
			} else {
			  throw ex;
			}
		}
		returnAST = port_connection_AST;
	}
	
	public final void named_port_connection() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST named_port_connection_AST = null;
		
		try {      // for error handling
			AST tmp170_AST = null;
			tmp170_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp170_AST);
			match(DOT);
			identifier();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp171_AST = null;
			tmp171_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp171_AST);
			match(LPAREN);
			{
			switch ( LA(1)) {
			case IDENTIFIER:
			case LCURLY:
			case ESCAPED_IDENTIFIER:
			case DOLLAR_IDENTIFIER:
			{
				var_ident();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case RPAREN:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			AST tmp172_AST = null;
			tmp172_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp172_AST);
			match(RPAREN);
			if ( inputState.guessing==0 ) {
				named_port_connection_AST = (AST)currentAST.root;
				named_port_connection_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(PORTDOT,"PortDot")).add(named_port_connection_AST));
				currentAST.root = named_port_connection_AST;
				currentAST.child = named_port_connection_AST!=null &&named_port_connection_AST.getFirstChild()!=null ?
					named_port_connection_AST.getFirstChild() : named_port_connection_AST;
				currentAST.advanceChildToEnd();
			}
			named_port_connection_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_12);
			} else {
			  throw ex;
			}
		}
		returnAST = named_port_connection_AST;
	}
	
	public final void var_ident() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST var_ident_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case IDENTIFIER:
			case ESCAPED_IDENTIFIER:
			case DOLLAR_IDENTIFIER:
			{
				name();
				astFactory.addASTChild(currentAST, returnAST);
				var_ident_AST = (AST)currentAST.root;
				break;
			}
			case LCURLY:
			{
				concatenation();
				astFactory.addASTChild(currentAST, returnAST);
				var_ident_AST = (AST)currentAST.root;
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
				recover(ex,_tokenSet_49);
			} else {
			  throw ex;
			}
		}
		returnAST = var_ident_AST;
	}
	
	public final void always_keyword() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST always_keyword_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_always:
			{
				AST tmp173_AST = null;
				tmp173_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp173_AST);
				match(LITERAL_always);
				always_keyword_AST = (AST)currentAST.root;
				break;
			}
			case LITERAL_always_latch:
			{
				AST tmp174_AST = null;
				tmp174_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp174_AST);
				match(LITERAL_always_latch);
				always_keyword_AST = (AST)currentAST.root;
				break;
			}
			case LITERAL_always_comb:
			{
				AST tmp175_AST = null;
				tmp175_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp175_AST);
				match(LITERAL_always_comb);
				always_keyword_AST = (AST)currentAST.root;
				break;
			}
			case LITERAL_always_ff:
			{
				AST tmp176_AST = null;
				tmp176_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp176_AST);
				match(LITERAL_always_ff);
				always_keyword_AST = (AST)currentAST.root;
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
				recover(ex,_tokenSet_30);
			} else {
			  throw ex;
			}
		}
		returnAST = always_keyword_AST;
	}
	
	public final void always_head() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST always_head_AST = null;
		
		try {      // for error handling
			delay_or_event_controlq();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				always_head_AST = (AST)currentAST.root;
				always_head_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(ALWAYSHEAD,"AlwaysHead")).add(always_head_AST));
				currentAST.root = always_head_AST;
				currentAST.child = always_head_AST!=null &&always_head_AST.getFirstChild()!=null ?
					always_head_AST.getFirstChild() : always_head_AST;
				currentAST.advanceChildToEnd();
			}
			always_head_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_30);
			} else {
			  throw ex;
			}
		}
		returnAST = always_head_AST;
	}
	
	public final void delay_or_event_controlq() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST delay_or_event_controlq_AST = null;
		
		try {      // for error handling
			{
			if ((LA(1)==POUND||LA(1)==AT) && (_tokenSet_50.member(LA(2)))) {
				delay_or_event_control();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else if ((_tokenSet_51.member(LA(1))) && (_tokenSet_52.member(LA(2)))) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			if ( inputState.guessing==0 ) {
				delay_or_event_controlq_AST = (AST)currentAST.root;
				delay_or_event_controlq_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(DELAYOREVENTCONTROLQ,"delay_or_event_controlq")).add(delay_or_event_controlq_AST));
				currentAST.root = delay_or_event_controlq_AST;
				currentAST.child = delay_or_event_controlq_AST!=null &&delay_or_event_controlq_AST.getFirstChild()!=null ?
					delay_or_event_controlq_AST.getFirstChild() : delay_or_event_controlq_AST;
				currentAST.advanceChildToEnd();
			}
			delay_or_event_controlq_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_51);
			} else {
			  throw ex;
			}
		}
		returnAST = delay_or_event_controlq_AST;
	}
	
	public final void statement_no_condition() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST statement_no_condition_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case LITERAL_unique:
			case LITERAL_priority:
			case LITERAL_case:
			case LITERAL_casez:
			case LITERAL_casex:
			{
				case_statement();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case LITERAL_forever:
			case LITERAL_repeat:
			case LITERAL_while:
			case LITERAL_for:
			{
				loop_statement();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case POUND:
			case AT:
			{
				procedural_timing_control_statement();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case LITERAL_wait:
			{
				wait_statement();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case TRIGGER:
			{
				event_trigger();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case LITERAL_fork:
			{
				par_block();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case LITERAL_disable:
			{
				disable_statement();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case LITERAL_assign:
			case LITERAL_deassign:
			case LITERAL_force:
			case LITERAL_release:
			{
				procedural_continuous_assignment();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			default:
				boolean synPredMatched158 = false;
				if (((LA(1)==IDENTIFIER||LA(1)==ESCAPED_IDENTIFIER||LA(1)==DOLLAR_IDENTIFIER) && (_tokenSet_53.member(LA(2))))) {
					int _m158 = mark();
					synPredMatched158 = true;
					inputState.guessing++;
					try {
						{
						task_enable();
						}
					}
					catch (RecognitionException pe) {
						synPredMatched158 = false;
					}
					rewind(_m158);
inputState.guessing--;
				}
				if ( synPredMatched158 ) {
					task_enable();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					boolean synPredMatched160 = false;
					if (((_tokenSet_39.member(LA(1))) && (_tokenSet_54.member(LA(2))))) {
						int _m160 = mark();
						synPredMatched160 = true;
						inputState.guessing++;
						try {
							{
							total_assignment();
							}
						}
						catch (RecognitionException pe) {
							synPredMatched160 = false;
						}
						rewind(_m160);
inputState.guessing--;
					}
					if ( synPredMatched160 ) {
						total_assignment();
						astFactory.addASTChild(currentAST, returnAST);
					}
					else if ((_tokenSet_55.member(LA(1))) && (_tokenSet_56.member(LA(2)))) {
						seq_block();
						astFactory.addASTChild(currentAST, returnAST);
					}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}}
				}
				statement_no_condition_AST = (AST)currentAST.root;
			}
			catch (RecognitionException ex) {
				if (inputState.guessing==0) {
					reportError(ex);
					recover(ex,_tokenSet_27);
				} else {
				  throw ex;
				}
			}
			returnAST = statement_no_condition_AST;
		}
		
	public final void task_enable() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST task_enable_AST = null;
		
		try {      // for error handling
			name_of_task();
			astFactory.addASTChild(currentAST, returnAST);
			{
			switch ( LA(1)) {
			case LPAREN:
			{
				AST tmp177_AST = null;
				tmp177_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp177_AST);
				match(LPAREN);
				{
				switch ( LA(1)) {
				case LPAREN:
				case NUMBER:
				case PLUS:
				case MINUS:
				case IDENTIFIER:
				case LCURLY:
				case STRING:
				case LNOT:
				case BNOT:
				case BAND:
				case RNAND:
				case BOR:
				case RNOR:
				case BXOR:
				case RXNOR:
				case PLUSPLUS:
				case MINMIN:
				case ESCAPED_IDENTIFIER:
				case DOLLAR_IDENTIFIER:
				{
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
				{
				_loop217:
				do {
					if ((LA(1)==COMMA)) {
						AST tmp178_AST = null;
						tmp178_AST = astFactory.create(LT(1));
						astFactory.addASTChild(currentAST, tmp178_AST);
						match(COMMA);
						{
						switch ( LA(1)) {
						case LPAREN:
						case NUMBER:
						case PLUS:
						case MINUS:
						case IDENTIFIER:
						case LCURLY:
						case STRING:
						case LNOT:
						case BNOT:
						case BAND:
						case RNAND:
						case BOR:
						case RNOR:
						case BXOR:
						case RXNOR:
						case PLUSPLUS:
						case MINMIN:
						case ESCAPED_IDENTIFIER:
						case DOLLAR_IDENTIFIER:
						{
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
					}
					else {
						break _loop217;
					}
					
				} while (true);
				}
				AST tmp179_AST = null;
				tmp179_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp179_AST);
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
			AST tmp180_AST = null;
			tmp180_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp180_AST);
			match(SEMI);
			task_enable_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_27);
			} else {
			  throw ex;
			}
		}
		returnAST = task_enable_AST;
	}
	
	public final void total_assignment() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST total_assignment_AST = null;
		
		try {      // for error handling
			lvalue();
			astFactory.addASTChild(currentAST, returnAST);
			assign_op();
			astFactory.addASTChild(currentAST, returnAST);
			delay_or_event_controlq();
			astFactory.addASTChild(currentAST, returnAST);
			expression();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp181_AST = null;
			tmp181_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp181_AST);
			match(SEMI);
			if ( inputState.guessing==0 ) {
				total_assignment_AST = (AST)currentAST.root;
				total_assignment_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(TOTALASSIGN,"TotalAssign")).add(total_assignment_AST));
				currentAST.root = total_assignment_AST;
				currentAST.child = total_assignment_AST!=null &&total_assignment_AST.getFirstChild()!=null ?
					total_assignment_AST.getFirstChild() : total_assignment_AST;
				currentAST.advanceChildToEnd();
			}
			total_assignment_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_27);
			} else {
			  throw ex;
			}
		}
		returnAST = total_assignment_AST;
	}
	
	public final void case_statement() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST case_statement_AST = null;
		
		try {      // for error handling
			unique_priority();
			astFactory.addASTChild(currentAST, returnAST);
			case_keyword();
			astFactory.addASTChild(currentAST, returnAST);
			case_head();
			astFactory.addASTChild(currentAST, returnAST);
			matches();
			astFactory.addASTChild(currentAST, returnAST);
			case_list();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp182_AST = null;
			tmp182_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp182_AST);
			match(LITERAL_endcase);
			if ( inputState.guessing==0 ) {
				case_statement_AST = (AST)currentAST.root;
				case_statement_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(CASETOP,"CaseTop")).add(case_statement_AST));
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
				recover(ex,_tokenSet_27);
			} else {
			  throw ex;
			}
		}
		returnAST = case_statement_AST;
	}
	
	public final void loop_statement() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST loop_statement_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_forever:
			{
				AST tmp183_AST = null;
				tmp183_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp183_AST);
				match(LITERAL_forever);
				statement_or_null();
				astFactory.addASTChild(currentAST, returnAST);
				loop_statement_AST = (AST)currentAST.root;
				break;
			}
			case LITERAL_repeat:
			{
				AST tmp184_AST = null;
				tmp184_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp184_AST);
				match(LITERAL_repeat);
				AST tmp185_AST = null;
				tmp185_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp185_AST);
				match(LPAREN);
				expression();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp186_AST = null;
				tmp186_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp186_AST);
				match(RPAREN);
				statement_or_null();
				astFactory.addASTChild(currentAST, returnAST);
				loop_statement_AST = (AST)currentAST.root;
				break;
			}
			case LITERAL_while:
			{
				AST tmp187_AST = null;
				tmp187_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp187_AST);
				match(LITERAL_while);
				AST tmp188_AST = null;
				tmp188_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp188_AST);
				match(LPAREN);
				expression();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp189_AST = null;
				tmp189_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp189_AST);
				match(RPAREN);
				statement_or_null();
				astFactory.addASTChild(currentAST, returnAST);
				loop_statement_AST = (AST)currentAST.root;
				break;
			}
			case LITERAL_for:
			{
				AST tmp190_AST = null;
				tmp190_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp190_AST);
				match(LITERAL_for);
				AST tmp191_AST = null;
				tmp191_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp191_AST);
				match(LPAREN);
				assignment();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp192_AST = null;
				tmp192_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp192_AST);
				match(SEMI);
				expression();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp193_AST = null;
				tmp193_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp193_AST);
				match(SEMI);
				assignment();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp194_AST = null;
				tmp194_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp194_AST);
				match(RPAREN);
				statement();
				astFactory.addASTChild(currentAST, returnAST);
				loop_statement_AST = (AST)currentAST.root;
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
		returnAST = loop_statement_AST;
	}
	
	public final void procedural_timing_control_statement() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST procedural_timing_control_statement_AST = null;
		
		try {      // for error handling
			delay_or_event_control();
			astFactory.addASTChild(currentAST, returnAST);
			statement_or_null();
			astFactory.addASTChild(currentAST, returnAST);
			procedural_timing_control_statement_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_27);
			} else {
			  throw ex;
			}
		}
		returnAST = procedural_timing_control_statement_AST;
	}
	
	public final void wait_statement() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST wait_statement_AST = null;
		
		try {      // for error handling
			AST tmp195_AST = null;
			tmp195_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp195_AST);
			match(LITERAL_wait);
			AST tmp196_AST = null;
			tmp196_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp196_AST);
			match(LPAREN);
			expression();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp197_AST = null;
			tmp197_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp197_AST);
			match(RPAREN);
			statement_or_null();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				wait_statement_AST = (AST)currentAST.root;
				wait_statement_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(WAITSTATEMENT,"wait_statement")).add(wait_statement_AST));
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
				recover(ex,_tokenSet_27);
			} else {
			  throw ex;
			}
		}
		returnAST = wait_statement_AST;
	}
	
	public final void event_trigger() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST event_trigger_AST = null;
		
		try {      // for error handling
			AST tmp198_AST = null;
			tmp198_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp198_AST);
			match(TRIGGER);
			name_of_event();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp199_AST = null;
			tmp199_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp199_AST);
			match(SEMI);
			event_trigger_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_27);
			} else {
			  throw ex;
			}
		}
		returnAST = event_trigger_AST;
	}
	
	public final void seq_block() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST seq_block_AST = null;
		
		try {      // for error handling
			pre_block_nameQ();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp200_AST = null;
			tmp200_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp200_AST);
			match(LITERAL_begin);
			seq_block_nameQ();
			astFactory.addASTChild(currentAST, returnAST);
			state_list();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp201_AST = null;
			tmp201_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp201_AST);
			match(LITERAL_end);
			seq_block_nameQ();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				seq_block_AST = (AST)currentAST.root;
				seq_block_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(BEGENDBLOCK,"BegEndBlock")).add(seq_block_AST));
				currentAST.root = seq_block_AST;
				currentAST.child = seq_block_AST!=null &&seq_block_AST.getFirstChild()!=null ?
					seq_block_AST.getFirstChild() : seq_block_AST;
				currentAST.advanceChildToEnd();
			}
			seq_block_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_27);
			} else {
			  throw ex;
			}
		}
		returnAST = seq_block_AST;
	}
	
	public final void par_block() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST par_block_AST = null;
		
		try {      // for error handling
			AST tmp202_AST = null;
			tmp202_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp202_AST);
			match(LITERAL_fork);
			{
			switch ( LA(1)) {
			case COLON:
			{
				AST tmp203_AST = null;
				tmp203_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp203_AST);
				match(COLON);
				name_of_block();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case LITERAL_assign:
			case POUND:
			case LITERAL_if:
			case LITERAL_unique:
			case LITERAL_priority:
			case LITERAL_case:
			case LITERAL_casez:
			case LITERAL_casex:
			case LITERAL_forever:
			case LITERAL_repeat:
			case LITERAL_while:
			case LITERAL_for:
			case LITERAL_wait:
			case TRIGGER:
			case LITERAL_disable:
			case LITERAL_begin:
			case LITERAL_fork:
			case LITERAL_join:
			case LITERAL_deassign:
			case LITERAL_force:
			case LITERAL_release:
			case IDENTIFIER:
			case LCURLY:
			case ESCAPED_IDENTIFIER:
			case DOLLAR_IDENTIFIER:
			case AT:
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
			_loop211:
			do {
				if ((_tokenSet_30.member(LA(1)))) {
					statement();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop211;
				}
				
			} while (true);
			}
			AST tmp204_AST = null;
			tmp204_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp204_AST);
			match(LITERAL_join);
			par_block_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_27);
			} else {
			  throw ex;
			}
		}
		returnAST = par_block_AST;
	}
	
	public final void disable_statement() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST disable_statement_AST = null;
		
		try {      // for error handling
			AST tmp205_AST = null;
			tmp205_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp205_AST);
			match(LITERAL_disable);
			identifier();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp206_AST = null;
			tmp206_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp206_AST);
			match(SEMI);
			if ( inputState.guessing==0 ) {
				disable_statement_AST = (AST)currentAST.root;
				disable_statement_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(DISABLESTATEMENT,"disable_statement")).add(disable_statement_AST));
				currentAST.root = disable_statement_AST;
				currentAST.child = disable_statement_AST!=null &&disable_statement_AST.getFirstChild()!=null ?
					disable_statement_AST.getFirstChild() : disable_statement_AST;
				currentAST.advanceChildToEnd();
			}
			disable_statement_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_27);
			} else {
			  throw ex;
			}
		}
		returnAST = disable_statement_AST;
	}
	
	public final void procedural_continuous_assignment() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST procedural_continuous_assignment_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_assign:
			{
				AST tmp207_AST = null;
				tmp207_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp207_AST);
				match(LITERAL_assign);
				assignment();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp208_AST = null;
				tmp208_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp208_AST);
				match(SEMI);
				procedural_continuous_assignment_AST = (AST)currentAST.root;
				break;
			}
			case LITERAL_deassign:
			{
				AST tmp209_AST = null;
				tmp209_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp209_AST);
				match(LITERAL_deassign);
				lvalue();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp210_AST = null;
				tmp210_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp210_AST);
				match(SEMI);
				procedural_continuous_assignment_AST = (AST)currentAST.root;
				break;
			}
			case LITERAL_force:
			{
				AST tmp211_AST = null;
				tmp211_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp211_AST);
				match(LITERAL_force);
				assignment();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp212_AST = null;
				tmp212_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp212_AST);
				match(SEMI);
				procedural_continuous_assignment_AST = (AST)currentAST.root;
				break;
			}
			case LITERAL_release:
			{
				AST tmp213_AST = null;
				tmp213_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp213_AST);
				match(LITERAL_release);
				lvalue();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp214_AST = null;
				tmp214_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp214_AST);
				match(SEMI);
				procedural_continuous_assignment_AST = (AST)currentAST.root;
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
		returnAST = procedural_continuous_assignment_AST;
	}
	
	public final void conditional_statement() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST conditional_statement_AST = null;
		
		try {      // for error handling
			unique_priority();
			astFactory.addASTChild(currentAST, returnAST);
			{
			condition_head();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop173:
			do {
				if ((LA(1)==LITERAL_else) && (_tokenSet_30.member(LA(2)))) {
					condition_else();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop173;
				}
				
			} while (true);
			}
			}
			if ( inputState.guessing==0 ) {
				conditional_statement_AST = (AST)currentAST.root;
				conditional_statement_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(CONDITIONTOP,"ConditionTop")).add(conditional_statement_AST));
				currentAST.root = conditional_statement_AST;
				currentAST.child = conditional_statement_AST!=null &&conditional_statement_AST.getFirstChild()!=null ?
					conditional_statement_AST.getFirstChild() : conditional_statement_AST;
				currentAST.advanceChildToEnd();
			}
			conditional_statement_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_27);
			} else {
			  throw ex;
			}
		}
		returnAST = conditional_statement_AST;
	}
	
	public final void lvalue() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST lvalue_AST = null;
		
		try {      // for error handling
			var_ident();
			astFactory.addASTChild(currentAST, returnAST);
			lvalue_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_49);
			} else {
			  throw ex;
			}
		}
		returnAST = lvalue_AST;
	}
	
	public final void assign_op() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST assign_op_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case ASSIGN:
			{
				AST tmp215_AST = null;
				tmp215_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp215_AST);
				match(ASSIGN);
				assign_op_AST = (AST)currentAST.root;
				break;
			}
			case LE:
			{
				AST tmp216_AST = null;
				tmp216_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp216_AST);
				match(LE);
				assign_op_AST = (AST)currentAST.root;
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
				recover(ex,_tokenSet_57);
			} else {
			  throw ex;
			}
		}
		returnAST = assign_op_AST;
	}
	
	public final void delay_or_event_control() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST delay_or_event_control_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case POUND:
			{
				delay_control();
				astFactory.addASTChild(currentAST, returnAST);
				delay_or_event_control_AST = (AST)currentAST.root;
				break;
			}
			case AT:
			{
				event_control();
				astFactory.addASTChild(currentAST, returnAST);
				delay_or_event_control_AST = (AST)currentAST.root;
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
				recover(ex,_tokenSet_58);
			} else {
			  throw ex;
			}
		}
		returnAST = delay_or_event_control_AST;
	}
	
	public final void unique_priority() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST unique_priority_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case LITERAL_unique:
			{
				AST tmp217_AST = null;
				tmp217_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp217_AST);
				match(LITERAL_unique);
				break;
			}
			case LITERAL_priority:
			{
				AST tmp218_AST = null;
				tmp218_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp218_AST);
				match(LITERAL_priority);
				break;
			}
			case LITERAL_if:
			case LITERAL_case:
			case LITERAL_casez:
			case LITERAL_casex:
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
				unique_priority_AST = (AST)currentAST.root;
				unique_priority_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(UNIQUEPRIORITY,"unique_priority")).add(unique_priority_AST));
				currentAST.root = unique_priority_AST;
				currentAST.child = unique_priority_AST!=null &&unique_priority_AST.getFirstChild()!=null ?
					unique_priority_AST.getFirstChild() : unique_priority_AST;
				currentAST.advanceChildToEnd();
			}
			unique_priority_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_59);
			} else {
			  throw ex;
			}
		}
		returnAST = unique_priority_AST;
	}
	
	public final void condition_head() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST condition_head_AST = null;
		
		try {      // for error handling
			AST tmp219_AST = null;
			tmp219_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp219_AST);
			match(LITERAL_if);
			parop();
			astFactory.addASTChild(currentAST, returnAST);
			statement_or_null();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				condition_head_AST = (AST)currentAST.root;
				condition_head_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(CONDITIONHEAD,"ConditionHead")).add(condition_head_AST));
				currentAST.root = condition_head_AST;
				currentAST.child = condition_head_AST!=null &&condition_head_AST.getFirstChild()!=null ?
					condition_head_AST.getFirstChild() : condition_head_AST;
				currentAST.advanceChildToEnd();
			}
			condition_head_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_27);
			} else {
			  throw ex;
			}
		}
		returnAST = condition_head_AST;
	}
	
	public final void condition_else() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST condition_else_AST = null;
		
		try {      // for error handling
			AST tmp220_AST = null;
			tmp220_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp220_AST);
			match(LITERAL_else);
			{
			switch ( LA(1)) {
			case LITERAL_if:
			{
				condition_head();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case LITERAL_assign:
			case POUND:
			case LITERAL_unique:
			case LITERAL_priority:
			case LITERAL_case:
			case LITERAL_casez:
			case LITERAL_casex:
			case LITERAL_forever:
			case LITERAL_repeat:
			case LITERAL_while:
			case LITERAL_for:
			case LITERAL_wait:
			case TRIGGER:
			case LITERAL_disable:
			case LITERAL_begin:
			case LITERAL_fork:
			case LITERAL_deassign:
			case LITERAL_force:
			case LITERAL_release:
			case IDENTIFIER:
			case LCURLY:
			case ESCAPED_IDENTIFIER:
			case DOLLAR_IDENTIFIER:
			case AT:
			{
				statement_no_condition();
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
				condition_else_AST = (AST)currentAST.root;
				condition_else_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(CONDITIONELSE,"ConditionElse")).add(condition_else_AST));
				currentAST.root = condition_else_AST;
				currentAST.child = condition_else_AST!=null &&condition_else_AST.getFirstChild()!=null ?
					condition_else_AST.getFirstChild() : condition_else_AST;
				currentAST.advanceChildToEnd();
			}
			condition_else_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_27);
			} else {
			  throw ex;
			}
		}
		returnAST = condition_else_AST;
	}
	
	public final void parop() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST parop_AST = null;
		
		try {      // for error handling
			AST tmp221_AST = null;
			tmp221_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp221_AST);
			match(LPAREN);
			expression();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp222_AST = null;
			tmp222_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp222_AST);
			match(RPAREN);
			if ( inputState.guessing==0 ) {
				parop_AST = (AST)currentAST.root;
				parop_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(PAROP,"ParOp")).add(parop_AST));
				currentAST.root = parop_AST;
				currentAST.child = parop_AST!=null &&parop_AST.getFirstChild()!=null ?
					parop_AST.getFirstChild() : parop_AST;
				currentAST.advanceChildToEnd();
			}
			parop_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_60);
			} else {
			  throw ex;
			}
		}
		returnAST = parop_AST;
	}
	
	public final void matches() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST matches_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case LITERAL_matches:
			{
				AST tmp223_AST = null;
				tmp223_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp223_AST);
				match(LITERAL_matches);
				break;
			}
			case LPAREN:
			case NUMBER:
			case PLUS:
			case MINUS:
			case LITERAL_default:
			case IDENTIFIER:
			case LCURLY:
			case STRING:
			case LNOT:
			case BNOT:
			case BAND:
			case RNAND:
			case BOR:
			case RNOR:
			case BXOR:
			case RXNOR:
			case PLUSPLUS:
			case MINMIN:
			case ESCAPED_IDENTIFIER:
			case DOLLAR_IDENTIFIER:
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
				matches_AST = (AST)currentAST.root;
				matches_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(MATCHES,"matches")).add(matches_AST));
				currentAST.root = matches_AST;
				currentAST.child = matches_AST!=null &&matches_AST.getFirstChild()!=null ?
					matches_AST.getFirstChild() : matches_AST;
				currentAST.advanceChildToEnd();
			}
			matches_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_61);
			} else {
			  throw ex;
			}
		}
		returnAST = matches_AST;
	}
	
	public final void case_keyword() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST case_keyword_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_case:
			{
				AST tmp224_AST = null;
				tmp224_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp224_AST);
				match(LITERAL_case);
				case_keyword_AST = (AST)currentAST.root;
				break;
			}
			case LITERAL_casez:
			{
				AST tmp225_AST = null;
				tmp225_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp225_AST);
				match(LITERAL_casez);
				case_keyword_AST = (AST)currentAST.root;
				break;
			}
			case LITERAL_casex:
			{
				AST tmp226_AST = null;
				tmp226_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp226_AST);
				match(LITERAL_casex);
				case_keyword_AST = (AST)currentAST.root;
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
				recover(ex,_tokenSet_48);
			} else {
			  throw ex;
			}
		}
		returnAST = case_keyword_AST;
	}
	
	public final void case_head() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST case_head_AST = null;
		
		try {      // for error handling
			cond_predicate();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				case_head_AST = (AST)currentAST.root;
				case_head_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(CASEHEAD,"CaseHead")).add(case_head_AST));
				currentAST.root = case_head_AST;
				currentAST.child = case_head_AST!=null &&case_head_AST.getFirstChild()!=null ?
					case_head_AST.getFirstChild() : case_head_AST;
				currentAST.advanceChildToEnd();
			}
			case_head_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_62);
			} else {
			  throw ex;
			}
		}
		returnAST = case_head_AST;
	}
	
	public final void case_list() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST case_list_AST = null;
		
		try {      // for error handling
			{
			int _cnt185=0;
			_loop185:
			do {
				if ((_tokenSet_61.member(LA(1)))) {
					case_item();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					if ( _cnt185>=1 ) { break _loop185; } else {throw new NoViableAltException(LT(1), getFilename());}
				}
				
				_cnt185++;
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				case_list_AST = (AST)currentAST.root;
				case_list_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(CASELIST,"CaseList")).add(case_list_AST));
				currentAST.root = case_list_AST;
				currentAST.child = case_list_AST!=null &&case_list_AST.getFirstChild()!=null ?
					case_list_AST.getFirstChild() : case_list_AST;
				currentAST.advanceChildToEnd();
			}
			case_list_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_63);
			} else {
			  throw ex;
			}
		}
		returnAST = case_list_AST;
	}
	
	public final void cond_predicate() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST cond_predicate_AST = null;
		
		try {      // for error handling
			parop();
			astFactory.addASTChild(currentAST, returnAST);
			cond_predicate_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_62);
			} else {
			  throw ex;
			}
		}
		returnAST = cond_predicate_AST;
	}
	
	public final void case_item() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST case_item_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case LPAREN:
			case NUMBER:
			case PLUS:
			case MINUS:
			case IDENTIFIER:
			case LCURLY:
			case STRING:
			case LNOT:
			case BNOT:
			case BAND:
			case RNAND:
			case BOR:
			case RNOR:
			case BXOR:
			case RXNOR:
			case PLUSPLUS:
			case MINMIN:
			case ESCAPED_IDENTIFIER:
			case DOLLAR_IDENTIFIER:
			{
				case_normal();
				astFactory.addASTChild(currentAST, returnAST);
				case_item_AST = (AST)currentAST.root;
				break;
			}
			case LITERAL_default:
			{
				case_default();
				astFactory.addASTChild(currentAST, returnAST);
				case_item_AST = (AST)currentAST.root;
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
				recover(ex,_tokenSet_64);
			} else {
			  throw ex;
			}
		}
		returnAST = case_item_AST;
	}
	
	public final void case_normal() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST case_normal_AST = null;
		
		try {      // for error handling
			case_expression();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp227_AST = null;
			tmp227_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp227_AST);
			match(COLON);
			statement_or_null();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				case_normal_AST = (AST)currentAST.root;
				case_normal_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(CASENORMAL,"CaseNormal")).add(case_normal_AST));
				currentAST.root = case_normal_AST;
				currentAST.child = case_normal_AST!=null &&case_normal_AST.getFirstChild()!=null ?
					case_normal_AST.getFirstChild() : case_normal_AST;
				currentAST.advanceChildToEnd();
			}
			case_normal_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_64);
			} else {
			  throw ex;
			}
		}
		returnAST = case_normal_AST;
	}
	
	public final void case_default() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST case_default_AST = null;
		
		try {      // for error handling
			AST tmp228_AST = null;
			tmp228_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp228_AST);
			match(LITERAL_default);
			{
			switch ( LA(1)) {
			case COLON:
			{
				AST tmp229_AST = null;
				tmp229_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp229_AST);
				match(COLON);
				break;
			}
			case SEMI:
			case LITERAL_assign:
			case POUND:
			case LITERAL_if:
			case LITERAL_unique:
			case LITERAL_priority:
			case LITERAL_case:
			case LITERAL_casez:
			case LITERAL_casex:
			case LITERAL_forever:
			case LITERAL_repeat:
			case LITERAL_while:
			case LITERAL_for:
			case LITERAL_wait:
			case TRIGGER:
			case LITERAL_disable:
			case LITERAL_begin:
			case LITERAL_fork:
			case LITERAL_deassign:
			case LITERAL_force:
			case LITERAL_release:
			case IDENTIFIER:
			case LCURLY:
			case ESCAPED_IDENTIFIER:
			case DOLLAR_IDENTIFIER:
			case AT:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			statement_or_null();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				case_default_AST = (AST)currentAST.root;
				case_default_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(CASEDEFAULT,"CaseDefault")).add(case_default_AST));
				currentAST.root = case_default_AST;
				currentAST.child = case_default_AST!=null &&case_default_AST.getFirstChild()!=null ?
					case_default_AST.getFirstChild() : case_default_AST;
				currentAST.advanceChildToEnd();
			}
			case_default_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_64);
			} else {
			  throw ex;
			}
		}
		returnAST = case_default_AST;
	}
	
	public final void case_expression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST case_expression_AST = null;
		
		try {      // for error handling
			expression();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop190:
			do {
				if ((LA(1)==COMMA)) {
					AST tmp230_AST = null;
					tmp230_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp230_AST);
					match(COMMA);
					expression();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop190;
				}
				
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				case_expression_AST = (AST)currentAST.root;
				case_expression_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(CASEEXPRESSION,"CaseExpression")).add(case_expression_AST));
				currentAST.root = case_expression_AST;
				currentAST.child = case_expression_AST!=null &&case_expression_AST.getFirstChild()!=null ?
					case_expression_AST.getFirstChild() : case_expression_AST;
				currentAST.advanceChildToEnd();
			}
			case_expression_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_45);
			} else {
			  throw ex;
			}
		}
		returnAST = case_expression_AST;
	}
	
	public final void name_of_event() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST name_of_event_AST = null;
		
		try {      // for error handling
			local_identifier();
			astFactory.addASTChild(currentAST, returnAST);
			name_of_event_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_9);
			} else {
			  throw ex;
			}
		}
		returnAST = name_of_event_AST;
	}
	
	public final void seq_block_nameQ() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST seq_block_nameQ_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case COLON:
			{
				AST tmp231_AST = null;
				tmp231_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp231_AST);
				match(COLON);
				name_of_block();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case TICKELSE:
			case TICKENDIF:
			case LITERAL_endmodule:
			case LPAREN:
			case LITERAL_generate:
			case LITERAL_endgenerate:
			case NUMBER:
			case LITERAL_task:
			case LITERAL_endtask:
			case LITERAL_function:
			case LITERAL_endfunction:
			case LITERAL_assign:
			case LITERAL_defparam:
			case PLUS:
			case MINUS:
			case LITERAL_and:
			case LITERAL_nand:
			case LITERAL_or:
			case LITERAL_nor:
			case LITERAL_xor:
			case LITERAL_xnor:
			case LITERAL_buf:
			case 187:
			case 188:
			case LITERAL_not:
			case 190:
			case 191:
			case LITERAL_pulldown:
			case LITERAL_pullup:
			case LITERAL_nmos:
			case LITERAL_rnmos:
			case LITERAL_pmos:
			case LITERAL_rpmos:
			case LITERAL_cmos:
			case LITERAL_rcmos:
			case LITERAL_tran:
			case LITERAL_rtran:
			case 202:
			case 203:
			case 204:
			case POUND:
			case LITERAL_initial:
			case LITERAL_final:
			case LITERAL_always:
			case LITERAL_always_latch:
			case LITERAL_always_comb:
			case LITERAL_always_ff:
			case LITERAL_if:
			case LITERAL_else:
			case LITERAL_unique:
			case LITERAL_priority:
			case LITERAL_endcase:
			case LITERAL_default:
			case LITERAL_case:
			case LITERAL_casez:
			case LITERAL_casex:
			case LITERAL_forever:
			case LITERAL_repeat:
			case LITERAL_while:
			case LITERAL_for:
			case LITERAL_wait:
			case TRIGGER:
			case LITERAL_disable:
			case LITERAL_begin:
			case LITERAL_end:
			case LITERAL_fork:
			case LITERAL_join:
			case LITERAL_deassign:
			case LITERAL_force:
			case LITERAL_release:
			case LITERAL_specify:
			case IDENTIFIER:
			case LCURLY:
			case STRING:
			case LNOT:
			case BNOT:
			case BAND:
			case RNAND:
			case BOR:
			case RNOR:
			case BXOR:
			case RXNOR:
			case PLUSPLUS:
			case MINMIN:
			case ESCAPED_IDENTIFIER:
			case DOLLAR_IDENTIFIER:
			case AT:
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
				seq_block_nameQ_AST = (AST)currentAST.root;
				seq_block_nameQ_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(SEQBLOCKNAMEQ,"seq_block_nameQ")).add(seq_block_nameQ_AST));
				currentAST.root = seq_block_nameQ_AST;
				currentAST.child = seq_block_nameQ_AST!=null &&seq_block_nameQ_AST.getFirstChild()!=null ?
					seq_block_nameQ_AST.getFirstChild() : seq_block_nameQ_AST;
				currentAST.advanceChildToEnd();
			}
			seq_block_nameQ_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_27);
			} else {
			  throw ex;
			}
		}
		returnAST = seq_block_nameQ_AST;
	}
	
	public final void name_of_block() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST name_of_block_AST = null;
		
		try {      // for error handling
			local_identifier();
			astFactory.addASTChild(currentAST, returnAST);
			name_of_block_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_65);
			} else {
			  throw ex;
			}
		}
		returnAST = name_of_block_AST;
	}
	
	public final void pre_block_nameQ() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST pre_block_nameQ_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case IDENTIFIER:
			case ESCAPED_IDENTIFIER:
			case DOLLAR_IDENTIFIER:
			{
				name_of_block();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp232_AST = null;
				tmp232_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp232_AST);
				match(COLON);
				break;
			}
			case LITERAL_begin:
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
				pre_block_nameQ_AST = (AST)currentAST.root;
				pre_block_nameQ_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(PREBLOCKNAMEQ,"pre_block_nameQ")).add(pre_block_nameQ_AST));
				currentAST.root = pre_block_nameQ_AST;
				currentAST.child = pre_block_nameQ_AST!=null &&pre_block_nameQ_AST.getFirstChild()!=null ?
					pre_block_nameQ_AST.getFirstChild() : pre_block_nameQ_AST;
				currentAST.advanceChildToEnd();
			}
			pre_block_nameQ_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_66);
			} else {
			  throw ex;
			}
		}
		returnAST = pre_block_nameQ_AST;
	}
	
	public final void state_list() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST state_list_AST = null;
		
		try {      // for error handling
			{
			_loop207:
			do {
				if ((_tokenSet_30.member(LA(1)))) {
					statement();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop207;
				}
				
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				state_list_AST = (AST)currentAST.root;
				state_list_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(STATELIST,"StateList")).add(state_list_AST));
				currentAST.root = state_list_AST;
				currentAST.child = state_list_AST!=null &&state_list_AST.getFirstChild()!=null ?
					state_list_AST.getFirstChild() : state_list_AST;
				currentAST.advanceChildToEnd();
			}
			state_list_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_67);
			} else {
			  throw ex;
			}
		}
		returnAST = state_list_AST;
	}
	
	public final void system_task_enable() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST system_task_enable_AST = null;
		
		try {      // for error handling
			identifier();
			astFactory.addASTChild(currentAST, returnAST);
			{
			switch ( LA(1)) {
			case LPAREN:
			{
				AST tmp233_AST = null;
				tmp233_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp233_AST);
				match(LPAREN);
				expression();
				astFactory.addASTChild(currentAST, returnAST);
				{
				_loop222:
				do {
					if ((LA(1)==COMMA)) {
						AST tmp234_AST = null;
						tmp234_AST = astFactory.create(LT(1));
						astFactory.addASTChild(currentAST, tmp234_AST);
						match(COMMA);
						{
						switch ( LA(1)) {
						case LPAREN:
						case NUMBER:
						case PLUS:
						case MINUS:
						case IDENTIFIER:
						case LCURLY:
						case STRING:
						case LNOT:
						case BNOT:
						case BAND:
						case RNAND:
						case BOR:
						case RNOR:
						case BXOR:
						case RXNOR:
						case PLUSPLUS:
						case MINMIN:
						case ESCAPED_IDENTIFIER:
						case DOLLAR_IDENTIFIER:
						{
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
					}
					else {
						break _loop222;
					}
					
				} while (true);
				}
				AST tmp235_AST = null;
				tmp235_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp235_AST);
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
			AST tmp236_AST = null;
			tmp236_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp236_AST);
			match(SEMI);
			system_task_enable_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_0);
			} else {
			  throw ex;
			}
		}
		returnAST = system_task_enable_AST;
	}
	
	public final void delay_control() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST delay_control_AST = null;
		
		try {      // for error handling
			boolean synPredMatched412 = false;
			if (((LA(1)==POUND) && (_tokenSet_68.member(LA(2))))) {
				int _m412 = mark();
				synPredMatched412 = true;
				inputState.guessing++;
				try {
					{
					delay_number();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched412 = false;
				}
				rewind(_m412);
inputState.guessing--;
			}
			if ( synPredMatched412 ) {
				delay_number();
				astFactory.addASTChild(currentAST, returnAST);
				delay_control_AST = (AST)currentAST.root;
			}
			else if ((LA(1)==POUND) && (LA(2)==LPAREN)) {
				AST tmp237_AST = null;
				tmp237_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp237_AST);
				match(POUND);
				AST tmp238_AST = null;
				tmp238_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp238_AST);
				match(LPAREN);
				mintypmax_expression();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp239_AST = null;
				tmp239_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp239_AST);
				match(RPAREN);
				delay_control_AST = (AST)currentAST.root;
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_58);
			} else {
			  throw ex;
			}
		}
		returnAST = delay_control_AST;
	}
	
	public final void event_control() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST event_control_AST = null;
		
		try {      // for error handling
			if ((LA(1)==AT) && (LA(2)==IDENTIFIER||LA(2)==ESCAPED_IDENTIFIER||LA(2)==DOLLAR_IDENTIFIER)) {
				AST tmp240_AST = null;
				tmp240_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp240_AST);
				match(AT);
				name();
				astFactory.addASTChild(currentAST, returnAST);
				event_control_AST = (AST)currentAST.root;
			}
			else if ((LA(1)==AT) && (LA(2)==LPAREN)) {
				AST tmp241_AST = null;
				tmp241_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp241_AST);
				match(AT);
				AST tmp242_AST = null;
				tmp242_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp242_AST);
				match(LPAREN);
				event_expression();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp243_AST = null;
				tmp243_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp243_AST);
				match(RPAREN);
				if ( inputState.guessing==0 ) {
					event_control_AST = (AST)currentAST.root;
					event_control_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(EVENTCONTROL,"EventControl")).add(event_control_AST));
					currentAST.root = event_control_AST;
					currentAST.child = event_control_AST!=null &&event_control_AST.getFirstChild()!=null ?
						event_control_AST.getFirstChild() : event_control_AST;
					currentAST.advanceChildToEnd();
				}
				event_control_AST = (AST)currentAST.root;
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_58);
			} else {
			  throw ex;
			}
		}
		returnAST = event_control_AST;
	}
	
	public final void specify_entries() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST specify_entries_AST = null;
		
		try {      // for error handling
			{
			_loop229:
			do {
				if ((_tokenSet_69.member(LA(1)))) {
					{
					AST tmp244_AST = null;
					tmp244_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp244_AST);
					match(_tokenSet_69);
					}
				}
				else {
					break _loop229;
				}
				
			} while (true);
			}
			specify_entries_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_70);
			} else {
			  throw ex;
			}
		}
		returnAST = specify_entries_AST;
	}
	
	public final void specify_item() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST specify_item_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case LITERAL_specparam:
			{
				spec_param_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case LPAREN:
			case LITERAL_if:
			{
				path_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case 242:
			case 243:
			case 244:
			case 245:
			case 246:
			case 247:
			case 248:
			{
				system_timing_check();
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
				specify_item_AST = (AST)currentAST.root;
				specify_item_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(SPECIFYITEM,"SpecifyItem")).add(specify_item_AST));
				currentAST.root = specify_item_AST;
				currentAST.child = specify_item_AST!=null &&specify_item_AST.getFirstChild()!=null ?
					specify_item_AST.getFirstChild() : specify_item_AST;
				currentAST.advanceChildToEnd();
			}
			specify_item_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_0);
			} else {
			  throw ex;
			}
		}
		returnAST = specify_item_AST;
	}
	
	public final void spec_param_declaration() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST spec_param_declaration_AST = null;
		
		try {      // for error handling
			AST tmp245_AST = null;
			tmp245_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp245_AST);
			match(LITERAL_specparam);
			list_of_specparam_assignments();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp246_AST = null;
			tmp246_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp246_AST);
			match(SEMI);
			if ( inputState.guessing==0 ) {
				spec_param_declaration_AST = (AST)currentAST.root;
				spec_param_declaration_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(SPECPARAMDECLARATION,"SpecParamDeclaration")).add(spec_param_declaration_AST));
				currentAST.root = spec_param_declaration_AST;
				currentAST.child = spec_param_declaration_AST!=null &&spec_param_declaration_AST.getFirstChild()!=null ?
					spec_param_declaration_AST.getFirstChild() : spec_param_declaration_AST;
				currentAST.advanceChildToEnd();
			}
			spec_param_declaration_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_0);
			} else {
			  throw ex;
			}
		}
		returnAST = spec_param_declaration_AST;
	}
	
	public final void path_declaration() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST path_declaration_AST = null;
		
		try {      // for error handling
			{
			boolean synPredMatched242 = false;
			if (((LA(1)==LPAREN) && (LA(2)==IDENTIFIER||LA(2)==ESCAPED_IDENTIFIER||LA(2)==DOLLAR_IDENTIFIER))) {
				int _m242 = mark();
				synPredMatched242 = true;
				inputState.guessing++;
				try {
					{
					simple_path_declaration();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched242 = false;
				}
				rewind(_m242);
inputState.guessing--;
			}
			if ( synPredMatched242 ) {
				simple_path_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp247_AST = null;
				tmp247_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp247_AST);
				match(SEMI);
			}
			else {
				boolean synPredMatched244 = false;
				if (((LA(1)==LPAREN||LA(1)==LITERAL_if) && (_tokenSet_71.member(LA(2))))) {
					int _m244 = mark();
					synPredMatched244 = true;
					inputState.guessing++;
					try {
						{
						level_sensitive_path_declaration();
						}
					}
					catch (RecognitionException pe) {
						synPredMatched244 = false;
					}
					rewind(_m244);
inputState.guessing--;
				}
				if ( synPredMatched244 ) {
					level_sensitive_path_declaration();
					astFactory.addASTChild(currentAST, returnAST);
					AST tmp248_AST = null;
					tmp248_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp248_AST);
					match(SEMI);
				}
				else if ((LA(1)==LPAREN) && (_tokenSet_72.member(LA(2)))) {
					edge_sensitive_path_declaration();
					astFactory.addASTChild(currentAST, returnAST);
					AST tmp249_AST = null;
					tmp249_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp249_AST);
					match(SEMI);
				}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				if ( inputState.guessing==0 ) {
					path_declaration_AST = (AST)currentAST.root;
					path_declaration_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(PATHDECLARATION,"PathDeclaration")).add(path_declaration_AST));
					currentAST.root = path_declaration_AST;
					currentAST.child = path_declaration_AST!=null &&path_declaration_AST.getFirstChild()!=null ?
						path_declaration_AST.getFirstChild() : path_declaration_AST;
					currentAST.advanceChildToEnd();
				}
				path_declaration_AST = (AST)currentAST.root;
			}
			catch (RecognitionException ex) {
				if (inputState.guessing==0) {
					reportError(ex);
					recover(ex,_tokenSet_0);
				} else {
				  throw ex;
				}
			}
			returnAST = path_declaration_AST;
		}
		
	public final void system_timing_check() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST system_timing_check_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case 242:
			{
				AST tmp250_AST = null;
				tmp250_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp250_AST);
				match(242);
				AST tmp251_AST = null;
				tmp251_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp251_AST);
				match(LPAREN);
				timing_check_event();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp252_AST = null;
				tmp252_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp252_AST);
				match(COMMA);
				timing_check_event();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp253_AST = null;
				tmp253_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp253_AST);
				match(COMMA);
				timing_check_limit();
				astFactory.addASTChild(currentAST, returnAST);
				{
				switch ( LA(1)) {
				case COMMA:
				{
					AST tmp254_AST = null;
					tmp254_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp254_AST);
					match(COMMA);
					notify_register();
					astFactory.addASTChild(currentAST, returnAST);
					break;
				}
				case RPAREN:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				AST tmp255_AST = null;
				tmp255_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp255_AST);
				match(RPAREN);
				AST tmp256_AST = null;
				tmp256_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp256_AST);
				match(SEMI);
				system_timing_check_AST = (AST)currentAST.root;
				break;
			}
			case 243:
			{
				AST tmp257_AST = null;
				tmp257_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp257_AST);
				match(243);
				AST tmp258_AST = null;
				tmp258_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp258_AST);
				match(LPAREN);
				timing_check_event();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp259_AST = null;
				tmp259_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp259_AST);
				match(COMMA);
				timing_check_event();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp260_AST = null;
				tmp260_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp260_AST);
				match(COMMA);
				timing_check_limit();
				astFactory.addASTChild(currentAST, returnAST);
				{
				switch ( LA(1)) {
				case COMMA:
				{
					AST tmp261_AST = null;
					tmp261_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp261_AST);
					match(COMMA);
					notify_register();
					astFactory.addASTChild(currentAST, returnAST);
					break;
				}
				case RPAREN:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				AST tmp262_AST = null;
				tmp262_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp262_AST);
				match(RPAREN);
				AST tmp263_AST = null;
				tmp263_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp263_AST);
				match(SEMI);
				system_timing_check_AST = (AST)currentAST.root;
				break;
			}
			case 244:
			{
				AST tmp264_AST = null;
				tmp264_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp264_AST);
				match(244);
				AST tmp265_AST = null;
				tmp265_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp265_AST);
				match(LPAREN);
				controlled_timing_check_event();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp266_AST = null;
				tmp266_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp266_AST);
				match(COMMA);
				timing_check_limit();
				astFactory.addASTChild(currentAST, returnAST);
				{
				switch ( LA(1)) {
				case COMMA:
				{
					AST tmp267_AST = null;
					tmp267_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp267_AST);
					match(COMMA);
					notify_register();
					astFactory.addASTChild(currentAST, returnAST);
					break;
				}
				case RPAREN:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				AST tmp268_AST = null;
				tmp268_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp268_AST);
				match(RPAREN);
				AST tmp269_AST = null;
				tmp269_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp269_AST);
				match(SEMI);
				system_timing_check_AST = (AST)currentAST.root;
				break;
			}
			case 245:
			{
				AST tmp270_AST = null;
				tmp270_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp270_AST);
				match(245);
				AST tmp271_AST = null;
				tmp271_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp271_AST);
				match(LPAREN);
				controlled_timing_check_event();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp272_AST = null;
				tmp272_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp272_AST);
				match(COMMA);
				timing_check_limit();
				astFactory.addASTChild(currentAST, returnAST);
				{
				switch ( LA(1)) {
				case COMMA:
				{
					AST tmp273_AST = null;
					tmp273_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp273_AST);
					match(COMMA);
					expression();
					astFactory.addASTChild(currentAST, returnAST);
					AST tmp274_AST = null;
					tmp274_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp274_AST);
					match(COMMA);
					notify_register();
					astFactory.addASTChild(currentAST, returnAST);
					break;
				}
				case RPAREN:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				AST tmp275_AST = null;
				tmp275_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp275_AST);
				match(RPAREN);
				AST tmp276_AST = null;
				tmp276_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp276_AST);
				match(SEMI);
				system_timing_check_AST = (AST)currentAST.root;
				break;
			}
			case 246:
			{
				AST tmp277_AST = null;
				tmp277_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp277_AST);
				match(246);
				AST tmp278_AST = null;
				tmp278_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp278_AST);
				match(LPAREN);
				timing_check_event();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp279_AST = null;
				tmp279_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp279_AST);
				match(COMMA);
				timing_check_event();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp280_AST = null;
				tmp280_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp280_AST);
				match(COMMA);
				timing_check_limit();
				astFactory.addASTChild(currentAST, returnAST);
				{
				switch ( LA(1)) {
				case COMMA:
				{
					AST tmp281_AST = null;
					tmp281_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp281_AST);
					match(COMMA);
					notify_register();
					astFactory.addASTChild(currentAST, returnAST);
					break;
				}
				case RPAREN:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				AST tmp282_AST = null;
				tmp282_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp282_AST);
				match(RPAREN);
				AST tmp283_AST = null;
				tmp283_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp283_AST);
				match(SEMI);
				system_timing_check_AST = (AST)currentAST.root;
				break;
			}
			case 247:
			{
				AST tmp284_AST = null;
				tmp284_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp284_AST);
				match(247);
				AST tmp285_AST = null;
				tmp285_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp285_AST);
				match(LPAREN);
				controlled_timing_check_event();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp286_AST = null;
				tmp286_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp286_AST);
				match(COMMA);
				timing_check_event();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp287_AST = null;
				tmp287_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp287_AST);
				match(COMMA);
				timing_check_limit();
				astFactory.addASTChild(currentAST, returnAST);
				{
				switch ( LA(1)) {
				case COMMA:
				{
					AST tmp288_AST = null;
					tmp288_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp288_AST);
					match(COMMA);
					notify_register();
					astFactory.addASTChild(currentAST, returnAST);
					break;
				}
				case RPAREN:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				AST tmp289_AST = null;
				tmp289_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp289_AST);
				match(RPAREN);
				AST tmp290_AST = null;
				tmp290_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp290_AST);
				match(SEMI);
				system_timing_check_AST = (AST)currentAST.root;
				break;
			}
			case 248:
			{
				AST tmp291_AST = null;
				tmp291_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp291_AST);
				match(248);
				AST tmp292_AST = null;
				tmp292_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp292_AST);
				match(LPAREN);
				timing_check_event();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp293_AST = null;
				tmp293_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp293_AST);
				match(COMMA);
				timing_check_event();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp294_AST = null;
				tmp294_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp294_AST);
				match(COMMA);
				timing_check_limit();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp295_AST = null;
				tmp295_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp295_AST);
				match(COMMA);
				timing_check_limit();
				astFactory.addASTChild(currentAST, returnAST);
				{
				switch ( LA(1)) {
				case COMMA:
				{
					AST tmp296_AST = null;
					tmp296_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp296_AST);
					match(COMMA);
					notify_register();
					astFactory.addASTChild(currentAST, returnAST);
					break;
				}
				case RPAREN:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				AST tmp297_AST = null;
				tmp297_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp297_AST);
				match(RPAREN);
				AST tmp298_AST = null;
				tmp298_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp298_AST);
				match(SEMI);
				system_timing_check_AST = (AST)currentAST.root;
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
		returnAST = system_timing_check_AST;
	}
	
	public final void list_of_specparam_assignments() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST list_of_specparam_assignments_AST = null;
		
		try {      // for error handling
			specparam_assignment();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop237:
			do {
				if ((LA(1)==COMMA)) {
					AST tmp299_AST = null;
					tmp299_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp299_AST);
					match(COMMA);
					specparam_assignment();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop237;
				}
				
			} while (true);
			}
			list_of_specparam_assignments_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_9);
			} else {
			  throw ex;
			}
		}
		returnAST = list_of_specparam_assignments_AST;
	}
	
	public final void specparam_assignment() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST specparam_assignment_AST = null;
		
		try {      // for error handling
			identifier();
			astFactory.addASTChild(currentAST, returnAST);
			assign_op();
			astFactory.addASTChild(currentAST, returnAST);
			expression();
			astFactory.addASTChild(currentAST, returnAST);
			specparam_assignment_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_34);
			} else {
			  throw ex;
			}
		}
		returnAST = specparam_assignment_AST;
	}
	
	public final void simple_path_declaration() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST simple_path_declaration_AST = null;
		
		try {      // for error handling
			{
			boolean synPredMatched252 = false;
			if (((LA(1)==LPAREN) && (LA(2)==IDENTIFIER||LA(2)==ESCAPED_IDENTIFIER||LA(2)==DOLLAR_IDENTIFIER))) {
				int _m252 = mark();
				synPredMatched252 = true;
				inputState.guessing++;
				try {
					{
					parallel_path_description();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched252 = false;
				}
				rewind(_m252);
inputState.guessing--;
			}
			if ( synPredMatched252 ) {
				parallel_path_description();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp300_AST = null;
				tmp300_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp300_AST);
				match(ASSIGN);
				path_delay_value();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else if ((LA(1)==LPAREN) && (LA(2)==IDENTIFIER||LA(2)==ESCAPED_IDENTIFIER||LA(2)==DOLLAR_IDENTIFIER)) {
				full_path_descriptor();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp301_AST = null;
				tmp301_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp301_AST);
				match(ASSIGN);
				path_delay_value();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			if ( inputState.guessing==0 ) {
				simple_path_declaration_AST = (AST)currentAST.root;
				simple_path_declaration_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(SIMPLEPATHDECLARATION,"SimplePathDeclaration")).add(simple_path_declaration_AST));
				currentAST.root = simple_path_declaration_AST;
				currentAST.child = simple_path_declaration_AST!=null &&simple_path_declaration_AST.getFirstChild()!=null ?
					simple_path_declaration_AST.getFirstChild() : simple_path_declaration_AST;
				currentAST.advanceChildToEnd();
			}
			simple_path_declaration_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_9);
			} else {
			  throw ex;
			}
		}
		returnAST = simple_path_declaration_AST;
	}
	
	public final void level_sensitive_path_declaration() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST level_sensitive_path_declaration_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case LITERAL_if:
			{
				AST tmp302_AST = null;
				tmp302_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp302_AST);
				match(LITERAL_if);
				AST tmp303_AST = null;
				tmp303_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp303_AST);
				match(LPAREN);
				expression();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp304_AST = null;
				tmp304_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp304_AST);
				match(RPAREN);
				simple_path_declaration();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case LPAREN:
			{
				edge_sensitive_path_declaration();
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
				level_sensitive_path_declaration_AST = (AST)currentAST.root;
				level_sensitive_path_declaration_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(LEVELSENSITIVEPATHDECLARATION,"LevelSensitivePathDeclaration")).add(level_sensitive_path_declaration_AST));
				currentAST.root = level_sensitive_path_declaration_AST;
				currentAST.child = level_sensitive_path_declaration_AST!=null &&level_sensitive_path_declaration_AST.getFirstChild()!=null ?
					level_sensitive_path_declaration_AST.getFirstChild() : level_sensitive_path_declaration_AST;
				currentAST.advanceChildToEnd();
			}
			level_sensitive_path_declaration_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_9);
			} else {
			  throw ex;
			}
		}
		returnAST = level_sensitive_path_declaration_AST;
	}
	
	public final void edge_sensitive_path_declaration() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST edge_sensitive_path_declaration_AST = null;
		
		try {      // for error handling
			AST tmp305_AST = null;
			tmp305_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp305_AST);
			match(LPAREN);
			{
			switch ( LA(1)) {
			case LITERAL_posedge:
			case LITERAL_negedge:
			{
				edge_identifier();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case IDENTIFIER:
			case ESCAPED_IDENTIFIER:
			case DOLLAR_IDENTIFIER:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			specify_terminal_descriptor();
			astFactory.addASTChild(currentAST, returnAST);
			{
			switch ( LA(1)) {
			case PPATH:
			{
				AST tmp306_AST = null;
				tmp306_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp306_AST);
				match(PPATH);
				break;
			}
			case FPATH:
			{
				AST tmp307_AST = null;
				tmp307_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp307_AST);
				match(FPATH);
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
			{
				AST tmp308_AST = null;
				tmp308_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp308_AST);
				match(LPAREN);
				break;
			}
			case IDENTIFIER:
			case ESCAPED_IDENTIFIER:
			case DOLLAR_IDENTIFIER:
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
			boolean synPredMatched299 = false;
			if (((LA(1)==IDENTIFIER||LA(1)==ESCAPED_IDENTIFIER||LA(1)==DOLLAR_IDENTIFIER) && (_tokenSet_73.member(LA(2))))) {
				int _m299 = mark();
				synPredMatched299 = true;
				inputState.guessing++;
				try {
					{
					list_of_path_terminals();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched299 = false;
				}
				rewind(_m299);
inputState.guessing--;
			}
			if ( synPredMatched299 ) {
				list_of_path_terminals();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else if ((LA(1)==IDENTIFIER||LA(1)==ESCAPED_IDENTIFIER||LA(1)==DOLLAR_IDENTIFIER) && (_tokenSet_74.member(LA(2)))) {
				specify_terminal_descriptor();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			{
			switch ( LA(1)) {
			case PLUS:
			case MINUS:
			{
				polarity_operator();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case RPAREN:
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
			{
			switch ( LA(1)) {
			case COLON:
			{
				AST tmp309_AST = null;
				tmp309_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp309_AST);
				match(COLON);
				data_source_expression();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case RPAREN:
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
			if ((LA(1)==RPAREN) && (LA(2)==RPAREN)) {
				AST tmp310_AST = null;
				tmp310_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp310_AST);
				match(RPAREN);
			}
			else if ((LA(1)==RPAREN) && (LA(2)==ASSIGN)) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			AST tmp311_AST = null;
			tmp311_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp311_AST);
			match(RPAREN);
			AST tmp312_AST = null;
			tmp312_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp312_AST);
			match(ASSIGN);
			path_delay_value();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp313_AST = null;
			tmp313_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp313_AST);
			match(SEMI);
			if ( inputState.guessing==0 ) {
				edge_sensitive_path_declaration_AST = (AST)currentAST.root;
				edge_sensitive_path_declaration_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(EDGESENSITIVEPATHDECLARATION,"EdgeSensitivePathDeclaration")).add(edge_sensitive_path_declaration_AST));
				currentAST.root = edge_sensitive_path_declaration_AST;
				currentAST.child = edge_sensitive_path_declaration_AST!=null &&edge_sensitive_path_declaration_AST.getFirstChild()!=null ?
					edge_sensitive_path_declaration_AST.getFirstChild() : edge_sensitive_path_declaration_AST;
				currentAST.advanceChildToEnd();
			}
			edge_sensitive_path_declaration_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_9);
			} else {
			  throw ex;
			}
		}
		returnAST = edge_sensitive_path_declaration_AST;
	}
	
	public final void parallel_path_description() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST parallel_path_description_AST = null;
		
		try {      // for error handling
			AST tmp314_AST = null;
			tmp314_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp314_AST);
			match(LPAREN);
			specify_terminal_descriptor();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp315_AST = null;
			tmp315_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp315_AST);
			match(PPATH);
			specify_terminal_descriptor();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp316_AST = null;
			tmp316_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp316_AST);
			match(RPAREN);
			if ( inputState.guessing==0 ) {
				parallel_path_description_AST = (AST)currentAST.root;
				parallel_path_description_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(PARALLELPATHDECLARATION,"ParallelPathDeclaration")).add(parallel_path_description_AST));
				currentAST.root = parallel_path_description_AST;
				currentAST.child = parallel_path_description_AST!=null &&parallel_path_description_AST.getFirstChild()!=null ?
					parallel_path_description_AST.getFirstChild() : parallel_path_description_AST;
				currentAST.advanceChildToEnd();
			}
			parallel_path_description_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_75);
			} else {
			  throw ex;
			}
		}
		returnAST = parallel_path_description_AST;
	}
	
	public final void path_delay_value() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST path_delay_value_AST = null;
		
		try {      // for error handling
			boolean synPredMatched265 = false;
			if (((_tokenSet_76.member(LA(1))) && (_tokenSet_77.member(LA(2))))) {
				int _m265 = mark();
				synPredMatched265 = true;
				inputState.guessing++;
				try {
					{
					path_delay_expression();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched265 = false;
				}
				rewind(_m265);
inputState.guessing--;
			}
			if ( synPredMatched265 ) {
				path_delay_expression();
				astFactory.addASTChild(currentAST, returnAST);
				path_delay_value_AST = (AST)currentAST.root;
			}
			else if ((LA(1)==LPAREN) && (_tokenSet_76.member(LA(2)))) {
				AST tmp317_AST = null;
				tmp317_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp317_AST);
				match(LPAREN);
				list_of_path_delay_expressions();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp318_AST = null;
				tmp318_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp318_AST);
				match(RPAREN);
				path_delay_value_AST = (AST)currentAST.root;
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_9);
			} else {
			  throw ex;
			}
		}
		returnAST = path_delay_value_AST;
	}
	
	public final void full_path_descriptor() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST full_path_descriptor_AST = null;
		
		try {      // for error handling
			AST tmp319_AST = null;
			tmp319_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp319_AST);
			match(LPAREN);
			list_of_path_terminals();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp320_AST = null;
			tmp320_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp320_AST);
			match(FPATH);
			list_of_path_terminals();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp321_AST = null;
			tmp321_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp321_AST);
			match(RPAREN);
			if ( inputState.guessing==0 ) {
				full_path_descriptor_AST = (AST)currentAST.root;
				full_path_descriptor_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(FULLPATHDESCRIPTOR,"FullPathDescriptor")).add(full_path_descriptor_AST));
				currentAST.root = full_path_descriptor_AST;
				currentAST.child = full_path_descriptor_AST!=null &&full_path_descriptor_AST.getFirstChild()!=null ?
					full_path_descriptor_AST.getFirstChild() : full_path_descriptor_AST;
				currentAST.advanceChildToEnd();
			}
			full_path_descriptor_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_75);
			} else {
			  throw ex;
			}
		}
		returnAST = full_path_descriptor_AST;
	}
	
	public final void specify_terminal_descriptor() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST specify_terminal_descriptor_AST = null;
		
		try {      // for error handling
			boolean synPredMatched260 = false;
			if (((LA(1)==IDENTIFIER||LA(1)==ESCAPED_IDENTIFIER||LA(1)==DOLLAR_IDENTIFIER) && (LA(2)==LBRACK))) {
				int _m260 = mark();
				synPredMatched260 = true;
				inputState.guessing++;
				try {
					{
					identifier();
					match(LBRACK);
					expression();
					match(COLON);
					}
				}
				catch (RecognitionException pe) {
					synPredMatched260 = false;
				}
				rewind(_m260);
inputState.guessing--;
			}
			if ( synPredMatched260 ) {
				identifier();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp322_AST = null;
				tmp322_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp322_AST);
				match(LBRACK);
				expression();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp323_AST = null;
				tmp323_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp323_AST);
				match(COLON);
				expression();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp324_AST = null;
				tmp324_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp324_AST);
				match(RBRACK);
				specify_terminal_descriptor_AST = (AST)currentAST.root;
			}
			else {
				boolean synPredMatched262 = false;
				if (((LA(1)==IDENTIFIER||LA(1)==ESCAPED_IDENTIFIER||LA(1)==DOLLAR_IDENTIFIER) && (LA(2)==LBRACK))) {
					int _m262 = mark();
					synPredMatched262 = true;
					inputState.guessing++;
					try {
						{
						identifier();
						match(LBRACK);
						}
					}
					catch (RecognitionException pe) {
						synPredMatched262 = false;
					}
					rewind(_m262);
inputState.guessing--;
				}
				if ( synPredMatched262 ) {
					identifier();
					astFactory.addASTChild(currentAST, returnAST);
					AST tmp325_AST = null;
					tmp325_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp325_AST);
					match(LBRACK);
					expression();
					astFactory.addASTChild(currentAST, returnAST);
					AST tmp326_AST = null;
					tmp326_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp326_AST);
					match(RBRACK);
					specify_terminal_descriptor_AST = (AST)currentAST.root;
				}
				else if ((LA(1)==IDENTIFIER||LA(1)==ESCAPED_IDENTIFIER||LA(1)==DOLLAR_IDENTIFIER) && (_tokenSet_78.member(LA(2)))) {
					identifier();
					astFactory.addASTChild(currentAST, returnAST);
					specify_terminal_descriptor_AST = (AST)currentAST.root;
				}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
			catch (RecognitionException ex) {
				if (inputState.guessing==0) {
					reportError(ex);
					recover(ex,_tokenSet_78);
				} else {
				  throw ex;
				}
			}
			returnAST = specify_terminal_descriptor_AST;
		}
		
	public final void list_of_path_terminals() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST list_of_path_terminals_AST = null;
		
		try {      // for error handling
			specify_terminal_descriptor();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop257:
			do {
				if ((LA(1)==COMMA)) {
					AST tmp327_AST = null;
					tmp327_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp327_AST);
					match(COMMA);
					specify_terminal_descriptor();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop257;
				}
				
			} while (true);
			}
			list_of_path_terminals_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_79);
			} else {
			  throw ex;
			}
		}
		returnAST = list_of_path_terminals_AST;
	}
	
	public final void path_delay_expression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST path_delay_expression_AST = null;
		
		try {      // for error handling
			mintypmax_expression();
			astFactory.addASTChild(currentAST, returnAST);
			path_delay_expression_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_44);
			} else {
			  throw ex;
			}
		}
		returnAST = path_delay_expression_AST;
	}
	
	public final void list_of_path_delay_expressions() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST list_of_path_delay_expressions_AST = null;
		
		try {      // for error handling
			path_delay_expression();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp328_AST = null;
			tmp328_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp328_AST);
			match(COMMA);
			path_delay_expression();
			astFactory.addASTChild(currentAST, returnAST);
			{
			switch ( LA(1)) {
			case COMMA:
			{
				AST tmp329_AST = null;
				tmp329_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp329_AST);
				match(COMMA);
				path_delay_expression();
				astFactory.addASTChild(currentAST, returnAST);
				{
				switch ( LA(1)) {
				case COMMA:
				{
					AST tmp330_AST = null;
					tmp330_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp330_AST);
					match(COMMA);
					path_delay_expression();
					astFactory.addASTChild(currentAST, returnAST);
					AST tmp331_AST = null;
					tmp331_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp331_AST);
					match(COMMA);
					path_delay_expression();
					astFactory.addASTChild(currentAST, returnAST);
					AST tmp332_AST = null;
					tmp332_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp332_AST);
					match(COMMA);
					path_delay_expression();
					astFactory.addASTChild(currentAST, returnAST);
					break;
				}
				case RPAREN:
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
			case RPAREN:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			list_of_path_delay_expressions_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_11);
			} else {
			  throw ex;
			}
		}
		returnAST = list_of_path_delay_expressions_AST;
	}
	
	public final void timing_check_event() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST timing_check_event_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case LITERAL_posedge:
			case LITERAL_negedge:
			case LITERAL_edge:
			{
				timing_check_event_control();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case IDENTIFIER:
			case ESCAPED_IDENTIFIER:
			case DOLLAR_IDENTIFIER:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			specify_terminal_descriptor();
			astFactory.addASTChild(currentAST, returnAST);
			{
			switch ( LA(1)) {
			case 249:
			{
				AST tmp333_AST = null;
				tmp333_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp333_AST);
				match(249);
				timing_check_condition();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
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
			timing_check_event_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_80);
			} else {
			  throw ex;
			}
		}
		returnAST = timing_check_event_AST;
	}
	
	public final void timing_check_limit() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST timing_check_limit_AST = null;
		
		try {      // for error handling
			expression();
			astFactory.addASTChild(currentAST, returnAST);
			timing_check_limit_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_12);
			} else {
			  throw ex;
			}
		}
		returnAST = timing_check_limit_AST;
	}
	
	public final void notify_register() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST notify_register_AST = null;
		
		try {      // for error handling
			name_of_register();
			astFactory.addASTChild(currentAST, returnAST);
			notify_register_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_11);
			} else {
			  throw ex;
			}
		}
		returnAST = notify_register_AST;
	}
	
	public final void controlled_timing_check_event() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST controlled_timing_check_event_AST = null;
		
		try {      // for error handling
			timing_check_event_control();
			astFactory.addASTChild(currentAST, returnAST);
			specify_terminal_descriptor();
			astFactory.addASTChild(currentAST, returnAST);
			{
			switch ( LA(1)) {
			case 249:
			{
				AST tmp334_AST = null;
				tmp334_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp334_AST);
				match(249);
				timing_check_condition();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
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
			controlled_timing_check_event_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_80);
			} else {
			  throw ex;
			}
		}
		returnAST = controlled_timing_check_event_AST;
	}
	
	public final void timing_check_event_control() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST timing_check_event_control_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_posedge:
			{
				AST tmp335_AST = null;
				tmp335_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp335_AST);
				match(LITERAL_posedge);
				timing_check_event_control_AST = (AST)currentAST.root;
				break;
			}
			case LITERAL_negedge:
			{
				AST tmp336_AST = null;
				tmp336_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp336_AST);
				match(LITERAL_negedge);
				timing_check_event_control_AST = (AST)currentAST.root;
				break;
			}
			case LITERAL_edge:
			{
				edge_control_specifier();
				astFactory.addASTChild(currentAST, returnAST);
				timing_check_event_control_AST = (AST)currentAST.root;
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
				recover(ex,_tokenSet_29);
			} else {
			  throw ex;
			}
		}
		returnAST = timing_check_event_control_AST;
	}
	
	public final void timing_check_condition() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST timing_check_condition_AST = null;
		
		try {      // for error handling
			scalar_timing_check_condition();
			astFactory.addASTChild(currentAST, returnAST);
			timing_check_condition_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_80);
			} else {
			  throw ex;
			}
		}
		returnAST = timing_check_condition_AST;
	}
	
	public final void edge_control_specifier() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST edge_control_specifier_AST = null;
		
		try {      // for error handling
			AST tmp337_AST = null;
			tmp337_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp337_AST);
			match(LITERAL_edge);
			AST tmp338_AST = null;
			tmp338_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp338_AST);
			match(LBRACK);
			edge_descriptor();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop286:
			do {
				if ((LA(1)==COMMA)) {
					AST tmp339_AST = null;
					tmp339_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp339_AST);
					match(COMMA);
					edge_descriptor();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop286;
				}
				
			} while (true);
			}
			AST tmp340_AST = null;
			tmp340_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp340_AST);
			match(RBRACK);
			edge_control_specifier_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_29);
			} else {
			  throw ex;
			}
		}
		returnAST = edge_control_specifier_AST;
	}
	
	public final void edge_descriptor() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST edge_descriptor_AST = null;
		Token  n = null;
		AST n_AST = null;
		Token  i = null;
		AST i_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case 253:
			{
				AST tmp341_AST = null;
				tmp341_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp341_AST);
				match(253);
				edge_descriptor_AST = (AST)currentAST.root;
				break;
			}
			case 254:
			{
				AST tmp342_AST = null;
				tmp342_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp342_AST);
				match(254);
				edge_descriptor_AST = (AST)currentAST.root;
				break;
			}
			case NUMBER:
			{
				n = LT(1);
				n_AST = astFactory.create(n);
				astFactory.addASTChild(currentAST, n_AST);
				match(NUMBER);
				if (!( n.getText()=="01" || n.getText()=="10"))
				  throw new SemanticException(" n.getText()==\"01\" || n.getText()==\"10\"");
				edge_descriptor_AST = (AST)currentAST.root;
				break;
			}
			case IDENTIFIER:
			{
				i = LT(1);
				i_AST = astFactory.create(i);
				astFactory.addASTChild(currentAST, i_AST);
				match(IDENTIFIER);
				if (!( i.getText()=="x1" || i.getText()=="x0"))
				  throw new SemanticException(" i.getText()==\"x1\" || i.getText()==\"x0\"");
				edge_descriptor_AST = (AST)currentAST.root;
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
		returnAST = edge_descriptor_AST;
	}
	
	public final void scalar_timing_check_condition() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST scalar_timing_check_condition_AST = null;
		
		try {      // for error handling
			expression();
			astFactory.addASTChild(currentAST, returnAST);
			scalar_timing_check_condition_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_80);
			} else {
			  throw ex;
			}
		}
		returnAST = scalar_timing_check_condition_AST;
	}
	
	public final void name_of_register() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST name_of_register_AST = null;
		
		try {      // for error handling
			local_identifier();
			astFactory.addASTChild(currentAST, returnAST);
			name_of_register_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_11);
			} else {
			  throw ex;
			}
		}
		returnAST = name_of_register_AST;
	}
	
	public final void polarity_operator() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST polarity_operator_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case PLUS:
			{
				AST tmp343_AST = null;
				tmp343_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp343_AST);
				match(PLUS);
				polarity_operator_AST = (AST)currentAST.root;
				break;
			}
			case MINUS:
			{
				AST tmp344_AST = null;
				tmp344_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp344_AST);
				match(MINUS);
				polarity_operator_AST = (AST)currentAST.root;
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
		returnAST = polarity_operator_AST;
	}
	
	public final void edge_identifier() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST edge_identifier_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_posedge:
			{
				AST tmp345_AST = null;
				tmp345_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp345_AST);
				match(LITERAL_posedge);
				edge_identifier_AST = (AST)currentAST.root;
				break;
			}
			case LITERAL_negedge:
			{
				AST tmp346_AST = null;
				tmp346_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp346_AST);
				match(LITERAL_negedge);
				edge_identifier_AST = (AST)currentAST.root;
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
				recover(ex,_tokenSet_29);
			} else {
			  throw ex;
			}
		}
		returnAST = edge_identifier_AST;
	}
	
	public final void data_source_expression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST data_source_expression_AST = null;
		
		try {      // for error handling
			expression();
			astFactory.addASTChild(currentAST, returnAST);
			data_source_expression_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_11);
			} else {
			  throw ex;
			}
		}
		returnAST = data_source_expression_AST;
	}
	
	public final void sdpd() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST sdpd_AST = null;
		
		try {      // for error handling
			AST tmp347_AST = null;
			tmp347_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp347_AST);
			match(LITERAL_if);
			AST tmp348_AST = null;
			tmp348_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp348_AST);
			match(LPAREN);
			expression();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp349_AST = null;
			tmp349_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp349_AST);
			match(RPAREN);
			{
			simple_path_declaration();
			astFactory.addASTChild(currentAST, returnAST);
			}
			AST tmp350_AST = null;
			tmp350_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp350_AST);
			match(SEMI);
			if ( inputState.guessing==0 ) {
				sdpd_AST = (AST)currentAST.root;
				sdpd_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(SDPD,"Sdpd")).add(sdpd_AST));
				currentAST.root = sdpd_AST;
				currentAST.child = sdpd_AST!=null &&sdpd_AST.getFirstChild()!=null ?
					sdpd_AST.getFirstChild() : sdpd_AST;
				currentAST.advanceChildToEnd();
			}
			sdpd_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_0);
			} else {
			  throw ex;
			}
		}
		returnAST = sdpd_AST;
	}
	
	public final void concatenation() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST concatenation_AST = null;
		
		try {      // for error handling
			AST tmp351_AST = null;
			tmp351_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp351_AST);
			match(LCURLY);
			concat_expr();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp352_AST = null;
			tmp352_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp352_AST);
			match(RCURLY);
			if ( inputState.guessing==0 ) {
				concatenation_AST = (AST)currentAST.root;
				concatenation_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(CONCAT,"Concat")).add(concatenation_AST));
				currentAST.root = concatenation_AST;
				currentAST.child = concatenation_AST!=null &&concatenation_AST.getFirstChild()!=null ?
					concatenation_AST.getFirstChild() : concatenation_AST;
				currentAST.advanceChildToEnd();
			}
			concatenation_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_49);
			} else {
			  throw ex;
			}
		}
		returnAST = concatenation_AST;
	}
	
	public final void inc_dec_expression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST inc_dec_expression_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case PLUSPLUS:
			case MINMIN:
			{
				inc_or_dec_operator();
				astFactory.addASTChild(currentAST, returnAST);
				lvalue();
				astFactory.addASTChild(currentAST, returnAST);
				inc_dec_expression_AST = (AST)currentAST.root;
				break;
			}
			case IDENTIFIER:
			case LCURLY:
			case ESCAPED_IDENTIFIER:
			case DOLLAR_IDENTIFIER:
			{
				lvalue();
				astFactory.addASTChild(currentAST, returnAST);
				inc_or_dec_operator();
				astFactory.addASTChild(currentAST, returnAST);
				inc_dec_expression_AST = (AST)currentAST.root;
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
				recover(ex,_tokenSet_83);
			} else {
			  throw ex;
			}
		}
		returnAST = inc_dec_expression_AST;
	}
	
	public final void inc_or_dec_operator() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST inc_or_dec_operator_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case PLUSPLUS:
			{
				AST tmp353_AST = null;
				tmp353_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp353_AST);
				match(PLUSPLUS);
				inc_or_dec_operator_AST = (AST)currentAST.root;
				break;
			}
			case MINMIN:
			{
				AST tmp354_AST = null;
				tmp354_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp354_AST);
				match(MINMIN);
				inc_or_dec_operator_AST = (AST)currentAST.root;
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
				recover(ex,_tokenSet_84);
			} else {
			  throw ex;
			}
		}
		returnAST = inc_or_dec_operator_AST;
	}
	
	public final void segment_range() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST segment_range_AST = null;
		
		try {      // for error handling
			range();
			astFactory.addASTChild(currentAST, returnAST);
			segment_range_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_15);
			} else {
			  throw ex;
			}
		}
		returnAST = segment_range_AST;
	}
	
	public final void concat_expr() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST concat_expr_AST = null;
		
		try {      // for error handling
			boolean synPredMatched314 = false;
			if (((_tokenSet_76.member(LA(1))) && (_tokenSet_85.member(LA(2))))) {
				int _m314 = mark();
				synPredMatched314 = true;
				inputState.guessing++;
				try {
					{
					concat_number();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched314 = false;
				}
				rewind(_m314);
inputState.guessing--;
			}
			if ( synPredMatched314 ) {
				concat_number();
				astFactory.addASTChild(currentAST, returnAST);
				concat_expr_AST = (AST)currentAST.root;
			}
			else if ((_tokenSet_76.member(LA(1))) && (_tokenSet_86.member(LA(2)))) {
				concat_normal();
				astFactory.addASTChild(currentAST, returnAST);
				concat_expr_AST = (AST)currentAST.root;
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_87);
			} else {
			  throw ex;
			}
		}
		returnAST = concat_expr_AST;
	}
	
	public final void concat_number() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST concat_number_AST = null;
		
		try {      // for error handling
			expression();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp355_AST = null;
			tmp355_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp355_AST);
			match(LCURLY);
			expression();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp356_AST = null;
			tmp356_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp356_AST);
			match(RCURLY);
			concat_number_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_87);
			} else {
			  throw ex;
			}
		}
		returnAST = concat_number_AST;
	}
	
	public final void concat_normal() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST concat_normal_AST = null;
		
		try {      // for error handling
			expression();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop317:
			do {
				if ((LA(1)==COMMA)) {
					AST tmp357_AST = null;
					tmp357_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp357_AST);
					match(COMMA);
					expression();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop317;
				}
				
			} while (true);
			}
			concat_normal_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_87);
			} else {
			  throw ex;
			}
		}
		returnAST = concat_normal_AST;
	}
	
	public final void exp11() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST exp11_AST = null;
		
		try {      // for error handling
			primary();
			astFactory.addASTChild(currentAST, returnAST);
			exp11_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_83);
			} else {
			  throw ex;
			}
		}
		returnAST = exp11_AST;
	}
	
	public final void primary() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST primary_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case NUMBER:
			{
				number();
				astFactory.addASTChild(currentAST, returnAST);
				primary_AST = (AST)currentAST.root;
				break;
			}
			case IDENTIFIER:
			case LCURLY:
			case PLUSPLUS:
			case MINMIN:
			case ESCAPED_IDENTIFIER:
			case DOLLAR_IDENTIFIER:
			{
				inc_dec_expression();
				astFactory.addASTChild(currentAST, returnAST);
				primary_AST = (AST)currentAST.root;
				break;
			}
			case STRING:
			{
				AST tmp358_AST = null;
				tmp358_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp358_AST);
				match(STRING);
				primary_AST = (AST)currentAST.root;
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
				recover(ex,_tokenSet_83);
			} else {
			  throw ex;
			}
		}
		returnAST = primary_AST;
	}
	
	public final void exp10() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST exp10_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case NUMBER:
			case IDENTIFIER:
			case LCURLY:
			case STRING:
			case PLUSPLUS:
			case MINMIN:
			case ESCAPED_IDENTIFIER:
			case DOLLAR_IDENTIFIER:
			{
				exp11();
				astFactory.addASTChild(currentAST, returnAST);
				exp10_AST = (AST)currentAST.root;
				break;
			}
			case LPAREN:
			{
				parop();
				astFactory.addASTChild(currentAST, returnAST);
				exp10_AST = (AST)currentAST.root;
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
				recover(ex,_tokenSet_83);
			} else {
			  throw ex;
			}
		}
		returnAST = exp10_AST;
	}
	
	public final void exp9() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST exp9_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case LPAREN:
			case NUMBER:
			case IDENTIFIER:
			case LCURLY:
			case STRING:
			case PLUSPLUS:
			case MINMIN:
			case ESCAPED_IDENTIFIER:
			case DOLLAR_IDENTIFIER:
			{
				exp10();
				astFactory.addASTChild(currentAST, returnAST);
				exp9_AST = (AST)currentAST.root;
				break;
			}
			case PLUS:
			case MINUS:
			case LNOT:
			case BNOT:
			case BAND:
			case RNAND:
			case BOR:
			case RNOR:
			case BXOR:
			case RXNOR:
			{
				unop();
				astFactory.addASTChild(currentAST, returnAST);
				exp9_AST = (AST)currentAST.root;
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
				recover(ex,_tokenSet_83);
			} else {
			  throw ex;
			}
		}
		returnAST = exp9_AST;
	}
	
	public final void unop() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST unop_AST = null;
		
		try {      // for error handling
			unary_operator();
			astFactory.addASTChild(currentAST, returnAST);
			exp9();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				unop_AST = (AST)currentAST.root;
				unop_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(UNOP,"UnOp")).add(unop_AST));
				currentAST.root = unop_AST;
				currentAST.child = unop_AST!=null &&unop_AST.getFirstChild()!=null ?
					unop_AST.getFirstChild() : unop_AST;
				currentAST.advanceChildToEnd();
			}
			unop_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_83);
			} else {
			  throw ex;
			}
		}
		returnAST = unop_AST;
	}
	
	public final void exp8() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST exp8_AST = null;
		
		try {      // for error handling
			exp9();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop331:
			do {
				if ((_tokenSet_88.member(LA(1))) && (_tokenSet_76.member(LA(2)))) {
					binop();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop331;
				}
				
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				exp8_AST = (AST)currentAST.root;
				exp8_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(BINOP,"BinOp")).add(exp8_AST));
				currentAST.root = exp8_AST;
				currentAST.child = exp8_AST!=null &&exp8_AST.getFirstChild()!=null ?
					exp8_AST.getFirstChild() : exp8_AST;
				currentAST.advanceChildToEnd();
			}
			exp8_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_89);
			} else {
			  throw ex;
			}
		}
		returnAST = exp8_AST;
	}
	
	public final void binop() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST binop_AST = null;
		
		try {      // for error handling
			binary_operator();
			astFactory.addASTChild(currentAST, returnAST);
			exp9();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				binop_AST = (AST)currentAST.root;
				binop_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(BINOPSMALL,"BinOpSmall")).add(binop_AST));
				currentAST.root = binop_AST;
				currentAST.child = binop_AST!=null &&binop_AST.getFirstChild()!=null ?
					binop_AST.getFirstChild() : binop_AST;
				currentAST.advanceChildToEnd();
			}
			binop_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_83);
			} else {
			  throw ex;
			}
		}
		returnAST = binop_AST;
	}
	
	public final void exp7() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST exp7_AST = null;
		
		try {      // for error handling
			exp8();
			astFactory.addASTChild(currentAST, returnAST);
			{
			switch ( LA(1)) {
			case QUESTION:
			{
				questop();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case EOF:
			case TICKIFDEF:
			case TICKIFNDEF:
			case TICKDEFINE:
			case TICKINCLUDE:
			case TICKTIMESCALE:
			case LITERAL_module:
			case LITERAL_macromodule:
			case SEMI:
			case RPAREN:
			case COMMA:
			case COLON:
			case RBRACK:
			case PLUS:
			case MINUS:
			case LITERAL_or:
			case LCURLY:
			case RCURLY:
			case LITERAL_iff:
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
				exp7_AST = (AST)currentAST.root;
				exp7_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(QUESTTOP,"QuestionTop")).add(exp7_AST));
				currentAST.root = exp7_AST;
				currentAST.child = exp7_AST!=null &&exp7_AST.getFirstChild()!=null ?
					exp7_AST.getFirstChild() : exp7_AST;
				currentAST.advanceChildToEnd();
			}
			exp7_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_18);
			} else {
			  throw ex;
			}
		}
		returnAST = exp7_AST;
	}
	
	public final void questop() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST questop_AST = null;
		
		try {      // for error handling
			AST tmp359_AST = null;
			tmp359_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp359_AST);
			match(QUESTION);
			exp7();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp360_AST = null;
			tmp360_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp360_AST);
			match(COLON);
			exp7();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				questop_AST = (AST)currentAST.root;
				questop_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(QUESTIONSEGMENT,"QuestionSegment")).add(questop_AST));
				currentAST.root = questop_AST;
				currentAST.child = questop_AST!=null &&questop_AST.getFirstChild()!=null ?
					questop_AST.getFirstChild() : questop_AST;
				currentAST.advanceChildToEnd();
			}
			questop_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_18);
			} else {
			  throw ex;
			}
		}
		returnAST = questop_AST;
	}
	
	public final void exp6() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST exp6_AST = null;
		
		try {      // for error handling
			exp7();
			astFactory.addASTChild(currentAST, returnAST);
			exp6_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_18);
			} else {
			  throw ex;
			}
		}
		returnAST = exp6_AST;
	}
	
	public final void unary_operator() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST unary_operator_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case PLUS:
			{
				AST tmp361_AST = null;
				tmp361_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp361_AST);
				match(PLUS);
				unary_operator_AST = (AST)currentAST.root;
				break;
			}
			case MINUS:
			{
				AST tmp362_AST = null;
				tmp362_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp362_AST);
				match(MINUS);
				unary_operator_AST = (AST)currentAST.root;
				break;
			}
			case LNOT:
			{
				AST tmp363_AST = null;
				tmp363_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp363_AST);
				match(LNOT);
				unary_operator_AST = (AST)currentAST.root;
				break;
			}
			case BNOT:
			{
				AST tmp364_AST = null;
				tmp364_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp364_AST);
				match(BNOT);
				unary_operator_AST = (AST)currentAST.root;
				break;
			}
			case BAND:
			{
				AST tmp365_AST = null;
				tmp365_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp365_AST);
				match(BAND);
				unary_operator_AST = (AST)currentAST.root;
				break;
			}
			case RNAND:
			{
				AST tmp366_AST = null;
				tmp366_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp366_AST);
				match(RNAND);
				unary_operator_AST = (AST)currentAST.root;
				break;
			}
			case BOR:
			{
				AST tmp367_AST = null;
				tmp367_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp367_AST);
				match(BOR);
				unary_operator_AST = (AST)currentAST.root;
				break;
			}
			case RNOR:
			{
				AST tmp368_AST = null;
				tmp368_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp368_AST);
				match(RNOR);
				unary_operator_AST = (AST)currentAST.root;
				break;
			}
			case BXOR:
			{
				AST tmp369_AST = null;
				tmp369_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp369_AST);
				match(BXOR);
				unary_operator_AST = (AST)currentAST.root;
				break;
			}
			case RXNOR:
			{
				AST tmp370_AST = null;
				tmp370_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp370_AST);
				match(RXNOR);
				unary_operator_AST = (AST)currentAST.root;
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
				recover(ex,_tokenSet_76);
			} else {
			  throw ex;
			}
		}
		returnAST = unary_operator_AST;
	}
	
	public final void binary_operator() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST binary_operator_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case PLUS:
			{
				AST tmp371_AST = null;
				tmp371_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp371_AST);
				match(PLUS);
				binary_operator_AST = (AST)currentAST.root;
				break;
			}
			case MINUS:
			{
				AST tmp372_AST = null;
				tmp372_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp372_AST);
				match(MINUS);
				binary_operator_AST = (AST)currentAST.root;
				break;
			}
			case STAR:
			{
				AST tmp373_AST = null;
				tmp373_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp373_AST);
				match(STAR);
				binary_operator_AST = (AST)currentAST.root;
				break;
			}
			case DIV:
			{
				AST tmp374_AST = null;
				tmp374_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp374_AST);
				match(DIV);
				binary_operator_AST = (AST)currentAST.root;
				break;
			}
			case MOD:
			{
				AST tmp375_AST = null;
				tmp375_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp375_AST);
				match(MOD);
				binary_operator_AST = (AST)currentAST.root;
				break;
			}
			case EQUAL:
			{
				AST tmp376_AST = null;
				tmp376_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp376_AST);
				match(EQUAL);
				binary_operator_AST = (AST)currentAST.root;
				break;
			}
			case NOT_EQ:
			{
				AST tmp377_AST = null;
				tmp377_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp377_AST);
				match(NOT_EQ);
				binary_operator_AST = (AST)currentAST.root;
				break;
			}
			case EQ_CASE:
			{
				AST tmp378_AST = null;
				tmp378_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp378_AST);
				match(EQ_CASE);
				binary_operator_AST = (AST)currentAST.root;
				break;
			}
			case NOT_EQ_CASE:
			{
				AST tmp379_AST = null;
				tmp379_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp379_AST);
				match(NOT_EQ_CASE);
				binary_operator_AST = (AST)currentAST.root;
				break;
			}
			case LAND:
			{
				AST tmp380_AST = null;
				tmp380_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp380_AST);
				match(LAND);
				binary_operator_AST = (AST)currentAST.root;
				break;
			}
			case LOR:
			{
				AST tmp381_AST = null;
				tmp381_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp381_AST);
				match(LOR);
				binary_operator_AST = (AST)currentAST.root;
				break;
			}
			case LT_:
			{
				AST tmp382_AST = null;
				tmp382_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp382_AST);
				match(LT_);
				binary_operator_AST = (AST)currentAST.root;
				break;
			}
			case LE:
			{
				AST tmp383_AST = null;
				tmp383_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp383_AST);
				match(LE);
				binary_operator_AST = (AST)currentAST.root;
				break;
			}
			case GT:
			{
				AST tmp384_AST = null;
				tmp384_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp384_AST);
				match(GT);
				binary_operator_AST = (AST)currentAST.root;
				break;
			}
			case GE:
			{
				AST tmp385_AST = null;
				tmp385_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp385_AST);
				match(GE);
				binary_operator_AST = (AST)currentAST.root;
				break;
			}
			case BAND:
			{
				AST tmp386_AST = null;
				tmp386_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp386_AST);
				match(BAND);
				binary_operator_AST = (AST)currentAST.root;
				break;
			}
			case BOR:
			{
				AST tmp387_AST = null;
				tmp387_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp387_AST);
				match(BOR);
				binary_operator_AST = (AST)currentAST.root;
				break;
			}
			case BXOR:
			{
				AST tmp388_AST = null;
				tmp388_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp388_AST);
				match(BXOR);
				binary_operator_AST = (AST)currentAST.root;
				break;
			}
			case BXNOR:
			{
				AST tmp389_AST = null;
				tmp389_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp389_AST);
				match(BXNOR);
				binary_operator_AST = (AST)currentAST.root;
				break;
			}
			case SR:
			{
				AST tmp390_AST = null;
				tmp390_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp390_AST);
				match(SR);
				binary_operator_AST = (AST)currentAST.root;
				break;
			}
			case SL:
			{
				AST tmp391_AST = null;
				tmp391_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp391_AST);
				match(SL);
				binary_operator_AST = (AST)currentAST.root;
				break;
			}
			case SRS:
			{
				AST tmp392_AST = null;
				tmp392_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp392_AST);
				match(SRS);
				binary_operator_AST = (AST)currentAST.root;
				break;
			}
			case SLS:
			{
				AST tmp393_AST = null;
				tmp393_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp393_AST);
				match(SLS);
				binary_operator_AST = (AST)currentAST.root;
				break;
			}
			case POW:
			{
				AST tmp394_AST = null;
				tmp394_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp394_AST);
				match(POW);
				binary_operator_AST = (AST)currentAST.root;
				break;
			}
			case RXNOR:
			{
				AST tmp395_AST = null;
				tmp395_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp395_AST);
				match(RXNOR);
				binary_operator_AST = (AST)currentAST.root;
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
				recover(ex,_tokenSet_76);
			} else {
			  throw ex;
			}
		}
		returnAST = binary_operator_AST;
	}
	
	public final void cast() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST cast_AST = null;
		
		try {      // for error handling
			AST tmp396_AST = null;
			tmp396_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp396_AST);
			match(260);
			cast_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_0);
			} else {
			  throw ex;
			}
		}
		returnAST = cast_AST;
	}
	
	public final void local_identifier() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST local_identifier_AST = null;
		
		try {      // for error handling
			identifier();
			astFactory.addASTChild(currentAST, returnAST);
			local_identifier_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_90);
			} else {
			  throw ex;
			}
		}
		returnAST = local_identifier_AST;
	}
	
	public final void name_of_port() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST name_of_port_AST = null;
		
		try {      // for error handling
			local_identifier();
			astFactory.addASTChild(currentAST, returnAST);
			name_of_port_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_0);
			} else {
			  throw ex;
			}
		}
		returnAST = name_of_port_AST;
	}
	
	public final void name_of_UDP() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST name_of_UDP_AST = null;
		
		try {      // for error handling
			local_identifier();
			astFactory.addASTChild(currentAST, returnAST);
			name_of_UDP_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_0);
			} else {
			  throw ex;
			}
		}
		returnAST = name_of_UDP_AST;
	}
	
	public final void real_identifier() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST real_identifier_AST = null;
		
		try {      // for error handling
			identifier();
			astFactory.addASTChild(currentAST, returnAST);
			real_identifier_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_0);
			} else {
			  throw ex;
			}
		}
		returnAST = real_identifier_AST;
	}
	
	public final void name_of_memory() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST name_of_memory_AST = null;
		
		try {      // for error handling
			local_identifier();
			astFactory.addASTChild(currentAST, returnAST);
			name_of_memory_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_0);
			} else {
			  throw ex;
			}
		}
		returnAST = name_of_memory_AST;
	}
	
	public final void net_identifier() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST net_identifier_AST = null;
		
		try {      // for error handling
			identifier();
			astFactory.addASTChild(currentAST, returnAST);
			net_identifier_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_0);
			} else {
			  throw ex;
			}
		}
		returnAST = net_identifier_AST;
	}
	
	public final void specparam_identifier() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST specparam_identifier_AST = null;
		
		try {      // for error handling
			identifier();
			astFactory.addASTChild(currentAST, returnAST);
			specparam_identifier_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_0);
			} else {
			  throw ex;
			}
		}
		returnAST = specparam_identifier_AST;
	}
	
	public final void udp_name_of_port() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST udp_name_of_port_AST = null;
		
		try {      // for error handling
			identifier();
			astFactory.addASTChild(currentAST, returnAST);
			udp_name_of_port_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_0);
			} else {
			  throw ex;
			}
		}
		returnAST = udp_name_of_port_AST;
	}
	
	public final void name_of_instance() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST name_of_instance_AST = null;
		
		try {      // for error handling
			local_identifier();
			astFactory.addASTChild(currentAST, returnAST);
			name_of_instance_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_0);
			} else {
			  throw ex;
			}
		}
		returnAST = name_of_instance_AST;
	}
	
	public final void output_terminal_name() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST output_terminal_name_AST = null;
		
		try {      // for error handling
			local_identifier();
			astFactory.addASTChild(currentAST, returnAST);
			output_terminal_name_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_0);
			} else {
			  throw ex;
			}
		}
		returnAST = output_terminal_name_AST;
	}
	
	public final void name_dot_colon_pound() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST name_dot_colon_pound_AST = null;
		
		try {      // for error handling
			identifier();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop364:
			do {
				switch ( LA(1)) {
				case DOT:
				{
					name_dot();
					astFactory.addASTChild(currentAST, returnAST);
					break;
				}
				case DOUBLECOLON:
				{
					name_colon();
					astFactory.addASTChild(currentAST, returnAST);
					break;
				}
				case POUND:
				{
					name_pound_expression();
					astFactory.addASTChild(currentAST, returnAST);
					break;
				}
				default:
				{
					break _loop364;
				}
				}
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				name_dot_colon_pound_AST = (AST)currentAST.root;
				name_dot_colon_pound_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(NAME,"name_dot_colon_pound")).add(name_dot_colon_pound_AST));
				currentAST.root = name_dot_colon_pound_AST;
				currentAST.child = name_dot_colon_pound_AST!=null &&name_dot_colon_pound_AST.getFirstChild()!=null ?
					name_dot_colon_pound_AST.getFirstChild() : name_dot_colon_pound_AST;
				currentAST.advanceChildToEnd();
			}
			name_dot_colon_pound_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_0);
			} else {
			  throw ex;
			}
		}
		returnAST = name_dot_colon_pound_AST;
	}
	
	public final void name_dot() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST name_dot_AST = null;
		
		try {      // for error handling
			AST tmp397_AST = null;
			tmp397_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp397_AST);
			match(DOT);
			local_identifier();
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
				recover(ex,_tokenSet_15);
			} else {
			  throw ex;
			}
		}
		returnAST = name_dot_AST;
	}
	
	public final void name_colon() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST name_colon_AST = null;
		
		try {      // for error handling
			AST tmp398_AST = null;
			tmp398_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp398_AST);
			match(DOUBLECOLON);
			local_identifier();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				name_colon_AST = (AST)currentAST.root;
				name_colon_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(NAMECOLON,"NameColon")).add(name_colon_AST));
				currentAST.root = name_colon_AST;
				currentAST.child = name_colon_AST!=null &&name_colon_AST.getFirstChild()!=null ?
					name_colon_AST.getFirstChild() : name_colon_AST;
				currentAST.advanceChildToEnd();
			}
			name_colon_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_15);
			} else {
			  throw ex;
			}
		}
		returnAST = name_colon_AST;
	}
	
	public final void name_pound_expression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST name_pound_expression_AST = null;
		
		try {      // for error handling
			AST tmp399_AST = null;
			tmp399_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp399_AST);
			match(POUND);
			AST tmp400_AST = null;
			tmp400_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp400_AST);
			match(LPAREN);
			{
			switch ( LA(1)) {
			case LPAREN:
			case NUMBER:
			case PLUS:
			case MINUS:
			case IDENTIFIER:
			case LCURLY:
			case STRING:
			case LNOT:
			case BNOT:
			case BAND:
			case RNAND:
			case BOR:
			case RNOR:
			case BXOR:
			case RXNOR:
			case PLUSPLUS:
			case MINMIN:
			case LITERAL_int:
			case ESCAPED_IDENTIFIER:
			case DOLLAR_IDENTIFIER:
			{
				expression_or_int();
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
			_loop399:
			do {
				if ((LA(1)==COMMA)) {
					AST tmp401_AST = null;
					tmp401_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp401_AST);
					match(COMMA);
					expression_or_int();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop399;
				}
				
			} while (true);
			}
			AST tmp402_AST = null;
			tmp402_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp402_AST);
			match(RPAREN);
			if ( inputState.guessing==0 ) {
				name_pound_expression_AST = (AST)currentAST.root;
				name_pound_expression_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(NAMEPOUNDEXPRESSION,"NamePoundExpression")).add(name_pound_expression_AST));
				currentAST.root = name_pound_expression_AST;
				currentAST.child = name_pound_expression_AST!=null &&name_pound_expression_AST.getFirstChild()!=null ?
					name_pound_expression_AST.getFirstChild() : name_pound_expression_AST;
				currentAST.advanceChildToEnd();
			}
			name_pound_expression_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_15);
			} else {
			  throw ex;
			}
		}
		returnAST = name_pound_expression_AST;
	}
	
	public final void name_dot_colon_only() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST name_dot_colon_only_AST = null;
		
		try {      // for error handling
			identifier();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop368:
			do {
				if ((LA(1)==DOT||LA(1)==DOUBLECOLON)) {
					{
					switch ( LA(1)) {
					case DOUBLECOLON:
					{
						name_colon();
						astFactory.addASTChild(currentAST, returnAST);
						break;
					}
					case DOT:
					{
						name_dot();
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
					break _loop368;
				}
				
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				name_dot_colon_only_AST = (AST)currentAST.root;
				name_dot_colon_only_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(NAME,"name_dot_colon_only")).add(name_dot_colon_only_AST));
				currentAST.root = name_dot_colon_only_AST;
				currentAST.child = name_dot_colon_only_AST!=null &&name_dot_colon_only_AST.getFirstChild()!=null ?
					name_dot_colon_only_AST.getFirstChild() : name_dot_colon_only_AST;
				currentAST.advanceChildToEnd();
			}
			name_dot_colon_only_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_0);
			} else {
			  throw ex;
			}
		}
		returnAST = name_dot_colon_only_AST;
	}
	
	public final void name_dot_colon_range() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST name_dot_colon_range_AST = null;
		
		try {      // for error handling
			identifier();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop372:
			do {
				if ((LA(1)==LBRACK||LA(1)==DOT||LA(1)==DOUBLECOLON)) {
					{
					switch ( LA(1)) {
					case DOUBLECOLON:
					{
						name_colon();
						astFactory.addASTChild(currentAST, returnAST);
						break;
					}
					case DOT:
					{
						name_dot();
						astFactory.addASTChild(currentAST, returnAST);
						break;
					}
					case LBRACK:
					{
						name_range();
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
					break _loop372;
				}
				
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				name_dot_colon_range_AST = (AST)currentAST.root;
				name_dot_colon_range_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(NAME,"name_dot_colon_range")).add(name_dot_colon_range_AST));
				currentAST.root = name_dot_colon_range_AST;
				currentAST.child = name_dot_colon_range_AST!=null &&name_dot_colon_range_AST.getFirstChild()!=null ?
					name_dot_colon_range_AST.getFirstChild() : name_dot_colon_range_AST;
				currentAST.advanceChildToEnd();
			}
			name_dot_colon_range_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_0);
			} else {
			  throw ex;
			}
		}
		returnAST = name_dot_colon_range_AST;
	}
	
	public final void name_range() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST name_range_AST = null;
		
		try {      // for error handling
			segment_range();
			astFactory.addASTChild(currentAST, returnAST);
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
				recover(ex,_tokenSet_15);
			} else {
			  throw ex;
			}
		}
		returnAST = name_range_AST;
	}
	
	public final void name_dot_only() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST name_dot_only_AST = null;
		
		try {      // for error handling
			identifier();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop375:
			do {
				if ((LA(1)==DOT)) {
					name_dot();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop375;
				}
				
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				name_dot_only_AST = (AST)currentAST.root;
				name_dot_only_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(NAME,"name_dot_only")).add(name_dot_only_AST));
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
				recover(ex,_tokenSet_0);
			} else {
			  throw ex;
			}
		}
		returnAST = name_dot_only_AST;
	}
	
	public final void name_no_expression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST name_no_expression_AST = null;
		
		try {      // for error handling
			{
			{
			identifier();
			astFactory.addASTChild(currentAST, returnAST);
			}
			{
			_loop381:
			do {
				if ((LA(1)==LBRACK||LA(1)==DOT||LA(1)==DOUBLECOLON)) {
					{
					switch ( LA(1)) {
					case DOT:
					{
						name_dot();
						astFactory.addASTChild(currentAST, returnAST);
						break;
					}
					case LBRACK:
					{
						name_range();
						astFactory.addASTChild(currentAST, returnAST);
						break;
					}
					case DOUBLECOLON:
					{
						name_colon();
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
					break _loop381;
				}
				
			} while (true);
			}
			}
			if ( inputState.guessing==0 ) {
				name_no_expression_AST = (AST)currentAST.root;
				name_no_expression_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(NAME,"Name")).add(name_no_expression_AST));
				currentAST.root = name_no_expression_AST;
				currentAST.child = name_no_expression_AST!=null &&name_no_expression_AST.getFirstChild()!=null ?
					name_no_expression_AST.getFirstChild() : name_no_expression_AST;
				currentAST.advanceChildToEnd();
			}
			name_no_expression_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_0);
			} else {
			  throw ex;
			}
		}
		returnAST = name_no_expression_AST;
	}
	
	public final void name_expression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST name_expression_AST = null;
		
		try {      // for error handling
			AST tmp403_AST = null;
			tmp403_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp403_AST);
			match(LPAREN);
			{
			switch ( LA(1)) {
			case LPAREN:
			case NUMBER:
			case PLUS:
			case MINUS:
			case DOT:
			case IDENTIFIER:
			case LCURLY:
			case STRING:
			case LNOT:
			case BNOT:
			case BAND:
			case RNAND:
			case BOR:
			case RNOR:
			case BXOR:
			case RXNOR:
			case PLUSPLUS:
			case MINMIN:
			case ESCAPED_IDENTIFIER:
			case DOLLAR_IDENTIFIER:
			{
				expression_or_dot_expression();
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
			_loop395:
			do {
				if ((LA(1)==COMMA)) {
					AST tmp404_AST = null;
					tmp404_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp404_AST);
					match(COMMA);
					{
					switch ( LA(1)) {
					case LPAREN:
					case NUMBER:
					case PLUS:
					case MINUS:
					case DOT:
					case IDENTIFIER:
					case LCURLY:
					case STRING:
					case LNOT:
					case BNOT:
					case BAND:
					case RNAND:
					case BOR:
					case RNOR:
					case BXOR:
					case RXNOR:
					case PLUSPLUS:
					case MINMIN:
					case ESCAPED_IDENTIFIER:
					case DOLLAR_IDENTIFIER:
					{
						expression_or_dot_expression();
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
				}
				else {
					break _loop395;
				}
				
			} while (true);
			}
			AST tmp405_AST = null;
			tmp405_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp405_AST);
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
				recover(ex,_tokenSet_15);
			} else {
			  throw ex;
			}
		}
		returnAST = name_expression_AST;
	}
	
	public final void expression_or_dot_expression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST expression_or_dot_expression_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case LPAREN:
			case NUMBER:
			case PLUS:
			case MINUS:
			case IDENTIFIER:
			case LCURLY:
			case STRING:
			case LNOT:
			case BNOT:
			case BAND:
			case RNAND:
			case BOR:
			case RNOR:
			case BXOR:
			case RXNOR:
			case PLUSPLUS:
			case MINMIN:
			case ESCAPED_IDENTIFIER:
			case DOLLAR_IDENTIFIER:
			{
				expression();
				astFactory.addASTChild(currentAST, returnAST);
				expression_or_dot_expression_AST = (AST)currentAST.root;
				break;
			}
			case DOT:
			{
				AST tmp406_AST = null;
				tmp406_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp406_AST);
				match(DOT);
				identifier();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp407_AST = null;
				tmp407_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp407_AST);
				match(LPAREN);
				expression();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp408_AST = null;
				tmp408_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp408_AST);
				match(RPAREN);
				expression_or_dot_expression_AST = (AST)currentAST.root;
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
				recover(ex,_tokenSet_12);
			} else {
			  throw ex;
			}
		}
		returnAST = expression_or_dot_expression_AST;
	}
	
	public final void expression_or_int() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST expression_or_int_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_int:
			{
				AST tmp409_AST = null;
				tmp409_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp409_AST);
				match(LITERAL_int);
				expression_or_int_AST = (AST)currentAST.root;
				break;
			}
			case LPAREN:
			case NUMBER:
			case PLUS:
			case MINUS:
			case IDENTIFIER:
			case LCURLY:
			case STRING:
			case LNOT:
			case BNOT:
			case BAND:
			case RNAND:
			case BOR:
			case RNOR:
			case BXOR:
			case RXNOR:
			case PLUSPLUS:
			case MINMIN:
			case ESCAPED_IDENTIFIER:
			case DOLLAR_IDENTIFIER:
			{
				expression();
				astFactory.addASTChild(currentAST, returnAST);
				expression_or_int_AST = (AST)currentAST.root;
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
				recover(ex,_tokenSet_12);
			} else {
			  throw ex;
			}
		}
		returnAST = expression_or_int_AST;
	}
	
	public final void ident_range() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST ident_range_AST = null;
		
		try {      // for error handling
			{
			boolean synPredMatched407 = false;
			if (((LA(1)==IDENTIFIER||LA(1)==ESCAPED_IDENTIFIER||LA(1)==DOLLAR_IDENTIFIER) && (LA(2)==LBRACK))) {
				int _m407 = mark();
				synPredMatched407 = true;
				inputState.guessing++;
				try {
					{
					identifier();
					segment_range();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched407 = false;
				}
				rewind(_m407);
inputState.guessing--;
			}
			if ( synPredMatched407 ) {
				identifier();
				astFactory.addASTChild(currentAST, returnAST);
				segment_range();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else if ((LA(1)==IDENTIFIER||LA(1)==ESCAPED_IDENTIFIER||LA(1)==DOLLAR_IDENTIFIER) && (LA(2)==EOF)) {
				identifier();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			if ( inputState.guessing==0 ) {
				ident_range_AST = (AST)currentAST.root;
				ident_range_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(IDENTRANGE,"IdentRange")).add(ident_range_AST));
				currentAST.root = ident_range_AST;
				currentAST.child = ident_range_AST!=null &&ident_range_AST.getFirstChild()!=null ?
					ident_range_AST.getFirstChild() : ident_range_AST;
				currentAST.advanceChildToEnd();
			}
			ident_range_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_0);
			} else {
			  throw ex;
			}
		}
		returnAST = ident_range_AST;
	}
	
	public final void delay_number() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST delay_number_AST = null;
		
		try {      // for error handling
			AST tmp410_AST = null;
			tmp410_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp410_AST);
			match(POUND);
			{
			switch ( LA(1)) {
			case NUMBER:
			{
				number();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case IDENTIFIER:
			case ESCAPED_IDENTIFIER:
			case DOLLAR_IDENTIFIER:
			{
				identifier();
				astFactory.addASTChild(currentAST, returnAST);
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
			case LITERAL_s:
			case LITERAL_ms:
			case LITERAL_us:
			case LITERAL_ns:
			case LITERAL_ps:
			case LITERAL_fs:
			case LITERAL_step:
			{
				time_unit();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case SEMI:
			case LPAREN:
			case NUMBER:
			case LITERAL_assign:
			case PLUS:
			case MINUS:
			case POUND:
			case LITERAL_if:
			case LITERAL_unique:
			case LITERAL_priority:
			case LITERAL_case:
			case LITERAL_casez:
			case LITERAL_casex:
			case LITERAL_forever:
			case LITERAL_repeat:
			case LITERAL_while:
			case LITERAL_for:
			case LITERAL_wait:
			case TRIGGER:
			case LITERAL_disable:
			case LITERAL_begin:
			case LITERAL_fork:
			case LITERAL_deassign:
			case LITERAL_force:
			case LITERAL_release:
			case IDENTIFIER:
			case LCURLY:
			case STRING:
			case LNOT:
			case BNOT:
			case BAND:
			case RNAND:
			case BOR:
			case RNOR:
			case BXOR:
			case RXNOR:
			case PLUSPLUS:
			case MINMIN:
			case ESCAPED_IDENTIFIER:
			case DOLLAR_IDENTIFIER:
			case AT:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			delay_number_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_58);
			} else {
			  throw ex;
			}
		}
		returnAST = delay_number_AST;
	}
	
	public final void event_expression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST event_expression_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case STAR:
			{
				AST tmp411_AST = null;
				tmp411_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp411_AST);
				match(STAR);
				event_expression_AST = (AST)currentAST.root;
				break;
			}
			case LPAREN:
			case NUMBER:
			case PLUS:
			case MINUS:
			case LITERAL_posedge:
			case LITERAL_negedge:
			case IDENTIFIER:
			case LCURLY:
			case STRING:
			case LNOT:
			case BNOT:
			case BAND:
			case RNAND:
			case BOR:
			case RNOR:
			case BXOR:
			case RXNOR:
			case PLUSPLUS:
			case MINMIN:
			case ESCAPED_IDENTIFIER:
			case DOLLAR_IDENTIFIER:
			{
				{
				sub_event_expression();
				astFactory.addASTChild(currentAST, returnAST);
				{
				_loop421:
				do {
					if ((LA(1)==COMMA||LA(1)==LITERAL_or)) {
						{
						switch ( LA(1)) {
						case LITERAL_or:
						{
							AST tmp412_AST = null;
							tmp412_AST = astFactory.create(LT(1));
							astFactory.addASTChild(currentAST, tmp412_AST);
							match(LITERAL_or);
							break;
						}
						case COMMA:
						{
							AST tmp413_AST = null;
							tmp413_AST = astFactory.create(LT(1));
							astFactory.addASTChild(currentAST, tmp413_AST);
							match(COMMA);
							break;
						}
						default:
						{
							throw new NoViableAltException(LT(1), getFilename());
						}
						}
						}
						sub_event_expression();
						astFactory.addASTChild(currentAST, returnAST);
					}
					else {
						break _loop421;
					}
					
				} while (true);
				}
				}
				if ( inputState.guessing==0 ) {
					event_expression_AST = (AST)currentAST.root;
					event_expression_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(EVENTEXPRESSION,"EventExpression")).add(event_expression_AST));
					currentAST.root = event_expression_AST;
					currentAST.child = event_expression_AST!=null &&event_expression_AST.getFirstChild()!=null ?
						event_expression_AST.getFirstChild() : event_expression_AST;
					currentAST.advanceChildToEnd();
				}
				event_expression_AST = (AST)currentAST.root;
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
				recover(ex,_tokenSet_11);
			} else {
			  throw ex;
			}
		}
		returnAST = event_expression_AST;
	}
	
	public final void sub_event_expression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST sub_event_expression_AST = null;
		
		try {      // for error handling
			{
			{
			switch ( LA(1)) {
			case LPAREN:
			case NUMBER:
			case PLUS:
			case MINUS:
			case IDENTIFIER:
			case LCURLY:
			case STRING:
			case LNOT:
			case BNOT:
			case BAND:
			case RNAND:
			case BOR:
			case RNOR:
			case BXOR:
			case RXNOR:
			case PLUSPLUS:
			case MINMIN:
			case ESCAPED_IDENTIFIER:
			case DOLLAR_IDENTIFIER:
			{
				expression();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case LITERAL_posedge:
			{
				AST tmp414_AST = null;
				tmp414_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp414_AST);
				match(LITERAL_posedge);
				expression();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case LITERAL_negedge:
			{
				AST tmp415_AST = null;
				tmp415_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp415_AST);
				match(LITERAL_negedge);
				expression();
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
			{
			switch ( LA(1)) {
			case LITERAL_iff:
			{
				AST tmp416_AST = null;
				tmp416_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp416_AST);
				match(LITERAL_iff);
				expression();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case RPAREN:
			case COMMA:
			case LITERAL_or:
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
				sub_event_expression_AST = (AST)currentAST.root;
				sub_event_expression_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(EVENTEXPRESSIONSUB,"EventExpressionSub")).add(sub_event_expression_AST));
				currentAST.root = sub_event_expression_AST;
				currentAST.child = sub_event_expression_AST!=null &&sub_event_expression_AST.getFirstChild()!=null ?
					sub_event_expression_AST.getFirstChild() : sub_event_expression_AST;
				currentAST.advanceChildToEnd();
			}
			sub_event_expression_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_91);
			} else {
			  throw ex;
			}
		}
		returnAST = sub_event_expression_AST;
	}
	
	public final void context_clause() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST context_clause_AST = null;
		
		try {      // for error handling
			{
			_loop428:
			do {
				if ((_tokenSet_92.member(LA(1)))) {
					context_item();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop428;
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
				recover(ex,_tokenSet_0);
			} else {
			  throw ex;
			}
		}
		returnAST = context_clause_AST;
	}
	
	public final void define_directive() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST define_directive_AST = null;
		
		try {      // for error handling
			AST tmp417_AST = null;
			tmp417_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp417_AST);
			match(TICKDEFINE);
			identifier();
			astFactory.addASTChild(currentAST, returnAST);
			{
			switch ( LA(1)) {
			case LPAREN:
			case NUMBER:
			case PLUS:
			case MINUS:
			case IDENTIFIER:
			case LCURLY:
			case STRING:
			case LNOT:
			case BNOT:
			case BAND:
			case RNAND:
			case BOR:
			case RNOR:
			case BXOR:
			case RXNOR:
			case PLUSPLUS:
			case MINMIN:
			case ESCAPED_IDENTIFIER:
			case DOLLAR_IDENTIFIER:
			{
				expression();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case EOF:
			case TICKIFDEF:
			case TICKIFNDEF:
			case TICKDEFINE:
			case TICKINCLUDE:
			case TICKTIMESCALE:
			case LITERAL_module:
			case LITERAL_macromodule:
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
				define_directive_AST = (AST)currentAST.root;
				define_directive_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(DEFINEDIRECTIVE,"DefineDirective")).add(define_directive_AST));
				currentAST.root = define_directive_AST;
				currentAST.child = define_directive_AST!=null &&define_directive_AST.getFirstChild()!=null ?
					define_directive_AST.getFirstChild() : define_directive_AST;
				currentAST.advanceChildToEnd();
			}
			define_directive_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_1);
			} else {
			  throw ex;
			}
		}
		returnAST = define_directive_AST;
	}
	
	public final void include_directive() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST include_directive_AST = null;
		
		try {      // for error handling
			AST tmp418_AST = null;
			tmp418_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp418_AST);
			match(TICKINCLUDE);
			{
			switch ( LA(1)) {
			case IDENTIFIER:
			case ESCAPED_IDENTIFIER:
			case DOLLAR_IDENTIFIER:
			{
				name();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case STRING:
			{
				AST tmp419_AST = null;
				tmp419_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp419_AST);
				match(STRING);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				include_directive_AST = (AST)currentAST.root;
				include_directive_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(INCLUDEDIRECTIVE,"IncludeDirective")).add(include_directive_AST));
				currentAST.root = include_directive_AST;
				currentAST.child = include_directive_AST!=null &&include_directive_AST.getFirstChild()!=null ?
					include_directive_AST.getFirstChild() : include_directive_AST;
				currentAST.advanceChildToEnd();
			}
			include_directive_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_1);
			} else {
			  throw ex;
			}
		}
		returnAST = include_directive_AST;
	}
	
	public final void ifdef_directive() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST ifdef_directive_AST = null;
		
		try {      // for error handling
			ifdef_head();
			astFactory.addASTChild(currentAST, returnAST);
			ifdef_cond();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp420_AST = null;
			tmp420_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp420_AST);
			match(TICKENDIF);
			if ( inputState.guessing==0 ) {
				ifdef_directive_AST = (AST)currentAST.root;
				ifdef_directive_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(IFDEFDIRECTIVE,"ifdef_directive")).add(ifdef_directive_AST));
				currentAST.root = ifdef_directive_AST;
				currentAST.child = ifdef_directive_AST!=null &&ifdef_directive_AST.getFirstChild()!=null ?
					ifdef_directive_AST.getFirstChild() : ifdef_directive_AST;
				currentAST.advanceChildToEnd();
			}
			ifdef_directive_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_1);
			} else {
			  throw ex;
			}
		}
		returnAST = ifdef_directive_AST;
	}
	
	public final void timescale_directive() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST timescale_directive_AST = null;
		
		try {      // for error handling
			AST tmp421_AST = null;
			tmp421_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp421_AST);
			match(TICKTIMESCALE);
			number();
			astFactory.addASTChild(currentAST, returnAST);
			identifier();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp422_AST = null;
			tmp422_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp422_AST);
			match(DIV);
			number();
			astFactory.addASTChild(currentAST, returnAST);
			identifier();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				timescale_directive_AST = (AST)currentAST.root;
				timescale_directive_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(TIMESCALEDIRECTIVE,"timescale_directive")).add(timescale_directive_AST));
				currentAST.root = timescale_directive_AST;
				currentAST.child = timescale_directive_AST!=null &&timescale_directive_AST.getFirstChild()!=null ?
					timescale_directive_AST.getFirstChild() : timescale_directive_AST;
				currentAST.advanceChildToEnd();
			}
			timescale_directive_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_1);
			} else {
			  throw ex;
			}
		}
		returnAST = timescale_directive_AST;
	}
	
	public final void ifdef_head() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST ifdef_head_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case TICKIFDEF:
			{
				AST tmp423_AST = null;
				tmp423_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp423_AST);
				match(TICKIFDEF);
				break;
			}
			case TICKIFNDEF:
			{
				AST tmp424_AST = null;
				tmp424_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp424_AST);
				match(TICKIFNDEF);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			identifier();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop439:
			do {
				if ((_tokenSet_6.member(LA(1)))) {
					module_item();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop439;
				}
				
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				ifdef_head_AST = (AST)currentAST.root;
				ifdef_head_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(IFDEFHEAD,"ifdef_head")).add(ifdef_head_AST));
				currentAST.root = ifdef_head_AST;
				currentAST.child = ifdef_head_AST!=null &&ifdef_head_AST.getFirstChild()!=null ?
					ifdef_head_AST.getFirstChild() : ifdef_head_AST;
				currentAST.advanceChildToEnd();
			}
			ifdef_head_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_93);
			} else {
			  throw ex;
			}
		}
		returnAST = ifdef_head_AST;
	}
	
	public final void ifdef_cond() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST ifdef_cond_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case TICKELSE:
			{
				AST tmp425_AST = null;
				tmp425_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp425_AST);
				match(TICKELSE);
				{
				_loop443:
				do {
					if ((_tokenSet_6.member(LA(1)))) {
						module_item();
						astFactory.addASTChild(currentAST, returnAST);
					}
					else {
						break _loop443;
					}
					
				} while (true);
				}
				break;
			}
			case TICKENDIF:
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
				ifdef_cond_AST = (AST)currentAST.root;
				ifdef_cond_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(IFDEFCOND,"ifdef_cond")).add(ifdef_cond_AST));
				currentAST.root = ifdef_cond_AST;
				currentAST.child = ifdef_cond_AST!=null &&ifdef_cond_AST.getFirstChild()!=null ?
					ifdef_cond_AST.getFirstChild() : ifdef_cond_AST;
				currentAST.advanceChildToEnd();
			}
			ifdef_cond_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_94);
			} else {
			  throw ex;
			}
		}
		returnAST = ifdef_cond_AST;
	}
	
	
	public static final String[] _tokenNames = {
		"<0>",
		"EOF",
		"<2>",
		"NULL_TREE_LOOKAHEAD",
		"ROOT",
		"DESIGNUNIT",
		"CONTEXTCLAUSE",
		"CONTEXTITEM",
		"DEFINEDIRECTIVE",
		"INCLUDEDIRECTIVE",
		"MODULE",
		"MODULEHEAD",
		"MODULEBODY",
		"MODULEITEM",
		"MODULEDEC",
		"MODULENAME",
		"PORTCLAUSE",
		"PORTLIST",
		"PORTELEMENT",
		"PORTTOP",
		"PORT",
		"PORTNODOT",
		"PORTDOT",
		"PORTEXPRESSION",
		"VARDEC",
		"VARTYPE",
		"VARRANGE",
		"VARLIST",
		"VARNAME",
		"REGMEM",
		"REGMEMARRAY",
		"PARDEC",
		"PARASSIGN",
		"CONTINUOUS_ASSIGN",
		"ASSIGNLIST",
		"ASSIGNMENT",
		"IDENTRANGE",
		"SEGMENTRANGE",
		"QUESTTOP",
		"QUESTIONSEGMENT",
		"PAROP",
		"UNOP",
		"BINOP",
		"BINOPSMALL",
		"CONCAT",
		"ALWAYSTOP",
		"ALWAYSHEAD",
		"EVENTCONTROL",
		"EVENTEXPRESSION",
		"EVENTEXPRESSIONSUB",
		"TOTALASSIGN",
		"CONDITIONTOP",
		"CONDITIONHEAD",
		"CONDITIONELSE",
		"CASETOP",
		"CASEHEAD",
		"CASELIST",
		"CASEEXPRESSION",
		"CASENORMAL",
		"CASEDEFAULT",
		"BEGENDBLOCK",
		"SEQBLOCKNAMEQ",
		"STATELIST",
		"MODITEM",
		"MODINSTANCETOP",
		"MODINSTANCE",
		"INSTANCENAME",
		"INSTANCEPORTLIST",
		"INSTANCEPORT",
		"PORTCON",
		"IDENTIFIERPATH",
		"NAME",
		"NAMERANGE",
		"NAMEDOT",
		"NAMEEXPRESSION",
		"NAMEPOUNDEXPRESSION",
		"DRIVESTRENGTHQ",
		"DELAYQ",
		"DELAYOREVENTCONTROLQ",
		"TASK",
		"FUNCTION",
		"TASKVARDECLARATION",
		"FUNCTIONVARDECLARATION",
		"RANGEORTYPE",
		"PARAMETEROVERRIDE",
		"IFDEFDIRECTIVE",
		"IFDEFHEAD",
		"IFDEFCOND",
		"TIMESCALEDIRECTIVE",
		"\"`ifdef\"",
		"\"`ifndef\"",
		"\"`else\"",
		"\"`endif\"",
		"\"`define\"",
		"\"`include\"",
		"\"`timescale\"",
		"\"`undef\"",
		"GENERATEITEM",
		"RANGEPARAM",
		"PRIMITIVEDEFINITION",
		"TABLEDEFINITION",
		"SPECIFYBLOCK",
		"SPECIFYITEM",
		"SPECPARAMDECLARATION",
		"PATHDECLARATION",
		"EDGESENSITIVEPATHDECLARATION",
		"SDPD",
		"SIMPLEPATHDECLARATION",
		"PARALLELPATHDECLARATION",
		"FULLPATHDESCRIPTOR",
		"LEVELSENSITIVEPATHDECLARATION",
		"RANGEORTYPEQ",
		"AUTOMATICQ",
		"PREBLOCKNAMEQ",
		"UNIQUEPRIORITY",
		"MATCHES",
		"NAMECOLON",
		"INITIALORFINAL",
		"WAITSTATEMENT",
		"DISABLESTATEMENT",
		"\"endmodule\"",
		"\"module\"",
		"\"macromodule\"",
		"SEMI",
		"LPAREN",
		"RPAREN",
		"COMMA",
		"LBRACE",
		"RBRACE",
		"LBRACK",
		"COLON",
		"RBRACK",
		"\"generate\"",
		"\"endgenerate\"",
		"\"1'b0\"",
		"\"1'b1\"",
		"\"1'bx\"",
		"NUMBER",
		"\"primitive\"",
		"\"endprimitive\"",
		"\"table\"",
		"\"endtable\"",
		"\"task\"",
		"\"endtask\"",
		"\"function\"",
		"\"endfunction\"",
		"\"automatic\"",
		"\"integer\"",
		"\"real\"",
		"\"QQQQQDDDDDEEEEEEEFFFFFFFf\"",
		"\"PPPPPQQDDDDDEEEEEEEFFFFFFFf\"",
		"ASSIGN",
		"\"tri\"",
		"\"tri1\"",
		"\"supply0\"",
		"\"wand\"",
		"\"triand\"",
		"\"tri0\"",
		"\"supply1\"",
		"\"wor\"",
		"\"trior\"",
		"\"trireg\"",
		"\"scalared\"",
		"\"vectored\"",
		"\"assign\"",
		"\"defparam\"",
		"\"small\"",
		"\"medium\"",
		"\"large\"",
		"\"strong0\"",
		"\"pull0\"",
		"\"weak0\"",
		"\"highz0\"",
		"\"strong1\"",
		"\"pull1\"",
		"\"weak1\"",
		"\"highz1\"",
		"DOLLAR",
		"PLUS",
		"MINUS",
		"\"and\"",
		"\"nand\"",
		"\"or\"",
		"\"nor\"",
		"\"xor\"",
		"\"xnor\"",
		"\"buf\"",
		"\"bufif0\"",
		"\"bufif1\"",
		"\"not\"",
		"\"notif0\"",
		"\"notif1\"",
		"\"pulldown\"",
		"\"pullup\"",
		"\"nmos\"",
		"\"rnmos\"",
		"\"pmos\"",
		"\"rpmos\"",
		"\"cmos\"",
		"\"rcmos\"",
		"\"tran\"",
		"\"rtran\"",
		"\"rtranif0\"",
		"\"tranif1\"",
		"\"rtranif1\"",
		"POUND",
		"DOT",
		"\"initial\"",
		"\"final\"",
		"\"always\"",
		"\"always_latch\"",
		"\"always_comb\"",
		"\"always_ff\"",
		"\"if\"",
		"\"else\"",
		"\"unique\"",
		"\"priority\"",
		"\"matches\"",
		"\"endcase\"",
		"\"default\"",
		"\"case\"",
		"\"casez\"",
		"\"casex\"",
		"\"forever\"",
		"\"repeat\"",
		"\"while\"",
		"\"for\"",
		"\"wait\"",
		"TRIGGER",
		"\"disable\"",
		"\"begin\"",
		"\"end\"",
		"\"fork\"",
		"\"join\"",
		"\"deassign\"",
		"\"force\"",
		"\"release\"",
		"\"specify\"",
		"\"endspecify\"",
		"\"specparam\"",
		"PPATH",
		"FPATH",
		"\"$setup\"",
		"\"$hold\"",
		"\"$period\"",
		"\"$width\"",
		"\"$skew\"",
		"\"$recovery\"",
		"\"$setuphold\"",
		"\"&&&\"",
		"\"posedge\"",
		"\"negedge\"",
		"\"edge\"",
		"\"0x\"",
		"\"1x\"",
		"IDENTIFIER",
		"LCURLY",
		"RCURLY",
		"STRING",
		"QUESTION",
		"\"asdfaslkdjfa;sljdf;alskjfd;aslkjdf;ajlsdf\"",
		"LNOT",
		"BNOT",
		"BAND",
		"RNAND",
		"BOR",
		"RNOR",
		"BXOR",
		"RXNOR",
		"STAR",
		"DIV",
		"MOD",
		"EQUAL",
		"NOT_EQ",
		"EQ_CASE",
		"NOT_EQ_CASE",
		"LAND",
		"LOR",
		"LT_",
		"LE",
		"GT",
		"GE",
		"BXNOR",
		"SR",
		"SL",
		"SRS",
		"SLS",
		"POW",
		"PLUSPLUS",
		"MINMIN",
		"DOUBLECOLON",
		"\"int\"",
		"ESCAPED_IDENTIFIER",
		"DOLLAR_IDENTIFIER",
		"AT",
		"\"iff\"",
		"\"s\"",
		"\"ms\"",
		"\"us\"",
		"\"ns\"",
		"\"ps\"",
		"\"fs\"",
		"\"step\"",
		"TICKTICK",
		"TICKQUOTE",
		"CASTPAREN",
		"CASTCURLY",
		"ESCAPE_NEWLINE",
		"ESC",
		"HEX_DIGIT",
		"DEFINE",
		"VOCAB",
		"SIZED_NUMBER",
		"SIZE",
		"BASE",
		"SIZED_DIGIT",
		"UNSIZED_NUMBER",
		"DIGIT",
		"HEXDIGIT",
		"EXPONENT",
		"WS_",
		"NEWLINE_",
		"SL_COMMENT",
		"ML_COMMENT",
		"OTHER_SPECIAL_CHARACTER"
	};
	
	protected void buildTokenTypeASTClassMap() {
		tokenTypeToASTClassMap=null;
	};
	
	private static final long[] mk_tokenSet_0() {
		long[] data = { 2L, 0L, 0L, 0L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	private static final long[] mk_tokenSet_1() {
		long[] data = { 2L, 432345568086327296L, 0L, 0L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
	private static final long[] mk_tokenSet_2() {
		long[] data = new long[10];
		data[1]=1152921504606846976L;
		data[2]=2L;
		data[3]=24576L;
		data[4]=17179869184L;
		return data;
	}
	public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
	private static final long[] mk_tokenSet_3() {
		long[] data = new long[10];
		data[1]=8070450532247928832L;
		data[2]=3940649673949696L;
		data[3]=-9223372036854759424L;
		data[4]=219043340261L;
		return data;
	}
	public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
	private static final long[] mk_tokenSet_4() {
		long[] data = new long[10];
		data[0]=2L;
		data[1]=9079256852637679616L;
		data[2]=21392166957875724L;
		data[3]=-9223339601502920704L;
		data[4]=1047972020207L;
		return data;
	}
	public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
	private static final long[] mk_tokenSet_5() {
		long[] data = new long[10];
		data[1]=72057594037927936L;
		data[2]=-4503393468858352L;
		data[3]=-9223336852480614401L;
		data[4]=206158430208L;
		return data;
	}
	public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
	private static final long[] mk_tokenSet_6() {
		long[] data = new long[10];
		data[2]=-4503393468858352L;
		data[3]=-9223336852480614401L;
		data[4]=206158430208L;
		return data;
	}
	public static final BitSet _tokenSet_6 = new BitSet(mk_tokenSet_6());
	private static final long[] mk_tokenSet_7() {
		long[] data = { 0L, 72057594037927936L, 0L, 0L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_7 = new BitSet(mk_tokenSet_7());
	private static final long[] mk_tokenSet_8() {
		long[] data = { 0L, 1729382256910270464L, 0L, 0L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_8 = new BitSet(mk_tokenSet_8());
	private static final long[] mk_tokenSet_9() {
		long[] data = { 0L, 576460752303423488L, 0L, 0L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_9 = new BitSet(mk_tokenSet_9());
	private static final long[] mk_tokenSet_10() {
		long[] data = new long[10];
		data[1]=72057594440581120L;
		data[2]=-4503393468858320L;
		data[3]=-9223336852480614401L;
		data[4]=206158430208L;
		return data;
	}
	public static final BitSet _tokenSet_10 = new BitSet(mk_tokenSet_10());
	private static final long[] mk_tokenSet_11() {
		long[] data = { 0L, 2305843009213693952L, 0L, 0L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_11 = new BitSet(mk_tokenSet_11());
	private static final long[] mk_tokenSet_12() {
		long[] data = { 0L, 6917529027641081856L, 0L, 0L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_12 = new BitSet(mk_tokenSet_12());
	private static final long[] mk_tokenSet_13() {
		long[] data = new long[10];
		data[0]=2L;
		data[1]=9151314447078260736L;
		data[2]=-1125693739777473L;
		data[3]=-9078342055138164737L;
		data[4]=140703128616943L;
		return data;
	}
	public static final BitSet _tokenSet_13 = new BitSet(mk_tokenSet_13());
	private static final long[] mk_tokenSet_14() {
		long[] data = new long[10];
		data[1]=1152921504606846976L;
		data[2]=3940649673949696L;
		data[3]=-9223372036854775808L;
		data[4]=219043340261L;
		return data;
	}
	public static final BitSet _tokenSet_14 = new BitSet(mk_tokenSet_14());
	private static final long[] mk_tokenSet_15() {
		long[] data = new long[10];
		data[0]=2L;
		data[1]=9079256852637679616L;
		data[2]=21392166957875726L;
		data[3]=-9223339601502904320L;
		data[4]=1065151889391L;
		return data;
	}
	public static final BitSet _tokenSet_15 = new BitSet(mk_tokenSet_15());
	private static final long[] mk_tokenSet_16() {
		long[] data = { 2L, 4611686018427387904L, 1L, 0L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_16 = new BitSet(mk_tokenSet_16());
	private static final long[] mk_tokenSet_17() {
		long[] data = { 2L, 5188146770730811392L, 8388611L, 0L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_17 = new BitSet(mk_tokenSet_17());
	private static final long[] mk_tokenSet_18() {
		long[] data = new long[10];
		data[0]=2L;
		data[1]=7926335348030832640L;
		data[2]=21392098230009868L;
		data[4]=549755813891L;
		return data;
	}
	public static final BitSet _tokenSet_18 = new BitSet(mk_tokenSet_18());
	private static final long[] mk_tokenSet_19() {
		long[] data = { 0L, 0L, 560752071016448L, 0L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_19 = new BitSet(mk_tokenSet_19());
	private static final long[] mk_tokenSet_20() {
		long[] data = new long[10];
		data[1]=1152921504606846976L;
		data[2]=2L;
		data[3]=-9223372036854767616L;
		data[4]=206158430208L;
		return data;
	}
	public static final BitSet _tokenSet_20 = new BitSet(mk_tokenSet_20());
	private static final long[] mk_tokenSet_21() {
		long[] data = new long[10];
		data[1]=1152921504606846976L;
		data[2]=3940649673949698L;
		data[3]=-9223372036854775808L;
		data[4]=219043340261L;
		return data;
	}
	public static final BitSet _tokenSet_21 = new BitSet(mk_tokenSet_21());
	private static final long[] mk_tokenSet_22() {
		long[] data = new long[12];
		data[0]=-16L;
		data[1]=-1L;
		data[2]=-2049L;
		for (int i = 3; i<=4; i++) { data[i]=-1L; }
		data[5]=31L;
		return data;
	}
	public static final BitSet _tokenSet_22 = new BitSet(mk_tokenSet_22());
	private static final long[] mk_tokenSet_23() {
		long[] data = { 0L, 0L, 2048L, 0L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_23 = new BitSet(mk_tokenSet_23());
	private static final long[] mk_tokenSet_24() {
		long[] data = new long[12];
		data[0]=-16L;
		data[1]=-576460752303423489L;
		data[2]=-8193L;
		for (int i = 3; i<=4; i++) { data[i]=-1L; }
		data[5]=31L;
		return data;
	}
	public static final BitSet _tokenSet_24 = new BitSet(mk_tokenSet_24());
	private static final long[] mk_tokenSet_25() {
		long[] data = { 0L, 0L, 8192L, 0L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_25 = new BitSet(mk_tokenSet_25());
	private static final long[] mk_tokenSet_26() {
		long[] data = new long[10];
		data[1]=576460752303423488L;
		data[2]=68719476736L;
		data[3]=-9223339601502920704L;
		data[4]=481036337153L;
		return data;
	}
	public static final BitSet _tokenSet_26 = new BitSet(mk_tokenSet_26());
	private static final long[] mk_tokenSet_27() {
		long[] data = new long[10];
		data[1]=1224979099047428096L;
		data[2]=-1125693748166096L;
		data[3]=-9223301668144168961L;
		data[4]=493921247205L;
		return data;
	}
	public static final BitSet _tokenSet_27 = new BitSet(mk_tokenSet_27());
	private static final long[] mk_tokenSet_28() {
		long[] data = new long[10];
		data[2]=1572866L;
		data[3]=-9223372036854775808L;
		data[4]=206158430208L;
		return data;
	}
	public static final BitSet _tokenSet_28 = new BitSet(mk_tokenSet_28());
	private static final long[] mk_tokenSet_29() {
		long[] data = new long[10];
		data[3]=-9223372036854775808L;
		data[4]=206158430208L;
		return data;
	}
	public static final BitSet _tokenSet_29 = new BitSet(mk_tokenSet_29());
	private static final long[] mk_tokenSet_30() {
		long[] data = new long[10];
		data[2]=68719476736L;
		data[3]=-9223339601502920704L;
		data[4]=481036337153L;
		return data;
	}
	public static final BitSet _tokenSet_30 = new BitSet(mk_tokenSet_30());
	private static final long[] mk_tokenSet_31() {
		long[] data = new long[10];
		data[2]=68719476736L;
		data[3]=-9223339601505017856L;
		data[4]=481036337153L;
		return data;
	}
	public static final BitSet _tokenSet_31 = new BitSet(mk_tokenSet_31());
	private static final long[] mk_tokenSet_32() {
		long[] data = new long[10];
		data[1]=1729382256910270464L;
		data[2]=3377768448393734L;
		data[3]=-9223336852723834880L;
		data[4]=511109504997L;
		return data;
	}
	public static final BitSet _tokenSet_32 = new BitSet(mk_tokenSet_32());
	private static final long[] mk_tokenSet_33() {
		long[] data = new long[10];
		data[1]=576460752303423488L;
		data[2]=68721573888L;
		data[3]=-9223339601502920704L;
		data[4]=481036337153L;
		return data;
	}
	public static final BitSet _tokenSet_33 = new BitSet(mk_tokenSet_33());
	private static final long[] mk_tokenSet_34() {
		long[] data = { 0L, 5188146770730811392L, 0L, 0L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_34 = new BitSet(mk_tokenSet_34());
	private static final long[] mk_tokenSet_35() {
		long[] data = { 2L, 5188146770730811392L, 8388610L, 0L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_35 = new BitSet(mk_tokenSet_35());
	private static final long[] mk_tokenSet_36() {
		long[] data = { 2L, 4611686018427387904L, 0L, 0L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_36 = new BitSet(mk_tokenSet_36());
	private static final long[] mk_tokenSet_37() {
		long[] data = new long[10];
		data[0]=2L;
		data[1]=9079256852637679616L;
		data[2]=21392166949487118L;
		data[3]=-9223339601502920704L;
		data[4]=140685948747759L;
		return data;
	}
	public static final BitSet _tokenSet_37 = new BitSet(mk_tokenSet_37());
	private static final long[] mk_tokenSet_38() {
		long[] data = new long[10];
		data[3]=-9223372036854767616L;
		data[4]=206158430209L;
		return data;
	}
	public static final BitSet _tokenSet_38 = new BitSet(mk_tokenSet_38());
	private static final long[] mk_tokenSet_39() {
		long[] data = new long[10];
		data[3]=-9223372036854775808L;
		data[4]=206158430209L;
		return data;
	}
	public static final BitSet _tokenSet_39 = new BitSet(mk_tokenSet_39());
	private static final long[] mk_tokenSet_40() {
		long[] data = { 0L, 0L, 32985415942144L, 0L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_40 = new BitSet(mk_tokenSet_40());
	private static final long[] mk_tokenSet_41() {
		long[] data = { 0L, 0L, 527766655074304L, 0L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_41 = new BitSet(mk_tokenSet_41());
	private static final long[] mk_tokenSet_42() {
		long[] data = new long[10];
		data[1]=1152921504606846976L;
		data[2]=2L;
		data[3]=-9223372036854767616L;
		data[4]=206158430209L;
		return data;
	}
	public static final BitSet _tokenSet_42 = new BitSet(mk_tokenSet_42());
	private static final long[] mk_tokenSet_43() {
		long[] data = new long[10];
		data[1]=1152921504606846976L;
		data[2]=2L;
		data[3]=-9223372036854775808L;
		data[4]=206158430209L;
		return data;
	}
	public static final BitSet _tokenSet_43 = new BitSet(mk_tokenSet_43());
	private static final long[] mk_tokenSet_44() {
		long[] data = { 0L, 7493989779944505344L, 0L, 0L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_44 = new BitSet(mk_tokenSet_44());
	private static final long[] mk_tokenSet_45() {
		long[] data = { 0L, 0L, 4L, 0L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_45 = new BitSet(mk_tokenSet_45());
	private static final long[] mk_tokenSet_46() {
		long[] data = new long[10];
		data[0]=2L;
		data[1]=1729382256910270464L;
		data[2]=3377768440005120L;
		data[3]=-9223339601502920704L;
		data[4]=493921247205L;
		return data;
	}
	public static final BitSet _tokenSet_46 = new BitSet(mk_tokenSet_46());
	private static final long[] mk_tokenSet_47() {
		long[] data = { 0L, 1152921504606846976L, 2L, 0L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_47 = new BitSet(mk_tokenSet_47());
	private static final long[] mk_tokenSet_48() {
		long[] data = { 0L, 1152921504606846976L, 0L, 0L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_48 = new BitSet(mk_tokenSet_48());
	private static final long[] mk_tokenSet_49() {
		long[] data = new long[10];
		data[0]=2L;
		data[1]=7926335348030832640L;
		data[2]=21392098238398476L;
		data[4]=566935681675L;
		return data;
	}
	public static final BitSet _tokenSet_49 = new BitSet(mk_tokenSet_49());
	private static final long[] mk_tokenSet_50() {
		long[] data = new long[10];
		data[1]=1152921504606846976L;
		data[2]=512L;
		data[3]=-9223372036854775808L;
		data[4]=206158430208L;
		return data;
	}
	public static final BitSet _tokenSet_50 = new BitSet(mk_tokenSet_50());
	private static final long[] mk_tokenSet_51() {
		long[] data = new long[10];
		data[1]=1152921504606846976L;
		data[2]=3377768440005120L;
		data[3]=-9223339601502920704L;
		data[4]=493921247205L;
		return data;
	}
	public static final BitSet _tokenSet_51 = new BitSet(mk_tokenSet_51());
	private static final long[] mk_tokenSet_52() {
		long[] data = new long[10];
		data[1]=1729382256910270464L;
		data[2]=3377768448393734L;
		data[3]=-9223336852723834880L;
		data[4]=515396075501L;
		return data;
	}
	public static final BitSet _tokenSet_52 = new BitSet(mk_tokenSet_52());
	private static final long[] mk_tokenSet_53() {
		long[] data = new long[10];
		data[1]=1729382256910270464L;
		data[2]=2L;
		data[3]=24576L;
		data[4]=17179869184L;
		return data;
	}
	public static final BitSet _tokenSet_53 = new BitSet(mk_tokenSet_53());
	private static final long[] mk_tokenSet_54() {
		long[] data = new long[10];
		data[1]=1152921504606846976L;
		data[2]=3377699728916994L;
		data[3]=-9223372036854751232L;
		data[4]=236231598053L;
		return data;
	}
	public static final BitSet _tokenSet_54 = new BitSet(mk_tokenSet_54());
	private static final long[] mk_tokenSet_55() {
		long[] data = new long[10];
		data[3]=-9223371761976868864L;
		data[4]=206158430208L;
		return data;
	}
	public static final BitSet _tokenSet_55 = new BitSet(mk_tokenSet_55());
	private static final long[] mk_tokenSet_56() {
		long[] data = new long[10];
		data[2]=68719476740L;
		data[3]=-9223339051747106816L;
		data[4]=481036337153L;
		return data;
	}
	public static final BitSet _tokenSet_56 = new BitSet(mk_tokenSet_56());
	private static final long[] mk_tokenSet_57() {
		long[] data = new long[10];
		data[1]=1152921504606846976L;
		data[2]=3377699720528384L;
		data[3]=-9223372036854767616L;
		data[4]=493921247205L;
		return data;
	}
	public static final BitSet _tokenSet_57 = new BitSet(mk_tokenSet_57());
	private static final long[] mk_tokenSet_58() {
		long[] data = new long[10];
		data[1]=1729382256910270464L;
		data[2]=3377768440005120L;
		data[3]=-9223339601502920704L;
		data[4]=493921247205L;
		return data;
	}
	public static final BitSet _tokenSet_58 = new BitSet(mk_tokenSet_58());
	private static final long[] mk_tokenSet_59() {
		long[] data = new long[8];
		data[3]=1881145344L;
		return data;
	}
	public static final BitSet _tokenSet_59 = new BitSet(mk_tokenSet_59());
	private static final long[] mk_tokenSet_60() {
		long[] data = new long[10];
		data[0]=2L;
		data[1]=9079256852637679616L;
		data[2]=21392166949487116L;
		data[3]=-9223339601335148544L;
		data[4]=1047972020207L;
		return data;
	}
	public static final BitSet _tokenSet_60 = new BitSet(mk_tokenSet_60());
	private static final long[] mk_tokenSet_61() {
		long[] data = new long[10];
		data[1]=1152921504606846976L;
		data[2]=3377699720528384L;
		data[3]=-9223372036720558080L;
		data[4]=219043340261L;
		return data;
	}
	public static final BitSet _tokenSet_61 = new BitSet(mk_tokenSet_61());
	private static final long[] mk_tokenSet_62() {
		long[] data = new long[10];
		data[1]=1152921504606846976L;
		data[2]=3377699720528384L;
		data[3]=-9223372036687003648L;
		data[4]=219043340261L;
		return data;
	}
	public static final BitSet _tokenSet_62 = new BitSet(mk_tokenSet_62());
	private static final long[] mk_tokenSet_63() {
		long[] data = new long[8];
		data[3]=67108864L;
		return data;
	}
	public static final BitSet _tokenSet_63 = new BitSet(mk_tokenSet_63());
	private static final long[] mk_tokenSet_64() {
		long[] data = new long[10];
		data[1]=1152921504606846976L;
		data[2]=3377699720528384L;
		data[3]=-9223372036653449216L;
		data[4]=219043340261L;
		return data;
	}
	public static final BitSet _tokenSet_64 = new BitSet(mk_tokenSet_64());
	private static final long[] mk_tokenSet_65() {
		long[] data = new long[10];
		data[1]=1224979099047428096L;
		data[2]=-1125693748166092L;
		data[3]=-9223301668144168961L;
		data[4]=493921247205L;
		return data;
	}
	public static final BitSet _tokenSet_65 = new BitSet(mk_tokenSet_65());
	private static final long[] mk_tokenSet_66() {
		long[] data = new long[8];
		data[3]=274877906944L;
		return data;
	}
	public static final BitSet _tokenSet_66 = new BitSet(mk_tokenSet_66());
	private static final long[] mk_tokenSet_67() {
		long[] data = new long[8];
		data[3]=549755813888L;
		return data;
	}
	public static final BitSet _tokenSet_67 = new BitSet(mk_tokenSet_67());
	private static final long[] mk_tokenSet_68() {
		long[] data = new long[10];
		data[2]=512L;
		data[3]=-9223372036854775808L;
		data[4]=206158430208L;
		return data;
	}
	public static final BitSet _tokenSet_68 = new BitSet(mk_tokenSet_68());
	private static final long[] mk_tokenSet_69() {
		long[] data = new long[16];
		data[0]=-16L;
		for (int i = 1; i<=2; i++) { data[i]=-1L; }
		data[3]=-70368744177665L;
		data[4]=-1L;
		data[5]=31L;
		return data;
	}
	public static final BitSet _tokenSet_69 = new BitSet(mk_tokenSet_69());
	private static final long[] mk_tokenSet_70() {
		long[] data = new long[8];
		data[3]=70368744177664L;
		return data;
	}
	public static final BitSet _tokenSet_70 = new BitSet(mk_tokenSet_70());
	private static final long[] mk_tokenSet_71() {
		long[] data = new long[10];
		data[1]=1152921504606846976L;
		data[3]=-8358680908399640576L;
		data[4]=206158430208L;
		return data;
	}
	public static final BitSet _tokenSet_71 = new BitSet(mk_tokenSet_71());
	private static final long[] mk_tokenSet_72() {
		long[] data = new long[10];
		data[3]=-8358680908399640576L;
		data[4]=206158430208L;
		return data;
	}
	public static final BitSet _tokenSet_72 = new BitSet(mk_tokenSet_72());
	private static final long[] mk_tokenSet_73() {
		long[] data = { 0L, 6917529027641081856L, 3377699720527878L, 0L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_73 = new BitSet(mk_tokenSet_73());
	private static final long[] mk_tokenSet_74() {
		long[] data = { 0L, 2305843009213693952L, 3377699720527878L, 0L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_74 = new BitSet(mk_tokenSet_74());
	private static final long[] mk_tokenSet_75() {
		long[] data = { 0L, 0L, 8388608L, 0L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_75 = new BitSet(mk_tokenSet_75());
	private static final long[] mk_tokenSet_76() {
		long[] data = new long[10];
		data[1]=1152921504606846976L;
		data[2]=3377699720528384L;
		data[3]=-9223372036854775808L;
		data[4]=219043340261L;
		return data;
	}
	public static final BitSet _tokenSet_76 = new BitSet(mk_tokenSet_76());
	private static final long[] mk_tokenSet_77() {
		long[] data = new long[10];
		data[1]=1729382256910270464L;
		data[2]=3377699720528390L;
		data[3]=-9223372036854751232L;
		data[4]=240518168557L;
		return data;
	}
	public static final BitSet _tokenSet_77 = new BitSet(mk_tokenSet_77());
	private static final long[] mk_tokenSet_78() {
		long[] data = new long[8];
		data[1]=6917529027641081856L;
		data[2]=3377699720527876L;
		data[3]=144959613005987840L;
		return data;
	}
	public static final BitSet _tokenSet_78 = new BitSet(mk_tokenSet_78());
	private static final long[] mk_tokenSet_79() {
		long[] data = new long[8];
		data[1]=2305843009213693952L;
		data[2]=3377699720527876L;
		data[3]=562949953421312L;
		return data;
	}
	public static final BitSet _tokenSet_79 = new BitSet(mk_tokenSet_79());
	private static final long[] mk_tokenSet_80() {
		long[] data = { 0L, 4611686018427387904L, 0L, 0L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_80 = new BitSet(mk_tokenSet_80());
	private static final long[] mk_tokenSet_81() {
		long[] data = { 0L, 4611686018427387904L, 8L, 0L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_81 = new BitSet(mk_tokenSet_81());
	private static final long[] mk_tokenSet_82() {
		long[] data = { 0L, 2305843009213693952L, 4L, 0L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_82 = new BitSet(mk_tokenSet_82());
	private static final long[] mk_tokenSet_83() {
		long[] data = new long[10];
		data[0]=2L;
		data[1]=7926335348030832640L;
		data[2]=21392098230009868L;
		data[4]=554050779787L;
		return data;
	}
	public static final BitSet _tokenSet_83 = new BitSet(mk_tokenSet_83());
	private static final long[] mk_tokenSet_84() {
		long[] data = new long[10];
		data[0]=2L;
		data[1]=7926335348030832640L;
		data[2]=21392098230009868L;
		data[3]=-9223372036854775808L;
		data[4]=760209209995L;
		return data;
	}
	public static final BitSet _tokenSet_84 = new BitSet(mk_tokenSet_84());
	private static final long[] mk_tokenSet_85() {
		long[] data = new long[10];
		data[1]=1152921504606846976L;
		data[2]=3377699720528386L;
		data[3]=-9223372036854751232L;
		data[4]=240518168557L;
		return data;
	}
	public static final BitSet _tokenSet_85 = new BitSet(mk_tokenSet_85());
	private static final long[] mk_tokenSet_86() {
		long[] data = new long[10];
		data[1]=5764607523034234880L;
		data[2]=3377699720528386L;
		data[3]=-9223372036854751232L;
		data[4]=240518168559L;
		return data;
	}
	public static final BitSet _tokenSet_86 = new BitSet(mk_tokenSet_86());
	private static final long[] mk_tokenSet_87() {
		long[] data = new long[10];
		data[4]=2L;
		return data;
	}
	public static final BitSet _tokenSet_87 = new BitSet(mk_tokenSet_87());
	private static final long[] mk_tokenSet_88() {
		long[] data = new long[10];
		data[2]=3377699720527872L;
		data[4]=4294965888L;
		return data;
	}
	public static final BitSet _tokenSet_88 = new BitSet(mk_tokenSet_88());
	private static final long[] mk_tokenSet_89() {
		long[] data = new long[10];
		data[0]=2L;
		data[1]=7926335348030832640L;
		data[2]=21392098230009868L;
		data[4]=549755813899L;
		return data;
	}
	public static final BitSet _tokenSet_89 = new BitSet(mk_tokenSet_89());
	private static final long[] mk_tokenSet_90() {
		long[] data = new long[10];
		data[0]=2L;
		data[1]=9151314447078260736L;
		data[2]=-1125693739777473L;
		data[3]=-9223301668144152577L;
		data[4]=1065151889391L;
		return data;
	}
	public static final BitSet _tokenSet_90 = new BitSet(mk_tokenSet_90());
	private static final long[] mk_tokenSet_91() {
		long[] data = { 0L, 6917529027641081856L, 18014398509481984L, 0L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_91 = new BitSet(mk_tokenSet_91());
	private static final long[] mk_tokenSet_92() {
		long[] data = { 0L, 3858759680L, 0L, 0L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_92 = new BitSet(mk_tokenSet_92());
	private static final long[] mk_tokenSet_93() {
		long[] data = { 0L, 402653184L, 0L, 0L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_93 = new BitSet(mk_tokenSet_93());
	private static final long[] mk_tokenSet_94() {
		long[] data = { 0L, 268435456L, 0L, 0L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_94 = new BitSet(mk_tokenSet_94());
	
	}
