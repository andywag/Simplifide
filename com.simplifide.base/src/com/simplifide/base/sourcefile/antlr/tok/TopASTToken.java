/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.sourcefile.antlr.tok;

import antlr.CommonHiddenStreamToken;
import antlr.RecognitionException;

import com.simplifide.base.basic.struct.DocPosition;
import com.simplifide.base.basic.struct.NodePosition;
import com.simplifide.base.core.doc.HdlDoc;





/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: Mar 17, 2004
 * Time: 10:26:45 AM
 * To change this template use File | Settings | File Templates.
 */


public class TopASTToken extends CommonHiddenStreamToken
{

   
    public static final int BASE = 5;
    private boolean marked = false;

    private int position;
    
    
    /** Poor Implementation of Documentation */
    private String commentBefore;
    /** Proper Implementation of Documentation although not really the correct place */
    private HdlDoc doc;
    private RecognitionException exception; 
    
   
    
   // private TopAnnotation annotation;

    /**
     * 
     */
    public TopASTToken() {super();}
    /**
     * 
     * @param id 
     */
    public TopASTToken(String id)
    {
        this(BASE,id);
    }
    /**
     * 
     * @param type 
     * @param id 
     */
    public TopASTToken(int type,String id)
    {
        super(type,id);
    }

    
    public int getPosition() {
        return position;
    }

   
    public void setPosition(int position) {
        this.position = position;
    }

    public int getLength() {
        if (this.getText() == null) return 0;
        return this.getText().length();
    }

    /** This method dereferences this token to get the real token and is used for errors based on
     * `define instantiations 
     * @return
     */
    public TopASTToken getRealToken() {
    	return this;
    }
    

  
    public String displayString()
    {
        return this.getText() + this.getLine() + " " + this.getColumn() +" "+ this.getPosition();
    }

    public String toString()
    {
        return "[\"" + getText() + "\",<" + this.getType() + ">,line=" + line + ",col=" + col + ",pos=" + this.position + "]";
    }

    /**
     * 
     * @return 
     */
    public NodePosition getNodePosition()
    {
        return new NodePosition(this.getPosition(),this.getPosition() + this.getLength(),this.getLine(),this.getColumn());
    }
     
    /**
     * 
     * @return 
     */
    public int getEndPosition()
    {
        return this.getPosition() + this.getLength();
    }
    
    /**
     * 
     * @return 
     */
    public DocPosition getStartDocPosition()
    {
        return new DocPosition(this.getLine(),this.getColumn(),this.getPosition());
    }

    /**
     * 
     * @return 
     */
    public DocPosition getEndDocPosition()
    {
        int len = 0;
        if (this.getText() != null) len = this.getText().length();
        return new DocPosition(this.getLine(),this.getColumn() + len,this.getPosition() + len);
    }

    /**
     * 
     * @return 
     */
    public boolean isMarked() {
        return marked;
    }

    /**
     * 
     * @param marked 
     */
    public void setMarked(boolean marked) {
        this.marked = marked;
    }

   

    /**
     * 
     * @return 
     */
    public String getCommentBefore() {
        return commentBefore;
    }

    /**
     * 
     * @param commentBefore 
     */
    public void setCommentBefore(String commentBefore) {
        this.commentBefore = commentBefore;
    }
    
     public HdlDoc getDoc() {
        return doc;
    }

    public void setDoc(HdlDoc doc) {
        this.doc = doc;
    }
	public void setException(RecognitionException exception) {
		this.exception = exception;
	}
	public RecognitionException getException() {
		return exception;
	}
  
}
