package com.simplifide.base.core.class1;

import java.util.List;

import javax.print.attribute.standard.ReferenceUriSchemesSupported;

import com.simplifide.base.core.finder.ModuleObjectFindItem;
import com.simplifide.base.core.finder.index.ModuleObjectIndexTop;
import com.simplifide.base.core.module.HardwareModule;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.newfunction.FunctionHolder;
import com.simplifide.base.core.port.PortList;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;

public class ClassModule extends HardwareModule{

	private ReferenceItem<PortList> portListR;

	public ClassModule(String name) {
		super(name);
	}
	
    public void cleanObject() {}
	
    private ClassEntity getClassEntity() {
    	InstanceModule imod = (InstanceModule) this.getInstModRef().getObject();
    	ClassEntity entity = (ClassEntity) imod.getEntity();
    	return entity;
    }
    public ClassInstanceModule getSuperModule() {
    	if (this.getInstModRef() != null && this.getInstModRef().getObject() != null) {
    		InstanceModule imod = (InstanceModule) this.getInstModRef().getObject();
        	ClassEntity entity = (ClassEntity) imod.getEntity();
        	if (entity.getSuperR() != null) {
        		return entity.getSuperR().getObject();
        	}
        	return null;
    	}
    	return null;
    	
    }
    
    public ReferenceItem<FunctionHolder> getFunctionHolder(String name) {
    	return this.findBaseReference(name, ReferenceUtilities.REF_FUNCTION_HOLDER);
    }
    
    /** Find a Reference Item based on the find item and type */
    public ReferenceItem findBaseReference(ModuleObjectIndexTop item) {
    	if (item.getItem().getname().equalsIgnoreCase("super")) {
    		return this.getSuperModule().createReferenceItem();
    	}
    	
    	ReferenceItem ref = super.findBaseReference(item);
    	if (ref == null && this.getSuperModule() != null) ref = this.getSuperModule().findBaseReference(item);
    	if (ref == null && item.getBase().getname().equalsIgnoreCase(this.getname())) {
    		return this.getInstModRef();
    	}
    	return ref;
    }
    
 
    
    /** @todo : Need to clean up types */
    public List<ReferenceItem> findPrefixItemList(ModuleObjectFindItem item, int type) {
        List<ReferenceItem> refList = super.findPrefixItemList(item, type);
        if (this.getSuperModule() != null) refList.addAll(this.getSuperModule().findPrefixItemList(item, type));
        return refList;
    }
    

	public void setPortListR(ReferenceItem<PortList> portListR) {
		this.portListR = portListR;
	}

	public ReferenceItem<PortList> getPortListR() {
		return portListR;
	}
	
}
