/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.verilog.parse.nodes.context;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

public class ContextClauseNode extends TopASTNode{

	
	public boolean canFold() {return true;}
	
	
	 public void resolveContext(ParseContext context) {
		 
	 }
	public TopObjectBase generateModuleSmallNew(ParseContext context) {
		
		
		TopASTNode child = this.getFirstASTChild();
		context.setActiveReference(context.getRefHandler().getFileReference());
		context.getRefHandler().setSecondaryReference(context.getRefHandler().getSearchReference());
		
		while (child != null) {
			ReferenceItem ref = (ReferenceItem) child.generateModule(context);
			child = child.getNextASTSibling();
		}
		return null;
	}
}
