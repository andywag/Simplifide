/*
 * ExpressionOpASTNode.java
 *
 * Created on December 13, 2005, 12:45 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.vhdl.parse.node.segment.core;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.segment.basic.operator.FactorOperator;
import com.simplifide.base.core.segment.basic.operator.MultiOperatorUnit;
import com.simplifide.base.sourcefile.antlr.node.segment.core.MultiOpASTNode;
import com.simplifide.base.sourcefile.antlr.tok.TopASTToken;
import com.simplifide.base.vhdl.parse.grammar.VhdlTokenTypes;


/**
 *
 * @author awagner
 */
public class FactorOpASTNode extends MultiOpASTNode{
    
    /** Creates a new instance of ExpressionOpASTNode */
    public FactorOpASTNode() {}

    public TopObjectBase getTopOperator() 
    {
        return new FactorOperator("Factor");
    }

    public ReferenceItem<MultiOperatorUnit> getOperatorUnit(ReferenceItem sc, TopASTToken uval) 
    {
        if (uval.getType() == VhdlTokenTypes.DOUBLESTAR) return new FactorOperator.DStar(sc).createReferenceItem();
            
        return null;
    }
    
}
