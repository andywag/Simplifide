package com.simplifide.core.refactor.port;

import java.util.ArrayList;
import java.util.List;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.core.hierarchy.PathTotalElement;
import com.simplifide.base.core.instance.Entity;
import com.simplifide.base.core.instance.ModInstanceConnect;
import com.simplifide.base.core.instance.ModInstanceDefault;
import com.simplifide.base.core.module.HardwareModule;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.port.PortConnect;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.core.reference.ReferenceUtilitiesInterface;
import com.simplifide.base.core.var.realvars.SystemVar;
import com.simplifide.base.refactor.model.ModInstanceConnectWrap;
import com.simplifide.base.refactor.model.ModInstanceWrap;
import com.simplifide.base.refactor.model.PortConnectWrap;
import com.simplifide.base.refactor.model.PortWrap;
import com.simplifide.core.refactor.changes.PortChangeMethods;
import com.simplifide.core.search.SourceMatch;

public class TopPortProcessor {

	
	
	public List<SourceMatch> createFinalChange(PathTotalElement element,boolean vhdl) {
		ArrayList<SourceMatch> matchList = new ArrayList<SourceMatch>();
		
		return matchList;
	}

	
	
	/** Create the Connection Changes */
	private List<SourceMatch> createConnectionChange(PathTotalElement element,boolean vhdl) {
		ModInstanceConnect connect = element.getTreeElement().getConnection();
		if (connect == null) return new ArrayList<SourceMatch>();
		ModInstanceConnectWrap wrap = new ModInstanceConnectWrap(connect,vhdl);
		
		PortConnect con = new PortConnect(element.getVariable().getReference(),element.getVariable().getReference());
		PortConnectWrap cwrap = new PortConnectWrap(con,vhdl);
		
		if (cwrap.getIoType().intValue() != PortWrap.INOUT.intValue()) {
			if (element.getDirection() == PathTotalElement.UP) cwrap.setIoType(PortWrap.OUTPUT);
			if (element.getDirection() == PathTotalElement.DOWN) cwrap.setIoType(PortWrap.INPUT);
		}
		wrap.addPort(cwrap);
		wrap.orderPorts();
		return PortChangeMethods.createConnectionChange(connect.getFullLocation(), wrap, vhdl);
	}
	/** Create the Connection Changes */
	private List<SourceMatch> createEntityComponentChange(PathTotalElement element,boolean vhdl) {
		InstanceModule imod = element.getTreeElement().getModule();
		ModInstanceWrap wrap = new ModInstanceWrap((ModInstanceDefault)imod.getEntity().getConnectRef().getObject(),vhdl);
		PortWrap pwrap = new PortWrap(element.getVariable(),vhdl);
		if (pwrap.getIoType().intValue() != PortWrap.INOUT.intValue()) {
			if (element.getDirection() == PathTotalElement.UP) pwrap.setIoType(PortWrap.OUTPUT);
			if (element.getDirection() == PathTotalElement.DOWN) pwrap.setIoType(PortWrap.INPUT);
		}
		wrap.addPort(pwrap);
		wrap.orderPorts();
		return PortChangeMethods.createEntityPortChange(imod, wrap, vhdl);
	}
	
	private List<SourceMatch> deleteExistingVar(PathTotalElement element, boolean vhdl) {
		SystemVar tvar = element.getVariable();
		if (tvar.getVariableType() == SystemVar.SIGNAL ||
	    	tvar.getVariableType() == SystemVar.VARIABLE) {
				return PortChangeMethods.deleteVariableDeclaration(tvar);
	    	}
			return PortChangeMethods.EMPTYLIST;
	}
	
	private List<SourceMatch> createEntityAndConnectionChange(PathTotalElement element,boolean vhdl) {
		ArrayList<SourceMatch> matchList = new ArrayList<SourceMatch>();
		matchList.addAll(this.createConnectionChange(element, vhdl));
		matchList.addAll(this.createEntityComponentChange(element, vhdl));
		return matchList;
	}
	
	private List<SourceMatch> createVarDeclaration(PathTotalElement element, boolean vhdl) {
		if (element.getTreeElement().getModule() == null) return new ArrayList<SourceMatch>();
		HardwareModule hmod = element.getTreeElement().getModule().getArchitecture();
		Entity ent = element.getTreeElement().getModule().getEntity();
		ModuleObject obj = hmod.findNewBaseReference(element.getVariable().getname(), ReferenceUtilitiesInterface.REF_MODULEOBJECT);
		if (obj == null) obj = ent.findNewBaseReference(element.getVariable().getname(), ReferenceUtilitiesInterface.REF_MODULEOBJECT);
		if (obj == null) {
			ReferenceLocation loc = hmod.getLastSignalLocation();
			if (loc == null) {
				loc = hmod.getDeclarationStartLocation();
				loc.setDocPosition(loc.getDocPosition()-1);
			}
			
			return PortChangeMethods.createVariableDeclaration(loc,
					element.getVariable(), vhdl);
		}
		return PortChangeMethods.EMPTYLIST;
	}
	
	/** Creates the middle change of a port addition 
	 * 
	 * 1. If UP only changes the module/entity header
	 * 2. If FLAT 
	 * 	  a. Adds Signals to Module
	 *    b. Changes the Entity/Header of Up Connection
	 *    c. Changes the Enityt/Header of Down Connection
	 * 3. If DOWN onlyc changes the module/entity header
	 */
	public List<SourceMatch> createMiddleChange(PathTotalElement element, boolean vhdl) {
		ArrayList<SourceMatch> matchList = new ArrayList<SourceMatch>();
		this.deleteExistingVar(element, vhdl);
		if (element.getDirection() == PathTotalElement.UP) {
			return this.createEntityAndConnectionChange(element, vhdl);
		}
		if (element.getDirection() == PathTotalElement.FLAT) {
			return createVarDeclaration(element, vhdl);
		}
		if (element.getDirection() == PathTotalElement.DOWN) {
			return this.createEntityAndConnectionChange(element, vhdl);
		}
		return matchList;
	}

	/** Create the total changes for a path list */
	public List<SourceMatch> createChanges(List<PathTotalElement> elements, boolean vhdl) {
		ArrayList<SourceMatch> matchList = new ArrayList<SourceMatch>();
		if (elements.size() == 1) return matchList;
		matchList.addAll(this.deleteExistingVar(elements.get(0), vhdl));
		for (PathTotalElement element : elements) {
         	matchList.addAll(this.createMiddleChange(element,vhdl));
		}
		return matchList;
	}

}
