/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.verilog.core.module;

import java.util.ArrayList;
import java.util.List;

import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.project.CoreProjectBasic;
import com.simplifide.base.core.reference.ReferenceItem;

public class VerilogInstanceModule extends InstanceModule{

	 public VerilogInstanceModule(String name, ReferenceItem<CoreProjectBasic> projectRef) {
		 super(name,projectRef);
	 }
	 /** Location of Base Template Location for Generating Documentation */
    public String getBaseDocTemplateFolder() {
    	return "vhdl";
    }
    
    public List<ReferenceItem> getHyperlinkItemList(ReferenceItem item) {
        ArrayList<ReferenceItem> list = new ArrayList<ReferenceItem>();
        if (this.getEntityReference() != null) list.add(this.getEntityReference());
        if (this.getComponentReference() != null) list.add(this.getComponentReference());
        return list;
    }
	
}
