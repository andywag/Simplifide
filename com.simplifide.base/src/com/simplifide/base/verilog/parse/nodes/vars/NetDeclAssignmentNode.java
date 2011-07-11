/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.verilog.parse.nodes.vars;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.core.var.range.VarRange;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.verilog.core.var.VerilogVar;


/**
//net_decl_assignment ::= net_identifier { unpacked_dimension } [ = expression ]
net_decl_assignment : IDENTIFIER packed_dimension (EQUAL expression)* 
*/
public class NetDeclAssignmentNode extends VariableIdentNode {

    
	private static final long serialVersionUID = 1L;

	public NetDeclAssignmentNode() {}
    
    public TopObjectBase generateModuleSmallNew(ParseContext context) {
    	ReferenceLocation loc = context.createReferenceLocation(this);
    	TopASTNode identNode = this.getFirstASTChild();
    	TopASTNode dimNode = identNode.getNextASTSibling();
    	
    	String text = identNode.getRealText();
    	VerilogVar tvar = new VerilogVar(text,null,null);
    	if (dimNode != null) {
    		ReferenceItem<VarRange> range = (ReferenceItem<VarRange>) dimNode.generateModuleSmallNew(context);
    		tvar.setRangeRef(range);
    	}
    	
        return tvar.createReferenceItemWithLocation(loc);
    }
}
