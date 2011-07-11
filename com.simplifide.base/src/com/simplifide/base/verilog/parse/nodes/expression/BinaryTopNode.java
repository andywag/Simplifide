/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.verilog.parse.nodes.expression;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.segment.basic.operator.MultiOperator;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

public class BinaryTopNode extends TopASTNode{
	
	public TopObjectBase generateModuleSmallNew(ParseContext context) {
		TopASTNode child = this.getFirstASTChild();
		int emode = context.getSearchMode();
		context.setSearchMode(ParseContext.SEARCHREFERENCECONTEXT);
		ReferenceItem base = (ReferenceItem) child.generateModule(context);
		context.setSearchMode(emode);
		
		if (this.getNumberOfChildren() == 1) return base;
		
		MultiOperator multi = new MultiOperator("op",base);
		
		child = child.getNextASTSibling();
		while (child != null) {
			TopObjectBase base1 = child.generateModule(context);
			if (base1 instanceof ReferenceItem) {
				ReferenceItem ref = (ReferenceItem) base1;
				multi.addObject(ref);
			}
			child = child.getNextASTSibling();
		}
		return multi.createReferenceItem();
		
		
		
		
	}

}
