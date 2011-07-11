/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.verilog.parse.nodes.name;

import com.simplifide.base.core.finder.ModuleObjectBaseItem;
import com.simplifide.base.core.finder.ModuleObjectFindItem;
import com.simplifide.base.sourcefile.antlr.node.namedec.IdentASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

public class DefineNode extends IdentASTNode{
	
	 public ModuleObjectFindItem createFindItem(ParseContext context, int pos) {
		 String text = this.getRealText();
		 text = text.substring(1,text.length());
		 return new ModuleObjectBaseItem(text);
	 }

}
