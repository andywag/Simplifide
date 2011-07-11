/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.vhdl.parse.node;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

public class LabelColonWrapNode extends TopASTNode{

	
	public TopObjectBase generateModuleSmallNew(ParseContext context) {
		if (this.getFirstASTChild() == null) return null;
		return this.getFirstASTChild().generateModule(context);
	}
	
	public String getText() {
		if (this.getFirstASTChild() == null) return null;
		return this.getFirstASTChild().getFirstASTChild().getRealText();
	}
	
	
}
