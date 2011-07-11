/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.vhdl.parse.node.generate;

import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.sourcefile.antlr.node.TopASTNodeNew;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;


// ( architecture_statement )*

public class GenerateStatementPartNode extends TopASTNodeNew<ReferenceItem<ModuleObjectWithList>> {

	public GenerateStatementPartNode() {}
	
	public ReferenceItem<ModuleObjectWithList> generateModuleSmallNew(ParseContext context) {
		this.generateModuleStatements(context);
		return null;
	}
}
