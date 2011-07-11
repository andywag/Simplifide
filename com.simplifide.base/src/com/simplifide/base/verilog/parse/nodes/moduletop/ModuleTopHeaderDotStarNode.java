package com.simplifide.base.verilog.parse.nodes.moduletop;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.verilog.core.module.VerilogModInstanceDefault;

public class ModuleTopHeaderDotStarNode extends TopASTNode{
	public boolean canFold() {return true;}
	
	public TopObjectBase generateModuleSmallNew(ParseContext context) {
    	
    	VerilogModInstanceDefault.DotStar dot = new VerilogModInstanceDefault.DotStar();
    	return dot.createReferenceItemWithLocation(context.createReferenceLocation(this));
    }
}
