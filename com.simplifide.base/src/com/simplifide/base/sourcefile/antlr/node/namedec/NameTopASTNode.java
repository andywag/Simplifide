/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.sourcefile.antlr.node.namedec;

import antlr.Token;

import com.simplifide.base.basic.struct.NodePosition;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;


/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: Mar 17, 2004
 * Time: 11:47:24 AM
 * To change this template use File | Settings | File Templates.
 */

public abstract class NameTopASTNode extends TopASTNode
{

    public NameTopASTNode() {}
    public NameTopASTNode(Token tok)
    {
        super(tok);
    }

    public abstract NodePosition getNodeRange();
    
    
  

 
}
