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
import com.simplifide.base.core.segment.basic.operator.ShiftOperator;
import com.simplifide.base.sourcefile.antlr.node.segment.core.MultiOpASTNode;
import com.simplifide.base.sourcefile.antlr.tok.TopASTToken;
import com.simplifide.base.vhdl.parse.grammar.VhdlTokenTypes;




/**
 *
 * @author awagner
 */
public class ShiftOpASTNode extends MultiOpASTNode{
    
    /** Creates a new instance of ExpressionOpASTNode */
    public ShiftOpASTNode() {}

    public TopObjectBase getTopOperator() 
    {
        return new ShiftOperator("Shift");
    }

    public ReferenceItem<MultiOperatorUnit> getOperatorUnit(ReferenceItem sc, TopASTToken uval) 
    {
        if (uval.getType() == VhdlTokenTypes.ROL) return new ShiftOperator.ROL(sc).createReferenceItem();
        else if (uval.getType() == VhdlTokenTypes.ROR) return new ShiftOperator.ROR(sc).createReferenceItem();
        else if (uval.getType() == VhdlTokenTypes.SLA) return new ShiftOperator.SLA(sc).createReferenceItem();
        else if (uval.getType() == VhdlTokenTypes.SLL) return new ShiftOperator.SLL(sc).createReferenceItem();
        else if (uval.getType() == VhdlTokenTypes.SRA) return new ShiftOperator.SRA(sc).createReferenceItem();
        else if (uval.getType() == VhdlTokenTypes.SRL) return new ShiftOperator.SRL(sc).createReferenceItem();
        return null;
    }
    
}
