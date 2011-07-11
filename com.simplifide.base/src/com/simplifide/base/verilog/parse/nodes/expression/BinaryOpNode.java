/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.verilog.parse.nodes.expression;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.segment.basic.operator.GenericMultiOperatorUnit;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;


/*
binary_operator :
    PLUS        |
    MINUS       |
    STAR        |
    DIV         |
    MOD         |
    EQUAL       |
    NOT_EQ      |
    EQ_CASE     |
    NOT_EQ_CASE |
    LAND        |
    LOR         |
    LT_         |
    LE          |
    GT          |
    GE          |
    BAND        |
    BOR         |
    BXOR        |
    BXNOR       |
    SR          |
    SL
    ;
*/
public class BinaryOpNode extends TopASTNode{

	public TopObjectBase generateModuleSmallNew(ParseContext context) {
		TopASTNode opNode = this.getFirstASTChild();
		TopASTNode baseNode = opNode.getNextASTSibling();
		
		// There are certain cases where this doesn't return the desired reference item 
		// which is unimportant for the majority of operations and will be ignroed for now
		ModuleObject base1 = (ModuleObject) baseNode.generateModule(context);
		ReferenceItem base = null;
		if (base1 instanceof ReferenceItem) {
			base = (ReferenceItem) base1;
			
		}
		
		GenericMultiOperatorUnit unit = new GenericMultiOperatorUnit(opNode.getRealText(),base);
		return unit.createReferenceItem();
	}
	
}
