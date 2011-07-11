/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.sourcefile.antlr.node.vars.types;

import antlr.Token;

import com.simplifide.base.core.doc.HdlDoc;
import com.simplifide.base.core.var.types.TypeVar;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.tok.TopASTToken;


/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: May 16, 2004
 * Time: 11:28:12 AM
 * To change this template use File | Settings | File Templates.
 */

public class TypeDefASTNode extends TopASTNode
{

    public TypeDefASTNode() {}
    public TypeDefASTNode(Token tok) {super(tok);}
   
    protected void handleDocEndtoken(TypeVar typevar) {
    	int len = this.getNumberOfChildren();
    	TopASTNode node = this.getLastASTChild();
    	TopASTToken tok = node.getToken();
    	HdlDoc doc = tok.getDoc();
    	if (doc != null) {
    		typevar.setDoc(doc);
    	}
    }


}
