/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.verilog.parse.nodes.segment.op;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.segment.basic.other.ConcatOp;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

// concatenation : LCURLY expression ( COMMA expression )* RCURLY

public class ConcatenationNode extends TopASTNode{

	public TopObjectBase generateModuleSmallNew(ParseContext context) {
		 TopASTNode child = this.getFirstASTChild();
		 ConcatOp op = new ConcatOp();
		 child = child.getNextASTSibling();
		 while (child != null) {
			 ReferenceItem ref = (ReferenceItem) child.generateModule(context);
			 op.addObject(ref);
			 child = child.getNextASTSibling();
			 if (child != null) child = child.getNextASTSibling();
		 }
		 return op.createReferenceItem();
	}
	
}
