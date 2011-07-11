/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.vhdl.parse.node.instance;

import antlr.Token;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.finder.ModuleObjectBaseItem;
import com.simplifide.base.core.instance.Component;
import com.simplifide.base.core.instance.EntityHolder;
import com.simplifide.base.core.instance.ModInstanceDefault;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;


/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: Mar 18, 2004
 * Time: 9:24:40 AM
 * To change this template use File | Settings | File Templates.
 */

// LabelColon (Instance Name )
// ModuleName
// PortMap
// GenericMap

public class VhdlComponentDeclarationASTNode extends TopASTNode
{


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VhdlComponentDeclarationASTNode() {}
    public VhdlComponentDeclarationASTNode(Token tok)
    {
        super(tok);
    }
    
    public TopASTNode formatBase() {return this;}
    
	
    
    public boolean canFold() {return true;}
    public String getFoldName()
    {
    	TopASTNode child = this.getFirstASTChild();
    	child = child.getNextASTSibling();
    	child = child.getNextASTSibling();
    	
        return "component " + child.getRealText();
    }
    
    public void resolveContext(ParseContext context) {
        TopASTNode node = this.getFirstASTChild();
        node = node.getNextASTSibling();
        ModuleObjectBaseItem base = new ModuleObjectBaseItem(node.getRealText());
        ReferenceItem<InstanceModule> imodref = (ReferenceItem) node.generateSearchTypeNew(context, ParseContext.SEARCHREFERENCECONTEXTANDGLOBAL, ReferenceUtilities.REF_INSTANCE_MODULE);
        if (imodref == null) {
        	imodref =  context.getRefHandler().getProjectReference().findReference(node.getRealText(), ReferenceUtilities.REF_INSTANCE_MODULE);
        }       
        if (imodref != null) {
            context.getRefHandler().setActiveReference(imodref.getObject().getComponentReference());
        }
    }
    
    
    /** @todo : Fatally Broken (need tree to check ) */
     public TopObjectBase generateModuleSmallNew(ParseContext context)
    {

        TopASTNode ch = this.getFirstASTChild(); // Component
        TopASTNode name = ch.getNextASTSibling(); // name
                
        ReferenceLocation loc = context.createReferenceLocation(name);             
        
        String instanceName = name.getRealText();
        Component component = new Component(name.getRealText());
        ReferenceItem<Component> componentRef = component.createReferenceItemWithLocation(loc);
        
        ReferenceItem imodref = (ReferenceItem) name.generateSearchTypeNew(context, ParseContext.SEARCHREFERENCECONTEXTANDGLOBAL, ReferenceUtilities.REF_INSTANCE_MODULE);
        if (imodref == null) {
        	imodref =  context.getRefHandler().getProjectReference().findReference(component.getname(), ReferenceUtilities.REF_INSTANCE_MODULE);
        }
        if (imodref == null) {
            InstanceModule instanceModule = new InstanceModule(instanceName, context.getRefHandler().getProjectReference());
            imodref = context.getRefHandler().getProjectReference().addModuleObject(instanceModule, loc);
        }
        if (imodref != null && imodref.getObject() instanceof EntityHolder) {
        	EntityHolder ent = (EntityHolder) imodref.getObject();
        	imodref = ent.getInstanceModRef();
        }
        
        ReferenceItem imod = imodref.addModuleObject(component, loc);
        
        // Seems like illogical order but is required based on the reference Generation
       
        component.setInstanceModRef(imodref);
        
        ParseContext.Storage store = context.createStorage();
        //ReferenceItem rcomp = context.changeRefObject(component,loc);
        
        ch = name.getNextASTSibling(); // is
        ch = ch.getNextASTSibling(); // Mod Instance Default (Entity Header )
        
        
        ReferenceItem<ModInstanceDefault> entRef = (ReferenceItem) ch.generateModule(context);
        entRef.getObject().setname(component.getname());
        entRef.setLocation(context.createReferenceLocation(this));
        component.setConnectRef(entRef);
        
        context.restoreStorage(store);
        ch = ch.getNextASTSibling();
        this.handleFindUsagesOverNodes(context, ch, ReferenceUtilities.REF_COMPONENT,3);
        // Attach Component to instancemodule

        
        
        return componentRef;
    }
    
    

  
    
}
