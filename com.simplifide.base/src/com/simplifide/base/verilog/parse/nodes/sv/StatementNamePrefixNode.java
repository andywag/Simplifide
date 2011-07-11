package com.simplifide.base.verilog.parse.nodes.sv;

import com.simplifide.base.basic.struct.ModuleObjectNew;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.sourcefile.antlr.node.TopASTNodeGeneric;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;


public class StatementNamePrefixNode extends TopASTNodeGeneric<ReferenceItem<ModuleObjectNew>> {

	public ReferenceItem<ModuleObjectNew> createObjectSmall(ParseContext context) {
		if (this.getNumberOfChildren() == 0) return null;
		ModuleObjectNew obj = new ModuleObjectNew(this.getFirstASTChild().getRealText());
		return obj.createReferenceItemWithLocation(context.createReferenceLocation(this.getFirstASTChild()));
	}
	
}
