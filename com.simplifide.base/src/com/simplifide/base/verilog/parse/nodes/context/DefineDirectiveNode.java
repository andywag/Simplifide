/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.verilog.parse.nodes.context;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.var.realvars.OperatingTypeVar;
import com.simplifide.base.core.var.realvars.SystemVar;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.verilog.core.types.VerilogBaseTypes;
import com.simplifide.base.verilog.core.var.VerilogVar;

public class DefineDirectiveNode extends TopASTNode{

	public TopObjectBase generateModuleSmallNew(ParseContext context) {
		TopASTNode child = this.getFirstASTChild();
		TopASTNode varNode = child.getNextASTSibling();
		TopASTNode exprNode = varNode.getNextASTSibling();
		
		String varName = varNode.getRealText();
		ReferenceItem value = null;
		if (exprNode != null)
			value = (ReferenceItem) exprNode.generateModule(context);
		
		SystemVar tvar = new VerilogVar(varName,VerilogBaseTypes.BIT.createReferenceItem(),
				new OperatingTypeVar.ConstantVar());
		ReferenceItem varRef = tvar.createReferenceItemWithLocation(context.createReferenceLocation(varNode));
		context.getActiveReference().addReferenceItem(varRef);
		tvar.setDefaultValue(value);
		tvar.setAssigned(true);
		
		return null;
		
	}
	
}
