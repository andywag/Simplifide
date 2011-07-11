/*
 * ModuleNode.java
 *
 * Created on April 21, 2007, 11:49 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.verilog.parse.nodes.module;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.finder.ModuleObjectBaseItem;
import com.simplifide.base.core.instance.Entity;
import com.simplifide.base.core.module.HardwareModule;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.module.SuperModule;
import com.simplifide.base.core.project.BuildInterface;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.top.ModuleTopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.verilog.core.module.VerilogInstanceModule;



/**
 *
 * @author Andy
 */

public class ModuleNode extends ModuleTopASTNode{
    
    /** Creates a new instance of ModuleNode */
    public ModuleNode() {}

    public boolean canFold() {
        return true;
    }
    
   
    
    @Override
    public void resolveContext(ParseContext context) {
        ModuleDecNode child = (ModuleDecNode) this.getFirstASTChild();
        String text = child.getEntName();
        ModuleObjectBaseItem item = new ModuleObjectBaseItem(text);
        ReferenceItem<InstanceModule> instRef = context.getRefHandler().findProjectObject(item,ReferenceUtilities.REF_INSTANCE_MODULE);
        ReferenceItem<Entity> entRef = item.findRealReferenceItem(instRef,ReferenceUtilities.REF_ENTITY);  
        ReferenceItem<HardwareModule> bodyRef = item.findRealReferenceItem(instRef, ReferenceUtilities.REF_HARDWARE_MODULE);
        context.setActiveReference(bodyRef);
        context.getRefHandler().setLocalReference(entRef);
        context.getRefHandler().setModuleReference(bodyRef);
    }

    
    private void createCompositeRef(ParseContext context, ReferenceItem entityRef, ReferenceItem hardwareRef) {
    	//ModuleObjectCompositeHolder composite = ModuleObjectCompositeHolder.dualHolder("", entityRef, hardwareRef);
    	context.getRefHandler().setLocalReference(entityRef);
    	
    }
    
    
    public TopObjectBase generateModuleSmallNew(ParseContext context) {
    	
    	TopASTNode headNode = this.getFirstASTChild();
    	TopASTNode bodyNode = headNode.getNextASTSibling();
    	
        ReferenceLocation loc = context.createReferenceLocation(this.getFirstASTChild());
        ReferenceItem<Entity> entityRef = (ReferenceItem<Entity>) headNode.generateModule(context);
       
        ReferenceItem<InstanceModule> imodref = context.getRefHandler().getProjectReference().findReference(entityRef.getname(), ReferenceUtilities.REF_INSTANCE_MODULE_TOP);
        if (imodref == null)
        {
            InstanceModule instanceModule = new VerilogInstanceModule(entityRef.getname(),context.getRefHandler().getProjectReference());
            imodref = context.getRefHandler().getProjectReference().addModuleObject(instanceModule, loc);
        }
        imodref.getObject().setEntityReference(entityRef);
        entityRef.getObject().setInstanceModRef(imodref);
        
        HardwareModule module = new HardwareModule(entityRef.getname(),"",imodref);
        ReferenceItem<HardwareModule> moduleRef = module.createReferenceItem();
        this.createCompositeRef(context, entityRef, moduleRef);
        context.setActiveReference(moduleRef);
        bodyNode.generateModule(context);
        imodref.addModuleObject(moduleRef.getObject(),null);
        
        SuperModule smod = (SuperModule) context.getRefHandler().getSuperModuleReference().getObject();
        smod.addObject(entityRef);
        smod.addObject(moduleRef);
        
        if (context.getPass() != BuildInterface.BUILD_FILE_CONTEXT) {
        	this.checkVariableDefinitions(context, imodref.getObject());
        }
        return entityRef;
            
    }

    
}
