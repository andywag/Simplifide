/*
 * InstanceModule.java
 *
 * Created on January 19, 2007, 5:09 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.module;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.core.finder.ModuleObjectBaseItem;
import com.simplifide.base.core.finder.ModuleObjectFindItem;
import com.simplifide.base.core.finder.index.ModuleObjectIndexTop;
import com.simplifide.base.core.hierarchy.ConnectorHolder;
import com.simplifide.base.core.hierarchy.ConnectorModule;
import com.simplifide.base.core.instance.Component;
import com.simplifide.base.core.instance.Entity;
import com.simplifide.base.core.instance.EntityHolder;
import com.simplifide.base.core.instance.ModInstanceConnect;
import com.simplifide.base.core.project.CoreProjectBasic;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.core.var.realvars.SystemVar;
import com.simplifide.base.refactor.model.ModInstanceConnectWrap;

/**
 *
 * @author awagner
 *
 * @todo : Add support for multiple architectures
 */
public class InstanceModule<T extends ModuleObject> extends InstanceModuleTop<T>{
        
   
    private static final long serialVersionUID = 1L;
	/** Pointer to the Entity for this object */
    private ReferenceItem<Entity> entityReference;
    /** Pointer to the component for this object */
    private ReferenceItem<Component> componentReference;
    /** Pointer to the architecture for this object (Should support multiple archs) */
    private ReferenceItem<HardwareModule> architectureReference;
    /** Pointer to a connection model used for hierarchy */
    private ReferenceItem<ConnectorModule> connectReference;
    
    private boolean vhdl;
    
    /** Creates a new instance of InstanceModule */
    public InstanceModule() {}
  
    public InstanceModule(String name, ReferenceItem<CoreProjectBasic> projectRef) {
    	super(name,projectRef);
    	init();
    }
    
    private void init() {
        ConnectorModule connect = new ConnectorModule(this.getname(),this.createReferenceItem());
        this.connectReference = connect.createReferenceItem();
    }
    
    private void finalizeObject() {
    	// Remove this object from the project if both items are in this file
    	if (this.architectureReference == null && this.entityReference == null) {
    		this.getProjectRef().getObject().removeObject(this.createReferenceItem());
    	}
    	
    }
    
    /** Create an initial instance for this module */
    public ModInstanceConnect createDefaultConnection() {
    	if (this.getEntityReference() != null) {
    		Entity entity = this.getEntityReference().getObject();
    		if (entity == null) entity = this.getEntityReference().getObject(); // Massive Kludge
    		return entity.createDefaultConnection();
    	}
    	else if (this.getComponentReference().getObject() != null) {
    		return this.getComponentReference().getObject().createDefaultConnection();
    	}
    	return null;
    }
    
    
    /** Remove the package references if they are shadows */
    public void clearShadowReferences() {
    	
    }
    
    /** Returns a list of places where this module is connected */
    public List<ModInstanceConnect> getConnectionList() {
    	ArrayList<ModInstanceConnect> connectList = new ArrayList<ModInstanceConnect>();
    	ConnectorModule<ConnectorHolder> mod = this.getConnectReference().getObject();
    	//ModuleObjectWithList<ConnectorHolder> holders = mod.getGenericSelfList();
    	return mod.getRealConnectionList();
    	/*
    	for (ReferenceItem<? extends ConnectorHolder> holder : mod.getRealConnectionList()) {
    		ConnectorHolder hold = holder.getObject();
    		if (hold != null && hold.getConnectRef() != null) {
        		ModInstanceConnect connect = hold.getConnectRef().getObject();
        		connectList.add(connect);
    		}
    		
    	}
    	return connectList;
    	*/
    }
    
    public List<SystemVar> getAllVars() {
    	if (this.getEntity() != null) {
    		List<SystemVar> varList = this.getEntity().getVars();
        	if (this.getArchitecture() != null) varList.addAll(this.getArchitecture().getVars());
        	Collections.sort(varList,new SystemVar.IOComparator());
        	return varList;
    	}
    	return new ArrayList<SystemVar>();
    	
    	
    }
    
    public Entity getEntity() {
    	if (this.getEntityReference() != null) return this.getEntityReference().getObject();
    	return null;
    }
    
    public Component getComponent() {
    	if (this.getComponentReference() != null) return this.getComponentReference().getObject();
    	return null;
    }
    
    public HardwareModule getArchitecture() {
    	if (this.getArchitectureReference() != null)
    		return this.getArchitectureReference().getObject();
    	return null;
    }
    
    public void clearConnectorReference() {
        this.connectReference.clearObject();
        this.connectReference.finalizeObject();
        init();
    }
    
    /** Remove the entity from this object */
    public void removeEntityReference() {
    	this.entityReference = null;
    	this.finalizeObject();
    }
    /** Remove the architecture from this object */
    public void removeArchitecture() {
    	this.architectureReference = null;
    	this.finalizeObject();
    }
    
    
    

    public Object getTemplateObject() {
        if (this.entityReference != null)
            return this.entityReference.getObject();
        return null;
    }
    
   
    
    public void deleteObject() {
    	if (this.connectReference != null) {
            this.connectReference.deleteObject();
            this.connectReference = null;
    	}


        super.deleteObject();
    }
    
    public EntityHolder getEntityHolder() {
    	if (this.componentReference != null) {
    		if (this.componentReference.getObject() != null) {
    			return this.componentReference.getObject();
    		}
    	}
    	if (this.entityReference != null) {
    		if (this.entityReference.getObject() != null) {
        		return this.entityReference.getObject();
        	}
    	}
    	
    	return null;
    }
    
    /** Location of Base Template Location for Generating Documentation */
    public String getBaseDocTemplateFolder() {
    	return "vhdl";
    }
    
    public int getSearchType(){return ReferenceUtilities.REF_INSTANCE_MODULE;}

    public boolean displayInPrefix() {
        if (this.entityReference == null) return false;
        return true;
    }
    
    public List<ModInstanceConnect> getConnections() {
    	return this.getConnectionList();
    	/*
    	List<ModInstanceConnect> connect = new ArrayList<ModInstanceConnect>();
    	for (ReferenceItem<ModInstanceConnect> cref : this.getConnectionReferenceItemList()) {
    		connect.add(cref.getObject());
    	}
    	return connect;
    	*/
    }
    
    /** Returns a list which contains the entity/component/ and connections */
    private List<ReferenceItem<ModInstanceConnect>> getConnectionReferenceItemList() {
    	List<ReferenceItem<ModInstanceConnect>> cons = new ArrayList<ReferenceItem<ModInstanceConnect>>();
    	List<ModInstanceConnect> cons2 = this.getConnections();
    	for (ModInstanceConnect con : cons2) {
    		cons.add(con.createReferenceItem());
    	}
    	return cons;
    	/*
    	ArrayList<ReferenceItem<ModInstanceConnect>> connectionList = new ArrayList<ReferenceItem<ModInstanceConnect>>();
    	ConnectorModule<ConnectorHolder> con = this.getConnectReference().getObject();
    	//ModuleObjectWithList<ConnectorHolder> mlist = con.getConnectionList();
        for (ReferenceItem<? extends ConnectorHolder> hold : con.getGenericSelfList()) {
        	if (hold.getObject().getConnectRef() != null) {
        		ModInstanceConnect connect = hold.getObject().getConnectRef().getObject();
            	ReferenceItem referenceItem = new ReferenceItem(connect);
            	referenceItem.setLocation(connect.getModnameLocation());
            	connectionList.add(referenceItem);
        	}
        }
    	return connectionList;
    	*/
    }
    
    public List<ReferenceItem> getRenameItemList(ReferenceItem item,ReferenceItem enclosingItem) {
        List<ReferenceItem> list = this.getHyperlinkItemList(item);
        list.addAll(this.getConnectionReferenceItemList());
        return list;
    }
    
    public List<ReferenceItem> getHyperlinkItemList(ReferenceItem item) {
        ArrayList<ReferenceItem> list = new ArrayList<ReferenceItem>();
        if (this.getEntityReference() != null) list.add(this.getEntityReference());
        if (this.getComponentReference() != null) list.add(this.getComponentReference());
        if (this.getArchitectureReference() != null) list.add(this.getArchitectureReference());
        return list;
    }
    
    public ReferenceItem findBaseReference(ModuleObjectIndexTop item)
    { 
        int type = item.getBase().getType();
        //if (ReferenceUtilities.checkType(ReferenceUtilities.REF_INSTANCE_MODULE,type) == 0) return this;
        if (type == ReferenceUtilities.REF_ENTITY) return (ReferenceItem) this.getEntityReference();
        else if (type == ReferenceUtilities.REF_COMPONENT) return (ReferenceItem) this.getComponentReference();
        else if (type == ReferenceUtilities.REF_HARDWARE_MODULE) return (ReferenceItem) this.getArchitectureReference();
        if (this.getEntityReference() != null) return this.getEntityReference().findBaseReference(item);
        return null;
    }
    
    public List<ReferenceItem> findPrefixItemList(ModuleObjectFindItem item, int type) {
    	List<ReferenceItem> refList = new ArrayList<ReferenceItem>();
        if (this.getEntityReference() != null) {
            refList.addAll(this.getEntityReference().findPrefixItemList(item,type));
        }
        if (this.getArchitectureReference() != null) {
        	refList.addAll(this.getArchitectureReference().findPrefixItemList(item, type));
        }
        return refList;
    }
    
    
     /** Adds a module object to the list */
     public ReferenceItem<T> addModuleObject(T obj, ReferenceLocation loc) {
        int type = obj.getSearchType();
        if (type == ReferenceUtilities.REF_ENTITY) {
            if (this.getEntityReference() == null) this.entityReference = obj.createReferenceItem();
            this.getEntityReference().setLocation(loc);
            this.getEntityReference().changeObject((Entity)obj);
            return (ReferenceItem<T>) this.getEntityReference();
        }
        else if (type == ReferenceUtilities.REF_COMPONENT) {
            if (this.getComponentReference() == null) this.componentReference = obj.createReferenceItem();
            this.getComponentReference().setLocation(loc);
            this.getComponentReference().changeObject((Component)obj);
            return (ReferenceItem<T>) this.getComponentReference();
        }
        else if (type == ReferenceUtilities.REF_HARDWARE_MODULE) {
            if (this.getArchitectureReference() == null) this.architectureReference = obj.createReferenceItem();
            this.getArchitectureReference().setLocation(loc);
            this.getArchitectureReference().changeObject((HardwareModule)obj);
            //this.fireConnectorChange();
            return (ReferenceItem<T>) this.getArchitectureReference();
        }
        
        return null;
    }
     
    /** Clean Connector --- Remove all of the connections*/
     public void cleanConnector() {
         if (this.connectReference != null) {
          // Remove this object from other modules connections
         	ConnectorModule<ConnectorHolder> connect = connectReference.getObject();
         	for (ReferenceItem<? extends ConnectorHolder> conRef : connect.getGenericSelfList()) {
         		if (conRef != null && 
         			conRef.getObject() != null && 
         			conRef.getObject().getModuleRef() != null &&
         			conRef.getObject().getModuleRef().getObject() != null) {
         			ConnectorModule conMod = conRef.getObject().getModuleRef().getObject();
         			if (conMod.getParentRef() != null) {
         				conMod.getParentRef().getObject().removeObject(this.connectReference);
         			}
         		}
         		
         	}
            connect.clearList();
         }
     }
     
   
    public ModuleObjectWithList getCompletionContextList(int type) {
    	ModuleObjectWithList alist = new ModuleObjectWithList();
    	ModuleObjectBaseItem item = new ModuleObjectBaseItem("");
    	if (this.getEntityReference() != null) {
    		Entity ent = this.getEntity();
    		List<ReferenceItem> blist = ent.findPrefixItemList(item, type);
    		for (ReferenceItem bref : blist) {
    			if (ReferenceUtilities.checkType(bref.getType(), type) == 0)
    				alist.addReferenceItem(bref);
    		}
    		if (ent.getContext() != null) {
        		blist = ent.getContext().findPrefixItemList(item, type);
        		for (ReferenceItem bref : blist) {
        			if (ReferenceUtilities.checkType(bref.getType(), type) == 0)
        				alist.addReferenceItem(bref);
        		}
    		}
    	}	
    	
    	if (this.getArchitectureReference() != null) {
    		HardwareModule mod = this.getArchitecture();
    		List<ReferenceItem> blist = mod.findPrefixItemList(item, ReferenceUtilities.REF_MODULEOBJECT);
    		for (ReferenceItem bref : blist) {
    			if (ReferenceUtilities.checkType(bref.getType(), type) == 0)
    				alist.addReferenceItem(bref);
    		}
    		if (mod.getContext() != null) {
        		blist = mod.getContext().findPrefixItemList(item, type);
        		for (ReferenceItem bref : blist) {
        			if (ReferenceUtilities.checkType(bref.getType(), type) == 0)
        				alist.addReferenceItem(bref);
        		}
    		}
    	}
    	return alist;
    }
    
   
    
    
    public ReferenceItem<Entity> getEntityReference() {
        return entityReference;
    }

    public void setEntityReference(ReferenceItem<Entity> entityReference) {
        this.entityReference = entityReference;
    }

    public ReferenceItem<Component> getComponentReference() {
        return componentReference;
    }

    public void setComponentReference(ReferenceItem<Component> componentReference) {
        this.componentReference = componentReference;
    }

    public ReferenceItem<HardwareModule> getArchitectureReference() {
        return architectureReference;
    }

    public void setArchitectureReference(ReferenceItem<HardwareModule> architectureReference) {
        this.architectureReference = architectureReference;
    }

    public ReferenceItem<ConnectorModule> getConnectReference() {
        return connectReference;
    }

    public void setConnectReference(ReferenceItem<ConnectorModule> connectReference) {
        this.connectReference = connectReference;
    }

	public void setVhdl(boolean vhdl) {
		this.vhdl = vhdl;
	}

	public boolean isVhdl() {
		return vhdl;
	}

	

    

   

  

  
    
    
}
