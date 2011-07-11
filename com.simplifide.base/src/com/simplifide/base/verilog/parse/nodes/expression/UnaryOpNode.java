/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.verilog.parse.nodes.expression;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.segment.basic.operator.GenericUniOperator;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;


/*

unop : unary_operator exp9

unary_operator :
    PLUS   |
    MINUS  |
    LNOT   |
    BNOT   |
    BAND   |
    RNAND  |
    BOR    |
    RNOR   |
    BXOR   |
    RXNOR
    ;
*/

public class UnaryOpNode extends TopASTNode{

	
	
	
	public TopObjectBase generateModuleSmallNew(ParseContext context) {
		if (this.getNumberOfChildren() == 1) return this.getFirstASTChild().generateModule(context);
		
		TopASTNode unaryNode = this.getFirstASTChild();
		TopASTNode expNode   = unaryNode.getNextASTSibling();
		ReferenceItem expRef = (ReferenceItem) expNode.generateModule(context);
		
		GenericUniOperator op = new GenericUniOperator(this.getRealText(),expRef);
		return op.createReferenceItem();
	}
}
