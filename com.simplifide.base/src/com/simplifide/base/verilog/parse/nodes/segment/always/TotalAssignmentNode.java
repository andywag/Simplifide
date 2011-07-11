/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.verilog.parse.nodes.segment.always;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.segment.basic.assign.SignalAssignment;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

//total_assignment : left_assign (ASSIGN | LE) ( delay_or_event_control )?  right_assign SEMI

public class TotalAssignmentNode extends TopASTNode{

	
	public TopObjectBase generateModuleSmallNew(ParseContext context) {
		TopASTNode lvalue = this.getFirstASTChild();
		TopASTNode child = lvalue.getNextASTSibling();
		child = child.getNextASTSibling(); // delay
		TopASTNode rvalue = child.getNextASTSibling();
		
		ReferenceItem outRef = (ReferenceItem) lvalue.generateModule(context);
		ReferenceItem inRef  = (ReferenceItem) rvalue.generateModule(context);
		
		SignalAssignment ass = new SignalAssignment("",inRef,outRef);
		return ass.createReferenceItem();
	}
	
}
