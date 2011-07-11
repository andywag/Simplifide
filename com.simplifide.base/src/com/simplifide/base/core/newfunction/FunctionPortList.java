package com.simplifide.base.core.newfunction;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.core.port.PortList;
import com.simplifide.base.core.port.PortTop;
import com.simplifide.base.core.reference.ReferenceItem;

public class FunctionPortList extends PortList<PortTop>{
    public FunctionPortList() {}
    public FunctionPortList(String name) {
        super(name);
    }
    
    public FunctionPortList(PortList ports) {
    	super(ports.getname());
    	this.copyList(ports);
    }
    private void copyList(PortList<PortTop> ports) {
    	for (ReferenceItem<? extends PortTop> portR : ports.getGenericSelfList()) {
    		this.addReferenceItem(portR);
    	}
    }
    
    public void updateHdlDoc(ModuleObject parent) {
    	for (PortTop obj : this.getRealSelfList()) {
    		obj.updateHdlDoc(parent);
    	}
    }
    
}
