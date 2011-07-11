/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.vhdl.parse.node.vars.signal;

import com.simplifide.base.core.var.realvars.OperatingTypeVar;


/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: May 16, 2004
 * Time: 11:28:12 AM
 * To change this template use File | Settings | File Templates.
 */

//variable_declaration : ( SHARED )? VARIABLE identifier_list COLON subtype_indication var_assign SEMI

public class VhdlVariableASTNode extends VhdlVarASTNode
{

   
	private static final long serialVersionUID = 1L;

	public VhdlVariableASTNode() {}
    

    public OperatingTypeVar createOperation() {
        return new OperatingTypeVar.VariableVar();
    }
   

    



}
