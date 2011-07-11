/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.vhdl.parse.node.instance;


import com.simplifide.base.BaseLog;
import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.basic.util.StringOps;
import com.simplifide.base.core.instance.EntityHolder;
import com.simplifide.base.core.instance.ModInstanceConnect;
import com.simplifide.base.core.instance.ModInstanceConnectNotFound;
import com.simplifide.base.core.module.HardwareModule;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.module.InstanceModuleTop;
import com.simplifide.base.core.module.InstancePackage;
import com.simplifide.base.core.module.SuperModule;
import com.simplifide.base.core.port.PortList;
import com.simplifide.base.core.project.BuildInterface;
import com.simplifide.base.core.project.CoreProjectBasic;
import com.simplifide.base.core.project.CoreProjectSuite;
import com.simplifide.base.core.project.LibraryHolder;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.instance.InstanceTopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.sourcefile.antlr.parse.SearchContext;





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

public class VhdlInstanceTopASTNode extends InstanceTopASTNode {
    
    
    
    private static final long serialVersionUID = 1L;

    public VhdlInstanceTopASTNode() {}
    
    public TopASTNode formatBase() {return this;}
   

    public String getFoldName() {
        TopASTNode child = this.getFirstASTChild();
        String instname = child.getFirstASTChild().getRealText();
        String  name = child.getNextASTSibling().getFirstASTChild().getNextASTSibling().getRealText();
        return instname + StringOps.addParens(name);
    }

    
    public void resolveContext(ParseContext context) {
        TopASTNode child = this.getFirstASTChild();
        child = child.getNextASTSibling(); // Module Name Node
        ReferenceItem<EntityHolder> holdRef = this.findEntityRef(child, context);
        context.getRefHandler().setSecondaryReference(context.getActiveReference());
        context.getRefHandler().setActiveReference(holdRef);
        context.setSearchContext(new SearchContext.Signal());
    }
    
    
    private ReferenceItem<EntityHolder> getEntityFromProject(ReferenceItem projR, String[] us) {
    	ReferenceItem<EntityHolder> holderRef = null;
    	ReferenceItem<InstanceModuleTop> iref = projR.findReference(us[1], ReferenceUtilities.REF_INSTANCE_MODULE_TOP);
		if (iref != null) {
			if (iref.getObject() instanceof InstancePackage && us.length > 2) {
				holderRef = iref.findReference(us[2], ReferenceUtilities.REF_INSTANCE_MODULE);
				if (holderRef == null) holderRef = iref.findReference(us[2], ReferenceUtilities.REF_COMPONENT);
			}
			else {
				holderRef = ((InstanceModule)iref.getObject()).getEntityReference();
				if (holderRef == null) holderRef = ((InstanceModule)iref.getObject()).getComponentReference();
			}
			
		}
		return holderRef;
    }
   
    private ReferenceItem<EntityHolder> getEntityFromOtherProject(CoreProjectSuite<CoreProjectBasic> suite, String[] us) {
    	ReferenceItem<EntityHolder> holderRef = null;
    	for (ReferenceItem<? extends CoreProjectBasic> basicR : suite.getGenericSelfList()) {
    		if (basicR != null) {
    			holderRef = getEntityFromProject(basicR, us);
    			if (holderRef != null) return holderRef;
    		}
    	}
    	LibraryHolder holder = suite.getLibraryReference().getObject();
    	if (holder != null)
    	for (ReferenceItem<? extends CoreProjectBasic> basicR : holder.getGenericSelfList()) {
    		if (basicR != null) {
    			holderRef = getEntityFromProject(basicR, us);
    			if (holderRef != null) return holderRef;
    		}
    	}
    	return holderRef;
    }
    
    private ReferenceItem<EntityHolder> findEntityRef(TopASTNode ch, ParseContext context) {
        // Finds both component and module (Used for Hierarchical Nodes)
    	InstanceNameNode nameNode = (InstanceNameNode) ch;
    	String uname = nameNode.getEntityName();
    	
    	ReferenceItem<EntityHolder> holderRef = null;
    	String[] us = uname.split("\\.");
    	
    	if (us.length > 1) {
    		int index = us[us.length-1].indexOf("(");
    		if (index > 0) {
    			us[us.length-1] = us[us.length-1].substring(0,index);
    		}
    		
    		
    		ReferenceItem projR = context.getRefHandler().getGlobalReference().findReference(us[0], ReferenceUtilities.REF_PROJECT_TOP);
    		if (projR != null) {
    			/*
    			ReferenceItem<InstanceModuleTop> iref = projR.findReference(us[1], ReferenceUtilities.REF_INSTANCE_MODULE_TOP);
    			if (iref != null) {
    				if (iref.getObject() instanceof InstancePackage && us.length > 2) {
    					holderRef = iref.findReference(us[2], ReferenceUtilities.REF_INSTANCE_MODULE);
    					if (holderRef == null) holderRef = iref.findReference(us[2], ReferenceUtilities.REF_COMPONENT);
    				}
    				else {
    					holderRef = ((InstanceModule)iref.getObject()).getEntityReference();
        				if (holderRef == null) holderRef = ((InstanceModule)iref.getObject()).getComponentReference();
    				}
    			}*/
    			holderRef = getEntityFromProject(projR, us);
    			if (holderRef == null && us[0].equalsIgnoreCase("work")) { // Check the current project
    				if (context.getRefHandler().getProjectReference() != null) {
    					holderRef = getEntityFromProject(context.getRefHandler().getActiveReference(), us);
    				}
    			}
    			if (holderRef == null) {
    				try {
    					if (us[0].equalsIgnoreCase("work")) {
        					ReferenceItem<? extends CoreProjectSuite> suiteR = context.getRefHandler().getGlobalReference();
            				if (suiteR != null) {
            					CoreProjectSuite suite = (CoreProjectSuite) suiteR.getObject();
            					if (suite != null) holderRef = getEntityFromOtherProject(suite, us);
            				}
        				}
    				}
    				catch (Exception e) {
    					BaseLog.logError(e);
    				}
    				
    				
    			}
    		}
    	}
    	if (holderRef != null) return holderRef;
    	
        context.setDefinedMode(ParseContext.ERROR_NOT_DEFINED_DISABLED);
        holderRef = (ReferenceItem) ch.generateSearchTypeNew(context,
                ParseContext.SEARCHREFERENCECONTEXT,ReferenceUtilities.REF_COMPONENT);
        if (holderRef == null) {
        	holderRef = (ReferenceItem) ch.generateSearchTypeNew(context, ParseContext.SEARCHREFERENCECONTEXT,ReferenceUtilities.REF_ENTITY);
        }
       
        
        
        
        if (holderRef == null) { // Search for Component First
            ReferenceItem instRef = (ReferenceItem) ch.generateSearchTypeNew(context,ParseContext.SEARCHREFERENCEPROJECT,ReferenceUtilities.REF_INSTANCE_MODULE);
            context.setDefinedMode(ParseContext.ERROR_NOT_DEFINED_ENABLED);

            if (instRef == null) instRef = (ReferenceItem) ch.generateSearchTypeNew(context,ParseContext.SEARCHREFERENCECONTEXTANDGLOBAL,ReferenceUtilities.REF_INSTANCE_MODULE);
            if (instRef != null) {
                InstanceModule imod = (InstanceModule) instRef.getObject();
                holderRef = imod.getEntityReference();
            }
        }
        else { // Case where component is found
            InstanceModule instMod = (InstanceModule) holderRef.getObject().getInstanceModRef().getObject();
            if (instMod != null) {
                ReferenceItem entRef = instMod.getEntityReference();
                
                if (entRef != null) {
                	holderRef = entRef;
                }
            }
        }
        if (holderRef != null) holderRef.loadObject();
        return holderRef;
    }
    
  
    
    private TopObjectBase generateModuleNormal(ParseContext context) {
    	TopASTNode ch = this.getFirstASTChild(); // Label Colon
        ReferenceLocation loc = context.createReferenceLocation(ch);
        
        // Instance Name
        String instName = ch.getFirstASTChild().getRealText();
        ch = ch.getNextASTSibling(); // Name
        
        // Returns the name of the instantiation used for refactoring
        InstanceNameNode nameNode = (InstanceNameNode) ch;
        String decName = nameNode.getDecString();
        
        ReferenceItem<HardwareModule> moduleRef = context.getRefHandler().getModuleReference();
        
        // Finds either the component or entity associated with this instance
        ReferenceItem<EntityHolder> entityRef = this.findEntityRef(ch, context);
        if (entityRef != null && entityRef.isShadow()) {
        	ModuleObject obj = entityRef.loadObject();
        	entityRef = obj.createReferenceItem();
        }
        
        boolean notfound = false;
        
        context.getRefHandler().setSecondaryReference(context.getActiveReference());
        ModInstanceConnect connect;
        if (entityRef != null) {
        	connect = new ModInstanceConnect(instName,entityRef);
			connect.setEnclosingModuleReference(context.getRefHandler().getModuleReference().getObject().getInstModRef());
        }
        else { // Case where entity isn't found
        	connect = new ModInstanceConnectNotFound(instName,ch.getFirstASTChild().getNextASTSibling().getRealText());
			connect.setEnclosingModuleReference(context.getRefHandler().getModuleReference().getObject().getInstModRef());
			notfound = true;
        }
        connect.setFullLocation(context.createReferenceLocation(this));
        connect.setModnameLocation(nameNode.getDecLocation(context));
        connect.setConnectionName(decName);
        
        ReferenceItem<ModInstanceConnect> instanceRef = 
         	(ReferenceItem<ModInstanceConnect>) connect.createReferenceItemWithLocation(loc);
        ReferenceItem oldReference = context.getActiveReference();

        //if (notfound == false) {
        	 
             context.setActiveReference(instanceRef);
        //}
       
        
        // Handles Port List Operation
        ch = ch.getNextASTSibling(); // Generic Map Aspect
        MapAspectASTNode genericMap = (MapAspectASTNode) ch;
        //if (entityRef != null) { // Only update the ports if the entity/component is found
        // Need to update the ports for find and rename refactoring
        ReferenceItem<PortList> genericList = ( ReferenceItem<PortList>) ch.generateModule(context);
        if (genericList != null) {
        	genericList.changeName("Generics");
        }
        	
        ch = ch.getNextASTSibling(); // Port Map Aspect
        MapAspectASTNode portMap = (MapAspectASTNode) ch;

        ReferenceItem<PortList> portList = ( ReferenceItem<PortList>) ch.generateModule(context);
        if (portList != null) {
        	portList.changeName("Ports");
        }
        	
        connect.setPortListRef(portList);
        connect.setGenericListRef(genericList);
        	
        
        this.attachConnectorModule(entityRef, moduleRef, instanceRef, oldReference,context);
        this.handleVariables(instanceRef.getObject());
        
        context.getRefHandler().setSecondaryReference(null);
        
        return instanceRef;
    }
    
    private TopObjectBase generateFileContext(ParseContext context) {
    	TopASTNode child = this.getFirstASTChild();
    	child = child.getNextASTSibling();
    	ModuleObject obj = (ModuleObject) child.generateSearchTypeNew(context, ParseContext.SEARCHFINDITEM, ReferenceUtilities.REF_COMPONENT);
    	SuperModule smod = context.getRefHandler().getSuperModuleReference().getObject();
    	smod.addParentList(obj.createReferenceItem());
    	return null;
    }
    
    /**  */
    public TopObjectBase generateModuleSmallNew(ParseContext context) {
    	this.generateFileContext(context);
    	if (context.getPass() != BuildInterface.BUILD_FILE_CONTEXT) {
        	return this.generateModuleNormal(context);
    	}
    	return null;
    }
    
    
    
    
    
    
    
    
    
    
}
