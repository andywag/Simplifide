/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.sourcefile.antlr.stream;

import com.simplifide.base.basic.struct.NodePosition;

/**
 * Class which contains information about the templates
 * @author andy
 *
 */
public class TemplateContents {

	public static final int NORMAL = 0;
	public static final int EMACS  = 1;
	public static final int PYTHON = 2;
	public static final int SCALA  = 3;
	
	private NodePosition position;
	private String text;
	private int type;
	
	public TemplateContents() {}

	public void appendText(String intext) {
		if (text == null) text = "";
		text += intext;
	}
	
	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setPosition(NodePosition position) {
		this.position = position;
	}

	public NodePosition getPosition() {
		return position;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

}
