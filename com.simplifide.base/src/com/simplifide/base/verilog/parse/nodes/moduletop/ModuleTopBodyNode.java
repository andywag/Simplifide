package com.simplifide.base.verilog.parse.nodes.moduletop;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.instance.Entity;
import com.simplifide.base.core.module.HardwareModule;
import com.simplifide.base.core.module.TopModule;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.verilog.parse.nodes.module.TopBodyNode;

// (interface_item)*
public abstract class ModuleTopBodyNode extends TopBodyNode{

	public boolean canFold() {return true;}
	public abstract TopModule createModule();
	
	
	
	 public TopObjectBase generateModuleSmallNew(ParseContext context) {
		 	TopModule imodule = this.createModule();
		 	
		 	ReferenceItem<HardwareModule> imoduleR = imodule.createReferenceItemWithLocation(context.createReferenceLocation(this));
		 	ReferenceItem<Entity> entityR = context.getActiveReference();
		 	imoduleR.getObject().setInstModRef(entityR.getObject().getInstanceModRef());
		 	
		 	//ModuleObjectCompositeHolder hold = ModuleObjectCompositeHolder.dualHolder("",entityR,imoduleR);
		 	context.getRefHandler().setLocalReference(entityR);
		 	context.getRefHandler().setActiveReference(imoduleR);
		 	context.getRefHandler().setModuleReference(imoduleR);
		 	
	    	TopASTNode child = this.getFirstASTChild();
	    	while (child != null) {
	    		ModuleObject obj = (ModuleObject) child.generateModule(context);
	    		this.addItem(imodule, obj, child);
	    		child = child.getNextASTSibling();
	    	}
	    	return imodule.createReferenceItem();
	    } 
	
}
