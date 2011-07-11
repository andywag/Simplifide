/*
 * BaseParser.java
 *
 * Created on October 18, 2005, 3:55 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.sourcefile.antlr.grammar;



import antlr.ParserSharedInputState;
import antlr.RecognitionException;
import antlr.Token;
import antlr.TokenBuffer;
import antlr.TokenStream;
import antlr.TokenStreamException;
import antlr.collections.impl.BitSet;

import com.simplifide.base.BaseLog;
import com.simplifide.base.sourcefile.antlr.tok.TopASTToken;

/**
 *
 * @author awagner
 */
public abstract class BaseParser extends  antlr.LLkParser {
    
   public boolean errored;
   public boolean erroredFirst;
   public boolean totErr;
   public boolean syntaxError;
   
   private RecognitionException exception;
    /** Creates a new instance of BaseParser */
    public BaseParser(TokenBuffer tokenBuf, int k) 
    {
        super(tokenBuf,k);
    }
     public BaseParser(TokenStream tokenBuf, int k) 
    {
        super(tokenBuf,k);
    }

  public BaseParser(ParserSharedInputState state, int k) 
    {
        super(state,k);
    }

  public abstract void source_text() throws RecognitionException, TokenStreamException;
  public void source_text_name() throws RecognitionException, TokenStreamException{}

  
  
  
  public void reportError(RecognitionException ex)
  {
	  	this.exception = ex;
        returnAST = null;
        this.totErr = true;
        this.errored = true;
        this.erroredFirst = true;
        this.syntaxError = true;
        
    }

  public void recover(RecognitionException ex, BitSet tokenSet) throws TokenStreamException {
	  consume();
	  consumeUntil(tokenSet);
  }

  
    public void consumeUntil(int tokenType) throws TokenStreamException {
        while (LA(1) != Token.EOF_TYPE && LA(1) != tokenType) {
            consume();
        }
        this.errored = false;
    }

    /** Consume tokens until one matches the given token set */
    public void consumeUntil(BitSet set) throws TokenStreamException {
        while (LA(1) != Token.EOF_TYPE && !set.member(LA(1))) {
            consume();
        }
        this.errored = false;
    }

    public void consume()
    {
        if (this.errored)
        {
            try {
                TopASTToken tok = (TopASTToken) this.LT(1);
                if (this.erroredFirst) tok.setException(this.exception);
                else this.erroredFirst = false;
                tok.setMarked(true);
            } catch (TokenStreamException e) {
                BaseLog.logError(e);
            }
        }
        try {
            super.consume();
        } catch(Exception e) 
        {
            BaseLog.logError(e);
        }
    }

}
