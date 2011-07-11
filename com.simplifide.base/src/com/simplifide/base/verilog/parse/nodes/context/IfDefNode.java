/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.verilog.parse.nodes.context;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

public class IfDefNode extends TopASTNode{

	public boolean canFold() {return true;}
	
	public TopObjectBase generateModuleSmallNew(ParseContext context) {
		
		NoSortList list = new NoSortList();
		TopASTNode child = this.getFirstASTChild();
		while (child != null) {
			ReferenceItem ref = (ReferenceItem) child.generateModule(context);
			if (ref != null) {
				NoSortList<ModuleObject> refList = (NoSortList<ModuleObject>) ref.getObject();
				list.addAll(refList);
			}
			child = child.getNextASTSibling();
		}
		return list.createReferenceItem();
	}
	
	

	
}
