/*
 * VhdlVarAssignASTNode.java
 *
 * Created on December 13, 2005, 2:05 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.vhdl.parse.node.vars.signal;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

/**
 *
 * @author awagner
 */
public class VhdlVarAssignASTNode extends TopASTNode
{
    
	private static final long serialVersionUID = 1L;

	/** Creates a new instance of VhdlVarAssignASTNode */
    public VhdlVarAssignASTNode() {}
    
    /** @todo : Not sure where this is used */
    public TopObjectBase generateModuleSmallNew(ParseContext context)
    {
        TopASTNode child = this.getFirstASTChild(); // top
        if (child == null) return null;
        child = child.getNextASTSibling();
        context.setDefinedMode(ParseContext.ERROR_NOT_DEFINED_DISABLED);
        TopObjectBase base =  child.generateModule(context);
        context.setDefinedMode(ParseContext.ERROR_NOT_DEFINED_ENABLED);
    
        return base;
    }
    
   
    
}
