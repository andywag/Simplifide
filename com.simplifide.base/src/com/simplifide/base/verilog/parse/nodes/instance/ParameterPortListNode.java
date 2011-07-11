/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.verilog.parse.nodes.instance;

import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.basic.struct.NodePosition;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.port.PortDefault;
import com.simplifide.base.core.port.PortList;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.core.var.realvars.SystemVar;
import com.simplifide.base.sourcefile.antlr.FormatSupport;
import com.simplifide.base.sourcefile.antlr.node.FormatPosition;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

public class ParameterPortListNode extends TopASTNode{

	public void format(FormatPosition position) {
		TopASTNode child = this.getFirstASTChild();  // #
		if (child == null) return;
		child = child.getNextASTSibling(); // (
		int start = child.getPosition().getStartPos();
		int stop = this.getPosition().getEndPos();

		int indent = FormatSupport.getInstance().getModuleIndent();
		FormatPosition npos = position.addNewPosition(start, start+1);
		npos.setMinimum(indent);
		npos.setIndent(indent);
		npos = position.addNewPosition(start+1, stop);
		npos.setMinimum(indent+1);
		npos.setIndent(indent+1);
	}
	
	/** TODO Add Types */ 
	public TopObjectBase generateModuleSmallNew(ParseContext context) {
		PortList list = new PortList("Parameters");
		TopASTNode child = this.getFirstASTChild(); // #
		if (child != null) child = child.getNextASTSibling(); // (
		if (child != null) child = child.getNextASTSibling();
		while (child != null) {
			ReferenceItem<NoSortList<SystemVar>> varListRef = (ReferenceItem<NoSortList<SystemVar>>) child.generateModule(context);
			if (varListRef != null && varListRef.getObject() instanceof NoSortList) { // Types Not Supported
				NoSortList<SystemVar> varList = varListRef.getObject();
				for (ReferenceItem<? extends SystemVar> var : varList.getGenericSelfList()) {
					PortDefault def = new PortDefault(var);
					list.addObject(def.createReferenceItemWithLocation(var.getLocation()));
				}
			}
			
			
			child = child.getNextASTSibling();
			if (child != null) child = child.getNextASTSibling();
		}
		return list.createReferenceItem();
	}
	
	/* @todo : Needs to be added for port attaching refactoring */
	public ReferenceLocation getLastPortLocation(ParseContext context) {
    	return null;
    }
	
}
