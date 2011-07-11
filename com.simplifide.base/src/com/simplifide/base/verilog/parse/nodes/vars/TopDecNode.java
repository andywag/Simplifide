/*
 * TopDecNode.java
 *
 * Created on April 23, 2007, 9:30 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.verilog.parse.nodes.vars;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.sourcefile.antlr.node.TopASTNodeNew;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;



// variable_declaration list_of_variables range_top SEMI

/**
 *
 * @author Andy
 */
public class TopDecNode extends TopASTNodeNew{
    
    /** Creates a new instance of TopDecNode */
    public TopDecNode() {}

    @Override
    public TopObjectBase generateModuleSmallNew(ParseContext context) {
        return super.generateModuleSmallNew(context);
    }

    
}
