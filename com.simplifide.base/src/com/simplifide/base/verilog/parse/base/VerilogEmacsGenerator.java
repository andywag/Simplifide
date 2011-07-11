/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.verilog.parse.base;

import java.util.List;

import com.simplifide.base.BaseLog;
import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.core.instance.Entity;
import com.simplifide.base.core.instance.ModInstanceDefault;
import com.simplifide.base.core.port.PortList;
import com.simplifide.base.core.var.realvars.SystemVar;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.sourcefile.antlr.stream.EmacsGenerator;
import com.simplifide.base.sourcefile.antlr.stream.TemplateContents;
import com.simplifide.base.verilog.core.port.VerilogPortDefault;

public class VerilogEmacsGenerator extends EmacsGenerator{

	
	
	public String handleAutoArgs(TemplateContents cont, ParseContext context) {
		if (!(context.getActiveReference().getObject() instanceof Entity)) {
			BaseLog.logInfo("Verilog Emacs Generator Broken" + context.getRefHandler().getSuperModuleReference());
			return "";
		}
		
		Entity ent = (Entity) context.getActiveReference().getObject();
		ModInstanceDefault def = (ModInstanceDefault) ent.getConnectRef().getObject();
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
		
		return outstr;
	}
	
	private String createPortName(ModuleObject port) {
		return "." + port.getname() + "(" + port.getname() + ")";
	}
	
	public String handleAutoInst(TemplateContents cont, ParseContext context) {
		
		Entity ent = (Entity) context.getRefHandler().getSecondaryReference().getObject();
		ModInstanceDefault def = (ModInstanceDefault) ent.getConnectRef().getObject();
		PortList plist = (PortList) def.getPortListRef().getObject();
		List<VerilogPortDefault> portList = plist.getInputOutputOrderedList();
		
		boolean first = true;
		String outstr = "";
		for (VerilogPortDefault port : portList) {
			if (port.getDeclarationRef() == null) continue;
			if (port.getDeclarationRef().getObject() == null) continue;
			if (first) {
				outstr = "\n" + EmacsGenerator.PORTINDENT + this.createPortName(port);
				first = false;
			}
			else {
				outstr += ",\n" + EmacsGenerator.PORTINDENT + this.createPortName(port);
			}
		}
		
		return outstr;
	}
	
	
	
	public String handleAutoSense(TemplateContents cont, ParseContext context) {
		return "";
	}

	@Override
	public String createWireDeclaration(SystemVar tvar) {
		//return tvar.createWireDeclaration();
		return "";
	}

	@Override
	public String getCommentString() {
		return "//";
	}
	
	
	
}
