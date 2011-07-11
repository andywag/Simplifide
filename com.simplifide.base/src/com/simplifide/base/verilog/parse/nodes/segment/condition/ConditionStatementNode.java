/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.verilog.parse.nodes.segment.condition;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.segment.basic.condition.IfConditionTop;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

// conditional_statement : condition_head (condition_else)*

public class ConditionStatementNode extends TopASTNode{

	public TopObjectBase generateModuleSmallNew(ParseContext context) {
		IfConditionTop iftop = new IfConditionTop();
		ReferenceItem<IfConditionTop> iftopRef = iftop.createReferenceItem();
		TopASTNode child = this.getFirstASTChild();
		while (child != null) {
			ReferenceItem item = (ReferenceItem) child.generateModule(context);
			iftop.addObject(item);
			child = child.getNextASTSibling();
		}
		
		return iftopRef;
	}
	
}
