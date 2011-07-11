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
import com.simplifide.base.core.segment.basic.operator.AddingOperator;
import com.simplifide.base.core.segment.basic.operator.MultiOperatorUnit;
import com.simplifide.base.sourcefile.antlr.node.segment.core.MultiOpASTNode;
import com.simplifide.base.sourcefile.antlr.tok.TopASTToken;
import com.simplifide.base.vhdl.parse.grammar.VhdlTokenTypes;


/**
 *
 * @author awagner
 */
public class AddOpASTNode extends MultiOpASTNode{
    
    
	private static final long serialVersionUID = 1L;

	/** Creates a new instance of ExpressionOpASTNode */
    public AddOpASTNode() {}

    public TopObjectBase getTopOperator() 
    {
        return new AddingOperator("Adder");
    }

    public ReferenceItem<MultiOperatorUnit> getOperatorUnit(ReferenceItem sc, TopASTToken uval) 
    {
        if (uval.getType() == VhdlTokenTypes.AMPERSAND) return new AddingOperator.Ampersand(sc).createReferenceItem();
        else if (uval.getType() == VhdlTokenTypes.MINUS) return new AddingOperator.Minus(sc).createReferenceItem();
        else if (uval.getType() == VhdlTokenTypes.PLUS) return new AddingOperator.Plus(sc).createReferenceItem();

      
        return null;
    }
    
}
