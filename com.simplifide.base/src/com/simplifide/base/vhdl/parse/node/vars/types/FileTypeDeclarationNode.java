/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.vhdl.parse.node.vars.types;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.var.types.FileTypeVar;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

// FILE OF name

public class FileTypeDeclarationNode extends TopASTNode{
    
    public TopObjectBase generateModuleSmallNew(ParseContext context) {
        TopASTNode child = this.getFirstASTChild();
        child = child.getNextASTSibling();
        child = child.getNextASTSibling();
        FileTypeVar ftype = new FileTypeVar("",child.getRealText());
        return ftype.createReferenceItem();
    }

}
