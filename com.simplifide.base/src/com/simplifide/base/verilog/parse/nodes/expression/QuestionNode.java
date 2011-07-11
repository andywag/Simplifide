/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.verilog.parse.nodes.expression;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.segment.basic.assign.QuestionSegment;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

// questop : QUESTION exp7 COLON exp7

public class QuestionNode extends TopASTNode{

	public TopObjectBase generateModuleSmallNew(ParseContext context) {
		TopASTNode child = this.getFirstASTChild();
		TopASTNode trNode = child.getNextASTSibling();
		child = child.getNextASTSibling();
		TopASTNode faNode = child.getNextASTSibling();
		
		ReferenceItem trValue = (ReferenceItem) trNode.generateModule(context);
		ReferenceItem faValue = (ReferenceItem) faNode.generateModule(context);
		
		QuestionSegment seg = new QuestionSegment("",null,trValue,faValue);
		return seg.createReferenceItem();
		
	}
	
}
