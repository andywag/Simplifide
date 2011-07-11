/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.sourcefile.antlr.stream;

import java.util.ArrayList;
import java.util.List;

import antlr.CharStreamException;
import antlr.Token;
import antlr.TokenStream;
import antlr.TokenStreamException;

import com.simplifide.base.BaseLog;
import com.simplifide.base.basic.struct.NodePosition;
import com.simplifide.base.basic.util.IDEout;
import com.simplifide.base.core.doc.HdlDoc;
import com.simplifide.base.core.doc.ToDoObject;
import com.simplifide.base.core.error.err.SyntaxError;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.sourcefile.antlr.grammar.BaseLexer;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.TopASTNodeFolding;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.sourcefile.antlr.tok.TopASTToken;








/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: May 2, 2004
 * Time: 2:13:28 PM
 * To change this template use File | Settings | File Templates.
 */

public abstract class PositionStream implements TokenStream {
    
    private ArrayList<TopASTToken> tokenList;
    private int index = 0;
    
    private ParseContext context;
    // List of nodes for compiler directives for folding
    private List<TopASTNode> directiveList = new ArrayList<TopASTNode>();
    private List<ToDoObject> todoList      = new ArrayList<ToDoObject>();
    private boolean preserveSpace = false;
    
    public PositionStream(ParseContext context) {
    	this.setContext(context);
    	
    }
     
   
    public TopASTToken nextRealToken() {
    	 Token tok = this.getToken(getIndex());
         setIndex(getIndex() + 1);
    	 return (TopASTToken) tok;
    }
    /** 
     * @return 
     @todo : Doesn't handle next token of comment */
    public Token nextToken() {
        Token tok = this.getToken(getIndex());
        setIndex(getIndex() + 1);
        while (hiddenToken(tok)) {
            tok = (Token) this.getToken(getIndex());
            setIndex(getIndex() + 1);
        }
        return tok;
    }
    
    
    public abstract boolean directiveTokenStart(Token tok);
    public abstract boolean directiveTokenFinish(Token tok);

    public abstract boolean hiddenToken(Token tok);
    public abstract boolean isWhiteSpace(Token tok);
    public abstract boolean docToken(Token tok);
    public abstract boolean isSingleLineComment(Token tok);
    public abstract void docText(Token tok, HdlDoc doc);
    public abstract boolean isNewLineToken(Token tok);
    
    public abstract boolean getStartOfGeneratedCode(Token tok);
    public abstract boolean getEndOfGeneratedCode(Token tok);
    
    
    /**
     * 
     * @param index 
     * @return 
     */
    public TopASTToken getASTToken(int index) {
        return (TopASTToken) this.getToken(index);
    }
    
    /**
     * 
     * @param index 
     * @return 
     */
    public Token getToken(int index) {
        if (index >= tokenList.size())
            return new TopASTToken(1,"null");
        else return (Token) tokenList.get(index);
        
    }
    
    /** Returns a list of folds based on compiler directive ifdefs */
    public List<TopASTNode> getDirectiveFoldList() {
    	return new ArrayList<TopASTNode>();
    }
    
    public List<TopASTNode> getCommentFoldList() {
    	
    	TopASTNodeFolding newNode = null;
    	ArrayList<TopASTNode> commentList = new ArrayList<TopASTNode>();
    	String ws = "";
    	
    	TopASTNodeFolding templateFold = null;
    	
    	for (TopASTToken tok : this.getTokenList()) {
    		
    		if (this.getStartOfGeneratedCode(tok)) {
				templateFold = new TopASTNodeFolding(tok.getPosition(),tok.getText());
			}
			else if (this.getEndOfGeneratedCode(tok)) {
				if (templateFold != null) {
					templateFold.updatePosition(tok.getEndPosition(), tok.getText());
					commentList.add(templateFold);
					templateFold = null;
				}
			}
			else if (templateFold != null) {
				templateFold.updatePosition(tok.getEndPosition(), tok.getText());
			}
    		
    		
    		if (newNode == null) {
    			if (this.hiddenToken(tok) || this.docToken(tok)) {
        			newNode = new TopASTNodeFolding(tok.getPosition(),tok.getText());
        		}
    		}
    		else {
    			
    			
    			
    			if (this.hiddenToken(tok) || this.docToken(tok)) {
    				newNode.updatePosition(tok.getEndPosition(), ws + tok.getText());
    				ws = "";
    			}
    			else if (this.isWhiteSpace(tok)) {
    				ws += tok.getText();
    			}
    			else {
    				commentList.add(newNode);
    				newNode = null;
    				ws = "";
    			}
    		}
    	}
    	
    	return commentList;
    }
    
    private TopASTToken getNextToken(TokenStream stream, int depth) {
    	try {
    		if (depth == 10) return null;
			TopASTToken tok = (TopASTToken) stream.nextToken();
			return tok;
		} catch (TokenStreamException e) {
			String loc = this.context.getURILocation().toString();
			BaseLog.logErrorString("Lex Error in" + loc, e);
			BaseLexer lex = (BaseLexer) stream;
			try {
				lex.consume();
			} catch (CharStreamException e1) {
				BaseLog.logErrorString("Lex Error in" + loc,e);
			}
			return getNextToken(stream,depth + 1);
		}
    }
    
    /**
     * 
     * @param stream 
     */
    public void createArrayList(TokenStream stream, String name) {
        
        if (tokenList == null) tokenList = new ArrayList<TopASTToken>();
        tokenList.clear();
        TopASTToken tok;
        TopASTToken lasttok = null;
        int pos = 0;
        HdlDoc doc = null;
        boolean preTok = false;
        boolean singleLineToken = false;
        boolean first = true;
        
        try {
   
            while ((tok = (TopASTToken) getNextToken(stream,0)) != null) {
            	boolean single = this.isSingleLineComment(tok);
            	boolean pre = lasttok != null && lasttok.getLine() == tok.getLine();
            	if (this.docToken(tok) || (single && pre) & !first) {
                    if (doc == null) {
                    	if (lasttok != null && lasttok.getLine() == tok.getLine()) {
                    		preTok = true;
                    	}
                    	else {
                    		preTok = false;
                    	}
                    	if (single) singleLineToken = true;
                    	else singleLineToken = false;
                    	doc = new HdlDoc();
                    }
                    this.docText(tok,doc);
            	}
            	else if (this.isNewLineToken(tok) && singleLineToken && doc != null && preTok) { // Close out Single Line Comments which are based on line
            		lasttok.setDoc(doc);
            		lasttok = tok;
                    doc = null;
            	}
            	/*else if (this.isNewLineToken(tok) && doc != null && doc.isSingleLine()) { // Close out Single Line Comments which are based on line
            		lasttok.setDoc(doc);
            		lasttok = tok;
                    doc = null;
            	}*/
                else if (!this.hiddenToken(tok) && !this.isWhiteSpace(tok)){
                	if (preTok) {
                		lasttok.setDoc(doc);
                	}
                	else {
                		tok.setDoc(doc);
                	}
                    doc = null;
                    lasttok = tok;
                    first = false;
                }
                pos = this.addToken((TopASTToken)tok,pos,stream,doc);
                
                if (tok.getType() == 1) break;
                
                
            }
            
        } catch (TokenStreamException e) {
			String loc = this.context.getURILocation().toString();
        	BaseLog.logErrorString("Lex error in" + loc, e);
        }
    }
    
    /**
     * 
     * @param tok 
     * @param pos 
     * @return 
     */
    public int addToken(TopASTToken tok,int pos, TokenStream stream, HdlDoc doc) throws TokenStreamException{
        if (this.isWhiteSpace(tok) == false || preserveSpace == true) {
            tokenList.add(tok);
        }
        
        if (tok.getType() == 1) return pos;
        return pos + tok.getText().length();
    }
    
   
    
    public List<SyntaxError> createRealSyntaxErrorList(ReferenceLocation base) {
    	List<SyntaxError> errList = new ArrayList();
    	SyntaxError error = null;
    	for (int i=0;i<this.getTokenList().size()-1;i++) {
    		TopASTToken tok = (TopASTToken) this.getTokenList().get(i);
    		if (this.isWhiteSpace(tok)) continue;
    		
    		if (tok.isMarked() && tok.getPosition() != 0) {
    			if (error == null) {
    				TopASTToken realtok = tok.getRealToken();
    				NodePosition pos = realtok.getNodePosition();
    				error = new SyntaxError(realtok.getException(),base.copyLocation(pos.getStartPos(), pos.getLength(), pos.getStartLine()));
    				errList.add(error);
    			}
    			else {
    				
    				//error.getLocation().setLength(tok.getNodePosition().getEndPos() - error.getLocation().getDocPosition());   
    			}
    		}
    		
    		else if (error != null) {
    			error = null;
    		}
    		
    	}
    	return errList;
    }
    
    
    
  
    
    
    
    
    
    
    /**
     * 
     */
    public void debugList() {
        for (int i=0;i<tokenList.size();i++) {
            Token tok = this.getToken(i);
            if (this.hiddenToken(tok))
                IDEout.printlnAlways("Hidden Token" + i + tokenList.get(i));
            else
                IDEout.printlnAlways("Token" + i + tokenList.get(i));
        }
        
    }
    
    /**
     * 
     * @return 
     */
    public ArrayList<TopASTToken> getTokenList() {
        return tokenList;
    }
    
    
    public void setTokenList(ArrayList<TopASTToken> tokenList) {
        this.tokenList = tokenList;
    }
    
    
    public int getIndex() {
        return index;
    }
    
    
    public void setIndex(int index) {
        this.index = index;
    }


	public void setContext(ParseContext context) {
		this.context = context;
	}


	public ParseContext getContext() {
		return context;
	}


	public void setDirectiveList(List<TopASTNode> directiveList) {
		this.directiveList = directiveList;
	}


	public List<TopASTNode> getDirectiveList() {
		return directiveList;
	}


	public void setTodoList(List<ToDoObject> todoList) {
		this.todoList = todoList;
	}


	public List<ToDoObject> getTodoList() {
		return todoList;
	}


	public void setPreserveSpace(boolean preserveSpace) {
		this.preserveSpace = preserveSpace;
	}


	public boolean isPreserveSpace() {
		return preserveSpace;
	}



    
   

    
    
}
