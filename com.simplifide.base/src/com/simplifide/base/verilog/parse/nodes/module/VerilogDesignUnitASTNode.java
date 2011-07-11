/*
 * VerilogDesignUnitASTNode.java
 *
 * Created on April 21, 2007, 11:47 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.verilog.parse.nodes.module;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.ModuleObjectNew;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.module.SuperModule;
import com.simplifide.base.core.newfunction.BaseFunction;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.var.realvars.SystemVar;
import com.simplifide.base.core.var.types.TypeVar;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;



/**
 *
 * @author Andy
 */
public class VerilogDesignUnitASTNode extends TopBodyNode{
    
    /** Creates a new instance of VerilogDesignUnitASTNode */
    public VerilogDesignUnitASTNode() {}


    public TopObjectBase generateModuleSmallNew(ParseContext context) {
       
    	TopASTNode designNode = this.getFirstASTChild();
    	
    	ReferenceItem active = context.getActiveReference();
    	ModuleObject base = (ModuleObject) designNode.generateModule(context);
    	if (base instanceof ReferenceItem) {
    		ReferenceItem ref = (ReferenceItem) base;
    		if (ref.getObject() instanceof BaseFunction ||
    			ref.getObject() instanceof SystemVar) {
    			SuperModule<ModuleObjectNew> smod = context.getRefHandler().getSuperModuleReference().getObject();
    			smod.setContainsDefines(true);
    		}
    		
    	}
    	
    	this.addItem((ModuleObjectNew)active.getObject(), base, designNode);
    	
        return designNode.generateModule(context);
    }

    
}
