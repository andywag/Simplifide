/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.vhdl.parse.base;

import java.util.List;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.util.StringOps;
import com.simplifide.base.core.instance.Entity;
import com.simplifide.base.core.instance.ModInstanceDefault;
import com.simplifide.base.core.port.PortDefault;
import com.simplifide.base.core.port.PortList;
import com.simplifide.base.core.var.realvars.SystemVar;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.sourcefile.antlr.stream.EmacsGenerator;
import com.simplifide.base.sourcefile.antlr.stream.TemplateContents;

public class VhdlEmacsGenerator extends EmacsGenerator{


	private String createPortName(ModuleObject port) {
		return StringOps.rightPad(port.getname() ,32) + " => "  + port.getname();
	}

	

	public String handleAutoInst(TemplateContents cont, ParseContext context, boolean portEn) {
		if (!(context.getRefHandler().getActiveReference().getObject() instanceof Entity)) return "";
		Entity ent = (Entity) context.getRefHandler().getActiveReference().getObject();
		ModInstanceDefault def = (ModInstanceDefault) ent.getConnectRef().getObject();
		PortList plist;
		if (portEn) {
			plist = (PortList) def.getPortListRef().getObject();
		}
		else {
			plist = (PortList) def.getGenericListRef().getObject();
		}
		List<PortDefault> portList = plist.getInputOutputOrderedList();

		boolean first = true;
		String outstr = "";

		for (PortDefault port : portList) {

			if (first) {
				outstr = "\n" + EmacsGenerator.SIGNALINDENT +  this.createPortName(port);
				first = false;
			}
			else {
				outstr += ",\n" + EmacsGenerator.SIGNALINDENT + this.createPortName(port);
			}
		}
		return outstr;
	}

	/*
	private String createWires(ModInstanceConnect connect, EntityHolder entHolder) {
		Entity ent = (Entity) connect.getEntityRef().getObject();
		ModInstanceDefault def = (ModInstanceDefault) ent.getConnectRef().getObject();
		def.getPortListRef().getObject();
		
		String outstr = "-- Outputs of " + connect.getname() + "\n";
		PortList<PortTop> list = (PortList) def.getPortListRef().getObject();
		for (ReferenceItem<? extends PortTop> port : list.getGenericSelfList()) {
			if (port.getSearchType() == ReferenceUtilities.REF_PORT_OUTPUT ||
				port.getSearchType() == ReferenceUtilities.REF_PORT_INOUT) {
				SystemVar tvar = port.getObject().getLocalVar();
				outstr += tvar.createVhdlWireDeclaration() + "\n";
			}
		}
		outstr += "\n";
		
		return outstr;
	}
	
	public String handleAutoWire(TemplateContents cont, ParseContext context) {
		
		ModuleObject obj = context.getRefHandler().getActiveReference().getObject();
		HardwareModule mod = null;
		if (obj instanceof Entity) {
			Entity ent = (Entity) obj;
			InstanceModule inst = (InstanceModule) ent.getInstanceModRef().getObject();
			mod = inst.getArchitecture();
		}
		else if (obj instanceof HardwareModule) {
			mod = (HardwareModule) obj;
		}
		else {
			return "";
		}
		InstanceModule inst = (InstanceModule) mod.getInstModRef().getObject();
		EntityHolder ent = inst.getEntityHolder();
		
		List<ReferenceItem<ModInstanceConnect>> refList = mod.findPrefixItemList(new ModuleObjectBaseItem(""), ReferenceUtilities.REF_MODINSTANCE_CONNECT);
		String outstr = "";
	
		for (ReferenceItem<ModInstanceConnect> conRef : refList) {
			outstr += this.createWires(conRef.getObject(), ent);
		}
		
		return outstr;
		
	}
*/


	@Override
	public String createWireDeclaration(SystemVar tvar) {
		return tvar.createVhdlWireDeclaration();
	}

	@Override
	public String getCommentString() {
		return "--";
	}
	
	
	
}
