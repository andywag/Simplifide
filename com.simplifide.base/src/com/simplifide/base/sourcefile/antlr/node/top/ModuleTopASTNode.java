/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.sourcefile.antlr.node.top;

import java.util.List;

import com.simplifide.base.core.error.warnings.VariableNotAssignedWarning;
import com.simplifide.base.core.error.warnings.VariableNotUsedWarning;
import com.simplifide.base.core.finder.ModuleObjectBaseItem;
import com.simplifide.base.core.instance.Entity;
import com.simplifide.base.core.module.HardwareModule;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.port.PortTop;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.core.var.realvars.SystemVar;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

public class ModuleTopASTNode extends TopASTNode{

	protected void checkUsed(ParseContext context, ReferenceItem<SystemVar> var) {
        if (var.getObject() instanceof SystemVar) {
            SystemVar uvar = var.getObject();
            
            if (!uvar.isUsed() && context.getErrorEnableHolder().warningNotused) {
                VariableNotUsedWarning warn = new VariableNotUsedWarning(var.getLocation(),var);
                context.getErrorList().add(warn);
            }
        }
    }
	protected void checkAssigned(ParseContext context, ReferenceItem<SystemVar> var) {
        if (var.getObject() instanceof SystemVar) {
            SystemVar uvar = var.getObject();
            if (!uvar.isAssigned() && context.getErrorEnableHolder().warningNotassigned) {
                VariableNotAssignedWarning warn = new VariableNotAssignedWarning(var.getLocation(),var);
                context.getErrorList().add(warn);
            }

        }
    }
	

	
    protected void checkVariableDefinitions(ParseContext context, InstanceModule instMod) {
        HardwareModule module = (HardwareModule) instMod.getArchitectureReference().getObject();
        List<ReferenceItem<SystemVar>> vars = module.findPrefixItemList(new ModuleObjectBaseItem(""), ReferenceUtilities.REF_SYSTEMVAR);
        for (ReferenceItem<SystemVar> var : vars) {
        	this.checkAssigned(context, var);
        	this.checkUsed(context, var);
        }
        if (instMod == null || 
        	instMod.getEntityReference() == null ||
        	instMod.getEntityReference().getObject() == null) return; 
        
        Entity entity = (Entity) instMod.getEntityReference().getObject();
        List<ReferenceItem> ports = entity.findPrefixItemList(new ModuleObjectBaseItem(""), ReferenceUtilities.REF_PORT_TOP);
        for (ReferenceItem<PortTop> port : ports) {
            ReferenceItem<SystemVar> varRef = port.getObject().getLocalVarReference();
        	if (port.getType() == ReferenceUtilities.REF_PORT_INOUT) {
            	
            	this.checkAssigned(context, varRef);
            	this.checkUsed(context, varRef);
               
            }
            else if (port.getType() == ReferenceUtilities.REF_PORT_INPUT ) {
            	this.checkUsed(context, varRef);
            }
            else if (port.getType() == ReferenceUtilities.REF_PORT_OUTPUT ) {
            	this.checkAssigned(context, varRef);
            }
        }
      
    }
	
}
