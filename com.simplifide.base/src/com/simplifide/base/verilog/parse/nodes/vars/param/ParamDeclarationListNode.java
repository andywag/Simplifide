/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.verilog.parse.nodes.vars.param;

import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

public class ParamDeclarationListNode extends TopASTNode{

	
	private static final long serialVersionUID = 1L;

	public TopObjectBase generateModuleSmallNew(ParseContext context) {
		NoSortList list = new NoSortList("Parameters");
		
		TopASTNode child = this.getFirstASTChild();
		while (child != null) {
			ReferenceItem item = (ReferenceItem) child.generateModule(context);
			list.addObject(item);
			child = child.getNextASTSibling();
			if (child != null) child = child.getNextASTSibling();
		}
		return list.createReferenceItem();
	}
	
}
