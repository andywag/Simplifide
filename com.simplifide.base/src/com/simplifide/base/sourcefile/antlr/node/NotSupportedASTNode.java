/*
 * NotSupportedASTNode.java
 *
 * Created on August 24, 2006, 10:29 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.sourcefile.antlr.node;

import antlr.Token;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.project.BuildInterface;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;


/**
 *
 * @author awagner
 */
public class NotSupportedASTNode extends TopASTNode
{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Creates a new instance of NotSupportedASTNode */
    public NotSupportedASTNode() {}
    public NotSupportedASTNode(Token tok) {super(tok);}
    
    public TopObjectBase generateModuleSmallNew(ParseContext context)
    {
    	if (context.getPass() == BuildInterface.BUILD_FIND_USAGES ) {
    		TopASTNode child = this.getFirstASTChild();
    		while (child != null) {
    			child.generateModule(context);
    			child = child.getNextASTSibling();
    		}
    	}
        return null;
    }
}
