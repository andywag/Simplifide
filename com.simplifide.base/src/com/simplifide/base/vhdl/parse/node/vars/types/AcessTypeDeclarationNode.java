/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.vhdl.parse.node.vars.types;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.var.types.AccessTypeVar;
import com.simplifide.base.core.var.types.TypeVar;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

// ACCESS subtype_indication


/** @todo : No access type defined just skips that portion */
public class AcessTypeDeclarationNode extends TopASTNode{

    public TopObjectBase generateModuleSmallNew(ParseContext context) {
        TopASTNode child = this.getFirstASTChild();
        child = child.getNextASTSibling();
        ReferenceItem<? extends TypeVar> typeVar = (ReferenceItem<? extends TypeVar>) child.generateModule(context);
        AccessTypeVar access = new AccessTypeVar("access",typeVar);
        return access.createReferenceItem();
    }
}
