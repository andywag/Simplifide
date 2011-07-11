package com.simplifide.base.verilog.parse.nodes.class1;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

// class_declaration : class_head class_body
public class ClassDeclarationNode extends TopASTNode{
	
	public TopObjectBase generateModuleSmallNew(ParseContext context) {
		// Node Handling
		TopASTNode headNode = this.getFirstASTChild();
		TopASTNode bodyNode = headNode.getNextASTSibling();
		
		return headNode.generateModule(context);
	}
	
}
