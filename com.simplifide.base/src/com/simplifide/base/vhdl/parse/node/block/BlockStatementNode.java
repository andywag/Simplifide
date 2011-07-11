package com.simplifide.base.vhdl.parse.node.block;

import com.simplifide.base.sourcefile.antlr.node.TopASTNode;

public class BlockStatementNode extends TopASTNode{

	 public boolean canFold() {return true;}
	 public String getFoldName() {
		 return "block statement";
	 }
	
}
