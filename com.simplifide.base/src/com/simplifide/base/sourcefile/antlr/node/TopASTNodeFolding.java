/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.sourcefile.antlr.node;

import com.simplifide.base.basic.struct.NodePosition;

public class TopASTNodeFolding extends TopASTNode{

	private static final long serialVersionUID = 1L;

	private NodePosition position;
	private boolean folded = false;
	
	public TopASTNodeFolding(int position, String text) {
		this(position,text,false);
	}
	
	public TopASTNodeFolding(int position, String text, boolean folded) {
		super();
		this.position = new NodePosition(position,position + text.length(),0,0);
		this.setText(text);
		this.setFolded(folded);
	}
	
	public void updatePosition(int end, String text) {
		this.setEndPosition(end);
		this.appendText(text);
	}
	
	public void appendText(String text) {
		this.setText(this.getText() + text);
	}
	
	public void setEndPosition(int end) {
		this.position.setEndPos(end);
	}
	
	public void setPosition(NodePosition position) {
		this.position = position;
	}

	public NodePosition getPosition() {
		return position;
	}

	public void setFolded(boolean folded) {
		this.folded = folded;
	}

	public boolean isFolded() {
		return folded;
	}
	
	
	
	
}
