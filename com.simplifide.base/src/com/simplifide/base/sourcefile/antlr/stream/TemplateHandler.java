/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.sourcefile.antlr.stream;

import java.util.ArrayList;

import antlr.TokenStream;
import antlr.TokenStreamException;

import com.simplifide.base.BaseLog;
import com.simplifide.base.basic.struct.NodePosition;
import com.simplifide.base.sourcefile.antlr.ParseDescriptor;
import com.simplifide.base.sourcefile.antlr.tok.TopASTToken;

/**
 * Class used to parse the input files and create a list of 
 * templates. 
 * 
 * @author andy
 *
 */
public abstract class TemplateHandler {

	public static final int TEMPLATE_NONE   = 0;
	public static final int TEMPLATE_START  = 1;
	public static final int TEMPLATE_END    = 2;
	public static final int TEMPLATE_OPEN_PAREN   = 5;
	public static final int TEMPLATE_CLOSE_PAREN  = 6;
	
	
	public static final int STATE_NONE     = 0;
	public static final int STATE_NORMAL   = 1;
	
	
	private PositionStream stream;
	private ParseDescriptor desc;
	
	// This is kind of a kludge as it is used as a convenience 
	private NodePosition position;
	
	public TemplateHandler(PositionStream stream, ParseDescriptor desc ) {
		this.stream = stream;
		this.setDesc(desc);
	}
	
	protected abstract int getGeneratedToken(TopASTToken tok);
	protected abstract boolean templateToken(TopASTToken tok, TemplateContents contents,
				boolean lastValid, ArrayList<TemplateContents> templateList);
	
	protected int getStartDeleteOffset(TopASTToken tok) {
		int pos = tok.getEndPosition();
		if (tok.getText().endsWith("\r"))
			pos = pos - 1;
		return pos;
	}
	
	/**
	 * Method used to locate the tokens which contain the delete list
	 * @param deleteList
	 * @param state
	 * @param tok
	 * @return
	 */
	private int handleDeleteList( ArrayList<NodePosition> deleteList, 
								  int state,
								  TopASTToken tok) {
		
		int newstate = state;
		int gtok = this.getGeneratedToken(tok);
		switch(state) {
			case STATE_NONE :
				if (gtok == TEMPLATE_START) {
					position.setStartPos(tok.getPosition());
					newstate = STATE_NORMAL;
				}
				break;
			case STATE_NORMAL :
				if (gtok == TEMPLATE_END) {
					position.setEndPos(tok.getEndPosition());
					deleteList.add(position);
					position = new NodePosition(-1);
					newstate = STATE_NONE;
				}
				break;
			default : 
				break;
		}
		return newstate;
		
	}
	
	/** 
	 * Parses the file to create a list of templates contained in 
	 * the TemplateList
	 */
	public TemplateList createTemplateList(TokenStream stream) {
    	
    	ArrayList<TemplateContents> templateList = new ArrayList<TemplateContents>();
    	TemplateContents contents = new TemplateContents();
    	
    	boolean template = false;
    	boolean lasttemplate = false;
    	
    	ArrayList<NodePosition> deleteList = new ArrayList<NodePosition>();
    	this.position = new NodePosition(-1);
    	int state = STATE_NONE;
    	
    	TopASTToken tok;
    	try {
			while ((tok = (TopASTToken) stream.nextToken()) != null) {
				if (tok.getType() == 1) break;
				//if (this.getStream().isWhiteSpace(tok)) continue;
				
				state = this.handleDeleteList(deleteList, state, tok);
				
				template = this.templateToken(tok,contents,template,templateList);
				if (lasttemplate) {
					if (!template) {
						templateList.add(contents);
						contents = new TemplateContents();
					}
				}
				lasttemplate = template;
				
			}
		} catch (TokenStreamException e) {
			BaseLog.logError(e);
		}
    	
		TemplateList list = new TemplateList(templateList, deleteList);
    	return list;
    }

	public void setStream(PositionStream stream) {
		this.stream = stream;
	}

	public PositionStream getStream() {
		return stream;
	}

	public void setDesc(ParseDescriptor desc) {
		this.desc = desc;
	}

	public ParseDescriptor getDesc() {
		return desc;
	}

	
	
}
