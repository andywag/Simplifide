/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.sourcefile.antlr.node.vars.signal;

import antlr.Token;
import antlr.collections.AST;

import com.simplifide.base.sourcefile.antlr.node.TopASTNode;



/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: May 16, 2004
 * Time: 1:46:10 PM
 * To change this template use File | Settings | File Templates.
 */

public class VarNameASTNode extends TopASTNode
{


    public VarNameASTNode() {}
    public VarNameASTNode(Token tok)
    {
        super(tok);
    }


    /*

    public ListObjectScalar createModuleSmall(SuperModule umod, TopModule topmod, ListObjectScalar obj, boolean initial)
    {
        return this.getFirstASTChild().createModule(umod,null, obj, initial);
    }

*/
    public String getVarName()
    {
        String varName ;
        AST child = this.getFirstChild();
        if (this.getNumberOfChildren() == 1) varName = child.getText();
        else varName = child.getNextSibling().getText();
        return varName;
    }

    
}
