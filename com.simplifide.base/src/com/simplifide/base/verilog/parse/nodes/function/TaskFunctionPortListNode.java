/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.verilog.parse.nodes.function;

import com.simplifide.base.core.newfunction.FunctionPortList;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.TopASTNodeGeneric;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;


/** Node which encompasses both functions and tasks */
//task_function_port_list : (LPAREN (tf_port_list)? RPAREN)?

public class TaskFunctionPortListNode extends TopASTNodeGeneric<ReferenceItem<FunctionPortList>>{

	
		public ReferenceItem<FunctionPortList> createObjectSmall(ParseContext context) {
			if (this.getNumberOfChildren() < 3) { // Empty Function List
				return new FunctionPortList().createReferenceItem();
			}
			TopASTNode child = this.getFirstASTChild();
			TfPortListNode pnode = (TfPortListNode) child.getNextASTSibling();
			return pnode.createObject(context);
			
		}
	
	
	
	
}
