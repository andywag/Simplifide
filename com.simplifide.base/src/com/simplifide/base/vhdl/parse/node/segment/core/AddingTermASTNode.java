/*
 * ExpressionOpASTNode.java
 *
 * Created on December 13, 2005, 12:45 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.vhdl.parse.node.segment.core;

import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.segment.basic.operator.AddingOperatorTerm;
import com.simplifide.base.sourcefile.antlr.node.segment.core.UniOpASTNode;
import com.simplifide.base.sourcefile.antlr.tok.TopASTToken;
import com.simplifide.base.vhdl.parse.grammar.VhdlTokenTypes;


/**
 *
 * @author awagner
 */
public class AddingTermASTNode extends UniOpASTNode{
    
	private static final long serialVersionUID = 1L;



	/** Creates a new instance of ExpressionOpASTNode */
    public AddingTermASTNode() {}

   

    public ReferenceItem getOperatorUnit(ReferenceItem sc, TopASTToken uval) 
    {
        if (uval.getType() == VhdlTokenTypes.MINUS) return new AddingOperatorTerm.Minus(sc).createReferenceItem();
        else if (uval.getType() == VhdlTokenTypes.PLUS) return new AddingOperatorTerm.Plus(sc).createReferenceItem();
        else if (uval.getType() == VhdlTokenTypes.NOT) return new AddingOperatorTerm.Not(sc).createReferenceItem();
        else if (uval.getType() == VhdlTokenTypes.ABS) return new AddingOperatorTerm.Abs(sc).createReferenceItem();
        return null;
    }
    
}
