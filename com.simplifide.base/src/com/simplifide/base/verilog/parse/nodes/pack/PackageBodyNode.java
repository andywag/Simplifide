package com.simplifide.base.verilog.parse.nodes.pack;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.module.TopModule;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.verilog.parse.nodes.module.TopBodyNode;

public class PackageBodyNode extends TopBodyNode{
	/** Creates a new instance of ModuleBodyNode */
    public PackageBodyNode() {}

   
    
    private void setStartLocation(ParseContext context) {
   	 	ReferenceLocation loc = context.createReferenceLocation(this);
        loc.setLength(0);
        context.getRefHandler().getModuleReference().getObject().setDeclarationStartLocation(loc);
    }
    @Override
    public TopObjectBase generateModuleSmallNew(ParseContext context) {
        TopModule module = (TopModule) context.getActiveReference().getObject();
        //this.setStartLocation(context);
        //context.setActiveReference(module.createReferenceItem());
        TopASTNode node = this.getFirstASTChild();
        while (node != null) {
        	ModuleObject item = (ModuleObject) node.generateModule(context);
            this.addItem(module, item, node);
        	node = node.getNextASTSibling();
        	
        }
        
        
        ReferenceLocation loc = null;
        if (this.getNumberOfChildren() > 0) loc = context.createReferenceLocation(this.getFirstASTChild());
        return module.createReferenceItemWithLocation(loc);
    }

}
