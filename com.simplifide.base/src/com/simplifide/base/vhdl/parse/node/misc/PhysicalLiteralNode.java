package com.simplifide.base.vhdl.parse.node.misc;

import com.simplifide.base.basic.struct.ModuleObjectNew;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

public class PhysicalLiteralNode extends TopASTNode{

	public TopObjectBase generateModuleSmallNew(ParseContext context) {
		TopASTNode first = this.getFirstASTChild();
		TopASTNode second = first.getNextASTSibling();

		if (second == null) first.generateModule(context);
		else second.generateModule(context);
		
		ModuleObjectNew cc = new ModuleObjectNew(this.getRealText());
		return cc.createReferenceItem();
	}
	
}
