package com.simplifide.base.verilog.parse.nodes.moduletop;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.instance.ModInstanceDefault;
import com.simplifide.base.core.port.PortList;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.verilog.core.module.VerilogModInstanceDefault;

public class ModuleTopHeaderNormalNode extends TopASTNode{
	public boolean canFold() {return true;}
	public TopObjectBase generateModuleSmallNew(ParseContext context) {
    	TopASTNode paramNode = this.getFirstASTChild();
    	TopASTNode portNode = paramNode.getNextASTSibling();
    	
    	ReferenceItem<PortList> paramsR = (ReferenceItem<PortList>) paramNode.generateModule(context);
    	ReferenceItem<PortList> portsR = (ReferenceItem<PortList>) portNode.generateModule(context);
    	ModInstanceDefault def = new VerilogModInstanceDefault("",paramsR,portsR);
    	return def.createReferenceItemWithLocation(context.createReferenceLocation(this));
    }
}
