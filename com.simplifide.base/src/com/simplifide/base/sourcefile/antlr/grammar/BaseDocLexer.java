/*
 * BaseDocLexer.java
 *
 * Created on April 13, 2007, 3:05 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.sourcefile.antlr.grammar;

import antlr.LexerSharedInputState;

import com.simplifide.base.sourcefile.antlr.tok.TopDocASTToken;

/**
 *
 * @author Andy
 */
public abstract class BaseDocLexer extends BaseLexer{
    
    /** Creates a new instance of BaseDocLexer 
     * @param state 
     */
     public BaseDocLexer(LexerSharedInputState state ) {
         super(state);
         this.tokenObjectClass = TopDocASTToken.class;
    }

   
    
}
