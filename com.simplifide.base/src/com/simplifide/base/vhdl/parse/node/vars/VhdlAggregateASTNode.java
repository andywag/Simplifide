/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.vhdl.parse.node.vars;

import antlr.Token;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: Apr 25, 2004
 * Time: 7:13:49 PM
 * To change this template use File | Settings | File Templates.
 *
 */


/** This class will serve to handle complex aggregate expressions 
 *  @todo : Dummy class (Just masks the errors 
 */ 

public class VhdlAggregateASTNode extends TopASTNode
{

    public VhdlAggregateASTNode() {super();}
    public VhdlAggregateASTNode(Token tok)
    {
        super(tok);
        init();
    }

    private void init() {}


   public TopObjectBase generateModuleSmallNew(ParseContext context)
   {
	   TopASTNode child = this.getFirstASTChild(); // (
	   child = child.getNextASTSibling(); // First Node
	   while (child != null) {
		   child.generateModule(context);
		   child = child.getNextASTSibling();
		   if (child != null) child = child.getNextASTSibling();
	   }
	   return null;
	   // Used to make sure things are found
        //return null;
   }
    
    
}
