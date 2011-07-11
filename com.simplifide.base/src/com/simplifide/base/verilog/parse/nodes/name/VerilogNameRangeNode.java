/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.verilog.parse.nodes.name;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.finder.ModuleObjectRange;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.var.range.VarRange;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.namedec.NameRangeASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

public class VerilogNameRangeNode extends NameRangeASTNode{

	
	public TopObjectBase generateModuleSmallNew(ParseContext context)
    {
        TopASTNode child = this.getFirstASTChild(); // (
        ReferenceItem<VarRange> rangeRef = (ReferenceItem) child.generateModule(context);
        ModuleObjectRange range = new ModuleObjectRange(rangeRef);
        return range;
    }
	
}
