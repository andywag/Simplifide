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
import com.simplifide.base.core.segment.basic.operator.ExpressionOperator;
import com.simplifide.base.core.segment.basic.operator.MultiOperatorUnit;
import com.simplifide.base.sourcefile.antlr.node.segment.core.MultiOpASTNode;
import com.simplifide.base.sourcefile.antlr.tok.TopASTToken;
import com.simplifide.base.vhdl.parse.grammar.VhdlTokenTypes;


/**
 *
 * @author awagner
 */
public class ExpressionOpASTNode extends MultiOpASTNode{
    
    private static final long serialVersionUID = -7612994189115436206L;

    /** Creates a new instance of ExpressionOpASTNode */
    public ExpressionOpASTNode() {}

    public TopObjectBase getTopOperator() 
    {
        return new ExpressionOperator("Expression");
    }

    public ReferenceItem<MultiOperatorUnit> getOperatorUnit(ReferenceItem sc, TopASTToken uval) 
    {
        if (uval.getType() == VhdlTokenTypes.AND) return new ExpressionOperator.And(sc).createReferenceItem();
        else if (uval.getType() == VhdlTokenTypes.NAND) return new ExpressionOperator.Nand(sc).createReferenceItem();
        else if (uval.getType() == VhdlTokenTypes.NOR) return new ExpressionOperator.Nor(sc).createReferenceItem();
        else if (uval.getType() == VhdlTokenTypes.OR) return new ExpressionOperator.Or(sc).createReferenceItem();
        else if (uval.getType() == VhdlTokenTypes.XNOR) return new ExpressionOperator.Xnor(sc).createReferenceItem();
        else if (uval.getType() == VhdlTokenTypes.XOR) return new ExpressionOperator.Xor(sc).createReferenceItem();
        return null;
    }
    
}
