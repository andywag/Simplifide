/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.sourcefile.antlr.stream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.simplifide.base.core.instance.Entity;
import com.simplifide.base.core.instance.EntityHolder;
import com.simplifide.base.core.instance.ModInstanceConnect;
import com.simplifide.base.core.instance.ModInstanceDefault;
import com.simplifide.base.core.module.HardwareModule;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.port.PortList;
import com.simplifide.base.core.port.PortTop;
import com.simplifide.base.core.reference.ReferenceItem;

public abstract class EntityStore {

	private Entity entity;

	private List<EntityStorePort> inputList  = new ArrayList();
	private List<EntityStorePort> outputList = new ArrayList();
	private List<EntityStorePort> wireList   = new ArrayList();
	// Not Supported Yet
	private List<EntityStorePort> paramList   = new ArrayList();


	public EntityStore(Entity entity) {
		this.setEntity(entity);
		this.calculateIOLists();
	}
	
	
	public String handleAutoWire() {
		String outstr = "";
		for (EntityStorePort port : wireList) {
			outstr += port.getVar().createWireDeclaration(port.getName()) + "\n";
		}
		return outstr;
	}
	public String handleAutoInput() {
		String outstr = "";
		for (EntityStorePort port : inputList) {
			outstr += port.getVar().createInputDeclaration(port.getName()) + "\n";
		}
		return outstr;
		
	}
	public String handleAutoOutput() {
		String outstr = "";
		for (EntityStorePort port : outputList) {
			outstr += port.getVar().createOutputDeclaration(port.getName()) + "\n";
		}
		return outstr;
	}
	
	public String handleAutoTestReg() {
		String outstr = "";
		for (EntityStorePort port : inputList) {
			outstr += port.getVar().createRegDeclaration(port.getName()) + "\n";
		}
		
		return outstr;
	}
	
	public String handleAutoTestWire() {
		String outstr = "";
		for (EntityStorePort port : outputList) {
			outstr += port.getVar().createWireDeclaration(port.getName()) + "\n";
		}
		return outstr;
	}
	
	public IOList createAutoArgs() {
		return new IOList();
	}
		
	private void handleEntityPortGroup(List<EntityStorePort> group) {
		
		boolean in = false;
		boolean out = false;
		for (EntityStorePort port : group) {
			if (port.isInput()) in = true;
			else out = true;
		}
		if (group.size() > 0) {
			if (in && out) {
				this.getWireList().add(group.get(0));
			}
			else if (in) {
				this.getInputList().add(group.get(0));
			}
			else if (out) {
				this.getOutputList().add(group.get(0));
			}
		}
		
	}
	
	private void handleEntityPortList(List<EntityStorePort> portList) {
		Collections.sort(portList);
	
		EntityStorePort lastPort = null;
		List<EntityStorePort> group = new ArrayList();
		for (int i=0;i<portList.size();i++) {
			EntityStorePort current = portList.get(i);
			if (lastPort == null) {
				lastPort = current;
				group.add(current);
			}
			else if (current.getName().equalsIgnoreCase(lastPort.getName())) {
				group.add(current);
			}
			else if (!current.getName().equalsIgnoreCase(lastPort.getName())) {
				this.handleEntityPortGroup(group);
				group = new ArrayList();
				group.add(current);
				lastPort = current;
			}
		}
		this.handleEntityPortGroup(group);
	}
	
	private List<EntityStorePort> calculateIOListsPort(PortList plist, ModInstanceConnect con) {
		List<EntityStorePort> totalPortList = new ArrayList<EntityStorePort>();
		List<PortTop> portList = plist.getInputOutputOrderedList();
		for (PortTop port : portList) {
			EntityStorePort eport = new EntityStorePort(port,con.getname());
			totalPortList.add(eport);
		}
		return totalPortList;
	}
	
	private void calculateIOLists() {
		InstanceModule imod = (InstanceModule) getEntity().getInstanceModRef().getObject();
		HardwareModule hmod = imod.getArchitecture();
		List<ReferenceItem<ModInstanceConnect>> conList = hmod.getInstantiations();
		List<EntityStorePort> totalPortList = new ArrayList<EntityStorePort>();
		for (ReferenceItem<ModInstanceConnect> conRef : conList) {
			List<EntityStorePort> conPortList = null;
			ModInstanceConnect con = conRef.getObject();
			if (con.getPortListRef() != null) {
				conPortList = this.calculateIOListsPort((PortList)con.getPortListRef().getObject(), con);
			}
			else {
				if (con.getEntityRef() != null) {
					EntityHolder holder = con.getEntityRef().getObject();
					if (holder == null) return;
					ModInstanceDefault def = (ModInstanceDefault) holder.getConnectRef().getObject();
					if (def == null) return;
					PortList portList = (PortList) def.getPortListRef().getObject();
					if (portList == null) return;
					conPortList = this.calculateIOListsPort(portList, con);
				}
				
			}
			if (conPortList != null) {
				totalPortList.addAll(conPortList);
			}
				
		}
		
		this.handleEntityPortList(totalPortList);

	}

	/*
	private void createAutoArgInput() {
		ModInstanceDefault def = (ModInstanceDefault) this.getEntity().getConnectRef().getObject();
		PortList plist = (PortList) def.getPortListRef().getObject();
		List<VerilogPortDefault> portList = plist.getInputOutputOrderedList();

		boolean first = true;
		String outstr = "";
		for (VerilogPortDefault port : portList) {
			if (port.getDeclarationRef() == null) continue;
			if (port.getDeclarationRef().getObject() == null) continue;
			if (first) {
				outstr = "\n" + EmacsGenerator.PORTINDENT + port.getname();
				first = false;
			}
			else {
				outstr += ",\n" + EmacsGenerator.PORTINDENT + port.getname();
			}
		}


	}
	 */
	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	public Entity getEntity() {
		return entity;
	}

	public void setInputList(List<EntityStorePort> inputList) {
		this.inputList = inputList;
	}

	public List<EntityStorePort> getInputList() {
		return inputList;
	}

	public void setOutputList(List<EntityStorePort> outputList) {
		this.outputList = outputList;
	}

	public List<EntityStorePort> getOutputList() {
		return outputList;
	}

	public void setWireList(List<EntityStorePort> wireList) {
		this.wireList = wireList;
	}

	public List<EntityStorePort> getWireList() {
		return wireList;
	}

	public static class IOList {
		public List<String> outputList = new ArrayList();
		public List<String> inputList  = new ArrayList();
		
	}

}
