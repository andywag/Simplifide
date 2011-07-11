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

// exp7 : exp8 ( questop )?

public class QuestionTopNode extends TopASTNode{

	public TopObjectBase generateModuleSmallNew(ParseContext context) {
		TopASTNode first = this.getFirstASTChild();
		TopASTNode questNode = first.getNextASTSibling();
		
		ReferenceItem exp = (ReferenceItem) first.generateModule(context);
		if (questNode == null) return exp;
		
		ReferenceItem<QuestionSegment> quRef = (ReferenceItem<QuestionSegment>) questNode.generateModule(context);
		quRef.getObject().setCondRef(exp);
		quRef.setLocation(context.createReferenceLocation(first));
		return quRef;
		
		
		
		
	}
}
