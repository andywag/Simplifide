/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.vhdl.parse.node.generate;

import com.simplifide.base.core.generate.GenerateIfStatement;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.TopASTNodeGeneric;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

// IF condition

public class GenerateIfNode extends TopASTNodeGeneric<ReferenceItem<GenerateIfStatement>> {

	public GenerateIfNode() {}
	
	public ReferenceItem<GenerateIfStatement> createObjectSmall(ParseContext context) {
		
		TopASTNode child = this.getFirstASTChild();
		child = child.getNextASTSibling();
		
		ReferenceItem ritem = (ReferenceItem) child.generateModule(context);
		
		
		GenerateIfStatement ifstatement = new GenerateIfStatement("",ritem);
		ifstatement.setBlockText(child.getRealText());
		
		return ifstatement.createReferenceItem();
	}
	
	
}
