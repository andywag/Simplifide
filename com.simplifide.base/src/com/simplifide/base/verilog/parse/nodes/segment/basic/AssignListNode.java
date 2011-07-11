/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.verilog.parse.nodes.segment.basic;

import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.segment.basic.assign.SignalAssignment;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.TopASTNodeGeneric;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

public class AssignListNode extends TopASTNodeGeneric<ReferenceItem<NoSortList<SignalAssignment>>>{
	
	public AssignListNode() {}
		
	public ReferenceItem<NoSortList<SignalAssignment>> createObjectSmall(ParseContext context) {
		NoSortList<SignalAssignment> list = new NoSortList<SignalAssignment>();
		TopASTNode child = this.getFirstASTChild(); // assign
		while (child != null) {
			ReferenceItem ritem = (ReferenceItem) child.generateModule(context);
			if (ritem != null) list.addObject(ritem);
			child = child.getNextASTSibling();
		}
		
		return list.createReferenceItem();
	}
}

