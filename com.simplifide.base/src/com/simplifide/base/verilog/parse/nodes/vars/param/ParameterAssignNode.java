/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.verilog.parse.nodes.vars.param;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.var.realvars.OperatingTypeVar;
import com.simplifide.base.core.var.realvars.SystemVar;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.verilog.core.types.VerilogBaseTypes;
import com.simplifide.base.verilog.core.var.VerilogVar;


// param_assignment : IDENTIFIER (ASSIGN expression)?

public class ParameterAssignNode extends TopASTNode{

	public TopObjectBase generateModuleSmallNew(ParseContext context) {
		TopASTNode identNode = this.getFirstASTChild();
		TopASTNode child = identNode.getNextASTSibling();
		
		TopASTNode exprNode = null;
		ReferenceItem expression = null;
		if (child != null) {
			exprNode = child.getNextASTSibling();
			expression = (ReferenceItem) exprNode.generateModule(context);

		}
		
		String paramName = identNode.getRealText();
		
		SystemVar tvar = new VerilogVar(paramName,VerilogBaseTypes.BIT.createReferenceItem(),
				new OperatingTypeVar.ConstantVar());
		tvar.setDefaultValue(expression);
		if (expression != null) tvar.setAssigned(true);
		
		return tvar.createReferenceItemWithLocation(context.createReferenceLocation(identNode));
	}
	
}
