/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.vhdl.parse.node.segment.assign;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.basic.util.StringOps;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.segment.basic.condition.CaseSingleOutputTop;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

// selected_signal_assignment : WITH expression SELECT target LE opts selected_waveforms SEMI

public class SelectedSignalAssignmentNode extends TopASTNode{

	public TopObjectBase generateModuleSmallNew(ParseContext context) {
		TopASTNode child = this.getFirstASTChild(); // With
		TopASTNode exprNode  = child.getNextASTSibling(); // Expression
		child = exprNode.getNextASTSibling(); // select
		TopASTNode outNode  = child.getNextASTSibling(); // target
		child = outNode.getNextASTSibling(); // eq
		child = child.getNextASTSibling(); // opts
		TopASTNode stateNode = child.getNextASTSibling(); // selected waveforms
		
		ReferenceItem exprRef = (ReferenceItem) exprNode.generateModule(context);
		ReferenceItem outRef = (ReferenceItem) outNode.generateModule(context);
		ReferenceItem<NoSortList<ModuleObject>> stateList = (ReferenceItem<NoSortList<ModuleObject>>)  stateNode.generateModule(context);
		
		String withName = "WithSelect" + StringOps.addParens(outRef.getDisplayName());
		CaseSingleOutputTop single = new CaseSingleOutputTop(withName,exprRef,outRef);
		
		for (ReferenceItem item : stateList.getObject().getGenericSelfList()) {
			single.addObject(item);
		}
		
		return single.createReferenceItemWithLocation(context.createReferenceLocation(this.getFirstASTChild()));
	}
	
}
