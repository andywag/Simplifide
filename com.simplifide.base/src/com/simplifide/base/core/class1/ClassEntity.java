package com.simplifide.base.core.class1;

import com.simplifide.base.BaseLog;
import com.simplifide.base.core.instance.Entity;
import com.simplifide.base.core.port.PortList;
import com.simplifide.base.core.reference.ReferenceItem;

public class ClassEntity extends Entity{

	private ReferenceItem<PortList> portsR;
	private ReferenceItem<ClassInstanceModule> superR;
	
	public ClassEntity(String name, ReferenceItem<PortList> ports) {
		super(name);
		this.portsR = portsR;
	}

	public void setPortsR(ReferenceItem<PortList> portsR) {
		this.portsR = portsR;
	}

	public ReferenceItem<PortList> getPortsR() {
		return portsR;
	}

	public void setSuperR(ReferenceItem<ClassInstanceModule> superR) {		
		this.superR = superR;
	}

	public ReferenceItem<ClassInstanceModule> getSuperR() {
		return superR;
	}
	
}
