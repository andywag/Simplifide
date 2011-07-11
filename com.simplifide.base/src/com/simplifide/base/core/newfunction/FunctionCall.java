/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.core.newfunction;

import java.util.ArrayList;
import java.util.List;

import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.core.port.PortList;
import com.simplifide.base.core.port.PortTop;
import com.simplifide.base.core.reference.ReferenceItem;

public class FunctionCall extends BaseFunction{

    private PortList<PortTop> ports;
    
    public FunctionCall(InstanceFunction instFunc, PortList ports) {
        super(instFunc.getDeclarationRef());
        ReferenceItem<InstanceFunction> instR = instFunc.createReferenceItem();
        this.setInstanceRef(instR);
        
        
        this.ports = ports;
    }
    
    public ReferenceItem<ModuleObjectWithList> getDependants() {
        ModuleObjectWithList outList = new ModuleObjectWithList("deps");
        
        for (PortTop port : this.ports.getRealSelfList()) {
            outList.addAll((ModuleObjectWithList)port.getDependants().getObject());
        }
        return outList.createReferenceItem();
    }
    
    public List<ReferenceItem> getHyperlinkItemList(ReferenceItem item)
    {
        if (this.getInstanceRef() != null) {
        	return this.getInstanceRef().getObject().getHyperlinkItemList(item);
        }
        return new ArrayList();
    }

	

	
}
