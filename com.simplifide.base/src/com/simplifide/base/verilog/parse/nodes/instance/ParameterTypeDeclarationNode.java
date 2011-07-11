package com.simplifide.base.verilog.parse.nodes.instance;

import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

// parameter_type_declaration : "type" list_of_type_assignments

public class ParameterTypeDeclarationNode extends TopASTNode{

	public TopObjectBase generateModuleSmallNew(ParseContext context) {
		return new NoSortList();
	}
	
	// list_of_type_assignments :type_assignment (options{greedy=true;} : COMMA type_assignment )*
	public static class List extends TopASTNode {
		
	}
	// type_assignment : type_identifier ASSIGN data_type
	public static class Type extends TopASTNode {
		// Node Handling
		TopASTNode nameNode = this.getFirstASTChild();
		TopASTNode child = nameNode.getNextASTSibling();
		TopASTNode typeNode = child.getNextASTSibling();
		
		// Object Handling
		
		
	}
	
}
