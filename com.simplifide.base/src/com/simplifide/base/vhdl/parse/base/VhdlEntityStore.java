/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.vhdl.parse.base;

import com.simplifide.base.core.instance.Entity;
import com.simplifide.base.sourcefile.antlr.stream.EntityStore;
import com.simplifide.base.sourcefile.antlr.stream.EntityStorePort;

public class VhdlEntityStore extends EntityStore{

	public VhdlEntityStore(Entity entity) {
		super(entity);
	}

	
	public String handleAutoArgs() {
		String outstr = "\n";
		boolean first = true;
		for (EntityStorePort port : this.getInputList()) {
			if (first) {
				first = false;
			}
			else {
				outstr += ";\n";
			}
			outstr += port.getVar().createInputDeclaration(port.getName());
		}
		for (EntityStorePort port : this.getOutputList()) {
			outstr += ";\n";
			outstr += port.getVar().createOutputDeclaration(port.getName());
		}
		return outstr;
		
	}
	public String handleAutoOutput() {
		String outstr = "";
		for (EntityStorePort port : this.getOutputList()) {
			outstr += port.getVar().createOutputDeclaration(port.getName()) + "\n";
		}
		return outstr;
	}
	
}
