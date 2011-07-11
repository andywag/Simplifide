/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.vhdl.describer;

import com.simplifide.base.basic.struct.NodePosition;
import com.simplifide.base.vhdl.parse.base.VhdlTemplateHandler;
import com.simplifide.core.project.EclipseBaseProject;
import com.simplifide.core.project.EclipseSuite;
import com.simplifide.core.python.template.TemplateInterpreter;
import com.simplifide.core.scalaext.ScalaInterpreter;
import com.simplifide.core.scalaext.context.ScalaCurrentContext;
import com.simplifide.core.source.design.DesignFile;
import com.simplifide.core.source.design.FileTemplateHandler;

public class VhdlFileTemplateHandler extends FileTemplateHandler{

	public VhdlFileTemplateHandler(DesignFile dfile) {
		super(dfile);
	}
	
	
	public String createAddition(String intermediate) {
		return VhdlTemplateHandler.TEMPLATE_STRING_BEGIN + "\n" + 
			   intermediate +
			   VhdlTemplateHandler.TEMPLATE_STRING_END;
	}
	
	

	@Override
	protected String executePython(String command, TemplateInterpreter interp) {
		return interp.executeVHDL(command);
	}


	@Override
	protected String executeScala(String command,NodePosition pos) {
		//int epos = command.indexOf("end_simplifide");
		//String ucommand = command.substring(0, epos);
		//String res = command.substring(epos);
		//int lpos = res.indexOf("\n");
		//pos.setEndPos(pos.getStartPos() + epos + lpos);
		String ucommand = command.replace("end_simplifide", "");
		ScalaCurrentContext context = this.createCurrentContext(pos.getStartPos());
		return ScalaInterpreter.executeVHDL(context,ucommand, pos);
	}
}
