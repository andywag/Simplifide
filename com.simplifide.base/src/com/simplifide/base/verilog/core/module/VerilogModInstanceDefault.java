/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.verilog.core.module;

import com.simplifide.base.basic.util.StringOps;
import com.simplifide.base.core.instance.ModInstanceDefault;
import com.simplifide.base.core.port.PortList;
import com.simplifide.base.core.port.PortTop;
import com.simplifide.base.core.reference.ReferenceItem;

public class VerilogModInstanceDefault extends ModInstanceDefault{

	
	private static final long serialVersionUID = 1L;

	public VerilogModInstanceDefault(String moduleName, ReferenceItem<PortList> generic, ReferenceItem<PortList> port) {
		super(moduleName,generic,port);
	}
		   
	private String getPortTemplatePattern(PortList<PortTop> list) {
        if (list.getnumber() == 0) return "";
        
        String out = "";
        boolean first = true;
        for (PortTop port : list.getInputOutputOrderedList()) {
            
            if (!first) out += ",\n" + StringOps.spaceString(HTML_PORT_INDENT+4);
            else out += StringOps.spaceString(HTML_PORT_INDENT+4);
            out += "." + port.getname();
            //String eqInd = StringOps.spaceString(32-port.getname().length());
            out +=  "(${" + port.getname() + "})";
            first = false;
        }
        out += ");\n";
        return out;
        
    }
	
	public String getTemplatePattern() {
    	String outType = this.getname() + " ${i" + this.getname() + "}  " + "(\n";
    	outType += this.getPortTemplatePattern((PortList)this.getPortListRef().getObject());
    	//outType += ");\n\n";
    	return outType;
	}
	
	public static class DotStar extends VerilogModInstanceDefault{
		
		public DotStar() {
			super("",null,null);
		}
		
	}
	
}
