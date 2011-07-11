package com.simplifide.base.verilog.parse.nodes.moduletop;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.instance.ModInstanceDefault;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

public class ModuleTopDeclarationExternNode extends TopASTNode {
	
	public boolean canFold() {return true;}
	public TopObjectBase generateModuleSmallNew(ParseContext context) {
    	TopASTNode child = this.getFirstASTChild();
    	TopASTNode headerNode = child.getNextASTSibling();
    	
    	ReferenceItem<ModInstanceDefault> modR = (ReferenceItem<ModInstanceDefault>)headerNode.generateModule(context);
    	return null;
    }
}
