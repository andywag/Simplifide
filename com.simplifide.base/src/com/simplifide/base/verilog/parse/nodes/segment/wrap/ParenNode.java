/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.verilog.parse.nodes.segment.wrap;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

public class ParenNode extends TopASTNode{
	
	public ParenNode() {}
	
	public TopObjectBase generateModuleSmallNew(ParseContext context) {
		TopASTNode child = this.getFirstASTChild();
		child = child.getNextASTSibling();
		return child.generateModule(context);
	}

}
