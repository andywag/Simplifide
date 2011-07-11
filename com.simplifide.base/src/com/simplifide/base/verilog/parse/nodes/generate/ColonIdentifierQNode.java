/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.verilog.parse.nodes.generate;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.TopASTNodeNew;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

/* : Identifier
 */
public class ColonIdentifierQNode extends TopASTNodeNew {

	public ColonIdentifierQNode() {}

	public String getName() {
		TopASTNode child = this.getFirstASTChild();
		if (child == null) return null;
		child = child.getNextASTSibling();
		return child.getRealText();
	}
	
	/** Override with Generic Return Type */
	public ModuleObject generateModuleSmallNew(ParseContext context) {
		TopASTNode child = this.getFirstASTChild(); // :
		if (child == null) return null;
		
		child = child.getNextASTSibling(); // Named Node
		return new ModuleObject(child.getRealText()).createReferenceItem();
		
	}

	public boolean canFold() {
		return true;
	}

}
