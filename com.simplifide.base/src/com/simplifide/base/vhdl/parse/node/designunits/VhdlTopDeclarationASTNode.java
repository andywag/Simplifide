/*
 * VhdlTopDeclarationASTNode.java
 *
 * Created on May 23, 2006, 4:38 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.vhdl.parse.node.designunits;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.ModuleObjectCompositeHolder;
import com.simplifide.base.core.module.InstanceModuleTop;
import com.simplifide.base.core.module.SuperModule;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.top.ModuleTopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;


/**
 *
 * @author awagner
 */
public abstract class VhdlTopDeclarationASTNode extends ModuleTopASTNode {
    
    /** Creates a new instance of VhdlTopDeclarationASTNode */
    public VhdlTopDeclarationASTNode() {super();}
    
    
   /**
    * 
    * @param modItem 
 * @param entItem 
 * @param context TODO
    * @return 
    */
   protected ReferenceItem createCompositeReferenceItem(ReferenceItem modItem, ReferenceItem entItem)
   {
	    String contextName = "Context";
	    if (modItem != null) contextName += modItem.getname();
        ModuleObjectCompositeHolder holder = new ModuleObjectCompositeHolder(contextName);
        if (modItem != null) {
            holder.addObject(modItem);
        }
        if (entItem != null) {
            holder.addObject(entItem);
        }
        return holder.createReferenceItem();
        
	   // Questionable as Object which are defined internally will not be added later
	   /*ModuleObjectCompositeHolderNew comp = new ModuleObjectCompositeHolderNew("EntArch");
	   comp.appendObject(modItem);
	   comp.appendObject(entItem);
	   return comp.createReferenceItem();
   	*/
   }
    
    
    /** Method used to create the instance module for this operation 
     * @param name 
     * @param context 
     * @return 
     */
    protected abstract InstanceModuleTop createNewInstanceModule(String name, ParseContext context);
    
    /**
     * 
     * @param context 
     * @param obj 
     * @return 
     */
    public ReferenceItem addReferenceObject(ParseContext context, ModuleObject obj, TopASTNode locationNode) {
        ReferenceLocation loc = context.createReferenceLocation(locationNode);
        SuperModule smod = (SuperModule) context.getRefHandler().getSuperModuleReference().getObject();
        
        
        // Creates or finds the instance module
        
        ReferenceItem<InstanceModuleTop> imodref = context.getRefHandler().getProjectReference().findReference(obj.getname(), ReferenceUtilities.REF_INSTANCE_MODULE_TOP);
        if (imodref == null)
        {
            InstanceModuleTop instanceModule = this.createNewInstanceModule(obj.getname(),context); // Create Instance Module Regardless
            imodref = context.getRefHandler().getProjectReference().addModuleObject(instanceModule, loc);
        }
        ReferenceItem uitem = imodref.addModuleObject(obj, loc);
        context.setActiveReference(uitem);
        
        
        /* This is a convenience operator to handle the navigator window operations. The ordering appears strange
          but is require for this to function properly */
        smod.addObject(uitem);//  addModuleObject(uitem,loc);
        //smod.getLocalReference().addReferenceItem(context.getActiveReference());
        
        return uitem;
    }
    
    private void init() {}
    
    
    
    
}
