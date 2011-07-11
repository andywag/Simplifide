package com.simplifide.base.verilog.parse.nodes.moduletop;

import com.simplifide.base.core.finder.ModuleObjectBaseItem;
import com.simplifide.base.core.instance.Entity;
import com.simplifide.base.core.module.HardwareModule;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.module.SuperModule;
import com.simplifide.base.core.module.TopModule;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.TopASTNodeGeneric;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

public abstract class ModuleTopDeclarationNormalNode extends TopASTNodeGeneric<ReferenceItem<Entity>>{
	public abstract InstanceModule createInstanceModule(String name, ParseContext context);
	public boolean canFold() {return true;}
	
    public void resolveContext(ParseContext context) {
		ModuleTopNoAnsiHeaderNode headerNode = (ModuleTopNoAnsiHeaderNode)this.getFirstASTChild();
		String entityName = headerNode.getEntityName();
        ModuleObjectBaseItem item = new ModuleObjectBaseItem(entityName);
        ReferenceItem<InstanceModule> instRef = context.getRefHandler().findProjectObject(item,ReferenceUtilities.REF_INSTANCE_MODULE_TOP);
        ReferenceItem<Entity> entRef = item.findRealReferenceItem(instRef,ReferenceUtilities.REF_ENTITY);  
        ReferenceItem<HardwareModule> bodyRef = item.findRealReferenceItem(instRef, ReferenceUtilities.REF_HARDWARE_MODULE);
        //ModuleObjectCompositeHolder holder = ModuleObjectCompositeHolder.dualHolder("Context", entRef, bodyRef);
        context.setActiveReference(bodyRef); // Changed for new menu (Not sure if it is a good idea)
        context.getRefHandler().setLocalReference(entRef);
    }
	
	public ReferenceItem<Entity> createObjectSmall(ParseContext context) {
		ModuleTopNoAnsiHeaderNode headerNode = (ModuleTopNoAnsiHeaderNode)this.getFirstASTChild();
    	TopASTNode timeDecNode = headerNode.getNextASTSibling();
    	TopASTNode bodyNode    = timeDecNode.getNextASTSibling();
    	
    	ReferenceItem currentActiveR = context.getActiveReference();
    	// Entity Handling
    	ReferenceItem<Entity> entityR = headerNode.createObject(context);
    	context.setActiveReference(entityR);
    	
    	// Instance Creation or Finding from Project
        ReferenceItem<InstanceModule> imodR = context.getRefHandler().getProjectReference().findReference(entityR.getname(), ReferenceUtilities.REF_INSTANCE_MODULE_TOP);
    	InstanceModule imod;
        if (imodR == null) {
        	imod = this.createInstanceModule(entityR.getname(),context);
            imodR = imod.createReferenceItemWithLocation(entityR.getLocation());
            // Add Instance Module to Project
            context.getRefHandler().getProjectReference().addModuleObject(imod, null);
    	}
        else {
        	imod = imodR.getObject();
        }
        entityR.getObject().setInstanceModRef(imodR);
        imodR.getObject().setEntityReference(entityR);
        
        // Module Handling
    	ReferenceItem<TopModule> modR = (ReferenceItem<TopModule>) bodyNode.generateModule(context);
    	modR.changeName(entityR.getname());
    	
    	

        imodR.setLocation(entityR.getLocation());
        // Connect the Entity and the Module to the Instance Module
        imod.setEntityReference(entityR);
        imod.setArchitectureReference(modR);
        entityR.getObject().setInstanceModRef(imodR);
        
        // Add the Entity and the Architecture to the SuperModule Only if this isn't a subinterface  
        if (currentActiveR.getObject() instanceof TopModule) {
        	if (currentActiveR.getname().equalsIgnoreCase("global")) {
        		 SuperModule smod = (SuperModule) context.getRefHandler().getSuperModuleReference().getObject();
                 smod.addObject(entityR);
                 smod.addObject(modR);
        	}
        }
        else {
        	 SuperModule smod = (SuperModule) context.getRefHandler().getSuperModuleReference().getObject();     
             smod.addObject(entityR);
             smod.addObject(modR);
        }
        
        // Seems a Bit Arbitrary to Return the entity but ...
    	return entityR;
    }
}
