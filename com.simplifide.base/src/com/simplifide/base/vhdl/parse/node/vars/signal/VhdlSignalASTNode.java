/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.vhdl.parse.node.vars.signal;

import antlr.Token;

import com.simplifide.base.core.module.HardwareModule;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.core.var.realvars.OperatingTypeVar;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: May 16, 2004
 * Time: 11:28:12 AM
 * To change this template use File | Settings | File Templates.
 */

//signal_declaration : SIGNAL identifier_list COLON subtype_indication signal_kind var_assign SEMI

public class VhdlSignalASTNode extends VhdlVarASTNode
{

    
	private static final long serialVersionUID = 1L;

	public VhdlSignalASTNode() {}
    public VhdlSignalASTNode(Token tok) {super(tok);}
   
    public OperatingTypeVar createOperation() {return new OperatingTypeVar.SignalVar();}

    protected void setLastSignal(ParseContext context) {
    	HardwareModule umod = context.getRefHandler().getModuleReference().getObject();
    	ReferenceLocation loc = context.createReferenceLocation(this);
    	loc.setDocPosition(loc.getDocPosition() + loc.getLength());
    	loc.setLength(0);
    	umod.setLastSignalLocation(loc);
    }

    
}
