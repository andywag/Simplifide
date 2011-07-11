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
import com.simplifide.base.core.segment.basic.operator.MultiOperatorUnit;
import com.simplifide.base.core.segment.basic.operator.RelationOperator;
import com.simplifide.base.sourcefile.antlr.node.segment.core.MultiOpASTNode;
import com.simplifide.base.sourcefile.antlr.tok.TopASTToken;
import com.simplifide.base.vhdl.parse.grammar.VhdlTokenTypes;


/**
 *
 * @author awagner
 */
public class RelationOpASTNode extends MultiOpASTNode{
    
    /** Creates a new instance of ExpressionOpASTNode */
    public RelationOpASTNode() {}

    public TopObjectBase getTopOperator() 
    {
        return new RelationOperator("Expression");
    }

    public ReferenceItem<MultiOperatorUnit> getOperatorUnit(ReferenceItem sc, TopASTToken uval) 
    {
        if (uval.getType() == VhdlTokenTypes.EQ) return new RelationOperator.Eq(sc).createReferenceItem();
        else if (uval.getType() == VhdlTokenTypes.GREATERTHAN) return new RelationOperator.Greater(sc).createReferenceItem();
        else if (uval.getType() == VhdlTokenTypes.GE) return new RelationOperator.GreaterEq(sc).createReferenceItem();
        else if (uval.getType() == VhdlTokenTypes.LOWERTHAN) return new RelationOperator.Less(sc).createReferenceItem();
        else if (uval.getType() == VhdlTokenTypes.LE) return new RelationOperator.LessEq(sc).createReferenceItem();
        else if (uval.getType() == VhdlTokenTypes.NEQ) return new RelationOperator.Neq(sc).createReferenceItem();
        return null;
    }
    
}
