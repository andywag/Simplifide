/*
 * ExpressionOpASTNode.java
 *
 * Created on December 13, 2005, 12:45 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.vhdl.parse.node.segment.core;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.segment.core.TopOpASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

/**
 *
 * @author awagner
 */
public class ParenOpASTNode extends TopOpASTNode{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Creates a new instance of ExpressionOpASTNode */
    public ParenOpASTNode() {}

    public TopObjectBase generateModuleSmallNew(ParseContext context)
    {
        TopASTNode child = this.getFirstASTChild();
        child = child.getNextASTSibling();
        return child.generateModule(context);
    }

    
    
}
