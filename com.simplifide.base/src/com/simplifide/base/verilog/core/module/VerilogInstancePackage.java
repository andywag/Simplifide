/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.verilog.core.module;

import com.simplifide.base.core.module.InstancePackage;
import com.simplifide.base.core.project.CoreProjectBasic;
import com.simplifide.base.core.reference.ReferenceItem;

public class VerilogInstancePackage extends InstancePackage{

	
	public VerilogInstancePackage(String name, ReferenceItem<CoreProjectBasic> basic) {
    	super(name,basic);
    }
	 /** Location of Base Template Location for Generating Documentation */
    public String getBaseDocTemplateFolder() {
    	return "vhdl";
    }
}
