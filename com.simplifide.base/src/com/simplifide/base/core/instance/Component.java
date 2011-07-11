/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.core.instance;

import java.awt.Image;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.reference.ReferenceUtilities;

public class Component extends EntityHolder {    
    
    
    public Component(){super();}
    public Component(String moduleName) {super(moduleName);}
       
    /** Move the doc from the entity to this component if no doc exists */
    public void convertHdlDoc(Entity entity) {
    	if (this.getDoc() == null) this.setDoc(entity.getDoc());
    	this.updateFromEntity(this);
    }
    
    public void updateHdlDoc(ModuleObject parent) {
    	if (this.getDoc() == null) {
    		InstanceModule instMod = (InstanceModule) this.getInstanceModRef().getObject();
    		if (instMod.getEntityReference() == null) return;
    		if (instMod.getEntityReference().isShadow()) return;
    		if (instMod.getEntityReference() != null && 
    			instMod.getEntityReference().getObject() != null) {
    			Entity entity = (Entity) instMod.getEntityReference().getObject();
    			this.setDoc(entity.getDoc());
    		}
    	}
    	super.updateHdlDoc(parent);
    }
    
    private void updateFromEntity(ModuleObject parent) {
    	if (this.getConnectRef() != null) {
    		this.getConnectRef().getObject().updateHdlDoc(this);
    	}
    }
    
    public Image getIcon() {
    	return null;
    }
    public int getSearchType() {return ReferenceUtilities.REF_COMPONENT;}
    
    public String getTemplateName() {
        return "component";
    }
    
    
}
