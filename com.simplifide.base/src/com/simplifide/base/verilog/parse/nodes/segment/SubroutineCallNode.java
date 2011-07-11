package com.simplifide.base.verilog.parse.nodes.segment;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

public class SubroutineCallNode extends TopASTNode{

	public TopObjectBase generateModuleSmallNew(ParseContext context) {
		TopASTNode call = this.getFirstASTChild();
		TopASTNode state_semi = call.getNextASTSibling();
		
		context.setSearchMode(ParseContext.SEARCHREFERENCECONTEXTANDGLOBAL);
		ReferenceItem ref = (ReferenceItem) call.generateModule(context);
		/*if (ref != null) {
			if (context.getPass() == ParseContext.BUILD_FIND_USAGES) {
				
			}
		}*/
		
		state_semi.generateModule(context);
		return null;
	}
	
}
