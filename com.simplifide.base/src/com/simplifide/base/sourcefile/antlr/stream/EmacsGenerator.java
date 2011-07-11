/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.sourcefile.antlr.stream;

import java.util.List;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.core.finder.ModuleObjectBaseItem;
import com.simplifide.base.core.instance.Entity;
import com.simplifide.base.core.instance.EntityHolder;
import com.simplifide.base.core.instance.ModInstanceConnect;
import com.simplifide.base.core.module.HardwareModule;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.port.PortConnect;
import com.simplifide.base.core.port.PortList;
import com.simplifide.base.core.port.PortTop;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.core.var.realvars.SystemVar;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

public abstract class EmacsGenerator {

	public static String PORTINDENT   = "         ";
	public static String SIGNALINDENT = "                    ";
	
	
	public abstract String getCommentString();
	public abstract String createWireDeclaration(SystemVar tvar);
	
	
	protected String createWires(ModInstanceConnect connect, EntityHolder entHolder) {
		
		List<PortTop> portList = entHolder.getPortList();
		
		String outstr = "";
		PortList<PortTop> list = (PortList) connect.getPortListRef().getObject();
		if (list.getnumber() > 0) {
			
		
			String tempstr = "";
			for (ReferenceItem<? extends PortTop> port : list.getGenericSelfList()) {
				if (port.getSearchType() == ReferenceUtilities.REF_PORT_OUTPUT ||
					port.getSearchType() == ReferenceUtilities.REF_PORT_INOUT) {
						PortConnect con = (PortConnect) port.getObject();
						SystemVar tvar = con.getLocalVar();
						if (con.getExternVar() != null) {
							if (!tvar.getname().equalsIgnoreCase(con.getExternVar().getname())) {
								continue;
							}
						}
						boolean skip = false;
						for (PortTop cport : portList) {
							String varName = tvar.getname();
							String portName = cport.getname();
							if (varName.equalsIgnoreCase(portName)) {
								skip = true;
							}
						}
						
						if (!skip) {
							tempstr += this.createWireDeclaration(tvar) + "\n";
						}
						
				}
			}
			if (!tempstr.equalsIgnoreCase("")) {
				outstr = this.getCommentString() + " Outputs of " + connect.getname() + "\n";
				outstr += tempstr + "\n";
			}
		}
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

public String handleAutoInput(TemplateContents cont, ParseContext context) {
	return "";
}


}
