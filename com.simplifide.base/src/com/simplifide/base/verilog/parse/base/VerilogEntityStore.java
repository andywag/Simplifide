/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.verilog.parse.base;

import java.util.Collections;
import java.util.List;

import com.simplifide.base.core.instance.Entity;
import com.simplifide.base.core.instance.ModInstanceDefault;
import com.simplifide.base.core.port.PortList;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.sourcefile.antlr.stream.EntityStore;
import com.simplifide.base.sourcefile.antlr.stream.EntityStorePort;
import com.simplifide.base.verilog.core.port.VerilogPortDefault;

public class VerilogEntityStore extends EntityStore{

	public VerilogEntityStore(Entity entity) {
		super(entity);
	}

	private void uniquify(List<String> str) {
		String lastname = "";
		for (int i=str.size()-1;i>=0;i--) {
			if (str.get(i).equals(lastname)) str.remove(i);
			lastname = str.get(i);
		}
	}
	
	public EntityStore.IOList createAutoArgs() {
		IOList ioList = new IOList();
		
		ModInstanceDefault def = (ModInstanceDefault) this.getEntity().getConnectRef().getObject();
		PortList plist = (PortList) def.getPortListRef().getObject();
		List<VerilogPortDefault> portList = plist.getInputOutputOrderedList();
		
		boolean first = true;
		String outstr = "";
		// Add Existing Input/Output Ports
		for (VerilogPortDefault port : portList) {
			if (port.getDeclarationRef() == null) continue;
			if (port.getDeclarationRef().getObject() == null) continue;
			if (port.getSearchType() == ReferenceUtilities.REF_PORT_OUTPUT) {
				ioList.outputList.add(port.getname());
			}
			if (port.getSearchType() == ReferenceUtilities.REF_PORT_INPUT) {
				ioList.inputList.add(port.getname());
			}
		}
		// Add Connection Ports
		for (EntityStorePort eport : this.getInputList()) {
			ioList.inputList.add(eport.getName());
		}
		for (EntityStorePort eport : this.getOutputList()) {
			ioList.outputList.add(eport.getName());
		}
		Collections.sort(ioList.inputList);
		Collections.sort(ioList.outputList);
		this.uniquify(ioList.inputList);
		this.uniquify(ioList.outputList);
		return ioList;
	}
	
}
