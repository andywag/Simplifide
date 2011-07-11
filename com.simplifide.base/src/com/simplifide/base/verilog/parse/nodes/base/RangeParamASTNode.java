/*
 * RangeASTNode.java
 *
 * Created on April 23, 2007, 2:52 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.verilog.parse.nodes.base;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

/**
 *
 * @author Andy
 */
public class RangeParamASTNode extends TopASTNode{
    
    /** Creates a new instance of RangeASTNode */
    public RangeParamASTNode() {}

    @Override
    public TopObjectBase generateModuleSmallNew(ParseContext context) {
        return this.getFirstASTChild().generateModuleSmallNew(context);
    }

    
}
