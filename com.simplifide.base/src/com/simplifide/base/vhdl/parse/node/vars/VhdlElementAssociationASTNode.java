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

public class VhdlElementAssociationASTNode extends TopASTNode
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public VhdlElementAssociationASTNode() {super();}
    public VhdlElementAssociationASTNode(Token tok)
    {
        super(tok);
        init();
    }

    private void init() {}


   public TopObjectBase generateModuleSmallNew(ParseContext context)
   {  
	   return this.getFirstASTChild().generateModule(context);
        //return null;
   }
    
    
}
