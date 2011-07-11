/*
 * BaseLexer.java
 *
 * Created on October 18, 2005, 3:55 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.sourcefile.antlr.grammar;

import antlr.CharStreamException;
import antlr.LexerSharedInputState;
import antlr.Token;

import com.simplifide.base.sourcefile.antlr.tok.TopASTToken;


/**
 *
 * @author awagner
 */
public abstract class BaseLexer extends antlr.CharScanner
{
    public int lastPosition = 0;
    public int docPosition  = 0;
    private boolean wait = false;
    /** Creates a new instance of BaseLexer */
    public BaseLexer(LexerSharedInputState state) 
    {
        super(state);
        init();
    }
    
    private void init()
    {
        tokenObjectClass = TopASTToken.class;
    }
    
    public void consume() throws CharStreamException {
   
        if (inputState.guessing == 0)
        {
            if (wait)
            {
                lastPosition = docPosition;
                wait = false;
            }
            docPosition++;
            //if (LA(1) != '\r') docPosition++;
        }
        super.consume();
    }
    
    protected Token makeToken(int t) {

        TopASTToken utok = (TopASTToken) super.makeToken(t);
        utok.setPosition(lastPosition);
        
        //IDEout.printlnOver("Makign Token" + utok  + docPosition + "-" + lastPosition);
        this.wait = true;
        return utok;
    }


    
}
