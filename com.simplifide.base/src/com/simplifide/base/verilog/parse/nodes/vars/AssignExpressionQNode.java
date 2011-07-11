package com.simplifide.base.verilog.parse.nodes.vars;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

public class AssignExpressionQNode extends TopASTNode{

	 public TopObjectBase generateModuleSmallNew(ParseContext context) {
	    	// Nodes
	    	TopASTNode assignNode = this.getFirstASTChild();
	    	if (assignNode == null) return null;
	    	TopASTNode expNode  = assignNode.getNextASTSibling();
	    
	    	return expNode.generateModule(context);	        
	    }
	
}
