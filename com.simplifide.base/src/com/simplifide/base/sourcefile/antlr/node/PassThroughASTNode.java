/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.sourcefile.antlr.node;

import antlr.Token;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;



/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: Mar 17, 2004
 * Time: 11:47:24 AM
 * To change this template use File | Settings | File Templates.
 */

public class PassThroughASTNode extends TopASTNode 
{

    

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PassThroughASTNode() {}
    public PassThroughASTNode(Token tok)
    {
        super(tok);
    }

     public boolean canPrune() 
    {
        if (this.getFirstASTChild() != null) return true;
        return false;
    }
    public TopASTNode prunedNode() 
    {
        if (this.getFirstASTChild().canPrune()) return this.getFirstASTChild().prunedNode();
        else return this.getFirstASTChild();
    }
    
    public TopObjectBase generateModuleSmallNew(ParseContext context)
    {
        if (this.getFirstASTChild() == null) return null;
        return this.getFirstASTChild().generateModule(context);
    } 


}
