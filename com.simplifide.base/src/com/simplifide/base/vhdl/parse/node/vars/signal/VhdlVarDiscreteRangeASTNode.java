/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.vhdl.parse.node.vars.signal;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.vars.signal.VarRangeASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;



/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: May 16, 2004
 * Time: 12:23:57 PM
 * To change this template use File | Settings | File Templates.
 */

// [ top : bottom]
// [ top ]


public class VhdlVarDiscreteRangeASTNode extends VarRangeASTNode
{

	private static final long serialVersionUID = 1L;


	public VhdlVarDiscreteRangeASTNode() {}
   

    public TopObjectBase generateModuleSmallNew(ParseContext context)
    {
        TopASTNode child = this.getFirstASTChild();
        return child.generateModule(context);
    }


    





}
