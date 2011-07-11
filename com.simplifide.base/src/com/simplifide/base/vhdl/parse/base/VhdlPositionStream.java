/*
 * VhdlPositionStream.java
 *
 * Created on October 18, 2005, 10:53 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.vhdl.parse.base;

import antlr.Token;
import antlr.TokenStream;
import antlr.TokenStreamException;

import com.simplifide.base.basic.struct.DocPosition;
import com.simplifide.base.core.doc.HdlDoc;
import com.simplifide.base.core.doc.ToDoObject;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.sourcefile.antlr.stream.PositionStream;
import com.simplifide.base.sourcefile.antlr.tok.TopASTToken;
import com.simplifide.base.vhdl.parse.grammar.VhdlTokenTypes;



/**
 *
 * @author Andy Wagner
 */
public class VhdlPositionStream extends PositionStream{
    

    /** Creates a new instance of VhdlPositionStream */
    public VhdlPositionStream(ParseContext context) {
        super(context);
    }
     
   
    
    public boolean scriptToken(Token tok) 
    {
       
        if (tok.getType() == VhdlTokenTypes.COMMENT)
        {
            String utok = tok.getText();
            if (utok.length() < 3) return false;
            if (utok.startsWith("--#")) return true;
            else return false; 
        }
        return false;
    }
    
    public boolean docToken(Token tok) {
        TopASTToken utok = (TopASTToken) tok;
    	if (tok.getType() == VhdlTokenTypes.COMMENT) {
            String text = tok.getText();
            if (text.length() < 3) return false;
            
            String utext = text.substring(3);
        	if (utext.contains("TODO")) {
        		int loc = utext.indexOf("TODO");
            	String todo = utext.substring(loc + 4);
            	todo.replace("\r", "");
            	DocPosition docp = utok.getStartDocPosition();
            	
            	ReferenceLocation rloc = new ReferenceLocation(this.getContext().getURILocation(),
            			utok.getStartDocPosition().getDocp(),
            			utok.getLength(),
            			utok.getLine());
            	ToDoObject obj = new ToDoObject(todo,rloc);
            	this.getTodoList().add(obj);
            }

            utext = text.substring(2,3);

            if (utext.equalsIgnoreCase("*")) return true;
            if (utext.equalsIgnoreCase("!")) return true;
            
            
        }
        return false;
    }
    
    public void docText(Token tok, HdlDoc doc) {
        VhdlDocParser.getInstance().parseDoc(doc, tok.getText(),this.getContext());       
    }
    
    public boolean hiddenToken(antlr.Token tok)
    {
        if (tok.getType() == VhdlTokenTypes.COMMENT) return true;
        else return false;    
    }
    
    public boolean initialHiddenToken(antlr.Token tok, TokenStream stream) throws TokenStreamException {
    	return this.hiddenToken(tok);
    }
    
    public boolean isWhiteSpace(antlr.Token tok)
    {
        if (tok.getType() == VhdlTokenTypes.WS_) return true;
        if (tok.getType() == VhdlTokenTypes.NEWLINE) return true;
        else return false;
    }
    
    @Override
	public boolean getEndOfGeneratedCode(Token tok) {
		if (tok.getType() == VhdlTokenTypes.COMMENT) {
			String tokText = tok.getText();
			String endText = VhdlTemplateHandler.TEMPLATE_STRING_END;
			
			if (tokText.startsWith(endText)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean getStartOfGeneratedCode(Token tok) {
		if (tok.getType() == VhdlTokenTypes.COMMENT) {
			if (tok.getText().startsWith(VhdlTemplateHandler.TEMPLATE_STRING_BEGIN)) return true;
		}
		return false;
	}



	@Override
	public boolean isSingleLineComment(Token tok) {
		if (tok.getType() == VhdlTokenTypes.COMMENT) return true;
		else return false;
	}



	@Override
	public boolean directiveTokenFinish(Token tok) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean directiveTokenStart(Token tok) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean isNewLineToken(Token tok) {
		if (tok.getType() == VhdlTokenTypes.NEWLINE) return true;
		return false;
	}
	
     
    
}
