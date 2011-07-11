package com.simplifide.base.refactor.model;

import java.util.ArrayList;
import java.util.Collections;

import com.simplifide.base.core.instance.ModInstanceConnect;
import com.simplifide.base.core.instance.ModInstanceTop;
import com.simplifide.base.core.port.PortConnect;
import com.simplifide.base.core.port.PortList;
import com.simplifide.base.core.port.PortTop;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.core.var.realvars.SystemVar;

public class ModInstanceWrap {

	private String name;
	private boolean vhdlFile;
	private ArrayList<PortWrap> generics = new ArrayList<PortWrap>();
	private ArrayList<PortWrap> ports = new ArrayList<PortWrap>();
	
	
	
	public ModInstanceWrap(String name, boolean vhdlFile) {
		this.setName(name);
		this.vhdlFile = vhdlFile;
	}
	
	public  ModInstanceWrap (ModInstanceTop def, boolean vhdlFile) {
		this.name = def.getname();
		this.vhdlFile = vhdlFile;
		
		PortList<PortTop> portList = (PortList) def.getPortListRef().getObject();
		PortList<PortTop> genericList = new PortList<PortTop>();
		if (def.getGenericListRef() != null) {
			genericList = (PortList) def.getGenericListRef().getObject();
		}
		
		ArrayList<PortWrap> inList = new ArrayList<PortWrap>();
		ArrayList<PortWrap> outList = new ArrayList<PortWrap>();
		ArrayList<PortWrap> inOutList = new ArrayList<PortWrap>();
		boolean ordered = false;
		
		if (ordered) { // Order ports alphabetically
			for (ReferenceItem <? extends PortTop> uport : genericList.getGenericSelfList()) {
				this.getGenerics().add(createBasicPort(uport.getObject() ,vhdlFile));
			}
			for (ReferenceItem <? extends PortTop> uport : portList.getGenericSelfList()) {
				if (uport.getObject().getSearchType() == ReferenceUtilities.REF_PORT_INPUT) {
					inList.add(createBasicPort(uport.getObject(),vhdlFile));
				}
				else if (uport.getObject().getSearchType() == ReferenceUtilities.REF_PORT_OUTPUT) {
					outList.add(createBasicPort(uport.getObject(),vhdlFile));
				}
				else {
					inOutList.add(createBasicPort(uport.getObject(),vhdlFile));
				}
			}
			this.getPorts().addAll(inList);
			this.getPorts().addAll(outList);
			this.getPorts().addAll(inOutList);
		}
		else {
			for (PortTop port : genericList.getOrderedPortList()) {
				this.getGenerics().add(createBasicPort(port ,vhdlFile));
			}
			for (PortTop port : portList.getOrderedPortList()) {
				this.getPorts().add(createBasicPort(port ,vhdlFile));
			}
		}
		
	}
	
	public ModInstanceConnectWrap createSimpleWrap() {
		ModInstanceConnectWrap wr = new ModInstanceConnectWrap("i" + this.getName(),this.isVhdlFile());
		wr.setConnectName(this.getName());
		for (PortWrap port : this.getGenerics()) {
			wr.addGeneric(port.getDefaultConnectWrap());
		}
		for (PortWrap port : this.getPorts()) {
			wr.addPort(port.getDefaultConnectWrap());
		}
		return wr;
	}

	
	
	public ModInstanceWrap copy() {
		ModInstanceWrap cop = new ModInstanceWrap(this.name,this.isVhdlFile());
		for (PortWrap gen : generics) cop.getGenerics().add(gen);
		for (PortWrap gen : ports) cop.getPorts().add(gen);
		return cop;
	}
	
	public void orderPorts() {
		Collections.sort(generics);
		Collections.sort(ports);
		
	}
	
	public void addGeneric(SystemVar var, boolean vhdl) {
		PortWrap wrap = new PortWrap(var,vhdl);
		this.addGeneric(wrap);
	}
	
	public void addPort(SystemVar var,boolean vhdl) {
		PortWrap wrap = new PortWrap(var,vhdl);
		this.addPort(wrap);
	}
	
	public void addGeneric(PortWrap port) {
		generics.add(port);
	}
	
	public void addPort(PortWrap port) {
		if (port.getIoType().intValue() == PortWrap.GENERIC.intValue()) {
			for (PortWrap port1 : this.generics) {
				if (port1.getName().equals(port.getName())) return;
			}
			generics.add(port);
		}
		else {
			for (PortWrap port1 : this.ports) {
				String p1 = port1.getName();
				String p =  port.getName();
				if (p1 != null && p1.equals(p)) return;
			}
			if (port != null && port.getName() != null) ports.add(port);
		}
	}
	
	protected static ModInstanceWrap createBasicWrap(ModInstanceTop def, boolean vhdlFile) {
		if (def instanceof ModInstanceConnect) return new ModInstanceConnectWrap(def.getname(),vhdlFile);
		else  return new ModInstanceWrap(def.getname(),vhdlFile);
	}
	
	protected static PortWrap createBasicPort(PortTop porttop, boolean vhdl) {
		if (porttop instanceof PortConnect) return new PortConnectWrap((PortConnect)porttop, vhdl);
		return new PortWrap(porttop.getLocalVar(),vhdl);
	}
	
	/*public  ModInstanceWrap (ModInstanceTop def, boolean vhdlFile) {
		ModInstanceWrap wrap = createBasicWrap(def, vhdlFile);
		PortList<PortTop> portList = (PortList) def.getPortListRef().getObject();
		PortList<PortTop> genericList = new PortList<PortTop>();
		if (def.getGenericListRef() != null) {
			genericList = (PortList) def.getGenericListRef().getObject();
		}
		
		ArrayList<PortWrap> inList = new ArrayList<PortWrap>();
		ArrayList<PortWrap> outList = new ArrayList<PortWrap>();
		ArrayList<PortWrap> inOutList = new ArrayList<PortWrap>();
		
		for (ReferenceItem <? extends PortTop> uport : genericList.getGenericSelfList()) {
			wrap.getGenerics().add(createBasicPort(uport.getObject(), true ,vhdlFile));
		}
		for (ReferenceItem <? extends PortTop> uport : portList.getGenericSelfList()) {
			if (uport.getObject().getSearchType() == ReferenceUtilities.REF_PORT_INPUT) {
				inList.add(createBasicPort(uport.getObject(), false,vhdlFile));
			}
			else if (uport.getObject().getSearchType() == ReferenceUtilities.REF_PORT_OUTPUT) {
				outList.add(createBasicPort(uport.getObject(), false,vhdlFile));
			}
			else {
				inOutList.add(createBasicPort(uport.getObject(), false,vhdlFile));
			}
		}
		wrap.getPorts().addAll(inList);
		wrap.getPorts().addAll(outList);
		wrap.getPorts().addAll(inOutList);
		return wrap;
	}*/
	
	public static ModInstanceWrap singlePortWrap(boolean vhdlFile) {
		ModInstanceWrap wrap = new ModInstanceWrap("",vhdlFile);
		for (int i=0;i<9;i++) {
			wrap.getPorts().add(new PortWrap("","",PortWrap.INPUT,"",vhdlFile));
		}
		return wrap;
	}
	
	public void setPorts(ArrayList<PortWrap> ports) {
		this.ports = ports;
	}

	public ArrayList<PortWrap> getPorts() {
		return ports;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setGenerics(ArrayList<PortWrap> generics) {
		this.generics = generics;
	}

	public ArrayList<PortWrap> getGenerics() {
		return generics;
	}


	private void setVhdlFile(boolean vhdlFile) {
		this.vhdlFile = vhdlFile;
	}


	public boolean isVhdlFile() {
		return vhdlFile;
	}

}
