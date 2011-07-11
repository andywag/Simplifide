/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.verilog.parse.nodes.function;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.var.range.VarRange;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.verilog.core.types.VerilogArrayType;
import com.simplifide.base.verilog.core.types.VerilogBaseTypes;


// range_or_type : range | "integer" | "real"

/** @deprecated : Used for Verilog Functions*/
public class RangeOrTypeNode extends TopASTNode{
	
	public RangeOrTypeNode() {}
	
	public TopObjectBase generateModuleSmallNew(ParseContext context) {
		TopASTNode child = this.getFirstASTChild();
		ReferenceItem item = (ReferenceItem) child.generateModule(context);
		
		if (item.getObject() instanceof VarRange) {
			VerilogArrayType type = new VerilogArrayType(item,VerilogBaseTypes.BIT.createReferenceItem());
			return type.createReferenceItem();
		}
		return item;
		
	}

}
