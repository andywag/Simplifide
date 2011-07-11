/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.verilog.parse.nodes.function;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.var.realvars.SystemVar;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;


/** Node which encompasses both functions and tasks */
// tf_port_direction : (port_direction | "const" "ref")?

public class TfPortDirectionNode extends TopASTNode{

	
		public TopObjectBase generateModuleSmallNew(ParseContext context) {
			return null;
		}
		
		public int getIOType() {
			TopASTNode child = this.getFirstASTChild();
			if (child == null) return SystemVar.INPUT;
			String type = child.getRealText();
			if (type.equalsIgnoreCase("input")) return SystemVar.INPUT;
			if (type.equalsIgnoreCase("output")) return SystemVar.OUTPUT;
			if (type.equalsIgnoreCase("const")) return SystemVar.CONSTANT;
			return SystemVar.INPUT;
		}
	
	
	
	
}
