/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.verilog.parse.nodes;

import com.simplifide.base.sourcefile.antlr.node.TopASTNode;

public class FoldingNode extends TopASTNode {
    
    
    public boolean canFold() {
        return true;
    }
}
