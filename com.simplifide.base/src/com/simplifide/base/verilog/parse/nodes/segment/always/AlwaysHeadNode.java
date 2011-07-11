/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.verilog.parse.nodes.segment.always;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.project.BuildInterface;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;


// always_head : delay_or_event_control
public class AlwaysHeadNode extends TopASTNode{

	
	private static final long serialVersionUID = 1L;

	public TopObjectBase generateModuleSmallNew(ParseContext context) {
		if (context.getPass() == BuildInterface.BUILD_FIND_USAGES) {
			return super.generateModuleSmallNew(context);
		}
		return null;
	}
	
}
