/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.sourcefile.antlr.node.port;

import antlr.Token;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;



/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: Mar 18, 2004
 * Time: 9:24:40 AM
 * To change this template use File | Settings | File Templates.
 */

public class PortElementASTNode extends TopASTNode
{

  

	private static final long serialVersionUID = 1L;


	public PortElementASTNode() {}
    public PortElementASTNode(Token tok)
    {
        super(tok);
    }

   
    public TopObjectBase generateModuleSmallNew(ParseContext context)
    {
        TopASTNode ch = this.getFirstASTChild();
        if (ch != null) return ch.generateModule(context);
        else return null;
    }
    
   

   

   



}
