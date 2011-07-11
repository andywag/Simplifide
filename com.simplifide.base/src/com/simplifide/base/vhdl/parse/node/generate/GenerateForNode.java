/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.vhdl.parse.node.generate;

import com.simplifide.base.core.generate.GenerateForStatement;
import com.simplifide.base.core.generate.ParameterSpecification;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.TopASTNodeGeneric;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

// FOR parameter_specification

public class GenerateForNode extends TopASTNodeGeneric<ReferenceItem<GenerateForStatement>> {

	
	private static final long serialVersionUID = 1L;

	public GenerateForNode() {}
	
	public ReferenceItem<GenerateForStatement> createObjectSmall(ParseContext context) {
		TopASTNode child = this.getFirstASTChild();
		child = child.getNextASTSibling();
		
		ReferenceItem<ParameterSpecification> pspecRef = (ReferenceItem<ParameterSpecification>) child.generateModule(context);
		GenerateForStatement fstate = new GenerateForStatement("",pspecRef);
		fstate.setBlockText(this.getRealTextSpace());
		return fstate.createReferenceItem();
	}
	
}
