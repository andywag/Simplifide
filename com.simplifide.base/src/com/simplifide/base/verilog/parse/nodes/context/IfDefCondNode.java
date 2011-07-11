/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.verilog.parse.nodes.context;

import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

public abstract class IfDefCondNode extends TopASTNode {

	
	public abstract TopASTNode getFirstRealNode();
	
	public boolean canFold() {return true;}
	
	public TopObjectBase generateModuleSmallNew(ParseContext context) {
		NoSortList list = new NoSortList();
		TopASTNode child = this.getFirstRealNode();
		while (child != null) {
			ReferenceItem ref = (ReferenceItem) child.generateModule(context);
			if (ref != null) list.addObject(ref);
			child = child.getNextASTSibling();
		}
		return list.createReferenceItem();
	}
	
	public static class First extends IfDefCondNode {
		
		
		@Override
		public TopASTNode getFirstRealNode() {
			TopASTNode child = this.getFirstASTChild();
			if (child != null)
				child = child.getNextASTSibling();
			if (child != null)
				child = child.getNextASTSibling();
			return child;
		}
		
	}
	
	public static class Else extends IfDefCondNode {

		@Override
		public TopASTNode getFirstRealNode() {
			TopASTNode child = this.getFirstASTChild();
			if (child != null)
				child = child.getNextASTSibling();
			return child;
		}
		
	}
	
}
