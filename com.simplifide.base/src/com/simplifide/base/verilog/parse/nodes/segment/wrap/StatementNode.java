package com.simplifide.base.verilog.parse.nodes.segment.wrap;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;


// statement : (options{greedy=true;}: block_identifier COLON )? statement_item
public class StatementNode extends TopASTNode{

	public TopObjectBase generateModuleSmallNew(ParseContext context) {
		if (this.getNumberOfChildren() == 1) return this.getFirstASTChild().generateModule(context);
		TopASTNode nameNode = this.getFirstASTChild();
		TopASTNode child = nameNode.getNextASTSibling();
		TopASTNode stateNode = child.getNextASTSibling();
		
		if (stateNode == null) return null;
		ReferenceItem ref = (ReferenceItem) stateNode.generateModule(context);
		if (ref != null) ref.changeName(nameNode.getRealText());
		
		return null;
	}
	
}
