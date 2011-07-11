package com.simplifide.base.verilog.parse.nodes.segment.wrap;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

public abstract class NameBlockPrefixNode extends TopASTNode{

	
	public abstract String getBlockName();
	public static class Pre extends NameBlockPrefixNode {
	
		public TopObjectBase generateModuleSmallNew(ParseContext context) {
			return null;
		}
		public String getBlockName() {
			if (this.getNumberOfChildren() == 0) return null;
			return this.getFirstASTChild().getRealText();
		}
		
	}
	
	public static class Post extends NameBlockPrefixNode {
		public TopObjectBase generateModuleSmallNew(ParseContext context) {
			return null;
		}
		
		public String getBlockName() {
			if (this.getNumberOfChildren() == 0) return null;
			TopASTNode child = this.getFirstASTChild();
			return child.getNextASTSibling().getRealText();
		}
	}
	
}
