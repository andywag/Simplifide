package com.simplifide.base.vhdl.parse.node.function;

import com.simplifide.base.core.newfunction.InstanceFunction;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

public interface FunctionInterfaceNode {

	public ReferenceItem<InstanceFunction> returnInstanceFunction(ParseContext context);
}
