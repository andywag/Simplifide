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
import com.simplifide.base.core.segment.basic.operator.MultiplyOperator;
import com.simplifide.base.sourcefile.antlr.node.segment.core.MultiOpASTNode;
import com.simplifide.base.sourcefile.antlr.tok.TopASTToken;
import com.simplifide.base.vhdl.parse.grammar.VhdlTokenTypes;


/**
 *
 * @author awagner
 */
public class MultiplyOpASTNode extends MultiOpASTNode{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Creates a new instance of ExpressionOpASTNode */
    public MultiplyOpASTNode() {}

    public TopObjectBase getTopOperator() 
    {
        return new MultiplyOperator("Shift");
    }

    public ReferenceItem<MultiOperatorUnit> getOperatorUnit(ReferenceItem sc, TopASTToken uval) 
    {
        if (uval.getType() == VhdlTokenTypes.DIV) return new MultiplyOperator.Divide(sc).createReferenceItem();
        else if (uval.getType() == VhdlTokenTypes.MOD) return new MultiplyOperator.Mod(sc).createReferenceItem();
        else if (uval.getType() == VhdlTokenTypes.REM) return new MultiplyOperator.Rem(sc).createReferenceItem();
        else if (uval.getType() == VhdlTokenTypes.MUL) return new MultiplyOperator.Times(sc).createReferenceItem();
      
        return null;
    }
    
}
