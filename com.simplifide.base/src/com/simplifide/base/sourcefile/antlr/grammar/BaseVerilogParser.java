package com.simplifide.base.sourcefile.antlr.grammar;

import antlr.MismatchedTokenException;
import antlr.ParserSharedInputState;
import antlr.TokenBuffer;
import antlr.TokenStream;
import antlr.TokenStreamException;
import antlr.collections.impl.BitSet;

import com.simplifide.base.sourcefile.antlr.tok.TopASTToken;
import com.simplifide.base.verilog.parse.grammar.system.SystemVerilogTokenTypes;

public abstract class BaseVerilogParser extends BaseParser{

	private static int[] IDENTS = {SystemVerilogTokenTypes.LITERAL_s,
								   SystemVerilogTokenTypes.LITERAL_us,
								   SystemVerilogTokenTypes.LITERAL_ms,
								   SystemVerilogTokenTypes.LITERAL_ps,
								   SystemVerilogTokenTypes.LITERAL_fs,
								   SystemVerilogTokenTypes.LITERAL_step,
								   SystemVerilogTokenTypes.LITERAL_do};

    public BaseVerilogParser(TokenBuffer tokenBuf, int k) 
    {
        super(tokenBuf,k);
    }
     public BaseVerilogParser(TokenStream tokenBuf, int k) 
    {
        super(tokenBuf,k);
    }

  public BaseVerilogParser(ParserSharedInputState state, int k) 
    {
        super(state,k);
    }
	
  /**Make sure current lookahead symbol matches token type <tt>t</tt>.
   * Throw an exception upon mismatch, which is catch by either the
   * error handler or by the syntactic predicate.
   */
  public void match(int t) throws MismatchedTokenException, TokenStreamException {
	 int la = LA(1);
	 int la2 = LA(2);
	 int la3 = LA(3);
      if (la != t)
    	  if (t == SystemVerilogTokenTypes.SEMI && this.inputState.guessing == 0) {
    		  TopASTToken tok = (TopASTToken) this.LT(0);
    		  MismatchedTokenException e = new MismatchedTokenException(tokenNames, LT(1), t, false, getFilename());
    		  tok.setMarked(true);
    		  tok.setException(e);
    		  return;
    	  } 
    	  else {
    		  throw new MismatchedTokenException(tokenNames, LT(1), t, false, getFilename());
    	  }
      // IDEA related to handle identifiers as keyword
	  /*else if (t== SystemVerilogTokenTypes.IDENTIFIER ) {
		  int type = LA(1);
		  for (int a : IDENTS) {
			  if (type == a) return;
		  }
	  }*/
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
    	  
    	  if (b.member(SystemVerilogTokenTypes.SEMI) && this.inputState.guessing == 0) return;
    	  else throw new MismatchedTokenException(tokenNames, LT(1), b, false, getFilename());
      else
      // mark token as consumed -- fetch next token deferred until LA/LT
          consume();
  }
  
}
