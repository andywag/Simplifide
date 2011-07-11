/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.verilog.parse.nodes.segment.cas;

import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;


//case_list :  (case_item)+

public class CaseListNode extends TopASTNode{

	
	public TopObjectBase generateModuleSmallNew(ParseContext context) {
		NoSortList list = new NoSortList("");
		TopASTNode child = this.getFirstASTChild();
		while (child != null) {
			ReferenceItem ref = (ReferenceItem) child.generateModule(context);
			list.addObject(ref);
			child = child.getNextASTSibling();
		}
		return list.createReferenceItem();
	}
	
	
}
