/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.verilog.parse.nodes.instance;

import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

// ordered_parameter_list : ordered_parameter_assignment ( COMMA ordered_parameter_assignment)*
// named_parameter_list   :  named_parameter_assignment  ( COMMA named_parameter_assignment )*

public class ParameterListNode extends TopASTNode{

	public TopObjectBase generateModuleSmallNew(ParseContext context) {
		NoSortList list = new NoSortList();
		TopASTNode child = this.getFirstASTChild();
		while (child != null) {
			ReferenceItem ref = (ReferenceItem) child.generateModule(context);
			if (ref != null) list.addObject(ref);
			child = child.getNextASTSibling();
			if (child != null) child = child.getNextASTSibling();
		}
		return list.createReferenceItem();
	}
	
}
