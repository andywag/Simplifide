/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.verilog.parse.nodes.vars.param;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.module.HardwareModule;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;


// parameter_declaration : ("parameter" | "specparam" | "localparam") data_type list_of_param_assignments SEMI

public class ParameterDeclaratationSemiNode extends TopASTNode{

	
	
	
	public TopObjectBase generateModuleSmallNew(ParseContext context) {
		TopASTNode child = this.getFirstASTChild();
		if (child.getFirstASTChild().getRealText().equalsIgnoreCase("parameter")) {
			if (context.getRefHandler().getModuleReference() != null) {
				HardwareModule mod = context.getRefHandler().getModuleReference().getObject();
				if (mod.getIoDeclarationList() != null) {
					mod.getIoDeclarationList().add(context.createReferenceLocation(this));
				}
			}
		}
		return child.generateModuleSmallNew(context);
	
		
	}
	
}
