/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.verilog.parse.nodes.types;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.var.range.VarRange;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.verilog.core.types.VerilogArrayType;
import com.simplifide.base.verilog.core.types.VerilogBaseTypes;

// parameter_dimension_node : signing packed_dimension

public class ParameterDimensionNode extends TopASTNode{

	
	private static final long serialVersionUID = -2139278721308699428L;

	public TopObjectBase generateModuleSmallNew(ParseContext context) {
		TopASTNode signNode = this.getFirstASTChild();
		TopASTNode dim  = signNode.getNextASTSibling();
		
		ReferenceItem<VarRange> range = (ReferenceItem<VarRange>) dim.generateModule(context);
		
		VerilogArrayType type = new VerilogArrayType(range,VerilogBaseTypes.BIT.createReferenceItem());
		return type.createReferenceItem();
	}
	
}
