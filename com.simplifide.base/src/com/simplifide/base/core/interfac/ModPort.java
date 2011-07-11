package com.simplifide.base.core.interfac;

import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.core.port.PortList;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;

public class ModPort extends ModuleObjectWithList{

	private ReferenceItem<PortList> portsR;
	
	public int getSearchType() {
		return ReferenceUtilities.REF_MODPORT;
	}
	
	public ModPort(String name) {
		super(name);
	}

	public void setPortsR(ReferenceItem<PortList> portsR) {
		this.portsR = portsR;
	}

	public ReferenceItem<PortList> getPortsR() {
		return portsR;
	}
	
}
