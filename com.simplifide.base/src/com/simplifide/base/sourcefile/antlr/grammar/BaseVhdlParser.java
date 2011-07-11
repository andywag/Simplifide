package com.simplifide.base.sourcefile.antlr.grammar;

import antlr.MismatchedTokenException;
import antlr.NoViableAltException;
import antlr.ParserSharedInputState;
import antlr.RecognitionException;
import antlr.TokenBuffer;
import antlr.TokenStream;
import antlr.TokenStreamException;
import antlr.collections.impl.BitSet;

import com.simplifide.base.sourcefile.antlr.tok.TopASTToken;
import com.simplifide.base.vhdl.parse.grammar.VhdlTokenTypes;

public abstract class BaseVhdlParser extends BaseParser{

	 public BaseVhdlParser(TokenBuffer tokenBuf, int k) 
	    {
	        super(tokenBuf,k);
	    }
	     public BaseVhdlParser(TokenStream tokenBuf, int k) 
	    {
	        super(tokenBuf,k);
	    }

	  public BaseVhdlParser(ParserSharedInputState state, int k) 
	    {
	        super(state,k);
	    }
	  
	  /** Handles exception for signal_kind issue only NoViable 
	   *  Does nothing so the error can be caught by the match of SEMI*/
	  public void var_assign_exception(RecognitionException ex) {
		 
	  }
	  /** Handles exception for signal_kind issue only NoViable 
	   *  Does nothing so the error can be caught by the match of SEMI*/
	  public void signal_kind_exception(RecognitionException ex) {
	  }
	  
	  public void reportError(RecognitionException ex) {
		  if (ex instanceof NoViableAltException) {
			  NoViableAltException exc = (NoViableAltException) ex;
		  }
		  super.reportError(ex);
	  }
	  
	  /**Make sure current lookahead symbol matches token type <tt>t</tt>.
	   * Throw an exception upon mismatch, which is catch by either the
	   * error handler or by the syntactic predicate.
	   */
	  public void match(int t) throws MismatchedTokenException, TokenStreamException {
	      if (LA(1) != t)
	    	  if (t == VhdlTokenTypes.SEMI && this.inputState.guessing == 0) {
	    		  TopASTToken tok = (TopASTToken) this.LT(0);
	    		  MismatchedTokenException e = new MismatchedTokenException(tokenNames, LT(1), t, false, getFilename());
	    		  tok.setMarked(true);
	    		  tok.setException(e);
	    		  return;
	    	  }
	    	  else {
	    		  throw new MismatchedTokenException(tokenNames, LT(1), t, false, getFilename());
	    	  }
	      else
	      // mark token as consumed -- fetch next token deferred until LA/LT
	          consume();
	  }

	  /**Make sure current lookahead symbol matches the given set
	   * Throw an exception upon mismatch, which is catch by either the
	   * error handler or by the syntactic predicate.
	   */
	  public void match(BitSet b) throws MismatchedTokenException, TokenStreamException {
	      if (!b.member(LA(1)))
	    	  
	    	  if (b.member(VhdlTokenTypes.SEMI) && this.inputState.guessing == 0) return;
	    	  else throw new MismatchedTokenException(tokenNames, LT(1), b, false, getFilename());
	      else
	      // mark token as consumed -- fetch next token deferred until LA/LT
	          consume();
	  }
	
}
