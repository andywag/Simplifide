/*
 * ProcessStatementASTNode.java
 *
 * Created on December 6, 2005, 5:23 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.vhdl.parse.node.segment.process;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.segment.basic.assign.SequenceStatements;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

/**
 *
 * @author Andy Wagner
 */

// Process Head

public class VhdlSequenceOfStatementsASTNode extends TopASTNode
{
    
    
	private static final long serialVersionUID = 1L;


	/** Creates a new instance of ProcessStatementASTNode */
    public VhdlSequenceOfStatementsASTNode() {}
   

    public ReferenceItem<SequenceStatements> generateModuleSmallNew(ParseContext context)
    {
        SequenceStatements holder = new SequenceStatements("Statements");
        TopASTNode child =  this.getFirstASTChild(); // process head

        while (child != null)
        {
            ModuleObject nobj = (ModuleObject) child.generateModule(context);
            holder.addObject(nobj);
            child = child.getNextASTSibling();
        }
        return holder.createReferenceItem();
        
    }


    
    
    
}
