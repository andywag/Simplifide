/*
 * InstanceNode.java
 *
 * Created on April 24, 2007, 11:52 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.verilog.parse.nodes.instance;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.finder.ModuleObjectBaseItem;
import com.simplifide.base.core.instance.Entity;
import com.simplifide.base.core.instance.EntityHolder;
import com.simplifide.base.core.instance.ModInstanceConnect;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.module.SuperModule;
import com.simplifide.base.core.port.PortList;
import com.simplifide.base.core.project.BuildInterface;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;


/**
 *
 * @author Andy
 * IDENTIFIER parameter_value_assignment module_instance ( COMMA module_instance )* SEMI
 */
// module_instantiation :IDENTIFIER parameter_value_assignment module_instance ( COMMA module_instance )* SEMI
public class ModuleInstantiationNode extends TopASTNode{
    
 
	private static final long serialVersionUID = -6955071774988898013L;

	/** Creates a new instance of InstanceNode */
    public ModuleInstantiationNode() {}
    
    public boolean canFold() {
        return true;
    }
    
    public TopASTNode formatBase() {return this;}
    
    public void resolveContext(ParseContext context) {
    	String text = this.getFirstASTChild().getRealText();
    	ModuleObjectBaseItem item = new ModuleObjectBaseItem(text);
    	ReferenceItem<InstanceModule> instMod = context.getRefHandler().findProjectAndLibraryObject(item, ReferenceUtilities.REF_INSTANCE_MODULE);
    	if (instMod != null) {
    		ReferenceItem<EntityHolder> entityRef = instMod.findReference(item.getname(), ReferenceUtilities.REF_ENTITY);	
    		context.getRefHandler().setSecondaryReference(entityRef);
    	}
    }
    
    /** Returns the location of the last child node. This is used to determine the appropriate place to 
     *  add new ports. This works becuase both the param and isntance node have their last node as the
     *  )
     */
    private ReferenceLocation getLastChildLocation(TopASTNode node, ParseContext context) {
    	TopASTNode child = node.getFirstASTChild();
    	if (child == null) return context.createReferenceLocation(node);
    	while (child != null) {
    		child = child.getNextASTSibling();
    	}
    	return context.createReferenceLocation(child);
    }
   
    public TopObjectBase generateModuleSmallNormal(ParseContext context) {
    	TopASTNode modNode   = this.getFirstASTChild();
    	TopASTNode paramNode = modNode.getNextASTSibling();
    	TopASTNode instNode  = paramNode.getNextASTSibling();
    	
    	
    	
    	// Find the Instance Module Associated with the Module Name
    	ReferenceItem<InstanceModule> instRef = (ReferenceItem<InstanceModule>) modNode.generateSearchTypeNew(context,
                 ParseContext.SEARCHREFERENCEPROJECTANDLIBRARY,
                 ReferenceUtilities.REF_MODULEOBJECT);
    	
    	// Set the found instance module as the secondary reference used in finding ports and other things
    	if (instRef != null) {
    		String modName = modNode.getRealText();
    		ReferenceItem<Entity> entRef = (ReferenceItem<Entity>) instRef.findReference(modName, ReferenceUtilities.REF_ENTITY);
    		context.getRefHandler().setSecondaryReference(entRef); 
    	}
    	else {
    		context.getRefHandler().setSecondaryReference(new ReferenceItem(modNode.getRealText(),ReferenceUtilities.REF_MODULEOBJECT));
    	}
    	// Need to add these to the ModInstanceConnect
    	ReferenceItem<PortList> genericList = (ReferenceItem<PortList>) paramNode.generateModule(context);
    	
    	// Parse the Actual Instances
    	NoSortList list = new NoSortList();
    	while (instNode != null) {
    		ReferenceItem<ModInstanceConnect> connectRef = (ReferenceItem<ModInstanceConnect>) instNode.generateModule(context);
    		if (connectRef != null) {
    			connectRef.getObject().setGenericListRef(genericList);
    			list.addObject(connectRef);
    		}
    		instNode = instNode.getNextASTSibling();
    		if (instNode != null) instNode.getNextASTSibling();
    		if (connectRef != null) {
        		connectRef.getObject().setFullLocation(context.createReferenceLocation(this));
        		connectRef.getObject().setModnameLocation(context.createReferenceLocation(this.getFirstASTChild()));
    		}
    		

    	}
    	context.getRefHandler().setSecondaryReference(null);

    	return list.createReferenceItem();
    	
        
    }

    private TopObjectBase generateFileContext(ParseContext context) {
    	TopASTNode child = this.getFirstASTChild();
    
    	ModuleObject obj = new ModuleObjectBaseItem(child.getRealText());
    	SuperModule smod = context.getRefHandler().getSuperModuleReference().getObject();
    	smod.addParentList(obj.createReferenceItem());
    	return null;
    }
    
    public TopObjectBase generateModuleSmallNew(ParseContext context) {
    	
    	TopObjectBase outval;
    	 outval =  this.generateFileContext(context);
    	 if (context.getPass() != BuildInterface.BUILD_FILE_CONTEXT) {
    		 outval = this.generateModuleSmallNormal(context);
    	 }
    
    	return outval;
    }
    
    
    
    
}
