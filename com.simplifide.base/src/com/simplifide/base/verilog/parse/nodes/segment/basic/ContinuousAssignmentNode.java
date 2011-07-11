/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.verilog.parse.nodes.segment.basic;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.project.BuildInterface;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

// "assign" (drive_strengthQ) (delayQ) list_of_assignments SEMI 

public class ContinuousAssignmentNode extends TopASTNode{

	private static final long serialVersionUID = 1L;

	public ContinuousAssignmentNode() {} 
	
	public TopObjectBase generateModuleSmallNew(ParseContext context) {
		
    	if (context.getPass() == BuildInterface.BUILD_FILE_CLOSED) return null;

		
		TopASTNode child = this.getFirstASTChild(); // assign
		child = child.getNextASTSibling(); // driveStrengthQ
		child = child.getNextASTSibling(); // delayQ;
		child = child.getNextASTSibling(); // ListOfAssignements
		
		return child.generateModule(context);
	}
}
