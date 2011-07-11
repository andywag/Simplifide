/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.verilog.parse.nodes.port;

import com.simplifide.base.basic.struct.ModuleObjectNew;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.port.PortConnect;
import com.simplifide.base.core.port.PortTop;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

// DOT IDENTIFIER LPAREN (expression)? RPAREN

public class NamedPortExpressionNode extends TopASTNode{
	
	
	private static final long serialVersionUID = 1L;

	public NamedPortExpressionNode() {}
	
	public ReferenceItem findItemResolveContext(ParseContext context, int pos) {
        // Determine which context to search for the given node
        TopASTNode child = this.getFirstASTChild();
        child = child.getNextASTSibling();
        child = child.getNextASTSibling();
        if (pos <= child.getPosition().getStartPos()) {
            context.setActiveReference(context.getRefHandler().getSecondaryReference());
            context.getRefHandler().setLocalReference(context.getRefHandler().getSecondaryReference());
        }
        else {
            context.getRefHandler().setLocalReference(context.getRefHandler().getLocalReference());
            context.getRefHandler().setSearchReference(context.getRefHandler().getLocalReference());
        }
        return null;
    }
	
	public TopObjectBase generateModuleSmallNew(ParseContext context) {
		TopASTNode child = this.getFirstASTChild();
		child = child.getNextASTSibling(); // Port Identifier
		TopASTNode local = child;
		child = child.getNextASTSibling();
		child = child.getNextASTSibling(); //
		TopASTNode external = child;
		
		ReferenceItem externRef = (ReferenceItem) external.generateModule(context);
		ReferenceItem localRef = null;
		if (context.getRefHandler().getSecondaryReference() != null) {
			ReferenceItem currentLocal = context.getRefHandler().getLocalReference();
			context.getRefHandler().setLocalReference(context.getRefHandler().getSecondaryReference());
			localRef = (ReferenceItem) local.generateModule(context);
			context.getRefHandler().setLocalReference(currentLocal);
		}
		
		if (localRef != null) {
			if (localRef.getObject() instanceof PortTop) {
				PortTop port = (PortTop) localRef.getObject();
				localRef = port.getLocalVarReference();
			}
		}
		if (externRef == null) {
			externRef = new ModuleObjectNew(external.getRealText()).createReferenceItem();
		}
		PortConnect connect = new PortConnect(localRef,externRef);
		connect.setConnectionLocation(context.createReferenceLocation(local));
		return connect.createReferenceItemWithLocation(context.createReferenceLocation(local));
	}

}
