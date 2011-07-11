/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.verilog.parse.nodes.function;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;


// function_data_type : data_type | "void"

/** Handle the Void Case for Functions 
 *  @todo : Handle the void functions
 * */
public class FunctionDataTypeNode extends TopASTNode{

	
		public TopObjectBase generateModuleSmallNew(ParseContext context) {
			
			TopASTNode child = this.getFirstASTChild();
			if (child.getRealText().equalsIgnoreCase("void")) {
				return null;
			}
			return child.generateModule(context);
			
		}
		
		
	
	
	
	
}
