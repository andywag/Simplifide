/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.sourcefile.antlr.node.segment.core;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.segment.basic.operator.MultiOperator;
import com.simplifide.base.core.segment.basic.operator.MultiOperatorUnit;
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

public abstract class MultiOpASTNode extends TopOpASTNode
{

    

    public MultiOpASTNode() {}
   
    public boolean canPrune() 
    {
        if (this.getNumberOfChildren() == 1) return true;
        return false;
    }
    public TopASTNode prunedNode() 
    {
        if (this.getFirstASTChild().canPrune()) return this.getFirstASTChild().prunedNode();
        else return this.getFirstASTChild();
    }
    
    
    
    
     /** Operations occur in a left to right fashion so group elements in  */ 
    
    public TopObjectBase generateModuleSmallNew(ParseContext context)
    {
        TopASTNode child = this.getFirstASTChild(); // First Operator
        ReferenceItem uval = (ReferenceItem) child.generateModule(context);
        TopASTNode nchild = child.getNextASTSibling();
        if (nchild == null) return uval; // This is a case when there is only 1 term
        
        MultiOperator multiOp = new MultiOperator("multi",uval);
        
        
        while (nchild != null)
        {
          
            TopASTToken tok = nchild.getToken();
            nchild = nchild.getNextASTSibling();
            if (nchild != null)
            {
                ReferenceItem newobj = (ReferenceItem) nchild.generateModule(context);
                ReferenceItem<MultiOperatorUnit> multiRef = this.getOperatorUnit(newobj, tok);
                multiOp.addReferenceItem(multiRef);
                nchild = nchild.getNextASTSibling();
            }
            
        }
        return multiOp.createReferenceItem();
        
    }
    
    public abstract ReferenceItem<MultiOperatorUnit> getOperatorUnit(ReferenceItem sc, TopASTToken uval);
    
    



}
