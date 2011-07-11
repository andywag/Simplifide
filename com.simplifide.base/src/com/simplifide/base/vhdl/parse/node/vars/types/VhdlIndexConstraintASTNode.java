/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.vhdl.parse.node.vars.types;

import antlr.Token;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.sourcefile.antlr.parse.SearchContext;



/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: May 16, 2004
 * Time: 11:28:12 AM
 * To change this template use File | Settings | File Templates.
 */
///     index_constraint : LPAREN discrete_range ( COMMA discrete_range )* RPAREN

public class VhdlIndexConstraintASTNode extends TopASTNode
{

	private static final long serialVersionUID = 1L;

	public VhdlIndexConstraintASTNode() {}
    public VhdlIndexConstraintASTNode(Token tok) {super(tok);}

    // Only Used To Specify Context Types for Nodes
    public void resolveContext(ParseContext context) {
    	context.setSearchContext(new SearchContext.Signal());   	
    }
    
    public TopObjectBase generateModuleSmallNew(ParseContext context)
    {
        TopASTNode child = this.getFirstASTChild(); // (
        child = child.getNextASTSibling();          // ArrPointer
        return child.generateModule(context);
    }
    

   





}
