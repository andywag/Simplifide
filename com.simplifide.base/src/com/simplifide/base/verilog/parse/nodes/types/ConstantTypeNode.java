/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.verilog.parse.nodes.types;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.verilog.core.types.VerilogBaseTypes;

public class ConstantTypeNode extends TopASTNode{

	
	public static class IntegerNode extends ConstantTypeNode{
		
		public TopObjectBase generateModuleSmallNew(ParseContext context) {
			return VerilogBaseTypes.INTEGER.createReferenceItem();
		}
		
	}
	
	public static class RealNode extends ConstantTypeNode{
		
		public TopObjectBase generateModuleSmallNew(ParseContext context) {
			return VerilogBaseTypes.REAL.createReferenceItem();
		}
		
	}
	
	
}
