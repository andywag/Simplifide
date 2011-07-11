/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.vhdl.parse.node.segment.assign;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.segment.basic.condition.CaseChoices;
import com.simplifide.base.core.segment.basic.condition.CaseConditionStatementSingle;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

// selected_waveform_item : waveform WHEN choices

public class SelectedWaveFormItemNode extends TopASTNode{

	public TopObjectBase generateModuleSmallNew(ParseContext context) {
		TopASTNode inNode = this.getFirstASTChild();
		TopASTNode child = inNode.getNextASTSibling();
		TopASTNode choiceNode = child.getNextASTSibling();
		
		ReferenceItem in = (ReferenceItem) inNode.generateModule(context);
		ReferenceItem<CaseChoices> choices = (ReferenceItem<CaseChoices>) choiceNode.generateModule(context);
		
		CaseConditionStatementSingle seg = new CaseConditionStatementSingle("",choices,in);
		
		return seg.createReferenceItem();
	}
	
}
