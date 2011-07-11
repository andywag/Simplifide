/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.sourcefile.antlr.node.segment.core;

import antlr.Token;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.sourcefile.antlr.tok.TopASTToken;


/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: May 16, 2004
 * Time: 11:28:12 AM
 * To change this template use File | Settings | File Templates.
 */

/* This is a class which handles operations not surrounded by pars */




public abstract class UniOpASTNode extends TopOpASTNode
{

    

    public UniOpASTNode() {}
    public UniOpASTNode(Token tok) {super(tok);}
   
    public TopObjectBase generateModuleSmallNew(ParseContext context)
    {
        TopASTNode child1 = this.getFirstASTChild(); // First Operator
        TopASTNode child2 = child1.getNextASTSibling();
        
        if (child2 == null) return child1.generateModule(context);
        
        TopASTToken top = child1.getToken();
        ReferenceItem codeseg = (ReferenceItem) child2.generateModule(context);
        
        ReferenceItem uobj = (ReferenceItem) this.getOperatorUnit(codeseg, top);
        return uobj.createReferenceItem();
    }
    
    
    
    
    public abstract ReferenceItem getOperatorUnit(ReferenceItem sc, TopASTToken uval);

    public boolean canPrune() {
        if (this.getNumberOfChildren() == 1) return true;
        return false;
    }
    
    public TopASTNode prunedNode() 
    {
        if (this.getFirstASTChild().canPrune()) return this.getFirstASTChild().prunedNode();
        else return this.getFirstASTChild();
    }
    



}
