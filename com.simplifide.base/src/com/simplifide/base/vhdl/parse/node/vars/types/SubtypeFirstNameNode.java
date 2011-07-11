/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.vhdl.parse.node.vars.types;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

public class SubtypeFirstNameNode extends TopASTNode{

    public SubtypeFirstNameNode() {}
    
    public TopObjectBase generateModuleSmallNew(ParseContext context) {
        TopASTNode child = this.getFirstASTChild();
        if (child.getRealText().equalsIgnoreCase("(")) {
            child = child.getNextASTSibling();
        }
        return child.generateModule(context);
    }
 
    public String getIdentText() {
        TopASTNode child = this.getFirstASTChild();
        if (child.getRealText().equalsIgnoreCase("(")) {
            child = child.getNextASTSibling();
        }
        return child.getRealText();
    }
    
}
