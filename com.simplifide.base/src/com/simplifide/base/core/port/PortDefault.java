/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.core.port;

import java.util.ArrayList;
import java.util.List;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.core.instance.Component;
import com.simplifide.base.core.instance.Entity;
import com.simplifide.base.core.instance.ModInstanceConnect;
import com.simplifide.base.core.module.HardwareModule;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.core.var.realvars.OperatingTypeVar;
import com.simplifide.base.core.var.realvars.SystemVar;
import com.simplifide.base.core.var.realvars.OperatingTypeVar.IOVar;




/**
 * 
 * @author Andy
 */
public class PortDefault extends PortTop  {
     
    /** Constructor */
    public PortDefault(ReferenceItem<? extends SystemVar> localVarReference) {
        super(localVarReference);
    }

   
    /** Convenience Method to return the default value */
    public ModuleObject getDefault() {
   	if (this.getDefaultValue() != null) {
   		return this.getDefaultValue().getObject();
   	}
   	return null;
   }
    
    public ReferenceItem getOutputs() {
        return this.getLocalVarReference().getOutputs();
    }

    public ReferenceItem getDependants() {
        return this.getLocalVarReference().getDependants();
    }

    
    public String getIoString() {
        int type = this.getSearchType();
        if (type == ReferenceUtilities.REF_PORT_INPUT) return "input";
        if (type == ReferenceUtilities.REF_PORT_OUTPUT) return "output";
        if (type == ReferenceUtilities.REF_PORT_INOUT) return "inout";
        return "input";
    }
   
    public List<ReferenceItem> getRenameItemList(ReferenceItem item,ReferenceItem enclosingItem) {
    	List<ReferenceItem> itemList = super.getRenameItemList(item, enclosingItem);
    	
    	Object obj = enclosingItem.getObject();
    	Entity ent = null;
    	if (obj instanceof Entity) {
    		ent = (Entity) obj;
    	}
    	else if (obj instanceof HardwareModule){
    		HardwareModule mod = (HardwareModule) obj;
    		InstanceModule imod = (InstanceModule) mod.getInstModRef().getObject();
    		ent = (Entity) imod.getEntityReference().getObject();
    	}
    	else if (obj instanceof Component) {
    		Component comp = (Component) obj;
    		InstanceModule imod = (InstanceModule) comp.getInstanceModRef().getObject();
    		ent = (Entity) imod.getEntityReference().getObject();
    	}
    	else {
    		ArrayList<ReferenceItem> refs = new ArrayList<ReferenceItem>();
    		refs.add(this.createReferenceItem());
    		return refs;
    	}
    	// Entity Addition 
    	PortTop port = ent.findPort(item.getname());
    	itemList.add(port.getLocalVarReference());
    	// Component Addition
    	InstanceModule instanceModule = (InstanceModule) ent.getInstanceModRef().getObject();
    	if (instanceModule.getComponentReference() != null) {
        	Component comp = (Component) instanceModule.getComponentReference().getObject();
        	port = comp.findPort(item.getname());
        	if (port != null) itemList.add(port.getLocalVarReference());
    	}
    	// Connection Addition
    	List<ModInstanceConnect> connections = instanceModule.getConnectionList();
    	for (ModInstanceConnect connect : connections) {
    		PortConnect portc = (PortConnect) connect.findPort(item.getname());
    		if (portc != null) {
    			ReferenceItem nitem = new ReferenceItem(portc.getname(),ReferenceUtilities.REF_PORT_TOP);
        		nitem.setLocation(portc.getConnectionLocation());
        		itemList.add(nitem);
    		}
    		
    	}
 
    	return itemList;
    	//List<ReferenceItem> list = this.getHyperlinkItemList(item);
        //list.addAll(this.getConnectionReferenceItemList());
        //return list;
    }
    
    public OperatingTypeVar getOperatingVar() {
    	return this.getVariable().getOpTypeVar();
    }
    
    public int getSearchType() {
        OperatingTypeVar tvar = this.getVariable().getOpTypeVar();
        int utype = ReferenceUtilities.REF_PORT_TOP;
        if (tvar instanceof OperatingTypeVar.IOVar) {
            OperatingTypeVar.IOVar uvar = (IOVar) tvar;
            if (uvar.getType() == OperatingTypeVar.IOVar.INPUT) utype = ReferenceUtilities.REF_PORT_INPUT;
            else if (uvar.getType() == OperatingTypeVar.IOVar.OUTPUT) utype = ReferenceUtilities.REF_PORT_OUTPUT;
            else if (uvar.getType() == OperatingTypeVar.IOVar.INOUT) utype = ReferenceUtilities.REF_PORT_INOUT;
        }
        return utype;
    }
    
    /** Get the port name followed by the type */
    public String getNameWithType() {
        String portname = "Not Defined";
        
        if (this.getLocalVarReference() != null) {
            portname = this.getLocalVarReference().getDisplayName();
            if (this.getType() != null) portname += " : " + this.getType().getDisplayName();
        }
        
        return portname;
    }
    
    public String getDisplayName() {
        return this.getLocalVarReference().getDisplayName(); // + StringOps.addParens(this.getLocalVar().getType().getDisplayName());
    }



    
   
    
    
}
