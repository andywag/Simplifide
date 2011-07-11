package com.simplifide.base.verilog.parse.nodes.class1;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.TopASTNodeGeneric;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

// class_property : constQ class_property_list data_declaration

public class ClassPropertyNode extends TopASTNodeGeneric<ModuleObject>{

	public ModuleObject createObjectSmall(ParseContext context) {
		// Node Handling
		TopASTNode constNode = this.getFirstASTChild();
		TopASTNode propListNode = constNode.getNextASTSibling();
		TopASTNode dataNode = propListNode.getNextASTSibling();

		// Data Generation
		return (ModuleObject) dataNode.generateModule(context);
		
	}
	
	
}
