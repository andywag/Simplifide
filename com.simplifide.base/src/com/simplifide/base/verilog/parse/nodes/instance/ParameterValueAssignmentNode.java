/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.verilog.parse.nodes.instance;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.sourcefile.antlr.FormatSupport;
import com.simplifide.base.sourcefile.antlr.node.FormatPosition;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;


// parameter_value_assignment : (POUND LPAREN list_of_parameter_assignments RPAREN)?

public class ParameterValueAssignmentNode extends TopASTNode{

	
	private static final long serialVersionUID = 1L;

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
	
	public TopObjectBase generateModuleSmallNew(ParseContext context) {
		
		TopASTNode child = this.getFirstASTChild(); // null or pound
		if (child == null) return null;
		child = child.getNextASTSibling(); // lparen
		TopASTNode paramNode = child.getNextASTSibling(); // list_or_param_assignments
		
		return paramNode.generateModule(context);
		
	}
	
}
