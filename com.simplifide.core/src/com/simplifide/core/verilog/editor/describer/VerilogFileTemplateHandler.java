/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.verilog.editor.describer;

import com.simplifide.base.basic.struct.NodePosition;
import com.simplifide.base.verilog.parse.base.VerilogTemplateHandler;
import com.simplifide.core.python.template.TemplateInterpreter;
//import com.simplifide.core.scalaext.ScalaInterpreter;
//import com.simplifide.core.scalaext.context.ScalaCurrentContext;
import com.simplifide.core.source.design.DesignFile;
import com.simplifide.core.source.design.FileTemplateHandler;

public class VerilogFileTemplateHandler extends FileTemplateHandler{

	public VerilogFileTemplateHandler(DesignFile dfile) {
		super(dfile);
	}
	
	public String createAddition(String intermediate) {
		return VerilogTemplateHandler.TEMPLATE_STRING_BEGIN  + "\n" +
			   intermediate  + 
			   VerilogTemplateHandler.TEMPLATE_STRING_END;
	}
	
	


	protected String executePython(String command, TemplateInterpreter interp) {
		return interp.executeVerilog(command);
	}

	@Override
	protected String executeScala(String command, NodePosition pos) {
		
		//ScalaCurrentContext context = this.createCurrentContext(pos.getStartPos());
		//return ScalaInterpreter.executeVerilog(context, command, pos);
		return "error";
	}
	
}
