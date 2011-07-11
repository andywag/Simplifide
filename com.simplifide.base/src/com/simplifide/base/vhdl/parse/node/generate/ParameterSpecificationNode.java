/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.vhdl.parse.node.generate;

import com.simplifide.base.core.generate.ParameterSpecification;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.var.range.VarRange;
import com.simplifide.base.sourcefile.antlr.node.TopASTNodeNew;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

// identifier IN discrete_range

public class ParameterSpecificationNode extends TopASTNodeNew<ReferenceItem<ParameterSpecification>>{

	public ParameterSpecificationNode() {}
	
	public ReferenceItem<ParameterSpecification> generateModuleSmallNew(ParseContext context) {
		String paramName = this.getNode(0).getRealText();
		ReferenceItem<VarRange> rangeRef = (ReferenceItem<VarRange>) this.getNode(2).generateModule(context);
		ParameterSpecification pspec = new ParameterSpecification(paramName,rangeRef);
		return pspec.createReferenceItemWithLocation(context.createReferenceLocation(this.getNode(0)));
	}
	
}
